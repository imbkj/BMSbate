package Controller.Embase;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.GroupsModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.SimpleGroupsModel;
import org.zkoss.zul.Window;

import sun.org.mozilla.javascript.internal.ObjToIntMap;

import bll.Embase.EmQutationBll;
import Controller.EmCensus.EmDh.emdh_remarkController;
import Model.CoBaseModel;
import Model.CoglistModel;
import Model.EmbaseModel;
import Util.DateStringChange;

public class EmQuotationModController {
	private List<EmbaseModel> eblist = new ListModelList<>();
	private List<CoglistModel> cgList = new ListModelList<>();

	private EmQutationBll bll = new EmQutationBll();

	private CoBaseModel cm = new CoBaseModel();
	private Window win = (Window) Path.getComponent("/cocenterwin/refleshurl/winQTM");

	private String emp = "";
	private String item = "";
	private Integer gid = 0;

	public EmQuotationModController() {
		try {
			cm = (CoBaseModel) Executions.getCurrent().getArg().get("model");
			setEblist(cm.getCid(), "");

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Command("winq")
	public void winq(@BindingParam("a") Window winc) {
		if (win == null) {
			win = winc;
		}
	}

	@Command
	@NotifyChange("eblist")
	public void searchEmp() {
		setEblist(cm.getCid(), emp);
	}

	@Command
	@NotifyChange("cgList")
	public void searchItem() {
		if (gid > 0) {
			setCgList(cm.getCid(), gid, item);
			for (int i = 0; i < cgList.size(); i++) {

				if (cgList.get(i).getCgli_startdate() != null) {
					cgList.get(i).setStartDate(
							DateStringChange.ownmonthTodate(cgList.get(i)
									.getCgli_startdate().toString()));
				}
				if (cgList.get(i).getCgli_startdate2() != null) {
					cgList.get(i).setStartDate2(
							DateStringChange.ownmonthTodate(cgList.get(i)
									.getCgli_startdate2().toString()));
				}
				if (cgList.get(i).getCgli_stopdate() != null) {
					cgList.get(i).setStopDate(
							DateStringChange.ownmonthTodate(cgList.get(i)
									.getCgli_stopdate().toString()));
				}
			}
		}
	}

	@Command("info")
	@NotifyChange({ "cgList", "gid" })
	public void info(@BindingParam("a") EmbaseModel em) {
		gid = em.getGid();
		setCgList(em.getCid(), em.getGid(), item);
		for (int i = 0; i < cgList.size(); i++) {

			if (cgList.get(i).getCgli_startdate() != null) {
				cgList.get(i).setStartDate(
						DateStringChange.ownmonthTodate(cgList.get(i)
								.getCgli_startdate().toString()));
			}
			if (cgList.get(i).getCgli_startdate2() != null) {
				cgList.get(i).setStartDate2(
						DateStringChange.ownmonthTodate(cgList.get(i)
								.getCgli_startdate2().toString()));
			}
			if (cgList.get(i).getCgli_stopdate() != null) {
				cgList.get(i).setStopDate(
						DateStringChange.ownmonthTodate(cgList.get(i)
								.getCgli_stopdate().toString()));
			}
		}
	}

	@Command
	public void updateInfo(@BindingParam("a") CoglistModel cm,
			@BindingParam("b") Integer type, @BindingParam("c") Object obj) {
		Datebox db;
		switch (type) {
		case 1:
			db = (Datebox) obj;
			if (cm.isChecked()) {

				if (db.getValue() == null) {
					cm.setCgli_startdate2(0);
				} else {
					cm.setCgli_startdate2(Integer.valueOf(DateStringChange
							.DatetoSting(db.getValue(), "yyyyMM")));
				}

				bll.modCoglist(cm);
			}
			break;
		case 2:
			db = (Datebox) obj;
			if (cm.isChecked()) {

				if (db.getValue() == null) {
					cm.setCgli_startdate(0);
				} else {
					cm.setCgli_startdate(Integer.valueOf(DateStringChange
							.DatetoSting(db.getValue(), "yyyyMM")));
				}

				bll.modCoglist(cm);
			}
			break;
		case 3:
			db = (Datebox) obj;
			if (cm.isChecked()) {

				if (db.getValue() == null) {
					cm.setCgli_stopdate(0);
				} else {
					cm.setCgli_stopdate(Integer.valueOf(DateStringChange
							.DatetoSting(db.getValue(), "yyyyMM")));
				}

				bll.modCoglist(cm);
			}
			break;
		case 4:
			if (cm.isChecked()) {
				Checkbox ck = (Checkbox) obj;
				Datebox db1 = (Datebox) ck.getParent().getParent()
						.getChildren().get(5).getChildren().get(0);
				Datebox db2 = (Datebox) ck.getParent().getParent()
						.getChildren().get(6).getChildren().get(0);
				Datebox db3 = (Datebox) ck.getParent().getParent()
						.getChildren().get(7).getChildren().get(0);
				if (db1.getValue() == null) {
					cm.setCgli_startdate2(null);
				} else {
					cm.setCgli_startdate2(Integer.valueOf(DateStringChange
							.DatetoSting(db1.getValue(), "yyyyMM")));
				}
				if (db2.getValue() == null) {
					cm.setCgli_startdate(null);
				} else {
					cm.setCgli_startdate(Integer.valueOf(DateStringChange
							.DatetoSting(db2.getValue(), "yyyyMM")));
				}
				if (db3.getValue() == null) {
					cm.setCgli_stopdate(null);
				} else {
					cm.setCgli_stopdate(Integer.valueOf(DateStringChange
							.DatetoSting(db3.getValue(), "yyyyMM")));
				}
				if (cm.getStartDate()!=null && cm.getStartDate2()!=null) {
					Integer i = bll.addCoglist(cm);
					cm.setCgli_id(i);
				}
			} else {
				bll.DelCoglist(cm);
				Checkbox ck = (Checkbox) obj;
				Datebox db1 = (Datebox) ck.getParent().getParent()
						.getChildren().get(5).getChildren().get(0);
				Datebox db2 = (Datebox) ck.getParent().getParent()
						.getChildren().get(6).getChildren().get(0);
				Datebox db3 = (Datebox) ck.getParent().getParent()
						.getChildren().get(7).getChildren().get(0);
				db1.setValue(null);
				db2.setValue(null);
				db3.setValue(null);
				cm.setStartDate(null);
				cm.setStartDate2(null);
				cm.setStopDate(null);
				cm.setCgli_startdate(null);
				cm.setCgli_startdate2(null);
				cm.setCgli_stopdate(null);

			}
			break;
		default:
			break;
		}
	}

	public List<EmbaseModel> getEblist() {
		return eblist;
	}

	public void setEblist(Integer cid, String name) {
		this.eblist = bll.embaseListInfo(cid, name);
	}

	public List<CoglistModel> getCgList() {
		return cgList;
	}

	public void setCgList(Integer cid, Integer gid, String name) {
		this.cgList = bll.coListInfo(cid, gid, name);
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

}
