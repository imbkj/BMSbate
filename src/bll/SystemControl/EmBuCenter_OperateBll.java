package bll.SystemControl;

import dal.SystemControl.EmBuCenter_OperateDal;

public class EmBuCenter_OperateBll {
	EmBuCenter_OperateDal dal=new EmBuCenter_OperateDal();
	//更新业务菜单权限
	public int updateEmbuMenuPub(int rol_id,int meu_id)
	{
		return dal.updateEmbuMenuPub(rol_id, meu_id);
	}
	
	//删除菜单权限
	public int deleteEmbuMenuPub(String str,int rol_id)
	{
		return dal.deleteEmbuMenuPub(str, rol_id);
	}
}
