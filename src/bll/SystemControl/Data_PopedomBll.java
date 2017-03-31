package bll.SystemControl;

import java.util.List;

import dal.LoginDal;
import dal.SystemControl.Data_PopedomDal;

import Model.CoLatencyClientModel;
import Model.DataPopedomModel;
import Model.LoginModel;
import Util.UserInfo;


public class Data_PopedomBll {

	//根据登录名获取权限列表（已签约）
	public List<DataPopedomModel> getpopedomlist(DataPopedomModel datam){
	{
		Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
		String str1="";
		
		try{
			if(!datam.getLog_name().isEmpty()){
				str1 += " and log_name='"+datam.getLog_name()+"'";
			};
			
			if(datam.getCid()!=0){
				
				str1 += " and cid="+datam.getCid()+"";
			};
			
			if(!datam.getCoba_client().isEmpty()){
				
				str1 += " and Coba_client like '%"+datam.getCoba_client()+"%'";
			};
			
			if(!datam.getCoba_shortname().isEmpty()){
				
				str1 += "and Coba_shortname like '%"+datam.getCoba_shortname()+"%'";
			};
			
			if(!datam.getDat_addname().isEmpty()){
				
				str1 += "and Dat_addname like '%"+datam.getDat_addname()+"%'";
			};
			
			if(datam.getDat_delete()!=null){
				
				str1 += " and Dat_delete="+datam.getDat_delete()+"";
			};
			if(datam.getDat_edited()!=null){
					
					str1 += " and Dat_edited="+datam.getDat_edited()+"";
			};
				
			if(datam.getDat_selected()!=null){
				
				str1 += " and Dat_selected="+datam.getDat_selected()+"";
			};
			
			if(datam.getLog_id()!=0){
				
				str1 += " and log_id="+datam.getLog_id()+"";
			};

			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		System.out.println(str1);
		return DataPopedomdal.getlist(str1);
	}
		
}
	
	//潜在客户列表获取
	public List<CoLatencyClientModel> getpopedomcllist(DataPopedomModel datam){
	{
		Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
		String str1="";
		
		try{
			
				
			if(!datam.getLog_name().isEmpty()){
				str1 += " and log_name='"+datam.getLog_name()+"'";
			};
			
			if(datam.getCola_id()!=0){
				
				str1 += " and Cola_id="+datam.getCola_id()+"";
			};
			
			if(datam.getCoba_shortname() != null &!datam.getCoba_shortname().isEmpty()){
				
				str1 += " and Cola_company like '%"+datam.getCoba_shortname()+"%'";
			};
			
			if(datam.getCoba_client() != null & !datam.getCoba_client().isEmpty()){
				
				str1 += " and cola_follower = '"+datam.getCoba_client()+"' ";
			};
			
			if(datam.getDat_delete()!=null){
				
				str1 += " and Dat_delete="+datam.getDat_delete()+"";
			};
			if(datam.getDat_edited()!=null){
					
					str1 += " and Dat_edited="+datam.getDat_edited()+"";
			};
				
			if(datam.getDat_selected()!=null){
				
				str1 += " and Dat_selected="+datam.getDat_selected()+"";
			};
			
			if(datam.getDat_addname() != null & !datam.getDat_addname().isEmpty()){
				
				str1 += " and Dat_addname like '%"+datam.getDat_addname()+"%'";
			};
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println(str1);
		return DataPopedomdal.getCoLatencyClientlist(str1);
	}
		
}
	
	//获取潜在客户列表
	public List<CoLatencyClientModel> getpopedomcllist(String str){
		
			Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
		
			return DataPopedomdal.getCoLatencyClientAllInfo(str);
		}
		
		
		
	
	//依据 登录id 和cid 判断是否有权限
	public List<DataPopedomModel> getDatapopedomlist(String log_id,String cid){
		{
			Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
			String str1="";
			
			try{
				if(!log_id.isEmpty()){
					str1 += " and log_id='"+Integer.parseInt(log_id)+"'";
				};

				if(!cid.isEmpty()){
					
					str1 += " and cid='"+Integer.parseInt(cid)+"'";
				};
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			
			return DataPopedomdal.getlist(str1);
		}
			
	}
	
	//自动插入权限：规则1、将客户分配给客服时插入客服对该cid所有权限，2、特殊角色，如：前台等自动插入所有客户查询角色，IT、总经理等可获取所有权限，
	// 		      3、上下级角色权限插入。4、其他
	public List<LoginModel> getloginlist()
	{
		Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
		return DataPopedomdal.getLoginlist();
	}
	//获取全部操作人列表
	public List<LoginModel> getloginlist(int log_id)
	{
		Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
		return DataPopedomdal.getLoginlist(log_id);
		
	}
	
	//依据角色获取操作人权限列表
	public List<LoginModel> getroleLoginlist(String role_name)
	{
		String rolewhere="";
		if(!role_name.isEmpty()){
			rolewhere += " and rol_name like '%"+role_name+"%'";
		};
		
		
		Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
		return DataPopedomdal.getroleLoginlist(rolewhere);
		
	}
	//依据部门获取操作人列表
	public List<LoginModel> getdepLoginlist(String dep_id1,String dep_id2,String dep_id3,String dep_id4,String dep_id5,String dep_id6,String dep_id7)
	{
		String depwhere="";
		
		try{
			if(dep_id1 != null){
				depwhere += " and dep_id="+Integer.parseInt(dep_id1)+"";
			};
			
			if(dep_id2 != null){
				depwhere += " or dep_id="+Integer.parseInt(dep_id2)+"";
			};
			if(dep_id3 != null){
				depwhere += " or dep_id="+Integer.parseInt(dep_id3)+"";
			};
			
			if(dep_id4 != null){
				depwhere += " or dep_id="+Integer.parseInt(dep_id4)+"";
			};
			
			if(dep_id5 != null){
				depwhere += " or dep_id="+Integer.parseInt(dep_id5)+"";
			};
			
			if(dep_id6 != null){
				depwhere += " or dep_id="+Integer.parseInt(dep_id6)+"";
			};
			
			if(dep_id7 != null){
				depwhere += " or dep_id="+Integer.parseInt(dep_id7)+"";
			};
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Data_PopedomDal DataPopedomdal =new Data_PopedomDal();
		return DataPopedomdal.getLoginlist(depwhere);
		
	}
	
	

	//更新客服以及上级权限
	public int Data_Popedomadd(Integer cid,String log_name)
	{
		LoginDal d =new LoginDal();
		DataPopedomModel m =new DataPopedomModel();
		m.setCid(cid);
		m.setDat_selected(true);
		m.setDat_edited(true);
		m.setDat_delete(true);
		m.setLog_id(d.getUserIDByname(log_name));
		m.setDat_addname(UserInfo.getUsername());
		Data_PopedomDal dal =new Data_PopedomDal();
		   return dal.DataPopedomAdd(m);
	}
	
	//更新薪酬负责人以及上级权限
		public int Data_Popedomcsadd(Integer cid,String log_name)
		{
			LoginDal d =new LoginDal();
			DataPopedomModel m =new DataPopedomModel();
			m.setCid(cid);
			m.setCola_id(0);
			m.setDat_selected(true);
			m.setDat_edited(true);
			m.setDat_delete(true);
			m.setLog_id(d.getUserIDByname(log_name));
			m.setDat_addname(UserInfo.getUsername());
			Data_PopedomDal dal =new Data_PopedomDal();
			   return dal.DataPopedomAddone(m);
		}
	
		//添加潜在客户跟进人以及上级权限
		public int Data_Popedomaddcz(Integer cola_id,String log_name)
		{
			LoginDal d =new LoginDal();
			DataPopedomModel m =new DataPopedomModel();
			m.setCola_id(cola_id);
			m.setDat_selected(true);
			m.setDat_edited(true);
			m.setDat_delete(true);
			m.setLog_id(d.getUserIDByname(log_name));
			m.setDat_addname(UserInfo.getUsername());
			Data_PopedomDal dal =new Data_PopedomDal();
			   return dal.DataPopedomAddone(m);
		}
		
		//分配客服经理
		public int Data_Popedomaddkfjl(Integer cola_id,String log_name,Integer cid)
		{
			LoginDal d =new LoginDal();
			DataPopedomModel m =new DataPopedomModel();
			m.setCola_id(0);
			m.setCid(cid);
			m.setDat_selected(true);
			m.setDat_edited(true);
			m.setDat_delete(true);
			m.setLog_id(d.getUserIDByname(log_name));
			m.setDat_addname(UserInfo.getUsername());
			Data_PopedomDal dal =new Data_PopedomDal();
			   return dal.DataPopedomAddone(m);
		}
		
		
		//更新潜在客户跟进人以及上级权限
		public int Data_Popedomeditcz(Integer cola_id,String log_name,String oldlog_name,Integer cid)
		{
			LoginDal d =new LoginDal();
			DataPopedomModel m =new DataPopedomModel();
			m.setOldlog_id(d.getUserIDByname(oldlog_name));
			m.setCola_id(cola_id);
			m.setDat_selected(true);
			m.setDat_edited(true);
			m.setDat_delete(true);
			m.setLog_id(d.getUserIDByname(log_name));
			m.setDat_addname(UserInfo.getUsername());
			Data_PopedomDal dal =new Data_PopedomDal();
			   return dal.DataPopedomcsedit(m);
		}
	
	
}
