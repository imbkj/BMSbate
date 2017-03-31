package Controller.EmSalary;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Detail;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import Controller.EmSalary.EmSalaryInfoListController.MyListeneronclose;
import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.EmSalaryInfoModel;
import bll.EmSalary.EmSalaryInfoListBll;
import bll.EmSalary.EmSalary_EditBll;
import Util.UserInfo;

public class EmSalary_audtingController {

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
	private List<EmSalaryInfoModel> emsdateinfo = new ArrayList<EmSalaryInfoModel>();
	private List<EmSalaryBaseAddItemModel> cobtlist = new ArrayList<EmSalaryBaseAddItemModel>();
	private List<EmSalaryBaseAddItemModel> itemList;
	private EmSalaryInfoListBll emsbll = new EmSalaryInfoListBll();
	
	private EmSalary_EditBll ebe =new EmSalary_EditBll();
	
	
	private String username = UserInfo.getUsername();

	
	
	private String ownmonth = "201312";
	private String cosname = "1091";
	private String salarystate = "";
	private String embaname = "";
	private String cid = "1091";

	private List ownmonthlist;
	private List cobaselist;
	private List salarystatelist;
	private int countsele;
	private int Scount;
	private int choosed;
	private static final long serialVersionUID = 1L;
	private int t_id;
	private int d_id;

