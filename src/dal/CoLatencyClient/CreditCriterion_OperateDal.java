package dal.CoLatencyClient;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Conn.dbconn;
import Model.CreditAppraiseModel;
import Model.CreditContentInfoModel;
import Model.CreditCriterionModel;
import Model.CreditInfoModel;
import Model.MenuListModel;

public class CreditCriterion_OperateDal {
	//查询信誉评价标准的信息
	public List<CreditCriterionModel> getCreditInfo(String str)
	{
		ArrayList<CreditCriterionModel> creditList = new ArrayList<CreditCriterionModel>();
		ResultSet rs = null;
		String sql = "select * from CreditCriterion where 1=1 "+str+" order by crcr_id desc";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				String ifvalid="-";
				if(rs.getInt("crcr_state")==1)
				{
					ifvalid="是";
				}
				else
				{
					ifvalid="否";
				}
				creditList.add(new CreditCriterionModel(rs.getInt("crcr_id"),rs.getString("crcr_type"),
						rs.getString("crcr_content"),rs.getInt("crcr_state"),rs.getString("crcr_addname"),
						rs.getDate("crcr_addtime"),ifvalid));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return creditList;
	}
	
	//查询信誉评价标准状态为1的信息
	public List<CreditCriterionModel> getCreditInfoValid()
	{
		ArrayList<CreditCriterionModel> creditList = new ArrayList<CreditCriterionModel>();
		ResultSet rs = null;
		String sql = "select * from CreditCriterion where crcr_state=1 order by crcr_id desc";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				String ifvalid="-";
				if(rs.getInt("crcr_state")==1)
				{
					ifvalid="是";
				}
				else
				{
					ifvalid="否";
				}
				creditList.add(new CreditCriterionModel(rs.getInt("crcr_id"),rs.getString("crcr_type"),
					rs.getString("crcr_content"),rs.getInt("crcr_state"),rs.getString("crcr_addname"),
					rs.getDate("crcr_addtime"),ifvalid));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return creditList;
		}
	
