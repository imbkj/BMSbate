package Controller.EmSheBao;

import impl.SystemControl.Data_PopedomIpml;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import bll.EmSheBao.Emsc_DeclareSelBll;

import service.DataPopedomService;

import Model.CoCompactModel;
import Model.LoginModel;
import Util.DateStringChange;

public class Emsc_DeclareBj_SearchController {
	private List<String> ownmonthList; // 所属月份
	private List<LoginModel> clientList; // 客服
	private List<LoginModel> dNameList; // 申报人
	private List<CoCompactModel> companyList; // 独立开户公司
	private String[] count; // 统计数据的数组
	private int ifExecl;
	// 页面表单值
	private String ownmonth;
	private String ifdeclare;
	private String emcon;
	private String emtype;
	private String client;
	private String single;
	private String addname;
	private String dmonth;
	private String dday;
	private String dbefore;
	private String amonth;
	private String aday;
	private String abefore;
	private String excelfile;
	private String dname;

	private int ifUpload;
	private int qType = 0;

	public Emsc_DeclareBj_SearchController() {
		DataPopedomService c_dps = new Data_PopedomIpml();
		DataPopedomService dn_dps = new Data_PopedomIpml("", "社保");
		Emsc_DeclareSelBll selbll = new Emsc_DeclareSelBll();
		clientList = c_dps.getdepLoginlist();
		dNameList = dn_dps.getroleLoginlist();
		companyList = selbll.getSCompany();
		ownmonthList = selbll.getBjOwnmonthList();
		String nowMonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");
		count = selbll.getBjDataCount(Integer.parseInt(nowMonth));
		ownmonth = nowMonth;
		ifdeclare = "全部";
		emtype = "员工姓名";
		client = "全部";
		single = "全部";
		addname = "全部";
		dname = "全部";
	}

	@Command("search")
	public void search(@BindingParam("cb") Combobox cb,
			@BindingParam("win") Window win) {
		int cid = 0;
		if (!"所有公司".equals(cb.getSelectedItem().getValue())) {
			cid = Integer.parseInt(cb.getSelectedItem().getValue().toString());
		}
		String sql = getSql(cid);
		if (!"".equals(sql)) {
			win.detach();
			Map<String, String> map = new HashMap<String, String>();
			map.put("sql", sql);
			map.put("ownmonth", ownmonth);
			map.put("ifExecl", String.valueOf(ifExecl));
			map.put("ifUpload", String.valueOf(ifUpload));
			Window window = (Window) Executions.createComponents(
					"Emsc_DeclareBj_List.zul", null, map);
			window.doModal();
		}

	}

	@Command("qSearch")
	public void qSearch(@BindingParam("win") Window win,
			@BindingParam("qType") Integer q_type) {

		qType = q_type;

		String sql = getSql(0);
		if (!"".equals(sql)) {
			win.detach();
			Map<String, String> map = new HashMap<String, String>();
			map.put("sql", sql);
			map.put("ownmonth", ownmonth);
			map.put("ifExecl", String.valueOf(ifExecl));
			map.put("ifUpload", String.valueOf(ifUpload));
			Window window = (Window) Executions.createComponents(
					"Emsc_DeclareBj_List.zul", null, map);
			window.doModal();
		}

	}

