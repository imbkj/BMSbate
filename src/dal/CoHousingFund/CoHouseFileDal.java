package dal.CoHousingFund;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import Conn.dbconn;
import Model.CoHousingFundFileModel;

public class CoHouseFileDal {

	// 查询指引列表
	public List<CoHousingFundFileModel> getFilelist(Integer pid) {
		dbconn db = new dbconn();
		List<CoHousingFundFileModel> list = new ListModelList<>();
		String sql = "select * from CoHousingFundFile where chff_state=1 and chff_pid=?";
		try {
			list = db.find(sql, CoHousingFundFileModel.class, null, pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询指引列表
	public List<CoHousingFundFileModel> getFilelist(String name) {
		dbconn db = new dbconn();
		List<CoHousingFundFileModel> list = new ListModelList<>();

		String sql = "select * from CoHousingFundFile where chff_state=1 and chff_name like ? ";
		try {
			list = db.find(sql, CoHousingFundFileModel.class, null, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询指引列表
	public List<CoHousingFundFileModel> getFilelist(String name, Integer pid) {
		dbconn db = new dbconn();
		List<CoHousingFundFileModel> list = new ListModelList<>();

		String sql = "select * from CoHousingFundFile where chff_state=1 and chff_name like ? and (chff_id=? or chff_pid=?)";
		try {
			list = db.find(sql, CoHousingFundFileModel.class, null, name, pid,
					pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询指引列表(含父节点)
	public List<CoHousingFundFileModel> getFilelistShow(Integer pid) {
		dbconn db = new dbconn();
		List<CoHousingFundFileModel> list = new ListModelList<>();
		String sql = "select * from CoHousingFundFile"
				+ " where chff_state=1 and chff_id=?" + " union all"
				+ " select * from CoHousingFundFile"
				+ " where chff_state=1 and chff_pid=?"
				+ " order by chff_pid,chff_sort";
		try {
			list = db.find(sql, CoHousingFundFileModel.class, null, pid, pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询当前节点最大排序
	public List<CoHousingFundFileModel> getMaxSort(Integer pid) {
		dbconn db = new dbconn();
		List<CoHousingFundFileModel> list = new ListModelList<>();
		String sql = "select isnull(max(chff_sort),0)chff_sort"
				+ " from CoHousingFundFile"
				+ " where chff_state=1 and chff_pid=?";
		try {
			list = db.find(sql, CoHousingFundFileModel.class, null, pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 查询父节点
	public List<CoHousingFundFileModel> getPid(Integer id) {
		dbconn db = new dbconn();
		List<CoHousingFundFileModel> list = new ListModelList<>();
		String sql = "select chff_pid from CoHousingFundFile"
				+ " where chff_state=1 and chff_id=?";
		try {
			list = db.find(sql, CoHousingFundFileModel.class, null, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	// 新增
	public Integer add(Integer pid, String name, String url, Integer sort,
			String content, String remark, Integer iffile, String addname) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "insert into CoHousingFundFile(chff_pid,chff_name,chff_url,"
				+ "chff_sort,chff_content,chff_remark,chff_state,chff_iffile,chff_addtime,chff_addname)"
				+ " select ?,?,?,?,?,?,1,?,getdate(),?";
		try {
			i = db.insertAndReturnKey(sql, pid, name, url, sort, content,
					remark, iffile, addname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 修改
	public Integer mod(Integer id, Integer pid, String name, String url,
			Integer sort, String content, String remark, Integer iffile) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "update CoHousingFundFile set chff_pid=?,chff_name=?,chff_url=?,chff_sort=?,"
				+ "chff_content=?,chff_remark=?,chff_iffile=?"
				+ " where chff_id=?";
		try {
			i = db.updatePrepareSQL(sql, pid, name, url, sort, content, remark,
					iffile, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	// 删除
	public Integer del(Integer id) {
		dbconn db = new dbconn();
		Integer i = 0;
		String sql = "update CoHousingFundFile set chff_state=0 where chff_id=?";
		try {
			i = db.updatePrepareSQL(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
}
