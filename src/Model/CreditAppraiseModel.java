package Model;

import java.util.Date;

public class CreditAppraiseModel {
	private int crap_id;
	private int crap_crcr_id;
	private int crap_crin_id;
	private String crap_content;
	private int crap_state;
	private Double crap_point;
	private String crap_addname;
	private Date crap_addtime;
	public CreditAppraiseModel(int crap_id, int crap_crcr_id, int crap_crin_id,
			String crap_content, int crap_state, Double crap_point,
			String crap_addname, Date crap_addtime) {
		super();
		this.crap_id = crap_id;
		this.crap_crcr_id = crap_crcr_id;
		this.crap_crin_id = crap_crin_id;
		this.crap_content = crap_content;
		this.crap_state = crap_state;
		this.crap_point = crap_point;
		this.crap_addname = crap_addname;
		this.crap_addtime = crap_addtime;
	}
	public CreditAppraiseModel()
	{}
	public int getCrap_id() {
		return crap_id;
	}
	public void setCrap_id(int crap_id) {
		this.crap_id = crap_id;
	}
	public int getCrap_crcr_id() {
		return crap_crcr_id;
	}
	public void setCrap_crcr_id(int crap_crcr_id) {
		this.crap_crcr_id = crap_crcr_id;
	}
	public int getCrap_crin_id() {
		return crap_crin_id;
	}
	public void setCrap_crin_id(int crap_crin_id) {
		this.crap_crin_id = crap_crin_id;
	}
	public String getCrap_content() {
		return crap_content;
	}
	public void setCrap_content(String crap_content) {
		this.crap_content = crap_content;
	}
	public int getCrap_state() {
		return crap_state;
	}
	public void setCrap_state(int crap_state) {
		this.crap_state = crap_state;
	}
	public Double getCrap_point() {
		return crap_point;
	}
	public void setCrap_point(Double crap_point) {
		this.crap_point = crap_point;
	}
	public String getCrap_addname() {
		return crap_addname;
	}
	public void setCrap_addname(String crap_addname) {
		this.crap_addname = crap_addname;
	}
	public Date getCrap_addtime() {
		return crap_addtime;
	}
	public void setCrap_addtime(Date crap_addtime) {
		this.crap_addtime = crap_addtime;
	}
}
