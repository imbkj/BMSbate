package Controller.EmCommissionOut.Standard;

import impl.MessageImpl;

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

import dal.LoginDal;

import service.MessageService;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.LoginModel;
import Model.PubProCityModel;
import Model.SysMessageModel;
import Model.WtServiceStandardrelationModel;
import Model.wtoutFeeModel;
import Util.UserInfo;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;
import bll.EmCommissionOut.Standard.wtoutFeeBll;
import bll.SysMessage.SysMessage_AddBll;


public class Wtfee_UpdateController {

	
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
	wtoutFeeBll bll= new wtoutFeeBll();
	// 默认委托机构
	private CoAgencyBaseCityRelViewModel cabm = new CoAgencyBaseCityRelViewModel();
	//Integer cid=0;
	Integer Wtot_feeid=0;
	
	public 	Wtfee_UpdateController() throws SQLException
	{
	//	cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
	//			.toString());
		
		Wtot_feeid = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());
		
		System.out.println(Wtot_feeid);
		
		// 城市
		String strwhere=" and  a.Wtot_feeid="+Wtot_feeid;
		setCityList(bll.getCityList());
		
		setM(bll.getmodellist(strwhere).get(0));
		
		setCom(bll.getCobaInfo(m.getCid()));
		
		
		setCabm(bll.getCoAgencyListView(m.getCoba_id()).get(0));
		
		setCoAgencyList(bll.getCoAgencyList(m.getCity()));
		
		String strwhere1="and a.wtss_id= "+m.getWtss_id();
		
		System.out.print(strwhere1);
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
	 
		
		// m.setWtot_fee(cabm.getCabc_fee());
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
				m.setWtot_editname(UserInfo.getUsername());
				if (m.getWtot_state()==0)
				{
					m.setWtot_examinenamekf(UserInfo.getUsername());
					
					//判断服务费
					if (m.getWtot_fee().compareTo(cabm.getCabc_fee())>=0) //  -1 小于   0 等于    1 大于
					{
						if (Messagebox.show("服务费不低于最低服务费提交后将立即生效，是否提交？", "INFORMATION", Messagebox.YES
								| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
							str=bll.wtfeeupdate(m, 0);
				
							if (str[0].equals("1")) {
								
								//审核后自动发送短信
								
								// 参数解释，业务表名：tablename；业务表id:id
								      MessageService msgservice=new MessageImpl("wtoutFee",m.getWtot_feeid());
								      LoginDal d =new LoginDal();
									      SysMessageModel sysm =new SysMessageModel();
								      String msgstr="客服经理已审核公司:"+m.getCoba_company()+"的"+m.getWtot_feetitle()+"委托出服务费";
								      sysm.setSyme_content(msgstr);//短信内容
								      sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
								      sysm.setSymr_name(m.getWtot_addname());//收件人姓名
								      sysm.setSymr_log_id(d.getUserIDByname((m.getWtot_addname())));//;
								      sysm.setEmail(1);
								      sysm.setEmailtitle("委托服务费审核");
								      msgservice.Add(sysm);

								Messagebox.show("提交成功", "INFORMATION", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("提交失败", "ERROR", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
					else
					{
						if (Messagebox.show("服务费低于最低服务费提交后将进入审核流程，是否提交", "INFORMATION", Messagebox.YES
								| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
							str=bll.wtfeeupdate(m, 1);
					
						      
							if (str[0].equals("1")) {
								
								//审核后自动发送短信
								// 参数解释，业务表名：tablename；业务表id:id
							      MessageService msgservice=new MessageImpl("wtoutFee",m.getWtot_feeid());
							      LoginDal d =new LoginDal();
							      
							      SysMessageModel sysm =new SysMessageModel();
							      String msgstr="部门经理已审核公司:"+m.getCoba_company()+"的"+m.getWtot_feetitle()+"委托出服务费";
							      sysm.setSyme_content(msgstr);//短信内容
							      sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
							      sysm.setSymr_name(m.getWtot_addname());//收件人姓名
							      sysm.setSymr_log_id(d.getUserIDByname((m.getWtot_addname())));//;
							      sysm.setEmail(1);
							      sysm.setEmailtitle("委托服务费审核");
							      msgservice.Add(sysm);
								
//							      sysm.setSymr_name(m.getWtot_examinenamekf());//收件人姓名
//							      sysm.setSymr_log_id(d.getUserIDByname((m.getWtot_examinenamekf())));//;
//							      sysm.setEmail(1);
//							      msgservice.Add(sysm);
							      
								
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
				else if (m.getWtot_state()==1)
				{
					
					if (Messagebox.show("审核后将立即生效，是否提交", "INFORMATION", Messagebox.YES
							| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
						m.setWtot_examinenameqg(UserInfo.getUsername());
						str=bll.wtfeeupdate(m, 1);
							
						if (str[0].equals("1")) {
	
								// 参数解释，业务表名：tablename；业务表id:id
							      MessageService msgservice=new MessageImpl("wtoutFee",m.getWtot_feeid());
							      LoginDal d =new LoginDal();
							      

							      
							      SysMessageModel sysm =new SysMessageModel();
							      String msgstr="全国项目部经理已审核公司:"+m.getCoba_company()+"的"+m.getWtot_feetitle()+"委托出服务费";
							      sysm.setSyme_content(msgstr);//短信内容
							      sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
							      sysm.setSymr_name(m.getWtot_addname());//收件人姓名
							      sysm.setSymr_log_id(d.getUserIDByname((m.getWtot_addname())));//;
							      sysm.setEmail(1);
							      sysm.setEmailtitle("委托服务费审核");
							      msgservice.Add(sysm);
								
							      sysm.setSymr_name(m.getWtot_examinenamekf());//收件人姓名
							      sysm.setSymr_log_id(d.getUserIDByname((m.getWtot_examinenamekf())));//;
							      sysm.setEmail(1);
							      msgservice.Add(sysm);
								
							
							Messagebox.show("提交成功", "INFORMATION", Messagebox.OK,
									Messagebox.INFORMATION);
							win.detach();
						} else {
							Messagebox.show("提交失败", "ERROR", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
					
				}
	
				
			} catch (Exception e) {
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
