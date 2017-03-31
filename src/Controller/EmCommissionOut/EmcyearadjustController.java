package Controller.EmCommissionOut;

import impl.PubCityImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.impl.BinderUtil;
import org.zkoss.zhtml.I;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Controller.CoPolicyNotice.Pono_PubInfoAddController;
import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.EmCommissionyearchangetitleModel;
import bll.CoAgency.BaseInfo_CityDisBll;
import bll.CoAgency.BaseInfo_DisBll;
import bll.EmCommissionOut.EmCommissionyearchangetitleBll;
import Util.UserInfo;
import Util.DateStringChange;

public class EmcyearadjustController {
	private List<String> regionlist;
	private List<String> provincelist;
	private List<String> citylist;

	private List<CoAgencyBaseModel> wtjglist;
	private CoAgencyBaseModel comm = new CoAgencyBaseModel();
	private PubCityImpl cityimpl = new PubCityImpl();
	private BaseInfo_DisBll Dbll = new BaseInfo_DisBll();
	private EmCommissionyearchangetitleBll ecbll = new EmCommissionyearchangetitleBll();
	private String cityname;
	private Boolean wtdh;
	private Boolean wtdl;
	private ArrayList<EmCommissionyearchangetitleModel> ecmodellist = new ArrayList<EmCommissionyearchangetitleModel>();

	/**
	 * @param args
	 */

	public EmcyearadjustController() throws Exception {
		regionlist = cityimpl.getRegionName();
		provincelist = cityimpl.getProvinceName();
		citylist = cityimpl.getCityName();
	}

	// 根据地区查询省份
	@Command("selProvince")
	@NotifyChange({ "provincelist", "citylist" })
	public void selProvince(@BindingParam("contact") String region,
			@BindingParam("com") Combobox province) throws Exception {
		province.setValue(null);
		setProvincelist(cityimpl.getProvinceNameByRegion(region));
		setCitylist(cityimpl.getCityName());
	}

