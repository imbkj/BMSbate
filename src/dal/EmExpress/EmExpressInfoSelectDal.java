package dal.EmExpress;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.CoBaseModel;
import Model.EmExpressContactInfoModel;
import Model.EmExpressInfoModel;
import Model.EmbaseModel;

public class EmExpressInfoSelectDal {
	
	//查询邮件信息
	public List<EmExpressInfoModel> getEmExpressInfoList(String str,String strorder){
		List<EmExpressInfoModel> list = new ArrayList<EmExpressInfoModel>();
		dbconn db = new dbconn();
		String sql = "select case expr_state when 1 then '未收件' when 2 then '已收件' when 3 then '已发件'  end ecprstate,expr_tarpid," +
				" a.*,b.*,num,coba_shortname,emba_name from EmExpressInfo a left join (SELECT expr_exct_id num FROM " +
				" EmExpressInfo GROUP BY expr_exct_id HAVING COUNT(1) > 1) f on " +
				"a.expr_exct_id=f.num and expr_state=2" +
				" inner join CoBase d on a.cid=d.cid " +
				" left join EmBase c on a.gid=c.gid,EmExpressContactInfo b where a.expr_exct_id=b.exct_id ";
				sql=sql+str+strorder;
		try {
			list = db.find(sql, EmExpressInfoModel.class,
				dbconn.parseSmap(EmExpressInfoModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//查询邮件地址信息
	public List<EmExpressContactInfoModel> getEmExpressContactInfoList(String str){
		List<EmExpressContactInfoModel> list = new ArrayList<EmExpressContactInfoModel>();
		dbconn db = new dbconn();
		String sql = "select * from EmExpressContactInfo where 1=1 "+str;
		try {
			list = db.find(sql, EmExpressContactInfoModel.class,
				dbconn.parseSmap(EmExpressContactInfoModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//员工信息查询
	public List<EmbaseModel> getEmBaseInfoList(String str){
		List<EmbaseModel> list = new ArrayList<EmbaseModel>();
		dbconn db = new dbconn();
		String sql = "select top 200 num,emba_id,b.cid,a.gid,emba_name,emba_englishname,emba_spell,emba_sex,emba_idcard,emba_state," +
				"emba_hjadd,emba_phone,emba_mobile,emba_address,emba_email,coba_shortname,coba_company,coba_client" +
				" from embase a left join (select gid,count(*) num from EmExpressContactInfo group by gid) c on" +
				" a.gid=c.gid,CoBase b where a.cid=b.CID"+str+" order by coba_shortname, emba_state desc";
		try {
			list = db.find(sql, EmbaseModel.class,
				dbconn.parseSmap(EmbaseModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//单位信息查询
	public List<CoBaseModel> getCoBaseInfoList(String str){
		List<CoBaseModel> list = new ArrayList<CoBaseModel>();
		dbconn db = new dbconn();
		String sql = "select top 200 coba_id,a.cid,coba_spell,coba_shortname,coba_servicestate,peopnum," +
				"coba_client,coba_address from CoBase a left join (select exct_id,gid,cid,count(*) peopnum " +
				" from EmExpressContactInfo group by exct_id,gid,cid) b on a.cid=b.cid and b.gid is null" +
				" where 1=1"+str+" order by coba_spell,coba_servicestate desc";
		try {
			list = db.find(sql, CoBaseModel.class,
				dbconn.parseSmap(CoBaseModel.class));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//获取员工姓名
	public String getEmbaseName(String str)
	{
		String embaname="";
		String sql="select * from embase where 1=1 "+str;
		dbconn db=new dbconn();
		ResultSet rs=null;
		try {
			rs=db.GRS(sql);
			while(rs.next())
			{
				embaname=rs.getString("emba_name");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return embaname;
	}
	
	//获取单位名称
	public String getCobaseName(String str)
	{
		String cobaname="";
		String sql="select * from cobase where 1=1 "+str;
		dbconn db=new dbconn();
		ResultSet rs=null;
		try {
			rs=db.GRS(sql);
			while(rs.next())
			{
				cobaname=rs.getString("coba_shortname");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cobaname;
	}
	
	//判断快递状态是否相同
	public Integer ifstatesame(String str)
	{
		Integer k=0;
		String sql="select distinct(expr_exct_id) num from EmExpressInfo where expr_id in("+str+")";
		dbconn db=new dbconn();
		ResultSet rs=null;
		try {
			rs=db.GRS(sql);
			while(rs.next())
			{
				k=rs.getInt("num");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return k;		
	}

	//查询客服
	public List<String> getClient()
	{
		List<String> list=new ArrayList<String>();
		String sql="select distinct(coba_client) coba_client from cobase order by coba_client";
		dbconn db=new dbconn();
		ResultSet rs=null;
		try {
			rs=db.GRS(sql);
			list.add("");
			while(rs.next())
			{
				list.add(rs.getString("coba_client"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
}
