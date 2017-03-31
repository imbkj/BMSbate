package dal.CoQuotation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoCompactCoofferModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;

public class CoofferlistDal {
	/**
	 * @Title: getCoofferlistByList
	 * @Description: TODO(获取所选报价单项目列表)
	 * @param l
	 * @return
	 * @throws SQLException
	 * @return List<CoOfferListModel> 返回类型
	 * @throws
	 */
	public List<CoOfferListModel> getCoofferlistByList(Integer cid,
			List<Integer> l, List<Integer> l2, List<String> l3, String city,
			String name) {
		List<CoOfferListModel> list = new ListModelList<CoOfferListModel>();
		dbconn db = new dbconn();
		String sql = "select distinct coof_id,coli_id,coof_name,coli_copr_id,coli_pclass,f.city,"
				+ "coli_name,coli_fee coli_fee2,coli_group_id,coli_group_count,coli_parid,coli_cpfc_name,"
				+ "case coli_copr_id when 7 then '('+coco_shebao+')' end coco_shebao,"
				+ "case coli_copr_id when 8 then '('+coco_house+')' end coco_house,coco_cpp,"
				+ "case when charindex('税',coli_name)>0 then '('+coco_gs+')' end coco_gs,"
				+ "Copc_id,coco_compacttype"
				+ " from CoOffer a"
				+ " inner join CoOfferList b on a.coof_id=b.coli_coof_id"
				+ " left join CoPclass c on b.coli_pclass=c.Copc_name"
				+ " left join CoProduct d on b.coli_copr_id=d.Copr_id"
				+ " left join CoAgencyBaseCityRel_view f on d.copr_cabc_id=f.cabc_id"
				+ " inner join cocompact g on a.coof_coco_id=g.coco_id"
				+ " where coof_state=3 and coli_state=1 and coli_stoptime is null and (coli_copr_id>0 or coli_isfwf>0)"
				+ " and (isnull(coli_isfwf,1)=0 or (isnull(coli_isfwf,1)=1 and coli_fee>0)) and coli_cpfc_name like '%人%'";
		if (cid != null) {
			if (!cid.equals("")) {
				sql += " and g.cid=" + cid;
			}
		}
		if (l.size() > 0) {
			sql += " and coco_id in (";
			for (Integer i = 0; i < l.size(); i++) {
				sql += l.get(i);
				if ((i + 1) < l.size()) {
					sql += ",";
				}
			}
			sql += ")";
		}
		if (l2.size() > 0) {
			sql += " and coof_id in (";
			for (Integer i = 0; i < l2.size(); i++) {
				sql += l2.get(i);
				if ((i + 1) < l2.size()) {
					sql += ",";
				}
			}
			sql += ")";
		}

		if (l3.size() > 0) {
			sql += " and coli_pclass in (";
			for (Integer i = 0; i < l3.size(); i++) {
				sql += "'" + l3.get(i) + "'";
				if ((i + 1) < l3.size()) {
					sql += ",";
				}
			}
			sql += ")";
		}
		if (name != null) {
			if (!name.equals("")) {
				sql += " and coli_name like '%" + name + "%'";
			}
		}
		if (city != null) {
			if (!city.equals("")) {
				sql += " and coli_city like '%" + city + "%'";
			}
		}

		sql += " order by coli_group_id,coli_group_count desc,Copc_id,coli_copr_id, coli_name,coof_name";
		System.out.println(sql);
		try {
			list = db.find(sql, CoOfferListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 获取报价单产品列表
	public List<CoOfferListModel> getcoofferlist(CoOfferListModel cm) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		boolean b = false;

		String sql = "select distinct c.cid,coli_id,coli_copr_id,coof_name,coli_pclass,isnull(coli_city,'')coli_city,coli_name+'('+convert(varchar(50),coli_fee)+coli_cpfc_name+')'coli_name,"
				+ "coli_group_id,coli_group_count,coof_coco_id coId,coof_id,"
				+"case when charindex('税',coli_name)>0 then '('+coco_gs+')' end coco_gs"
				+ " from CoOfferList a"
				+ " inner join CoOffer b on a.coli_coof_id=b.coof_id"
				+ " inner join cocompact c on b.coof_coco_id=c.coco_id"
				+ " where 1=1";
		String sql2 = "";
		if (cm != null) {
			if (cm.getCid() != null) {
				sql = sql + " and c.cid=" + cm.getCid();
			}

			if (cm.getColi_coof_id() != null) {
				sql = sql + " and coli_coof_id=" + cm.getColi_coof_id();
			}
			if (cm.getComList() != null) {
				if (cm.getComList().size() > 0) {
					sql2 = "";
					b = false;
					sql2 = sql2 + " and coli_coof_id in (";
					for (int i = 0; i < cm.getComList().size(); i++) {
						if (cm.getComList().get(i).isChecked()) {
							sql2 = sql2 + (b ? "," : "")
									+ cm.getComList().get(i).getCoof_id();
							b = true;
						}
					}
					sql2 = sql2 + ")";
					if (b) {
						sql = sql + sql2;
					}
				}
			}
			if (cm.getCoId() != null) {
				sql = sql + " and coof_coco_id=" + cm.getCoId();
			}
			if (cm.getCcmList() != null) {
				if (cm.getCcmList().size() > 0) {
					sql2 = "";
					b = false;
					sql2 = sql2 + " and coof_coco_id in (";
					for (int i = 0; i < cm.getCcmList().size(); i++) {
						if (cm.getCcmList().get(i).isChecked()) {
							sql2 = sql2 + (b ? "," : "")
									+ cm.getCcmList().get(i).getCoco_id();
							b = true;
						}

					}
					sql2 = sql2 + ")";
					if (b) {
						sql = sql + sql2;
					}
				}
			}
			if (cm.getColi_name() != null) {
				sql = sql + " and coli_name like '%" + cm.getColi_name() + "%'";
			}

			if (cm.getProduct() != null) {
				if (cm.getProduct()) {
					sql = sql
							+ " and (coli_copr_id>0 or (coli_isfwf>0 and coli_fee>0))";
				}

			}

			if (cm.getAllot() != null) {
				if (cm.getAllot()) {
					sql += " and coli_cpfc_name like '%人%' and coco_state in(4,5) and coof_state =3";
				}
			}

			if (cm.getColi_state() != null) {
				if (!cm.getColi_state().equals("")) {
					sql += " and coli_state =" + cm.getColi_state();
				}
			}

		}

		sql = sql + " order by coli_pclass,coli_name";
		System.out.print(sql);
		try {
			list = db.find(sql, CoOfferListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getCoofferMenuByCoofId
	 * @Description: TODO(获取合同报价单唯一列表)
	 * @param l
	 * @return
	 * @throws SQLException
	 * @return List<CoOfferListModel> 返回类型
	 * @throws
	 */
	public List<CoCompactCoofferModel> getCoofferMenuByCoofId(
			List<CoOfferModel> l) {
		List<CoCompactCoofferModel> list = new ListModelList<CoCompactCoofferModel>();
		dbconn db = new dbconn();

		String sql = "select distinct coli_pclass"
				+ " from View_CoCompactCooffer"
				+ " where coof_state=3 and coli_state=1 and coli_cpfc_name like '%人%' and (isnull(coli_isfwf,1)=0 or (isnull(coli_isfwf,1)=1 and coli_fee>0))";
		if (l.size() > 0) {
			sql += " and coof_id in (";
			for (Integer i = 0; i < l.size(); i++) {
				sql += l.get(i).getCoof_id();
				if ((i + 1) < l.size()) {
					sql += ",";
				}
			}
			sql += ")";
		}
		sql += " order by coli_pclass";

		try {
			list = db.find(sql, CoCompactCoofferModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<CoOfferListModel> getlistcity(Integer cid, List<Integer> l,
			List<Integer> l2, List<String> l3) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coli_city"
				+ " from View_CoCompactCooffer"
				+ " where coof_state=3 and coli_state=1 and coli_cpfc_name like '%人%' and (isnull(coli_isfwf,1)=0 or (isnull(coli_isfwf,1)=1 and coli_fee>0))";
		if (cid != null) {
			if (!cid.equals("")) {
				sql += " and cid=" + cid;
			}
		}

		if (l.size() > 0) {
			sql += " and coco_id in (";
			for (Integer i = 0; i < l.size(); i++) {
				sql += l.get(i);
				if ((i + 1) < l.size()) {
					sql += ",";
				}
			}
			sql += ")";
		}
		if (l2.size() > 0) {
			sql += " and coof_id in (";
			for (Integer i = 0; i < l2.size(); i++) {
				sql += l2.get(i);
				if ((i + 1) < l2.size()) {
					sql += ",";
				}
			}
			sql += ")";
		}
		if (l3.size() > 0) {
			sql += " and coli_pclass in (";
			for (Integer i = 0; i < l3.size(); i++) {
				sql += "'" + l3.get(i) + "'";
				if ((i + 1) < l3.size()) {
					sql += ",";
				}
			}
			sql += ")";
		}
		sql += " order by coli_city";
		try {
			list = db.find(sql, CoOfferListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询同一分组报价单项目列表
	public List<CoOfferListModel> getcoofferInfoGroup(Integer id) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coli_id from CoOfferList "
				+ "where coli_group_id in(select coli_group_id from CoOfferList where coli_id=?) "
				+ "and coli_id!=?";
		try {
			list = db.find(sql, CoOfferListModel.class, null, id, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询报价单项目信息
	public List<CoOfferListModel> getInfoById(Integer id) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coli_id,coli_name from coofferlist where coli_id =?";
		try {
			list = db.find(sql, CoOfferListModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	// 查询合同所属报价单项目信息列表
	public List<CoOfferListModel> getcoofferlist(Integer cid, Integer cocoId,
			Integer coofId, String name) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coli_id,coco_compactid,coof_name,coli_name,convert(varchar(50),coli_fee)+coli_cpfc_name coli_cpfc_name,coli_group_id,coli_group_count "
				+ "from cocompact a "
				+ "inner join cooffer b on a.coco_id=b.coof_coco_id "
				+ "inner join coofferlist c on b.coof_id=c.coli_coof_id "
				+ "where coco_state>3 and coof_state=3 and coli_state=1";
		if (cid != null) {
			sql = sql + " and a.cid=" + cid;
		}
		if (cocoId != null) {
			sql = sql + " and coco_id=" + cocoId;
		}
		if (coofId != null) {
			sql = sql + " and coof_id=" + coofId;
		}
		if (name != null) {
			if (!name.equals("")) {
				sql = sql + " and coli_name like '%" + name + "%'";
			}

		}
		System.out.println(sql);
		try {
			list = db.find(sql, CoOfferListModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询报价单项目ID
	public List<CoOfferListModel> getcoofferlistId(Integer id, String name) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coli_id "
				+ "from CoOfferList a "
				+ "inner join CoOffer b on a.coli_coof_id=b.coof_id "
				+ "inner join CoCompact c on b.coof_coco_id=c.coco_id "
				+ "where coli_state=1 and coco_id=? and coli_name=? and isnull(coli_tapr_id,0)=0";
		try {
			list = db.find(sql, CoOfferListModel.class, null, id, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询报价单项目信息
	public List<CoOfferListModel> getCoofflistProductById(Integer id) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coli_id,coli_name,copr_emce_id "
				+ "from coofferlist a "
				+ "inner join CoProduct b on a.coli_copr_id=b.copr_id"
				+ "  where coli_id =?";
		try {
			list = db.find(sql, CoOfferListModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public Integer updateTaprId(Integer id, Integer tapr_id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update coofferlist set coli_tapr_id=? where coli_id=?";
		try {
			i = db.updatePrepareSQL(sql, tapr_id, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
