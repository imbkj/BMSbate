package bll.CoLatencyClient;

import impl.WorkflowCore.WfOperateImpl;

import java.util.ArrayList;

import java.util.List;

import org.zkoss.zul.ListModelList;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import dal.LoginDal;
import dal.CoCompact.CoCompactBaseDal;
import dal.CoLatencyClient.CoLatencyClientDal;
import dal.CoLatencyClient.CreditCriterion_OperateDal;
import dal.SystemControl.Data_PopedomDal;
import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoLaContactsModel;
import Model.CoLatencyClientModel;
import Model.CreditAppraiseModel;
import Model.CreditContentInfoModel;
import Model.CreditCriterionModel;
import Model.CreditInfoModel;
import Model.DataPopedomModel;
import Model.PubTradeModel;
import Util.UserInfo;

public class CoLatencyClient_AddBll {
	static CoLatencyClientDal dal = new CoLatencyClientDal();
	Data_PopedomDal DataPopedomdal = new Data_PopedomDal();

	// 添加潜在客户信息
	public int CoLatencyClient_Add(CoLatencyClientModel model) {
		int k = dal.CoLatencyClient_Add_P_cyj(model);
		return k;

	}

	// 添加潜在客户联系人信息
	public int CoLatencyClientLinkmanAdd(CoAgencyLinkmanModel model, int id) {
		return dal.CoLatencyClientLinkmanAdd_P_cyj(model, id);
	}

	// 更改潜在客户联系人信息（转成功客户）
	public int CoLatencyClientLinkmanAdd_P_lwj(CoAgencyLinkmanModel model,
			int id) {
		return dal.CoLatencyClientLinkmanAdd_P_lwj(model, id);
	}

	// 根据输入的潜在客户名称模糊查询潜在客户表和Cobase表是否已存在该公司
	public static List<CoLatencyClientModel> ifexistCompanyList(String name) {
		List<CoLatencyClientModel> colalist = dal
				.getCoLatencyClientinfoList(name);
		List<CoLatencyClientModel> cobalist = dal.getCobasefoList(name);
		colalist.addAll(cobalist);
		return colalist;
	}

	public static List<String> getInfoList(String name) {
		List<CoLatencyClientModel> colalist = ifexistCompanyList(name);
		int num = colalist.size();
		List<String> dictionary = new ArrayList<String>();
		for (int i = 0; i < num; i++) {
			CoLatencyClientModel m = colalist.get(i);
			dictionary.add(m.getCola_company() + "【" + m.getCompanytype()
					+ "】，" + m.getCola_addname());
		}
		return dictionary;
	}

