package bll.EmSheBao;

import Model.EmShebaoChangeForeignerModel;
import dal.EmSheBao.Emsc_DeclareOperateDal;
import dal.EmSheBao.Emsi_CheckOperateDal;
import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsc_ForeignerDeclareImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		if ("0".equals(obj[0].toString())) {
			message = ForeignerDeclareUpState(obj);
		} else if ("1".equals(obj[0].toString())) {
			Emsi_CheckOperateDal dal = new Emsi_CheckOperateDal();
			int i = dal.upForeignerDeclare(Integer.parseInt(obj[1].toString()),
					0, obj[2].toString(), obj[3].toString());
			if (i > 0) {
				message[0] = "1";
				message[1] = "操作成功。";
				message[2] = obj[1].toString();
				message[3] = "EmShebaoChangeForeigner";
				message[4] = "服务中心核收";
			} else {
				message[0] = "0";
				message[1] = "数据处理，失败。";
			}

		}
		return message;
	}

	// 外籍人社保变更修改状态
	private String[] ForeignerDeclareUpState(Object[] obj) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		EmShebaoChangeForeignerModel m = (EmShebaoChangeForeignerModel) obj[1];
		String username = obj[2].toString();
		int i = dal.ForeignerDeclareUpState(m, username);
		if (i == 1) {
			message[0] = "1";
			message[1] = "外籍人社保变更申报成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeForeigner";
			message[4] = checkDeclare(m.getEmsc_ifdeclare());
		} else {
			message[0] = "0";
			message[1] = "外籍人社保变更申报失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoChangeForeigner";
			message[4] = "外籍人社保变更申报";
		}
		return message;
	}

	// 判断修改的内容
	private String checkDeclare(String declare) {
		String str = "";
		switch (declare) {
		case "0":
			str = "未申报";
			break;
		case "1":
			str = "已申报";
			break;
		case "2":
			str = "申报中";
			break;
		case "3":
			str = "退回";
			break;
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		if ("0".equals(obj[0].toString())) {
			message = ForeignerDeclareUpState(obj);
		} else if ("1".equals(obj[0].toString())) {
			Emsi_CheckOperateDal dal = new Emsi_CheckOperateDal();
			int i = dal.upForeignerDeclare(Integer.parseInt(obj[1].toString()),
					3, obj[2].toString(), obj[3].toString());
			if (i > 0) {
				message[0] = "1";
				message[1] = "操作成功。";
				message[2] = obj[1].toString();
				message[3] = "EmShebaoChangeForeigner";
				message[4] = "服务中心退回";
			} else {
				message[0] = "0";
				message[1] = "退回，数据处理时，失败。";
			}
		}
		return message;
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
		Emsi_OperateDal dal = new Emsi_OperateDal();
		return dal.upEmShebaoChangeForeignerTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
