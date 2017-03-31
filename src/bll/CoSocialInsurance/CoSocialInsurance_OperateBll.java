package bll.CoSocialInsurance;

import org.zkoss.zul.Grid;

import dal.CoSocialInsurance.CoSocialInsurance_OperateDal;
import impl.DocumentsInfoImpl;
import impl.WorkflowCore.WfOperateImpl;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoShebaoChangeModel;
import Model.CoShebaoModel;
import Util.UserInfo;
import service.DocumentsInfoService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class CoSocialInsurance_OperateBll implements WfBusinessService {

	// 缴存登记/账户接管
	public String[] add(CoShebaoChangeModel m, String company) {
		Object[] obj = { "1", m, company };
		Integer taskid = 0;
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		if (m.getCsbc_addtype().equals("缴存登记")) {
			taskid = 58;
		} else if (m.getCsbc_addtype().equals("账户接管")) {
			taskid = 59;
		}
		String[] str = wfs.AddTaskToNext(obj, "社保独立账户" + m.getCsbc_addtype(),
				company + "社保" + m.getCsbc_addtype(), taskid,
				UserInfo.getUsername(), "", m.getCid(), "");
		return str;
	}

	// 信息变更
	public String[] changeadd(CoShebaoChangeModel m, String company) {
		Object[] obj = { "3", m, company };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "社保独立账户" + m.getCsbc_addtype(),
				company + "社保" + m.getCsbc_addtype(), 60,
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

	// 账户注销
	public String[] cancellationadd(CoShebaoChangeModel m, String company) {
		Object[] obj = { "4", m, company };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "社保独立账户" + m.getCsbc_addtype(),
				company + "社保" + m.getCsbc_addtype(), 61,
				UserInfo.getUsername(), "", m.getCid(), "");
		return str;
	}

	// 管理终止
	public String[] terminationadd(CoShebaoChangeModel m, String company) {
		Object[] obj = { "5", m, company };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "社保独立账户" + m.getCsbc_addtype(),
				company + "社保" + m.getCsbc_addtype(), 62,
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

	// 状态变更
	public String[] UpdateState(CoShebaoChangeModel m, Grid gd) {
		Object[] obj = { "2", m, gd };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getCsbc_tapr_id(),
				UserInfo.getUsername(), "", m.getCid(), "");
		return str;
	}

	// 更新合同
	public String[] UpdateCompact(CoShebaoChangeModel m) {
		Object[] obj = { "7", m };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getCsbc_tapr_id(),
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

	// 重新提交
	public String[] resubmit(CoShebaoChangeModel m) {
		Object[] obj = { "6", m };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getCsbc_tapr_id(),
				UserInfo.getUsername(), "", m.getCid(), "");
		return str;
	}

	// 退还材料
	public String[] DocBack(CoShebaoChangeModel m, Grid gd) {
		Object[] obj = { "8", m, gd };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getCsbc_tapr_id(),
				UserInfo.getUsername(), "", m.getCid(), "");
		return str;
	}

	// 插入日志
	public Integer InsertLog(Integer daid, String statename, String content,
			String type, String remark) {
		CoShebaoChangeModel m = new CoShebaoChangeModel();
		m.setCsbc_id(daid);
		m.setPbsr_statename(statename);
		m.setPbsr_content(content);
		m.setPbsr_type(type);
		m.setPbsr_remark(remark);
		m.setPbsr_addname(UserInfo.getUsername());
		return new CoSocialInsurance_OperateDal().InsertLog(m);
	}

	// 退回
	public String[] back(CoShebaoChangeModel m) {
		Object[] obj = { m };
		WfOperateService wfs = new WfOperateImpl(
				new CoSocialInsurance_OperateBll());
		String[] str = wfs.ReturnToPrev(obj, m.getCsbc_tapr_id(),
				UserInfo.getUsername(), m.getPbsr_remark());
		return str;
	}

	// 修改比例(失业、工商)
	public boolean UpdatePercent(CoShebaoModel m) {
		Integer row = 0;
		CoSocialInsurance_OperateDal dal = new CoSocialInsurance_OperateDal();
		row = dal.UpdatePer(m);
		row += InsertLog(m.getCosb_id(), "", m.getStatename(), "csoi",
				m.getCosb_remark());
		return row > 1 ? true : false;
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		Integer id = 0;
		Integer row = 0;
		CoSocialInsurance_OperateDal dal = new CoSocialInsurance_OperateDal();
		if (obj[0].equals("1")) {
			// 缴存登记/账户接管
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];

			try {
				id = dal.add(m);

				if (id > 0) {

					// 缴存登记需提交纸质材料
					if (m.getCsbc_addtype().equals("缴存登记")) {
						// 提交空材料数据
						DocumentsInfoService docService = new DocumentsInfoImpl();
						docService.createDocumentInfo(36, id,
								UserInfo.getUsername());
					}

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoShebaoChange";
					str[4] = "客服提交申报数据";
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
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

			try {
				row = dal.updatestate(m);

				if (m.getCsbc_addtype().equals("缴存登记")) {
					if (m.getCsbc_state() == 2) {
						dal.UpdateInfo(m);
					}
					else if (m.getCsbc_state() == 3) {
						docOC.UpsubmitDoc((Grid) obj[2], m.getCsbc_id() + "");
					} else if (m.getCsbc_state() == 4) {
						dal.addcomplete(m);
					}
					if (row >0 ) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoiadd' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("账户接管")) {
					if (m.getCsbc_state() == 2) {
						dal.mod(m);
						dal.addcomplete(m);
					}
					if (row >0) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoiadd1' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("信息变更")) {
					if (m.getCsbc_state() == 3) {
						docOC.UpsubmitDoc((Grid) obj[2], m.getCsbc_id() + "");
					} else if (m.getCsbc_state() == 4) {
						dal.changecomplete(m);
					}
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoichange' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("账户注销")) {
					if (m.getCsbc_state() == 3) {
						docOC.UpsubmitDoc((Grid) obj[2], m.getCsbc_id() + "");
					} else if (m.getCsbc_state() == 4) {
						dal.cancellationcomplete(m);
					}
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoican' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("管理终止")) {
					if (m.getCsbc_state() == 3) {
						dal.cancellationcomplete(m);
					}
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoican' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				}

			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("3")) {
			// 信息变更
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];

			try {
				id = dal.changeadd(m);

				if (id > 0) {

					// 提交空材料数据
					DocumentsInfoService docService = new DocumentsInfoImpl();
					docService.createDocumentInfo(37, id,
							UserInfo.getUsername());

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoShebaoChange";
					str[4] = new CoSocialInsurance_ListBll()
							.getStateList(
									" and type='csoichange' and stateid="
											+ m.getCsbc_state()).get(0)
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
		} else if (obj[0].equals("4")) {
			// 账户注销
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];

			try {
				id = dal.cancellationadd(m);

				if (id > 0) {

					// 提交空材料数据
					DocumentsInfoService docService = new DocumentsInfoImpl();
					docService.createDocumentInfo(38, id,
							UserInfo.getUsername());

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoShebaoChange";
					str[4] = new CoSocialInsurance_ListBll()
							.getStateList(
									" and type='csoican' and stateid="
											+ m.getCsbc_state()).get(0)
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
		} else if (obj[0].equals("5")) {
			// 管理终止
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];

			try {
				id = dal.cancellationadd(m);

				if (id > 0) {

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoShebaoChange";
					str[4] = new CoSocialInsurance_ListBll()
							.getStateList(
									" and type='csoiter' and stateid="
											+ m.getCsbc_state()).get(0)
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
		} else if (obj[0].equals("6")) {
			// 重新提交
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];

			try {
				row = dal.updatestate(m);

				if (m.getCsbc_addtype().equals("缴存登记")) {
					dal.mod(m);
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = "重新提交申报";
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("账户接管")) {
					dal.mod(m);
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoiadd1' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("信息变更")) {
					dal.mod(m);
					dal.changereadd(m);
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoichange' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("账户注销")) {
					dal.mod(m);
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoican' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} else if (m.getCsbc_addtype().equals("管理终止")) {
					if (m.getCsbc_state() == 3) {
						dal.cancellationcomplete(m);
					}
					if (row == 1) {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCsbc_id() + "";
						str[3] = "CoShebaoChange";
						str[4] = new CoSocialInsurance_ListBll()
								.getStateList(
										" and type='csoican' and stateid="
												+ m.getCsbc_state()).get(0)
								.getOperate();
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				}

			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("7")) {
			// 更新合同
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];

			try {
				row = dal.UpdateCompact(m);

				if (row > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getCsbc_id() + "";
					str[3] = "CoShebaoChange";
					str[4] = "更新了合同号:" + m.getCoco_compactid() + " 的社保信息";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}

		} else if (obj[0].equals("8")) {
			// 退还材料
			CoShebaoChangeModel m = (CoShebaoChangeModel) obj[1];
			Grid gd = (Grid) obj[2];
			DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

			if (dal.DocBack(m.getCsbc_id())) {

				try {
					docOC.UpsubmitDoc(gd, m.getCsbc_id() + "");

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getCsbc_id() + "";
					str[3] = "CoShebaoChange";
					str[4] = "退还材料";
				} catch (Exception e) {
					str[0] = "0";
					str[1] = "提交出错!";
					e.printStackTrace();
				}
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] str = new String[5];
		int row = 0;
		CoShebaoChangeModel m = (CoShebaoChangeModel) obj[0];
		row = new CoSocialInsurance_OperateDal().back(m);
		try {
			if (row > 0) {

				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = m.getCsbc_id() + "";
				str[3] = "CoShebaoChange";
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
		return new CoSocialInsurance_OperateDal().UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
