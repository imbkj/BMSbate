package dal.EmBodyCheck;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmBcItemGroupModel;
import Model.EmBcSetupModel;
import Model.EmBodyCheckCommitModel;
import Util.UserInfo;

public class EmBc_CommitSelectDal {
	// 年度体检信息
	public List<EmBodyCheckCommitModel> getEmBodyCheckCommitInfo(
			EmBodyCheckCommitModel em, boolean mod) {
		List<EmBodyCheckCommitModel> list = new ArrayList<EmBodyCheckCommitModel>();
		dbconn db = new dbconn();
		String sql = "select a.ebcc_id,ebcc_ownmonth,a.cid,a.gid,emba_name+case when bd is null then '' else '('+convert(varchar(10),bd,120)+')' end emba_name,coba_company,coba_shortname,coba_client,"
				+ "convert(varchar(10),ebcc_bookdate,120)ebcc_bookdate,ebcc_hospital,ebcc_address,"
				+ "ebcc_groupid,ebcc_itemid,ebcc_modname,convert(varchar(10),ebcc_modtime,120)ebcc_modtime,"
				+ "ebcs_hospital hospital,ebsa_address address,isnull(ebig_name,ebit_name)items,bd,case when h.gid>0 then '发现员工今年有体检记录' end remark,"
				+ "emba_sex,case emba_marital when '未婚' then '未婚' when '初婚' then '已婚' when '离异' then '已婚' when '丧偶' then '已婚' when '已婚' then '已婚' when '再婚' then '已婚' end emba_marital"
				+ ",emba_idcard,convert(varchar(50),emba_indate,120)emba_indate,DATEDIFF(m,bd,GETDATE()) month,"
				+ "case DATEPART(dw,ebcc_bookdate) when 1 then ebsa_w7 when 2 then ebsa_w1 when 3 then ebsa_w2 when 4 then ebsa_w3 when 5 then ebsa_w4 when 6 then ebsa_w5 when 7 then ebsa_w6 end wk"
				+ " from EmBodyCheckCommit a "
				+ "inner join EmBase b on a.gid=b.gid "
				+ "inner join CoBase c on a.cid=c.cid "
				+ "left join EmBcSetup d on a.ebcc_hospital=d.ebcs_id "
				+ "left join EmBcSetupAddress e on a.ebcc_address=e.ebsa_id "
				+ "left join EmBcItemGroup f on a.ebcc_groupid=f.ebig_id "
				+ "left join EmBodyCheckItem g on a.ebcc_itemid=g.ebit_id "
				+ "left join ("
				+ "	select z.gid,isnull(y.ebcl_plandate,y.ebcl_bookdate)bd "
				+ "	from EmBodyCheck z inner join EmBodyCheckList y on z.embc_id=y.ebcl_embc_id "
				+ "	where embc_state=1 and ebcl_flag=1"
				+ "	 and embc_id in (select MAX(embc_id)id from EmBodyCheck group by gid)"
				+ ")h on a.gid=h.GID"
				+ " where ebcc_state=0 and ebcc_deleteState=0 ";
		if (mod) {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			sql = sql
					+ " and a.CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}

		if (em.getCoba_company() != null) {
			if (!em.getCoba_company().equals("")) {
				sql += " and (a.cid like '%" + em.getCoba_company()
						+ "%' or coba_company  like '%" + em.getCoba_company()
						+ "%' or coba_shortname like '%" + em.getCoba_company()
						+ "%' or coba_namespell like '%" + em.getCoba_company()
						+ "%')";
			}
		}

		if (em.getEmba_name() != null) {
			if (!em.getEmba_name().equals("")) {
				sql += " and (a.gid like '%" + em.getEmba_name()
						+ "%' or emba_name  like '%" + em.getEmba_name()
						+ "%' or emba_spell like '%" + em.getEmba_name()
						+ "%')";
			}
		}

		if (em.getCoba_client() != null) {
			if (!em.getCoba_client().equals("")) {
				sql += " and coba_client ='" + em.getCoba_client() + "'";
			}
		}
		if (em.getEmba_sex() != null) {
			if (!em.getEmba_sex().equals("")) {
				sql += " and emba_sex ='" + em.getEmba_sex() + "'";
			}
		}
		if (em.getEmba_marital() != null) {
			if (!em.getEmba_marital().equals("")) {

				sql += " and case emba_marital when '未婚' then '未婚' when '初婚' then '已婚' when '离异' then '已婚' when '丧偶' then '已婚' when '已婚' then '已婚' when '再婚' then '已婚' end ='"
						+ em.getEmba_marital() + "'";
			}
		}
		if (em.getEbcc_ownmonth() != null) {
			if (!em.getEbcc_ownmonth().equals("")) {
				sql += " and ebcc_ownmonth =" + em.getEbcc_ownmonth();
			}
		}

		sql = sql + " order by a.ebcc_ownmonth,coba_client,coba_shortname,emba_name";
		System.out.println(sql);
		try {
			list = db.find(sql, EmBodyCheckCommitModel.class,
					dbconn.parseSmap(EmBodyCheckCommitModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询机构名称+地址
	public List<EmBcSetupModel> getEmBcSetupname(String str) {
		List<EmBcSetupModel> fidlist = new ArrayList<EmBcSetupModel>();
		dbconn db = new dbconn();
		String sql = "select ebsa_id,ebcs_hospital +' -  '+ ebsa_address ebcs_hospital,ebcs_id from EmBcSetup a ";
		sql = sql
				+ " inner join  EmBcSetupAddress b on ebcs_id=ebsa_ebcs_id where ebcs_state=1 and ebsa_state=1";

		sql = sql + str;
		try {
			fidlist = db.find(sql, EmBcSetupModel.class,
					dbconn.parseSmap(EmBcSetupModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fidlist;
	}

	// 查询体检组合
	public List<EmBcItemGroupModel> getEmBcGroupInfo(String str, boolean mod) {
		ResultSet rs = null;
		List<EmBcItemGroupModel> list = new ArrayList<EmBcItemGroupModel>();
		dbconn db = new dbconn();
		String sql = "select ebig_id id ,ebig_name name from EmBcItemGroup where ebig_state=1 ";
		if (mod) {
			sql = sql
					+ " and CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and  Dat_edited=1 )";
		} else {
			sql = sql
					+ " and CID in ( select cid from DataPopedom where log_id="
					+ UserInfo.getUserid() + " and dat_selected=1 )";
		}
		sql = sql + str;
		try {
			list = db.find(sql, EmBcItemGroupModel.class,
					dbconn.parseSmap(EmBcItemGroupModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询所属月份
	public List<EmBodyCheckCommitModel> ownmonthlist() {
		List<EmBodyCheckCommitModel> list = new ArrayList<EmBodyCheckCommitModel>();
		dbconn db = new dbconn();
		String sql = "select distinct ebcc_ownmonth"
				+ " from EmBodyCheckCommit"
				+ " where ebcc_deletestate=0 and ebcc_state=0"
				+ " and CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and dat_selected=1 )"
				+ " order by ebcc_ownmonth desc";
		try {
			list = db.find(sql, EmBodyCheckCommitModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
