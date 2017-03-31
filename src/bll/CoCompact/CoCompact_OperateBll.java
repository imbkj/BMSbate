package bll.CoCompact;

import impl.PubCityImpl;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.ListModelList;

import service.PubCityService;

import Model.CoCompactModel;
import Model.CoCompactTemAddModel;
import Model.CoFinanceCollectModel;
import Model.CoHousingFundModel;
import Model.CoOfferListModel;
import Model.CoOfferModel;
import Model.CoShebaoModel;
import Model.EmHouseCpp;
import Util.UserInfo;
import dal.CoCompact.CoCompact_OperateDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.CoQuotation.CoofferlistDal;
import dal.CoSocialInsurance.CoSocialInsurance_ListDal;
import dal.Taskflow.TaskBatchDal;
import dal.Taskflow.Task_ListDal;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import impl.WorkflowCore.WfOperateImpl;
import bll.CoCompact.CoCompact_OperateImpl;
import bll.CoQuotation.CoofferlistImpl;

public class CoCompact_OperateBll {

	private CoCompact_OperateDal cocoDal = new CoCompact_OperateDal();

	// 获取合同信息
	public List<CoCompactModel> getInfoList(Integer id) {
		List<CoCompactModel> list = new ListModelList<>();
		list = cocoDal.getlist(id);
		return list;
	}

	// 获取中智户单位公积金比例
	public List<EmHouseCpp> cppList() {
		List<EmHouseCpp> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		list = dal.getcpplist();
		EmHouseCpp m = new EmHouseCpp();
		m.setCpName("浮动比例");
		list.add(m);
		return list;
	}

	// 获取中智户单位公积金比例
	public List<EmHouseCpp> cppList(Integer cid) {
		List<EmHouseCpp> list = new ListModelList<>();
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		list = dal.getcpplist(cid);
		EmHouseCpp m = new EmHouseCpp();
		m.setCpName("浮动比例");
		list.add(m);
		return list;
	}

	// 获取合同履行地(所有城市)
	public List<String> getCityName() throws Exception {
		PubCityService pcs = new PubCityImpl();
		List<String> city = pcs.getCityName();
		return city;
	}

	/**
	 * @Title: getcoshebao
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param cid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean getcoshebao(Integer cid) {
		boolean b = false;
		CoSocialInsurance_ListDal dal = new CoSocialInsurance_ListDal();
		CoShebaoModel cm = new CoShebaoModel();
		cm.setCid(cid);
		cm.setCosb_state(1);
		cm.setInure(true);
		List<CoShebaoModel> list = dal.getlist(cm);
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	/**
	 * @Title: getcohouse
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param cid
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean getcohouse(Integer cid) {
		boolean b = false;
		CoHousingFund_ListDal dal = new CoHousingFund_ListDal();
		CoHousingFundModel cm = new CoHousingFundModel();
		cm.setCid(cid);
		cm.setInure(true);
		List<CoHousingFundModel> list = dal.getlist(cm);
		if (list.size() > 0) {
			b = true;
		}
		return b;
	}

	// 判断任务是否在执行
	public boolean gettaskState(String name) {
		Task_ListDal dal = new Task_ListDal();
		return dal.taskState(name);

	}

	/**
	 * @Title: startMission
	 * @Description: TODO(启动合同项目流程)
	 * @param cm
	 *            coco_shebao,coco_house,coco_id,cid,cid2
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean startMission(CoCompactModel cm) {
		boolean b = false;
		Integer coli_id = 0;
		// 社保独立户开户流程
		if (cm.getCoco_shebao() != null && cm.getCoco_shebao().equals("独立开户")) {
			if (cm.getCid2() != null && !getcoshebao(cm.getCid2())) {
				// coli_id = getcoofferListId(cm.getCoco_id(), "社会保险服务");
				// if (coli_id != null && coli_id > 0) {
				if (!gettaskState(cm.getCompany() + " - 社保独立开户")) {

					WfBusinessService wfbs = new CoofferlistImpl();
					WfOperateService wfs = new WfOperateImpl(wfbs);
					Object[] obj = { "启动社保开户", cm };
					Map<String, String> map = new HashMap();
					map.put("cid", cm.getCid());
					String[] str = wfs.AddTaskToNext(obj, "社保单位账户新增/接管",
							cm.getCompany() + " - 社保独立开户", 104,
							UserInfo.getUsername(), "",
							Integer.valueOf(cm.getCid()), "", map);
					b = true;
				}
				// }
			}
		}
		// 公积金独立户开户流程
		if (cm.getCoco_house() != null && cm.getCoco_house().equals("独立开户")) {
			if (cm.getCid2() != null && !getcohouse(cm.getCid2())) {
				// coli_id = getcoofferListId(cm.getCoco_id(), "住房公积金服务");
				// if (coli_id != null && coli_id > 0) {
				if (!gettaskState(cm.getCompany() + " - 公积金独立开户")) {
					WfBusinessService wfbs = new CoofferlistImpl();
					WfOperateService wfs = new WfOperateImpl(wfbs);
					Object[] obj = { "启动公积金开户", cm };
					Map<String, String> map = new HashMap();
					map.put("cid", cm.getCid());
					String[] str = wfs.AddTaskToNext(obj, "住房公积金单位账户新增/接管",
							cm.getCompany() + " - 公积金独立开户", 105,
							UserInfo.getUsername(), "",
							Integer.valueOf(cm.getCid()), "", map);
					b = true;
				}
				// }
			}
		}
		return b;
	}

	/**
	 * @Title: completeMission
	 * @Description: TODO(完成合同项目流程)
	 * @param id
	 * @param taprId
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean completeMission(Integer id, Integer taprId) {
		boolean b = false;
		WfBusinessService wfbs = new CoofferlistImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Object[] obj = { "任务结束", id };
		String[] str = wfs.PassToNext(obj, taprId, UserInfo.getUsername(), "",
				0, "");
		if (str[0].equals("1")) {
			b = true;
		}
		return b;
	}

	/**
	 * @Title: getcoofferListId
	 * @Description: TODO(返回合同指定项目报价单项目ID)
	 * @param id
	 * @return
	 * @return Integer 返回类型
	 * @throws
	 */
	public Integer getcoofferListId(Integer coid, String name) {
		Integer coliId = 0;
		List<CoOfferListModel> list = new ListModelList<>();
		CoofferlistDal dal = new CoofferlistDal();
		list = dal.getcoofferlistId(coid, name);
		if (list.size() > 0) {
			coliId = list.get(0).getColi_id();
		}
		return coliId;

	}

