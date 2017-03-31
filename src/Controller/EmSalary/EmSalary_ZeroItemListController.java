package Controller.EmSalary;

import impl.SystemControl.Data_PopedomIpml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import service.DataPopedomService;

import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;

public class EmSalary_ZeroItemListController {
	private CoBase_SelectBll cobaBll = new CoBase_SelectBll();
	private List cobaBaseList; // 公司信息列表
	private List cobaClientList; // 客服列表

	// 查询条件
	private String cid;
	private String coba_company;
	private String coba_client;
	private String sql = "";

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();
	private DataPopedomService dps = new Data_PopedomIpml(userid, username);

	public EmSalary_ZeroItemListController() {
		// 初始化显示公司数据
		cobaBaseList = cobaBll.getCobaseinfo(" AND a.coba_client='" + username
				+ "'");
		// 初始化客服下拉框数据
		cobaClientList = dps.getpidLoginlist();
	}

	// 查询公司信息
	@Command("search")
	@NotifyChange("cobaBaseList")
	public void search() {
		sql = "";
		if (cid != null && !"".equals(cid)) {
			sql = sql + " AND a.cid=" + cid;
		}
		if (!"".equals(coba_company) && coba_company != null) {
			sql = sql + " AND a.coba_company LIKE '%" + coba_company + "%'";
		}
		if (!"".equals(coba_client) && coba_client != null) {
			sql = sql + " AND a.coba_client='" + coba_client + "'";
		}
		cobaBaseList = cobaBll.getCobaseinfo(sql);
	}

	// 弹出公司各个操作页面
	@Command("openZUL")
	public void openZUL(@BindingParam("cid") int cid) {
		Map map = new HashMap();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(
				"../EmSalary/EmSalary_ZeroItemUpdate.zul", null, map);
		
		try {
			window.doModal();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public List getCobaBaseList() {
		return cobaBaseList;
	}

	public void setCobaBaseList(List cobaBaseList) {
		this.cobaBaseList = cobaBaseList;
	}

	public List getCobaClientList() {
		return cobaClientList;
	}

	public void setCobaClientList(List cobaClientList) {
		this.cobaClientList = cobaClientList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCoba_company() {
		return coba_company;
	}

	public void setCoba_company(String coba_company) {
		this.coba_company = coba_company;
	}

	public String getCoba_client() {
		return coba_client;
	}

	public void setCoba_client(String coba_client) {
		this.coba_client = coba_client;
	}
}
