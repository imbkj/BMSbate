package bll.CoHousingFund;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.PubIndustryDal;
import dal.CoCompact.CocompactDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.Taskflow.TaskBatchDal;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Model.PubAreaSZModel;
import Model.PubCoEcoclassModel;
import Model.PubCoNatureModel;
import Model.PubGjBankModel;
import Model.PubIdcardTypeModel;
import Model.PubIndustryModel;
import Model.PubMemberShipModel;
import Model.PubStateModel;
import Model.PubStateRelModel;
import Model.PubTsbankModel;
import Model.TaskBatchModel;

public class CoHousingFund_ListBll {

	CoHousingFund_ListDal dal = new CoHousingFund_ListDal();

	public List<CoBaseModel> getcobase(Integer id) {
		List<CoBaseModel> list = new ListModelList<>();
		list = dal.getcobaseList(id);

		return list;
	}
	
	public List<TaskBatchModel> gettaskinfo(Integer id){
		List<TaskBatchModel> list = new ListModelList<>();
		TaskBatchDal dal2 = new TaskBatchDal();
		try {
			list= dal2.getListByid(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
		
	}
	
	//获取行业代码列表
	public List<PubIndustryModel> pubIndustry(){
		List<PubIndustryModel> list= new ListModelList<>();
		PubIndustryDal dal = new PubIndustryDal();
		list=dal.getIndustrylist();
		return list;
	}
	
	public List<CoBaseModel> getcobaseinfo(Integer cid) {
		List<CoBaseModel> list = new ListModelList<>();
		list = dal.getcobaseinfoList(cid);

		return list;
	}

	/**
	 * 新增-公司列表
	 * 
	 * @return
	 */
	public List<CoHousingFundChangeModel> getCobaList(Integer cid) {
		return dal.getCobaList(cid);
	}

	/**
	 * 性质分类列表
	 * 
	 * @return
	 */
	public List<PubCoNatureModel> getCoNatureList() {
		return dal.getCoNatureList();
	}

	/**
	 * 企业经济类型列表
	 * 
	 * @return
	 */
	public List<PubCoEcoclassModel> getCoEcoclassList() {
		return dal.getCoEcoclassList();
	}

	/**
	 * 归集银行列表
	 * 
	 * @return
	 */
	public List<PubGjBankModel> getGjBankList() {
		return dal.getGjBankList();
	}

	/**
	 * 托收银行列表
	 * 
	 * @return
	 */
	public List<PubTsbankModel> getTsBankList() {
		return dal.getTsBankList();
	}

	/**
	 * 所属区域列表
	 * 
	 * @return
	 */
	public List<PubAreaSZModel> getAreaSzList() {
		return dal.getAreaSzList();
	}

	/**
	 * 隶属关系列表
	 * 
	 * @return
	 */
	public List<PubMemberShipModel> getMemberShipList() {
		return dal.getMemberShipList();
	}

	/**
	 * 证件类型列表
	 * 
	 * @return
	 */
	public List<PubIdcardTypeModel> getIdcardTypeList() {
		return dal.getIdcardTypeList();
	}

	public List<Integer> getHouseCppList(Integer cid) {
		List<Integer> list = new ListModelList<>();
		CocompactDal dal2 = new CocompactDal();
		list = dal2.getHousecppList(cid);
		return list;
	}

	/**
	 * 获取操作历史
	 * 
	 * @param daid
	 * @param str
	 * @return
	 */
	public List<PubStateRelModel> getHosList(Integer daid, String str) {
		return dal.getHosList(daid, str);
	}

	/**
	 * 获取状态列表
	 * 
	 * @param str
	 * @return
	 */
	public List<PubStateModel> getStateList(String str) {
		return dal.getStateList(str);
	}

	/**
	 * 获取单位公积金申报列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundChangeModel> getCoHoChangeList(String str) {
		return dal.getCoHoChangeList(str);
	}

	public List<CoHousingFundChangeModel> getCoHoChangeList(Integer id) {
		List<CoHousingFundChangeModel> list = new ListModelList<>();
		list = dal.getCoHoChangeList(id);
		return list;
	}

	/**
	 * 获取专办员列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundZbModel> getZbList(String str) {
		return dal.getZbList(str);
	}

	/**
	 * 获取专办员申报列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundZbChangeModel> getZbChangeList(String str) {
		return dal.getZbChangeList(str);
	}

	/**
	 * 获取密钥信息
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundPwdModel> getPwdList(String str) {
		return dal.getPwdList(str);
	}

	/**
	 * 获取密钥申报列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundPwdChangeModel> getPwdChangeList(String str) {
		return dal.getPwdChangeList(str);
	}

	/**
	 * 获取单位公积金在册列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundModel> getCoHoList(String str, boolean mod) {
		return dal.getCoHoList(str, mod);
	}

	// 根据任务单编号查询任务单状态
	public Integer getTaskState(Integer tapr_id) {
		return dal.getTaskState(tapr_id);
	}

	// 根据cid查询公积金接管或者新增数据
	public CoHousingFundChangeModel getCoHouseingTapId(Integer cid) {
		return dal.getCoHouseingTapId(cid);
	}

	/**
	 * 获取单位公积金在册客服列表
	 * 
	 * @return
	 */
	public List<String> getCoHoClientList(String str, boolean mod) {
		return dal.getCoHoClientList(str, mod);
	}

	/**
	 * 获取信息变更详情列表
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundInforChangeModel> getInforChangeList(String str) {
		return dal.getInforChangeList(str);
	}

	/**
	 * 获取末次(上个月)缴存人数和缴存额
	 * 
	 * @param str
	 * @return
	 */
	public List<CoHousingFundChangeModel> getLastMonth_EmCount_Sum(String str) {
		return dal.getLastMonth_EmCount_Sum(str);
	}

	/**
	 * @Title: getcompactList
	 * @Description: TODO(查询合同列表)
	 * @param cid
	 * @return
	 * @return List<CoCompactModel> 返回类型
	 * @throws
	 */
	public List<CoCompactModel> getcompactList(Integer cid) {
		List<CoCompactModel> list = new ListModelList<>();
		CocompactDal dal2 = new CocompactDal();
		CoCompactModel cm = new CoCompactModel();
		cm.setCid2(cid);
		cm.setDataState(1);
		cm.setCoco_house("独立开户");
		list = dal2.getCompactList(cm);
		return list;
	}

}
