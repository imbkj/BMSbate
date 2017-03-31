package Controller.EmSalary;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.EmbaseModel;
import Util.DateStringChange;
import Util.RegexUtil;
import Util.UserInfo;
import bll.EmSalary.EmSalary_SalarySelBll;
import Util.CheckString;

public class EmSalaryBase_ListController {

	private List<String> ownmonthList;
	private List<CoBaseModel> companyList;
	private List<CoBaseModel> winCompanyList;
	private List<CoBaseModel> cbCompanyList;
	private List<EmbaseModel> emList;
	private List<EmbaseModel> winemList;
	private int cid;
	private String gzaddname;
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
	private boolean winCanUp;
	private String ownmonth;
	private String company;

	public EmSalaryBase_ListController() {
		ownmonth = DateStringChange.DatetoSting(new Date(), "yyyyMM");
		winCanUp = true;
		ownmonthList = bll.getOwnmonth();
		companyList = bll.getCoBaseAll();
		winCompanyList = bll.getCoBaseAll(Integer.parseInt(ownmonth));
		cbCompanyList = winCompanyList;
	}

	// 所属月份检索
	@Command("ownmonthChange")
	@NotifyChange({ "winCompanyList", "cbCompanyList", "winemList", "winCanUp" })
	public void ownmonthChange() {
		if (!"".equals(ownmonth) && ownmonth != null) {
			winCanUp = true;
			cbCompanyList = bll.getCoBaseAll(Integer.parseInt(ownmonth));
			if (!"".equals(company) && company != null) {
				winCompanyList = searchCompany(company, cbCompanyList);
			} else {
				winCompanyList = cbCompanyList;
			}
			winemList = null;
			emList = null;
		} else {
			winCanUp = false;
			cbCompanyList = companyList;
			winCompanyList = companyList;
		}
	}

	// 公司检索
	@Command("companyChange")
	public void companyChange() {
		if (!"".equals(company) && company != null) {
			winCompanyList = searchCompany(company, cbCompanyList);
			if (winCompanyList.size() == 0) {
				winCompanyList = searchCompany(company, companyList);
			}
		} else {
			winCompanyList = cbCompanyList;
		}
		winemList = null;
		emList = null;
		BindUtils.postNotifyChange(null, null, this, "winCompanyList");
		BindUtils.postNotifyChange(null, null, this, "winemList");
	}

	// 检索公司信息
	private List<CoBaseModel> searchCompany(String str,
			List<CoBaseModel> searchList) {
		List<CoBaseModel> list = new LinkedList<CoBaseModel>();
		if (CheckString.isNum(str)) {
			for (CoBaseModel m : searchList) {
				if (m.getCid() == Integer.parseInt(str)) {
					list.add(m);
					break;
				}
			}
		} else if (CheckString.isChinese(str)) {
			for (CoBaseModel m : searchList) {
				if (RegexUtil.isExists(str, m.getCoba_shortname())) {
					list.add(m);
				}
			}
		} else if (CheckString.isLetter(str)) {
			for (CoBaseModel m : searchList) {
				if (RegexUtil.isExists(str, m.getCoba_shortname())
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
			this.cid = ((CoBaseModel) selectitem.getValue()).getCid();
			this.gzaddname = ((CoBaseModel) selectitem.getValue())
					.getCoba_gzaddname();
			emList = bll.getEmbaseByCid(cid);
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

	// 新增工资
	@Command("addSalary")
	@NotifyChange("winCompanyList")
	public void addSalary(@BindingParam("gid") String gid) throws InterruptedException{
		// 判断用户是否为薪酬负责人
		if (checkGzaddname(cid)) {
			// 判断员工是否已分配算法
			if (bll.existCfin(cid, Integer.parseInt(gid))) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("gid", gid);
				map.put("cid", String.valueOf(cid));
				Window window = (Window) Executions.createComponents(
						"EmSalary_Add_Base.zul", null, map);
				window.doModal();
				refreshWin();

			} else {
				Messagebox.show("该员工未分配算法，请分配算法后再操作此步骤！", "操作提示",
						Messagebox.OK, Messagebox.INFORMATION);
			}
		}
	}

	// 生成工资
	@Command("createSalary")
	public void createSalary(@BindingParam("cid") String cid,
			@BindingParam("gzaddname") String gzaddname,
			@ContextParam(ContextType.VIEW) Component view) throws InterruptedException{
		// 判断用户是否为薪酬负责人
		if (checkGzaddname(Integer.parseInt(cid))) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cid", cid);
			Window window = (Window) Executions.createComponents(
					"EmSalary_CreateData.zul", view, map);
			window.doModal();
		}
	}

	// 批量新增工资
	@Command("createSalaryFirst")
	public void createSalaryFirst(@BindingParam("cid") String cid,
			@BindingParam("gzaddname") String gzaddname,
			@ContextParam(ContextType.VIEW) Component view) throws InterruptedException{
		// 判断用户是否为薪酬负责人
		if (checkGzaddname(Integer.parseInt(cid))) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cid", cid);
			Window window = (Window) Executions.createComponents(
					"EmSalary_CreateFirstData.zul", view, map);
			window.doModal();
		}
	}

	// 修改工资
	@Command("upSalary")
	@NotifyChange("winCompanyList")
	public void upSalary(@BindingParam("cid") String cid,
			@BindingParam("ownmonth") String ownmonth,
			@BindingParam("gzaddname") String gzaddname) throws InterruptedException{
		// 判断用户是否为薪酬负责人
		if (checkGzaddname(Integer.parseInt(cid))) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cid", cid);
			map.put("ownmonth", ownmonth);
			Window window = (Window) Executions.createComponents(
					"EmSalaryBase_Update.zul", null, map);
			window.doModal();
			refreshWin();
		}
	}

