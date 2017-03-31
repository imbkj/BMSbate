package service;

import java.util.List;

import Model.CoHousingFundModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;

/**
 * 专办员业务接口
 * 
 * @author Administrator
 * 
 */
public interface CoHousingFundZbService {

	public CoHousingFundZbModel getZb(int chfz_id);
	public int delSb(int cfzc_id);
	public int UpdateZb(int chfz_id,int cfzc_id);
	public int updateCusZbChange(CoHousingFundZbChangeModel c);

	public CoHousingFundZbModel getNewZbInfo(int chfz_id);

	public CoHousingFundZbChangeModel ZbChangeInfo(int cfzc_id);

	public CoHousingFundModel getCohf(int cohf_id);

	public int resubmitZb(CoHousingFundZbChangeModel cfzc);

	public List<CoHousingFundModel> queryZbByCondition(String Condition);

	public List<CoHousingFundModel> queryZbByC(String Condition);

	/**
	 * 查询专办员
	 * 
	 * @return
	 */
	public List<CoHousingFundModel> queryZb();

	public List<CoHousingFundModel> queryZbC();

	/**
	 * 专办员业务完成
	 * 
	 * @param r
	 * @return
	 */
	public int ZbCompelete(String r, int chfz_id);

	/**
	 * 新增专办员
	 * 
	 * @param cfzm
	 * @return
	 */
	public int addZb(CoHousingFundZbChangeModel cfzm);

	public int addZbc(CoHousingFundZbModel cfzm,int state);
	public int addZbChange(CoHousingFundZbChangeModel cfzm,int state);
	/**
	 * 专办员信息变更
	 * 
	 * @param cfzm
	 * @return
	 */
	public boolean updateZb(CoHousingFundZbChangeModel cfzm, int chfz_id);

	/**
	 * 注销专办员
	 * 
	 * @param cfzm
	 * @return
	 */
	public int delZb(CoHousingFundZbChangeModel cfzc);

	public int delZbc(CoHousingFundZbChangeModel cfzc);

	/**
	 * 查询专办员信息
	 * 
	 * @return
	 */
	public List<CoHousingFundZbModel> getCoHousingFundZb();

	/**
	 * 根据id查询专办员
	 * 
	 * @param ID
	 * @return
	 */
	public CoHousingFundZbModel getCoHousingFundZbById(int chfz_id);

	/**
	 * 查询单位公积金信息
	 * 
	 * @return
	 */
	public List<CoHousingFundModel> getCoHousingFundList();

	public List<CoHousingFundZbChangeModel> getChfzListChange();

	/**
	 * 按条件查询申办信息
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundModel> getChfzListByCondition(String str);

	public List<CoHousingFundModel> getCoHousingFundListbyCondition(String str);

	/**
	 * 按条件查询前到申办
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundZbChangeModel> getChfzListChangeByCondition(
			String condition);

	/**
	 * 查询单位公积金详细信息
	 * 
	 * @param cid
	 * @return
	 */
	public CoHousingFundModel getCoHousingFundListById(int cid);

	/**
	 * 查询单位专办员信息
	 * 
	 * @param cohf_id
	 * @return
	 */
	public List<CoHousingFundZbModel> getZbListById(int cohf_id);

	/**
	 * 查询单位密钥信息
	 * 
	 * @param cohf_id
	 * @return
	 */
	public List<CoHousingFundPwdModel> getPwdListById(int cohf_id);

	/**
	 * 查询该单位是否存在专办员
	 * 
	 * @param cid
	 * @return
	 */
	public int isHaveZb(int cohf_id);

	/**
	 * 前到添加专办员
	 * 
	 * @param cfzb
	 * @return
	 */
	public int addZbChange(CoHousingFundZbChangeModel cfzb);

	/**
	 * 查询出单位公积金的专办员
	 * 
	 * @param cohf_id
	 * @return
	 */
	public List<CoHousingFundZbModel> getZbListBycohf_id(int cohf_id);
	public List<CoHousingFundZbModel> getNotPwdZb(int cohf_id);
	

	/**
	 * 后到修改申报状态
	 * 
	 * @param cohf_id
	 * @param r
	 * @return
	 */
	public int changeStatus(CoHousingFundZbChangeModel cfzc);

	public CoHousingFundZbChangeModel addtype(int cohf_id);

	/**
	 * 获取前到录入的修改信息
	 * 
	 * @param cfzc_id
	 * @return
	 */
	public CoHousingFundZbModel getZbChangeInfo(int chfz_id);

	/**
	 * 查询该单位的该专办员是否存在密钥
	 * 
	 * @param cohf_id
	 * @param chfz_id
	 * @return
	 */
	public int isHavePwd(int cohf_id, int chfz_id);

}
