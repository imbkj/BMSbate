package bll.SysMessage;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dal.SysMessage.SysMessage_NotReadListDal;

import Conn.dbconn;
import Model.SysMessageModel;

public class SysMessage_NotReadListBll {

	//获取未阅消息列表
	public List<SysMessageModel> getNotReadList(int log_id,String str) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_NotReadListDal dal = new SysMessage_NotReadListDal();
		list = dal.getNotReadList(log_id,str);
		return list;
	}
}
