package bll.SystemControl;

import dal.SystemControl.PubRegUserDal;
import dal.SystemControl.RoleAddDal;

import Model.RoleModel;

public class PubRoleAddBll {
	private Integer y;
	private RoleAddDal data;
	private RoleModel reg;
	private Integer y1;
	private RoleAddDal data2;
	private RoleModel chk;
	
	public void PubRoleAddBllAdd(String name,String index) {

		reg=new RoleModel(0,name, index, "");
	}
	
	public void RoleCF(String name) {
		chk=new RoleModel(0, name,"","");
	}

	public int Dochek() {
		data = new RoleAddDal();

		int y = 0;
		try {
			y = data.AddReg(reg);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public int DochekCF() {
		data2 = new RoleAddDal();

		int y1 = 0;
		try {
			y1 = data2.RoleCF(chk);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return y1;
	}

	public void setY1(Integer y1) {
		this.y1 = y1;
	}
}
