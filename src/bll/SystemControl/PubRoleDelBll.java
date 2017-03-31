package bll.SystemControl;

import Model.RoleModel;
import dal.SystemControl.RoleDelDal;

public class PubRoleDelBll {
	private Integer y;
	private RoleDelDal data;
	private RoleModel reg;
	
	public PubRoleDelBll(String name,String index,int rol_id) {

		reg=new RoleModel(rol_id,name, index, "");
	}

	public int Dochek() {
		data = new RoleDelDal();

		int y = 0;
		try {
			y = data.delrol(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
}
