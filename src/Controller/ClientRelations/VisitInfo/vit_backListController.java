package Controller.ClientRelations.VisitInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Window;

import dal.ClientRelations.VisitInfo.vit_backListDal;

import bll.ClientRelations.VisitInfo.vit_backListBll;

import Model.VisitInfoModel;

public class vit_backListController extends SelectorComposer<Component> {

	private List<VisitInfoModel> visitList = new ListModelList<VisitInfoModel>();
	private List<String> personList = new ListModelList<String>();
	private List<String> subordinateList = new ListModelList<String>();
	private List<String> addnameList = new ListModelList<String>();
	private String viin_month;
	private String viin_person;
	private String viin_subordinate;
	private String viin_addname;
	private Date viin_addtime;
	private String viin_state;
	private String viin_iffollow;
	private String cola_company;

	// 初始化
	@Init
	public void init() throws Exception {
		this.setVisitList(vit_backListBll.getVisitList(""));
		this.setPersonList(vit_backListDal.getPersonList());
		this.setSubordinateList(vit_backListBll.getSubordinateList());
		this.setAddnameList(vit_backListBll.getAddnameList());
		viin_month = "";
		viin_person = "";
		viin_subordinate = "";
		viin_addname = "";
		viin_addtime = null;
		viin_state = "";
		viin_iffollow = "";
		cola_company = "";
	}

	@Command("menubarinit")
	public void menubarinit(@BindingParam("mb") Menubar mb,
			@BindingParam("state") int state) {
		Menu mn = (Menu)mb.getChildren().get(0);
		Menupopup mpp = (Menupopup)mn.getChildren().get(0);
		Menuitem mt1 = (Menuitem)mpp.getChildren().get(0);
		Menuitem mt2 = (Menuitem)mpp.getChildren().get(1);
		if (state == 1) {
			mb.setVisible(true);
			mt1.setVisible(true);
			mt2.setVisible(false);
		} else if (state > 1 && state < 5) {
			mb.setVisible(true);
			mt1.setVisible(false);
			mt2.setVisible(true);
		} else {
			mb.setVisible(false);
		}
	}

	// 查询
	@Command("search")
	@NotifyChange("visitList")
	public void search() throws Exception {
		String str = "";
		if (!cola_company.isEmpty()) {
			str += " and cola_company like '%" + cola_company + "%'";
		}
		if (!viin_month.isEmpty()) {
			str += " and viin_month = '" + viin_month + "'";
		}
		if (!viin_person.isEmpty()) {
			str += " and viin_person = '" + viin_person + "'";
		}
		if (!viin_subordinate.isEmpty()) {
			str += " and viin_subordinate = '" + viin_subordinate + "'";
		}
		if (!viin_addname.isEmpty()) {
			str += " and viin_addname = '" + viin_addname + "'";
		}
		if (viin_addtime != null) {
			String viin_addtimeStr = new SimpleDateFormat("yyyy-MM-dd")
					.format(viin_addtime);
			str += " and convert(varchar(10),viin_addtime,120)='"
					+ viin_addtimeStr + "'";
		}
		if (!viin_state.isEmpty()) {
			Integer state = 0;
			if (viin_state.equals("已审核")) {
				state = 1;
			} else if (viin_state.equals("拜访完成")) {
				state = 2;
			} else if (viin_state.equals("跟进中")) {
				state = 3;
			} else if (viin_state.equals("跟进完成")) {
				state = 4;
			} else if (viin_state.equals("完结")) {
				state = 5;
			}
			str += " and viin_state = " + state;
		}
		if (!viin_iffollow.isEmpty()) {
			Boolean iffollow = null;
			if (viin_iffollow.equals("是")) {
				iffollow = true;
			} else if (viin_iffollow.equals("否")) {
				iffollow = false;
			}
			str += " and viin_iffollow = '" + iffollow + "'";
		}
		this.setVisitList(vit_backListBll.getVisitList(str));
	}

	// 弹出录入拜访详情窗口
	@Command("visitback")
	@NotifyChange("visitList")
	public void visitback(@BindingParam("vim") VisitInfoModel vim) throws Exception {
		Map map = new HashMap();
		//map.put("vim", vim);
		
		map.put("id",vim.getViin_tapr_id());
		map.put("daid",vim.getViin_id());
		
		Window window = (Window) Executions.createComponents(
				"/ClientRelations/VisitInfo/vit_back.zul", null, map);
		window.doModal();
		search();
	}
	
	//弹出修改拜访详情窗口
	@Command("visitbackmod")
	@NotifyChange("visitList")
	public void visitbackmod(@BindingParam("vim") VisitInfoModel vim) throws Exception{
		Map map = new HashMap();
		//map.put("vim", vim);
		
		map.put("id",vim.getViin_tapr_id());
		map.put("daid",vim.getViin_id());
		
		Window window = (Window) Executions.createComponents(
				"/ClientRelations/VisitInfo/vit_backmod.zul", null, map);
		window.doModal();
		search();
	}

	public List<VisitInfoModel> getVisitList() {
		return visitList;
	}

	public void setVisitList(List<VisitInfoModel> visitList) {
		this.visitList = visitList;
	}

	public String getViin_month() {
		return viin_month;
	}

	public void setViin_month(String viin_month) {
		this.viin_month = viin_month;
	}

	public String getViin_person() {
		return viin_person;
	}

	public void setViin_person(String viin_person) {
		this.viin_person = viin_person;
	}

	public String getViin_subordinate() {
		return viin_subordinate;
	}

	public void setViin_subordinate(String viin_subordinate) {
		this.viin_subordinate = viin_subordinate;
	}

	public String getViin_addname() {
		return viin_addname;
	}

	public void setViin_addname(String viin_addname) {
		this.viin_addname = viin_addname;
	}

	public Date getViin_addtime() {
		return viin_addtime;
	}

	public void setViin_addtime(Date viin_addtime) {
		this.viin_addtime = viin_addtime;
	}

	public String getViin_state() {
		return viin_state;
	}

	public void setViin_state(String viin_state) {
		this.viin_state = viin_state;
	}

	public String getViin_iffollow() {
		return viin_iffollow;
	}

	public void setViin_iffollow(String viin_iffollow) {
		this.viin_iffollow = viin_iffollow;
	}

	public List<String> getPersonList() {
		return personList;
	}

	public void setPersonList(List<String> personList) {
		this.personList = personList;
	}

	public List<String> getSubordinateList() {
		return subordinateList;
	}

	public void setSubordinateList(List<String> subordinateList) {
		this.subordinateList = subordinateList;
	}

	public List<String> getAddnameList() {
		return addnameList;
	}

	public void setAddnameList(List<String> addnameList) {
		this.addnameList = addnameList;
	}

	public String getCola_company() {
		return cola_company;
	}

	public void setCola_company(String cola_company) {
		this.cola_company = cola_company;
	}
}
