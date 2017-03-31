package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_SelBll;

import Model.CoBaseModel;
import Model.CoFinanceCollectModel;
import Model.CoFinanceMonthlyBillModel;
import Model.CoInvoiceModel;
import Model.CollectAmountModel;
import Model.LoginModel;
import Util.RegexUtil;
import Util.SingleBllFactory;
import Util.UserInfo;
import dal.LoginDal;

/**
 * 公司收款列表控制
 * @param args
 */
public class Cfma_CollectListslkController {

	private String client = "";
	private String company = "";
	private String clientxc= "";
	private Date startDate;
	private Date endDate;
	private String ownmonth;
	private String amount;
	private String coin_id;
	private String remark = null;
	private StringBuilder wheresql= new StringBuilder();
	private BigDecimal total = null;
	private List<String> clientnameList;
	private List<String> clientxcnameList;
	
	private List<CoBaseModel> collectList;
	private List<String> coPAccountList;
	private List<CoBaseModel> scollectList = new ArrayList<CoBaseModel>();
	
	private List<CollectAmountModel> amountlist;
	
	private List<CoFinanceCollectModel> samountlist= new ArrayList<CoFinanceCollectModel>();
	
	//private List<CoFinanceCollectModel> CoFinanceCollectlklist =new ArrayList<CoFinanceCollectModel>();
	
	
	private Cfma_SelBll csb = SingleBllFactory.getInstance().getCsb();

	int coinid = 0;

	public Cfma_CollectListslkController() throws Exception {

		init();

	}

