package bll.EmHouseCard;

import java.util.List;

import Model.CoHousingFundModel;
import Model.EmHouseCardBackInfoModel;
import Model.EmHouseCardFailInfoModel;
import Model.EmHouseTakeCardInfoModel;
import dal.EmHouseCard.EmHouse_TakeCardInfoSelectDal;

public class EmHouse_TakeCardInfoSelectBll {
	private EmHouse_TakeCardInfoSelectDal dal = new EmHouse_TakeCardInfoSelectDal();

	// 查询公积金领卡信息
	public List<EmHouseTakeCardInfoModel> getEmhouseTakeCardInfo(String str) {
		return dal.getEmhouseTakeCardInfo(str);
	}

	// 查询领卡信息的所属月份和申报月份
	public List<String> getOwnmonthInfo(String searchinfo, String str,
			String getInfo) {
		return dal.getOwnmonthInfo(searchinfo, str, getInfo);
	}

	// 查询状态
	public List<EmHouseTakeCardInfoModel> getStateInfo(String str) {
		return dal.getStateInfo(str);
	}

	public boolean ifExist(String ownmonth, String no) {
		return dal.ifExist(ownmonth, no);
	}

	// 判断制卡信息中是否存在该员工的所属月份的制卡信息
	public EmHouseTakeCardInfoModel getInfo(String sfcard, String ownmonth,
			String str) {
		return dal.getInfo(sfcard, ownmonth, str);
	}

	// 查询员工的公积金申报信息
	public EmHouseTakeCardInfoModel getEmhouseCahneInfo(String str) {
		return dal.getEmhouseCahneInfo(str);
	}

	// 查询状态
	public EmHouseTakeCardInfoModel getStateId(String str) {
		return dal.getStateId(str);
	}

	// 统计数据
	public Integer[] getCountInfo() {
		return dal.getCountInfo();
	}

	// 获取公积金领卡银行名称
	public List<String> getBankInfo() {
		return dal.getBankInfo();
	}

	// 获取公积金卡退回信息
	public List<EmHouseCardBackInfoModel> getEmhouseCardBackInfo(String str) {
		return dal.getEmhouseCardBackInfo(str);
	}

	// 根据领卡Id获取退回原因
	public EmHouseCardBackInfoModel getBackCase(String id) {
		EmHouseCardBackInfoModel model = new EmHouseCardBackInfoModel();
		String sql = " and backlei=2 and backleiname='领卡' and Back_CardId="
				+ id;
		List<EmHouseCardBackInfoModel> list = getEmhouseCardBackInfo(sql);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 获取备注
	public List<EmHouseCardFailInfoModel> getEmhouseRemarkCardInfo(String str) {
		return dal.getEmhouseRemarkCardInfo(str);
	}

	// 查询清册
	public List<EmHouseTakeCardInfoModel> getOutEmhouseTakeCardInfo(String str) {
		return dal.getOutEmhouseTakeCardInfo(str);
	}

	// 查询单位公积金号是否相同
	public int isSameCompanyId(String strid) {
		return dal.isSameCompanyId(strid);
	}

	// 查询单位公积金号
	public CoHousingFundModel getCompanyId(String strid) {
		return dal.getCompanyId(strid);
	}

	// 查询是否所有的都是中智户
	public boolean iFCiicCompanyId(String strid) {
		return dal.iFCiicCompanyId(strid);
	}

	// 查询是否所有的领卡银行都是招行
	public boolean iFZH(String strid) {
		return dal.iFZH(strid);
	}

	// 查询是否所有的领卡银行都是招行
	public boolean iFJH(String strid) {
		return dal.iFJH(strid);
	}

	// 查询单位
	public CoHousingFundModel getCompany(String strid) {
		return dal.getCompany(strid);
	}

	// 查询单位
	public CoHousingFundModel getCompanyByCid(int cid) {
		return dal.getCompanyByCid(cid);
	}

	// 查询公积金单位数量
	public int getCompanyCount(String strid) {
		return dal.getCompanyCount(strid);
	}

	// 查询是否所有的领卡银行都是招行
	public boolean iFSameCompany(String strid) {
		return dal.iFSameCompany(strid);
	}

	// 根据gid查询员工状态
	public String getEmbaState(Integer gid) {
		return dal.getEmbaState(gid);
	}

	// 查询员工手机号码、
	public String getEmba_mobile(int gid) {
		return dal.getEmba_mobile(gid);
	}
}
