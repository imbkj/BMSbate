package Model;

public class Task {
	private String taskname;
	private String state;
	public Task(String taskname, String state) {
		super();
		this.taskname = taskname;
		this.state = state;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
