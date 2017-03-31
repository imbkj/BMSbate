package Controller.Archives;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import bll.Archives.Archive_FeeManagerBll;
import Model.EmArchivePaymentModel;
import Util.FormatObjectValueUtil;

public class Archive_FeeSearchController {
	private List<EmArchivePaymentModel> eaplist = new ListModelList<EmArchivePaymentModel>();
	private List<EmArchivePaymentModel> ownmonthList = new ListModelList<EmArchivePaymentModel>();
	private List<EmArchivePaymentModel> loanManList = new ListModelList<EmArchivePaymentModel>();

	private EmArchivePaymentModel epModel = new EmArchivePaymentModel();

	private Archive_FeeManagerBll bll = new Archive_FeeManagerBll();
	private FormatObjectValueUtil fovu = new FormatObjectValueUtil();

	public Archive_FeeSearchController() {
		setEaplist(epModel);
		setLoanManList();
		setOwnmonthList();
	}

	@Command("setDateType")
	public void setDateType(@BindingParam("a") Comboitem ci,
			@BindingParam("b") Datebox db1, @BindingParam("c") Datebox db2) {
		db1.setValue(null);
		db2.setValue(null);
		if (ci != null && ci.getLabel() != "") {

			if (ci.getLabel().equals("缴费时段")) {
				db1.setStyle("display:");
				db2.setStyle("display:");
			} else {
				db1.setStyle("display:");
				db2.setStyle("display:none;");
			}
		} else {
			db1.setStyle("display:none;");
			db2.setStyle("display:none;");
		}
	}

	

	@Command("SearchInfo")
	@NotifyChange("eaplist")
	public void SearchInfo(@BindingParam("a") Combobox cb,
			@BindingParam("b") Combobox cbfee) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String feeType = fovu.getFieldValue(cbfee);
		String ownmonth = fovu.getFieldValue(epModel.getOwnmonth());
		String datetype = fovu.getFieldLabel(cb.getSelectedItem());
		String date1 = fovu.getFieldValue(epModel.getEmap_sdate());
		String date2 = fovu.getFieldValue(epModel.getEmap_cdate());
		String loanMan = fovu.getFieldLabel(epModel.getEmap_lname());
		String company = fovu.getFieldValue(epModel.getEmap_company());
		String name = fovu.getFieldValue(epModel.getEmap_name());
		String fileplace = fovu.getFieldValue(epModel.getEmap_fileplace());
		String num = fovu.getFieldValue(epModel.getEmap_idlist());

		epModel.setEmap_lstate(Integer.valueOf(feeType));
		epModel.setOwnmonth(Integer.valueOf(ownmonth));
		epModel.setEmap_lname(loanMan);
		epModel.setEmap_company(company);
		epModel.setEmap_name(name);
		epModel.setEmap_fileplace(fileplace);
		epModel.setEmap_idlist(num);
		epModel.setDatetype(Integer.valueOf(datetype));
		

		switch (datetype) {
		case "缴费时段":
			if (date1.equals("") || date2.equals("")) {
				Messagebox.show("请选择缴费时间段!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				
				//pList.add(date1);
				//pList.add(date2);
				//sql.append(" and emap_sdate between ? and ?");
			}
			break;
		case "借款日期":
			if (date1.equals("")) {
				Messagebox.show("请选择借款日期!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				
				//pList.add(date1);
				//sql.append(" and emap_ltime = ?");
			}

			break;
		case "交发票日期":
			if (date1.equals("")) {
				Messagebox.show("请选择交发票日期!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {
				
				//pList.add(date1);
				//sql.append(" and emap_itime = ?");
			}
			break;
		}

		//setEaplist(sql.toString(), pList);

	}

	public List<EmArchivePaymentModel> getEaplist() {
		return eaplist;
	}

	public void setEaplist(EmArchivePaymentModel em) {
		this.eaplist = bll.getBaseList(em);
	}

	public List<EmArchivePaymentModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList() {
		this.ownmonthList = bll.getOwnmonthList();
	}

	public List<EmArchivePaymentModel> getLoanManList() {
		return loanManList;
	}

	public void setLoanManList() {
		this.loanManList = bll.getLoanManList();
	}

	public EmArchivePaymentModel getEpModel() {
		return epModel;
	}

	public void setEpModel(EmArchivePaymentModel epModel) {
		this.epModel = epModel;
	}

}
