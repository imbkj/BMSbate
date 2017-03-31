package bll.EmResidencePermit;

import java.util.List;

import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.EmReg.EmReg_OperateBll;
import dal.EmReg.EmReg_OperateDal;
import dal.EmResidencePermit.Emrp_OperateDal;
import impl.DocumentsInfoImpl;
import impl.WorkflowCore.WfOperateImpl;
import service.DocumentsInfoService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoOnlineRegisterInfoModel;
import Model.EmRegistrationInfoModel;

import Model.EmResidencePermitInfoModel;
import Util.UserInfo;

public class Emrp_OperateBll implements WfBusinessService {

	// 新办、补办
	public String[] add(EmResidencePermitInfoModel m1,
			EmRegistrationInfoModel m2) {
		String[] str = new String[5];
		Integer cid = 0;
		if (m2.getCid() != null) {
			cid = m2.getCid();
		}
		f1: {
			if (m1.getErin_id() == null || m1.getErin_id() == 0) {
				/******************** 先查询该员工坐在单位是否是中智户，如果是中智户则强制办理员工就业登记，独立户则根据客服选择是否办理 ***********************/
				Emrp_ListBll bll = new Emrp_ListBll();
				CoOnlineRegisterInfoModel coom = bll
						.getCoOnlineRegisterInfo(cid);

				// 该公司没有就业登记数据则表示中智户
				if (coom.getCori_id() == null) {
					// 如果没有就业登记，则新增一条员工就业登记新办
					str = new EmReg_OperateBll().addfir(m2, m2.getEmba_name());
				} else if (m1.getIfemreg() != null
						&& m1.getIfemreg().equals("是")) {
					str = new EmReg_OperateBll().addfir(m2, m2.getEmba_name());
				} else {
					str[0] = "0";
				}
				if (str[0].equals("1")) {
					m1.setErin_id(Integer.parseInt(str[3]));
				} else {
					// break f1;
				}
			} else {
				new EmReg_OperateDal().mod(m2);
			}
			Object[] obj = { "1", m1, m2 };
			if (cid == null && m1.getCid() != null) {
				cid = m1.getCid();
			}
			WfOperateService wfs = new WfOperateImpl(new Emrp_OperateBll());
			str = wfs.AddTaskToNext(obj, "员工居住证办理", m2.getCoba_shortname()+"——"+m2.getEmba_name() + "居住证"
					+ m1.getErpi_t_kind(), 49, UserInfo.getUsername(), "", cid,
					"");
		}
		return str;
	}

	// 居住证办理状态变更
	public String[] UpdateState(EmResidencePermitInfoModel epm,
			EmRegistrationInfoModel erm, Grid gd) {
		Object[] obj = { "2", epm, erm, gd };
		Integer cid = 0;
		if (epm.getCid() != null) {
			cid = epm.getCid();
		}
		if (cid == null && erm.getCid() != null) {
			cid = erm.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new Emrp_OperateBll());
		String[] str = wfs.PassToNext(obj, epm.getErpi_tapr_id(),
				UserInfo.getUsername(), "", cid, "");
		if (epm.getErpi_state() == 5
				&& (epm.getZhtype() == 0 || epm.getErpi_t_kind().equals("补办"))) {
			Object[] obj1 = { "4", epm };
			str = wfs.SkipToN(obj1, Integer.parseInt(str[2]), 8, "系统", "", 0,
					"");
		}
		return str;
	}
	
	//服务中心发卡给员工后完结任务单
	public String[] CenterOverTask(EmResidencePermitInfoModel epm,EmRegistrationInfoModel erm) {
		Object[] obj = { "2", epm, erm, null };
		WfOperateService wfs = new WfOperateImpl(new Emrp_OperateBll());
		String[] str = wfs.OverTask(obj, epm.getErpi_tapr_id(),
				UserInfo.getUsername(), "");
		return str;
	}

	// 退回上一步
	public String[] back(EmResidencePermitInfoModel m) {
		Object[] obj = { m };
		WfOperateService wfs = new WfOperateImpl(new Emrp_OperateBll());
		String[] str = wfs.ReturnToPrev(obj, m.getErpi_tapr_id(),
				UserInfo.getUsername(), m.getErpi_return_reason());
		return str;
	}