	// 公司合同录入
	public String[] addCompactTemp(int cola_id, String coof_id,
			String coco_compacttype, String coco_servicearea,
			String coco_inuredate, String coco_remark, String coco_addname,
			String coco_shebao, String coco_house, String coco_cpp,
			String coco_gzmonth, String coco_gsmonth, int coco_sbfee,
			int coco_housefee, int coco_sbperfee, int coco_gsfee,
			int coco_gzpay, String cola_shortname, int coco_houseperfee,
			int coco_gzperfee, int coco_ifgzpay, String coco_gs,
			String coco_compactclass) {

		// 新任务单代码
		String[] message = new String[5];
		try {
			CoCompactModel m = new CoCompactModel();
			m.setCoco_compacttype(coco_compacttype);
			m.setCoco_servicearea(coco_servicearea);
			m.setCoco_inuredate(coco_inuredate);
			m.setCoco_remark(coco_remark);
			m.setCoco_addname(coco_addname);
			m.setCoco_shebao(coco_shebao);
			m.setCoco_house(coco_house);
			m.setCoco_cpp(coco_cpp);
			m.setCoco_opp(coco_cpp);
			m.setCoco_gzmonth(coco_gzmonth);
			m.setCoco_gsmonth(coco_gsmonth);
			m.setCoco_sbfee(coco_sbfee);
			m.setCoco_housefee(coco_housefee);
			m.setCoco_sbperfee(coco_sbperfee);
			m.setCoco_gsfee(coco_gsfee);
			m.setCoco_gzpay(coco_gzpay);
			m.setCoco_houseperfee(coco_houseperfee);
			m.setCoco_gzperfee(coco_gzperfee);
			m.setCoco_ifgzpay(coco_ifgzpay);
			m.setCoco_gs(coco_gs);
			m.setCoco_compactclass(coco_compactclass);
			Object[] obj = { "0", cola_id, coof_id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new CoCompact_OperateImpl());
			message = wf.AddTaskToNext(obj, "公司合同", cola_shortname
					+ coco_compacttype + "类型合同", 2, coco_addname, "", cola_id,
					"");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同生成模板，操作出错。";
		}
		return message;
	}

