package Controller.CoReg;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoOnlineRegisterInfoModel;
import Model.ResponsbilityBookModel;
import bll.CoReg.CoReg_Bll;
import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_SpOperateBll;

public class ResponsbilityBookStep3Controller {
	private List<CoOnlineRegisterInfoModel> docList = new ListModelList<>();
	private ResponsbilityBookModel r = new ResponsbilityBookModel();
	private Date date;
	private String timeStr;
	private CoReg_Bll bll = new CoReg_Bll();
	private CoOnlineRegisterInfoModel m = new CoOnlineRegisterInfoModel();
	private int coriid;
	private int daid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());
	private int taprid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());

	public ResponsbilityBookStep3Controller() {
		timeStr = "反馈所需材料日期";
		r = bll.getRbbm(daid);
		coriid = r.getRebk_cori_id();
		date = new Date();
		docList = new CoReg_ListBll().getstateList(" and type=2");
		m = bll.getCoRim(coriid);
	}

	@Command("lbinit")
	public void lbinit(@BindingParam("lb") Listbox lb) {
		lb.setMultiple(true);
	}

	// 提交
	@Command
	public void submit(@BindingParam("win") Window window,
			@BindingParam("ltb") Listbox ltb) {

		Set<Listitem> set = ltb.getSelectedItems();
		String need_doc = "";
		for (Listitem lt : set) {
			need_doc += ((CoOnlineRegisterInfoModel) lt.getValue())
					.getStatename() + "，";
		}
		need_doc = need_doc.substring(0, need_doc.length() - 1);
		r.setRebk_need_doc(need_doc);
		r.setRebk_rs_doctime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(date));
		CoReg_SpOperateBll bll = new CoReg_SpOperateBll();
		String[] str = bll.reSponStep(r, taprid, r.getCid());
		if (str[0].equals("1")) {
			Messagebox.show("提交成功!", "INFORMATION", Messagebox.OK,
					Messagebox.INFORMATION);
			window.detach();
		} else {
			Messagebox.show(str[1], "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public CoOnlineRegisterInfoModel getM() {
		return m;
	}

	public void setM(CoOnlineRegisterInfoModel m) {
		this.m = m;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

	public List<CoOnlineRegisterInfoModel> getDocList() {
		return docList;
	}

	public void setDocList(List<CoOnlineRegisterInfoModel> docList) {
		this.docList = docList;
	}
}
