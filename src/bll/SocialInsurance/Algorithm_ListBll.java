/**
 * 
 */
/**
 * @author Lee
 *
 */
package bll.SocialInsurance;

import java.util.List;

import Model.CoAgencyBaseCityRelViewModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceChangeModel;
import dal.SocialInsurance.AlgorithmDal;

public class Algorithm_ListBll {
	AlgorithmDal dal = new AlgorithmDal();

	// 获取机构及城市信息数据集
	public List<CoAgencyBaseCityRelViewModel> getcoAgBaseCityRelList() {
		List<CoAgencyBaseCityRelViewModel> list = dal.getCoAgBaseCityRel();
		return list;
	}

	// 获取机构数据
	public List<String> getCoAgBase() {
		List<String> list = dal.getCoAgBase();
		return list;
	}

	// 获取机构性质数据集
	public List<String> getSetuptype() {
		List<String> list = dal.getSetuptype();
		return list;
	}

	// 获取机构服务省份数据
	public List<String> getCoAgProvince() {
		List<String> list = dal.getCoAgProvince();
		return list;
	}

	// 获取机构服务城市数据
	public List<String> getCoAgCity() {
		List<String> list = dal.getCoAgCity();
		return list;
	}

	// 根据省份获取城市数据
	public List<String> getCoAgCityByPro(String pro) {
		List<String> list = dal.getCoAgCityByPro(pro);
		return list;
	}

	// 查询现用社保或历史社保算法(1现用及待用2历史)
	public List<SocialInsuranceAlgorithmViewModel> getSiAlgorithmSel(
			int cabc_id, int type) {
		List<SocialInsuranceAlgorithmViewModel> list = dal.getSiAlgorithmSel(
				cabc_id, type);
		return list;
	}

	// 查询待审核的社保算法
	public List<SocialInsuranceChangeModel> getSiAlgorithmChangeList(int cabc_id) {
		return dal.getSiAlgorithmChangeList(cabc_id);
	}

}