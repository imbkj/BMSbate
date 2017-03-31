package Controller.EmCommercialInsurance;

import impl.MessageImpl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.MessageService;

import dal.LoginDal;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmCommissionOut.EmCommissionOut_ListDal;

import bll.EmCommercialInsurance.CI_Insurant_ListBll;
import bll.EmCommercialInsurance.CI_Insurant_OperateBll;

import Model.CI_Insurant_ListModel;
import Model.EmCommissionOutChangeModel;
import Model.EmbaseGDModel;
import Model.SysMessageModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class CI_RAutInsurant_Controller {
	private ListModelList<CI_Insurant_ListModel> ci_insurant_rlist;// 显示商保未处理任务单数据

	CI_Insurant_ListBll bll = new CI_Insurant_ListBll();

	private ListModelList<CI_Insurant_ListModel> castsortlist;// 显示保险类型

	private ListModelList<CI_Insurant_ListModel> clientlist;// 显示客服

	private ListModelList<CI_Insurant_ListModel> rclientlist;// 显示中心客服

	@Init
	public void init() throws SQLException {
		ci_insurant_rlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_rlist("","","","","","","",""));// 显示未审核列表

		castsortlist = new ListModelList<CI_Insurant_ListModel>(
				bll.castsortlist());// 显示保险类型

		clientlist = new ListModelList<CI_Insurant_ListModel>(bll.clientlist());// 显示保险类型

		rclientlist = new ListModelList<CI_Insurant_ListModel>(
				bll.rclientlist());// 显示保险类型
	}

	// 商保审核查询
	@Command("search")
	@NotifyChange("ci_insurant_rlist")
	public void search(@BindingParam("castsort") Combobox castsort,
			@BindingParam("client") Combobox client,
			@BindingParam("wyclient") Combobox wyclient,
			@BindingParam("name") Textbox name,
			@BindingParam("idcard") Textbox idcard,
			@BindingParam("company") Textbox company,
			@BindingParam("state") Combobox state,
			@BindingParam("gid") Textbox gid) throws Exception {
		ci_insurant_rlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_rlist(castsort.getValue(), client.getValue(),
						wyclient.getValue(), name.getValue(),
						idcard.getValue(), company.getValue(),
						state.getValue(), gid.getValue()));// 显示未审核列表
	}

	public ListModelList<CI_Insurant_ListModel> getCi_insurant_rlist() {
		return ci_insurant_rlist;
	}

	public void setCi_insurant_rlist(
			ListModelList<CI_Insurant_ListModel> ci_insurant_rlist) {
		this.ci_insurant_rlist = ci_insurant_rlist;
	}

	// 打开信息详情
	@Command
	@NotifyChange("ci_insurant_rlist")
	public void add(@BindingParam("emco") CI_Insurant_ListModel emco)
			throws SQLException {
		String URL = "";
		Map map = new HashMap<>();

		map.put("daid", emco.getEcin_id());
		map.put("id", emco.getEcin_tapr_id());
		URL = "/EmCommercialInsurance/CI_Insurant_LinkAdd.zul";
		Window window;
		window = (Window) Executions.createComponents(URL, null, map);
		window.doModal();

		ci_insurant_rlist = new ListModelList<CI_Insurant_ListModel>(
				bll.ci_insurant_rlist("","","","","","","",""));// 显示未审核列表
	}

	public ListModelList<CI_Insurant_ListModel> getCastsortlist() {
		return castsortlist;
	}

	public void setCastsortlist(
			ListModelList<CI_Insurant_ListModel> castsortlist) {
		this.castsortlist = castsortlist;
	}

	public ListModelList<CI_Insurant_ListModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(ListModelList<CI_Insurant_ListModel> clientlist) {
		this.clientlist = clientlist;
	}

	public ListModelList<CI_Insurant_ListModel> getRclientlist() {
		return rclientlist;
	}

	public void setRclientlist(ListModelList<CI_Insurant_ListModel> rclientlist) {
		this.rclientlist = rclientlist;
	}

}
