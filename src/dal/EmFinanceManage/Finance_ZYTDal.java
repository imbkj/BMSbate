package dal.EmFinanceManage;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.FinanceZYTModel;

public class Finance_ZYTDal {

	// 查询未生成TXT数据
	public List<FinanceZYTModel> search(String addname) {
		dbconn db = new dbconn();
		String sql = "select cofz_id id,a.cid,isnull(coba_shortname,cofz_company) company,cofz_fl flTotal,cofz_other otherTotal"
				+ ",cofz_file fileTotal,cofz_fee feeTotal,cofz_sb sbTotal,cofz_gjj gjjTotal"
				+ ",cofz_total total,cofz_uid uid,cofz_type compactType"
				+ ",cofz_addname addname,cofz_addtime"
				+ " from CoFinanceZYT a left join cobase b on a.cid=convert(varchar(50),b.cid)"
				+ " where cofz_state=1 and cofz_addname=?";
		List<FinanceZYTModel> list = new ListModelList<>();
		try {
			list = db.find(sql, FinanceZYTModel.class, null, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询TXT结果
	public List<FinanceZYTModel> txt(String city, String addname) {
		dbconn db = new dbconn();
		List<FinanceZYTModel> list = new ListModelList<>();
		String sql = "select '付'+?+convert(varchar(4),convert(int,SUBSTRING(max(ownmonth),6,8)))+'月服务费'content,SUM(cofz_fee)fee,'' uid,'' kmId from CoFinanceZYT where cofz_addname=? and cofz_state=1"
				+ " union all"
				+ " select '付'+?+convert(varchar(4),convert(int,SUBSTRING(max(ownmonth),6,8)))+'月档案费',SUM(cofz_file),'','' from CoFinanceZYT where cofz_addname=? and cofz_state=1"
				+ " union all"
				+ " select '付'+?+convert(varchar(4),convert(int,SUBSTRING(max(ownmonth),6,8)))+'月福利费',SUM(cofz_fl),'','' from CoFinanceZYT where cofz_addname=? and cofz_state=1"
				+ " union all"
				+ " select '付'+?+convert(varchar(4),convert(int,SUBSTRING(max(ownmonth),6,8)))+'月额外费用',SUM(cofz_other),'','' from CoFinanceZYT where cofz_addname=? and cofz_state=1"
				+ " union all"
				+ " select '付'+isnull(cofz_shortname,cofz_company)+?+'员工'+convert(varchar(4),convert(int,SUBSTRING(ownmonth,6,8)))+'月'+case ftype when 'cofz_sb' then '保险费' when 'cofz_gjj' then '住房公积金' end,fee,cofz_uid"
				+ ",convert(varchar(50),case cofz_type when 'AF' then case ftype when 'cofz_sb' then b.cpac_af when 'cofz_gjj' then c.cpac_af end when 'FS' then case ftype when 'cofz_sb' then b.cpac_fs when 'cofz_gjj' then c.cpac_fs end end) "
				+ " from CoFinanceZYT a"
				+ " unpivot("
				+ " fee for ftype in (cofz_sb,cofz_gjj))fee"
				+ " left join CoPAccount b on b.cpac_name='社保费'"
				+ " left join CoPAccount c on c.cpac_name='住房公积金'"
				+ " where cofz_addname=? and cofz_state=1";
		System.out.println(sql);
		try {
			list = db.find(sql, FinanceZYTModel.class, null, city, addname,
					city, addname, city, addname, city, addname, city, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer add(FinanceZYTModel m) {
		dbconn db = new dbconn();
		Integer i = 0;
		try {
			i = (Integer) db
					.callWithReturn(
							"{?=call FinanceZYTImport_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?)}",
							Types.INTEGER, m.getCid(), m.getCompany(),
							m.getPayOwnmonth(), m.getFlTotal(),
							m.getOtherTotal(), m.getFileTotal(),
							m.getFeeTotal(), m.getSbTotal(), m.getGjjTotal(),
							m.getTotal(), m.getUid(), m.getCompactType(),
							m.getAddname());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer modData(Integer id, String uid, String compacttype) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "update CoFinanceZYT set cofz_uid=? , cofz_type=? where cofz_id=?";
		try {
			i = db.updatePrepareSQL(sql, uid, compacttype, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer delData(String addname) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "delete from CoFinanceZYT where cofz_addname=? and cofz_state=1";
		try {
			i = db.updatePrepareSQL(sql, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public List<FinanceZYTModel> getTotal(String addname) {
		dbconn db = new dbconn();
		List<FinanceZYTModel> list = new ListModelList<>();
		String sql = "select SUM(cofz_fl)flTotal,SUM(cofz_other)otherTotal,SUM(cofz_file)fileTotal,SUM(cofz_fee)feeTotal,SUM(cofz_sb)sbTotal,SUM(cofz_gjj)gjjTotal,SUM(cofz_total)total"
				+ " from CoFinanceZYT"
				+ " where cofz_addname=? and cofz_state=1";
		try {
			list = db.find(sql, FinanceZYTModel.class, null, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
}
