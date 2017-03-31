package Controller.Embase;

import impl.SystemControl.Data_PopedomIpml;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.DataPopedomService;

import dal.EmCommercialInsurance.CI_Insurant_ListDal;

import bll.Embase.EmBase_gdBll;
import bll.Taskflow.Task_controlBll;
import bll.LoginBll;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoBase.CoBase_SelectBll;

import Model.CI_InsurantClaimModel;
import Model.CI_Insurant_ListModel;
import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmHouseChangeModel;
import Model.EmbaseGDModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.loginroleModel;
import Util.UserInfo;

public class EmBase_gdforcompactController {

	private List<EmbaseGDModel> gdlist = new ListModelList<>();
	// private List<EmbaseGDModel> rgdlist = new ListModelList<>();
	private List<EmbaseGDModel> ownmonthList = new ListModelList<>();
	private List<EmbaseGDModel> clientList = new ListModelList<>();
	private List<EmbaseGDModel> typeList = new ListModelList<>();
	private List<EmbaseGDModel> rtypeList = new ListModelList<>();
	private List<EmbaseGDModel> addnamelist = new ListModelList<>();

	private EmbaseGDModel egm = new EmbaseGDModel();

	private EmBase_gdBll bll = new EmBase_gdBll();
	private Window win;
	
	public List<LoginModel> assistantlist = new ArrayList<LoginModel>();
	private DataPopedomService dpService = new Data_PopedomIpml("16", "", "", "", "", "", "");
	/*
	 * 1. 操作状态不符，劳动合同的操作状态：未处理-已打印-待签订-待签回-待归档-已归档； 2. 需增加联系状态，未联系-已联系签订-已联系签回；
	 * 3. 需增加材料状态，未提交-部分提交-资料齐全，
	 * 同时需有页面给服务中心补充材料清单（客服需设置相关类型需提供的材料清单），服务中心按客服要求收集材料； 4. 合同类型，新签/续签；
	 */

	public EmBase_gdforcompactController() {
		// egm.setDeclareName("未确认");
		egm.setDeclareState(3);
		gdlist = bll.getListldht(egm);
		ownmonthList = bll.getownmonthList();
		clientList = bll.getclientList();
		assistantlist=dpService.getdepLoginlist();
		rtypeList = bll.gettypeListldht();
		addnamelist = bll.getaddnameListldht();
		for (EmbaseGDModel m1 : rtypeList) {
			if (m1.getType().equals("未处理") || m1.getType().equals("已打印")
					|| m1.getType().equals("待签订") || m1.getType().equals("待签回")
					|| m1.getType().equals("待归档") || m1.getType().equals("已归档")
					|| m1.getType().equals("退回"))

				if (m1.getTypeId().equals(8) || m1.getTypeId().equals(80)
						|| m1.getTypeId().equals(81)
						|| m1.getTypeId().equals(82)
						|| m1.getTypeId().equals(83)
						|| m1.getTypeId().equals(84)
						|| m1.getTypeId().equals(85)

				) {
					typeList.add(m1);
				}

		}

	}

	// 打开联系人信息
	@Command
	public void linkinfo(@BindingParam("a") EmbaseGDModel em)
			throws SQLException {
		// if (em.getCosp_sbhs_caliname().contains("联系人")) {
		// String a[] = em.getCosp_sbhs_caliname().split("—");
		// if (a.length > 1) {
		// Integer cali_id = 0;
		// CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
		// List<CoAgencyLinkmanModel> linklist = lmBll.getLinkmanByCid(em
		// .getCid());
		// for (int i = 0; i < linklist.size(); i++) {
		// if (linklist.get(i).getCali_name().equals(a[1])) {
		// cali_id = linklist.get(i).getCali_id();
		// }
		// }
		// if (cali_id != 0) {
		Map<String, String> map = new HashMap<String, String>();
		// map.put("cali_id", String.valueOf(cali_id));
		// /Window window = (Window) Executions.createComponents(
		// "../Cobase/CoBaseLinkMan_Sel.zul", null, map);

		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> list = dal.getebcccid(em.getDataId()
				.toString());
		map.put("cid", list.get(0).getCid());
		Window window = (Window) Executions.createComponents(
				"/CoServePromise/CoPromise_Info.zul", null, map);
		window.doModal();
		// }
		// }
		// }
	}

