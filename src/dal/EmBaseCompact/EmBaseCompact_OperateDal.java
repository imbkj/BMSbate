package dal.EmBaseCompact;

import Conn.dbconn;

public class EmBaseCompact_OperateDal {
	private dbconn conn = new dbconn();

	// 任务单编号更新
	public int updatetaskid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmBaseCompactTemplate  set ebct_tapr_id="
				+ tapr_id + " where ebct_id=" + ccsa_id + "";
		System.out.println("xxccccxxx");
		System.out.println(sqlstr);
		dbconn update = new dbconn();
		try {

			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}

	// 任务单编号更新
	public int addemcompactid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmBaseCompactChange  set ebcc_tapr_id="
				+ tapr_id + " where ebcc_id=" + ccsa_id + "";
		dbconn update = new dbconn();
		try {

			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}

	// 任务单编号更新
	public int addemcompactsaid(int ccsa_id, int tapr_id) throws Exception {
		int row = 0;
		String sqlstr = "update EmBaseCompactUpdate  set ebcu_tapr_id="
				+ tapr_id + " where ebcu_id=" + ccsa_id + "";
		dbconn update = new dbconn();
		try {

			row = update.execQuery(sqlstr);

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			update.Close();
		}
		return row;
	}
}
