package bll.CoReg;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Grid;

import impl.DocumentsInfoImpl;
import impl.WorkflowCore.WfOperateImpl;
import service.DocumentsInfoService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import dal.CoReg.CoReg_Dal;
import dal.CoReg.CoReg_OperateDal;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoBaseModel;
import Model.CoOnlineRegisterInfoModel;
import Model.DocumentsInfoModel;
import Model.ResponsbilityBookModel;
import Util.UserInfo;

public class CoReg_OperateBll implements WfBusinessService {

	// 公司就业立户
	public String[] CoRegAdd(CoOnlineRegisterInfoModel m, String company) {

		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "公司就业立户", company + "就业立户申报", 18,
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 公司就业立户及签订计生责任书
	public String[] CoRegAdd1(CoOnlineRegisterInfoModel m, String company) {
		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "公司就业立户及签订计生责任书", company
				+ "公司就业立户及签订计生责任书", 23, UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 公司就业立户接管
	public String[] CoRegTackOver(CoOnlineRegisterInfoModel m) {

		Object[] obj = { "1", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "公司就业接管", m.getCoba_shortname()
				+ "公司就业接管", 112, UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 公司就业立户接管
	public String[] CoRegTackOver_cyj(CoOnlineRegisterInfoModel m,
			CoBaseModel model) {
		Object[] obj = { "6", m, model };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "公司就业接管", m.getCoba_shortname()
				+ "公司就业接管", 112, UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 修改信息
	public Integer CoRegMod(CoOnlineRegisterInfoModel m) {
		Integer row = 0;
		row = new CoReg_OperateDal().CoRegMod(m);
		if (row == 1) {
			if (m.getCori_state().equals("10")) {
				row = new CoReg_OperateDal().UpdateState(m.getCori_id(), 11);
			}
		} else {
			row = 0;
		}
		return row;
	}

	// 重新提交
	public String[] CoRegReSubmit(CoOnlineRegisterInfoModel m) {
		Object[] obj = { "3", m };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getCori_tapr_id(),
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 状态变更
	public String[] CoRegUpdateState(CoOnlineRegisterInfoModel m, Grid gd) {
		Object[] obj = { "2", m, gd };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getCori_tapr_id(),
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 退回资料
	public String[] CoRegDocBack(CoOnlineRegisterInfoModel m, Grid gd) {
		Object[] obj = { "5", m, gd };
		Integer cid = 0;
		if (m.getCid() != null) {
			cid = m.getCid();
		}
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getCori_tapr_id(),
				UserInfo.getUsername(), "", cid, "");
		return str;
	}

	// 终止任务单
	public String[] CoRegEnd(CoOnlineRegisterInfoModel m, Integer tarp_id) {
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.StopTask(obj, tarp_id, UserInfo.getUsername(), "");
		return str;
	}

	//
	public String[] CoRegreEnd(CoOnlineRegisterInfoModel m, Integer tarp_id) {
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.PassToNext(obj, tarp_id, UserInfo.getUsername(), "",m.getCid(),"");
		return str;
	}

	// 跳过补充责任书步骤
	public String[] CoRegSkipToNext(CoOnlineRegisterInfoModel m, Integer tarp_id) {
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		String[] str = wfs.SkipToNext(obj, tarp_id, UserInfo.getUsername(), "",
				m.getCid(), "");
		return str;
	}

	// 材料退回
	public String[] DocBack(int cori_id, Grid gd) {
		String[] str = new String[5];
		if (new CoReg_OperateDal().DocBack(cori_id) == 1) {
			try {
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				str = docOC.UpsubmitDoc(gd, cori_id + "");
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
			}
		}
		return str;
	}

	// 退回上一步
	public String[] back(Object... objs) {
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		return wfs.ReturnToPrev(objs,
				((CoOnlineRegisterInfoModel) objs[0]).getCori_tapr_id(),
				UserInfo.getUsername(),
				((CoOnlineRegisterInfoModel) objs[0]).getCori_backreason());
	}

	// 退回到指定步骤
	public String[] backToN(Object... objs) {
		WfOperateService wfs = new WfOperateImpl(new CoReg_OperateBll());
		return wfs.ReturnToN(objs,
				((CoOnlineRegisterInfoModel) objs[0]).getCori_tapr_id(),
				Integer.parseInt(objs[1].toString()), UserInfo.getUsername());
	}

	// 新增员工就业登记材料空数据
	public void EmRegDocAdd(Integer cori_id, String addname) {
		new CoReg_OperateDal().EmRegDocAdd(cori_id, addname);
	}

	// 修改员工就业登记材料状态
	public Integer EmRegDocUpdateState(DocumentsInfoModel m) {
		return new CoReg_OperateDal().EmRegDocUpdateState(m);
	}

	// 计划生育责任书新增
	public Integer CoBookAdd(Integer cid, String booktype) {
		return new CoReg_OperateDal().CoBookAdd(cid, booktype);
	}

	// 更新计划生育责任书信息
	public Integer updateCoRenInfo(String cori_key, Integer cori_id) {
		return new CoReg_OperateDal().updateCoRenInfo(cori_key, cori_id);
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		int id = 0;
		int row = 0;
		if (obj[0].equals("1")) {
			// 新增
			CoOnlineRegisterInfoModel m = (CoOnlineRegisterInfoModel) obj[1];
			id = new CoReg_OperateDal().CoRegAdd(m);
			try {
				if (id > 0) {
					DocumentsInfoService docService = new DocumentsInfoImpl();
					int puzu_id = 0;
					if (m.getCori_if_responsebook() == 1
							|| (m.getCori_if_responsebook() == 0 && m
									.getCori_if_sign_responsebook() == 0)) {
						puzu_id = 13;
					} else if (m.getCori_if_responsebook() == 0
							&& m.getCori_if_sign_responsebook() == 1) {
						puzu_id = 14;
					}

					// 新增公司立户材料空数据
					docService.createDocumentInfo(puzu_id, id,
							UserInfo.getUsername());

					// 新增员工就业登记材料空数据
					EmRegDocAdd(id, UserInfo.getUsername());

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoOnlineRegisterInfo";
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
			CoOnlineRegisterInfoModel m = (CoOnlineRegisterInfoModel) obj[1];
			List<CoOnlineRegisterInfoModel> list = new ArrayList<>();
			list = new CoReg_ListBll()
					.getstateList(" and typename='后道状态' and stateid="
							+ Integer.parseInt(m.getCori_state()));
			//System.out.println("m.getCori_state()="+m.getCori_state());
			row = new CoReg_OperateDal().UpdateState(m);
			try {
				if (row == 2) {
					if (m.getCori_state().equals("2")) {
						row = new CoReg_OperateDal().NeedDoc(m);
						if (row == 1) {
							str[0] = "1";
							str[1] = "提交成功!";
							str[2] = m.getCori_id() + "";
							str[3] = "CoOnlineRegisterInfo";
							str[4] = list.get(0).getOperate();
						} else {
							str[0] = "0";
							str[1] = "人事反馈材料失败!";
						}
					} else if (m.getCori_state().equals("5")
							|| m.getCori_state().equals("6")) {
						String[] str1 = docOC.UpsubmitDoc((Grid) obj[2],
								m.getCori_id() + "");
						if (str1[0].equals("1")) {
							str[0] = "1";
							str[1] = "提交成功!";
							str[2] = m.getCori_id() + "";
							str[3] = "CoOnlineRegisterInfo";
							str[4] = list.get(0).getOperate();
						} else {
							str[0] = "0";
							str[1] = "材料签收失败!";
						}
					} else if (m.getCori_state().equals("7")) {
						row = new CoReg_OperateDal().CoRegMod(m);
						if (row == 1) {
							str[0] = "1";
							str[1] = "提交成功!";
							str[2] = m.getCori_id() + "";
							str[3] = "CoOnlineRegisterInfo";
							str[4] = list.get(0).getOperate();
						} else {
							str[0] = "0";
							str[1] = "补充信息失败!";
						}
					} else {
						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = m.getCori_id() + "";
						str[3] = "CoOnlineRegisterInfo";
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

			row = new CoReg_OperateDal()
					.CoRegMod((CoOnlineRegisterInfoModel) obj[1]);
			new CoReg_OperateDal()
					.UpdateState((CoOnlineRegisterInfoModel) obj[1]);
			try {
				if (row > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = ((CoOnlineRegisterInfoModel) obj[1]).getCori_id()
							+ "";
					str[3] = "CoOnlineRegisterInfo";
					str[4] = "公司就业立户申报重新提交";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "重新提交出错!";
			}
		} else if (obj[0].equals("4")) {// 接管
			// 接管
			CoOnlineRegisterInfoModel m = (CoOnlineRegisterInfoModel) obj[1];
			id = new CoReg_OperateDal().CoRegAdd(m);
			try {
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoOnlineRegisterInfo";
					str[4] = "客服提交申报";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
			}
		} else if (obj[0].equals("5")) {// 退回资料
			CoOnlineRegisterInfoModel m = (CoOnlineRegisterInfoModel) obj[1];
			Grid gd = (Grid) obj[2];
			String[] strs = DocBack(m.getCori_id(), gd);
			try {
				if (strs[0] == "1") {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "CoOnlineRegisterInfo";
					str[4] = "客服提交申报";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
			}
		}
		/************* 就业登记接管——陈耀家 ***************************/
		else if (obj[0].equals("6")) {
			// 接管
			CoBaseModel cm = (CoBaseModel) obj[2];
			Integer k = CoBaseRegUpdate(cm);
			if (k > 0) {
				CoOnlineRegisterInfoModel m = (CoOnlineRegisterInfoModel) obj[1];
				id = new CoReg_OperateDal().CoRegAdd(m);
				try {
					if (id > 0) {
						DocumentsInfoService docService = new DocumentsInfoImpl();
						int puzu_id = 0;
						if (m.getCori_if_responsebook() == 1
								|| (m.getCori_if_responsebook() == 0 && m
										.getCori_if_sign_responsebook() == 0)) {
							puzu_id = 13;
						} else if (m.getCori_if_responsebook() == 0
								&& m.getCori_if_sign_responsebook() == 1) {
							puzu_id = 14;
						}

						// 新增公司立户材料空数据
						docService.createDocumentInfo(puzu_id, id,
								UserInfo.getUsername());

						// 新增员工就业登记材料空数据
						EmRegDocAdd(id, UserInfo.getUsername());

						str[0] = "1";
						str[1] = "提交成功!";
						str[2] = id + "";
						str[3] = "CoOnlineRegisterInfo";
						str[4] = "客服提交申报";
					} else {
						str[0] = "0";
						str[1] = "提交失败!";
					}
				} catch (Exception e) {
					str[0] = "0";
					str[1] = "提交出错!";
				}
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		}
		return str;
	}

	// 更新计划生育责任书信息
	public Integer updateresponebook(String str, Integer rsbk_cori_id) {
		return new CoReg_OperateDal().updateresponebook(str, rsbk_cori_id);
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] str = new String[5];
		try {
			Integer row = new CoReg_OperateDal()
					.Back((CoOnlineRegisterInfoModel) obj[0]);
			if (row == 1) {
				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = ((CoOnlineRegisterInfoModel) obj[0]).getCori_id() + "";
				str[3] = "CoOnlineRegisterInfo";
				str[4] = "退回";
			} else {
				str[0] = "0";
				str[1] = "退回失败!";
			}
		} catch (Exception e) {
			str[0] = "0";
			str[1] = "退回出错!";
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		// TODO Auto-generated method stub
		CoOnlineRegisterInfoModel m = (CoOnlineRegisterInfoModel) obj[1];
		String[] str = new String[5];
		str[0] = "1";
		str[1] = "提交成功!";
		str[2] = m.getCori_id() + "";
		str[3] = "CoOnlineRegisterInfo";
		str[4] = "人事办理完成";
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		CoOnlineRegisterInfoModel m = (CoOnlineRegisterInfoModel) obj[1];
		String[] str = new String[5];
		str[0] = "1";
		str[1] = "提交成功!";
		str[2] = m.getCori_id() + "";
		str[3] = "CoOnlineRegisterInfo";
		str[4] = "人事办理完成";
		return str;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return new CoReg_OperateDal().UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 就业登记接管修改注册信息
	public Integer CoBaseRegUpdate(CoBaseModel m) {
		CoReg_Dal dal = new CoReg_Dal();
		return dal.CoBaseRegUpdate(m);
	}
}
