package bll.EmBaseCompact;

import Model.EmBaseCompactModel;
import dal.EmBaseCompact.EmBaseCompact_OperateDal;
import dal.EmBaseCompact.EmBaseCompact_UploadDal;
import service.WorkflowCore.WfBusinessService;

public class EmBaseCompact_OperateImpl implements WfBusinessService {
	private EmBaseCompact_OperateDal ccsaDal = new EmBaseCompact_OperateDal();
	private EmBaseCompactModel reg;

	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();

		if (i == "1") {
			message = AddEmCompact(obj);
		} else if (i == "2") {
			message = MakEmCompact(obj);
		} else if (i == "3") {
			message = AutEmCompact(obj);
		} else if (i == "4") {
			message = PrintEmCompact(obj);
		} else if (i == "5") {
			message = SignEmCompact(obj);
		} else if (i == "6") {
			message = FilingEmCompact(obj);
		} else if (i == "7") {
			message = UpEmCompact(obj);
		} else if (i == "8") {
			message = RenEmCompact(obj);
		} else if (i == "9") {
			message = EditEmCompact(obj);
		} else if (i == "10") {
			message = GZEmCompact(obj);
		} else if (i == "11") {
			message = QDEmCompact(obj);
		}

		return message;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		// TODO Auto-generated method stub
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "10") {
			message = outEmCompact(obj);
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
			ccsaDal.addemcompactid(dataId, tapr_id);
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

	// 文件上传
	public String[] UpEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.DocFileUpload(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), obj[3].toString(), obj[4].toString(),
				obj[5].toString(),"");
		//System.out.println(result);
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同模板上传成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同模板上传";
		} else {
			message[0] = "0";
			message[1] = "劳动合同模板上传失败!";
		}
		return message;
	}

	// 模板审核
	public String[] UpAutEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.AutDocFileUpload(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), 0);
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同模板审核成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同模板审核";
		} else {
			message[0] = "0";
			message[1] = "劳动合同模板审核失败!";
		}
		return message;
	}

	// 劳动合同新签
	public String[] AddEmCompact(Object[] obj) {
		int result = 0;
		reg = new EmBaseCompactModel(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(),
				obj[12].toString(), obj[13].toString(), obj[14].toString(),
				obj[15].toString(), obj[16].toString(), obj[17].toString(),
				obj[18].toString(), obj[19].toString(), obj[20].toString(),
				obj[21].toString(), "", "0", "新签", obj[25].toString(), obj[26].toString());

		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Add_Emcompact(reg);
		
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同新签成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactadd";
			message[4] = "劳动合同新签";
		} else {
			message[0] = "0";
			message[1] = "劳动合同新签失败!";
		}
		return message;
	}

	// 劳动合同续签
	public String[] RenEmCompact(Object[] obj) {
		int result = 0;
		reg = new EmBaseCompactModel(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(),
				obj[12].toString(), obj[13].toString(), obj[14].toString(),
				obj[15].toString(), obj[16].toString(), obj[17].toString(),
				obj[18].toString(), obj[19].toString(), obj[20].toString(),
				obj[21].toString(), "", "0", "续签", obj[25].toString(), obj[26].toString());

		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Ren_Emcompact(reg);
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同续签成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactren";
			message[4] = "劳动合同续签";
		} else {
			message[0] = "0";
			message[1] = "劳动合同续签失败!";
		}
		return message;
	}

	// 劳动合同修改
	public String[] EditEmCompact(Object[] obj) {
		String result = "0";
		reg = new EmBaseCompactModel(obj[1].toString(), obj[2].toString(),
				obj[3].toString(), obj[4].toString(), obj[5].toString(),
				obj[6].toString(), obj[7].toString(), obj[8].toString(),
				obj[9].toString(), obj[10].toString(), obj[11].toString(),
				obj[12].toString(), obj[13].toString(), obj[14].toString(),
				obj[15].toString(), obj[16].toString(), obj[17].toString(),
				obj[18].toString(), obj[19].toString(), obj[20].toString(),
				obj[21].toString(), "", "0", "修改", obj[25].toString(), obj[26].toString());
		//System.out.println("0x0x0x0x0x");
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Edit_Emcompact(reg);
		if (result != "0") {
			message[0] = "1";
			message[1] = "劳动合同修改成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactedit";
			message[4] = "劳动合同修改";
		} else {
			message[0] = "0";
			message[1] = "劳动合同续签失败!";
		}
		return message;
	}

	// 劳动合同制作
	public String[] MakEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Mak_Emcompact(Integer.parseInt(obj[1].toString()),
				obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同制作成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同制作";
		} else {
			message[0] = "0";
			message[1] = "劳动合同制作失败!";
		}
		return message;
	}

	// 劳动合同审核
	public String[] AutEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Aut_Emcompact(Integer.parseInt(obj[1].toString()),
				obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同审核成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同审核";
		} else {
			message[0] = "0";
			message[1] = "劳动合同审核失败!";
		}
		return message;
	}

	// 劳动合同打印
	public String[] PrintEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Print_Emcompact(Integer.parseInt(obj[1].toString()),
				obj[2].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同打印成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同打印";
		} else {
			message[0] = "0";
			message[1] = "劳动合同打印失败!";
		}
		return message;
	}
	
	// 劳动合同盖章
		public String[] GZEmCompact(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
			result = dal.GZ_Emcompact(Integer.parseInt(obj[1].toString()),
					obj[2].toString());
			if (result != 0) {
				message[0] = "1";
				message[1] = "劳动合同盖章成功!";
				message[2] = String.valueOf(result);
				message[3] = "emcompactup";
				message[4] = "劳动合同盖章";
			} else {
				message[0] = "0";
				message[1] = "劳动合同盖章失败!";
			}
			return message;
		}
		
		// 劳动合同签回
		public String[] QDEmCompact(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
			result = dal.Qd_Emcompact(Integer.parseInt(obj[1].toString()),
					obj[2].toString(), obj[3].toString(), obj[4].toString());
			if (result != 0) {
				message[0] = "1";
				message[1] = "劳动合同签订成功!";
				message[2] = String.valueOf(result);
				message[3] = "emcompactup";
				message[4] = "劳动合同签订";
			} else {
				message[0] = "0";
				message[1] = "劳动合同签订失败!";
			}
			return message;
		}

	// 劳动合同签回
	public String[] SignEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Sign_Emcompact(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), obj[3].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同签回成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同签回";
		} else {
			message[0] = "0";
			message[1] = "劳动合同签回失败!";
		}
		return message;
	}

	// 劳动合同归档
	public String[] FilingEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.Filing_Emcompact(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), obj[3].toString());
		if (result != 0) {
			message[0] = "1";
			message[1] = "劳动合同归档成功!";
			message[2] = String.valueOf(result);
			message[3] = "emcompactup";
			message[4] = "劳动合同归档";
		} else {
			message[0] = "0";
			message[1] = "劳动合同归档失败!";
		}
		return message;
	}

	// 劳动合同退回
	private String[] outEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		int coco_id = Integer.parseInt(obj[1].toString());
		result = dal.outEmCompact(coco_id,obj[2].toString());
		if (result == 0) {
			message[0] = "1";
			message[1] = "劳动合同退回成功!";
			message[2] = String.valueOf(coco_id);
			message[3] = "cocompact";
			message[4] = "劳动合同退回";
		} else {
			message[0] = "0";
			message[1] = "劳动合同退回失败!";
		}
		return message;
	}
}
