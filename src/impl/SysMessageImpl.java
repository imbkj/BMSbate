package impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dal.SysMessage.SysMessage_AddDal;
import dal.SysMessage.SysMessage_NotReadInfoListDal;
import dal.SysMessage.SysMessage_ReplyDal;
import Model.SysMessageModel;
import Util.PublishWindow;
import Util.PublishWindow.openWindow;
import Util.UserInfo;
import service.SysMessageService;

public class SysMessageImpl implements SysMessageService {

	@Override
	public int add(SysMessageModel model, List<SysMessageModel> list)
			throws Exception {
		int issuccess = 0;
		int row = 0;
		int syme_id = 0;
		SysMessage_AddDal dal = new SysMessage_AddDal();
		SysMessageModel model1 = new SysMessageModel();
		
		model.setSyme_addname(UserInfo.getUsername());
		model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));

		model1 = dal.SysMessageAdd(model);

		row = model1.getRow();
		syme_id = model1.getSyme_id();

		for (SysMessageModel smModel : list) {
			smModel.setSymr_syme_id(syme_id);
			row += dal.SysMessageRecpAdd(smModel);

			// 推送首页提醒
			sysmessagePublish(smModel,null);
		}

		if (row == list.size() + 1) {
			issuccess = 1;
		}

		return issuccess;
	}


	
	@Override
	public List<SysMessageModel> findAll(int syme_id, int syme_log_id) {
		List<SysMessageModel> list = new ArrayList<SysMessageModel>();
		SysMessage_NotReadInfoListDal dal = new SysMessage_NotReadInfoListDal();
		list = dal.findAll(syme_id, syme_log_id);
		return list;
	}


	@Override
	public int add(String title, String content, String url,
			Map<String, Object> para, int state, int reply_id,
			List<SysMessageModel> list) throws Exception {
		int issuccess = 0;
		int row = 0;
		int syme_id = 0;
		SysMessage_AddDal dal = new SysMessage_AddDal();
		SysMessageModel model1 = new SysMessageModel();
		
		model1.setSyme_title(title);
		model1.setSyme_content(content);
		model1.setSyme_state(state);
		model1.setSyme_addname(UserInfo.getUsername());
		model1.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));
		model1.setSyme_reply_id(reply_id);
		model1.setSyme_url(url);
		model1.setSyme_para(para==null?null:para.toString());

		model1 = dal.SysMessageAdd(model1);

		row = model1.getRow();
		syme_id = model1.getSyme_id();

		for (SysMessageModel smModel : list) {
			smModel.setSymr_syme_id(syme_id);
			row += dal.SysMessageRecpAdd(smModel);
			
			// 推送首页提醒
			sysmessagePublish(smModel,para);
		}

		if (row == list.size() + 1) {
			issuccess = 1;
		}

		return issuccess;
	}

	@Override
	public int updateReplyState(int symr_id, int state) {
		int row = 0;
		SysMessage_ReplyDal dal = new SysMessage_ReplyDal();
		row = dal.updateReplyState(symr_id, state);
		return row;
	}

	@Override
	public int updateReadState(int symr_id, int state) {
		int row = 0;
		SysMessage_NotReadInfoListDal dal = new SysMessage_NotReadInfoListDal();
		row = dal.updateReadState(symr_id, state);
		return row;
	}

		
	@Override
	public int add(String title, String content, int state, int reply_id,
			int cid, int type) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public int add(String title, String content, int state, int reply_id,
			List<SysMessageModel> list) throws Exception {
		int issuccess = 0;
		int row = 0;
		int syme_id = 0;
		SysMessage_AddDal dal = new SysMessage_AddDal();
		SysMessageModel model1 = new SysMessageModel();
		
		model1.setSyme_title(title);
		model1.setSyme_content(content);
		model1.setSyme_state(state);
		model1.setSyme_addname(UserInfo.getUsername());
		model1.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));
		model1.setSyme_reply_id(reply_id);
		model1.setSyme_url(null);
		model1.setSyme_para(null);

		model1 = dal.SysMessageAdd(model1);

		row = model1.getRow();
		syme_id = model1.getSyme_id();

		for (SysMessageModel smModel : list) {
			smModel.setSymr_syme_id(syme_id);
			row += dal.SysMessageRecpAdd(smModel);

			// 推送首页提醒
			sysmessagePublish(smModel,null);
		}

		if (row == list.size() + 1) {
			issuccess = 1;
		}

		return issuccess;
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


}
