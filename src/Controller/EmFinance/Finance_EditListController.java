package Controller.EmFinance;

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
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceWtModel;
import Model.EmFinanceWtModel;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.EmFinance.EmFinance_SelectBll;

public class Finance_EditListController {
	private String cid = "", company = "", client = "", gid = "",
			emba_name = "", idcard = "", total = "";
	private EmFinance_SelectBll bll = new EmFinance_SelectBll();
	private CoFinanceWtModel model = new CoFinanceWtModel();
	private Integer ownmonth = 0, year = 0, own = 0;
	private String ownmonthstr = "";
	private List<String> loginlist = new ArrayList<String>();// 客服
	private CoServiceRequestSelectBll cbll = new CoServiceRequestSelectBll();

	public Finance_EditListController() {
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		own = cal.get(Calendar.MONTH);
		if (own > 0 && own < 10) {
			ownmonthstr = year + "0" + own;
		} else {
			ownmonthstr = year + "" + own;
		}
		loginlist = cbll.getLoginlist();
		ownmonth = Integer.parseInt(ownmonthstr);
		model = bll.getModels(0);
	}

	// 查询
	@Command
	@NotifyChange("model")
	public void search(@BindingParam("ownmonth") Date ownm) {
		CoFinanceWtModel m = new CoFinanceWtModel();
		if (ownm != null && !ownm.equals("")) {
			this.ownmonth = Integer.parseInt(datetostr(ownm));
		} else {
			this.ownmonth = 0;
		}
		m = bll.getModels(this.ownmonth);
		List<EmFinanceWtModel> li = getList(m.getEfWtList());
		if(total!=null&&!total.equals(""))
		{
			li=ifchae(li);
		}
		model.setEfWtList(li);
	}
	
	//查询合计差额
	private List<EmFinanceWtModel> ifchae(List<EmFinanceWtModel> list)
	{
		List<EmFinanceWtModel> l=new ArrayList<EmFinanceWtModel>();
		if(total!=null||!total.equals(""))
		{
			BigDecimal zi=BigDecimal.ZERO;
			if(total.equals("有差额"))
			{
				for(EmFinanceWtModel m:list)
				{
					if(m.getFifztotal().compareTo(zi)!=0)
					{
						l.add(m);
					}
				}
			}
			else if(total.equals("无差额"))
			{
				for(EmFinanceWtModel m:list)
				{
					if(m.getFifztotal().compareTo(zi)==0)
					{
						l.add(m);
					}
				}
			}
			else if(total.equals("差额大于0"))
			{
				for(EmFinanceWtModel m:list)
				{
					if(m.getFifztotal().compareTo(zi)>0)
					{
						l.add(m);
					}
				}
			}
			else if(total.equals("差额小于0"))
			{
				for(EmFinanceWtModel m:list)
				{
					if(m.getFifztotal().compareTo(zi)<0)
					{
						l.add(m);
					}
				}
			}
		}
		return l;
	}

	private List<EmFinanceWtModel> getList(List<EmFinanceWtModel> list) {
		List<EmFinanceWtModel> li = new ArrayList<EmFinanceWtModel>();
		List<Integer> cidlist=new ArrayList<Integer>();
		if(client!=null&&!client.equals(""))
		{
			 cidlist=bll.getCidByClient(client.substring(1));
		}
		for (int i = 0; i < list.size(); i++) {
			EmFinanceWtModel ml = list.get(i);
			int k = 0;
			if (cid != null && !cid.equals("")) {
				if (ml.getCid() == Integer.parseInt(cid)) {
					k = 1;
				} else {
					k = 0;
					continue;
				}
			}
			if (company != null && !company.equals("")) {
				if (ml.getCoba_company().equals(company)) {
					k = 1;
				} else {
					k = 0;
					continue;
				}
			}
			if (gid != null && !gid.equals("")) {
				if (ml.getGid() == Integer.parseInt(gid)) {
					k = 1;
				} else {
					k = 0;
					continue;
				}
			}
			if (emba_name != null && !emba_name.equals("")) {
				if (ml.getEmfi_name().equals(emba_name)) {
					k = 1;
				} else {
					k = 0;
					continue;
				}
			}
			if (idcard != null && !idcard.equals("")) {
				if (ml.getEmfi_idcard().equals(idcard)) {
					k = 1;
				} else {
					k = 0;
					continue;
				}
			}
			if(client!=null&&!client.equals(""))
			{
				for(Integer ncid:cidlist)
				{
					if(ml.getCid().equals(ncid))
					{
						k = 1;
						break;
					}
					else {
						k = 0;
						continue;
					}
				}
				if(k<=0)
				{
					continue;
				}
			}
			li.add(ml);
		}
		
		return li;
	}

	// 打开补交报价单调整页面
	@Command("openImprove")
	public void openImprove(@BindingParam("gid") Integer gid,@BindingParam("cid") Integer cid,@BindingParam("ownmonth") Integer ownmonth) {
		Map map = new HashMap<>();
		map.put("gid", gid);
		map.put("cid", cid);
		map.put("ownmonth", ownmonth);
		Window window;
		window = (Window) Executions.createComponents("Finance_Improve.zul",
				null, map);
		window.doModal();
	}

	public CoFinanceWtModel getModel() {
		return model;
	}

	public void setModel(CoFinanceWtModel model) {
		this.model = model;
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

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	// 日期转字符串
	private String datetostr(Date date) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		if (date != null && !date.equals("")) {
			str = sdf.format(date);
		}
		return str;
	}
}
