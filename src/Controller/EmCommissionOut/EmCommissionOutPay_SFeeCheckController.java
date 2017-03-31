package Controller.EmCommissionOut;

import java.sql.SQLException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.EmCommissionOutPay_UnfinishedBll;

import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutPayModel;

public class EmCommissionOutPay_SFeeCheckController {

	private EmCommissionOutPayModel wt_sfeelist = new EmCommissionOutPayModel();
	private EmCommissionOutPay_UnfinishedBll bll = new EmCommissionOutPay_UnfinishedBll();
	private ListModelList<EmCommissionOutPayModel> ownmonthlist;// 显示帐单所属月份

	@Init
	public void init() throws SQLException {
		String ecod_id = "";
		try {
			ecod_id = Executions.getCurrent().getArg().get("ecop_id")
					.toString();
		} catch (Exception e) {
			System.out.print(e.toString());
		}

		wt_sfeelist = bll.getsfeelist(ecod_id);// 显示未完成列表数据
		
		ownmonthlist = new ListModelList<EmCommissionOutPayModel>(
				bll.getzdownmonthlist(ecod_id,wt_sfeelist.getOwnmonth()));// 显示系统帐单月份明细
	}

	@Command("zd_add")
	@NotifyChange("wt_kfunflist")
	public void zd_add(@BindingParam("ecod_id") Label ecod_id,
			@BindingParam("win") Window win,@BindingParam("zd_ownmonth") Combobox zd_ownmonth,
			@BindingParam("zd_st") Combobox zd_st)
			throws SQLException {
		if(zd_st.getValue().equals("")){
			Messagebox.show("请选择是否添加帐单非标！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		int rsult = 0;
		rsult = bll.zd_add(ecod_id.getValue(),zd_st.getValue(),zd_ownmonth.getValue());// 更新二次确认时间

		if (rsult > 0) {
			Messagebox.show("操作成功！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			win.detach();
			
		} else {
			Messagebox.show("操作失败！", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmCommissionOutPayModel getWt_sfeelist() {
		return wt_sfeelist;
	}

	public void setWt_sfeelist(EmCommissionOutPayModel wt_sfeelist) {
		this.wt_sfeelist = wt_sfeelist;
	}

	public ListModelList<EmCommissionOutPayModel> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(ListModelList<EmCommissionOutPayModel> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

}
