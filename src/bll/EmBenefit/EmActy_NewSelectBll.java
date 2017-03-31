package bll.EmBenefit;

import java.util.List;

import Model.EmActyProduceModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.EmactyUseHouseLogModel;
import dal.EmBenefit.EmActy_NewSelectDal;

public class EmActy_NewSelectBll {
	private EmActy_NewSelectDal dal = new EmActy_NewSelectDal();

	public List<EmActySuppilerGiftInfoModel> getEmActyGiftInfo(int gift_id) {
		return dal.getEmActyGiftInfo(gift_id);
	}

	public EmActySuppilerGiftInfoModel getGiftInfo(int gift_id) {
		List<EmActySuppilerGiftInfoModel> list = getEmActyGiftInfo(gift_id);
		EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
		if (list.size() > 0) {
			m = list.get(0);
		}
		return m;
	}

	// 根据批次编号查询员工福利信息
	public List<EmWelfareModel> getEmWelfareList(String scortId) {
		return dal.getEmWelfareList(scortId);
	}

	// 根据批次编号查询员工福利信息
	public List<EmWelfareModel> getEmWelfareInfoList(String scortId) {
		return dal.getEmWelfareInfoList(scortId);
	}

	// 根据批次编号查询员工福利信息_采购礼品
	public List<EmWelfareModel> getEmWelfareInfoListBySortId(String scortId,
			String str) {
		return dal.getEmWelfareInfoListBySortId(scortId, str);
	}
	
	public List<EmactyUseHouseLogModel> getEmactyUseHouseLogModel(String useh_sortid) {
		return dal.getEmactyUseHouseLogModel(useh_sortid);
	}

	// 查询库存
	public List<EmActyWarehouseModel> getEmWelfareHouse(String emwf_idstr) {
		return dal.getEmWelfareHouse(emwf_idstr);
	}

	// 查询采购总数
	public int getEmwfProdNum(String str) {
		return dal.getEmwfProdNum(str);
	}
	
	public EmActyProduceModel getEmwfProdPrice(int prod_id) {
		return dal.getEmwfProdPrice(prod_id);
	}
}
