package Model;

public class PubProvinceModel {
	private int id; // 省份表id
	private String name; // 省份名称
	private int Regionid; // 地区表id

	public PubProvinceModel(int id, String name, int regionid) {
		super();
		this.id = id;
		this.name = name;
		Regionid = regionid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRegionid() {
		return Regionid;
	}

	public void setRegionid(int regionid) {
		Regionid = regionid;
	}

}
