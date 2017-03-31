package bll.SystemControl;


import java.util.List;

import Model.CoLatencyClientModel;
import Model.DataPopedomChangeModel;
import Model.DataPopedomModel;
import dal.SystemControl.Data_PopedomauditingDal;
import dal.SystemControl.Data_PopedomChangeDal;

public class Data_PopedomChangeBll {
	private Data_PopedomChangeDal datapopedomchanged = new Data_PopedomChangeDal();
	
	public Data_PopedomChangeBll(){
		
	}
	//提交已签数据权限列表
	public int DataPopedomchangeAdd(DataPopedomModel m)
	{
		return datapopedomchanged.DataPopedomchangeAdd_p_zmj(m);
		
	}
	
	//提交潜在客户数据权限列表
	public int DataPopedomclchangeAdd(CoLatencyClientModel datapchangem)
	{
		return datapopedomchanged.DataPopedomclchangeAdd_p_zmj(datapchangem);
		
	}
	//获取已签数据权限列表
	public List<DataPopedomModel> getcobaselist(String str,String str1,String cid,String cobastrname)
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
				
		  
			strwhere1= " and log_id  in (select log_id from login where log_name='"+str1+"')  ";

		Data_PopedomChangeDal cobasd =new Data_PopedomChangeDal();
		return cobasd.getlist(strwhere,strwhere1);
		
	}
	
	//
	//获取潜在客户数据权限列表
	public List<DataPopedomModel> getczlist(String str,String str1,String cid,String cobastrname)
	{
		String strwhere = "";
		String strwhere1 = "";
	
		if(!str.isEmpty())
		{
			strwhere= strwhere + " and cola_follower ='" +str+"'";
		}
		if(!cid.isEmpty())
		{
			strwhere=strwhere + " and a.cola_id ='" +Integer.parseInt(cid) +"'";
		}
		
		if(!cobastrname.isEmpty())
		{
			strwhere=strwhere +  "  and a.cola_company like '%"+cobastrname+"%'";
		}
				
		  
			strwhere1= " and log_id  in (select log_id from login where log_name='"+str1+"')  ";

		Data_PopedomChangeDal cobasd =new Data_PopedomChangeDal();
		return cobasd.getczlist(strwhere,strwhere1);
		
	}
	
	
}
