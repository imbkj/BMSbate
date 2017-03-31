package Controller.CoCompact.CoQuotation;

import impl.UserInfoImpl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.UserInfoService;

import bll.CoQuotation.CoQuotation_AddBll;
import bll.CoProduct.cpd_addBll;

import Controller.SystemControl.Menu_TreeController;
import Model.CoLatencyClientModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoPFeeclassModel;
import Model.CoProductModel;

public class CoQuotation_AddController extends SelectorComposer<Component> {

	private List<String> classList;
	private static List<String> cityList;
	private ListModelList<CoOfferListModel> coproductList;
	private ListModelList<String> fpclassList = new ListModelList<String>();
	private List<CoPFeeclassModel> feeclassList;
	private ListModelList<CoOfferListModel> standardList;
	private List<String> shangbaoList = new ListModelList<String>();
	private List<String> amountList = new ListModelList<String>();
	private List<CoOfferListModel> coofferList = new ListModelList<CoOfferListModel>();
	int a = 1;
	CoLatencyClientModel colaModel = (CoLatencyClientModel) Executions
			.getCurrent().getArg().get("model");
	int cola_id = colaModel.getCola_id();

	// 获取session，实例化UserInfoService接口
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);

	@Wire
	private Combobox coof_name;// 报价单名称
	@Wire
	private Combobox coof_quotemode;// 报价方式
	@Wire
	private Datebox coof_quotetime;// 报价时间
	@Wire
	private Combobox coof_compacttype;// 合同类型
	@Wire
	private Radiogroup coof_register;// 是否国内注册
	@Wire
	private Combobox coli_pclass;// 产品类型
	@Wire
	private Combobox coli_city;// 适用地
	@Wire
	private Combobox copr_id;// 产品id
	@Wire
	private Doublebox coli_fee;// 收费金额
	@Wire
	private Combobox coli_cpfc_id;// 收费单位
	@Wire
	private Combobox coli_amount;// 份数
	@Wire
	private Datebox coli_starttime;// 收费起始日
	@Wire
	private Combobox fpclass;// 收费方式
	@Wire
	private Combobox coli_standard;// 享受方式
	@Wire
	private Button pSet;// 享受条件设置
	@Wire
	private Intbox coli_min;// 人数规模下限
	@Wire
	private Intbox coli_max;// 人数规模上限
	@Wire
	private Textbox coli_remark;// 说明
	@Wire
	private Textbox coli_content;// 描述
	@Wire
	private Radiogroup coli_gather;// 是否汇总项目
	@Wire
	private Button add;// 添加按钮
	@Wire
	private Grid gd;// 已选项目列表
	@Wire
	private Listbox modelistbox;//享受条件
	@Wire
	private Window win;// 窗体

	// 初始化
	public CoQuotation_AddController() throws Exception{
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		cpd_addBll bll1 = new cpd_addBll();
		setClassList(new ListModelList<String>(bll.getClassList()));
		setCityList(bll.getCityList());
		setFeeclassList(bll1.geteclass());
	}

	// 改变产品类型或者适用地时调整其他控件状态
	@Listen("onChange = #coli_pclass,#coli_city")
	public void GetProduct() throws Exception {
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		String pclass = coli_pclass.getValue();
		String city = coli_city.getValue();
		if (!pclass.isEmpty() && !city.isEmpty()) {
			city = city.substring(1);
			if (!city.isEmpty()) {
				city = city.equals("深圳") ? city : "外地";
				setCoproductList(new ListModelList<CoOfferListModel>(
						bll.getCoProductList(pclass, city)));
				copr_id.setModel(coproductList);
				copr_id.setValue("");
			}
		} else {
			setCoproductList(null);
			copr_id.setModel(coproductList);
			copr_id.setValue("");
		}
		SetProduct();
	}

	// 改变产品名称时调整其他控件状态
	@Listen("onChange = #copr_id")
	public void SetProduct() throws Exception {
		if (!copr_id.getValue().isEmpty()) {
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			int coprid = ((CoOfferListModel) copr_id.getSelectedItem()
					.getValue()).getColi_copr_id();
			String content = ((CoOfferListModel) copr_id.getSelectedItem()
					.getValue()).getColi_content();
			// 享受条件设置
			if (coli_pclass.getValue().equals("员工福利")) {
				pSet.setVisible(true);
			} else {
				pSet.setVisible(false);
			}

			// 收费方式
			if (coli_pclass.getValue().equals("档案")) {
				fpclass.setDisabled(false);
				fpclassList.clear();
				fpclassList.add("");
				fpclassList.add("按实际收取");
				fpclassList.add("全部收取");
				fpclass.setModel(fpclassList);
			} else if (coli_pclass.getValue().equals("商业保险")) {
				fpclass.setDisabled(false);
				fpclassList.clear();
				fpclassList.add("");
				fpclassList.add("按年收费，员工离职需核算是否退费");
				fpclassList.add("按月收费，员工离职需核算是否补费");
				fpclassList.add("按月收费，员工离职无需核算是否补费");
				fpclass.setModel(fpclassList);
			} else {
				fpclass.setDisabled(true);
				fpclassList.clear();
				fpclass.setModel(fpclassList);
			}

			// 收费单位
			coli_cpfc_id.setDisabled(false);
			coli_cpfc_id.setValue("");
			// 收费金额
			coli_fee.setValue(0);
			// 享受方式
			setStandardList(new ListModelList<CoOfferListModel>(
					bll.getStandardList(coprid)));
			// 份数
			if (coli_pclass.getValue().equals("商业保险")) {
				int amount = 1;
				amountList.clear();
				for (int i = 0; i < amount; i++) {
					amountList.add(i + 1 + "份");
				}
				coli_amount.setModel(new ListModelList<String>(amountList));
				coli_amount.setVisible(true);
				coli_amount.setValue("");
			} else {
				coli_amount.setVisible(false);
				coli_amount.setValue("");
			}
			// 描述
			coli_content.setValue(content);
			if (standardList.size() > 0) {
				coli_standard.setModel(standardList);
				coli_standard.setDisabled(false);
				coli_standard.setValue(standardList.get(0).getColi_standard());
			} else {
				coli_standard.setValue("");
				coli_standard.setDisabled(true);
			}
		} else {
			coli_cpfc_id.setDisabled(true);
			coli_cpfc_id.setValue("");
			coli_fee.setValue(0);
			coli_fee.setReadonly(true);
			coli_standard.setValue("");
			coli_standard.setDisabled(true);
			coli_amount.setVisible(false);
			coli_amount.setValue("");
		}
	}

	// 改变收费单位时调整收费金额控件的状态
	@Listen("onChange = #coli_cpfc_id")
	public void getFee() throws Exception {
		if (!coli_cpfc_id.getValue().isEmpty()) {
			CoQuotation_AddBll bll = new CoQuotation_AddBll();
			int id1 = ((CoOfferListModel) copr_id.getSelectedItem().getValue())
					.getColi_copr_id();
			int id2 = ((CoPFeeclassModel) coli_cpfc_id.getSelectedItem()
					.getValue()).getCpfc_id();
			CoProductModel model = new CoProductModel();
			model = bll.getFee(id1, id2,coli_city.getValue().substring(1));
			coli_fee.setValue(model.getFee() == null ? 0.0 : model.getFee().doubleValue());
			if (model.getCpfr_lock() == 0) {
				coli_fee.setReadonly(false);
			} else {
				coli_fee.setReadonly(true);
			}
		}
	}

	// 将收费方式添加到说明中
	@Listen("onChange = #fpclass")
	public void setRemark() {
		if (!fpclass.getValue().isEmpty()) {
			coli_remark.setValue(fpclass.getValue() + "；");
		} else {
			coli_remark.setValue("");
		}
	}
	
	//享受条件提交
	@Listen("onClick = #modesubmit")
	public void modesubmit(){
		alert("aaa");
	}

	// 点击添加按钮
	@Listen("onClick = #add")
	public void add() {
		CoOfferListModel model = new CoOfferListModel();
		try {
			// 参数处理
			int amount = coli_amount.getValue().isEmpty() ? 1 : Integer
					.parseInt(coli_amount.getValue().replace("份", ""));
			int gather = coli_gather.getSelectedItem().getLabel().equals("是") ? 1
					: 0;
			int min = coli_min.getValue() == null ? 0 : coli_min.getValue();
			int max = coli_max.getValue() == null ? 0 : coli_max.getValue();
			String gmStr = "";
			if (min == 0 && max != 0) {
				gmStr = max + "人以内";
			} else if (min != 0 && max == 0) {
				gmStr = "大于" + min + "人";
			} else if (min != 0 && max != 0) {
				gmStr = min + "人到" + max + "人";
			}

			// 将页面的值放入model
			model.setColi_pclass(coli_pclass.getValue());
			model.setColi_city(coli_city.getValue().substring(1));
			model.setColi_name(copr_id.getValue());
			model.setColi_copr_id(((CoOfferListModel) copr_id.getSelectedItem()
					.getValue()).getColi_copr_id());
			model.setColi_cpfc_id(((CoPFeeclassModel) coli_cpfc_id
					.getSelectedItem().getValue()).getCpfc_id());
			model.setCpfc_name(coli_cpfc_id.getValue());
			model.setColi_fee(coli_fee.getValue());
			model.setColi_standard(coli_standard.getValue());
			model.setColi_amount(amount);
			model.setColi_remark(coli_remark.getValue());
			model.setColi_content(coli_content.getValue());
			model.setColi_gather(gather);
			model.setColi_discount(1);
			model.setColi_state(1);
			model.setColi_addname(user.getUsername());
			model.setA(a);
			a++;

			coofferList.add(model);
			setCoofferList(coofferList);
			gd.setModel((ListModelList<CoOfferListModel>) coofferList);
		} catch (Exception e) {
			Messagebox.show("添加失败，原因：缺少必要数据!","ERROR",Messagebox.OK,Messagebox.ERROR);
		}
		
		
		//控件处理
		coli_min.setValue(0);
		coli_max.setValue(0);

		if (coli_pclass.getValue().equals("商业保险")
				&& copr_id.getValue().substring(0, 1).equals("P")) {
			if (!shangbaoList.contains(copr_id.getValue().substring(0, 2))) {
				shangbaoList.add(copr_id.getValue().substring(0, 2));
				// System.out.println(shangbaoList);
			}
		}
	}
	
	//删除已添加的服务产品
	@Command("delete")
	public void delete(@BindingParam("model") CoOfferListModel model,
			@BindingParam("list") List<CoOfferListModel> list,
			@BindingParam("gd") Grid gd){
		setCoofferList(list);
		coofferList.remove(model);
		gd.setModel((ListModelList<CoOfferListModel>)coofferList);
	}

	@Listen("onClick = #submit")
	public void submit() throws Exception {
		CoOfferModel ofmodel = new CoOfferModel();
		CoQuotation_AddBll bll = new CoQuotation_AddBll();
		int row = 0;
		if (coof_name.getValue().isEmpty()) {
			Messagebox.show("请输入报价单名称!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else if (coof_compacttype.getValue().isEmpty()) {
			Messagebox.show("请选择合同类型!", "ERROR", Messagebox.YES,
					Messagebox.ERROR);
		} else {

			// 参数处理
			int register = 0;
			if (coof_register.getSelectedItem().getLabel().equals("是")) {
				register = 1;
			}

			ofmodel.setCoof_name(coof_name.getValue());
			ofmodel.setCoof_quotemode(coof_quotemode.getValue());
			ofmodel.setCoof_quotetime(coof_quotetime.getValue());
			ofmodel.setCoof_compacttype(coof_compacttype.getValue());
			ofmodel.setCoof_register(register);
			ofmodel.setCoof_addname(user.getUsername());
			ofmodel.setCoof_cola_id(cola_id);

			ofmodel = bll.CoOfferAdd(ofmodel);
			row = ofmodel.getRow();

			int count = coofferList.size();
			for (int i = 0; i < count; i++) {
				CoOfferListModel model1 = new CoOfferListModel();
				model1 = coofferList.get(i);
				model1.setColi_coof_id(ofmodel.getCoof_id());
				model1 = bll.CoOfferListAdd(model1);
				row += model1.getRow();
			}

			if (row == count + 1) {
				if (Messagebox.show("提交成功!", "INFORMATION", Messagebox.YES,
						Messagebox.INFORMATION) == Messagebox.YES) {
					win.detach();
				}
			} else if (row == 1) {
				Messagebox.show("报价单项目提交出错,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			} else {
				Messagebox.show("提交失败,请联系IT部门!", "ERROR", Messagebox.YES,
						Messagebox.ERROR);
			}
		}
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public static List<String> getCityList() {
		return cityList;
	}

	public void setCityList(List<String> cityList) {
		CoQuotation_AddController.cityList = cityList;
	}

	public ListModelList<CoOfferListModel> getCoproductList() {
		return coproductList;
	}

	public void setCoproductList(ListModelList<CoOfferListModel> coproductList) {
		this.coproductList = coproductList;
	}

	public ListModelList<String> getFpclassList() {
		return fpclassList;
	}

	public void setFpclassList(ListModelList<String> fpclassList) {
		this.fpclassList = fpclassList;
	}

	public List<CoPFeeclassModel> getFeeclassList() {
		return feeclassList;
	}

	public void setFeeclassList(List<CoPFeeclassModel> feeclassList) {
		this.feeclassList = feeclassList;
	}

	public ListModelList<CoOfferListModel> getStandardList() {
		return standardList;
	}

	public void setStandardList(ListModelList<CoOfferListModel> standardList) {
		this.standardList = standardList;
	}

	public List<CoOfferListModel> getCoofferList() {
		return coofferList;
	}

	public void setCoofferList(List<CoOfferListModel> coofferList) {
		this.coofferList = coofferList;
	}

	public List<String> getShangbaoList() {
		return shangbaoList;
	}

	public void setShangbaoList(List<String> shangbaoList) {
		this.shangbaoList = shangbaoList;
	}

	public List<String> getAmountList() {
		return amountList;
	}

	public void setAmountList(List<String> amountList) {
		this.amountList = amountList;
	}

}
