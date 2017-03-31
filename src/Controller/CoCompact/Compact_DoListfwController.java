package Controller.CoCompact;

import impl.CheckStringImpl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Window;

import service.CheckStringService;

import Model.CoCompactModel;
import bll.CoCompact.BaseInfo_SelectListBll;

public class Compact_DoListfwController {
	private List cocoBaseList; // 合同基本信息列表
	private List cocoAddnameList; // 添加人列表
	private BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();

	// 查询条件
	private String coco_company;
	private String coco_compactid;
	private String coco_addname;
	private String coco_state;

	String sql;// 初始化sql语句
	Session co_session = Executions.getCurrent().getDesktop().getSession();

	public Compact_DoListfwController() {
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
/*
			coco_state = Executions.getCurrent().getParameter("coco_state")
					.toString();
			coco_state = new String(coco_state.getBytes("ISO-8859-1"), "UTF-8");

			coco_company = Executions.getCurrent().getParameter("coco_company")
					.toString();
			coco_company = new String(coco_company.getBytes("ISO-8859-1"),
					"UTF-8");

			coco_compactid = Executions.getCurrent()
					.getParameter("coco_compactid").toString();
			coco_compactid = new String(coco_compactid.getBytes("ISO-8859-1"),
					"UTF-8");

			coco_addname = Executions.getCurrent().getParameter("coco_addname")
					.toString();
			coco_addname = new String(coco_addname.getBytes("ISO-8859-1"),
					"UTF-8");
*/
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 首页加载显示待制作合同、已制作合同和部门经理审核的数据
		search();

		// 查询中添加人下拉框
		cocoAddnameList = cocoBll.getCoCoAddname(" And coco_state in(2,4) and coco_autst in(1,2,3,4)");
	}

	// 查询公司合同基本信息
	@Command("search")
	@NotifyChange("cocoBaseList")
	public void search() {
		CheckStringService ch = new CheckStringImpl();

		sql = " And coco_state in(2,3,4) and coco_autst in(1,2,3,4)";// 页面初始化sql语句

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
			
			switch (coco_state) {
			case "待审核":
				sql = sql + " and a.coco_state=2 and a.coco_autst=1";
				break;
			case "待打印":
				sql = sql + " and a.coco_state=2 and a.coco_autst=2";
				break;
			case "待盖章":
				sql = sql + " and a.coco_state=2 and a.coco_autst=3";
				break;
			case "待签回":
				sql = sql + " and a.coco_state=2 and a.coco_autst=4";
				break;
			case "待归档":
				sql = sql + " and a.coco_state=4";
				break;
			default:
				break;
			}
		}else {
			co_session.setAttribute("coco_state", "");
		}

		cocoBaseList = cocoBll.searchCoCoBaseList(sql);
	}

	// 弹出相应的处理页面
	@Command("open")
	@NotifyChange("cocoBaseList")
	public void open(@BindingParam("cocoM") CoCompactModel cocoM)
			throws UnsupportedEncodingException {

		// 制作合同
		String url = "";
		if (cocoM.getCoco_state() == 2 && cocoM.getCoco_autst() == 1) {
			url = "Compact_AutDoc.zul";
		} else if (cocoM.getCoco_state() == 2 && cocoM.getCoco_autst() == 2) {
			url = "Compact_PrintDoc.zul";
		} else if (cocoM.getCoco_state() == 2 && cocoM.getCoco_autst() == 3) {
			url = "Compact_GZDoc.zul";
		} else if (cocoM.getCoco_state() == 2 && cocoM.getCoco_autst() == 4) {
			url = "Compact_Sign.zul";
		} else if (cocoM.getCoco_state() == 4) {
			url = "Compact_InFile.zul";
		}

		// 专递cocoM
		Map map = new HashMap();
		map.put("id", cocoM.getCoco_tapr_id());
		map.put("daid", cocoM.getCoco_id());
		// 传递查询条件
		if (cocoM.getCoco_state() == 2 && cocoM.getCoco_autst() != 4) {
			map.put("coco_state", coco_state);
			map.put("coco_company", coco_company);
			map.put("coco_compactid", coco_compactid);
			map.put("coco_addname", coco_addname);
			map.put("way", 2);
		}

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		// 刷新
		search();
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
}
