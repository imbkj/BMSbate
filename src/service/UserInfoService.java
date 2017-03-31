/**
 * @Classname UserInfoService
 * @ClassInfo 获取登录信息接口
 * @author 李文洁
 * @Date 2013-9-18
 */
package service;

public interface UserInfoService {
	//获取用户名
	String getUsername();
	//获取用户id
	String getUserid();
	//获取部门
	String getDepID();
	//获取角色
	String getRolName();
}