	@Command
	@NotifyChange("clist")
	public void message(@BindingParam("a") EmbaseGDModel em) {
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();

		Map map = new HashMap<>();

		LoginBll lbll = new LoginBll();
		LoginModel logmodel = new LoginModel();
		// int log_id=0;
		CoBase_SelectBll cobll = new CoBase_SelectBll();

		cobll.getCobaseByCid(em.getCid()).getCoba_client();

		logmodel = lbll.loginList(
				"and log_name='"
						+ cobll.getCobaseByCid(em.getCid()).getCoba_client()
						+ "'").get(0);

		if (em.getTypeId().equals(5)) {

			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name

			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emshebaochange");// 业务表名

			msglist = bll.getuserlist("39,44,45");

		} else if (em.getTypeId().equals(6)) {

			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emhousechange");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		} else if (em.getTypeId().equals(7)) {

			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emhousebj");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		} else if (em.getTypeId().equals(4)) {

			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emshebaobj");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		}

		// for (loginroleModel m : msglist) {
		// LoginModel lm = new LoginModel();
		// lm.setLog_id(m.getLog_id());
		// lm.setLog_name(m.getLog_name());
		// mlist.add(lm);
		// }
		mlist.add(logmodel);
		// 收件人姓名和收件人id至少要填一个

		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	@NotifyChange("gdlist")
	public void Search() {
		gdlist = bll.getListldht(egm);
	}

	// 打开短信页面
	@Command
	public void openmobile(@BindingParam("a") EmbaseGDModel em) {
		Map map = new HashMap<>();
		map.put("mobile", em.getEmba_mobile());
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("SMS_Add.zul", null, map);
		window.doModal();
	}

	// //退回客服权限
	// @Command
	// public void

	// 打开信息详情
	@Command
	@NotifyChange("gdlist")
	public void checkInfo(@BindingParam("a") EmbaseGDModel em) {
		String URL = "";
		Map map = new HashMap<>();
		switch (em.getTypeId()) {

		case 8:// 打印
			map.put("daid", em.getDataId());
			map.put("id", em.getTaprId());
			URL = "/EmBaseCompact/EmBaseCompact_PrintDoc.zul";
			break;

		case 81:// 待签订
			map.put("daid", em.getDataId());
			map.put("id", em.getTaprId());
			URL = "/EmBaseCompact/EmBaseCompact_Qd.zul";
			break;

		case 82:// 签回
			map.put("daid", em.getDataId());
			map.put("id", em.getTaprId());
			URL = "/EmBaseCompact/EmBaseCompact_Sign.zul";
			break;
		case 83:// 归档
			map.put("daid", em.getDataId());
			map.put("id", em.getTaprId());
			URL = "/EmBaseCompact/EmBaseCompact_Filing.zul";
			break;

		default:
			break;
		}
		Window window;
		window = (Window) Executions.createComponents(URL, null, map);
		window.doModal();
		Search();
	}

	// 更新状态
	@Command
	public void mod(@BindingParam("a") EmbaseGDModel em) {

		if (em.getDeclaretype() != null && em.getDeclaretype().equals("退回客服")) {
			if (Messagebox.show("你确认此任务单退给客服吗？？", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
				Task_controlBll tbll = new Task_controlBll();
				CoBase_SelectBll cobll = new CoBase_SelectBll();

				if (tbll.setOpName(em.getTaprId(),
						cobll.getCobaseByCid(em.getCid()).getCoba_client()) == 1) {
					Messagebox.show("退回客服成功！", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					bll.modinfo(em);
				} else {
					Messagebox.show("退回失败！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			}

		} else if (em.getDeclaretype() != null
				&& em.getDeclaretype().equals("结束任务单")) {
			if (Messagebox.show("你确认终止此任务单吗？？", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {

				Task_controlBll tbll = new Task_controlBll();

				if (tbll.stopTask(em.getTaprId(), "雇员服务中心终止任务单",
						UserInfo.getUsername()) == 1) {
					Messagebox.show("结束成功！", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					bll.modinfo(em);
				} else {
					Messagebox.show("退回失败！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} else {
			if (!bll.modinfo(em)) {
				Messagebox
						.show("更新失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}

	}

	// 添加备注
	@Command("embasecompact_remark")
	public void embasecompact_remark(@BindingParam("emco") EmbaseGDModel emco)
			throws Exception {
		Map arg = new HashMap();
		arg.put("daid", emco.getDataId());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompact_Remark.zul", null, arg);
		wnd.doModal();
	}

	public List<EmbaseGDModel> getAddnamelist() {
		return addnamelist;
	}

	public void setAddnamelist(List<EmbaseGDModel> addnamelist) {
		this.addnamelist = addnamelist;
	}

	public List<EmbaseGDModel> getGdlist() {
		return gdlist;
	}

	public void setGdlist(List<EmbaseGDModel> gdlist) {
		this.gdlist = gdlist;
	}

	public EmbaseGDModel getEgm() {
		return egm;
	}

	public void setEgm(EmbaseGDModel egm) {
		this.egm = egm;
	}

	public List<EmbaseGDModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList(List<EmbaseGDModel> ownmonthList) {
		this.ownmonthList = ownmonthList;
	}

	public List<EmbaseGDModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<EmbaseGDModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmbaseGDModel> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<EmbaseGDModel> typeList) {
		this.typeList = typeList;
	}

	public List<LoginModel> getAssistantlist() {
		return assistantlist;
	}

	public void setAssistantlist(List<LoginModel> assistantlist) {
		this.assistantlist = assistantlist;
	}

}
