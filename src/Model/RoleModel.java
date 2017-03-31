package Model;

import java.util.Date;

public class RoleModel {
	/*
	  Author:陈耀家
	  Create date: 09/13/2013
	  Description:角色表role的Model类
	*/
	
	private int rol_id;
	private String rol_name;
	private String rol_index;
	private String addname;
	//private Date addtime;
	
	//不带参数的构造器
	public RoleModel() {
		super();
	}

	public int getRol_id() {
		return rol_id;
	}

	public void setRol_id(int rol_id) {
		this.rol_id = rol_id;
	}

	public String getRol_name() {
		return rol_name;
	}

	public void setRol_name(String rol_name) {
		this.rol_name = rol_name;
	}

	public String getRol_index() {
		return rol_index;
	}

	public void setRol_index(String rol_index) {
		this.rol_index = rol_index;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public RoleModel(int rol_id, String rol_name, String rol_index,
			String addname) {
		super();
		this.rol_id = rol_id;
		this.rol_name = rol_name;
		this.rol_index = rol_index;
		this.addname = addname;
	}


	
}
