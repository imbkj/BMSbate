package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceMonthlyAccountModel;
import Model.CoFinanceMonthlyBillModel;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.CoFinanceManage.cfma_OperateBll;

public class Cfma_MothlyBillManageController {
	private final int cfma_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cfma_id").toString());

	private Cfma_SelBll bll;
	private CoFinanceMonthlyAccountModel cfmaModel;
	private List<CoFinanceMonthlyBillModel> mbList;
	private cfma_OperateBll opbll;
	private String listSrc;
	private Include icBusinessList;

	public Cfma_MothlyBillManageController() {
		bll = new Cfma_SelBll();
		opbll = new cfma_OperateBll();
		cfmaModel = bll.getMonthlyAccountByCfmaId(cfma_id);
		mbList = bll.getMonthlyBill(cfmaModel.getCfma_cfta_id(),
				cfmaModel.getOwnmonth());
		listSrc = "../CoFinanceManage/Cfma_BusinessList.zul?cid="
				+ cfmaModel.getCid() + "&ownmonth=" + cfmaModel.getOwnmonth()
				+ "&billNo=0";
	}

	// 同步台账
	@Command("synchronous")
	public void synchronous() {
		if (Messagebox.show("确认同步台账数据吗？同步台账需要一定的时间，请耐心等待...", "操作提示",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			opbll.synchronousFinance(cfmaModel.getCid(),
					cfmaModel.getOwnmonth());
			cfmaModel = bll.getMonthlyAccountByCfmaId(cfma_id);
			BindUtils.postNotifyChange(null, null, this, "cfmaModel");
			refreshListSrc();
			Messagebox.show("已完成同步！", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	// 生成账单
	@Command("createBill")
	public void createBill(@ContextParam(ContextType.VIEW) Component view) {
		//opbll.synchronousFinance(cfmaModel.getCid(),
		//		cfmaModel.getOwnmonth());
		cfmaModel = bll.getMonthlyAccountByCfmaId(cfma_id);
		BindUtils.postNotifyChange(null, null, this, "cfmaModel");
		refreshListSrc();
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cfta_id", cfmaModel.getCfma_cfta_id());
		map.put("ownmonth", cfmaModel.getOwnmonth());
		map.put("cid", cfmaModel.getCid());
		Window window = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_MonthlyBillCreate.zul", view, map);
		window.doModal();
	}

	// 合并账单
	@Command("mergeBill")
	public void mergeBill() {
		if (Messagebox.show("确认合并所选的账单吗？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			List<CoFinanceMonthlyBillModel> mgList = new ArrayList<CoFinanceMonthlyBillModel>();
			for (CoFinanceMonthlyBillModel m : mbList) {
				if (m.isCheck()) {
					mgList.add(m);
				}
			}
			if (mgList.size() > 1) {
				String[] message = opbll.mergeBill(mgList,
						cfmaModel.getCfma_cfta_id(), cfmaModel.getCid(),
						cfmaModel.getOwnmonth(), UserInfo.getUsername());
				if ("1".equals(message[0])) {
					refreshWin();
					BindUtils.postNotifyChange(null, null, this, "mbList");
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				Messagebox.show("请选择至少两张账单进行合并。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		}
	}
	
	
//	// 领款分配
//		@Command("distribution")
//		public void distribution(@BindingParam("m") CoFinanceMonthlyBillModel m,
//				@ContextParam(ContextType.VIEW) Component view) {
//			if (m.getCfmb_FinanceConfirm() == 1
//					|| m.getCfmb_PersonnelConfirm() == 1) {
//				if (m.getCfmb_WriteOffs() == 1) {
//					Messagebox.show("该账单已销账。", "操作提示", Messagebox.OK,
//							Messagebox.NONE);
//				} else if (m.getCfmb_WriteOffs() == 2) {
//					Messagebox.show("该账单已结转。", "操作提示", Messagebox.OK,
//							Messagebox.NONE);
//				} else {
//					Map<String, String> map = new HashMap<String, String>();
//					map.put("billNo", m.getCfmb_number());
//					map.put("coba_client",cfmaModel.getCoba_client());
//					Integer a=m.getOwnmonth();
//					map.put("owmno",a.toString());
//					Window window = (Window) Executions.createComponents(
//							"/CoFinanceManage/Cfma_CollectDistribution.zul", view, map);
//					window.doModal();
//				}
//			} else {
//				Messagebox.show("该账单应收未确认，无法领款。", "操作提示", Messagebox.OK,
//						Messagebox.NONE);
//			}
//		}


	// 非标管理
	@Command("manualDisposable")
	public void manualDisposable() {
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("cid", cfmaModel.getCid());
			Window window = (Window) Executions.createComponents(
					"../CoFinanceManage/Cfma_ManualDisposable.zul", null, map);
			window.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 撤销账单
	@Command("delBill")
	public void delBill(@BindingParam("m") CoFinanceMonthlyBillModel m) {
		if (Messagebox.show("确认撤销该账单吗？", "操作提示",
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			
			String[] message = opbll.delBill(m.getCfmb_number());
			if ("1".equals(message[0])) {
				mbList.remove(m);
				refreshListSrc();
				BindUtils.postNotifyChange(null, null, this, "mbList");
			}
			Messagebox.show(message[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}
	
//	// 取消确认
//		@Command("delBill")
//		public void delBill(@BindingParam("m") CoFinanceMonthlyBillModel m) {
//			if (Messagebox.show("确认撤销该账单吗？", "操作提示",
//					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
//				String[] message = opbll.delBill(m.getCfmb_number());
//				if ("1".equals(message[0])) {
//					mbList.remove(m);
//					refreshListSrc();
//					BindUtils.postNotifyChange(null, null, this, "mbList");
//				}
//				Messagebox.show(message[1], "操作提示", Messagebox.OK,
//						Messagebox.INFORMATION);
//			}
//		}

	// 查看账单
	@Command("viewBill")
	public void viewBill(@BindingParam("billNo") String billNo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("billNo", billNo);
		Window window = (Window) Executions.createComponents(
				"../CoFinanceManage/Cfma_MonthlyBillView.zul", null, map);
		window.doModal();
	}

	// 人事应收确认
	@Command("perConfirm")
	public void perConfirm(@BindingParam("m") CoFinanceMonthlyBillModel m) {
		try {
			if (m.getCfmb_PersonnelReceivable().compareTo(BigDecimal.ZERO) == 0) {
				Messagebox.show("当前账单并无人事应收款，无需确认。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				String username = UserInfo.getUsername();
				String dept = opbll.getUserDept(username);
				if ("客户服务部".equals(dept) || "全国项目部".equals(dept)
						|| "计算机信息部".equals(dept)) {
					if (Messagebox.show("确认人事应收吗？", "操作提示", Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
						int i = opbll.confirmBill(m.getCfmb_number(), 1,
								username);
						if (i == 1) {
							m.setCfmb_PersonnelConfirm(1);
							BindUtils.postNotifyChange(null, null, this,
									"mbList");
							Messagebox.show("已确认。", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
						} else {
							Messagebox.show("确认出错。", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				} else {
					Messagebox.show("抱歉，您无权限操作人事应收的确认。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 财务应收确认
	@Command("finConfirm")
	public void finConfirm(@BindingParam("m") CoFinanceMonthlyBillModel m) {
		try {
			if (m.getCfmb_FinanceReceivable().compareTo(BigDecimal.ZERO) == 0) {
				Messagebox.show("当前账单并无财务应收款，无需确认。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				String username = UserInfo.getUsername();
				String dept = opbll.getUserDept(username);
				if ("财务部".equals(dept) || "计算机信息部".equals(dept)) {
					if (Messagebox.show("确认财务应收吗？", "操作提示", Messagebox.YES
							| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
						int i = opbll.confirmBill(m.getCfmb_number(), 2,
								UserInfo.getUsername());
						if (i == 1) {
							m.setCfmb_FinanceConfirm(1);
							BindUtils.postNotifyChange(null, null, this,
									"mbList");
							Messagebox.show("已确认。", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
						} else {
							Messagebox.show("确认出错。", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				} else {
					Messagebox.show("抱歉，您无权限操作财务应收的确认。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取include组件
	@Command("setInclude")
	public void setInclude(@BindingParam("com") Include com) {
		icBusinessList = com;
	}

	// 刷新业务列表
	private void refreshListSrc() {
		try {
			/*
			 * Include icBusinessList = (Include) Path
			 * .getComponent("/winMothlyBillManage/icBusinessList");
			 */
			icBusinessList.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 刷新账单列表
	@Command("refreshWin")
	@NotifyChange("mbList")
	public void refreshWin() {
		mbList = bll.getMonthlyBill(cfmaModel.getCfma_cfta_id(),
				cfmaModel.getOwnmonth());
		refreshListSrc();
	}

	public CoFinanceMonthlyAccountModel getCfmaModel() {
		return cfmaModel;
	}

	public List<CoFinanceMonthlyBillModel> getMbList() {
		return mbList;
	}

	public void setMbList(List<CoFinanceMonthlyBillModel> mbList) {
		this.mbList = mbList;
	}

	public String getListSrc() {
		return listSrc;
	}

}
