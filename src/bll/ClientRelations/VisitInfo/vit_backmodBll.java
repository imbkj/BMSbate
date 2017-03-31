package bll.ClientRelations.VisitInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.ClientRelations.VisitInfo.vit_backmodDal;

import Conn.dbconn;
import Model.VisitFollowModel;
import Model.VisitInfoModel;

public class vit_backmodBll {

	// 根据viin_id获取拜访计划详情
	public static VisitInfoModel getvisitbackDetail(int viin_id)
			throws SQLException {
		VisitInfoModel model = new VisitInfoModel();
		model = vit_backmodDal.getvisitbackDetail(viin_id);
		return model;
	}

	// 根据viin_id获取跟进事项
	public static List<VisitFollowModel> getvisitfollows(int viin_id)
			throws SQLException {
		List<VisitFollowModel> list = new ArrayList<VisitFollowModel>();
		list = vit_backmodDal.getvisitfollows(viin_id);
		return list;
	}
	
	//根据viin_id删除所有跟进事项
	public static int followsDel(int viin_id) throws SQLException{
		int row = 0;
		row = vit_backmodDal.followsDel(viin_id);
		return row;
	}
}
