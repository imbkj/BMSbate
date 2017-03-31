package Model;

import java.util.Date;

public class VisitFollowModel {

	private int vifo_order;// 序号
	private int vifo_id;// 主键ID
	private String vifo_content;// 跟进事项
	private int vifo_dept_id;// 跟进部门ID
	private String vifo_name;// 跟进人
	private int vifo_state;// 跟进状态
	private Date vifo_disposetime;// 处理时间
	private String vifo_disposecontent;// 跟进处理过程
	private Date vifo_addtime;// 添加时间
	private String vifo_addname;// 添加人
	private String deptname;//跟进部门名称
	
	//关系表字段
	private int vire_viin_id;//拜访计划ID
	private int vire_vifo_id;//跟进事项ID

	public int getVifo_id() {
		return vifo_id;
	}

	public void setVifo_id(int vifo_id) {
		this.vifo_id = vifo_id;
	}

	public String getVifo_content() {
		return vifo_content;
	}

	public void setVifo_content(String vifo_content) {
		this.vifo_content = vifo_content;
	}

	public int getVifo_dept_id() {
		return vifo_dept_id;
	}

	public void setVifo_dept_id(int vifo_dept_id) {
		this.vifo_dept_id = vifo_dept_id;
	}

	public String getVifo_name() {
		return vifo_name;
	}

	public void setVifo_name(String vifo_name) {
		this.vifo_name = vifo_name;
	}

	public int getVifo_state() {
		return vifo_state;
	}

	public void setVifo_state(int vifo_state) {
		this.vifo_state = vifo_state;
	}

	public Date getVifo_disposetime() {
		return vifo_disposetime;
	}

	public void setVifo_disposetime(Date vifo_disposetime) {
		this.vifo_disposetime = vifo_disposetime;
	}

	public String getVifo_disposecontent() {
		return vifo_disposecontent;
	}

	public void setVifo_disposecontent(String vifo_disposecontent) {
		this.vifo_disposecontent = vifo_disposecontent;
	}

	public Date getVifo_addtime() {
		return vifo_addtime;
	}

	public void setVifo_addtime(Date vifo_addtime) {
		this.vifo_addtime = vifo_addtime;
	}

	public String getVifo_addname() {
		return vifo_addname;
	}

	public void setVifo_addname(String vifo_addname) {
		this.vifo_addname = vifo_addname;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public int getVire_viin_id() {
		return vire_viin_id;
	}

	public void setVire_viin_id(int vire_viin_id) {
		this.vire_viin_id = vire_viin_id;
	}

	public int getVire_vifo_id() {
		return vire_vifo_id;
	}

	public void setVire_vifo_id(int vire_vifo_id) {
		this.vire_vifo_id = vire_vifo_id;
	}

	public int getVifo_order() {
		return vifo_order;
	}

	public void setVifo_order(int vifo_order) {
		this.vifo_order = vifo_order;
	}

}
