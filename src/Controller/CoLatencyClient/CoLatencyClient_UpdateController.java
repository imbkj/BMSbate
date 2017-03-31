package Controller.CoLatencyClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import service.DataPopedomService;
import service.UserInfoService;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.SystemControl.Data_PopedomBll;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.CoLaContactsModel;
import Model.CoLatencyClientModel;
import Model.LoginModel;
import Model.PubTradeModel;

public class CoLatencyClient_UpdateController extends
		SelectorComposer<Component> implements AfterCompose {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox company;
	@Wire
	private Textbox website;
	@Wire
	private Textbox address;
	@Wire
	private Combobox clientstyle;
	@Wire
	private Combobox ifsign;
	@Wire
	private Combobox follower;
	@Wire
	private Combobox ClientArea;
	@Wire
	private Combobox clientsize;
	@Wire
	private Combobox trade;
	@Wire
	private Combobox clientsource;
	@Wire
	private Combobox slevel;
	@Wire
	private Combobox realsize;
	@Wire
	private Intbox ownyear;
	@Wire
	private Textbox servicecontent;
	@Wire
	private Textbox remark;
	@Wire
	private Vlayout notesvla;
	@Wire
	private Datebox notes;
	@Wire
	private Textbox txtnotes;
	@Wire
	private Window win;
	@Wire
	private Grid linkgd;
	@Wire
	private Combobox ssource;
	
	@Wire
	private Combobox kind;
	CoLatencyClientModel frommodel = (CoLatencyClientModel) Executions
			.getCurrent().getArg().get("cola");

	CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
	List<CoAgencyLinkmanModel> linkmodel = bll.getLinkmanForAg(frommodel
			.getCola_id());
	ListModel<CoAgencyLinkmanModel> linklist = new ListModelList<CoAgencyLinkmanModel>(
			linkmodel);;

	// 获取跟进人信息
	private DataPopedomService dps = new Data_PopedomIpml("13", "");
	private List<String> personList = bll.getpidLoginlist(); // 执行人

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		// 判断是否国内注册并赋值
		if (frommodel.getCola_sign() == 1) {
			ifsign.setSelectedIndex(1);
		} else if (frommodel.getCola_sign() == 0) {
			ifsign.setSelectedIndex(2);
		} else {
			ifsign.setValue("");
		}

		// 判断成功率并赋值
		if (frommodel.getCola_successlevel() == 1) {
			slevel.setSelectedIndex(3);
		}
		// else if(frommodel.getCola_successlevel()==2)
		// {
		// slevel.setSelectedIndex(4);
		// }
		else if (frommodel.getCola_successlevel() == 3) {
			slevel.setSelectedIndex(2);
		}
		// else if(frommodel.getCola_successlevel()==4)
		// {
		// slevel.setSelectedIndex(2);
		// }
		else if (frommodel.getCola_successlevel() == 5) {
			slevel.setSelectedIndex(1);
		} else {
			slevel.setSelectedIndex(0);
		}

		// 绑定所属行业
		CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
		List<PubTradeModel> trademodel = bll.getTradeIndo();
		Comboitem itemempty = new Comboitem();
		itemempty.setParent(trade);
		itemempty.setLabel("");
		if (!trademodel.isEmpty()) {
			for (int i = 0; i < trademodel.size(); i++) {
				Comboitem item = new Comboitem();
				item.setParent(trade);
				item.setLabel(trademodel.get(i).getName());
			}
		}

		// 绑定跟进人信息
		if (!personList.isEmpty()) {
			for (int i = 0; i < personList.size(); i++) {
				Comboitem item = new Comboitem();
				item.setValue(personList.get(i));
				item.setLabel(personList.get(i));
				item.setParent(follower);
			}
		}

		// 绑定联系记录
		List<CoLaContactsModel> colalist = bll.getCoLaContactsInfo(frommodel
				.getCola_id());
		if (!colalist.isEmpty()) {
			for (int i = 0; i < colalist.size(); i++) {
				if (i == 0) {
					notes.setValue(colalist.get(i).getClco_linktime());
					txtnotes.setValue(colalist.get(i).getClco_content());
				} else {
					Hlayout hl = new Hlayout();
					Hlayout h2 = new Hlayout();
					Hlayout h3 = new Hlayout();
					h3.setHflex("1");
					hl.setHflex("1");
					Datebox datebox = new Datebox();
					Textbox txtbox = new Textbox();
					hl.setParent(notesvla);
					h2.setParent(hl);
					h3.setParent(hl);
					datebox.setParent(h2);
					datebox.setValue(colalist.get(i).getClco_linktime());
					txtbox.setParent(h3);
					txtbox.setHflex("1");
					txtbox.setValue(colalist.get(i).getClco_content());
				}
			}
		}
		linkgd.setModel(linklist);
	}

	@Override
	public void afterCompose() {
		// TODO Auto-generated method stub

	}
	
	@Listen("onClick = #linkMan")
	public void linkMan(){
		Map colaMap = new HashMap();
		colaMap.put("model", frommodel);
		Window window = (Window) Executions.createComponents(
				"../CoLatencyClient/Cola_LinkManInfo.zul", null, colaMap);
		window.doModal();
	}

	// 更新潜在客户信息事件
	@Listen("onClick = #updatecola")
	public void updateColaClientList() {
		String strs = ifEmploy();
		if (strs == "") {
			Session session = Executions.getCurrent().getDesktop().getSession();
			UserInfoService uservice = new UserInfoImpl(session);
			String username = uservice.getUsername();
			CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
			CoLatencyClientModel model = new CoLatencyClientModel();
			model.setCola_id(frommodel.getCola_id());
			model.setCola_company(company.getValue());
			model.setCola_companytype(clientstyle.getValue());
			model.setCola_website(website.getValue());
			model.setCola_address(address.getValue());
			model.setCola_sign(Integer.parseInt((String) ifsign
					.getSelectedItem().getValue()));
			model.setCola_clientarea(ClientArea.getValue());
			model.setCola_kind(kind.getValue());
			model.setCola_clientsize(clientsize.getValue());
			model.setCola_trade(trade.getValue());
			model.setCola_clientsource(clientsource.getValue());
			model.setCola_successlevel(Integer.parseInt((String) slevel
					.getSelectedItem().getValue()));
			model.setCola_ownyear(ownyear.getValue());
			model.setCola_servicecontent(servicecontent.getValue());
			model.setCola_remark(remark.getValue());
			model.setCola_modifyname(username);
			model.setCola_follower(follower.getValue());
			model.setCola_realsize(realsize.getValue());
			model.setCola_servicessource(ssource.getValue());
			
			String str = bll.CoLatencyClient_update(model);

			// 跟新潜在客户权限
			// 潜在客户跟进人权限添加
			Data_PopedomBll ddll = new Data_PopedomBll();
			// Integer cola_id,String log_name,Integer oldlog_id,Integer cid
			ddll.Data_Popedomeditcz(frommodel.getCola_id(),
					follower.getValue(), frommodel.getCola_follower(), 0);

			// 更新联系记录
			List<Component> hl = notesvla.getChildren();
			if (!hl.isEmpty()) {
				bll.deleteCoLaContactsInfo(frommodel.getCola_id());
				for (int h = 0; h < hl.size(); h++) {
					// 获取联系时间
					Hlayout hdb = (Hlayout) (hl.get(h).getChildren().get(0));
					Datebox dbox = (Datebox) hdb.getChildren().get(0);

					// 获取联系记录内容
					Hlayout htxt = (Hlayout) (hl.get(h).getChildren().get(1));
					Textbox txtbox = (Textbox) htxt.getChildren().get(0);
					if (dbox.getValue() != null
							&& !txtbox.getValue().equals("")
							&& txtbox.getValue() != "") {
						CoLaContactsModel colamodel = new CoLaContactsModel();
						colamodel.setClco_content(txtbox.getValue());
						colamodel.setClco_addname(username);
						colamodel.setClco_calo_id(frommodel.getCola_id());
						colamodel.setClco_linktime(dbox.getValue());
						bll.addCoLaContactsInfo(colamodel);
					}
				}
			}

			if (str == "修改成功" || str.equals("修改成功")) {
				Messagebox.show(str, "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
				// Executions.sendRedirect("CoLatencyClient_InfoList.zul");
			} else {
				Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	// 判断修改的数据是否符合要求
	// 判断填写数据是否为空的方法
	private String ifEmploy() {
		String str = "";
		if (company.getValue() == "" || company.getValue().equals("")) {
			str = "公司名称不能为空,请输入公司名称";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			company.focus();
		} else if (clientstyle.getValue() == ""
				|| clientstyle.getValue().equals("")) {
			str = "请选择公司类型";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			clientstyle.focus();

		} else if (address.getValue() == "" || address.getValue().equals("")) {
			str = "请输入公司地址";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			address.focus();
		} else if (ClientArea.getValue() == ""
				|| ClientArea.getValue().equals("")) {
			str = "请选择客户位置";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			ClientArea.focus();
		} else if (kind.getValue() == null || kind.getValue().equals("")) {
			str = "请选择客户企业性质";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			kind.focus();
		} else if (clientsize.getValue() == ""
				|| clientsize.getValue().equals("")) {
			str = "请输入客户规模";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			clientsize.focus();
		} else if (trade.getValue() == "" || trade.getValue().equals("")) {
			str = "请选择所属行业";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			trade.focus();
		} else if (clientsource.getValue() == ""
				|| clientsource.getValue().equals("")) {
			str = "请选择客户来源";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			clientsource.focus();
		} else if (slevel.getValue() == "" || slevel.getValue().equals("")) {
			str = "请选择成功率";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			slevel.focus();
		} else if (ownyear.getValue() == null || ownyear.getValue().equals("")) {
			str = "请输入所属年份";
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
			ownyear.focus();
		}
		// else
		// if(servicecontent.getValue()==""||servicecontent.getValue().equals(""))
		// {
		// str="请输入客户要求服务内容";
		// Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
		// servicecontent.focus();
		// }

		return str;
	}

	public List<CoAgencyLinkmanModel> getLinkmodel() {
		return linkmodel;
	}

	public void setLinkmodel(List<CoAgencyLinkmanModel> linkmodel) {
		this.linkmodel = linkmodel;
	}

}
