package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import bll.EmSheBao.Emsi_SelBll;

import Model.CoBaseModel;
import Model.EmbaseModel;
import Util.CheckString;
import Util.RegexUtil;

public class Emsi_BaseListController {
	private List<CoBaseModel> companyList;
	private List<CoBaseModel> winCompanyList;
	private List<EmbaseModel> emList;
	private List<EmbaseModel> winemList;
	private Emsi_SelBll bll = new Emsi_SelBll();

	public Emsi_BaseListController() {
		companyList = bll.getCoBase();
		winCompanyList = companyList;
	}

	// 公司检索
	@Command("companyChange")
	@NotifyChange({ "winCompanyList", "winemList" })
	public void companyChange(@BindingParam("com") String company) {
		if (!"".equals(company) && company != null) {
			winCompanyList = searchCompany(company);
		} else {
			winCompanyList = companyList;
		}
		winemList = null;
		emList = null;
	}

	// 检索公司信息
	private List<CoBaseModel> searchCompany(String str) {
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		if (CheckString.isNum(str)) {
			for (CoBaseModel m : companyList) {
				if (m.getCid() == Integer.parseInt(str)) {
					list.add(m);
					break;
				}
			}
		} else if (CheckString.isChinese(str)) {
			for (CoBaseModel m : companyList) {
				if (RegexUtil.isExists(str, m.getCoba_company())) {
					list.add(m);
				}
			}
		} else if (CheckString.isLetter(str)) {
			for (CoBaseModel m : companyList) {
				if (RegexUtil.isExists(str, m.getCoba_company())
						|| RegexUtil.isExists(str, m.getCoba_namespell())) {
					list.add(m);
				}
			}
		}
		return list;
	}

	// 根据CID检索员工信息
	@Command("selEmbase")
	@NotifyChange("winemList")
	public void selEmbase(@BindingParam("selectitem") Listitem selectitem) {
		try {
			emList = bll.getEmbaseByCid(((CoBaseModel) selectitem.getValue())
					.getCid());
			winemList = emList;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 员工检索
	@Command("selEm")
	@NotifyChange("winemList")
	public void selEm(@BindingParam("em") String em) {
		try {
			if (!"".equals(em) && em != null) {
				winemList = searchEmbase(em);
			} else {
				winemList = emList;
			}
		} catch (Exception e) {

		}
	}

	// 检索员工信息
	private List<EmbaseModel> searchEmbase(String str) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		if (CheckString.isNum(str)) {
			for (EmbaseModel m : emList) {
				if (m.getGid() == Integer.parseInt(str)) {
					list.add(m);
					break;
				}
			}
		} else if (CheckString.isChinese(str)) {
			for (EmbaseModel m : emList) {
				if (RegexUtil.isExists(str, m.getEmba_name())) {
					list.add(m);
				}
			}
		} else if (CheckString.isLetter(str)) {
			for (EmbaseModel m : emList) {
				if (RegexUtil.isExists(str, m.getEmba_name())
						|| RegexUtil.isExists(str, m.getEmba_spell())) {
					list.add(m);
				}
			}
		}
		return list;
	}

	// 社保新增(无电脑号)
	@Command("newin")
	public void newinadd(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents("Emsi_Newin.zul",
				null, map);
		window.doModal();
	}

	// 调入(有电脑号)
	@Command("movein")
	public void movein(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents("Emsi_Movein.zul",
				null, map);
		window.doModal();
	}

	// 调回
	@Command("moveback")
	public void moveback(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_MoveBack.zul", null, map);
		window.doModal();
	}

	// 外籍人社保新增
	@Command("foreignerNewin")
	public void foreignerNewin(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_Foreigner_Newin.zul", null, map);
		window.doModal();
	}

	// 外籍人社保调入
	@Command("foreignerMovein")
	public void foreignerMovein(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_Foreigner_Movein.zul", null, map);
		window.doModal();
	}

	// 外籍人社保调回
	@Command("foreignerMoveback")
	public void foreignerMoveback(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_Foreigner_MoveBack.zul", null, map);
		window.doModal();
	}

	// 修改工资
	@Command("upSalary")
	public void upSalary(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_Salary_Update.zul", null, map);
		window.doModal();
	}

	// 档案修改
	@Command("upfile")
	public void upfile(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents("Emsi_UpFile.zul",
				null, map);
		window.doModal();
	}

	// 社保停交
	@Command("stop")
	public void stop(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents("Emsi_Stop.zul",
				null, map);
		window.doModal();
	}

	// 特殊(交单)变更
	@Command("changeSZSI")
	public void changeSZSI(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_Change_SZSI.zul", null, map);
		window.doModal();
	}

	// 新增补缴
	@Command("bj")
	public void bj(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents("Emsi_Bj.zul",
				null, map);
		window.doModal();
	}

	// 编辑变更
	@Command("deleteChange")
	public void deleteChange(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_DeleteChange_List.zul", null, map);
		window.doModal();
	}

	// 编辑补缴
	@Command("deleteBj")
	public void deleteBj(@BindingParam("gid") String gid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"Emsi_DeleteBj_List.zul", null, map);
		window.doModal();
	}

	public List<CoBaseModel> getWinCompanyList() {
		return winCompanyList;
	}

	public List<EmbaseModel> getEmList() {
		return emList;
	}

	public List<EmbaseModel> getWinemList() {
		return winemList;
	}

}
