package bll.SocialInsurance;

import java.util.ArrayList;

import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import Model.SocialInsuranceClassInfoViewModel;
import dal.SocialInsurance.AlgorithmChangeDal;

public class AlgorithmChangeBll {
	private AlgorithmChangeDal dal;

	public AlgorithmChangeBll() {
		dal = new AlgorithmChangeDal();
	}

	// 获取社保字典库变更信息数据(type:更变类型(1新增标准or更新算法or调整算法2停用标准or停用算法))
	public SocialInsuranceChangeModel getSocialInsuranceChange(int sich_id) {
		return dal.getSocialInsuranceChange(sich_id);
	}

	// 根据sich_id获取算法变更基本信息
	public SocialInsuranceAlgorithmViewModel getSiAlChange(int sich_id) {
		return dal.getSiAlChange(sich_id);
	}

	// 根据siac_id获取算法项目详细信息
	public ArrayList<SocialInsuranceClassInfoViewModel> getSiAlInfoChange(
			int siac_id) {
		return dal.getSiAlInfoChange(siac_id);
	}

	// 获取变更前的算法项目
	public SocialInsuranceClassInfoViewModel getOldSiAlInfoChange(
			int siai_sial_id, int siai_sicl_id) {
		return dal.getOldSiAlInfoChange(siai_sial_id, siai_sicl_id);
	}
}
