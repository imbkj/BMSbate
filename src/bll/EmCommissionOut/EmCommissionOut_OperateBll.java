package bll.EmCommissionOut;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bll.EmCommercialInsurance.CI_InsurantChange_OperateImpl;
import bll.SysMessage.SysMessage_AddBll;

import dal.LoginDal;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmCommissionOut.EmCommissionOut_ListDal;
import dal.EmCommissionOut.EmCommissionOut_OperateDal;

import impl.MessageImpl;
import impl.SysMessageImpl;
import impl.WorkflowCore.WfOperateImpl;
import service.MessageService;
import service.SysMessageService;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Model.CI_Insurant_ListModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateFeeDetailModel;
import Model.EmCommissionOutPayUpdateModel;
import Model.SysMessageModel;
import Util.DateStringChange;
import Util.Log4jInit;
import Util.UserInfo;

public class EmCommissionOut_OperateBll implements WfBusinessService {
	String username = UserInfo.getUsername();

	/**
	 * 委托外地新增
	 * 
	 * @param m
	 * @param list
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public Integer add(List<EmCommissionOutChangeModel> list, String name,
			String city) throws SQLException {

		Integer wfbu_id = 0;
		Integer count = 0;
		Integer ecoc_id = 0;

		for (EmCommissionOutChangeModel m : list) {
			if (!m.getEcoc_addtype().equals("补缴")) {
				Object[] obj = { "1", m };
				if (m.getEcoc_addtype().equals("新增")) {
					wfbu_id = 55;
				} else if (m.getEcoc_addtype().equals("调整")) {
					wfbu_id = 66;
					m.setEcoc_id(ecoc_id);
				}
				
				CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
				List<CI_Insurant_ListModel> listname = dal.getallcidname(m.getGid()); 
				
				WfOperateService wfs = new WfOperateImpl(
						new EmCommissionOut_OperateBll());
				String[] str = wfs.AddTaskToNext(obj,
						"委托外地" + m.getEcoc_addtype(), name+"-"+listname.get(0).getCoba_shortname() + "-委托外地-" + city
								+ "-" + m.getEcoc_addtype() + "-("
								+ m.getEcoc_title_date().substring(0, 7) + ")",
						wfbu_id, UserInfo.getUsername(), "", m.getCid(), "");
				if (str[0].equals("1")) {
					count++;
					if (m.getEcoc_addtype().equals("新增"))
					{
						ecoc_id = Integer.parseInt(str[3]);
					}
				}
				else 
				{
					System.out.println(str[1]);
				}
			} 
			else {
				if (!ecoc_id.equals(0)) {
					String wt_name="";
					m.setEcoc_id(ecoc_id);
					
					System.out.println("xxxx");
					System.out.println(m.getFeeList().size());
					
					for (EmCommissionOutFeeDetailChangeModel m1 : m
							.getFeeList()) {
						//new EmCommissionOut_OperateDal().addBJFeeDetail(m1);
						
						wt_name = wt_name
								+ m1.getEofc_sicl_id().toString() + ","
								+ m1.getEofc_name().toString() + ","
								+ m1.getEofc_co_base().toString() + ","
								+ m1.getEofc_em_base().toString() + ","
								+ m1.getEofc_cp() + ","
								+ m1.getEofc_op() + ","
								+ m1.getEofc_co_sum() + ","
								+ m1.getEofc_em_sum() + ","
								+ m1.getEofc_month_sum().toString() + ","
								+ m1.getEofc_start_date() + "|";							
					}
					
					System.out.println(wt_name);

					Integer id = new EmCommissionOut_OperateDal()
							.addEmcommissionOutBJ(m,wt_name);
					wt_name="";
				}
			}
		}
		return count;
	}

	/**
	 * 委托外地状态变更
	 * 
	 * @param m
	 * @param list
	 * @return
	 */
	public String[] updatestate(EmCommissionOutChangeModel m,
			List<EmCommissionOutFeeDetailChangeModel> list) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		String[] str = new String[5];
		if (!m.getEcoc_state().equals(3)) {
			Object[] obj = { "2", m, list };
			WfOperateService wfs = new WfOperateImpl(
					new EmCommissionOut_OperateBll());
			str = wfs.PassToNext(obj, m.getEcoc_tapr_id(), username, "",
					m.getCid(), "");
		} else {
			dal.UpdateState(m);
			dal.mod(m);

			for (EmCommissionOutFeeDetailChangeModel m1 : list) {
				dal.FeeDetailChangeMod(m1);
			}

			str[0] = "1";
			str[1] = "提交完成!";
		}

