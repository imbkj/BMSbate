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
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmSheBao.Emsc_DeclareSelBll;

import Model.LoginModel;
import Util.DateStringChange;

public class Emsi_Change_SearchController {
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
	private String change;
	private String dstate;
	private String single;
	private String opstate;
	private String addname;
	private String dept;
	private boolean ifmodify = false;
	private boolean ifmsg = false;

	public Emsi_Change_SearchController() {
		DataPopedomService c_dps = new Data_PopedomIpml();
		Emsc_DeclareSelBll selbll = new Emsc_DeclareSelBll();
		 clientList = c_dps.getdepLoginlist();
		ownmonthList = selbll.getChangeOwnmonthList();
		// 当前月份
		String nowMonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");
		ownmonth = nowMonth;
		// 数据统计
		EmSheBao_DSelectBll sbbll = new EmSheBao_DSelectBll();
		count = sbbll.getChangeDataCount(nowMonth);
		// 页面值初始化
		client = "全部";
		cotype = "公司名称";
		emtype = "员工姓名";
		change = "全部";
		dstate = "全部";
		single = "全部";
		opstate = "全部";
		addname = "全部";
		dept = "全部";
	}

	@Command("search")
	public void search() {
		String sql = getSql();
		if (!"".equals(sql)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("sql", sql);
			Window window = (Window) Executions.createComponents(
					"Emsi_Change_List.zul", null, map);
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
					sb.append(" and emsc_name like '%");
					sb.append(emcon);
					sb.append("%'");
					break;
				case "员工编号":
					sb.append(" and gid=");
					sb.append(emcon);
					break;
				case "身份证":
					sb.append(" and emsc_idcard='");
					sb.append(emcon);
					sb.append("'");
					break;
				case "电脑号":
					sb.append(" and emsc_Computerid='");
					sb.append(emcon);
					sb.append("'");
					break;
				}
			} else {
				if (ownmonth != null) {
					sb.append(" and ec.Ownmonth=");
					sb.append(ownmonth);
				}
				if (!"全部".equals(client)) {
					sb.append(" and emsc_client='");
					sb.append(client);
					sb.append("'");
				}
				if (cocon != null && !"".equals(cocon)) {
					switch (cotype) {
					case "公司名称":
						sb.append(" and emsc_Company like '%");
						sb.append(cocon);
						sb.append("%'");
						break;
					case "公司编号":
						sb.append(" and ec.cid=");
						sb.append(cocon);
						break;
					}
				}
				if (!"全部".equals(change)) {
					sb.append(" and emsc_change='");
					sb.append(change);
					sb.append("'");
				}
				if (!"全部".equals(dstate)) {
					switch (dstate) {
					case "已申报":
						sb.append(" and emsc_ifdeclare=1");
						break;
					case "申报中":
						sb.append(" and emsc_ifdeclare=2");
						break;
					case "退回":
						sb.append(" and emsc_ifdeclare=3");
						break;
					case "待确认":
						sb.append(" and emsc_ifdeclare=4");
						break;
					case "审核中":
						sb.append(" and emsc_ifdeclare=5");
						break;
					}
				}
				if (!"全部".equals(single)) {
					switch (single) {
					case "独立开户":
						sb.append(" and emsc_single=1");
						break;
					case "中智开户":
						sb.append(" and emsc_single=0");
						break;
					case "中智开户(委托)":
						sb.append(" and emsc_single=2");
						break;
					}
				}
				if (!"全部".equals(opstate)) {
					switch (opstate) {
					case "已生效":
						sb.append(" and emsc_ifinure=1");
						break;
					case "未生效":
						sb.append(" and emsc_ifinure=0");
						break;

					}
				}
				if (!"全部".equals(addname)) {
					sb.append(" and emsc_addname='");
					sb.append(addname);
					sb.append("'");
				}
				if (!"全部".equals(dept)) {
					sb.append(" and emsc_client in (select log_name from login where dep_id=(select dep_id from department where dep_name='");
					sb.append(dept);
					sb.append("'))");
				}
				if (ifmodify) {
					sb.append(" and emsc_ifmodify=1");
				}
				if (ifmsg) {
					sb.append(" and emsc_ifmsg=1");
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

	public String getChange() {
		return change;
	}

	public void setChange(String change) {
		this.change = change;
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

	public String getOpstate() {
		return opstate;
	}

	public void setOpstate(String opstate) {
		this.opstate = opstate;
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

	public boolean isIfmodify() {
		return ifmodify;
	}

	public void setIfmodify(boolean ifmodify) {
		this.ifmodify = ifmodify;
	}

	public boolean isIfmsg() {
		return ifmsg;
	}

	public void setIfmsg(boolean ifmsg) {
		this.ifmsg = ifmsg;
	}

}
