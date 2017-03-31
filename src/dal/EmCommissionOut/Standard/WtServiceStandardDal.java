package dal.EmCommissionOut.Standard;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.zul.ListModelList;
import Conn.dbconn;


import Model.CoBaseModel;
import Model.EmCommissionOutStandardModel;
import Model.WtServiceStandardrelationModel;
import Util.UserInfo;

public class WtServiceStandardDal {
	dbconn db = new dbconn();
	
	//根据cid获取list
	public List<WtServiceStandardrelationModel> getmodelList(String strwhere)
	{
		
		List<WtServiceStandardrelationModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT a.cid,coba_company,coba_shortname,wtss_id,wtss_title,wtss_compacttype,wtss_laborcontract," +
				"wtss_laborcontractbb,wtss_employment,wtss_archives,wtss_shebaoco,wtss_gjjco,wtss_vcgjjco,wtss_shebaopayty," +
				"wtss_gjjpayty,wtss_commercial,wtss_commercialramark,wtss_bcwelfare,wtss_bcwelfareremark,wtss_payroll," +
				"wtss_tax,wtss_remark,CONVERT(varchar(10),wtss_addtime,120) wtss_addtime,wtss_addname,CONVERT(varchar(10),wtss_edittime,120) wtss_edittime,wtss_editname," +
				"wtss_state,case wtss_state when 0 then'已生效' when 1 then'已删除' end wtss_statename,coab_name FROM  View_wtservicestandard " +
				"a	 left join (select cid,coba_company,coba_shortname,coba_client from cobase ) 	b on a.cid=b.CID " +
				" where 1=1  and wtss_state<1 "
				+ strwhere
				+ " order by wtss_id desc";
		System.out.print(sql);
		try {
			list = db.find(sql, WtServiceStandardrelationModel.class,
					dbconn.parseSmap(WtServiceStandardrelationModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
		
		
	}
	
	
	public List<WtServiceStandardrelationModel> getmodelListonly(String strwhere)
	{
		
		List<WtServiceStandardrelationModel> list = new ArrayList<>();
		dbconn db = new dbconn();
		String sql = "SELECT a.cid,coba_company,coba_shortname,wtss_id,wtss_title,wtss_compacttype,wtss_laborcontract," +
				"wtss_laborcontractbb,wtss_employment,wtss_archives,wtss_shebaoco,wtss_gjjco,wtss_vcgjjco,wtss_shebaopayty," +
				"wtss_gjjpayty,wtss_commercial,wtss_commercialramark,wtss_bcwelfare,wtss_bcwelfareremark,wtss_payroll," +
				"wtss_tax,wtss_remark,CONVERT(varchar(10),wtss_addtime,120) wtss_addtime,wtss_addname,CONVERT(varchar(10),wtss_edittime,120) wtss_edittime,wtss_editname," +
				"wtss_state,case wtss_state when 0 then'已生效' when 1 then'已删除' end wtss_statename FROM wtservicestandard " +
				"a	 left join (select cid,coba_company,coba_shortname,coba_client from cobase ) 	b on a.cid=b.CID " +
				" where 1=1  and wtss_state<1 "
				+ strwhere
				+ " order by wtss_id desc";
		System.out.print(sql);
		try {
			list = db.find(sql, WtServiceStandardrelationModel.class,
					dbconn.parseSmap(WtServiceStandardrelationModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  list;
		
		
		
	}
	
	public CoBaseModel getCobaInfo(Integer cid) {
		dbconn db = new dbconn();
		List<CoBaseModel> list = new ArrayList<>();
		String sql = "select cid,coba_company,coba_shortname from cobase where cid="
				+ cid + " and coba_servicestate=1";

		try {
			list = db.find(sql, CoBaseModel.class,
					dbconn.parseSmap(CoBaseModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list.get(0) : new CoBaseModel();
	}
	
	
	
	
	//插入mode
	public Integer WtServiceStandardadd(WtServiceStandardrelationModel m) {
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call WtServiceStandard_ADD_p_zmj(" +
				     "?,?,?,?,?" +
					",?,?,?,?,?" +
					",?,?,?,?,?" +
					",?,?,?,?,?" +
					",?,?,?,?)}",
					Types.INTEGER, 
					m.getCid(),
					m.getWtss_title(),
					m.getWtss_compacttype(),
					m.getWtss_laborcontract(),
					m.getWtss_laborcontractbb(),
					m.getWtss_employment(),
					m.getWtss_archives(),
					m.getWtss_shebaoco(),
					m.getWtss_gjjco(),
					m.getWtss_vcgjjco(),
					m.getWtss_shebaopayty(),
					m.getWtss_gjjpayty(),
					m.getWtss_commercial(),
					m.getWtss_commercialramark(),
					m.getWtss_bcwelfare(),
					m.getWtss_bcwelfareremark(),
					m.getWtss_payroll(),
					m.getWtss_tax(),
					m.getWtss_remark(),
					m.getWtss_addtime(),
					m.getWtss_addname(),
					m.getWtss_edittime(),
					m.getWtss_editname(),
					m.getWtss_state()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
	//修改
	public Integer WtServiceStandardupdate(WtServiceStandardrelationModel m)
	{
		dbconn db = new dbconn();
		Integer row = 0;

		try {
			row = Integer.parseInt(db.callWithReturn(
					"{?=call WtServiceStandard_update_p_zmj(" +
				     "?,?,?,?,?,?" +
					",?,?,?,?,?" +
					",?,?,?,?,?" +
					",?,?,?,?,?" +
					",?,?,?,?)}",
					Types.INTEGER, 
					m.getWtss_id(),
					m.getCid(),
					m.getWtss_title(),
					m.getWtss_compacttype(),
					m.getWtss_laborcontract(),
					m.getWtss_laborcontractbb(),
					m.getWtss_employment(),
					m.getWtss_archives(),
					m.getWtss_shebaoco(),
					m.getWtss_gjjco(),
					m.getWtss_vcgjjco(),
					m.getWtss_shebaopayty(),
					m.getWtss_gjjpayty(),
					m.getWtss_commercial(),
					m.getWtss_commercialramark(),
					m.getWtss_bcwelfare(),
					m.getWtss_bcwelfareremark(),
					m.getWtss_payroll(),
					m.getWtss_tax(),
					m.getWtss_remark(),
					m.getWtss_addtime(),
					m.getWtss_addname(),
					m.getWtss_edittime(),
					m.getWtss_editname(),
					m.getWtss_state()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
		
	}
	
	//删除
	public Integer WtServiceStandarddelete(int dataid)
	{
		java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
		String sql ="update WtServiceStandard set wtss_state=?,wtss_edittime=?,wtss_editname=? where wtss_id= ? ";
		int i=0;
		dbconn db = new dbconn();
		
			try {
				i = db.updatePrepareSQL(sql,1,currentDate,UserInfo.getUsername(), dataid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return i;
		
	}
	
	//判断是否关联到服务费
	public  boolean checkrlation(WtServiceStandardrelationModel m )
	{
		String sql ="select COUNT(*) con from View_wtservicestandard  where wtss_id="+m.getWtss_id();
		int i=0;
		dbconn db = new dbconn();
		
			try{
			
			ResultSet rs=db.GRS(sql);
			while(rs.next())
			{
				i=rs.getInt("con");
			}
		}catch(Exception e)
		{
			
		}
			return i > 0 ? true : false;
	}
}
