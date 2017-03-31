package bll.SysMessage;

import java.util.ArrayList;
import java.util.List;

import dal.SysMessage.SysMessage_ReciListDal;

import Model.SysMessageModel;

public class SysMessage_ReciListBll {

	// 获取所有收到的信息
	public List<SysMessageModel> getReciList(int log_id, String str) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_ReciListDal dal = new SysMessage_ReciListDal();
		list = dal.getReciList(log_id, str);
		return list;
	}
	
	// 获取一条信息的回复详情
	public List<SysMessageModel> getReplyList(int syme_id, int log_id) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_ReciListDal dal = new SysMessage_ReciListDal();
		list = dal.getReplyList(syme_id, log_id);
		return list;
	}
}
