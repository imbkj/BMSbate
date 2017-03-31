package Controller.Embase;

import impl.SystemControl.Data_PopedomIpml;
import impl.WorkflowCore.Core.WfFlowControlImpl;

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

import bll.Embase.EmBase_gdBll;
import bll.Taskflow.Task_controlBll;
import bll.LoginBll;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoBase.CoBase_SelectBll;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmHouseChangeModel;
import Model.EmbaseGDModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmBase_gdforbackController {

	private List<EmbaseGDModel> gdlist = new ListModelList<>();
	// private List<EmbaseGDModel> rgdlist = new ListModelList<>();
	private List<EmbaseGDModel> ownmonthList = new ListModelList<>();
	private List<loginroleModel> clientList = new ListModelList<>();
	private List<EmbaseGDModel> typeList = new ListModelList<>();
	private List<EmbaseGDModel> rtypeList = new ListModelList<>();
	private List<loginroleModel> addnamelist = new ListModelList<>();
	private List<Integer> tsdayList = new ListModelList<>();

	private EmbaseGDModel egm = new EmbaseGDModel();

	private EmBase_gdBll bll = new EmBase_gdBll();
	private Window win;

	public List<LoginModel> assistantlist = new ArrayList<LoginModel>();
	private DataPopedomService dpService = new Data_PopedomIpml("16", "", "", "", "", "", "");
	
	public EmBase_gdforbackController() {
		for (int i = 1; i < 32; i++) {
			tsdayList.add(i);
		}
		// egm.setDeclareName("未确认");

		egm.setDeclareState(3);
		egm.setOwnmonth(Integer.valueOf(DateStringChange.getOwnmonth()));
		gdlist = bll.getListonjd(egm);

		ownmonthList = bll.getownmonthList();

		// clientList = bll.getclientList();
		clientList = bll.getclientList2();

		assistantlist=dpService.getdepLoginlist();
		
		// addnamelist = bll.getaddnameListnojd();
		addnamelist = bll.getclientList2();
		String[][] type = { { "4", "养老补缴退回" }, { "4", "医疗补缴退回" },
				{ "5", "社保退回" }, { "6", "公积金退回" }, { "7", "公积金补缴退回" },
				{ "7", "公积金补缴新增" }, { "9", "社保新增" }, { "10", "公积金新增" },
				{ "11", "养老补缴新增" }, { "11", "养老和医疗补缴新增" } };

		
		for (String[] s1 : type) {
			EmbaseGDModel m = new EmbaseGDModel();
			m.setTypeId(Integer.valueOf(s1[0]));
			m.setType(s1[1]);
			typeList.add(m);
		}
		/*
		 * rtypeList = bll.gettypeListnojd(); for (EmbaseGDModel m1 : rtypeList)
		 * { if (m1.getType().equals("社保退回") || m1.getType().equals("公积金退回") ||
		 * m1.getType().equals("养老补缴退回") || m1.getType().equals("医疗补缴退回") ||
		 * m1.getType().equals("公积金补缴退回") || m1.getType().equals("社保新增") ||
		 * m1.getType().equals("公积金新增") || m1.getType().equals("养老补缴新增") ||
		 * m1.getType().equals("公积金补缴新增"))
		 * 
		 * if (m1.getTypeId().equals(5) || m1.getTypeId().equals(6) ||
		 * m1.getTypeId().equals(4) || m1.getTypeId().equals(7) ||
		 * m1.getTypeId().equals(8) || m1.getTypeId().equals(9) ||
		 * m1.getTypeId().equals(10) || m1.getTypeId().equals(11) ||
		 * m1.getTypeId().equals(12)
		 * 
		 * ) { typeList.add(m1); }
		 * 
		 * } EmbaseGDModel jlbjM = new EmbaseGDModel();
		 * jlbjM.setType("养老和医疗补缴新增"); jlbjM.setTypeId(11); typeList.add(jlbjM);
		 */
	}

	@Command
	@NotifyChange("clist")
	public void message(@BindingParam("a") EmbaseGDModel em) {
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();
		String title = "";
		CoBaseModel cobamodel = new CoBaseModel();

		Map map = new HashMap<>();

		LoginBll lbll = new LoginBll();
		LoginModel logmodel = new LoginModel();
		// int log_id=0;
		CoBase_SelectBll cobll = new CoBase_SelectBll();

		cobamodel = cobll.getCobaseByCid(em.getCid());

		logmodel = lbll.loginList(
				"and log_name='" + cobamodel.getCoba_client() + "'").get(0);

		if (em.getTypeId().equals(5) || em.getTypeId().equals(9)) {

			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),社会保险被退回";

			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name

			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emshebaochange");// 业务表名

			msglist = bll.getuserlist("39,44,45");

		} else if (em.getTypeId().equals(6) || em.getTypeId().equals(10)) {
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),住房公积金被退回";

			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emhousechange");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		} else if (em.getTypeId().equals(7)) {
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),住房公积金被退回";

			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name
			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "emhousebj");// 业务表名
			msglist = bll.getuserlist("39,40,45");
		} else if (em.getTypeId().equals(4)) {
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),社会保险被退回";

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
		map.put("title", title);

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();

	}

	// 打开联系人信息
	@Command
	public void linkinfo(@BindingParam("a") EmbaseGDModel em) {
		if (em.getCosp_sbhs_caliname().contains("联系人")) {
			String a[] = em.getCosp_sbhs_caliname().split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll.getLinkmanByCid(
						em.getCid(), 1);
				for (int i = 0; i < linklist.size(); i++) {
					if (linklist.get(i).getCali_name().equals(a[1])) {
						cali_id = linklist.get(i).getCali_id();
					}
				}
				if (cali_id != 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cali_id", String.valueOf(cali_id));
					Window window = (Window) Executions.createComponents(
							"../Cobase/CoBaseLinkMan_Sel.zul", null, map);
					window.doModal();
				}
			}
		}
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	@NotifyChange("gdlist")
	public void Search() {
		gdlist = bll.getListonjd(egm);
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

		case 5:// 社保退回
			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmSheBao/Emsi_DeleteChange_List.zul";
			break;

		case 6:// 公积金退回
			map.put("daid", em.getDataId());
			// map.put("id", em.getTaprId());
			URL = "/EmHOuse/Emhouse_EditList.zul";
			break;
		case 4:// 社保补交
			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmSheBao/Emsi_DeleteBj_List.zul";
			break;

		case 7:// 公积金退回

			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmHOuse/Emhouse_EditList.zul";
			break;

		case 8:// 劳动合同
			map.put("daid", em.getDataId());
			map.put("id", em.getTaprId());
			URL = "/EmBaseCompact/EmBaseCompact_PrintDoc.zul";
			break;

		case 9:// 社保新增

			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmSheBao/Emsi_DeleteChange_List.zul";
			break;
		case 10:// 公积金新增

			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmHOuse/Emhouse_EditList.zul";
			break;

		case 11:// 社保补交
			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmSheBao/Emsi_DeleteBj_List.zul";
			break;

		case 12:// 公积金退回
			map.put("daid", em.getDataId());
			// map.put("id", em.getTaprId());
			URL = "/EmHOuse/Emhouse_EditList.zul";
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

				message(em);
				CoBase_SelectBll cobll = new CoBase_SelectBll();

				if (em.getTypeId().equals(6) || em.getTypeId().equals(10)) {
					bll.modHchange(em.getDataId(), em.getGid());
				} else if (em.getTypeId().equals(7)) {
					bll.modHbj(em.getDataId());
				}
				if (em.getTaprId() != null) {
					if (tbll.setOpName(em.getTaprId(),

					cobll.getCobaseByCid(em.getCid()).getCoba_client()) == 1) {
						bll.modinfo(em);
						Messagebox.show("退回客服成功！", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);

					} else {
						Messagebox.show("退回失败！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					bll.modinfo(em);
				}

			}

		} else if (em.getDeclaretype() != null
				&& em.getDeclaretype().equals("结束任务单")) {
			if (Messagebox.show("你确认终止此任务单吗？？", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
				WfFlowControlImpl impl = new WfFlowControlImpl();
				// Task_controlBll tbll =new Task_controlBll();

				if (impl.StopTask(em.getTaprId(), "雇员服务中心终止任务单",
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

	public List<loginroleModel> getAddnamelist() {
		return addnamelist;
	}

	public void setAddnamelist(List<loginroleModel> addnamelist) {
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

	public List<loginroleModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<loginroleModel> clientList) {
		this.clientList = clientList;
	}

	public List<EmbaseGDModel> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<EmbaseGDModel> typeList) {
		this.typeList = typeList;
	}

	public List<Integer> getTsdayList() {
		return tsdayList;
	}

	public void setTsdayList(List<Integer> tsdayList) {
		this.tsdayList = tsdayList;
	}

	public List<LoginModel> getAssistantlist() {
		return assistantlist;
	}

	public void setAssistantlist(List<LoginModel> assistantlist) {
		this.assistantlist = assistantlist;
	}

}