		// 发送系统短信
		try {
			m.setIf_sendmessage(m.getIf_sendmessage() == null ? false : m
					.getIf_sendmessage());
			if (m.getIf_sendmessage() && m.getEcoc_remark() != null
					&& !m.getEcoc_remark().isEmpty()) {
				String[] sendstr = sendSysMessage(m);

				if (sendstr[0].equals("0")) {
					str[0] = "0";
					str[1] = sendstr[1];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;
	}

	/**
	 * 委托外地调整
	 * 
	 * @param m
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public String[] change(EmCommissionOutChangeModel m, String name,String wt_name) throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> listname = dal.getallcidname(m.getGid()); 
		Object[] obj = { "3",m,wt_name };
		WfOperateService wfs = new WfOperateImpl(
				new EmCommissionOut_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "委托外地" + m.getEcoc_addtype(),
				name+"-"+listname.get(0).getCoba_shortname() + "-委托外地" + m.getEcoc_addtype() + "("
						+ m.getEcoc_title_date().substring(0, 7) + ")", 66,
				UserInfo.getUsername(), "", m.getCid(), "");

		return str;
	}

	/**
	 * 委托外地离职
	 * 
	 * @param m
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public String[] termination(EmCommissionOutChangeModel m, String name) throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> listname = dal.getallcidname(m.getGid()); 
		Object[] obj = { "4", m };
		WfOperateService wfs = new WfOperateImpl(
				new EmCommissionOut_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "委托外地" + m.getEcoc_addtype(),
				name+"-"+listname.get(0).getCoba_shortname() + "-委托外地" + m.getEcoc_addtype() + "("
						+ m.getEcoc_title_date().substring(0, 7) + ")", 74,
				UserInfo.getUsername(), "", m.getCid(), "");

		return str;
	}

	/**
	 * 一次性费用
	 * 
	 * @param m
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public String[] onetimefee(EmCommissionOutChangeModel m, String name) throws SQLException {
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		List<CI_Insurant_ListModel> listname = dal.getallcidname(m.getGid()); 
		Object[] obj = { "5", m };
		WfOperateService wfs = new WfOperateImpl(
				new EmCommissionOut_OperateBll());
		String[] str = wfs.AddTaskToNext(obj, "委托外地" + m.getEcoc_addtype(),
				name+"-"+listname.get(0).getCoba_shortname() + "-委托外地" + m.getEcoc_addtype() + "("
						+ m.getEcoc_title_date().substring(0, 7) + ")", 77,
				UserInfo.getUsername(), "", m.getCid(), "");

		return str;
	}

	/**
	 * 重新提交
	 * 
	 * @param m
	 * @return
	 */
	public String[] resub(EmCommissionOutChangeModel m,
			List<EmCommissionOutFeeDetailChangeModel> list) {
		Object[] obj = { "6", m, list };
		WfOperateService wfs = new WfOperateImpl(
				new EmCommissionOut_OperateBll());
		String[] str = wfs.PassToNext(obj, m.getEcoc_tapr_id(),
				UserInfo.getUsername(), "", m.getCid(), "");
		return str;
	}

	/**
	 * 取消委托单
	 * 
	 * @param m
	 * @return
	 */
	public String[] cancel(EmCommissionOutChangeModel m) {
		Object[] obj = { "7", m };
		WfOperateService wfs = new WfOperateImpl(
				new EmCommissionOut_OperateBll());
		String[] str = wfs.OverTask(obj, m.getEcoc_tapr_id(),
				UserInfo.getUsername(),
				"手动取消该委托单，原因：" + m.getEcoc_cancel_cause());
		if (str[0].equals("1")) {
			obj[0] = "8";
			m.setEcoc_state(1);
			m.setEcoc_laststate(0);
			m.setEcoc_type("ecoccancel");
			String faddtype = m.getEcoc_addtype();
			m.setEcoc_addtype("取消");
			str = wfs.AddTaskToNext(obj, "取消委托单", "取消\"" + m.getEmba_name()
					+ "\"的" + faddtype + "单("
					+ m.getEcoc_title_date().substring(0, 7) + ")", 82,
					UserInfo.getUsername(), "", m.getCid(), "");
		}
		return str;
	}

	/**
	 * 退回
	 * 
	 * @param m
	 * @return
	 */
	public String[] back(EmCommissionOutChangeModel m) {
		Object[] obj = { m };
		WfOperateService wfs = new WfOperateImpl(
				new EmCommissionOut_OperateBll());

		String[] str = wfs.ReturnToN(obj, m.getEcoc_tapr_id(),1,
				UserInfo.getUsername());

		if (m.getEcoc_addtype().equals("新增") && str[0].equals("1")) {
			List<EmCommissionOutChangeModel> list = new EmCommissionOutListBll()
					.getEmCommOutChangeList(" and ecoc_ecou_id="
							+ m.getEcoc_ecou_id()
							+ " and ecoc_state in(1,2) and ecoc_addtype in('调整','离职')");

			if (list.size() > 0) {
				for (EmCommissionOutChangeModel m1 : list) {
					m1.setEcoc_state(4);
					m1.setEcoc_remark("系统执行退回");
					obj[0] = m1;
					str = wfs.ReturnToN(obj, m1.getEcoc_tapr_id(),1, username);
					//"该" + m1.getEcoc_addtype()					+ "单的新增单被退回，系统自动执行退回此单的操作"

				}
			}
		}
		return str;
	}

	/**
	 * 发送系统短信
	 * 
	 * @param gid
	 * @param content
	 * @return String[] [0]1:成功 0:失败 [1]失败原因
	 */
	public String[] sendSysMessage(EmCommissionOutChangeModel m) {
		String[] str = new String[2];
		str[0] = "1";
		SysMessageService sms = new SysMessageImpl();
		// 接收人列表
		List<SysMessageModel> reciList = new ArrayList<>();
		String content = "";

		send: {
			try {
				// 查询客服，添加为接收人
				SysMessageModel tm = new SysMessageModel();

				tm.setSymr_log_id(new SysMessage_AddBll()
						.getInfoByGid(m.getGid()).get(0).getLog_id());
				tm.setSymr_name(new SysMessage_AddBll()
						.getInfoByGid(m.getGid()).get(0).getCoba_client());

				reciList.add(tm);
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "系统短信发送失败：查询客服出错";
				e.printStackTrace();
				break send;
			}

			try {
				content = m.getCoba_shortname() + "(" + m.getCid() + ")的"
						+ m.getEmba_name() + "(" + m.getGid() + ")的委托外地"
						+ m.getEcoc_addtype() + "需跟进：" + m.getEcoc_remark();
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "系统短信发送失败：短信内容获取出错";
				e.printStackTrace();
				break send;
			}

			try {
				if (!content.isEmpty()) {
					sms.add("委托外地", content, 1, 0, reciList);
				} else {
					str[0] = "0";
					str[1] = "系统短信发送失败：短信内容获取失败";
					break send;
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "系统短信发送失败：发送出错";
				e.printStackTrace();
				break send;
			}
		}
		return str;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String[] Operate(Object[] obj) {
		String[] str = new String[5];
		Integer id = 0;
		Integer row = 0;
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		if (obj[0].equals("1")) {
			// 委托外地新增、调整
						EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];
						List<EmCommissionOutFeeDetailChangeModel> list = m.getFeeList();

						try {

							int ecou_count = 0;
							String wt_name = "";

							// 项目费用详情新增
							for (int i = 0; i < list.size(); i++) {

								wt_name = wt_name
										+ list.get(i).getEofc_sicl_id().toString() + ","
										+ list.get(i).getEofc_name().toString() + ","
										+ list.get(i).getEofc_co_base().toString() + ","
										+ list.get(i).getEofc_em_base().toString() + ","
										+ list.get(i).getEofc_cp() + ","
										+ list.get(i).getEofc_op() + ","
										+ list.get(i).getEofc_co_sum() + ","
										+ list.get(i).getEofc_em_sum() + ","
										+ list.get(i).getEofc_month_sum().toString() + ","
										+ list.get(i).getEofc_start_date() + "|";
								ecou_count = ecou_count + 1;
								
								System.out.println("aaaaa");
								System.out.println(wt_name);
								// dal.addFeeDetail(list.get(i));
							}

							if (ecou_count > 8) {
								id = dal.addEmcommissionOut(m,wt_name);
								Log4jInit.toLog("委托外地新增" + m.getGid());
								Log4jInit.toLog("委托外地新增" + id);
								wt_name="";
								if (id > 0) {
									str[0] = "1";
									str[1] = "提交成功!";
									str[2] = id + "";
									str[3] = "EmCommissionOutChange";
									str[4] = "客服新增委托单";

								} else {
									str[0] = "0";
									str[1] = "提交失败，请过5秒钟后在提交!";
								}
							} else {
								str[0] = "0";
								str[1] = "提交失败，请过5秒钟后在提交!";
							}

							
						} catch (Exception e) {
							str[0] = "0";
							str[1] = "提交出错!";
							e.printStackTrace();
						}
					} else if (obj[0].equals("2")) {
			// 状态变更
			EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];

			try {
				row = dal.UpdateState(m);

				if (row > 0) {
					if (m.getEcoc_addtype().equals("新增")
							|| m.getEcoc_addtype().equals("调整")
							|| m.getEcoc_addtype().equals("离职")) {
						if (m.getEcoc_state() == 6) {
							dal.mod(m);
							List<EmCommissionOutFeeDetailChangeModel> list = (List<EmCommissionOutFeeDetailChangeModel>) obj[2];

							for (EmCommissionOutFeeDetailChangeModel m1 : list) {
								dal.FeeDetailChangeMod(m1);
							}
						}
					} else if (m.getEcoc_addtype().equals("一次性费用")) {
						if (m.getEcoc_state() == 2) {
							dal.mod(m);
							List<EmCommissionOutFeeDetailChangeModel> list = (List<EmCommissionOutFeeDetailChangeModel>) obj[2];

							for (EmCommissionOutFeeDetailChangeModel m1 : list) {
								dal.FeeDetailChangeMod(m1);
							}
						}
					}
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getEcoc_id() + "";
					str[3] = "EmCommissionOutChange";
					str[4] = new EmCommissionOutListBll()
							.getStateList(
									" and typename='后道状态' and type='"
											+ m.getType() + "' and stateid="
											+ m.getEcoc_state()).get(0)
							.getOperate();
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("3")) {
			// 委托外地调整
			
			EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];
			List<EmCommissionOutFeeDetailChangeModel> list = m.getFeeList();

			try {
				id = dal.addEmcommissionOut(m,obj[2].toString());
				
//				for (EmCommissionOutFeeDetailChangeModel m1 : list) {
//					m1.setEofc_ecoc_id(id);
//					dal.addFeeDetail(m1);
//				}
				
				System.out.println("xcxcxc");
				System.out.println(id);
//				if (id > 0) {
//					int y=0;
//					List<EmCommissionOutFeeDetailChangeModel> list1;
//					list1 = new EmCommissionOut_ListDal().getEcofdidList(id);
//
//					// 项目费用详情新增
//					for (int i = 0; i < list.size(); i++) {
//						list.get(i).setEofc_ecoc_id(id);
//						list.get(i).setEofc_eofd_id(
//								list1.get(i).getEofc_eofd_id());
//						System.out.println("ccccccccccccccccccc");
//						System.out.println();
//						int x=0;
//						x=dal.addFeeDetail(list.get(i));
//						if (x>0)
//						{
//							y=y+1;
//						}
//					}
					
					if (id>0)
					{
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "EmCommissionOutChange";
					str[4] = "客服提交调整委托单";
					}
					else
					{
						str[0] = "0";
						str[1] = "提交失败!";
					}
				
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		}  if (obj[0].equals("4")) {
			// 委托外地离职
						EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];
						List<EmCommissionOutFeeDetailChangeModel> list = m.getFeeList();

						try {

							int ecou_count = 0;
							String wt_name = "";

							// 项目费用详情新增
							for (int i = 0; i < list.size(); i++) {

								wt_name = wt_name
										+ list.get(i).getEofc_sicl_id().toString() + ","
										+ list.get(i).getEofc_name().toString() + ","
										+ list.get(i).getEofc_co_base().toString() + ","
										+ list.get(i).getEofc_em_base().toString() + ","
										+ list.get(i).getEofc_cp().toString() + ","
										+ list.get(i).getEofc_op().toString() + ","
										+ list.get(i).getEofc_co_sum().toString() + ","
										+ list.get(i).getEofc_em_sum().toString() + ","
										+ list.get(i).getEofc_month_sum().toString() + ","
										+ list.get(i).getEofc_stop_date() + "|";
								ecou_count = ecou_count + 1;

								// dal.addFeeDetail(list.get(i));
							}

							if (ecou_count > 8) {
								id = dal.EmcommissionOutTer(m, wt_name);

								if (id > 0) {
								str[0] = "1";
								str[1] = "提交成功!";
								str[2] = id + "";
								str[3] = "EmCommissionOutChange";
								str[4] = "客服提交离职委托单";
								}else {
									str[0] = "0";
									str[1] = "提交出错!";
								}

							} else {
								str[0] = "0";
								str[1] = "提交出错!";
							}

						} catch (Exception e) {
							str[0] = "0";
							str[1] = "提交出错!";
							e.printStackTrace();
						}
		} else if (obj[0].equals("5")) {
			// 一次性费用
			EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];
			List<EmCommissionOutFeeDetailChangeModel> list = m.getFeeList();
			
			try {
				
				id = dal.addEmcommissionOut(m,"");

				if (id > 0) {

					// 项目费用详情新增
					for (int i = 0; i < list.size(); i++) {
						
						list.get(i).setEofc_ecoc_id(id);
						//if(Integer.parseInt(list.get(i).getEofc_co_sum().toString())>0){
						list.get(i).setEofc_start_date(DateStringChange.DatetoSting(
								m.getTitle_date(), "yyyy-MM-dd"));
						
						//}else{
						//	list.get(i).setEofc_start_date(null);
						//}
						dal.addFeeDetail(list.get(i));
					}

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "EmCommissionOutChange";
					str[4] = "客服提交一次性费用单";

				} else {
					str[0] = "0";
					str[1] = "提交失败!";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("6")) {
			// 重新提交
			EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];

			try {
				row = dal.UpdateState(m);

				if (row > 0) {
					dal.EmOutChangeReSubmit(m);
					List<EmCommissionOutFeeDetailChangeModel> list = (List<EmCommissionOutFeeDetailChangeModel>) obj[2];

					for (EmCommissionOutFeeDetailChangeModel m1 : list) {
						dal.FeeDetailChangeReSubmit(m1);
					}

					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getEcoc_id() + "";
					str[3] = "EmCommissionOutChange";
					str[4] = "重新提交";
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("7")) {
			// 取消委托单
			EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];

			try {
				row = dal.Cancel(m);

				if (row > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = m.getEcoc_id() + "";
					str[3] = "EmCommissionOutChange";
					str[4] = new EmCommissionOutListBll()
							.getStateList(
									" and typename='后道状态' and type='"
											+ m.getEcoc_type()
											+ "' and stateid="
											+ m.getEcoc_state()).get(0)
							.getOperate();
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		} else if (obj[0].equals("8")) {
			// 新增取消变更数据
			EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[1];

			try {
				id = dal.addEmcommissionOut(m,"");

				if (id > 0) {
					str[0] = "1";
					str[1] = "提交成功!";
					str[2] = id + "";
					str[3] = "EmCommissionOutChange";
					str[4] = new EmCommissionOutListBll()
							.getStateList(
									" and typename='后道状态' and type='"
											+ m.getEcoc_type()
											+ "' and stateid="
											+ m.getEcoc_state()).get(0)
							.getOperate();
				}
			} catch (Exception e) {
				str[0] = "0";
				str[1] = "提交出错!";
				e.printStackTrace();
			}
		}
		return str;
	}

	@Override
	public String[] ReturnOperate(Object[] obj) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		String[] str = new String[5];
		// 退回
		EmCommissionOutChangeModel m = (EmCommissionOutChangeModel) obj[0];

		try {
			Integer row = dal.Back(m);

			if (row > 0) {

				str[0] = "1";
				str[1] = "退回成功!";
				str[2] = m.getEcoc_id() + "";
				str[3] = "EmCommissionOutChange";
				str[4] = new EmCommissionOutListBll()
						.getStateList(
								" and typename='后道状态' and type='"
										+ m.getEcoc_type() + "' and stateid="
										+ m.getEcoc_state()).get(0)
						.getOperate();
			}
		} catch (Exception e) {
			str[0] = "0";
			str[1] = "退回出错!";
			e.printStackTrace();
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
		return null;
	}

	@Override
	public Boolean UpdateTaprid(int dataId, int tapr_id, int state) {
		return new EmCommissionOut_OperateDal().UpdateTaprid(dataId, tapr_id);
	}

	@Override
	public Boolean ErrOperate(int dataId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 模板新增
	 * 
	 * @param m
	 * @return
	 */
	public String[] EmOutPayUpdateTCAdd(EmCommissionOutPayUpdateCRTModel m) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		String[] strs = { "0", "", "0" };
		Integer id = 0;

		try {
			id = dal.EmOutPayUpdateTAdd(m);
			if (id > 0) {
				List<EmCommissionOutPayUpdateCRTModel> cList = m.getcList();
				for (int i = 0; i < cList.size(); i++) {
					EmCommissionOutPayUpdateCRTModel ecm = cList.get(i);
					ecm.setEcuc_ecut_id(id);
					dal.EmOutPayUpdateCAdd(cList.get(i));
				}

				strs[0] = "1";
				strs[1] = "提交成功!";
				strs[2] = id.toString();
			} else {
				strs[0] = "0";
				strs[1] = "模板新增失败!";
			}
		} catch (Exception e) {
			strs[0] = "0";
			strs[1] = "提交出错!";
		}
		return strs;
	}

	/**
	 * 模板修改
	 * 
	 * @param m
	 * @return
	 */
	public String[] EmOutPayUpdateTCMod(EmCommissionOutPayUpdateCRTModel m) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();
		String[] strs = { "0", "", "0" };
		Integer row = 0;

		try {
			row = dal.EmOutPayUpdateTMod(m);
			if (row > 0) {
				List<EmCommissionOutPayUpdateCRTModel> cList = m.getcList();

				row = dal.EmOutPayUpdateCDel(m.getEcut_id());

				if (row > 0) {
					for (int i = 0; i < cList.size(); i++) {
						EmCommissionOutPayUpdateCRTModel ecm = cList.get(i);
						ecm.setEcuc_ecut_id(m.getEcut_id());
						dal.EmOutPayUpdateCAdd(cList.get(i));
					}
				} else {
					strs[0] = "0";
					strs[1] = "模板字段映射失败!";
				}

				strs[0] = "1";
				strs[1] = "提交成功!";
				strs[2] = m.getEcut_id().toString();
			} else {
				strs[0] = "0";
				strs[1] = "模板修改失败!";
			}
		} catch (Exception e) {
			strs[0] = "0";
			strs[1] = "提交出错!";
		}
		return strs;
	}

	/**
	 * 判断模板名称是否已存在
	 * 
	 * @param m
	 * @return true 存在;false 不存在
	 */
	public boolean isExist_Ecut_name(EmCommissionOutPayUpdateCRTModel m) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();

		return dal.isExist_Ecut_name(m);
	}

	/**
	 * 导入委托外地账单
	 * 
	 * @param m
	 * @return
	 */
	public Integer EmOutPayUpdateAdd(EmCommissionOutPayUpdateModel m) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();

		return dal.EmOutPayUpdateAdd(m);
	}

	/**
	 * 导入委托外地账单明细
	 * 
	 * @param m
	 * @return
	 */
	public Integer EmOutPayUpdateFeeDetailAdd(
			EmCommissionOutPayUpdateFeeDetailModel m) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();

		return dal.EmOutPayUpdateFeeDetailAdd(m);
	}

	/**
	 * 同步历史表
	 * 
	 * @param ownmonth
	 * @return
	 */
	public Integer EmOutHisAdd(String ownmonth) {
		EmCommissionOut_OperateDal dal = new EmCommissionOut_OperateDal();

		return dal.EmOutHisAdd(ownmonth);
	}
	
}
