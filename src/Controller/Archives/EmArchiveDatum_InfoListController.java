package Controller.Archives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmHouseChangeModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.TaskProcessViewModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmArchiveDatum_InfoListController {
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveDatumModel> datumlist;

	String strss = "";

	public EmArchiveDatum_InfoListController() {
		setDatumlist(bll.getEmArchiveDatumInfo("  and eada_type=0 "));

	}

	// 查询按钮的点击事件
	@Command
	@NotifyChange("datumlist")
	public void search(@BindingParam("acclass") String acclass,
			@BindingParam("company") String company,
			@BindingParam("name") String name,
			@BindingParam("acid") String acid,
			@BindingParam("astate") Combobox astate,
			@BindingParam("yewclass") Combobox yewclass) {
		strss = "";
		String str = "  and eada_state>0";
		if (company != null && !company.equals("") && company != "") {
			str = str + " and (coba_company like '%" + company
					+ "%' or coba_shortname like '%" + company + "%')";
		}
		if (name != null && !name.equals("") && name != "") {
			str = str + " and eada_name='" + name + "'";
		}
		if (acclass != "" && !acclass.equals("")) {
			str = str + " and eada_filetype='" + acclass + "'";
		}
		if (acid != null && !acid.equals("") && acid != "") {
			str = str + " and eada_fid like '%" + acid + "%'";
		}
		if (astate.getValue() != null && !astate.getValue().equals("")
				&& astate.getValue() != "") {
			str = str + " and eada_final="
					+ astate.getSelectedItem().getValue();
		}
		if (yewclass.getValue() != null && !yewclass.getValue().equals("")
				&& yewclass.getValue() != "") {
			str = str + " and eada_type="
					+ yewclass.getSelectedItem().getValue();
		}
		strss = str;
		datumlist = bll.getEmArchiveDatumInfo(str);
	}

	// 详细页面
	@Command
	@NotifyChange("datumlist")
	public void detail(@BindingParam("model") EmArchiveDatumModel model) {
		EmArchiveModel amodel = new EmArchiveModel();
		List<EmArchiveModel> archivelist = bll.getEmArchiveInfo(" and a.gid="
				+ model.getGid());
		if (!archivelist.isEmpty()) {
			amodel = archivelist.get(0);
		}
		// 查询任务单信息
		List<TaskProcessViewModel> tlist = bll.getLastId(model
				.getEada_tapr_id() + "");
		TaskProcessViewModel tmodel = new TaskProcessViewModel();
		if (!tlist.isEmpty()) {
			tmodel = tlist.get(0);
		}
		Map map = new HashMap<>();
		map.put("daid", model.getEada_id());
		map.put("amodel", amodel);
		map.put("model", model);
		map.put("tmodel", tmodel);
		Window window = (Window) Executions.createComponents(
				"EmArchiveDatum_DetailInfo.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("clist")
	public void message(@BindingParam("a") EmArchiveDatumModel em) {

		Map map = new HashMap<>();
		map.put("id", em.getEada_id());// 业务表id
		map.put("tablename", "emarchivedatum");// 业务表名

		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();
		msglist = bll.getuserlist("20,50,165");
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

	// 受理
	@Command
	@NotifyChange("datumlist")
	public void Accepted(@BindingParam("archivemodel") EmArchiveDatumModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("daid", model.getEada_id());
		map.put("id", model.getEada_tapr_id());
		Window window = (Window) Executions.createComponents("", null, map);
		window.doModal();
		if (strss == "") {
			strss = " and eada_final=1 ";
		}
		datumlist = bll.getEmArchiveDatumInfo(strss);
	}

	// 联系记录
	@Command
	public void linkinfo(@BindingParam("m") EmArchiveDatumModel model) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("daid", model.getEada_id());
		Window window = (Window) Executions.createComponents(
				"/Archives/Archive_addLink.zul", null, map);
		window.doModal();
	}

	// 弹出页面
	@Command
	@NotifyChange("datumlist")
	public void openZUL(
			@BindingParam("archivemodel") EmArchiveDatumModel model,
			@BindingParam("url") String url) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("daid", model.getEada_id() + "");
		map.put("id", model.getEada_tapr_id());
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		datumlist = bll.getEmArchiveDatumInfo(strss);
	}

	// 弹出系统短信
	@Command
	public void sendSysMessage(
			@BindingParam("archivemodel") EmArchiveDatumModel em) {
		/*
		 * Map map = new HashMap<>(); map.put("gid", model.getGid());
		 * map.put("cn", "档案"); map.put("title", "档案业务短信"); Window window =
		 * (Window) Executions.createComponents(
		 * "/SysMessage/SysMessage_Send.zul", null, map); window.doModal();
		 */
		Map map = new HashMap<>();
		map.put("id", em.getEada_id());// 业务表id
		map.put("tablename", "EmArchiveDatum");// 业务表名
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel lm = new LoginModel();
		lm.setLog_name(em.getCoba_client());
		mlist.add(lm);
		// 收件人姓名和收件人id至少要填一个
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	// 弹出手机短信
	@Command
	public void sendSMSMessage(
			@BindingParam("archivemodel") EmArchiveDatumModel model) {
		Map map = new HashMap<>();
		map.put("gid", model.getGid());

		Window window = (Window) Executions.createComponents(
				"/SmsMessage/SmsManager_add.zul", null, map);
		window.doModal();
	}

	// 弹出变更档案调出页面
	@Command
	@NotifyChange("datumlist")
	public void openZULcheck(
			@BindingParam("archivemodel") EmArchiveDatumModel model) {
		EmArchive_SelectBll tbll = new EmArchive_SelectBll();
		List<TaskProcessViewModel> tlist = tbll.getLastId(model
				.getEada_tapr_id() + "");
		TaskProcessViewModel tmodel = new TaskProcessViewModel();
		if (tlist.size() > 0) {
			tmodel = tlist.get(0);
		}
		String url = "";
		if (tmodel.getWfno_step() == 4) {
			url = "Archive_FileCheckOutData.zul";
		} else {
			url = "Archive_FileCheckOutAccount.zul";
		}
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("daid", model.getEada_id() + "");
		map.put("id", model.getEada_tapr_id());
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		datumlist = bll.getEmArchiveDatumInfo(strss);
	}

	// 弹出备注
	@Command
	public void addremark(
			@BindingParam("archivemodel") EmArchiveDatumModel model) {
		Map map = new HashMap<>();
		map.put("id", model.getEada_id().toString());
		map.put("typeid", "2");
		map.put("gid", model.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public List<EmArchiveDatumModel> getDatumlist() {
		return datumlist;
	}

	public void setDatumlist(List<EmArchiveDatumModel> datumlist) {
		this.datumlist = datumlist;
	}

}
