package Controller.EmCommissionOut;

import impl.PubCityImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import Util.UserInfo;



import Model.CoAgencyBaseModel;
import Model.EmCommissionyearchangetitleModel;
import Util.RegexUtil;
import bll.CoAgency.BaseInfo_DisBll;
import bll.EmCommissionOut.EmCommissionyearchangetitleBll;

public class EmCyearaddlistqgController  extends SelectorComposer<Component>  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
	private List<String> regionlist;
	private List<String> provincelist;
	private List<String> citylist;
 
	private List<EmCommissionyearchangetitleModel> emcomm;
	private List<EmCommissionyearchangetitleModel> semcomm =new ListModelList();
	private EmCommissionyearchangetitleBll bll =new EmCommissionyearchangetitleBll();
 	private List<CoAgencyBaseModel> wtjglist;
//	private String city = "广州";
//	private String jgname = "中智广州经济技术合作公司";
	private EmCommissionyearchangetitleModel m;
	
	private String cityname;
	public String cbcity;
	public String wtjgname;
	public String wtzt;
	
	private PubCityImpl cbll = new PubCityImpl();
	private BaseInfo_DisBll Dbll = new BaseInfo_DisBll();
	private ArrayList<String> wtztlist =new ArrayList<String>();
	
	@Wire
	private Grid gridwin;
	@Wire
	private Columns gridcols;
	@Wire
	private Rows gridrows;

	
	
	@Wire
	private Checkbox editAll;	  //全选修改选项框
	public String getCbcity() {
		return cbcity;
	}


	public void setCbcity(String cbcity) {
		this.cbcity = cbcity;
	}


	@Wire
	private Button btplsh;

	public EmCyearaddlistqgController() throws Exception
	{

		
		setEmcomm(new ListModelList<>(bll.getemcommtlistsh(8)));
		//System.out.println(emcomm);
		
		regionlist = cbll.getRegionName();
		provincelist = cbll.getProvinceName();
		citylist = cbll.getCityName();
		
		search();
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

			
			
		}
		
				
		@Command("viewjtlist")
		public void viewjtlist(@BindingParam("city") String city,@BindingParam("jgname") String jgname,@BindingParam("id") String id) {
		
			Map<String, String> map = new HashMap<String, String>();
			map.put("city", city);
			map.put("jgname", jgname);
			map.put("id", id);
			Window window = (Window) Executions.createComponents(
					"../EmCommissionOut/EmCommissionntinfo.zul", null, map); 
			window.doModal();
		
		}

	// 检索
		@Command("search")
		@NotifyChange("semcomm")
		public void search() {
			semcomm.clear();

			for (EmCommissionyearchangetitleModel m : emcomm) {
				if (!"".equals(cityname)) {
					if (!RegexUtil.isExists(cityname, m.getCity())) {
						continue;
					}
				}
				if (!"".equals(wtjgname)) {
					if (!RegexUtil.isExists(wtjgname, m.getJgname())) {
						continue;
					}
				}
				semcomm.add(m);
				}
			
			
			//System.out.println(semcomm);
			}
		
		

		@Command("qrtime")
		public void qrtime(@BindingParam("city") String city,@BindingParam("jgname") String jgname,@BindingParam("id") String id) {
		
			Map<String, String> map = new HashMap<String, String>();
			map.put("city", city);
			map.put("jgname", jgname);
			map.put("id", id);
			Window window = (Window) Executions.createComponents(
					"../EmCommissionOut/EmCommissionOut_ntOperates.zul", null, map);
			window.doModal();
		
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
				    //	  System.out.print(ck.getValue()+",");
				      }
				   }
			}

		 

	public List<CoAgencyBaseModel> getWtjglist() {
			return wtjglist;
		}


		public void setWtjglist(List<CoAgencyBaseModel> wtjglist) {
			this.wtjglist = wtjglist;
		}


	public ArrayList<String> getWtztlist() {
			return wtztlist;
		}


		public void setWtztlist(ArrayList<String> wtztlist) {
			this.wtztlist = wtztlist;
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


	public String getCityname() {
		return cityname;
	}


	public void setCityname(String cityname) {
		this.cityname = cityname;
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


}
