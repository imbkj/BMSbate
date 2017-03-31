package bll.CoReg;

import java.util.ArrayList;
import java.util.List;

import dal.CoBase.CoBase_SelectDal;
import dal.CoReg.CoReg_ListDal;

import Model.CoBaseModel;
import Model.CoOnlineRegisterInfoModel;
import Model.DocumentsInfoModel;
import Model.ResponsibilityBookModel;

public class CoReg_ListBll {

	// 获取公司列表
	public List<CoBaseModel> getCobaList() {
		List<CoBaseModel> list = new ArrayList<>();
		CoReg_ListDal dal = new CoReg_ListDal();
		list = dal.getCobaList();
		return list;
	}

	// 根据cid获取公司详情
	public CoBaseModel getCobaseInfo(Integer cid) {
		CoReg_ListDal dal = new CoReg_ListDal();
		return dal.getCobaseInfo(cid);
	}

	// 获取行业类型列表
	public List<String> getPubIndustry() {
		return new CoReg_ListDal().getPubIndustry();
	}

	// 后道获取公司立户申报列表
	public List<CoOnlineRegisterInfoModel> getCoRegOpList() {
		return new CoReg_ListDal().getCoRegOpList();
	}

	// 前道获取公司立户申报列表
	public List<CoOnlineRegisterInfoModel> getqdCoRegOpList() {
		return new CoReg_ListDal().getqdCoRegOpList();
	}

	// 获取状态列表
	public List<CoOnlineRegisterInfoModel> getstateList(String str) {
		return new CoReg_ListDal().getstateList(str);
	}

	// 获取所属月份列表
	public List<String> getownmonthList() {
		return new CoReg_ListDal().getownmonthList();
	}

	// 根据主键id获取公司立户申报详情
	public CoOnlineRegisterInfoModel getCoregInfo(Integer daid) {
		return new CoReg_ListDal().getCoregInfo(daid);
	}

	// 根据cid获取公司立户申报详情
	public CoOnlineRegisterInfoModel getCoregInfobyCid(Integer cid) {
		return new CoReg_ListDal().getCoregInfobyCid(cid);
	}

	// 根据主键id获取状态变更记录
	public List<CoOnlineRegisterInfoModel> getStateRelList(String str,
			Integer cori_id) {
		return new CoReg_ListDal().getStateRelList(str, cori_id);
	}

	// 根据cid查询cori_id
	public int getCori_id(int cid) {

		return new CoReg_ListDal().getCori_id(cid);
	}

	// 获取员工就业登记材料列表
	public List<DocumentsInfoModel> getDocList(Integer puzu_id) {
		return new CoReg_ListDal().getDocList(puzu_id);
	}

	// 根据公司就业登记信息表id获取责任书表Id
	public Integer getRebk_id(Integer cori_id) {
		return new CoReg_ListDal().getRebk_id(cori_id);
	}

	// 根据cid获取公司信息
	public CoBaseModel getCobase(Integer cid) {
		CoBaseModel model = new CoBaseModel();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		List<CoBaseModel> list = dal.getCobaseinfoforall(" and a.cid=" + cid);
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	/*
	 * 查询公司是否已有就业登记信息
	 */

	public boolean ifExists(Integer cid, String str) {
		CoReg_ListDal dal = new CoReg_ListDal();
		return dal.ifExists(cid, str);
	}

	// 根据公司就业登记信息表id获取责任书表Id
	public List<ResponsibilityBookModel> getResponsibilityBookList(Integer cid) {
		CoReg_ListDal dal = new CoReg_ListDal();
		return dal.getResponsibilityBookList(cid);
	}
	
	// 根据公司就业登记信息表id获取责任书表Id
	public ResponsibilityBookModel getResponsibilityBook(Integer cid) {
		ResponsibilityBookModel model=new ResponsibilityBookModel();
		List<ResponsibilityBookModel> list=getResponsibilityBookList(cid);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
	
	/*
	 * 查询公司是否已有就业登记信息
	 */
	public CoOnlineRegisterInfoModel coregInfo(Integer cid, String str) {
		CoReg_ListDal dal = new CoReg_ListDal();
		return dal.coregInfo(cid, str);
	}
}
