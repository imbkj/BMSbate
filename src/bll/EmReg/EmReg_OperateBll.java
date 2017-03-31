package bll.EmReg;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Grid;

import bll.CoReg.CoReg_ListBll;
import dal.EmReg.EmReg_ListDal;
import dal.EmReg.EmReg_OperateDal;
import impl.DocumentsInfoImpl;
import impl.WorkflowCore.WfOperateImpl;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoOnlineRegisterInfoModel;
import Model.EmRegContactModel;
import Model.EmRegistrationInfoModel;
import Model.EmResidencePermitInfoModel;
import Util.UserInfo;
import service.DocumentsInfoService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class EmReg_OperateBll implements WfBusinessService {

	// 新办、续办
	public String[] add(EmRegistrationInfoModel m, String name) {
		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "员工就业登记办理",
				name + "就业登记" + m.getErin_t_kind(), 30, UserInfo.getUsername(),
				"", 0, "");
		Object[] obj1 = { "1", m, str[3] };
		str = wfs.SkipToNext(obj1, Integer.parseInt(str[2]),
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 状态变更
	public String[] UpdateState(EmRegistrationInfoModel m, Grid gd) {
		Object[] obj = { "2", m, gd };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getErin_tapr_id(),
				UserInfo.getUsername(), "", cid, "");

		// Zhtype==0表示该客户为中智户，跳过盖章步骤
		if (m.getErin_state() == 4 && m.getZhtype() == 0) {
			Object[] obj1 = { "4", m };
			str = wfs.SkipToN(obj1, Integer.parseInt(str[2]), 7, "系统", "", 0,
					"");
		}
		return str;
	}

	// 终止流程
	public String[] stop(EmRegistrationInfoModel m, Integer tapr_id,
			Integer erin_ifbackdata) {
		Object[] obj = { m, erin_ifbackdata, "0" };
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.StopTask(obj, tapr_id, "系统", "");
		return str;
	}

	// 预增数据
	public String[] addfir(EmRegistrationInfoModel m, String name) {
		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "员工就业登记办理",
				name + "就业登记" + m.getErin_t_kind(), 30, UserInfo.getUsername(),
				"", cid, "");
		return str;
	}

	// 服务中心提交
	public String[] readd(EmRegistrationInfoModel m) {
		Object[] obj = { "3", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getErin_tapr_id(),
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 退回上一步
	public String[] back(EmRegistrationInfoModel m) {
		Object[] obj = { m };
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.ReturnToPrev(obj, m.getErin_tapr_id(),
				UserInfo.getUsername(), m.getErsr_remark());
		return str;
	}

	// 退回至第N步
	public String[] backToN(EmRegistrationInfoModel m, Integer n) {
		Object[] obj = { m };
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.ReturnToN(obj, m.getErin_tapr_id(), n,
				UserInfo.getUsername());
		return str;
	}

	// 终止流程
	public String[] StopTask(EmRegistrationInfoModel m) {
		Object[] obj = { m, 0, "1" };
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.StopTask(obj, m.getErin_tapr_id(),
				UserInfo.getUsername(), "");
		return str;
	}

	// 材料退回
	public String[] DocBack(EmRegistrationInfoModel m, Grid gd) {
		Object[] obj = { "5", m, gd };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getErin_tapr_id(),
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	public Integer docinfoback(Integer erin_id, String str) {
		EmReg_OperateDal dal = new EmReg_OperateDal();
		return dal.docinfoback(erin_id, str);
	}

	// 添加居住证
	public Integer EmResidencePermitInfoAdd(EmResidencePermitInfoModel m) {
		EmReg_OperateDal dal = new EmReg_OperateDal();
		return dal.EmResidencePermitInfoAdd(m);
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		int id = 0;
		int row = 0;
		int puzu_id = 0;
		if (obj[0].equals("1")) {
			// 新办、续办
			EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[1];
			id = new EmReg_OperateDal().add(m);
			try {
				if (id > 0) {
					DocumentsInfoService docService = new DocumentsInfoImpl();
					CoOnlineRegisterInfoModel com = new CoReg_ListBll()
							.getCoregInfobyCid(m.getCid());

					if (m.getErin_hjtype() != null) {
						if (m.getErin_hjtype().equals("本市城镇")) {
							puzu_id = com.getCori_sz_puzu_id() == null ? 12
									: com.getCori_sz_puzu_id();
							EmReg_ListDal dl = new EmReg_ListDal();
							boolean flag = dl.isexistdata(puzu_id);
							if (!flag) {
								puzu_id = 12;
							}
						} else {
							puzu_id = com.getCori_wd_puzu_id() == null ? 22
									: com.getCori_wd_puzu_id();
							EmReg_ListDal dl = new EmReg_ListDal();
							boolean flag = dl.isexistdata(puzu_id);
							if (!flag) {
								puzu_id = 22;
							}
						}
						docService.createDocumentInfo(puzu_id, id,
								UserInfo.getUsername());
					}

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "EmRegistrationInfo";
					str[4] = "客服提交申报";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
			}
		} else if (obj[0].equals("2")) {
			// 状态变更
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[1];
			List<EmRegistrationInfoModel> list = new ArrayList<>();
			list = new EmReg_ListBll()
					.getStateInfoList(" and typename='后道状态' and stateid="
							+ m.getErin_state());

			row = new EmReg_OperateDal().UpdateState(m);
			try {
				if (row > 0) {
					if (m.getErin_state() == 3) {
						String[] str1 = docOC.UpsubmitDoc((Grid) obj[2],
								m.getErin_id() + "");
						if (str1[0].equals("1")) {
							str[0] = "1";
							str[1] = "提交成功!";
							str[2] = m.getErin_id() + "";
							str[3] = "EmRegistrationInfo";
							str[4] = list.get(0).getOperate();
						} else {
							str[0] = "0";
							str[1] = "材料签收失败!";
						}
					} else {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getErin_id() + "";
						str[3] = "EmRegistrationInfo";
						str[4] = list.get(0).getOperate();
					}
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "状态变更出错!";
			}
		} else if (obj[0].equals("3")) {
			// 重新提交
			DocumentsInfoService docService = new DocumentsInfoImpl();
			EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[1];
			List<EmRegistrationInfoModel> list = new ArrayList<>();
			list = new EmReg_ListBll()
					.getStateInfoList(" and typename='前道状态' and stateid="
							+ m.getErin_state());
			row = new EmReg_OperateDal().mod(m);
			try {
				if (row > 0) {
					CoOnlineRegisterInfoModel com = new CoReg_ListBll()
							.getCoregInfobyCid(m.getCid());

					if (m.getErin_hjtype().equals("本市城镇")) {
						puzu_id = com.getCori_sz_puzu_id() == null ? 12 : com
								.getCori_sz_puzu_id();
					} else {
						puzu_id = com.getCori_wd_puzu_id() == null ? 22 : com
								.getCori_wd_puzu_id();
					}
					if (new EmReg_ListDal()
							.isDocExists(m.getErin_id(), puzu_id)) {
						docService.createDocumentInfo(puzu_id, id,
								UserInfo.getUsername());
					}
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getErin_id() + "";
					str[3] = "EmRegistrationInfo";
					str[4] = list.get(0).getOperate();

				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "状态变更出错!";
			}
		} else if (obj[0].equals("4")) {
			// 跳过盖章步骤
			EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[1];
			str[0] = "1";
			str[1] = "提交成功!";
			str[2] = m.getErin_id() + "";
			str[3] = "EmRegistrationInfo";
			str[4] = "跳过";
		} else if (obj[0].equals("5")) {
			// 退回材料
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
			try {
				EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[1];
				if (new EmReg_OperateDal().DocBack(m.getErin_id()) == 1) {

					if (docOC.UpsubmitDoc((Grid) obj[2], m.getErin_id() + "")[0]
							.equals("1")) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getErin_id() + "";
						str[3] = "EmRegistrationInfo";
						str[4] = "退回材料";
					} else {
						str[0] = "0";
						str[1] = "退回材料失败!";
					}
				} else {
					str[0] = "0";
					str[1] = "状态变更失败!";
				}
			} catch (Exception e) {
				e.printStackTrace();
				str[0] = "0";
				str[1] = "退回材料出错：" + e.toString();
			}
		} else if (obj[0].equals("6")) {
			// 申请就业登记终止
			EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[1];
			id = new EmReg_OperateDal().erinStopApp(m);
			try {
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getErin_id() + "";
					str[3] = "EmRegistrationInfo";
					str[4] = "申请就业登记终止";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
			}
		} else if (obj[0].equals("7")) {
			// 申请就业登记终止
			EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[1];
			id = new EmReg_OperateDal().erinStop(m);
			try {
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "EmRegistrationInfo";
					str[4] = "操作就业登记终止";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
			}
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] str = new String[5];
		int row = 0;
		EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[0];
		row = new EmReg_OperateDal().UpdateState(m);
		try {
			if (row > 0) {

				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = m.getErin_id() + "";
				str[3] = "EmRegistrationInfo";
				str[4] = "退回";

			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} catch (Exception e) {
			str[0] = "0";
			str[1] = "状态变更出错!";
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
		// TODO Auto-generated method stub
		String[] str = new String[5];
		EmRegistrationInfoModel m = (EmRegistrationInfoModel) obj[0];
		Integer erin_ifbackdata = (Integer) obj[1];
		String type = obj[2].toString();
		int row = 0;
		if (type.equals("0")) {
			row = new EmReg_OperateDal().updateerin(m.getErin_id(),
					erin_ifbackdata);
		} else if (type.equals("1")) {
			row = new EmReg_OperateDal().UpdateState(m);
		}
		str[0] = "1";
		str[1] = "提交成功!";
		str[2] = m.getErin_id() + "";
		str[3] = "EmRegistrationInfo";
		str[4] = "办理完成";
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return new EmReg_OperateDal().UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 终止申请
	public String[] erinStopApp(EmRegistrationInfoModel m) {
		Object[] obj = { "6", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "员工就业登记终止", m.getEmba_name()
				+ "就业登记终止", 110, UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 终止申报
	public String[] erinStop(EmRegistrationInfoModel m) {
		Object[] obj = { "7", m, };
		WfOperateService wfs = new WfOperateImpl(new EmReg_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getErin_tapr_id(),
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

	public Integer UpdateTime(EmRegistrationInfoModel m) {
		EmReg_OperateDal dal = new EmReg_OperateDal();
		return dal.UpdateTime(m);
	}

	// 添加联系记录
	public Integer EmRegContactAdd(EmRegContactModel model) {
		EmReg_OperateDal dal = new EmReg_OperateDal();
		return dal.EmRegContactAdd(model);
	}
}
