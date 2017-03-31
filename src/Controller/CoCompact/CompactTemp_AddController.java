package Controller.CoCompact;

import impl.UserInfoImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;

import Model.CoCompactOperateGroupingModel;
import Model.CoCompactTemAddModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoQuotation.CoQuotationInfoBll;

public class CompactTemp_AddController {
	private List<CoOfferListModel> coofferinfoList;
	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();
	// private List citylist; // 合同履行地
	private CoCompactOperateGroupingModel coofferInfoGroupList;
	private boolean showGroup = false;
	CoLatencyClient_AddBll colaBll = new CoLatencyClient_AddBll();
	private List<CoOfferListModel> compactclass;// 获取合同类型

	// 页面显示
	CoCompactTemAddModel cctaModel = new CoCompactTemAddModel();

	// 合同信息
	private List<String> zhlxList = new ListModelList<String>();// 账户类型列表
	private List<String> zhlxList1 = new ListModelList<String>();
	private String coco_shebao = "";// 社保账户类型
	private String coco_house = "";// 公积金账户类型
	private List<String> blList = new ListModelList<String>();// 公积金比例列表
	private String coco_cpp = "";// 公积金比例(公司、个人)
	private List<String> monthList = new ListModelList<String>();// 工资个税所属期列表
	private List<String> monthList1 = new ListModelList<String>();
	private String coco_gzmonth = "";// 工资所属期
	private String coco_gsmonth = "";// 个税所属期

	private String coco_sbfee = "0";// 社保支付方式
	private String coco_housefee = "0";// 公积金支付方式
	private String coco_gsfee = "0";// 个税支付方式
	private String coco_sbperfee = "0";// 社保个人部分支付方式
	private String coco_houseperfee = "0";// 公积金个人部分支付方式
	private String coco_gzperfee = "0";// 工资支付方式
	private String coco_ifgzpay = "0";// 是否只计算工资不发放
	private int coco_gzpay;
	private String coco_gs = "";// 个税账户类型

	private String ifgz = "";
	private String ifsbper = "否";
	private String ifhouseper = "否";
	private String ifgs = "";
	private boolean if_coco_gs=false;

	private String cola_shortname = "";// 公司简称

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	// 页面传递数据
	int cola_id = ((CoOfferModel) Executions.getCurrent().getArg().get("model"))
			.getCoof_cola_id();
	int coof_id = ((CoOfferModel) Executions.getCurrent().getArg().get("model"))
			.getCoof_id();
	String coof_ids = Executions.getCurrent().getArg().get("coof_id")
			.toString();
	String cpct_shortname = ((CoOfferModel) Executions.getCurrent().getArg()
			.get("model")).getCpct_shortname();

