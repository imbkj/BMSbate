package bll.CoAgency;

import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyBaseModel;
import dal.CoAgency.CoAgencyBaseDal;
import dal.CoAgency.CoAgencyBaseOperateDal;

public class BaseInfo_BaseDisListBll {

	// 根据服务城市获取委托机构
	public List<CoAgencyBaseCityRelViewModel> getCoAgBaseListByCity(String city) {
		CoAgencyBaseDal dal = new CoAgencyBaseDal();
		return dal.getCoAgBaseListByCity(city);
	}

	// 删除机构分配操作
	public String[] DelDisBasefromCity(int coab_id, String city, String addname) {
		CoAgencyBaseOperateDal dal = new CoAgencyBaseOperateDal();
		String[] message = new String[2];
		int result = 0;
		try {
			result = dal.DelDisBasefromCity(coab_id, city, addname);
			if (result == 0) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}

	// 设置城市默认机构
	public String[] SetDefaultAgency(int cabc_id) {
		CoAgencyBaseOperateDal dal = new CoAgencyBaseOperateDal();
		String[] message = new String[2];
		int result = 0;
		try {
			result = dal.SetDefaultAgency(cabc_id);
			if (result == 1) {
				message[0] = "1";
				message[1] = "操作成功!";
			} else {
				message[0] = "0";
				message[1] = "操作失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "操作出错。";
		}
		return message;
	}


}
