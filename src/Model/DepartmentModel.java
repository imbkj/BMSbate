package Model;

public class DepartmentModel {
	private int dep_id;
	private String dep_name;
	private String dep_addname;
	private String dep_addtime;
	private int dep_pid;
	private int dep_order;

	public DepartmentModel() {
		super();
	}

	public int getDep_id() {
		return dep_id;
	}

	public void setDep_id(int dep_id) {
		this.dep_id = dep_id;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getDep_addname() {
		return dep_addname;
	}

	public void setDep_addname(String dep_addname) {
		this.dep_addname = dep_addname;
	}

	public String getDep_addtime() {
		return dep_addtime;
	}

	public void setDep_addtime(String dep_addtime) {
		this.dep_addtime = dep_addtime;
	}

	public int getDep_pid() {
		return dep_pid;
	}

	public void setDep_pid(int dep_pid) {
		this.dep_pid = dep_pid;
	}
	
	public int getDep_order() {
		return dep_order;
	}

	public void setDep_order(int dep_order) {
		this.dep_order = dep_order;
	}

	public DepartmentModel(int dep_id, String dep_name, String dep_addname,
			String dep_addtime, int dep_pid) {
		super();
		this.dep_id = dep_id;
		this.dep_name = dep_name;
		this.dep_addname = dep_addname;
		this.dep_addtime = dep_addtime;
		this.dep_pid = dep_pid;
	}

}
