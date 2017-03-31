package bll.EmCommissionOut.Standard;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmCommissionOut.Standard.Standard_ListDal;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.CoOfferListModel;
import Model.EmCommissionOutStandardModel;
import Model.PubProCityModel;

public class Standard_ListBll {

	// 获取城市列表
	public ListModelList<PubProCityModel> getCityList() throws SQLException {
		return new Standard_ListDal().getCityList();
	}

	public List<PubProCityModel> getCityList1() throws SQLException {
		return new Standard_ListDal().getCityList1();
	}

	// 获取公司信息
	public CoBaseModel getCobaInfo(Integer cid) {
		return new Standard_ListDal().getCobaInfo(cid);
	}

	// 根据城市获取默认机构
	public CoAgencyBaseCityRelViewModel getDefaultCoAgency(Integer id) {
		return new Standard_ListDal().getDefaultCoAgency(id);
	}

	// 获取委托出标准详情(无机构)
	public EmCommissionOutStandardModel getStandardInfo(Integer daid) {
		return new Standard_ListDal().getStandardInfo(daid);
	}

	// 获取委托出标准详情(有机构)
	public EmCommissionOutStandardModel getStandardInfo1(Integer daid) {
		return new Standard_ListDal().getStandardInfo1(daid);
	}

	// 根据城市id获取所有机构列表
	public List<CoAgencyBaseCityRelViewModel> getCoAgencyList(Integer ppc_id) {
		return new Standard_ListDal().getCoAgencyList(ppc_id);
	}

	// 根据城市机构关系表id获取所有机构列表
	public List<CoAgencyBaseCityRelViewModel> getCoAgencyList1(Integer cabc_id) {
		return new Standard_ListDal().getCoAgencyList1(cabc_id);
	}

	// 根据cid和cabc_id获取合同中的福利产品
	public List<CoOfferListModel> getCoOfferList(Integer cid, Integer cabc_id,
			Integer ppc_id) {
		return new Standard_ListDal().getCoOfferList(cid, cabc_id, ppc_id);
	}

	// 根据报价单获取服务费收入
	public EmCommissionOutStandardModel getServiceCost(
			EmCommissionOutStandardModel m) {
		return new Standard_ListDal().getServiceCost(m);
	}

	// 根据主键id获取所选的福利产品列表
	public List<CoOfferListModel> getCoOfferListRel(Integer daid) {
		return new Standard_ListDal().getCoOfferListRel(daid);
	}

	// 获取标准列表
	public List<EmCommissionOutStandardModel> getStandardList(String str) {
		return new Standard_ListDal().getStandardList(str);
	}

	// 获取状态列表
	public List<EmCommissionOutStandardModel> getStateList(String str) {
		return new Standard_ListDal().getStateList(str);
	}
}
