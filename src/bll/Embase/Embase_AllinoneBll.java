package bll.Embase;

import java.sql.SQLException;
import java.util.List;

import Model.Allinone_ListModel;
import Model.EmBaseCompactListModel;
import dal.EmBaseCompact.EmBaseCompact_BaseDal;
import dal.Embase.Embase_AllinoneDal;

public class Embase_AllinoneBll {
	// 显示所有服务类型状态
	public List<Allinone_ListModel> getall_state(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getall_state(gid);
		return list;
	}

	// 获取该员所选的服务项目
	public List<Allinone_ListModel> getcoglist_list(int gid)
			throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getcoglist_list(gid);
		return list;
	}

	// 显示社保数据
	public List<Allinone_ListModel> getsb_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getsb_list(gid);
		return list;
	}

	// 显示住房数据
	public List<Allinone_ListModel> gethouse_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gethouse_list(gid);
		return list;
	}

	// 显示劳动合同数据
	public List<Allinone_ListModel> getemcompact_list(int gid)
			throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getemcompact_list(gid);
		return list;
	}
	
	// 获取未制作合同列表
		public List<EmBaseCompactListModel> getemcompactlist(int gid)
				throws SQLException {
			Embase_AllinoneDal dal = new Embase_AllinoneDal();
			List<EmBaseCompactListModel> list = dal.getEmBaseCompact_Base(gid);
			return list;
		}

	// 显示委托出数据
	public List<Allinone_ListModel> getwt_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getwt_list(gid);
		return list;
	}

	// 显示工资数据
	public List<Allinone_ListModel> getgz_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getgz_list(gid);
		return list;
	}

	//显示个人支付数据
	public List<Allinone_ListModel> getpay_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getpay_list(gid);
		return list;
	}
	
	// 显示档案数据
	public List<Allinone_ListModel> getfile_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getfile_list(gid);
		return list;
	}

	// 显示体检数据
	public List<Allinone_ListModel> gettj_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gettj_list(gid);
		return list;
	}

	// 显示社保卡数据
	public List<Allinone_ListModel> getcard_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getcard_list(gid);
		return list;
	}

	// 显示户籍数据
	public List<Allinone_ListModel> gethj_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.gethj_list(gid);
		return list;
	}

	// 显示公积金卡制卡数据
	public List<Allinone_ListModel> getgzk_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getgzk_list(gid);
		return list;
	}

	// 显示公积金卡领卡数据
	public List<Allinone_ListModel> getglk_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getglk_list(gid);
		return list;
	}

	// 显示商保数据
	public List<Allinone_ListModel> getsy_list(int gid) throws SQLException {
		Embase_AllinoneDal dal = new Embase_AllinoneDal();
		List<Allinone_ListModel> list = dal.getsy_list(gid);
		return list;
	}
}
