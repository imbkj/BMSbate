package Controller.EmCAFFeeInfo;

import impl.CheckStringImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.CheckStringService;

import Model.EmCAFFeeInfoModel;
import Util.DateStringChange;
import Util.UserInfo;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_OperateBll;
import bll.EmCAFFeeInfo.EmCAFFeeInfo_SelectBll;

public class EmCAFFeeInfo_RefundmentListController {
	private EmCAFFeeInfo_SelectBll ecfiSBll = new EmCAFFeeInfo_SelectBll();
	private EmCAFFeeInfo_OperateBll ecfiOBll = new EmCAFFeeInfo_OperateBll();
	private DateStringChange dsc = new DateStringChange();

	private List<EmCAFFeeInfoModel> ecfiList = new ArrayList<EmCAFFeeInfoModel>();
	String str = "";
	String orderBy = " ORDER BY a.ecfi_if_refundment,a.shortname,a.name";
	// 获取用户名
	String username = UserInfo.getUsername();

	// 查询条件
	private String company;

	public EmCAFFeeInfo_RefundmentListController() throws SQLException {
		str = " AND a.ecfi_cl_number IS NOT NULL AND a.ecfi_cl_number<>'' AND a.ecfi_if_return=1";
		ecfiList = ecfiSBll.getECFIInfoList(str, orderBy);
	}

	// 查询
	@Command("search")
	@NotifyChange({ "ecfiList" })
	public void search() throws SQLException {
		CheckStringService ch = new CheckStringImpl();
		str = " AND ecfi_cl_number IS NOT NULL AND ecfi_cl_number<>'' AND ecfi_if_return=1";
		// 公司名称
		if (company != "" && company != null && !company.equals("")) {
			company = company.trim();
			if (ch.isChinese(company)) {
				str = str + " and a.shortname like '%" + company + "%' ";
			} else if (ch.isNum(company)) {
				str = str + " and (a.cid='" + company
						+ "' or a.shortname like '%" + company + "%')";
			}
		}

		ecfiList = ecfiSBll.getECFIInfoList(str, orderBy);
	}

	// 打开处理页面
	@Command("openUpdate")
	@NotifyChange({ "ecfiList" })
	public void openUpdate(@BindingParam("ecfi_id") Integer ecfi_id)
			throws SQLException {

		if (Messagebox.show("是否操作退款？", "操作提示", Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION) == Messagebox.YES) {
			EmCAFFeeInfoModel ecfiM = new EmCAFFeeInfoModel();
			String[] message;

			ecfiM.setEcfi_id(ecfi_id);
			ecfiM.setEcfi_refundment_people(username);
			ecfiM.setEcfi_refundment_date(dsc.Datestringnow("yyyy-MM-dd"));
			// 调用更新方法
			message = ecfiOBll.ecfiRefundment(ecfiM);
			// 弹出提示
			Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.NONE);
		}

		ecfiList = ecfiSBll.getECFIInfoList(str, orderBy);

	}

	public List<EmCAFFeeInfoModel> getEcfiList() {
		return ecfiList;
	}

	public void setEcfiList(List<EmCAFFeeInfoModel> ecfiList) {
		this.ecfiList = ecfiList;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
