package Controller.CoReg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoBase.CoBase_SelectBll;
import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_OperateBll;

import Model.CoBaseModel;
import Model.CoOnlineRegisterInfoModel;
import Util.UserInfo;

public class CoReg_TackoverEndController {
	// 页面传值
	private Integer daid;
	private Integer taprid;
	private CoOnlineRegisterInfoModel m = new CoOnlineRegisterInfoModel();
	private Date tackovertime;

	// 下拉绑定列表
	private List<String> pubindustryList = new ListModelList<>();

	private boolean wtimeVis = false;
	private boolean wtimeVis1 = false;
	private boolean wtimeVis2 = false;
	private boolean jsVis = true;

	// 特殊字段
	private String if_build = "是";
	private String if_pass = "是";
	private String if_tell = "是";
	private String if_give = "是";
	private String if_build_sign_in = "是";
	private String if_ot = "否";
	private String if_arrear = "是";
	private String if_lowest = "是";
	private String if_ar_ot = "是";
	private String if_join_ss = "否";
	private String if_happen_ld = "否";
	private String if_kid = "否";
	private String if_union = "否";
	private String if_responsebook = "否";
	private String if_sign_responsebook = "否";
	private String cori_key;
	private CoReg_ListBll bll = new CoReg_ListBll();
	// 界面传值
	private Integer cid = 0;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

	/******************* 最新修改——陈耀家 *******************************/
	private CoBaseModel model;
	private String str;
	private CoBase_SelectBll cobaSBll = new CoBase_SelectBll();
	// 用于string转换date
	private Date s_regexpires;
	private Date s_regtime;
	Date s_reglimit;
	Date s_ntregtime;
	Date s_ntlimit;
	Date s_ntdeadline;
	Date s_ltregtime;
	Date s_ltlimit;
	Date s_orregtime;
	Date s_organdeadline;

	/******************* 最新修改End——陈耀家 *******************************/
	public CoReg_TackoverEndController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
			taprid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
			setM(new CoReg_ListBll().getCoregInfo(daid));
		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
		if (m.getCori_tackovertime() != null
				&& !m.getCori_tackovertime().equals("")) {
			tackovertime = getStringtoDate(m.getCori_tackovertime());
		}

