package impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dal.SysMessage.Message_OperateDal;
import dal.SysMessage.Message_SelectDal;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.PublishWindow;
import Util.UserInfo;
import Util.PublishWindow.openWindow;
import service.MessageService;

public class MessageImpl implements MessageService {
	private String smwr_table;
	private Integer smwr_tid;

	/**
	 * @Methodname:实现类的构造函数
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input: gid:员工id；type:业务类型
	 * 
	 */
	public MessageImpl(String smwr_table, Integer smwr_tid) {
		if(smwr_table==null)
			smwr_table="";
		this.smwr_tid = smwr_tid;
		this.smwr_table = smwr_table;
	}

	/**
	 * @Methodname:实现类的构造函数
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input: gid:员工id；type:业务类型
	 * 
	 */
	public MessageImpl() {

	}

	/**
	 * @Methodname:短信新增
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input:短信内容：syme_content;收件人:symr_name;类型：链接地址：syme_url
	 * 
	 * 
	 * @out: message[]; Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	@Override
	public String[] Add(SysMessageModel model) {
		// TODO Auto-generated method stub
		Message_OperateDal dal = new Message_OperateDal();
		String[] str = new String[5];
		try {
			if (model.getSymr_name() != null && (model.getSymr_log_id() == null||model.getSymr_log_id().equals(0))) {
				Message_SelectDal sdal = new Message_SelectDal();
				LoginModel mm = sdal.getClient(" and log_name='"
						+ model.getSymr_name() + "'");
				model.setSymr_log_id(mm.getLog_id());
			}
			if (model.getSymr_log_id() != null
					&& !model.getSymr_log_id().equals(0)
					&& (model.getSymr_name() == null || model.getSymr_name()
							.equals(""))) {
				Message_SelectDal sdal = new Message_SelectDal();
				LoginModel mm = sdal.getClient(" and log_id="
						+ model.getSymr_log_id());
				model.setSymr_name(mm.getLog_name());
			}
			model.setSyme_addname(UserInfo.getUsername());
			model.setSyme_reply_id(model.getSyme_reply_id());
			if (model.getSmwr_type() != null
					&& !model.getSmwr_type().equals("")
					&& (model.getSyme_title() == null || model.getSyme_title()
							.equals(""))) {
				model.setSyme_title(model.getSmwr_type());
			}
			model.setSyme_para("");
			model.setSmwr_type(model.getSmwr_type());
			if (smwr_tid != null) {
				model.setSmwr_tid(smwr_tid);
			} else {
				model.setSmwr_tid(0);
			}
			model.setSmwr_table(smwr_table);
			if (model.getEmail() == null) {
				model.setEmail(0);
			}
			if (model.getSyme_log_id() == null || model.getSyme_log_id() == 0) {
				model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));
			}
			Integer k = 0, ifem = 0;
			if (model.getSymr_name() != null
					&& !model.getSymr_name().equals("")
					&& model.getSymr_log_id() != null
					&& !model.getSymr_log_id().equals(""))// 当接收人姓名和id都不为空的时候才新增
			{
				k = k + dal.SysMessageAdd(model);
			} else {
				ifem = -1;
			}
			if (k > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				model.setSymr_syme_id(k);
				// 推送首页提醒
				// sysmessagePublish(model,null);
				Map map = new HashMap();
				map.put("logid", model.getSyme_log_id());
				sysmessagePublish(model.getSymr_log_id(),
						"/SysMessage/SysMessage_NotReadInfoList.zul", map);
			} else if (ifem == -1) {
				str[0] = "0";
				str[1] = "找不到收信人信息";
			} else if (k == -2) {
				str[0] = "0";
				str[1] = "短信内容不能为空";
			} else {
				str[0] = "0";
				str[1] = "数据添加出错";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * @Methodname:短信信息查询
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input:
	 * 
	 * 
	 * @out: List<SysMessageModel>;
	 * 
	 */
	@Override
	public List<SysMessageModel> Select() {
		// TODO Auto-generated method stub
		Message_SelectDal dal = new Message_SelectDal();
		return dal.getReciList(smwr_table, smwr_tid);
	}

