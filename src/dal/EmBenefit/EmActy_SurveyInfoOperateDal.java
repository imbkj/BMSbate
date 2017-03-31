package dal.EmBenefit;

import java.sql.CallableStatement;
import java.sql.SQLException;

import Conn.dbconn;
import Model.CreditContentInfoModel;
import Model.SurveyContentModel;
import Model.SurveyHistoryTitleInfoModel;
import Model.SurveyInfoModel;

public class EmActy_SurveyInfoOperateDal {
	//调查题目新增
	public Integer EmActy_SurveyTitleAdd(SurveyInfoModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
			.getcall("SurveyTitleInfo_Add_cyj(?,?,?,?)");
			c.setString(1, m.getSury_title());
			c.setInt(2, m.getSury_order());
			c.setString(3, m.getSury_answertype());
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	//调查内容新增
	public Integer EmActy_SurveyContentAdd(SurveyContentModel m) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
			.getcall("SurveyContent_Add_cyj(?,?,?,?,?)");
			c.setInt(1, m.getCont_suryid());
			c.setString(2, m.getCont_content());
			c.setBigDecimal(3, m.getCont_score());
			c.setInt(4, m.getCont_order());
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(5);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	//修改调查题目信息
	public int updateSurveyTitleInfo(SurveyInfoModel model){
		int k=0;
		try {
			String sql="";
			dbconn db = new dbconn();
			sql="update SurveyTitleInfo set sury_title='"+model.getSury_title()+"'," +
					"sury_answertype='"+model.getSury_answertype();
			sql=sql+"',sury_order="+model.getSury_order()+" where sury_id="+model.getSury_id();
			k=db.execQuery(sql);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
	
	//修改调查内容信息
	public int updateSurveyContentInfo(SurveyContentModel model){
		int k=0;
		try {
			String sql="";
			dbconn db = new dbconn();
			sql="update SurveyContent set cont_content='"+model.getCont_content()+"'," +
					"cont_order="+model.getCont_order()+",cont_state="+model.getCont_state()+" where cont_id="+model.getCont_id();
			k=db.execQuery(sql);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
	
	//每年的调查信息新增
	public Integer EmActy_SurveyHistoryInfoAdd(Integer suryid,Integer ownyear,String hitl_type) {
		dbconn db = new dbconn();
		try {
			CallableStatement c = db
			.getcall("SurveyHistoryTitleInfoAdd_p_cyj(?,?,?,?)");
			c.setInt(1, suryid);
			c.setInt(2, ownyear);
			c.setString(3, hitl_type);
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return 0;
		}
	}
}