	//根据标准名称查询信誉评价标准的信息
	public List<CreditCriterionModel> getCreditInfoFromName(String content)
	{
		ArrayList<CreditCriterionModel> creditList = new ArrayList<CreditCriterionModel>();
		ResultSet rs = null;
		String sql = "select * from CreditCriterion where crcr_content='"+content+"'";
		try {
			dbconn db = new dbconn();
			rs = db.GRS(sql);
			while (rs.next()) {
				String ifvalid="-";
				if(rs.getInt("crcr_state")==1)
				{
					ifvalid="是";
				}
				else
				{
					ifvalid="否";
				}
				creditList.add(new CreditCriterionModel(rs.getInt("crcr_id"),rs.getString("crcr_type"),
						rs.getString("crcr_content"),rs.getInt("crcr_state"),rs.getString("crcr_addname"),
						rs.getDate("crcr_addtime"),ifvalid));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return creditList;
	}
	
	//根据ID查询信誉评价标准的信息
	public List<CreditContentInfoModel> getCreditContentInfo(int id)
	{
		ArrayList<CreditContentInfoModel> contentList = new ArrayList<CreditContentInfoModel>();
		ResultSet rs = null;
		String sql = "select * from CreditContentInfo where crst_crcr_id="+id;
		try {
			dbconn db = new dbconn();
			rs= db.GRS(sql);
			while (rs.next()) {
				String ifvalid="-";
				if(rs.getInt("crst_state")==1)
				{
					ifvalid="是";
				}
				else
				{
					ifvalid="否";
				}
				contentList.add(new CreditContentInfoModel(rs.getInt("crst_id"),rs.getInt("crst_crcr_id"),rs.getString("crst_name"),
						rs.getString("crst_addname"),rs.getDate("crst_addtime"),rs.getInt("crst_state"),ifvalid));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return contentList;
	}
	
	//根据ID查询信誉评价标准的有效信息
	public List<CreditContentInfoModel> getCreditContentInfoValid(int id)
	{
		ArrayList<CreditContentInfoModel> contentList = new ArrayList<CreditContentInfoModel>();
		ResultSet rs = null;
		String sql = "select * from CreditContentInfo where crst_state=1 and  crst_crcr_id="+id;
		try {
			dbconn db = new dbconn();
			rs= db.GRS(sql);
			while (rs.next()) {
				String ifvalid="-";
				if(rs.getInt("crst_state")==1)
				{
					ifvalid="是";
				}
				else
				{
					ifvalid="否";
				}
				contentList.add(new CreditContentInfoModel(rs.getInt("crst_id"),rs.getInt("crst_crcr_id"),rs.getString("crst_name"),
						rs.getString("crst_addname"),rs.getDate("crst_addtime"),rs.getInt("crst_state"),ifvalid));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return contentList;
		}
	
	//根据潜在客户的id查询该潜在客户是否已经存在信誉评价的信息
	public List<CreditInfoModel> getCocrRelationInfo(int id)
	{
		ArrayList<CreditInfoModel> creditInfoList = new ArrayList<CreditInfoModel>();
		ResultSet rs = null;
		String sql = "select * from CocrRelation where cocr_Cola_id="+id;
		try {
			dbconn db = new dbconn();
			rs= db.GRS(sql);
			while (rs.next()) {
				CreditInfoModel model=new CreditInfoModel();
				model.setCocr_id(rs.getInt("cocr_id"));
				model.setCrIn_id(rs.getInt("cocr_crin_id"));
				model.setCocr_Cola_id(rs.getInt("cocr_Cola_id"));
				model.setCid(rs.getInt("cid"));
				creditInfoList.add(model);
				}
			} catch (Exception e) {
				e.printStackTrace();
		}
		return creditInfoList;
	}
	
	//添加一条潜在客户信誉评价总分信息
	public int addCreditInfo(CreditInfoModel model,int id)
	{
		dbconn conn = new dbconn();
		try {
			Timestamp date= new Timestamp(System.currentTimeMillis());
			CallableStatement c=conn.getcall("[CreditInfoAdd_P_cyj](?,?,?,?)");
			c.setString(1, model.getCrIn_addname());
			c.setTimestamp(2, date);
			c.setInt(3, id);
			c.registerOutParameter(4,java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(4);
		} catch (SQLException e) {
			return -1;
	  }
	}
	
	//对信誉评定标准得分表CreditAppraise插入一条信息
	public int addCreditAppraise(CreditAppraiseModel model)
	{
		dbconn conn = new dbconn();
		try {
			Timestamp date= new Timestamp(System.currentTimeMillis());
			CallableStatement c=conn.getcall("[CreditAppraiseAdd_P_cyj](?,?,?,?,?,?)");
			c.setInt(1, model.getCrap_crcr_id());
			c.setInt(2, model.getCrap_crin_id());
			c.setString(3, model.getCrap_content());
			c.setString(4, model.getCrap_addname());
			c.setTimestamp(5, date);
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			c.execute();
			return c.getInt(6);
		} catch (SQLException e) {
			return -1;
	  }
	}
	
	//修改菜单表CreditCriterion的一条数据，并返回一个Int类型的数
	public int updateCreditCriterion(CreditCriterionModel model){
		int k=0;
		try {
			String sql="";
			dbconn db = new dbconn();
			sql="update CreditCriterion set crcr_content='"+model.getCrcr_content()+"',crcr_type='"+model.getCrcr_type()+"',";
			sql=sql+"crcr_state="+model.getCrcr_state()+" where crcr_id="+model.getCrcr_id();
			k=db.execQuery(sql);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return k;
	}
	
	//修改菜单表CreditContentInfo的一条数据，并返回一个Int类型的数
	public int updateCreditContentInfo(CreditContentInfoModel model){
		int k=0;
		try {
			String sql="";
			dbconn db = new dbconn();
			sql="update CreditContentInfo set crst_name='"+model.getCrst_name()+"',crst_state="+model.getCrst_state();
			sql=sql+" where crst_id="+model.getCrst_id();
			k=db.execQuery(sql);		
			} catch (Exception e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k;
	}
}
