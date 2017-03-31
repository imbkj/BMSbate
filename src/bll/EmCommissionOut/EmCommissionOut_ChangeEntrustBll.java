package bll.EmCommissionOut;

import java.sql.SQLException;
import java.util.List;

import dal.EmCommissionOut.EmCommissionOut_ChangeEntrustDel;
import Model.EmCommissionOutChangeEntrustModel;


public class EmCommissionOut_ChangeEntrustBll {
	EmCommissionOut_ChangeEntrustDel dal = new EmCommissionOut_ChangeEntrustDel();
	    // 获取委托服务项目费用列表
		public List<EmCommissionOutChangeEntrustModel> getEmCommissionOut_ChangeEntrustList(String str) throws SQLException {
			return dal.getEmCommissionOut_ChangeEntrustList(str);
		}
		// 获取委托地列表
		public List<EmCommissionOutChangeEntrustModel> getCityList() {
			return dal.getCityList();
		}
		// 获取客服列表
		public List<EmCommissionOutChangeEntrustModel> getCobaClientList(){
			return dal.getCobaClientList();
		}

}
