package Controller.EmSheBao;

import impl.SystemControl.Data_PopedomIpml;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import service.DataPopedomService;

import bll.EmSheBao.Emsc_DeclareSelBll;

import Model.LoginModel;
import Util.DateStringChange;

public class Emsi_Bj_SearchController {
	private List<String> ownmonthList; // 所属月份
	private List<LoginModel> clientList; // 客服
	private String[] count; // 统计数据的数组
	// 页面表单值
	private String ownmonth;
	private String emcon;
	private String emtype;
	private String client;
	private String cocon;
	private String cotype;
	private String dstate;
	private String single;
	private String addname;
	private String dept;

	public Emsi_Bj_SearchController() {
		DataPopedomService c_dps = new Data_PopedomIpml();
		Emsc_DeclareSelBll selbll = new Emsc_DeclareSelBll();
		clientList = c_dps.getdepLoginlist();
		ownmonthList = selbll.getChangeOwnmonthList();
		// 当前月份
		String nowMonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");
		ownmonth = nowMonth;
		count = selbll.getBjDataCount(Integer.parseInt(nowMonth));
		// 页面值初始化
		client = "全部";
		cotype = "公司简称";
		emtype = "员工姓名";
		dstate = "全部";
		single = "全部";
		addname = "全部";
		dept = "全部";
	}

	@Command("search")
	public void search() {
		String sql = getSql();
		if (!"".equals(sql)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sql", sql);
			map.put("ownmonth", ownmonth);
			Window window = (Window) Executions.createComponents(
					"Emsi_Bj_List.zul", null, map);
			window.doModal();
		}
	}

	// 根据检索数据拼接SQL
	private String getSql() {
		StringBuilder sb = new StringBuilder();
		try {
			if (emcon != null && !"".equals(emcon)) {
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
					sb.append(" and bj.Ownmonth=");
					sb.append(ownmonth);
				}
				if (!"全部".equals(client)) {
					sb.append(" and coba_client='");
					sb.append(client);
					sb.append("'");
				}
				if (cocon != null && !"".equals(cocon)) {
					switch (cotype) {
					case "公司简称":
						sb.append(" and emsb_Company like '%");
						sb.append(cocon);
						sb.append("%'");
						break;
					case "公司编号":
						sb.append(" and bj.cid=");
						sb.append(cocon);
						break;
					}
				}
				if (!"全部".equals(dstate)) {
					switch (dstate) {
					case "已申报":
						sb.append(" and emsb_ifdeclare=1");
						break;
					case "已交单":
						sb.append(" and emsb_ifdeclare=2");
						break;
					case "已打单":
						sb.append(" and emsb_ifdeclare=6");
						break;
					case "退回":
						sb.append(" and emsb_ifdeclare=3");
						break;
					case "待确认":
						sb.append(" and emsb_ifdeclare=4");
						break;
					case "审核中":
						sb.append(" and emsb_ifdeclare=5");
						break;
					}
				}
				if (!"全部".equals(single)) {
					switch (single) {
					case "独立开户":
						sb.append(" and emsb_single=1");
						break;
					case "中智开户":
						sb.append(" and emsb_single=0");
						break;
					case "中智开户(委托)":
						sb.append(" and emsb_single=2");
						break;
					}
				}
				if (!"全部".equals(addname)) {
					sb.append(" and emsb_addname='");
					sb.append(addname);
					sb.append("'");
				}
				if (!"全部".equals(dept)) {
					sb.append(" and coba_client in (select log_name from login where dep_id=(select dep_id from department where dep_name='");
					sb.append(dept);
					sb.append("'))");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
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

	public String getCocon() {
		return cocon;
	}

	public void setCocon(String cocon) {
		this.cocon = cocon;
	}

	public String getCotype() {
		return cotype;
	}

	public void setCotype(String cotype) {
		this.cotype = cotype;
	}

	public String getDstate() {
		return dstate;
	}

	public void setDstate(String dstate) {
		this.dstate = dstate;
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

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public List<LoginModel> getClientList() {
		return clientList;
	}

	public String[] getCount() {
		return count;
	}

}
