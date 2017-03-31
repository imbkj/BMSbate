package Controller.EmFinance;

import java.text.ParseException;
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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoFinanceWtModel;
import Model.EmFinanceZYTModel;
import Util.UserInfo;
import bll.CoFinanceManage.EmFinanceWt_SelBll;
import bll.CoFinanceManage.cfma_OperateBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.EmFinance.EmFinance_SelectBll;
import bll.EmFinance.Finance_SetupBll;

public class Finance_SetupListController {
	private String city = "", setup = "", client = "", cid = "", company = "",
			confirm = "", xiao = "";
	private Integer ownmonth = 0, year = 0, own = 0;
	private String ownmonthstr = "";
	private Finance_SetupBll bll = new Finance_SetupBll();
	private List<EmFinanceZYTModel> list = new ArrayList<EmFinanceZYTModel>();
	private List<String> wtarealist = new ArrayList<String>();// 委托地区
	private List<String> setuplist = new ArrayList<String>();// 委托机构
	private CoServiceRequestSelectBll cbll = new CoServiceRequestSelectBll();
	private List<EmFinanceZYTModel> lists = new ArrayList<EmFinanceZYTModel>();
	private Date ow;

	public Finance_SetupListController() {
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		own = cal.get(Calendar.MONTH);
		if (own > 0 && own < 10) {
			ownmonthstr = year + "0" + own;
		} else {
			ownmonthstr = year + "" + own;
		}
		ow = strtodate(ownmonthstr);
		ownmonth = Integer.parseInt(ownmonthstr);
		lists = bll.getEmFinanceSetupList(ownmonth);
		wtarealist = getWtArea();
	}

	// 获取委托地区
	private List<String> getWtArea() {
		List<String> alist = new ArrayList<String>();
		Map<String, String> map = new HashMap<String, String>();
		for (EmFinanceZYTModel m : lists) {
			if (!map.containsKey(m.getName())) {
				map.put(m.getName(), m.getName());
			}
		}
		for (String key : map.keySet()) {
			String name = map.get(key);
			alist.add(name);
		}
		return alist;
	}

	// 获取委托机构
	private List<String> getWtSetup() {
		List<String> alist = new ArrayList<String>();
		if (city != null && !city.equals("")) {
			Map<String, String> map = new HashMap<String, String>();
			for (EmFinanceZYTModel m : lists) {
				if (!map.containsKey(m.getCoab_name() + "" + m.getCabc_id())
						&& city.equals(m.getName())) {
					map.put(m.getCoab_name() + "" + m.getCabc_id(),
							m.getCoab_name());
				}
			}
			for (String key : map.keySet()) {
				String name = map.get(key);
				alist.add(name);
			}
		}
		return alist;
	}

	// 查询
	@Command
	@NotifyChange("lists")
	public void search(@BindingParam("ownmonth") Date ownmon) {
		String sql = "";
		if (city != null && !city.equals("")) {
			sql = sql + " and name='" + city + "'";
		}
		if (setup != null && !setup.equals("")) {
			sql = " and coab_name='" + setup + "'";
		}
		if (confirm != null && !confirm.equals("")) {
			// sql=sql+" and a.cid="+cid;
		}
		if (xiao != null && !xiao.equals("")) {
			// sql=sql+" and a.cid="+cid;
		}
		if (ownmon != null && !ownmon.equals("")) {
			sql = sql + " and ownmonth=" + datetostr(ownmon);
		}
		lists = bll.getEmFinanceSetupList(ownmonth);
		lists = getInfo(city, setup);
	}

	//
	private List<EmFinanceZYTModel> getInfo(String city, String setup) {
		List<EmFinanceZYTModel> lt = new ArrayList<EmFinanceZYTModel>();
		if (city != null && !city.equals("")) {
			for (EmFinanceZYTModel m : lists) {
				if (m.getName().equals(city)) {
					if (setup != null && !setup.equals("")) {
						if (m.getCoab_name().equals(setup)) {
							lt.add(m);

						}
					} else {
						lt.add(m);
					}
				}
			}
		}
		return lt;
	}

	// 根据填写的所属月份获取委托地区
	@Command
	@NotifyChange({ "wtarealist", "setuplist" })
	public void changearea(@BindingParam("ownmonth") Date ownmonth) {
		String sql = "";
		if (datetostr(ownmonth) != null && !datetostr(ownmonth).equals("")) {
			List<EmFinanceZYTModel> listsl = bll.getEmFinanceSetupList(Integer
					.parseInt(datetostr(ownmonth)));
			String idstr = "";
			wtarealist = bll.getCoabCity(Integer.parseInt(datetostr(ownmonth)));
		}

	}

	// 根据填写的所属月份获取委托地区
	@Command
	@NotifyChange("setuplist")
	public void changesetup(@BindingParam("ownmonth") Date ownmonth) {
		String sql = "";
		if (ownmonth != null) {
			setuplist = getWtSetup();
		}
	}