	// 根据公司名称查询是否已存在该公司
	public CoLatencyClientModel ifexistCompany(String name) {
		List<CoLatencyClientModel> list = dal.getCoLatencyClientinfo(name);
		CoLatencyClientModel model = new CoLatencyClientModel();
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 查询潜在客户所有有效合同
	public List<CoCompactModel> getcocompactList(Integer colaId) {
		List<CoCompactModel> list = new ListModelList<>();
		CoCompactBaseDal cdal = new CoCompactBaseDal();
		list = cdal.getcocompactListBycolaId(colaId);
		return list;
	}

	// 获取所有的潜在客户的信息
	public List<CoLatencyClientModel> getCoLatencyClientAllInfo(String str) {
		return DataPopedomdal.getCoLatencyClientAllInfo(str);
	}

	// 获取修改的潜在客户的信息列表
	public List<CoLatencyClientModel> getCoLatencyClientAllInfoedit(String str) {
		return DataPopedomdal.getCoLatencyClientAllInfoedit(str);
	}

	// 更新客服以及上级权限
	public int Data_Popedomadd(Integer cid, String log_name) {
		LoginDal d = new LoginDal();
		DataPopedomModel m = new DataPopedomModel();
		m.setCid(cid);
		m.setDat_selected(true);
		m.setDat_edited(true);
		m.setDat_delete(true);
		m.setLog_id(d.getUserIDByname(log_name));
		m.setDat_addname(UserInfo.getUsername());
		Data_PopedomDal dal = new Data_PopedomDal();
		return dal.DataPopedomAdd(m);
	}

	// 全国项目部权限（特殊）
	public int Data_Popedomalladd(Integer cid, String log_name) {
		LoginDal d = new LoginDal();
		DataPopedomModel m = new DataPopedomModel();
		m.setCid(cid);
		m.setDat_selected(true);
		m.setDat_edited(true);
		m.setDat_delete(true);
		m.setLog_id(d.getUserIDByname(log_name));
		m.setDat_addname(UserInfo.getUsername());
		Data_PopedomDal dal = new Data_PopedomDal();
		return dal.DataPopedomallAdd(m);
	}

	// 修改潜在客户的信息
	public String CoLatencyClient_update(CoLatencyClientModel model) {
		String str = "";
		int k = dal.CoLatencyClient_update_P_cyj(model);
		if (k > 0) {
			str = "修改失败";
		} else if (k == -1) {
			str = "系统错误，请联系开发人员";
		} else {
			str = "修改成功";
		}
		return str;
	}

	// 修改一条潜在客户的联系人信息
	public String CoLatencyClientLinkmanupdate_P_cyj(CoAgencyLinkmanModel model) {
		String str = "";
		int k = dal.CoLatencyClientLinkmanupdate_P_cyj(model);
		if (k > 0) {
			str = "修改失败";
		} else if (k == -1) {
			str = "系统错误，请联系开发人员";
		} else {
			str = "修改成功";
		}
		return str;
	}

	// 根据机构ID查询联系人信息
	public List<CoAgencyLinkmanModel> getLinkmanForAg(int id) {
		return dal.getLinkmanForAg(id);
	}

	// 根据机构ID查询合同信息
	public List<CoCompactModel> getCompact(int id) {
		return dal.getCompact(id);
	}

	// 获取所属行业的信息
	public List<PubTradeModel> getTradeIndo() {
		return dal.getTradeIndo();
	}

	// 获取信誉评价的信息
	public List<CreditCriterionModel> getCreditIndo(String str) {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.getCreditInfo(str);
	}

	// 获取信誉评价状态为1的信息
	public List<CreditCriterionModel> getCreditIndoValid() {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.getCreditInfoValid();
	}

	// 根据标准名称查询信誉评价标准的信息
	public List<CreditCriterionModel> getCreditInfoFromName(String content) {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.getCreditInfoFromName(content);
	}

	// 根据id获取信誉评价内容的的所有信息
	public List<CreditContentInfoModel> getCreditContentInfo(int id) {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.getCreditContentInfo(id);
	}

	// 根据id获取信誉评价内容的的有效信息
	public List<CreditContentInfoModel> getCreditContentInfoValid(int id) {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.getCreditContentInfoValid(id);
	}

	// 根据id获取信誉评价分数表与潜在客户信息表的关系表该id的所有信息
	public List<CreditInfoModel> getCocrRelationInfo(int id) {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.getCocrRelationInfo(id);
	}

	// 添加一条潜在客户信誉评价总分信息
	public int addCreditInfo(CreditInfoModel model, int id) {
		int k = 0;
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		k = dll.addCreditInfo(model, id);
		return k;
	}

	// 对信誉评定标准得分表CreditAppraise插入一条信息
	public int addCreditAppraise(CreditAppraiseModel model) {
		int k = 0;
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		k = dll.addCreditAppraise(model);
		return k;
	}

	// 修改菜单表CreditCriterion的一条数据，并返回一个Int类型的数
	public int updateCreditCriterion(CreditCriterionModel model) {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.updateCreditCriterion(model);
	}

	// 修改菜单表CreditContentInfo的一条数据，并返回一个Int类型的数
	public int updateCreditContentInfo(CreditContentInfoModel model) {
		CreditCriterion_OperateDal dll = new CreditCriterion_OperateDal();
		return dll.updateCreditContentInfo(model);
	}

	// 查询潜在客户报价人(添加人)
	public List<CoLatencyClientModel> getAddName() {
		return dal.getAddName();
	}

	// 获取联系记录的信息
	public List<CoLaContactsModel> getCoLaContactsInfo(int id) {
		return dal.getCoLaContactsInfo(id);
	}

	// 添加联系记录的信息
	public int addCoLaContactsInfo(CoLaContactsModel model) {
		return dal.addCoLaContactsInfo(model);
	}

	// 更新联系记录的信息
	public int UpdateCoLaContactsInfo(CoLaContactsModel model) {
		return dal.UpdateCoLaContactsInfo(model);
	}

	// 删除联系记录的信息
	public int deleteCoLaContactsInfo(int id) {
		return dal.deleteCoLaContactsInfo(id);
	}

	// 根据潜在客户Id查询是否有已审核的合同
	public Integer getCoCompact(Integer cola_id) {
		return dal.getCoCompact(cola_id);
	}

	// 根据潜在客户Id查询是否有有合同
	public Integer ifExistCoCompact(Integer cola_id) {
		return dal.ifExistCoCompact(cola_id);
	}

	// 潜在客户转成功客户
	public String[] CoLatencyClientchange(CoBaseModel model, Integer cola_id) {
		Object[] obj = { "1", model, cola_id };
		WfBusinessService wfbs = new CoLatencyClientImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Integer colaid = 0;
		if (cola_id != null) {
			colaid = cola_id;
		}
		String[] str = wfs.AddTaskToNext(obj, "人事服务交接",
				"“" + model.getCoba_shortname() + "”人事服务交接", 91,
				UserInfo.getUsername(), "", colaid, "");
		return str;
	}

	// 给公司分配客服经理
	public String[] CobaseUpdate(String sql, Integer tarid, Integer cid) {
		Object[] obj = { "2", sql, cid };
		Integer id = 0;
		if (cid != null) {
			id = cid;
		}
		String[] str = new String[5];
		WfBusinessService wfbs = new CoLatencyClientImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		str = wfs.PassToNext(obj, tarid, UserInfo.getUsername(), "", id, "");
		return str;
	}

	// 获取客服信息
	public List<String> getLoginInfo() {
		return dal.getLoginInfo();
	}

	// 获取客服信息
	public List<String> getpidLoginlist() {
		return dal.getpidLoginlist();
	}

	// 获取潜在客户跟进人
	public List<String> getFollower() {
		return dal.getFollower();
	}

	// 查询报价单是否有与个税相关的服务
	public boolean ifExistsgeshui(String str) {
		return dal.ifExistsgeshui(str);
	}

	// 查询客户是否有财税服务
	public boolean isHasCS(int cola_id) {
		return dal.isHasCS(cola_id);
	}
}
