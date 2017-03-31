package bll.EmBaseCompact;

import java.sql.SQLException;
import java.util.List;

import dal.EmBaseCompact.EmBaseCompact_BaseDal;
import dal.Embase.Embase_AllinoneDal;
import Model.Allinone_ListModel;
import Model.EmBaseCompactListModel;

public class EmBaseCompact_BaseBll {

	// 获取未制作合同列表
	public List<EmBaseCompactListModel> getemcompactlist(int gid)
			throws SQLException {
		EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
		List<EmBaseCompactListModel> list = dal.getEmBaseCompact_Base(gid);
		return list;
	}

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_list(int gid)
			throws SQLException {
		EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
		List<Allinone_ListModel> list = dal.getemcompact_list(gid);
		return list;
	}
	
	// 显示劳动合同数据
		public List<Allinone_ListModel> getemcompact_chlist(String ebco_id)
				throws SQLException {
			EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
			List<Allinone_ListModel> list = dal.getemcompact_chlist(ebco_id);
			return list;
		}

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_baselist(int gid)
			throws SQLException {
		EmBaseCompact_BaseDal dal = new EmBaseCompact_BaseDal();
		List<Allinone_ListModel> list = dal.getemcompact_baselist(gid);
		return list;
	}
}
