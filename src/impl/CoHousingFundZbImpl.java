package impl;

import java.util.List;

import dal.CoHousingFund.CoHousingFundZbDal;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.SingleDalFactory;
import service.CoHousingFundZbService;

/**
 * 专办员业务实现类
 * 
 * @author Administrator
 * 
 */
public class CoHousingFundZbImpl implements CoHousingFundZbService {

	CoHousingFundZbDal cfzd = SingleDalFactory.getInstance().getChzd();

	@Override
	public int addZbc(CoHousingFundZbModel cfzm, int state) {

		return cfzd.addZbc(cfzm, state);
	}
	
	@Override
	public int addZbChange(CoHousingFundZbChangeModel cfzm, int state) {

		return cfzd.addZbChange(cfzm, state);
	}

	@Override
	public int addZb(CoHousingFundZbChangeModel cfzm) {

		return cfzd.addZb(cfzm);
	}

	@Override
	public boolean updateZb(CoHousingFundZbChangeModel cfzm, int chfz_id) {
		boolean flag = false;
		if (cfzd.updateZb(cfzm, chfz_id) != 0) {
			flag = true;
		}
		return flag;
	}

	@Override
	public int delZb(CoHousingFundZbChangeModel cfzc) {
		return cfzd.delZb(cfzc);
	}

	@Override
	public int delZbc(CoHousingFundZbChangeModel cfzc) {
		return cfzd.delZbc(cfzc);
	}

	@Override
	public List<CoHousingFundZbModel> getCoHousingFundZb() {

		return null;
	}

	@Override
	public CoHousingFundZbModel getCoHousingFundZbById(int chfz_id) {

		return cfzd.getCoHousingFundZbById(chfz_id);
	}

	@Override
	public List<CoHousingFundModel> getCoHousingFundList() {

		return SingleDalFactory.getInstance().getChzd().getCoHousingFundList();
	}

	/**
	 * 按条件查询申办信息
	 */
	@Override
	public List<CoHousingFundModel> getChfzListByCondition(String str) {

		return cfzd.getChfzListByCondition(str);
	}

	@Override
	public CoHousingFundModel getCoHousingFundListById(int cid) {

		return cfzd.getCoHousingFundListById(cid);
	}

	/**
	 * 查询单位专办员信息
	 */
	@Override
	public List<CoHousingFundZbModel> getZbListById(int cohf_id) {

		return cfzd.getZbListById(cohf_id);
	}

	/**
	 * 查询单位密钥信息
	 */
	@Override
	public List<CoHousingFundPwdModel> getPwdListById(int cohf_id) {

		return cfzd.getPwdListById(cohf_id);
	}

	/**
	 * 查询单位是否存在密钥信息
	 */
	@Override
	public int isHaveZb(int cohf_id) {

		return cfzd.isHaveZb(cohf_id);
	}

	/**
	 * 前到新增专办员
	 */
	@Override
	public int addZbChange(CoHousingFundZbChangeModel cfzb) {

		return cfzd.addZbChange(cfzb);
	}

	/**
	 * 查询专办员
	 */
	@Override
	public List<CoHousingFundZbModel> getZbListBycohf_id(int cohf_id) {

		return cfzd.getZbListBycohf_id(cohf_id);
	}

	/**
	 * 查询专办员
	 */
	@Override
	public List<CoHousingFundZbModel> getNotPwdZb(int cohf_id) {

		return cfzd.getNotPwdZb(cohf_id);
	}

	/**
	 * 条件查询前到录入的信息
	 */
	@Override
	public List<CoHousingFundZbChangeModel> getChfzListChangeByCondition(
			String condition) {
		return cfzd.getChfzListChangeByCondition(condition);
	}

	@Override
	public List<CoHousingFundZbChangeModel> getChfzListChange() {

		return cfzd.getChfzListChange();
	}

	/**
	 * 后到修改申报状态
	 */
	@Override
	public int changeStatus(CoHousingFundZbChangeModel cfzc) {
		return cfzd.changeStatus(cfzc);
	}

	@Override
	public CoHousingFundZbChangeModel addtype(int cfzc_id) {
		return cfzd.addtype(cfzc_id);
	}

	@Override
	public CoHousingFundZbModel getZbChangeInfo(int chfz_id) {
		return cfzd.getZbChangeInfo(chfz_id);
	}

	@Override
	public int ZbCompelete(String r, int chfz_id) {

		return cfzd.ZbCompelete(r, chfz_id);
	}

	@Override
	public int isHavePwd(int cohf_id, int chfz_id) {

		return cfzd.isHavePwd(cohf_id, chfz_id);
	}

	@Override
	public List<CoHousingFundModel> queryZb() {

		return cfzd.queryZb();
	}

	@Override
	public List<CoHousingFundModel> queryZbC() {

		return cfzd.queryZbC();
	}

	@Override
	public List<CoHousingFundModel> queryZbByCondition(String Condition) {

		return cfzd.queryZbByCondition(Condition);
	}

	@Override
	public List<CoHousingFundModel> queryZbByC(String Condition) {

		return cfzd.queryZbByC(Condition);
	}

	@Override
	public int resubmitZb(CoHousingFundZbChangeModel cfzc) {

		return cfzd.resubmitZb(cfzc);
	}

	@Override
	public CoHousingFundModel getCohf(int cohf_id) {
		return cfzd.getCohf(cohf_id);
	}

	@Override
	public CoHousingFundZbChangeModel ZbChangeInfo(int cfzc_id) {
		return cfzd.ZbChangeInfo(cfzc_id);
	}

	@Override
	public CoHousingFundZbModel getNewZbInfo(int chfz_id) {
		return cfzd.getNewZbInfo(chfz_id);
	}

	@Override
	public int updateCusZbChange(CoHousingFundZbChangeModel c) {
		return cfzd.updateCusZbChange(c);
	}

	@Override
	public List<CoHousingFundModel> getCoHousingFundListbyCondition(String str) {
		return cfzd.getCoHousingFundListbyCondition(str);
	}

	@Override
	public int UpdateZb(int chfz_id, int cfzc_id) {
		return cfzd.UpdateZb(chfz_id, cfzc_id);
	}

	@Override
	public int delSb(int cfzc_id) {

		return cfzd.delSb(cfzc_id);
	}

	@Override
	public CoHousingFundZbModel getZb(int chfz_id) {

		return cfzd.getZb(chfz_id);
	}

}