	// 根据检索数据拼接SQL
	private String getSql(int cid) {
		ifExecl = 2;// 判断申报状态及开户状态是否已选；
		ifUpload = 0;// 判断申报状态是否只选择“未申报”
		StringBuilder sb = new StringBuilder();
		try {
			if (qType != 0) {
				sb.append(" and type='EmShebaoBJJL' and bj.emsb_ifdeclare<>1 and bj.emsb_ifdeclare<>3 ");
			} else if (emcon != null && !"".equals(emcon)) {
				switch (emtype) {
				case "员工姓名":
					sb.append(" and emsb_name like '%");
					sb.append(emcon);
					sb.append("%'");
					break;
				case "员工编号":
					sb.append(" and bj.gid=");
					sb.append(emcon);
					break;
				case "身份证":
					sb.append(" and emsb_idcard='");
					sb.append(emcon);
					sb.append("'");
					break;
				case "电脑号":
					sb.append(" and emsb_Computerid='");
					sb.append(emcon);
					sb.append("'");
					break;
				}
			} else {
				if (ownmonth != null) {
					sb.append(" and Ownmonth=");
					sb.append(ownmonth);
				}
				if (!"全部".equals(ifdeclare)) {
					if (!"退回".equals(ifdeclare)) {
						sb.append(" and emsb_Ifdeclare=");
						sb.append(tranIfdeclare());
					} else {
						sb.append(" and emsb_Ifdeclare in(3,9)");
					}
					ifExecl--;

					if ("未申报".equals(ifdeclare)) {
						ifUpload = 1;
					}
				}
				if (cid != 0) {
					sb.append(" and bj.CID=");
					sb.append(cid);
				}
				if (!"全部".equals(client)) {
					sb.append(" and cid in (select cid from cobase where coba_client='");
					sb.append(client);
					sb.append("')");
				}
				if (!"全部".equals(single)) {
					sb.append(" and emsb_single=");
					sb.append(tranSingle());
					ifExecl--;
				}
				if (!"全部".equals(addname)) {
					sb.append(" and emsb_addname='");
					sb.append(addname);
					sb.append("'");
				}
				if (dday != null) {
					if (dmonth != null) {
						sb.append(" and month(emsb_declaretime)=");
						sb.append(dmonth.replace("月", ""));
					}
					if (dbefore != null) {
						switch (dbefore) {
						case "之前":
							sb.append(" and day(emsb_declaretime)<");
							sb.append(dday.replace("日", ""));
							break;
						case "之后":
							sb.append(" and day(emsb_declaretime)>");
							sb.append(dday.replace("日", ""));
							break;
						case "当日":
							sb.append(" and day(emsb_declaretime)=");
							sb.append(dday.replace("日", ""));
							break;
						}
					}
				}
				if (aday != null && amonth != null && abefore != null) {
					switch (abefore) {
					case "之前":
						sb.append(" and day(emsb_addname)<");
						sb.append(aday.replace("日", ""));
						sb.append(" and month(emsb_addname)<=");
						sb.append(amonth.replace("月", ""));
						break;
					case "之后":
						sb.append(" and day(emsb_addname)>");
						sb.append(aday.replace("日", ""));
						sb.append(" and month(emsb_addname)>=");
						sb.append(amonth.replace("月", ""));
						break;
					case "当日":
						sb.append(" and day(emsb_addname)=");
						sb.append(aday.replace("日", ""));
						sb.append(" and month(emsb_addname)=");
						sb.append(amonth.replace("月", ""));
						break;
					}
				}
				if (excelfile != null) {
					sb.append(" and emsb_excelfile='");
					sb.append(excelfile);
					sb.append("'");
				}
				if (!"全部".equals(dname)) {
					sb.append(" and emsb_DeclareName='");
					sb.append(dname);
					sb.append("'");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	// 转换申报状态为int
	private int tranIfdeclare() {
		switch (ifdeclare) {
		case "未申报":
			return 0;
		case "已申报":
			return 1;
		case "已交单":
			return 6;
		case "待申报":
			return 8;
		case "已上单":
			return 2;
		case "退回":
			return 3;
		case "中心待核收":
			return 7;
		default:
			return -1;
		}
	}

	// 转换开户状态为int
	private int tranSingle() {
		switch (single) {
		case "中智开户":
			return 0;
		case "独立开户":
			return 1;
		case "中智开户(委托)":
			return 2;
		default:
			return -1;
		}
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getIfdeclare() {
		return ifdeclare;
	}

	public void setIfdeclare(String ifdeclare) {
		this.ifdeclare = ifdeclare;
	}

	public String getEmcon() {
		return emcon;
	}

	public void setEmcon(String emcon) {
		this.emcon = emcon;
	}

	public String getEmtype() {
		return emtype;
	}

	public void setEmtype(String emtype) {
		this.emtype = emtype;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public String getDmonth() {
		return dmonth;
	}

	public void setDmonth(String dmonth) {
		this.dmonth = dmonth;
	}

	public String getDday() {
		return dday;
	}

	public void setDday(String dday) {
		this.dday = dday;
	}

	public String getDbefore() {
		return dbefore;
	}

	public void setDbefore(String dbefore) {
		this.dbefore = dbefore;
	}

	public String getAmonth() {
		return amonth;
	}

	public void setAmonth(String amonth) {
		this.amonth = amonth;
	}

	public String getAday() {
		return aday;
	}

	public void setAday(String aday) {
		this.aday = aday;
	}

	public String getAbefore() {
		return abefore;
	}

	public void setAbefore(String abefore) {
		this.abefore = abefore;
	}

	public String getExcelfile() {
		return excelfile;
	}

	public void setExcelfile(String excelfile) {
		this.excelfile = excelfile;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public List<LoginModel> getClientList() {
		return clientList;
	}

	public List<LoginModel> getdNameList() {
		return dNameList;
	}

	public List<CoCompactModel> getCompanyList() {
		return companyList;
	}

	public String[] getCount() {
		return count;
	}

}
