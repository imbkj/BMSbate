package bll.EmBaseCompact;

import java.sql.SQLException;
import java.util.List;

import Model.EmBaseCompactListModel;
import dal.EmBaseCompact.EmBaseCompactSA_MakeListDal;

public class EmBaseCompactSA_MakeListBll {
	// 获取未制作合同补充协议基本信息
	public List<EmBaseCompactListModel> getcompactsabase(String gid)
			throws SQLException {
		EmBaseCompactSA_MakeListDal dal = new EmBaseCompactSA_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getcompactsabase(gid);
		return list;
	}

	// 获取未制作合同补充协议列表
	public List<EmBaseCompactListModel> getemcompactsalist(String name)
			throws SQLException {
		EmBaseCompactSA_MakeListDal dal = new EmBaseCompactSA_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getemcompactsalist(name);
		return list;
	}

	// 获取未审核合同补充协议列表
	public List<EmBaseCompactListModel> getautemcompactsalist()
			throws SQLException {
		EmBaseCompactSA_MakeListDal dal = new EmBaseCompactSA_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getautemcompactsalist();
		return list;
	}

	// 获取未打印合同补充协议列表
	public List<EmBaseCompactListModel> getpremcompactsalist()
			throws SQLException {
		EmBaseCompactSA_MakeListDal dal = new EmBaseCompactSA_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getpremcompactsalist();
		return list;
	}

	// 获取未签回合同补充协议列表
	public List<EmBaseCompactListModel> getsiemcompactsalist()
			throws SQLException {
		EmBaseCompactSA_MakeListDal dal = new EmBaseCompactSA_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getsiemcompactsalist();
		return list;
	}

	// 获取未归档合同补充协议列表
	public List<EmBaseCompactListModel> getfilingemcompactsalist()
			throws SQLException {
		EmBaseCompactSA_MakeListDal dal = new EmBaseCompactSA_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getfilingemcompactsalist();
		return list;
	}
}
