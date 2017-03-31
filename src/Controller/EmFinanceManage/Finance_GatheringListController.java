package Controller.EmFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import sun.text.normalizer.UTF16;

import Model.CoBaseModel;
import Model.CoBaseModel;
import Model.CoFinanceCollectImportModel;
import Model.SbGatherModel;
import Util.SingleBllFactory;
import bll.CoFinanceManage.Cfma_CollectImportBll;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.EmFinanceManage.GatherInfoBll;
import bll.EmFinanceManage.GatherInfoNewBll;

public class Finance_GatheringListController {
	private DecimalFormat myformat = new DecimalFormat("0");
	private List<CoBaseModel> list = new ArrayList<CoBaseModel>();
	// private GatherInfoBll bll = new GatherInfoBll();
	private GatherInfoNewBll bll = new GatherInfoNewBll();
	private Date startDate;
	private Date endDate, ownmonth;
	private String cid = "", company = "", client = "";
	private BigDecimal total = BigDecimal.ZERO;
	private BigDecimal fee;
	private String colsql = "";
	private String inyongyou = "";
	private Date nowdate = new Date();
	private String remark = "";

	public Finance_GatheringListController() {
		// startDate=new Date();
		// endDate=new Date();
		if (startDate != null && endDate != null) {
			// CoFinanceCollect表记录每笔公司收款
//			colsql = colsql 
//					+ " and convert(varchar(10),cfss_addtime,120) BETWEEN "
//					+ " '" + dateToStr(startDate) + "' and '"
//					+ dateToStr(endDate) + "'";
		}
		list = bll.setAmountToCollectssNew(colsql);

	}

	@Command
	public void checkCb(@BindingParam("cel") Cell cell) {
		Row row = (Row) cell.getParent();
		if (row.getChildren() != null && row.getChildren().size() > 0) {
			Cell rcell = (Cell) row.getChildren().get(0);
			if (rcell.getChildren() != null && rcell.getChildren().size() > 1) {
				Checkbox cb = (Checkbox) rcell.getChildren().get(1);
				if (cb.isChecked()) {
					cb.setChecked(false);
				} else {
					cb.setChecked(true);
				}
			}
		}
	}