	// 生成台帐
	@Command
	@NotifyChange("lists")
	public void createbill(@BindingParam("model") final EmFinanceZYTModel model) {
		// 先同步台账
//		Messagebox.show("如已生成账单请先撤销账单?", "操作提示", new Messagebox.Button[] {
//				Messagebox.Button.OK, Messagebox.Button.CANCEL },
//				Messagebox.QUESTION,
//				new EventListener<Messagebox.ClickEvent>() {
//					@Override
//					public void onEvent(ClickEvent event) throws Exception {
//						if (Messagebox.Button.OK.equals(event.getButton())) {
							cfma_OperateBll obll = new cfma_OperateBll();
							EmFinance_SelectBll sbll = new EmFinance_SelectBll();
							String sql = " and coab_name='"
									+ model.getCoab_name()
									+ "' and coab_city='" + model.getName()
									+ "'";
							int[] ints = sbll.getCocoID(sql);// 根据cid获取合同编号
							Integer y = 0, k = 0;
							for (int i = 0; i < ints.length; i++) {
								Integer cid = sbll
										.getCidByCocoId(" and coco_id="
												+ ints[i]);
								String[] str = obll.createBillByCp(cid,
										ownmonth, ints[i],
										UserInfo.getUsername(), "", false);
								if (str[0] == "1") {
									y = 1;
								} else if (str[0] == "0") {
									k = k + 1;
								}
							}
							Messagebox.show("生成账单成功", "提示", Messagebox.OK,
									Messagebox.INFORMATION);
							lists = bll.getEmFinanceSetupList(ownmonth);
					//	}
					//}
//				});
	}

	// 确认台帐
	@Command
	@NotifyChange("lists")
	public void confirmbill(@BindingParam("model") EmFinanceZYTModel model) {
		cfma_OperateBll obll = new cfma_OperateBll();
		EmFinance_SelectBll sbll = new EmFinance_SelectBll();
		EmFinanceWt_SelBll wtbll = new EmFinanceWt_SelBll();
		String sql = " and coab_name='" + model.getCoab_name()
				+ "' and coab_city='" + model.getName() + "'";
		int[] ints = sbll.getCocoID(sql);// 根据cid获取合同编号
		for (int i = 0; i < ints.length; i++) {
			String billnum = wtbll.getBillNumberByCp(ownmonth,
					ints[i]);
			if (!billnum.equals("0")) {
				obll.confirmBill(billnum, 1, UserInfo.getUsername());
			}
		}
		Messagebox.show("确认账单成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		lists = bll.getEmFinanceSetupList(ownmonth);
	}

	// 取消台帐
	@Command
	public void delbill(@BindingParam("model") final EmFinanceZYTModel model) {
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					cfma_OperateBll obll = new cfma_OperateBll();
					EmFinance_SelectBll sbll = new EmFinance_SelectBll();
					EmFinanceWt_SelBll wtbll = new EmFinanceWt_SelBll();
					String sql = " and coab_name='" + model.getCoab_name()
							+ "' and coab_city='" + model.getName() + "'";
					int[] ints = sbll.getCocoID(sql);// 根据cid获取合同编号
					for (int i = 0; i < ints.length; i++) {
						String billnum = wtbll.getBillNumberByCp(
								ownmonth, ints[i]);
						if (!billnum.equals("0")) {
							obll.delBill(billnum);
						}
					}
					Messagebox.show("取消成功", "提示", Messagebox.OK,
							Messagebox.INFORMATION);
					//lists = bll.getEmFinanceSetupList(ownmonth);
				}
			}
		};
		Messagebox.show("是否确定取消", "提示", new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);

	}

	public List<EmFinanceZYTModel> getList() {
		return list;
	}

	public void setList(List<EmFinanceZYTModel> list) {
		this.list = list;
	}

	public List<String> getWtarealist() {
		return wtarealist;
	}

	public void setWtarealist(List<String> wtarealist) {
		this.wtarealist = wtarealist;
	}

	public List<String> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist(List<String> setuplist) {
		this.setuplist = setuplist;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSetup() {
		return setup;
	}

	public void setSetup(String setup) {
		this.setup = setup;
	}

	public String getXiao() {
		return xiao;
	}

	public void setXiao(String xiao) {
		this.xiao = xiao;
	}

	public List<EmFinanceZYTModel> getLists() {
		return lists;
	}

	public void setLists(List<EmFinanceZYTModel> lists) {
		this.lists = lists;
	}

	public Date getOw() {
		return ow;
	}

	public void setOw(Date ow) {
		this.ow = ow;
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

	private Date strtodate(String d) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		if (d != null && !d.equals("")) {
			try {
				date = sdf.parse(d);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}
}
