package bll.CoCompact.CoCompactSA;

import Model.CoCompactSAModel;
import dal.CoCompact.CoCompactSA.CoCompactSA_OperateDal;
import dal.CoCompact.CoCompactSA.Compact_BcUploadDal;
import service.WorkflowCore.WfBusinessService;

public class CoCompactSA_OperateImpl implements WfBusinessService {
	private CoCompactSA_OperateDal ccsaDal = new CoCompactSA_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		//System.out.print("ssssss");
		if (i == "2") {
			message = signCoCompactSA(obj);
		} else if (i == "3") {
			message = returnCoCompactSA(obj);
		} else if (i == "0") {
			message = addCoCompactSA(obj);
		} else if (i == "1") {
			message = UpCoCompactSA(obj);
		} else if (i == "4") {
			message = PrintCoCompactSA(obj);
		} else if (i == "5") {
			message = KfAutCoCompactSA(obj);
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "8") {
			message = outCoCompact(obj);
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
		try {
			ccsaDal.updatetaskid(dataId, tapr_id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 更新合同状态
	private String[] UpCoCompactSA(Object[] obj) {
		//System.out.println("ok");
		String[] message = new String[5];
		int ccsa_state = Integer.parseInt(obj[1].toString());
		int ccsa_id = Integer.parseInt(obj[2].toString());

		if (ccsaDal.UpCoCompactSA(ccsa_state, ccsa_id)) {
			message[0] = "1";
			message[1] = "发送成功!";
			message[2] = obj[2].toString();
			message[3] = "cocompactsa";
			switch (ccsa_state) {
			case 1:
				message[4] = "合同补充协议审核";
				break;
			case 2:
				message[4] = "合同补充协议打印";
				break;
			default:
				message[4] = "default";
				break;
			}
		} else {
			message[0] = "0";
			message[1] = "发送失败!";
		}
		return message;
	}

	// 更新合同状态
	private String[] PrintCoCompactSA(Object[] obj) {
		String[] message = new String[5];
		int ccsa_state = Integer.parseInt(obj[1].toString());
		int ccsa_id = Integer.parseInt(obj[2].toString());
		int save_rwd = Integer.parseInt(obj[3].toString());
		if (ccsaDal.PrintCoCompactSA(ccsa_state, ccsa_id, save_rwd)) {
			message[0] = "1";
			message[1] = "操作成功!";
			message[2] = obj[2].toString();
			message[3] = "cocompactsa";
			switch (ccsa_state) {
			case 1:
				message[4] = "合同补充协议打印";
				break;
			case 2:
				message[4] = "合同补充协议打印";
				break;
			default:
				message[4] = "default";
				break;
			}
		} else {
			message[0] = "0";
			message[1] = "操作失败!";
		}
		return message;
	}
	
	// 更新合同状态
		private String[] KfAutCoCompactSA(Object[] obj) {
			String[] message = new String[5];
			int ccsa_state = Integer.parseInt(obj[1].toString());
			int ccsa_id = Integer.parseInt(obj[2].toString());
			if (ccsaDal.KfAutCoCompactSA(ccsa_state, ccsa_id)) {
				message[0] = "1";
				message[1] = "操作成功!";
				message[2] = obj[2].toString();
				message[3] = "cocompactsa";
				switch (ccsa_state) {
				case 1:
					message[4] = "合同补充协议审核";
					break;
				case 2:
					message[4] = "合同补充协议审核";
					break;
				default:
					message[4] = "default";
					break;
				}
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
			return message;
		}

	// 合同签回
	private String[] signCoCompactSA(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int ccsa_id = Integer.parseInt(obj[1].toString());
		result = ccsaDal.signCoCompactSA(ccsa_id, (CoCompactSAModel) obj[2]);
		if (result == 0) {
			message[0] = "1";
			message[1] = "公司合同补充协议签回成功!";
			message[2] = String.valueOf(ccsa_id);
			message[3] = "cocompactsa";
			message[4] = "合同补充协议签回";
		} else {
			message[0] = "0";
			message[1] = "公司合同补充协议签回失败!";
		}
		return message;
	}

	// 合同归档
	private String[] returnCoCompactSA(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int ccsa_id = Integer.parseInt(obj[1].toString());
		result = ccsaDal.returnCoCompactSA(ccsa_id, (CoCompactSAModel) obj[2]);
		if (result == 0) {
			message[0] = "1";
			message[1] = "公司合同补充协议归档成功!";
			message[2] = String.valueOf(ccsa_id);
			message[3] = "cocompactsa";
			message[4] = "合同补充协议归档";
		} else {
			message[0] = "0";
			message[1] = "公司合同补充协议归档失败!";
		}
		return message;
	}

	// 文件上传
	public String[] addCoCompactSA(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		Compact_BcUploadDal dal = new Compact_BcUploadDal();

		result = dal.DocFileUpload(Integer.parseInt(obj[1].toString()),
				Integer.parseInt(obj[2].toString()), obj[3].toString(),
				obj[4].toString(), obj[5].toString(), obj[6].toString(),
				Integer.parseInt(obj[7].toString()), obj[8].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "公司合同补充协议上传成功!";
			message[2] = String.valueOf(result);
			message[3] = "cocompactsa";
			message[4] = "合同补充协议上传";
		} else {
			message[0] = "0";
			message[1] = "公司合同补充协议上传失败!";
		}
		return message;
	}

	// 合同退回
	private String[] outCoCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int coco_id = Integer.parseInt(obj[1].toString());
		String remark = obj[2].toString();
		result = ccsaDal.outCoCompact(coco_id, remark);
		if (result == 0) {
			message[0] = "1";
			message[1] = "公司合同退回成功!";
			message[2] = String.valueOf(coco_id);
			message[3] = "cocompact";
			message[4] = "合同退回";
		} else {
			message[0] = "0";
			message[1] = "公司合同退回失败!";
		}
		return message;
	}
}
