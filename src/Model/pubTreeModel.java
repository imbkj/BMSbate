package Model;

public class pubTreeModel {
	  /// ID
    private int  id;
          /// PID
    private int  pid;
          /// name
    private String  name;
          /// url
    private String  url;
          /// FinanceUrl
    private String  financeurl;
          /// level
    private int  level;
          /// target
    private String  target;
    
	public pubTreeModel() {
		super();
	}
	public pubTreeModel(int id, int pid, String name, String url,
			String financeurl, int level, String target) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.url = url;
		this.financeurl = financeurl;
		this.level = level;
		this.target = target;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFinanceurl() {
		return financeurl;
	}
	public void setFinanceurl(String financeurl) {
		this.financeurl = financeurl;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
    
    

}