	public EmSalary_audtingController() throws SQLException {
		
		//节点ID
		t_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());
		//t_id=2;
		//数据ID
		d_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
		//d_id=2;
		ownmonthlist = emsbll.getownmonth("");
		salarystatelist = new ArrayList();
		salarystatelist.add("已发放");
		salarystatelist.add("未发放");
		
		

	}


	@Command("auditing")
	@NotifyChange({ "emsdate", "emsdateinfo" })
	public void auditing(@BindingParam("gd") Grid gd) throws Exception {
		//清除grid
//		deletegrid(gd);
//		
//		if(ownmonth=="" || cosname=="")
//		{
//			Messagebox.show("请选择月份和公司编号", "操作提示", Messagebox.OK, Messagebox.ERROR);
//		}
//		else
//		{
//			//动态生成表头
//			colsInit(gd);
//			//动态生成行
//			rowsInit(gd);
//		}
		int msg = 0;
		String[] msg1 = null;
		int i = 0;
		int x = 0;

		try {
			List row = gd.getRows().getChildren();
			for (Object obj : row) {
				Row comp = (Row) obj;
				Checkbox ck = (Checkbox) comp.getChildren().get(9);
				if (ck.isChecked()) {

					msg = ebe.audit(Integer.parseInt(ck.getValue().toString()));

					if (msg == 1) {
						i++;
					}
				}

				
			}
			
			if (i > 0) {

				x = emsbll.checkstate(d_id,0);

				if (x == 0) {
					msg1 = ebe.passtonext("1",t_id, username, d_id);
					Messagebox.show(msg1[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				}

			}

		} catch (Exception e) {
			Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}
		
		
		
		
	
	

	private String getwherestr(String embaname,  String salarystate) {
		StringBuilder str = new StringBuilder();
		if (!salarystate.equals("")) {
			if (salarystate.equals("未发放")) {
				str.append(" and esda_payment_state<2 ");
			} else {
				//str.append(" and esda_payment_state=2 ");
			}
		}
		if (!embaname.equals("")) {
			str.append(" and emba_name like '%");
			str.append(embaname);
			str.append("%' ");
		}
		
		str.append(" and esda_payment_state=0 ");
		
		//System.out.println(str);
		return str.toString();
	}

	public void deletegrid(Grid salarygrid) {
		System.out.println(salarygrid.getColumns().getChildren().size());
		Rows rows = salarygrid.getRows();
		Columns cols = salarygrid.getColumns();
		
		List<Component> rownull =rows.getChildren();
		for(int i=rownull.size()-1;i>=0;i--){
			rows.removeChild(rownull.get(i));
		}
		List<Component> colnull = cols.getChildren();
		System.out.println(colnull.size());
		for(int i=colnull.size()-1;i>=0;i--){
			cols.removeChild(colnull.get(i));
		}
	}

	@Command("colsInit")
	@NotifyChange("cobtlist")
	public void colsInit(@BindingParam("self") Grid gridcols) throws Exception {
		//生成表头
		// 生成Column
		cobtlist=emsbll.getItemInfoByCidMonth(d_id);
		 Scount = cobtlist.size();
		Column col6 = new Column();
		col6.setParent(gridcols.getColumns());
		col6.setAlign("center");
		col6.setWidth("40px");
		//col6.setStyle("cursor:pointer");

		Column col0 = new Column();
		
		col0.setParent(gridcols.getColumns());
		Label lab0 =new Label();
		lab0.setParent(col0);
		lab0.setValue("员工姓名");
		//lab0.setStyle("cursor:pointer");
		col0.setWidth("120px");
		
		Column col1 = new Column();
		col1.setParent(gridcols.getColumns());
		Label lab1 =new Label();
		lab1.setParent(col1);
		lab1.setValue("用途");
		//lab1.setStyle("cursor:pointer");
		col1.setWidth("120px");
		
		
		Column col2 = new Column();
		col2.setParent(gridcols.getColumns());
		Label lab2 =new Label();
		lab2.setParent(col2);
		lab2.setValue("备注");
		//lab2.setStyle("cursor:pointer");
		col2.setWidth("120px");
		
		
		Column col3 = new Column();
		col3.setParent(gridcols.getColumns());
		Label lab3 =new Label();
		lab3.setParent(col3);
		lab3.setValue("状态");
		//lab3.setStyle("cursor:pointer");
		col3.setWidth("120px");
		
		Column col7 = new Column();
		col7.setParent(gridcols.getColumns());
		Label lab7 =new Label();
		lab7.setParent(col7);
		lab7.setValue("挂起");
		//lab3.setStyle("cursor:pointer");
		col7.setWidth("120px");
		
		Column col4 = new Column();
		col4.setParent(gridcols.getColumns());
		Label lab4 =new Label();
		lab4.setParent(col4);
		lab4.setValue("台帐");
		//lab4.setStyle("cursor:pointer");
		col4.setWidth("120px");
		
		Column col5 = new Column();
		col5.setParent(gridcols.getColumns());
		Label lab5 =new Label();
		lab5.setParent(col5);
		lab5.setValue("是否系统内");
		//lab5.setStyle("cursor:pointer");
		col5.setWidth("120px");
		
		Column col8 = new Column();
		col8.setParent(gridcols.getColumns());
		Label lab8 =new Label();
		lab8.setParent(col8);
		lab8.setValue("操作");
		
		//lab5.setStyle("cursor:pointer");
		col8.setWidth("40px");
		
		Column col9 = new Column();
		col9.setWidth("50px");
		col9.setParent(gridcols.getColumns());
		Checkbox cball =new Checkbox();
		cball.setParent(col9);
		//cball.setWidth("50px");
		System.out.println(cball.isChecked());
		
		//cball.setChecked(false);	
		
		cball.addEventListener("onCheck", new MyListenerCheckall(cball,gridcols));
	
		//System.out.println(Scount);
		
		for (int i = 0; i < Scount; i++) {
			// 生成表头
			Column col = new Column();
			col.setParent(gridcols.getColumns());
			Label lab =new Label();
			lab.setParent(col);
			lab.setValue(cobtlist.get(i).getCsii_item_name());
			lab.setStyle("cursor:pointer");
//			col.setLabel(cobtlist.get(i).getCsii_item_name());
			col.setWidth("120px");
			col.addEventListener("onDoubleClick",
					new MyListener(cobtlist.get(i).getCsii_item_name(), gridcols));
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
		wherestr=getwherestr(embaname, this.salarystate);
		itemList=emsbll.getItemInfoByCidMonth(d_id);
		
		emsdate=emsbll.getSalaryDataByCidMonth(d_id);
		
		setEmSalaryDataModelOfItemList(emsdate,itemList);
		for (int m=0;m<emsdate.size();m++)
	{
		Row gdr = new Row();
		gdr.setParent(gridrows.getRows());
		Detail Det = new Detail();
		Det.setParent(gdr);
					
		Det.addEventListener("onOpen", new MyListeneronopen(Det,emsdate.get(m).getEsda_id()));
		Det.addEventListener("onClick", new MyListeneronclick(gridrows,Det));
	
		Label lab0 = new Label();
		lab0.setParent(gdr);
		if (emsdate.get(m).getName()!=null)
		{
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
		
		Cell cl =new Cell();
		cl.setParent(gdr);
		Hbox hb =new Hbox();
		hb.setParent(cl);
		Label lab8 = new Label();
		lab8.setParent(hb);
		lab8.setValue("审核");
		lab8.setStyle("cursor:pointer");
		lab8.addEventListener("onClick", new MyListeneraudt(emsdate.get(m).getEsda_id()));
//		Label lab9 = new Label();
//		lab9.setParent(hb);
//		lab9.setValue("挂起");
//		lab9.setStyle("cursor:pointer");
//		lab9.addEventListener("onClick", new MyListenerconfrim(emsdate.get(m).getEsda_id()));
		
		Checkbox cbone =new Checkbox();
		cbone.setParent(gdr);
		cbone.setValue(emsdate.get(m).getEsda_id());
		
		//cbone.addEventListener("onCheck", new MyListenerCheckall());
		
		
		for (int i=0;i<emsdate.get(m).getItemList().size();i++)
		{
			
				Label lab = new Label();
				lab.setParent(gdr);
				lab.setValue(df.format(emsdate.get(m).getItemList().get(i).getAmount())
						.toString());
			
		}
	}

	}
	
	
	
	//点击全选事件
		@SuppressWarnings("rawtypes")
class MyListenerCheckall implements org.zkoss.zk.ui.event.EventListener {
		Grid gridcols;
		Checkbox chall;
		public MyListenerCheckall(Checkbox chall,Grid gridcols)
		{
			this.gridcols=gridcols;
			this.chall=chall;
		}
		@Override
		public void onEvent(Event arg0)  {
			// TODO Auto-generated method stub
			// System.out.print(chall.isChecked());
			int msg = 0;
			String[] msg1=null;
			int i=0;
			int x=0;
			try{
			 List row = gridcols.getRows().getChildren();
			    for(Object obj:row){
			      Row comp = (Row) obj;
			      Checkbox ck = (Checkbox) comp.getChildren().get(9);
			      ck.setChecked(chall.isChecked());
			      if(ck.isChecked()){
			    	 
						} 
//			      else {
//							Messagebox.show(msg1[1], "ERROR", Messagebox.OK,
//									Messagebox.ERROR);
//						}
			      }
			      
			   }
			catch (Exception e) {
				Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				System.out.println(e.toString());
			}
		}
		}

	
	//点击审核事件
	@SuppressWarnings("rawtypes")
	class MyListeneraudt implements org.zkoss.zk.ui.event.EventListener {
		public int esda_id;
		
	public MyListeneraudt(int esda_id)
	{
		this.esda_id=esda_id;
	}
	@Override
	public void onEvent(Event arg0) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		int x=0;
		String[] msg1=null;
		if (Messagebox.show("是否审核该条数据?", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
		//执行确认方法
			row= ebe.audit(esda_id);
			x=emsbll.checkstate(d_id,0);
	    	  
    		if (x==0)
    		{
    			msg1=ebe.passtonext("1",t_id,username,d_id);

    		}
    		}
			if (row > 0) {
				Messagebox.show("审核成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				
				
			} else {
				Messagebox.show("审核失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		

	}
	}
	
	//点击确认事件
	@SuppressWarnings("rawtypes")
	class MyListenerconfrim implements org.zkoss.zk.ui.event.EventListener {
		public int esda_id;
		
	public MyListenerconfrim(int esda_id)
	{
		this.esda_id=esda_id;
	}
	@Override
	public void onEvent(Event arg0) throws Exception {
		// TODO Auto-generated method stub
		int row=0;
		if (Messagebox.show("是否挂起该条数据?", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
		//执行确认方法
			
			if (row > 0) {
				Messagebox.show("挂起成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("挂起失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}
	}
	
	

	//点击加载事件
	@SuppressWarnings("rawtypes")
	class MyListeneronopen implements org.zkoss.zk.ui.event.EventListener {
		public Detail Det1;
		public int str1;
	public MyListeneronopen( Detail Det, int str)
	{
		Det1=Det;
		str1=str;
	}
	@Override
	public void onEvent(Event arg0) throws Exception {
		// TODO Auto-generated method stub
		Include inc = new Include();
		inc.setParent(Det1);
		inc.setAttribute("wherestrinfo", str1);
		inc.setSrc("/EmSalary/EmSalartyde_List.zul?wherestrinfo="+str1+"&type=1");
	}
	}
	
	//点击刷新grid事件
@SuppressWarnings("rawtypes")
class MyListeneronclick implements org.zkoss.zk.ui.event.EventListener {
			public Grid gd;
			Detail de;
		public MyListeneronclick(Grid gd,Detail de)
		{
			this.gd=gd;
			this.de=de;
		}
		@Override
		public void onEvent(Event arg0) throws Exception {
			if(!de.isOpen())
			{
				//清除grid
				deletegrid(gd);
				//动态生成表头
				colsInit(gd);
				//动态生成行
				rowsInit(gd);
				//System.out.println("Close");
			}
		}

		}

	
	//控制表头不显示事件
	@SuppressWarnings("rawtypes")
	class MyListener implements org.zkoss.zk.ui.event.EventListener {
		String t;
		Grid gd;

		public MyListener(Object value,Grid gd) {
			// TODO Auto-generated constructor stub
			t = value.toString();
			this.gd=gd;
		}
		public void onEvent(Event e) throws Exception {
			System.out.println(t);
			
			emsbll.updatevisbel(Integer.parseInt(cosname), t, 0,1);
			//清除grid
			deletegrid(gd);
				//动态生成表头
			colsInit(gd);
			//动态生成行
			rowsInit(gd);
		}
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

	public String getOwnmoth() {
		return ownmonth;
	}

	public void setOwnmoth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public String getCosname() {
		return cosname;
	}

	public void setCosname(String cosname) {
		this.cosname = cosname;
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List getSalarystatelist() {
		return salarystatelist;
	}

	public void setSalarystatelist(List salarystatelist) {
		this.salarystatelist = salarystatelist;
	}

}
