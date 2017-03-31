package bll.SmsMessage;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.Embase.Embasedal;
import dal.SmsMessage.SmsMessageDal;
import Model.EmbaseModel;
import Model.PubMobileIdModel;
import Model.SmsMessageReceiveModel;
import Model.SmsMessageSendModel;
import Model.SmsSendRecModel;

public class SmsMessageManagerBll {
	/**
	 * @Title: getRecList
	 * @Description: TODO(查询短信接收列表)
	 * @param userId
	 * @param cid
	 * @param gid
	 * @param company
	 * @param name
	 * @param phoneNum
	 * @param content
	 * @return
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getRecList(Integer userId, String cid,
			String gid, String company, String name, String phoneNum,
			String content) {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		SmsMessageDal dal = new SmsMessageDal();
		String uid = userId == null ? "%" : userId.equals("") ? "%" : userId + "%";
		cid = cid == null ? "%" : cid.equals("") ? "%" : cid + "%";
		gid = gid == null ? "%" : gid.equals("") ? "%" : gid + "%";
		company = company == null ? "%" : company.equals("") ? "%" : company
				+ "%";
		name = name == null ? "%" : name.equals("") ? "%" : name + "%";

		phoneNum = phoneNum == null ? "%" : phoneNum.equals("") ? "%"
				: phoneNum + "%";
		content = content == null ? "%" : content.equals("") ? "%" : "%"
				+ content + "%";

		System.out.println(uid);
		try {
			list = dal.getMessageList(uid, cid, gid, company, name,
					phoneNum, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getSendList
	 * @Description: TODO(查询短信发送列表)
	 * @param userId
	 * @param cid
	 * @param gid
	 * @param company
	 * @param name
	 * @param phoneNum
	 * @param content
	 * @return
	 * @return List<SmsMessageSendModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageSendModel> getSendList(Integer userId, String cid,
			String gid, String company, String name, String phoneNum,
			String content) {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		SmsMessageDal dal = new SmsMessageDal();

		cid = cid == null ? "%" : cid.equals("") ? "%" : cid + "%";
		gid = gid == null ? "%" : gid.equals("") ? "%" : gid + "%";
		company = company == null ? "%" : company.equals("") ? "%" : company
				+ "%";
		name = name == null ? "%" : name.equals("") ? "%" : name + "%";

		phoneNum = phoneNum == null ? "%" : phoneNum.equals("") ? "%"
				: phoneNum + "%";
		content = content == null ? "%" : content.equals("") ? "%" : "%"
				+ content + "%";

		try {
			list = dal.getSendMessageList(userId, cid, gid, company, name,
					phoneNum, content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getDisPhoneName
	 * @Description: TODO(获取接收信息电话号码列表)
	 * @param userId
	 * @param phoneNum
	 * @return
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getDisPhoneName(Integer userId) {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameList(userId, "sendNumber", "sendNumber");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 发送信息电话号码
	public List<SmsMessageSendModel> getSendPhoneName(Integer userId) {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameSendList(userId, "phoneNumber", "phoneNumber");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getDisCid
	 * @Description: TODO(获取接收信息公司编号列表)
	 * @param userId
	 * @param cid
	 * @return
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getDisCid(Integer userId) {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameList(userId, "b.cid", "cid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 发送信息公司编号
	public List<SmsMessageSendModel> getSendDisCid(Integer userId) {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameSendList(userId, "b.cid", "cid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getDisGid
	 * @Description: TODO(获取接收信息员工编号列表)
	 * @param userId
	 * @param gid
	 * @return
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getDisGid(Integer userId) {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameList(userId, "b.gid", "gid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 发送信息员工编号
	public List<SmsMessageSendModel> getSendDisGid(Integer userId) {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameSendList(userId, "b.gid", "gid");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getDisCompany
	 * @Description: TODO(获取接收信息公司名称列表)
	 * @param userId
	 * @param company
	 * @return
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getDisCompany(Integer userId) {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameList(userId, "coba_company", "coba_company");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 发送信息公司名称
	public List<SmsMessageSendModel> getSendDisCompany(Integer userId) {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameSendList(userId, "coba_company",
					"coba_company");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getDisName
	 * @Description: TODO(获取接收信息员工姓名列表)
	 * @param userId
	 * @param name
	 * @return
	 * @return List<SmsMessageReceiveModel> 返回类型
	 * @throws
	 */
	public List<SmsMessageReceiveModel> getDisName(Integer userId) {
		List<SmsMessageReceiveModel> list = new ListModelList<SmsMessageReceiveModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameList(userId, "emba_name", "emba_name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 发送信息员工姓名
	public List<SmsMessageSendModel> getSendDisName(Integer userId) {
		List<SmsMessageSendModel> list = new ListModelList<SmsMessageSendModel>();
		SmsMessageDal dal = new SmsMessageDal();

		try {
			list = dal.getDisNameSendList(userId, "emba_name", "emba_name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getNewMessageCount
	 * @Description: TODO(获取新信息数目)
	 * @param userId
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer getNewMessageCount(Integer userId) {
		Integer i = 0;
		SmsMessageDal dal = new SmsMessageDal();
		try {
			i = dal.getNewRecCount(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: getRecMessageCount
	 * @Description: TODO(获取接收短信数目)
	 * @param userId
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer getRecMessageCount(Integer userId) {
		Integer i = 0;
		SmsMessageDal dal = new SmsMessageDal();
		try {
			i = dal.getRecCount(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: getSendMessageCount
	 * @Description: TODO(获取发送短信数目)
	 * @param userId
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer getSendMessageCount(Integer userId) {
		Integer i = 0;
		SmsMessageDal dal = new SmsMessageDal();
		try {
			i = dal.getSendCount(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/** 
	* @Title: getSendrecList 
	* @Description: TODO(查询短信联系信息) 
	* @param gid
	* @return
	* @return List<SmsSendRecModel>    返回类型 
	* @throws 
	*/
	public List<SmsSendRecModel> getSendrecList(Integer gid) {
		List<SmsSendRecModel> list = new ListModelList<SmsSendRecModel>();
		SmsMessageDal dal = new SmsMessageDal();
		try {
			list = dal.getMessageByGid(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/** 
	* @Title: getPhoneInfo 
	* @Description: TODO(查询号码所在地信息) 
	* @param num
	* @return
	* @return List<PubMobileIdModel>    返回类型 
	* @throws 
	*/
	public List<PubMobileIdModel> getPhoneInfo(String num){
		List<PubMobileIdModel> list = new ListModelList<PubMobileIdModel>();
		SmsMessageDal dal = new SmsMessageDal();
		Integer n = 0;
		n=Integer.valueOf(num.substring(0, 7));
		try {
			list = dal.getPhoneAddress(n);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	* @return Integer    返回类型 
	* @throws 
	*/
	public Integer addMessage(Integer gid, String phoneNumber, String content,
			Integer userid, String username, Integer priority)
			{
				SmsMessageDal dal = new SmsMessageDal();
				int k=0;
				try {
					k=dal.addMessage(gid, phoneNumber, content, userid, username, priority);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return k;
			}
	public List<EmbaseModel> getembaList(String str) {
		Embasedal dal=new Embasedal();
		return dal.getembaList(str);
	}
}