	@Command
	@NotifyChange("total")
	public void selectLabel(@BindingParam("cel") Cell cell,
			@BindingParam("type") String type) {
		if (cell.getChildren() != null && cell.getChildren().size() > 0) {
			Label lab = (Label) cell.getChildren().get(0);
			if (lab.getStyle() != null
					&& (lab.getStyle().equals("color:red;") || lab.getStyle()
							.equals("color:#000099;"))) {// 消除
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.subtract(new BigDecimal(newfee));
				}

				lab.setStyle("color:#000000;");
				cell.setStyle("background-color:#f5fafe;");
				if (type != null && type.equals("total")) {
					lab.setStyle("color:#0000FF;");
				}
			} else {// 添加
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.add(new BigDecimal(newfee));
				}
				lab.setStyle("color:red;");
				cell.setStyle("background-color:#1C120E;");
				if (type != null && type.equals("total")) {
					lab.setStyle("color:#000099;");
					cell.setStyle("background-color:#CC3333;");
				}
			}
		}

	}

	@Command
	@NotifyChange("total")
	public void selectTotall(@BindingParam("cel") Cell cell) {
		if (cell.getChildren() != null && cell.getChildren().size() > 0) {
			Label lab = (Label) cell.getChildren().get(0);
			if (lab.getStyle() != null && lab.getStyle().equals("color:red;")) {// 消除
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.subtract(new BigDecimal(newfee));
				}
				lab.setStyle("color:#0000FF;");
				cell.setStyle("background-color:#f5fafe;");
			} else {// 添加
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.add(new BigDecimal(newfee));
				}
				lab.setStyle("color:#0000FF;");
				cell.setStyle("background-color:#CC3333;");
			}
		}

	}

	@Command
	public void addoffer(@BindingParam("gd") Grid gd) {
		List<CoBaseModel> checklist = new ArrayList<CoBaseModel>();
		int num = gd.getPageSize();
		if (list.size() < num) {
			num = list.size();
		}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					if (ck.isChecked()) {
						CoBaseModel m = ck.getValue();
						checklist.add(m);
					}
				}
			}
		}
		if (checklist.size() > 0) {
			Map map = new HashMap<>();
			map.put("list", checklist);
			map.put("ownmonth", datesToStr(ownmonth));
			Window window = (Window) Executions.createComponents(
					"Finance_GatherInfoList.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void addsk(@BindingParam("gd") Grid gd) {

		if ((cid.equals("") || cid.equals("0")) && company.equals("")) {

			Messagebox.show("请先输入公司名或公司编号。", "提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {

			Map map = new HashMap<>();
			map.put("cid", cid);
			map.put("company", company);

			// Window window = (Window) Executions.createComponents(
			// "../CoFinanceManage/Cfma_CollectssLog.zul", null, map);

			Window window = (Window) Executions.createComponents(
					"../CoFinanceManage/Cfma_CoGathering.zul", null, map);

			window.doModal();
		}
	}

	// 弹出修改页面
	@Command
	@NotifyChange("list")
	public void edit(@BindingParam("model") CoBaseModel model) {
		Map map = new HashMap<>();
		map.put("cid", model.getCid());
		map.put("type", "1");
		Window window = (Window) Executions.createComponents(
				"Finance_GatherInfoEdit.zul", null, map);
		window.doModal();
		String owns = "";
		if (ownmonth != null) {
			owns = datesToStr(ownmonth);
			colsql = colsql + " and ownmonth=" + owns;
		}

		list = bll.setAmountToCollectssNew(colsql);
		total = BigDecimal.ZERO;
	}

	@Command
	@NotifyChange({ "list", "total" })
	public void search() {
		colsql = "";
		total = BigDecimal.ZERO;
		if (cid != null && !cid.equals("")) {
			colsql = colsql + " and a.cid=" + cid;
		}
		if (company != null && !company.equals("")) {
			colsql = colsql + " and coba_company like '%" + company + "%'";
		}
		if (client != null && !client.equals("")) {
			colsql = colsql + " and coba_client like '%" + client + "%'";
		}

		if (startDate != null && endDate != null) {
			colsql = colsql
					+ " and convert(varchar(10),cfss_addtime,120) BETWEEN "
					+ " '" + dateToStr(startDate) + "' and '"
					+ dateToStr(endDate) + "'";
		}
		String owns = "";
		if (ownmonth != null) {
			owns = datesToStr(ownmonth);
			colsql = colsql + " and ownmonth=" + owns;
		}
		if (inyongyou != null && !inyongyou.equals("")) {
			if (inyongyou.equals("未录用友")) {
				colsql = colsql + " and cfss_yystate=0";
			} else {
				colsql = colsql + " and cfss_yystate=1";
			}
		}
		if (remark != null && !remark.equals("")) {
			colsql = colsql + " and cfss_remark like '%" + remark + "%'";
		}
		list = bll.setAmountToCollectssNew(colsql);
		if (fee != null) {
			List<CoBaseModel> nlist = new ArrayList<CoBaseModel>();
			for (CoBaseModel cm : list) {
				BigDecimal totalp = new BigDecimal(myformat.format(cm
						.getAmount().getCfmb_TotalPaidIn()));
				BigDecimal totafee = new BigDecimal(myformat.format(fee));
				if (totalp.compareTo(totafee) == 0) {
					nlist.add(cm);
				}
			}
			list = nlist;
		}
	}

	// 弹出修改页面
	@Command
	@NotifyChange("list")
	public void editUfid(@BindingParam("model") CoBaseModel model) {
		Map map = new HashMap<>();
		map.put("modelold", model);
		map.put("type", "1");
		Window window = (Window) Executions.createComponents(
				"Finance_GatherInfoEdit.zul", null, map);
		window.doModal();
		list = bll.setAmountToCollectssNew(colsql);
		total = BigDecimal.ZERO;
	}

	@Command
	public void checkAll(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox allCk) {
		int num = gd.getPageSize();
		if (list.size() < num) {
			num = list.size();
		}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					ck.setChecked(allCk.isChecked());
				}
			}
		}
	}

	
	public List<CoBaseModel> getList() {
		return list;
	}

	public void setList(List<CoBaseModel> list) {
		this.list = list;
	}

	public GatherInfoNewBll getBll() {
		return bll;
	}

	public void setBll(GatherInfoNewBll bll) {
		this.bll = bll;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getInyongyou() {
		return inyongyou;
	}

	public void setInyongyou(String inyongyou) {
		this.inyongyou = inyongyou;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	private String dateToStr(Date date) {
		String datestr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (date != null) {
			try {
				datestr = format.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datestr;
	}

	private String datesToStr(Date date) {
		String datestr = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		if (date != null) {
			try {
				datestr = format.format(date);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return datestr;
	}

}
