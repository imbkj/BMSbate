package Controller.CoBase;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.CoBase.CoBase_SelectBll;
import bll.CoCompact.BaseInfo_SelectListBll;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoQuotation.CoQuotationInfoBll;
import Model.CoAgencyBaseModel;
import Model.CoCompactModel;
import Model.CoCompactTemAddModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;

public class CoCompact_InfoqgController {
	String cocoid = (String) Executions.getCurrent().getArg().get("coco_id")
			.toString();
	BaseInfo_SelectListBll cocoBll = new BaseInfo_SelectListBll();
	CoCompact_OperateBll cobl = new CoCompact_OperateBll();
	private List<CoCompactModel> modellist;
	CoCompactModel model = new CoCompactModel();
	CoQuotationInfoBll bll = new CoQuotationInfoBll();
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
	private String coco_houseperfee = "0";// 公积金个人部分支付方式
	private String coco_gzperfee = "0";// 工资支付方式

	private int coco_gzpay;
	ListModelList<CoOfferListModel> comodellist;
	private List<CoOfferModel> scoofferList = new ListModelList<>();
	// 页面显示
	CoCompactTemAddModel cctaModel = new CoCompactTemAddModel();

	public CoCompact_InfoqgController() throws SQLException {
		modellist = cocoBll.getcompact(Integer.parseInt(cocoid));
		if (!modellist.isEmpty()) {
			model = modellist.get(0);
		}

		// setModel(model);
		setModel(cocoBll.getCoCompactInfo(cocoid));

		// 获取机构数据
		CoAgencyBaseModel coabM = new CoAgencyBaseModel();
		coabM = cocoBll.getAgencyInfo(Integer.parseInt(cocoid));
		if (coabM != null) {
			model.setAgency(coabM.getCoab_name());
			model.setCity(coabM.getCoab_city());
		}

		setCctaModel(cobl.getPageVisible2(cocoid));
		if (!cctaModel.isSbVis() && !cctaModel.isGjjVis()
				&& !cctaModel.isGzVis() && !cctaModel.isGsVis()) {
			cctaModel.setGroupVis(false);
		} else {
			cctaModel.setGroupVis(true);
		}

		CoBase_SelectBll sbll = new CoBase_SelectBll();

		setScoofferList(sbll.getcoofferList(" and b.coco_id='" + cocoid + "'"));

		// 社保
		if (cctaModel.isSbVis()) {
			// 账户类型
			zhlxList.add("独立开户");
			zhlxList.add("中智开户");
			zhlxList.add("中智开户(委托)");

			coco_sbfee = String.valueOf(model.getCoco_sbfee());
		}

		// 公积金
		if (cctaModel.isGjjVis()) {
			// 账户类型
			zhlxList1.add("独立开户");
			zhlxList1.add("中智开户");
			// zhlxList1.add("中智开户(委托)");

			// 公积金比例
			for (int i = 1; i <= 20; i++) {
				blList.add(i + "");
			}
			blList.add("浮动比例");

			coco_housefee = String.valueOf(model.getCoco_housefee());
		}

		// 工资
		if (cctaModel.isGzVis()) {
			// 所属期
			monthList.add("上月");
			monthList.add("当月");
			monthList.add("下月");

			coco_sbperfee = String.valueOf(model.getCoco_sbperfee());
			coco_houseperfee = String.valueOf(model.getCoco_houseperfee());
			coco_gzperfee = String.valueOf(model.getCoco_gzperfee());
			coco_gzpay = 1;
		}

		// 个税
		if (cctaModel.isGsVis()) {
			// 所属期
			monthList1.add("上月");
			monthList1.add("当月");
			monthList1.add("下月");

			coco_gsfee = String.valueOf(model.getCoco_gsfee());
		}

		setComodellist(new ListModelList<CoOfferListModel>(
				bll.getCoOfferlist(cocoid)));
	}

	// 弹出查看页面
	@Command("chakan")
	public void chakan(@BindingParam("model") CoOfferModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coofid", model.getCoof_id());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/CoQuotationInfoSelectqg.zul", null, map);
		window.doModal();
	}

	public List<CoOfferModel> getScoofferList() {
		return scoofferList;
	}

	public void setScoofferList(List<CoOfferModel> scoofferList) {
		this.scoofferList = scoofferList;
	}

	public CoCompactModel getModel() {
		return model;
	}

	public void setModel(CoCompactModel model) {
		this.model = model;
	}

	public CoCompactTemAddModel getCctaModel() {
		return cctaModel;
	}

	public void setCctaModel(CoCompactTemAddModel cctaModel) {
		this.cctaModel = cctaModel;
	}

	public List<CoCompactModel> getModellist() {
		return modellist;
	}

	public void setModellist(List<CoCompactModel> modellist) {
		this.modellist = modellist;
	}

	public List<String> getZhlxList() {
		return zhlxList;
	}

	public void setZhlxList(List<String> zhlxList) {
		this.zhlxList = zhlxList;
	}

	public List<String> getZhlxList1() {
		return zhlxList1;
	}

	public void setZhlxList1(List<String> zhlxList1) {
		this.zhlxList1 = zhlxList1;
	}

	public String getCoco_shebao() {
		return coco_shebao;
	}

	public void setCoco_shebao(String coco_shebao) {
		this.coco_shebao = coco_shebao;
	}

	public String getCoco_house() {
		return coco_house;
	}

	public void setCoco_house(String coco_house) {
		this.coco_house = coco_house;
	}

	public List<String> getBlList() {
		return blList;
	}

	public void setBlList(List<String> blList) {
		this.blList = blList;
	}

	public String getCoco_cpp() {
		return coco_cpp;
	}

	public void setCoco_cpp(String coco_cpp) {
		this.coco_cpp = coco_cpp;
	}

	public List<String> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<String> monthList) {
		this.monthList = monthList;
	}

	public List<String> getMonthList1() {
		return monthList1;
	}

	public void setMonthList1(List<String> monthList1) {
		this.monthList1 = monthList1;
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

	public int getCoco_gzpay() {
		return coco_gzpay;
	}

	public void setCoco_gzpay(int coco_gzpay) {
		this.coco_gzpay = coco_gzpay;
	}

	public ListModelList<CoOfferListModel> getComodellist() {
		return comodellist;
	}

	public void setComodellist(ListModelList<CoOfferListModel> comodellist) {
		this.comodellist = comodellist;
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

}
