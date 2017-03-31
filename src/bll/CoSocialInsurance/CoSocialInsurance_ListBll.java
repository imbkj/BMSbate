package bll.CoSocialInsurance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoSocialInsurance.CoSocialInsurance_ListDal;
import dal.Taskflow.TaskBatchDal;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoShebaoChangeModel;
import Model.CoShebaoInforChangeModel;
import Model.CoShebaoModel;
import Model.PubAreaCoShebaoModel;
import Model.PubBankModel;
import Model.PubHealthModel;
import Model.PubIndustryFirstModel;
import Model.PubIndustryModel;
import Model.PubStateModel;
import Model.PubStateRelModel;
import Model.TaskBatchModel;

public class CoSocialInsurance_ListBll {

	CoSocialInsurance_ListDal dal = new CoSocialInsurance_ListDal();

	public List<CoBaseModel> getcobase(Integer id) {
		List<CoBaseModel> list = new ListModelList<>();
		list = dal.getcobaseList(id);

		return list;
	}

	public List<TaskBatchModel> gettaskinfo(Integer id) {
		List<TaskBatchModel> list = new ListModelList<>();
		TaskBatchDal dal2 = new TaskBatchDal();
		try {
			list = dal2.getListByid(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public List<CoBaseModel> getcobaseinfo(Integer cid) {
		List<CoBaseModel> list = new ListModelList<>();
		list = dal.getcobaseinfoList(cid);

		return list;
	}

	// 获取公司列表
	public List<CoBaseModel> getCobaList(Integer cid) {
		return dal.getCobaList(cid);
	}

	// 获取社保分局列表
	public List<String> getShebaoArealist() {
		return dal.getShebaoArealist();
	}

	// 行业二级
	public List<PubIndustryModel> getIndustryList() {
		return dal.getIndustryList();
	}

	// 行业一级
	public List<PubIndustryFirstModel> getIndustryFirstList() {
		return dal.getIndustryFirstList();
	}

	// 获取区域、镇、街道
	public List<PubAreaCoShebaoModel> getAreaList() {
		return dal.getAreaList();
	}

	public List<PubAreaCoShebaoModel> getTownList() {
		return dal.getTownList();
	}

	public List<PubAreaCoShebaoModel> getStreetList() {
		return dal.getStreetList();
	}

	// 银行
	public List<PubBankModel> getBankList() {
		return dal.getBankList();
	}

	// 医疗机构
	public List<PubHealthModel> getHealthList() {
		return dal.getHealthList();
	}

	// 获取社保独立账户缴存登记/接管列表
	public List<CoShebaoChangeModel> getCoShebaoChangeList(String wherestr) {
		return dal.getCoShebaoChangeList(wherestr);
	}

	// 获取状态列表
	public List<PubStateModel> getStateList(String str) {
		return dal.getStateList(str);
	}

	// 获取客服列表
	public List<CoShebaoChangeModel> getClientList() {
		return dal.getClientList();
	}

	// 获取社保独立账户缴存登记/接管详情
	public CoShebaoChangeModel getCoShebaoChangeInfo(Integer daid) {
		return dal.getCoShebaoChangeInfo(daid);
	}

	// 获取状态变更历史记录列表
	public List<PubStateRelModel> getHosList(Integer daid, String str) {
		return dal.getHosList(daid, str);
	}

	// 获取独立账户列表
	public List<CoShebaoModel> getCoShebaoList(String wherestr) {
		return dal.getCoShebaoList(wherestr);
	}

	// 获取独立账户详细信息
	public CoShebaoModel getCoShebaoInfo(Integer daid) {
		return dal.getCoShebaoInfo(daid);
	}

	// 获取信息变更详情列表
	public List<CoShebaoInforChangeModel> getInforChangeList(Integer daid,
			String str) {
		return dal.getInforChangeList(daid, str);
	}

	// 根据cid获取该公司的合同列表(合同内无社保员工)
	public List<CoCompactModel> getCompactList(Integer cid) {
		return dal.getCompactList(cid);
	}

	// 获取制卡银行信息
	public List<PubBankModel> getBankList(String str) {
		return dal.getBankList(str);
	}

	// 获取制卡银行支行信息
	public List<PubBankModel> getBankBranchList(String str) {
		return dal.getBankBranchList(str);
	}
}
