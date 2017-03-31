package Model;

//工资单发送类型实体
public class SalarySendTypeModel {
	
	

	private int cost_id;
	private String cost_name;
	private String cost_url;
	private int cost_state; // 状态 用于分辨发送类型的多选和单选

	public int getCost_state() {
		return cost_state;
	}

	public void setCost_state(int cost_state) {
		this.cost_state = cost_state;
	}

	public String getCost_url() {
		return cost_url;
	}

	public void setCost_url(String cost_url) {
		this.cost_url = cost_url;
	}

	public int getCost_id() {
		return cost_id;
	}

	public void setCost_id(int cost_id) {
		this.cost_id = cost_id;
	}

	public String getCost_name() {
		return cost_name;
	}

	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}
}
