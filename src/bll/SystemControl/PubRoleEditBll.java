package bll.SystemControl;

import Model.RoleModel;
import dal.SystemControl.RoleEditDal;

public class PubRoleEditBll {
	private Integer y;
	private RoleEditDal data;
	private RoleModel reg;
	
	public PubRoleEditBll(String name,String index,int rol_id) {

		reg=new RoleModel(rol_id,name, index, "");
	}

	public int Dochek() {
		data = new RoleEditDal();

		int y = 0;
		try {
			y = data.editrol(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}
}
