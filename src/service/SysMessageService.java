package service;

import java.util.List;
import java.util.Map;

import Model.SysMessageModel;

public interface SysMessageService {

	// 发送新的系统短信
	// 参数 model:系统短信基本信息 list:接收人列表
	// 返回值 1:成功 0:失败
	public abstract int add(SysMessageModel model, List<SysMessageModel> list)
			throws Exception;

	/**
	 * 发送新的系统短信
	 * 
	 * @param title
	 *            业务类型
	 * @param content
	 *            内容
	 * @param state
	 *            0:暂存 1:发送
	 * @param reply_id
	 *            回复id,新发送的短信都输入0即可
	 * @param list
	 *            接收人列表 list内的model需录入:symr_log_id 接收人的uersid,symr_name 接收人姓名
	 * @return 1:成功 0:失败
	 */
	public abstract int add(String title, String content, int state,
			int reply_id, List<SysMessageModel> list) throws Exception;

	/**
	 * 发送新的系统短信
	 * 
	 * @param title
	 *            业务类型
	 * @param content
	 *            内容
	 * @param url
	 *            链接地址
	 * @param para
	 *            地址传参
	 * @param state
	 *            0:暂存 1:发送
	 * @param reply_id
	 *            回复id,新发送的短信都输入0即可
	 * @param list
	 *            接收人列表 list内的model需录入:symr_log_id 接收人的uersid,symr_name 接收人姓名
	 * @return 1:成功 0:失败
	 */
	public abstract int add(String title, String content, String url,
			Map<String, Object> para, int state, int reply_id,
			List<SysMessageModel> list) throws Exception;

	// 改变回复状态
	public abstract int updateReplyState(int symr_id, int state);

	// 改变阅读状态
	public abstract int updateReadState(int symr_id, int state);

	// 获取一条信息的所有信息记录
	// 参数 syme_id syme_log_id
	// 返回 list
	public abstract List<SysMessageModel> findAll(int syme_id, int syme_log_id);
	
	
	//系统直接发送给客服
	//type:0 发系统短信
	//type:1 发邮件
	//type:2 发系统短信和邮件
	public abstract int add(String title, String content, int state,
			int reply_id,int cid,int type)throws Exception;


}
