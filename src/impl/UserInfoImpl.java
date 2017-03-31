/**
 * @Classname UserInfoImpl
 * @ClassInfo 获取登录信息实现类(必须传入页面session(Session session =  Executions.getCurrent().getDesktop().getSession();))
 * @author 李文洁
 * @Date 2013-9-18
 */
package impl;

import org.zkoss.zk.ui.Session;
import service.UserInfoService;
public class UserInfoImpl implements UserInfoService{
   private Session session;
   //构造函数必须传入session
   public UserInfoImpl(Session s){
	   this.session = s;
   }
   //获取用户名
   public String getUsername(){
	   String username = "";
	   try{
	   username = session.getAttribute("username").toString();
	   }catch(Exception e){
		   //username = "陈耀家";
	   }
	   return username;
   }
   //获取用户id
   public String getUserid(){
	   String userid="";
	   try{
	   userid = session.getAttribute("userid").toString();
	   }catch(Exception e){
		   userid="err";
	   }
	   return userid;
   }
   
   //获取部门ID
   public String getDepID(){
	   String depid = "";
	   try{
		   depid = session.getAttribute("depid").toString();
	   }catch(Exception e){
		   depid = "err";
	   }
	   return depid;
   }
   //获取角色名
   public String getRolName(){
	   String rolname="";
	   try{
		   rolname = session.getAttribute("rolname").toString();
	   }catch(Exception e){
		   rolname="err";
	   }
	   return rolname;
   }
   
}
