package bll.CoHousingFund;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;

import dal.CoCompact.CocompactDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.CoHousingFund.CoHousingFund_OperateDal;
import impl.DocumentsInfoImpl;
import impl.WorkflowCore.WfOperateImpl;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Util.UserInfo;
import service.DocumentsInfoService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class CoHousingFund_OperateBll implements WfBusinessService {

	// 缴存登记/账户接管
	public String[] add(CoHousingFundChangeModel m) {
		Object[] obj = { "1", m };
		Integer taskid = 0;
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		if (m.getChfc_addtype().equals("缴存登记")) {
			taskid = 89;
		} else if (m.getChfc_addtype().equals("账户接管")) {
			taskid = 90;
		}
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), taskid,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");
		return str;
	}

	public List<CoBaseModel> getcobase(Integer id) {

		List<CoBaseModel> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		list = dal.getcobaseList(id);

		return list;
	}

	// 状态变更
	public String[] updatestate(CoHousingFundChangeModel m, Grid gd) {
		Object[] obj = { "2", m, gd };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = new String[5];
		if ((m.getChfc_addtype().equals("信息变更")
				|| m.getChfc_addtype().equals("比例调整") || m.getChfc_addtype()
				.equals("降低比例/缓缴提前终止"))
				&& m.getChfc_state().equals(3)
				&& m.getChfc_puzu_id().equals(0)) {
			String remark = "";
			switch (m.getChfc_addtype()) {
			case "信息变更":
				remark = "只修改备案类信息,无退还材料流程,任务单自动完结;";
				break;
			case "比例调整":
				remark = "该公司有密钥信息,无退还材料流程,任务单自动完结;";
				break;
			case "降低比例/缓缴提前终止":
				remark = "该公司有密钥信息,无退还材料流程,任务单自动完结;";
				break;
			default:
				break;
			}
			remark += m.getChfc_remark1() == null ? "" : m.getChfc_remark1()
					.trim();
			str = wfs.OverTask(obj, m.getChfc_tapr_id(),
					UserInfo.getUsername(), remark);
		} else {
			str = wfs
					.PassToNext(obj, m.getChfc_tapr_id(),
							UserInfo.getUsername(), m.getChfc_remark1(),
							m.getCid(), "");
		}
		return str;
	}

	// 退还材料
	public String[] DocBack(CoHousingFundChangeModel m, Grid gd) {
		Object[] obj = { "3", m, gd };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getChfc_tapr_id(),
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");
		return str;
	}

	// 信息变更
	public String[] inforchange(CoHousingFundChangeModel m) {
		Object[] obj = { "4", m };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), 93,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");

		return str;
	}

	// 比例调整
	public String[] radixcpp(CoHousingFundChangeModel m) {
		Object[] obj = { "5", m };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), 94,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");

		return str;
	}

	// 降低比例
	public String[] radixlow(CoHousingFundChangeModel m) {
		Object[] obj = { "5", m };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), 95,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");
		return str;
	}

	// 缓缴
	public String[] radixhj(CoHousingFundChangeModel m) {
		Object[] obj = { "5", m };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), 96,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");
		return str;
	}

	// 降低比例/缓缴提前终止
	public String[] termination(CoHousingFundChangeModel m) {
		Object[] obj = { "5", m };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), 97,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");
		return str;
	}

	// 单位登记注销
	public String[] cancel(CoHousingFundChangeModel m) {
		Object[] obj = { "5", m };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), 98,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");
		return str;
	}

	// 单位合同终止
	public String[] surrender(CoHousingFundChangeModel m) {
		Object[] obj = { "5", m };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "单位公积金" + m.getChfc_addtype(),
				m.getCoba_shortname() + "单位公积金" + m.getChfc_addtype(), 99,
				UserInfo.getUsername(), m.getChfc_remark(), m.getCid(), "");
		return str;
	}

	// 客服重新发送数据
	public Integer sendData(CoHousingFundChangeModel cm) {
		Object[] obj = { "客服重新发送数据", cm };
		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		String[] str = wfs.PassToNext(obj, cm.getChfc_tapr_id(),
				UserInfo.getUsername(), "", 0, "");
		if (str[0].equals("1")) {
			return 1;
		} else {
			return 0;
		}
	}

	/** 
	* @Title: updateCompact 
	* @Description: TODO(更新合同独立户信息) 
	* @param cm
	* @return
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer updateCompact(CoCompactModel cm) {
		Integer i = 0;
		CocompactDal dal = new CocompactDal();
		i = dal.mod(cm, cm.getCoco_id());
		return i;
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		Integer id = 0;
		Integer row = 0;
		CoHousingFund_OperateDal dal = new CoHousingFund_OperateDal();
		if (obj[0].equals("1")) {
			// 缴存登记/账户接管
			CoHousingFundChangeModel m = (CoHousingFundChangeModel) obj[1];

			try {
				if (m.getChfc_addtype().equals("缴存登记")) {
					if (m.getChfc_ispwd().equals(1)) {
						m.setChfc_puzu_id(39);
					} else {
						m.setChfc_puzu_id(40);
					}
				} else if (m.getChfc_addtype().equals("账户接管")) {
					if (m.getChfc_ispwd().equals(1)) {
						if (m.getIsmodzb().equals("1")) {
							m.setChfc_puzu_id(73);
						} else {
							m.setChfc_puzu_id(74);
						}
					} else {
						m.setChfc_puzu_id(75);
					}
				}
				id = dal.cohouseadd(m);

				if (id > 0) {
					// 提交空材料数据
					DocumentsInfoService docService = new DocumentsInfoImpl();
					docService.createDocumentInfo(m.getChfc_puzu_id(), id,
							UserInfo.getUsername());

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoHousingFundChange";
					if (m.getChfc_addtype().equals("缴存登记")) {
						str[4] = "客服提交申报数据";
					} else if (m.getChfc_addtype().equals("账户接管")) {
						str[4] = "客服提交接管数据";
					}
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("2")) {
			// 状态变更
			CoHousingFundChangeModel m = (CoHousingFundChangeModel) obj[1];
			List<CoHousingFundZbChangeModel> zbList = m.getZbList();
			List<CoHousingFundPwdChangeModel> pwdList = m.getPwdList();

			try {
				row = dal.UpdateState(m);

				if (row > 0) {

					if (m.getChfc_addtype().equals("缴存登记")
							|| m.getChfc_addtype().equals("账户接管")) {
						if (m.getChfc_state().equals(2)
								&& !m.getChfc_puzu_id().equals(0)) {
							// 提交材料
							DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
							docOC.UpsubmitDoc((Grid) obj[2], m.getChfc_id()
									+ "");
						} else if (m.getChfc_state().equals(3)) {
							dal.cohousemod(m);
							dal.Complete(m);

							// 删除暂存的专办员临时数据
							dal.DelZbTem(m.getChfc_id());
							// 新增专办员数据
							for (CoHousingFundZbChangeModel m1 : zbList) {
								m1.setCfzc_state(3);
								m1.setCfzc_laststate(2);

								m1.setCfzc_id(dal.zbadd(m1));
								dal.ZbComplete(m1);
							}

							// 删除暂存的密钥临时数据
							dal.DelPwdTem(m.getChfc_id());
							// 新增密钥数据
							for (CoHousingFundPwdChangeModel m1 : pwdList) {
								m1.setCfpc_state(3);
								m1.setCfpc_laststate(2);

								m1.setCfpc_id(dal.pwdadd(m1));
								dal.PwdComplete(m1);
							}
						}
					} else if (m.getChfc_addtype().equals("信息变更")
							|| m.getChfc_addtype().equals("比例调整")
							|| m.getChfc_addtype().equals("降低比例/缓缴提前终止")) {
						if (m.getChfc_state().equals(2)
								&& !m.getChfc_puzu_id().equals(0)) {
							// 提交材料
							DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
							docOC.UpsubmitDoc((Grid) obj[2], m.getChfc_id()
									+ "");
						} else if (m.getChfc_state().equals(3)) {
							dal.Complete(m);
						}
					} else if (m.getChfc_addtype().equals("降低比例")
							|| m.getChfc_addtype().equals("缓缴")
							|| m.getChfc_addtype().equals("登记注销")) {
						if (m.getChfc_state().equals(2)) {
							// 提交材料
							DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
							docOC.UpsubmitDoc((Grid) obj[2], m.getChfc_id()
									+ "");
						} else if (m.getChfc_state().equals(3)) {
							dal.Complete(m);
						}
					} else if (m.getChfc_addtype().equals("合同终止")) {
						if (m.getChfc_state().equals(2)) {
							// 提交材料
							DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
							docOC.UpsubmitDoc((Grid) obj[2], m.getChfc_id()
									+ "");
						} else if (m.getChfc_state().equals(3)) {
							dal.Complete(m);
							zbList = m.getZbList();
							for (int i = 0; i < zbList.size(); i++) {
								CoHousingFundZbChangeModel m1 = zbList.get(i);
								m1.setCfzc_state(3);
								dal.ZbUpdateState(m1);
								dal.ZbComplete(m1);
							}
						}
					}

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getChfc_id() + "";
					str[3] = "CoHousingFundChange";
					str[4] = new CoHousingFund_ListBll()
							.getStateList(
									" and type='cogjj' and stateid="
											+ m.getChfc_state()).get(0)
							.getOperate();
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("3")) {
			// 退还材料
			CoHousingFundChangeModel m = (CoHousingFundChangeModel) obj[1];

			try {
				row = dal.CoHoDocBack(m);

				if (row > 0) {

					// 提交材料
					DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
					docOC.UpsubmitDoc((Grid) obj[2], m.getChfc_id() + "");

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getChfc_id() + "";
					str[3] = "CoHousingFundChange";
					str[4] = "退还纸质材料";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("4")) {
			// 信息变更
			CoHousingFundChangeModel m = (CoHousingFundChangeModel) obj[1];
			List<CoHousingFundInforChangeModel> cficList = m.getCficList();

			try {
				id = dal.cohouseadd(m);

				if (id > 0) {
					// 循环插入变更项
					for (CoHousingFundInforChangeModel cficM : cficList) {
						cficM.setCfic_chfc_id(id);
						dal.inforchangeadd(cficM);
					}

					if (!m.getChfc_puzu_id().equals(0)) {
						// 提交空材料数据
						DocumentsInfoService docService = new DocumentsInfoImpl();
						docService.createDocumentInfo(m.getChfc_puzu_id(), id,
								UserInfo.getUsername());
					}

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoHousingFundChange";
					str[4] = "提交信息变更申报数据";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("5")) {
			// 比例调整、降低比例、缓缴、降低比例/缓缴提前终止、登记注销、合同终止
			CoHousingFundChangeModel m = (CoHousingFundChangeModel) obj[1];

			try {
				id = dal.cohouseadd(m);

				if (id > 0) {

					if (!m.getChfc_puzu_id().equals(0)) {
						// 提交空材料数据
						DocumentsInfoService docService = new DocumentsInfoImpl();
						docService.createDocumentInfo(m.getChfc_puzu_id(), id,
								UserInfo.getUsername());
					}

					if (m.getCficList() != null && m.getCficList().size() > 0) {
						List<CoHousingFundInforChangeModel> cficList = m
								.getCficList();

						// 循环插入变更项
						for (CoHousingFundInforChangeModel cficM : cficList) {
							cficM.setCfic_chfc_id(id);
							dal.inforchangeadd(cficM);
						}
					}

					if (m.getZbList() != null && m.getZbList().size() > 0) {
						List<CoHousingFundZbChangeModel> zbList = m.getZbList();
						// 插入专办员注销数据
						for (int i = 0; i < zbList.size(); i++) {
							CoHousingFundZbChangeModel m1 = zbList.get(i);
							m1.setCfzc_chfc_id(id);
							dal.zbadd(m1);
						}
					}
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoHousingFundChange";
					str[4] = "提交" + m.getChfc_addtype() + "申报数据";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("客服重新发送数据")) {

			CoHousingFundChangeModel cm = (CoHousingFundChangeModel) obj[1];
			CoHousingFund_OperateDal dal2 = new CoHousingFund_OperateDal();
			// Integer i=dal2.cohousechange(cm, cm.getChfc_id());
			Integer i = dal2.cohousemod(cm);
			if (i > 0) {
				if (cm.getChfc_addtype().equals("缴存登记")) {
					if (cm.getChfc_ispwd().equals(1)) {
						cm.setChfc_puzu_id(39);
					} else {
						cm.setChfc_puzu_id(40);
					}
				} else if (cm.getChfc_addtype().equals("账户接管")) {
					if (cm.getChfc_ispwd().equals(1)) {
						if (cm.getIsmodzb().equals("1")) {
							cm.setChfc_puzu_id(73);
						} else {
							cm.setChfc_puzu_id(74);
						}
					} else {
						cm.setChfc_puzu_id(75);
					}
				}
				DocumentsInfoService docService = new DocumentsInfoImpl();
				docService.createDocumentInfo(cm.getChfc_puzu_id(),
						cm.getChfc_id(), UserInfo.getUsername());

				str[0] = "1";
				str[1] = "提交数据";
				str[2] = cm.getChfc_id().toString();
				str[3] = "CoHousingFundChange";
				str[4] = "客服重新发送单位公积金数据";
			} else {
				str[0] = "2";
				str[1] = "重新发送单位公积金数据出错。";
			}
		} else if (obj[0].equals("更新合同")) {
			CoCompactModel cm = (CoCompactModel) obj[1];
			
			Integer i = updateCompact(cm);
			if (i > 0) {
				str[0] = "1";
				str[1] = "提交数据";
				str[2] = cm.getChfc_id().toString();
				str[3] = "CoHousingFundChange";
				str[4] = "更新合同数据";
			} else {
				str[0] = "2";
				str[1] = "更新合同数据出错。";
			}
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		if (obj[0].equals("退回客服")) {
			CoHousingFundChangeModel cm = (CoHousingFundChangeModel) obj[1];
			CoHousingFund_OperateDal dal = new CoHousingFund_OperateDal();
			Integer i = dal.cohousechange(cm, cm.getChfc_id());
			if (i > 0) {
				message[0] = "1";
				message[1] = "退回";
				message[2] = cm.getChfc_id().toString();
				message[3] = "CoHousingFundChange";
				message[4] = "退回单位公积金数据";
			} else {
				message[0] = "2";
				message[1] = "退回单位公积金数据出错。";
			}
		}
		return message;
	}

	public Integer returnTocommit(CoHousingFundChangeModel cm) {
		Object[] obj = { "退回客服", cm };

		WfOperateService wfs = new WfOperateImpl(new CoHousingFund_OperateBll());
		/*
		String[] str = wfs.ReturnToPrev(obj, cm.getChfc_tapr_id(),
				UserInfo.getUsername(), "");
				*/
		String[] str = wfs.ReturnToN(obj, cm.getChfc_tapr_id(), 1, UserInfo.getUsername());
		if (str[0].equals("1")) {
			return 1;
		} else {
			return 0;
		}

	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return new CoHousingFund_OperateDal().UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
