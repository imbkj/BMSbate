package impl;

import java.util.List;

import dal.CoHousingFund.CoHousingFund_PwdDal;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.SingleDalFactory;
import service.CoHousingFundPwdService;

/**
 * 密钥专办员业务实现类
 * 
 * @author Administrator
 * 
 */
public class CoHousingFundPwdImpl implements CoHousingFundPwdService {

	CoHousingFund_PwdDal cfpd = SingleDalFactory.getInstance().getCfpd();

	@Override
	public int addPwd(CoHousingFundPwdChangeModel cfpc) {
		return cfpd.addPwd(cfpc);
	}

	@Override
	public int addPwdc(CoHousingFundPwdChangeModel cfpc) {
		return cfpd.addPwdc(cfpc);
	}

	@Override
	public boolean updatePwd(CoHousingFundPwdModel cfpm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delPwd(CoHousingFundPwdModel cfpm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<CoHousingFundPwdModel> getCoHousingFundPwd() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addPassWord(CoHousingFundPwdModel p){
		cfpd.addPassWord(p);
	}
	
	@Override
	public List<CoHousingFundPwdModel> getCoHousingFundPwdById(int cohf_id) {

		return cfpd.getCoHousingFundPwdById(cohf_id);
	}

	/**
	 * 前到添加密钥信息
	 */
	@Override
	public int addPwdChange(CoHousingFundPwdChangeModel cfpc, String sign) {

		return cfpd.addPwdChange(cfpc, sign);
	}

	/**
	 * 后到列表
	 */
	@Override
	public List<CoHousingFundPwdChangeModel> getPwdListChange() {
		return cfpd.getPwdListChange();
	}

	@Override
	public List<CoHousingFundPwdChangeModel> getPwdListChangeByCondition(
			String queryCondition) {

		return cfpd.getPwdListChangeByCondition(queryCondition);
	}

	/**
	 * 该单位该专办员是否存在密钥
	 * 
	 * @param cohf_id
	 * @param addZbid
	 * @return
	 */
	public int havePwd(int cohf_id, int addZbid) {
		return cfpd.havePwd(cohf_id, addZbid);
	}

	@Override
	public List<CoHousingFundPwdModel> allPwd(int cohf_id) {

		return cfpd.allPwd(cohf_id);
	}

	@Override
	public int renewalPwd(CoHousingFundPwdChangeModel cfpc) {

		return cfpd.renewalPwd(cfpc);
	}

	@Override
	public int renewalPwdc(CoHousingFundPwdChangeModel cfpc) {

		return cfpd.renewalPwdc(cfpc);
	}

	@Override
	public int changeStatus(CoHousingFundPwdChangeModel cfcm) {

		return cfpd.changeStatus(cfcm);
	}

	@Override
	public List<CoHousingFundZbModel> notPwdZb(int cohf_id) {
		return cfpd.notPwdZb(cohf_id);
	}

	@Override
	public int changePwd(CoHousingFundPwdModel cfpm, int chfp_id) {
		return cfpd.changePwd(cfpm, chfp_id);
	}

	@Override
	public int changePwdc(CoHousingFundPwdModel cfpm, int chfp_id,int sbstate) {
		return cfpd.changePwdc(cfpm, chfp_id,sbstate);
	}

	@Override
	public int resubmitPwdChange(CoHousingFundPwdChangeModel cfpm) {
		return cfpd.resubmitPwdChange(cfpm);
	}

	@Override
	public CoHousingFundPwdChangeModel getDate(int cfpc) {
		return cfpd.getDate(cfpc);
	}

	@Override
	public List<CoHousingFundPwdChangeModel> queryPwd() {
		return cfpd.queryPwd();
	}

	@Override
	public List<CoHousingFundPwdChangeModel> queryPwdByCondition(
			String queryCondition) {

		return cfpd.queryPwdByCondition(queryCondition);
	}

	@Override
	public CoHousingFundPwdChangeModel getPwdChangeByid(int cfpc_id) {
		return cfpd.getPwdChangeByid(cfpc_id);
	}

	@Override
	public CoHousingFundPwdModel getPwdByid(int chfp_id) {
		return cfpd.getPwdByid(chfp_id);
	}

	@Override
	public int UpdatePwdZb(CoHousingFundPwdChangeModel m) {
		return cfpd.UpdatePwdZb(m);
	}

	@Override
	public int UpdateLimit(CoHousingFundPwdChangeModel m) {
		return cfpd.UpdateLimit(m);
	}

	@Override
	public int UpdateLimit1(CoHousingFundPwdChangeModel m) {
		return cfpd.UpdateLimit1(m);
	}

	@Override
	public int delPwdd(int cfpc_id) {
		return cfpd.delPwdd(cfpc_id);
	}

	@Override
	public int getcohfid(int cid) {
		
		return cfpd.getcohfid(cid);
	}
}
