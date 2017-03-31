package bll.EmSheBao;

import Model.EmShebaoBJModel;
import dal.EmSheBao.Emsc_DeclareOperateDal;
import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class Emsc_BjDeclareImpl implements WfBusinessService {

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		if ("0".equals(obj[0].toString())) {
			message = BjDeclare(obj);
		} else if ("1".equals(obj[0].toString())) {
			message = BjDeclareUpState(obj);
		} else if ("2".equals(obj[0].toString())) {
			message = BjDeclareUpload(obj);
		} else if ("7".equals(obj[0].toString())) {
			message = BjDeclareDownload(obj);
		}
		return message;
	}

	// 补缴申报
	private String[] BjDeclare(Object[] obj) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		EmShebaoBJModel m = (EmShebaoBJModel) obj[1];
		String username = obj[2].toString();
		int i = dal.BjDeclare(m, username);
		switch (i) {
		case 0:
			message[0] = "0";
			message[1] = "社保补缴申报失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "社保补缴申报";
			break;
		case 2:
			message[0] = "1";
			message[1] = "社保补缴申报成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "已打单";
			break;
		case 6:
			message[0] = "1";
			message[1] = "社保补缴申报成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "已交单";
			break;
		case 1:
			message[0] = "1";
			message[1] = "社保补缴申报成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "已申报";
			break;
		}
		return message;
	}

	// 补缴申报修改状态
	private String[] BjDeclareUpState(Object[] obj) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		EmShebaoBJModel m = (EmShebaoBJModel) obj[1];
		String username = obj[2].toString();
		int i = dal.BjDeclareUpState(m, username);
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保补缴操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = m.getEmsb_ifdeclarestr();
		} else {
			message[0] = "0";
			message[1] = "社保补缴操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "社保补缴操作";
		}
		return message;
	}

	// 补缴申报修改状态
	private String[] BjDeclareUpload(Object[] obj) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		EmShebaoBJModel m = (EmShebaoBJModel) obj[1];
		int i = dal.BjDeclareUpload(m);
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保补缴操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = m.getEmsb_ifdeclarestr();
		} else {
			message[0] = "0";
			message[1] = "社保补缴操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "社保补缴操作";
		}
		return message;
	}

	// 社保补缴客服下载PDF文件
	private String[] BjDeclareDownload(Object[] obj) {
		String[] message = new String[5];
		Emsc_DeclareOperateDal dal = new Emsc_DeclareOperateDal();
		EmShebaoBJModel m = (EmShebaoBJModel) obj[1];
		int i = dal.BjDeclareDownload(m);
		if (i == 1) {
			message[0] = "1";
			message[1] = "社保补缴操作成功。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = m.getEmsb_ifdeclarestr();
		} else {
			message[0] = "0";
			message[1] = "社保补缴操作失败。";
			message[2] = String.valueOf(m.getId());
			message[3] = "EmShebaoBJ";
			message[4] = "社保补缴操作";
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		if ("1".equals(obj[0].toString())) {
			message = BjDeclareUpState(obj);
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
		String[] message = new String[5];
		if ("1".equals(obj[0].toString())) {
			message = BjDeclareUpState(obj);
		}
		return message;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		Emsi_OperateDal dal = new Emsi_OperateDal();
		return dal.upEmShebaoBJTaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

}
