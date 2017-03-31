package bll.EmBaseCompact;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Model.DocumentsInfoPubPicModel;
import Model.EmBaseCompactListModel;
import dal.CoCompact.Compact_UploadDal;
import dal.EmBaseCompact.EmBaseCompact_MakeListDal;

public class EmBaseCompact_MakeListBll {
	// 获取首页列表
	public List<EmBaseCompactListModel> getemcompactmainlist(String st,
			String name,String client) throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getemcompactmainlist(st, name,client);
		return list;
	}

	// 获取未制作合同列表
	public List<EmBaseCompactListModel> getemcompactlist(String str) throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getemcompactlist(str);
		return list;
	}

	// 获取未审核合同列表
	public List<EmBaseCompactListModel> getautemcompactlist(String str)
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getautemcompactlist(str);
		return list;
	}

	// 获取未打印合同列表
	public List<EmBaseCompactListModel> getpremcompactlist()
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getpremcompactlist();
		return list;
	}

	// 获取未签回合同列表
	public List<EmBaseCompactListModel> getsiemcompactlist()
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getsiemcompactlist();
		return list;
	}

	// 获取未归档合同列表
	public List<EmBaseCompactListModel> getfilingemcompactlist()
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getfilingemcompactlist();
		return list;
	}

	// 获取未审核模板列表
	public List<EmBaseCompactListModel> gettempemcompactlist()
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.gettempemcompactlist();
		return list;
	}

	// 获取模板列表
	public List<EmBaseCompactListModel> gettempemcompactlistall(String company,
			String eb_class) throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.gettempemcompactlistall(
				company, eb_class);
		return list;
	}

	// 获取未审核模板列表
	public List<EmBaseCompactListModel> gettemplist() throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.gettemplist();
		return list;
	}

	// 获取合同状态
	public List<EmBaseCompactListModel> getcom_state(String ebcc_id)
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getcom_state(ebcc_id);
		return list;
	}

	// 获取合同列表
	public List<EmBaseCompactListModel> getchecklist(String name,
			String ebco_maturity_date, String cl) throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getchecklist(name,
				ebco_maturity_date, cl);
		return list;
	}

	// 获取劳动合同版本列表
	public List<EmBaseCompactListModel> getverlist() throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getverlist();
		return list;
	}

	// 获取劳动合同非标列表
	public List<EmBaseCompactListModel> getemverlist(String cid)
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getemverlist(cid);
		return list;
	}

	public List<DocumentsInfoPubPicModel> getoutcont(String coco_id)
			throws Exception {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<DocumentsInfoPubPicModel> list = dal.getoutcont(coco_id);
		return list;
	}

	// 获取公司合同版本列表
	public List<EmBaseCompactListModel> getcoverlist(String str1, String str2,
			String str3, String str4) throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		String st = "";
		int zx_state = 0;
		if (!str1.equals("")) {
			st = st + " and ebct_addname like '%" + str1 + "%'";
		}
		if (!str2.equals("")) {
			st = st + " and ebct_name like '%" + str2 + "%'";
		}
		if (!str3.equals("")) {
			st = st + " and ebct_type ='" + str3 + "'";
		}
		if (!str4.equals("")) {
			if (str4.equals("执行中")) {
				zx_state = 1;
			}
			if (str4.equals("历史")) {
				zx_state = 0;
			}
			st = st + " and ebct_state = " + zx_state;
		}
		List<EmBaseCompactListModel> list = dal.getcoverlist(st);
		return list;
	}

	// 获取公司合同版本列表下载
	public List<EmBaseCompactListModel> gettempdown(String type)
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.gettempdown(type);
		return list;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	// 获取客服名称
	public List<EmBaseCompactListModel> getclientlist()
			throws SQLException {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		List<EmBaseCompactListModel> list = dal.getclientlist();
		return list;
	}
}
