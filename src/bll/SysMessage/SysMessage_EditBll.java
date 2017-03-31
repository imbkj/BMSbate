package bll.SysMessage;

import impl.SysMessageImpl;

import java.sql.ResultSet;
import java.util.List;

import service.SysMessageService;

import dal.SysMessage.SysMessage_AddDal;
import dal.SysMessage.SysMessage_EditDal;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_EditBll {

	//提交
	public int EditSubmit(SysMessageModel model, List<SysMessageModel> list,
			int oldcount) throws Exception {
		int issuccess = 0;
		int row = 0;
		SysMessage_AddDal dal = new SysMessage_AddDal();
		SysMessage_EditDal edal = new SysMessage_EditDal();
		SysMessageModel model1 = new SysMessageModel();

		model1 = edal.SysMessageSave(model);
		row = model1.getRow();

		row += edal.deleterecipient(model.getSyme_id());
		
		for (SysMessageModel smModel : list) {
			smModel.setSymr_syme_id(model.getSyme_id());
			row += dal.SysMessageRecpAdd(smModel);
			new SysMessageImpl().sysmessagePublish(smModel,null);
		}

		if (row == list.size() + oldcount + 1) {
			issuccess = 1;
		}

		return issuccess;
	}
	
	//根据syme_id,symr_log_id获取symr_id
	public int getsymrid(int syme_id, int symr_log_id) {
		int symr_id = 0;
		SysMessage_EditDal dal = new SysMessage_EditDal();
		symr_id = dal.getsymrid(syme_id, symr_log_id);
		return symr_id;
	}
}
