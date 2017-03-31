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
import dal.EmCommissionOut.EmCommissionOut_OperateDal;

import service.MessageService;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutModel;
import Model.EmCommissionYearChangemModel;
import Model.LoginModel;
import Model.PubProCityModel;
import Model.SysMessageModel;
import Model.WtServiceStandardrelationModel;
import Model.wtoutFeeModel;
import Util.DateStringChange;
import Util.LoginInfoStatic;
import Util.UserInfo;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;
import bll.EmCommissionOut.Standard.wtoutFeeBll;
import bll.EmCommissionOut.Standard.wtoutFeechangeBll;
import bll.SocialInsurance.Algorithm_InfoBll;
import bll.SocialInsurance.Algorithm_RegisteredDataBll;
import bll.SysMessage.SysMessage_AddBll;


public class Wtfee_changeUpdateController {

	
	private ListModelList<PubProCityModel> cityList = new ListModelList<>();
	private List<CoAgencyBaseCityRelViewModel> coAgencyList = new ListModelList<>();
	private CoAgencyBaseCityRelViewModel comodel= new CoAgencyBaseCityRelViewModel();
	private PubProCityModel ppcModel = new PubProCityModel();
	private CoBaseModel com = new CoBaseModel();
	private String wotsfee;
	
	private List<WtServiceStandardrelationModel> mlsit = new ArrayList<>();
	private WtServiceStandardrelationModel mm=new WtServiceStandardrelationModel();
	WtServiceStandardBll blls = new WtServiceStandardBll();
	EmCommissionOutListBll wtbll = new EmCommissionOutListBll();
	 
	private List<EmCommissionOutModel> emlist = new ListModelList<>();
	private EmCommissionOutModel wtm = new EmCommissionOutModel();
	private EmCommissionOutChangeModel wtcm = new EmCommissionOutChangeModel();
	private List<EmCommissionOutFeeDetailChangeModel> cfeeList = new ListModelList<>();// 委托外地明细表
	private List<EmCommissionOutFeeDetailModel> feeList = new ListModelList<>();
	
	
	private wtoutFeeModel m =new wtoutFeeModel();
	wtoutFeechangeBll bll= new wtoutFeechangeBll();
	// 默认委托机构
	private CoAgencyBaseCityRelViewModel cabm = new CoAgencyBaseCityRelViewModel();
	//Integer cid=0;
	private Integer Wtotchange_feeid=0;

	 
	
	public 	Wtfee_changeUpdateController() throws SQLException
	{
	//	cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
	//			.toString());
		
		Wtotchange_feeid = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());
		
	 
		
		// 城市
		String strwhere=" and  a.wtot_feechangeid="+Wtotchange_feeid;
		setCityList(bll.getCityList());
		
