package bll.CoMenuList;

import dal.CoMenuList.Come_OperateDal;

public class Come_OperateBll {
	private Come_OperateDal dal=new Come_OperateDal();
	// 更新业务菜单权限
	public int updateCobaseMenuListRel(int role_id, int come_id) {
		return dal.updateCobaseMenuListRel(role_id, come_id);
	}
	
	//删除菜单权限
	public int deleteCobaseMenuListRel(String str, int role_id) {
		return dal.deleteCobaseMenuListRel(str, role_id);
	}
}
