package bll.EmCommissionOut.Standard;

import impl.MessageImpl;
import impl.WorkflowCore.WfOperateImpl;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.MessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoBaseModel;
import Model.PubProCityModel;
import Model.SysMessageModel;
import dal.LoginDal;
import dal.EmCommissionOut.Standard.Standard_ListDal;
import dal.EmCommissionOut.Standard.Standard_OperateDal;
import dal.EmCommissionOut.Standard.wtoutFeeDal;
import Model.wtoutFeeModel;
import Util.UserInfo;

public class wtoutFeechangeBll  implements WfBusinessService  {
	
	// 获取城市列表
		public ListModelList<PubProCityModel> getCityList() throws SQLException {
			return new Standard_ListDal().getCityListforwt();
		}
		
		// 根据城市获取默认机构
		public CoAgencyBaseCityRelViewModel getDefaultCoAgency(Integer id) {
			return new Standard_ListDal().getDefaultCoAgency(id);
		}
		


		// 根据城市id获取所有机构列表
		public List<CoAgencyBaseCityRelViewModel> getCoAgencyList(Integer ppc_id) {
			return new Standard_ListDal().getCoAgencyList(ppc_id);
		}
		// 根据城市名获取所有机构列表
		public List<CoAgencyBaseCityRelViewModel> getCoAgencyList(String cityname) {
			return new Standard_ListDal().getCoAgencyList(cityname);
		}

		// 根据城市机构关系表id获取所有机构列表
		public List<CoAgencyBaseCityRelViewModel> getCoAgencyList1(Integer cabc_id) {
			return new Standard_ListDal().getCoAgencyList1(cabc_id);
		}
		// 根据城市机构关系表id获取所有机构列表
				public List<CoAgencyBaseCityRelViewModel> getCoAgencyListView(Integer cabc_id) {
					return new Standard_ListDal().getCoAgencyListView(cabc_id);
				}
		
		// 获取公司信息
		public CoBaseModel getCobaInfo(Integer cid) {
			return new Standard_ListDal().getCobaInfo(cid);
		}
		//插入服务费
		
		public String[] Wtfeeadd(wtoutFeeModel m,String company)
		{
			
			
			String[] str = new String[5];
			Object[] obj = {"1", m};
			WfOperateService wfs = new WfOperateImpl(new wtoutFeechangeBll());
			str = wfs.AddTaskToNext(obj, "委托出服务费新增",
					company + "委托出服务费新增:" + m.getWtot_feetitle(), 53,
					UserInfo.getUsername(), "", m.getCid(), "");
	
			return str;
		}
		
		
		//插入服务费变更
		
				public String[] Wtfeechangeadd(wtoutFeeModel m,String company)
				{
					
					
					String[] str = new String[5];
					Object[] obj = {"11", m};
					WfOperateService wfs = new WfOperateImpl(new wtoutFeechangeBll());
					str = wfs.AddTaskToNext(obj, "委托出服务费调整",
							company + "委托出服务费调整:" + m.getWtot_feetitle(), 119,
							UserInfo.getUsername(), "", m.getCid(), "");
			
					return str;
				}
				
		
		//状态变更
		public String[] wtfeeupdate(wtoutFeeModel m,Integer i)
		{
			
			String[] str = new String[5];
		
			WfOperateService wfs = new WfOperateImpl(new wtoutFeechangeBll());
			
			if (m.getWtot_state()==0)//客服经理审核
			{
				if (i>0)//低于最低服务费走全国项目部审核
				{
					m.setWtot_state(1);
					Object[] obj = {"2", m};
					str = wfs.PassToNext(obj, m.getWtot_tapr_id(),
							UserInfo.getUsername(), "",m.getCid(), "");
				}
				else //服务费高于最低服务费流程完
				{
					m.setWtot_state(3);
					Object[] obj = {"3", m};
					str = wfs.OverTask(obj, m.getWtot_tapr_id(),
							UserInfo.getUsername(), "任务完结");
				}
			
			}
			else if (m.getWtot_state()==1)//全国项目部审核
			{
				m.setWtot_state(3);
				Object[] obj = {"3", m};
				str = wfs.OverTask(obj, m.getWtot_tapr_id(),
						UserInfo.getUsername(), "任务完结");
			}
//			else if (i==0)
//			{
//				Object[] obj = {"4", m};
//				str = wfs.PassToNext(obj, m.getWtot_tapr_id(),
//						UserInfo.getUsername(), "", 0, "");
//			}
//			
			
	
			return str;
		
			
		}
		
		
		
