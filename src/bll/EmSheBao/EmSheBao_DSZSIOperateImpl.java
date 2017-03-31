package bll.EmSheBao;

import Model.EmShebaoChangeSZSIModel;
import dal.EmSheBao.EmSheBao_DOperateDal;
import dal.EmSheBao.Emsi_OperateDal;
import service.WorkflowCore.WfBusinessService;

public class EmSheBao_DSZSIOperateImpl implements WfBusinessService {
	EmSheBao_DOperateDal dal = new EmSheBao_DOperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "0") {
			message = declareSZSISingle(obj);
		} else if (i == "1") {
			message = declareSZSISingleSuccess(obj);
		} else if (i == "2") {
			message = declareSZSIUpload(obj);
		} else if (i == "7") {
			message = declareSZSIDownload(obj);
		}/* else if (i == "8") {
			message = declareSZSIBackTOCenter(obj);
		}*/

		return message;
	}

	private String[] declareSZSISingle(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSZSISingle((EmShebaoChangeSZSIModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作交单变更数据为“已交单”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeSZSI";
			message[4] = "社保交单变更已交单";
		} else {
			message[0] = "0";
			message[1] = "操作交单变更数据为“已交单”失败!";
		}
		return message;
	}

	private String[] declareSZSIUpload(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSZSIUpload((EmShebaoChangeSZSIModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作交单变更数据为“已上单”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeSZSI";
			message[4] = "社保交单变更已上单";
		} else {
			message[0] = "0";
			message[1] = "操作交单变更数据为“已上单”失败!";
		}
		return message;
	}

	private String[] declareSZSIDownload(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSZSIDownload((EmShebaoChangeSZSIModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作交单变更数据为“下载表格”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeSZSI";
			message[4] = "社保交单变更中心待核收";
		} else {
			message[0] = "0";
			message[1] = "操作交单变更数据为“下载表格”失败!";
		}
		return message;
	}

	private String[] declareSZSIBackTOCenter(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		EmShebaoChangeSZSIModel m=new EmShebaoChangeSZSIModel();
		m=null;
		m=(EmShebaoChangeSZSIModel) obj[2];
		result = dal.declareSZSIBackTOCenter(m);

		if (result == 0) {
			message[0] = "1";
			if (m.getEscs_single()==1) {
				message[1] = "操作交单变更数据为“返回未上传表格”成功!";
				message[4] = "社保交单变更返回未上传表格";
			}else {
				message[1] = "操作交单变更数据为“返回中心收集材料”成功!";
				message[4] = "社保交单变更返回中心待核收";
			}
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeSZSI";
		} else {
			message[0] = "0";
			message[1] = "操作交单变更数据为“返回中心收集材料”失败!";
		}
		return message;
	}

	private String[] declareSZSISingleSuccess(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSZSISingleSuccess((EmShebaoChangeSZSIModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作交单变更数据为“已申报”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeSZSI";
			message[4] = "社保交单变更申报中";
		} else {
			message[0] = "0";
			message[1] = "操作交单变更数据为“已申报”失败!";
		}
		return message;
	}

	private String[] declareSZSIReturn(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSZSIReturn((EmShebaoChangeSZSIModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作交单变更数据为“退回”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeSZSI";
			message[4] = "社保交单变更申报退回";
		} else {
			message[0] = "0";
			message[1] = "操作交单变更数据为“退回”失败!";
		}
		return message;
	}

	private String[] declareSZSIFirstStep(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int id = Integer.parseInt(obj[1].toString());
		result = dal.declareSZSIFirstStep((EmShebaoChangeSZSIModel) obj[2]);

		if (result == 0) {
			message[0] = "1";
			message[1] = "操作交单变更数据为“未申报”成功!";
			message[2] = String.valueOf(id);
			message[3] = "EmShebaoChangeSZSI";
			message[4] = "社保交单变更未申报";
		} else {
			message[0] = "0";
			message[1] = "操作交单变更数据为“未申报”失败!";
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "4") {
			message = declareSZSIFirstStep(obj);
		} else if (i == "3") {
			message = declareSZSIReturn(obj);
		} else if (i == "8") {
			message = declareSZSIBackTOCenter(obj);
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
		return dal.upEmShebaoChangeSZSITaprId(tapr_id, dataId);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}
}
