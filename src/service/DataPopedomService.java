package service;

import java.util.List;

import Model.CoLatencyClientModel;
import Model.DataPopedomModel;
import Model.LoginModel;

public interface DataPopedomService {
	
	//获取已签权限列表
	public abstract  List<DataPopedomModel> getPopedomlist();

	//获取全局login列表
	public abstract  List<LoginModel> getLoginlist();
	//依据上下级关系获取login列表
	public abstract  List<LoginModel> getpidLoginlist();
	
	//获取部门login列表
	public abstract  List<LoginModel> getdepLoginlist();
	
	//获取角色login列表
	public abstract  List<LoginModel> getroleLoginlist();
	List<DataPopedomModel> getPopedomcllist();

	//获取潜在客户权限列表
	public abstract  List<CoLatencyClientModel> getPopedomCllist();

}
