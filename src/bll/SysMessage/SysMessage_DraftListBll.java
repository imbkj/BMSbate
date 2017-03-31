package bll.SysMessage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dal.SysMessage.SysMessage_DraftListDal;
import dal.SysMessage.SysMessage_EditDal;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_DraftListBll {

	// 根据登陆用户的userid获取草稿列表
	public List<SysMessageModel> getDraftList(int syme_log_id, String str) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_DraftListDal dal = new SysMessage_DraftListDal();
		list = dal.getDraftList(syme_log_id, str);
		return list;
	}

	// 删除草稿
	public int delete(int syme_id) {
		int row = 0;
		SysMessage_DraftListDal dal = new SysMessage_DraftListDal();
		row = dal.delete(syme_id);
		new SysMessage_EditDal().deleterecipient(syme_id);
		return row;
	}
	
	// 根据syme_id,syme_log_id获取所有关联短信(包括未发送的草稿)
	public List<SysMessageModel> findAll(int syme_id, int syme_log_id) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_DraftListDal dal = new SysMessage_DraftListDal();
		list = dal.findAll(syme_id, syme_log_id);
		return list;
	}
}
