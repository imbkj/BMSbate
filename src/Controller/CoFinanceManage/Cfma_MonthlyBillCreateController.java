package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceMonthlyBillTempletConModel;
import Model.CoFinanceMonthlyBillTempletModel;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_SelBll;
import bll.CoFinanceManage.cfma_OperateBll;
import Util.UserInfo;

public class Cfma_MonthlyBillCreateController {
	private final int cfta_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cfta_id").toString());
	private final int ownmonth = Integer.parseInt(Executions.getCurrent()
			.getArg().get("ownmonth").toString());
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private Cfma_SelBll bll;
	private List<CoFinanceMonthlyBillTempletModel> btModel;
	private List<CoFinanceMonthlyBillTempletConModel> winTempList;
	private boolean winVisible = false;
	private boolean cellIdVisible = false;
	private String cellName;
	private String cellIdName;
	private List<CoFinanceMonthlyBillTempletConModel> compactList;
	private List<CoFinanceMonthlyBillTempletConModel> embaseList;
	private List<CoFinanceMonthlyBillTempletConModel> commissionOutList;
	private List<CoFinanceMonthlyBillTempletConModel> itemList;

	public Cfma_MonthlyBillCreateController() {
		bll = new Cfma_SelBll();
		
		System.out.println("cfta_id="+cfta_id);
		btModel = bll.getBillTemplet(Integer.parseInt(UserInfo.getDepID()));
		
		//btModel = bll.getBillTemplet();
	}

	// 生成账单
	@Command("createBill")
	public void createBill(@BindingParam("cb") Combobox cb,
			@BindingParam("remark") String remark,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (cb.getSelectedItem() != null) {
				cfma_OperateBll opbll = new cfma_OperateBll();
				String[] message = opbll.createBill(cb.getSelectedItem()
						.getValue().toString(), cfta_id, cid, ownmonth,
						UserInfo.getUsername(), winTempList, remark);
				if ("1".equals(message[0])) {
					Binder bind = (Binder) view.getParent().getAttribute(
							"binder");
					bind.postCommand("refreshWin", null);
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("请选择需要生成的模板。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("账单生成出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Command("cbChange")
	public void cbChange(@BindingParam("cb") Combobox cb) {
		try {
			if (cb.getSelectedItem() != null) {
				String prefix = cb.getSelectedItem().getValue().toString();
				changePrefix(prefix);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 根据选择的模板改变页面显示
	private void changePrefix(String prefix) {
		switch (prefix) {
		case "CO":
			winVisible = false;
			BindUtils.postNotifyChange(null, null, this, "winVisible");
			break;
		case "CP":
			winVisible = true;
			if (compactList == null) {
				compactList = bll.getBillTempletCompact(cid, ownmonth);
			}
			cellName = "合同编号";
			winTempList = compactList;
			cellIdVisible = false;
			BindUtils.postNotifyChange(null, null, this, "winVisible");
			BindUtils.postNotifyChange(null, null, this, "cellIdVisible");
			BindUtils.postNotifyChange(null, null, this, "cellName");
			BindUtils.postNotifyChange(null, null, this, "winTempList");
			break;
		case "EM":
			winVisible = true;
			if (embaseList == null) {
				embaseList = bll.getBillTempletEmbase(cid, ownmonth);
			}
			cellIdName = "雇员编号";
			cellName = "雇员姓名";
			winTempList = embaseList;
			cellIdVisible = true;
			BindUtils.postNotifyChange(null, null, this, "cellIdName");
			BindUtils.postNotifyChange(null, null, this, "cellIdVisible");
			BindUtils.postNotifyChange(null, null, this, "winVisible");
			BindUtils.postNotifyChange(null, null, this, "cellName");
			BindUtils.postNotifyChange(null, null, this, "winTempList");
			break;
		case "AD":
			winVisible = true;
			if (commissionOutList == null) {
				commissionOutList = bll.getBillTempletCommissionOutCity(cid,
						ownmonth);
			}
			cellName = "委托出地区";
			winTempList = commissionOutList;
			cellIdVisible = false;
			BindUtils.postNotifyChange(null, null, this, "winVisible");
			BindUtils.postNotifyChange(null, null, this, "cellIdVisible");
			BindUtils.postNotifyChange(null, null, this, "cellName");
			BindUtils.postNotifyChange(null, null, this, "winTempList");
			break;
		case "SP":
			winVisible = true;
			if (itemList == null) {
				itemList = bll.getBillTempletItem(cid, ownmonth);
			}
			cellName = "项目";
			winTempList = itemList;
			cellIdVisible = false;
			BindUtils.postNotifyChange(null, null, this, "winVisible");
			BindUtils.postNotifyChange(null, null, this, "cellIdVisible");
			BindUtils.postNotifyChange(null, null, this, "cellName");
			BindUtils.postNotifyChange(null, null, this, "winTempList");
			break;
		default:
			winVisible = false;
			winTempList = null;
			BindUtils.postNotifyChange(null, null, this, "winVisible");
			break;
		}
	}

	public List<CoFinanceMonthlyBillTempletModel> getBtModel() {
		return btModel;
	}

	public List<CoFinanceMonthlyBillTempletConModel> getWinTempList() {
		return winTempList;
	}

	public void setWinTempList(
			List<CoFinanceMonthlyBillTempletConModel> winTempList) {
		this.winTempList = winTempList;
	}

	public boolean isWinVisible() {
		return winVisible;
	}

	public String getCellName() {
		return cellName;
	}

	public boolean isCellIdVisible() {
		return cellIdVisible;
	}

	public String getCellIdName() {
		return cellIdName;
	}

}
