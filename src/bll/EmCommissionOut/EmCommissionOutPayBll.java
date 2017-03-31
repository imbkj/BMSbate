package bll.EmCommissionOut;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.EmCommissionOutPayModel;
import dal.EmCommissionOut.EmCommissionOutPayDal;

public class EmCommissionOutPayBll {
	// 显示帐单月份
	public List<EmCommissionOutPayModel> getownmonthlist() throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getownmonthlist();
		return list;
	}

	// 显示所有帐单数
	public List<EmCommissionOutPayModel> getempaylist(String city,
			String depcompany) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getempaylist(city, depcompany);
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getemmonthpaylist(
			String ecop_dep_company, String str_id) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getemmonthpaylist(
				ecop_dep_company, str_id);
		return list;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getautpaylist(String ownmonth,
			String ecop_dep_company,String wt_city) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getautpaylist1(ownmonth,
				ecop_dep_company,wt_city);
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getautpaysflist(String ownmonth,
			String ecop_dep_company) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getautpaysflist(ownmonth,
				ecop_dep_company);
		return list;
	}

	// 显示公司帐单月份明细
	public List<EmCommissionOutPayModel> getautpaycolist(String ownmonth,
			String cabc_id) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getautpaycolist1(ownmonth,
				cabc_id);
		return list;
	}

	// 显示系统帐单月份明细
	public List<EmCommissionOutPayModel> getyfemmonthpaylist(String city,
			String depcompany) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getyfemmonthpaylist(city,
				depcompany);
		return list;
	}

	// 显示帐单明细
	public List<EmCommissionOutPayModel> getembasepaylist(String ownmonth,
			String cabc_id, String company, String st_p) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getembasepaylist1(ownmonth,
				cabc_id, company, st_p);
		return list;
	}

	// 显示系统应付帐单明细
	public List<EmCommissionOutPayModel> getyfembasepaylist(String ownmonth,
			String ecou_dep_company) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getyfembasepaylist(ownmonth,
				ecou_dep_company);
		return list;
	}

	// 显示帐单月份明细
	public List<EmCommissionOutPayModel> getembasepaylistin(String gid,
			String ownmonth, String cabc_id) throws Exception {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getembasepaylistin(gid,
				ownmonth, cabc_id);
		return list;
	}

	// 显示委托城市
	public List<EmCommissionOutPayModel> getcitylist() throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getcitylist();
		return list;
	}

	// 显示应付委托城市
	public List<EmCommissionOutPayModel> getyfcitylist() throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getyfcitylist();
		return list;
	}

	// 显示委托机构
	public List<EmCommissionOutPayModel> getdepcompanylist(String city)
			throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		String str = "";
		if (!city.equals("")) {
			str = " and b.city='" + city + "'";
		}
		List<EmCommissionOutPayModel> list = dal.getdepcompanylist(str);
		return list;
	}

	// 显示委托总费用
	public List<EmCommissionOutPayModel> getwt_sftotal(String cabc_id,
			String ownmonth) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getwt_sftotal(cabc_id,
				ownmonth);
		return list;
	}

	// 显示委托明细
	public List<EmCommissionOutPayModel> getwt_paylist(String cabc_id,
			String ownmonth, String filename) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getwt_paylist(cabc_id,
				ownmonth, filename);
		return list;
	}

	// 委托帐单确认
	public int getwt_paypass(String cabc_id, String ownmonth)
			throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		int list = dal.getwt_paypass(cabc_id, ownmonth);
		return list;
	}

	// 委托帐单确认
	public int getwt_sfpaylist(String ownmonth, String dep_company)
			throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		int list = dal.getwt_sfpaylist(ownmonth, dep_company);
		return list;
	}

	// 显示应付委托机构
	public List<EmCommissionOutPayModel> getyfdepcompanylist(String city)
			throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		String str = "";
		if (!city.equals("")) {
			str = " and coab_city='" + city + "'";
		}
		List<EmCommissionOutPayModel> list = dal.getyfdepcompanylist(str);
		return list;
	}

	// 委托对帐确认
	public String wt_ok(String chk) {
		int result = 0;
		String message = "0";
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		result = dal.wt_ok(chk);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}

	// 委托对帐二次确认
	public String wtdate_ok(String gid, String yl_cp, String yl_op,
			String jl_cp, String jl_op, String gs_cp, String gs_op,
			String syu_cp, String syu_op, String sye_cp, String sye_op,
			String house_cp, String house_op, String fw, String file,
			String other) {
		int result = 0;
		String message = "0";
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		result = dal.wtdate_ok(gid, yl_cp, yl_op, jl_cp, jl_op, gs_cp, gs_op,
				syu_cp, syu_op, sye_cp, sye_op, house_cp, house_op, fw, file,
				other);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}

	// 委托对帐公司确认
	public String wtco_ok(String ownmonth, String cabc_id,String cid) {
		int result = 0;
		String message = "0";
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		result = dal.wtco_ok(ownmonth, cid,cabc_id);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}

	// 委托对帐确认
	public String pass_ok(String chk, int pass_state, String ylcp, String ylop,
			String jlcp, String jlop, String djlcp, String djlop, String syucp,
			String syuop, String gscp, String gsop, String syecp, String syeop,
			String housecp, String houseop, String fwcp, String fwop,
			String filecp, String fileop, String flcp, String flop) {
		int result = 0;
		String message = "0";
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		result = dal.pass_ok(chk, pass_state, ylcp, ylop, jlcp, jlop, djlcp,
				djlop, syucp, syuop, gscp, gsop, syecp, syeop, housecp,
				houseop, fwcp, fwop, filecp, fileop, flcp, flop);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}

	// 委托转下月
	public String wt_up(String chk, String company, String name, String yl,
			String sye, String jl, String gs, String syu, String house,
			String other, String file, String fw, String total, String gid,
			String cid, String cabc_id, String ownmonth) {
		int result = 0;
		String message = "0";
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		result = dal.wt_up(chk, company, name, yl, sye, jl, gs, syu, house,
				other, file, fw, total, gid, cid, cabc_id, ownmonth);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}

	// 委托转下月
	public String wt_allup(String chk, String company, String name, String yl,
			String sye, String jl, String gs, String syu, String house,
			String other, String file, String fw, String total, String gid,
			String cid, String cabc_id, String ownmonth) {
		int result = 0;
		String message = "0";
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		result = dal.wt_allup(chk, company, name, yl, sye, jl, gs, syu, house,
				other, file, fw, total, gid, cid, cabc_id, ownmonth);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}

	// 添加费用
	public String feeadd(String gid, String cid, String ownmonth,
			String cabc_id, String name, String yl,
			String jl, String syu, String gs, String sye, String house,
			String file, String fw, String other, String remark) {
		int result = 0;
		String message = "0";
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		result = dal.feeadd(gid, cid, ownmonth, cabc_id, name, yl,
				jl, syu, gs, sye, house, file, fw, other, remark);
		if (result != 0) {
			message = "1";
		} else {
			message = "0";
		}
		return message;
	}

	// 显示帐单人数对比
	public List<EmCommissionOutPayModel> getpay_yflist(String cabc_id,String ownmonth,String er_st) throws SQLException {
		EmCommissionOutPayDal dal = new EmCommissionOutPayDal();
		List<EmCommissionOutPayModel> list = dal.getpay_yflist(cabc_id,ownmonth,er_st);
		return list;
	}
}
