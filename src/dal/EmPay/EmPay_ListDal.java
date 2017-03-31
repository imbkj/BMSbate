package dal.EmPay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmPayModel;
import Model.EmPay_ListModel;

public class EmPay_ListDal {

	// 查询重复的支付信息
	public List<EmPayModel> getList(Integer gid, String clazz, Integer ownmonth) {
		List<EmPayModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from empay where empa_state>0 and empa_state!=6 and gid=? and empa_class=? and ownmonth=?";
		try {
			list = db.find(sql, EmPayModel.class, null, gid, clazz, ownmonth);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 获取未审核支付数据
	public List<EmPay_ListModel> getEmPay_Autlist(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_ListModel> list = new ArrayList<EmPay_ListModel>();
		String sqlstr = "select * from EmShouldPayAut where espa_state=0"
				+ str1;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmPay_ListModel model = new EmPay_ListModel();
				model.setEspa_paynum(rs.getString("espa_paynum"));
				model.setEspa_sf_fee(rs.getString("espa_sf_fee"));
				model.setEspa_ss_fee(rs.getString("espa_ss_fee"));
				model.setEspa_yf_fee(rs.getString("espa_yf_fee"));
				model.setEspa_ys_fee(rs.getString("espa_ys_fee"));
				model.setEspa_type(rs.getString("espa_type"));
				model.setEspa_dis_fee(rs.getString("espa_dis_fee"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEspa_id(rs.getString("espa_id"));
				model.setEspa_tapr_id(rs.getString("espa_tapr_id"));
				model.setEspa_typeinfo(rs.getString("espa_typeinfo"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取已审核支付数据
	public List<EmPay_ListModel> getEmPay_Autpaylist(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_ListModel> list = new ArrayList<EmPay_ListModel>();
		String sqlstr = "select * from EmShouldPayAut where espa_state=2"
				+ str1;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmPay_ListModel model = new EmPay_ListModel();
				model.setEspa_paynum(rs.getString("espa_paynum"));
				model.setEspa_sf_fee(rs.getString("espa_sf_fee"));
				model.setEspa_ss_fee(rs.getString("espa_ss_fee"));
				model.setEspa_yf_fee(rs.getString("espa_yf_fee"));
				model.setEspa_ys_fee(rs.getString("espa_ys_fee"));
				model.setEspa_type(rs.getString("espa_type"));
				model.setEspa_dis_fee(rs.getString("espa_dis_fee"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEspa_id(rs.getString("espa_id"));
				model.setEspa_tapr_id(rs.getString("espa_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取已审核支付数据（总经理）
	public List<EmPay_ListModel> getEmpay_Mgautlist(String str1)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_ListModel> list = new ArrayList<EmPay_ListModel>();
		String sqlstr = "select * from EmShouldPayAut where espa_state=1"
				+ str1;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmPay_ListModel model = new EmPay_ListModel();
				model.setEspa_paynum(rs.getString("espa_paynum"));
				model.setEspa_sf_fee(rs.getString("espa_sf_fee"));
				model.setEspa_ss_fee(rs.getString("espa_ss_fee"));
				model.setEspa_yf_fee(rs.getString("espa_yf_fee"));
				model.setEspa_ys_fee(rs.getString("espa_ys_fee"));
				model.setEspa_type(rs.getString("espa_type"));
				model.setEspa_dis_fee(rs.getString("espa_dis_fee"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEspa_id(rs.getString("espa_id"));
				model.setEspa_tapr_id(rs.getString("espa_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取查询列表
	public List<EmPay_ListModel> getEmpay_List(String str1) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmPay_ListModel> list = new ArrayList<EmPay_ListModel>();
		String sqlstr = "select * from EmShouldPayAut" + str1;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {

				EmPay_ListModel model = new EmPay_ListModel();
				model.setEspa_paynum(rs.getString("espa_paynum"));
				model.setEspa_sf_fee(rs.getString("espa_sf_fee"));
				model.setEspa_ss_fee(rs.getString("espa_ss_fee"));
				model.setEspa_yf_fee(rs.getString("espa_yf_fee"));
				model.setEspa_ys_fee(rs.getString("espa_ys_fee"));
				model.setEspa_type(rs.getString("espa_type"));
				model.setEspa_dis_fee(rs.getString("espa_dis_fee"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEspa_id(rs.getString("espa_id"));
				model.setEspa_tapr_id(rs.getString("espa_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}
