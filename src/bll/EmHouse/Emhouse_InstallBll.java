package bll.EmHouse;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.CoHousingFund.CoHousingFund_OperateDal;
import dal.EmHouse.Emhouse_InstallDal;

import Model.CoHousingFundModel;
import Model.EmHouseSetupModel;

public class Emhouse_InstallBll {
	Emhouse_InstallDal dal = new Emhouse_InstallDal();

	// 查询公积金设置信息
	public EmHouseSetupModel getEmHouseSetupInfo() {
		return dal.getEmHouseSetupInfo();
	}

	// 更新公积金设置信息
	public int updateEmhouseInstall(EmHouseSetupModel m) {
		int k = 0;
		k = dal.EmHouseSetupUpdate(m);
		return k;
	}

	// 查询独立户截单信息
	public List<CoHousingFundModel> lastInfo() {
		List<CoHousingFundModel> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		CoHousingFundModel cm = new CoHousingFundModel();
		cm.setCohf_state(1);
		cm.setCohf_single(1);
		list = dal.getlist(cm);
		return list;
	}

	// 查询独立户截单信息
	public List<CoHousingFundModel> lastInfo(CoHousingFundModel cm) {
		List<CoHousingFundModel> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		
		cm.setCohf_state(1);
		cm.setCohf_single(1);
		list = dal.getlist(cm);
		return list;
	}
	
	//修改独立户截单日
	public Integer modLastDay(CoHousingFundModel cm){
		Integer i=0;
		CoHousingFund_OperateDal dal = new CoHousingFund_OperateDal();
		i=dal.mod(cm.getCohf_lastday(), cm.getCohf_id());
		return i;
	}
}
