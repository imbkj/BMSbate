package dal.SmsMessage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.PubMobileIdModel;
import Model.SmsMessageReceiveModel;
import Model.SmsMessageSendModel;
import Model.SmsSendRecModel;

public class SmsMessageDal {

	/**
	 * @Title: getMessageList
	 * @Description: TODO(查询短信收信列表)
	 * @param userId
	 *            LOGID
	 * @param cid
	 * @param gid
	 * @param company
	 * @param name
	 * @param phoneNum
	 * @param content
	 * @return 收信列表
	 * @throws SQLException
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getMessageList(String userId,
			String cid, String gid, String company, String name,
			String phoneNum, String content) throws SQLException {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		dbconn db = new dbconn();

		String sql = "select smsIndex,smsTime,sendNumber,logId,receiver,smsContent,newFlag,b.gid gid,b.cid,coba_shortname,emba_name,coba_namespell,emba_spell,case newFlag when 1 then 'black' end lstyle "
				+ " from RecvSmsTable a"
				+ " left join EmBase b on a.SendNumber=b.emba_mobile"
				+ " left join CoBase c on b.cid=c.cid"
				+ " where isnull(logId,'') like ? and isnull(b.cid,'') like ? and isnull(b.gid,'') like ?"
				+ " and (isnull(coba_company,'') like ? or isnull(coba_namespell,'') like ?)"
				+ " and (isnull(emba_name,'') like ? or isnull(emba_spell,'') like ?)"
				+ " and isnull(SendNumber,'') like ? and isnull(SmsContent,'') like ?"
				+ " order by SmsIndex desc";
		/*
		list = db.find(sql, SmsMessageReceiveModel.class,
				dbconn.parseSmap(SmsMessageReceiveModel.class), userId);
				*/
		
		list = db.find(sql, SmsMessageReceiveModel.class,
				dbconn.parseSmap(SmsMessageReceiveModel.class), userId, cid,
				gid, company, company, name, name, phoneNum, content);
				
