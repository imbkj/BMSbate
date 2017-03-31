package bll.CoQuotation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.CoQuotation.CoQuotation_List1Dal;
import dal.CoQuotation.CoofferOperateDal;

import Conn.dbconn;
import Model.CoLatencyClientModel;
import Model.CoOfferModel;

public class CoQuotation_List1Bll {

	// 根据潜在客户id获取报价单列表
	public List<CoOfferModel> getCoOfferList(int cola_id) throws SQLException {
		List<CoOfferModel> list = new ArrayList<CoOfferModel>();
		CoQuotation_List1Dal dal = new CoQuotation_List1Dal();
		list = dal.getCoOfferList(cola_id);
		return list;
	}

	// 根据报价单id获取报价单信息
	public List<CoOfferModel> getCoOfferInfoList(int coof_id)
			throws SQLException {
		CoQuotation_List1Dal dal = new CoQuotation_List1Dal();
		return dal.getCoOfferInfoList(coof_id);
	}

	// 获取潜在客户列表
	public List<CoLatencyClientModel> getCoLatencyClientAllInfo(String str,String top) {
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		CoQuotation_List1Dal dal = new CoQuotation_List1Dal();
		menuinfo = dal.getCoLatencyClientAllInfo(str,top);
		return menuinfo;
	}
	
	public List<CoLatencyClientModel> getCoLatencyClientAllInfo(String str) {
		List<CoLatencyClientModel> menuinfo = new ArrayList<CoLatencyClientModel>();
		CoQuotation_List1Dal dal = new CoQuotation_List1Dal();
		menuinfo = dal.getCoLatencyClientAllInfo(str);
		return menuinfo;
	}

	// 获取添加人列表
	public List<String> getAddnameList() throws SQLException {
		List<String> list = new ArrayList<String>();
		CoQuotation_List1Dal dal = new CoQuotation_List1Dal();
		list = dal.getAddnameList();
		return list;
	}

	// 获取添加人列表
	public List<String> getFollowerList() throws SQLException {
		List<String> list = new ArrayList<String>();
		CoQuotation_List1Dal dal = new CoQuotation_List1Dal();
		list = dal.getFollowerList();
		return list;
	}

	// 根据潜在客户id获取潜在客户信息
	public CoLatencyClientModel getModel(Integer cola_id) {
		List<CoLatencyClientModel> list = getCoLatencyClientAllInfo("and cola_id="
				+ cola_id,"");
		CoLatencyClientModel model = new CoLatencyClientModel();
		if (list.size() > 0) {
			model = list.get(0);
		}
		return model;
	}

	// 删除报价
	public Integer delQutation(Integer id) {
		CoofferOperateDal dal = new CoofferOperateDal();
		Integer i = dal.delCoofferById(id);
		return i;
	}

	// 根据报价单id查询公司名称
	public String getCompany(Integer coof_id) {
		CoofferOperateDal dal = new CoofferOperateDal();
		return dal.getCompany(coof_id);
	}

}
