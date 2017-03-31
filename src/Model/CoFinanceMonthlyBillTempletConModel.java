package Model;

public class CoFinanceMonthlyBillTempletConModel {
	private int id;
	private int winId;
	private String name;
	private boolean check = false;

	public CoFinanceMonthlyBillTempletConModel() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWinId() {
		return winId;
	}

	public void setWinId(int winId) {
		this.winId = winId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

}
