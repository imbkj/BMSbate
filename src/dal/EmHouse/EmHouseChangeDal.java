package dal.EmHouse;

import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;

import bll.EmHouse.EmHouseSetBll;

import bll.EmHouse.EmHouseSetBll;

import Conn.dbconn;
import Controller.EmSheBaocard.newExcelImpl;
import Model.CoCompactModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmHouseAduitModel;
import Model.EmHouseChangeModel;
import Util.Log4jInit;
import Util.UserInfo;

public class EmHouseChangeDal {

	// 判断字符换是否为数字
	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public List<EmHouseChangeModel> getlist(Integer gid) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select gid from emhousechange where gid=?";
		try {
			list = db.find(sql, EmHouseChangeModel.class, null, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询统计数
	public List<EmHouseChangeModel> getNum(String ownmonth, String declare,
			String single) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select count(*)num from emhousechange "
				+ "where emhc_tid=0 and ownmonth=? and emhc_ifdeclare = ?";
		if (single != null) {

			if (single.equals(0)) {
				sql = sql + " and emhc_single in(0,2)";
			} else {
				sql = sql + " and emhc_single =" + single;
			}
		}
		try {
			list = db.find(sql, EmHouseChangeModel.class,
					dbconn.parseSmap(EmHouseChangeModel.class), ownmonth,
					declare);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 获取唯一列表
	public List<EmHouseChangeModel> getDistinList(String name, String sort)
			throws SQLException {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct " + name
				+ " from EmHouseChange order by " + name + " " + sort;
		list = db.find(sql, EmHouseChangeModel.class,
				dbconn.parseSmap(EmHouseChangeModel.class));
		return list;

	}

	// 查询员工所属账户类型
	public Integer emhouseSingle(Integer gid, Integer ownmonth) {
		Integer i = null;
		String single = "";
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coco_house from CoGList a"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id"
				+ " inner join cooffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " where coli_name='住房公积金服务' and a.cgli_startdate is not null"
				+ " and ? between a.cgli_startdate2 and isnull(a.cgli_stopdate,204912) and isnull(a.cgli_stopdate,204912)>=a.cgli_startdate2"
				+ " and gid=?";
		try {
			list = db.find(sql, CoCompactModel.class, null, ownmonth, gid);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (list.size() > 0) {
			single = list.get(0).getCoco_house();
			if (single != null) {
				if (single.equals("中智开户")) {
					i = 0;
				} else if (single.equals("独立开户")) {
					i = 1;
				}
			}
		}
		return i;
	}

	// 查询列表
	public List<EmHouseChangeModel> getInfoById(Integer dataid)
			throws SQLException {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emhc_id,gid,cid,ownmonth,emhc_companyid,emhc_company,emhc_shortname,emhc_name,emhc_idcard,emhc_idcardclass,emhc_hj,emhc_houseid,emhc_mobile,emhc_title,emhc_wifename,emhc_wifeidcard,emhc_degree,emhc_change,emhc_radix,isnull(emhc_trueradix,0)emhc_trueradix,emhc_cpp,emhc_opp,emhc_single,emhc_ifprogress,emhc_ifdeclare,convert(varchar(19),emhc_declaretime,120)emhc_declaretime,emhc_declarename,convert(varchar(19),emhc_addtime,120)emhc_addtime,emhc_addname,emhc_remark,convert(varchar(19),emhc_confirmtime,120)emhc_confirmtime,emhc_flag,emhc_flagname,emhc_excelfile,emhc_client,emhc_content,emhc_tid,emhc_tapr_id,case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' end emhc_statename,emhc_excompany,emhc_excompanyid");
		sql.append(" from EmHouseChange");
		sql.append(" where emhc_id = ?");
		list = db.find(sql.toString(), EmHouseChangeModel.class,
				dbconn.parseSmap(EmHouseChangeModel.class), dataid);
		return list;
	}

	// 根据字符串ID查询变更列表
	public List<EmHouseChangeModel> getInfoByIdList(String idlist)
			throws SQLException {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emhc_id,gid,cid,ownmonth,emhc_companyid,emhc_company,emhc_shortname,emhc_name,emhc_idcard,emhc_idcardclass,emhc_hj,emhc_houseid,emhc_mobile,emhc_title,emhc_wifename,emhc_wifeidcard,emhc_degree,emhc_change,emhc_radix,isnull(emhc_trueradix,0)emhc_trueradix,emhc_cpp,emhc_opp,emhc_single,emhc_ifprogress,emhc_ifdeclare,convert(varchar(19),emhc_declaretime,120)emhc_declaretime,emhc_declarename,convert(varchar(19),emhc_addtime,120)emhc_addtime,emhc_addname,emhc_remark,convert(varchar(19),emhc_confirmtime,120)emhc_confirmtime,emhc_flag,emhc_flagname,emhc_excelfile,emhc_client,emhc_content,emhc_tid,emhc_tapr_id,case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' end emhc_statename,emhc_excompany,emhc_excompanyid");
		sql.append(" from EmHouseChange");
		sql.append(" where emhc_id  in(" + idlist + ")");
		list = db.find(sql.toString(), EmHouseChangeModel.class,
				dbconn.parseSmap(EmHouseChangeModel.class));
		return list;
	}

	// 查询特殊变更列表
	public List<EmHouseChangeModel> getInfoByTid(Integer tid) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emhc_id,gid,cid,ownmonth,emhc_companyid,emhc_company,emhc_shortname,emhc_name,emhc_idcard,emhc_idcardclass,emhc_hj,emhc_houseid,emhc_mobile,emhc_title,emhc_wifename,emhc_wifeidcard,emhc_degree,emhc_change,emhc_radix,isnull(emhc_trueradix,0)emhc_trueradix,emhc_cpp,emhc_opp,emhc_single,emhc_ifdeclare,emhc_declaretime,emhc_declarename,emhc_addtime,emhc_addname,emhc_remark,emhc_confirmtime,emhc_flag,emhc_flagname,emhc_excelfile,emhc_client,emhc_content,emhc_tid,emhc_tapr_id,case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '核查失败' end emhc_statename");
		sql.append(" from EmHouseChange");
		sql.append(" where emhc_tid = ? order by emhc_id desc");
		System.out.println(sql);
		System.out.println(tid);
		try {
			list = db.find(sql.toString(), EmHouseChangeModel.class,
					dbconn.parseSmap(EmHouseChangeModel.class), tid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getListByParams
	 * @Description: TODO(前道查询公积金变更表数据)
	 * @param company
	 * @param name
	 * @param ownmonth
	 * @return
	 * @throws SQLException
	 * @return List<EmHouseChangeModel> 返回类型
	 * @throws
	 */
	public List<EmHouseChangeModel> getListByParams(String ownmonth,
			String company, String name, String change, String dept,
			String client, String declare, String ifsingle) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();

		sql.append("select emhc_id,a.gid,a.cid,a.ownmonth,emhc_companyid,emhc_company,emhc_shortname,emhc_name,emhc_idcard,"
				+ "emhc_idcardclass,emhc_hj,emhc_houseid,emhc_mobile,emhc_title,emhc_wifename,emhc_wifeidcard,emhc_degree,"
				+ "emhc_change,emhc_radix,isnull(emhc_trueradix,0)emhc_trueradix,emhc_cpp,emhc_opp,emhc_single,emhc_ifdeclare,"
				+ "convert(varchar(19),emhc_declaretime,120)emhc_declaretime,emhc_declarename,convert(varchar(19),emhc_addtime,120)emhc_addtime,"
				+ "emhc_addname,emhc_remark,convert(varchar(19),emhc_confirmtime,120)emhc_confirmtime,emhc_flag,emhc_flagname,"
				+ "emhc_excelfile,emhc_client,emhc_content,a.emhc_tid,emhc_tapr_id,"
				+ "case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中'  when 6 then '核查失败' end emhc_statename,"
				+ "case emhc_ifprogress when 11 then '等待设立' when 12 then '设立完成' when 21 then '等待转移' when 22 then '等待启封' when 23 then '调入完成' when 31 then '等待封存' when 32 then '封存完成' when 41 then '等待调整' when 42 then '调整完成' end emhc_progressname,"
				+ "num");
		sql.append(" ,coba_shortname");
		sql.append(" from EmHouseChange a inner join (select distinct log_name,dep_name from View_loginrole) b on emhc_client=b.log_name");
		sql.append(" inner join cobase c on a.cid=c.cid");
		sql.append(" left join (select emhc_tid,COUNT(*)num from EmHouseChange where emhc_tid>0 group by emhc_tid)d on a.emhc_id=d.emhc_tid");
		sql.append(" where a.emhc_tid=0");
		sql.append(" and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 )");

		if (ownmonth != null && !ownmonth.equals("")) {
			sql.append(" and a.ownmonth = " + ownmonth);
		}

		if (company != null && !company.equals("")) {
			if (isNumeric(company)) {
				sql.append(" and (a.cid like '" + company
						+ "%' or emhc_companyid like '" + company + "%')");
			} else {
				sql.append(" and emhc_company like '" + company + "%'");
			}
		}

		if (name != null && !name.equals("")) {
			if (isNumeric(name)) {
				sql.append(" and (a.gid like '" + name
						+ "%' or emhc_houseid like '" + name + "%')");
			} else {
				sql.append(" and (emhc_name like '" + name
						+ "%' or emhc_idcard like '" + name + "%')");
			}
		}

		if (change != null && !change.equals("")) {
			sql.append(" and emhc_change ='" + change + "'");
		}

		if (dept != null && !dept.equals("")) {
			sql.append(" and dep_name ='" + dept + "'");
		}

		if (client != null && !client.equals("")) {
			sql.append(" and emhc_client ='" + client + "'");
		}

		if (declare != null && !declare.equals("")) {
			switch (declare) {
			case "待确认":
				declare = "4";
				break;
			case "未申报":
				declare = "0";
				break;
			case "申报中":
				declare = "2";
				break;
			case "已申报":
				declare = "1";
				break;
			case "退回":
				declare = "3";
				break;
			case "审核中":
				declare = "5";
				break;
			case "核查失败":
				declare = "6";
				break;

			}
			sql.append(" and emhc_ifdeclare =" + declare);
		}

		if (ifsingle != null && !ifsingle.equals("")) {
			switch (declare) {
			case "中智开户":
				ifsingle = "0";
				break;
			case "中智开户(委托)":
				ifsingle = "2";
				break;
			case "独立开户":
				ifsingle = "1";
				break;
			}
			sql.append(" and emhc_single =" + ifsingle);
		}

		sql.append(" order by a.ownmonth desc,a.cid,gid,emhc_addtime desc");

		try {
			list = db.find(sql.toString(), EmHouseChangeModel.class,
					dbconn.parseSmap(EmHouseChangeModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getListByParams
	 * @Description: TODO(后道查询公积金变更表数据)
	 * @param company
	 * @param name
	 * @param ownmonth
	 * @return
	 * @throws SQLException
	 * @return List<EmHouseChangeModel> 返回类型
	 * @throws
	 */
	public List<EmHouseChangeModel> getDeclareListByParams(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ";
		if (em.getPageSize() != null) {
			if (!em.getPageSize().equals("")) {
				// sql += " top " + em.getPageSize();
			}
		}
		sql += " emhc_id,a.gid,a.cid,a.ownmonth,emhc_companyid,emhc_company,emhc_shortname,"
				+ "emhc_name,emhc_idcard,emhc_idcardclass,emhc_hj,emhc_houseid,emhc_mobile,emhc_title,"
				+ "emhc_wifename,emhc_wifeidcard,emhc_degree,emhc_change,emhc_radix,isnull(emhc_trueradix,0)emhc_trueradix,"
				+ "emhc_cpp,emhc_opp,emhc_single,emhc_ifdeclare,emhc_declaretime,emhc_declarename,"
				+ "emhc_addtime,emhc_addname,emhc_remark,emhc_confirmtime,emhc_ifsame, emhc_ifmodify, emhc_iffifteen,"
				+ "emhc_flag,emhc_flagname,emhc_excelfile,emhc_client,emhc_content,a.emhc_tid,emhc_tapr_id,"
				+ "case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中'  when 6 then '核查失败' end emhc_statename,"
				+ "emhc_ifprogress,case emhc_ifprogress when 11 then '等待设立' when 12 then '设立完成' when 21 then '等待转移' when 22 then '等待启封' when 23 then '调入完成' when 31 then '等待封存' when 32 then '封存完成' when 41 then '等待调整' when 42 then '调整完成' end emhc_progressname,"
				+ "num,coba_shortname,coba_client,e.cohf_houseid,convert(varchar(50),e.cohf_lastday)lastday,e.cohf_bankjc jc,bjid,convert(bit,0) emhc_ischecked,emhc_confirmname "
				+ "from EmHouseChange a "
				+ "inner join (select distinct log_id,log_name,dep_id,dep_name from View_loginrole) b on emhc_client=b.log_name "
				+ "inner join cobase c on a.cid=c.cid "
				+ "left join (select emhc_tid,COUNT(*)num from EmHouseChange where emhc_tid>0 group by emhc_tid)d on a.emhc_id=d.emhc_tid "
				+ "left join (select * from CoHousingFund where cohf_state=1)e on a.emhc_companyid=e.cohf_houseid and ((emhc_single=1 and a.cid=e.cid) or (emhc_single=0 and e.cid is null))"
				+ "left join (select cid,gid,ownmonth,max(emhb_id)bjid from emhousebj where emhb_ifdeclare=0 group by cid,gid,ownmonth)f on a.cid=f.cid and a.gid=f.gid and a.ownmonth=f.ownmonth"
				+ " where 1=1";

		if (em.getDataState() != null) {
			if (em.getDataState().equals(5)) {
				sql = sql + " and emhc_ifdeclare in(0,1,2,6)";
			} else if (em.getDataState().equals(6)) {
				sql = sql + " and emhc_ifdeclare in(0,1,2,3,6)";
			}
		}
		if (em.getLastday() != null) {
			if (!em.getLastday().equals("")) {
				sql += " and e.cohf_lastday=" + em.getLastday();
			}
		}
		if (em.getTsday() != null) {
			if (!em.getTsday().equals("")) {
				sql += " and e.cohf_tsday=" + em.getTsday();
			}
		}
		if (em.getJc() != null) {
			if (!em.getJc().equals("")) {
				sql += " and e.cohf_bankjc='" + em.getJc() + "'";
			}
		}

		if (em.getIdState() != null) {
			if (!em.getIdState().equals("")) {
				sql = sql + " and emhc_id in(" + em.getIdState() + ")";
			}
		} else {
			if (em.getEmhc_id() != null) {
				sql += " and emhc_id=" + em.getEmhc_id();
			}
		}
		if (em.getEmhc_tid() != null) {
			if (!em.getEmhc_tid().equals("")) {
				sql += " and a.emhc_tid=" + em.getEmhc_tid();
			}
		}
		if (em.getTidchecked() != null) {
			if (em.getTidchecked()) {
				sql += " and a.emhc_tid>0";
			}
		}

		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql = sql + " and a.ownmonth = " + em.getOwnmonth();
			}
		}

		if (em.getCid() != null) {
			if (!em.getCid().equals("")) {
				sql += " and a.cid = " + em.getCid();
			}
		}

		if (em.getEmhc_company() != null) {
			if (!em.getEmhc_company().equals("")) {
				sql = sql + " and (a.cid like '" + em.getEmhc_company()
						+ "%' or emhc_company like '" + em.getEmhc_company()
						+ "%' or emhc_companyid like '%" + em.getEmhc_company()
						+ "%' or emhc_shortname like '%" + em.getEmhc_company()
						+ "%')";
			}
		}

		if (em.getEmhc_companyid() != null) {
			if (!em.getEmhc_companyid().equals("")) {
				sql += " and emhc_companyid like '" + em.getEmhc_companyid()
						+ "%'";
			}
		}

		if (em.getEmhc_name() != null) {
			if (!em.getEmhc_name().equals("")) {
				sql = sql + " and (a.gid like '" + em.getEmhc_name()
						+ "%' or emhc_name like '" + em.getEmhc_name()
						+ "%' or emhc_idcard like '" + em.getEmhc_name()
						+ "%' or emhc_houseid like '" + em.getEmhc_name()
						+ "%')";
			}
		}

		if (em.getEmhc_change() != null) {

			if (!em.getEmhc_change().equals("")) {
				sql = sql + " and emhc_change ='" + em.getEmhc_change() + "'";
			}
		}

		if (em.getDep_name() != null) {

			if (!em.getDep_name().equals("")) {
				sql = sql + " and dep_name ='" + em.getDep_name() + "'";
			}
		}

		if (em.getEmhc_client() != null) {

			if (!em.getEmhc_client().equals("")) {
				sql = sql + " and emhc_client ='" + em.getEmhc_client() + "'";
			}
		}
		if (em.getEmhc_cpp() != null) {
			if (!em.getEmhc_cpp().equals("")) {
				sql = sql + " and emhc_cpp =" + em.getEmhc_cpp();
			}
		}

		if (em.getEmhc_ifdeclare() != null) {

			if (!em.getEmhc_ifdeclare().equals("")) {

				sql = sql + " and emhc_ifdeclare =" + em.getEmhc_ifdeclare();
			}
		}

		if (em.getSingleState() != null) {
			if (em.getSingleState().equals(0)) {
				sql = sql + " and emhc_single in (0,2)";
			} else if (em.getSingleState().equals(1)) {
				sql = sql + " and emhc_single =1";
			}
		} else {
			if (em.getEmhc_single() != null) {

				if (!em.getEmhc_single().equals("")) {

					sql = sql + " and emhc_single =" + em.getEmhc_single();
				}
			}
		}

		if (em.getEmhc_ifprogress() != null) {

			if (!em.getEmhc_ifprogress().equals("")) {
				sql = sql + " and emhc_ifprogress =" + em.getEmhc_ifprogress();
			}
		}

		if (em.getEmhc_addname() != null) {
			if (!em.getEmhc_addname().equals("")) {
				sql = sql + " and emhc_addname ='" + em.getEmhc_addname() + "'";
			}
		}

		if (em.getEmhc_addtime() != null) {
			if (!em.getEmhc_addtime().equals("")) {
				if (em.getAddtimeArea() != null) {
					if (!em.getAddtimeArea().equals("")) {
						if (em.getAddtimeArea().equals("之前")) {
							sql += " and emhc_addname <'"
									+ em.getEmhc_addtime() + "'";
						} else if (em.getAddtimeArea().equals("之后")) {
							sql += " and emhc_addname >'"
									+ em.getEmhc_addtime() + "'";
						} else {
							sql += " and emhc_addname ='"
									+ em.getEmhc_addtime() + "'";
						}
					} else {
						sql += " and emhc_addname ='" + em.getEmhc_addtime()
								+ "'";
					}
				} else {
					sql += " and emhc_addname ='" + em.getEmhc_addtime() + "'";
				}

			}
		}

		if (em.getEmhc_declaretime() != null) {
			if (!em.getEmhc_declaretime().equals("")) {
				if (em.getDeclaretimeArea() != null) {
					if (!em.getDeclaretimeArea().equals("")) {
						if (em.getDeclaretimeArea().equals("之前")) {
							sql += " and emhc_declaretime <'"
									+ em.getEmhc_declaretime() + "'";
						} else if (em.getDeclaretimeArea().equals("之后")) {
							sql += " and emhc_declaretime >'"
									+ em.getEmhc_declaretime() + "'";
						} else {
							sql += " and emhc_declaretime ='"
									+ em.getEmhc_declaretime() + "'";
						}
					} else {
						sql += " and emhc_declaretime ='"
								+ em.getEmhc_declaretime() + "'";
					}
				} else {
					sql += " and emhc_declaretime ='"
							+ em.getEmhc_declaretime() + "'";
				}

			}
		}

		sql = sql
				+ " order by a.ownmonth desc,a.cid,emhc_name,emhc_confirmtime desc";

		System.out.println(sql);
		try {
			list = db.find(sql.toString(), EmHouseChangeModel.class, null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 查询当前月份是否有未处理特殊变更
	public List<EmHouseChangeModel> getSpList(Integer ownmonth, Integer gid) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select gid from emhousechange" + " where ownmonth=?"
				+ " and gid=?"
				+ " and emhc_ifdeclare in (0,2,4) and emhc_tid>0";
		try {
			list = db.find(sql, EmHouseChangeModel.class, null, ownmonth, gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询当前月份是否有同类未处理数据
	public List<EmHouseChangeModel> getSameList(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select gid from emhousechange"
				+ " where ownmonth=? and emhc_change=?"
				+ " and (gid=? or emhc_idcard=? or emhc_houseid=?)"
				+ " and emhc_ifdeclare in (0,2,4) and emhc_tid=0";
		try {
			list = db.find(sql, EmHouseChangeModel.class, null,
					em.getOwnmonth(), em.getEmhc_change(), em.getGid(),
					em.getEmhc_idcard(), em.getEmhc_houseid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询当前月份是否有同类未处理数据
	public List<EmHouseChangeModel> getSame2List(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select gid from emhousechange"
				+ " where ownmonth=? and emhc_change=?" + " and gid=?"
				+ " and emhc_ifdeclare in (0,2,4) and emhc_tid=0";
		try {
			list = db.find(sql, EmHouseChangeModel.class, null,
					em.getOwnmonth(), em.getEmhc_change(), em.getGid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmHouseChangeModel> getChangeList(EmHouseChangeModel em,
			boolean top, Integer topNum, boolean distinct, String columns,
			String order) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		String sql = "select ";
		dbconn db = new dbconn();
		if (distinct) {
			sql = sql + " distinct ";
		}

		if (top) {
			sql = sql + " top " + topNum + " ";
		}

		if (columns != null && !columns.equals("")) {
			sql = sql + columns;
		} else {
			sql = sql
					+ " emhc_id, a.gid, a.cid, a.ownmonth, emhc_startmonth, emhc_companyid, emhc_excompany,"
					+ " emhc_excompanyid, emhc_company, emhc_shortname, emhc_name, emhc_idcard, emhc_idcardclass,"
					+ " emhc_hj, emhc_computerid, emhc_houseid, emhc_mobile, emhc_title, emhc_wifename,"
					+ " emhc_wifeidcard, emhc_degree, emhc_change, emhc_radix, emhc_trueradix, emhc_bonus,"
					+ " emhc_cpp, emhc_opp, emhc_single, emhc_ifsame, emhc_ifmodify, emhc_iffifteen,"
					+ " emhc_ifdeclare,emhc_ifprogress, emhc_ifwrong, emhc_ifmsg, convert(varchar(19),emhc_declaretime,120)emhc_declaretime,"
					+ " emhc_declarename, convert(varchar(19),emhc_addtime,120)emhc_addtime, emhc_addname,emhc_addname addname, emhc_remark, emhc_ConfirmTime,"
					+ " emhc_flag,emhc_flagname, emhc_excelfile, emhc_client, emhc_content, a.emhc_tid,"
					+ " emhc_tapr_id,case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中'  when 6 then '核查失败' end emhc_ifdeclare2,"
					+ " emhc_tapr_id,case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中'  when 6 then '核查失败' end+case when emhc_tapr_id>0 then case when tapr_appointcon=coba_client then '' else '(中心)' end else '' end emhc_ifdeclare3,"
					+ " case emhc_ifprogress when 11 then '等待设立' when 12 then '设立完成' when 21 then '等待转移' when 22 then '等待启封' when 23 then '调入完成' when 31 then '等待封存' when 32 then '封存完成' when 41 then '等待调整' when 42 then '调整完成' end emhc_ifprogress2,"
					+ " case emhc_single when 0 then '中智开户' when 1 then '独立开户' when 2 then '中智开户(委托)' else '账户类型有误' end emhc_single2,"
					+ " convert(varchar(10),emhc_cpp*100)+'%' emhc_cpp2,dep_name,isnull(tapr_state,1)tapr_state,"
					+ " case emhc_single when 0 then '中智开户' when 1 then '独立开户' when 2 then '中智开户(委托)' else '账户类型有误' end +'-'+convert(varchar(10),emhc_cpp*100)+'%' account,"
					+ " case when d.emhc_tid>0 then 1 else 0 end tid,tapr_appointcon,coba_client,cohf_bankjc jc";
			if (em.getAlarm() != null) {
				if (em.getAlarm()) {
					sql += ",smwr_tid,symr_readstate";
				}
			}
		}
		// 添加部门连接
		sql = sql
				+ " from emhousechange a "
				+ " inner join cobase b on a.cid=b.cid "
				+ " left join (select distinct log_id,dep_id,log_name,dep_name from View_loginrole where log_inure=1 )c on b.coba_client=c.log_name"
				+ " inner join cohousingfund g on a.emhc_companyid=g.cohf_houseid and ((emhc_single=1 and a.cid=g.cid) or emhc_single=0)"
				+ " left join (select distinct emhc_tid from emhousechange where emhc_tid>0)d on a.emhc_id=d.emhc_tid"
				+ " left join TaskProcess e on a.emhc_tapr_id=e.tapr_id";
		if (em.getAlarm() != null) {
			if (em.getAlarm()) {
				sql += " left join (select smwr_tid,MIN(symr_readstate)symr_readstate from View_Message where smwr_table='emhousechange' group by smwr_tid)f on a.emhc_id=f.smwr_tid";
			}
		}
		sql += " where 1=1  and a.CID in ( select cid from DataPopedom where log_id="
				+ UserInfo.getUserid() + " and  Dat_edited=1 )";

		if (em.getEmhc_id() != null) {
			if (!em.getEmhc_id().equals("")) {
				sql = sql + " and emhc_id=" + em.getEmhc_id();
			}
		}

		if (em.getNowmonth() != null) {
			if (!em.getNowmonth().equals("")) {
				sql += " and a.ownmonth>=" + em.getNowmonth();
			}
		}
		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql += " and a.ownmonth=" + em.getOwnmonth();
			}
		}

		if (em.getIdState() != null) {
			if (!em.getIdState().equals("")) {
				sql = sql + " and emhc_id in(" + em.getIdState() + ")";
			}
		}

		if (em.isSameInfo()) {
			EmHouseSetBll bll = new EmHouseSetBll();
			sql = sql + " and ((gid in (" + em.getGid()
					+ ") or emhc_idcard in ('" + em.getEmhc_idcard()
					+ "') or emhc_houseid in ('" + em.getEmhc_houseid()
					+ "')) )";
		} else {

			if (em.getGid() != null) {
				if (!em.getGid().equals("")) {
					sql = sql + " and gid=" + em.getGid();
				}
			}
		}

		if (em.getEmhc_ifdeclare() != null) {
			if (!em.getEmhc_ifdeclare().equals("")) {
				sql = sql + " and emhc_ifdeclare =" + em.getEmhc_ifdeclare();
			}
		}

		if (em.getAlarm() != null) {
			if (em.getAlarm()) {
				sql = sql + " and tapr_id is not null and tapr_state!=5 and ("
						+ "	( tapr_appointid=-1 and tapr_appointcon in ("
						+ "select name from dbo.GetChildren("
						+ UserInfo.getUserid() + "))) " + " or "
						+ UserInfo.getDepID() + "=3) and a.ownmonth>=201508";
			}
		} else {

		}

		if (em.getDataState() != null) {
			if (em.getDataState().equals(1)) {
				sql = sql + " and emhc_ifdeclare in(0,4)";
			} else if (em.getDataState().equals(2)) {
				sql = sql + " and emhc_ifdeclare in(0,3,4,6)";
			} else if (em.getDataState().equals(3)) {
				sql = sql + " and emhc_ifdeclare in(0,2)";
			} else if (em.getDataState().equals(7)) {
				sql = sql + " and emhc_ifdeclare in(1,2)";
			}
		}

		if (em.getEmhc_tid() != null) {
			if (!em.getEmhc_tid().equals("")) {
				sql += " and a.emhc_tid=" + em.getEmhc_tid();
			}
		}

		if (order != null) {
			if (!order.equals("")) {
				sql = sql + " order by " + order;
			}
		}

		System.out.println(sql);
		try {
			list = db.find(sql, EmHouseChangeModel.class, null);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return list;
	}

	public List<EmHouseChangeModel> getList(EmHouseChangeModel em) {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select emhc_id,cid,gid,emhc_change,emhc_tid "
				+ "from emhousechange where 1=1";

		if (em.getEmhc_id() != null) {
			if (!em.getEmhc_id().equals("")) {
				sql = sql + " and emhc_id=" + em.getEmhc_id();
			}
		}

		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				sql = sql + " and ownmonth=" + em.getOwnmonth();
			}
		}

		if (em.getGid() != null) {
			if (!em.getGid().equals("")) {
				sql = sql + " and gid=" + em.getGid();
			}
		}

		if (em.getDataState() != null) {
			if (em.getDataState().equals(3)) {
				sql = sql + " and emhc_ifdeclare in (0,2)";
			}
		} else {
			if (em.getEmhc_ifdeclare() != null) {
				if (!em.getEmhc_ifdeclare().equals("")) {
					sql = sql + " and emhc_ifdeclare=" + em.getEmhc_ifdeclare();
				}
			}
		}
		try {
			list = db.find(sql, EmHouseChangeModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 添加新增数据
	public Integer addEmhouse(Object... obj) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer
				.valueOf(db
						.callWithReturn(
								"{?=call EmHouse_New_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
								Types.INTEGER, obj).toString());

		return i;
	}

	// 添加调入/调回数据
	public Integer moveinEmhouse(Object... obj) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer
				.valueOf(db
						.callWithReturn(
								"{?=call EmHouse_Movein_P_py(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
								Types.INTEGER, obj).toString());

		return i;
	}

	// 添加停交数据
	public Integer stopEmhouse(Object... obj) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmHouse_Stop_P_py(?,?,?,?,?,?)}", Types.INTEGER,
					obj).toString());
		} catch (NumberFormatException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		Log4jInit.toLog("公积金|停交结果:" + i);
		return i;
	}

	// 申报数据
	public Integer declareData(Object... obj) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer.valueOf(db.callWithReturn(
				"{?=call EmHouse_declareSingle_P_py(?,?,?)}", Types.INTEGER,
				obj).toString());
		return i;
	}

	// 生成清册
	public Integer updateExcelFile(Object... obj) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer.valueOf(db.callWithReturn(
				"{?=call EmHouse_declareExcel_P_py(?,?,?,?,?)}", Types.INTEGER,
				obj).toString());
		return i;
	}

	// 调入转新增
	public Integer changeNew(Object... obj) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmHouseChangeToNew_P_py(?,?,?)}", Types.INTEGER,
					obj).toString());
		} catch (NumberFormatException | SQLException e) {

			e.printStackTrace();
		}
		return i;

	}

	// 新增转调入
	public Integer changeMove(Object... obj) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmHouseChangeToMove_P_py(?,?,?,?)}",
					Types.INTEGER, obj).toString());
		} catch (NumberFormatException | SQLException e) {

			e.printStackTrace();
		}
		return i;

	}

	/**
	 * @Title: changeinfo
	 * @Description: TODO(比例基数调整)
	 * @param obj
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer changeinfo(Object... obj) {
		Integer i = 0;
		dbconn db = new dbconn();
		try {
			i = Integer.valueOf(db.callWithReturn(
					"{?=call EmHouse_RadixCPP_P_py(?,?,?,?,?,?,?)}",
					Types.INTEGER, obj).toString());
		} catch (NumberFormatException | SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	// 更新任务流程ID
	public Integer updateTaprId(Integer dataId, Integer taprId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChange set emhc_tapr_id=? where emhc_id=?";
		try {
			i = db.updatePrepareSQL(sql, taprId, dataId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	// 确认数据
	public Integer updateConfirmDate(Integer dataId, Integer ownmonth) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChange set emhc_ConfirmTime=getdate(),emhc_ifdeclare=0,ownmonth=? where emhc_id=?";
		try {
			i = db.updatePrepareSQL(sql, ownmonth, dataId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	// 修改状态
	public Integer updateState(Integer id, Integer state, String name) {
		Integer i = 0;
		dbconn db = new dbconn();
		if (id != null && id > 0) {

			String sql = "update emhousechange set emhc_modtime=getdate(),emhc_modname=?,emhc_ifdeclare=?"
					+ " where emhc_id=?";
			try {
				i = db.updatePrepareSQL(sql, name, state, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	// 变更为未确认状态
	public Integer returnConfirmDate(Integer dataId, Integer ownmonth) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChange set emhc_ConfirmTime=null,emhc_ifdeclare=4,ownmonth=? where emhc_id=?";
		try {
			i = db.updatePrepareSQL(sql, ownmonth, dataId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return i;
	}

	public Integer mod(EmHouseChangeModel em, Integer id, Integer cid,
			Integer gid, Integer bstate) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChange set emhc_modtime=getdate()";
		if (em.getOwnmonth() != null) {
			sql = sql + ",ownmonth='" + em.getOwnmonth() + "'";
		}

		if (em.getEmhc_houseid() != null) {
			sql = sql + ",emhc_houseid='" + em.getEmhc_houseid() + "'";
		}
		if (em.getEmhc_computerid() != null) {
			sql = sql + ",emhc_computerid='" + em.getEmhc_computerid() + "'";
		}
		if (em.getEmhc_idcard() != null) {
			sql = sql + ",emhc_idcard='" + em.getEmhc_idcard() + "'";
		}
		if (em.getEmhc_hj() != null) {
			sql = sql + ",emhc_hj='" + em.getEmhc_hj() + "'";
		}

		if (em.getEmhc_change() != null) {
			sql = sql + ",emhc_change='" + em.getEmhc_change() + "'";
		}

		if (em.getEmhc_radix() != null) {
			if (em.getEmhc_radix() > 0) {
				sql = sql + ",emhc_radix=" + em.getEmhc_radix();
			}
		}

		if (em.getEmhc_cpp() != null) {
			if (em.getEmhc_cpp() > 0) {
				sql = sql + ",emhc_cpp=" + em.getEmhc_cpp();
			}
		}

		if (em.getEmhc_opp() != null) {
			if (em.getEmhc_opp() > 0) {
				sql = sql + ",emhc_opp=" + em.getEmhc_opp();
			}
		}
		if (em.getEmhc_mobile() != null) {
			sql = sql + ",emhc_mobile='" + em.getEmhc_mobile() + "'";
		}

		if (em.getEmhc_title() != null) {
			sql = sql + ",emhc_title='" + em.getEmhc_title() + "'";
		}
		if (em.getEmhc_degree() != null) {
			sql = sql + ",emhc_degree='" + em.getEmhc_degree() + "'";
		}
		if (em.getEmhc_wifename() != null) {
			sql = sql + ",emhc_wifename='" + em.getEmhc_wifename() + "'";
		}
		if (em.getEmhc_wifeidcard() != null) {
			sql = sql + ",emhc_wifeidcard='" + em.getEmhc_wifeidcard() + "'";
		}
		if (em.getEmhc_ifdeclare() != null) {
			sql = sql + ",emhc_ifdeclare=" + em.getEmhc_ifdeclare();
		}
		if (em.getEmhc_ifprogress() != null) {
			sql = sql + ",emhc_ifprogress=" + em.getEmhc_ifprogress();
		}
		if (em.getEmhc_iffifteen() != null) {
			sql = sql + ",emhc_iffifteen=" + em.getEmhc_iffifteen();
		}
		if (em.getEmhc_confirmtime() != null) {
			sql = sql + ",emhc_confirmtime='" + em.getEmhc_confirmtime() + "'";
		}
		if (em.getEmhc_addname() != null) {
			sql = sql + ",emhc_addname='" + em.getEmhc_addname() + "'";
		}

		sql = sql + " where 1=1";

		if (id != null) {
			sql = sql + " and emhc_id=" + id;
		}
		if (cid != null) {
			sql = sql + " and cid=" + cid;
		}
		if (gid != null) {
			sql = sql + " and gid=" + gid;
		}

		if (em.getOwnmonth() != null) {
			if (!em.getOwnmonth().equals("")) {
				if (bstate != null) {
					if (bstate.equals(1)) {
						sql = sql + " and ownmonth>" + em.getOwnmonth();
					} else {
						sql = sql + " and ownmonth=" + em.getOwnmonth();
					}
				} else {
					if (id != null) {

					} else {
						sql = sql + " and ownmonth=" + em.getOwnmonth();
					}

				}

			}
		}

		System.out.println(sql);
		try {
			i = db.updatePrepareSQL(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		if (i > 0) {
			i = id;
		}

		return i;
	}

	// 更新特殊调基数据
	public Integer updateRadixData(Integer id, Integer tid) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update emhousechange set emhc_tid=0" + " where 1=1";
		if (tid != null && tid > 0) {
			sql += " and emhc_tid=?";
			try {
				i = db.updatePrepareSQL(sql, tid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (id != null && id > 0) {
			sql += " and emhc_id=? and emhc_tid>0";
			try {
				i = db.updatePrepareSQL(sql, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;

	}

	// 审核数据
	public Integer auditData(Integer dataId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmHouseChange set emhc_ifdeclare=0 where emhc_id=?";
		try {
			i = db.updatePrepareSQL(sql, dataId);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		if (i > 0) {
			return dataId;
		} else {
			return 0;
		}

	}

	// 删除数据
	public Integer del(Integer id) {
		Integer i = 0;
		if (id != null && id > 0) {
			dbconn db = new dbconn();
			String sql = "delete from emhousechange where emhc_id=?";
			try {
				i = db.updatePrepareSQL(sql, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return i;
	}

	// 获取节点ID
	public Integer getTaprId(Integer id) {
		Integer i = 0;
		List<EmHouseChangeModel> list = new ListModelList<EmHouseChangeModel>();
		dbconn db = new dbconn();
		String sql = "select emhc_tapr_id from EmHouseChange where emhc_id=?";
		try {
			list = db.find(sql, EmHouseChangeModel.class,
					dbconn.parseSmap(EmHouseChangeModel.class), id);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		i = list.get(0).getEmhc_tapr_id();

		return i;
	}

	// 核查/汇缴失败更新当月新增/调入数据状态
	public Integer updateData(Integer gid, Integer ownmonth, Integer state) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update emhousechange set emhc_state=?"
				+ " where gid=? and ownmonth=?"
				+ " and emhc_change in('新增','调入')  and emhc_ifdeclare=1";
		try {
			i = db.updatePrepareSQL(sql, state, gid, ownmonth);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

}
