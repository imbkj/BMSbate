 package bll.SystemControl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dal.SystemControl.Data_PopedomauditingDal;
import Model.DataPopedomChangeModel;
import Model.DataPopedomModel;
import Model.LoginModel;


public class Data_PopedomAuditingBll {
	private Data_PopedomauditingDal datapopedomchanged = new Data_PopedomauditingDal();
	
	//提交需审核的数据申请列表
	public int DataPopedomchangeauditing(DataPopedomChangeModel m)
	{
		return datapopedomchanged.DataPopedomchangeauditing(m);
	}
	//获取审核的列表
	public List<DataPopedomChangeModel> getcobaselist(String str,String str1,String cid,String cobastrname,String addname)
	{
		String strwhere = "";
		String strwhere1 = "";
	
		if(!str.isEmpty())
		{
			strwhere= strwhere + " and coba_client ='" +str+"'";
		}
		if(!cid.isEmpty())
		{
			strwhere=strwhere + " and a.cid ='" +Integer.parseInt(cid) +"'";
		}
		
		if(!cobastrname.isEmpty())
		{
			strwhere=strwhere +  "  and a.coba_shortname like '%"+cobastrname+"%'";
		}
		if(!addname.isEmpty())
		{
			strwhere=strwhere +  "  and Dat_addname like '%"+addname+"%'";
		}
		strwhere1= " and log_id  in (select log_id from login where log_name='"+str1+"')  ";
		Data_PopedomauditingDal cobasd =new Data_PopedomauditingDal();
		return cobasd.getlist(strwhere,strwhere1);
		
	}
	
		
}
