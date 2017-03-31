package bll.EmCommissionOut.Standard;

import java.util.Set;
import org.zkoss.zul.Listitem;

import dal.EmCommissionOut.Standard.Standard_ListDal;
import dal.EmCommissionOut.Standard.Standard_OperateDal;
import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.CoOfferListModel;
import Model.EmCommissionOutStandardModel;
import Util.UserInfo;

public class Standard_OperateBll implements WfBusinessService {

	// 新增
	public String[] add(EmCommissionOutStandardModel m, String company,
			Set<Listitem> set) {
		String[] str = new String[5];
		Object[] obj = { "1", m, set };
		WfOperateService wfs = new WfOperateImpl(new Standard_OperateBll());
		str = wfs.AddTaskToNext(obj, "委托出标准新增",
				company + "新增委托出标准:" + m.getEcos_name(), 53,
				UserInfo.getUsername(), "", 0, "");
		if (m.getEcos_state() == 2) {
			Object[] obj1 = { "2", str[3] };
			str = wfs.SkipToN(obj1, Integer.parseInt(str[2]), 3,
					"系统", "", 0, "");
		}
		return str;
	}

	// 状态变更
	public String[] UpdateState(EmCommissionOutStandardModel m) {
		String[] str = new String[5];
		WfOperateService wfs = new WfOperateImpl(new Standard_OperateBll());
		if (m.getEcos_state() == 3
				|| (m.getEcos_state() == 2 && m.getEcos_laststate() == 1)) {
			Object[] obj = { "3", m };
			str = wfs.PassToNext(obj, m.getEcos_tapr_id(),
					UserInfo.getUsername(), "", 0, "");
		} else if (m.getEcos_state() == 2 && m.getEcos_laststate() > 2) {
			Object[] obj = { "1", m };
			str = wfs.ReturnToN(obj, m.getEcos_tapr_id(), 1,
					UserInfo.getUsername());
		} else if (m.getEcos_state() == 4) {
			String remark = "";
			if (m.getEcos_laststate() == 1) {
				remark = "服务费大于成本，任务单完结。";
			}
			Object[] obj = { "4", m };
			str = wfs.OverTask(obj, m.getEcos_tapr_id(),
					UserInfo.getUsername(), remark);
		}
		return str;
	}

	// 退回
	public String[] Return(EmCommissionOutStandardModel m) {
		String[] str = new String[5];
		WfOperateService wfs = new WfOperateImpl(new Standard_OperateBll());

		if (m.getEcos_laststate() == 1) {
			Object[] obj = { "2", m };
			str = wfs.ReturnToN(obj, m.getEcos_tapr_id(), 1,
					UserInfo.getUsername());
		} else if (m.getEcos_laststate() == 2) {
			Object[] obj = { "2", m };
			str = wfs.ReturnToPrev(obj, m.getEcos_tapr_id(),
					UserInfo.getUsername(), "");
		}
		return str;
	}

	// 福利产品修改
	public void CoOfferListMod(Integer daid, Set<Listitem> set) {
		Standard_OperateDal dal = new Standard_OperateDal();
		// 删除旧数据
		dal.DeleteCoOfferList(daid);

		// 加新数据
		for (Listitem lt : set) {
			EmCommissionOutStandardModel m = new EmCommissionOutStandardModel();
			m.setEcop_ecos_id(daid);
			m.setEcop_coli_id(((CoOfferListModel) lt.getValue()).getColi_id());
			new Standard_OperateDal().CoOfferListReladd(m);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		Integer id = 0;
		Integer row = 0;
		Standard_OperateDal dal = new Standard_OperateDal();
		if (obj[0].equals("1")) {
			id = dal.add((EmCommissionOutStandardModel) obj[1]);

			Set<Listitem> set = (Set<Listitem>) obj[2];
			for (Listitem lt : set) {
				EmCommissionOutStandardModel m = new EmCommissionOutStandardModel();
				m.setEcop_ecos_id(id);
				m.setEcop_coli_id(((CoOfferListModel) lt.getValue())
						.getColi_id());
				dal.CoOfferListReladd(m);
			}

			if (id > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = id + "";
				str[3] = "EmCommissionOutStandard";
				str[4] = "客服提交申请";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("2")) {
			str[0] = "1";
			str[1] = "提交成功!";
			str[2] = obj[1] + "";
			str[3] = "EmCommissionOutStandard";
			str[4] = "有默认机构，自动跳过该步骤";
		} else if (obj[0].equals("3")) {
			EmCommissionOutStandardModel m = (EmCommissionOutStandardModel) obj[1];

			if (m.getEcos_state() == 2) {
				row = dal.DefaultCoAgency(m);
				row += dal.UpdateState(m);
				if (row == 3) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getEcos_id() + "";
					str[3] = "EmCommissionOutStandard";
					str[4] = "设置默认机构";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} else if (m.getEcos_state() == 3 || m.getEcos_state() == 4) {
				row = new Standard_OperateDal().mod(m);
				row += new Standard_OperateDal().UpdateState(m);

				if (row == 2) {
					EmCommissionOutStandardModel m1 = new Standard_ListDal()
							.getStateInfo(m.getEcos_id(), m.getEcos_state() - 1);

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getEcos_id() + "";
					str[3] = "EmCommissionOutStandard";
					str[4] = m1.getOperate();
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			}
		} else if (obj[0].equals("4")) {
			EmCommissionOutStandardModel m = (EmCommissionOutStandardModel) obj[1];

			row = new Standard_OperateDal().mod(m);
			row += new Standard_OperateDal().UpdateState(m);

			if (row == 2) {
				EmCommissionOutStandardModel m1 = new Standard_ListDal()
						.getStateInfo(m.getEcos_id(), 2);

				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = m.getEcos_id() + "";
				str[3] = "EmCommissionOutStandard";
				str[4] = m1.getOperate();
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
		Integer row = 0;
		EmCommissionOutStandardModel m = (EmCommissionOutStandardModel) obj[1];

		if (obj[0].equals("1")) {
			row = new Standard_OperateDal().mod(m);
			row += new Standard_OperateDal().UpdateState(m);
			if (row == 2) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = m.getEcos_id() + "";
				str[3] = "EmCommissionOutStandard";
				str[4] = "变更委托地区，重新设置默认机构";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("2")) {
			row = new Standard_OperateDal().UpdateState(m);
			if (row == 1) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = m.getEcos_id() + "";
				str[3] = "EmCommissionOutStandard";
				str[4] = "退回";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
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
		return new Standard_OperateDal().UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
}
