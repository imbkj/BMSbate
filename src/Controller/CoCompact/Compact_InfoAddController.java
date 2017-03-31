package Controller.CoCompact;

import impl.UserInfoImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.UserInfoService;
import Model.CoBaseModel;
import Model.CoCompactModel;

import Model.CoCompactTemAddModel;

import Model.CopCompactModel;
import bll.CoBase.CoBase_SelectBll;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoProduct.cpd_ListBll;

public class Compact_InfoAddController {
	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();
	// private List citylist; // 合同履行地
	CoLatencyClient_AddBll colaBll = new CoLatencyClient_AddBll();

	// 页面显示
	CoCompactTemAddModel cctaModel = new CoCompactTemAddModel();

	// 合同信息
	private List<CopCompactModel> cpctList;// 合同类型下拉框

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
	private String coco_sbperfee = "0";// 社保个人支付方式
	private int coco_gzpay;
	private String coco_houseperfee = "0";// 公积金个人部分支付方式
	private String coco_gzperfee = "0";// 工资支付方式
	private String coco_ifgzpay = "0";// 是否只计算工资不发放
	private String coco_gs = "";// 个税账户类型

	private String ifgz = "";
	private String ifsbper = "否";
	private String ifhouseper = "否";
	private String ifgs = "";

	private boolean tfcompactid = true;
	private String comidText = "";

