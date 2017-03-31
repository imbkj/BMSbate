package bll.EmResidencePermit;

import dal.EmResidencePermit.Emrp_OperateDal;
import impl.WorkflowCore.WfOperateImpl;
import Model.EmResidencePermitChangeModel;
import Util.UserInfo;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

public class Emrp_ChangeBll implements WfBusinessService {

	// 居住证转换新增
	public String[] changeadd(EmResidencePermitChangeModel m, String name) {
		String[] str = new String[5];
		Object[] obj = { "1", m };
		WfOperateService wfs = new WfOperateImpl(new Emrp_ChangeBll());
		str = wfs.AddTaskToNext(obj, "员工居住证转换", name + "员工居住证转换", 52,
				UserInfo.getUsername(), "", 0, "");
		if (str[0] == "1") {
			// 新增转换时跳过重新提交步骤
			m.setEpcr_id(Integer.parseInt(str[3]));
			wfs.SkipToNext(obj, Integer.parseInt(str[2]),
					UserInfo.getUsername(), "", m.getCid(), "");
		}
		return str;
	}

	// 居住证转换状态变更
	public String[] updatestate(EmResidencePermitChangeModel m) {
		String[] str = new String[5];
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(new Emrp_ChangeBll());
		str = wfs.PassToNext(obj, m.getErpc_tapr_id(), UserInfo.getUsername(),
				"", 0, "");
		return str;
	}

	// 居住证转换退回
	public String[] EmrpBack(EmResidencePermitChangeModel m) {
		String[] str = new String[5];
		Object[] obj = { "2", m };
		WfOperateService wfs = new WfOperateImpl(new Emrp_ChangeBll());
		str = wfs.ReturnToPrev(obj, m.getErpc_tapr_id(), "系统","");
		return str;
	}

	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		Integer id = 0;
		Integer row = 0;
		if (obj[0].equals("1")) {
			id = new Emrp_OperateDal()
					.changeadd((EmResidencePermitChangeModel) obj[1]);

			if (id > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = id + "";
				str[3] = "EmResidencePermitChange";
				str[4] = "客服添加转换申报";
			} else {
				str[0] = "0";
				str[1] = "提交失败!";
			}
		} else if (obj[0].equals("2")) {
			EmResidencePermitChangeModel m = new EmResidencePermitChangeModel();
			m = (EmResidencePermitChangeModel) obj[1];
			row = new Emrp_OperateDal().changeUpdateState(m);

			if (row > 0) {
				str[0] = "1";
				str[1] = "提交成功!";
				str[2] = m.getErpc_id() + "";
				str[3] = "EmResidencePermitChange";
				str[4] = "客服添加转换申报";
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
		Emrp_OperateDal dal=new Emrp_OperateDal();
		EmResidencePermitChangeModel m = new EmResidencePermitChangeModel();
		m = (EmResidencePermitChangeModel) obj[1];
		row =dal.changeUpdateState(m);
		if (row > 0) {
			str[0] = "1";
			str[1] = "提交成功!";
			str[2] = m.getErpc_id() + "";
			str[3] = "EmResidencePermitChange";
			str[4] = "退回转换数据";
		} else {
			str[0] = "0";
			str[1] = "提交失败!";
		}
		return str;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		String[] str = new String[5];
		Integer row = 1;
		EmResidencePermitChangeModel m = new EmResidencePermitChangeModel();
		m = (EmResidencePermitChangeModel) obj[1];
		if (row > 0) {
			str[0] = "1";
			str[1] = "提交成功!";
			str[2] = m.getErpc_id() + "";
			str[3] = "EmResidencePermitChange";
			str[4] = "跳过";
		} else {
			str[0] = "0";
			str[1] = "提交失败!";
		}
		return str;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return new Emrp_OperateDal().UpdateTaprid1(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
