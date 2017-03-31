package bll.EmBaseCompact;

import Model.EmBaseCompactModel;
import dal.EmBaseCompact.EmBaseCompact_OperateDal;
import dal.EmBaseCompact.EmBaseCompact_UploadDal;
import service.WorkflowCore.WfBusinessService;

public class EmBaseCompact_UpOperateImpl implements WfBusinessService {
	private EmBaseCompact_OperateDal ccsaDal = new EmBaseCompact_OperateDal();
	private EmBaseCompactModel reg;
	@Override
	public String[] Operate(Object[] obj) {
		String[] message = new String[5];
		String i = obj[0].toString();
		if (i == "1") {
			message = UpEmCompact(obj);
		} else if (i == "2") {
			 
			message = UpAutEmCompact(obj);
		} else if (i == "3") {
			message = AddEmCompact(obj);
		} else if (i == "4") {
			message = UpVerEmCompact(obj);
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

	// 文件上传
	public String[] UpEmCompact(Object[] obj) {
		int result = 0;
		String[] message = new String[5];
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.DocFileUpload(Integer.parseInt(obj[1].toString()),
				obj[2].toString(), obj[3].toString(), obj[4].toString(),
				obj[5].toString(),obj[6].toString());
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
		
		 System.out.println(Integer.parseInt(obj[3].toString()));
		EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
		result = dal.AutDocFileUpload(Integer.parseInt(obj[1].toString()),
				obj[2].toString(),Integer.parseInt(obj[3].toString()));
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
				obj[21].toString(), "", "0", "新签","","");
		
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
	
	// 文件上传
		public String[] UpVerEmCompact(Object[] obj) {
			int result = 0;
			String[] message = new String[5];
			EmBaseCompact_UploadDal dal = new EmBaseCompact_UploadDal();
			result = dal.VerUpload(Integer.parseInt(obj[1].toString()),
					obj[2].toString(), obj[3].toString(), obj[4].toString(),
					obj[5].toString());
			//System.out.println(result);
			if (result != 0) {
				message[0] = "1";
				message[1] = "劳动合同版本上传成功!";
				message[2] = String.valueOf(result);
				message[3] = "emcompactupver";
				message[4] = "劳动合同版本上传";
			} else {
				message[0] = "0";
				message[1] = "劳动合同版本上传失败!";
			}
			return message;
		}
}
