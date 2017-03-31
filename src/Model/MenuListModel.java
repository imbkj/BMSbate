package Model;

public class MenuListModel {
	/*
	  Author:陈耀家
	  Create date: 09/11/2013
	  Description:菜单表menulist的Model类
	*/
	private int meu_id; //菜单ID
	private String meu_name; //菜单
	private String meu_url; //菜单url
	private int meu_pid; //菜单的父ID
	private String meu_imgurl; //菜单的url
	private String meu_remark; //备注
	private int meu_orderid; //菜单排序
	private String meu_target;//打开方式
	private Integer menu_flag;
	
	//父菜单名称在menulist表是没有这个字段的，是因为我要显示父菜单，所以添加在这里，
	//没有把他添加到构造器里面,如果只是查询menulist表可以忽略
	private String meu_parentname;//菜单的父菜单名称
	public MenuListModel(int meu_id, String meu_name, String meu_url,
			int meu_pid) {
		super();
		this.meu_id = meu_id;
		this.meu_name = meu_name;
		this.meu_url = meu_url;
		this.meu_pid = meu_pid;
	} 
	
	public MenuListModel(int meu_id, String meu_name, String meu_url,
			int meu_pid, String meu_imgurl, String meu_remark, int meu_orderid,String meutarget) {
		super();
		this.meu_id = meu_id;
		this.meu_name = meu_name;
		this.meu_url = meu_url;
		this.meu_pid = meu_pid;
		this.meu_imgurl = meu_imgurl;
		this.meu_remark = meu_remark;
		this.meu_orderid = meu_orderid;
		this.meu_target=meutarget;
	}

	public MenuListModel() {
		super();
	}
	public int getMeu_id() {
		return meu_id;
	}
	public void setMeu_id(int meu_id) {
		this.meu_id = meu_id;
	}
	public String getMeu_name() {
		return meu_name;
	}
	public void setMeu_name(String meu_name) {
		this.meu_name = meu_name;
	}
	public String getMeu_url() {
		return meu_url;
	}
	public void setMeu_url(String meu_url) {
		this.meu_url = meu_url;
	}
	public int getMeu_pid() {
		return meu_pid;
	}
	public void setMeu_pid(int meu_pid) {
		this.meu_pid = meu_pid;
	}
	public String getMeu_imgurl() {
		return meu_imgurl;
	}
	public void setMeu_imgurl(String meu_imgurl) {
		this.meu_imgurl = meu_imgurl;
	}
	public String getMeu_remark() {
		return meu_remark;
	}
	public void setMeu_remark(String meu_remark) {
		this.meu_remark = meu_remark;
	}
	public int getMeu_orderid() {
		return meu_orderid;
	}
	public void setMeu_orderid(int meu_orderid) {
		this.meu_orderid = meu_orderid;
	}

	public String getMeu_target() {
		return meu_target;
	}

	public void setMeu_target(String meu_target) {
		this.meu_target = meu_target;
	}

	public String getMeu_parentname() {
		return meu_parentname;
	}

	public void setMeu_parentname(String meu_parentname) {
		this.meu_parentname = meu_parentname;
	}

	public Integer getMenu_flag() {
		return menu_flag;
	}

	public void setMenu_flag(Integer menu_flag) {
		this.menu_flag = menu_flag;
	}
	
}
