package dal.Embase;

import Model.CoCompactModel;
import Model.EmbaseModel;
import Model.Emcontactinfo;
import Model.embaseyfModel;
import Util.CheckString;
import Util.Log4jInit;
import Util.UserInfo;

import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.zkoss.zul.ListModelList;

public class Embasedal {
	private static dbconn conn = new dbconn();

	/**
	 * 根据embaid查询gid
	 * 
	 * @return
	 */
	public int getGidForEmba(int emba_id) {
		int gid = 0;
		String sql = " SELECT gid FROM Embase WHERE emba_id = ? ";
		PreparedStatement pstmt = conn.getpre(sql);
		try {
			pstmt.setInt(1, emba_id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				gid = rs.getInt("gid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gid;
	}

	// 查询员工是否已入职
	public boolean embaInfo(String idcard) {
		boolean b = false;
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = " SELECT gid FROM Embase WHERE emba_idcard=? and emba_state>0";
		try {
			list = db.find(sql, EmbaseModel.class, null, idcard);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}

		return b;
	}

	/**
	 * 添加是否有档案委托服务
	 * 
	 * @param emcont
	 */
	public void addEmcont(Emcontactinfo emcont) {
		String sql = " INSERT INTO Emcontactinfo(gid,emzt_iffileservice )  VALUES(?,?) ";
		Object[] objs = { emcont.getGid(), emcont.getEmzt_iffileservice() };
		try {
			conn.updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新是否有档案委托服务
	 * 
	 * @param emcont
	 */
	public void modifyEmcont(Emcontactinfo emcont) {
		String sql = " UPDATE Emcontactinfo SET emzt_iffileservice = ?  WHERE gid = ? ";
		Object[] objs = { emcont.getEmzt_iffileservice(), emcont.getGid() };
		try {
			conn.updatePrepareSQL(sql, objs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param gid
	 * @return int 查询结果条数
	 */
	public int isShowGjj(int gid) {
		int count = 0;
		String sql = " select count(*)c from coglist a inner join coofferlist b on a.cgli_coli_id=b.coli_id where a. gid=? and coli_name ='住房公积金服务' and cgli_startdate <=isnull(cgli_stopdate,204912)";
		PreparedStatement pstmt = conn.getpre(sql);
		try {
			pstmt.setInt(1, gid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int isShowShebao(int gid) {
		int count = 0;
		String sql = "select count(*)c from coglist a inner join coofferlist b on a.cgli_coli_id=b.coli_id where a. gid=? and coli_name ='社会保险服务' and cgli_startdate <=isnull(cgli_stopdate,204912)";
		PreparedStatement pstmt = conn.getpre(sql);
		try {
			pstmt.setInt(1, gid);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("c");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	// 根据查询语句获取员工列表数据集
	private ResultSet getembase(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from (SELECT top 5000 emba_id,gid,cid,emba_name,emba_spell,emba_pinyin,emba_sex, emba_idcard,emba_idcardclass,emba_mobile,"
				+ "emba_email,emba_state,emba_wt, coba_shortname, coba_client, sein_shebao, sein_gjj,sein_shangbao,sein_wt,"
				+ "sein_shebaob, sein_gjjb,sein_da, sein_zj,sein_ldyg, sein_xc,emba_statestr,"
				+ "empic,mobile from  View_Embaselist where 1=1");
		sql.append(str);
		sql.append(" ) a order by emba_state desc,cid,gid ");
		System.out.println(sql.toString());
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 根据查询语句获取员工列表数据集
	public List<EmbaseModel> searchembase(String str, boolean search) {
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		if (search) {
			sql.append("select * from (SELECT top 5000 emba_id,gid,cid,emba_name,emba_spell,emba_pinyin,emba_sex, emba_idcard,emba_idcardclass,emba_mobile,"
					+ "emba_email,emba_state,emba_wt, coba_shortname coba_name, coba_client, sein_shebao, sein_gjj,sein_shangbao,sein_wt,"
					+ "sein_shebaob, sein_gjjb,sein_da, sein_zj,sein_ldyg, sein_xc,emba_statestr,"
					+ "empic,mobile,coba_assistant from  View_Embaselist where 1=1");
			sql.append(str);
			sql.append(")z  order by emba_state desc,cid,gid ");
		} else {
			sql.append("SELECT top 20 emba_id,gid,cid,emba_name,emba_spell,emba_pinyin,emba_sex, emba_idcard,emba_idcardclass,emba_mobile,"
					+ "emba_email,emba_state,emba_wt, coba_shortname coba_name, coba_client, sein_shebao, sein_gjj,sein_shangbao,sein_wt,"
					+ "sein_shebaob, sein_gjjb,sein_da, sein_zj,sein_ldyg, sein_xc,emba_statestr,"
					+ "empic,mobile,coba_assistant from  View_Embaselist where 1=1");
			sql.append(str);
			sql.append("  order by emba_state desc,cid,gid ");
		}

		List<EmbaseModel> list = new ListModelList<>();
		try {
			list = db.find(sql.toString(), EmbaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据查询语句获取员工列表数据集(员服)
	private ResultSet getembaseyf(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select a.gid,a.cid,emba_addtime,"
				+ "emba_emhb_ownmonth,emba_emsb_ownmonth,"
				+ " case WHEN  isnull(emba_emhb_ownmonth,0)=0 THEN emba_emsb_ownmonth"
				+ " WHEN isnull(emba_emsb_ownmonth,0)=0 THEN emba_emhb_ownmonth"
				+ "  when isnull(emba_emhb_ownmonth,0)< isnull(emba_emsb_ownmonth,0) then emba_emhb_ownmonth "
				+ " else emba_emsb_ownmonth end ownmonth,"
				+ "emzt_datastate,emzt_r_record,coba_shortname,coba_spell,coba_company,coba_client,coba_namespell,emba_name,emba_spell,a.emba_idcard,emba_mobile,emba_tapr_id,"
				+ "convert(nvarchar(10),isnull(e.tali_addtime,e2.tali_addtime),120) tali_addtime,cosp_enty_caliname,emba_state,emzt_contactstate,emzt_contactstateweb,emzt_contactstateyd,coba_assistant,");
		sql.append(" CASE emba_state WHEN   '1' THEN '已申报' WHEN '3' THEN '退回' WHEN '5' THEN '未处理' END AS emba_statestr,");
		sql.append(" CASE emzt_contactstate  WHEN  '0' THEN '未联系' WHEN '1' THEN '部分确认' WHEN '2' THEN '联系完成' END AS emzt_contactstatestr,");
		sql.append("  CASE emzt_contactstateweb  WHEN  '0' THEN '未创建' WHEN '1' THEN '未更新' WHEN '2' THEN '部分更新' WHEN '3' THEN '已更新' END AS emzt_contactstatewebstr,");
		sql.append("  CASE emzt_datastate  WHEN  '0' THEN '未提交' WHEN '1' THEN '部分提交' WHEN '2' THEN '提交完成'   END AS emzt_datastatestr,");
		sql.append(" CASE emzt_contactstateyd  WHEN  '0' THEN '未创建' WHEN '1' THEN '未更新' WHEN '2' THEN '部分更新' WHEN '3' THEN '已更新' END AS emzt_contactstateydstr");
		sql.append(" from EmBase a left join CoBase b on a.cid=b.CID");
		sql.append(" left join Emcontactinfo c on a.gid=c.gid");
		sql.append(" left join TaskProcess d on a.emba_tapr_id=d.tapr_id");
		sql.append(" left join TaskList e on d.tapr_tali_id=e.tali_id");
		sql.append(" left join TaskProcess2 d2 on a.emba_tapr_id=d2.tapr_id");
		sql.append(" left join TaskList2 e2 on d.tapr_tali_id=e2.tali_id");
		sql.append(" left join CoBaseServePromise f on a.cid=f.cid");
		sql.append(" left join (select * from CoShebao where cosb_state=1)  co on co.CID=a.cid");
		sql.append(" left join CoHousingFund ch on ch.cid=a.cid and cohf_state=1");
		sql.append(" where  emba_state in (1,3,5) ");
		sql.append(str);
		sql.append(" order by  a.gid desc");
		rs = conn.GRS(sql.toString());
		System.out.println(sql.toString());
		return rs;
	}

	// 查询拥有福利项目的员工名单
	public List<EmbaseModel> getnameFlList(Integer cid, String client,
			String name) {
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct emba_name"
				+ " from embase a"
				+ " inner join cobase b on a.cid=b.cid"
				+ " where a.CID in ("
				+ "select CID from CoCompact where coco_id in ("
				+ "select coof_coco_id from CoOffer where coof_id in ("
				+ "select coli_coof_id from CoOfferList where coli_state=1 and coli_pclass ='员工福利'))"
				+ "and CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 ))";
		if (cid != null && !cid.equals("")) {
			sql += " and a.cid=" + cid;
		}
		if (client != null && !client.equals("")) {
			sql += " and coba_client='" + client + "'";
		}
		if (name != null && !name.equals("")) {
			sql += " and emba_name like '%" + name + "%'";
		}
		sql += " order by emba_name";
		System.out.println(sql);
		try {
			list = db.find(sql, EmbaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询员工福利项目有效名单
	public List<EmbaseModel> getflList(Integer cid, String client, String name,
			Integer item) {
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct a.cid,a.gid,emba_name,emba_indate,coli_name,emba_idcard,"
				+ "coba_company,coba_client,"
				+ "embf_id,coli_remark,coli_standard,coli_flpaykind,colm_fee,g.dept"
				+ " from EmBase a"
				+ " inner join CoBase b on a.cid=b.cid"
				+ " inner join CoGList c on a.gid=c.gid"
				+ " inner join CoOfferList d on c.cgli_coli_id=d.coli_id"
				+ " inner join CoOLMode e on d.coli_id=e.colm_coli_id"
				+ " inner join EmBenefit f on d.coli_name=f.embf_name"
				+ " left join login g on b.coba_client=g.log_name"
				+ "	and (case when colm_startmonth=0 and colm_stopmonth=0 then 1 else 0 end=1"
				+ "	or case when colm_startmonth=0 then"
				+ "	case when DATEDIFF(M,emba_indate,GETDATE())< colm_stopmonth then 1 	else 0 end"
				+ "	else case when DATEDIFF(M,emba_indate,GETDATE())>colm_startmonth then 1 else 0 end"
				+ "	end=1)"
				+ " where coli_pclass='员工福利' and cgli_stopdate is null"
				+ " and not exists ("
				+ "		select 1 from EmWelfare "
				+ "		where a.gid=gid and emwf_embf_id=f.embf_id and emwf_state not in (11,15,16)"
				+ ")"
				+ " and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		if (cid != null && !cid.equals("")) {
			sql += " and a.cid=" + cid;
		}
		if (client != null && !client.equals("")) {
			sql += " and coba_client='" + client + "'";
		}
		if (name != null && !name.equals("")) {
			sql += " and emba_name like '%" + name + "%'";
		}
		if (item != null && !item.equals("")) {
			sql += " and embf_id=" + item;
		}
		sql += " order by coba_company,emba_name";

		System.out.println(sql);
		try {
			list = db.find(sql, EmbaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据查询语句获取员工列表数据集
	private ResultSet getcountsumres(String str) throws Exception {
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select  (SELECT count(*) from  View_Embaselist where 1=1 and coba_client ='"
				+ UserInfo.getUsername() + "' ");
		sql.append(str);
		sql.append(" )  as countall ,(select COUNT (*) from View_Embaselist where emba_state=1 and coba_client ='"
				+ UserInfo.getUsername() + "' ");
		sql.append(str);
		sql.append(" ) as counton ,(select COUNT (*) from View_Embaselist where emba_state=0 and coba_client ='"
				+ UserInfo.getUsername() + "' ");
		sql.append(str);
		sql.append(" )  as countout ");
		rs = conn.GRS(sql.toString());
		return rs;
	}

	// 检查员工信息表中是否存在传入的身份证
	public boolean haveIDCard(Integer cid, Integer gid, String idcard,
			String idcardclass) {
		boolean b = false;
		dbconn db = new dbconn();
		String sql = "select 1 from embase where emba_state>0 and emba_state<>4";

		if (cid != null) {
			sql = sql + " and cid=" + cid;
		}

		if (gid != null) {
			sql = sql + " and gid!=" + gid;
		}
		if (idcardclass != null) {
			if (idcardclass.equals("身份证")) {
				sql += " and case when len(dbo.idcard(emba_idcard))=18 then dbo.idcard(emba_idcard) else emba_idcard end="
						+ " case when len(dbo.idcard('"
						+ idcard
						+ "'))=18 then dbo.idcard('"
						+ idcard
						+ "') else '"
						+ idcard + "' end";
			} else {
				sql += " and emba_idcard='" + idcard + "'";
			}
		}

		System.out.println(sql);
		List<EmbaseModel> list = new ListModelList<>();
		try {
			list = db.find(sql, EmbaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 根据查询语句获取员工列表数据集
	public List<EmbaseModel> getembaList(String str) {
		List<EmbaseModel> embaList = new ArrayList<EmbaseModel>();
		try {
			ResultSet rs = getembase(str);
			while (rs.next()) {
				embaList.add(new EmbaseModel(rs.getInt("emba_id"), rs
						.getInt("gid"), rs.getInt("cid"), rs
						.getString("emba_name"), rs.getString("emba_spell"), rs
						.getString("emba_pinyin"), rs.getString("emba_sex"), rs
						.getString("emba_idcard"), rs
						.getString("emba_idcardclass"), rs
						.getString("emba_mobile"), rs.getString("emba_email"),
						rs.getInt("emba_state"), rs.getString("emba_wt"), rs
								.getString("coba_shortname"), rs
								.getString("coba_client"), rs
								.getString("sein_shebao"), rs
								.getString("sein_gjj"), rs
								.getString("sein_shangbao"), rs
								.getString("sein_wt"), rs
								.getString("sein_shebaob"), rs
								.getString("sein_gjjb"), rs
								.getString("sein_da"), rs.getString("sein_zj"),
						rs.getString("sein_ldyg"), rs.getString("sein_xc"), rs
								.getString("emba_statestr"),
						rs.getInt("empic"), rs.getInt("mobile")));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return embaList;
	}

	// 根据查询语句获取员工列表数据集(员服)
	public List<embaseyfModel> getembaListyf(String str) {
		List<embaseyfModel> embaList = new ArrayList<embaseyfModel>();
		try {
			ResultSet rs = getembaseyf(str);
			while (rs.next()) {
				embaList.add(new embaseyfModel(rs.getString("ownmonth"), rs
						.getInt("gid"), rs.getInt("cid"), rs
						.getString("coba_shortname"), rs
						.getString("coba_spell"), rs.getString("coba_company"),
						rs.getString("coba_namespell"), rs
								.getString("emba_name"), rs
								.getString("emba_spell"), rs
								.getString("emba_idcard"), rs
								.getString("emba_mobile"), rs
								.getInt("emba_tapr_id"), rs
								.getString("tali_addtime"), rs
								.getString("cosp_enty_caliname"), rs
								.getInt("emba_state"), rs
								.getInt("emzt_contactstate"), rs
								.getInt("emzt_datastate"), rs
								.getInt("emzt_contactstateweb"), rs
								.getInt("emzt_contactstateyd"), rs
								.getString("emba_statestr"), rs
								.getString("emzt_contactstatestr"), rs
								.getString("emzt_datastatestr"), rs
								.getString("emzt_contactstatewebstr"), rs
								.getString("emzt_contactstateydstr"), rs
								.getString("coba_client"), rs
								.getString("emzt_r_record"), rs
								.getString("emba_emhb_ownmonth"), rs
								.getString("emba_emsb_ownmonth"),rs.getString("coba_assistant")));
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return embaList;
	}

	// 根据查询语句获取页面统计数
	public List<Integer> getcountsum(String str) {
		// int[] i =new int[5] ;
		List<Integer> i = new ArrayList<Integer>();

		try {
			ResultSet rs = getcountsumres(str);

			while (rs.next()) {
				i.add(rs.getInt("countall"));
				i.add(rs.getInt("counton"));
				i.add(rs.getInt("countout"));

			}

		}

		catch (Exception e) {
			System.out.println(e.toString());
		}

		return i;
	}

	// 综合查询列表
	public String[] getembaList() {

		StringBuilder spstr = new StringBuilder();

		try {

			ResultSet rs = conn
					.GRS("SELECT  top 100* from  View_Embaselist where 1=1 ");
			while (rs.next()) {

				spstr.append(rs.getString("emba_spell") + "|"
						+ rs.getInt("gid") + "|" + rs.getString("emba_name")
						+ "|" + rs.getString("emba_statestr") + "|"
						+ rs.getString("coba_shortname"));
				spstr.append(",");
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return spstr.toString().split(",");

	}

	// 分配员工报价单时获取员工信息
	public List<EmbaseModel> getEmbaseInfoById(Integer embaId)
			throws SQLException {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select emba_id,a.cid,coba_company,emba_name,emba_idcard,emba_wt,emba_hjadd,emba_nationality,emba_tapr_id from EmBaseNotIn a inner join cobase b on a.cid=b.cid where emba_state=1 and emba_id=?";
		list = db.find(sql, EmbaseModel.class,
				dbconn.parseSmap(EmbaseModel.class), embaId);
		return list;
	}

	// 获取员工名称列表
	public List<EmbaseModel> getEmbaseByName(String client, String cid,
			String name) throws SQLException {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		client = client.equals("") ? "%" : client;
		cid = cid.equals("") ? "%" : cid;
		String idcard = name.equals("") ? "%" : name;
		name = name.equals("") ? "%" : name + "%";
		StringBuilder sql = new StringBuilder();
		sql.append("select top 10 cid,gid,emba_name from embase");
		sql.append(" where emba_state=1 and cid in (select distinct cid from cobase where coba_client like ?) and cid like ?");
		sql.append(" and (emba_name like ? or emba_spell like ? or gid like ? or emba_idcard like ?)");
		sql.append(" order by emba_name");
		list = db.find(sql.toString(), EmbaseModel.class,
				dbconn.parseSmap(EmbaseModel.class), client, cid, name, name,
				name, idcard);
		return list;
	}

	// 获取员工列表
	public List<EmbaseModel> getEmbaseByCid(String client, String cid,
			String gid) throws SQLException {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		client = client.equals("") ? "%" : client;
		cid = cid.equals("") ? "%" : cid;
		gid = gid.equals("") ? "%" : gid + "%";

		StringBuilder sql = new StringBuilder();
		sql.append("select a.cid,a.gid,coba_company,emba_name,emba_idcard,emba_sex,emar_id,emar_archivetype+isnull('('+emar_archivearea+')','')archive,case emar_state when 1 then '在册' when 0 then '已调离' end statename,isnull(emar_state,0)emar_state");
		sql.append(" from embase a inner join cobase b on a.cid=b.cid left join emarchive c on a.gid=c.gid");
		sql.append(" where coba_servicestate=1 and emba_state=1");
		sql.append(" and coba_client like ? and a.cid like ? and a.gid like ?");
		sql.append(" order by coba_shortname,emba_name");
		list = db.find(sql.toString(), EmbaseModel.class,
				dbconn.parseSmap(EmbaseModel.class), client, cid, gid);

		return list;
	}

	// 根据ID获取员工信息
	public List<EmbaseModel> getEmBaseById(Integer id) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select emba_gjjuname,emba_gjjidcard,emba_sbuname,emba_sbidcard,emba_id,a.cid,a.gid,coba_company,coba_shortname,coba_client,"
				+ "emba_name,emba_idcard,emba_idcardclass,emba_sex,emba_phone,emba_mobile,emba_email,"
				+ "convert(varchar(10),emba_indate,120)emba_indate,convert(varchar(10),emba_birth,120)emba_birth,"
				+ "emba_wifename,emba_wifeidcard,emba_sb_place,emba_house_place,"
				+ "emba_excompany,emba_excompanyid,emba_houseid,emba_house_radix,emba_emsb_foreigner,emba_nationality,"
				+ "emba_emhb_startdate,emba_emhb_stopdate,emba_emhb_ownmonth,emba_emhb_feeownmonth,emba_emhb_reason,emba_emhb_total,"
				+ "emba_sbuname ,emba_sbidcard,emba_sb_radix,emba_gjjuname,emba_gjjidcard,emba_house_radix,emba_house_cpp,emba_state,"
				+ " emba_tapr_id,emba_addname,emba_addtime,emba_emsb_jlbj1,emba_emsb_jlbj2,emba_emsb_jlbj3 "
				+ "from embase a "
				+ "inner join cobase b on a.cid=b.cid "
				+ "where emba_id=? ";
		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工信息
	public List<EmbaseModel> getEmbaseInfoByPama(EmbaseModel em) {
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emba_id,a.cid,a.gid,b.coba_company,emba_name,emba_idcard,"
				+ "case emba_state when 1 then '在职' else '离职' end statename "
				+ "from embase a "
				+ "inner join cobase b on a.cid=b.cid "
				+ "where 1=1";
		if (em.getCid() != null) {
			sql = sql + " and a.cid=" + em.getCid();
		}
		if (em.getEmba_name() != null) {
			if (CheckString.isNum(em.getEmba_name())) {
				if (em.getEmba_name().length() < 10) {
					sql = sql + " and a.gid like '%" + em.getEmba_name() + "%'";
				} else {
					sql = sql + " and a.emba_idcard like '%"
							+ em.getEmba_name() + "%'";
				}
			} else {
				sql = sql + " and (emba_name like '%" + em.getEmba_name()
						+ "%' or emba_spell like '%" + em.getEmba_name()
						+ "%' or a.emba_idcard like '%" + em.getEmba_name()
						+ "%')";
			}
		}
		if (em.getEmba_state() != null) {
			if (!em.getEmba_state().equals("")) {
				sql = sql + " and a.emba_state=" + em.getEmba_state();
			}
		}

		sql = sql + " order by emba_state desc,coba_company,emba_name";
		try {
			list = db.find(sql, EmbaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 分配报价单时查询员工
	public List<EmbaseModel> getEmbaseInfo(EmbaseModel em) {
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ";
		if (em.isTop()) {
			if (em.getTopNum() != null && !em.getTopNum().equals("")) {
				sql += " top " + em.getTopNum();
			}
		}
		sql += " emba_id,a.cid,a.gid,b.coba_company,emba_name,emba_spell,emba_idcard,emba_sex,emba_wt,case when isnull(c.gid,0) >0 then 1 else 0 end num"
				+ " from embase a "
				+ " inner join cobase b on a.cid=b.cid "
				+ " left join (select distinct gid from coglist where cgli_stopdate is null) c on a.gid=c.gid "
				+ " where 1=1 ";
		if (em.getEmba_state() != null) {
			sql = sql + " and emba_state=" + em.getEmba_state();
		}

		if (em.getCid() != null) {
			sql = sql + " and a.cid=" + em.getCid();
		}

		if (em.getGid() != null) {
			if (!em.getGid().equals("")) {
				sql = sql + " and a.gid like '%" + em.getGid() + "%'";
			}
		}
		if (em.getEmba_idcard() != null) {
			if (!em.getEmba_idcard().equals("")) {
				sql = sql + " and a.emba_idcard like '%" + em.getEmba_idcard()
						+ "%'";
			}
		}

		if (em.getEmba_name() != null) {
			if (CheckString.isNum(em.getEmba_name())) {
				if (em.getEmba_name().length() < 10) {
					sql = sql + " and a.gid like '%" + em.getEmba_name() + "%'";
				} else {
					sql = sql + " and a.emba_idcard like '%"
							+ em.getEmba_name() + "%'";
				}
			} else {
				sql = sql + " and (emba_name like '%" + em.getEmba_name()
						+ "%' or emba_spell like '%" + em.getEmba_name()
						+ "%' or a.emba_idcard like '%" + em.getEmba_name()
						+ "%')";
			}
		}

		// sql = sql+
		// " group by emba_id,a.cid,a.gid,b.coba_company,emba_name,emba_spell,emba_idcard,emba_sex ";

		if (em.getNum() != null) {
			if (em.getNum().equals(0)) {
				sql = sql + " and isnull(c.gid,0)=0";
			} else if (em.getNum().equals(1)) {
				sql = sql + " and isnull(c.gid,0)>0";
			}
		}

		sql = sql + " order by num,emba_name";
		System.out.println(sql);
		try {
			list = db.find(sql, EmbaseModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 根据GID获取员工信息,档案业务
	public List<EmbaseModel> getEmBaseByGid(Integer gid) {
		List<EmbaseModel> list = new ListModelList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select coba_shortname,emba_folk,emba_sex,a.emba_id,a.cid,a.gid,coba_company,coba_client,emba_name,emba_idcard,emba_mobile,emba_wifename,emba_wifeidcard from embase a inner join cobase b on a.cid=b.cid where a.gid=?";

		try {
			list = db.find(sql, EmbaseModel.class,
					dbconn.parseSmap(EmbaseModel.class), gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmbaseModel> getInfoListByName(String cid, String name)
			throws SQLException {
		List<EmbaseModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select top 20 a.cid,a.gid,emba_name,emba_idcard,emhu_houseid "
				+ "from embase a "
				+ "inner join emhouseupdate b on a.cid=b.cid and a.gid=b.gid "
				+ "inner join cobase c on a.cid=c.cid");
		sql.append(" where (a.cid like ? or coba_company like ? or coba_shortname like ? or coba_englishname like ? or coba_namespell like ? )"
				+ "and (a.gid like ? or emba_name like ? or emba_idcard like ? or emba_spell like ? or emhu_houseid like ?)");
		sql.append(" order by cid,emba_name");

		list = db.find(sql.toString(), EmbaseModel.class,
				dbconn.parseSmap(EmbaseModel.class), cid, cid, cid, cid, cid,
				name, name, name, name, name);
		return list;

	}

	// 获取单个员工信息
	public EmbaseModel getEmbaseInfo(String str) {
		EmbaseModel model = new EmbaseModel();
		String sql = " select * from EmBase a,CoBase b where a.cid=b.cid "
				+ str;
		ResultSet rs = null;
		dbconn db = new dbconn();
		try {
			rs = db.GRS(sql);
			while (rs.next()) {
				model.setCid(rs.getInt("cid"));
				model.setGid(rs.getInt("gid"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setEmba_name(rs.getString("emba_name"));
				model.setEmba_idcard(rs.getString("emba_idcard"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				model.setCoba_company(rs.getString("coba_company"));
				model.setCoba_client(rs.getString("coba_client"));
				model.setEmba_state(rs.getInt("emba_state"));
				model.setEmba_mobile(rs.getString("emba_mobile"));
				model.setEmba_email(rs.getString("emba_email"));
				model.setEmba_id(rs.getInt("emba_id"));
				model.setEmba_tapr_id(rs.getInt("emba_tapr_id"));
				model.setEmba_computerid(rs.getString("Emba_computerid"));
				model.setEmba_sex(rs.getString("Emba_sex"));
				model.setEmba_birth(rs.getString("emba_birth"));
				model.setEmba_spell(rs.getString("emba_spell"));
				model.setEmba_pinyin(rs.getString("emba_pinyin"));

			}
		} catch (Exception e) {

		}
		return model;
	}

	// 新增员工基本信息及报价单信息
	public Integer addbaseInfo(EmbaseModel em, String id, String sd1, String sd2) {
		Integer i = 0;
		dbconn db = new dbconn();

		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoglistAdd_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, em.getCid(),
									em.getEmba_name(), em.getEmba_idcard(),
									em.getEmba_idcardclass(),
									em.getEmba_indate(), em.getEmba_birth(),
									em.getEmba_sex(), em.getEmba_phone(),
									em.getEmba_mobile(), em.getEmba_email(),
									em.getEmba_emsb_foreigner(),
									em.getEmba_sb_place(),
									em.getEmba_house_place(), id, sd1, sd2,
									em.getEmba_addname(),
									em.getEmba_nationality(),
									em.getEmzt_iffileservice(), 0).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	// 修改员工基本信息
	public Integer modInfo(EmbaseModel em) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update embase set emba_modname='" + em.getEmba_modname()
				+ "',emba_modtime=getdate()";
		if (em.getEmba_name() != null) {
			if (em.getEmba_name() != "") {
				sql = sql + ",emba_name='" + em.getEmba_name() + "'";
			}
		}

		if (em.getEmba_sex() != null) {
			if (em.getEmba_sex() != "") {
				sql = sql + ",emba_sex='" + em.getEmba_sex() + "'";
			}
		}
		// if (em.getEmba_state()!= null) {
		//
		// sql = sql + ",emba_state='" + em.getEmba_state() + "'";
		//
		// }
		if (em.getEmba_folk() != null) {
			if (em.getEmba_folk() != "") {
				sql = sql + ",emba_folk='" + em.getEmba_folk() + "'";
			}
		}
		if (em.getEmba_idcard() != null) {
			if (em.getEmba_idcard() != "") {
				sql = sql + ",emba_idcard='" + em.getEmba_idcard() + "'";
			}
		}
		if (em.getEmba_idcardclass() != null) {
			if (em.getEmba_idcardclass() != "") {
				sql = sql + ",emba_idcardclass='" + em.getEmba_idcardclass()
						+ "'";
			}
		}
		if (em.getEmba_birth() != null) {
			if (em.getEmba_birth() != "") {
				sql = sql + ",emba_birth='" + em.getEmba_birth() + "'";
			}
		}
		if (em.getEmba_phone() != null) {
			if (em.getEmba_phone() != "") {
				sql = sql + ",emba_phone='" + em.getEmba_phone() + "'";
			}
		}
		if (em.getEmba_mobile() != null) {
			if (em.getEmba_mobile() != "") {
				sql = sql + ",emba_mobile='" + em.getEmba_mobile() + "'";
			}
		}
		if (em.getEmba_email() != null) {
			if (em.getEmba_email() != "") {
				sql = sql + ",emba_email='" + em.getEmba_email() + "'";
			}
		}

		if (em.getEmba_sb_radix() != null) {
			sql = sql + ",emba_sb_radix=" + em.getEmba_sb_radix();
		}

		if (em.getEmba_computerid() != null) {
			sql = sql + ",emba_computerid='" + em.getEmba_computerid() + "'";
		}

		if (em.getEmba_formula() != null) {
			if (em.getEmba_formula() != "") {
				sql = sql + ",emba_formula='" + em.getEmba_formula() + "'";
			}
		}
		if (em.getEmba_sb_hj() != null) {
			if (em.getEmba_sb_hj() != "") {
				sql = sql + ",emba_sb_hj='" + em.getEmba_sb_hj() + "'";
			}
		}

		if (em.getEmba_house_radix() != null) {
			sql = sql
					+ ",emba_house_radix="
					+ em.getEmba_house_radix().setScale(2,
							BigDecimal.ROUND_HALF_UP);
		}

		if (em.getEmba_houseid() != null) {
			sql = sql + ",emba_houseid='" + em.getEmba_houseid() + "'";
		}

		if (em.getEmba_house_cpp() != null) {
			if (em.getEmba_house_cpp() > 0) {
				sql = sql + ",emba_house_cpp=" + em.getEmba_house_cpp();
			}
		}

		if (em.getEmba_emsb_ownmonth() != null) {
			if (!em.getEmba_emsb_ownmonth().equals("")) {
				sql = sql + ",emba_emsb_ownmonth='"
						+ em.getEmba_emsb_ownmonth() + "'";
			}
		}

		if (em.getEmba_emsb_feeownmonth() != null) {
			if (!em.getEmba_emsb_feeownmonth().equals("")) {
				sql = sql + ",emba_emsb_feeownmonth='"
						+ em.getEmba_emsb_feeownmonth() + "'";
			}
		}

		if (em.getEmba_emsb_m1() != null) {
			if (!em.getEmba_emsb_m1().equals("")) {
				if (em.getEmba_emsb_m1().equals("0")) {
					sql = sql + ",emba_emsb_m1=null";
				} else {
					sql = sql + ",emba_emsb_m1='" + em.getEmba_emsb_m1() + "'";
				}
			}
		}
		if (em.getEmba_emsb_r1() != null) {
			if (!em.getEmba_emsb_r1().equals("")) {
				if (em.getEmba_emsb_r1().equals(0)) {
					sql = sql + ",emba_emsb_r1=null";
				} else {
					sql = sql + ",emba_emsb_r1=" + em.getEmba_emsb_r1();
				}
			}
		}
		if (em.getEmba_emsb_m2() != null) {
			if (!em.getEmba_emsb_m2().equals("")) {
				if (em.getEmba_emsb_m2().equals("0")) {
					sql = sql + ",emba_emsb_m2=null";
				} else {
					sql = sql + ",emba_emsb_m2='" + em.getEmba_emsb_m2() + "'";
				}
			}
		}
		if (em.getEmba_emsb_r2() != null) {
			if (!em.getEmba_emsb_r2().equals("")) {
				if (em.getEmba_emsb_r2().equals(0)) {
					sql = sql + ",emba_emsb_r2=null";
				} else {
					sql = sql + ",emba_emsb_r2=" + em.getEmba_emsb_r2();
				}
			}
		}
		if (em.getEmba_emsb_m3() != null) {
			if (!em.getEmba_emsb_m3().equals("")) {
				if (em.getEmba_emsb_m3().equals("0")) {
					sql = sql + ",emba_emsb_m3=null";
				} else {
					sql = sql + ",emba_emsb_m3='" + em.getEmba_emsb_m3() + "'";
				}
			}
		}
		if (em.getEmba_emsb_r3() != null) {
			if (!em.getEmba_emsb_r3().equals("")) {
				if (em.getEmba_emsb_r3().equals(0)) {
					sql = sql + ",emba_emsb_r3=null";
				} else {
					sql = sql + ",emba_emsb_r3=" + em.getEmba_emsb_r3();
				}
			}
		}

		if (em.getEmba_emsb_jlbj1() != null) {
			if (em.isChk_jlbj1() == false) {
				sql = sql + ",emba_emsb_jlbj1=0";
			} else {
				sql = sql + ",emba_emsb_jlbj1=1";
			}

		}

		if (em.getEmba_emsb_jlbj2() != null) {
			if (em.isChk_jlbj2() == false) {
				sql = sql + ",emba_emsb_jlbj2=0";
			} else {
				sql = sql + ",emba_emsb_jlbj2=1";
			}
		}

		if (em.getEmba_emsb_jlbj3() != null) {
			if (em.isChk_jlbj3() == false) {
				sql = sql + ",emba_emsb_jlbj3=0";
			} else {
				sql = sql + ",emba_emsb_jlbj3=1";
			}
		}

		if (em.getEmba_emhb_ownmonth() != null) {
			if (!em.getEmba_emhb_ownmonth().equals("")) {
				sql = sql + ",emba_emhb_ownmonth='"
						+ em.getEmba_emhb_ownmonth() + "'";
			}
		}
		if (em.getEmba_emhb_feeownmonth() != null) {
			if (!em.getEmba_emhb_feeownmonth().equals("")) {
				sql = sql + ",emba_emhb_feeownmonth='"
						+ em.getEmba_emhb_feeownmonth() + "'";
			}
		}

		if (em.getEmba_emhb_startdate() != null) {
			if (!em.getEmba_emhb_startdate().equals("")) {
				if (em.getEmba_emhb_startdate().equals("0")) {
					sql = sql + ",emba_emhb_startdate=null";
				} else {
					sql = sql + ",emba_emhb_startdate='"
							+ em.getEmba_emhb_startdate() + "'";
				}
			}
		}
		if (em.getEmba_emhb_stopdate() != null) {
			if (!em.getEmba_emhb_stopdate().equals("")) {
				if (em.getEmba_emhb_stopdate().equals("0")) {
					sql = sql + ",emba_emhb_stopdate=null";
				} else {
					sql = sql + ",emba_emhb_stopdate='"
							+ em.getEmba_emhb_stopdate() + "'";
				}
			}
		}
		if (em.getEmba_emhb_reason() != null) {
			if (!em.getEmba_emhb_reason().equals("")) {
				sql = sql + ",emba_emhb_reason='" + em.getEmba_emhb_reason()
						+ "'";
			}
		}
		if (em.getEmba_emhb_radix() != null) {
			sql = sql
					+ ",emba_emhb_radix="
					+ em.getEmba_emhb_radix().setScale(2,
							BigDecimal.ROUND_HALF_UP);

		}
		if (em.getEmba_emhb_total() != null) {
			if (!em.getEmba_emhb_total().equals("")) {
				if (em.getEmba_emhb_total().equals(BigDecimal.ZERO)) {
					sql = sql + ",emba_emhb_total=null ";
				} else {
					sql = sql + ",emba_emhb_total=" + em.getEmba_emhb_total();
				}
			}

		}

		if (em.getEmba_state() != null) {
			// if (em.getEmba_state() > 0) {
			sql = sql + ",emba_state=" + em.getEmba_state();
			// }
		}
		if (em.getEmba_sbuname() != null) {
			if (!em.getEmba_sbuname().equals("")) {
				sql = sql + ",emba_sbuname='" + em.getEmba_sbuname() + "'";
			}
		}
		if (em.getEmba_sbidcard() != null) {
			if (!em.getEmba_sbidcard().equals("")) {
				sql = sql + ",emba_sbidcard='" + em.getEmba_sbidcard() + "'";
			}
		}
		if (em.getEmba_gjjuname() != null) {
			if (!em.getEmba_gjjuname().equals("")) {
				sql = sql + ",emba_gjjuname='" + em.getEmba_gjjuname() + "'";
			}
		}
		if (em.getEmba_gjjidcard() != null) {
			if (!em.getEmba_gjjidcard().equals("")) {
				sql = sql + ",emba_gjjidcard='" + em.getEmba_gjjidcard() + "'";
			}
		}
		if (em.getEmba_remark() != null) {
			if (!em.getEmba_remark().equals("")) {
				sql = sql + ",emba_remark='" + em.getEmba_remark() + "'";
			}
		}
		if (em.getEmba_sbname1() != null) {
			if (!em.getEmba_sbname1().equals("")) {
				sql = sql + ",Emba_sbname1='" + em.getEmba_sbname1() + "'";
			}
		}
		if (em.getEmba_sbname2() != null) {
			if (!em.getEmba_sbname2().equals("")) {
				sql = sql + ",Emba_sbname2='" + em.getEmba_sbname2() + "'";
			}
		}
		if (em.getEmba_sbname3() != null) {
			if (!em.getEmba_sbname3().equals("")) {
				sql = sql + ",Emba_sbname3='" + em.getEmba_sbname3() + "'";
			}
		}
		if (em.getEmba_sbname4() != null) {
			if (!em.getEmba_sbname4().equals("")) {
				sql = sql + ",Emba_sbname4='" + em.getEmba_sbname4() + "'";
			}
		}

		if (em.getEmba_sbage1() != null) {
			if (!em.getEmba_sbage1().equals("")) {
				sql = sql + ",Emba_sbage1='" + em.getEmba_sbage1() + "'";
			}
		}

		if (em.getEmba_sbage2() != null) {
			if (!em.getEmba_sbage2().equals("")) {
				sql = sql + ",Emba_sbage2='" + em.getEmba_sbage2() + "'";
			}
		}

		if (em.getEmba_sbage3() != null) {
			if (!em.getEmba_sbage3().equals("")) {
				sql = sql + ",Emba_sbage3='" + em.getEmba_sbage3() + "'";
			}
		}

		if (em.getEmba_sbage4() != null) {
			if (!em.getEmba_sbage4().equals("")) {
				sql = sql + ",Emba_sbage4='" + em.getEmba_sbage4() + "'";
			}
		}

		if (em.getEmba_sbidcard1() != null) {
			if (!em.getEmba_sbidcard1().equals("")) {
				sql = sql + ",Emba_sbidcard1='" + em.getEmba_sbidcard1() + "'";
				/*
				 * sql = sql + ",Emba_sbbirth1='" +
				 * IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard1()) + "'";
				 */
			}
		}
		if (em.getEmba_sbbirth1() != null) {
			if (!em.getEmba_sbbirth1().equals("")) {
				sql = sql + ",Emba_sbbirth1='" + em.getEmba_sbbirth1() + "'";
			}
		}

		if (em.getEmba_sbidcard2() != null) {
			if (!em.getEmba_sbidcard2().equals("")) {
				sql = sql + ",Emba_sbidcard2='" + em.getEmba_sbidcard2() + "'";
				/*
				 * sql = sql + ",Emba_sbbirth2='" +
				 * IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard2()) + "'";
				 */
			}
		}

		if (em.getEmba_sbidcard3() != null) {
			if (!em.getEmba_sbidcard3().equals("")) {
				sql = sql + ",Emba_sbidcard3='" + em.getEmba_sbidcard3() + "'";
				/*
				 * sql = sql + ",Emba_sbbirth3='" +
				 * IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard3()) + "'";
				 */
			}
		}

		if (em.getEmba_sbidcard4() != null) {
			if (!em.getEmba_sbidcard4().equals("")) {
				sql = sql + ",Emba_sbidcard4='" + em.getEmba_sbidcard4() + "'";
				/*
				 * sql = sql + ",Emba_sbbirth4='" +
				 * IdcardUtil.getBirthByIdCard(em.getEmba_sbidcard4()) + "'";
				 */
			}
		}

		if (em.getEmba_sbrelation2() != null) {
			if (!em.getEmba_sbrelation2().equals("")) {
				sql = sql + ",Emba_sbrelation2='" + em.getEmba_sbrelation2()
						+ "'";
			}
		}

		if (em.getEmba_sbrelation1() != null) {
			if (!em.getEmba_sbrelation1().equals("")) {
				sql = sql + ",Emba_sbrelation1='" + em.getEmba_sbrelation1()
						+ "'";
			}
		}

		if (em.getEmba_sbrelation3() != null) {
			if (!em.getEmba_sbrelation3().equals("")) {
				sql = sql + ",Emba_sbrelation3='" + em.getEmba_sbrelation3()
						+ "'";
			}
		}

		if (em.getEmba_sbrelation4() != null) {
			if (!em.getEmba_sbrelation4().equals("")) {
				sql = sql + ",Emba_sbrelation4='" + em.getEmba_sbrelation4()
						+ "'";
			}
		}

		if (em.getEmba_hjtype() != null) {
			if (!em.getEmba_hjtype().equals("")) {
				sql = sql + ",Emba_hjtype='" + em.getEmba_hjtype() + "'";
			}
		}

		if (em.getEmba_hjadd() != null) {
			if (!em.getEmba_hjadd().equals("")) {
				sql = sql + ",Emba_hjadd='" + em.getEmba_hjadd() + "'";
			}
		}

		if (em.getEmba_native() != null) {
			if (!em.getEmba_native().equals("")) {
				sql = sql + ",Emba_native='" + em.getEmba_native() + "'";
			}
		}

		sql = sql + " where emba_id=" + em.getEmba_id();
		System.out.println(sql);

		Log4jInit.toLog(sql);

		if (em.getEmba_id() != null && em.getEmba_id() > 0
				&& em.getEmba_modname() != null
				&& !em.getEmba_modname().equals("")) {
			try {
				updateshebaogjj(em);
				i = db.updatePrepareSQL(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return i;
	}

	public Integer updateshebaogjj(EmbaseModel em) {

		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call updatesbgjjyz_p_zmj(?,?,?,?,?)}", Types.INTEGER,
					em.getGid(), em.getEmba_name(), em.getEmba_idcard(),
					UserInfo.getUsername(), 0).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 修改员工基本信息及报价单信息
	public Integer modbaseinfo(EmbaseModel em, String id, String sd1, String sd2) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer
					.valueOf(db
							.callWithReturn(
									"{?=call CoglistMod_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
									Types.INTEGER, em.getEmba_id(),
									em.getCid(), em.getEmba_name(),
									em.getEmba_idcard(),
									em.getEmba_idcardclass(),
									em.getEmba_indate(), em.getEmba_birth(),
									em.getEmba_sex(), em.getEmba_phone(),
									em.getEmba_mobile(), em.getEmba_email(),
									em.getEmba_emsb_foreigner(),
									em.getEmba_sb_place(),
									em.getEmba_house_place(), id, sd1, sd2,
									em.getEmba_addname(),
									em.getEmba_nationality(),
									em.getEmzt_iffileservice(), 0).toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public int UpdateTaprid(int daid, int taprid) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "update embase set emba_tapr_id=? where emba_id=?";

		try {
			row = db.updatePrepareSQL(sql, taprid, daid);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 修改雇员服务中心联系状态
	public int Updatestate(int gid, int state, int type) {
		dbconn db = new dbconn();
		int row = 0;
		String sql = "";
		if (type == 1) // 联系状态：0未联系/1部分确认/2联系完成
		{
			sql = "update Emcontactinfo set emzt_contactstate=? where gid=?";
		} else if (type == 2) // 材料状态:0未提交/1部分提交/2提交完成
		{
			sql = "update Emcontactinfo set emzt_datastate=? where gid=?";
		}

		try {
			row = db.updatePrepareSQL(sql, state, gid);

		} catch (Exception e) {
			// TODO: handle exception
		}
		return row;
	}

	// 根据员工编号查询员工合同信息
	public CoCompactModel getEmBaseCompact(Integer gid) {
		CoCompactModel model = new CoCompactModel();
		String sql = "select top 1 coco_id,coco_compacttype,coco_compactid from cocompact c "
				+ "inner join (select * from coglist a left join coofferlist b "
				+ "on b.coli_id=a.cgli_coli_id) b on c.coco_id=b.coli_coco_id and gid="
				+ gid;
		try {
			dbconn db = new dbconn();
			ResultSet rs = db.GRS(sql);
			while (rs.next()) {
				model.setCoco_id(rs.getInt("coco_id"));
				model.setCoco_compacttype(rs.getString("coco_compacttype"));
				model.setCoco_compactid(rs.getString("coco_compactid"));
			}
		} catch (Exception e) {

		}
		return model;
	}

	// 根据cid获取该公司的员工信息
	public List<EmbaseModel> getEmBaseListByCid(Integer cid) {
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select a.gid,emba_name,emba_idcard,emba_sex,emba_birth,emba_hjadd,esiu_hj,"
				+ "emba_mobile,emba_phone,emba_email,emba_address,emba_indate,esiu_computerid,esiu_radix,esiu_yl,"
				+ "esiu_yltype,esiu_gs,esiu_sye,esiu_syu,esiu_addtime,emhu_radix,emhu_bonus,emhu_cpp,"
				+ "emhu_opp,ebco_incept_date,ebco_maturity_date,ebco_probation_incept,ebco_probation_mdate,emba_gz_bank,"
				+ "emba_gz_account,emba_house_bank,emba_house_account,emba_sy_bank,emba_sy_account,"
				+ "emba_writeoff_bank,emba_writeoff_account,emba_school,emba_education,"
				+ "ebco_working_station,emhu_houseid,emba_hjadd,emba_folk,emba_privateemail,emba_wt "
				+ "from EmBase a left join EmShebaoUpdate b on a.gid=b.gid left join EmHouseUpdate c "
				+ "on a.gid=c.gid left join embasecompact d on a.gid=d.gid and ebco_state<>6 "
				+ "and ebco_change<>'终止' and ebco_change<>'解除' "
				+ " where emba_state<>0 and emba_state<>3 and emba_state<>4 and a.cid=?";

		try {
			list = db.find(sql, EmbaseModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
