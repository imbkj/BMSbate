package Controller.Embase;

import impl.SystemControl.Data_PopedomIpml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.DataPopedomService;

import dal.Taskflow.TaskListDal;

import bll.LoginBll;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.CoBase.CoBase_SelectBll;
import bll.Embase.EmBase_gdBll;
import bll.Taskflow.Task_controlBll;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.EmShebaoBJModel;
import Model.EmbaseGDModel;
import Model.LoginModel;
import Model.TaskListModel;
import Model.loginroleModel;
import R.EL;
import Util.RedirectUtil;

public class EmBase_gdController {

	private List<EmbaseGDModel> gdlist = new ListModelList<>();
	private List<EmbaseGDModel> ownmonthList = new ListModelList<>();
	private List<EmbaseGDModel> clientList = new ListModelList<>();
	private List<EmbaseGDModel> typeList = new ListModelList<>();
	private EmbaseGDModel egm = new EmbaseGDModel();

	private EmBase_gdBll bll = new EmBase_gdBll();
	private Window win;

	public List<LoginModel> assistantlist = new ArrayList<LoginModel>();
	private DataPopedomService dpService = new Data_PopedomIpml("16", "", "", "", "", "", "");
	
	public EmBase_gdController() {
		egm.setDeclareName("未确认");
		egm.setDeclareState(2);
		gdlist = bll.getinfoList(egm);
		ownmonthList = bll.getownmonthList();
		clientList = bll.getclientList();
		typeList = bll.gettypeList();

		EmbaseGDModel m1 = new EmbaseGDModel();
		m1.setType("补缴养老和医疗(社保)");
		typeList.add(m1);
		
		assistantlist=dpService.getdepLoginlist();
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	@NotifyChange("gdlist")
	public void Search() {
		gdlist = bll.getinfoList(egm);
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

	// 打开联系人信息
	@Command
	public void linkinfo(@BindingParam("a") EmbaseGDModel em) {
		if (em.getCosp_sbhs_caliname().contains("联系人")) {
			String a[] = em.getCosp_sbhs_caliname().split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll.getLinkmanByCid(em
						.getCid(),1);
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

	// 点击任务名称弹出页面
	@Command("taskinfo")
	public void taskinfo(@BindingParam("a") EmbaseGDModel model) {
		if (model.getTaprId() != null && model.getTaprId() != 0) {
			// 获取tali_id
			TaskListDal tdal = new TaskListDal();
			List<TaskListModel> tList = new ArrayList<TaskListModel>();
			Integer tali_id;
			tList = tdal.getListByTaprId(model.getTaprId());

			if (tList.size() > 0) {
				tali_id = tList.get(0).getTali_id();

				String url = "../Taskflow/Task_Info.zul";
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put("id", tali_id);
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
			} else {
				Messagebox.show("任务单信息获取失败，请确认是否已生成任务单。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} else {
			Messagebox.show("任务单信息获取失败，请确认是否已生成任务单。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 打开信息详情
	@Command
	@NotifyChange("gdlist")
	public void checkInfo(@BindingParam("a") EmbaseGDModel em) {
		String URL = "";
		Map map = new HashMap<>();
		switch (em.getTypeId()) {
		case 1:
			if (em.getDeclareState().equals(1)) {

				map.put("gid", em.getGid());

				URL = "/EmHouse/Emhouse_EditList.zul";
			} else {
				map.put("daid", em.getDataId());
				map.put("id", em.getTaprId());
				URL = "/EmHouse/Emhouse_ChangeGjjConfirm.zul";
			}

			break;
		case 2:// 社保交单
			map.put("daid", em.getDataId());
			map.put("id", em.getTaprId());
			if (em.getDeclareState() == 6) {
				URL = "/EmSheBao/Emsc_SZSIFileDownload.zul";
			} else if (em.getDeclareState() == 7) {// 核收
				URL = "/EmSheBao/Emsi_Change_SZSI_ColData.zul";
			} else {
				map.put("gid", em.getGid());
				URL = "/EmSheBao/Emsi_DeleteChange_List.zul";
			}
			break;
		case 3:// 社保外籍人
			if (em.getDeclareState() == 1) {// 判断是否退回
				map.put("gid", em.getGid());
				URL = "/EmSheBao/Emsi_DeleteChange_List.zul";
			} else {
				map.put("daid", em.getDataId());
				map.put("id", em.getTaprId());
				URL = "/EmSheBao/Emsi_Foreigner_ColData.zul";
			}

			break;
		case 4:// 社保补交
			map.put("daid", em.getDataId());
			map.put("id", em.getTaprId());
			if (em.getDeclareState() == 6) {
				// URL = "/EmSheBao/Emsc_BJFileDownload.zul";
				URL = "/EmSheBao/Emsc_BJFileDownloadAll.zul";
			} else if (em.getDeclareState() == 7) {// 核收
				// URL = "/EmSheBao/Emsi_BjCheck.zul";
				URL = "/EmSheBao/Emsi_BjCheckAll.zul";
			} else {
				map.put("gid", em.getGid());
				URL = "/EmSheBao/Emsi_DeleteBj_List.zul";
			}
			break;
		case 5:// 社保退回
			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmSheBao/Emsi_DeleteChange_List.zul";
			break;

		case 6:// 公积金退回
			map.put("daid", em.getDataId());
			// map.put("id", em.getTaprId());
			URL = "/EmHouse/Emhouse_EditList.zul";
			break;
		case 7:// 公积金补缴确认
			map.put("gid", em.getGid());
			// map.put("id", em.getTaprId());
			URL = "/EmHouse/Emhouse_EditList.zul";
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
		if (em.getDeclaretype() != null && (em.getDeclaretype().equals("数据退回") || em.getDeclaretype().equals("退回客服"))) {
			if (Messagebox.show("你确认此任务单退给客服吗？？", "INFORMATION", Messagebox.YES
					| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
				Task_controlBll tbll = new Task_controlBll();
				CoBase_SelectBll cobll = new CoBase_SelectBll();
				message(em);
				if (em.getTypeId().equals(1)) {
					bll.modHgjj(em.getDataId());
				} else if (em.getTypeId().equals(6)
						|| em.getTypeId().equals(10)) {
					bll.modHchange(em.getDataId(), em.getGid());
				} else if (em.getTypeId().equals(7)) {
					bll.modHbj(em.getDataId());
				}
				if (em.getTaprId() != null) {
					if (tbll.setOpName(em.getTaprId(),
							cobll.getCobaseByCid(em.getCid()).getCoba_client()) == 1) {
						Messagebox.show("退回客服成功！", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						bll.modinfo(em);
					} else {
						Messagebox.show("退回失败！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					bll.modinfo(em);
					Messagebox.show("退回客服成功！", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			}
		} else {
			if (!bll.modinfo(em)) {
				Messagebox
						.show("更新失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}

	}

	@Command
	@NotifyChange("gdlist")
	public void message(EmbaseGDModel em) {
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		List<loginroleModel> msglist = new ListModelList<>();
		String title = "";
		CoBaseModel cobamodel = new CoBaseModel();

		Map map = new HashMap<>();

		LoginBll lbll = new LoginBll();
		LoginModel logmodel = new LoginModel();
		CoBase_SelectBll cobll = new CoBase_SelectBll();

		cobamodel = cobll.getCobaseByCid(em.getCid());
		logmodel = lbll.loginList(
				"and log_name='" + cobamodel.getCoba_client() + "'").get(0);
		switch (em.getTypeId()) {
		case 1:// 公积金交单
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),住房公积金交单数据被退回";
			map.put("gid", em.getGid());
			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name

			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "EmHouseChangeGJJ");// 业务表名

			break;
		case 2:// 社保交单
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),社保交单数据被退回";
			map.put("gid", em.getGid());
			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name

			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "EmShebaoChangeSZSI");// 业务表名
			break;
		case 3:// 社保外籍人
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),社保外籍人数据被退回";
			map.put("gid", em.getGid());
			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name

			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "EmShebaoChangeForeigner");// 业务表名
			break;
		case 4:// 社保补交
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),社保补缴数据被退回";
			map.put("gid", em.getGid());
			map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name

			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "EmShebaoBJ");// 业务表名
			break;

		case 7:// 公积金补缴确认
			title = cobamodel.getCoba_company() + "," + em.getName() + "("
					+ em.getGid() + "),住房公积金补缴数据被退回";
			map.put("gid", em.getGid());
			map.put("type", "住房公积金");// 业务类型:来自WfClass的wfcl_name

			map.put("id", em.getDataId());// 业务表id
			map.put("tablename", "EmHousebj");// 业务表名
			break;

		default:
			break;
		}

		mlist.add(logmodel);
		map.put("list", mlist);// 默认收件人list
		map.put("title", title);

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	// 表格每行checkbox全选(当前页)
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") Checkbox check,
			@BindingParam("a") Checkbox cka) {
		if (check.getId().equals("cka")) {

			Integer pageSize = gd.getPageSize();
			Integer activePage = gd.getActivePage() + 1;
			Integer page = gd.getPageCount();
			Integer start = 0;
			Integer size = 0;

			if (gdlist.size() < pageSize) {
				size = gdlist.size();
			} else {
				start = gd.getActivePage() * pageSize;
				if (activePage.equals(page)) {
					if (gdlist.size() < activePage * pageSize) {
						size = gdlist.size();
					} else {
						size = activePage * pageSize;
					}
				} else {
					size = activePage * pageSize;
				}

			}

			for (int i = start; i < size; i++) {
				gdlist.get(i).setChecked(cka.isChecked());
				BindUtils.postNotifyChange(null, null, gdlist.get(i),
						"ischecked");
			}
		} else {
			if (!check.isChecked()) {
				cka.setChecked(false);
			}
		}

	}

	// 批量下载社保补交pdf
	@Command("sbbjDownload")
	@NotifyChange("gdlist")
	public void sbbjDownload() {
		List<EmShebaoBJModel> list1 = new ArrayList<EmShebaoBJModel>();
		Integer chkD = 0;
		for (EmbaseGDModel m : gdlist) {
			if (m.isChecked()) {// 获取已选中数据
				if (m.getDeclareState() != 6 || !"补缴养老(社保)".equals(m.getType())) {
					chkD = 1;
					break;
				} else {
					// 转成EmShebaoBJModel数据
					EmShebaoBJModel bjM = new EmShebaoBJModel();
					bjM.setId(m.getDataId());
					bjM.setOwnmonth(m.getOwnmonth());
					bjM.setGid(m.getGid());
					list1.add(bjM);
				}

			}
		}

		if (chkD == 0) {
			if (list1.size() > 0) {
				for (EmShebaoBJModel m : list1) {
					if (list1.get(0).getGid() != m.getGid()
							|| list1.get(0).getOwnmonth() != m.getOwnmonth()) {
						chkD = 1;
						break;
					}
				}

				if (chkD == 0) {
					Map map = new HashMap<>();
					map.put("list", list1);// 数据list
					Window window = (Window) Executions
							.createComponents(
									"../EmSheBao/Emsc_BJFileDownloadAll.zul",
									null, map);
					window.doModal();
					// 刷新
					Search();
				} else {
					Messagebox.show("请按正确的规则选择社保补交数据！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				Messagebox.show("请选择社保补交数据！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请按正确的规则选择社保补交数据！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 批量核收社保补交数据
	@Command("sbbjCheck")
	@NotifyChange("gdlist")
	public void sbbjCheck() {

		List<EmShebaoBJModel> list1 = new ArrayList<EmShebaoBJModel>();
		Integer chkD = 0;
		for (EmbaseGDModel m : gdlist) {
			if (m.isChecked()) {// 获取已选中数据
				if (m.getDeclareState() != 7 || !"补缴养老(社保)".equals(m.getType())) {
					chkD = 1;
					break;
				} else {
					// 转成EmShebaoBJModel数据
					EmShebaoBJModel bjM = new EmShebaoBJModel();
					bjM.setId(m.getDataId());
					bjM.setOwnmonth(m.getOwnmonth());
					bjM.setGid(m.getGid());
					list1.add(bjM);
				}

			}
		}

		if (chkD == 0) {
			if (list1.size() > 0) {
				for (EmShebaoBJModel m : list1) {
					if (list1.get(0).getGid() != m.getGid()
							|| list1.get(0).getOwnmonth() != m.getOwnmonth()) {
						chkD = 1;
						break;
					}
				}

				if (chkD == 0) {
					Map map = new HashMap<>();
					map.put("list", list1);// 数据list
					Window window = (Window) Executions.createComponents(
							"../EmSheBao/Emsi_BjCheckAll.zul", null, map);
					window.doModal();
					// 刷新
					Search();
				} else {
					Messagebox.show("请按正确的规则选择社保补交数据！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				Messagebox.show("请选择社保补交数据！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请按正确的规则选择社保补交数据！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

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
