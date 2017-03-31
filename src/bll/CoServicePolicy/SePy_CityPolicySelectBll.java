package bll.CoServicePolicy;

import java.util.List;

import Model.CoAgencyBaseModel;
import Model.CoServicePolicyFileModel;
import Model.CoServicePolicyModel;
import Model.CoServicePolicyTitleDropInfoModel;
import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;
import dal.CoServicePolicy.SePy_CityPolicySelectDal;

public class SePy_CityPolicySelectBll {
	private SePy_CityPolicySelectDal dal = new SePy_CityPolicySelectDal();

	// 查询政策通知基本信息
	public List<CoServicePolicyModel> getCoServicePolicyList(String str) {
		return dal.getCoServicePolicyList(str);
	}

	// 查询政策通知文件信息
	public List<CoServicePolicyFileModel> getCoServicePolicyFileList(String str) {
		return dal.getCoServicePolicyFileList(str);
	}

	// 获取政策通知基本信息并根据id获取文件信息
	public List<CoServicePolicyModel> getList(String str) {
		List<CoServicePolicyModel> list = getCoServicePolicyList(str);
		for (int i = 0; i < list.size(); i++) {
			CoServicePolicyModel m = list.get(i);
			List<CoServicePolicyFileModel> flist = getCoServicePolicyFileList(" and file_pono_id="
					+ m.getSypo_id());
			m.setFilelist(flist);
		}
		return list;
	}

	// 查询服务政策类型信息
	public List<CoServicePolicyTypeModel> getCoServicePolicyType(String str) {
		return dal.getCoServicePolicyType(str);
	}

	// 查询服务政策标题信息
	public List<CoServicePolicyTitleModel> getCoServicePolicyTitle(String str) {
		return dal.getCoServicePolicyTitle(str);
	}

	// 查询服务政策类型ID查询标题
	public List<CoServicePolicyTypeModel> getCoServicePolicyTypeList(String str) {
		List<CoServicePolicyTypeModel> list = getCoServicePolicyType(str);
		for (int i = 0; i < list.size(); i++) {
			CoServicePolicyTypeModel m = list.get(i);
			m.setTitlelist(getCoServicePolicyTitle("and item_type_id="
					+ m.getNote_id()));
		}
		return list;
	}

	// 查询服务政策标题信息
	public List<CoServicePolicyTitleModel> getCoServicePolicyTitles(String str,
			Integer sypo_cabc_id) {
		return dal.getCoServicePolicyTitles(str, sypo_cabc_id);
	}

	// 查询服务政策类型ID查询标题
	public List<CoServicePolicyTypeModel> getCoServicePolicyTypeLists(
			Integer cabc_id, String str) {
		List<CoServicePolicyTypeModel> list = getCoServicePolicyType(str);
		for (int i = 0; i < list.size(); i++) {
			CoServicePolicyTypeModel m = list.get(i);
			m.setTitlelist(getCoServicePolicyTitles(
					"and item_type_id=" + m.getNote_id(), cabc_id));
		}
		return list;
	}

	// 获取城市
	public List<String> getCity() {
		return dal.getCity();
	}

	// 查询机构
	public List<CoAgencyBaseModel> getCoAgencyBaseList(String str) {
		return dal.getCoAgencyBaseList(str);
	}

	// 根据城市查询机构
	public List<CoAgencyBaseModel> getCoAgencyList(String citystr) {
		String sql = "";
		String namestr = "";
		if (citystr != null && !citystr.equals("")) {
			String a[] = citystr.split("、");
			for (String s : a) {
				if (s != null && !s.equals("")) {
					if (namestr == null || namestr.equals("")) {
						namestr = "'" + s + "'";
					} else {
						namestr = namestr + ",'" + s + "'";
					}
				}
			}
		}
		if (namestr != null && !namestr.equals("")) {
			sql = " and name in(" + namestr + ")";
		}
		return getCoAgencyBaseList(sql);
	}

	// 查询城市与机构
	public List<CoAgencyBaseModel> getWtAgencyList(String str) {
		return dal.getWtAgencyList(str);
	}

	// 查询下拉列表信息
	public List<CoServicePolicyTitleDropInfoModel> getDropInfoList(String str) {
		return dal.getDropInfoList(str);
	}

	// 查询下拉列表信息
	public List<CoServicePolicyTitleModel> getTypeAndTitleList(String str) {
		return dal.getTypeAndTitleList(str);
	}
	
	public CoServicePolicyTitleModel getPolicyTitleModel(Integer item_id)
	{
		CoServicePolicyTitleModel model=new CoServicePolicyTitleModel();
		String sql=" and item_id="+item_id;
		List<CoServicePolicyTitleModel> list=getTypeAndTitleList(sql);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
}
