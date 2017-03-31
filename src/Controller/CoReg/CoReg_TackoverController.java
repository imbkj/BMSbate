package Controller.CoReg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
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
import bll.CoReg.CoReg_SpOperateBll;

import Model.CoBaseModel;
import Model.CoOnlineRegisterInfoModel;
import Model.ResponsbilityBookModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class CoReg_TackoverController {
	private CoBaseModel cm = new CoBaseModel();
	private CoOnlineRegisterInfoModel com = new CoOnlineRegisterInfoModel();
	
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
	private Date s_regexpires ;
	private Date s_regtime ;
	Date s_reglimit;
	Date s_ntregtime;
	Date s_ntlimit;
	Date s_ntdeadline;
	Date s_ltregtime;
	Date s_ltlimit;
	Date s_orregtime;
	Date s_organdeadline;

	/******************* 最新修改End——陈耀家 *******************************/

	public CoReg_TackoverController() {
		try {
			cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
					.toString());
			setCm(bll.getCobaseInfo(cid));
			com.setCori_reg_fund(cm.getCoba_reg_fund());
			com.setCori_legal_person(cm.getCoba_companymanager());
			com.setCoba_shortname(cm.getCoba_shortname());
			com.setCid(cid);
			com.setOwnmonth(sdf.format(new Date()));
			com.setCori_addname(UserInfo.getUsername());
			com.setCori_reg_transact_type("中智办理");
			com.setCori_reg_type("接管");

			setPubindustryList(bll.getPubIndustry());
			
			/******************陈耀家********************/
			str = " AND a.cid=" + cid;
			model = cobaSBll.getCobaseeditinfo(str).get(0);
			if(model.getCoba_regexpires()!=null)
			{
				s_regexpires=strtodate(model.getCoba_regexpires());
			}
			if(model.getCoba_regtime()!=null)
			{
				s_regtime=strtodate(model.getCoba_regtime());
			}
			if(model.getCoba_reglimit()!=null)
			{
				s_reglimit=strtodate(model.getCoba_reglimit());
			}
			if(model.getCoba_orregtime()!=null)
			{
				s_orregtime=strtodate(model.getCoba_orregtime());
			}
			if(model.getCoba_organdeadline()!=null)
			{
				s_organdeadline=strtodate(model.getCoba_organdeadline());
			}
			/*************************************************/

		} catch (Exception e) {
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}
	
	//*******************陈耀家***********************
	@Command
	public void summit(@BindingParam("win") Window win)
	{
		if (model.getCoba_regaccount() == null
				||model.getCoba_regaccount().equals("")) {
			Messagebox.show("请输入网上登录帐号!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} 
		else if (model.getCoba_regaccountpwd() == null
				||model.getCoba_regaccountpwd().equals("")) {
			Messagebox.show("请输入网上登录密码!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			com.setCori_web_password(model.getCoba_regaccountpwd());
			fieldhandle();
			com.setCori_state("6");
			CoReg_OperateBll obll = new CoReg_OperateBll();
			String[] str = obll.CoRegTackOver_cyj(com,model);
			if(str[0]=="1")
			{
				Messagebox.show("提交成功!", "ERROR", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		
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
			// TODO: handle exception
		}
	}

	// 创建窗口事件
	@Command
	public void createwin(@BindingParam("win") Window win) {
		CoReg_ListBll bll = new CoReg_ListBll();
		if (bll.ifExists(cid, "")) {
			Messagebox.show("该公司已有立户信息!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			ReUrl();
		}
	}

	private void ReUrl() {
		RedirectUtil util = new RedirectUtil();
		util.refreshCoUrl("../CoReg/CoReg_List.zul");
	}

	@Command("response")
	@NotifyChange("jsVis")
	public void response() {
		if (if_responsebook.equals("否")) {
			jsVis = true;
		} else {
			jsVis = false;
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
				com.setCori_foreign_p(com.getCori_all_p() - com.getCori_sz_p());
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

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		try {
			if (com.getCori_web_password() == null
					|| com.getCori_web_password().equals("")) {
				Messagebox.show("请输入网上登录密码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				CoReg_OperateBll obll = new CoReg_OperateBll();
				fieldhandle();

				com.setCori_state("6");
				String[] str = obll.CoRegTackOver(com);
				if (str[0] == "1") {
					Integer id = Integer.parseInt(str[3]);

					// 添加计划生育责任书任务单
					if (com.getCori_if_sign_responsebook() == 1) {
						Integer rebk_id = bll.getRebk_id(id);
						if (rebk_id == null || rebk_id.equals(""))// 如果还没有责任书数据
						{
							CoReg_SpOperateBll bl = new CoReg_SpOperateBll();
							ResponsbilityBookModel rbc = new ResponsbilityBookModel();
							rbc.setRebk_cori_id(id);
							rbc.setCid(cid);
							rbc.setRebk_step_state(1);
							bl.addRbb(rbc, cm.getCoba_shortname(), id);
						} else {
							CoReg_SpOperateBll bl = new CoReg_SpOperateBll();
							ResponsbilityBookModel rbc = new ResponsbilityBookModel();
							rbc.setRebk_id(rebk_id);
							rbc.setRebk_cori_id(id);
							rbc.setCid(cid);
							rbc.setRebk_step_state(1);
							bl.addRbbTask(rbc, cm.getCoba_shortname(), id);
						}
					}
					Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					ReUrl();
				} else {
					Messagebox.show("提交失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}

		} catch (Exception e) {
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	}

	public void fieldhandle() {
		com.setCori_if_build(if_build.equals("否") ? 0 : 1);
		com.setCori_if_pass(if_pass.equals("否") ? 0 : 1);
		com.setCori_if_tell(if_tell.equals("否") ? 0 : 1);
		com.setCori_if_give(if_give.equals("否") ? 0 : 1);
		com.setCori_if_build_sign_in(if_build_sign_in.equals("否") ? 0 : 1);
		com.setCori_if_ot(if_ot.equals("否") ? 0 : 1);
		com.setCori_if_arrear(if_arrear.equals("否") ? 0 : 1);
		com.setCori_if_lowest(if_lowest.equals("否") ? 0 : 1);
		com.setCori_if_ar_ot(if_ar_ot.equals("否") ? 0 : 1);
		com.setCori_if_join_ss(if_join_ss.equals("否") ? 0 : 1);
		com.setCori_if_happen_ld(if_happen_ld.equals("否") ? 0 : 1);
		com.setCori_if_kid(if_kid.equals("否") ? 0 : 1);
		com.setCori_if_union(if_union.equals("否") ? 0 : 1);
		com.setCori_if_responsebook(if_responsebook.equals("否") ? 0 : 1);
		com.setCori_if_sign_responsebook(if_sign_responsebook.equals("否") ? 0
				: 1);
	}
	
	//字符串转为时间
	private Date strtodate(String str)
	{
		Date date=null;
		if(str!=null)
		{
			SimpleDateFormat fromat=new SimpleDateFormat("yyyy-MM-dd");
			try {
				date=fromat.parse(str);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}

	public CoBaseModel getCm() {
		return cm;
	}

	public void setCm(CoBaseModel cm) {
		this.cm = cm;
	}

	public boolean isWtimeVis() {
		return wtimeVis;
	}

	public void setWtimeVis(boolean wtimeVis) {
		this.wtimeVis = wtimeVis;
	}

	public CoOnlineRegisterInfoModel getCom() {
		return com;
	}

	public void setCom(CoOnlineRegisterInfoModel com) {
		this.com = com;
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
	
}
