package Controller.CoCompact;

import impl.UserInfoImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoCompactOperateGroupingModel;
import Model.CoCompactTemAddModel;
import Model.RoleListModel;
import Util.RedirectUtil;
import bll.CoCompact.CoCompact_CompanyAddBll;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoAgency.WtAgency_DisCitySelBll;

public class CoCompact_CompanyAddController {
	private List<String[]> citylist;
	private ListModelList<RoleListModel> deplist;// 获取已选角色
	CoCompact_CompanyAddBll bll = new CoCompact_CompanyAddBll();
	private WtAgency_DisCitySelBll ctbll = new WtAgency_DisCitySelBll();
	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();

	// private List citylist; // 合同履行地
	private CoCompactOperateGroupingModel coofferInfoGroupList;
	private boolean showGroup = false;

	// 页面显示
	CoCompactTemAddModel cctaModel = new CoCompactTemAddModel();

	private CoCompactModel ccm = new CoCompactModel();

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

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	// 页面传递数据
	public CoCompact_CompanyAddController() throws Exception {
		citylist = ctbll.getCityNamePY();
		// 社保
		// 账户类型
		zhlxList.add("独立开户");
		//zhlxList.add("中智开户");
		zhlxList.add("中智开户(委托)");

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

		// blList.add("浮动比例");

		// 工资
		// 所属期
		monthList.add("上月");
		monthList.add("当月");
		monthList.add("下月");

		coco_sbperfee = "1";
		coco_gzpay = 1;

		// 个税
		// 所属期
		monthList1.add("上月");
		monthList1.add("当月");
		monthList1.add("下月");

		coco_gsfee = "1";
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

	// 账户类型改变,当独立账户时,需要选择支付方式
	@Command("zhlxchange")
	@NotifyChange({ "coco_sbfee", "coco_housefee", "cctaModel", "blList" })
	public void zhlxinit() {
		if (coco_shebao.equals("独立开户")) {
			coco_sbfee = "2";
			cctaModel.setSbrowVis(true);
		} else {
			coco_sbfee = "0";
			cctaModel.setSbrowVis(false);
		}
		if (coco_house.equals("独立开户")) {
			coco_housefee = "2";
			cctaModel.setGjjrowVis(true);
			blList.clear();

			for (int i = 5; i <= 12; i++) {
				blList.add(i + "");
			}
			coco_cpp = "5";
		} else {
			coco_housefee = "0";
			cctaModel.setGjjrowVis(false);
			blList.clear();
			for (int i = 5; i <= 12; i++) {
				blList.add(i + "");
			}
			blList.add("浮动比例");
			coco_cpp = "5";
		}
	}

	// 公司合同生成模板方法
	@Command("addCompactTemp")
	public void addCompactTemp(
			@BindingParam("w1") final Window w1,
			/*
			 * @BindingParam("cbClass1") Combobox coco_wttype,
			 * 
			 * @BindingParam("cbClass2") Combobox coco_class,
			 * 
			 * @BindingParam("cbClass3") Combobox coco_compacttype,
			 * 
			 * @BindingParam("cbClass4") Datebox coco_inuredate,
			 * 
			 * @BindingParam("cbClass5") Datebox coco_indate,
			 * 
			 * @BindingParam("cbClass6") Combobox coco_delay,
			 * 
			 * @BindingParam("cbClass7") Datebox coco_signdate,
			 * 
			 * @BindingParam("cbClass8") Textbox coco_money,
			 * 
			 * @BindingParam("cbClass9") Combobox coco_invoice,
			 */
			// @BindingParam("cbClass10") Textbox coco_compactid,
			@BindingParam("cbClass11") final Combobox city,
			@BindingParam("cbClass12") final Combobox agency,
			@BindingParam("cbClass13") final Textbox coco_remark,
			@BindingParam("cid") final Label cid,
			@BindingParam("fileid") final Textbox fileid) throws Exception {

		// 日期判断
		String inuredate = "";
		/*
		 * if (coco_inuredate.getValue() != null) { inuredate =
		 * bll.DatetoSting(coco_inuredate.getValue()); }
		 */

		String indate = "";
		/*
		 * if (coco_indate.getValue() != null) { indate =
		 * bll.DatetoSting(coco_indate.getValue()); }
		 */

		String signdate = "";
		/*
		 * if (coco_signdate.getValue() != null) { signdate =
		 * bll.DatetoSting(coco_signdate.getValue()); }
		 */

		/*
		 * if("".equals(coco_class.getValue())){ Messagebox.show("请选择“合同类型”",
		 * "操作提示", Messagebox.OK, Messagebox.ERROR); return; }
		 * 
		 * if("".equals(coco_compacttype.getValue())){
		 * Messagebox.show("请选择“合同版本”", "操作提示", Messagebox.OK,
		 * Messagebox.ERROR); return; }
		 * 
		 * if("".equals(coco_wttype.getValue())){ Messagebox.show("请选择“合同性质”",
		 * "操作提示", Messagebox.OK, Messagebox.ERROR); return; }
		 * 
		 * if("".equals(signdate)){ Messagebox.show("请选择“合同签订时间”", "操作提示",
		 * Messagebox.OK, Messagebox.ERROR); return; }
		 */
		// 判断是否选择城市和委托机构
		if ("".equals(city.getValue()) || city.getSelectedItem() == null) {
			Messagebox.show("请选择“委托城市”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if ("".equals(agency.getValue()) || agency.getSelectedItem() == null) {
			Messagebox.show("请选择“委托机构”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		// 判断合同号是否已存在
		/*
		 * if (!"".equals(coco_compactid.getValue())) { if
		 * (bll.checkCompactID(coco_compactid.getValue()) > 0) {
		 * Messagebox.show("已存在该“合同编号”，请检查输入是否有误", "操作提示", Messagebox.OK,
		 * Messagebox.ERROR); return; } }
		 */

		// 判断必填项是否为空
		// if (!inuredate.equals("")) {

		// 调用合同生成模板方法

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							String cpp = "";
							if (coco_cpp != null && !coco_cpp.equals("")) {

								cpp = coco_cpp.equals("浮动比例") ? coco_cpp
										: Double.parseDouble(coco_cpp) / 100
												+ "";

							}
							int wttype = 1; // 合同性质
							String cocoType = "FS"; // 合同类型
							String cocoClass = "1"; // 合同版本
							bll.Compact_CompanyAdd(cid.getValue(), wttype,
									cocoType, cocoClass, "", "", "", "", "",
									"", "", city.getSelectedItem().getValue()
											.toString(), agency
											.getSelectedItem().getValue()
											.toString(),
									coco_remark.getValue(), username,
									coco_shebao, coco_house, cpp, coco_gzmonth,
									coco_gsmonth, Integer.parseInt(coco_sbfee),
									Integer.parseInt(coco_housefee),
									Integer.parseInt(coco_sbperfee),
									Integer.parseInt(coco_gsfee), coco_gzpay,
									fileid.getValue());

							Integer coid = bll.Dochek();
							if (coid > 0) {
								/*
								if (bll.addCoofferData(
										Integer.valueOf(cid.getValue()), coid,
										coco_shebao, coco_house)) {
*/
									ccm.setCoco_id(coid);
									ccm.setCid(cid.getValue());
									ccm.setCid2(Integer.valueOf(cid.getValue()));
									ccm.setCoco_shebao(coco_shebao);
									ccm.setCoco_house(coco_house);
									CoBaseModel cbm = new CoBaseModel();
									cbm = bll.SearchCobase(ccm.getCid2());
									ccm.setCompany(cbm.getCoba_company());
									cocoBll.startMission(ccm);
								//}
								Messagebox.show("提交成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								
								//页面跳转方法已写好，调用如下：
								RedirectUtil util=new RedirectUtil();

								//公司业务中心跳转方法:
								util.refreshCoUrl("../CoCompact/Compact_Managerbjdaddqg.zul");//url为跳转页面连接

								
							} else {
								Messagebox.show("提交失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
		// 判断是否插入数据********************start**************************************************************

		// ******************************end*****************************************************************
		// } else {
		/*
		 * if (coco_servicearea.getValue().equals("")) {
		 * Messagebox.show("请选择“合同履行地”", "操作提示", Messagebox.OK,
		 * Messagebox.ERROR); } else if ("".equals(inuredate)) {
		 * Messagebox.show("请选择“合同生效日期”", "操作提示", Messagebox.OK,
		 * Messagebox.ERROR); }
		 */
		// }
	}

	/*
	 * public List getCitylist() { return citylist; }
	 * 
	 * public void setCitylist(List citylist) { this.citylist = citylist; }
	 */

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
