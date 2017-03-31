package Controller.EmBaseCompact;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import dal.EmBaseCompact.EmBaseCompact_MakeListDal;
import Model.DocumentsInfoPubPicModel;
import Model.EmBaseCompactListModel;
import bll.EmBaseCompact.EmBaseCompact_MakeListBll;

public class EmBaseCompact_MakeListController {
	EmBaseCompact_MakeListBll bll = new EmBaseCompact_MakeListBll();

	private String ebcc_id;

	private ListModelList<EmBaseCompactListModel> emcompactmainlist;// 劳动合同首页列表

	private ListModelList<EmBaseCompactListModel> emcompactlist;// 劳动合同未制作列表

	private ListModelList<EmBaseCompactListModel> autemcompactlist;// 劳动合同未审核列表

	private ListModelList<EmBaseCompactListModel> premcompactlist;// 劳动合同未打印列表

	private ListModelList<EmBaseCompactListModel> siemcompactlist;// 劳动合同签回印列表

	private ListModelList<EmBaseCompactListModel> filingemcompactlist;// 劳动合同归档印列表

	private ListModelList<EmBaseCompactListModel> tempemcompactlist;// 劳动合同模板列表

	private ListModelList<EmBaseCompactListModel> tempemcompactlistall;// 劳动合同模板列表

	private ListModelList<EmBaseCompactListModel> templist;// 劳动合同模板列表

	private ListModelList<EmBaseCompactListModel> com_state;// 劳动合同是否退回

	private ListModelList<EmBaseCompactListModel> checklist;// 劳动合同未制作列表

	private ListModelList<EmBaseCompactListModel> clientlist;// 显示客服名称

	private Window win1 = (Window) Path.getComponent("win1");

	private List<DocumentsInfoPubPicModel> outcont;

	private String conname;

	private String st;

	private String st2;

	@Init
	public void init() throws SQLException {
		try {
			ebcc_id = Executions.getCurrent().getArg().get("daid").toString();
			outcont = bll.getoutcont(ebcc_id);
			setConname(outcont.get(0).getDoin_content());
		} catch (Exception e) {
			ebcc_id = "0";
		}

		try {
			st = Executions.getCurrent().getArg().get("p1").toString();
		} catch (Exception e) {
			// TODO: handle exception
		}

		try {
			emcompactmainlist = new ListModelList<EmBaseCompactListModel>(
					bll.getemcompactmainlist(st, "", ""));// 劳动合同首页列表
		} catch (Exception e) {
			// TODO: handle exception
		}

		emcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.getemcompactlist(""));// 劳动合同未制作列表

		autemcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.getautemcompactlist(""));// 劳动合同未审核列表

		premcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.getpremcompactlist());// 劳动合同未审核列表

		siemcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.getsiemcompactlist());// 劳动合同未审核列表

		filingemcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.getfilingemcompactlist());// 劳动合同归档印列表

		tempemcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.gettempemcompactlist());// 劳动合同模板列表

		tempemcompactlistall = new ListModelList<EmBaseCompactListModel>(
				bll.gettempemcompactlistall("", ""));// 劳动合同模板列表

		clientlist = new ListModelList<EmBaseCompactListModel>(
				bll.getclientlist());// 显示客服名称

		templist = new ListModelList<EmBaseCompactListModel>(bll.gettemplist());// 劳动合同模板列表

		com_state = new ListModelList<EmBaseCompactListModel>(
				bll.getcom_state(ebcc_id));// 劳动合同是否退回

		checklist = new ListModelList<EmBaseCompactListModel>(bll.getchecklist(
				"", "", "0"));// 劳动合同是否退回

	}

	@Command
	public void aw(@BindingParam("a") Window w) {
		win1 = w;
	}

	@Command("search")
	@NotifyChange("tempemcompactlistall")
	public void seach(@BindingParam("company") Textbox company,
			@BindingParam("emco_class") Combobox emco_class)
			throws SQLException {
		tempemcompactlistall = new ListModelList<EmBaseCompactListModel>(
				bll.gettempemcompactlistall(company.getValue(), emco_class
						.getSelectedItem().getValue().toString()));// 劳动合同模板列表
	}

	@Command("aut_search")
	@NotifyChange("autemcompactlist")
	public void aut_search(@BindingParam("tb1") Textbox tb1,
			@BindingParam("gid") Textbox gid, @BindingParam("company") Textbox company,
			@BindingParam("cid") Textbox cid, @BindingParam("client") Combobox client)
			throws SQLException {
		
		String str="";
		if(!tb1.getValue().equals("")){
			str=" and emba_name like '%"+tb1.getValue()+"%'";
		}
		if(!gid.getValue().equals("")){
			str=str+" and a.gid="+gid.getValue();
		}
		if(!company.getValue().equals("")){
			str=str+" and coba_company like '%"+company.getValue()+"%'";
		}
		if(!cid.getValue().equals("")){
			str=str+" and a.cid="+cid.getValue();
		}
		if(!client.getValue().equals("")){
			str=str+" and coba_client='"+client.getValue()+"'";
		}
		
		autemcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.getautemcompactlist(str));// 劳动合同未审核列表
	}

	@Command("make_search")
	@NotifyChange("emcompactlist")
	public void make_search(@BindingParam("tb1") Textbox tb1,
			@BindingParam("gid") Textbox gid, @BindingParam("company") Textbox company,
			@BindingParam("cid") Textbox cid, @BindingParam("client") Combobox client)
			throws SQLException {
		String str="";
		if(!tb1.getValue().equals("")){
			str=" and emba_name like '%"+tb1.getValue()+"%'";
		}
		if(!gid.getValue().equals("")){
			str=str+" and a.gid="+gid.getValue();
		}
		if(!company.getValue().equals("")){
			str=str+" and coba_company like '%"+company.getValue()+"%'";
		}
		if(!cid.getValue().equals("")){
			str=str+" and a.cid="+cid.getValue();
		}
		if(!client.getValue().equals("")){
			str=str+" and coba_client='"+client.getValue()+"'";
		}
		emcompactlist = new ListModelList<EmBaseCompactListModel>(
				bll.getemcompactlist(str));// 劳动合同未制作列表
	}

	@Command("qs_search")
	@NotifyChange("emcompactmainlist")
	public void qs_search(@BindingParam("name") Textbox name,
			@BindingParam("client") Combobox client) throws SQLException {
		emcompactmainlist = new ListModelList<EmBaseCompactListModel>(
				bll.getemcompactmainlist(st, name.getValue(), client.getValue()));// 劳动合同首页列表
	}

	@Command("em_search")
	@NotifyChange("checklist")
	public void em_search(@BindingParam("tb1") Textbox tb1,
			@BindingParam("ebco_maturity_date") Datebox ebco_maturity_date)
			throws SQLException {
		String em4 = "";
		if (ebco_maturity_date.getValue() != null) {
			em4 = bll.DatetoSting(ebco_maturity_date.getValue());
		}
		checklist = new ListModelList<EmBaseCompactListModel>(bll.getchecklist(
				tb1.getValue(), em4, "1"));// 劳动合同是否退回
	}

	// 制作纸质劳动合同合同
	@Command("emcompact_make")
	public void compact_add(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"EmBaseCompact_AddDoc.zul", null, arg);
		wnd.doModal();
	}

	// 审核纸质劳动合同合同
	@Command("emcompact_aut")
	public void compact_aut(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"EmBaseCompact_AutDoc.zul", null, arg);
		wnd.doModal();
	}

	// 打印纸质劳动合同合同
	@Command("emcompact_print")
	public void compact_pr(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"EmBaseCompact_PrintDoc.zul", null, arg);
		wnd.doModal();
	}

	// 查看劳动合同合同
	@Command("emcompact_check")
	public void emcompact_check(
			@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"EmBaseCompact_CheckDoc.zul", null, arg);
		wnd.doModal();
	}

	// 签回劳动合同合同
	@Command("emcompact_sign")
	public void compact_sign(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"EmBaseCompact_Sign.zul", null, arg);
		wnd.doModal();
	}

	// 归档劳动合同合同
	@Command("emcompact_filing")
	public void emcompact_filing(
			@BindingParam("emco") EmBaseCompactListModel emco) {

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String incept_Date = sdf.format("2013-12-12");

		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"EmBaseCompact_Filing.zul", null, arg);
		wnd.doModal();
	}

	// 审核纸质劳动合同合同
	@Command("tempemcompact_aut")
	public void tempemcompact_aut(
			@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"EmBaseCompact_UpAutDoc.zul", null, arg);
		wnd.doModal();
	}

	@Command("remind_st")
	@NotifyChange("emcompactmainlist")
	public void sign_st(@BindingParam("emco") EmBaseCompactListModel emco)
			throws Exception {
		EmBaseCompact_MakeListDal dal = new EmBaseCompact_MakeListDal();
		int sta = dal.remind_ok(emco.getEbco_id());

		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);

		emcompactmainlist = new ListModelList<EmBaseCompactListModel>(
				bll.getemcompactmainlist(st, "", ""));// 劳动合同首页列表
	}

	public ListModelList<EmBaseCompactListModel> getEmcompactlist() {
		return emcompactlist;
	}

	public void setEmcompactlist(
			ListModelList<EmBaseCompactListModel> emcompactlist) {
		this.emcompactlist = emcompactlist;
	}

	public ListModelList<EmBaseCompactListModel> getAutemcompactlist() {
		return autemcompactlist;
	}

	public void setAutemcompactlist(
			ListModelList<EmBaseCompactListModel> autemcompactlist) {
		this.autemcompactlist = autemcompactlist;
	}

	public ListModelList<EmBaseCompactListModel> getPremcompactlist() {
		return premcompactlist;
	}

	public void setPremcompactlist(
			ListModelList<EmBaseCompactListModel> premcompactlist) {
		this.premcompactlist = premcompactlist;
	}

	public ListModelList<EmBaseCompactListModel> getSiemcompactlist() {
		return siemcompactlist;
	}

	public void setSiemcompactlist(
			ListModelList<EmBaseCompactListModel> siemcompactlist) {
		this.siemcompactlist = siemcompactlist;
	}

	public ListModelList<EmBaseCompactListModel> getFilingemcompactlist() {
		return filingemcompactlist;
	}

	public void setFilingemcompactlist(
			ListModelList<EmBaseCompactListModel> filingemcompactlist) {
		this.filingemcompactlist = filingemcompactlist;
	}

	public ListModelList<EmBaseCompactListModel> getTempemcompactlist() {
		return tempemcompactlist;
	}

	public void setTempemcompactlist(
			ListModelList<EmBaseCompactListModel> tempemcompactlist) {
		this.tempemcompactlist = tempemcompactlist;
	}

	public ListModelList<EmBaseCompactListModel> getTemplist() {
		return templist;
	}

	public void setTemplist(ListModelList<EmBaseCompactListModel> templist) {
		this.templist = templist;
	}

	public ListModelList<EmBaseCompactListModel> getTempemcompactlistall() {
		return tempemcompactlistall;
	}

	public void setTempemcompactlistall(
			ListModelList<EmBaseCompactListModel> tempemcompactlistall) {
		this.tempemcompactlistall = tempemcompactlistall;
	}

	public ListModelList<EmBaseCompactListModel> getCom_state() {
		return com_state;
	}

	public void setCom_state(ListModelList<EmBaseCompactListModel> com_state) {
		this.com_state = com_state;
	}

	public Window getWin1() {
		return win1;
	}

	public void setWin1(Window win1) {
		this.win1 = win1;
	}

	public ListModelList<EmBaseCompactListModel> getChecklist() {
		return checklist;
	}

	public void setChecklist(ListModelList<EmBaseCompactListModel> checklist) {
		this.checklist = checklist;
	}

	public String getEbcc_id() {
		return ebcc_id;
	}

	public void setEbcc_id(String ebcc_id) {
		this.ebcc_id = ebcc_id;
	}

	public String getConname() {
		return conname;
	}

	public void setConname(String conname) {
		this.conname = conname;
	}

	public ListModelList<EmBaseCompactListModel> getEmcompactmainlist() {
		return emcompactmainlist;
	}

	public void setEmcompactmainlist(
			ListModelList<EmBaseCompactListModel> emcompactmainlist) {
		this.emcompactmainlist = emcompactmainlist;
	}

	public ListModelList<EmBaseCompactListModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(ListModelList<EmBaseCompactListModel> clientlist) {
		this.clientlist = clientlist;
	}

}
