package Controller.Embase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmHouse.EmHouse_EditBll;
import bll.Embase.Embase_DimissionBll;
import bll.Embase.Embase_ReEntryBll;
import bll.Embase.Embase_ReEntryOperateBll;

import Model.CancelDimissionModel;
import Model.CoOfferListModel;
import Model.EmArchiveDatumModel;
import Model.EmBaseCompactChangeModel;
import Model.EmComInsuranceChangeModel;
import Model.EmCommissionOutChangeModel;
import Model.EmHouseChangeModel;
import Model.EmSheBaoChangeModel;
import Model.EmbaseModel;
import Model.LoginModel;
import Util.UserInfo;

public class Embase_CancelDimissionController {
	private Embase_ReEntryBll bll = new Embase_ReEntryBll();
	private EmbaseModel emmodel = (EmbaseModel) Executions.getCurrent()
			.getArg().get("model");
	private Embase_DimissionBll dBll = new Embase_DimissionBll();
	private EmbaseModel model = bll.getEmbaseInfoByGid(emmodel.getGid());
	private List<Integer> cgliId = dBll.getCgliId(emmodel.getGid());// 获取报价单数据
	private List<CoOfferListModel> cflList = dBll
			.getCoofferlistByColiId(cgliId);
	private List<CancelDimissionModel> list = bll.getList(emmodel.getGid());

