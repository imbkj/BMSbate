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
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.UserInfoService;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoCompactTemAddModel;
import Model.RoleListModel;
import bll.CoAgency.WtAgency_DisCitySelBll;
import bll.CoBase.CoBase_SelectBll;
import bll.CoCompact.CoCompact_CompanyAddBll;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;

public class CoCompact_CompanyAddCSController {
	private List<String[]> citylist;
	private ListModelList<RoleListModel> deplist;// 获取已选角色
	CoCompact_CompanyAddBll bll = new CoCompact_CompanyAddBll();

	private WtAgency_DisCitySelBll ctbll = new WtAgency_DisCitySelBll();

	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();
	// private List citylist; // 合同履行地
	CoLatencyClient_AddBll colaBll = new CoLatencyClient_AddBll();

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
	private boolean csvisble = true;
	private boolean if_coco_gs = false;
	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	// 页面传递数据
	int cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
			.toString());

	public CoCompact_CompanyAddCSController() throws Exception {
		citylist = ctbll.getCityNamePY();
		// 社保

		// 账户类型
		zhlxList.add("独立开户");
		zhlxList.add("中智开户");

		// 公积金

		// 账户类型
		zhlxList1.add("独立开户");
		zhlxList1.add("中智开户");

		for (int i = 5; i <= 12; i++) {
			blList.add(i + "");
		}
		blList.add("浮动比例");

		// 工资

		// 所属期
		monthList.add("上月");
		monthList.add("当月");
		monthList.add("下月");

		coco_gzpay = 1;

		// 个税

		// 所属期
		monthList1.add("上月");
		monthList1.add("当月");
		monthList1.add("下月");

	}

	// 查询机构下拉框
	@Command("search")
	@NotifyChange("deplist")
	public void submit(@BindingParam("tb1") Combobox tb1) throws Exception {
		deplist = new ListModelList<RoleListModel>();
		try {
			deplist = new ListModelList<RoleListModel>(bll.deplist(tb1
					.getSelectedItem().getValue().toString()));
		} catch (Exception e) {
			// TODO: handle exception
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

	// 改变个税开户状态
	@Command("gsChange")
	@NotifyChange({ "ifgs", "coco_gsfee", "if_coco_gs" })
	public void gsChange() {
		if (coco_gs.equals("中智开户")) {
			ifgs = "是";
			coco_gsfee = "1";
			if_coco_gs = false;
		} else if (coco_gs.equals("独立开户")) {
			if_coco_gs = true;
		}
	}

	// 公司合同生成模板方法
	@Command("addCompact")
	public void addCompact(@BindingParam("w1") final Window w1)
			throws Exception {

		Textbox coco_remark = (Textbox) w1.getFellow("coco_remark");
		Combobox coco_paydate = (Combobox) w1.getFellow("coco_paydate");
		Combobox cb_city = (Combobox) w1.getFellow("cbCity");
		Combobox cb_agency = (Combobox) w1.getFellow("cbAgency");

		if ("".equals(cb_city.getValue()) || cb_city.getSelectedItem() == null) {
			Messagebox.show("请选择“委托城市”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if ("".equals(cb_agency.getValue()) || cb_agency.getSelectedItem() == null) {
			Messagebox.show("请选择“委托机构”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		// 日期判断
/*		String inuredate = "";// 生效日期
		String returndate = "";// 签回日期
		String signdate = "";// 合同签订日期
		String indate = "";// 合同到期日
		String filedate = "";// 合同归档日期
*/

		String chsCopies = "0"; // 中文份数
		String enCopies = "0"; // 英文份数
		// 整数判断


		// 判断必填项是否为空
		if (chkCSCompact(csvisble, coco_gsfee,
				coco_sbperfee, coco_houseperfee, coco_gzperfee, coco_ifgzpay,
				coco_gzmonth, coco_gsmonth, coco_gs,coco_paydate.getValue())) {

			// 调用合同生成模板方法
			String cpp = "";
/*			if (!coco_cpp.equals("")) {
				cpp = coco_cpp.equals("浮动比例") ? coco_cpp : Double
						.parseDouble(coco_cpp) / 100 + "";
			}
*/
			CoBase_SelectBll cBll = new CoBase_SelectBll();
			CoBaseModel cbm = cBll.getCobaseByCid(cid);

			CoCompactModel m = new CoCompactModel();
			m.setCompany(cbm.getCoba_company());
			m.setCid(String.valueOf(cid));
			m.setCid2(cid);
			m.setCoco_returndate(null);
			m.setCoco_servicearea("深圳");
			m.setCoco_inuredate(null);
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
			m.setCoco_compacttype("CS");
			m.setCity(cb_city.getValue());
			m.setAgency(cb_agency.getValue());
			
			m.setCoco_signdate(null);
			m.setCoco_indate(null);
			m.setCoco_filedate(null);


			//m.setCoco_money(coco_money.getValue());
			//m.setCoco_invoice(coco_invoice.getValue());
			//m.setCoco_fileid(coco_fileid.getValue());
			m.setCoco_chs_copies(chsCopies);
			m.setCoco_en_copies(enCopies);
			//m.setCoco_compactid(coco_compactid.getValue());
			m.setSbInure(true);
			m.setHouseInure(true);
			m.setCoco_paydate(Integer.parseInt(coco_paydate.getValue()));
			String[] message = cocoBll.addCSCompactInfo(m);

			//cocoBll.startMission(m);

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				// 关闭
				w1.detach();
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public boolean chkCSCompact(boolean gzVis, String coco_gsfee, String coco_sbperfee,
			String coco_houseperfee, String coco_gzperfee, String coco_ifgzpay,
			String coco_gzmonth, String coco_gsmonth, String coco_gs,String paydate) {
		boolean b = true;

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

	public List<String[]> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String[]> citylist) {
		this.citylist = citylist;
	}

	public ListModelList<RoleListModel> getDeplist() {
		return deplist;
	}

	public void setDeplist(ListModelList<RoleListModel> deplist) {
		this.deplist = deplist;
	}

}
