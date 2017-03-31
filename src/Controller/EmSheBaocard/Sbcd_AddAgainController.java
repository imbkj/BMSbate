package Controller.EmSheBaocard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoCardInfoModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Util.UserInfo;
import bll.EmSheBaocard.EmShebaoCardInfoOperateBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_AddAgainController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmShebaoCardInfoSelectBll bll = new EmShebaoCardInfoSelectBll();
	private List<EmShebaoCardInfoModel> list = bll
			.getEmShebaoCardInfoList(" and sbcd_id=" + id);
	private EmShebaoCardInfoModel m = new EmShebaoCardInfoModel();

	private List<PubCodeConversionModel> classlist = bll
			.getPubCodeConversionList(36, "证件类型");// 证件类型
	private List<PubCodeConversionModel> edulist = bll
			.getPubCodeConversionList(36, "文化程度");// 文化程度
	private List<PubCodeConversionModel> marylist = bll
			.getPubCodeConversionList(36, "婚姻状况");// 婚姻状况
	private List<PubCodeConversionModel> hjlist = bll.getPubCodeConversionList(
			36, "户口性质");// 户口性质
	private List<PubCodeConversionModel> joblist = bll
			.getPubCodeConversionList(36, "职业性质");// 职业

	private List<PubCodeConversionModel> folklist = bll.getFolkList("");// 民族
	private List<PubCodeConversionModel> provlist = bll.getPubProvinceList("");// 联系地址省
	private List<PubCodeConversionModel> citylist = bll.getCityList("");// 联系地址城市
	private List<PubCodeConversionModel> hjcitylist = bll.getCityList("");// 户籍城市
	private Date birthday, startdate, enddate;

	public Sbcd_AddAgainController() {
		if (list.size() > 0) {
			m = list.get(0);
			if (m.getSbcd_province() == null || m.getSbcd_province().equals("")) {
				m.setSbcd_province("广东");
				m.setSbcd_city("深圳");
			}
			birthday = StrToDate(m.getSbcd_birthday());
			startdate = StrToDate(m.getSbcd_idcardstartdate());
			enddate = StrToDate(m.getSbcd_idcardenddate());
		}
	}

	// 服务中心提交（状态：客服核收）
	@Command
	public void summit(@BindingParam("gd") final Grid gd,
			@BindingParam("win") Window win) {
		if (m.getSbcd_content() != null && !m.getSbcd_content().equals("")) {
			EmShebaoCardInfoOperateBll obll = new EmShebaoCardInfoOperateBll();
			if (birthday != null) {
				m.setSbcd_birthday(DateToStr(birthday));
			}
			if (startdate != null) {
				m.setSbcd_idcardstartdate(DateToStr(startdate));
			}
			if (enddate != null) {
				m.setSbcd_idcardenddate(DateToStr(enddate));
			}
			if (m.getSbcd_idcardclass() != null
					&& !m.getSbcd_idcardclass().equals("")) {
				String a[] = m.getSbcd_idcardclass().split("\\.");
				if (a.length > 1) {
					m.setSbcd_idcardclass(a[1]);
				}
			}
			if (m.getSbcd_education() != null
					&& !m.getSbcd_education().equals("")) {
				String a[] = m.getSbcd_education().split("\\.");
				if (a.length > 1) {
					m.setSbcd_education(a[1]);
				}
			}
			if (m.getSbcd_marry() != null && !m.getSbcd_marry().equals("")) {
				String a[] = m.getSbcd_marry().split("\\.");
				if (a.length > 1) {
					m.setSbcd_marry(a[1]);
				}
			}
			if (m.getSbcd_hj() != null && !m.getSbcd_hj().equals("")) {
				String a[] = m.getSbcd_hj().split("\\.");
				if (a.length > 1) {
					m.setSbcd_hj(a[1]);
				}
			}
			if (m.getSbcd_job() != null && !m.getSbcd_job().equals("")) {
				String a[] = m.getSbcd_job().split("\\.");
				if (a.length > 1) {
					m.setSbcd_job(a[1]);
				}
			}
			m.setSbcd_stateid(15);
			String[] str = obll.EmShebaoCardAddAgain(m);
			if (str[0] == "1") {
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				try {
					String[] message = docOC.UpsubmitDoc(gd, m.getSbcd_id()
							+ "");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择办理类型", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 联系地址省选择事件
	@Command
	@NotifyChange("citylist")
	public void changeaddcity(@BindingParam("val") String val) {
		if (val != null && !val.equals("")) {
			citylist = bll.getCityList(" and b.name='" + val + "'");
		}
	}

	// 户籍省选择事件
	@Command
	@NotifyChange("hjcitylist")
	public void changehjcity(@BindingParam("val") String val) {
		if (val != null && !val.equals("")) {
			hjcitylist = bll.getCityList(" and b.name='" + val + "'");
		}
	}

	// 根据有效期的开始日期计算有效期终止日期
	@Command
	@NotifyChange("enddate")
	public void changedate() {
		if (startdate != null) {
			String str = DateToStr(startdate);
			if (str != null && !str.equals("")) {
				String a[] = str.split("-");
				int year = Integer.parseInt(a[0]) + 10;
				String en = year + "-" + a[1] + "-" + a[2];
				enddate = StrToDate(en);
			}
		}
	}

	// 查询并判断是否是中行长城支行或者建行国会支行
	private boolean ifCenterMakeCard() {
		boolean flag = false;
		if (m != null) {
			if (m.getSbcd_upbankname() != null) {
				if (m.getSbcd_upbankname().equals("中行长城支行")
						|| m.getSbcd_upbankname().contains("国会支行")) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}

	/**
	 * 日期转换成Long
	 * 
	 * @param date
	 * @return str
	 */
	public static long DateToLong(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		long str = 0;
		if (date != null) {
			str = Long.parseLong(format.format(date));
		}
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (str != null && !str.equals("")) {
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public EmShebaoCardInfoModel getM() {
		return m;
	}

	public void setM(EmShebaoCardInfoModel m) {
		this.m = m;
	}

	public List<PubCodeConversionModel> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<PubCodeConversionModel> classlist) {
		this.classlist = classlist;
	}

	public List<PubCodeConversionModel> getEdulist() {
		return edulist;
	}

	public void setEdulist(List<PubCodeConversionModel> edulist) {
		this.edulist = edulist;
	}

	public List<PubCodeConversionModel> getMarylist() {
		return marylist;
	}

	public void setMarylist(List<PubCodeConversionModel> marylist) {
		this.marylist = marylist;
	}

	public List<PubCodeConversionModel> getHjlist() {
		return hjlist;
	}

	public void setHjlist(List<PubCodeConversionModel> hjlist) {
		this.hjlist = hjlist;
	}

	public List<PubCodeConversionModel> getJoblist() {
		return joblist;
	}

	public void setJoblist(List<PubCodeConversionModel> joblist) {
		this.joblist = joblist;
	}

	public List<PubCodeConversionModel> getFolklist() {
		return folklist;
	}

	public void setFolklist(List<PubCodeConversionModel> folklist) {
		this.folklist = folklist;
	}

	public List<PubCodeConversionModel> getProvlist() {
		return provlist;
	}

	public void setProvlist(List<PubCodeConversionModel> provlist) {
		this.provlist = provlist;
	}

	public List<PubCodeConversionModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<PubCodeConversionModel> citylist) {
		this.citylist = citylist;
	}

	public List<PubCodeConversionModel> getHjcitylist() {
		return hjcitylist;
	}

	public void setHjcitylist(List<PubCodeConversionModel> hjcitylist) {
		this.hjcitylist = hjcitylist;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
}
