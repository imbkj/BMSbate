package dal.EmResidencePermit;

import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmResidencePermitChangeModel;

public class Emrp_ChangeSelectDal {
	
	//查询居住证转换信息
	public List<EmResidencePermitChangeModel> getChangeList(String str)
	{
		List<EmResidencePermitChangeModel> list=new ArrayList<EmResidencePermitChangeModel>();
		String sql="select emba_name,coba_shortname,coba_company,case erpc_state when 1 then " +
				" '待转换' when 2 then '转换中' when 3 then '已转换' when 4 then '退回' when 5 " +
				" then '已撤销' end sname ,emba_idcard,coba_client,statename," +
				" convert(varchar(10),erpc_csd_ac_date,120) as erpc_csd_ac_date," +
				" convert(varchar(10),erpc_wd_oc_date,120) as erpc_wd_oc_date," +
				" a.* from EmResidencePermitChange a inner join EmBase b on a.gid=b.gid " +
				" inner join CoBase c on a.cid=c.cid " +
				" left join (select distinct(gid) gid,statename from EmRegistrationInfo er inner join (select * " +
				" from EmRegistrationState a inner join (select MAX(ersr_stateid) ersr_stateid," +
				" ersr_erin_id from EmRegistrationStateRel a inner join EmRegistrationState c " +
				" on a.ersr_stateid=c.stateid  where typename='后道状态' GROUP BY ersr_erin_id) b " +
				" on a.stateid=b.ersr_stateid where typename='后道状态') st " +
				" on er.erin_id=st.ersr_erin_id) erst on a.gid=erst.gid " +
				" where 1=1 "+str;
		try{
			dbconn db=new dbconn();
			list = db.find(sql, EmResidencePermitChangeModel.class,
					dbconn.parseSmap(EmResidencePermitChangeModel.class));
		}
		catch(Exception e)
		{
			
		}
		return list;
	}
}
