package Controller.CoCompact;

import impl.CheckStringImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.CheckStringService;

import Model.CoCompactModel;
import Util.UserInfo;
import bll.CoCompact.BaseInfo_SelectListBll;

public class Compact_DoListkfController {
	private List cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();

	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;
	private String coco_state;

	String sql;// 初始化sql语句

	boolean chk_dm = false;
	Session co_session = Executions.getCurrent().getDesktop().getSession();

	public Compact_DoListkfController() {
		
		// 判断是否为部门经理
		if ("客户服务部部门经理".equals(UserInfo.getRolName())
				|| "全国项目部部门经理".equals(UserInfo.getRolName())
				|| "财务部部门经理".equals(UserInfo.getRolName())
				|| "人才招聘部经理".equals(UserInfo.getRolName())
				|| "系统管理员".equals(UserInfo.getRolName())
				|| "雇员人事部部门经理".equals(UserInfo.getRolName())
				|| "业务拓展部部门经理".equals(UserInfo.getRolName())
				|| "总经理".equals(UserInfo.getRolName())) {
			chk_dm = true;
		}

		// 获取查询条件
		try {
			if (co_session.getAttribute("coco_state")!=null) {
				coco_state=String.valueOf(co_session.getAttribute("coco_state"));
				//删除session 
				co_session.removeAttribute("coco_state");
			}
			if (co_session.getAttribute("coco_company")!=null) {
				coco_company=String.valueOf(co_session.getAttribute("coco_company"));
				//删除session 
				co_session.removeAttribute("coco_company");
			}
			if (co_session.getAttribute("coco_compactid")!=null) {
				coco_compactid=String.valueOf(co_session.getAttribute("coco_compactid"));
				//删除session 
				co_session.removeAttribute("coco_compactid");
			}
			if (co_session.getAttribute("coco_addname")!=null) {
				coco_addname=String.valueOf(co_session.getAttribute("coco_addname"));
				//删除session 
				co_session.removeAttribute("coco_addname");
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		// 首页加载显示待制作合同、已制作合同和部门经理审核的数据
		search();

		// 查询中添加人下拉框
		cocoAddnameList = cocoBll
				.getCoCoAddname(" And coco_state in(0,1,7) and (coco_autst=0 or coco_autst is null)");
	}

	// 查询公司合同基本信息
	@Command("search")
	@NotifyChange("cocoBaseList")
	public void search() {
		CheckStringService ch = new CheckStringImpl();

		sql = " And coco_state in(0,1,7) and (coco_autst=0 or coco_autst is null)";// 页面初始化sql语句

		if (coco_company != null && !"".equals(coco_company)) {
			if (ch.isChinese(coco_company.trim())) {
				sql = sql + " and company like '%" + coco_company.trim()
						+ "%' ";
			} else if (ch.isNum(coco_company.trim())) {
				sql = sql + " and (a.cid='" + coco_company.trim()
						+ "' or company like '%" + coco_company.trim() + "%')";
			}
			co_session.setAttribute("coco_company", coco_company.trim());
		}else {
			co_session.setAttribute("coco_company", "");
		}

		if (coco_compactid != null && !"".equals(coco_compactid)) {
			sql = sql + " and coco_compactid LIKE'%" + coco_compactid.trim()
					+ "%' ";
			co_session.setAttribute("coco_compactid", coco_compactid.trim());
		}else {
			co_session.setAttribute("coco_compactid", "");
		}

		if (coco_addname != null && !"".equals(coco_addname)) {
			sql = sql + " and a.coco_addname='" + coco_addname + "'";
			co_session.setAttribute("coco_addname", coco_addname);
		}else {
			co_session.setAttribute("coco_addname", "");
		}

		if (coco_state != null && !"".equals(coco_state)) {
			co_session.setAttribute("coco_state", coco_state);
			String state = "";
			switch (coco_state) {
			case "待制作合同":
				state = "0";
				break;
			case "待审核":
				state = "1";
			case "退回":
				state = "7";
			default:
				break;
			}

			sql = sql + " and a.coco_state=" + state;
		}else {
			co_session.setAttribute("coco_state", "");
		}

		cocoBaseList = cocoBll.searchCoCoBaseList(sql);
	}

	// 弹出相应的处理页面
	@Command("open")
	@NotifyChange("cocoBaseList")
	public void open(@BindingParam("cocoM") CoCompactModel cocoM) {

		// 制作合同
		String url = "";
		if (cocoM.getCoco_state() == 0 || cocoM.getCoco_state()==7) {
			url = "Compact_Doc.zul";
		} else if (cocoM.getCoco_state() == 1) {
			if (chk_dm) {
				url = "Compact_AutRDoc.zul";
			} else {
				// 弹出提示
				Messagebox.show("当前步骤为部门经理审核步骤！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

		if (!"".equals(url)) {
			// 专递cocoM
			Map map = new HashMap();
			map.put("id", cocoM.getCoco_tapr_id());
			map.put("daid", cocoM.getCoco_id());

			// 传递查询条件
			map.put("coco_state", coco_state);
			map.put("coco_company", coco_company);
			map.put("coco_compactid", coco_compactid);
			map.put("coco_addname", coco_addname);
			map.put("way", 1);//标记是从客服页面跳转

			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			// 刷新
			search();
		}
	}

	public List getCocoBaseList() {
		return cocoBaseList;
	}

	public void setCocoBaseList(List cocoBaseList) {
		this.cocoBaseList = cocoBaseList;
	}

	public List getCocoAddnameList() {
		return cocoAddnameList;
	}

	public void setCocoAddnameList(List cocoAddnameList) {
		this.cocoAddnameList = cocoAddnameList;
	}

	public String getCoco_company() {
		return coco_company;
	}

	public void setCoco_company(String coco_company) {
		this.coco_company = coco_company;
	}

	public String getCoco_compactid() {
		return coco_compactid;
	}

	public void setCoco_compactid(String coco_compactid) {
		this.coco_compactid = coco_compactid;
	}

	public String getCoco_addname() {
		return coco_addname;
	}

	public String getCoco_state() {
		return coco_state;
	}

	public void setCoco_state(String coco_state) {
		this.coco_state = coco_state;
	}

	public void setCoco_addname(String coco_addname) {
		this.coco_addname = coco_addname;
	}

	public boolean isChk_dm() {
		return chk_dm;
	}

	public void setChk_dm(boolean chk_dm) {
		this.chk_dm = chk_dm;
	}

}
