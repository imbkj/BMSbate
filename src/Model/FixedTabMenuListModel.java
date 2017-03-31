package Model;

public class FixedTabMenuListModel {
	private int id;
	private int menu_id;
	private String username;
	public FixedTabMenuListModel()
	{}
	public FixedTabMenuListModel(int id, int menu_id, String username) {
		super();
		this.id = id;
		this.menu_id = menu_id;
		this.username = username;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
