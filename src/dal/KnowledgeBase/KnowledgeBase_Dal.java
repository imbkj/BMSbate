package dal.KnowledgeBase;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.KnowledgeBaseModel;
import Model.KnowledgeBase_ContentModel;

public class KnowledgeBase_Dal {
	
	//根据pid获取字典库的类型信息
	public List<KnowledgeBaseModel> getKnowledgeBaseInfo(int pid)
	{
		ResultSet rs = null;
		List<KnowledgeBaseModel> KnowledgeBase = new ArrayList<KnowledgeBaseModel>();
		if (!KnowledgeBase.isEmpty())
			KnowledgeBase.clear();
		try {
			dbconn db = new dbconn();
			
			String sqlstr = "";
			if(pid==0)
			{
				sqlstr = "SELECT  * from KnowledgeBase where pid=0";
			}
			else
			{
				sqlstr = "SELECT  * from KnowledgeBase where pid<>0";
			}
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				KnowledgeBase.add(new KnowledgeBaseModel(rs.getInt("id"),rs.getInt("PID"),rs.getString("name"),
					rs.getString("url"),rs.getString("adminUrl"),rs.getInt("level"),
					rs.getString("target"),rs.getTimestamp("addtime"),rs.getTimestamp("addtime"),
					rs.getString("addname")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return KnowledgeBase;
		}
	
	//获取字典库的内容信息
	public List<KnowledgeBase_ContentModel> getKnowledgeBaseConInfo(String str)
	{
		ResultSet rs = null;
		List<KnowledgeBase_ContentModel> KnowledgeCon = new ArrayList<KnowledgeBase_ContentModel>();
		if (!KnowledgeCon.isEmpty())
			KnowledgeCon.clear();
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  * from KnowledgeBase_Content where 1=1"+str;
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				KnowledgeCon.add(new KnowledgeBase_ContentModel(rs.getInt("kbco_ID"),rs.getInt("kbco_classid"),rs.getString("kbco_title"),
				rs.getString("kbco_content"),rs.getTimestamp("kbco_addtime"),rs.getTimestamp("kbco_updatetime"),
				rs.getString("kbco_remark"),rs.getString("kbco_addname"),rs.getString("kbco_classname"),rs.getString("kbco_fileurl")));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return KnowledgeCon;
	}
	
	//获取字典库的内容类型信息
	public List<KnowledgeBase_ContentModel> getKnowledgeBaseConClassInfo()
	{
		ResultSet rs = null;
		List<KnowledgeBase_ContentModel> KnowledgeCon = new ArrayList<KnowledgeBase_ContentModel>();
		KnowledgeBase_ContentModel con1=new KnowledgeBase_ContentModel();
		if (!KnowledgeCon.isEmpty())
			KnowledgeCon.clear();
		con1.setKbco_classname("");
		KnowledgeCon.add(con1);
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  distinct(kbco_classname) from KnowledgeBase_Content";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				KnowledgeBase_ContentModel con=new KnowledgeBase_ContentModel();
				con.setKbco_classname(rs.getString("kbco_classname"));
				KnowledgeCon.add(con);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return KnowledgeCon;
	}
	
	//获取字典库的内容添加人信息
	public List<KnowledgeBase_ContentModel> getKnowleBaseAddnameInfo()
	{
		ResultSet rs = null;
		List<KnowledgeBase_ContentModel> KnowledgeCon = new ArrayList<KnowledgeBase_ContentModel>();
		if (!KnowledgeCon.isEmpty())
			KnowledgeCon.clear();
		KnowledgeBase_ContentModel con1=new KnowledgeBase_ContentModel();
		con1.setKbco_addname("");
		KnowledgeCon.add(con1);
		try {
			dbconn db = new dbconn();
			String sqlstr = "SELECT  distinct(kbco_addname) from KnowledgeBase_Content";
			rs = db.GRS(sqlstr);
			while (rs.next()) {
				KnowledgeBase_ContentModel con=new KnowledgeBase_ContentModel();
				con.setKbco_addname(rs.getString("kbco_addname"));
				KnowledgeCon.add(con);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return KnowledgeCon;
	}
	
	//添加知识库内容
	public int addKnowleBaseInfo(KnowledgeBase_ContentModel model)
	{
		int k=0;
		try {
			dbconn db = new dbconn();
			Timestamp date= new Timestamp(System.currentTimeMillis());
			String sql = "insert into KnowledgeBase_Content(kbco_title,kbco_content,kbco_addtime,kbco_remark,kbco_addname,kbco_classname,kbco_fileurl) ";
				   sql=sql+" values('"+model.getKbco_title()+"','"+model.getKbco_content()+"','"+date+"','"+model.getKbco_remark();
				   sql=sql+"','"+model.getKbco_addname()+"','"+model.getKbco_classname()+"','"+model.getKbco_fileurl()+"')";
				   k=db.execQuery(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
	
	//更新知识库内容
	public int updateKnowleBaseInfo(KnowledgeBase_ContentModel model)
	{
		int k=0;
		try {
			dbconn db = new dbconn();
			Timestamp date= new Timestamp(System.currentTimeMillis());
			String sql = "update KnowledgeBase_Content set kbco_title='"+model.getKbco_title()+"',kbco_content='"+model.getKbco_content();
				   sql=sql+"',kbco_remark='"+model.getKbco_remark()+"',kbco_classname='"+model.getKbco_classname();
				   sql=sql+	"',kbco_fileurl='"+model.getKbco_fileurl()+"' where kbco_ID="+model.getKbco_ID();
				   k=db.execQuery(sql);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
		}
}
