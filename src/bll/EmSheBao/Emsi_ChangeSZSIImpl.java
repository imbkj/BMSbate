package bll.EmSheBao;

import Model.EmShebaoChangeSZSIModel;
import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsi_ChangeSZSIImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		EmShebaoChangeSZSIModel m = (EmShebaoChangeSZSIModel) obj[1];
		int i = Integer.parseInt(obj[0].toString());
		Emsi_OperateDal dal = new Emsi_OperateDal();
		try {
			if (i == 0) {
				int changeid = dal.changeSZSI(m);
				if (changeid > 0) {
					message[0] = "1";
					message[1] = "特殊变更操作成功。";
					message[2] = String.valueOf(changeid);
					message[3] = "EmShebaoChangeSZSI";
					message[4] = m.getEscs_name() + m.getEscs_change();
				} else if (changeid == -1) {
					message[0] = "0";
					message[1] = "该员工已有" + m.getEscs_change() + "的数据。";
				} else {
					message[0] = "0";
					message[1] = "特殊变更操作失败";
				}
			} else if (i == 1) {
				if (dal.upChangeSZSIDeclare(8, m.getEscs_id())) {
					message[0] = "1";
					message[1] = "特殊变更操作成功。";
					message[2] = String.valueOf(m.getEscs_id());
					message[3] = "EmShebaoChangeSZSI";
					message[4] = m.getEscs_name() + m.getEscs_change()
							+ "，材料收集。";
				} else {
					message[0] = "0";
					message[1] = "特殊变更操作失败";
				}
			} else if (i == 2) {
				if (dal.upChangeSZSIDeclare(m.getEscs_ifdeclare(),
						m.getEscs_id())) {
					message[0] = "1";
					message[1] = "特殊变更操作成功。";
					message[2] = String.valueOf(m.getEscs_id());
					message[3] = "EmShebaoChangeSZSI";
					message[4] = m.getEscs_name() + m.getEscs_change()
							+ "，材料收集。";
				} else {
					message[0] = "0";
					message[1] = "特殊变更操作失败";
				}
			} else if (i == 3) {
				if (dal.upChangeSZSIDeclareRS(m.getEscs_ifdeclare(),
						m.getEscs_id(), m.getOwnmonth())) {
					message[0] = "1";
					message[1] = "特殊变更重新操作成功。";
					message[2] = String.valueOf(m.getEscs_id());
					message[3] = "EmShebaoChangeSZSI";
					message[4] = m.getEscs_name() + m.getEscs_change()
							+ "，材料收集。";
				} else {
					message[0] = "0";
					message[1] = "特殊变更重新操作失败";
				}
			} else if (i == 4) {
				if ("变更户籍".equals(m.getEscs_change())) {
					message[0] = "1";
					message[1] = "特殊变更重新操作跳过成功。";
					message[2] = String.valueOf(m.getEscs_id());
					message[3] = "EmShebaoChangeSZSI";
					message[4] = "跳过";
				} else {
					message[0] = "0";
					message[1] = "特殊变更重新操作跳过失败";
				}
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "特殊变更操作出错";
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] SkipOperate(Object[] obj) {
		/*int i = Integer.parseInt(obj[0].toString());
		String[] message = new String[5];
		EmShebaoChangeSZSIModel m = (EmShebaoChangeSZSIModel) obj[1];
		if (i == 4) {
			if ("变更户籍".equals(m.getEscs_change())) {
				message[0] = "1";
				message[1] = "特殊变更重新操作跳过成功。";
				message[2] = String.valueOf(m.getEscs_id());
				message[3] = "EmShebaoChangeSZSI";
				message[4] = "跳过";
			} else {
				message[0] = "0";
				message[1] = "特殊变更重新操作跳过失败";
			}
		}
		return message;*/
		return null;
	}

	@Override
	public String[] StopOperate(Object[] obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		Emsi_OperateDal dal = new Emsi_OperateDal();
		return dal.upEmShebaoChangeSZSITaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
