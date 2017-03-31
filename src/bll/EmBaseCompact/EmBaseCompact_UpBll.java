package bll.EmBaseCompact;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.EmBaseCompact.EmBaseCompact_UpDal;
import Model.EmBaseCompactBModel;

public class EmBaseCompact_UpBll {
	// 获取该员工信息
	public List<EmBaseCompactBModel> compact_base(String gid)
			throws SQLException {
		EmBaseCompact_UpDal dal = new EmBaseCompact_UpDal();
		List<EmBaseCompactBModel> list = dal.getcompact_base(gid);
		return list;
	}
	
	// 获取该员工信息
		public List<EmBaseCompactBModel> compact_editbase(String ebcc_id)
				throws SQLException {
			EmBaseCompact_UpDal dal = new EmBaseCompact_UpDal();
			List<EmBaseCompactBModel> list = dal.getcompact_editbase(ebcc_id);
			return list;
		}
}
