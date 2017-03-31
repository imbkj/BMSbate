package Controller.EmCommissionOut.Standard;

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

import bll.EmCommissionOut.Standard.Standard_ListBll;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;
import bll.EmCommissionOut.Standard.wtoutFeeBll;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.PubProCityModel;
import Model.WtServiceStandardrelationModel;
import Model.wtoutFeeModel;
import Util.UserInfo;


public class Wtfee_AddController {
	
	wtoutFeeBll bll= new wtoutFeeBll();
	// 城市
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private PubProCityModel ppcModel = new PubProCityModel();
	private CoBaseModel com = new CoBaseModel();
	private String wotsfee;
 
	
	
	private wtoutFeeModel m =new wtoutFeeModel();
	
	private List<WtServiceStandardrelationModel> mlsit = new ArrayList<>();
	private WtServiceStandardrelationModel mm=new WtServiceStandardrelationModel();
	WtServiceStandardBll blls = new WtServiceStandardBll();

	// 默认委托机构
	private CoAgencyBaseCityRelViewModel cabm = new CoAgencyBaseCityRelViewModel();
	
	private List<CoAgencyBaseCityRelViewModel> cabmlist = new ListModelList();
	
	Integer cid=0;
	public Wtfee_AddController() throws SQLException
	{
		cid =Integer.parseInt(Executions.getCurrent().getArg().get("cid")
				.toString());
		// 城市
		setCityList(bll.getCityList());
		
		setCom(bll.getCobaInfo(cid));
		
		
		String strwhere="and a.cid= "+cid;
		
		System.out.print(strwhere);
		mlsit=blls.getmodelListonly(strwhere);
		
		
	}
	
	//城市和默认机构
	@Command("cityOnChange")
	@NotifyChange({ "cabm", "colList","cabmlist" })
	public void cityOnChange() {
		cabm.setCoab_name("无");

		if (ppcModel != null) {
			setCabmlist(bll.getDefaultCoAgencylist(ppcModel.getId()));
			
			setCabm(cabmlist.get(0));
			//setCabm(bll.getDefaultCoAgencylist(ppcModel.getId()));
			m.setCoba_id(cabm.getCabc_id());
		//	m.setWtot_fee(cabm.getCabc_fee());
			if (cabm.getCoab_name() == null || cabm.getCoab_name().isEmpty()) {
				cabm.setCoab_name("无");
			}
			

		}
		
	}
	
	public List<CoAgencyBaseCityRelViewModel> getCabmlist() {
		return cabmlist;
	}

	public void setCabmlist(List<CoAgencyBaseCityRelViewModel> cabmlist) {
		this.cabmlist = cabmlist;
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {
		//Integer msgint=0;
		String[] str = null;
		if (m.getWtot_feetitle() == null || m.getWtot_feetitle().isEmpty()) {
			Messagebox.show("请输入名称!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else if (mm.getWtss_id()==0)
		{
			Messagebox.show("请选择委托出标准!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{

			try {
				m.setCid(cid);
				m.setWtot_addname(UserInfo.getUsername());
				m.setWtot_state(0);
				m.setCoba_id(cabm.getCabc_id());
				m.setWtss_id(mm.getWtss_id());
				m.setWtot_fee(cabm.getCabc_fee());
			
				String strwhere ="and a.cid="+cid+" and e.wtss_id ="+ m.getWtss_id()+"  and a.coab_id="+m.getCoba_id()+"   AND a.wtot_state NOT IN (4,5) ";
				List<wtoutFeeModel> mlist =bll.getmodellist(strwhere);
				
				if (mlist.size()>0)
				{
//					if (Messagebox.show("该地区已有在审核中或已生效的服务费,是否要继续添加？", "INFORMATION", Messagebox.YES
//							| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
//						str=bll.Wtfeeadd(m,com.getCoba_shortname());
//						
//						
//					}
//					
					Messagebox.show("该公司已有此地区的委托出服务费不允许重复添加,请核查！", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
				else
				{
					str=bll.Wtfeeadd(m,com.getCoba_shortname());
					if (str[0].equals("1")) {
						Messagebox.show("添加成功", "INFORMATION", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
						
					
					} else {
						Messagebox.show("添加失败", "ERROR", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}
	
	@Command("openwin")
	public void openwin() {
		if (cid >0) {
			Map map = new HashMap();
			map.put("cid", cid);
			map.put("coco_compactid", "");
			Window window = (Window) Executions.createComponents(
					"/CoBase/CoBase_SelectCoOfferwt.zul", null, map);
			window.doModal();
		}
	}
	
	
	
	public WtServiceStandardrelationModel getMm() {
		return mm;
	}

	public void setMm(WtServiceStandardrelationModel mm) {
		this.mm = mm;
	}

	public List<WtServiceStandardrelationModel> getMlsit() {
		return mlsit;
	}

	public void setMlsit(List<WtServiceStandardrelationModel> mlsit) {
		this.mlsit = mlsit;
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

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
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

	public CoAgencyBaseCityRelViewModel getCabm() {
		return cabm;
	}

	public void setCabm(CoAgencyBaseCityRelViewModel cabm) {
		this.cabm = cabm;
	}

	
	
}
