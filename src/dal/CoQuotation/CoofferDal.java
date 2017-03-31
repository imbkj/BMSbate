package dal.CoQuotation;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoCompactModel;
import Model.CoOfferModel;
import Util.UserInfo;

public class CoofferDal {
	public List<CoOfferModel> getCooffer(Integer cid, Integer coid) {
		List<CoOfferModel> list = new ListModelList<CoOfferModel>();
		dbconn db = new dbconn();
		String sql = "select coof_id,coof_name from cooffer "
				+ "where coof_state=3";
		
		if (coid != null) {
			sql += " and coof_coco_id=" + coid;
		}else {
			if (cid != null && cid!=0) {
				sql += " and cid=" + cid;
			}
		}
		try {
			list = db.find(sql, CoOfferModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: getCoofferListByCid
	 * @Description: TODO(根据合同表ID获取报价单列表)
	 * @param cid
	 * @return
	 * @throws SQLException
	 * @return List<CoOfferModel> 返回类型
	 * @throws
	 */
	public List<CoOfferModel> getCoofferByCoid(Integer coid) {
		List<CoOfferModel> list = new ListModelList<CoOfferModel>();
		dbconn db = new dbconn();
		String sql = "select coof_id,coof_name from cooffer "
				+ "where coof_coco_id =? and coof_state=3";
		try {
			list = db.find(sql, CoOfferModel.class,
					dbconn.parseSmap(CoOfferModel.class), coid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOfferModel> getCoofferByCid(Integer cid) {
		List<CoOfferModel> list = new ListModelList<CoOfferModel>();
		dbconn db = new dbconn();
		String sql = "select coof_id,coof_name from cooffer where cid =? and coof_state=3";

		try {
			list = db.find(sql, CoOfferModel.class,
					dbconn.parseSmap(CoOfferModel.class), cid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoOfferModel> getCoofferByCocoList(List<CoCompactModel> colist,boolean b) {
		List<CoOfferModel> list = new ListModelList<CoOfferModel>();
		dbconn db = new dbconn();
		String sql = "select coof_id,coof_name from cooffer where coof_state=3";
		String sql2 = "";
		if (colist != null && colist.size() > 0) {
			for (CoCompactModel cm : colist) {
				if ((cm.isChecked() || b) && cm.getCoco_id() != null) {
					sql2 = sql2 + (sql2.equals("") ? "" : ",")
							+ cm.getCoco_id();
				}
			}
		}
		sql = sql
				+ (sql2.equals("") ? "" : " and coof_coco_id in (" + sql2 + ")");

		try {
			list = db.find(sql, CoOfferModel.class,
					dbconn.parseSmap(CoOfferModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @Title: empCooffer
	 * @Description: 根据员工GID获取报价单信息
	 * @return
	 * @return List<CoOfferModel> 返回类型
	 * @throws
	 */
	public List<CoOfferModel> empCooffer(Integer gid) {
		List<CoOfferModel> list = new ListModelList<CoOfferModel>();
		dbconn db = new dbconn();
		String sql = "select gid,coof_id,coof_name,coli_id,coli_name,"
				+ "convert(varchar(10),cgli_startdate)cgli_startdate,"
				+ "convert(varchar(10),cgli_startdate2)cgli_startdate2,"
				+ "convert(varchar(10),cgli_stopdate)cgli_stopdate,f.name city,copc_id,g.copc_name"
				+ " ,convert(varchar(50),coli_fee)+coli_cpfc_name coli_fee"
				+ " from CoGList a inner join CoOfferList b on a.cgli_coli_id=b.coli_id "
				+ " inner join CoOffer c on b.coli_coof_id=c.coof_id "
				+ " inner join CoProduct d on b.coli_copr_id=d.Copr_id "
				+ " inner join CoAgencyBaseCityRel e on d.copr_cabc_id=e.cabc_id "
				+ " inner join PubProCity f on e.cabc_ppc_id=f.id "
				+ " inner join CoPclass g on d.copr_copc_id=g.Copc_id "
				+ " where gid=? and cgli_state=1 "
				+ " order by coof_id,copc_id,coli_name";
		System.out.println(sql);
		try {

			list = db.find(sql, CoOfferModel.class,
					dbconn.parseSmap(CoOfferModel.class), gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	public Integer mod(CoOfferModel m, Integer id) {
		Integer i = 0;
		dbconn db = new dbconn();
		if (id != null && !id.equals("")) {
			String sql = "update cooffer set coof_modtime=getdate(),coof_modname=?";
			if (m.getCoof_state() != null) {
				if (!m.getCoof_state().equals("")) {
					sql += ",coof_state=" + m.getCoof_state();
				}
			}
			sql += " where coof_id=?";
			try {
				i = db.updatePrepareSQL(sql, m.getCoof_modname(), id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
}
