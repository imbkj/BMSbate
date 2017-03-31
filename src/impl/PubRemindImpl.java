package impl;

import java.util.List;

import dal.SysRemind.SysRemindDal;

import Model.LoginModel;
import Model.PubRemindModel;
import service.PubRemindService;

public class PubRemindImpl implements PubRemindService {

	@Override
	public int PubRemindAdd(PubRemindModel prModel, List<LoginModel> list)
			throws Exception {
		int issuccess = 0;
		int count = list.size();
		int row = 0;
		for (int i = 0; i < count; i++) {
			SysRemindDal dal = new SysRemindDal();
			prModel.setRemindname(list.get(i).getLog_name());
			prModel.setMobile(list.get(i).getLog_mobile());
			prModel.setEmail(list.get(i).getLog_email());
			row += dal.PubRemindAdd(prModel);
		}

		if (row == count) {
			issuccess = 1;
		}
		return issuccess;
	}

}
