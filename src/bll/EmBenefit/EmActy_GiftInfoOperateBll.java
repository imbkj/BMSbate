package bll.EmBenefit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Controller.EmBenefit.EmActy_PayInfoImpl;
import Model.EmActyContactContentInfoModel;
import Model.EmActyGiftBackInfoModel;
import Model.EmActyGiftOutInfoModel;
import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseHistoryModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.EmactyUseHouseLogModel;
import Util.UserInfo;
import dal.EmBenefit.EmActy_GiftInfoOperateDal;

public class EmActy_GiftInfoOperateBll {
	private EmActy_GiftInfoOperateDal dal = new EmActy_GiftInfoOperateDal();

	// 新增礼品信息
	public String[] EmActy_GiftAdd(EmActySuppilerGiftInfoModel m, String typeid) {
		// 任务单id为67
		Object[] obj = { typeid, m };
		Map<String, String> map = new HashMap<String, String>();
		map.put("ownmonth", m.getOwnmonth() + "");
		WfBusinessService wfbs = new EmActy_GiftInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "福利申请", "“" + m.getGift_name()
				+ "”福利申请", 67, UserInfo.getUsername(), "", 0, "", map);
		return str;
	}

	// 新增礼品信息(新修改)
	public String[] EmActy_GiftInfoAdd(EmActySuppilerGiftInfoModel m,
			String typeid, String con, Integer lcID) {
		// 任务单id为67(活动)，100礼品
		Object[] obj = { typeid, m };
		Map<String, String> map = new HashMap<String, String>();
		map.put("ownmonth", m.getOwnmonth() + "");
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.AddTaskToNext(obj, "福利申请", "“" + m.getOwnmonth()
				+ con + "”福利申请", lcID, UserInfo.getUsername(), "", 0, "", map);
		if (str[0] == "1") {
			// 跳过生成生成支付明细
			// if(str[3]!=null&&!str[3].equals("")&&str[3]!="")
			// {
			// m.setGift_id(Integer.parseInt(str[3]));
			// Object[] objs = {"1",m};
			// int tapr_id=Integer.parseInt(str[2]);
			// wfs.SkipToNext(objs, tapr_id, UserInfo.getUsername(), "", 0, "");
			// }
		}
		return str;
	}

	// 新增支付任务单
	public String[] EmActy_payAdd(EmActySuppilerGiftInfoModel m) {
		Object[] obj = { "1", m };
		WfBusinessService wfbs = new EmActy_PayInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Map<String, String> map = new HashMap<String, String>();
		map.put("ownmonth", m.getOwnmonth() + "");
		String[] str = wfs.AddTaskToNext(obj, "福利申请", "财务支付费用", 88,
				UserInfo.getUsername(), "", 0, "", map);
		return str;
	}

	// 新增支付任务单
	public String[] EmActy_payUpdate(EmActySuppilerGiftInfoModel m,
			Integer parid) {
		Object[] obj = { "1", m };
		WfBusinessService wfbs = new EmActy_PayInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj, parid, UserInfo.getUsername(), "",
				0, "");
		return str;
	}

	// 礼品信息任务单修改
	public String[] updateEmActy_GiftInfo(EmActySuppilerGiftInfoModel m,
			String sql, String type) {
		// type=2,更新；type=3,重新提交
		Object[] obj = { type, m, sql };
		WfBusinessService wfbs = new EmActy_GiftInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj, m.getGift_tarpid(),
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

	// 礼品信息任务单修改并跳过下一步
	public String[] updateEmActy_GiftInfos(EmActySuppilerGiftInfoModel m,
			String sql, String type) {
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.PassToNext(obj, m.getGift_tarpid(),
				UserInfo.getUsername(), "", 0, "");
		if (str[0] == "1") {
			// type=1则跳过下一步，type=2则跳转到指定节点
			if (type.equals("1") || type == "1") {
				Object[] objs = { "1", m };
				int tapr_id = Integer.parseInt(str[2]);
				wfs.SkipToNext(objs, tapr_id, UserInfo.getUsername(), "", 0, "");
			} else if (type.equals("2") || type == "2") {
				// 活动审核完后就是报名中
				String strsql = ",gift_state=1";
				Object[] objs = { "2", m, strsql };
				int tapr_id = Integer.parseInt(str[2]);
				wfs.SkipToN(objs, tapr_id, 7, UserInfo.getUsername(), "", 0, "");
			}
		}
		return str;
	}

	// 处理完当前步骤后完结流程
	public String[] endEmActy_GiftInfos(EmActySuppilerGiftInfoModel m,
			String sql, String type) {
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.OverTask(obj, m.getGift_tarpid(),
				UserInfo.getUsername(), "");
		return str;
	}

	// 终止福利申请流程
	public String[] EmWelfareInfoStop(Integer tarpid, String sortid,
			String backcase, String id) {
		Object[] obj = { id, sortid, backcase };
		WfBusinessService wfbs = new EmWelfareImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.StopTask(obj, tarpid, UserInfo.getUsername(), "");
		return str;
	}

	// 礼品信息任务单修改并跳转到制定步骤
	public String[] updateEmActy_skip(EmActySuppilerGiftInfoModel m, String sql) {
		Object[] obj = { "2", m, sql };
		WfBusinessService wfbs = new EmActy_GiftImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.SkipToN(obj, m.getGift_tarpid(), 7,
				UserInfo.getUsername(), "", 0, "");
		return str;
	}

	// 跳转到支付节点
	public String[] skiptopay(Integer tarpid, String sql, Integer id) {
		Object[] obj = { id, sql };
		WfBusinessService wfbs = new EmWelfareImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		String[] str = wfs.SkipToN(obj, tarpid, 8, "系统", "", 0, "");
		return str;
	}

	// 支付审核完成后调用的方法
	public String[] passaudit(String paynum) {
		String[] str = new String[5];
		EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
		List<EmWelfareModel> list = bll.getEmWelfareList(" and emwf_paynum='"
				+ paynum + "'");
		if (list.size() > 0) {
			EmActy_GiftInfoSelectBll blls = new EmActy_GiftInfoSelectBll();
			List<EmActySuppilerGiftInfoModel> lists = blls
					.getEmActyGiftInfo(" and gift_sortid="
							+ list.get(0).getEmwf_sortid());
			if (lists.size() > 0) {
				String sql = ",emwf_state=5";
				Integer k = updateEmWelfareInfos(sql, paynum);
				if (k > 0) {
					EmActySuppilerGiftInfoModel model = lists.get(0);
					str = updateEmActy_GiftInfo(model, "", "2");
				}
			}
		}
		return str;
	}

	// 礼品信息任务单退回-财务退回
	public String[] backEmActy_AuditGiftInfo(EmActyGiftBackInfoModel m,
			Integer tarpid) {
		Object[] obj = { "3", m };
		WfBusinessService wfbs = new EmActy_GiftInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		// 处理完当前任务后完结流程
		String[] str = wfs.OverTask(obj, tarpid, UserInfo.getUsername(),
				m.getGibk_cause());
		return str;
	}

	// 礼品信息任务单退回
	public String[] backEmActy_GiftInfo(EmActyGiftBackInfoModel m,
			Integer tarpid) {
		Object[] obj = { "3", m };
		WfBusinessService wfbs = new EmActy_GiftInfoImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		// 退回上一步
		String[] str = wfs.ReturnToPrev(obj, tarpid, UserInfo.getUsername(),
				m.getGibk_cause());
		return str;
	}

	// 修改礼品信息
	public Integer EmActy_GiftEdit(EmActySuppilerGiftInfoModel m) {
		return dal.EmActy_GiftEdit(m);
	}

	// 更新领卡信息表任务单id
	public boolean updateGiftInfo(String sqlstr, int id) {
		return dal.updateGiftInfo(sqlstr, id);
	}

	// 礼品出库并修改库存数量
	public Integer EmActy_Giftout(EmActyGiftOutInfoModel m) {
		return dal.EmActy_Giftout(m);
	}

	// 供应商联系信息新增
	public Integer EmActyContactContentInfo(EmActyContactContentInfoModel m) {
		return dal.EmActyContactContentInfo(m);
	}

	// 更新员工福利名单信息
	public boolean updateEmWelfare(String sortid, String idstr, Integer stateid) {
		return dal.updateEmWelfare(sortid, idstr, stateid);
	}

	// 更新员工福利名单信息
	public Integer updateEmWelfare(Integer gift_id, Integer emwf_id,
			String client) {
		return dal.updateEmWelfare(gift_id, emwf_id, client);
	}

	// 更新员工福利名单信息
	public Integer updateEmWelfares(String emwf_linktime,
			String emwf_linkcontent, Integer id) {
		return dal.updateEmWelfares(emwf_linktime, emwf_linkcontent, id);
	}

	// 更新员工福利名单信息表的任务id
	public boolean updateEmWelfareTarpid(String idstr, Integer tarpid) {
		return dal.updateEmWelfareTarpid(idstr, tarpid);
	}

	// 更新员工福利名单信息(新修改)
	public Integer updateEmWelfare2(Integer gift_id, EmWelfareModel model) {
		return dal.updateEmWelfare2(gift_id, model);
	}

	// 更新员工福利名单信息
	public Integer updateEmWelfareInfo(String sqlstr, Integer id) {
		return dal.updateEmWelfareInfo(sqlstr, id);
	}

	// 更新员工福利名单信息
	public Integer updateEmWelfareInfo2(String sortid) {
		return dal.updateEmWelfareInfo2(sortid);
	}

	// 更新员工福利名单信息
	public boolean updateEmWelfare(String idstr, Integer ownmonth) {
		return dal.updateEmWelfare(idstr, ownmonth);
	}

	// 取消单个员工申请
	public Integer EmWelfare_cancel(Integer emwf_id, String backcase) {
		return dal.EmWelfare_cancel(emwf_id, backcase);
	}

	public Integer updateEmWelfareInfos(String sql, String paynum) {
		return dal.updateEmWelfareInfos(sql, paynum);
	}

	// 新增或者修改库存信息
	public Integer EmActyWarehouse(EmActyWarehouseModel model) {
		return dal.EmActyWarehouse(model);
	}

	// 库存记录
	public Integer addEmActyWarehouseHistory(Integer emwf_id, Integer num,
			BigDecimal hsry_price, Integer hsry_wase_id) {
		return dal.addEmActyWarehouseHistory(emwf_id, num, hsry_price,
				hsry_wase_id);
	}

	// 修改库存
	public Integer WarehouseHistoryEdit(EmActyWarehouseHistoryModel m,
			Integer num) {
		return dal.WarehouseHistoryEdit(m, num);
	}

	// 修改库存
	public Integer WarehouseEdit(EmActyWarehouseModel m, Integer num,
			String editcontent) {
		return dal.WarehouseEdit(m, num, editcontent);
	}

	// 市场部退回客服确认的数据
	public Integer backEmWelfareInfo(String emwf_backcase, String strid) {
		return dal.backEmWelfareInfo(emwf_backcase, strid);
	}

	public int insertHouse(int wase_id, int hsnt_num, int wase_tnum,
			int changenum, String editcontent, String hsnt_remark,
			String wase_name, String hsnt_tali_name) {
		return dal.insertHouse(wase_id, hsnt_num, wase_tnum, changenum,
				editcontent, hsnt_remark, wase_name, hsnt_tali_name);
	}

	// 更新员工福利名单信息
	public boolean updateEmWelfareBySortid(String sortid, Integer stateid) {
		return dal.updateEmWelfareBySortid(sortid, stateid);
	}

	// 更新员工福利名单信息
	public int updateEmWelfareBySortid(String sortid, String sqls) {
		return dal.updateEmWelfareBySortid(sortid, sqls);
	}

	public Integer addEmactyUseHouseLog(EmactyUseHouseLogModel m) {
		return dal.addEmactyUseHouseLog(m);
	}

	// 减去使用的库存数量
	public Integer delEmactyUseHouse(int wase_id, int useh_num) {
		return dal.delEmactyUseHouse(wase_id, useh_num);
	}
}
