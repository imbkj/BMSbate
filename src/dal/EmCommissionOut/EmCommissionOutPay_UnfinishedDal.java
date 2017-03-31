package dal.EmCommissionOut;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutPayModel;

public class EmCommissionOutPay_UnfinishedDal {
	// 获取委托未完成列表
	public List<EmCommissionOutPayModel> getwt_unflist(String ownmonth,
			String city, String company) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str1 = "";
		String str2 = "";
		String str3 = "";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		if (!ownmonth.equals("")) {
			str1 = " and ownmonth=" + ownmonth;
		}
		if (!city.equals("")) {
			str2 = " and city=substring('" + city + "',4,10)";
		}
		if (!company.equals("")) {
			str3 = " and coab_name='" + company + "'";
		}
		String sqlstr = "select ecwd_id,ownmonth,city,coab_name,coba_shortname,emba_name,emba_idcard,ecwd_total,case when ecwd_fstate=0 then '未处理' else '已处理' end state from EmCommissionOutPayWTDif a left join View_wtservicestandard b on b.cabc_id=a.ecwd_cabc_id and b.cid=a.cid left join EmBase c on c.gid=a.gid left join CoBase d on d.CID=a.cid where 1=1"
				+ str1 + str2 + str3;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setEcop_id(rs.getString("ecwd_id"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEcop_cabc_id(rs.getString("city"));
				model.setEcop_company(rs.getString("coba_shortname"));
				model.setEcop_name(rs.getString("emba_name"));
				model.setEcop_dep_company(rs.getString("coab_name"));
				model.setEcop_di_total(rs.getString("emba_idcard"));
				model.setEcop_state(rs.getString("state"));
				model.setEcop_file_di(rs.getString("emba_idcard"));
				model.setEcop_sf_fee(rs.getString("ecwd_total"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取客服未完成列表
	public List<EmCommissionOutPayModel> getwt_kfunflist(String gid,String name,String company,String coba_client)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str="";
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		if(!gid.equals("")){
			str=" and c.gid="+gid;
		}
		if(!name.equals("")){
			str=str+" and emba_name like '%"+name+"%'";
		}
		if(!company.equals("")){
			str=str+" and coba_company like '%"+company+"%'";
		}
		if(!coba_client.equals("")){
			str=str+" and coba_client='"+coba_client+"'";
		}
		
		String sqlstr = "select ecod_id,ownmonth,city,coab_name,coba_shortname,emba_name,emba_idcard,cast(isnull(ecod_yl,0) as money)+cast(isnull(ecod_jl,0) as money)+cast(isnull(ecod_gs,0) as money)+cast(isnull(ecod_syu,0) as money)+cast(isnull(ecod_sye,0) as money)+cast(isnull(ecod_house,0) as money)+cast(isnull(ecod_file,0) as money)+cast(isnull(ecod_fuwu,0) as money)+cast(isnull(ecod_other,0) as money) ecod_total,case when ecod_fstate=0 then '未处理' else '已处理' end state,a.gid,coba_client from EmCommissionOutPayDif a left join View_wtservicestandard b on b.cabc_id=a.ecod_cabc_id and b.cid=a.cid left join EmBase c on c.gid=a.gid left join CoBase d on d.CID=a.cid where ecod_remark is not null and ecod_remark<>'' "+str+" order by ecod_id desc";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setEcop_id(rs.getString("ecod_id"));
				model.setOwnmonth(rs.getString("ownmonth"));
				model.setEcop_cabc_id(rs.getString("city"));
				model.setEcop_company(rs.getString("coba_shortname"));
				model.setEcop_name(rs.getString("emba_name"));
				model.setEcop_dep_company(rs.getString("coab_name"));
				model.setEcop_di_total(rs.getString("emba_idcard"));
				model.setEcop_state(rs.getString("state"));
				model.setEcop_file_di(rs.getString("emba_idcard"));
				model.setEcop_sf_fee(rs.getString("ecod_total"));
				model.setGid(rs.getString("gid"));
				model.setCoba_client(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 获取客服未完成列表
		public List<EmCommissionOutPayModel> getwt_wtunflist(String gid,String name,String company,String cabc_id,String ownmonth)
				throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;
			String str="";
			List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
			if(!gid.equals("")){
				str=" and c.gid="+gid;
			}
			if(!name.equals("")){
				str=str+" and emba_name like '%"+name+"%'";
			}
			if(!company.equals("")){
				str=str+" and coba_company like '%"+company+"%'";
			}
			
			String sqlstr = "select isnull(ch_st,0) ch_st,ecod_id,ownmonth,city,coab_name,coba_shortname,emba_name,emba_idcard,cast(isnull(ecod_yl,0) as money)+cast(isnull(ecod_jl,0) as money)+cast(isnull(ecod_gs,0) as money)+cast(isnull(ecod_syu,0) as money)+cast(isnull(ecod_sye,0) as money)+cast(isnull(ecod_house,0) as money)+cast(isnull(ecod_file,0) as money)+cast(isnull(ecod_fuwu,0) as money)+cast(isnull(ecod_other,0) as money) ecod_total,case when ecod_fstate=0 then '未处理' else '已处理' end state,a.gid,coba_client,ecod_premark,ecod_yl,ecod_jl,ecod_gs,ecod_syu,ecod_sye,ecod_house,ecod_file,ecod_fuwu ecod_fw,ecod_other,ecod_total,c.cid,ecod_cabc_id from EmCommissionOutPayDif a left join View_wtservicestandard b on b.cabc_id=a.ecod_cabc_id and b.cid=a.cid left join EmBase c on c.gid=a.gid left join CoBase d on d.CID=a.cid  left join (select gid,COUNT(*) ch_st from EmCommissionOutChange where ecoc_state in (2,3) group by gid) e on e.gid=a.gid  where ecod_cabc_id="+cabc_id+" and ownmonth="+ownmonth+str+" order by ABS(ecod_total)";
			try {
				System.out.println(sqlstr);
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					EmCommissionOutPayModel model = new EmCommissionOutPayModel();
					model.setEcop_id(rs.getString("ecod_id"));
					model.setEcop_ch_st(rs.getString("ch_st"));
					model.setOwnmonth(rs.getString("ownmonth"));
					model.setEcop_cabc_id(rs.getString("ecod_cabc_id"));
					model.setEcop_company(rs.getString("coba_shortname"));
					model.setEcop_name(rs.getString("emba_name"));
					model.setEcop_dep_company(rs.getString("coab_name"));
					model.setEcop_di_total(rs.getString("emba_idcard"));
					model.setEcop_state(rs.getString("state"));
					model.setEcop_file_di(rs.getString("emba_idcard"));
					model.setEcop_sf_fee(rs.getString("ecod_total"));
					model.setGid(rs.getString("gid"));
					model.setCoba_client(rs.getString("coba_client"));
					model.setEcod_premark(rs.getString("ecod_premark"));
					model.setEcop_yl_di(rs.getString("ecod_yl"));
					model.setEcop_jl_di(rs.getString("ecod_jl"));
					model.setEcop_gs_di(rs.getString("ecod_gs"));
					model.setEcop_sye_di(rs.getString("ecod_sye"));
					model.setEcop_syu_di(rs.getString("ecod_syu"));
					model.setEcop_house_di(rs.getString("ecod_house"));
					model.setEcop_file_di(rs.getString("ecod_file"));
					model.setEcop_fw_di(rs.getString("ecod_fw"));
					model.setEcop_other_di(rs.getString("ecod_other"));
					model.setCid(rs.getString("cid"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return list;
		}

	// 获取客服未完成列表
	public List<EmCommissionOutPayModel> getownmonthlist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select distinct ownmonth from EmCommissionOutPayDif order by ownmonth";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				model.setOwnmonth(rs.getString("ownmonth"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 获取客服
		public List<EmCommissionOutPayModel> getclientlist() throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;
			List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
			String sqlstr = "select log_name from login where dept='客户服务部' and log_inure=1 order by log_name";
			try {
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					EmCommissionOutPayModel model = new EmCommissionOutPayModel();
					model.setCoba_client(rs.getString("log_name"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return list;
		}

	private dbconn conn = new dbconn();

	// 非标添加
	public int zd_add(String ecop_id, String zd_st, String zd_ownmonth) {
		try {
			System.out.println("xxxx");
			System.out.println(ecop_id);
			CallableStatement c = conn
					.getcall("EmCommissionOut_PayZDFeeAdd_P_zzq(?,?,?,?)");
			c.setString(1, ecop_id);
			c.setString(2, zd_st);
			c.setString(3, zd_ownmonth);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);

		} catch (SQLException e) {
			return 1;
		}
	}

	// 获取客服未完成列表
	public EmCommissionOutPayModel getsfeelist(String ecod_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		EmCommissionOutPayModel list = new EmCommissionOutPayModel();
		String sqlstr = "select * from EmCommissionOutPayDif a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join CoAgencyBaseCityRel d on d.cabc_id=a.ecod_cabc_id left join CoAgencyBase e on e.coab_id=d.cabc_coab_id where ecod_id="
				+ ecod_id;
		try {
			rs = db.GRS(sqlstr);
			System.out.println(sqlstr);
			while (rs.next()) {
				EmCommissionOutPayModel model = new EmCommissionOutPayModel();
				list.setEcop_name(rs.getString("ecod_name"));
				list.setGid(rs.getString("gid"));
				list.setCid(rs.getString("cid"));
				list.setEcop_yl_yf(rs.getString("ecod_yl"));
				list.setEcop_jl_yf(rs.getString("ecod_jl"));
				list.setEcop_gs_yf(rs.getString("ecod_gs"));
				list.setEcop_syu_yf(rs.getString("ecod_syu"));
				list.setEcop_sye_yf(rs.getString("ecod_sye"));
				list.setEcop_house_yf(rs.getString("ecod_house"));
				list.setEcop_file_yf(rs.getString("ecod_file"));
				list.setEcop_fw_yf(rs.getString("ecod_fuwu"));
				list.setEcop_other_yf(rs.getString("ecod_other"));
				list.setEcop_company(rs.getString("coba_company"));
				list.setEcop_dep_company(rs.getString("ecod_remark"));
				list.setOwnmonth(rs.getString("ownmonth"));
				list.setEcop_id(rs.getString("ecod_id"));
				list.setEcop_ch_st(rs.getString("coab_name"));
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 获取未确认帐单月份
	public List<EmCommissionOutPayModel> getzdownmonthlist(String ecod_id,String nowmonth)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmCommissionOutPayModel> list = new ArrayList<EmCommissionOutPayModel>();
		String sqlstr = "select case when c.ownmonth IS null then a.ownmonth else c.ownmonth end ownmonth from EmCommissionOutPayDif a left join EmFinanceCommissionOut b on b.gid=a.gid and b.ownmonth=a.ownmonth and b.efco_cabc_id=a.ecod_cabc_id left join CoFinanceMonthlyBill c on c.cfmb_number=b.efco_cfmb_number where cfmb_PersonnelConfirm=0 and ecod_id="
				+ ecod_id;
		try {
			System.out.println(sqlstr);
			rs = db.GRS(sqlstr);
	
			if (rs.getRow() == 0) {
				for (int i = Integer.parseInt(nowmonth); i < Integer.parseInt(nowmonth)+3; i++) {
					EmCommissionOutPayModel model = new EmCommissionOutPayModel();
					model.setOwnmonth(String.valueOf(i));
					list.add(model);
				}
			}

			while (rs.next()) {
				for (int i = Integer.parseInt(rs.getString("ownmonth")); i < Integer
						.parseInt(rs.getString("ownmonth")) + 3; i++) {
						EmCommissionOutPayModel model = new EmCommissionOutPayModel();
					model.setOwnmonth(String.valueOf(i));
					list.add(model);
				}
			}

		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
}
