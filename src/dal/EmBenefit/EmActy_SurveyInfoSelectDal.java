package dal.EmBenefit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.SurveyContentModel;
import Model.SurveyInfoModel;

public class EmActy_SurveyInfoSelectDal {
	// 查询员工活动——满意度调查题目信息
	public List<SurveyInfoModel> getSurverTitleInfo(String str){
		List<SurveyInfoModel> list=new ArrayList<SurveyInfoModel>();
		dbconn db = new dbconn();
		ResultSet rs=null;
		String sql="select sury_id,sury_title,sury_order,sury_state,sury_type," +
				"sury_answertype,case sury_answertype when 1 then '单选' when 2 then '多选' when 3 then '填空' end as answertype" +
				" from SurveyTitleInfo where 1=1 " +str+
				" order by sury_order";
			try {
				rs=db.GRS(sql);
				while(rs.next())
				{
					SurveyInfoModel model=new SurveyInfoModel();
					model.setSury_id(rs.getInt("sury_id"));
					model.setSury_order(rs.getInt("sury_order"));
					model.setSury_state(rs.getInt("sury_state"));
					model.setSury_title(rs.getString("sury_title"));
					model.setList(getSurveyContent(rs.getInt("sury_id")));
					model.setSury_answertype(rs.getString("sury_answertype"));
					model.setSury_type(rs.getString("sury_type"));
					model.setAnswertype(rs.getString("answertype"));
					list.add(model);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		return list;
	}
	
	// 查询员工活动——满意度调查内容信息
	public List<SurveyContentModel> getSurveyContentInfo(String str){
		List<SurveyContentModel> list=new ArrayList<SurveyContentModel>();
		dbconn db = new dbconn();
		String sql="select cont_id,cont_suryid,cont_content,convert(decimal(18,1),cont_score) as cont_score,cont_order,cont_state " +
				" from SurveyContent where 1=1 "+str+
				" order by cont_order";
		try {
			list = db
				.find(sql, SurveyContentModel.class, dbconn.parseSmap(
						SurveyContentModel.class));
			} catch (Exception e) {
				e.printStackTrace();
			}	
		return list;
	}
	
	// 查询员工活动——满意度调查单位信息
	public List<CoBaseModel> getSurveyCobaseInfo(String str){
		List<CoBaseModel> list=new ArrayList<CoBaseModel>();
		dbconn db = new dbconn();
		ResultSet rs=null;
		String sql="select a.cid,coba_company,coba_shortname,coba_client,coba_setuptype,coba_compacttype, peopnum";
		sql=sql+" from CoBase a left join (select cid,COUNT(*) peopnum from EmBase group by cid) b " +
				"on a.cid=b.cid  where a.CID in(select distinct(cid) cid from SurveyResultInfo)";
		try {
				rs=db.GRS(sql);
				while (rs.next()) {
					CoBaseModel model=new CoBaseModel();
					model.setCid(rs.getInt("cid"));
					model.setCoba_company(rs.getString("coba_company"));
					model.setCoba_shortname(rs.getString("coba_shortname"));
					model.setCoba_client(rs.getString("coba_client"));
					model.setCoba_setuptype(rs.getString("coba_setuptype"));
					model.setCoba_compacttype(rs.getString("coba_compacttype"));
					model.setPeopnum(rs.getInt("peopnum"));
					list.add(model);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}	
		return list;
	}
	
	//根据cont_suryid获取调查内容信息
	public List<SurveyContentModel> getSurveyContent(int cont_suryid)
	{
		return getSurveyContentInfo(" and cont_suryid="+cont_suryid);
	}
	
	//获取题目表中的最大排序号
	public Integer getMaxOrder()
	{
		Integer orderid=0;
		ResultSet rs=null;
		dbconn db = new dbconn();
		String sql="select max(sury_order) as sury_order from SurveyTitleInfo";
		try {
			rs=db.GRS(sql);
			while (rs.next()) {
				orderid=rs.getInt("sury_order");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderid;
	}
}