		/****************** 陈耀家 ********************/
		str = " AND a.cid=" + m.getCid();
		model = cobaSBll.getCobaseeditinfo(str).get(0);
		if (model.getCoba_regexpires() != null) {
			s_regexpires = strtodate(model.getCoba_regexpires());
		}
		if (model.getCoba_regtime() != null) {
			s_regtime = strtodate(model.getCoba_regtime());
		}
		if (model.getCoba_reglimit() != null) {
			s_reglimit = strtodate(model.getCoba_reglimit());
		}
		if (model.getCoba_orregtime() != null) {
			s_orregtime = strtodate(model.getCoba_orregtime());
		}
		if (model.getCoba_organdeadline() != null) {
			s_organdeadline = strtodate(model.getCoba_organdeadline());
		}
		m.setCori_reg_fund(model.getCoba_reg_fund());
		init();
		/*************************************************/
	}

	@Command
	public void summit(@BindingParam("win") Window win) {
		init();
		if (tackovertime != null) {
			m.setCori_tackovertime(getDatetoString(tackovertime));
			m.setCori_state("7");
			String[] str = new CoReg_OperateBll().CoRegUpdateState(m, null);
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择接管时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	/**************陈耀家*******************/
	@Command
	public void submitend(@BindingParam("win") Window win)
	{
		init();
		if (tackovertime != null) {
			m.setCori_tackovertime(getDatetoString(tackovertime));
			m.setCori_state("7");
			String[] str = new CoReg_OperateBll().CoRegUpdateState(m, null);
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择接管时间", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void init() {
		m.setCori_legal_person(model.getCoba_companymanager());
		
		m.setCori_oi_code(model.getCoba_organcode());
		m.setCoba_shortname(model.getCoba_shortname());
		m.setCid( m.getCid());
		m.setOwnmonth(sdf.format(new Date()));
		m.setCori_addname(UserInfo.getUsername());
		m.setCori_reg_transact_type("中智办理");
		m.setCori_reg_type("接管");
		setPubindustryList(bll.getPubIndustry());
	}
	
	@Command("emcount")
	@NotifyChange("com")
	public void emcount(@BindingParam("a") int a) {
		if (m.getCori_all_p() != null && m.getCori_all_p() != 0) {
			if (a == 1 && m.getCori_sz_p() != null
					&& m.getCori_sz_p() > m.getCori_all_p()) {
				m.setCori_sz_p(null);
			}
			if (a == 2 && m.getCori_foreign_p() != null
					&& m.getCori_foreign_p() > m.getCori_all_p()) {
				m.setCori_foreign_p(null);
			}
			if (a == 3) {
				m.setCori_sign_contract_p(m.getCori_all_p());
			}
			if (a == 1 && m.getCori_sz_p() != null) {
				m.setCori_foreign_p(m.getCori_all_p() - m.getCori_sz_p());
			} else if (a == 2 && m.getCori_foreign_p() != null) {
				m.setCori_sz_p(m.getCori_all_p() - m.getCori_foreign_p());
			}
		} else {
			if (a == 1 || a == 2) {
				m.setCori_sz_p(null);
				m.setCori_foreign_p(null);
			}
		}
	}
	
	@Command("wtime")
	@NotifyChange({ "wtimeVis", "wtimeVis1", "wtimeVis2" })
	public void wtime() {
		try {
			if (m.getCori_wtime_type().equals("标准工时制度")) {
				wtimeVis = true;
				wtimeVis1 = false;
				wtimeVis2 = false;
			} else if (m.getCori_wtime_type().equals("不定时工作制")) {
				wtimeVis1 = true;
				wtimeVis = false;
				wtimeVis2 = false;
			} else if (m.getCori_wtime_type().equals("综合计算工时制")) {
				wtimeVis2 = true;
				wtimeVis = false;
				wtimeVis1 = false;
			} else {
				wtimeVis = false;
				wtimeVis1 = false;
				wtimeVis2 = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


	public CoOnlineRegisterInfoModel getM() {
		return m;
	}

	public void setM(CoOnlineRegisterInfoModel m) {
		this.m = m;
	}

	public Date getTackovertime() {
		return tackovertime;
	}

	public void setTackovertime(Date tackovertime) {
		this.tackovertime = tackovertime;
	}

	/**
	 * Date转换成字符串
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getDatetoString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	// 字符串转为时间
	private Date strtodate(String str) {
		Date date = null;
		if (str != null) {
			SimpleDateFormat fromat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = fromat.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}

	/**
	 * Date转换成字符串
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getStringtoDate(String datestr) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateString = null;
		try {
			dateString = formatter.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateString;
	}

	public CoBaseModel getModel() {
		return model;
	}

	public void setModel(CoBaseModel model) {
		this.model = model;
	}

	public Date getS_regexpires() {
		return s_regexpires;
	}

	public void setS_regexpires(Date s_regexpires) {
		this.s_regexpires = s_regexpires;
	}

	public Date getS_regtime() {
		return s_regtime;
	}

	public void setS_regtime(Date s_regtime) {
		this.s_regtime = s_regtime;
	}

	public Date getS_reglimit() {
		return s_reglimit;
	}

	public void setS_reglimit(Date s_reglimit) {
		this.s_reglimit = s_reglimit;
	}

	public Date getS_ntregtime() {
		return s_ntregtime;
	}

	public void setS_ntregtime(Date s_ntregtime) {
		this.s_ntregtime = s_ntregtime;
	}

	public Date getS_ntlimit() {
		return s_ntlimit;
	}

	public void setS_ntlimit(Date s_ntlimit) {
		this.s_ntlimit = s_ntlimit;
	}

	public Date getS_ntdeadline() {
		return s_ntdeadline;
	}

	public void setS_ntdeadline(Date s_ntdeadline) {
		this.s_ntdeadline = s_ntdeadline;
	}

	public Date getS_ltregtime() {
		return s_ltregtime;
	}

	public void setS_ltregtime(Date s_ltregtime) {
		this.s_ltregtime = s_ltregtime;
	}

	public Date getS_ltlimit() {
		return s_ltlimit;
	}

	public void setS_ltlimit(Date s_ltlimit) {
		this.s_ltlimit = s_ltlimit;
	}

	public Date getS_orregtime() {
		return s_orregtime;
	}

	public void setS_orregtime(Date s_orregtime) {
		this.s_orregtime = s_orregtime;
	}

	public Date getS_organdeadline() {
		return s_organdeadline;
	}

	public void setS_organdeadline(Date s_organdeadline) {
		this.s_organdeadline = s_organdeadline;
	}

	public boolean isWtimeVis() {
		return wtimeVis;
	}

	public void setWtimeVis(boolean wtimeVis) {
		this.wtimeVis = wtimeVis;
	}
	public boolean isWtimeVis1() {
		return wtimeVis1;
	}

	public void setWtimeVis1(boolean wtimeVis1) {
		this.wtimeVis1 = wtimeVis1;
	}

	public boolean isWtimeVis2() {
		return wtimeVis2;
	}

	public void setWtimeVis2(boolean wtimeVis2) {
		this.wtimeVis2 = wtimeVis2;
	}

	public boolean isJsVis() {
		return jsVis;
	}

	public void setJsVis(boolean jsVis) {
		this.jsVis = jsVis;
	}

	public String getIf_build() {
		return if_build;
	}

	public void setIf_build(String if_build) {
		this.if_build = if_build;
	}

	public String getIf_pass() {
		return if_pass;
	}

	public void setIf_pass(String if_pass) {
		this.if_pass = if_pass;
	}

	public String getIf_tell() {
		return if_tell;
	}

	public void setIf_tell(String if_tell) {
		this.if_tell = if_tell;
	}

	public String getIf_give() {
		return if_give;
	}

	public void setIf_give(String if_give) {
		this.if_give = if_give;
	}

	public String getIf_build_sign_in() {
		return if_build_sign_in;
	}

	public void setIf_build_sign_in(String if_build_sign_in) {
		this.if_build_sign_in = if_build_sign_in;
	}

	public String getIf_ot() {
		return if_ot;
	}

	public void setIf_ot(String if_ot) {
		this.if_ot = if_ot;
	}

	public String getIf_arrear() {
		return if_arrear;
	}

	public void setIf_arrear(String if_arrear) {
		this.if_arrear = if_arrear;
	}

	public String getIf_lowest() {
		return if_lowest;
	}

	public void setIf_lowest(String if_lowest) {
		this.if_lowest = if_lowest;
	}

	public String getIf_ar_ot() {
		return if_ar_ot;
	}

	public void setIf_ar_ot(String if_ar_ot) {
		this.if_ar_ot = if_ar_ot;
	}

	public String getIf_join_ss() {
		return if_join_ss;
	}

	public void setIf_join_ss(String if_join_ss) {
		this.if_join_ss = if_join_ss;
	}

	public String getIf_happen_ld() {
		return if_happen_ld;
	}

	public void setIf_happen_ld(String if_happen_ld) {
		this.if_happen_ld = if_happen_ld;
	}

	public String getIf_kid() {
		return if_kid;
	}

	public void setIf_kid(String if_kid) {
		this.if_kid = if_kid;
	}

	public String getIf_union() {
		return if_union;
	}

	public void setIf_union(String if_union) {
		this.if_union = if_union;
	}

	public String getIf_responsebook() {
		return if_responsebook;
	}

	public void setIf_responsebook(String if_responsebook) {
		this.if_responsebook = if_responsebook;
	}

	public String getIf_sign_responsebook() {
		return if_sign_responsebook;
	}

	public void setIf_sign_responsebook(String if_sign_responsebook) {
		this.if_sign_responsebook = if_sign_responsebook;
	}

	public List<String> getPubindustryList() {
		return pubindustryList;
	}

	public void setPubindustryList(List<String> pubindustryList) {
		this.pubindustryList = pubindustryList;
	}

}
