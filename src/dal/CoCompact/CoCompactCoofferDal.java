package dal.CoCompact;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoCompactCoofferModel;

public class CoCompactCoofferDal {
	public List<CoCompactCoofferModel> getCoCompactCoofferList(Integer cid)
			throws SQLException {
		List<CoCompactCoofferModel> list = new ListModelList<CoCompactCoofferModel>();
		dbconn db = new dbconn();
		String sql = "select distinct coco_id,coof_id,coco_compactid,coof_name,coco_compacttype,"
				+ " coco_shebao,coco_house"
				+ " from View_CoCompactCooffer "
				+ " where cid=? and coco_state>3 and coof_state=3 and coli_state=1 and coli_cpfc_name like '%人'";
		System.out.println(sql);
		System.out.println(cid);
		list = db.find(sql, CoCompactCoofferModel.class,
				dbconn.parseSmap(CoCompactCoofferModel.class), cid);

		return list;
	}
}
