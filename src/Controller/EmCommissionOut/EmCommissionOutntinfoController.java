package Controller.EmCommissionOut;

import impl.PubCityImpl;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.format.UnderlineStyle;
//import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import Controller.EmCommissionOut.EmCyearaddinfolistController.MyListenerCheckall;
import Controller.EmCommissionOut.EmCyearaddinfolistController.listener;
import Model.CoAgencyBaseModel;
import Model.CoBaseModel;
import Model.EmCommissionYearChangemModel;
import Model.EmCommissionyearchangetitleModel;
import Model.EmbaseModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;
import bll.CoAgency.BaseInfo_CityDisBll;
import bll.CoAgency.BaseInfo_DisBll;
import bll.CoBase.CoBase_SelectBll;
import bll.EmCommissionOut.EmCommissionyearchangetitleBll;
import bll.Embase.EmbaseListBll;



public class EmCommissionOutntinfoController  extends SelectorComposer<Component> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	private List<Integer> countdate;
	private String strwhere="";
	public List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	EmbaseListBll embasebll = new EmbaseListBll();
	EmCommissionyearchangetitleBll bll = new EmCommissionyearchangetitleBll();
	private List<EmCommissionYearChangemModel> ecycmodellist;
	
	private List<EmCommissionyearchangetitleModel> emcomm;
	private List<EmCommissionYearChangemModel> ecycmodellistinfo;
	private List<EmCommissionyearchangetitleModel> emcomminfo;
	private EmCommissionyearchangetitleModel modelinfo;
 
	private List<EmCommissionyearchangetitleModel> semcomm =new ListModelList();
	private EmCommissionyearchangetitleModel model;
	private CoBaseModel  coba_clinet = new CoBaseModel();
	
	@Wire
	private Grid gridwin;
	@Wire
	private Columns gridcols;

	@Wire
	private Rows gridrows;

	@Wire
	private Checkbox editAll;	  //全选修改选项框
	
	@Wire
	private Button btSubmit;
	
	private List<String> regionlist;
	private List<String> provincelist;
	private List<String> citylist;
	private List<CoBaseModel> cobasemodellist =new ArrayList<CoBaseModel>();
	private ArrayList<String> wtztlist =new ArrayList<String>();
	
	private List<CoAgencyBaseModel> wtjglist;
	private String cityname;
	public int t_id;
	public int d_id;
	public String cbcity;
	public String wtjgname;
	public int id;
	public String wtzt="";
	private PubCityImpl cbll = new PubCityImpl();
	private BaseInfo_DisBll Dbll = new BaseInfo_DisBll();
	private String coba_company="";
	private String emba_name="";
	private Integer cid=null;
	private Integer gid=null;
	
	
	private StringBuilder wherestr;
	private CoBase_SelectBll cobll=new CoBase_SelectBll();
	

	public EmCommissionOutntinfoController() throws Exception
	{
		d_id=81;
		System.out.println(wtzt);
//		cbcity="上海";
//		wtjgname="中智上海经济技术合作公司";
		
		
		cbcity=Executions.getCurrent().getArg().get("city").toString();
	   // t_id=60;
		//数据ID
		wtjgname=Executions.getCurrent().getArg().get("jgname").toString();
		//d_id=60;
		
		id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());
		
		
	//	状态 0,"未采集"1,"已采集"2,"已确认"3,"已导出"4,"已更新"
		wtztlist.add(0,"未录入");
		wtztlist.add(1,"已录入");
		wtztlist.add(2,"已采集");
		wtztlist.add(3,"已导出");
		wtztlist.add(4,"已更新");
		wtztlist.add(5,"已完成");
		
		
		//selectlist();
		//search();
		cobasemodellist=cobll.getCobaseClient("1=1");
		regionlist = cbll.getRegionName();
		provincelist = cbll.getProvinceName();
		citylist = cbll.getCityName();
		
		wherestr= new StringBuilder();
		wherestr.append(" and ecyt_id= "+id);
		wherestr.append(" and ecyc_state= "+wtztlist.indexOf(wtzt));
		if(cid!=null)
		{
			wherestr.append(" and c.cid="+cid+"");
		}
		if (gid!=null)
		{
			wherestr.append(" and a.gid="+gid+"");
		}
		if (coba_clinet!=null && coba_clinet.getCoba_client()!=null &&!"".equals(coba_clinet.getCoba_client()))
		{
			wherestr.append(" and  c.coba_client = '"+coba_clinet.getCoba_client()+"'");
		 
		}
		
		
		if(!"".equals(coba_company))
		{
			wherestr.append(" and  c.coba_company like '%"+coba_company+"");
			wherestr.append("%'");
			
		}
		if (!"".equals(emba_name))
		{
			wherestr.append(" and  b.emba_name like '%"+emba_name+"");
			wherestr.append("%'");
		}
		
		setEmcomm(bll.getemcommtlistforid(id));
		setEcycmodellist(bll.searchembaselisthdl(wherestr.toString()));

		
		if (emcomm.size()>0)
		{
		setModel(emcomm.get(0));
		}

	}
	
	
	// 根据城市查询委托机构
	@Command("setvsb")
	 
	public void selwtjg(@BindingParam("contact") String Statestring,
			@BindingParam("bt") Button btSubmit) throws Exception {
		
		if (Statestring.equals("已采集"))
		{
			btSubmit.setDisabled(false);
		}
		else
		{
			btSubmit.setDisabled(true);
		}
	 
	}


	public ArrayList<String> getWtztlist() {
		return wtztlist;
	}



	public void setWtztlist(ArrayList<String> wtztlist) {
		this.wtztlist = wtztlist;
	}



	public List<Integer> getCountdate() {
		return countdate;
	}
	public void setCountdate(List<Integer> countdate) {
		this.countdate = countdate;
	}
	public String getStrwhere() {
		return strwhere;
	}
	public void setStrwhere(String strwhere) {
		this.strwhere = strwhere;
	}
	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}
	public void setEmbaselist(List<EmbaseModel> embaselist) {
		this.embaselist = embaselist;
	}
	
	
	public List<CoBaseModel> getCobasemodellist() {
		return cobasemodellist;
	}


	public void setCobasemodellist(List<CoBaseModel> cobasemodellist) {
		this.cobasemodellist = cobasemodellist;
	}


	@Command("selectlist")
	@NotifyChange({"emcomm","ecycmodellist"})
	public void selectlist(@BindingParam("grd") Grid grid)
	{
		try {
			deletegrid(grid);

			
			if (cbcity.equals(""))
			{
				Messagebox.show("请选择城市和委托机构!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
			}
			else
			{
				
				 
				wherestr= new StringBuilder();
				wherestr.append(" and ecyt_id= "+id);
				wherestr.append(" and ecyc_state= "+wtztlist.indexOf(wtzt));
				if(cid!=null)
				{
					wherestr.append(" and c.cid="+cid+"");
				}
				if (gid!=null)
				{
					wherestr.append(" and a.gid="+gid+"");
				}
				if(!"".equals(coba_company))
				{
					wherestr.append(" and  c.coba_company like '%"+coba_company+"");
					wherestr.append("%'");
					
				}
				if (coba_clinet!=null && coba_clinet.getCoba_client()!=null &&!"".equals(coba_clinet.getCoba_client()))
				{
					wherestr.append(" and  c.coba_client = '"+coba_clinet.getCoba_client()+"'");
				 
				}
				
				if (!"".equals(emba_name))
				{
					wherestr.append(" and  b.emba_name like '%"+emba_name+"");
					wherestr.append("%'");
				}
				
				
				setEmcomm(bll.getemcommtlistforid(id));
				setEcycmodellist(bll.searchembaselisthdl(wherestr.toString()));
				setModel(emcomm.get(0));
				colsInit(grid);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	
	}
	
	
	// 根据地区查询省份
	@Command("selProvince")
	@NotifyChange({ "provincelist", "citylist" })
	public void selProvince(@BindingParam("contact") String region,
			@BindingParam("com") Combobox province) throws Exception {
		province.setValue(null);
		setProvincelist(cbll.getProvinceNameByRegion(region));
		setCitylist(cbll.getCityName());
	}

	// 根据省份查询城市
	@Command("selCity")
	@NotifyChange("citylist")
	public void selCity(@BindingParam("contact") String province,
			@BindingParam("com") Combobox city) throws Exception {
		city.setValue(null);
		setCitylist(cbll.getCityName(province));
	}
	
	//根据城市查询委托机构
	@Command("selwtjg")
	@NotifyChange("wtjglist")
	public void selwtjg(@BindingParam("contact") String city,
			@BindingParam("com") Combobox wtjg) throws Exception {
		wtjg.setValue(null);
		setWtjglist(bll.getCoAgencyBaseAll(city));
//		CoAgencyBaseModel m =new CoAgencyBaseModel();
//		m.setCoab_name("全部");
//		m.setCoab_id(0);
//		wtjglist.add(0,m);
		
		
	}
	
	//检索 
	@Command("setemcomm")
	@NotifyChange({"emcomm","ecycmodellist"})
	public void setemcomm() throws Exception {
//		gridwin=new Grid();
//		gridcols=gridwin.getColumns();
//		gridrows=gridwin.getRows();
//		colsInit(gridwin);
	//	Executions.sendRedirect("/EmCommissionOut/EmCyearaddinfolist.zul");
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
	
		//生成表头
			@Command("colsInit")
			@NotifyChange("emcomm")
			public void colsInit(@BindingParam("self") Grid gridcols) throws Exception {
		
	
			//	System.out.println(ecycmodellist.get(0).getEcyc_sye_base());
				Column col0 = new Column();
				col0.setParent(gridcols.getColumns());
				Label lab0 =new Label();
				lab0.setParent(col0);
				lab0.setValue("公司编号");
				col0.setWidth("60px");
				
				
				
				Column col2 = new Column();
				col2.setParent(gridcols.getColumns());
				Label lab2 =new Label();
				lab2.setParent(col2);
				lab2.setValue("公司简称");
				//lab2.setStyle("cursor:pointer");
				col2.setWidth("120px");
				

				Column col1 = new Column();
				col1.setParent(gridcols.getColumns());
				Label lab1 =new Label();
				lab1.setParent(col1);
				lab1.setValue("雇员编号");
				//lab1.setStyle("cursor:pointer");
				col1.setWidth("60px");
				
				
				Column col3 = new Column();
				col3.setParent(gridcols.getColumns());
				Label lab3 =new Label();
				lab3.setParent(col3);
				lab3.setValue("员工姓名");
				//lab3.setStyle("cursor:pointer");
				col3.setWidth("60px");
				
				Column col4 = new Column();
				col4.setParent(gridcols.getColumns());
				Label lab4 =new Label();
				lab4.setParent(col4);
				lab4.setValue("城市");
				//lab3.setStyle("cursor:pointer");
				col4.setWidth("60px");
				
				Column col41 = new Column();
				col41.setParent(gridcols.getColumns());
				Label lab41 =new Label();
				lab41.setParent(col41);
				lab41.setValue("机构");
				//lab3.setStyle("cursor:pointer");
				col41.setWidth("60px");

			if (model!=null)	
			{
				
				if (model.getEcyt_ylao()==1)
				{
					Column colylao = new Column();
					colylao.setParent(gridcols.getColumns());
					Label labylao =new Label();
					labylao.setParent(colylao);
					labylao.setValue("养老");
					//lab3.setStyle("cursor:pointer");
					colylao.setWidth("60px");
		
				}
				
				if (model.getEcyt_gshang()==1)
				{
					Column colgshang = new Column();
					colgshang.setParent(gridcols.getColumns());
					Label labgshang =new Label();
					labgshang.setParent(colgshang);
					labgshang.setValue("工伤");
					//lab3.setStyle("cursor:pointer");
					colgshang.setWidth("60px");
					
					
				}
				
				if (model.getEcyt_yliao()==1)
				{
					Column colyliao = new Column();
					colyliao.setParent(gridcols.getColumns());
					Label labyliao =new Label();
					labyliao.setParent(colyliao);
					labyliao.setValue("医疗");
					//lab3.setStyle("cursor:pointer");
					colyliao.setWidth("60px");
				
				}
				
				if (model.getEcyt_sye()==1)
				{
					Column colsye = new Column();
					colsye.setParent(gridcols.getColumns());
					Label labsye =new Label();
					labsye.setParent(colsye);
					labsye.setValue("失业");
					//lab3.setStyle("cursor:pointer");
					colsye.setWidth("60px");
					
				}
				if (model.getEcyt_syu()==1)
				{
					Column colsyu = new Column();
					colsyu.setParent(gridcols.getColumns());
					Label labsyu =new Label();
					labsyu.setParent(colsyu);
					labsyu.setValue("生育");
					//lab3.setStyle("cursor:pointer");
					colsyu.setWidth("60px");
				
				}

				if (model.getEcyt_gjj()==1)
				{
					Column colgjj = new Column();
					colgjj.setParent(gridcols.getColumns());
					Label labgjj =new Label();
					labgjj.setParent(colgjj);
					labgjj.setValue("住房基数");
					//lab3.setStyle("cursor:pointer");
					colgjj.setWidth("60px");

					Column colgjj1 = new Column();
					colgjj1.setParent(gridcols.getColumns());
					Label labgjj1 =new Label();
					labgjj1.setParent(colgjj1);
					labgjj1.setValue("住房企业比例");
					//lab3.setStyle("cursor:pointer");
					colgjj1.setWidth("60px");
					
					Column colgjj2 = new Column();
					colgjj2.setParent(gridcols.getColumns());
					Label labgjj2 =new Label();
					labgjj2.setParent(colgjj2);
					labgjj2.setValue("住房个人比例");
					//lab3.setStyle("cursor:pointer");
					colgjj2.setWidth("80px");
					
					Column colgjj3 = new Column();
					colgjj3.setParent(gridcols.getColumns());
					Label labgjj3 =new Label();
					labgjj3.setParent(colgjj3);
					labgjj3.setValue("住房缴存额");
					//lab3.setStyle("cursor:pointer");
					colgjj3.setWidth("80px");

				}
				
				if (model.getEcyt_bcgjj()==1)
				{
					Column colbcgjj = new Column();
					colbcgjj.setParent(gridcols.getColumns());
					Label labbcgjj =new Label();
					labbcgjj.setParent(colbcgjj);
					labbcgjj.setValue("补充住房");
					//lab3.setStyle("cursor:pointer");
					colbcgjj.setWidth("60px");
					
					Column colbcgjj1 = new Column();
					colbcgjj1.setParent(gridcols.getColumns());
					Label labbcgjj1 =new Label();
					labbcgjj1.setParent(colbcgjj1);
					labbcgjj1.setValue("补充住房企业比例");
					//lab3.setStyle("cursor:pointer");
					colbcgjj1.setWidth("80px");
					
					Column colbcgjj2 = new Column();
					colbcgjj2.setParent(gridcols.getColumns());
					Label labbcgjj2 =new Label();
					labbcgjj2.setParent(colbcgjj2);
					labbcgjj2.setValue("补充住房个人比例");
					//lab3.setStyle("cursor:pointer");
					colbcgjj2.setWidth("80px");
					
					Column colbcgjj3 = new Column();
					colbcgjj3.setParent(gridcols.getColumns());
					Label labbcgjj3 =new Label();
					labbcgjj3.setParent(colbcgjj3);
					labbcgjj3.setValue("补充住房缴存额");
					//lab3.setStyle("cursor:pointer");
					colbcgjj3.setWidth("80px");

				}
				

				Column col9 = new Column();
				col9.setParent(gridcols.getColumns());
				Label lab9 =new Label();
				lab9.setParent(col9);
				lab9.setValue("客服");
				//lab3.setStyle("cursor:pointer");
				col9.setWidth("60px");
		
				
				Column colsstate = new Column();
				colsstate.setParent(gridcols.getColumns());
				Label labstate =new Label();
				labstate.setParent(colsstate);
				labstate.setValue("状态");
				//lab3.setStyle("cursor:pointer");
				colsstate.setWidth("80px");
				
//				Column colschose = new Column();
//				colschose.setParent(gridcols.getColumns());
//				Label labchose =new Label();
//				labchose.setParent(colschose);
//				labchose.setValue("选择");
//				//lab3.setStyle("cursor:pointer");
//				colschose.setWidth("80px");
//				Column col9 = new Column();
//				col9.setWidth("50px");
//				col9.setParent(gridcols.getColumns());
//				Checkbox cball =new Checkbox();
//				cball.setParent(col9);
				
				
				Column colschose = new Column();
				colschose.setWidth("50px");
				colschose.setParent(gridcols.getColumns());
				Checkbox cbone =new Checkbox();
				cbone.setParent(colschose);
				cbone.setValue("选择");
				cbone.addEventListener("onCheck", new MyListenerCheckall(cbone,gridcols));
				
				rowsInit(gridcols);
				}
			}
			
			//生成数据
	
			@SuppressWarnings("unchecked")
			public void rowsInit(Grid gridrows) throws Exception {
				
				
				java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
				
				for (int m=0;m<ecycmodellist.size();m++)
				{
					Row gdr = new Row();
					gdr.setParent(gridrows.getRows());
					Label lab0 = new Label();
					lab0.setParent(gdr);
					lab0.setValue(ecycmodellist.get(m).getCid().toString());
					
					
					Label lab2 = new Label();
					lab2.setParent(gdr);
					lab2.setValue(ecycmodellist.get(m).getCoba_shortname().toString());
					
					Label lab1 = new Label();
					lab1.setParent(gdr);
					lab1.setValue(ecycmodellist.get(m).getGid().toString());
					Label lab3 = new Label();
					lab3.setParent(gdr);
					lab3.setValue(ecycmodellist.get(m).getEmba_name().toString());
					
					Label lab4 = new Label();
					lab4.setParent(gdr);
					lab4.setValue(ecycmodellist.get(m).getCity().toString());
					
					Label lab41 = new Label();
					lab41.setParent(gdr);
					lab41.setValue(ecycmodellist.get(m).getCoab_name().toString());
					
					
					if (model.getEcyt_ylao()==1)
					{
						Label lab5 = new Label();
						lab5.setParent(gdr);
						lab5.setValue(df.format(ecycmodellist.get(m).getEcyc_sb_base()).toString());
					}
					if (model.getEcyt_gshang()==1)
					{
						Label lab6 = new Label();
						lab6.setParent(gdr);
						lab6.setValue(df.format(ecycmodellist.get(m).getEcyc_sb_base()).toString());
					}
					if (model.getEcyt_yliao()==1)
					{
						Label lab7 = new Label();
						lab7.setParent(gdr);
						lab7.setValue(df.format(ecycmodellist.get(m).getEcyc_sb_base()).toString());
					}
					if (model.getEcyt_sye()==1)
					{
						Label lab8 = new Label();
						lab8.setParent(gdr);
						lab8.setValue(df.format(ecycmodellist.get(m).getEcyc_sb_base()).toString());
					}
					if (model.getEcyt_syu()==1)
					{
						Label lab9 = new Label();
						lab9.setParent(gdr);
						lab9.setValue(df.format(ecycmodellist.get(m).getEcyc_sb_base()).toString());
					}

//					if (model.getEcyt_ylao()==1)
//					{
//						Label lab5 = new Label();
//						lab5.setParent(gdr);
//						lab5.setValue(df.format(ecycmodellist.get(m).getEcyc_yl_base()).toString());
//					}
//					if (model.getEcyt_gshang()==1)
//					{
//						Label lab6 = new Label();
//						lab6.setParent(gdr);
//						lab6.setValue(df.format(ecycmodellist.get(m).getEcyc_gs_base()).toString());
//					}
//					if (model.getEcyt_yliao()==1)
//					{
//						Label lab7 = new Label();
//						lab7.setParent(gdr);
//						lab7.setValue(df.format(ecycmodellist.get(m).getEcyc_jl_base()).toString());
//					}
//					if (model.getEcyt_sye()==1)
//					{
//						Label lab8 = new Label();
//						lab8.setParent(gdr);
//						lab8.setValue(df.format(ecycmodellist.get(m).getEcyc_sye_base()).toString());
//					}
//					if (model.getEcyt_syu()==1)
//					{
//						Label lab9 = new Label();
//						lab9.setParent(gdr);
//						lab9.setValue(df.format(ecycmodellist.get(m).getEcyc_syu_base()).toString());
//					}
					if (model.getEcyt_gjj()==1)
					{
						Label lab91 = new Label();
						lab91.setParent(gdr);
						lab91.setValue(df.format(ecycmodellist.get(m).getEcyc_house_base()).toString());
						Label lab92 = new Label();
						lab92.setParent(gdr);
						lab92.setValue(ecycmodellist.get(m).getEcyc_house_cp());
						Label lab93 = new Label();
						lab93.setParent(gdr);
						lab93.setValue(ecycmodellist.get(m).getEcyc_house_op());
						Label lab94 = new Label();
						lab94.setParent(gdr);
						lab94.setValue(df.format(ecycmodellist.get(m).getEcyc_house_hj()).toString());
					}
					
					if (model.getEcyt_bcgjj()==1)
					{
						Label lab911 = new Label();
						lab911.setParent(gdr);
						lab911.setValue(df.format(ecycmodellist.get(m).getEcyc_bc_base()).toString());
						Label lab921 = new Label();
						lab921.setParent(gdr);
						lab921.setValue(ecycmodellist.get(m).getEcyc_bc_cp());
						Label lab931 = new Label();
						lab931.setParent(gdr);
						lab931.setValue(ecycmodellist.get(m).getEcyc_bc_op());
						Label lab941 = new Label();
						lab941.setParent(gdr);
						lab941.setValue(df.format(ecycmodellist.get(m).getEcyc_bc_total()).toString());
						
					
					}
					Label lab49 = new Label();
					lab49.setParent(gdr);
					lab49.setValue(ecycmodellist.get(m).getCoba_client().toString());
					
//					Label lab10 = new Label();
//					lab10.setParent(gdr);
//					lab10.setValue(ecycmodellist.get(m).getEcyc_statestr());
//					lab10.setStyle("cursor:pointer");
					//lab8.addEventListener("onClick", new MyListeneraudt(emsdate.get(m).getEsda_id()));
					
					//lab10.addEventListener("onClick", new MyListeneraudt(ecycmodellist.get(m).getEcyc_id()));
					
					Cell cl =new Cell();
					cl.setParent(gdr);
					Hbox hb =new Hbox();
					hb.setParent(cl);
					Label lab81 = new Label();
					lab81.setParent(hb);
					lab81.setValue(ecycmodellist.get(m).getEcyc_statestr());
					lab81.setStyle("cursor:pointer");
					lab81.addEventListener("onClick",new listener(ecycmodellist.get(m).getEcyc_id()));
					//lab81.addEventListener("onClick", new listener)
					Checkbox cbone =new Checkbox();
					cbone.setParent(gdr);
					cbone.setValue(m);
					//cbone.addEventListener("onCheck", new MyListenerCheckall(ecycmodellist.get(m).getEcyc_id(),gridrows));
				}
			}
		
			//全选功能
				 @Listen("onCheck = #editAll")
			   public void checkedi(CheckEvent e){
					List row = gridwin.getRows().getChildren();
					    for(Object obj:row){
					      Row comp = (Row) obj;
					      Checkbox ck = (Checkbox) comp.getChildren().get(0);
					      ck.setChecked(e.isChecked());
					      if(ck.isChecked()){
					    	  System.out.print(ck.getValue()+",");
					      }
					   }
				}
				// 弹出导入基数
				@Command("jscj")
				public void ChangeSalary(@BindingParam("win") Window win) {
					//win.detach();
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("d_id", d_id);
					map.put("t_id", t_id);
					map.put("cid", 1033);
					map.put("model", model);
					map.put("wininfo", win);
					map.put("ecycmodellist", ecycmodellist);
					Window window = (Window) Executions.createComponents(
							"..\\EmCommissionOut\\EmCyearcjupdate.zul", null, map);
					window.doModal();
				}
				
//				// 弹出更新确认日期
//				@Command("submitdate")
//				public void submitdate(@BindingParam("self") Grid gridcols) {
//					//win.detach();
//					Map<String, Object> map = new HashMap<String, Object>();
//					
//					map.put("d_id", d_id);
//					map.put("t_id", t_id);
//					map.put("cid", 1033);
//					map.put("model", model);
//					map.put("wininfo", win);
//					map.put("ecycmodellist", ecycmodellist);
//					Window window = (Window) Executions.createComponents(
//							"..\\EmCommissionOut\\EmCyearcjupdate.zul", null, map);
//					window.doModal();
//				}
//	
			 //审核
			 @Listen("onClick = #btplsh")
			 public void submit()
			 {
				 boolean rowcout=false;
				 String idcountstr="";
					try{
						
						//定义row 并将grid的行赋值到row	
						 List row = gridwin.getRows().getChildren();
						 //遍历row
						    for(Object obj:row){
	
						    		Row comp = (Row) obj;
							      //定义1个checkbox获取当前遍历行的控件
							      Checkbox checksel = (Checkbox) comp.getChildren().get(0);
							      if(checksel.isChecked())
							      {
							    	 // Integer.parseInt(checksel.getValue().toString());
							    	  idcountstr=idcountstr+checksel.getValue().toString()+",";
							      
							      }
						    }
						  //  System.out.println(idcountstr.substring(0,idcountstr.length()-1));
						    //生成采集单号
						 //   rowcout= bll.adutingemcommlist(idcountstr.substring(0,idcountstr.length()-1), UserInfo.getUsername());
						    //判断是否任务单是否需要前行
						    
						    int x=0;
						    x=bll.checktasktonext(d_id, 0);
						    
						    
						    if (x==0)
				    		{
						    	bll.passtonext("2",t_id, UserInfo.getUsername(), d_id);
				    		}
				    		
							if (rowcout) {
								Messagebox.show("审核成功!", "INFORMATION", Messagebox.OK,
										Messagebox.INFORMATION);
								
								
							} else {
								Messagebox.show("审核失败!", "ERROR", Messagebox.OK,
										Messagebox.ERROR);
							}
						    
						}
						catch(Exception e)
						{
							e.printStackTrace();
							System.out.println(e);
							
						}
			 }
			//点击审核事件
				
@SuppressWarnings("rawtypes")
class listener implements org.zkoss.zk.ui.event.EventListener {
					public int ecyc_id;
					
				public listener(int ecyc_id)
				{
					this.ecyc_id=ecyc_id;
					System.out.println(ecyc_id);
				}
				@Override
				public void onEvent(Event arg0) throws Exception {
					// TODO Auto-generated method stub
					boolean row=false;
					
					
					if (Messagebox.show("是否取消该条数据?", "INFORMATION", Messagebox.YES
							| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
					//执行确认方法
					//	row= bll.unlockedit(ecyc_id);
						
						if (row) {
							Messagebox.show("取消成功!", "INFORMATION", Messagebox.OK,
									Messagebox.INFORMATION);
							
							
						} else {
							Messagebox.show("取消失败!", "ERROR", Messagebox.OK,
									Messagebox.ERROR);
						}
						
					}	
				}
			}
//点击选择事件
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
		      Checkbox ck = (Checkbox) comp.getChildren().get(gridcols.getColumns().getChildren().size()-1);
		      ck.setChecked(chall.isChecked());
		      if(ck.isChecked()){
		    	 
					} 
		      }
		      
		   }
		catch (Exception e) {
			Messagebox.show("提交出错,请联系IT部门!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			System.out.println(e.toString());
		}
	}
}


/**
 * 退回
 * 
 */
@SuppressWarnings("null")
@Command("reback")
@NotifyChange({ "emcomm", "ecycmodellist" })
public void reback(@BindingParam("self") Grid gridcols) {
	
	 List row = gridcols.getRows().getChildren();
	 
	  
 List<EmCommissionYearChangemModel> ecycmodellists = new ArrayList<EmCommissionYearChangemModel>();
 EmCommissionYearChangemModel modela= new EmCommissionYearChangemModel();
	 
	    for(Object obj:row){
	      Row comp = (Row) obj;
	      Checkbox ck = (Checkbox) comp.getChildren().get(gridcols.getColumns().getChildren().size()-1);
	      if(ck.isChecked()==true){
	    	  System.out.println(ck.getValue());
	    	  //ecycmodellist.remove(Integer.parseInt(ck.getValue().toString()));
	    	  modela=ecycmodellist.get(Integer.parseInt(ck.getValue().toString()));
	    	  
	    	  ecycmodellists.add(modela);

				} 
	      }

	if (ecycmodellists.size()==0)
	{
		Messagebox.show("还没有勾选数据呢，亲!", "info", Messagebox.OK,
				Messagebox.INFORMATION);
	}
	else
	{
	try {
		if (bll.rebacknt(ecycmodellists,0))
			{
			Messagebox.show("退回成功!", "OK", Messagebox.OK,
					Messagebox.INFORMATION);
			}
		else 
		{
			Messagebox.show("退回失败!", "Err", Messagebox.OK,
					Messagebox.ERROR);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
		Messagebox.show("退回失败!", "Err", Messagebox.OK,
				Messagebox.ERROR);
	}
	}
	
	selectlist(gridcols);
	
}

/**
 * 导出Excel
 * 
 */
@Command("selectexcel")
public void selectexcel(@BindingParam("self") Grid gridcols) {
	//EmCommissionOutListBll bll = new EmCommissionOutListBll();
	excel: {
		try {
			String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
					.format(new Date())
					+ UserInfo.getUserid()
					+ "wtjscj_out_excelzyt.xls";
			File file = new File(new plyUtil().getAbsolutePath(
					"/../../OfficeFile/DownLoad/EmCommissionOut", filename,
					this));
			try {
				file.createNewFile();
			} catch (Exception e) {
				Messagebox.show("生成文件出错,请再次点击生成!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				break excel;
			}

			// 读取Excel模板
			Workbook wb = Workbook.getWorkbook(new File(new plyUtil()
					.getAbsolutePath(
							"/../../OfficeFile/Templet/EmCommissionOut",
							"wtjscj_out_excelzyt.xls", this)));

			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

			// 生成工作表,(name:First Sheet,参数0表示这是第一页)
			WritableSheet sheet = wwb.getSheet(0);

			// 开始写入内容
			try {
				
				//Label label = null;
				jxl.write.Label label =null;
				
				CellFormat cf = null;
//				WritableCellFormat wcf = null; 

//				// 获取字体样式
//				cf = sheet.getCell(1, 1).getCellFormat();
//				wcf = new WritableCellFormat(cf);
				// 设置字体格式
				WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
						WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
				WritableCellFormat wcf = new WritableCellFormat(wf);
				
				
				setEcycmodellist(bll.searchembaselisthd(wtztlist.indexOf(wtzt),id));
				java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
				
				 List<EmCommissionYearChangemModel> ecycmodellists = new ArrayList<EmCommissionYearChangemModel>();
				 EmCommissionYearChangemModel modela= new EmCommissionYearChangemModel();
				
				 List row = gridcols.getRows().getChildren();
				    for(Object obj:row){
				      Row comp = (Row) obj;
				      Checkbox ck = (Checkbox) comp.getChildren().get(gridcols.getColumns().getChildren().size()-1);
				   
				      if(ck.isChecked()==true){
				    	  System.out.println(ck.getValue());
				    	  System.out.println(ecycmodellist.get(Integer.parseInt(ck.getValue().toString())).getEcyc_id());
				    	  modela=ecycmodellist.get(Integer.parseInt(ck.getValue().toString()));
				    	  ecycmodellists.add(modela);
				    	 // ecycmodellist.remove(Integer.parseInt(ck.getValue().toString()));
				    	  
	    	 
							} 
				      }
	
				if (ecycmodellists.size()==0)
				{
					Messagebox.show("还没有勾选数据呢，亲!", "info", Messagebox.OK,
							Messagebox.INFORMATION);
				}
				else
				{
				for (int m=0;m<ecycmodellists.size();m++)
				{
					//委托方雇员编号
					label = new jxl.write.Label(1, m+1,ecycmodellists.get(m).getGid().toString(), wcf);
					sheet.addCell(label);
					//雇员姓名
					label = new jxl.write.Label(3,m+1, ecycmodellists.get(m).getEmba_name().toString(), wcf);
					sheet.addCell(label);
					//委托方公司编号
					label = new jxl.write.Label(5,m+1,ecycmodellists.get(m).getCid().toString(), wcf);
					sheet.addCell(label);
					//公司名称
					label = new jxl.write.Label(7,m+1, ecycmodellists.get(m).getCoba_company().toString(), wcf);
					sheet.addCell(label);
					//身份证号码
					label = new jxl.write.Label(8,m+1,ecycmodellists.get(m).getEmba_idcard().toString(), wcf);
					sheet.addCell(label);
					//执行城市
					label = new jxl.write.Label(9,m+1,ecycmodellists.get(m).getCity().toString(), wcf);
					sheet.addCell(label);
					
					
					
					if (model.getEcyt_ylao()==1)
					{
						//现养老企业基数
						label = new jxl.write.Label(11,m+1, df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
						//现养老个人基数
						label = new jxl.write.Label(13, m+1, df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
					}
					if (model.getEcyt_gshang()==1)
					{
						//现工伤企业基数
						label = new jxl.write.Label(29,m+1,  df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
					}
					if (model.getEcyt_yliao()==1)
					{
						//现养老医疗企业基数
						label = new jxl.write.Label(17,m+1, df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
						//现养老个医疗人基数
						label = new jxl.write.Label(19,m+1, df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
					}
					if (model.getEcyt_sye()==1)
					{
						//现失业企业基数
						label = new jxl.write.Label(23,m+1, df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
						//现失业个人基数
						label = new jxl.write.Label(25, m+1, df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
					}
					if (model.getEcyt_syu()==1)
					{
						//现生育企业基数
						label = new jxl.write.Label(33,m+1, df.format(ecycmodellists.get(m).getEcyc_sb_base()).toString(), wcf);
						sheet.addCell(label);
						
					}
					
					
//					//现养老企业基数
//					label = new jxl.write.Label(11,m+1, df.format(ecycmodellist.get(m).getEcyc_yl_base()).toString(), wcf);
//					sheet.addCell(label);
//					//现养老个人基数
//					label = new jxl.write.Label(13, m+1, df.format(ecycmodellist.get(m).getEcyc_yl_base()).toString(), wcf);
//					sheet.addCell(label);
//					//现养老医疗企业基数
//					label = new jxl.write.Label(17,m+1, df.format(ecycmodellist.get(m).getEcyc_jl_base()).toString(), wcf);
//					sheet.addCell(label);
//					//现养老个医疗人基数
//					label = new jxl.write.Label(19,m+1, df.format(ecycmodellist.get(m).getEcyc_jl_base()).toString(), wcf);
//					sheet.addCell(label);
//					//现失业企业基数
//					label = new jxl.write.Label(23,m+1, df.format(ecycmodellist.get(m).getEcyc_sye_base()).toString(), wcf);
//					sheet.addCell(label);
//					//现失业个人基数
//					label = new jxl.write.Label(25, m+1, df.format(ecycmodellist.get(m).getEcyc_sye_base()).toString(), wcf);
//					sheet.addCell(label);
//					//现工伤企业基数
//					label = new jxl.write.Label(29,m+1,  df.format(ecycmodellist.get(m).getEcyc_gs_base()).toString(), wcf);
//					sheet.addCell(label);
//					//现生育企业基数
//					label = new jxl.write.Label(33,m+1, df.format(ecycmodellist.get(m).getEcyc_syu_base()).toString(), wcf);
//					sheet.addCell(label);
					//现公积金企业基数
					label = new jxl.write.Label(37,m+1,  df.format(ecycmodellists.get(m).getEcyc_house_base()).toString(), wcf);
					sheet.addCell(label);
					//现公积金个人基数
					label = new jxl.write.Label(39, m+1, df.format(ecycmodellists.get(m).getEcyc_house_base()).toString(), wcf);
					sheet.addCell(label);
		
					//现公积金企业比例

					label = new jxl.write.Label(41,m+1,ecycmodellists.get(m).getEcyc_house_cp().toString(), wcf);
					sheet.addCell(label);
				
					//现公积金个人比例
					label = new jxl.write.Label(43,m+1,ecycmodellists.get(m).getEcyc_house_op().toString(), wcf);
					sheet.addCell(label);
					
					//现补充公积金个人基数
					label = new jxl.write.Label(47, m+1, df.format(ecycmodellists.get(m).getEcyc_bc_base()).toString(), wcf);
					sheet.addCell(label);
					//现补充公积金个人基数
					label = new jxl.write.Label(49,m+1,  df.format(ecycmodellists.get(m).getEcyc_bc_base()).toString(), wcf);
					sheet.addCell(label);
					//现补充公积金企业比例
					label = new jxl.write.Label(51,m+1,  ecycmodellists.get(m).getEcyc_bc_op().toString(), wcf);
					sheet.addCell(label);
					//现补充公积金个人比例
					label = new jxl.write.Label(53,m+1,ecycmodellists.get(m).getEcyc_bc_cp().toString(), wcf);
					sheet.addCell(label);
					//备注
					if(ecycmodellists.get(m).getEcyc_remark()==null)
					{
				
					}else
					{
						label = new jxl.write.Label(56,m+1,ecycmodellists.get(m).getEcyc_remark().toString(), wcf);
						sheet.addCell(label);
					}
					if(ecycmodellists.get(m).getCoba_client()!=null)
					{
					label = new jxl.write.Label(57,m+1,ecycmodellists.get(m).getCoba_client().toString(), wcf);
					sheet.addCell(label);
					}
					
					//更新状态为已导出
					bll.updateemcomm("("+ecycmodellists.get(m).getEcyc_id()+")", 3);
				}
				
				// 写入数据
				wwb.write();
				// 关闭文件
				wwb.close();
				try {
					FileOperate.download("OfficeFile/DownLoad/EmCommissionOut/"
							+ filename);
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					break excel;
				}
				}
						
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("写入内容失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
				break excel;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
}




public CoBaseModel getCoba_clinet() {
	return coba_clinet;
}


public void setCoba_clinet(CoBaseModel coba_clinet) {
	this.coba_clinet = coba_clinet;
}


public String getCbcity() {
	return cbcity;
}

public void setCbcity(String cbcity) {
	this.cbcity = cbcity;
}

public String getWtjgname() {
	return wtjgname;
}

public void setWtjgname(String wtjgname) {
	this.wtjgname = wtjgname;
}

public List<String> getProvincelist() {
	return provincelist;
}

public void setProvincelist(List<String> provincelist) {
	this.provincelist = provincelist;
}
public List<String> getCitylist() {
	return citylist;
}
public void setCitylist(List<String> citylist) {
	this.citylist = citylist;
}
public List<CoAgencyBaseModel> getWtjglist() {
	return wtjglist;
}

public void setWtjglist(List<CoAgencyBaseModel> wtjglist) {
	this.wtjglist = wtjglist;
}



public String getCoba_company() {
	return coba_company;
}


public void setCoba_company(String coba_company) {
	this.coba_company = coba_company;
}


public String getEmba_name() {
	return emba_name;
}


public void setEmba_name(String emba_name) {
	this.emba_name = emba_name;
}


 

public Integer getCid() {
	return cid;
}


public void setCid(Integer cid) {
	this.cid = cid;
}


public Integer getGid() {
	return gid;
}


public void setGid(Integer gid) {
	this.gid = gid;
}


public String getWtzt() {
	return wtzt;
}



public void setWtzt(String wtzt) {
	this.wtzt = wtzt;
}



				public List<String> getRegionlist() {
	return regionlist;
}



public void setRegionlist(List<String> regionlist) {
	this.regionlist = regionlist;
}



				public String getCityname() {
	return cityname;
}



public void setCityname(String cityname) {
	this.cityname = cityname;
}



				public List<EmCommissionYearChangemModel> getEcycmodellist() {
					return ecycmodellist;
				}
				public void setEcycmodellist(List<EmCommissionYearChangemModel> ecycmodellist) {
					this.ecycmodellist = ecycmodellist;
				}
				public List<EmCommissionyearchangetitleModel> getEmcomm() {
					return emcomm;
				}
				public void setEmcomm(List<EmCommissionyearchangetitleModel> emcomm) {
					this.emcomm = emcomm;
				}
				public List<EmCommissionyearchangetitleModel> getSemcomm() {
					return semcomm;
				}
				public void setSemcomm(List<EmCommissionyearchangetitleModel> semcomm) {
					this.semcomm = semcomm;
				}
				public EmCommissionyearchangetitleModel getModel() {
					return model;
				}
				public void setModel(EmCommissionyearchangetitleModel model) {
					this.model = model;
				}
				public Grid getGridwin() {
					return gridwin;
				}
				public void setGridwin(Grid gridwin) {
					this.gridwin = gridwin;
				}
				public Columns getGridcols() {
					return gridcols;
				}
				public void setGridcols(Columns gridcols) {
					this.gridcols = gridcols;
				}
				public Rows getGridrows() {
					return gridrows;
				}
				public void setGridrows(Rows gridrows) {
					this.gridrows = gridrows;
				}
}

