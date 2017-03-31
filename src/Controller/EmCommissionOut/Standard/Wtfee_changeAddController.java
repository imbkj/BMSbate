package Controller.EmCommissionOut.Standard;

import impl.MessageImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;
import dal.LoginDal;

import bll.EmCommissionOut.Standard.Standard_ListBll;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;
import bll.EmCommissionOut.Standard.wtoutFeeBll;
import bll.EmCommissionOut.Standard.wtoutFeechangeBll;
import bll.SysMessage.SysMessage_AddBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.PubProCityModel;
import Model.SysMessageModel;
import Model.WtServiceStandardrelationModel;
import Model.wtoutFeeModel;
import Util.UserInfo;


public class Wtfee_changeAddController {
	
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coAgencyList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel comodel= new CoAgencyBaseCityRelViewModel();
	private PubProCityModel ppcModel = new PubProCityModel();
	private CoBaseModel com = new CoBaseModel();
	private String wotsfee;
	
	private List<WtServiceStandardrelationModel> mlsit = new ArrayList<>();
	private WtServiceStandardrelationModel mm=new WtServiceStandardrelationModel();
	WtServiceStandardBll blls = new WtServiceStandardBll();
	
	private wtoutFeeModel m =new wtoutFeeModel();
	wtoutFeechangeBll bll= new wtoutFeechangeBll();
	// 默认委托机构
	private CoAgencyBaseCityRelViewModel cabm = new CoAgencyBaseCityRelViewModel();
	//Integer cid=0;
	Integer Wtot_feeid=0;
	
	public 	Wtfee_changeAddController() throws SQLException
	{
		Wtot_feeid = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());
		
		System.out.println(Wtot_feeid);
		String strwhere=" and  a.Wtot_feeid="+Wtot_feeid;
		setCityList(bll.getCityList());
		setM(bll.getmodellist(strwhere).get(0));
		setCom(bll.getCobaInfo(m.getCid()));
		setCabm(bll.getCoAgencyListView(m.getCoba_id()).get(0));
		setCoAgencyList(bll.getCoAgencyList(m.getCity()));
		String strwhere1="and a.wtss_id= "+m.getWtss_id();
		mlsit=blls.getmodelListonly(strwhere1);
		mm=mlsit.get(0);
		
	}
	
	//城市和默认机构
	@Command("cityOnChange")
	@NotifyChange({ "cabm", "colList","coAgencyList" })
	public void cityOnChange() {
		

		if (ppcModel != null) {
			setCabm(bll.getDefaultCoAgency(ppcModel.getId()));
			m.setCoba_id(cabm.getCabc_id());
			if (cabm.getCoab_name() == null || cabm.getCoab_name().isEmpty()) {
				cabm.setCoab_name("无");
			}
			

		}
		
		setCoAgencyList(bll.getCoAgencyList(ppcModel.getId()));
		
		
		
	}
	
	// 默认机构最低服务费
	@Command("cityOnChangefee")
	@NotifyChange({ "cabm", "colList","m" })
	public void cityOnChangefee() {
		
//		cabm=comodel;
		
		//cabm.setCabc_fee(comodel.getCabc_fee());
	 
		
		 m.setWtot_fee(cabm.getCabc_fee());
		 m.setCoba_id(cabm.getCabc_id());
		
	}
	
	@Command("openwin")
	public void openwin() {
		if (m.getCid() >0) {
			Map map = new HashMap();
			map.put("cid", m.getCid());
			map.put("coco_compactid","");
			Window window = (Window) Executions.createComponents(
					"/CoBase/CoBase_SelectCoOfferwt.zul", null, map);
			window.doModal();
		}
	}
	
	@Command("openwt")
	public void openwt() {
		if (m.getCid() >0) {
			Map map = new HashMap();
			map.put("daid", mm.getWtss_id());
			map.put("cid", m.getCid());
			Window window = (Window) Executions.createComponents(
					"/EmCommissionOut/Standard/Wtstandard_Detail.zul", null, map);
			window.doModal();
		}
	}
	
	
	
	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		//Integer msgint=0;
		String[] str = null;

		try {
		if (Messagebox.show("是否要调整服务费？", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
			
			m.setWtot_state(0);
			m.setWtot_addname(UserInfo.getUsername());
			
			str=bll.Wtfeechangeadd(m,com.getCoba_shortname());
			if(m.getWtot_comfdate()==null)
			{
				Messagebox.show("请填写生效日期！", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
			else
			{
			if (str[0].equals("1")) {
				Messagebox.show("提交成功", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				
				win.detach();
			} else {
				Messagebox.show("提交失败", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
			} 
			}
		}
		 catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		
		}

	//退回
	@Command("returnback")
	public void returnback(@BindingParam("win") Window win) {
		String[] str = null;

		try{
		if (Messagebox.show("你确定要退回此条数据吗？", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
			
			str=bll.wtfeeback(m);
			
		
			
			if (str[0].equals("1")) {
				Messagebox.show("退回成功", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				
				win.detach();
			} else {
				Messagebox.show("退回失败", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		else
		{
			
		}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	
		}
	
	
	public int submit(int state,String content,String title,List<SysMessageModel> list) throws Exception {
		int issuccess = 0;
		SysMessage_AddBll bll = new SysMessage_AddBll();
		SysMessageModel model = new SysMessageModel();
		// 传参数
		model.setSyme_content(content);
		model.setSyme_title(title);
		model.setSyme_state(state);
		
		
	
		issuccess = bll.SysMessageAdd(model, list);
		return issuccess;
	}
	
	
	public List<CoAgencyBaseCityRelViewModel> getCoAgencyList() {
		return coAgencyList;
	}

	public void setCoAgencyList(List<CoAgencyBaseCityRelViewModel> coAgencyList) {
		this.coAgencyList = coAgencyList;
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

	public Integer getWtot_feeid() {
		return Wtot_feeid;
	}
	public void setWtot_feeid(Integer wtot_feeid) {
		Wtot_feeid = wtot_feeid;
	}

	public CoAgencyBaseCityRelViewModel getComodel() {
		return comodel;
	}

	public void setComodel(CoAgencyBaseCityRelViewModel comodel) {
		this.comodel = comodel;
	}
	
	
	
	

}
