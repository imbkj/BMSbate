package bll.SmsMessage;

import java.util.List;

import Model.CoBaseModel;
import Model.EmbaseModel;
import dal.SysMessage.Message_SelectDal;

public class SmsGroupBll {
	private Message_SelectDal dal = new Message_SelectDal();

	// 获取员工名称列表
	public List<EmbaseModel> getEmbaseList(String str) {
		return dal.getEmbaseList(str);
	}

	// 获取员工名称列表
	public List<EmbaseModel> getEmbaseInfoList(EmbaseModel m) {
		String sql = "";
		if (m.getGid() != null) {
			sql = sql + " and gid=" + m.getGid();
		}
		if (m.getCid() != null) {
			sql = sql + " and a.cid=" + m.getCid();
		}
		if (m.getCoba_company() != null && !m.getCoba_company().equals("")) {
			sql = sql + " and coba_company like '%" + m.getCoba_company()
					+ "%'";
		}
		if (m.getCoba_client() != null && !m.getCoba_client().equals("")) {
			sql = sql + " and coba_client='" + m.getCoba_client() + "'";
		}
		if (m.getEmba_name() != null && !m.getEmba_name().equals("")) {
			sql = sql + " and emba_name='" + m.getEmba_name() + "'";
		}
		if (m.getEmba_idcard() != null && !m.getEmba_idcard().equals("")) {
			sql = sql + " and emba_idcard='" + m.getEmba_idcard() + "'";
		}
		if(m.getStatename()!=null&&!m.getStatename().equals(""))
		{
			if(m.getStatename().equals("离职"))
			{
				sql = sql + " and emba_state=0";
			}else{
				sql = sql + " and emba_state<>0";
			}
		}
		return dal.getEmbaseList(sql);
	}

	public List<String> getLoginList() {
		return dal.getLoginList();
	}

	// 根据gid 获取公司简称和客服
	public CoBaseModel getCoBaseModel(Integer gid) {
		return dal.getCoBaseModel(gid);
	}
}
