package dal.CoFinanceManage;

import java.sql.SQLException;

import Conn.dbconn;
import Model.CoInvoiceInfoModel;
import Model.CoInvoiceModel;
import Util.UserInfo;

public class CoInvoiceOperateDal {
	dbconn db = new dbconn();

	/**
	 * @Title: add
	 * @Description: TODO(添加发票信息)
	 * @param cm
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer add(CoInvoiceModel cm) {
		Integer i = 0;
		String sql = "insert into CoInvoice(coin_invoiceid, coin_receiptid, coin_codeid, coin_total, "
				+ "coin_title, coin_idate, coin_itype, coin_iprint, coin_remark, "
				+ "coin_addtime, coin_addname,coin_state) values(?,?,?,?,?,?,?,?,?,getdate(),?,?)";
		try {
			i = db.insertAndReturnKey(sql, cm.getCoin_invoiceid(),
					cm.getCoin_receiptid(), cm.getCoin_codeid(),
					cm.getCoin_total(), cm.getCoin_title(), cm.getCoin_idate(),
					cm.getCoin_itype(), cm.getCoin_iprint(),
					cm.getCoin_remark(), cm.getCoin_addname(), 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer addRelation(Integer coinId, Integer cfcoId) {
		Integer i = 0;
		String sql = "insert into CoInvoiceRelation(cire_cfco_id,cire_coin_id,cire_state,cire_addtime,cire_addname)"
				+ "values(?,?,1,getdate(),?)";
		System.out.println(sql);
		try {
			i = db.insertAndReturnKey(sql, cfcoId, coinId,
					UserInfo.getUsername());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: addInfo
	 * @Description: TODO(添加发票明细)
	 * @param cm
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer addInfo(CoInvoiceInfoModel cm) {
		Integer i = 0;
		String sql = "insert into CoInvoiceInfo(coii_coin_id, coii_owmonth,coii_ownmonth2 "
				+ "coii_content, coii_fee, coii_feetype, coii_addtime, coii_addname, coii_state)"
				+ "values(?,?,?,?,?,?,getdate(),?,?)";
		System.out.println(sql);
		try {
			i = db.insertAndReturnKey(sql, cm.getCoii_coin_id(),
					cm.getCoii_owmonth(), cm.getCoii_owmonth2(),
					cm.getCoii_content(), cm.getCoii_fee(),
					cm.getCoii_feetype(), cm.getCoii_addname(), 1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: del
	 * @Description: TODO(删除发票信息)
	 * @param id
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer del(Integer id) {
		Integer i = 0;
		String sql = "delete from CoInvoice where coin_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: delRelation
	 * @Description: TODO(删除发票同时删除关系表)
	 * @param id
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer DelRelationByCoinId(Integer id) {
		Integer i = 0;
		String sql = "delete from CoInvoiceRelation where cire_coin_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: delinfo
	 * @Description: TODO(删除发票明细)
	 * @param id
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer DelInfoById(Integer id) {
		Integer i = 0;
		String sql = "delete from CoInvoiceInfo where coii_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * @Title: delinfo
	 * @Description: TODO(删除发票明细)
	 * @param id
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer DelInfoByCoinId(Integer id) {
		Integer i = 0;
		String sql = "delete from CoInvoiceInfo where coii_coin_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer mod(Integer id, CoInvoiceModel cm) {
		Integer i = 0;
		String sql = "update CoInvoice set coin_modtime=getdate(),coin_modname='"
				+ UserInfo.getUsername() + "'";

		if (cm.getCoin_invoiceid() != null) {
			sql = sql + ",Coin_invoiceid='" + cm.getCoin_invoiceid() + "'";
		}

		if (cm.getCoin_receiptid() != null) {
			sql = sql + ",coin_receiptid='" + cm.getCoin_receiptid() + "'";
		}
		if (cm.getCoin_codeid() != null) {
			sql = sql + ",coin_codeid='" + cm.getCoin_codeid() + "'";
		}

		if (cm.getCoin_idate() != null) {
			sql = sql + ",coin_idate='" + cm.getCoin_idate() + "'";
		}

		if (cm.getCoin_title() != null) {
			sql = sql + ",coin_title='" + cm.getCoin_title() + "'";
		}
		if (cm.getCoin_itype() != null) {
			sql = sql + ",coin_itype='" + cm.getCoin_itype() + "'";
		}
		if (cm.getCoin_iprint() != null) {
			sql = sql + ",coin_iprint='" + cm.getCoin_iprint() + "'";
		}
		if (cm.getCoin_remark() != null) {
			sql = sql + ",coin_remark='" + cm.getCoin_remark() + "'";
		}

		sql = sql + " where coin_id=?";
		System.out.println(sql);
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
