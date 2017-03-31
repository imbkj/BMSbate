package dal.EmBaseCompact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.DocumentsInfoPubPicModel;
import Model.EmBaseCompactListModel;
import Util.UserInfo;

public class EmBaseCompact_MakeListDal {
	String username = UserInfo.getUsername();
	
	// 获取首页列表
	public List<EmBaseCompactListModel> getemcompactmainlist(String st,String name,String client)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String sqlstr = "";
		String sqlstr1="";
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String username = UserInfo.getUsername();
		String userid=UserInfo.getUserid();
		if (!name.equals("")){
			sqlstr1=" and emba_name like '%"+name+"%'";
		}
		if (!client.equals("")){
			sqlstr1=sqlstr1+" and coba_client='"+client+"'";
		}
		if (!userid.equals("5")&&!userid.equals("10")&&!userid.equals("11")&&!userid.equals("59")&&!userid.equals("23")&&!userid.equals("25")){
			sqlstr1=sqlstr1+" and  coba_client in (select username from getchildbase("+userid+",5))";
		}
		if (st.equals("劳动合同60日内到期")) {
			sqlstr = "select a.gid,a.cid,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,coba_client,CONVERT(varchar(100),ebco_remind_date, 23) ebco_remind_date,ebco_remind_state,ebco_id from EmBaseCompact a left join cobase b on b.cid=a.cid left join embase c on c.gid=a.gid where ebco_change<>'解除' and ebco_change<>'终止'  and datediff(dd,getdate(),ebco_maturity_date)<=60 and datediff(dd,getdate(),ebco_maturity_date)>50 and isnull(ebco_term,'')<>'长期'"+sqlstr1;
		}
		if (st.equals("劳动合同50日内到期")) {
			sqlstr = "select a.gid,a.cid,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,coba_client,CONVERT(varchar(100),ebco_remind_date, 23) ebco_remind_date,ebco_remind_state,ebco_id from EmBaseCompact a left join cobase b on b.cid=a.cid left join embase c on c.gid=a.gid  where ebco_change<>'解除' and ebco_change<>'终止'  and datediff(dd,getdate(),ebco_maturity_date)<=50 and datediff(dd,getdate(),ebco_maturity_date)>15  and isnull(ebco_term,'')<>'长期'"+sqlstr1;
		}
		if (st.equals("劳动合同15日内到期")) {
			sqlstr = "select a.gid,a.cid,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,coba_client,CONVERT(varchar(100),ebco_remind_date, 23) ebco_remind_date,ebco_remind_state,ebco_id from EmBaseCompact a left join cobase b on b.cid=a.cid left join embase c on c.gid=a.gid  where ebco_change<>'解除' and ebco_change<>'终止'  and datediff(dd,getdate(),ebco_maturity_date)<=15 and datediff(dd,getdate(),ebco_maturity_date)>0  and isnull(ebco_term,'')<>'长期'"+sqlstr1;
		}
		if (st.equals("劳动合同已过期")) {
			sqlstr = "select a.gid,a.cid,coba_shortname,emba_name,CONVERT(varchar(100), ebco_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebco_maturity_date, 23) ebco_maturity_date,ebco_change,coba_client,CONVERT(varchar(100),ebco_remind_date, 23) ebco_remind_date,ebco_remind_state,ebco_id from EmBaseCompact a left join cobase b on b.cid=a.cid left join embase c on c.gid=a.gid  where ebco_change<>'解除' and ebco_change<>'终止'  and datediff(dd,getdate(),ebco_maturity_date)<=0  and isnull(ebco_term,'')<>'长期'"+sqlstr1;
		}
		if (st.equals("劳动合同生效20天未签回")) {
			sqlstr = "select a.gid,a.cid,coba_shortname,emba_name,CONVERT(varchar(100), ebcc_incept_date, 23) ebco_incept_date,CONVERT(varchar(100), ebcc_maturity_date, 23) ebco_maturity_date,ebcc_change ebco_change,coba_client,CONVERT(varchar(100),ebcc_remind_date, 23) ebco_remind_date,ebcc_remind_state ebco_remind_state,ebcc_id ebco_id from EmBaseCompactChange a left join cobase b on b.cid=a.cid left join embase c on c.gid=a.gid where ebcc_change<>'解除' and ebcc_change<>'终止'  and datediff(dd,emba_indate,getdate())>20  and ebcc_state<>10 and ebcc_state<>11 and emba_state<>0 and ebcc_sign_date is null"+sqlstr1;
		}

