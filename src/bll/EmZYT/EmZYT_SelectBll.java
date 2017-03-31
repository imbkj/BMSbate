package bll.EmZYT;

import impl.PubCityImpl;

import java.util.ArrayList;
import java.util.List;

import service.PubCityService;

import dal.CoBase.CoBase_SelectDal;
import dal.EmZYT.EmZYT_SelectDal;
import dal.Embase.Embasedal;

import Model.CoBaseModel;
import Model.EmZYTFeedbackFileModel;
import Model.EmZYTFeedbackModel;
import Model.EmZYTModel;
import Model.EmbaseModel;

public class EmZYT_SelectBll {
	EmZYT_SelectDal dal = new EmZYT_SelectDal();

	// 获取智翼通接口委托单数据
	public List<EmZYTModel> getEmZYTList(String str) {
		return dal.getEmZYTList(str);
	}

	// 获取智翼通接口委托单数据
	public List<EmZYTModel> getEmZYTFeeList(String str,String ownmonth,String smonth) {
		return dal.getEmZYTFeeList(str,ownmonth,smonth);
	}

	// 获取智翼通接口反馈数据
	public List<EmZYTFeedbackModel> getEmZYTFeedbackList(String str) {
		return dal.getEmZYTFeedbackList(str);
	}

	// 获取合同履行地(所有城市)
	public List<String> getCityName() throws Exception {
		PubCityService pcs = new PubCityImpl();
		List<String> city = pcs.getCityName();
		return city;
	}

	// 获取委托地区
	public List<String> getZYTCity() {
		List<String> city = dal.getZYTCity();
		return city;
	}

	// 获取客服代表
	public List<String> getZYTClient() {
		List<String> city = dal.getZYTClient();
		return city;
	}

	// 获取委托事件类型
	public List<String> getSclass() {
		ArrayList<String> dataList = new ArrayList<String>();
		dataList.add("全部");
		dataList.add("新增");
		dataList.add("社保基数调整");
		dataList.add("户籍调整");
		dataList.add("特殊调整");
		dataList.add("服务项目调整");
		dataList.add("服务费调整");
		dataList.add("年度调整");
		dataList.add("终止");
		dataList.add("一次性费用");
		dataList.add("调整");
		return dataList;
	}

	// 获取委托处理状态
	public List<String[]> getState(int way) {
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		dataList.add(new String[] { "99", "全部" });
		if (way == 1 || way == 3) {// way=1为管理页面传入，way=2为申报页面传入；申报不显示未处理数据
			dataList.add(new String[] { "10", "未处理" });
		}
		dataList.add(new String[] { "2", "待处理" });
		dataList.add(new String[] { "1", "已处理" });
		dataList.add(new String[] { "3", "退单" });
		dataList.add(new String[] { "11", "退单(待跟踪)" });
		if (way == 3) {// way=3为金额比对页面传入
			dataList.add(new String[] { "100", "非退单和非未处理" });
		}
		return dataList;
	}

	// 获取委托处理状态
	public List<String[]> getOutState() {
		ArrayList<String[]> dataList = new ArrayList<String[]>();
		dataList.add(new String[] { "0", "未反馈" });
		dataList.add(new String[] { "2", "反馈中" });
		dataList.add(new String[] { "1", "已反馈" });
		dataList.add(new String[] { "3", "部分反馈" });
		return dataList;
	}

	// 获取员工查询类型
	public List<String> getEmkeyType() {
		ArrayList<String> dataList = new ArrayList<String>();
		dataList.add("姓名");
		dataList.add("员工编号");
		dataList.add("身份证");
		dataList.add("委托方雇员编号");
		dataList.add("智翼通序号");
		return dataList;
	}

	// 根据way获取委托单 福利津小计、服务费、档案费、总计、服务费是否含档案费 显示内容
	public String getSpecInfo(int way, String type, String city, String data) {
		String result = "";

		if (type.equals("福利津小计") || type.equals("总计") || type.equals("服务费")) {
			if (way == 1) {
				result = data;
			} else {
				result = "-";
			}

		} else if (type.equals("档案费")) {

			int dataInt;
			try {
				dataInt = Integer.parseInt(data);
			} catch (Exception e) {
				dataInt = 0;
			}

			if (!city.equals("上海")) {
				if (way == 1) {
					result = data;
				} else {
					if (dataInt > 0) {
						result = "保管";
					} else {
						result = "不保管";
					}
				}
			} else {
				if (dataInt > 0) {
					result = "有";
				} else {
					result = "无";
				}
			}
		} else if (type.equals("服务费是否含档案费")) {
			if (!city.equals("上海")) {
				if (data.equals("1")) {
					result = "(服务费包含档案费)";
				} else {
					result = "(不包含档案费)";
				}
			} else {
				result = "-";
			}
		}
		return result;
	}

	// 获取新增数据的报价单新增日期
	public String getEZCGAddtime(int id) {
		return dal.getEZCGAddtime(id);
	}

	// 获取公司信息
	public List<CoBaseModel> getCobaseinfo(String company) {
		CoBase_SelectDal cDal = new CoBase_SelectDal();
		return cDal.getCobaseinfo(" AND coba_company LIKE '%" + company + "%'");
	}

	// 获取员工信息
	public List<EmbaseModel> getembaList(String idcard) {
		Embasedal eDal = new Embasedal();
		return eDal.getembaList(" AND emba_idcard='" + idcard + "'");
	}

	// 获取联系方式
	public List<String> getContacttype() {
		ArrayList<String> dataList = new ArrayList<String>();
		dataList.add("联系员工本人");
		dataList.add("联系HR");
		dataList.add("联系指定联系人");
		dataList.add("联系委托机构");
		dataList.add("客服提供");
		dataList.add("重新入职");
		return dataList;
	}

	// 获取EmBaseNotIn表id
	public int[] getEmBaseNotInId(String idcard) {
		return dal.getEmBaseNotInId(idcard);
	}

	// 获取报价单表id
	public List<Integer> getCoofId(int emzt_id) {
		return dal.getCoofId(emzt_id);
	}

	// 获取智翼通接口预选的报价单项目表id
	public List<Integer> getColiId(int emzt_id) {
		return dal.getColiId(emzt_id);
	}

	// 上传委托成功行数
	public Integer getUploadCount(String filename) {
		return dal.getUploadCount(filename);
	}

	// 获取EmBase表数据
	public List<EmbaseModel> getEmbaseInfo(String str) {
		return dal.getEmbaseInfo(str);
	}

	// 获取智翼通接口委托单数据
	public List<EmZYTFeedbackFileModel> getEmZYTFeedbackFileList(String str) {
		return dal.getEmZYTFeedbackFileList(str);
	}

	// 通过智翼通获取社保城市备注获取bms社保模板title
	public String getShebaoFormula(String zyt_title,Integer ifForeigner) {
		return dal.getShebaoFormula(zyt_title,ifForeigner);
	}
}
