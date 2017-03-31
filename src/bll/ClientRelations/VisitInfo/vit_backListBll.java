package bll.ClientRelations.VisitInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.ClientRelations.VisitInfo.vit_backListDal;

import Model.VisitInfoModel;

public class vit_backListBll {

	// 获取拜访计划已审核列表
	public static List<VisitInfoModel> getVisitList(String str)
			throws Exception {
		List<VisitInfoModel> list = new ArrayList<VisitInfoModel>();
		list = vit_backListDal.getVisitList(str);
		return list;
	}

	// 获取执行人列表
	public static List<String> getPersonList() throws SQLException {
		List<String> list = new ArrayList<String>();
		list = vit_backListDal.getPersonList();
		return list;
	}

	// 获取次执行人列表
	public static List<String> getSubordinateList() throws SQLException {
		List<String> list = new ArrayList<String>();
		list = vit_backListDal.getSubordinateList();
		return list;
	}

	// 获取添加人列表
	public static List<String> getAddnameList() throws SQLException {
		List<String> list = new ArrayList<String>();
		list = vit_backListDal.getAddnameList();
		return list;
	}
}