	/**
	 * 初始化
	 * @throws Exception 
	 */
	public void init() throws Exception {

		if (coin_id != null) {
			coinid = Integer.valueOf(coin_id);
		}
		coPAccountList = csb.getCoPAccountList();
		clientnameList = csb.getClientnameListkf();
		clientxcnameList = csb.getClientnameListxc();
		
		try {
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	client=UserInfo.getUsername();
		 
		
		search();
	}

	/**
	 * 全选
	 * 
	 * @param gridId
	 * @param checkall
	 */
	@Command
	public void allcheck(@BindingParam("gridid") Grid gridId,
			@BindingParam("c") Checkbox c) {
		boolean isCheck = c.isChecked();
		int page = gridId.getPageCount();
		int size = gridId.getPageSize();
		int num = page * size;
		try {
			for (int i = 0; i <= num; i++) {
				try {
					Checkbox check = (Checkbox) gridId.getCell(i, 24)
							.getChildren().get(0);
					if (!check.isDisabled()) {
						check.setChecked(isCheck);
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * \ 批量打印收款
	 */
	@Command
	public void printInvoices(@BindingParam("gridid") Grid grid) {
		int page = grid.getPageCount();
		int size = grid.getPageSize();
		List<CoFinanceCollectModel> l = new ArrayList<CoFinanceCollectModel>();
		int num = page * size;
		try {
			for (int i = 0; i <= num; i++) {
				try {
					Checkbox check = (Checkbox) grid.getCell(i, 24)
							.getChildren().get(0);
					if (!check.isDisabled() && check.isChecked() == true) {
						List<CoFinanceCollectModel> ls = check.getValue();
						for (int j = 0; j < ls.size(); j++) {
							l.add(ls.get(j));
						}
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
		}
		Map<String, List<CoFinanceCollectModel>> map = new HashMap<String, List<CoFinanceCollectModel>>();
		map.put("cfcmList", l);
		System.out.println(l.size());
		Window window = (Window) Executions.createComponents(
				"Cfma_selectCollect.zul", null, map);
		window.doModal();

	}

	// 打印收款
	@Command
	public void printInvoice(@BindingParam("m") CoFinanceCollectModel m) {
		Map<String, CoFinanceCollectModel> map = new HashMap<String, CoFinanceCollectModel>();
		map.put("cfcm", m);
		Window window = (Window) Executions.createComponents(
				"Cfma_invoiceAddDoc.zul", null, map);
		window.doModal();
	}

	// 检查日期
	@Command
	public void checkDate() {
		if (startDate != null && endDate != null) {
			if (startDate.after(endDate)) {
				Messagebox.show("开始时间不能在结束时间之后", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
			if (startDate.equals(endDate)) {
				Calendar c = Calendar.getInstance();
				c.setTime(endDate);
				c.add(Calendar.DAY_OF_MONTH, 1);
				endDate = c.getTime();
			}
		}
		if (endDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DAY_OF_MONTH, 1);
			endDate = c.getTime();
		}
	}

	// 领取发票
	@Command
	public void CoInvoice() {

		if (startDate != null && endDate != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(endDate);
			c.add(Calendar.DAY_OF_MONTH, 1);
			if (startDate.after(endDate)) {
				Messagebox.show("开始时间不能在结束时间之后", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else {

				List<CoInvoiceModel> invoices = csb.searchCoInvoice(startDate,
						c.getTime());
				if (invoices.size() == 0) {
					Messagebox.show("该段时间没有数据", "操作提示", Messagebox.OK,
							Messagebox.NONE);
				} else {
					Map<String, List<CoInvoiceModel>> map = new HashMap<String, List<CoInvoiceModel>>();
					map.put("invoices", invoices);
					Window window = (Window) Executions.createComponents(
							"Cfma_InvoiceReceive.zul", null, map);
					window.doModal();
				}
			}
		} else {
			Messagebox
					.show("请选择收款起始日期", "操作提示", Messagebox.OK, Messagebox.NONE);
		}

	}

	// 公司收款
	@Command("companyCollect")
	public void companyCollect(@ContextParam(ContextType.VIEW) Component view,
			@BindingParam("cid") int cid,
			@BindingParam("companyname") String companyname) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		map.put("company", companyname);
		Window window = (Window) Executions.createComponents(
				"Cfma_CollectMain.zul", view, map);
		window.doModal();
	}

	// 小信封
	@Command("msg")
	@NotifyChange("scollectList")
	public void msg(@BindingParam("id") int id,
			@BindingParam("addname") String addname) {
		LoginDal d = new LoginDal();
		Map map = new HashMap<>();
		map.put("type", "收款");// 业务类型:来自WfClass的wfcl_name
		map.put("id", id);// 业务表id
		map.put("tablename", "CoFinanceCollect");// 业务表名
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(addname);
		m.setLog_id(d.getUserIDByname(addname));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
//		search(coin_id);
	}

	// 刷新列表
	@Command
	@NotifyChange("scollectList")
	public void addcollect(@BindingParam("m") CoFinanceCollectModel m) {
		Map<String, CoFinanceCollectModel> map = new HashMap<String, CoFinanceCollectModel>();
		map.put("m", m);
		Window window = (Window) Executions.createComponents(
				"Cfma_Addcollect.zul", null, map);
		window.doModal();

//		search(coin_id);
	}

	@Command
	public void getRemarks(@BindingParam("remark") String remark) {
		if (remark.equals("")) {
			this.remark = null;
			remark = null;
		}
		this.remark = remark;
	}

	@Command
	public void ownmon(@BindingParam("own") Date d) {
		if (d != null) {
			ownmonth = new SimpleDateFormat("yyyyMM").format(d);
		} else {
			ownmonth = null;
		}
	}
	
	


	@Command
	public void getClientName(@BindingParam("client") String client) {
		this.client = client;
	}
	
	@Command
	public void getClientxc(@BindingParam("client") String clientxc) {
		this.clientxc = clientxc;
	}

	/**
	 * 按条件查询
	 * 
	 * @param company
	 * @param client
	 * @throws Exception 
	 */
	@Command
	@NotifyChange("samountlist")
	public void search() throws Exception {
		// // 点击查询，先根据条件刷新列表
		
		wheresql.delete(0, wheresql.length());
		
		
		if (company != null ||!company.equals("") ) {
			try {
				 
				wheresql.append(" and  a.cid= "+ Integer.valueOf(company));
				 
			} catch (Exception e) {
				wheresql.append(" and  a.coba_company like '%"+company+"%'");
			}
		}
		if (!client.equals("")) {
			wheresql.append(" and  a.coba_client like '%"+client+"%'");
		}
		if (!clientxc.equals("")) {
			wheresql.append(" and  a.coba_gzaddname like '%"+clientxc+"%'");
		}
		
		wheresql.append("  order by  CASE when  a.coba_client='");
		wheresql.append(UserInfo.getUsername());
 
		wheresql.append( "' then 1 else 2 end,month desc, cfta_updatetime " );
		
		samountlist=csb.getCollectlk(wheresql.toString());
		

		
	}

//	@Command
//	@NotifyChange("scollectList")
//	public void scollectList() {
//		// collectList = csb.getCollectLists(ownmonth, startDate, endDate,
//		// coinid,
//		// remark, count);
//		search(coin_id);
//	}

	/**
	 * 查看今天所有收款
	 */
	@Command
	@NotifyChange("scollectList")
	public void today() {
		String addtime = new SimpleDateFormat("yyyy-MM-dd ").format(System
				.currentTimeMillis());
		Date date = new Date();

		Date eDate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(eDate);
		c.add(Calendar.DAY_OF_MONTH, 1);
		eDate = c.getTime();
		scollectList.clear();
		collectList = csb.setAmountToCollect(ownmonth, date, eDate, remark);
		List<CoFinanceCollectModel> l = csb.getTodayCollect(addtime);
		for (int j = 0; j < collectList.size(); j++) {
			for (int i = 0; i < l.size(); i++) {
				if (collectList.get(j).getCid() == l.get(i).getCid())
					scollectList.add(collectList.get(j));
			}
		}
	}

//	// 刷新列表
//	@NotifyChange("collectList")
//	@GlobalCommand
//	public void reflush(@BindingParam("cid") int cid,
//			@BindingParam("cfmb_number") String cfmb_number) {
//		collectList = csb.setAmountToCollect(ownmonth, startDate, endDate,
//				remark, cid, cfmb_number);
//	}
	
	


	public List<String> getClientnameList() {
		return clientnameList;
	}

	public List<CollectAmountModel> getAmountlist() {
		return amountlist;
	}

	public void setAmountlist(List<CollectAmountModel> amountlist) {
		this.amountlist = amountlist;
	}

	public List<CoFinanceCollectModel> getSamountlist() {
		return samountlist;
	}

	public void setSamountlist(List<CoFinanceCollectModel> samountlist) {
		this.samountlist = samountlist;
	}


	public void setClientnameList(List<String> clientnameList) {
		this.clientnameList = clientnameList;
	}
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCoin_id() {
		return coin_id;
	}

	public void setCoin_id(String coin_id) {
		this.coin_id = coin_id;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<String> getCoPAccountList() {
		return coPAccountList;
	}

	public void setCoPAccountList(List<String> coPAccountList) {
		this.coPAccountList = coPAccountList;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<CoBaseModel> getCollectList() {
		return collectList;
	}

	public void setCollectList(List<CoBaseModel> collectList) {
		this.collectList = collectList;
	}

	public List<CoBaseModel> getScollectList() {
		return scollectList;
	}

	public void setScollectList(List<CoBaseModel> scollectList) {
		this.scollectList = scollectList;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public List<String> getClientxcnameList() {
		return clientxcnameList;
	}

	public void setClientxcnameList(List<String> clientxcnameList) {
		this.clientxcnameList = clientxcnameList;
	}

	public String getClientxc() {
		return clientxc;
	}

	public void setClientxc(String clientxc) {
		this.clientxc = clientxc;
	}

}