		setM(bll.getmodelchangelist(strwhere).get(0));
		
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
	 
		
		 //m.setWtot_fee(cabm.getCabc_fee());
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
							str=bll.wtfeechangeupdate(m, 0);
				
							
							if (str[0].equals("1")) {
								
								//更新在册表
								//插入变更表
								frashFrame(m);
							//	updatewt(m);
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
							
							str=bll.wtfeechangeupdate(m, 1);
					
						      
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
						
					//	updatewt(m);
						
						frashFrame(m);
						
						str=bll.wtfeechangeupdate(m, 1);
						
					
						
							
						if (str[0].equals("1")) {
	
							//更新在册表
							//插入变更表
							
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
	
	
	@Command
	public void frashFrame(final wtoutFeeModel ml
			)  {
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				try {
					LoginInfoStatic.setSetupdatestate(true);
					updatewt(ml);
					LoginInfoStatic.setSetupdatestate(false);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	
	public Boolean updatewt(wtoutFeeModel m ) throws SQLException
	{ 
		 
		StringBuilder wt_name=new StringBuilder();
		Boolean state=true;

		//更新在册表
			emlist=wtbll.getEmCommOutList(" and ecou_ecos_id="+m.getWtot_feeid()+" and  ecou_state<>3 and  ecou_addtype not in ('离职','取消','一次性费用') ");
			
		
			
			for (EmCommissionOutModel ml : emlist) {
				
				if (m.getWtot_fee().compareTo(ml.getEcou_service_fee())!=0)
				{
				wtcm.setEmba_name(ml.getEmba_name());
				wtcm.setGid(ml.getGid());
				wtcm.setCid(ml.getCid());
				wtcm.setEcoc_ecou_id(ml.getEcou_id());
				wtcm.setEcoc_soin_id(ml.getEcou_soin_id());
				wtcm.setEcoc_ecos_id(ml.getEcou_ecos_id());
				wtcm.setEcoc_addtype("调整");  
				wtcm.setEcoc_type("ecocchange");
				wtcm.setEcoc_idcard(ml.getEcou_idcard());
				wtcm.setEcoc_email(ml.getEcou_email());
				wtcm.setEcoc_phone(ml.getEcou_phone());
				wtcm.setEcoc_mobile(ml.getEcou_mobile());
				wtcm.setEcoc_in_date(ml.getEcou_in_date());
				wtcm.setEcoc_com_phone(ml.getEcou_com_phone());
				wtcm.setEcoc_com_principal(ml.getEcou_com_principal());
				wtcm.setEcoc_com_company(ml.getEcou_com_company());
				wtcm.setEcoc_domicile(ml.getEcou_domicile());
				wtcm.setEcoc_compact_jud(ml.getEcou_compact_jud());
				wtcm.setEcoc_compact_f(ml.getEcou_compact_f());
				wtcm.setEcoc_compact_l(ml.getEcou_compact_l());
				wtcm.setEcoc_salary(ml.getEcou_salary());
				wtcm.setEcoc_sb_base(ml.getEcou_sb_base());
				wtcm.setEcoc_house_base(ml.getEcou_house_base());
				wtcm.setEcoc_sb_em_sum(ml.getEcou_sb_em_sum());
				wtcm.setEcoc_sb_co_sum(ml.getEcou_sb_co_sum());
				wtcm.setEcoc_sb_sum(ml.getEcou_gjj_sum());
				wtcm.setEcoc_gjj_em_sum(ml.getEcou_gjj_em_sum());
				wtcm.setEcoc_gjj_co_sum(ml.getEcou_gjj_co_sum());
				wtcm.setEcoc_gjj_sum(ml.getEcou_gjj_sum());
				wtcm.setEcoc_welfare_sum(ml.getEcou_welfare_sum());
				wtcm.setEcoc_service_fee(m.getWtot_fee());
				wtcm.setEcoc_file_fee(ml.getEcou_file_fee());
				wtcm.setEcoc_sum(ml.getEcou_sum());
				wtcm.setEcoc_stop_date(ml.getEcou_stop_date());
				wtcm.setEcoc_stop_cause(ml.getEcou_stop_cause());
				wtcm.setEcoc_cancel_cause(ml.getEcou_cancel_cause());
				wtcm.setEcoc_laststate(0);
				wtcm.setEcoc_state(1);
				wtcm.setEcoc_client(ml.getEcou_client());
				wtcm.setEcoc_addname(m.getWtot_addname());
				wtcm.setEcoc_remark("调整服务费[系统生成]");
		/*		
				wtcm.setEcoc_title_date(DateStringChange.Datestringnow("yyyy-MM")+"-01");
		*/		
				wtcm.setEcoc_title_date(DateStringChange.DatetoSting(m.getWtot_comfdate(), "yyyy-MM-01"));
				feeList=wtbll.getFeeDetailFwListall(" and eofd_ecou_id="+ml.getEcou_id()+"");
				cfeeList.clear();
				for (EmCommissionOutFeeDetailModel m1 : feeList) {
					
					 
				
					EmCommissionOutFeeDetailChangeModel cm1 = new EmCommissionOutFeeDetailChangeModel();
					cm1.setEofc_eofd_id(m1.getEofd_id());
					cm1.setEofc_sicl_id(m1.getEofd_sicl_id());
					cm1.setEofc_ecop_id(m1.getEofd_ecop_id());
					cm1.setEofc_name(m1.getEofd_name());
					cm1.setEofc_content(m1.getEofd_content());
					cm1.setEofc_em_base(m1.getEofd_em_base());
					cm1.setEofc_co_base(m1.getEofd_co_base());
					cm1.setEofc_cp(m1.getEofd_cp());
					cm1.setEofc_op(m1.getEofd_op());
					cm1.setEofc_em_sum(m1.getEofd_em_sum());
					cm1.setEofc_co_sum(m1.getEofd_co_sum());
					cm1.setEofc_month_sum(m1.getEofd_month_sum());
				/*	
					cm1.setEofc_start_date(DateStringChange.Datestringnow("yyyy-MM")+"-01");
				*/
					cm1.setEofc_start_date(DateStringChange.DatetoSting(m.getWtot_comfdate(), "yyyy-MM-01"));
					//wtcm.setEcoc_title_date(DateStringChange.DatetoSting(m.getWtot_comfdate(), "yyyy-MM-dd"));
					cm1.setEofc_state(1);
					cm1.setTempDate(m1.getTempDate());
					cm1.setSicl_class(m1.getSicl_class());
					cfeeList.add(cm1);
					
				}

				wt_name.setLength(0);
				 
				for (EmCommissionOutFeeDetailChangeModel m12 : cfeeList) {
					// new
					// EmCommissionOut_OperateDal().addBJFeeDetail(m1);

					wt_name.append(m12.getEofc_sicl_id()
							.toString() + ",");
					wt_name.append(m12.getEofc_name().toString()
							+ ",");
					wt_name.append(m12.getEofc_co_base()
							.toString() + ",");
					wt_name.append(m12.getEofc_em_base()
							.toString() + ",");
					wt_name.append(m12.getEofc_cp() + ",");
					wt_name.append(m12.getEofc_op() + ",");
					wt_name.append(m12.getEofc_co_sum() + ",");
					wt_name.append(m12.getEofc_em_sum() + ",");
					if ("服务费".equals(m12.getEofc_name()))
					{
						wt_name.append(m.getWtot_fee()
								.toString() + ",");
					}
					else
					{
					wt_name.append(m12.getEofc_month_sum()
							.toString() + ",");
					}
					
					wt_name.append(m12.getEofc_start_date()
							+ "|");
				 
				}
				
				
				EmCommissionOut_OperateBll wtbllp = new EmCommissionOut_OperateBll();
				
				String[] str = wtbllp.change(wtcm, wtcm.getEmba_name(),wt_name.toString());
				
				 if (str[0].equals("0")) {  
					 state=false;
				}
				}
			
			}
			 
		//插入变更表
		
		return state;
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

 

	public Integer getWtotchange_feeid() {
		return Wtotchange_feeid;
	}

	public void setWtotchange_feeid(Integer wtotchange_feeid) {
		Wtotchange_feeid = wtotchange_feeid;
	}

	public CoAgencyBaseCityRelViewModel getComodel() {
		return comodel;
	}

	public void setComodel(CoAgencyBaseCityRelViewModel comodel) {
		this.comodel = comodel;
	}
	
	
	
	

}
