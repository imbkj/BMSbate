package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import bll.CoFinanceManage.Cfma_AgencyWriteOffOperateBll;
import bll.CoFinanceManage.Cfma_AgencyWriteOffSelBll;

import Model.CoFinanceAgencyMonthlyBillModel;
import Model.CoFinanceAgencyWriteOffModel;
import Util.UserInfo;

public class Cfma_AgencyWriteOffController {
	private final int coab_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("coab_id").toString());
	private List<CoFinanceAgencyMonthlyBillModel> wtList;
	private List<CoFinanceAgencyMonthlyBillModel> stList;
	private List<CoFinanceAgencyWriteOffModel> woList;
	private BigDecimal wtWriteOffEx = BigDecimal.ZERO;
	private BigDecimal stWriteOffEx = BigDecimal.ZERO;
	private BigDecimal writeOffEx = BigDecimal.ZERO;
	private Cfma_AgencyWriteOffSelBll bll;
	private boolean logOpen = false;

	public Cfma_AgencyWriteOffController() {
		bll = new Cfma_AgencyWriteOffSelBll();
		wtList = bll.getWtWriteOffBill(coab_id);
		stList = bll.getStWriteOffBill(coab_id);
		woList = bll.getWriteOffList(coab_id);
	}

	// 提交
	@Command("Submit")
	public void Submit(@BindingParam("gdWt") Grid gdWt,
			@BindingParam("gdSt") Grid gdSt) {
		if (writeOffEx.compareTo(BigDecimal.ZERO) == 1) {
			if (Messagebox.show("请核对冲销的金额：" + writeOffEx, "操作提示",
					Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
				try {
					List<CoFinanceAgencyMonthlyBillModel> wtCheckList = getGridData(gdWt);
					List<CoFinanceAgencyMonthlyBillModel> stCheckList = getGridData(gdSt);
					if (wtCheckList.size() > 0 && stCheckList.size() > 0) {
						Cfma_AgencyWriteOffOperateBll opBll = new Cfma_AgencyWriteOffOperateBll();
						int i = opBll.CoFinanceAgencyWriteOff(coab_id,
								wtCheckList, stCheckList, writeOffEx,
								UserInfo.getUsername());
						if (i == 1) {
							refreshWin();
							Messagebox.show("冲销，提交成功。", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
						} else {
							Messagebox.show("冲销,提交失败。", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					} else {
						Messagebox.show("获取数据失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} catch (Exception e) {
					Messagebox.show("冲销,提交出错。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					e.printStackTrace();
				}
			}
		} else {
			Messagebox.show("无需提交,您需要冲销的金额为0。", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	// 获取Grid的修改数据
	private List<CoFinanceAgencyMonthlyBillModel> getGridData(Grid gd) {
		List<Component> rows = gd.getRows().getChildren();
		List<CoFinanceAgencyMonthlyBillModel> list = new ArrayList<CoFinanceAgencyMonthlyBillModel>();
		CoFinanceAgencyMonthlyBillModel m = null;
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否有勾选数据
				if (((Checkbox) row.getChildren().get(0).getChildren().get(0))
						.isChecked()) {
					m = (CoFinanceAgencyMonthlyBillModel) row.getValue();
					list.add(m);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	// 委托列表勾选
	@Command("checkWt")
	public void checkWt(@BindingParam("m") CoFinanceAgencyMonthlyBillModel m,
			@BindingParam("check") boolean check) {
		if (check) {
			wtWriteOffEx = wtWriteOffEx.add(m.getImbalance());
		} else {
			wtWriteOffEx = wtWriteOffEx.subtract(m.getImbalance());
		}
		setWriteOffEx();
	}

	// 受托列表勾选
	@Command("checkSt")
	public void checkSt(@BindingParam("m") CoFinanceAgencyMonthlyBillModel m,
			@BindingParam("check") boolean check) {
		if (check) {
			stWriteOffEx = stWriteOffEx.add(m.getImbalance());
		} else {
			stWriteOffEx = stWriteOffEx.subtract(m.getImbalance());
		}
		setWriteOffEx();
	}

	// 计算冲销金额
	private void setWriteOffEx() {
		if (wtWriteOffEx.compareTo(stWriteOffEx) == -1) {
			writeOffEx = wtWriteOffEx;
		} else {
			writeOffEx = stWriteOffEx;
		}
		BindUtils.postNotifyChange(null, null, this, "writeOffEx");
	}

	private void refreshWin() {
		wtWriteOffEx = BigDecimal.ZERO;
		stWriteOffEx = BigDecimal.ZERO;
		writeOffEx = BigDecimal.ZERO;
		wtList = bll.getWtWriteOffBill(coab_id);
		stList = bll.getStWriteOffBill(coab_id);
		woList = bll.getWriteOffList(coab_id);
		logOpen = true;
		BindUtils.postNotifyChange(null, null, this, "writeOffEx");
		BindUtils.postNotifyChange(null, null, this, "wtList");
		BindUtils.postNotifyChange(null, null, this, "stList");
		BindUtils.postNotifyChange(null, null, this, "woList");
		BindUtils.postNotifyChange(null, null, this, "logOpen");
	}

	public List<CoFinanceAgencyMonthlyBillModel> getWtList() {
		return wtList;
	}

	public List<CoFinanceAgencyMonthlyBillModel> getStList() {
		return stList;
	}

	public List<CoFinanceAgencyWriteOffModel> getWoList() {
		return woList;
	}

	public BigDecimal getWriteOffEx() {
		return writeOffEx;
	}

	public boolean isLogOpen() {
		return logOpen;
	}

}
