package dal.EmBenefit;

import java.sql.SQLException;

import Util.UserInfo;
import Conn.dbconn;

public class EmBenefitRelationDal {

	public Integer add(Integer embfId, Integer suppId) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "insert into EmBenefitRelation select ?,?,getdate(),?,1"
				+ " where not exists(" + " select 1 from EmBenefitRelation"
				+ " where ebre_embf_id=? and ebre_supp_id=?)";
		try {
			i = db.insertAndReturnKey(sql, embfId, suppId,
					UserInfo.getUsername(), embfId, suppId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer mod(Integer embfId, Integer suppId, Integer state) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmBenefitRelation set ebre_state=?"
				+ " where ebre_embf_id=? and ebre_supp_id=?";
		try {
			i = db.updatePrepareSQL(sql, state, embfId, suppId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	public Integer mod(Integer id, Integer state) {
		Integer i = 0;
		dbconn db = new dbconn();
		String sql = "update EmBenefitRelation set ebre_state=?"
				+ " where ebre_id=?";
		try {
			i = db.updatePrepareSQL(sql, state, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