	// 根据省份查询城市
	@Command("selCity")
	@NotifyChange("citylist")
	public void selCity(@BindingParam("contact") String province,
			@BindingParam("com") Combobox city) throws Exception {
		city.setValue(null);
		setCitylist(cityimpl.getCityName(province));

		System.out.println(cityname);
		Map map = new HashMap<>();
		map.put("pono_city", cityname);// 城市
		map.put("pono_agencies ", comm.getCoab_name()); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map);
	}

	// 根据城市查询委托机构
	@Command("selwtjg")
	@NotifyChange("wtjglist")
	public void selwtjg(@BindingParam("contact") String city,
			@BindingParam("com") Combobox wtjg) throws Exception {
		wtjg.setValue(null);
		setWtjglist(ecbll.getCoAgencyBaseAll(city));
		CoAgencyBaseModel m = new CoAgencyBaseModel();
		m.setCoab_name("全部");
		m.setCoab_id(0);
		wtjglist.add(0, m);

		Map map = new HashMap<>();
		map.put("pono_city", cityname);// 城市
		map.put("pono_agencies ", comm); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map);

	}

	// 提交年调
	@Command("btsubmit")
	// @NotifyChange("wtjglist")
	public void btsubmit(@BindingParam("gd") Listbox listb,
			@BindingParam("city") String city,
			@BindingParam("remark") String remark,
			@BindingParam("cbdh") boolean cbdh,
			@BindingParam("cbdl") boolean cbdl,
			@BindingParam("datestart") Date datestart,
			@BindingParam("dateending") Date dateending,
			@BindingParam("ylaont") boolean ylaont,
			@BindingParam("yliaont") boolean yliaont,
			@BindingParam("syent") boolean syent,
			@BindingParam("syunt") boolean syunt,
			@BindingParam("gshangnt") boolean gshangnt,
			@BindingParam("gjjnt") boolean gjjnt,
			@BindingParam("bcgjjnt") boolean bcgjjnt,
			@BindingParam("lzstate") boolean lzstate,
			@BindingParam("lzdatestart") Date lzdatestart,
			@BindingParam("lzdateending") Date lzdateending,
			@BindingParam("ecytstartdate") Date ecytstartdate
			
			) throws Exception {

		String[] message = new String[5];
		
		String[] mescheck = new String[2];

		// 检查
		String str = "";

		if (city == "" || city.equals("")) {
			str = "请选择城市";
		}
		
		

		if (!cbdh & !cbdl) {
			str = "请选择开户类型";
		}

		if (datestart == null) {
			str = "请选择年调起始时间";
		}
		if (dateending == null) {
			str = "请选择年调结束时间";
		}
		if (ecytstartdate == null) {
			str = "请选择参与年调的对象截止时间";
		}
		
		

		if (lzstate) {
			if (lzdatestart == null) {
				str = "请选择采集离职员工起始时间";
			}
			if (lzdateending == null) {
				str = "请选择采集离职员工结束时间";
			}
		}

		if (str == "" || str.equals("")) {
			//如果
			if (comm.getCoab_id() == 0) {
				
				wtjglist.remove(0);
				for (int i = 0; i < wtjglist.size(); i++) {
					EmCommissionyearchangetitleModel ecmodel = new EmCommissionyearchangetitleModel();
					// ecmodel.setEcyt_addname(UserInfo.getUsername());
					// comm.getCoab_id();
					ecmodel.setEcyt_state(0);
					// ecmodel.setEcyt_addname(UserInfo.getUsername());
					ecmodel.setEcyt_addname(UserInfo.getUsername());
					// ecmodel.setEcyt_addtime();
					if (bcgjjnt) {
						ecmodel.setEcyt_bcgjj(1);
					} else {
						ecmodel.setEcyt_bcgjj(0);
					}
					if (gjjnt) {
						ecmodel.setEcyt_gjj(1);
					} else {
						ecmodel.setEcyt_gjj(0);
					}
					if (gshangnt) {
						ecmodel.setEcyt_gshang(1);
					} else {
						ecmodel.setEcyt_gshang(0);
					}
					if (cbdh) {
						if (cbdl) {
							ecmodel.setEcyt_single(3);// 大户和独立户都采集
						} else {
							ecmodel.setEcyt_single(2);// 只采集大户
						}
					} else {
						if (cbdl) {
							ecmodel.setEcyt_single(1);// 只采集独立户
						} else {
							// 请选择账户类型
						}
					}
					if (syent) {
						ecmodel.setEcyt_sye(1);
					} else {
						ecmodel.setEcyt_sye(0);
					}
					if (syunt) {
						ecmodel.setEcyt_syu(1);
					} else {
						ecmodel.setEcyt_syu(0);
					}
					if (ylaont) {
						ecmodel.setEcyt_ylao(1);
					} else {
						ecmodel.setEcyt_ylao(0);
					}
					if (yliaont) {
						ecmodel.setEcyt_yliao(1);
					} else {
						ecmodel.setEcyt_yliao(0);
					}
					if (lzstate) {
						ecmodel.setEcyt_lzcj(1);

						ecmodel.setEcyt_lzstatedate(DateStringChange
								.DatetoSting(lzdatestart, "yyyy-MM-dd"));
						ecmodel.setEcyt_lzenddate(DateStringChange.DatetoSting(
								lzdateending, "yyyy-MM-dd"));
					} else {
						ecmodel.setEcyt_lzcj(0);
					}

					ecmodel.setEcyt_monthend(DateStringChange.DatetoSting(
							dateending, "yyyy-MM-dd"));
					ecmodel.setEcyt_monthsart(DateStringChange.DatetoSting(
							datestart, "yyyy-MM-dd"));
					ecmodel.setEcyt_remark(remark.toString());
				 
					ecmodel.setEcyt_startdate(DateStringChange.DatetoSting(
							ecytstartdate, "yyyy-MM-dd"));

					// ecmodel.setCoab_id(wtjglist.get(i).getCoab_id());
					int cabc_id = (ecbll.getcabc_id(cityname, wtjglist.get(i)
							.getCoab_name())).getCabc_id();
					ecmodel.setCoab_id(cabc_id);
					ecmodellist.add(ecmodel);
					
				}
				
				
				
				
				
				
			}
			else
			{
				EmCommissionyearchangetitleModel ecmodel = new EmCommissionyearchangetitleModel();
				// ecmodel.setEcyt_addname(UserInfo.getUsername());
				// comm.getCoab_id();
				ecmodel.setEcyt_state(1); //去掉审核那一步
				ecmodel.setEcyt_addname(UserInfo.getUsername());
				// ecmodel.setEcyt_addtime();
				if (bcgjjnt) {
					ecmodel.setEcyt_bcgjj(1);
				} else {
					ecmodel.setEcyt_bcgjj(0);
				}
				if (gjjnt) {
					ecmodel.setEcyt_gjj(1);
				} else {
					ecmodel.setEcyt_gjj(0);
				}
				if (gshangnt) {
					ecmodel.setEcyt_gshang(1);
				} else {
					ecmodel.setEcyt_gshang(0);
				}
				if (cbdh) {
					if (cbdl) {
						ecmodel.setEcyt_single(3);// 大户和独立户都采集
					} else {
						ecmodel.setEcyt_single(2);// 只采集大户
					}
				} else {
					if (cbdl) {
						ecmodel.setEcyt_single(1);// 只采集独立户
					} else {
						// 请选择账户类型
					}
				}
				if (syent) {
					ecmodel.setEcyt_sye(1);
				} else {
					ecmodel.setEcyt_sye(0);
				}
				if (syunt) {
					ecmodel.setEcyt_syu(1);
				} else {
					ecmodel.setEcyt_syu(0);
				}
				if (ylaont) {
					ecmodel.setEcyt_ylao(1);
				} else {
					ecmodel.setEcyt_ylao(0);
				}
				if (yliaont) {
					ecmodel.setEcyt_yliao(1);
				} else {
					ecmodel.setEcyt_yliao(0);
				}

				if (lzstate) {
					ecmodel.setEcyt_lzcj(1);

					ecmodel.setEcyt_lzstatedate(DateStringChange.DatetoSting(
							lzdatestart, "yyyy-MM-dd"));
					ecmodel.setEcyt_lzenddate(DateStringChange.DatetoSting(
							lzdateending, "yyyy-MM-dd"));
				} else {
					ecmodel.setEcyt_lzcj(0);
				}
				ecmodel.setEcyt_monthend(DateStringChange.DatetoSting(
						dateending, "yyyy-MM-dd"));
				ecmodel.setEcyt_monthsart(DateStringChange.DatetoSting(
						datestart, "yyyy-MM-dd"));
				ecmodel.setEcyt_remark(remark.toString());

				ecmodel.setCoab_id(ecbll.getcabc_id(cityname,
						comm.getCoab_name()).getCabc_id());
				ecmodel.setEcyt_startdate(DateStringChange.DatetoSting(
						ecytstartdate, "yyyy-MM-dd"));

				ecmodellist.add(ecmodel);		
				
			}
			
//			mescheck=checkdata(ecmodellist);
//			if (mescheck[0].equals("1"))
//			{
//				
//				Messagebox.show(mescheck[1], "ERROR", Messagebox.OK,
//								Messagebox.INFORMATION);
//			}
//			else
//			{
		 
				message = ecbll.changetitleadd(ecmodellist,
						UserInfo.getUsername());
				
				if (message[0]!=null & message[0].equals("1")) {
					Messagebox.show("提交成功", "提交成功", Messagebox.OK,
							Messagebox.INFORMATION);
					// 插入表
					Pono_PubInfoAddController pd = new Pono_PubInfoAddController();
					pd.InfoAdd(listb, Integer.parseInt(message[3]), "基数采集");
					
					
					Executions
					.sendRedirect("/EmCommissionOut/EmCyearaddlistqg.zul");

				}

				if (message[0]!=null & message[0].equals("2")) {

					Messagebox.show("该机构年调未完成。请勿重复添加!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
//			}
		
		 
		else
		{
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.INFORMATION);
			
			
		}
		
		
		
	}
	
	private String[] checkdata(ArrayList<EmCommissionyearchangetitleModel> alist) throws Exception
	{
		String[] masg=new String[2];
		
			
			masg=ecbll.checkdata(alist);
		
		
		return masg;
		
	}
	
	//打开新增政策通知页面
	@Command
	public void addnote()
	{
		if(cityname!=null&&!cityname.equals(""))
		{
		CoAgencyBaseCityRelModel model=new CoAgencyBaseCityRelModel();
		Integer cityid=cityimpl.getCityId(cityname);
		model.setId(cityid);
		model.setCity(cityname);
		Map map=new HashMap();
		map.put("model", model);
		map.put("type", "政府通知");
		map.put("classname", model.getCity());
		Window window=(Window)Executions.createComponents("/CoPolicyNotice/PoNo_InfoAdd.zul", null, map);
		window.doModal();
		}
		else
		{
			Messagebox.show("请选择城市", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		Map map = new HashMap<>();
		map.put("pono_city", cityname);// 城市
		map.put("pono_agencies ", comm); // 机构名称
		BindUtils.postGlobalCommand(null, null, "refreshlist", map);
	}

	public CoAgencyBaseModel getComm() {
		return comm;
	}

	public void setComm(CoAgencyBaseModel comm) {
		this.comm = comm;
	}

	public List<CoAgencyBaseModel> getWtjglist() {
		return wtjglist;
	}

	public void setWtjglist(List<CoAgencyBaseModel> wtjglist) {
		this.wtjglist = wtjglist;
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

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	public Boolean getWtdh() {
		return wtdh;
	}

	public void setWtdh(Boolean wtdh) {
		this.wtdh = wtdh;
	}

	public Boolean getWtdl() {
		return wtdl;
	}

	public void setWtdl(Boolean wtdl) {
		this.wtdl = wtdl;
	}

}
