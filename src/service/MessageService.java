package service;

import java.util.List;

import Model.SysMessageModel;

public interface MessageService {
	
	/**
	 * @Methodname:短信新增
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input: 短信表Model：SysMessageModel;必填：syme_content;收件人:symr_name;链接地址：syme_url;
	 *			业务表表名：smwr_table；业务表id：smwr_tid；
	 * 
	 * @out: message[];
	 *       Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] Add(SysMessageModel model);
	
	/**
	 * @Methodname:短信回复
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input: 短信表Model：SysMessageModel;必填：syme_content;收件人:symr_name;链接地址：syme_url;
	 *			业务表表名：smwr_table；业务表id：smwr_tid；回复信息id:syme_reply_id
	 * 
	 * @out: message[];
	 *       Str[0]:处理的状态,0失败1成功2出错;Str[1]提示信息；
	 */
	public String[] Reply(SysMessageModel model);
	
	/**
	 * @Methodname:短信信息查询——任何人都可以看到
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
	public List<SysMessageModel> SelectList();
	
	
	/**
	 * @Methodname:短信信息查询——只有收件人和发件人可以看到
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
	public List<SysMessageModel> Select();
	
	/**
	 * @Methodname:短信信息查询——根据log_id
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
	public List<SysMessageModel> SelectByLogId();
	
	
	
	/**
	 * @Methodname:读短信——把员工的业务短信改为已读
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input: syme_id:短信表id
	 *         
	 * 
	 *@out: Integer;
	 *       处理的状态,0失败，大于1成功
	 */
	public Integer Read();
	
	/**
	 * @Methodname:根据ID读短信——把员工的业务短信改为已读
	 * 
	 * @remark:
	 * 
	 * @procedure:
	 * 
	 * @businessMethode:
	 * 
	 * @input: symr_id:收件人信息表
	 *         
	 * 
	 *@out: Integer;
	 *       处理的状态,0失败，大于1成功
	 */
	public Integer Read(Integer symr_id);
	
	
	
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
	 * @out: boolean;
	 *       true:存在未读的短息；false没有未读的短息；
	 */
	public boolean isRead();
}