		//状态变更
				public String[] wtfeechangeupdate(wtoutFeeModel m,Integer i)
				{
					
					String[] str = new String[5];
				
					WfOperateService wfs = new WfOperateImpl(new wtoutFeechangeBll());
					
					if (m.getWtot_state()==0)//客服经理审核
					{
						if (i>0)//低于最低服务费走全国项目部审核
						{
							m.setWtot_state(1);
							Object[] obj = {"12", m};
							str = wfs.PassToNext(obj, m.getWtot_tapr_id(),
									UserInfo.getUsername(), "",m.getCid(), "");
						}
						else //服务费高于最低服务费流程完
						{
							m.setWtot_state(3);
							Object[] obj = {"13", m};
							str = wfs.OverTask(obj, m.getWtot_tapr_id(),
									UserInfo.getUsername(), "任务完结");
							
							
						}
					
					}
					else if (m.getWtot_state()==1)
					{
						m.setWtot_state(3);
						Object[] obj = {"13", m};
						str = wfs.OverTask(obj, m.getWtot_tapr_id(),
								UserInfo.getUsername(), "任务完结");
						
						
					}
//					else if (i==0)
//					{
//						Object[] obj = {"4", m};
//						str = wfs.PassToNext(obj, m.getWtot_tapr_id(),
//								UserInfo.getUsername(), "", 0, "");
//					}
//					
					
			
					return str;
				
					
				}
		
		
		//状态变更
		public String[] wtfeeback(wtoutFeeModel m)
		{
				String[] str = new String[5];
				WfOperateService wfs = new WfOperateImpl(new wtoutFeechangeBll());
				
				if (m.getWtot_state()==1)//全国退
					
				{
					m.setWtot_state(0);
					Object[] obj = {"4", m};
					str = wfs.ReturnToPrev(obj, m.getWtot_tapr_id(),
							UserInfo.getUsername(), "");
					
					
					// 参数解释，业务表名：tablename；业务表id:id
				      MessageService msgservice=new MessageImpl("wtoutFeechange",m.getWtot_feechangeid());
				      LoginDal d =new LoginDal();
					      SysMessageModel sysm =new SysMessageModel();
				      String msgstr="亲，"+m.getCoba_company()+"的"+m.getWtot_feetitle()+"委托出服务费，已被退回";
				      sysm.setSyme_content(msgstr);//短信内容
				      sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
				      sysm.setSymr_name(m.getWtot_addname());//收件人姓名
				      sysm.setSymr_log_id(d.getUserIDByname((m.getWtot_addname())));//;
				      sysm.setEmail(1);
				      sysm.setEmailtitle("委托服务费退回");
				      msgservice.Add(sysm);
					
				}else if(m.getWtot_state()==0)//客服退
				{
					m.setWtot_state(4);
					Object[] obj = {"4", m};
//					str = wfs.ReturnToPrev(obj, m.getWtot_tapr_id(),
//							UserInfo.getUsername(), "");
//							
							
					
					str =wfs.StopTask(obj, m.getWtot_tapr_id(),"系统", "");
					
					// 参数解释，业务表名：tablename；业务表id:id
				      MessageService msgservice=new MessageImpl("wtoutFee",m.getWtot_feechangeid());
				      LoginDal d =new LoginDal();
					      SysMessageModel sysm =new SysMessageModel();
				      String msgstr="亲，"+m.getCoba_company()+"的"+m.getWtot_feetitle()+"委托出服务费，已被退回";
				      sysm.setSyme_content(msgstr);//短信内容
				      sysm.setSyme_log_id(d.getUserIDByname(UserInfo.getUsername()));//发件人id
				      sysm.setSymr_name(m.getWtot_addname());//收件人姓名
				      sysm.setSymr_log_id(d.getUserIDByname((m.getWtot_addname())));//;
				      sysm.setEmail(1);
				      sysm.setEmailtitle("委托服务费退回");
				      msgservice.Add(sysm);
				}
			
				
				return str;
		}
		
		
		
