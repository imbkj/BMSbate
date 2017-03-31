package Controller.EmSalary;

import impl.CheckStringImpl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.sun.istack.internal.FinalArrayList;

import service.CheckStringService;

import Model.CoBaseModel;
import Model.CoFinanceMonthlyBillSortAccountModel;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import bll.EmSalary.EmSalaryInfoListBll;
import bll.EmSalary.EmSalary_SalarySelBll;

public class EmSalaryInfoListController extends SelectorComposer<Component> {

	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());

	/**
	 * 
	 */
	@Wire
	private Grid salarygrid;

	@Wire
	private Columns gridcols;

	@Wire
	private Rows gridrows;

	@Wire
	private Button btSearch;

	private List<EmSalaryDataModel> emsdate = new ArrayList<EmSalaryDataModel>();
	private List<EmSalaryDataModel> emsdatesum = new ArrayList<EmSalaryDataModel>();
	private List<EmSalaryInfoModel> emsdateinfo = new ArrayList<EmSalaryInfoModel>();
	private List<EmSalaryInfoModel> emsdateinfosum = new ArrayList<EmSalaryInfoModel>();
	private List<EmSalaryBaseAddItemModel> cobtlist = new ArrayList<EmSalaryBaseAddItemModel>();
	private List<EmSalaryBaseAddItemModel> cobtlistsum = new ArrayList<EmSalaryBaseAddItemModel>();
	private List<EmSalaryBaseAddItemModel> itemList;
	private List<EmSalaryBaseAddItemModel> itemListsum;
	private EmSalaryInfoListBll emsbll = new EmSalaryInfoListBll();
	private List<CoFinanceMonthlyBillSortAccountModel> cofinList;
	private CoBaseModel cm;

	// private String ownmonth = "201312";
	private int cosname = cid;
	private String salarystate = "";
	private String embaname = "";
	private String gid = "";
	private String emba_idcard = "";
	// private String cid = "1091";

	private List ownmonthlist;
	private List cobaselist;
	private List salarystatelist;
	private int countsele;
	private int Scount;
	private static final long serialVersionUID = 1L;

	public EmSalaryInfoListController() throws SQLException {
		cm = emsbll.getCobaseByCid(cid);
		cofinList = getCoFinanceMonthlyBillSortAccount();
		ownmonthlist = emsbll.getownmonth("");
		salarystatelist = new ArrayList();
		salarystatelist.add("已发放");
		salarystatelist.add("未发放");

	}

	// 获取公司当月财务收款情况
	private List<CoFinanceMonthlyBillSortAccountModel> getCoFinanceMonthlyBillSortAccount() {
		List<CoFinanceMonthlyBillSortAccountModel> list = null;
		try {
			list = emsbll.getCwReceivable(ownmonth, cid);
			// 如无数据则填充为0的数据
			if (list.size() == 0) {
				CoFinanceMonthlyBillSortAccountModel m = new CoFinanceMonthlyBillSortAccountModel();
				m.setCfsa_cpac_name("税后工资");
				m.setCfsa_Receivable(BigDecimal.ZERO);
				m.setCfsa_PaidIn(BigDecimal.ZERO);
				m.setImbalance(BigDecimal.ZERO);
				list.add(m);

				m = new CoFinanceMonthlyBillSortAccountModel();
				m.setCfsa_cpac_name("个调税");
				m.setCfsa_Receivable(BigDecimal.ZERO);
				m.setCfsa_PaidIn(BigDecimal.ZERO);
				m.setImbalance(BigDecimal.ZERO);
				list.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 根据所属月份获取公司列表
	@Command("cobaselsit")
	@NotifyChange("cobaselist")
	public void cobaselsit(@BindingParam("contact") String ownmonth,
			@BindingParam("cb") Combobox cb) throws SQLException {
		cb.setValue("");
		setCobaselist(emsbll.getcobases(Integer.parseInt(ownmonth)));

	}

	// 根据获取绑定公司简称
	// @Command("scosname")
	// public void scosname(@BindingParam("contact") String ownmonth)
	// throws SQLException {
	//
	// boolean key = ownmonth.contains("|");
	// if (key)
	// {
	// setCosname(ownmonth.split("\\|")[1]);
	// }
	// else
	// {
	// setCosname(ownmonth);
	// }
	// }

	@Command("search")
	@NotifyChange({ "emsdate", "emsdateinfo", "emsdatesum" })
	public void search(@BindingParam("gd") Grid gd,
			@BindingParam("gdsum") Grid gdsum) throws Exception {
		// 清除grid
		deletegrid(gd);
		deletegridsum(gdsum);

		try {
			// 动态生成表头
			colsInit(gd);
			// 动态生成行
			rowsInit(gd);
			// 生成统计数据
			colsInitsum(gdsum);
			rowsInitsum(gdsum);
		} catch (Exception e) {
			throw e;
		}

	}

	private String getwherestr(String embaname, String salarystate) {
		StringBuilder str = new StringBuilder();
		if (!salarystate.equals("")) {
			if (salarystate.equals("未发放")) {
				str.append(" and esda_payment_state<2 ");
			} else {
				str.append(" and esda_payment_state=2 ");
			}

		}

		if (!embaname.equals("")) {
			str.append(" and emba_name like '%");
			str.append(embaname);
			str.append("%' ");
		}

		if (!"".equals(gid)) {
			str.append(" and ed.gid =");
			str.append(gid);

		}

		if (!"".equals(emba_idcard)) {
			str.append(" and eb.emba_idcard like '%");
			str.append(emba_idcard);
			str.append("%' ");
		}

		System.out.println(str);
		return str.toString();
	}

	private void deletegrid(Grid salarygrid) {
		System.out.println(salarygrid.getColumns().getChildren().size());
		Rows rows = salarygrid.getRows();
		Columns cols = salarygrid.getColumns();

		List<Component> rownull = rows.getChildren();
		for (int i = rownull.size() - 1; i >= 0; i--) {
			rows.removeChild(rownull.get(i));
		}
		List<Component> colnull = cols.getChildren();
		System.out.println(colnull.size());
		for (int i = colnull.size() - 1; i >= 0; i--) {
			cols.removeChild(colnull.get(i));
		}
	}

	private void deletegridsum(Grid salarygrid) {
		// System.out.println(salarygrid.getColumns().getChildren().size());
		Rows rows = salarygrid.getRows();
		Columns cols = salarygrid.getColumns();

		List<Component> rownull = rows.getChildren();
		for (int i = rownull.size() - 1; i >= 0; i--) {
			rows.removeChild(rownull.get(i));
		}
		List<Component> colnull = cols.getChildren();
		System.out.println(colnull.size());
		for (int i = colnull.size() - 1; i >= 0; i--) {
			cols.removeChild(colnull.get(i));
		}
	}

	@Command("colsInit")
	@NotifyChange("cobtlist")
	public void colsInit(@BindingParam("self") Grid gridcols)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		// 生成表头
		// 生成Column
		cobtlist = emsbll.getItemInfoByCidMonth(cosname, ownmonth, 1);
		Scount = cobtlist.size();
		Column col6 = new Column();
		col6.setParent(gridcols.getColumns());
		col6.setAlign("center");
		col6.setWidth("40px");
		// col6.setStyle("cursor:pointer");

		Column col0 = new Column();

		col0.setParent(gridcols.getColumns());
		// col0.setSort("auto(name)");
		Label lab0 = new Label();
		lab0.setParent(col0);
		lab0.setValue("员工姓名");
		// lab0.setStyle("cursor:pointer");
		col0.setWidth("120px");

		Column col1 = new Column();
		col1.setParent(gridcols.getColumns());
		Label lab1 = new Label();
		lab1.setParent(col1);
		lab1.setValue("用途");
		// lab1.setStyle("cursor:pointer");
		col1.setWidth("120px");

		Column col2 = new Column();
		col2.setParent(gridcols.getColumns());
		Label lab2 = new Label();
		lab2.setParent(col2);
		lab2.setValue("备注");
		// lab2.setStyle("cursor:pointer");
		col2.setWidth("120px");

		Column col3 = new Column();
		col3.setParent(gridcols.getColumns());
		Label lab3 = new Label();
		lab3.setParent(col3);
		lab3.setValue("状态");
		// lab3.setStyle("cursor:pointer");
		col3.setWidth("120px");

		Column col7 = new Column();
		col7.setParent(gridcols.getColumns());

		Label lab7 = new Label();
		lab7.setParent(col7);
		lab7.setValue("挂起");
		// lab3.setStyle("cursor:pointer");
		col7.setWidth("120px");

		Column col4 = new Column();
		col4.setParent(gridcols.getColumns());
		// col4.setSort("auto(Esda_oof_statestr)");
		Label lab4 = new Label();
		lab4.setParent(col4);
		lab4.setValue("台帐");
		// lab4.setStyle("cursor:pointer");
		col4.setWidth("120px");

		Column col5 = new Column();
		col5.setParent(gridcols.getColumns());
		Label lab5 = new Label();
		lab5.setParent(col5);
		lab5.setValue("是否系统内");
		// lab5.setStyle("cursor:pointer");
		col5.setWidth("120px");

		// System.out.println(Scount);

		for (int i = 0; i < Scount; i++) {
			// 生成表头
			Column col = new Column();
			col.setParent(gridcols.getColumns());
			Label lab = new Label();
			lab.setParent(col);
			lab.setValue(cobtlist.get(i).getCsii_item_name());
			lab.setStyle("cursor:pointer");
			// col.setLabel(cobtlist.get(i).getCsii_item_name());
			col.setWidth("100px");
			col.addEventListener("onDoubleClick", new MyListener(cobtlist
					.get(i).getCsii_item_name(), gridcols));
		}
	}

	@Command("colsInitsum")
	@NotifyChange("cobtlistsum")
	public void colsInitsum(@BindingParam("self") Grid gridcols)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		// 生成表头
		// 生成Column

		cobtlistsum = emsbll.getItemInfoByCidMonth(cosname, ownmonth, 1);
		Scount = cobtlist.size();
		// Column col6 = new Column();
		// col6.setParent(gridcols.getColumns());
		// col6.setAlign("center");
		// col6.setWidth("40px");
		// col6.setStyle("cursor:pointer");

		for (int i = 0; i < Scount; i++) {
			// 生成表头
			Column col = new Column();
			col.setParent(gridcols.getColumns());
			Label lab = new Label();
			lab.setParent(col);
			lab.setValue(cobtlist.get(i).getCsii_item_name());
			lab.setStyle("cursor:pointer");
			col.setWidth("100px");
		}
	}

	@Command("rowsInitsum")
	@NotifyChange({ "itemListsum", "emsdatesum" })
	public void rowsInitsum(@BindingParam("self") Grid gridrows)
			throws Exception {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String wherestr;

		wherestr = getwherestr(embaname, this.salarystate);
		itemListsum = emsbll.getItemInfoByCidMonth(cosname, ownmonth, 1);

		emsdatesum = emsbll.getSalaryDataByCidMonth(cosname, ownmonth);

		setEmSalaryDataModelOfItemList(emsdatesum, itemListsum);
		for (int m = 0; m < emsdatesum.size(); m++) {
			Row gdr = new Row();
			gdr.setParent(gridrows.getRows());

			for (int i = 0; i < emsdatesum.get(m).getItemList().size(); i++) {

				Label lab = new Label();
				lab.setParent(gdr);
				lab.setValue(df.format(
						emsdatesum.get(m).getItemList().get(i).getAmount())
						.toString());

			}
		}

	}

	// 初始化EmSalaryDataModel的itemList
	private void setEmSalaryDataModelOfItemList(List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setItemList(itList);
			} catch (Exception e) {

			}
		}
	}

	@Command("rowsInit")
	@NotifyChange({ "itemList", "emsdate" })
	public void rowsInit(@BindingParam("self") Grid gridrows) throws Exception {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String wherestr;
		wherestr = getwherestr(embaname, this.salarystate);
		itemList = emsbll.getItemInfoByCidMonth(cosname, ownmonth, 1);

		emsdate = emsbll.getSalaryDataByCidMonth(cosname, ownmonth, wherestr);

		setEmSalaryDataModelOfItemList(emsdate, itemList);
		for (int m = 0; m < emsdate.size(); m++) {
			Row gdr = new Row();
			gdr.setParent(gridrows.getRows());
			Detail Det = new Detail();
			Det.setParent(gdr);

			Det.addEventListener("onOpen", new MyListeneronopen(Det, emsdate
					.get(m).getEsda_id()));
			Det.addEventListener("onClick",
					new MyListeneronclose(gridrows, Det));

			Label lab0 = new Label();
			lab0.setParent(gdr);
			if (emsdate.get(m).getName() != null) {
				lab0.setValue(emsdate.get(m).getName().toString());
			}
			Label lab1 = new Label();
			lab1.setParent(gdr);
			lab1.setValue(emsdate.get(m).getEsda_usage_typestr().toString());
			Label lab2 = new Label();
			lab2.setParent(gdr);
			lab2.setValue(emsdate.get(m).getEsda_remarkstr().toString());
			Label lab3 = new Label();
			lab3.setParent(gdr);
			lab3.setValue(emsdate.get(m).getEsda_payment_statestr().toString());

			Label lab7 = new Label();
			lab7.setParent(gdr);
			lab7.setValue(emsdate.get(m).getEsda_ifholdstr().toString());

			Label lab4 = new Label();
			lab4.setParent(gdr);
			lab4.setValue(emsdate.get(m).getEsda_oof_statestr().toString());
			Label lab5 = new Label();
			lab5.setParent(gdr);
			lab5.setValue(emsdate.get(m).getEsda_if_bmsstr().toString());

			for (int i = 0; i < emsdate.get(m).getItemList().size(); i++) {

				Label lab = new Label();
				lab.setParent(gdr);
				lab.setValue(df.format(
						emsdate.get(m).getItemList().get(i).getAmount())
						.toString());

			}
		}

	}

	// 点击加载事件
	class MyListeneronopen implements org.zkoss.zk.ui.event.EventListener {
		public Detail Det1;
		public int str1;

		private void addEventListener(String string, EventListener eventListener) {
			// TODO Auto-generated method stub

		}

		public MyListeneronopen(Detail Det, int str) {
			Det1 = Det;
			str1 = str;

		}

		@Override
		public void onEvent(Event arg0) throws Exception {
			// TODO Auto-generated method stub
			Include inc = new Include();
			inc.setParent(Det1);

			inc.setAttribute("wherestrinfo", str1);
			inc.setSrc("/EmSalary/EmSalartyde_List.zul?wherestrinfo=" + str1
					+ "&type=1");
		}
	}

	// 点击加载事件
	class MyListeneronclose implements org.zkoss.zk.ui.event.EventListener {
		public Grid gd;
		Detail de;

		private void addEventListener(String string, EventListener eventListener) {
			// TODO Auto-generated method stub

		}

		public MyListeneronclose(Grid gd, Detail de) {
			this.gd = gd;
			this.de = de;
		}

		@Override
		public void onEvent(Event arg0) throws Exception {
			// TODO Auto-generated method stub

			// de.setOpen(false);
			if (!de.isOpen()) {
				// 清除grid
				deletegrid(gd);

				// 动态生成表头
				colsInit(gd);
				// 动态生成行
				rowsInit(gd);
				// System.out.println("Close");
			}

		}
	}

	@SuppressWarnings("rawtypes")
	class MyListener implements org.zkoss.zk.ui.event.EventListener {
		String t;
		Grid gd;

		public MyListener(Object value, Grid gd) {
			// TODO Auto-generated constructor stub
			t = value.toString();
			this.gd = gd;
		}

		public void onEvent(Event e) throws Exception {
			System.out.println(t);

			emsbll.updatevisbel(cosname, t, 0, 1);

			// 清除grid
			deletegrid(gd);

			// 动态生成表头
			colsInit(gd);
			// 动态生成行
			rowsInit(gd);

		}
	}

	public int getCosname() {
		return cosname;
	}

	public void setCosname(int cosname) {
		this.cosname = cosname;
	}

	public int getCid() {
		return cid;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public List<EmSalaryDataModel> getEmsdate() {
		return emsdate;
	}

	public void setEmsdate(List<EmSalaryDataModel> emsdate) {
		this.emsdate = emsdate;
	}

	public List<EmSalaryInfoModel> getEmsdateinfo() {
		return emsdateinfo;
	}

	public void setEmsdateinfo(List<EmSalaryInfoModel> emsdateinfo) {
		this.emsdateinfo = emsdateinfo;
	}

	public List getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public List getCobaselist() {
		return cobaselist;
	}

	public void setCobaselist(List cobaselist) {
		this.cobaselist = cobaselist;
	}

	public String getSalarystate() {
		return salarystate;
	}

	public void setSalarystate(String salarystate) {
		this.salarystate = salarystate;
	}

	public String getEmbaname() {
		return embaname;
	}

	public void setEmbaname(String embaname) {
		this.embaname = embaname;
	}

	public List getSalarystatelist() {
		return salarystatelist;
	}

	public void setSalarystatelist(List salarystatelist) {
		this.salarystatelist = salarystatelist;
	}

	public List<EmSalaryDataModel> getEmsdatesum() {
		return emsdatesum;
	}

	public void setEmsdatesum(List<EmSalaryDataModel> emsdatesum) {
		this.emsdatesum = emsdatesum;
	}

	public List<EmSalaryInfoModel> getEmsdateinfosum() {
		return emsdateinfosum;
	}

	public void setEmsdateinfosum(List<EmSalaryInfoModel> emsdateinfosum) {
		this.emsdateinfosum = emsdateinfosum;
	}

	public List<EmSalaryBaseAddItemModel> getCobtlistsum() {
		return cobtlistsum;
	}

	public void setCobtlistsum(List<EmSalaryBaseAddItemModel> cobtlistsum) {
		this.cobtlistsum = cobtlistsum;
	}

	public List<EmSalaryBaseAddItemModel> getItemListsum() {
		return itemListsum;
	}

	public void setItemListsum(List<EmSalaryBaseAddItemModel> itemListsum) {
		this.itemListsum = itemListsum;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getEmba_idcard() {
		return emba_idcard;
	}

	public void setEmba_idcard(String emba_idcard) {
		this.emba_idcard = emba_idcard;
	}

	public List<CoFinanceMonthlyBillSortAccountModel> getCofinList() {
		return cofinList;
	}

	public CoBaseModel getCm() {
		return cm;
	}

}
