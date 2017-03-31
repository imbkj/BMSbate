package dal.CoFinanceManage;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoInvoiceInfoModel;
import Model.CoInvoiceModel;
import Model.CoInvoiceRelationModel;

public class CoInvoiceSelectDal {
	private dbconn db = new dbconn();

	public List<CoInvoiceModel> SearchList(CoInvoiceModel cm) {
		List<CoInvoiceModel> list = new ListModelList<>();
		String sql = "select distinct coin_id, coin_invoiceid, coin_receiptid, coin_codeid, coin_total, "
				+ "coin_title, coin_idate, coin_itype, coin_iprint, coin_remark, coin_addtime, coin_addname, coin_state"
				+ " from CoInvoice a"
				+ " inner join CoInvoiceRelation b on a.coin_id=b.cire_coin_id"
				+ " where 1=1";
		if (cm.getCoin_state() != null) {
			if (!cm.getCoin_state().equals("")) {
				sql = sql + " and coin_state=" + cm.getCoin_state();
			}
		}
		if (cm.getCoin_id() != null) {
			if (!cm.getCoin_id().equals("")) {
				sql = sql + " and coin_id=" + cm.getCoin_id();
			}
		}
		if (cm.getCoin_cfco_id() != null) {
			if (!cm.getCoin_cfco_id().equals("")) {
				sql = sql + " and cire_cfco_id=" + cm.getCoin_cfco_id();
			}
		}

		if (cm.getCid() != null) {
			if (!cm.getCid().equals("")) {
				sql = sql
						+ " and cire_cfco_id in (select cfco_id from CoFinanceCollect where cid="
						+ cm.getCid() + ")";
			}
		}

		sql = sql + " order by coin_id desc";

		try {
			list = db.find(sql, CoInvoiceModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	public List<CoInvoiceModel> maxId(){
		List<CoInvoiceModel> list = new ListModelList<>();
		String sql="select convert(varchar(50),(max(coin_invoiceid)+1))coin_invoiceid from CoInvoice";
		
		try {
			list=db.find(sql, CoInvoiceModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<CoInvoiceInfoModel> SearchInfoList(CoInvoiceInfoModel cm) {
		List<CoInvoiceInfoModel> list = new ListModelList<>();
		String sql = "SELECT coii_id, coii_coin_id, coii_owmonth,coii_ownmonth2, coii_content, coii_fee, coii_feetype, coii_addtime, coii_addname, coii_state"
				+ " FROM CoInvoiceInfo" + " where 1=1";
		if (cm.getCoii_coin_id() != null) {
			if (!cm.getCoii_coin_id().equals("")) {
				sql = sql + " and coii_coin_id=" + cm.getCoii_coin_id();
			}
		}

		try {
			list = db.find(sql, CoInvoiceInfoModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public List<CoInvoiceRelationModel> SearchRelationList(
			CoInvoiceRelationModel cm) {
		List<CoInvoiceRelationModel> list = new ListModelList<>();
		String sql = "SELECT cire_id, cire_cfco_id, cire_coin_id, cire_state, cire_addtime, cire_addname"
				+ " FROM CoInvoiceRelation" + " where 1=1";
		if (cm.getCire_coin_id() != null) {
			if (!cm.getCire_coin_id().equals("")) {
				sql = sql + " and cire_coin_id=" + cm.getCire_coin_id();
			}
		}

		try {
			list = db.find(sql, CoInvoiceRelationModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;

	}

}
