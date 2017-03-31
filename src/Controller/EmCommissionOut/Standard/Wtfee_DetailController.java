package Controller.EmCommissionOut.Standard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.Standard.WtServiceStandardBll;
import bll.EmCommissionOut.Standard.wtoutFeeBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.PubProCityModel;
import Model.WtServiceStandardrelationModel;
import Model.wtoutFeeModel;
import Util.UserInfo;

public class Wtfee_DetailController {
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private PubProCityModel ppcModel = new PubProCityModel();
	private CoBaseModel com = new CoBaseModel();
	private String wotsfee;
	
	
	private List<WtServiceStandardrelationModel> mlsit = new ArrayList<>();
	private WtServiceStandardrelationModel mm=new WtServiceStandardrelationModel();
	WtServiceStandardBll blls = new WtServiceStandardBll();
	
	private wtoutFeeModel m =new wtoutFeeModel();
	wtoutFeeBll bll= new wtoutFeeBll();
	// 默认委托机构
	private CoAgencyBaseCityRelViewModel cabm = new CoAgencyBaseCityRelViewModel();
	Integer cid=0;
	Integer Wtot_feeid=0;
	public Wtfee_DetailController() throws SQLException
	{
		

		cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
				.toString());
		
		Wtot_feeid = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());
		
		// 城市
		String strwhere=" and  a.Wtot_feeid="+Wtot_feeid;
		setCityList(bll.getCityList());
		setCom(bll.getCobaInfo(cid));
		setM(bll.getmodellist(strwhere).get(0));
		
		
		setCabm(bll.getCoAgencyListView(m.getCoba_id()).get(0));
		
		
		String strwhere1="and a.wtss_id= "+m.getWtss_id();
		
		System.out.print(strwhere1);
		mlsit=blls.getmodellist(strwhere1);
		mm=mlsit.get(0);
		
	}
	
	//城市和默认机构
	@Command("cityOnChange")
	@NotifyChange({ "cabm", "colList" })
	public void cityOnChange() {
		

		if (ppcModel != null) {
			setCabm(bll.getDefaultCoAgency(ppcModel.getId()));
			m.setCoba_id(cabm.getCabc_id());
			if (cabm.getCoab_name() == null || cabm.getCoab_name().isEmpty()) {
				cabm.setCoab_name("无");
			}
			

		}
		
	}
	
	@Command("openwin")
	public void openwin() {
		if (cid >0) {
			Map map = new HashMap();
			map.put("cid", cid);
			map.put("coco_compactid",null);
			
			Window window = (Window) Executions.createComponents(
					"/CoBase/CoBase_SelectCoOffersc.zul", null, map);
			window.doModal();
		}
	}
	
	@Command("openwt")
	public void openwt() {
		if (cid >0) {
			Map map = new HashMap();
			map.put("daid", mm.getWtss_id());
			map.put("cid", cid);
			Window window = (Window) Executions.createComponents(
					"/EmCommissionOut/Standard/Wtstandard_Detail.zul", null, map);
			window.doModal();
		}
	}
	
	
	
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		//Integer msgint=0;
		String[] str = null;
		if (m.getWtot_feetitle() == null || m.getWtot_feetitle().isEmpty()) {
			Messagebox.show("请输入名称!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {

			try {

				if(bll.wtfeeupdateownmonth(m)>0)
				{
					Messagebox.show("更新成功!", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				}
				else
				{
					Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}
	
	

	
	public List<WtServiceStandardrelationModel> getMlsit() {
		return mlsit;
	}

	public void setMlsit(List<WtServiceStandardrelationModel> mlsit) {
		this.mlsit = mlsit;
	}

	public WtServiceStandardrelationModel getMm() {
		return mm;
	}

	public void setMm(WtServiceStandardrelationModel mm) {
		this.mm = mm;
	}

	public ListModelList<PubProCityModel> getCityList() {
		return cityList;
	}
	public void setCityList(ListModelList<PubProCityModel> cityList) {
		this.cityList = cityList;
	}
	public PubProCityModel getPpcModel() {
		return ppcModel;
	}
	public void setPpcModel(PubProCityModel ppcModel) {
		this.ppcModel = ppcModel;
	}
	public CoBaseModel getCom() {
		return com;
	}
	public void setCom(CoBaseModel com) {
		this.com = com;
	}
	public String getWotsfee() {
		return wotsfee;
	}
	public void setWotsfee(String wotsfee) {
		this.wotsfee = wotsfee;
	}
	public wtoutFeeModel getM() {
		return m;
	}
	public void setM(wtoutFeeModel m) {
		this.m = m;
	}
	public CoAgencyBaseCityRelViewModel getCabm() {
		return cabm;
	}
	public void setCabm(CoAgencyBaseCityRelViewModel cabm) {
		this.cabm = cabm;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getWtot_feeid() {
		return Wtot_feeid;
	}
	public void setWtot_feeid(Integer wtot_feeid) {
		Wtot_feeid = wtot_feeid;
	}
	
	
	
	

}
