package bll.CoCompact;

import java.sql.SQLException;

import com.sun.mail.iap.Response;

import dal.CoCompact.CoCompact_OperateDal;
import dal.CoCompact.CoCompactSA.Compact_BcUploadDal;
import service.WorkflowCore.WfBusinessService;
import Model.CoCompactModel;
import Model.CoCompactTemAddModel;

public class CoCompact_OperateImpl implements WfBusinessService {
	private CoCompact_OperateDal cocoDal = new CoCompact_OperateDal();

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "0") {
			message = addCompactTemp(obj);
		} else if (i == "1") {
			message = MakeCoCompact(obj);
		} else if (i == "4") {
			message = AutCoCompact(obj);
		} else if (i == "5") {
			message = UpCoCompact(obj);
		}else if (i == "2") {
			message = signCoCompact(obj);
		} else if (i == "3") {
			message = returnCoCompact(obj);
		} else if (i == "8") {
			message = UpCoCompact(obj);
		}else if (i == "9") {
			message = GzCoCompact(obj);
		}else if (i == "10") {
			message = UpLoadCoCompactSA(obj);
		}
		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "7") {
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
			cocoDal.updatetaskid(dataId, tapr_id);
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

	// 公司合同录入
	private String[] addCompactTemp(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		result = cocoDal.addCompactTemp(Integer.parseInt(obj[1].toString()),
				String.valueOf(obj[2]), (CoCompactModel) obj[3]);
		if (result > 0) {
			message[0] = "1";
			message[1] = "公司合同生成模板成功!";
			message[2] = String.valueOf(result);
			message[3] = "cocompact";
			message[4] = "新增合同模板";
		} else {
			message[0] = "0";
			message[1] = "公司合同生成模板失败!";
		}
		return message;
	}

	// 制作合同
	private String[] MakeCoCompact(Object[] obj) {
		String[] message = new String[5];
		int coco_state = Integer.parseInt(obj[1].toString());
		int coco_id = Integer.parseInt(obj[2].toString());
		if (cocoDal.MakeCoCompact(1, coco_id)) {
			message[0] = "1";
			message[1] = "制作成功!";
			message[2] = obj[2].toString();
			message[3] = "cocompact";
			switch (coco_state) {
			case 1:
				message[4] = "合同制作";
				break;

			default:
				message[4] = "default";
				break;
			}
		} else {
			message[0] = "0";
			message[1] = "制作失败!";
		}
		return message;
	}

	// 审核合同
	private String[] AutCoCompact(Object[] obj) {
		String[] message = new String[5];
		int coco_state = Integer.parseInt(obj[1].toString());
		int coco_id = Integer.parseInt(obj[2].toString());
		int aut_st = Integer.parseInt(obj[4].toString());
		if (cocoDal.AutCoCompact(2, coco_id, obj[3].toString(),aut_st)) {
			message[0] = "1";
			message[1] = "审核成功!";
			message[2] = obj[2].toString();
			message[3] = "cocompact";
			switch (coco_state) {
			case 1:
				message[4] = "合同制作";
				break;
			case 2:
				message[4] = "合同审核";
				break;
	
			default:
				message[4] = "default";
				break;
			}
		} else {
			message[0] = "0";
			message[1] = "审核失败!";
		}
		return message;
	}

	// 更新合同状态
	private String[] UpCoCompact(Object[] obj) {
		String[] message = new String[5];
		int coco_state = Integer.parseInt(obj[1].toString());
		int coco_id = Integer.parseInt(obj[2].toString());
		int save_rwd=Integer.parseInt(obj[3].toString());
		int coco_autst=Integer.parseInt(obj[4].toString());
		if (cocoDal.UpCoCompact(2, coco_id,save_rwd,coco_autst)) {
			message[0] = "1";
			message[1] = "发送成功!";
			message[2] = obj[2].toString();
			message[3] = "cocompact";
			switch (coco_state) {
			case 1:
				message[4] = "合同制作";
				break;
			case 2:
				message[4] = "合同审核";
				break;
			case 3:
				message[4] = "合同打印";
				break;
			case 4:
				message[4] = "合同导出";
				break;
			case 5:
				message[4] = "合同盖章";
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
	
	// 更新合同盖章状态
		private String[] GzCoCompact(Object[] obj) {
			String[] message = new String[5];
			int coco_state = Integer.parseInt(obj[1].toString());
			int coco_id = Integer.parseInt(obj[2].toString());
			int coco_autst=Integer.parseInt(obj[3].toString());
			if (cocoDal.GzCoCompact(2, coco_id,coco_autst)) {
				message[0] = "1";
				message[1] = "发送成功!";
				message[2] = obj[2].toString();
				message[3] = "cocompact";
				switch (coco_state) {
				case 1:
					message[4] = "合同制作";
					break;
				case 2:
					message[4] = "合同审核";
					break;
				case 3:
					message[4] = "合同打印";
					break;
				case 4:
					message[4] = "合同导出";
					break;
				case 5:
					message[4] = "合同盖章";
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

	// 合同签回
	private String[] signCoCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int coco_id = Integer.parseInt(obj[1].toString());
		result = cocoDal.signCoCompact(coco_id, (CoCompactModel) obj[2]);
		if (result == 0) {
			message[0] = "1";
			message[1] = "公司合同签回成功!";
			message[2] = String.valueOf(coco_id);
			message[3] = "cocompact";
			message[4] = "合同签回";
		} else {
			message[0] = "0";
			message[1] = "公司合同签回失败!";
		}
		return message;
	}

	// 合同归档
	private String[] returnCoCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int coco_id = Integer.parseInt(obj[1].toString());
		result = cocoDal.returnCoCompact(coco_id, (CoCompactModel) obj[2]);
		if (result == 0) {
			message[0] = "1";
			message[1] = "公司合同归档成功!";
			message[2] = String.valueOf(coco_id);
			message[3] = "cocompact";
			message[4] = "合同归档";
		} else {
			message[0] = "0";
			message[1] = "公司合同归档失败!";
		}
		return message;
	}

	// 合同退回
	private String[] outCoCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		int coco_id = Integer.parseInt(obj[1].toString());
		String remark=obj[2].toString();
		result = cocoDal.outCoCompact(coco_id,remark);
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
	
	// 公司合同上传
		public String[] UpLoadCoCompactSA(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			Compact_BcUploadDal dal = new Compact_BcUploadDal();

			result = dal.UDocFileUpload(Integer.parseInt(obj[1].toString()),
					Integer.parseInt(obj[2].toString()), obj[3].toString(),
					obj[4].toString(), obj[5].toString(), obj[6].toString());
			if (result != 0) {
				message[0] = "1";
				message[1] = "公司合同上传成功!";
				message[2] = String.valueOf(result);
				message[3] = "cocompactsa";
				message[4] = "公司合同上传";
			} else {
				message[0] = "0";
				message[1] = "公司合同上传失败!";
			}

			return message;
		}
}
