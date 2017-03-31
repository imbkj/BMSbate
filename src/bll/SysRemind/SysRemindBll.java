package bll.SysRemind;

import impl.PubRemindImpl;

import java.util.ArrayList;
import java.util.List;

import dal.SysRemind.SysRemindDal;

import service.PubRemindService;

import Model.LoginModel;
import Model.PubRemindModel;

public class SysRemindBll {

	// 系统提醒新增
	public int PubRemindAdd(PubRemindModel prModel, List<LoginModel> list)
			throws Exception {
		int issuccess = 0;
		PubRemindService prs = new PubRemindImpl();
		issuccess = prs.PubRemindAdd(prModel, list);
		return issuccess;
	}

	// 获取未提醒和已提醒数量,获取即将提醒的信息
	public PubRemindModel getRemindMain(int log_id) {
		PubRemindModel prModel = new PubRemindModel();
		PubRemindModel prModel1 = new PubRemindModel();
		SysRemindDal dal = new SysRemindDal();
		prModel = dal.getRemindCount(log_id);
		prModel1 = dal.getNextRemind(log_id);
		prModel.setRemindtime(prModel1.getRemindtime());
		prModel.setContent(prModel1.getContent());
		return prModel;
	}

	// 获取提醒列表
	// 参数	log_id 登录人id
	// 		state 1:已提醒 0:未提醒
	public List<PubRemindModel> getRemindList(int log_id, int state) {
		SysRemindDal dal = new SysRemindDal();
		List<PubRemindModel> list = new ArrayList<PubRemindModel>();
		list = dal.getRemindList(log_id, state);
		return list;
	}
}
