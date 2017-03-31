package Model;

import java.util.Date;

public class CreditInfoModel {
	private int CrIn_id;
	private int CrIn_state;
	private Double CrIn_point;
	private String CrIn_addname;
	private Date CrIn_addtime;
	private int cid;
	private int cocr_id;	//不是CreditCriterion表中的字段，是关系表CocrRelation表的ID
	private int cocr_Cola_id;//不是CreditCriterion表中的字段，是关系表CocrRelation表的字段，用来连接潜在客户信息表
	public CreditInfoModel(int crIn_id, int crIn_state, Double crIn_point,
			String crIn_addname, Date crIn_addtime,int cid) {
		super();
		CrIn_id = crIn_id;
		CrIn_state = crIn_state;
		CrIn_point = crIn_point;
		CrIn_addname = crIn_addname;
		CrIn_addtime = crIn_addtime;
		this.cid=cid;
	}
	public CreditInfoModel()
	{}
	public int getCrIn_id() {
		return CrIn_id;
	}
	public void setCrIn_id(int crIn_id) {
		CrIn_id = crIn_id;
	}
	public int getCrIn_state() {
		return CrIn_state;
	}
	public void setCrIn_state(int crIn_state) {
		CrIn_state = crIn_state;
	}
	public Double getCrIn_point() {
		return CrIn_point;
	}
	public void setCrIn_point(Double crIn_point) {
		CrIn_point = crIn_point;
	}
	public String getCrIn_addname() {
		return CrIn_addname;
	}
	public void setCrIn_addname(String crIn_addname) {
		CrIn_addname = crIn_addname;
	}
	public Date getCrIn_addtime() {
		return CrIn_addtime;
	}
	public void setCrIn_addtime(Date crIn_addtime) {
		CrIn_addtime = crIn_addtime;
	}
	public int getCocr_id() {
		return cocr_id;
	}
	public void setCocr_id(int cocr_id) {
		this.cocr_id = cocr_id;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int getCocr_Cola_id() {
		return cocr_Cola_id;
	}
	public void setCocr_Cola_id(int cocr_Cola_id) {
		this.cocr_Cola_id = cocr_Cola_id;
	}
}
