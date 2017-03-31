package Controller.CoFinanceManage.CoInvoice;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import bll.CoFinanceManage.Cfma_SelBll;

import Model.CoInvoiceModel;
import Util.RegexUtil;

/**
 * 发票领取
 * 
 * @author Administrator
 * 
 */
public class Cfma_InvoiceReceiveController {

	private final List<CoInvoiceModel> invoices = (List<CoInvoiceModel>) Executions
			.getCurrent().getArg().get("invoices");
	private Cfma_SelBll bll = new Cfma_SelBll();
	private String ownmonth = "";
	private String client = "";
	private Date date = null;
	private String username = "";
	private String invoiceid = "";
	private String codeid = "";
	private BigDecimal total = null;
	private String receivename = "";
	private List<CoInvoiceModel> sinvoices = new ArrayList<CoInvoiceModel>();

	public Cfma_InvoiceReceiveController(){
		search();
	}
	
	
	public List<CoInvoiceModel> getInvoices() {
		return invoices;
	}

	
	//添加发票领取人
	@Command
	public void save(@BindingParam("m") CoInvoiceModel m) {
		int count = bll.addInvoiceReceive(m);
	}

	//根据条件筛选
	@Command
	@NotifyChange("sinvoices")
	public void search() {
		if (invoices.size() > 0) {
			sinvoices.clear();
			for (CoInvoiceModel m : invoices) {
				if (ownmonth != null) {
					if (!RegexUtil.isExists(ownmonth, m.getOwnmonth() + "")) {
						continue;
					}
				}
				if (client != null) {
					if (!RegexUtil.isExists(client, m.getCoin_addname())) {
						continue;
					}
				}
				if (date != null) {
					String strdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
							.format(date);
					if (!RegexUtil.isExists(strdate, m.getCoin_idate())) {
						continue;
					}
				}
				if (username != null) {
					if (!RegexUtil.isExists(username, m.getCoin_title())) {
						continue;
					}
				}
				if (invoiceid != null) {
					if (!RegexUtil.isExists(invoiceid, m.getCoin_invoiceid())) {
						continue;
					}
				}
				if (codeid != null) {
					if (!RegexUtil.isExists(codeid, m.getCoin_codeid())) {
						continue;
					}
				}
				if (total != null) {
					if (!RegexUtil.isExists(total.toString(), m.getCoin_total()
							.toString())) {
						continue;
					}
				}
				if (receivename != null) {
					if (!RegexUtil.isExists(receivename, m.getReceivename())) {
						continue;
					}
				}
				sinvoices.add(m);
			}
		}
	}

	
	public List<CoInvoiceModel> getSinvoices() {
		return sinvoices;
	}


	public void setSinvoices(List<CoInvoiceModel> sinvoices) {
		this.sinvoices = sinvoices;
	}


	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(String invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getCodeid() {
		return codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getReceivename() {
		return receivename;
	}

	public void setReceivename(String receivename) {
		this.receivename = receivename;
	}

}
