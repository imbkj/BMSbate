package Controller.CoReg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_Bll;
import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_OperateBll;
import bll.CoReg.CoReg_SpOperateBll;

import Model.CoBaseModel;
import Model.CoOnlineRegisterInfoModel;
import Model.ResponsbilityBookModel;
import Model.ResponsibilityBookModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class ResponsbilityBookController {

	// 下拉绑定列表
	private List<String> pubindustryList = new ListModelList<>();
	private CoOnlineRegisterInfoModel com = new CoOnlineRegisterInfoModel();
	private CoBaseModel cm = new CoBaseModel();
	private boolean wtimeVis = false;
	private boolean wtimeVis1 = false;
	private boolean wtimeVis2 = false;
	private CoReg_ListBll bll = new CoReg_ListBll();
	private int daid;
	private int cid;
	private ResponsbilityBookModel rbc = new ResponsbilityBookModel();

	public ResponsbilityBookController() {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			daid = Integer.valueOf(Executions.getCurrent().getArg().get("daid")
					.toString());
		}
		if (Executions.getCurrent().getArg().get("cid") != null) {
			cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
					.toString());
		}
		// 获取行业类型下拉列表
		pubindustryList = bll.getPubIndustry();
		if (daid != 0) {
			com = bll.getCoregInfo(daid);
		} else {
			// 根据cid查询cori_id
			int coir_id = 0;
			coir_id = bll.getCori_id(cid);
			com = bll.getCoregInfo(coir_id);
		}
	}

	// 创建窗口事件
	@Command
	public void createwin(@BindingParam("win") Window win) {
		CoReg_ListBll bll = new CoReg_ListBll();
		ResponsibilityBookModel rebkmodel = bll.getResponsibilityBook(cid);
		if (rebkmodel.getRebk_id() != null) {
			if (rebkmodel.getRebk_limit() != null
					&& rebkmodel.getRebk_limit().equals("当年有效")) {
				if (rebkmodel.getRebk_signdate() != null) {
					Calendar can = Calendar.getInstance();
					int nowYear = can.YEAR;
					String[] date = rebkmodel.getRebk_signdate().split("-");
					if (date[0] != null) {
						String signYear = date[0];
						if (signYear.equals(nowYear + ""))
							;
						{
							Messagebox.show("该公司就业登记责任书在有效期内，不能办理就业登记!",
									"ERROR", Messagebox.OK, Messagebox.ERROR);
							ReUrl();
						}
					}
				}
			} else if (rebkmodel.getRebk_limit() != null
					&& rebkmodel.getRebk_limit().equals("一年有效")) {
				if (rebkmodel.getRebk_signdate() != null) {
					String[] date = rebkmodel.getRebk_signdate().split("-");
					String signdate = "";
					String signyear = "", signmonth = "";
					if (date.length > 0) {
						signyear = date[0];
						signmonth = date[1];
						signdate = date[2];
					}

					Date nowdd = new Date();
					String nowyear = "", nowmonth = "", nowdate = "";
					String nowstr = datechange(nowdd);
					String[] nowdates = nowstr.split("-");
					if (nowdates.length > 0) {
						nowyear = nowdates[0];
						nowmonth = nowdates[1];
						nowdate = nowdates[2];
					}
					Integer k = 0;//0表示有效
					Integer year = Integer.parseInt(nowyear)
							- Integer.parseInt(signyear);
					if (year <= 1)// 有效年
					{
						if (year == 1) {
							Integer month = Integer.parseInt(nowmonth)
									- Integer.parseInt(signmonth);
							if (month <= 0)// 有效月
							{
								Integer hdate = Integer.parseInt(nowdate)
										- Integer.parseInt(signdate);
								if (hdate <= 0) {
									k = 0;
								} else {
									k = 1;
								}
							} else {
								k = 1;
							}
						}
						else
						{
							k=0;
						}
					} else {
						// 无效
						k = 1;
					}
					if (k==0) {
						Messagebox.show("不能重复办理!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);
						ReUrl();
					}
				}
			} else if (rebkmodel.getRebk_limit() == null) {
				Messagebox.show("不能重复办理!", "ERROR",
						Messagebox.OK, Messagebox.ERROR);
				ReUrl();
			}
		}
	}

	private void ReUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshCoUrl("../CoReg/CoReg_List.zul");
	}

	@Command("wtime")
	@NotifyChange({ "wtimeVis", "wtimeVis1", "wtimeVis2" })
	public void wtime() {
		try {
			if (com.getCori_wtime_type().equals("标准工时制度")) {
				wtimeVis = true;
				wtimeVis1 = false;
				wtimeVis2 = false;
			} else if (com.getCori_wtime_type().equals("不定时工作制")) {
				wtimeVis1 = true;
				wtimeVis = false;
				wtimeVis2 = false;
			} else if (com.getCori_wtime_type().equals("综合计算工时制")) {
				wtimeVis2 = true;
				wtimeVis = false;
				wtimeVis1 = false;
			} else {
				wtimeVis = false;
				wtimeVis1 = false;
				wtimeVis2 = false;
			}
		} catch (Exception e) {
		}
	}

	@Command("emcount")
	@NotifyChange("com")
	public void emcount(@BindingParam("a") int a) {
		if (com.getCori_all_p() != null && com.getCori_all_p() != 0) {
			if (a == 1 && com.getCori_sz_p() != null
					&& com.getCori_sz_p() > com.getCori_all_p()) {
				com.setCori_sz_p(null);
			}
			if (a == 2 && com.getCori_foreign_p() != null
					&& com.getCori_foreign_p() > com.getCori_all_p()) {
				com.setCori_foreign_p(null);
			}
			if (a == 3) {
				com.setCori_sign_contract_p(com.getCori_all_p());
			}
			if (a == 1 && com.getCori_sz_p() != null) {
				Integer foreign_p = com.getCori_all_p() - com.getCori_sz_p();
				com.setCori_foreign_p(foreign_p);
			} else if (a == 2 && com.getCori_foreign_p() != null) {
				com.setCori_sz_p(com.getCori_all_p() - com.getCori_foreign_p());
			}
		} else {
			if (a == 1 || a == 2) {
				com.setCori_sz_p(null);
				com.setCori_foreign_p(null);
			}
		}
	}

	@Command
	public void submit(@BindingParam("win") Window win) {
		// 更新或者新增就业登记
		CoReg_Bll bll = new CoReg_Bll();
		com.setCori_state("1");
		int coriid = bll.addRbb(com);
		System.out.println(coriid);
		rbc.setRebk_cori_id(coriid);
		rbc.setCid(cid);
		rbc.setRebk_step_state(1);
		CoReg_SpOperateBll bl = new CoReg_SpOperateBll();
		String[] str = bl.addRbb(rbc, cm.getCoba_company(), coriid);
		if (str[0].equals("1")) {
			Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
					Messagebox.INFORMATION);
			// win.detach();
			RedirectUtil util = new RedirectUtil();
			util.refreshCoUrl("../CoReg/CoReg_List.zul");
		} else {
			Messagebox.show(str[1], "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

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

	public CoBaseModel getCm() {
		return cm;
	}

	public void setCm(CoBaseModel cm) {
		this.cm = cm;
	}

	public CoOnlineRegisterInfoModel getCom() {
		return com;
	}

	public void setCom(CoOnlineRegisterInfoModel com) {
		this.com = com;
	}

	public List<String> getPubindustryList() {
		return pubindustryList;
	}

	public void setPubindustryList(List<String> pubindustryList) {
		this.pubindustryList = pubindustryList;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		if (d != null && !d.equals("")) {
			date = time.format(d);
		}
		return date;
	}
}
