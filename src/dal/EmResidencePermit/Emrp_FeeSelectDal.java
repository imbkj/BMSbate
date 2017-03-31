package dal.EmResidencePermit;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmCAFFeeInfoModel;
import Model.EmResidencePermitInfoModel;

public class Emrp_FeeSelectDal {
	public List<EmResidencePermitInfoModel> getFeeList(String str) {
		List<EmResidencePermitInfoModel> list = new ArrayList<EmResidencePermitInfoModel>();
		dbconn db = new dbconn();
		String sql = "select distinct(erpi_id) erpi_id,a.gid,a.cid,coba_client," +
				" coba_shortname,a.ownmonth,emba_name,erpi_payment_kind,erpi_fee," +
				" erpi_payment_state,erpi_cl_number,erpi_wd_applicant,erpi_wd_loan_date,erpi_ri_date," +
				" case when erpi_wd_loan_date IS NULL then case  when erpi_ri_date is null then '未借款' end " +
				"when erpi_wd_loan_date IS not NULL then case  when erpi_ri_date is null then '已借款' " +
				"else '已清账' end end feestate " +
				" from EmResidencePermitInfo a left join EmRegistrationInfo b on a.erin_id=b.erin_id " +
				" inner join EmBase eb on a.gid=eb.gid inner join cobase cb on a.cid=cb.cid " +
				" inner join EmResidencePermitState st on a.erpi_state=st.stateid " +
				" WHERE statename<>'缺资料' AND statename<>'待确认' AND statename<>'待交接' " +
				" AND statename<>'待审核' AND statename<>'退回' "+str+" ORDER BY erpi_id desc,emba_name,a.ownmonth";
		try {
			list = db.find(sql, EmResidencePermitInfoModel.class,
					dbconn.parseSmap(EmResidencePermitInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//查询客服
	public List<String> getClient()
	{
		List<String> list=new ArrayList<String>();
		String sql="select distinct(coba_client) coba_client from cobase where coba_servicestate=1";
		try{
			list.add("");
			dbconn db=new dbconn();
			ResultSet rs=db.GRS(sql);
			while (rs.next()) {
				list.add(rs.getString("coba_client"));
			}
		}catch(Exception e)
		{
			
		}
		return list;
	}
	
	//查询费用明细
	public List<EmCAFFeeInfoModel> getFeeInfoList(String str) {
		List<EmCAFFeeInfoModel> list = new ArrayList<EmCAFFeeInfoModel>();
		dbconn db = new dbconn();
		String sql = "SELECT ecfi_cl_number,ecfi_class,SUM(ecfi_csd_xls) AS ecfi_csd_xls," +
				"COUNT(*) AS cou FROM EmCAFFeeInfo WHERE ecfi_cl_number LIKE 'WJZZ%' " +str+
				"GROUP BY ecfi_cl_number,ecfi_class ORDER BY ecfi_cl_number DESC";
		try {
			list = db.find(sql, EmCAFFeeInfoModel.class,
					dbconn.parseSmap(EmCAFFeeInfoModel.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//获取福利经办人
	public String getWDPeople()
	{
		String name="";
		String sql="SELECT hape_username,hape_channel FROM HandoverPeople " +
				"WHERE hape_state=1 AND hape_type='居住证' AND hape_channel LIKE " +
				"'%后道%' ORDER BY hape_order";
		try{
			dbconn db=new dbconn();
			ResultSet rs=db.GRS(sql);
			while(rs.next())
			{
				name=rs.getString("hape_username");
			}
		}catch(Exception e)
		{
			
		}
		return name;
	}
}
