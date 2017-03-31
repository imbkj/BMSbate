package bll.SysMessage;

import impl.SysMessageImpl;

import java.util.ArrayList;
import java.util.List;

import service.SysMessageService;

import dal.SysMessage.SysMessage_NotReadInfoListDal;

import Model.SysMessageModel;

public class SysMessage_NotReadInfoListBll {

	// 根据发件人id和接收人id获取未阅信息列表
	public List<SysMessageModel> getNotReadInfoList(int syme_log_id,
			int symr_log_id, String str) {
		SysMessage_NotReadInfoListDal dal = new SysMessage_NotReadInfoListDal();
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		list = dal.getNotReadInfoList(syme_log_id, symr_log_id, str);
		return list;
	}

	// 根据syme_id获取所有接收人
	public String getNotReadRecipientStr(int syme_id) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		String str = "";
		SysMessage_NotReadInfoListDal dal = new SysMessage_NotReadInfoListDal();
		list = dal.getNotReadRecipientlistList(syme_id);
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				str += list.get(i).getSymr_name();
			} else {
				str += list.get(i).getSymr_name() + "、";
			}
		}
		return str;
	}

	// 获取回复历史列表
	public List<SysMessageModel> getReplyMessageList(int syme_id,
			int syme_log_id) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_NotReadInfoListDal dal = new SysMessage_NotReadInfoListDal();
		list = dal.getReplyMessageList(syme_id, syme_log_id);
		return list;
	}

	// 变更阅读状态
	public int updateReadState(int symr_id, int state) {
		int row = 0;
		SysMessageService sms = new SysMessageImpl();
		row = sms.updateReadState(symr_id, state);
		return row;
	}
}