	private boolean rsvisble = false;
	private boolean csvisble = false;
	private boolean if_coco_gs = false;
	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	// 页面传递数据
	int cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
			.toString());

	public Compact_InfoAddController() throws Exception {
		// citylist = cocoBll.getCityName(); // 合同履行地

		// 社保

		// 账户类型
		zhlxList.add("独立开户");
		zhlxList.add("中智开户");
		//zhlxList.add("中智开户(委托)");

		// 公积金

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

		// 工资

		// 所属期
		monthList.add("上月");
		monthList.add("当月");
		monthList.add("下月");

		// coco_sbperfee = "1";
		coco_gzpay = 1;

		// 个税

		// 所属期
		monthList1.add("上月");
		monthList1.add("当月");
		monthList1.add("下月");

		// coco_gsfee = "1";

		// 获取合同类型
		setCpctList(new cpd_ListBll().getCopCompactList(""));
		for (CopCompactModel m : cpctList) {
			if (m.getCpct_name() != null && !m.getCpct_name().isEmpty()) {
				m.setCpct_name(m.getCpct_shortname() + "(" + m.getCpct_name()
						+ ")");
			} else {
				m.setCpct_name(m.getCpct_shortname());
			}
		}

	}

	@Command("compactidReadonly")
	@NotifyChange({ "tfcompactid", "comidText" })
	public void compactidReadonly(@BindingParam("chkb") Checkbox chkb) {
		if (chkb.isChecked()) {
			tfcompactid = true;
			comidText = "";
		} else {
			tfcompactid = false;
		}
	}

	@Command("changevisble")
	@NotifyChange({ "rsvisble", "csvisble" })
	public void changevisble(
			@BindingParam("compacttype") Combobox coco_compacttype) {
		if (coco_compacttype.getSelectedItem().getValue().equals("CS")) {
			rsvisble = false;
			csvisble = true;
		} else {
			rsvisble = true;
			csvisble = false;
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
			for (int i = 5; i <= 20; i++) {
				blList.add(i + "");
			}

		} else {
			coco_housefee = "0";
			cctaModel.setGjjrowVis(false);

			// 公积金比例
			blList = new ListModelList<String>();
			// blList.add("0.5");
			for (int i = 5; i <= 20; i++) {
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
	@Command("addCompact")
	public void addCompact(@BindingParam("w1") final Window w1)
			throws Exception {

		Datebox coco_inuredate = (Datebox) w1.getFellow("coco_inuredate");
		Combobox coco_compacttype = (Combobox) w1.getFellow("coco_compacttype");
		Textbox coco_remark = (Textbox) w1.getFellow("coco_remark");
		Datebox coco_returndate = (Datebox) w1.getFellow("coco_returndate");
		Datebox coco_signdate = (Datebox) w1.getFellow("coco_signdate");
		Datebox coco_indate = (Datebox) w1.getFellow("coco_indate");
		Datebox coco_filedate = (Datebox) w1.getFellow("coco_filedate");
		Combobox coco_signplace = (Combobox) w1.getFellow("coco_signplace");
		Combobox coco_delay = (Combobox) w1.getFellow("coco_delay");
		Textbox coco_money = (Textbox) w1.getFellow("coco_money");
		Combobox coco_invoice = (Combobox) w1.getFellow("coco_invoice");
		Textbox coco_fileid = (Textbox) w1.getFellow("coco_fileid");
		Intbox coco_chs_copies = (Intbox) w1.getFellow("coco_chs_copies");
		Intbox coco_en_copies = (Intbox) w1.getFellow("coco_en_copies");
		Textbox coco_compactid = (Textbox) w1.getFellow("coco_compactid");
		Combobox coco_paydate = (Combobox) w1.getFellow("coco_paydate");

		// 日期判断
		String inuredate = "";// 生效日期
		if (coco_inuredate.getValue() != null) {
			inuredate = cocoBll.DatetoSting(coco_inuredate.getValue());
		}
		String returndate = "";// 签回日期
		if (coco_returndate.getValue() != null) {
			returndate = cocoBll.DatetoSting(coco_returndate.getValue());
		}
		String signdate = "";// 合同签订日期
		if (coco_signdate.getValue() != null) {
			signdate = cocoBll.DatetoSting(coco_signdate.getValue());
		}
		String indate = "";// 合同到期日
		if (coco_indate.getValue() != null) {
			indate = cocoBll.DatetoSting(coco_indate.getValue());
		}
		String filedate = "";// 合同归档日期
		if (coco_filedate.getValue() != null) {
			filedate = cocoBll.DatetoSting(coco_filedate.getValue());
		}

		String chsCopies = "0"; // 中文份数
		String enCopies = "0"; // 英文份数
		// 整数判断
		if (coco_chs_copies.getValue() != null) {
			chsCopies = String.valueOf(coco_chs_copies.getValue());
		}
		if (coco_en_copies.getValue() != null) {
			enCopies = String.valueOf(coco_en_copies.getValue());
		}
		

		// 判断必填项是否为空
		if (chkCSCompact(inuredate, signdate, coco_compacttype.getValue(),
				coco_delay.getValue(), chsCopies, csvisble, coco_gsfee,
				coco_sbperfee, coco_houseperfee, coco_gzperfee, coco_ifgzpay,
				coco_gzmonth, coco_gsmonth, coco_gs,coco_paydate.getValue())) {

			// 调用合同生成模板方法
			String cpp = "";
			if (!coco_cpp.equals("")) {
				cpp = coco_cpp.equals("浮动比例") ? coco_cpp : Double
						.parseDouble(coco_cpp) / 100 + "";
			}

			CoBase_SelectBll cBll = new CoBase_SelectBll();
			CoBaseModel cbm = cBll.getCobaseByCid(cid);

			CoCompactModel m = new CoCompactModel();
			m.setCompany(cbm.getCoba_company());
			m.setCid(String.valueOf(cid));
			m.setCid2(cid);
			m.setCoco_returndate(returndate);
			m.setCoco_servicearea("深圳");
			m.setCoco_inuredate(inuredate);
			m.setCoco_remark(coco_remark.getValue());
			m.setCoco_addname(username);
			m.setCoco_shebao(coco_shebao);
			m.setCoco_house(coco_house);
			m.setCoco_cpp(cpp);
			m.setCoco_opp(cpp);
			m.setCoco_gzmonth(coco_gzmonth);
			m.setCoco_gsmonth(coco_gsmonth);
			m.setCoco_sbfee(Integer.parseInt(coco_sbfee));
			m.setCoco_housefee(Integer.parseInt(coco_housefee));
			m.setCoco_sbperfee(Integer.parseInt(coco_sbperfee));
			m.setCoco_gsfee(Integer.parseInt(coco_gsfee));
			m.setCoco_gzpay(coco_gzpay);
			m.setCoco_houseperfee(Integer.parseInt(coco_houseperfee));
			m.setCoco_gzperfee(Integer.parseInt(coco_gzperfee));
			m.setCoco_ifgzpay(Integer.parseInt(coco_ifgzpay));
			m.setCoco_gs(coco_gs);

			if (coco_compacttype.getSelectedItem() != null) {
				m.setCoco_compacttype(coco_compacttype.getSelectedItem()
						.getValue().toString());
			}

			m.setCoco_signdate(signdate);
			m.setCoco_indate(indate);
			m.setCoco_filedate(filedate);

			if (coco_signplace.getSelectedItem() != null) {
				m.setCoco_signplace(coco_signplace.getSelectedItem().getLabel());
			}

			if (coco_delay.getSelectedItem() != null) {
				m.setCoco_delay(coco_delay.getSelectedItem().getLabel());

			}

			m.setCoco_money(coco_money.getValue());
			m.setCoco_invoice(coco_invoice.getValue());
			m.setCoco_fileid(coco_fileid.getValue());
			m.setCoco_chs_copies(chsCopies);
			m.setCoco_en_copies(enCopies);
			m.setCoco_compactid(coco_compactid.getValue());
			m.setSbInure(true);
			m.setHouseInure(true);
			m.setCoco_paydate(Integer.parseInt(coco_paydate.getValue()));
			String[] message = cocoBll.addCompactInfo(m);

			cocoBll.startMission(m);

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				/*EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {

							// 跳转页面
							// Executions.sendRedirect("Compact_InFileList.zul");
							//关闭
							w1.detach();
						}
					}
				};
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);*/
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				//关闭
				w1.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public boolean chkCSCompact(String inuredate, String signdate,
			String coco_compacttype, String coco_delay, String chsCopies,
			boolean gzVis, String coco_gsfee, String coco_sbperfee,
			String coco_houseperfee, String coco_gzperfee, String coco_ifgzpay,
			String coco_gzmonth, String coco_gsmonth, String coco_gs,String paydate) {
		boolean b = true;

		if ("".equals(signdate)) {
			b = false;
			Messagebox.show("请选择“合同签回日期”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if ("".equals(inuredate)) {
			b = false;
			Messagebox.show("请选择“合同生效日期”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if (coco_compacttype == null || "".equals(coco_compacttype)) {
			b = false;
			Messagebox.show("请选择“合同类型”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if ("".equals(coco_delay)) {
			b = false;
			Messagebox.show("请选择“合同自动延长”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if ("0".equals(chsCopies)) {
			b = false;
			Messagebox.show("请输入“中文合同份数”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}
		if (paydate == null || "".equals(paydate)) {
			b = false;
			Messagebox.show("请选择“每月付款日”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return b;
		}

		if (gzVis) {// cs合同必填项是否完整

			if ("".equals(ifgz)) {
				b = false;
				Messagebox.show("请选择“是否支付工资款”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
			/*if ("".equals(ifsbper)) {
				b = false;
				Messagebox.show("请选择“是否支付社保个人部分”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
			if ("".equals(ifhouseper)) {
				b = false;
				Messagebox.show("请选择“是否支付住房公积金个人部分”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}*/
			if ("".equals(ifgs)) {
				b = false;
				Messagebox.show("请选择“是否支付个人所得税”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}

			if ("".equals(coco_gzmonth)) {
				b = false;
				Messagebox.show("请选择“台帐中工资所属期”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return b;
			}
			if ("".equals(coco_gsmonth)) {
				b = false;
				Messagebox.show("请选择“台帐中个税所属期”", "操作提示", Messagebox.OK,
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

			/*
			 * if ("".equals(coco_ifgzpay)) { b = false;
			 * Messagebox.show("请选择“是否只计算工资不发”", "操作提示", Messagebox.OK,
			 * Messagebox.ERROR); return b; }
			 */
		}

		return b;
	}

	public CoCompactTemAddModel getCctaModel() {
		return cctaModel;
	}

	public boolean isRsvisble() {
		return rsvisble;
	}

	public void setRsvisble(boolean rsvisble) {
		this.rsvisble = rsvisble;
	}

	public boolean isCsvisble() {
		return csvisble;
	}

	public void setCsvisble(boolean csvisble) {
		this.csvisble = csvisble;
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

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public boolean isTfcompactid() {
		return tfcompactid;
	}

	public void setTfcompactid(boolean tfcompactid) {
		this.tfcompactid = tfcompactid;
	}

	public String getComidText() {
		return comidText;
	}

	public void setComidText(String comidText) {
		this.comidText = comidText;
	}

	public List<CopCompactModel> getCpctList() {
		return cpctList;
	}

	public void setCpctList(List<CopCompactModel> cpctList) {
		this.cpctList = cpctList;
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

	public boolean isIf_coco_gs() {
		return if_coco_gs;
	}

	public void setIf_coco_gs(boolean if_coco_gs) {
		this.if_coco_gs = if_coco_gs;
	}

}