		public List<wtoutFeeModel> getmodellist(String strwhere)
		{
			return new wtoutFeeDal().getmodellist(strwhere);
		}
		
		public List<wtoutFeeModel> getmodelchangelist(String strwhere)
		{
			return new wtoutFeeDal().getmodelchangelist(strwhere);
		}


		@Override
		public String[] Operate(Object[] obj) {
			String[] str = new String[5];
			Integer id = 0;
			Integer row = 0;
			wtoutFeeDal dal =new wtoutFeeDal();
			//添加委托出服务费
			if (obj[0].equals("1")) {
				id=dal.Wtfeeadd((wtoutFeeModel)obj[1]);
				
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "wtoutFee";
					str[4] = "客服提交申请";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			}
			
			
			
			//审核服务费,低于最低服务费
			else if (obj[0].equals("2"))
			{
				id=dal.Wtfeeupdate((wtoutFeeModel)obj[1]);
				
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = ((wtoutFeeModel)obj[1]).getWtot_feeid()+"";
					str[3] = "wtoutFee";
					str[4] = "客服经理审核";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
				
			}
			//审核服务费,高于最低服务费
			else if (obj[0].equals("3"))
			{
				id=dal.Wtfeeupdate((wtoutFeeModel)obj[1]);
				
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = ((wtoutFeeModel)obj[1]).getWtot_feeid() + "";
					str[3] = "wtoutFee";
					str[4] = "审核完成，服务费生效";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			}
			
			//委托出服务费调整
			else if (obj[0].equals("11"))
			{
				id=dal.Wtfeeachangedd((wtoutFeeModel)obj[1]);
				
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "wtoutFee";
					str[4] = "调整服务费";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			}
			//委托出服务费调整客服审核
			else if (obj[0].equals("12"))
			{
				
				id=dal.Wtfeechangeupdate((wtoutFeeModel)obj[1]);
				
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = ((wtoutFeeModel)obj[1]).getWtot_feechangeid() + "";
					str[3] = "wtoutFee";
					str[4] = "调整服务费";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			}
			//委托出服务费调整审核 全国
			else if (obj[0].equals("13"))
			{
				id=dal.Wtfeechangeupdate((wtoutFeeModel)obj[1]);
				
				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = ((wtoutFeeModel)obj[1]).getWtot_feechangeid() + "";
					str[3] = "wtoutFee";
					str[4] = "调整服务费";
				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			}
//			
			
			return str;
		}

		@Override
		public String[] ReturnOperate(Object[] obj) {
			// TODO Auto-generated method stub
			String[] str = new String[5];
			Integer id = 0;
			
			wtoutFeeDal dal =new wtoutFeeDal();
			if (obj[0].equals("4"))
			{
				id=dal.Wtfeechangeupdate((wtoutFeeModel)obj[1]);
				
				if (id > 0) {
					str[0] = "1";
					str[1] = "退回成功!";
					str[2] = ((wtoutFeeModel)obj[1]).getWtot_feechangeid() + "";
					str[3] = "wtoutFeechange";
					str[4] = "被退回";
				} else {
					str[0] = "0";
					str[1] = "退回失败!";
				}
			}
			
			
			return str;
		}

		@Override
		public String[] SkipOperate(Object[] obj) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String[] StopOperate(Object[] obj) {
			// TODO Auto-generated method stub
						String[] str = new String[5];
						Integer id = 0;
						
						wtoutFeeDal dal =new wtoutFeeDal();
						if (obj[0].equals("4"))
						{
							id=dal.Wtfeechangeupdate((wtoutFeeModel)obj[1]);
							
							if (id > 0) {
								str[0] = "1";
								str[1] = "退回成功!";
								str[2] = ((wtoutFeeModel)obj[1]).getWtot_feechangeid() + "";
								str[3] = "wtoutFeechange";
								str[4] = "被退回";
							} else {
								str[0] = "0";
								str[1] = "退回失败!";
							}
						}
						
						
						return str;
		}

		@Override
		public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
			return new wtoutFeeDal().UpdatechangeTaprid(dataId, tapr_id);
		}

		@Override
		public Boolean ErrOperate(int dataId) {
			// TODO Auto-generated method stub
			return null;
		}
		
	
}
