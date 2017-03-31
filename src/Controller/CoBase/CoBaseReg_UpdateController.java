package Controller.CoBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoBase.CoBase_OperateBll;
import bll.CoBase.CoBase_SelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoBaseModel;
import Model.CoHousingFundModel;
import Model.CoShebaoModel;
import Util.UserInfo;

public class CoBaseReg_UpdateController {
	CoBase_OperateBll cobaBll = new CoBase_OperateBll();
	DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	CoBase_SelectBll cobaSBll = new CoBase_SelectBll();
	CoBaseModel model;
	String str;
	

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	int cid = ((CoBaseModel) Executions.getCurrent().getArg().get("model"))
			.getCid();

	// 用于string转换date
	Date s_regexpires = new Date();
	Date s_regtime = new Date();
	Date s_reglimit = new Date();
	Date s_ntregtime = new Date();
	Date s_ntlimit = new Date();
	Date s_ntdeadline = new Date();
	Date s_ltregtime = new Date();
	Date s_ltlimit = new Date();
	Date s_orregtime = new Date();
	Date s_organdeadline = new Date();
	String coba_ufclassstr="";

	// 0，1 转 是，否
	String s_gzautoemail = "";
	 String s_gzemailtype = "";

	private List<CoShebaoModel> scsbList = new ListModelList<>();
	private List<CoHousingFundModel> scohfList = new ListModelList<>();

	public CoBaseReg_UpdateController() {
		str = " AND a.cid=" + String.valueOf(cid);
		model = cobaSBll.getCobaseeditinfo(str).get(0);
		setCoba_ufclassstr(model.getCoba_ufclass());
		int m_gzautoemail = model.getCoba_gzautoemail();// ((CoBaseModel)
														// Executions.getCurrent().getArg().get("model")).getCoba_gzautoemail();
		int m_gzemailtype = model.getCoba_gzemailtype();// ((CoBaseModel)
														// Executions.getCurrent().getArg().get("model")).getCoba_gzemailtype();

		if (m_gzautoemail == 1) {
			s_gzautoemail = "是";
		} else {
			s_gzautoemail = "否";
		}
		if (m_gzemailtype == 1) {
			s_gzemailtype = "HTML";
		} else {
			s_gzemailtype = "纯文本";
		}

		// 读取model的日期数据
		String m_regexpires = model.getCoba_regexpires();// ((CoBaseModel)
															// Executions.getCurrent().getArg().get("model")).getCoba_regexpires();
		String m_regtime = model.getCoba_regtime();// ((CoBaseModel)
													// Executions.getCurrent().getArg().get("model")).getCoba_regtime();
		String m_reglimit = model.getCoba_reglimit();// ((CoBaseModel)
														// Executions.getCurrent().getArg().get("model")).getCoba_reglimit();
		String m_ntregtime = model.getCoba_ntregtime();// ((CoBaseModel)
														// Executions.getCurrent().getArg().get("model")).getCoba_ntregtime();
		String m_ntlimit = model.getCoba_ntlimit();// ((CoBaseModel)
													// Executions.getCurrent().getArg().get("model")).getCoba_ntlimit();
		String m_ntdeadline = model.getCoba_ntdeadline();// ((CoBaseModel)
															// Executions.getCurrent().getArg().get("model")).getCoba_ntdeadline();
		String m_ltregtime = model.getCoba_ltregtime();// ((CoBaseModel)
														// Executions.getCurrent().getArg().get("model")).getCoba_ltregtime();
		String m_ltlimit = model.getCoba_ltlimit();// ((CoBaseModel)
													// Executions.getCurrent().getArg().get("model")).getCoba_ltlimit();
		String m_orregtime = model.getCoba_orregtime();// ((CoBaseModel)
														// Executions.getCurrent().getArg().get("model")).getCoba_orregtime();
		String m_organdeadline = model.getCoba_organdeadline();// ((CoBaseModel)
																// Executions.getCurrent().getArg().get("model")).getCoba_organdeadline();

		// string转换date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			s_regexpires = sdf.parse(m_regexpires);
		} catch (Exception e) {
			s_regexpires = null;
		}

