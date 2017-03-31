package service;

import java.util.List;



public interface PublicDataService {
	//修改数据
	public abstract  int update();
	//删除数据
	public abstract  int delete();
	//添加数据
	public abstract  int add();
	//检查数据是否存在
	public abstract  int check();

}