	// 确认工资
	@Command("confirmSalary")
	public void confirmSalary(@BindingParam("cid") String cid,
			@BindingParam("ownmonth") String ownmonth,
			@BindingParam("gzaddname") String gzaddname,
			@ContextParam(ContextType.VIEW) Component view) throws InterruptedException{
		// 判断用户是否为薪酬负责人
		//if (checkGzaddname(gzaddname)) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("cid", cid);
			map.put("ownmonth", ownmonth);
			Window window = (Window) Executions.createComponents(
					"EmSalary_ConfirmList.zul", view, map);
			window.doModal();
		//}
	}

	// 弹出非清零字段数据更新申请页面
	@Command("openZeroItemUp")
	public void openZeroItemUp(@BindingParam("cid") String cid,
			@BindingParam("gzaddname") String gzaddname) throws InterruptedException{
		// 判断用户是否为薪酬负责人
		if (checkGzaddname(Integer.parseInt(cid))) {
			Map map = new HashMap();
			map.put("cid", Integer.parseInt(cid));
			Window window = (Window) Executions.createComponents(
					"EmSalary_ZeroItemUpdate.zul", null, map);
			window.doModal();
		}
	}

	// 刷新winCompanyList
	@Command("refreshWin")
	public void refreshWin() {
		if (winCanUp) {
			cbCompanyList = winCompanyList = bll.getCoBaseAll(Integer
					.parseInt(ownmonth));
			
			if (!"".equals(company) && company != null) {
				winCompanyList = searchCompany(company, cbCompanyList);
				if (winCompanyList.size() == 0) {
					winCompanyList = searchCompany(company, companyList);
				}
			}
			
			BindUtils.postNotifyChange(null, null, this, "winCompanyList");
			BindUtils.postNotifyChange(null, null, this, "cbCompanyList");
		}
	}

	// 根据所属月份,cid刷新winCompanyList
	@Command("refreshWinByOwnmonth")
	@NotifyChange("winCompanyList")
	public void refreshWinByOwnmonth(
			@BindingParam("ownmonth") Object ownmonthSel,
			@BindingParam("cid") Object cid) {
		try {
			if (!winCanUp || !ownmonthSel.equals(this.ownmonth)) {
				this.ownmonth = ownmonthSel.toString();
				BindUtils.postNotifyChange(null, null, this, "ownmonth");
			}
			refreshWin();
			this.company = cid.toString();
			BindUtils.postNotifyChange(null, null, this, "company");
			companyChange();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断用户是否为薪酬负责人
	private boolean checkGzaddname(Integer cid) {
		//获取最新薪酬负责人
		EmSalary_SalarySelBll selBll=new EmSalary_SalarySelBll();
		gzaddname=selBll.getCobaseByCid(cid).getCoba_gzaddname();
		
		String username = UserInfo.getUsername();
		if ("林少斌".equals(username))
			return true;
		if (username.equals(gzaddname)) {
			return true;
		} else {
			Messagebox.show("您不是该公司薪酬负责人，没有权限操作该步骤!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		return false;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public List<CoBaseModel> getWinCompanyList() {
		return winCompanyList;
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<CoBaseModel> getCbCompanyList() {
		return cbCompanyList;
	}

	public List<EmbaseModel> getWinemList() {
		return winemList;
	}

	public boolean isWinCanUp() {
		return winCanUp;
	}

}
