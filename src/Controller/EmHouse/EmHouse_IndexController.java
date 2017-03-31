package Controller.EmHouse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_InfoBll;

import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.loginroleModel;
import R.EL;

public class EmHouse_IndexController {
	private EmHouseUpdateModel eum = new EmHouseUpdateModel();
	private List<EmHouseUpdateModel> list = new ListModelList<>();
	private List<EmHouseChangeModel> clist = new ListModelList<>();

	private Integer gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private String idcard = "";
	private String houseid = "";
	private EmHouse_InfoBll bll = new EmHouse_InfoBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private Integer ownmonth = 0;
	private boolean monthdis = false;
	private boolean stopdis = false;
	private boolean stopdis2 = false;
	private boolean returndis = false;
	private boolean errordis = false;

	public EmHouse_IndexController() {

		ownmonth = sbll.nowmonth();

		if (gid != null) {
			List<EmbaseModel> l = bll.baseInfo(gid);
			idcard = l.get(0).getEmba_idcard();
			list = bll.houseupdateInfo(gid, null);
			if (list.size() > 0) {

				eum = list.get(0);
				eum.setEmhu_cpp2(((int) Math.floor(eum.getEmhu_cpp() * 100))
						+ "%");
				eum.setEmhu_opp2(((int) Math.floor(eum.getEmhu_opp() * 100))
						+ "%");
				eum.setTotal(eum.getEmhu_cp()+eum.getEmhu_op());
				houseid = eum.getEmhu_houseid();
				idcard = eum.getEmhu_idcard();
			}
			updateState(eum);
			clist = bll.gethouseIndex(gid, houseid, idcard);
		}

	}

	@Command
	@NotifyChange("clist")
	public void message(@BindingParam("a") EmHouseChangeModel em) {

		Map map = new HashMap<>();
		map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
		if (em.getEmhc_type().equals(1)) {
			map.put("id", em.getEmhc_id());// 业务表id
			map.put("tablename", "emhousechange");// 业务表名
		} else if (em.getEmhc_type().equals(2)) {
			map.put("id", em.getEmhc_id());// 业务表id
			map.put("tablename", "emhousebj");// 业务表名

		} else if (em.getEmhc_type().equals(3)) {
			map.put("id", em.getEmhc_id());// 业务表id
			map.put("tablename", "emhousechangegjj");// 业务表名

		}

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();
		msglist = bll.getuserlist("39,40,45,43");
		for (loginroleModel m : msglist) {
			LoginModel lm = new LoginModel();
			lm.setLog_id(m.getLog_id());
			lm.setLog_name(m.getLog_name());
			mlist.add(lm);
		}
		// 收件人姓名和收件人id至少要填一个

		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	@Command
	public void history() {
		Map map = new HashMap<>();
		map.put("gid", gid);
		map.put("idcard", idcard);
		map.put("houseid", houseid);
		Window window = (Window) Executions.createComponents(
				"../EmHouse/Emhouse_history.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange({"eum","monthdis","stopdis","stopdis2","returndis","errordis"})
	public void update() {
		bll.updateInfo(gid);

		list = bll.houseupdateInfo(gid, null);
		if (list.size() > 0) {
			eum = list.get(0);
			eum.setEmhu_cpp2(((int) Math.floor(eum.getEmhu_cpp() * 100)) + "%");
			eum.setEmhu_opp2(((int) Math.floor(eum.getEmhu_opp() * 100)) + "%");
			eum.setTotal(eum.getEmhu_cp()+eum.getEmhu_op());
		}else {
			eum=null;
		}
		
		updateState(eum);
	}
	
	@NotifyChange({"monthdis","stopdis","stopdis2","returndis","errordis"})
	public void updateState(EmHouseUpdateModel m){
		monthdis = false;
		stopdis = false;
		stopdis2 = false;
		returndis = false;
		errordis = false;
		
		if (m!=null && m.getEmhu_ifstop() != null) {
			if (m.getEmhu_ifstop().equals(1)) {
				if (m.getOwnmonth().equals(ownmonth)) {
					stopdis2 = true;
				}else {
					stopdis=true;
				}
			} else if (m.getEmhu_ifstop().equals(2)) {
				errordis = true;
			} else if (m.getEmhu_ifstop().equals(3)) {
				returndis = true;
			}
		}else {
			monthdis=true;
		}
		
	}

	@Command("zyt")
	public void zyt() {
		Map map = new HashMap<>();
		map.put("gid", gid);
		Window window = (Window) Executions.createComponents(
				"../EMZYT/EmZYT_DetailAllInOne.zul", null, map);
		window.doModal();
	}

	public List<EmHouseUpdateModel> getList() {
		return list;
	}

	public void setList(List<EmHouseUpdateModel> list) {
		this.list = list;
	}

	public List<EmHouseChangeModel> getClist() {
		return clist;
	}

	public void setClist(List<EmHouseChangeModel> clist) {
		this.clist = clist;
	}

	public EmHouseUpdateModel getEum() {
		return eum;
	}

	public void setEum(EmHouseUpdateModel eum) {
		this.eum = eum;
	}

	public Integer getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Integer ownmonth) {
		this.ownmonth = ownmonth;
	}

	public boolean isStopdis() {
		return stopdis;
	}

	public void setStopdis(boolean stopdis) {
		this.stopdis = stopdis;
	}

	public boolean isStopdis2() {
		return stopdis2;
	}

	public void setStopdis2(boolean stopdis2) {
		this.stopdis2 = stopdis2;
	}

	public boolean isReturndis() {
		return returndis;
	}

	public void setReturndis(boolean returndis) {
		this.returndis = returndis;
	}

	public boolean isErrordis() {
		return errordis;
	}

	public void setErrordis(boolean errordis) {
		this.errordis = errordis;
	}

	public boolean isMonthdis() {
		return monthdis;
	}

	public void setMonthdis(boolean monthdis) {
		this.monthdis = monthdis;
	}

}
