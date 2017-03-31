package Controller.EmSalary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;

import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Util.CheckString;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalary_CompanyListController {

	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
	private List<CoBaseModel> coList;
	private List<CoBaseModel> coviewList;
	private List<String> gzUserList;
	private String user;
	private String coms;

	public EmSalary_CompanyListController() {
		gzUserList = bll.getGzUser();
		coList = bll.getCoBase(" top 500 ", "");
		coviewList = coList;
	}

	// 检索
	@Command("search")
	@NotifyChange("coviewList")
	public void changeList() {
		if (!"".equals(user) || !"".equals(coms)) {
			coviewList = getNewList(user,coms);
		} else {
			coviewList = coList;
		}
	}

	// 检索数据
	private List<CoBaseModel> getNewList(String gzUser, String com) {
		String str = "";
		if (gzUser != null && !"".equals(gzUser)) {
			str = str + " And coba_gzaddname='" + gzUser + "'";
		}

		if (com != null && !"".equals(com)) {
			if (CheckString.isNum(com)) {
				str = str + " And cid=" + com;
			} else if (CheckString.isChinese(com)) {
				str = str + " And coba_shortname like'%" + com + "%'";
			} else if (CheckString.isLetter(com)) {
				str = str + " And coba_namespell like'%" + com + "%'";
			}
		}
		coList = bll.getCoBase(" top 500 ", str);
		return coList;
	}

	// 弹出薪酬负责人设置页面
	@Command("set")
	public void set(@BindingParam("model") CoBaseModel m,
			@ContextParam(ContextType.VIEW) Component view) {
		String gzUser = "";
		String gzAudUser = "";
		if (m.getCoba_gzaddname() != null) {
			gzUser = m.getCoba_gzaddname();
		}
		if (m.getCoba_gzaudname() != null) {
			gzAudUser = m.getCoba_gzaudname();
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(m.getCid()));
		map.put("shortname", m.getCoba_shortname());
		map.put("gzUser", gzUser);
		map.put("gzAudUser", gzAudUser);
		Window window = (Window) Executions.createComponents(
				"EmSalary_CompanySet.zul", view, map);
		window.doModal();
	}

	// 子页面刷新本页面List
	@Command("setList")
	@NotifyChange({ "coviewList", "gzUserList" })
	public void setList() {

		if (!"".equals(user) || !"".equals(coms)) {
			coList = bll.getCoBase();
			coviewList = getNewList(user, coms);
			// user = "";
			// coms = "";
		} else {
			coList = bll.getCoBase();
			coviewList = coList;
		}
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComs() {
		return coms;
	}

	public void setComs(String coms) {
		this.coms = coms;
	}

	public List<CoBaseModel> getCoList() {
		return coList;
	}

	public List<CoBaseModel> getCoviewList() {
		return coviewList;
	}

	public List<String> getGzUserList() {
		return gzUserList;
	}

}
