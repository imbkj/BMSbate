package bll.SysMessage;

import impl.SysMessageImpl;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import service.SysMessageService;

import Conn.dbconn;
import Model.LoginModel;
import Model.SysMessageModel;

import dal.SysMessage.SysMessage_NotReadInfoListDal;
import dal.SysMessage.SysMessage_ReplyDal;

public class SysMessage_ReplyBll {

	// 根据syme_id获取发送人信息
	public LoginModel getSenderModel(int syme_id) {
		LoginModel model = new LoginModel();
		SysMessage_ReplyDal dal = new SysMessage_ReplyDal();
		model = dal.getSenderModel(syme_id);
		return model;
	}

	// 根据syme_id获取发送人和所有接收人列表
	public List<LoginModel> getReplyList(int syme_id, int userid) {
		List<LoginModel> list = new ArrayList<LoginModel>();
		SysMessage_ReplyDal dal = new SysMessage_ReplyDal();
		list = dal.getReplyList(syme_id, userid);
		return list;
	}

	// 更新回复状态
	public int updateReplyState(int symr_id, int state) {
		int row = 0;
		SysMessageService sms = new SysMessageImpl();
		row = sms.updateReplyState(symr_id, state);
		return row;
	}
}
