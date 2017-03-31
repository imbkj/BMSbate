package bll.SysMessage;

import java.util.ArrayList;
import java.util.List;

import dal.SysMessage.SysMessage_SendListDal;

import Model.SysMessageModel;

public class SysMessage_SendListBll {

	// 根据登陆的userid获取发送消息列表
	public List<SysMessageModel> getSendList(int log_id, String str) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_SendListDal dal = new SysMessage_SendListDal();
		list = dal.getSendList(log_id, str);
		return list;
	}

	// 获取回复记录
	public List<SysMessageModel> getReplyList(int symr_log_id, int syme_id) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_SendListDal dal = new SysMessage_SendListDal();
		list = dal.getReplyList(symr_log_id, syme_id);
		return list;
	}
}
