package dal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmArchiveSetupModel;
import Model.PubCodeConversionModel;

public class PubCodeConversionDal {

	// 按类型获取列表
	public List<PubCodeConversionModel> getListInfo(Integer id, String name) {
		List<PubCodeConversionModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select pcco_cn,pcco_code from PubCodeConversion where pucl_id=? and pcco_name=?";
		try {
			list = db.find(sql, PubCodeConversionModel.class,
					dbconn.parseSmap(PubCodeConversionModel.class), id, name);
			System.out.println(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List<EmArchiveSetupModel> getdaInfo() {
		dbconn db = new dbconn();
		List<EmArchiveSetupModel> list = new ArrayList<EmArchiveSetupModel>();
		String sql = " select * from EmArchiveSetup";
		try {
			list = db.find(sql, EmArchiveSetupModel.class,
					dbconn.parseSmap(EmArchiveSetupModel.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