	/**
	 * @Methodname:读短信——把员工的业务短信改为已读
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input: symr_id:短信状态表id
	 * 
	 * 
	 * @out: Integer; 处理的状态,0失败,大于1成功
	 */
	@Override
	public Integer Read() {
		// TODO Auto-generated method stub
		Message_OperateDal dal = new Message_OperateDal();
		return dal.updateMessage(smwr_table, smwr_tid);
	}

	/**
	 * @Methodname:查询员工业务是否有短信未读
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input:
	 * 
	 * @out: boolean; true:存在未读的短息；false没有未读的短息；
	 */
	@Override
	public boolean isRead() {
		// TODO Auto-generated method stub
		Message_SelectDal dal = new Message_SelectDal();
		Integer num = dal.isRead(smwr_table, smwr_tid);
		boolean flag = false;
		if (num > 0) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 推送首页提醒
	 * 
	 * @param smModel
	 */
	public void sysmessagePublish(Integer symr_log_id, String url,
			Map<String, Object> map) {
		PublishWindow pw = new PublishWindow();
		// pw.setOpenMethod(openWindow.doOverlapped);
		pw.publish("Uid" + symr_log_id,
				UserInfo.getUsername() + "给您发了一条新的系统短信", url, map);
	}

	@Override
	public String[] Reply(SysMessageModel model) {
		// TODO Auto-generated method stub
		Message_OperateDal dal = new Message_OperateDal();
		String[] str = new String[5];
		try {
			model.setSyme_addname(UserInfo.getUsername());
			model.setSyme_reply_id(model.getSyme_reply_id());
			model.setSyme_title(model.getSmwr_type());
			model.setSyme_para("");
			model.setSmwr_type(model.getSmwr_type());
			model.setSmwr_table(smwr_table);
			if (model.getEmail() == null) {
				model.setEmail(0);
			}
			if (smwr_tid != null) {
				model.setSmwr_tid(smwr_tid);
			} else {
				model.setSmwr_tid(0);
			}
			if (model.getSymr_name() != null && model.getSymr_log_id() == null) {
				Message_SelectDal sdal = new Message_SelectDal();
				LoginModel mm = sdal.getClient(" and log_name='"
						+ model.getSymr_name() + "'");
				model.setSymr_log_id(mm.getLog_id());
			}
			if (model.getSymr_log_id() != null && model.getSymr_name() == null) {
				Message_SelectDal sdal = new Message_SelectDal();
				LoginModel mm = sdal.getClient(" and log_id="
						+ model.getSymr_log_id());
				model.setSymr_name(mm.getLog_name());
			}
			if (model.getSyme_log_id() == null || model.getSyme_log_id() == 0) {
				model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));
			}

			Integer k = dal.SysMessageReply(model);
			if (k > 0) {
				str[0] = "1";
				str[1] = "提交成功";
				model.setSymr_syme_id(k);

				// 推送首页提醒
				// sysmessagePublish(model,null);
				Map map = new HashMap();
				map.put("logid", model.getSymr_log_id());
				sysmessagePublish(model.getSymr_log_id(),
						"/SysMessage/SysMessage_NotReadInfoList.zul", map);
			} else if (k == -2) {
				str[0] = "0";
				str[1] = "短信内容不能为空";
			} else {
				str[0] = "0";
				str[1] = "数据添加出错";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	@Override
	public List<SysMessageModel> SelectByLogId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer Read(Integer symr_id) {
		// TODO Auto-generated method stub
		Message_OperateDal dal = new Message_OperateDal();
		return dal.updateMessageRead(symr_id);
	}
	
	/**推送首页提醒
	 * @param smModel
	 */
	public void sysmessagePublish(SysMessageModel smModel,Map<String, Object> map){
		PublishWindow pw = new PublishWindow();
		pw.setOpenMethod(openWindow.doOverlapped);
		pw.publish("Uid" + smModel.getSymr_log_id(), UserInfo.getUsername()
				+ "给您发了一条新的系统短信", smModel.getSyme_url(), map);
	}

	@Override
	public List<SysMessageModel> SelectList() {
		Message_SelectDal dal = new Message_SelectDal();
		return dal.getSysList(smwr_table, smwr_tid);
	}
}
