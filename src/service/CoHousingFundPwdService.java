package service;

import java.util.List;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbModel;

/**
 * 密钥专办员业务接口
 * 
 * @author Administrator
 * 
 */
public interface CoHousingFundPwdService {

	public void addPassWord(CoHousingFundPwdModel p);
	public int getcohfid(int cid);

	public int delPwdd(int cfpc_id);

	public int UpdateLimit1(CoHousingFundPwdChangeModel m);

	public int UpdateLimit(CoHousingFundPwdChangeModel m);

	public int UpdatePwdZb(CoHousingFundPwdChangeModel m);

	public CoHousingFundPwdChangeModel getPwdChangeByid(int cfpc_id);

	public List<CoHousingFundPwdChangeModel> queryPwd();

	public List<CoHousingFundPwdChangeModel> queryPwdByCondition(
			String queryCondition);

	public CoHousingFundPwdChangeModel getDate(int cfpc);

	public CoHousingFundPwdModel getPwdByid(int chfp_id);

	/**
	 * 重新提交
	 * 
	 * @param cfpm
	 * @return
	 */
	public int resubmitPwdChange(CoHousingFundPwdChangeModel cfpm);

	/**
	 * 变更密钥专办员
	 * 
	 * @param chfz_id
	 * @param chfp_id
	 * @return
	 */
	public int changePwd(CoHousingFundPwdModel cfpm, int chfp_id);

	public int changePwdc(CoHousingFundPwdModel cfpm, int chfp_id, int sbstate);

	/**
	 * 获取该单位没有密钥的专办员
	 * 
	 * @param cohf_id
	 * @return
	 */
	public List<CoHousingFundZbModel> notPwdZb(int cohf_id);

	public int changeStatus(CoHousingFundPwdChangeModel cfcm);

	/**
	 * 密钥续期
	 * 
	 * @param cfpm
	 * @return
	 */
	public int renewalPwd(CoHousingFundPwdChangeModel cfpc);

	public int renewalPwdc(CoHousingFundPwdChangeModel cfpc);

	public List<CoHousingFundPwdModel> allPwd(int cohf_id);

	/**
	 * 新增专办员
	 * 
	 * @param cfzm
	 * @return
	 */
	public int addPwd(CoHousingFundPwdChangeModel cfpc);

	public int addPwdc(CoHousingFundPwdChangeModel cfpm);

	/**
	 * 专办员信息变更
	 * 
	 * @param cfzm
	 * @return
	 */
	public boolean updatePwd(CoHousingFundPwdModel cfpm);

	/**
	 * 注销专办员
	 * 
	 * @param cfzm
	 * @return
	 */
	public boolean delPwd(CoHousingFundPwdModel cfpm);

	/**
	 * 查询专办员信息
	 * 
	 * @return
	 */
	public List<CoHousingFundPwdModel> getCoHousingFundPwd();

	/**
	 * 根据id查询专办员
	 * 
	 * @param ID
	 * @return
	 */
	public List<CoHousingFundPwdModel> getCoHousingFundPwdById(int ID);

	/**
	 * 前到申报密钥信息
	 * 
	 * @param cfpc
	 * @return
	 */
	public int addPwdChange(CoHousingFundPwdChangeModel cfpc, String sign);

	/**
	 * 后到列表
	 * 
	 * @return
	 */
	public List<CoHousingFundPwdChangeModel> getPwdListChange();

	/**
	 * 后到条件查询
	 * 
	 * @param queryCondition
	 * @return
	 */
	public List<CoHousingFundPwdChangeModel> getPwdListChangeByCondition(
			String queryCondition);

	/**
	 * 该单位该专办员是否存在密钥
	 * 
	 * @param cohf_id
	 * @param addZbid
	 * @return
	 */
	public int havePwd(int cohf_id, int addZbid);

}
