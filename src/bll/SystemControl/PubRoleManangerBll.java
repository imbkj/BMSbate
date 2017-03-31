package bll.SystemControl;

import java.sql.SQLException;
import java.util.List;

import Model.RoleModel;
import Model.LoginUserModel;
import dal.SystemControl.PubRoleManangerDal;

;

public class PubRoleManangerBll {
	private Integer y;
	private PubRoleManangerDal data;
	private RoleModel reg;

	private Integer y1;
	private PubRoleManangerDal data1;
	private RoleModel reg1;

	public void PubRoleManangerBllAdd(String name, int rol_id) {

		reg = new RoleModel(rol_id, name, "", "");
	}

	public void PubRoleManangerBllDel(int rol_id) {

		reg1 = new RoleModel(rol_id, "", "", "");
		
	}
	
	public int Dochek2() {
		data1 = new PubRoleManangerDal();

		int y1 = 0;
		try {

			y1 = data1.DetReg(reg1);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return y1;
	}

	public int Dochek() {
		data = new PubRoleManangerDal();

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

}
