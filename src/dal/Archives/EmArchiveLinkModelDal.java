package dal.Archives;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.EmArchiveLinkModel;

public class EmArchiveLinkModelDal {

	// 查询联系信息
	public List<EmArchiveLinkModel> getInfoById(Integer id) throws SQLException {
		List<EmArchiveLinkModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select eali_id,eali_eada_id,convert(varchar(10),eali_linktime,120)eali_linktime,eali_linkmode,eali_linkcontent,eali_addname from EmArchiveLink where eali_state=1 and eali_eada_id=?";
		list = db.find(sql, EmArchiveLinkModel.class,
				dbconn.parseSmap(EmArchiveLinkModel.class), id);

		return list;
	}

	// 查询联系信息
	public List<EmArchiveLinkModel> getInfoByGid(Integer gid) {
		List<EmArchiveLinkModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		String sql = "select eali_id,eali_eada_id,gid,convert(varchar(10),eali_linktime,120)eali_linktime,eali_linkmode,eali_linkcontent,eali_addname from EmArchiveLink where eali_state=1 and gid=?";
		try {
			list = db.find(sql, EmArchiveLinkModel.class,
					dbconn.parseSmap(EmArchiveLinkModel.class), gid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 新增联系信息
	public Integer addLinkInfo(Object... obj) throws SQLException {
		Integer i = 0;
		dbconn db = new dbconn();
		i = Integer.valueOf(db
				.callWithReturn("{?=call EmArchive_Link_P_py(?,?,?,?,?,?)}",
						Types.INTEGER, obj).toString());

		return i;
	}

	// 删除
	public Integer del(Integer id) {
		Integer i = 0;
		if (id > 0) {
			dbconn db = new dbconn();
			String sql = "delete from EmArchiveLink where eali_id=?";
			try {
				i = db.updatePrepareSQL(sql, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}

}
