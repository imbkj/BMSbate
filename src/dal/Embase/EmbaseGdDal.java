package dal.Embase;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmbaseGDModel;

public class EmbaseGdDal {

	/**
	 * @Title: getList
	 * @Description: TODO 雇员服务中心表类型 typeId:0:体检
	 * @param m
	 * @param distinctName
	 * @param order
	 * @return
	 * @return List<EmbaseGDModel> 返回类型
	 * @throws
	 */
	public List<EmbaseGDModel> getList(EmbaseGDModel m, String distinctName,
			String order) {
		dbconn db = new dbconn();
		List<EmbaseGDModel> list = new ListModelList<>();
		String dn = "";
		String sql = "select ";
		if (distinctName != null && !distinctName.equals("")) {
			switch (distinctName) {
			case "client":
				dn = "coba_client";
				sql += "distinct " + dn + " " + distinctName;
				break;
			case "type":
				dn = "typeId," + distinctName;
				sql += "distinct typeId," + distinctName;
				break;
			default:
				dn = distinctName;
				sql += "distinct " + distinctName;
				break;
			}

		} else {
			sql += "ROW_NUMBER()over(order by declareState,ownmonth desc,addtime desc) id,a.*,"
					+ "emgd_id,emgd_link linktype,emgd_declare declaretype,emgd_datatype datatype,"
					+ "emgd_remark remark,"
					+ "coba_shortname shortname,coba_client client,emba_mobile,cosp_sbhs_caliname,coba_assistant assistant,"
					+ "case declareState when 1 then '退回' when 2 then '未确认 ' when 3 then '未申报' when 4 then '申报中'"
					+ " when 5 then '已申报' when 6 then '已上传' when 7 then '待核收' end declareName,case emba_state when 1 then '在职' when 0 then '离职' else '入职中' END emba_statestr,convert(varchar(10),ehcg_single) as single,convert(int,startMonth) as startMonth,bjc.bj_cou,ifjlbj";
		}
		sql += " from ("
				+ "select 1 typeId,ehcg_id dataId,cid,gid,ownmonth,ehcg_name name,"
				+ "'信息变更(公积金)' type,ehcg_addtime addtime,ehcg_tapr_id taprId,"
				+ "case ehcg_ifdeclare when 3 then 1 when 4 then 2 when 0 then 3 when 2 then 4 when 1 then 5 end declareState,ehcg_single,'' as startMonth,0 as ifjlbj"
				+ " from EmHouseChangeGJJ"
				+ " union all"
				+ " select 2,escs_id dataId,CID,GID,Ownmonth,escs_Name,"
				+ "escs_Change+'(社保)',escs_Addtime,escs_tapr_id taprId,"
				+ "case escs_IfDeclare when 3 then 1 when 9 then 1 when 0 then 3 when 2 then 6 when 4 then 2 when 7 then 7 when 8 then 4 when 6 then 4 when 1 then 5 end,escs_single,'' as startMonth,0 as ifjlbj"
				+ " from EmShebaoChangeSZSI where (escs_ifnet!=1 or escs_ifnet is null)"
				+ " union all"
				+ " select 3,id dataId,CID,GID,Ownmonth,emsc_Name,"
				+ "'外籍人'+emsc_Change+'(社保)',emsc_Addtime,emsc_tapr_id taprId,"
				+ "case emsc_IfDeclare when 3 then 1 when 9 then 1 when 4 then 2 when 7 then 2 when 0 then 3 when 2 then 4 when 1 then 5 end,emsc_single,'' as startMonth,0 as ifjlbj "
				+ " from EmShebaoChangeForeigner"
				+ " union all"
				+ " select 4,a.id dataId,a.CID,a.GID,a.Ownmonth,emsb_Name,"
				+ "'补缴养老(社保)',emsb_Addtime,emsb_tapr_id taprId,"
				+ "case emsb_IfDeclare when 3 then 1 when 9 then 1 when 0 then 3 when 2 then 6 when 4 then 2 when 7 then 7 when 8 then 4 when 6 then 4 when 1 then 5 end,emsb_single,emsb_startmonth,isnull(b.id,0) as ifjlbj"
				+ " from EmShebaoBJ a left join EmShebaoBJJL b on a.GID=b.GID and a.Ownmonth=b.Ownmonth and a.emsb_startmonth=b.esbj_startmonth"
				+ " where (emsb_IfDeclare in(4,2,7) or (emsb_single=1 and a.cid not in(select cid from CoShebao where cosb_state!=0 and (cosb_ukey='有' or cosb_ukey='是'))) "
				+ " or (dbo.DiffOwnmonth(a.Ownmonth,emsb_startmonth)>3)) and (emsb_tapr_id<>0 or emsb_tapr_id is null)  or (b.ID is not null and (b.esbj_tapr_id<>0 or b.esbj_tapr_id is null))"

				+ " union all"
				+ " select 4,id dataId,CID,GID,Ownmonth,esbj_Name,"
				+ "'补缴医疗(社保)',esbj_Addtime,esbj_tapr_id taprId,"
				+ "case esbj_IfDeclare when 3 then 1 when 9 then 1 when 0 then 3 when 2 then 6 when 4 then 2 when 7 then 7 when 8 then 4 when 6 then 4 when 1 then 5 end,esbj_single,esbj_startmonth,0 as ifjlbj"
				+ " from EmShebaoBJjl where esbj_tapr_id<>0 or esbj_tapr_id is null"

				+ " union all"
				+ " select 7,emhb_id dataid,cid,gid,ownmonth,emhb_name,'公积金补缴确认',emhb_addtime,emhb_tapr_id taprId,case emhb_ifdeclare when 3 then 1 when 4 then 2 when 0 then 3 when 2 then 4 when 1 then 5 end,emhb_single,'' as startMonth,0 as ifjlbj"
				+ " from emhousebj"
				+ " where ownmonth>=201508 and (emhb_tapr_id is null or emhb_tapr_id!=0) and emhb_single=1"

				+ ")a"

				+ " left join cobase b on a.cid=b.cid"
				+ " left join embase c on a.cid=c.cid and a.gid=c.gid"
				+ " left join CoBaseServePromise csp on a.CID=csp.cid "
				+ " left join embasegd d on a.typeId=d.emgd_type and a.dataId=d.emgd_dataId and emgd_state=1"
				+ " left join (select ownmonth as bjownmonth,gid,COUNT(*)bj_cou from (select Ownmonth,GID from EmShebaoBJ union all select Ownmonth,GID from EmShebaoBJJL)a group by Ownmonth,gid)bjc on a.ownmonth=bjc.bjownmonth and a.gid=bjc.gid and a.typeId=4"
				+ " where 1=1";
		if (m != null) {
			if (m.getCompany() != null) {
				if (!m.getCompany().equals("")) {
					sql += " and (a.cid like '" + m.getCompany()
							+ "' or coba_company like '%" + m.getCompany()
							+ "%' or coba_shortname like '%" + m.getCompany()
							+ "%')";
				}
			}
			if (m.getName() != null) {
				if (!m.getName().equals("")) {
					sql += " and (a.gid like '" + m.getName()
							+ "' or name like '%" + m.getName()
							+ "%' or emba_idcard like '%" + m.getName() + "%')";
				}
			}

			if (m.getDeclareState() != null) {
				if (!m.getDeclareState().equals("")) {
					sql += " and declareState=" + m.getDeclareState();
				}
			}
			if (m.getOwnmonth() != null) {
				if (!m.getOwnmonth().equals("")) {
					sql += " and ownmonth=" + m.getOwnmonth();
				}

			}
			if (m.getClient() != null) {
				if (!m.getClient().equals("")) {
					sql += " and coba_client='" + m.getClient() + "'";
				}

			}
			if (m.getAssistant() != null) {
				if (!m.getAssistant().equals("")) {
					sql += " and coba_assistant='" + m.getAssistant() + "'";
				}

			}
			if (m.getSingle() != null) {
				if ("独立开户".equals(m.getSingle())) {
					sql += " and ehcg_single='1'";
				} else if ("中智开户".equals(m.getSingle())) {
					sql += " and ehcg_single!='1'";
				}

			}

			if (m.getType() != null) {
				if (!m.getType().equals("")) {
					if (m.getType().equals("补缴养老(社保)")) {
						sql += " and ifjlbj=0 and type='补缴养老(社保)'";
					} else if (m.getType().equals("补缴养老和医疗(社保)")) {
						sql += " and (ifjlbj>0 or type='补缴医疗(社保)')";
					} else {
						sql += " and type like'%" + m.getType() + "%'";
					}
				}

			}

			if (m.getDeclaretype() != null) {
				if (!m.getDeclaretype().equals("")) {
					sql += " and emgd_declare like '%" + m.getDeclaretype()
							+ "%'";
				}

			}
			if (m.getLinktype() != null) {
				if (!m.getLinktype().equals("")) {
					sql += " and cosp_sbhs_caliname like '%" + m.getLinktype()
							+ "%'";
				}

			}
			if (m.getDatatype() != null) {
				if (!m.getDatatype().equals("")) {
					sql += " and emgd_datatype like '%" + m.getDatatype()
							+ "%'";
				}

			}

		}
		if (distinctName != null && !distinctName.equals("")) {
			sql += " order by " + dn + " " + order;
		} else {
			sql += " order by declareState,ownmonth desc,addtime desc";
		}

		System.out.println(sql);

		try {
			list = db.find(sql, EmbaseGDModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 非交单
	 * 
	 * @Title: getList
	 * @Description: TODO 雇员服务中心表类型 typeId:0:体检
	 * @param m
	 * @param distinctName
	 * @param order
	 * @return
	 * @return List<EmbaseGDModel> 返回类型
	 * @throws
	 */
	public List<EmbaseGDModel> getListonjd(EmbaseGDModel m,
			String distinctName, String order) {
		dbconn db = new dbconn();
		List<EmbaseGDModel> list = new ListModelList<>();
		String dn = "";
		String sql = "select ";
		if (distinctName != null && !distinctName.equals("")) {
			switch (distinctName) {
			case "client":
				dn = "coba_client";
				sql += "distinct " + dn + " " + distinctName;
				break;
			case "type":
				dn = "typeId," + distinctName;
				sql += "distinct typeId," + distinctName;
				break;

			case "addname":
				dn = "addname ";
				sql += "distinct addname " + distinctName;
				break;

			default:
				dn = distinctName;
				sql += "distinct " + distinctName;
				break;
			}

		} else {
			sql += "ROW_NUMBER()over(order by declareState,ownmonth desc,addtime desc) id,a.*,"
					+ "emgd_id,emgd_link linktype,emgd_declare declaretype,emgd_datatype datatype,"
					+ "emgd_remark remark,"
					+ "coba_shortname shortname,coba_client client,emba_mobile,addname,cosp_sbhs_caliname,d.emgd_addname,coba_assistant assistant,"
					+ "hsingle,tsday,case emba_state when 1 then '在职' when 0 then '离职' else '入职中' END emba_statestr,ifjlbj";
		}
		sql += " from ("
				+ " select 4 typeId,id dataId,cid,gid,ownmonth,emsb_Name+'('+cast(emsb_startmonth as varchar(10)) +')'  name,"
				+ "'养老补缴退回' type,emsb_Addtime addtime,emsb_tapr_id taprId,"
				+ " 3 declareState ,emsb_addname addname,null hsingle,null tsday,0 as ifjlbj"
				+ " from EmShebaoBJ where (emsb_IfDeclare=3 or emsb_IfDeclare=9 ) and ownmonth>=201507 "

				+ " union all"
				+ " select 4 typeId,id dataId,cid,gid,ownmonth,esbj_Name+'('+cast(esbj_startmonth as varchar(10)) +')'  name,"
				+ "'医疗补缴退回' type,esbj_Addtime addtime,esbj_tapr_id taprId,"
				+ " 3 declareState ,esbj_addname addname,null hsingle,null tsday,0 as ifjlbj"
				+ " from EmShebaoBJJL where (esbj_IfDeclare=3 or esbj_IfDeclare=9 ) "

				+ " union all"
				+ " select 5,ID dataid,CID,GID,Ownmonth,emsc_Name,'社保退回',emsc_Addtime,emsc_tapr_id taprId,3 ,emsc_addname ,null,null,0 as ifjlbj "
				+ " from EmSheBaoChange where (emsc_IfDeclare=3 or emsc_IfDeclare=9) and  "
				+ "(emsc_change='新增' or emsc_change='调入') and  Ownmonth >=201508 "

				+ " union all"
				+ "  select distinct 7,emhb_id dataid,z.CID,z.GID,z.Ownmonth,emhb_name,'公积金补缴退回',emhb_Addtime,emhb_tapr_id taprId ,3,emhb_addname,"
				+ " emhb_single,isnull(e.cohf_lastday,0),0 as ifjlbj"
				+ " from EmHouseBJ z"
				// +
				// " inner join coglist a on z.gid=a.gid and cgli_startdate2<=isnull(cgli_stopdate,204912)"
				+ " inner join coglist a on z.gid=a.gid and cgli_state=1"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and b.coli_name='住房公积金服务'"
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " inner join EmHouseSetup h on 1=1"
				+ " left join (select * from CoHousingFund where cohf_state=1)e on z.emhb_companyid=e.cohf_houseid and ((cohf_single=1 and z.cid=e.cid) or cohf_single=0) "
				+ " where emhb_ifdeclare=3  "
				+ "   and  z.Ownmonth >=201508 "

				+ " union all"
				+ " select distinct 6,emhc_id dataid,z.CID,z.GID,z.Ownmonth,emhc_name,'公积金退回',emhc_Addtime,emhc_tapr_id taprId ,3 ,emhc_addname,"
				+ " emhc_single,isnull(e.cohf_lastday,0),0 as ifjlbj"
				+ " from EmHouseChange z"
				// +
				// " inner join coglist a on z.gid=a.gid and cgli_startdate2<=isnull(cgli_stopdate,204912)"
				+ " inner join coglist a on z.gid=a.gid and cgli_state=1"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and b.coli_name='住房公积金服务'"
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " inner join EmHouseSetup h on 1=1"
				+ " left join (select * from CoHousingFund where cohf_state=1 )e on z.emhc_companyid=e.cohf_houseid and ((cohf_single=1 and z.cid=e.cid) or cohf_single=0) "
				+ " where emhc_IfDeclare=3 and  (emhc_change<>'停交' and  emhc_change<>'基数调整') and  z.Ownmonth >=201508 "

				// 社保新增
				+ " union all"
				+ " select 9,ID dataid,CID,GID,Ownmonth,emsc_Name,'社保新增',emsc_Addtime,emsc_tapr_id taprId,3 ,emsc_addname,null,null,0 as ifjlbj  "
				+ " from EmSheBaoChange where  emsc_IfDeclare=4 and  "
				+ "(emsc_change='新增' or emsc_change='调入') and  Ownmonth >=201508 "
				// 公积金新增
				+ " union all"
				+ " select 10,emhc_id dataid,z.CID,z.GID,z.Ownmonth,emhc_name,'公积金新增',emhc_Addtime,emhc_tapr_id taprId ,3 ,emhc_addname,"
				+ " emhc_single,isnull(e.cohf_lastday,0),0 as ifjlbj"
				+ " from EmHouseChange z"
				// +
				// " inner join coglist a on z.gid=a.gid and cgli_startdate2<=isnull(cgli_stopdate,204912)"
				+ " inner join coglist a on z.gid=a.gid and cgli_state=1"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and b.coli_name='住房公积金服务'"
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " inner join EmHouseSetup h on 1=1"
				+ " left join (select * from CoHousingFund where cohf_state=1)e on z.emhc_companyid=e.cohf_houseid and ((cohf_single=1 and z.cid=e.cid) or cohf_single=0) "
				+ " where emhc_IfDeclare=4 and  (emhc_change<>'停交' and  emhc_change<>'基数调整') and  z.Ownmonth >=201508  "
				// 社保补缴
				+ " union all"
				+ "  select 11,a.ID dataid,a.CID,a.GID,a.Ownmonth,emsb_Name+'('+cast(emsb_startmonth as varchar(10)) +')','养老补缴新增',emsb_Addtime,emsb_tapr_id taprId,3 ,emsb_addname,null,null,isnull(b.id,0) as ifjlbj"
				+ " from EmSheBaobj a left join EmShebaoBJJL b on a.GID=b.GID and a.Ownmonth=b.Ownmonth and a.emsb_startmonth=b.esbj_startmonth"
				+ " where emsb_IfDeclare=4 and  a.Ownmonth >=201508 and (emsb_tapr_id >0  or emsb_tapr_id is null) "

				// 社保补缴(医疗)
				+ " union all"
				+ "  select 11,ID dataid,CID,GID,Ownmonth,esbj_Name+'('+cast(esbj_startmonth as varchar(10)) +')','医疗补缴新增',esbj_Addtime,esbj_tapr_id taprId,3 ,esbj_addname,null,null,0 as ifjlbj"
				+ " from EmSheBaobjJl "
				+ " where esbj_IfDeclare=4 and (esbj_tapr_id >0  or esbj_tapr_id is null) "

				// 公积金补缴
				+ " union all"
				+ " select  7,emhb_id dataid,z.CID,z.GID,z.Ownmonth,emhb_name,'公积金补缴新增',emhb_Addtime,emhb_tapr_id taprId ,3,emhb_addname,"
				+ " emhb_single,isnull(e.cohf_lastday,0),0 as ifjlbj"
				+ " from EmHouseBJ z"
				// +
				// " inner join coglist a on z.gid=a.gid and cgli_startdate2<=isnull(cgli_stopdate,204912)"
				+ " inner join coglist a on z.gid=a.gid and cgli_state=1"
				+ " inner join CoOfferList b on a.cgli_coli_id=b.coli_id and b.coli_name='住房公积金服务'"
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id"
				+ " inner join CoCompact d on c.coof_coco_id=d.coco_id"
				+ " inner join EmHouseSetup h on 1=1"
				+ " left join (select * from CoHousingFund where cohf_state=1 )e on z.emhb_companyid=e.cohf_houseid and ((cohf_single=1 and z.cid=e.cid) or cohf_single=0) "
				+ " where emhb_ifdeclare=4 and (emhb_tapr_id >0 or emhb_tapr_id is null) "
				+ "  and   z.Ownmonth >=201508 "

				+ ")a"

				+ " left join cobase b on a.cid=b.cid"
				+ " left join embase c on a.cid=c.cid and a.gid=c.gid"
				+ " left join CoBaseServePromise csp on a.CID=csp.cid "
				+ " left join embasegd d on a.typeId=d.emgd_type and a.dataId=d.emgd_dataId and emgd_state=1"
				+ " where 1=1 ";
		if (m != null) {
			if (m.getCompany() != null) {
				if (!m.getCompany().equals("")) {
					sql += " and (a.cid like '" + m.getCompany()
							+ "' or coba_company like '%" + m.getCompany()
							+ "%' or coba_shortname like '%" + m.getCompany()
							+ "%')";
				}
			}
			if (m.getName() != null) {
				if (!m.getName().equals("")) {
					sql += " and (a.gid like '" + m.getName()
							+ "' or name like '%" + m.getName()
							+ "%' or emba_idcard like '%" + m.getName() + "%')";
				}
			}

			if (m.getDeclareState() != null) {
				if (!m.getDeclareState().equals("")) {
					sql += " and declareState=" + m.getDeclareState();
				}
			}
			if (m.getOwnmonth() != null) {
				if (!m.getOwnmonth().equals("")) {
					sql += " and ownmonth=" + m.getOwnmonth();
				}

			}
			if (m.getClient() != null) {
				if (!m.getClient().equals("")) {
					sql += " and coba_client='" + m.getClient() + "'";
				}

			}
			if (m.getAssistant() != null) {
				if (!m.getAssistant().equals("")) {
					sql += " and coba_assistant='" + m.getAssistant() + "'";
				}

			}

			if (m.getAddname() != null) {
				if (!m.getAddname().equals("")) {
					sql += " and addname='" + m.getAddname() + "'";
				}

			}

			if (m.getType() != null) {
				if (!m.getType().equals("")) {
					if (m.getType().equals("养老补缴新增")) {
						sql += " and ifjlbj=0 and type='养老补缴新增'";
					} else if (m.getType().equals("养老和医疗补缴新增")) {
						sql += " and (ifjlbj>0 or type='医疗补缴新增')";
					} else {
						sql += " and type like'%" + m.getType() + "%'";
					}
				}

			}

			if (m.getTypeId() != null) {
				if (!m.getTypeId().equals("")) {
					sql += " and typeId like'%" + m.getTypeId() + "%'";
				}

			}
			if (m.getDataId() != null) {
				if (!m.getDataId().equals("")) {
					sql += " and dataId like'%" + m.getType() + "%'";
				}

			}

			if (m.getDeclaretype() != null) {
				if (!m.getDeclaretype().equals("")) {
					sql += " and emgd_declare like '%" + m.getDeclaretype()
							+ "%'";
				}

			}
			if (m.getLinktype() != null) {
				if (!m.getLinktype().equals("")) {
					sql += " and cosp_sbhs_caliname like '%" + m.getLinktype()
							+ "%'";
				}

			}
			if (m.getDatatype() != null) {
				if (!m.getDatatype().equals("")) {
					sql += " and emgd_datatype like '%" + m.getDatatype()
							+ "%'";
				}

			}

			if (m.getCosp_sbhs_caliname() != null) {
				if (!m.getCosp_sbhs_caliname().equals("")) {
					sql += " and cosp_sbhs_caliname like '%"
							+ m.getCosp_sbhs_caliname() + "%'";
				}

			}

			if (m.getSingle() != null) {
				if (!m.getSingle().equals("")) {
					if (m.getSingle().equals("独立开户")) {
						sql += " and hsingle=1";
					} else if (m.getSingle().equals("中智开户")) {
						sql += " and hsingle=0";
					}

				}
			}
			if (m.getTsday() != null) {
				if (!m.getTsday().equals("")) {
					sql += " and tsday=" + m.getTsday();
				}
			}
		}
		if (distinctName != null && !distinctName.equals("")) {
			sql += " order by " + dn + " " + order;
		} else {
			sql += " order by declareState,ownmonth desc,addtime desc";
		}

		System.out.println(sql);

		try {
			list = db.find(sql, EmbaseGDModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 客服社保公积金退回列表
	 * 
	 * @Title: getList
	 * @Description: TODO 雇员服务中心表类型 typeId:0:体检
	 * @param m
	 * @param distinctName
	 * @param order
	 * @return
	 * @return List<EmbaseGDModel> 返回类型
	 * @throws
	 */
	public List<EmbaseGDModel> getListsbgjjkfback(EmbaseGDModel m,
			String distinctName, String order) {
		dbconn db = new dbconn();
		List<EmbaseGDModel> list = new ListModelList<>();
		String dn = "";
		String sql = "select ";
		if (distinctName != null && !distinctName.equals("")) {
			switch (distinctName) {
			case "client":
				dn = "coba_client";
				sql += "distinct " + dn + " " + distinctName;
				break;
			case "type":
				dn = "typeId," + distinctName;
				sql += "distinct typeId," + distinctName;
				break;

			case "addname":
				dn = "addname ";
				sql += "distinct addname " + distinctName;
				break;

			default:
				dn = distinctName;
				sql += "distinct " + distinctName;
				break;
			}

		} else {
			sql += "ROW_NUMBER()over(order by declareState,ownmonth desc,addtime desc) id,a.*,"
					+ "emgd_id,emgd_link linktype,emgd_declare declaretype,emgd_datatype datatype,"
					+ "emgd_remark remark,"
					+ "coba_shortname shortname,coba_client client,emba_mobile,addname,cosp_sbhs_caliname,d.emgd_addname";
		}
		sql += " from ("
				+ " select 4 typeId,id dataId,cid,gid,ownmonth,emsb_Name+'('+cast(emsb_startmonth as varchar(10)) +')'  name,'养老补缴退回' type, "
				+ " emsb_Addtime addtime,emsb_tapr_id taprId, 3 declareState ,emsb_addname addname from EmShebaoBJ "
				+ " where (emsb_IfDeclare=3 or emsb_IfDeclare=9 ) and ownmonth>=201507 "

				+ " union all"
				+ " select 4 typeId,id dataId,cid,gid,ownmonth,esbj_Name+'('+cast(esbj_startmonth as varchar(10)) +')'  name,'医疗补缴退回' type, "
				+ " esbj_Addtime addtime,esbj_tapr_id taprId, 3 declareState ,esbj_addname addname from EmShebaoBJjl "
				+ " where (esbj_IfDeclare=3 or esbj_IfDeclare=9 ) and ownmonth>=201507 "

				+ " union all"
				+ " select 5,ID dataid,CID,GID,Ownmonth,emsc_Name+'('+emsc_Change +')','社保退回',emsc_Addtime,emsc_tapr_id taprId,3 ,emsc_addname  "
				+ " from EmSheBaoChange where (emsc_IfDeclare=3 or emsc_IfDeclare=9) "
				+ " and  Ownmonth >=201508 "

				+ " union all"
				+ " select 6,ID dataid,CID,GID,Ownmonth,emsc_Name,'外籍人退回',emsc_Addtime,emsc_tapr_id taprId,3 ,emsc_addname "
				+ "  from EmShebaoChangeForeigner where (emsc_IfDeclare=3 or emsc_IfDeclare=9) "
				+ "  and  Ownmonth >=201508 "
				+ " union all"
				+ " select 7,escs_ID dataid,CID,GID,Ownmonth,escs_Name,'社保交单退回',escs_Addtime,escs_tapr_id taprId,3 ,escs_addname "
				+ " from EmShebaoChangeSZSI where (escs_IfDeclare=3 or escs_IfDeclare=9) "
				+ " and  Ownmonth >=201508 "

				// 社保新增
				+ " union all"
				+ " select  8,emhb_id dataid,CID,GID,Ownmonth,emhb_name,'公积金补缴退回',"
				+ " emhb_Addtime,emhb_tapr_id taprId ,3,emhb_addname from EmHouseBJ where emhb_ifdeclare=3   and  Ownmonth >=201508 "

				// 公积金新增
				+ " union all"
				+ " select 9,emhc_id dataid,CID,GID,Ownmonth,emhc_name+'('+emhc_Change +')','公积金退回',emhc_Addtime,emhc_tapr_id taprId ,3 ,emhc_addname "
				+ "  from EmHouseChange   where emhc_IfDeclare=3  and  Ownmonth >=201508 "

				// 社保补缴
				+ " union all"
				+ " select 10,ehcg_id dataid,CID,GID,Ownmonth,ehcg_name,'公积金交单退回',ehcg_Addtime,ehcg_tapr_id taprId ,3 ,ehcg_addname "
				+ "  from EmHouseChangeGJJ   where ehcg_IfDeclare=3   and  Ownmonth >=201508 "
				+ ") "

				+ " a left join cobase b on a.cid=b.cid "
				+ " left join embase c on a.cid=c.cid and a.gid=c.gid "
				+ " left join CoBaseServePromise csp on a.CID=csp.cid "
				+ " left join embasegd d on a.typeId=d.emgd_type and a.dataId=d.emgd_dataId and emgd_state=1 "
				+ " left join TaskProcess tp on tp.tapr_id=a.taprId "
				+ " left join TaskList tl on tp.tapr_tali_id=tl.tali_id "
				+ " where "
				+ "  1=1  and declareState=3 and tp.tapr_appointcon='"
				+ Util.UserInfo.getUsername() + "' and tl.tali_state<>3 ";

		if (m != null) {
			if (m.getCompany() != null) {
				if (!m.getCompany().equals("")) {
					sql += " and (a.cid like '" + m.getCompany()
							+ "' or coba_company like '%" + m.getCompany()
							+ "%' or coba_shortname like '%" + m.getCompany()
							+ "%')";
				}
			}
			if (m.getName() != null) {
				if (!m.getName().equals("")) {
					sql += " and (a.gid like '" + m.getName()
							+ "' or name like '%" + m.getName()
							+ "%' or emba_idcard like '%" + m.getName() + "%')";
				}
			}

			if (m.getDeclareState() != null) {
				if (!m.getDeclareState().equals("")) {
					sql += " and declareState=" + m.getDeclareState();
				}
			}
			if (m.getOwnmonth() != null) {
				if (!m.getOwnmonth().equals("")) {
					sql += " and ownmonth=" + m.getOwnmonth();
				}

			}
			if (m.getClient() != null) {
				if (!m.getClient().equals("")) {
					sql += " and coba_client='" + m.getClient() + "'";
				}

			}

			if (m.getAddname() != null) {
				if (!m.getAddname().equals("")) {
					sql += " and addname='" + m.getAddname() + "'";
				}

			}

			if (m.getType() != null) {
				if (!m.getType().equals("")) {
					sql += " and type like'%" + m.getType() + "%'";
				}

			}

			if (m.getDeclaretype() != null) {
				if (!m.getDeclaretype().equals("")) {
					sql += " and emgd_declare like '%" + m.getDeclaretype()
							+ "%'";
				}

			}
			if (m.getLinktype() != null) {
				if (!m.getLinktype().equals("")) {
					sql += " and cosp_sbhs_caliname like '%" + m.getLinktype()
							+ "%'";
				}

			}
			if (m.getDatatype() != null) {
				if (!m.getDatatype().equals("")) {
					sql += " and emgd_datatype like '%" + m.getDatatype()
							+ "%'";
				}

			}

			if (m.getCosp_sbhs_caliname() != null) {
				if (!m.getCosp_sbhs_caliname().equals("")) {
					sql += " and cosp_sbhs_caliname like '%"
							+ m.getCosp_sbhs_caliname() + "%'";
				}

			}

		}
		if (distinctName != null && !distinctName.equals("")) {
			sql += " order by " + dn + " " + order;
		} else {
			sql += " order by declareState,ownmonth desc,addtime desc";
		}

		System.out.println(sql);

		try {
			list = db.find(sql, EmbaseGDModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 非交单
	 * 
	 * @Title: getList
	 * @Description: TODO 雇员服务中心表类型 typeId:0:体检
	 * @param m
	 * @param distinctName
	 * @param order
	 * @return
	 * @return List<EmbaseGDModel> 返回类型
	 * @throws
	 */
	public List<EmbaseGDModel> getListldht(EmbaseGDModel m,
			String distinctName, String order) {
		dbconn db = new dbconn();
		List<EmbaseGDModel> list = new ListModelList<>();
		String dn = "";
		String sql = "select ";
		if (distinctName != null && !distinctName.equals("")) {
			switch (distinctName) {
			case "client":
				dn = "coba_client";
				sql += "distinct " + dn + " " + distinctName;
				break;
			case "type":
				dn = "typeId," + distinctName;
				sql += "distinct typeId," + distinctName;
				break;

			case "addname":
				dn = "addname ";
				sql += "distinct a.addname " + distinctName;
				break;

			default:
				dn = distinctName;
				sql += "distinct " + distinctName;
				break;
			}

		} else {
			sql += "ROW_NUMBER()over(order by declareState,ownmonth desc,a.addtime desc) id,a.*,"
					+ "emgd_id,emgd_link linktype,emgd_declare declaretype,emgd_datatype datatype,"
					+ "emgd_remark remark,"
					+ "coba_shortname shortname,coba_client client,coba_assistant assistant,emba_mobile,a.addname addname,emgd_contactstate contactstate,emgd_clstate clstate,cosp_coco_signname cosp_sbhs_caliname,ebcc_incept_date addtime2";
		}
		sql += " from ("
				// 劳动合同打印
				+ "  select 8 typeId,ebcc_id dataId,a.cid,a.gid,null ownmonth,b.emba_name+'('+ebcc_change +')' name,"
				+ "'未处理' type,ebcc_auditing_time addtime,ebcc_tapr_Id taprId,3 declareState,ebcc_addname addname,ebcc_incept_date "
				+ "from EmBaseCompactChange a  "
				+ "  left join EmBase b on a.gid=b.gid   where ebcc_state=2 and (ebcc_change='续签' or ebcc_change='新签')   "

				// 劳动合同待盖章
				+ " union all"
				+ "  select 80,ebcc_id dataId,a.cid,a.gid,null,b.emba_name+'('+ebcc_change +')','已打印',ebcc_auditing_time addtime,ebcc_tapr_Id ,3 ,ebcc_addname,ebcc_incept_date from EmBaseCompactChange a  "
				+ "  left join EmBase b on a.gid=b.gid   where ebcc_state=3 and (ebcc_change='续签' or ebcc_change='新签')   "

				// 劳动合同待签订
				+ " union all"
				+ "  select 81,ebcc_id dataId,a.cid,a.gid,null,b.emba_name+'('+ebcc_change +')','待签订',ebcc_auditing_time addtime,ebcc_tapr_Id ,3 ,ebcc_addname,ebcc_incept_date from EmBaseCompactChange a  "
				+ "  left join EmBase b on a.gid=b.gid   where ebcc_state=4 and (ebcc_change='续签' or ebcc_change='新签')   "

				// 劳动合同签回
				+ " union all"
				+ "  select 82,ebcc_id dataId,a.cid,a.gid,null,b.emba_name+'('+ebcc_change +')','待签回',ebcc_auditing_time addtime,ebcc_tapr_Id ,3 ,ebcc_addname,ebcc_incept_date from EmBaseCompactChange a  "
				+ "  left join EmBase b on a.gid=b.gid   where ebcc_state=5 and (ebcc_change='续签' or ebcc_change='新签')   "

				// 劳动合同归档
				+ " union all"
				+ "  select 83,ebcc_id dataId,a.cid,a.gid,null,b.emba_name+'('+ebcc_change +')','待归档',ebcc_auditing_time addtime,ebcc_tapr_Id ,3 ,ebcc_addname,ebcc_incept_date from EmBaseCompactChange a  "
				+ "  left join EmBase b on a.gid=b.gid   where ebcc_state=6 and (ebcc_change='续签' or ebcc_change='新签')   "

				// 劳动合同归档
				+ " union all"
				+ "  select 84,ebcc_id dataId,a.cid,a.gid,null,b.emba_name+'('+ebcc_change +')','已归档',ebcc_auditing_time addtime,ebcc_tapr_Id ,3 ,ebcc_addname,ebcc_incept_date from EmBaseCompactChange a  "
				+ "  left join EmBase b on a.gid=b.gid   where ebcc_state=7 and (ebcc_change='续签' or ebcc_change='新签')   "

				// 劳动合同归档
				+ " union all"
				+ "  select 85,ebcc_id dataId,a.cid,a.gid,null,b.emba_name+'('+ebcc_change +')','退回',ebcc_auditing_time addtime,ebcc_tapr_Id ,3 ,ebcc_addname,ebcc_incept_date from EmBaseCompactChange a  "
				+ "  left join EmBase b on a.gid=b.gid   where ebcc_state=10 and (ebcc_change='续签' or ebcc_change='新签')   "

				+ ")a"

				+ " left join cobase b on a.cid=b.cid"
				+ " left join embase c on a.cid=c.cid and a.gid=c.gid"
				+ " left join CoBaseServePromise csp on a.CID=csp.cid "
				+ " left join embasegd d on a.typeId=d.emgd_type and a.dataId=d.emgd_dataId and emgd_state=1"
				+ " left join login e on e.log_name=b.coba_client"
				+ " where  a.cid in (select distinct cid from DataPopedom where log_id="
				+ Util.UserInfo.getUserid() + "  and  Dat_edited=1) and 1=1 ";
		if (m != null) {
			if (m.getCompany() != null) {
				if (!m.getCompany().equals("")) {
					sql += " and (a.cid like '" + m.getCompany()
							+ "' or coba_company like '%" + m.getCompany()
							+ "%' or coba_shortname like '%" + m.getCompany()
							+ "%')";
				}
			}
			if (m.getName() != null) {
				if (!m.getName().equals("")) {
					sql += " and (a.gid like '" + m.getName()
							+ "' or name like '%" + m.getName()
							+ "%' or emba_idcard like '%" + m.getName() + "%')";
				}
			}

			if (m.getDeclareState() != null) {
				if (!m.getDeclareState().equals("")) {
					sql += " and declareState=" + m.getDeclareState();
				}
			}
			if (m.getOwnmonth() != null) {
				if (!m.getOwnmonth().equals("")) {
					sql += " and ownmonth=" + m.getOwnmonth();
				}

			}
			if (m.getClient() != null) {
				if (!m.getClient().equals("")) {
					sql += " and coba_client='" + m.getClient() + "'";
				}

			}
			
			if (m.getAssistant() != null) {
				if (!m.getAssistant().equals("")) {
					sql += " and coba_assistant='" + m.getAssistant() + "'";
				}

			}

			if (m.getAddname() != null) {
				if (!m.getAddname().equals("")) {
					sql += " and a.addname='" + m.getAddname() + "'";
				}

			}

			if (m.getType() != null) {
				if (!m.getType().equals("")) {
					sql += " and type like'%" + m.getType() + "%'";
				}

			}

			if (m.getDeclaretype() != null) {
				if (!m.getDeclaretype().equals("")) {
					sql += " and type like '%" + m.getDeclaretype() + "%'";
				}

			}
			if (m.getLinktype() != null) {
				if (!m.getLinktype().equals("")) {
					sql += " and cosp_sbhs_caliname like '%" + m.getLinktype()
							+ "%'";
				}

			}
			if (m.getDatatype() != null) {
				if (!m.getDatatype().equals("")) {
					sql += " and emgd_datatype like '%" + m.getDatatype()
							+ "%'";
				}

			}
			if (m.getContactstate() != null) {
				if (!m.getContactstate().equals("")) {
					sql += " and emgd_contactstate like '%"
							+ m.getContactstate() + "%'";
				}

			}

			if (m.getClstate() != null) {
				if (!m.getClstate().equals("")) {
					sql += " and emgd_clstate like '%" + m.getClstate() + "%'";
				}

			}
			if (m.getCosp_sbhs_caliname() != null) {
				if (!m.getCosp_sbhs_caliname().equals("")) {
					sql += " and cosp_sbhs_caliname like '%"
							+ m.getCosp_sbhs_caliname() + "%'";
				}

			}

			if (m.getBookdate() != null) {
				if (!m.getBookdate().equals("")) {
					sql += " and e.dept like '%" + m.getBookdate() + "%'";
				}

			}

		}
		if (distinctName != null && !distinctName.equals("")) {
			sql += " order by " + dn + " " + order;
		} else {
			sql += " order by declareState,ownmonth desc,a.addtime desc";
		}

		if (m != null) {
			if (m.getContactstate() != null) {
				if (m.getContactstate().equals("近一周需签回")) {
					sql = "select ROW_NUMBER()over(order by declareState,ownmonth desc,addtime desc) id,a.*,emgd_id,emgd_link linktype,emgd_declare declaretype,emgd_datatype datatype,emgd_remark remark,coba_shortname shortname,coba_client client,emba_mobile,addname,emgd_contactstate contactstate,emgd_clstate clstate,cosp_sbhs_caliname from (  select 8 typeId,ebcc_id dataId,a.cid,a.gid,null ownmonth,b.emba_name+'('+ebcc_change +')' name,'近一周需签回' type,ebcc_addtime addtime,ebcc_tapr_Id taprId,3 declareState,ebcc_addname addname,ebcc_auditing_time from EmBaseCompactChange a    left join EmBase b on a.gid=b.gid   where ebcc_state>2 and ebcc_state<6 and (ebcc_change='续签' or ebcc_change='新签')  and datediff(day,ebcc_auditing_time,getdate())>20 )a left join cobase b on a.cid=b.cid left join embase c on a.cid=c.cid and a.gid=c.gid left join CoBaseServePromise csp on a.CID=csp.cid  left join embasegd d on a.typeId=d.emgd_type and a.dataId=d.emgd_dataId and emgd_state=1 order by declareState,ownmonth desc,addtime desc";
				}
			}
		}

		System.out.println(sql);

		try {
			list = db.find(sql, EmbaseGDModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 体检确认列表
	public List<EmbaseGDModel> getbcList(EmbaseGDModel m, String distinctName,
			boolean sort) {
		List<EmbaseGDModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select";
		if (distinctName != null && !distinctName.equals("")) {
			sql += " distinct " + distinctName;
		} else {
			sql += " * ";
		}
		sql += " from (select convert(int,convert(varchar(10),YEAR(embc_addtime))+replicate('0',2-len(MONTH(embc_addtime)))+convert(varchar(10),MONTH(embc_addtime))) ownmonth,"
				+ "0 typeId,case ebcl_type when 0 then '单次体检' when 1 then '入职体检' when 2 then '年度体检' end type,"
				+ "a.cid,a.gid,embc_id dataId,embc_company company,embc_shortname shortname,embc_name name,embc_spell,embc_idcard,embc_sex,coba_client client,embc_addtime addtime,embc_mobile emba_mobile,"
				+ "emgd_link linktype,emgd_declare declaretype,emgd_datatype datatype,emgd_id,cosp_bcrp_caliname,cosp_bcrp_bclinkname,ebcl_id,"
				+ "ebcs_hospital hospital,ebsa_address address,ebcl_bookdate bookdate,emgd_remark remark,ebcl_state,embc_tapr_id taprId,"
				+ "case ebcl_state when 0 then '已取消' when 1 then '待确认'  when 2 then '待申报'"
				+ " when 3 then '体检中' when 4 then '已体检' when 5 then '已体检' when 6 then '已报销' when 7 then '福利退回'"
				+ " when 8 then '报销处理' when 9 then '重新预约' when 10 then '预约中' when 11 then '已结算' when 12 then '已签收报告' when 13 then '取消中' when 14 then '已删除' when 15 then '中心退回' end declareName,"
				+ "isnull(isnull(embc_marry,emba_marital),'')embc_marry,coba_assistant assistant"
				+ " from EmBodyCheck a"
				+ " inner join EmBodyCheckList b on a.embc_id=b.ebcl_embc_id and ebcl_flag=1"
				+ " left join embase a1 on a.gid=a1.gid"
				+ " left join cobase c on a.CID=c.CID"
				+ " left join EmBaseGd d on emgd_dataId=embc_id and emgd_type=0 and emgd_state=1"
				+ " left join CoBaseServePromise csp on a.CID=csp.cid "
				+ " left join EmBcSetup e on b.ebcl_hospital=e.ebcs_id"
				+ " left join EmBcSetupAddress f on b.ebcl_area=f.ebsa_id"
				+ " left join (select gid,COUNT(*)n from embodycheck where embc_state=1 and GID >0 group by gid)g on a.gid=g.gid)z"
				+ " where 1=1";
		if (m != null) {
			if (m.getOwnmonth() != null && !m.getOwnmonth().equals("")) {
				sql += " and ownmonth=" + m.getOwnmonth();
			}
			if (m.getClient() != null && !m.getClient().equals("")) {
				sql += " and client='" + m.getClient() + "'";
			}
			if (m.getAssistant() != null && !m.getAssistant().equals("")) {
				sql += " and assistant='" + m.getAssistant() + "'";
			}
			if (m.getType() != null && !m.getType().equals("")) {
				sql += " and type='" + m.getType() + "'";
			}
			if (m.getCompany() != null && !m.getCompany().equals("")) {
				sql += " and (cid like '" + m.getCompany()
						+ "%' or company like '%" + m.getCompany()
						+ "%' or shortname like '%" + m.getCompany() + "%')";
			}
			if (m.getName() != null && !m.getName().equals("")) {
				sql += " and (gid like '" + m.getName() + "%' or name like '%"
						+ m.getName() + "%' or embc_spell like '%"
						+ m.getName() + "%')";
			}
			if (m.getAddtime() != null && !m.getAddtime().equals("")) {
				sql += " and convert(varchar(10),addtime,120)='"
						+ m.getAddtime() + "'";
			}
			if (m.getHospital() != null && !m.getHospital().equals("")) {
				sql += " and hospital='" + m.getHospital() + "'";
			}
			if (m.getAddress() != null && !m.getAddress().equals("")) {
				sql += " and address='" + m.getAddress() + "'";
			}
			if (m.getBookdate() != null && !m.getBookdate().equals("")) {
				sql += " and bookdate='" + m.getBookdate() + "'";
			}
			if (m.getLinktype() != null && !m.getLinktype().equals("")) {
				if (m.getLinktype().contains("客服")) {
					sql += " and cosp_bcrp_bclinkname like '%客服%'";
				} else if (m.getLinktype().contains("指定")) {
					sql += " and cosp_bcrp_bclinkname like '%指定%'";
				} else if (m.getLinktype().contains("员工")) {
					sql += " and cosp_bcrp_bclinkname like '%员工%'";
				} else if (m.getLinktype().contains("无需")) {
					sql += " and cosp_bcrp_bclinkname like '%无需%'";
				}
			}
			if (m.getDeclaretype() != null && !m.getDeclaretype().equals("")) {
				sql += " and declaretype='" + m.getDeclaretype() + "'";
			}
			if (m.getDatatype() != null && !m.getDatatype().equals("")) {
				if (m.getDatatype().contains("客服")) {
					sql += " and cosp_bcrp_caliname like '%客服%'";
				} else if (m.getDatatype().contains("指定")) {
					sql += " and cosp_bcrp_caliname like '%指定%'";
				} else if (m.getDatatype().contains("员工")) {
					sql += " and cosp_bcrp_caliname like '%员工%'";
				} else if (m.getDatatype().contains("无需")) {
					sql += " and cosp_bcrp_caliname like '%无需%'";
				}
			}
			if (m.getIfAddress() != null) {
				if (m.getIfAddress()) {
					sql += " and isnull(address,'')!=''";
				} else {
					sql += " and isnull(address,'')=''";
				}
			}
			if (m.getIfbookdate() != null) {
				if (m.getIfbookdate()) {
					sql += " and isnull(bookdate,'')!=''";
				} else {
					sql += " and isnull(bookdate,'')=''";
				}
			}
			if (m.getDeclareName() != null) {
				if (!m.getDeclareName().equals("")) {
					if (m.getDeclareName().contains("退回")) {
						sql += " and declareName like'%退回%'";
					} else {
						sql += " and declareName='" + m.getDeclareName() + "'";
					}
				}
			}
		}

		if (distinctName != null && !distinctName.equals("")) {
			sql += "order by " + distinctName + (sort ? " asc" : " desc");
		} else {
			sql += " order by addtime desc";
		}
		System.out.println(sql);

		try {
			list = db.find(sql, EmbaseGDModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: gethouseList
	 * @Description: TODO(服务中心读取公积金清册信息)
	 * @param m
	 * @return
	 * @return List<EmbaseGDModel> 返回类型
	 * @throws
	 */
	public List<EmbaseGDModel> gethouseList(EmbaseGDModel m) {
		List<EmbaseGDModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select ROW_NUMBER() OVER(order by a.ownmonth desc,change,hsingle,coba_company,name)id,a.*,coba_company company,coba_shortname shortname,coba_client client,coba_assistant assistant,cohf_tsday tsday,case cohf_ispwd when 1 then '有' else '无' end cohf_ispwd "
				+ ",emgd_clstate clstate,emgd_contactstate contactstate,cohf_houseid,cosp_sing_caliname,emgd_id,isnull(emgd_type,21) typeId,emgd_remark remark,cohf_company"
				+ " from ("
				+ "select emhc_id dataId,ownmonth,cid,gid,emhc_companyid companyid,emhc_houseid houseid,emhc_name name,emhc_idcard idcard,emhc_computerid computerid,emhc_change change,"
				+ "case emhc_ifdeclare when 0 then '福利未出' when 4 then '福利未出' when 3 then '福利未出' when 1 then '福利已出' when 2 then '福利已出' end excelState,"
				+ "emhc_addtime addtime,emhc_single hsingle,emhc_ifprogress ifprogress,"
				+ "case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '核查失败' end declareName,"
				+ "case emhc_ifprogress when 11 then '等待设立' when 12 then '设立完成' when 21 then '等待转移' when 22 then '等待启封' when 23 then '调入完成' when 31 then '等待封存' when 32 then '封存完成' when 41 then '等待调整' when 42 then '调整完成' end progress,"
				+ "case emhc_degree when '博士学位' then '01' when '硕士学位' then '02' when '学士学位' then '03' when '其他' then '04' end degree,"
				+ "case emhc_title when '正高职称' then '010' when '副高职称' then '020' when '中级职称' then '030' when '初级职称' then '040' when '无' then '050' end title,"
				+ "emhc_radix radix,case emhc_hj when '深户' then '01' when '非深户城镇' then '02' when '非深户农村' then '03' when '其他' then '04' end hj,emhc_mobile mobile,case isnull(emhc_wifename,'') when '' then '02' else '01' end marry,emhc_wifename wifename,emhc_wifeidcard wifeidcard,"
				+ "null total,null startMonth,null stopMonth,null bjreason"
				+ " from EmHouseChange"
				+ " where emhc_single=1"
				+ " union all"
				+ " select emhb_id,ownmonth,cid,gid,emhb_companyid,emhb_houseid,emhb_name,emhb_idcard,null,'补缴',"
				// +
				// "case isnull(emhb_Excelfile,'') when '' then '福利未出' else '福利已出' end,"
				+ "case emhb_ifdeclare when 0 then '福利未出' when 4 then '福利未出' when 3 then '福利未出' when 1 then '福利已出' when 2 then '福利已出' end,"
				+ "emhb_addtime,emhb_single,0,"
				+ "case emhb_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '核查失败' end,null,"
				+ "null,null,null,null,null,null,null,null,emhb_total,emhb_startmonth,emhb_stopmonth,"
				+ "case emhb_reason when '漏缴' then '01' when '少缴' then '02' when '外省市转入补缴' then '03' when '基数调整补缴' then '04' when '比例调整补缴' then '05' when '缓缴补缴' then '06' else '07' end emhb_reason"
				+ " from EmHouseBJ"
				+ " where emhb_tapr_id>0 and emhb_single=1"
				+ ")a inner join cobase b on a.cid=b.CID"
				+ " inner join embase e on a.gid=e.gid"
				+ " left join CoHousingFund c on companyid=c.cohf_houseid and cohf_state=1 and ((cohf_single=1 and a.cid=c.cid) or cohf_single=0) "
				+ " left join EmBaseGd d on emgd_type=21 and dataId=d.emgd_dataId"
				+ " left join CoBaseServePromise f on a.cid=f.cid"
				+ " where (change='补缴' or (change!='补缴' and cohf_ispwd=0))";
		if (m.getOwnmonth() != null && !m.getOwnmonth().equals("")) {
			sql += " and a.ownmonth=" + m.getOwnmonth();
		}
		if (m.getClient() != null && !m.getClient().equals("")) {
			sql += " and b.coba_client='" + m.getClient() + "'";
		}
		if (m.getAssistant() != null && !m.getAssistant().equals("")) {
			sql += " and b.coba_assistant='" + m.getAssistant() + "'";
		}
		if (m.getCompany() != null && !m.getCompany().equals("")) {
			sql += " and (coba_company like '%" + m.getCompany()
					+ "%' or coba_shortname like '%" + m.getCompany()
					+ "%' or coba_namespell like '%" + m.getCompany()
					+ "%' or coba_englishname like '%" + m.getCompany()
					+ "%' or a.cid like '" + m.getCompany() + "')";
		}
		if (m.getName() != null && !m.getName().equals("")) {
			sql += " and (a.name like '" + m.getName()
					+ "%' or a.idcard like '" + m.getName()
					+ "' or emba_spell like '" + m.getName()
					+ "' or a.gid like '" + m.getName() + "')";
		}

		if (m.getChange() != null && !m.getChange().equals("")) {
			sql += " and a.change='" + m.getChange() + "'";
		}
		if (m.getExcelState() != null && !m.getExcelState().equals("")) {
			sql += " and a.excelState='" + m.getExcelState() + "'";
		}
		if (m.getDeclareName() != null && !m.getDeclareName().equals("")) {
			sql += " and a.declareName='" + m.getDeclareName() + "'";
		}
		if (m.getProgress() != null && !m.getProgress().equals("")) {
			sql += " and a.progress='" + m.getProgress() + "'";
		}

		if (m.getTsday() != null && !m.getTsday().equals("")) {
			sql += " and cohf_tsday='" + m.getTsday() + "'";
		}
		if (m.getCohf_ispwd() != null && !m.getCohf_ispwd().equals("")) {
			sql += " and cohf_ispwd='" + m.getCohf_ispwd() + "'";
		}
		if (m.getContactstate() != null) {
			if (m.getContactstate().equals("")) {
				sql += " and isnull(emgd_contactstate,'')=''";
			} else {
				sql += " and emgd_contactstate='" + m.getContactstate() + "'";
			}

		}
		if (m.getClstate() != null) {
			if (m.getClstate().equals("")) {
				sql += " and isnull(emgd_clstate,'')=''";
			} else {
				sql += " and emgd_clstate='" + m.getClstate() + "'";
			}
		}
		if (m.getCosp_sing_caliname() != null
				&& !m.getCosp_sing_caliname().equals("")) {
			sql += " and cosp_sing_caliname like '" + m.getCosp_sing_caliname()
					+ "%'";
		}

		sql += " order by ownmonth desc,change,hsingle,coba_company,name";
		System.out.println(sql);
		try {
			list = db.find(sql, EmbaseGDModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmbaseGDModel> getDisList(String name, String order) {
		List<EmbaseGDModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct "
				+ name
				+ " from ("
				+ "select emhc_id dataId,ownmonth,cid,gid,emhc_companyid companyid,emhc_name name,emhc_idcard idcard,emhc_change change,"
				+ "case isnull(emhc_excelfile,'') when '' then '福利未出' else '福利已出' end excelState,"
				+ "emhc_addtime addtime,emhc_single hsingle,"
				+ "case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '核查失败' end declareName,"
				+ "emhc_remark remark"
				+ " from EmHouseChange"
				+ " where emhc_single=1"
				+ " union all"
				+ " select emhb_id,ownmonth,cid,gid,emhb_companyid,emhb_name,emhb_idcard,'补缴',null,emhb_addtime,emhb_single,"
				+ "case emhb_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' when 6 then '核查失败' end,"
				+ "emhb_remark"
				+ " from EmHouseBJ"
				+ " where emhb_tapr_id>0 and emhb_single=1"
				+ ")a inner join cobase b on a.cid=b.CID"
				+ " inner join embase e on a.gid=e.gid"
				+ " left join CoHousingFund c on companyid=c.cohf_houseid and cohf_state=1 and ((cohf_single=1 and a.cid=c.cid) or cohf_single=0) "
				+ " left join EmBaseGd d on emgd_type=21 and dataId=d.emgd_dataId"
				+ " where (change='补缴' or (change!='补缴' and cohf_ispwd=0))"
				+ " order by " + order;
		System.out.println(sql);
		try {
			list = db.find(sql, EmbaseGDModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer add(EmbaseGDModel m) {

		Integer i = 0;
		dbconn db = new dbconn();
		if (m.getDataId() != null && !m.getDataId().equals("")) {
			String sql = "insert into embasegd( emgd_type, emgd_dataId, emgd_name, "
					+ "emgd_link, emgd_declare, emgd_datatype, emgd_addtime, emgd_addname, "
					+ "emgd_state,emgd_remark,emgd_contactstate,emgd_clstate)values(?,?,?,?,?,?,getdate(),?,1,?,?,?)";
			try {
				i = db.insertAndReturnKey(sql, m.getTypeId(), m.getDataId(),
						m.getName(), m.getLinktype(), m.getDeclaretype(),
						m.getDatatype(), m.getAddname(), m.getRemark(),
						m.getContactstate(), m.getClstate());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;

	}

	public Integer mod(EmbaseGDModel m, Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();

		if (id != null && !id.equals("")) {
			String sql = "update embasegd set emgd_modname=?,emgd_modtime=getdate()";
			if (m.getLinktype() != null) {
				sql += ",emgd_link='" + m.getLinktype() + "'";
			}
			if (m.getDeclaretype() != null) {
				sql += ",emgd_declare='" + m.getDeclaretype() + "'";
			}
			if (m.getDatatype() != null) {
				sql += ",emgd_datatype='" + m.getDatatype() + "'";
			}
			if (m.getRemark() != null) {
				sql += ",emgd_remark='" + m.getRemark() + "'";
			}

			if (m.getContactstate() != null) {
				sql += ",emgd_contactstate='" + m.getContactstate() + "'";
			}

			if (m.getClstate() != null) {
				sql += ",emgd_clstate='" + m.getClstate() + "'";
			}

			sql += " where emgd_id=?";
			try {
				i = db.updatePrepareSQL(sql, m.getEmgd_modname(),
						m.getEmgd_id());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

	public Integer mod(int typeid, int dataid, String typestring) {
		Integer i = 0;
		dbconn db = new dbconn();

		String sql = "update embasegd set emgd_declare='',emgd_modtime=getdate()";

		sql += " where emgd_type=? and emgd_dataId=?";
		try {
			i = db.updatePrepareSQL(sql, typeid, dataid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}
}