	// 公司合同修改
	public String[] updateCompactTemp(int coco_id, String coco_servicearea,
			String coco_inuredate, String coco_remark) {
		String[] message = new String[2];
		try {
			CoCompactModel m = new CoCompactModel();
			int result = 0;

			m.setCoco_id(coco_id);
			m.setCoco_servicearea(coco_servicearea);
			m.setCoco_inuredate(coco_inuredate);
			m.setCoco_remark(coco_remark);

			result = cocoDal.updateCompactTemp(m);
			if (result == 0) {
				message[0] = "1";
				message[1] = "公司合同修改成功!";
			} else {
				message[0] = "0";
				message[1] = "公司合同修改失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同修改，操作出错。";

		}
		return message;
	}

	// 公司合同签回
	public String[] signCoCompact(int coco_tapr_id, int coco_id,
			String coco_returndate, String coco_signdate,
			String coco_signplace, String coco_indate, String coco_delay,
			String coco_money, String coco_invoice, String username,
			int cola_id, Integer paydate, Double coco_fw_p, Double coco_fl_p,
			Double coco_dk_p) throws Exception {
		/*
		 * String[] message = new String[2];
		 * 
		 * try { CoCompactModel m = new CoCompactModel(); int result = 0;
		 * 
		 * m.setCoco_returndate(coco_returndate);
		 * m.setCoco_signdate(coco_signdate);
		 * m.setCoco_inuredate(coco_inuredate);
		 * m.setCoco_signplace(coco_signplace); m.setCoco_indate(coco_indate);
		 * m.setCoco_delay(coco_delay); m.setCoco_money(coco_money);
		 * m.setCoco_invoice(coco_invoice);
		 * 
		 * result = cocoDal.signCoCompact(coco_id, m);
		 * 
		 * if (result == 0) {
		 * 
		 * WfFlowControlService wfcs = new WfFlowControlImpl(); // 流程控制接口 //
		 * 添加业务操作log wfcs.AddTaskLog(coco_tapr_id, "cocompact", coco_id, "合同签回",
		 * username, ""); // 任务单跳转至下一步 int x = wfcs.PassToNext(coco_tapr_id,
		 * coco_id, username, "", 0); // 更新业务表任务单id
		 * cocoDal.updatetaskid(coco_id, x);
		 * 
		 * message[0] = "1"; message[1] = "公司合同签回成功!"; } else { message[0] =
		 * "0"; message[1] = "公司合同签回失败!"; } } catch (Exception e) { message[0] =
		 * "2"; message[1] = "公司合同签回，操作出错。";
		 * 
		 * } return message;
		 */
		String[] message = new String[5];
		try {
			CoCompactModel m = new CoCompactModel();
			m.setCoco_returndate(coco_returndate);
			m.setCoco_signdate(coco_signdate);
			m.setCoco_signplace(coco_signplace);
			m.setCoco_indate(coco_indate);
			m.setCoco_delay(coco_delay);
			m.setCoco_money(coco_money);
			m.setCoco_invoice(coco_invoice);
			m.setCoco_paydate(paydate);
			m.setCoco_fw_p(coco_fw_p);
			m.setCoco_fl_p(coco_fl_p);
			m.setCoco_dk_p(coco_dk_p);
			Object[] obj = { "2", coco_id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new CoCompact_OperateImpl());
			message = wf.PassToNext(obj, coco_tapr_id, username, "", cola_id,
					"");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同签回，操作出错。";
		}
		return message;
	}

	// 公司合同归档
	public String[] returnCoCompact(int coco_tapr_id, int coco_id,
			String coco_filedate, String coco_fileid, String coco_chs_copies,
			String coco_en_copies, String coco_remark, String username,
			int cola_id, String coco_delay, String coco_indate, Integer paydate) {
		/*
		 * String[] message = new String[2]; try { CoCompactModel m = new
		 * CoCompactModel(); int result = 0;
		 * 
		 * m.setCoco_filedate(coco_filedate); m.setCoco_fileid(coco_fileid);
		 * m.setCoco_remark(coco_remark); result =
		 * cocoDal.returnCoCompact(coco_id, m);
		 * 
		 * if (result == 0) {
		 * 
		 * WfFlowControlService wfcs = new WfFlowControlImpl(); // 流程控制接口 //
		 * 添加业务操作log wfcs.AddTaskLog(coco_tapr_id, "cocompact", coco_id, "合同归档",
		 * username, ""); // 任务单跳转至下一步 int x = wfcs.PassToNext(coco_tapr_id,
		 * coco_id, username, "", 0); // 更新业务表任务单id
		 * cocoDal.updatetaskid(coco_id, x);
		 * 
		 * message[0] = "1"; message[1] = "公司合同归档成功!"; } else { message[0] =
		 * "0"; message[1] = "公司合同归档失败!"; }
		 * 
		 * } catch (Exception e) { message[0] = "2"; message[1] =
		 * "公司合同归档，操作出错。";
		 * 
		 * } return message;
		 */
		String[] message = new String[5];
		try {
			CoCompactModel m = new CoCompactModel();
			m.setCoco_filedate(coco_filedate);
			m.setCoco_fileid(coco_fileid);
			m.setCoco_chs_copies(coco_chs_copies);
			m.setCoco_en_copies(coco_en_copies);
			m.setCoco_remark(coco_remark);
			m.setCoco_delay(coco_delay);
			m.setCoco_indate(coco_indate);
			m.setCoco_paydate(paydate);
			Object[] obj = { "3", coco_id, m };
			// 执行工作流
			WfOperateService wf = new WfOperateImpl(new CoCompact_OperateImpl());
			message = wf.PassToNext(obj, coco_tapr_id, username, "", cola_id,
					"");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同归档，操作出错。";
		}
		return message;
	}

	// 根据合同id获取报价单列表
	public List<CoOfferModel> getCoOfferList(int coco_id) throws SQLException {
		List<CoOfferModel> list = new ArrayList<CoOfferModel>();
		CoCompact_OperateDal dal = new CoCompact_OperateDal();
		list = dal.getCoOfferList(coco_id);
		return list;
	}

	// 根据合同id获取报价单详情列表
	public List<CoOfferListModel> getCoOfferInfoList(int coco_id)
			throws SQLException {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoCompact_OperateDal dal = new CoCompact_OperateDal();
		list = dal.getCoOfferInfoList(coco_id);
		return list;
	}

	// 根据潜在客户id获取报价单详情列表
	public List<CoOfferListModel> getCoOfferInfoListBycolaid(int cola_id)
			throws SQLException {
		List<CoOfferListModel> list = new ArrayList<CoOfferListModel>();
		CoCompact_OperateDal dal = new CoCompact_OperateDal();
		list = dal.getCoOfferInfoListBycolaid(cola_id);
		return list;
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}

	// 根据报价单项目获取页面显示情况(单个报价单)
	public CoCompactTemAddModel getPageVisible(int coof_id) throws SQLException {
		CoCompactTemAddModel model = new CoCompactTemAddModel();
		model = cocoDal.getPageVisible(coof_id);
		return model;
	}

	// 根据报价单项目获取页面显示情况(多个报价单)
	public CoCompactTemAddModel getPageVisible(String coof_ids)
			throws SQLException {
		CoCompactTemAddModel model = new CoCompactTemAddModel();
		model = cocoDal.getPageVisible(coof_ids);
		return model;
	}

	// 根据合同编号获取
	public CoCompactTemAddModel getPageVisible2(String coli_coco_id)
			throws SQLException {
		CoCompactTemAddModel model = new CoCompactTemAddModel();
		model = cocoDal.getPageVisible2(coli_coco_id);
		return model;
	}

	// 公司合同录入
	public String[] addCompactInfo(CoCompactModel m) {

		// 新任务单代码
		String[] message = new String[3];
		try {
			int result = 0;
			result = cocoDal.addCompactInfo(m);
			if (result != 0) {
				message[0] = "1";
				message[1] = "公司合同新增成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "公司合同新增失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同新增，操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 公司合同录入(财务外地委托合同)
	public String[] addCSCompactInfo(CoCompactModel m) {

		// 新任务单代码
		String[] message = new String[3];
		try {
			int result = 0;
			result = cocoDal.addCSCompactInfo(m);
			if (result != 0) {
				message[0] = "1";
				message[1] = "公司合同新增成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "公司合同新增失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同新增，操作出错。";
			message[2] = "0";
		}
		return message;
	}

	// 更新每月付款日和增值税比例
	public String[] upCocoPaydate(CoCompactModel m) {

		// 新任务单代码
		String[] message = new String[3];
		try {
			int result = 0;
			result = cocoDal.upCocoPaydate(m);
			if (result != 0) {
				message[0] = "1";
				message[1] = "公司合同修改成功!";
				message[2] = String.valueOf(result);
			} else {
				message[0] = "0";
				message[1] = "公司合同修改失败!";
				message[2] = "0";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司合同修改，操作出错。";
			message[2] = "0";
		}
		return message;
	}
}
