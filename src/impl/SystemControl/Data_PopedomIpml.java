package impl.SystemControl;

import java.util.List;

import Model.CoLatencyClientModel;
import Model.DataPopedomModel;
import Model.LoginModel;
import service.DataPopedomService;
import bll.SystemControl.Data_PopedomBll;


public  class Data_PopedomIpml implements DataPopedomService {
	private int log_id;
	private String dep_id1;
	private String dep_id2;
	private String dep_id3;
	private String dep_id4;
	private String dep_id5;
	private String dep_id6;
	private String dep_id7;
	private String rolename;
	private int cid;
	
	DataPopedomModel datam =new DataPopedomModel();
	
	CoLatencyClientModel datamcl =new CoLatencyClientModel();
	
	//获取数据权限列表 参数 说明：1、cid 2、登录名 3、公司简称 4 客服 5查询权限6修改权限7删除权限8数据权限添加人
	//"0",cocid,log_name,cola_company,cola_follower,sel,edi,del,cola_addname
	public Data_PopedomIpml(String cola_id,String cocid,String log_name,String coshortname,
			String coclinet,Boolean sel,Boolean edi,Boolean del,String coaddname){
	 try{
		 if (cola_id=="1")
		 {
			 if(!cocid.isEmpty()){
					datam.setCid(Integer.parseInt(cocid));
				}
				else
				{
					datam.setCid(0);
				};
				datam.setCoba_client(coclinet);
				datam.setCoba_shortname(coshortname);
				datam.setDat_addname(coaddname);
				if(del!=null){
					datam.setDat_delete(del);
				}
				
				if(edi!=null){
					datam.setDat_edited(edi);
					
				}
				
				if(sel!=null){
					datam.setDat_selected(sel);
					
				}
			 
		 }
		 if (cola_id=="0")
		 {
			 if(!cocid.isEmpty()){
				 datam.setCola_id(Integer.parseInt(cocid));
				}
				else
				{
					datam.setCola_id(0);
				};
				datam.setCoba_client(coclinet);
				datam.setCoba_shortname(coshortname);
				datam.setDat_addname(coaddname);
				
				if(del!=null){
					datam.setDat_delete(del);
				}
				
				if(edi!=null){
					datam.setDat_edited(edi);
					
				}
				
				if(sel!=null){
					datam.setDat_selected(sel);
					
				}
				
				
		 }
		
		
		
	 }catch (Exception e) {
			System.out.println(e.toString());
		}
	 
		datam.setLog_name(log_name);
	}
	// 按log_id获取数据权限,角色
	public Data_PopedomIpml(String log_id,String rolename){
		
		try{
			if(!log_id.isEmpty()){
				
				this.log_id=Integer.parseInt(log_id);
						
			}
			else
			{
				this.log_id=0;
			};
			
			if(!rolename.isEmpty()){
				
				this.rolename=rolename;
			}
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	
		
	}
	
	//部门获取login构造函数
	public Data_PopedomIpml(String dep_id1,String dep_id2,String dep_id3,String dep_id4,String dep_id5,String dep_id6,String dep_id7){
		
		 this.dep_id1=dep_id1;
		 this.dep_id2=dep_id2;
		 this.dep_id3=dep_id3;
		 this.dep_id4=dep_id4;
		 this.dep_id5=dep_id5;
		 this.dep_id6=dep_id6;
		 this.dep_id7=dep_id7;
		
	}
	
	public Data_PopedomIpml(){
		
	}
	
	//实现接口获取权限列表
	@Override
	public List<DataPopedomModel> getPopedomlist() {
		// TODO Auto-generated method stub
		Data_PopedomBll datapbll =new Data_PopedomBll();
		return datapbll.getpopedomlist(datam);
	}
	

	
	
	//实现接口获取login全部列表
	@Override
	public List<LoginModel> getLoginlist() {
		// TODO Auto-generated method stub
		Data_PopedomBll datapbll =new Data_PopedomBll();
		return datapbll.getloginlist();
	}
	
	//按上下级获取
	@Override
	public List<LoginModel> getpidLoginlist() {
		// TODO Auto-generated method stub
		Data_PopedomBll datapbll =new Data_PopedomBll();
		return datapbll.getloginlist(log_id);
		
	}
	//按部门获取
	@Override
	public List<LoginModel> getdepLoginlist() {
		// TODO Auto-generated method stub
		Data_PopedomBll datapbll =new Data_PopedomBll();
		return datapbll.getdepLoginlist(dep_id1, dep_id2, dep_id3, dep_id4, dep_id5, dep_id6, dep_id7);
		
	}
	//按角色获取
	@Override
	public List<LoginModel> getroleLoginlist() {
		// TODO Auto-generated method stub
		Data_PopedomBll datapbll =new Data_PopedomBll();
		return datapbll.getroleLoginlist(rolename);
		
	}
	
	//实现接口获取潜在客户权限列表
	@Override
	public List<CoLatencyClientModel> getPopedomCllist() {
		// TODO Auto-generated method stub
		
		Data_PopedomBll datapbll =new Data_PopedomBll();
		return datapbll.getpopedomcllist(datam);
	}
	@Override
	public List<DataPopedomModel> getPopedomcllist() {
		// TODO Auto-generated method stub
		return null;
	}
}
