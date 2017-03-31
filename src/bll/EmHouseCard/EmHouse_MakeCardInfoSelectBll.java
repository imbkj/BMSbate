package bll.EmHouseCard;

import java.util.List;

import dal.EmHouseCard.EmHouse_MakeCardInfoSelectDal;

import Model.EmHouseMakeCardInfoModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;

public class EmHouse_MakeCardInfoSelectBll {
	private EmHouse_MakeCardInfoSelectDal dal = new EmHouse_MakeCardInfoSelectDal();

	// 查询制卡信息
	public List<EmHouseMakeCardInfoModel> getEmhouseMakeCardInfo(String str) {
		return dal.getEmhouseMakeCardInfo(str);
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		return dal.getLoginInfo();
	}

	// 获取制卡银行信息
	public List<String> getBankInfo() {
		return dal.getBankInfo();
	}

	// 根据员工Gid查询公积金在册表
	public EmHouseUpdateModel getEmhouseInfo(String gid) {
		return dal.getEmhouseInfo(gid);
	}

	// 获取单条制卡数据
	public EmHouseMakeCardInfoModel getMakeCardInfo(String id) {
		return dal.getMakeCardInfo(id);
	}

	// 查询员工列表
	public List<EmbaseModel> getEmBaseInfo(String str) {
		return dal.getEmBaseInfo(str);
	}

	// 根据条件查询员工信息
	public EmbaseModel getEmBaseModel(String str) {
		return dal.getEmBaseModel(str);
	}

	// 获取公积金账户类型
	public String getZhType(Integer gid) {
		String zhType = dal.getZhType(gid);// 先查询emhousechange表
		if (zhType == null || zhType.equals("")) {
			zhType = dal.getZhTypes(gid);// 当emhousechange表没有数据时就查询emhouseupdate表
		}
		return zhType;
	}

}