	public CompactTemp_AddController() throws Exception {
		// citylist = cocoBll.getCityName(); // 合同履行地
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				new CoQuotationInfoBll().getCoOfferlistList(coof_ids)));
		setCctaModel(cocoBll.getPageVisible(coof_ids));
		setCompactclass(new ListModelList<CoOfferListModel>(
				new CoQuotationInfoBll().getCompactClass(coof_ids)));
		if (!cctaModel.isSbVis() && !cctaModel.isGjjVis()
				&& !cctaModel.isGzVis() && !cctaModel.isGsVis()) {
			cctaModel.setGroupVis(false);
		} else {
			cctaModel.setGroupVis(true);
		}

		// 社保
		if (cctaModel.isSbVis()) {
			// 账户类型
			zhlxList.add("独立开户");
			zhlxList.add("中智开户");
			//zhlxList.add("中智开户(委托)");
		}

		// 公积金
		if (cctaModel.isGjjVis()) {
			// 账户类型
			zhlxList1.add("独立开户");
			zhlxList1.add("中智开户");
			// zhlxList1.add("中智开户(委托)");

			// 公积金比例
			// blList.add("0.5");
			for (int i = 5; i <= 12; i++) {
				blList.add(i + "");
			}
			blList.add("浮动比例");
		}

		// 工资
		if (cctaModel.isGzVis()) {
			// 所属期
			monthList.add("上月");
			monthList.add("当月");
			monthList.add("下月");

			// coco_sbperfee = "1";
			coco_gzpay = 1;
		}

		// 个税
		if (cctaModel.isGsVis()) {
			// 所属期
			monthList1.add("上月");
			monthList1.add("当月");
			monthList1.add("下月");

			// coco_gsfee = "1";
		}
	}

	// 账户类型改变,当独立账户时,需要选择支付方式
	@Command("zhlxchange")
	@NotifyChange({ "coco_sbfee", "coco_housefee", "cctaModel", "blList" })
	public void zhlxinit() {
		if (coco_shebao.equals("独立开户")) {
			coco_sbfee = "1";
			cctaModel.setSbrowVis(true);
		} else {
			coco_sbfee = "0";
			cctaModel.setSbrowVis(false);
		}
		if (coco_house.equals("独立开户")) {
			coco_housefee = "1";
			cctaModel.setGjjrowVis(true);

			// 公积金比例
			blList = new ListModelList<String>();
			// blList.add("0.5");
			for (int i = 5; i <= 12; i++) {
				blList.add(i + "");
			}

		} else {
			coco_housefee = "0";
			cctaModel.setGjjrowVis(false);

			// 公积金比例
			blList = new ListModelList<String>();
			// blList.add("0.5");
			for (int i = 5; i <= 12; i++) {
				blList.add(i + "");
			}
			blList.add("浮动比例");
		}
	}
	
	//改变个税开户状态
	@Command("gsChange")
	@NotifyChange({"ifgs","coco_gsfee","if_coco_gs"})
	public void gsChange() {
		if (coco_gs.equals("中智开户")) {
			ifgs="是";
			coco_gsfee="1";
			if_coco_gs=false;
		}else if (coco_gs.equals("独立开户")) {
			if_coco_gs=true;
		}
	}

	// 公司合同生成模板方法
	@Command("addCompactTemp")
	public void addCompactTemp(@BindingParam("w1") final Window w1,
			@BindingParam("coco_inuredate") Datebox coco_inuredate,
			@BindingParam("coco_compacttype") Textbox coco_compacttype,
			@BindingParam("coco_remark") Textbox coco_remark) throws Exception {

		if ("".equals(coco_compacttype)) {

			Messagebox.show("请选择“合同类型”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		// 判断选择的是单个报价单还是多个报价单
		if (coof_ids.equals("0")) {
			coof_ids = coof_ids + "," + String.valueOf(coof_id);
		}

		// 日期判断
		String inuredate = "";
		if (coco_inuredate.getValue() != null) {
			inuredate = cocoBll.DatetoSting(coco_inuredate.getValue());
		}

		// 判断必填项是否为空
		if (chkCSCompact(inuredate, cctaModel.isGzVis(), coco_gsfee,
				coco_sbperfee, coco_houseperfee, coco_gzperfee, coco_ifgzpay,
				coco_gzmonth, coco_gsmonth, coco_gs, cctaModel.isGsVis())) {

			// 调用合同生成模板方法
			String cpp = "";
			if (!coco_cpp.equals("")) {
				cpp = coco_cpp.equals("浮动比例") ? coco_cpp : Double
						.parseDouble(coco_cpp) / 100 + "";
			}

			// 获取公司简称
			cola_shortname = colaBll
					.getCoLatencyClientAllInfo(" AND a.cola_id=" + cola_id)
					.get(0).getCola_company().toString();

			String[] message = cocoBll.addCompactTemp(cola_id, coof_ids,
					cpct_shortname, "深圳", inuredate, coco_remark.getValue(),
					username, coco_shebao, coco_house, cpp, coco_gzmonth,
					coco_gsmonth, Integer.parseInt(coco_sbfee),
					Integer.parseInt(coco_housefee),
					Integer.parseInt(coco_sbperfee),
					Integer.parseInt(coco_gsfee), coco_gzpay, cola_shortname,
					Integer.parseInt(coco_houseperfee),
					Integer.parseInt(coco_gzperfee),
					Integer.parseInt(coco_ifgzpay), coco_gs,
					coco_compacttype.getValue());

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// 跳转页面
							// Executions.sendRedirect("Compact_InFileList.zul");
							// 关闭
							w1.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	/*
	 * public List getCitylist() { return citylist; }
	 * 
	 * public void setCitylist(List citylist) { this.citylist = citylist; }
	 */

	public boolean chkCSCompact(String inuredate, boolean gzVis,
			String coco_gsfee, String coco_sbperfee, String coco_houseperfee,
			String coco_gzperfee, String coco_ifgzpay, String coco_gzmonth,
			String coco_gsmonth, String coco_gs, boolean gsVis) {
		boolean b = true;
		if ("".equals(inuredate)) {
			b = false;
			Messagebox.show("请选择“合同生效日期”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if (gzVis) {// cs合同工资必填项是否完整

			if ("".equals(ifgz)) {
				b = false;
				Messagebox.show("请选择“是否支付工资款”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

			if ("".equals(coco_gzmonth)) {
				b = false;
				Messagebox.show("请选择“台帐中工资所属期”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

			if ("是".equals(ifgz)) {
				if ("0".equals(coco_gzperfee)) {
					b = false;
					Messagebox.show("请选择“工资款支付方式”", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return b;
				}
			}

			if ("是".equals(ifsbper)) {
				if ("0".equals(coco_sbperfee)) {
					b = false;
					Messagebox.show("请选择“社保个人部分支付方式”", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return b;
				}
			}

			if ("是".equals(ifhouseper)) {
				if ("0".equals(coco_houseperfee)) {
					b = false;
					Messagebox.show("请选择“住房公积金个人部分支付方式”", "操作提示",
							Messagebox.OK, Messagebox.ERROR);
					return b;
				}
			}

		}

		if (gsVis) {// cs合同个税必填项是否完整

			if ("".equals(ifgs)) {
				b = false;
				Messagebox.show("请选择“是否支付个人所得税”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

			if ("".equals(coco_gsmonth)) {
				b = false;
				Messagebox.show("请选择“台帐中个税所属期”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

			if ("".equals(coco_gs)) {
				b = false;
				Messagebox.show("请选择“个税开户状态”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

			if ("是".equals(ifgs)) {
				if ("0".equals(coco_gsfee)) {
					b = false;
					Messagebox.show("请选择“个税支付方式”", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return b;
				}
			}

		}

		return b;
	}

	public CoCompactOperateGroupingModel getCoofferInfoGroupList() {
		return coofferInfoGroupList;
	}

	public void setCoofferInfoGroupList(
			CoCompactOperateGroupingModel coofferInfoGroupList) {
		this.coofferInfoGroupList = coofferInfoGroupList;
	}

	public boolean isShowGroup() {
		return showGroup;
	}

	public void setShowGroup(boolean showGroup) {
		this.showGroup = showGroup;
	}

	public List<CoOfferListModel> getCoofferinfoList() {
		return coofferinfoList;
	}

	public void setCoofferinfoList(List<CoOfferListModel> coofferinfoList) {
		this.coofferinfoList = coofferinfoList;
	}

	public CoCompactTemAddModel getCctaModel() {
		return cctaModel;
	}

	public void setCctaModel(CoCompactTemAddModel cctaModel) {
		this.cctaModel = cctaModel;
	}

	public List<String> getZhlxList() {
		return zhlxList;
	}

	public void setZhlxList(List<String> zhlxList) {
		this.zhlxList = zhlxList;
	}

	public List<String> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}

	public List<String> getBlList() {
		return blList;
	}

	public void setBlList(List<String> blList) {
		this.blList = blList;
	}

	public String getCoco_shebao() {
		return coco_shebao;
	}

	public void setCoco_shebao(String coco_shebao) {
		this.coco_shebao = coco_shebao;
	}

	public String getCoco_sbfee() {
		return coco_sbfee;
	}

	public void setCoco_sbfee(String coco_sbfee) {
		this.coco_sbfee = coco_sbfee;
	}

	public String getCoco_housefee() {
		return coco_housefee;
	}

	public void setCoco_housefee(String coco_housefee) {
		this.coco_housefee = coco_housefee;
	}

	public String getCoco_gsfee() {
		return coco_gsfee;
	}

	public void setCoco_gsfee(String coco_gsfee) {
		this.coco_gsfee = coco_gsfee;
	}

	public String getCoco_sbperfee() {
		return coco_sbperfee;
	}

	public void setCoco_sbperfee(String coco_sbperfee) {
		this.coco_sbperfee = coco_sbperfee;
	}

	public String getCoco_cpp() {
		return coco_cpp;
	}

	public void setCoco_cpp(String coco_cpp) {
		this.coco_cpp = coco_cpp;
	}

	public String getCoco_gzmonth() {
		return coco_gzmonth;
	}

	public void setCoco_gzmonth(String coco_gzmonth) {
		this.coco_gzmonth = coco_gzmonth;
	}

	public String getCoco_gsmonth() {
		return coco_gsmonth;
	}

	public void setCoco_gsmonth(String coco_gsmonth) {
		this.coco_gsmonth = coco_gsmonth;
	}

	public String getCoco_house() {
		return coco_house;
	}

	public void setCoco_house(String coco_house) {
		this.coco_house = coco_house;
	}

	public List<String> getZhlxList1() {
		return zhlxList1;
	}

	public void setZhlxList1(List<String> zhlxList1) {
		this.zhlxList1 = zhlxList1;
	}

	public List<String> getMonthList1() {
		return monthList1;
	}

	public void setMonthList1(List<String> monthList1) {
		this.monthList1 = monthList1;
	}

	public int getCoco_gzpay() {
		return coco_gzpay;
	}

	public void setCoco_gzpay(int coco_gzpay) {
		this.coco_gzpay = coco_gzpay;
	}

	public String getCoco_houseperfee() {
		return coco_houseperfee;
	}

	public void setCoco_houseperfee(String coco_houseperfee) {
		this.coco_houseperfee = coco_houseperfee;
	}

	public String getCoco_gzperfee() {
		return coco_gzperfee;
	}

	public void setCoco_gzperfee(String coco_gzperfee) {
		this.coco_gzperfee = coco_gzperfee;
	}

	public String getCoco_ifgzpay() {
		return coco_ifgzpay;
	}

	public void setCoco_ifgzpay(String coco_ifgzpay) {
		this.coco_ifgzpay = coco_ifgzpay;
	}

	public String getCoco_gs() {
		return coco_gs;
	}

	public void setCoco_gs(String coco_gs) {
		this.coco_gs = coco_gs;
	}

	public String getIfgz() {
		return ifgz;
	}

	public void setIfgz(String ifgz) {
		this.ifgz = ifgz;
	}

	public String getIfsbper() {
		return ifsbper;
	}

	public void setIfsbper(String ifsbper) {
		this.ifsbper = ifsbper;
	}

	public String getIfhouseper() {
		return ifhouseper;
	}

	public void setIfhouseper(String ifhouseper) {
		this.ifhouseper = ifhouseper;
	}

	public String getIfgs() {
		return ifgs;
	}

	public void setIfgs(String ifgs) {
		this.ifgs = ifgs;
	}

	public List<CoOfferListModel> getCompactclass() {
		return compactclass;
	}

	public void setCompactclass(List<CoOfferListModel> compactclass) {
		this.compactclass = compactclass;
	}

	public boolean isIf_coco_gs() {
		return if_coco_gs;
	}

	public void setIf_coco_gs(boolean if_coco_gs) {
		this.if_coco_gs = if_coco_gs;
	}

}