		try {
			s_regtime = sdf.parse(m_regtime);
		} catch (Exception e) {
			s_regtime = null;
		}

		try {
			s_reglimit = sdf.parse(m_reglimit);
		} catch (Exception e) {
			s_reglimit = null;
		}

		try {
			s_ntregtime = sdf.parse(m_ntregtime);
		} catch (Exception e) {
			s_ntregtime = null;
		}

		try {
			s_ntlimit = sdf.parse(m_ntlimit);
		} catch (Exception e) {
			s_ntlimit = null;
		}

		try {
			s_ntdeadline = sdf.parse(m_ntdeadline);
		} catch (Exception e) {
			s_ntdeadline = null;
		}

		try {
			s_ltregtime = sdf.parse(m_ltregtime);
		} catch (Exception e) {
			s_ltregtime = null;
		}

		try {
			s_ltlimit = sdf.parse(m_ltlimit);
		} catch (Exception e) {
			s_ltlimit = null;
		}

		try {
			s_orregtime = sdf.parse(m_orregtime);
		} catch (Exception e) {
			s_orregtime = null;
		}

		try {
			s_organdeadline = sdf.parse(m_organdeadline);
		} catch (Exception e) {
			s_organdeadline = null;
		}

		setScsbList(cobaSBll.getCoShebaoList(" and a.cid=" + cid + " "));
		setScohfList(cobaSBll.getCoHoList(" and a.cid=" + cid + "", false));
	}

	/**
	 * 账户详情
	 * 
	 * @param m
	 */
	@Command("detail")
	public void detail(@BindingParam("each") CoShebaoModel m) {
		String url = "/CoSocialInsurance/CoSocialInsurance_Detail.zul";
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("daid", m.getCosb_id());
		map.put("role", "qd");

		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}

	/**
	 * 弹出窗口
	 * 
	 * @param url
	 *            窗口链接
	 * @param m
	 */
	@Command
	@NotifyChange({ "scohfList" })
	public void openwin(@BindingParam("each") CoHousingFundModel m,
			@BindingParam("url") String url) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getCohf_id());
		map.put("cid", m.getCid());

		try {
			if (!url.isEmpty()) {
				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();

				// CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
				// setCohfList(bll.getCoHoList(""));
				// search();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("弹出窗口出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	@Command("updateCoBaseReg")
	public void updateCoBaseReg(
			@BindingParam("winCoBaseReg") final Window winCoBaseReg,
			@BindingParam("gd") Grid gd,
			@BindingParam("coba_regagent") Combobox coba_regagent,
			@BindingParam("coba_companymanager") Textbox coba_companymanager,
			@BindingParam("coba_address") Textbox coba_address,
			@BindingParam("coba_certificate") Combobox coba_certificate,
			@BindingParam("coba_bregid") Textbox coba_bregid,
			@BindingParam("coba_regexpires") Datebox coba_regexpires,
			@BindingParam("coba_regtime") Datebox coba_regtime,
			@BindingParam("coba_reglimit") Datebox coba_reglimit,
			@BindingParam("coba_organdeadline") Datebox coba_organdeadline,
			@BindingParam("coba_orregtime") Datebox coba_orregtime,
			@BindingParam("coba_organcode") Textbox coba_organcode,
			@BindingParam("coba_ntbank") Textbox coba_ntbank,
			@BindingParam("coba_ntaccount") Textbox coba_ntaccount,
			@BindingParam("coba_ntid") Textbox coba_ntid,
			@BindingParam("coba_ntregtime") Datebox coba_ntregtime,
			@BindingParam("coba_ntlimit") Datebox coba_ntlimit,
			@BindingParam("coba_ntdeadline") Datebox coba_ntdeadline,
			@BindingParam("coba_ltregid") Textbox coba_ltregid,
			@BindingParam("coba_ltregtime") Datebox coba_ltregtime,
			@BindingParam("coba_ltlimit") Datebox coba_ltlimit,
			@BindingParam("coba_ltstate") Combobox coba_ltstate,
			@BindingParam("coba_ltid") Textbox coba_ltid,
			@BindingParam("coba_ltdeadline") Combobox coba_ltdeadline,
			@BindingParam("coba_ltbank") Textbox coba_ltbank,
			@BindingParam("coba_ltaccount") Textbox coba_ltaccount,
			@BindingParam("coba_gtstate") Combobox coba_gtstate,
			@BindingParam("coba_gtbank") Textbox coba_gtbank,
			@BindingParam("coba_gtacc") Textbox coba_gtacc,
			@BindingParam("coba_gtto") Textbox coba_gtto,
			@BindingParam("coba_gtdeadline") Combobox coba_gtdeadline,
			@BindingParam("coba_gzemailtype") Combobox coba_gzemailtype,
			@BindingParam("coba_gzautoemaildays") Combobox coba_gzautoemaildays,
			@BindingParam("coba_gzautoemail") Combobox coba_gzautoemail,
			@BindingParam("coba_ufid") Textbox coba_ufid,
			@BindingParam("coba_ufid2") Textbox coba_ufid2,
			@BindingParam("coba_ufclass") Combobox coba_ufclass,
			@BindingParam("coba_sihospital") Textbox coba_sihospital,
			@BindingParam("coba_sihosphone") Textbox coba_sihosphone,
			@BindingParam("coba_sihosadd") Textbox coba_sihosadd,
			@BindingParam("coba_regremark") Textbox coba_regremark) {

		// Date转换成String
		String u_regexpires = "";
		String u_regtime = "";
		String u_reglimit = "";
		String u_ntregtime = "";
		String u_ntlimit = "";
		String u_ntdeadline = "";
		String u_ltregtime = "";
		String u_ltlimit = "";
		String u_organdeadline = "";
		String u_orregtime = "";

		if (coba_regexpires.getValue() != null) {
			u_regexpires = cobaBll.DatetoSting(coba_regexpires.getValue());
		}
		if (coba_regtime.getValue() != null) {
			u_regtime = cobaBll.DatetoSting(coba_regtime.getValue());
		}
		if (coba_reglimit.getValue() != null) {
			u_reglimit = cobaBll.DatetoSting(coba_reglimit.getValue());
		}
		if (coba_ntregtime.getValue() != null) {
			u_ntregtime = cobaBll.DatetoSting(coba_ntregtime.getValue());
		}
		if (coba_ntlimit.getValue() != null) {
			u_ntlimit = cobaBll.DatetoSting(coba_ntlimit.getValue());
		}
		if (coba_ntdeadline.getValue() != null) {
			u_ntdeadline = cobaBll.DatetoSting(coba_ntdeadline.getValue());
		}
		if (coba_ltregtime.getValue() != null) {
			u_ltregtime = cobaBll.DatetoSting(coba_ltregtime.getValue());
		}
		if (coba_ltlimit.getValue() != null) {
			u_ltlimit = cobaBll.DatetoSting(coba_ltlimit.getValue());
		}
		if (coba_organdeadline.getValue() != null) {
			u_organdeadline = cobaBll
					.DatetoSting(coba_organdeadline.getValue());
		}
		if (coba_orregtime.getValue() != null) {
			u_orregtime = cobaBll.DatetoSting(coba_orregtime.getValue());
		}

		// String是、否 转换 成0、1
		int u_gzautoemail = 0;
		int u_gzemailtype = 0;
		int u_gzautoemaildays = 1;
		if (!coba_gzautoemail.getValue().equals("")) {
			if (coba_gzautoemail.getValue().equals("否")) {
				u_gzautoemail = 0;
			} else if (coba_gzautoemail.getValue().equals("是")) {
				u_gzautoemail = 1;
			}
		}
		if (!coba_gzemailtype.getValue().equals("")) {
			if (coba_gzemailtype.getValue().equals("纯文本")) {
				u_gzemailtype = 0;
			} else if (coba_gzemailtype.getValue().equals("HTML")) {
				u_gzemailtype = 1;
			}
		}
		if (!coba_gzautoemaildays.getValue().equals("")) {
			u_gzautoemaildays = Integer.parseInt(coba_gzautoemaildays
					.getValue().toString());
		}

		cCoba_ufclassstr(coba_ufclass.getValue());
		
		String[] message = cobaBll.updateCoBaseReg(cid, username,
				coba_regagent.getValue(), coba_companymanager.getValue(),
				coba_address.getValue(), coba_certificate.getValue(),
				coba_bregid.getValue(), u_regexpires, u_regtime, u_reglimit,
				coba_organcode.getValue(), u_orregtime, u_organdeadline,
				coba_ntid.getValue(), u_ntregtime, u_ntlimit,
				coba_ntbank.getValue(), coba_ntaccount.getValue(),
				u_ntdeadline, coba_ltregid.getValue(), u_ltregtime, u_ltlimit,
				coba_ltstate.getValue(), coba_ltid.getValue(),
				coba_ltdeadline.getValue(), coba_ltbank.getValue(),
				coba_ltaccount.getValue(), coba_gtstate.getValue(),
				coba_gtbank.getValue(), coba_gtacc.getValue(),
				coba_gtto.getValue(), coba_gtdeadline.getValue(),
				u_gzautoemail, u_gzautoemaildays, u_gzemailtype,
				coba_ufclassstr, coba_ufid.getValue(),
				coba_ufid2.getValue(), coba_sihospital.getValue(),
				coba_sihosphone.getValue(), coba_sihosadd.getValue(),
				coba_regremark.getValue());

		// 调用内联页方法chkHaveTo(Grid gd)
		String dtid = String.valueOf(cid);
		try {
			String[] d_message = docOC.UpsubmitDoc(gd, dtid);
		} catch (Exception e) {
			Messagebox.show("材料数据更新失败。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

		// 弹出提示并跳转页面
		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// 跳转页面
						// Executions.sendRedirect(".zul");
						//
						// winCoBaseReg.detach();
						model = cobaSBll.getCobaseeditinfo(str).get(0);
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getCoba_ufclassstr() {
	
		return coba_ufclassstr;
	}
	
	public String cCoba_ufclassstr(String u) {
		if (!u.equals("")&&u.equals("AF"))
		{
			coba_ufclassstr="224105";
		}
		else
		{
			coba_ufclassstr="224106";
		}
		
		return coba_ufclassstr;
	}


	
	public void setCoba_ufclassstr(String coba_ufclassstr) {
		
		if (coba_ufclassstr==null || coba_ufclassstr.equals("224106"))
		{
			coba_ufclassstr="FS";
		}
		else
		{
			coba_ufclassstr="AF";
		}
		
		
		this.coba_ufclassstr = coba_ufclassstr;
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

	public String getS_gzautoemail() {
		return s_gzautoemail;
	}

	public void setS_gzautoemail(String s_gzautoemail) {
		this.s_gzautoemail = s_gzautoemail;
	}

	public String getS_gzemailtype() {
		return s_gzemailtype;
	}

	public void setS_gzemailtype(String s_gzemailtype) {
		this.s_gzemailtype = s_gzemailtype;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public CoBaseModel getModel() {
		return model;
	}

	public void setModel(CoBaseModel model) {
		this.model = model;
	}

	public List<CoShebaoModel> getScsbList() {
		return scsbList;
	}

	public void setScsbList(List<CoShebaoModel> scsbList) {
		this.scsbList = scsbList;
	}

	public List<CoHousingFundModel> getScohfList() {
		return scohfList;
	}

	public void setScohfList(List<CoHousingFundModel> scohfList) {
		this.scohfList = scohfList;
	}

}