		System.out.println(list.size());
		return list;
	}

	/**
	 * @Title: getSendMessageList
	 * @Description: TODO(查询短信发信列表)
	 * @param userId
	 * @param cid
	 * @param gid
	 * @param company
	 * @param name
	 * @param phoneNum
	 * @param content
	 * @return
	 * @throws SQLException
	 * @return List<SmsMessageSendModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageSendModel> getSendMessageList(Integer userId,
			String cid, String gid, String company, String name,
			String phoneNum, String content) throws SQLException {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select smsIndex,phoneNumber,smsContent,smsTime,smsUserId,smsUser,b.cid,b.gid,coba_company,emba_name,coba_shortspell,emba_spell from SentSmsTable a left join embase b on a.gid=b.gid left join cobase c on b.cid=c.cid");
		sql.append(" where smsUserId=? and ISNULL(b.cid,'') like ? and ISNULL(b.gid,'') like ?");
		sql.append(" and (ISNULL(coba_company,'') like ? or ISNULL(coba_namespell,'') like ?)");
		sql.append(" and (ISNULL(emba_name,'') like ? or ISNULL(emba_spell,'') like ?)");
		sql.append(" and phoneNumber like ? and smsContent like ?");
		sql.append(" order by smsIndex desc");
		list = db.find(sql.toString(), SmsMessageSendModel.class,
				dbconn.parseSmap(SmsMessageSendModel.class), userId, cid, gid,
				company, company, name, name, phoneNum, content);

		return list;
	}

	/**
	 * @Title: getDisNameList
	 * @Description: TODO(获取收信聚焦列表)
	 * @param userId
	 * @param pama
	 * @param columnName
	 * @return
	 * @throws SQLException
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getDisNameList(Integer userId,
			String pama, String columnName) throws SQLException {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		dbconn db = new dbconn();
		String sql = "select distinct "
				+ pama
				+ " as "
				+ columnName
				+ " from RecvSmsTable a inner join EmBase b on a.gid=b.gid inner join CoBase c on b.cid=c.cid where logId=? order by "
				+ pama;
		list = db.find(sql, SmsMessageReceiveModel.class,
				dbconn.parseSmap(SmsMessageReceiveModel.class), userId);
		return list;
	}

	public List<SmsMessageSendModel> getDisNameSendList(Integer userId,
			String pama, String columnName) throws SQLException {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		dbconn db = new dbconn();
		String sql = "select distinct "
				+ pama
				+ " as "
				+ columnName
				+ " from SentSmsTable a left join EmBase b on a.gid=b.gid left join CoBase c on b.cid=c.cid where SmsUserId=? order by "
				+ pama;
		list = db.find(sql, SmsMessageSendModel.class,
				dbconn.parseSmap(SmsMessageSendModel.class), userId);
		return list;
	}

	/**
	 * @Title: getSendCount
	 * @Description: TODO(获取发送短信数目)
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer getSendCount(Integer userId) throws SQLException {
		Integer i = 0;
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		dbconn db = new dbconn();
		String sql = "select 1 from SentSmsTable where SmsUserId=?";
		list = db.find(sql, SmsMessageSendModel.class,
				dbconn.parseSmap(SmsMessageSendModel.class), userId);
		i = list.size();
		return i;
	}

	/**
	 * @Title: getRecCount
	 * @Description: TODO(获取接收短信数目)
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer getRecCount(Integer userId) throws SQLException {
		Integer i = 0;
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		dbconn db = new dbconn();
		String sql = "select 1 from RecvSmsTable where logid=?";
		list = db.find(sql, SmsMessageReceiveModel.class,
				dbconn.parseSmap(SmsMessageReceiveModel.class), userId);
		i = list.size();
		return i;
	}

	/**
	 * @Title: getNewRecCount
	 * @Description: TODO(获取新信息计数)
	 * @param userId
	 * @return
	 * @throws SQLException
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer getNewRecCount(Integer userId) throws SQLException {
		Integer i = 0;
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		dbconn db = new dbconn();
		String sql = "select 1 from RecvSmsTable where logid=? and newFlag=1";
		list = db.find(sql, SmsMessageReceiveModel.class,
				dbconn.parseSmap(SmsMessageReceiveModel.class), userId);
		i = list.size();
		return i;
	}

	/**
	 * @Title: getMessageByGid
	 * @Description: TODO(查询短信往来记录)
	 * @param gid
	 * @return
	 * @throws SQLException
	 * @return List<SmsSendRecModel> 返回类型
	 * @throws
	 */
	public List<SmsSendRecModel> getMessageByGid(Integer gid)
			throws SQLException {
		List<SmsSendRecModel> list = new ListModelList<SmsSendRecModel>();
		dbconn db = new dbconn();
		list = db
				.find("select mobile phoneNumber,content smsContent,addtime smsTime,username smsUser from Sms_send a " +
						" inner join Sms_rel b on a.id=b.sms_se_id where gid=? order by addtime desc",
						SmsSendRecModel.class,
						dbconn.parseSmap(SmsSendRecModel.class), gid);
		return list;
	}

	/**
	 * @Title: getPhoneAddress
	 * @Description: TODO(获取手机号码所在地信息)
	 * @param num
	 * @return
	 * @throws SQLException
	 * @return List<PubMobileIdModel> 返回类型
	 * @throws
	 */
	public List<PubMobileIdModel> getPhoneAddress(Integer num)
			throws SQLException {
		List<PubMobileIdModel> list = new ListModelList<PubMobileIdModel>();
		dbconn db = new dbconn();
		list = db.find("select * from PubMobileID where pmid_num=?",
				PubMobileIdModel.class,
				dbconn.parseSmap(PubMobileIdModel.class), num);
		return list;
	}

	/**
	 * @Title: addMessage
	 * @Description: TODO(发送员工短信)
	 * @param gid
	 * @param phoneNumber
	 * @param content
	 * @param userid
	 * @param username
	 * @param priority
	 * @return
	 * @throws SQLException
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer addMessage(Integer gid, String phoneNumber, String content,
			Integer userid, String username, Integer priority)
			throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer.valueOf(db.callWithReturn(
				"{?=call EMSMS_ADD_P_py(?,?,?,?,?,?,?)}", Types.INTEGER,
				userid, username, phoneNumber, gid, content, priority, 0)
				.toString());
		return i;
	}

}
