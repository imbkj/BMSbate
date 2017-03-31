package dal.EmBenefit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.SurveyHistoryContentInfoModel;
import Model.SurveyHistoryTitleInfoModel;

public class EmActy_SurveryHistorySelectDal {
	// 查询员工活动——满意度调查历史题目信息
	public List<SurveyHistoryTitleInfoModel> getSurveyHistoryTitleInfo(String str){
		List<SurveyHistoryTitleInfoModel> list=new ArrayList<SurveyHistoryTitleInfoModel>();
		dbconn db = new dbconn();
		ResultSet rs=null;
		String sql="select hitl_id,hitl_title,hitl_type,hiti_answertype,hiti_order,hiti_ownyear,hiti_state, " +
				"case hiti_answertype when 1 then '单选' when 2 then '多选' when 3 then '填空' end as answertype" +
				" from SurveyHistoryTitleInfo where 1=1 "+str+
				" order by hiti_order";
		try {
				rs=db.GRS(sql);
				while(rs.next())
				{
					SurveyHistoryTitleInfoModel model=new SurveyHistoryTitleInfoModel();
					model.setAnswertype(rs.getString("answertype"));
					model.setHiti_answertype(rs.getString("hiti_answertype"));
					model.setHiti_order(rs.getInt("hiti_order"));
					model.setHiti_ownyear(rs.getInt("hiti_ownyear"));
					model.setHiti_state(rs.getInt("hiti_state"));
					model.setHitl_id(rs.getInt("hitl_id"));
					model.setHitl_title(rs.getString("hitl_title"));
					model.setHitl_type(rs.getString("hitl_type"));
					model.setList(getList(rs.getInt("hitl_id")));
					list.add(model);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		return list;
	}
	
	// 查询员工活动——满意度调查单条历史题目信息
	public SurveyHistoryTitleInfoModel getHistoryTitleModelInfo(String str){
			SurveyHistoryTitleInfoModel model=new SurveyHistoryTitleInfoModel();
			dbconn db = new dbconn();
			ResultSet rs=null;
			String sql="select hitl_id,hitl_title,hitl_type,hiti_answertype,hiti_order,hiti_ownyear,hiti_state,relt_txtcon, " +
					"case hiti_answertype when 1 then '单选' when 2 then '多选' when 3 then '填空' end as answertype" +
					" from SurveyHistoryTitleInfo a left join SurveyResultInfo b on a.hitl_id=b.relt_titlid where 1=1 "+str+
					" order by hiti_order";
			try {
				rs=db.GRS(sql);
				while(rs.next())
				{
					model.setHiti_answertype(rs.getString("hiti_answertype"));
					model.setAnswertype(rs.getString("answertype"));
				}
				} catch (Exception e) {
					e.printStackTrace();
				}	
			return model;
		}
	
	private List<SurveyHistoryContentInfoModel> getList(Integer hicn_titleid)
	{
		return getSurveyHistoryResultInfo(" and hicn_titleid="+hicn_titleid);
	}
	
	// 查询员工活动——满意度调查历史内容信息
	public List<SurveyHistoryContentInfoModel> getSurveyHistoryContentInfo(String str){
		List<SurveyHistoryContentInfoModel> list=new ArrayList<SurveyHistoryContentInfoModel>();
		dbconn db = new dbconn();
		String sql="select hicn_id,hicn_titleid,hicn_content,convert(decimal(18,1),hicn_score) as hicn_score,hicn_order,hicn_state " +
				" from SurveyHistoryContentInfo where 1=1 "+str+
				" order by hicn_order ";
		try {
			list = db
				.find(sql, SurveyHistoryContentInfoModel.class, dbconn.parseSmap(
					SurveyHistoryContentInfoModel.class));
			} catch (Exception e) {
				e.printStackTrace();
			}	
		return list;
	}
	
	// 查询员工活动——满意度调查结果信息
	public List<SurveyHistoryContentInfoModel> getSurveyHistoryResultInfo(String str){
		List<SurveyHistoryContentInfoModel> list=new ArrayList<SurveyHistoryContentInfoModel>();
		dbconn db = new dbconn();
		String sql="select hicn_id,hicn_titleid,hicn_content,convert(decimal(18,1),hicn_score) as hicn_score,relt_txtcon," +
				" hicn_order,hicn_state,num from SurveyHistoryContentInfo a left join (select COUNT(relt_contid) num,relt_txtcon," +
				" relt_contid,relt_titlid from SurveyResultInfo GROUP BY relt_contid,relt_txtcon,relt_titlid ) b" +
				" on a.hicn_id=b.relt_contid and relt_titlid=a.hicn_titleid " +
				" where 1=1 "+str+" order by hicn_order";
		
		try {
			list = db
				.find(sql, SurveyHistoryContentInfoModel.class, dbconn.parseSmap(
					SurveyHistoryContentInfoModel.class));
			} catch (Exception e) {
				e.printStackTrace();
			}	
		return list;
	}
	
	// 查询员工活动——满意度调查结果关系信息表数据
	public List<SurveyHistoryContentInfoModel> getSurveyResultInfo(String str){
		List<SurveyHistoryContentInfoModel> list=new ArrayList<SurveyHistoryContentInfoModel>();
		dbconn db = new dbconn();
		String sql="select relt_id,a.cid,relt_titlid,relt_contid,relt_txtcon,coba_shortname " +
				" from SurveyResultInfo a inner join cobase b on a.cid=b.cid where 1=1 "+str;
		ResultSet rs=null;
		try {
			rs=db.GRS(sql);
			while(rs.next())
			{
				SurveyHistoryContentInfoModel model=new SurveyHistoryContentInfoModel();
				model.setRelt_id(rs.getInt("relt_id"));
				model.setCid(rs.getInt("cid"));
				model.setRelt_titlid(rs.getInt("relt_titlid"));
				model.setRelt_contid(rs.getInt("relt_contid"));
				model.setRelt_txtcon(rs.getString("relt_txtcon"));
				model.setCoba_shortname(rs.getString("coba_shortname"));
				list.add(model);
			}
			/*list = db
				.find(sql, SurveyHistoryContentInfoModel.class, dbconn.parseSmap(
						SurveyHistoryContentInfoModel.class));*/
			} catch (Exception e) {
				e.printStackTrace();
			}	
			return list;
	}
}