	// 提交事件
	@Command
	public void submit(@BindingParam("win") Window win) throws Exception {
		String str = isStateOk();
		if (str == "") {
			Embase_ReEntryOperateBll obll = new Embase_ReEntryOperateBll();
			// 更改服务的状态和报价单的终止收费时间
			Integer k = obll.EmBase_ReEntry(emmodel.getGid(),
					UserInfo.getUsername());
			if (k > 0) {
				String[] msg = new String[5];
				String errstr = "";
				Integer lk = 0;
				// 根据任务单id把个业务的停缴任务单终止
				for (CancelDimissionModel m : list) {
					if (m.getTypeid() == 1)// 社保
					{
						EmSheBaoChangeModel ml = bll.getSheBaoInfoModel(m
								.getId());
						if (!ml.getEmsc_ifdeclare().equals(1)
								&& !ml.getEmsc_ifdeclare().equals(2)) {
							if(ml.getEmsc_tapr_id()!=0)
							{
								msg = obll.endTaskList(ml.getId() + "",
									"EmShebaoChange", ml.getEmsc_tapr_id());
							}
							else
							{
								msg[0] = "1";
							}
							if (msg[0] == "1") {
								Integer kk = obll.updateEmshebao(m.getId());
								lk = lk + 1;

							} else {
								errstr = "社保流程：" + msg[1];
							}
						}
					} else if (m.getTypeid() == 2)// 公积金
					{
						EmHouseChangeModel ml = bll.getEmhouseInfoModel(m
								.getId());
						if (!ml.getEmhc_ifdeclare().equals(1)
								&& !ml.getEmhc_ifdeclare().equals(2)) {
							msg = obll.endTaskList(ml.getEmhc_id() + "",
									"EmHouseChange", ml.getEmhc_tapr_id());
							if (msg[0] == "1") {
								Integer kk = obll.deleteEmHouse(m.getId());
								if(kk>0)
								{
									EmHouse_EditBll hbll=new EmHouse_EditBll();
									hbll.updateData(emmodel.getGid());
								}
								lk = lk + 1;

							} else {
								errstr = "公积金流程：" + msg[1];
							}
						}
					} else if (m.getTypeid() == 3)// 劳动合同
					{
						EmBaseCompactChangeModel ml = bll
								.getEmBaseCompactChangeInfoModel(m.getId());
						if (ml.getEbcc_change().equals("终止")
								|| ml.getEbcc_change().equals("解约")) {
							msg = obll
									.endTaskList(ml.getEbcc_id() + "",
											"EmBaseCompactChange",
											ml.getEbcc_tapr_id());
							if (msg[0] == "1") {
								Integer kk = obll.deletesEmCompact(m.getId());
								if (kk > 0) {
									lk = lk + 1;
								}
							} else {
								errstr = "社保流程：" + msg[1];
							}
						}
					} else if (m.getTypeid() == 4)// 商保
					{
						EmComInsuranceChangeModel ml = bll
								.getEmComInsuranceChangeInfoModel(m.getId());
						if (!ml.getEcic_state2().equals(1)) {
							msg = obll.endTaskList(ml.getEcic_id() + "",
									"EmComInsuranceChange",
									ml.getEcic_tapr_id());
							if (msg[0] == "1") {
								Integer kk = obll.deletesahngbao(m.getId());
								if (kk > 0) {
									lk = lk + 1;
								}
							} else {
								errstr = "商保流程：" + msg[1];
							}
						}
					} else if (m.getTypeid() == 5)// 委托外地
					{
						EmCommissionOutChangeModel ml = bll
								.getEmCommissionOutChangeInfoModel(m.getId());
						if (!ml.getEcoc_state().equals(2)
								&& !ml.getEcoc_state().equals(3)) {
							msg = obll.endTaskList(ml.getEcoc_id() + "",
									"EmCommissionOutChange",
									ml.getEcoc_tapr_id());
							if (msg[0] == "1") {
								lk = lk + 1;
							} else {
								errstr = "委托外地流程：" + msg[1];
							}
						}
					} else if (m.getTypeid() == 6)// 档案
					{
						EmArchiveDatumModel ml = bll
								.getEmArchiveDatumInfoModel(m.getId());
						if (!ml.getEada_final().equals(2)
								&& !ml.getEada_final().equals(3)) {
							msg = obll.endTaskList(ml.getEada_id() + "",
									"EmArchiveDatum", ml.getEada_tapr_id());
							if (msg[0] == "1") {
								lk = lk + 1;
							} else {
								errstr = "档案流程：" + msg[1];
							}
						}
					}
				}
				if (lk > 0) {
					Messagebox.show("提交成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					if (errstr == null || errstr.equals("")) {
						errstr = "提交成功";
						Messagebox.show(errstr, "提示", Messagebox.OK,
								Messagebox.INFORMATION);
					} else {
						Messagebox.show(errstr, "提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				if (list.size() > 0) {
					Messagebox.show("重新入职失败", "提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} else {
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 判断是否所有业务的状态都是未申报或退回
	private String isStateOk() {
		String str = "";
		for (CancelDimissionModel m : list) {
			if (m.getStateid() != 0) {
				if (m.getTablename().equals("EmHouseChange")) {
					List<EmHouseChangeModel> hslist = bll
							.getEmhouseInfo(" and emhc_IfDeclare=0 and gid="
									+ emmodel.getGid());
					if (hslist.size() > 1) {
						str = "公积金有" + hslist.size()
								+ "停缴未申报的数据，请联系IT人员处理后再操作!";
						break;
					} else if (hslist.size() == 0) {
						str = "“" + m.getInfoname() + "”的状态为"
								+ m.getStatename() + ",不可以恢复入职，请联系后道人员处理后再操作！";
						break;
					}
				}
				else if (m.getTablename().equals("EmShebaoChange")) {
					
				}
			}
		}
		// 查询公积金当月是否有多条未申报的停缴数据
		if (str == "") {
			List<EmHouseChangeModel> hslist = bll
					.getEmhouseInfo(" and emhc_IfDeclare=0 and gid="
							+ emmodel.getGid());
			if (hslist.size() > 1) {
				str = "公积金有" + hslist.size() + "停缴未申报的数据，请联系IT人员处理后再操作!";
			}
		}
		return str;
	}

	// 弹出发短信页面
	@Command
	public void send(@BindingParam("model") CancelDimissionModel camodel) {
		Map map = new HashMap<>();
		map.put("type", camodel.getType());// 业务类型:根据WfClass表的wfcl_name得来
		map.put("id", camodel.getId());// 业务表id
		map.put("tablename", camodel.getTablename());// 业务表名
		LoginModel mo = new LoginModel();
		mo.setLog_name(model.getCoba_client());
		// mo.setLog_id(246);
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		// mlist.add(mo);
		map.put("list", mlist);// 默认收件人
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	public EmbaseModel getModel() {
		return model;
	}

	public void setModel(EmbaseModel model) {
		this.model = model;
	}

	public List<CoOfferListModel> getCflList() {
		return cflList;
	}

	public void setCflList(List<CoOfferListModel> cflList) {
		this.cflList = cflList;
	}

	public List<CancelDimissionModel> getList() {
		return list;
	}

	public void setList(List<CancelDimissionModel> list) {
		this.list = list;
	}
}
