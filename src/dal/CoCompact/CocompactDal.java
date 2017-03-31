package dal.CoCompact;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoCompactModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Util.UserInfo;

public class CocompactDal {

	/**
	 * @Title: getCompactListByCid
	 * @Description: TODO(获取所属公司有效合同)
	 * @param cid
	 * @return
	 * @throws SQLException
	 * @return List<CoCompactModel> 返回类型
	 * @throws
	 */
	public List<CoCompactModel> getCompactListByCid(Integer cid) {
		List<CoCompactModel> list = new ListModelList<CoCompactModel>();
		dbconn db = new dbconn();

		String sql = "select coco_id,cid cid2,coco_compactid "
				+ "from cocompact "
				+ "where coco_id in (select coof_coco_id from CoOffer where coof_state=3) "
				+ "and cid=?";
		try {
			list = db.find(sql, CoCompactModel.class,
					dbconn.parseSmap(CoCompactModel.class), cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getCompactIndate
	 * @Description: TODO(系统预警查询合同到期记录)
	 * @param type
	 * @return
	 * @return List<CoCompactModel> 返回类型
	 * @throws
	 */
	public List<CoCompactModel> getCompactIndate(Integer type, String client,
			String company, String userid) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String str = "";

		String sql = "select a.cid cid2,coba_company company,coba_client client,coco_compactid,coco_inuredate,coco_indate"
				+ " from CoCompact a inner join cobase b on a.cid=b.CID"
				+ " inner join login c on dep_id IN(1,2,3,8,14,17) AND log_inure=1"
				+ " inner join (select distinct cid ,A.log_id from DataPopedom A where Dat_selected=1) D on C.log_id=D.log_id AND A.cid=D.CID"
				+ " where coco_delay='否' and coco_state>3 and coco_state!=9  and isnull(coco_stoptype,1) not in (3,4)";
		if (type.equals(15)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate) between 0 and 15";
		} else if (type.equals(20)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate) between 15 and 20";
		} else if (type.equals(50)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate) between 20 and 50";
		} else if (type.equals(-1)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate)<0";
		}
		if (client != null && !client.equals("")) {
			sql += " and coba_Client ='" + client + "'";
		}
		if (company != null && !company.equals("")) {
			sql += " and (coba_company like '%" + company
					+ "%' or coba_shortname like '%" + company
					+ "%' or a.cid like '%" + company + "%')";
		}
		if (userid != null && !userid.equals("")) {
			sql += " and c.log_id =" + userid;
		}
		sql += " order by coco_indate,coba_client,coba_company,coco_compactid";
		System.out.print(sql);
		try {
			list = db.find(sql, CoCompactModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getclientlist
	 * @Description: TODO(合同预警客服列表)
	 * @param typeString
	 * @return
	 * @return List<CoCompactModel> 返回类型
	 * @throws
	 */
	public List<CoCompactModel> getclientlist(Integer type, String userid) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String str = "";

		String sql = "select distinct coba_client client"
				+ " from CoCompact a inner join cobase b on a.cid=b.CID"
				+ " inner join login c on dep_id IN(1,2,3,8,14,17) AND log_inure=1"
				+ " inner join (select distinct cid ,A.log_id from DataPopedom A where Dat_selected=1) D on C.log_id=D.log_id AND A.cid=D.CID"
				+ " where coco_delay='否' and coco_state>3 and coco_state!=9  and isnull(coco_stoptype,1) not in (3,4)";
		if (type.equals(15)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate) between 0 and 15";
		} else if (type.equals(20)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate) between 15 and 20";
		} else if (type.equals(50)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate) between 20 and 50";
		} else if (type.equals(-1)) {
			sql += " and DATEDIFF(D,GETDATE(),coco_indate)<0";
		}

		if (userid != null && !userid.equals("")) {
			sql += " and c.log_id =" + userid;
		}
		sql += " order by coba_client";
		try {
			list = db.find(sql, CoCompactModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoCompactModel> getCompactList(CoCompactModel cm) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select coco_id,cid cid2,coco_compactid "
				+ "from cocompact " + "where 1=1";
		if (cm.getCid2() != null) {
			if (!cm.getCid2().equals("")) {
				sql = sql + " and cid=" + cm.getCid2();
			}
		}

		if (cm.getCoco_id() != null) {
			if (!cm.getCoco_id().equals("")) {
				sql = sql + " and coco_id=" + cm.getCoco_id();
			}
		}

		if (cm.getDataState() != null) {
			if (cm.getDataState().equals(1)) {
				sql = sql + " and coco_state>3";
			}

		} else {
			if (cm.getCoco_state() != null) {
				if (!cm.getCoco_state().equals("")) {
					sql = sql + " and coco_state=" + cm.getCoco_state();
				}
			}
		}

		if (cm.getCoco_house() != null) {
			if (!cm.getCoco_house().equals("")) {
				sql += " and coco_house='" + cm.getCoco_house() + "'";
			}
		}

		sql += " order by coco_compactid";
		System.out.println(sql);
		try {
			list = db.find(sql, CoCompactModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getCompactType
	 * @Description: TODO(查询公司合同是否同时包含社保/公积金的中智开户和独立开户两种情况)
	 * @param cid
	 * @param type
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean getCompactType(Integer cid, Integer type) {
		boolean b = false;
		dbconn db = new dbconn();
		String sql = "select cid from CoCompact where coco_state>3 ";
		if (type.equals(1)) {
			sql = sql
					+ " and coco_shebao is not null group by cid having MAX(coco_shebao)!=MIN(coco_shebao)";
		} else if (type.equals(2)) {
			sql = sql
					+ " and coco_house is not null group by cid having MAX(coco_house)!=MIN(coco_house)";
		}

		sql = sql + " and COUNT(*)>1 and cid=?";
		try {
			List<CoCompactModel> list = db.find(sql, CoCompactModel.class,
					null, cid);
			if (list.size() > 0) {
				b = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * @Title: getCompactList
	 * @Description: TODO(读取包含社保,公积金项目合同)
	 * @param cid
	 * @param name
	 * @return
	 * @return List<CoCompactModel> 返回类型
	 * @throws
	 */
	public List<CoCompactModel> getCompactList(Integer cid, String name,
			String single, Boolean b) {
		List<CoCompactModel> list = new ListModelList<>();
		dbconn db = new dbconn();

		String sql = "select distinct coco_id,a.cid cid2,coco_compactid,coco_shebao,coco_house "
				+ "from CoCompact a "
				+ "inner join CoOffer b on a.coco_id=b.coof_coco_id "
				+ "inner join CoOfferList c on b.coof_id=c.coli_coof_id "
				+ "where coco_state>3 and a.cid=? and coof_state=3 and coli_name=? and coli_state=1";

		if (single != null) {
			if (name.equals("社会保险服务")) {
				if (b) {
					sql = sql + " and coco_shebao='" + single + "'";
				} else {
					sql = sql + " and coco_shebao!='" + single + "'";
				}

			} else if (name.equals("住房公积金服务")) {
				if (b) {
					sql = sql + " and coco_house='" + single + "'";
				} else {
					sql = sql + " and coco_house!='" + single + "'";
				}
			}
		} else {
			sql = sql
					+ " and coli_id in (select cgli_coli_id from coglist where cgli_stopdate is null)";
		}
		System.out.println(sql);
		try {
			list = db.find(sql, CoCompactModel.class, null, cid, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getcoofferList
	 * @Description: TODO(读取包含社保,公积金项目报价单)
	 * @param coId
	 * @param name
	 * @return
	 * @return List<CoOfferModel> 返回类型
	 * @throws
	 */
	public List<CoOfferModel> getcoofferList(Integer coId, String name,
			String single, Boolean b) {
		List<CoOfferModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coof_id,coof_name,coco_shebao,coco_house "
				+ "from CoCompact a "
				+ "inner join CoOffer b on a.coco_id=b.coof_coco_id "
				+ "inner join CoOfferList c on b.coof_id=c.coli_coof_id "
				+ "where coco_state>3 and coco_id=? and coof_state=3 and coli_name=? and coli_state=1";

		if (single != null) {
			if (name.equals("社会保险服务")) {
				if (b) {
					sql = sql + " and coco_shebao ='" + single + "'";
				} else {
					sql = sql + " and coco_shebao !='" + single + "'";
				}

			} else if (name.equals("住房公积金服务")) {
				if (b) {
					sql = sql + " and coco_house ='" + single + "'";
				} else {
					sql = sql + " and coco_house !='" + single + "'";
				}
			}
		} else {
			sql = sql
					+ " and coli_id in (select cgli_coli_id from coglist where cgli_stopdate is null)";
		}
		try {
			list = db.find(sql, CoOfferModel.class, null, coId, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<Integer> getHousecppList(Integer cid) {
		List<CoCompactModel> list = new ListModelList<>();
		List<Integer> list2 = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coco_cpp from CoCompact where coco_house='独立开户' and cid=?";
		try {
			list = db.find(sql, CoCompactModel.class, null, cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() > 0) {
			for (CoCompactModel cm : list) {
				if (cm.getCoco_cpp() != null && !cm.getCoco_cpp().equals("")) {
					if (cm.getCoco_cpp().equals("浮动比例")) {
						for (int i = 5; i <= 12; i++) {
							list2.add(i);
						}
					} else {
						list2.add((int) (Double.valueOf(cm.getCoco_cpp()) * 100));
					}
				} else {
					for (int i = 5; i <= 12; i++) {
						list2.add(i);
					}
				}
			}
		} else {
			for (int i = 5; i <= 12; i++) {
				list2.add(i);
			}
		}
		return list2;
	}

	/**
	 * @Title: getitemList
	 * @Description: TODO(查询所属社保公积金项目)
	 * @param coofId
	 * @param name
	 * @return
	 * @return List<CoOfferListModel> 返回类型
	 * @throws
	 */
	public List<CoOfferListModel> getitemList(Integer coofId, String name,
			String single, Boolean b) {
		List<CoOfferListModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select distinct coli_id,coli_name,coco_shebao,coco_house,"
				+ "convert(varchar(50),coli_fee)+coli_cpfc_name coli_cpfc_name,coli_group_id,coli_group_count "
				+ "from CoCompact a "
				+ "inner join CoOffer b on a.coco_id=b.coof_coco_id "
				+ "inner join CoOfferList c on b.coof_id=c.coli_coof_id "
				+ "where coco_state>3 and coli_coof_id=? and coof_state=3 and coli_name=? and coli_state=1";
		if (single != null) {
			String singleName = "";
			if (name.equals("社会保险服务")) {
				if (b) {
					sql = sql + " and coco_shebao ='" + single + "'";
				} else {
					sql = sql + " and coco_shebao !='" + single + "'";
				}
			} else if (name.equals("住房公积金服务")) {
				if (b) {
					sql = sql + " and coco_house ='" + single + "'";
				} else {
					sql = sql + " and coco_house !='" + single + "'";
				}
			}
		} else {
			sql = sql
					+ " and coli_id in (select cgli_coli_id from coglist where cgli_stopdate is null)";
		}
		try {
			list = db.find(sql, CoOfferListModel.class, null, coofId, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public Integer mod(CoCompactModel cm, Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update cocompact set coco_modtime=getdate(),coco_modname='"
				+ cm.getCoco_modname() + "'";
		if (cm.getCoco_housefee() != null) {
			if (!cm.getCoco_housefee().equals("")) {
				sql += ",coco_housefee=" + cm.getCoco_housefee();
			}
		}
		if (cm.getCoco_houseid() != null) {
			if (!cm.getCoco_houseid().equals("")) {
				sql += ",coco_houseid='" + cm.getCoco_houseid() + "'";
			}
		}
		if (cm.getCoco_houseacc() != null) {
			if (!cm.getCoco_houseacc().equals("")) {
				sql += ",coco_houseacc='" + cm.getCoco_houseacc() + "'";
			}
		}
		if (cm.getCoco_housebank() != null) {
			if (!cm.getCoco_housebank().equals("")) {
				sql += ",coco_housebank='" + cm.getCoco_housebank() + "'";
			}
		}

		sql += " where coco_id=?";

		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
