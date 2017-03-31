package Model;

import java.util.Date;

public class CalendarsModel {
	private int id;
	private String viin_person;
	private Date cale_begindate;
	private Date cale_enddate;
	private String cale_headcolor;
	private String cale_contentcolor;
	private String cale_headcolor1;
	private String cale_contentcolor1;
	private String cale_content;
	private Date cale_addtime;
	private String cale_addname;
	private String cale_tittle;
	public CalendarsModel(int id, Date cale_begindate, Date cale_enddate,
			String cale_headcolor, String cale_contentcolor,
			String cale_content, Date cale_addtime, String cale_addname,String cale_tittle) {
		super();
		this.id = id;
		this.cale_begindate = cale_begindate;
		this.cale_enddate = cale_enddate;
		this.cale_headcolor = cale_headcolor;
		this.cale_contentcolor = cale_contentcolor;
		this.cale_content = cale_content;
		this.cale_addtime = cale_addtime;
		this.cale_addname = cale_addname;
		this.cale_tittle=cale_tittle;
	}
	
	
	public CalendarsModel(int id, Date cale_begindate, Date cale_enddate,
			String cale_headcolor, String cale_contentcolor,
			String cale_headcolor1, String cale_contentcolor1,
			String cale_content, Date cale_addtime, String cale_addname,
			String cale_tittle) {
		super();
		this.id = id;
		this.cale_begindate = cale_begindate;
		this.cale_enddate = cale_enddate;
		this.cale_headcolor = cale_headcolor;
		this.cale_contentcolor = cale_contentcolor;
		this.cale_headcolor1 = cale_headcolor1;
		this.cale_contentcolor1 = cale_contentcolor1;
		this.cale_content = cale_content;
		this.cale_addtime = cale_addtime;
		this.cale_addname = cale_addname;
		this.cale_tittle = cale_tittle;
	}
	


	public CalendarsModel(int id, String viin_person, Date cale_begindate,
			Date cale_enddate, String cale_headcolor, String cale_contentcolor,
			String cale_headcolor1, String cale_contentcolor1,
			String cale_content, Date cale_addtime, String cale_addname,
			String cale_tittle) {
		super();
		this.id = id;
		this.viin_person = viin_person;
		this.cale_begindate = cale_begindate;
		this.cale_enddate = cale_enddate;
		this.cale_headcolor = cale_headcolor;
		this.cale_contentcolor = cale_contentcolor;
		this.cale_headcolor1 = cale_headcolor1;
		this.cale_contentcolor1 = cale_contentcolor1;
		this.cale_content = cale_content;
		this.cale_addtime = cale_addtime;
		this.cale_addname = cale_addname;
		this.cale_tittle = cale_tittle;
	}


	public CalendarsModel() {
		super();
	}
	
	public String getViin_person() {
		return viin_person;
	}


	public void setViin_person(String viin_person) {
		this.viin_person = viin_person;
	}


	public String getCale_headcolor1() {
		return cale_headcolor1;
	}

	public void setCale_headcolor1(String cale_headcolor1) {
		this.cale_headcolor1 = cale_headcolor1;
	}

	public String getCale_contentcolor1() {
		return cale_contentcolor1;
	}

	public void setCale_contentcolor1(String cale_contentcolor1) {
		this.cale_contentcolor1 = cale_contentcolor1;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCale_begindate() {
		return cale_begindate;
	}
	public void setCale_begindate(Date cale_begindate) {
		this.cale_begindate = cale_begindate;
	}
	public Date getCale_enddate() {
		return cale_enddate;
	}
	public void setCale_enddate(Date cale_enddate) {
		this.cale_enddate = cale_enddate;
	}
	public String getCale_headcolor() {
		return cale_headcolor;
	}
	public void setCale_headcolor(String cale_headcolor) {
		this.cale_headcolor = cale_headcolor;
	}
	public String getCale_contentcolor() {
		return cale_contentcolor;
	}
	public void setCale_contentcolor(String cale_contentcolor) {
		this.cale_contentcolor = cale_contentcolor;
	}
	public String getCale_content() {
		return cale_content;
	}
	public void setCale_content(String cale_content) {
		this.cale_content = cale_content;
	}
	public Date getCale_addtime() {
		return cale_addtime;
	}
	public void setCale_addtime(Date cale_addtime) {
		this.cale_addtime = cale_addtime;
	}
	public String getCale_addname() {
		return cale_addname;
	}
	public void setCale_addname(String cale_addname) {
		this.cale_addname = cale_addname;
	}
	public String getCale_tittle() {
		return cale_tittle;
	}
	public void setCale_tittle(String cale_tittle) {
		this.cale_tittle = cale_tittle;
	}
	
}
