package Controller.EmCommissionOut;

import impl.PubCityImpl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import Model.CoAgencyBaseModel;
import Model.EmCommissionyearchangetitleModel;
import Util.RegexUtil;
import Util.UserInfo;
import bll.CoAgency.BaseInfo_CityDisBll;
import bll.CoAgency.BaseInfo_DisBll;
import bll.EmCommissionOut.EmCommissionyearchangetitleBll;

public class EmCommissionOutsubmitController  extends SelectorComposer<Component>  {


private static final long serialVersionUID = 1L;
/**
 * @param args
 */
//public static void main(String[] args) {
//	// TODO Auto-generated method stub
//
//}

private List<EmCommissionyearchangetitleModel> emcomm;
private List<EmCommissionyearchangetitleModel> semcomm =new ListModelList();
private EmCommissionyearchangetitleBll bll =new EmCommissionyearchangetitleBll();

private String city = "北京";
private String jgname = "北京中智";
private EmCommissionyearchangetitleModel m;

@Wire
private Grid gridwin;
@Wire
private Columns gridcols;
@Wire
private Rows gridrows;



@Wire
private Checkbox editAll;	  //全选修改选项框
@Wire
private Button btplsh;

private int t_id;
private int d_id;
//private StringBuilder strsqlinfo;

private List<String> regionlist;
private List<String> provincelist;
private List<String> citylist;
private ArrayList<String> wtztlist =new ArrayList<String>();

private List<CoAgencyBaseModel> wtjglist;
private String cityname;
public String cbcity;
public String wtjgname;
public String wtzt;
private PubCityImpl cbll = new PubCityImpl();
private BaseInfo_DisBll Dbll = new BaseInfo_DisBll();

public EmCommissionOutsubmitController() throws Exception
{
	//节点ID
	//t_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());
    t_id=60;
	//数据ID
	//d_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
	d_id=60;
	
	
	regionlist = cbll.getRegionName();
	provincelist = cbll.getProvinceName();
	citylist = cbll.getCityName();
	
	setSemcomm(new ListModelList<>(bll.getemcommtlistall(1,cityname,wtjgname)));
	//System.out.println(emcomm);
//	search();
}



// 检索
	@Command("search")
	@NotifyChange("semcomm")
	public void search() {
		
		setSemcomm(new ListModelList<>(bll.getemcommtlistall(1,cityname,wtjgname)));
		
//		semcomm.clear();
//
//		for (EmCommissionyearchangetitleModel m : emcomm) {
//			if (!city.isEmpty()) {
//				if (!RegexUtil.isExists(city, m.getCity())) {
//					continue;
//				}
//			}
//			if (!jgname.isEmpty()) {
//				if (!RegexUtil.isExists(jgname, m.getJgname())) {
//					continue;
//				}
//			}
//			semcomm.add(m);
//			}
//		
//		System.out.println(semcomm);
		
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
	
	

	@Command("selectlist")
	@NotifyChange({"emcomm","ecycmodellist"})
	public void selectlist(@BindingParam("grd") Grid grid)
	{
		try {
			
				
			if (cbcity.equals(""))
			{
			
			}
			else
			{
				
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				    //审核
				    rowcout= bll.adutingemcommlist(idcountstr.substring(0,idcountstr.length()-1), UserInfo.getUsername());
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
					System.out.println(e);
					e.printStackTrace();
				}
	 }


public String getWtjgname() {
		return wtjgname;
	}



	public void setWtjgname(String wtjgname) {
		this.wtjgname = wtjgname;
	}



	public String getWtzt() {
		return wtzt;
	}



	public void setWtzt(String wtzt) {
		this.wtzt = wtzt;
	}



public String getCityname() {
		return cityname;
	}



	public void setCityname(String cityname) {
		this.cityname = cityname;
	}



public String getCbcity() {
		return cbcity;
	}



	public ArrayList<String> getWtztlist() {
	return wtztlist;
}



public void setWtztlist(ArrayList<String> wtztlist) {
	this.wtztlist = wtztlist;
}



	public void setCbcity(String cbcity) {
		this.cbcity = cbcity;
	}



public List<String> getRegionlist() {
		return regionlist;
	}



	public void setRegionlist(List<String> regionlist) {
		this.regionlist = regionlist;
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

public String getCity() {
	return city;
}

public void setCity(String city) {
	this.city = city;
}

public String getJgname() {
	return jgname;
}

public void setJgname(String jgname) {
	this.jgname = jgname;
}
}


