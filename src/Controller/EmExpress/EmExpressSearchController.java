package Controller.EmExpress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Model.EmExpressInfoModel;
import Util.PhotoNumberUtil;

public class EmExpressSearchController {
	private EmExpressInfoModel model = (EmExpressInfoModel) Executions
			.getCurrent().getArg().get("model");
	private List<EmExpressInfoModel> list = new ArrayList<EmExpressInfoModel>();
	private String errorMsg = "没有数据";

	public EmExpressSearchController() {
		list = getExpressLog(model.getExpr_waynumber(), model.getExpr_company());
	}

	// 根据快递单号查询物流动态
	private List<EmExpressInfoModel> getExpressLog(String expr_number,
			String waycompany) {
		String expresscode = "";
		if (waycompany != null && !waycompany.equals("")) {
			if (waycompany.contains("圆通")) {
				expresscode = "YT";
			} else if (waycompany.contains("申通")) {
				expresscode = "ST";
			} else if (waycompany.contains("中通")) {
				expresscode = "ZT";
			} else if (waycompany.contains("EMS")) {
				expresscode = "YZEMS";
			} else if (waycompany.contains("天天")) {
				expresscode = "TT";
			} else if (waycompany.contains("优速")) {
				expresscode = "YS";
			} else if (waycompany.contains("快捷")) {
				expresscode = "KJ";
			} else if (waycompany.contains("全峰")) {
				expresscode = "QF";
			} else if (waycompany.contains("增益")) {
				expresscode = "ZY";
			} else {
				errorMsg = "暂时不支持" + waycompany;
			}
		}
		String httpUrl = "http://apis.baidu.com/ppsuda/waybillnoquery/waybillnotrace";
		String httpArg = "expresscode=" + expresscode + "&billno="
				+ expr_number;
		String jsonResult = request(httpUrl, httpArg);
		List<EmExpressInfoModel> exprList = new ArrayList<EmExpressInfoModel>();

		JSONObject object = JSONObject.fromObject(jsonResult);
		try {
			if (object.get("errNum") != null) {
				String errorNum = object.getString("errNum");
				switch (errorNum) {
				case "300101":
					errorMsg = "用户请求过期";
					break;
				case "300102":
					errorMsg = "用户日调用量超限";
					break;
				case "300103":
					errorMsg = "服务每秒调用量超限";
					break;
				case "300104":
					errorMsg = "服务日调用量超限";
					break;
				case "300201":
					errorMsg = "地址有误";
					break;
				case "300202":
					errorMsg = "缺少apikey，请先系IT人员处理";
					break;
				case "300206":
					errorMsg = "快递查询服务已关闭";
					break;
				case "300302":
					errorMsg = "系统繁忙稍候再试";
					break;
				case "300204":
					errorMsg = "apikey无效，请先系IT人员处理";
					break;
				default:
					errorMsg = "快递查询出错";
					break;
				}
			}
		} catch (Exception e) {

		}
		if (errorMsg == null || errorMsg.equals("") || errorMsg.equals("没有数据")) {
			JSONArray array = object.getJSONArray("data");
			if (array.size() > 0) {
				JSONObject objbill = array.getJSONObject(0);
				JSONArray wayBilllist = objbill.getJSONArray("wayBills");
				for (int i = wayBilllist.size() - 1; i >= 0; i--) {
					EmExpressInfoModel m = new EmExpressInfoModel();
					JSONObject obj = wayBilllist.getJSONObject(i);
					if (obj.get("time") != null) {
						m.setExpr_operattime(obj.getString("time"));
					}
					if (obj.get("processInfo") != null) {
						m.setExpr_operatecontent(obj.getString("processInfo"));
					}
					if (obj.get("remark") != null) {
						m.setExpr_remark(obj.getString("remark"));
					}
					exprList.add(m);
				}
			}
		} else {
			Messagebox.show(errorMsg, "错误提示", Messagebox.OK, Messagebox.ERROR);
		}
		return exprList;
	}

	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			//apikey获取访问百度apistore网站的pp速达获取，网址http://apistore.baidu.com/apiworks/servicedetail/1055.html
			connection.setRequestProperty("apikey",
					"8c5adac67aaff5d8ed638ece273a0e71");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<EmExpressInfoModel> getList() {
		return list;
	}

	public void setList(List<EmExpressInfoModel> list) {
		this.list = list;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
