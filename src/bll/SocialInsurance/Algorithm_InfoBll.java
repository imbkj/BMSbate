package bll.SocialInsurance;

import java.util.ArrayList;
import java.util.List;

import Model.CoAgencyBaseCityRelViewModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutModel;
import Model.EmShebaoUpdateModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import Model.SocialInsuranceDecimalModel;
import Model.SocialInsuranceStandardModel;
import Util.DateStringChange;
import dal.SocialInsurance.AlgorithmDal;
import dal.SocialInsurance.AlgorithmInfoDal;

public class Algorithm_InfoBll {
	private AlgorithmInfoDal dal = new AlgorithmInfoDal();

	// 获取项目及类型列表
	public ArrayList<SocialInsuranceClassInfoViewModel> getSiClass(int cabc_id) {
		ArrayList<SocialInsuranceClassInfoViewModel> list = dal
				.getSiClass(cabc_id);
		return list;
	}

	// 获取项目小数处理列表
	public ArrayList<SocialInsuranceDecimalModel> getDecimal() {
		ArrayList<SocialInsuranceDecimalModel> list = dal.getDecimal();
		return list;
	}

	// 获取社保或公积金标准(type:1社保2公积金)
	public ArrayList<SocialInsuranceStandardModel> getStandard(int type) {
		ArrayList<SocialInsuranceStandardModel> list = dal.getStandard(type);
		return list;
	}

	// 根据ID获取机构名称及服务城市
	public CoAgencyBaseCityRelViewModel getBaseCityById(int id) {
		CoAgencyBaseCityRelViewModel m = dal.getBaseCityById(id);
		return m;
	}

	// 根据sial_id获取算法基本信息
	public SocialInsuranceAlgorithmViewModel getSiAlBase(int sial_id) {
		SocialInsuranceAlgorithmViewModel m = dal.getSiAlBase(sial_id);
		return m;
	}

	// 根据sial_id获取算法项目详细信息
	public ArrayList<SocialInsuranceClassInfoViewModel> getSiAlInfo(int sial_id) {
		ArrayList<SocialInsuranceClassInfoViewModel> list = dal
				.getSiAlInfo(sial_id);
		return list;
	}

	// 获取算法的执行年月列表数据
	public ArrayList<String[]> getExecdateList(int sial_id) {
		ArrayList<String[]> list = dal.getExecdateList(sial_id);
		return list;
	}

	// 查找在册数据
	public List<EmCommissionOutModel> getRegDataBySoinId(int soin_id) {
		return dal.getRegDataBySoinId(soin_id);
	}

	// 根据soin_id查找在册表的明细数据
	public List<EmCommissionOutFeeDetailChangeModel> getRegDataFeeDetailBySoinId(
			int sial_id) {
		return dal.getRegDataFeeDetailBySoinId(sial_id);
	}

	// 根据soin_id查找变更表的明细数据
	public List<EmCommissionOutFeeDetailChangeModel> getRegDataFeeDetailChangeBySoinId(
			int sial_id) {
		return dal.getRegDataFeeDetailChangeBySoinId(sial_id);
	}

	// 根据算法名称查找本地社保在册数据
	public List<EmShebaoUpdateModel> getLocalShebaoUpdate(String soin_title,
			int ownmonth) {
		return dal.getLocalShebaoUpdate(soin_title, ownmonth);
	}

	// 根据公司编号和员工编号 更新本地社保在册数据
	public String[] upLocalShebaoUpdate(Integer cid, Integer gid) {
		String[] message = new String[2];
		message[0] = "1";
		message[1] = "操作成功!";

		// 获取本地现用所有算法
		AlgorithmDal aDal = new AlgorithmDal();
		List<SocialInsuranceAlgorithmViewModel> list = new ArrayList<SocialInsuranceAlgorithmViewModel>();
		list = aDal.getSiAlgorithmSel(1, 1);

		String str = "";
		if (cid != null && cid != 0) {
			str = str + " and cid=" + cid;
		}
		if (gid != null && gid != 0) {
			str = str + " and gid=" + gid;
		}

		List<EmShebaoUpdateModel> sbList;
		for (int i = 0; i < list.size(); i++) {

			// 根据公司编号和员工编号 查找本地社保在册数据
			sbList = new ArrayList<EmShebaoUpdateModel>();
			SocialInsuranceAlgorithmViewModel saModel = new SocialInsuranceAlgorithmViewModel();
			saModel = getSiAlBase(list.get(i).getSial_id());
			sbList = dal.getLocalShebaoUpdateByCidGid(
					saModel.getSoin_title(),
					Integer.parseInt(DateStringChange.DatetoSting(
							saModel.getSial_execdate(), "yyyyMM")), str);
			
			if (sbList.size() > 0) {
				// 更新社保在册数据
				Algorithm_RegisteredDataBll opBll = new Algorithm_RegisteredDataBll();
				opBll.upLocalRegData(saModel.getSoin_id(), list.get(i)
						.getSial_id(), saModel.getSial_execdate(), sbList);
			}

		}

		return message;
	}
}
