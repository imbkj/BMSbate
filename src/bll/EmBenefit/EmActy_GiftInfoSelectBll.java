package bll.EmBenefit;

import java.util.List;

import Model.EmActyGiftBackInfoModel;
import Model.EmActyGiftOutInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActySupplierInfoModel;
import Model.EmWelfareModel;
import dal.EmBenefit.EmActy_GiftInfoSelectDal;

public class EmActy_GiftInfoSelectBll {
	private EmActy_GiftInfoSelectDal dal = new EmActy_GiftInfoSelectDal();

	// 查询员工活动——礼品库存信息
	public List<EmActySuppilerGiftInfoModel> getEmActyGiftInfo(String str) {
		return dal.getEmActyGiftInfo(str);
	}

	// 获取供应商名单
	public List<EmActySupplierInfoModel> getSupName() {
		return dal.getSupName();
	}

	// 获取礼品类型
	public List<String> getGiftType() {
		return dal.getGiftType();
	}

	// 查询员工活动——出库信息
	public List<EmActyGiftOutInfoModel> getEmActyGiftOutInfo(String str) {
		return dal.getEmActyGiftOutInfo(str);
	}

	// 查询员工活动——采购申请退回信息
	public List<EmActyGiftBackInfoModel> getEmActyGiftBackInfo(String str) {
		return dal.getEmActyGiftBackInfo(str);
	}

	// 获取统计数据
	public List<EmWelfareModel> getWfCount(String str) {
		return dal.getWfCount(str);
	}

	// 获取 select * from EmActySuppilerGiftInfo 表的id
	public Integer getTarpId(String str) {
		return dal.getTarpId(str);
	}

	// 获取 EmActySuppilerGiftInfo 表的id
	public String getSortId(String str) {
		return dal.getSortId(str);
	}

	// 获取EmWelfare 表的任务id
	public Integer getEmWelfare(String str) {
		return dal.getEmWelfare(str);
	}

	// 根据cid查询账单号
	public String getBillByCid(int cid, int ownmonth) {
		return dal.getBillByCid(cid, ownmonth);
	}
}
