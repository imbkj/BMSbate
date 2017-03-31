package bll.CoReg;

import Model.CoOnlineRegisterInfoModel;
import Model.ResponsbilityBookModel;
import dal.CoReg.CoReg_Dal;

public class CoReg_Bll {

	CoReg_Dal dal = new CoReg_Dal();

	//修改就业登记信息
	public int modCorim(CoOnlineRegisterInfoModel m){
		return dal.modCorim(m);
	}
	// 根据id获得对象
	public CoOnlineRegisterInfoModel getCoRim(Integer coriid) {
		return dal.getCoRim(coriid);
	}

	// 根据id获得对象
	public ResponsbilityBookModel getRbbm(int rebk_id) {

		return dal.getRbbm(rebk_id);
	}

	// 计生责任书新增就业登记表
	public int addRbb(CoOnlineRegisterInfoModel m) {

		return dal.addRbb(m);
	}
}
