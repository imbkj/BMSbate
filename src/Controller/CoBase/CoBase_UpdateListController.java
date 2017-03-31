package Controller.CoBase;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import service.DataPopedomService;
import service.UserInfoService;

import Model.CoBaseModel;
import bll.CoBase.CoBase_SelectBll;

public class CoBase_UpdateListController {
	private CoBase_SelectBll cobaBll = new CoBase_SelectBll();
	private List cobaBaseList; // 公司信息列表
	private List cobaClientList; // 客服列表

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	String userid = user.getUserid();
	private DataPopedomService dps = new Data_PopedomIpml(userid, username);
	// 查询条件
	private String cid;
	private String coba_company;
	private String coba_client;
	private String sql = "";
	private String strwhere;

	public CoBase_UpdateListController() {
		// 初始化显示公司数据
		cobaBaseList = cobaBll.getCobaseinfo(" AND a.coba_client='" + username + "'");
		// 初始化客服下拉框数据
		cobaClientList = dps.getpidLoginlist();
	}

	// 查询公司信息
	@Command("search")
	@NotifyChange("cobaBaseList")
	public void search() {
//		sql="";
//		if (cid != null && !"".equals(cid)) {
//			sql = sql + " AND a.cid=" + cid;
//		}
//		if (!"".equals(coba_company) && coba_company != null) {
//			sql = sql + " AND a.coba_company LIKE '%" + coba_company + "%'";
//		}
//		if (!"".equals(coba_client) && coba_client != null) {
//			sql = sql + " AND a.coba_client='" + coba_client + "'";
//		}
//		cobaBaseList = cobaBll.getCobaseinfo(sql);
		
		strwhere =strwhere.split("\\|")[1];
		
		
		try {
			if (strwhere.indexOf("|") != -1) {
				strwhere =strwhere.split("\\|")[1];
				cobaBaseList  = cobaBll.getshCobaseinfo(strwhere);
		
			} else {
				
				cobaBaseList  = cobaBll.getshCobaseinfo(strwhere);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
		
		
	}

	// 弹出公司各个操作页面
	@Command("openZUL")
	@NotifyChange("cobaBaseList")
	public void openZUL(@BindingParam("url") String url,
			@BindingParam("cobaM") CoBaseModel cobaM) {
		// 专递cobaM
		Map map = new HashMap();
		map.put("cobaM", cobaM);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		cobaBaseList = cobaBll.getCobaseinfo(sql);
	}
	
	// 弹出公司各个操作页面
		@Command("AddCompany")
		public void AddCompany(@BindingParam("cobaM") CoBaseModel cobaM) {
			// 专递cobaM
			Map map = new HashMap();
			map.put("cid", cobaM.getCid());
			Window window = (Window) Executions.createComponents("CoCompact_CompanyAdd.zul", null, map);
			window.doModal();
		}

	@Command("addem")
	public void addem(@BindingParam("a") int a,
			@BindingParam("cobaM") CoBaseModel cobaM) {
		Map<String, Object> map = new HashMap<String, Object>();
		String url = "";
		// 预增
		if (a == 1) {
			url = "/Embase/Embase_Addfst.zul";
			map.put("cid", cobaM.getCid());
			map.put("company", cobaM.getCoba_company());
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
		}
		// 新增
		else if (a == 2) {
			url = "/Embase/Embase_Addsec.zul";
			map.put("cid", cobaM.getCid());
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
		}else if (a == 3) {
			url = "/Embase/Embase_Add.zul";
			map.put("cobaM", cobaM);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
		}
	}
	
	@Command("openLinkMan_CtrlList")
	public void openLinkMan_CtrlList(@BindingParam("cid") int cid){
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		Window window = (Window) Executions.createComponents(
				"CoBaseLinkMan_List.zul", null, map);
		window.doModal();
	}
	

	public String getStrwhere() {
		return strwhere;
	}

	public void setStrwhere(String strwhere) {
		this.strwhere = strwhere;
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
