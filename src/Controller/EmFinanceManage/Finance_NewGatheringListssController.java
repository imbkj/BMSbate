package Controller.EmFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.EmFinanceManage.GatherInfoNewBll;

public class Finance_NewGatheringListssController {
	private DecimalFormat myformat = new DecimalFormat("0");
	private List<CoBaseModel> list = new ArrayList<CoBaseModel>();
	private GatherInfoNewBll bll1 = new GatherInfoNewBll();
	private Date startDate;
	private Date endDate, ownmonth;
	private String cid = "", company = "", client = "";
	private BigDecimal total = BigDecimal.ZERO;
	private BigDecimal fee;
	private String colsql = "";
	private String inyongyou = "";
	private Date nowdate = new Date();
	private String remark="";

	public Finance_NewGatheringListssController() {
		startDate=new Date();
		endDate=new Date();
		if (startDate != null && endDate != null) {
			//CoFinanceCollect表记录每笔公司收款
			colsql=colsql+" and convert(varchar(10),cfss_addtime,120) BETWEEN " +
					" '"+dateToStr(startDate)+"' and '"+dateToStr(endDate)+"'";
		}
		list = bll1.setAmountToCollectssNew(colsql);
	}

	@Command
	public void checkCb(@BindingParam("cel") Cell cell,@BindingParam("m") CoBaseModel model) {
		Row row = (Row) cell.getParent();
		if (row.getChildren() != null && row.getChildren().size() > 0) {
			Cell rcell = (Cell) row.getChildren().get(0);
			if (rcell.getChildren() != null && rcell.getChildren().size() > 1) {
				Checkbox cb = (Checkbox) rcell.getChildren().get(1);
				if (cb.isChecked()) {
					model.setChecked(false);
					cb.setChecked(false);
				} else {
					model.setChecked(true);
					cb.setChecked(true);
				}
			}
		}
	}

	@Command
	@NotifyChange("total")
	public void selectLabel(@BindingParam("cel") Cell cell,@BindingParam("type") String type) {
		if (cell.getChildren() != null && cell.getChildren().size() > 0) {
			Label lab = (Label) cell.getChildren().get(0);
			if (lab.getStyle() != null && (lab.getStyle().equals("color:red;")
					||lab.getStyle().equals("color:#000099;"))) {// 消除
				String newfee = "0.00";
				if (lab.getValue() != null && !lab.getValue().equals("")) {
					newfee = lab.getValue();
					total = total.subtract(new BigDecimal(newfee));
				}
				
				lab.setStyle("color:#000000;");
				cell.setStyle("background-color:#f5fafe;");
				if(type!=null&&type.equals("total"))
				{
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
				if(type!=null&&type.equals("total"))
				{
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

		CoBase_SelectBll cobasebll=new CoBase_SelectBll();
		Cfma_SelBll cbll = new Cfma_SelBll();
		 CoBaseModel cobasemodel = null;
		 
		if ((cid.equals("")|| cid.equals("0"))&&company.equals(""))
		{
			
			Messagebox.show("请先输入公司名或公司编号。", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			 
			 List<CoBaseModel> cobasemodellsit =new ArrayList<CoBaseModel>();
			if (!company.equals(""))
			{
			 cobasemodellsit=cobasebll.getCobaseinfo(" and coba_servicestate=1 and a.coba_company like '%"+company+"%'");
			}
			 
			else
			{
				 cobasemodellsit=cobasebll.getCobaseinfo(" and coba_servicestate=1 and a.cid="+cid+"");
			}
			
			
			
			
			 if (cobasemodellsit.size()==0)
			{
				Messagebox.show("系统无法查询到该公司，请核查！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				
			 
				return;
			}
			else if (cobasemodellsit.size()>1)
			{
				Messagebox.show("系统查询到2个以上记录，请核查！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			else
			{
				
				cobasemodel=cobasemodellsit.get(0);
				
				Map map = new HashMap<>();
				map.put("cid", cid);
				map.put("company", company);
			
		
				Window window = (Window) Executions.createComponents(
						"../CoFinanceManage/Cfma_CoGathering.zul", null, map);
				
				
				
				window.doModal();
	
			}
		}
	
	}
	
	//修改开票状态
	@Command("editFpstate")
	@NotifyChange({ "list", "total" })
	public void editFpstate(@BindingParam("m") CoBaseModel m){
		System.out.println(m.getCfss_fpstate());
		System.out.println(m.getCfss_cfso_id());
		 Integer cfss_fpstate=0;
		 total = BigDecimal.ZERO;
		if(m.getCfss_fpstate().equals("未开票")){
			cfss_fpstate=1;
		}else{
			cfss_fpstate=0;
		}
		int a=bll1.updateCfss_fpstate(m.getCfss_cfso_id(), cfss_fpstate);
		list = bll1.setAmountToCollectssNew(colsql);
		if(a>0){
			
		}else{
			Messagebox.show("未能修改开票状态！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
	}
	
	//添加发票信息
	@Command
	@NotifyChange({ "list", "total" })
	public void invoice(@BindingParam("a") CoBaseModel m){
		Map map = new HashMap<>();
		map.put("model", m);
		Window window = (Window) Executions.createComponents(
				"Finance_Invoice.zul", null, map);
		window.doModal();
		//search();
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

		list = bll1.setAmountToCollectssNew(colsql);
		total = BigDecimal.ZERO;
	}

	@Command
	@NotifyChange({ "list", "total" })
	public void search() {
		UserInfo.getUsername();
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
			colsql=colsql+" and convert(varchar(10),cfss_addtime,120) BETWEEN " +
					" '"+dateToStr(startDate)+"' and '"+dateToStr(endDate)+"'";
		}
		String owns = "";
		if (ownmonth != null) {
			owns = datesToStr(ownmonth);
			colsql = colsql + " and ownmonth=" + owns;
		}
		if (inyongyou != null && !inyongyou.equals("")) {
			if (inyongyou.equals("未录用友")) {
				colsql=colsql+" and cfss_yystate=0";
			}else
			{
				colsql=colsql+" and cfss_yystate=1";
			}
		}
		if(remark!=null&&!remark.equals(""))
		{
			 
				 
				
				colsql = colsql + " and cfss_remark like '%" + remark + "%'";
		 
		}
		list = bll1.setAmountToCollectssNew(colsql);
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
	@SuppressWarnings("unchecked")
	@Command
	@NotifyChange("list")
	public void editUfid(@BindingParam("model") CoBaseModel model) {
		Map map = new HashMap<>();
		map.put("cfss_cfso_id", model.getCfss_cfso_id());
		//map.put("type", "1");
		Window window = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_CoGatheringmod.zul", null, map);
		window.doModal();
		list = bll1.setAmountToCollectssNew(colsql);
		total = BigDecimal.ZERO;
	}
	
	@Command
	public void printGa(){
		List<CoBaseModel> clist = new ListModelList<>();
		for (CoBaseModel model : list) {
			if (model.getChecked()!=null && model.getChecked()) {
				clist.add(model);
				
			}
		}
		if (clist.size()>0) {
			Map<String, List<CoBaseModel>> map = new HashMap<String, List<CoBaseModel>>();
			map.put("cfcmList", clist);
			Window window = (Window) Executions.createComponents(
					"../CoFinanceManage/Cfma_invoiceAddDoc.zul", null, map);
			window.doModal();
		}else {
			Messagebox.show("请选择收款!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
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
					CoBaseModel m = ck.getValue();
					m.setChecked(allCk.isChecked());
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
		return bll1;
	}

	public void setBll(GatherInfoNewBll bll1) {
		this.bll1 = bll1;
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
