package service;

import java.util.List;

import Model.LoginModel;
import Model.PubRemindModel;

public interface PubRemindService {

	// 新增系统提醒
	// 返回参数  1:成功 0:失败
	// 参数	prModel,list
	public abstract int PubRemindAdd(PubRemindModel prModel,List<LoginModel> list) throws Exception;
}
