package Controller.EmBaseCompact;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.CI_Insurant_ListModel;
import Model.EmBaseCompactListModel;
import bll.EmBaseCompact.EmBaseCompactSA_MakeListBll;

public class EmBaseCompactSA_MakeListController {
	EmBaseCompactSA_MakeListBll bll = new EmBaseCompactSA_MakeListBll();
	
	private ListModelList<EmBaseCompactListModel> compactsabase;// 劳动合同补充协议基本信息

	private ListModelList<EmBaseCompactListModel> emcompactsalist;// 劳动合同补充协议未制作列表

	private ListModelList<EmBaseCompactListModel> autemcompactsalist;// 劳动合同补充协议未审核列表

	private ListModelList<EmBaseCompactListModel> premcompactsalist;// 劳动合同补充协议未打印列表

	private ListModelList<EmBaseCompactListModel> siemcompactsalist;// 劳动合同补充协议签回印列表

	private ListModelList<EmBaseCompactListModel> filingemcompactsalist;// 劳动合同补充协议归档印列表
	
	private String gid;
	private String cid;

	@Init
	public void init() throws SQLException {
		try {
			gid = Executions.getCurrent().getArg().get("gid").toString();
		} catch (Exception e) {
			gid = "0";
		}
		cid = "0";
		
		compactsabase = new ListModelList<EmBaseCompactListModel>(
				bll.getcompactsabase(gid));// 劳动合同补充协议基本信息

		emcompactsalist = new ListModelList<EmBaseCompactListModel>(
				bll.getemcompactsalist(""));// 劳动合同补充协议未制作列表

		autemcompactsalist = new ListModelList<EmBaseCompactListModel>(
				bll.getautemcompactsalist());// 劳动合同补充协议未审核列表

		premcompactsalist = new ListModelList<EmBaseCompactListModel>(
				bll.getpremcompactsalist());// 劳动合同补充协议未审核列表

		siemcompactsalist = new ListModelList<EmBaseCompactListModel>(
				bll.getsiemcompactsalist());// 劳动合同补充协议未审核列表

		filingemcompactsalist = new ListModelList<EmBaseCompactListModel>(
				bll.getfilingemcompactsalist());// 劳动合同补充协议归档印列表
	}
	
	// 商保审核查询
		@Command("zz_search")
		@NotifyChange("emcompactsalist")
		public void zz_search(@BindingParam("tb1") Textbox name)
				throws Exception {
			emcompactsalist = new ListModelList<EmBaseCompactListModel>(
					bll.getemcompactsalist(name.getValue()));// 劳动合同补充协议未制作列表
		}

	// 制作纸质劳动合同补充协议
	@Command("emcompact_make")
	public void compact_add(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("emba_name", emco.getName());
		arg.put("ebcc_work_place", emco.getEbco_work_place());
		arg.put("ebcc_wage", emco.getEbco_wage());
		arg.put("ebcc_working_station", emco.getEbco_working_station());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompactSA_AddDoc.zul", null, arg);
		wnd.doModal();
	}

	// 审核纸质劳动合同补充协议
	@Command("emcompact_aut")
	public void compact_aut(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompactSA_AutDoc.zul", null, arg);
		wnd.doModal();
	}

	// 打印纸质劳动合同补充协议
	@Command("emcompact_print")
	public void compact_pr(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompactSA_PrintDoc.zul", null, arg);
		wnd.doModal();
	}

	// 查看劳动合同补充协议
	@Command("emcompact_check")
	public void compact_check(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompactSA_CheckDoc.zul", null, arg);
		wnd.doModal();
	}

	// 签回劳动合同补充协议
	@Command("emcompact_sign")
	public void compact_sign(@BindingParam("emco") EmBaseCompactListModel emco) {
		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompactSA_Sign.zul", null, arg);
		wnd.doModal();
	}

	// 归档劳动合同补充协议
	@Command("emcompact_filing")
	public void emcompact_filing(
			@BindingParam("emco") EmBaseCompactListModel emco) {

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// String incept_Date = sdf.format("2013-12-12");

		Map arg = new HashMap();
		arg.put("daid", emco.getEbco_id());
		// arg.put("ebcc_incept_date", emco.getEbco_incept_date());
		// arg.put("ebcc_maturity_date", emco.getEbco_maturity_date());
		arg.put("ebcc_wage", emco.getEbco_wage());
		arg.put("ebcc_wage_gz", emco.getEbco_wage_gz());
		arg.put("ebcc_working_station", emco.getEbco_working_station());
		arg.put("id", emco.getEbco_tapr_id());
		Window wnd = (Window) Executions.createComponents(
				"../EmBaseCompact/EmBaseCompactSA_Filing.zul", null, arg);
		wnd.doModal();
	}

	public ListModelList<EmBaseCompactListModel> getEmcompactsalist() {
		return emcompactsalist;
	}

	public void setEmcompactlist(
			ListModelList<EmBaseCompactListModel> emcompactsalist) {
		this.emcompactsalist = emcompactsalist;
	}

	public ListModelList<EmBaseCompactListModel> getAutemcompactsalist() {
		return autemcompactsalist;
	}

	public void setAutemcompactsalist(
			ListModelList<EmBaseCompactListModel> autemcompactsalist) {
		this.autemcompactsalist = autemcompactsalist;
	}

	public ListModelList<EmBaseCompactListModel> getPremcompactsalist() {
		return premcompactsalist;
	}

	public void setPremcompactsalist(
			ListModelList<EmBaseCompactListModel> premcompactsalist) {
		this.premcompactsalist = premcompactsalist;
	}

	public ListModelList<EmBaseCompactListModel> getSiemcompactsalist() {
		return siemcompactsalist;
	}

	public void setSiemcompactsalist(
			ListModelList<EmBaseCompactListModel> siemcompactsalist) {
		this.siemcompactsalist = siemcompactsalist;
	}

	public ListModelList<EmBaseCompactListModel> getFilingemcompactsalist() {
		return filingemcompactsalist;
	}

	public void setFilingemcompactsalist(
			ListModelList<EmBaseCompactListModel> filingemcompactsalist) {
		this.filingemcompactsalist = filingemcompactsalist;
	}

	public ListModelList<EmBaseCompactListModel> getCompactsabase() {
		return compactsabase;
	}

	public void setCompactsabase(ListModelList<EmBaseCompactListModel> compactsabase) {
		this.compactsabase = compactsabase;
	}
}