		try {
			System.out.println(sqlstr + st);
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebco_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebco_maturity_date"));
				model.setEbco_change(rs.getString("ebco_change"));
				model.setEbco_addname(rs.getString("coba_client"));
				model.setEbco_remind_date(rs.getString("ebco_remind_date"));
				model.setEbco_remind_state(rs.getString("ebco_remind_state"));
				model.setEbco_id(rs.getInt("ebco_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未制作数据
	public List<EmBaseCompactListModel> getemcompactlist(String str) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String userid=UserInfo.getUserid();
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();

		String sqlstr = "select ebcc_id,coba_shortname,emba_name,CONVERT(varchar(100), ebcc_incept_date, 23) ebcc_incept_date,CONVERT(varchar(100), ebcc_maturity_date, 23) ebcc_maturity_date,ebcc_change,ebcc_tapr_id,case when ebcc_state=0 then '未制作' else '退回' end ebst,a.gid,a.cid,coba_client from EmBaseCompactChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid  left join DataPopedom d on d.cid=a.cid  where ebcc_state in (0,10) "+str+"  and log_id="+userid;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebcc_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebcc_maturity_date"));
				model.setEbco_change(rs.getString("ebcc_change"));
				model.setEbco_id(rs.getInt("ebcc_id"));
				model.setEbco_tapr_id(rs.getInt("ebcc_tapr_id"));
				model.setEbco_auditing_name(rs.getString("ebst"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEbco_addname(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未审核数据
	public List<EmBaseCompactListModel> getautemcompactlist(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String userid=UserInfo.getUserid();
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcc_id,coba_shortname,emba_name,CONVERT(varchar(100), ebcc_incept_date, 23) ebcc_incept_date,CONVERT(varchar(100), ebcc_maturity_date, 23) ebcc_maturity_date,ebcc_change,ebcc_tapr_id,a.gid,a.cid,coba_client from EmBaseCompactChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid left join DataPopedom d on d.cid=a.cid  where ebcc_state=1"+str+" and log_id="+userid;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebcc_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebcc_maturity_date"));
				model.setEbco_change(rs.getString("ebcc_change"));
				model.setEbco_id(rs.getInt("ebcc_id"));
				model.setEbco_tapr_id(rs.getInt("ebcc_tapr_id"));
				model.setGid(rs.getString("gid"));
				model.setCid(rs.getString("cid"));
				model.setEbco_addname(rs.getString("coba_client"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未打印数据
	public List<EmBaseCompactListModel> getpremcompactlist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcc_id,coba_shortname,emba_name,CONVERT(varchar(100), ebcc_incept_date, 23) ebcc_incept_date,CONVERT(varchar(100), ebcc_maturity_date, 23) ebcc_maturity_date,ebcc_change,ebcc_tapr_id from EmBaseCompactChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where ebcc_state=2";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebcc_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebcc_maturity_date"));
				model.setEbco_change(rs.getString("ebcc_change"));
				model.setEbco_id(rs.getInt("ebcc_id"));
				model.setEbco_tapr_id(rs.getInt("ebcc_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同数据
	public List<EmBaseCompactListModel> getchecklist(String name,
			String ebco_maturity_date, String cl) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		String str = "";
		String str1 = "";
		String str2 = "";
		str2 = " and ebcc_state=0";
		if (cl.equals("1")) {
			str2 = "";
		}

		if (!name.equals("")) {
			str = " and emba_name like '%" + name + "%'";
		}
		if (!ebco_maturity_date.equals("")) {
			str1 = " and ebcc_maturity_date='" + ebco_maturity_date + "'";
		}
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcc_id,coba_shortname,emba_name,CONVERT(varchar(100), ebcc_incept_date, 23) ebcc_incept_date,CONVERT(varchar(100), ebcc_maturity_date, 23) ebcc_maturity_date,ebcc_change,ebcc_tapr_id,CONVERT(varchar(100), emba_indate,23) indate from EmBaseCompactChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where 1=1"
				+ str + str1 + str2;
		System.out.println(sqlstr);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebcc_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebcc_maturity_date"));
				model.setEbco_change(rs.getString("ebcc_change"));
				model.setEbco_id(rs.getInt("ebcc_id"));
				model.setEbco_tapr_id(rs.getInt("ebcc_tapr_id"));
				model.setEbco_addtime(rs.getString("indate"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> getsiemcompactlist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcc_id,coba_shortname,emba_name,CONVERT(varchar(100), ebcc_incept_date, 23) ebcc_incept_date,CONVERT(varchar(100), ebcc_maturity_date, 23) ebcc_maturity_date,ebcc_change,ebcc_tapr_id from EmBaseCompactChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where ebcc_state=3";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebcc_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebcc_maturity_date"));
				model.setEbco_change(rs.getString("ebcc_change"));
				model.setEbco_id(rs.getInt("ebcc_id"));
				model.setEbco_tapr_id(rs.getInt("ebcc_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> getfilingemcompactlist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcc_id,coba_shortname,emba_name,CONVERT(varchar(100), ebcc_incept_date, 23) ebcc_incept_date,CONVERT(varchar(100), ebcc_maturity_date, 23) ebcc_maturity_date,ebcc_change,ebcc_wage,ebcc_wage_gz,ebcc_working_station,ebcc_wage_gz,ebcc_tapr_id from EmBaseCompactChange a left join embase b on b.gid=a.gid left join cobase c on c.cid=a.cid where ebcc_state=4";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_shortname"));
				model.setName(rs.getString("emba_name"));
				model.setEbco_incept_date(rs.getString("ebcc_incept_date"));
				model.setEbco_maturity_date(rs.getString("ebcc_maturity_date"));
				model.setEbco_change(rs.getString("ebcc_change"));
				model.setEbco_id(rs.getInt("ebcc_id"));
				model.setEbco_wage(rs.getString("ebcc_wage"));
				model.setEbco_wage_gz(rs.getString("ebcc_wage_gz"));
				model.setEbco_working_station(rs
						.getString("ebcc_working_station"));
				model.setEbco_tapr_id(rs.getInt("ebcc_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> gettempemcompactlist()
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebct_id,coba_company,ebct_name,ebct_addname,CONVERT(varchar(100), ebct_addtime, 23) addtime,ebct_tapr_id from EmBaseCompactTemplate a left join CoBase b on b.CID=a.cid where ebct_state=0";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_company"));
				model.setName(rs.getString("ebct_name"));
				model.setEbco_incept_date(rs.getString("ebct_addname"));
				model.setEbco_maturity_date(rs.getString("addtime"));
				model.setEbco_id(rs.getInt("ebct_id"));
				model.setEbco_tapr_id(rs.getInt("ebct_tapr_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> gettempemcompactlistall(String company,
			String eb_class) throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String str = "";
		String str1 = "";
		if (!company.equals("")) {
			str = " and coba_company like '%" + company + "%'";
		}
		if (!eb_class.equals("")) {
			str1 = " and ebct_class=" + eb_class;
		}
		String sqlstr = "select ebct_id,coba_company,ebct_name,ebct_addname,CONVERT(varchar(100), ebct_addtime, 23) addtime,ebct_tapr_id,case when ebct_class=1 then '一般非标' else '特殊非标' end ebct_class from EmBaseCompactTemplate a left join CoBase b on b.CID=a.cid where 1=1"
				+ str + str1;

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("coba_company"));
				model.setName(rs.getString("ebct_name"));
				model.setEbco_incept_date(rs.getString("ebct_addname"));
				model.setEbco_maturity_date(rs.getString("addtime"));
				model.setEbco_id(rs.getInt("ebct_id"));
				model.setEbco_tapr_id(rs.getInt("ebct_tapr_id"));
				model.setEbct_class(rs.getString("ebct_class"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> gettemplist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebct_id,coba_company,ebct_name,ebct_addname,CONVERT(varchar(100), ebct_addtime, 23) addtime,ebct_tapr_id from EmBaseCompactTemplate a left join CoBase b on b.CID=a.cid where ebct_state=1";

		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setName(rs.getString("ebct_name"));
				model.setEbco_id(rs.getInt("ebct_id"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> getcom_state(String ebcc_id)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebcc_state from EmBaseCompactChange where ebcc_id="
				+ ebcc_id;
		System.out
				.println("select ebcc_state from EmBaseCompactChange where ebcc_id="
						+ ebcc_id);
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setEbco_state(rs.getString("ebcc_state"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> getverlist() throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebct_id,ebct_name,ebct_addname,ebct_addtime,case when ebct_state=0 then '历史' else '执行中' end ebct_state,ebct_type from CompactVer where ecid=1 order by ebct_id desc";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setName(rs.getString("ebct_name"));
				model.setEbco_id(rs.getInt("ebct_id"));
				model.setEbco_addname(rs.getString("ebct_addname"));
				model.setEbco_addtime(rs.getString("ebct_addtime"));
				model.setEbco_state(rs.getString("ebct_state"));
				model.setEbco_compact_type(rs.getString("ebct_type"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> getemverlist(String cid)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebct_name,ebct_id,ebct_addname,ebct_addtime,case when ebct_state=0 then '未审核' else '' end+case when ebct_state=1 then '已生效' else '' end+case when ebct_state=5 then '退回' else '' end+case when ebct_state=10 then '历史' else '' end ebct_state,ebct_compact_type from EmBaseCompactTemplate where cid="
				+ cid + " order by ebct_id desc";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setName(rs.getString("ebct_name"));
				model.setEbco_id(rs.getInt("ebct_id"));
				model.setEbco_addname(rs.getString("ebct_addname"));
				model.setEbco_addtime(rs.getString("ebct_addtime"));
				model.setEbco_state(rs.getString("ebct_state"));
				model.setEbco_compact_type(rs.getString("ebct_compact_type"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动合同未签回数据
	public List<EmBaseCompactListModel> getcoverlist(String str)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebct_id,ebct_name,ebct_addname,ebct_addtime,case when ebct_state=0 then '历史' else '执行中' end ebct_state,ebct_type from CompactVer where ecid=2 "
				+ str + " order by ebct_id desc";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setName(rs.getString("ebct_name"));
				model.setEbco_id(rs.getInt("ebct_id"));
				model.setEbco_addname(rs.getString("ebct_addname"));
				model.setEbco_addtime(rs.getString("ebct_addtime"));
				model.setEbco_state(rs.getString("ebct_state"));
				model.setEbco_term(rs.getString("ebct_type"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}

	// 显示劳动版本下载
	public List<EmBaseCompactListModel> gettempdown(String type)
			throws SQLException {
		dbconn db = new dbconn();
		ResultSet rs = null;
		List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
		String sqlstr = "select ebct_url from CompactVer where ecid=2 and ebct_type='"
				+ type + "' and ebct_state=1";
		try {
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				EmBaseCompactListModel model = new EmBaseCompactListModel();
				model.setCompany(rs.getString("ebct_url"));
				list.add(model);
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		} finally {
			db.Close();
		}
		return list;
	}
	
	// 显示劳动版本下载
		public List<EmBaseCompactListModel> getclientlist()
				throws SQLException {
			dbconn db = new dbconn();
			ResultSet rs = null;
			List<EmBaseCompactListModel> list = new ArrayList<EmBaseCompactListModel>();
			String sqlstr = "select distinct coba_client from embasecompact a left join cobase b on b.cid=a.cid where coba_client is not null and ebco_change in ('新签','续签') order by coba_client";
			try {
				rs = db.GRS(sqlstr);
				while (rs.next()) {
					EmBaseCompactListModel model = new EmBaseCompactListModel();
					model.setEbco_addname(rs.getString("coba_client"));
					list.add(model);
				}
			} catch (Exception e) {
				System.out.print(e.toString());
			} finally {
				db.Close();
			}
			return list;
		}

	private static dbconn conn = new dbconn();

	// 根据coco_id获取公司退回信息
	private ResultSet getOutContRs(String coco_id) throws Exception {
		String sql = "select * from EmBaseCompactChange where ebcc_id=?";
		PreparedStatement psmt = conn.getpre(sql);
		psmt.setString(1, coco_id);
		ResultSet rs = psmt.executeQuery();
		return rs;
	}

	// 根据coco_id获取退回信息
	public ArrayList<DocumentsInfoPubPicModel> getoutcont(String coco_id)
			throws Exception {
		ArrayList<DocumentsInfoPubPicModel> list = new ArrayList<DocumentsInfoPubPicModel>();
		ResultSet rs = null;

		rs = getOutContRs(coco_id);
		try {
			while (rs.next()) {
				DocumentsInfoPubPicModel m = new DocumentsInfoPubPicModel();
				m.setDoin_content(rs.getString("ebcc_outremark"));
				list.add(m);
			}
		} catch (Exception e) {

		}
		return list;
	}
	
	//通知客户
	public int remind_ok(int ebco_id) throws Exception {
		dbconn db = new dbconn();
		ResultSet rs = null;
		//List<CI_Insurant_ListModel> list = new ArrayList<CI_Insurant_ListModel>();
		String sqlstr = "update EmBaseCompact set  ebco_remind_state=1,ebco_remind_name='"+username+"',ebco_remind_date=getdate() where ebco_id="+ebco_id;
			System.out.println(sqlstr);
		rs = db.GRS(sqlstr);
			db.Close();
		return 0;
	}
}
