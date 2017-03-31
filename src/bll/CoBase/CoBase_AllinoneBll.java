package bll.CoBase;

import java.sql.SQLException;
import java.util.List;

import Model.Allinone_ListModel;
import dal.CoBase.Cobase_AllinoneDal;

;

public class CoBase_AllinoneBll {

	// public List<Allinone_ListModel> getall_state(int gid) throws SQLException
	// {
	// Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
	// List<Allinone_ListModel> list = dal.getall_state(gid);
	// return list;
	// }
	//
	// // 获取该员所选的服务项目
	// public List<Allinone_ListModel> getcoglist_list(int gid)
	// throws SQLException {
	// Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
	// List<Allinone_ListModel> list = dal.getcoglist_list(gid);
	// return list;
	// }

	// 显示社保数据
	public List<Allinone_ListModel> getsb_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getsb_list(gid);
		return list;
	}

	public List<Allinone_ListModel> getsb_list(String strwhere)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getsb_list(strwhere);
		return list;
	}

	public List<Allinone_ListModel> getsb_listcount(int cid)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getshebao_count(cid);
		return list;
	}

	public List<Allinone_ListModel> getsb_listcount(String str)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getshebao_count(str);
		return list;
	}

	public List<Allinone_ListModel> gethouse_listcount(int cid)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gethouse_count(cid);
		return list;
	}
	
	public List<Allinone_ListModel> gethouse_listcount(String str)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gethouse_count(str);
		return list;
	}

	// 显示委托出数据
	public List<Allinone_ListModel> getwt_listcount(int cid)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getwt_listcount(cid);
		return list;
	}

	// 显示住房数据
	public List<Allinone_ListModel> gethouse_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gethouse_list(gid);
		return list;
	}

	// 显示住房数据
	public List<Allinone_ListModel> gethouse_list(String strwhere)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gethouse_list(strwhere);
		return list;
	}

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_list(int gid)
			throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getemcompact_list(gid);
		return list;
	}

	// 显示委托出数据
	public List<Allinone_ListModel> getwt_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getwt_list(gid);
		return list;
	}

	// 显示工资数据
	public List<Allinone_ListModel> getgz_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getgz_list(gid);
		return list;
	}

	// 显示档案数据
	public List<Allinone_ListModel> getfile_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getfile_list(gid);
		return list;
	}

	// 显示体检数据
	public List<Allinone_ListModel> gettj_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gettj_list(gid);
		return list;
	}

	// 显示社保卡数据
	public List<Allinone_ListModel> getcard_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getcard_list(gid);
		return list;
	}

	// 显示户籍数据
	public List<Allinone_ListModel> gethj_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gethj_list(gid);
		return list;
	}

	// 显示公积金卡制卡数据
	public List<Allinone_ListModel> getgzk_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getgzk_list(gid);
		return list;
	}

	// 显示公积金卡领卡数据
	public List<Allinone_ListModel> getglk_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getglk_list(gid);
		return list;
	}

	// 显示商保数据
	public List<Allinone_ListModel> getsy_list(int gid) throws SQLException {
		Cobase_AllinoneDal dal = new Cobase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getsy_list(gid);
		return list;
	}
}