	// 退回至第N步
	public String[] backToN(EmResidencePermitInfoModel m, Integer n) {
		Object[] obj = { m };
		WfOperateService wfs = new WfOperateImpl(new Emrp_OperateBll());
		String[] str = wfs.ReturnToN(obj, m.getErpi_tapr_id(), n,
				UserInfo.getUsername());
		return str;
	}

	// 中心退回任务单并终止任务
	public String[] CenterbackToN(EmResidencePermitInfoModel m) {
		Object[] obj = { m };
		WfOperateService wfs = new WfOperateImpl(new Emrp_OperateBll());
		String[] str = wfs.StopTask(obj, m.getErpi_tapr_id(),
				UserInfo.getUsername(), "");
		return str;
	}

	// 材料退回
	public String[] DocBack(Integer erpi_id, Grid gd) {
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		String[] str = new String[5];
		try {
			if (new Emrp_OperateDal().DocBack(erpi_id) == 1) {
				str = docOC.UpsubmitDoc(gd, erpi_id + "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		Integer id = 0;
		Integer row = 0;

		// 新办、补办
		if (obj[0].equals("1")) {
			id = new Emrp_OperateDal().add((EmResidencePermitInfoModel) obj[1]);

			// 提交空材料数据
			DocumentsInfoService docService = new DocumentsInfoImpl();
			docService.createDocumentInfo(34, id, UserInfo.getUsername());
			//docService.createDocumentInfo(321, id, UserInfo.getUsername());

			if (id > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = id + "";
				str[3] = "EmResidencePermitInfo";
				str[4] = "客服预增办理数据";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("2")) {
			// 状态变更
			EmResidencePermitInfoModel epm = (EmResidencePermitInfoModel) obj[1];
			EmRegistrationInfoModel erm = (EmRegistrationInfoModel) obj[2];
			Grid gd = (Grid) obj[3];
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			Emrp_OperateDal dal = new Emrp_OperateDal();
			List<EmResidencePermitInfoModel> list = new ListModelList<>(
					new Emrp_ListBll()
							.getStateInfoList(" and typename='前道状态' and state=1 and stateid="
									+ epm.getErpi_state()));

			if (epm.getErpi_state() == 2) {
				row = dal.mod(epm);
				row += new EmReg_OperateDal().mod(erm);
			} else {
				row = dal.UpdateState(epm);
				// 员工领取则修改居住证信息(收费状态、发证对象)
				if (epm.getErpi_state() == 11) {
					dal.mod(epm);
				}
				if (epm.getErpi_state() == 3) {
					try {
						docOC.UpsubmitDoc(gd, epm.getErpi_id() + "");
					} catch (Exception e) {
						Messagebox.show("材料提交出错!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
			if (row > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = epm.getErpi_id() + "";
				str[3] = "EmResidencePermitInfo";
				str[4] = list.get(0).getOperate();
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		}
		else if (obj[0].equals("4")) {
			EmResidencePermitInfoModel m = (EmResidencePermitInfoModel) obj[1];
			str[0] = "1";
			str[1] = "提交成功!";
			str[2] = m.getErpi_id() + "";
			str[3] = "EmResidencePermitInfo";
			str[4] = "跳过";
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] str = new String[5];
		int row = 0;
		EmResidencePermitInfoModel m = (EmResidencePermitInfoModel) obj[0];
		row = new Emrp_OperateDal().back(m);
		try {
			if (row > 0) {

				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = m.getErpi_id() + "";
				str[3] = "EmResidencePermitInfo";
				str[4] = "退回";

			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} catch (Exception e) {
			str[0] = "0";
			str[1] = "提交出错!";
		}
		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		String[] str = new String[5];
		if (obj[0].equals("1")) {
			str[0] = "1";
			str[2] = obj[2].toString();
		}
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		String[] str = new String[5];
		int row = 0;
		EmResidencePermitInfoModel m = (EmResidencePermitInfoModel) obj[0];
		row = new Emrp_OperateDal().back(m);
		try {
			if (row > 0) {

				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = m.getErpi_id() + "";
				str[3] = "EmResidencePermitInfo";
				str[4] = "退回";

			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} catch (Exception e) {
			str[0] = "0";
			str[1] = "提交出错!";
		}
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return new Emrp_OperateDal().UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
}
