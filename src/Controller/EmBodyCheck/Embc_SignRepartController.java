package Controller.EmBodyCheck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseServePromiseModel;
import Model.EmBodyCheckModel;
import Model.embodycheckoperlogModel;
import Util.UserInfo;
import bll.CoServePromise.CoServePromiseSelectBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;

public class Embc_SignRepartController {
	private EmBodyCheckModel model = new EmBodyCheckModel();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = null;
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();

	private List<EmBodyCheckModel> bclist = selectbll
			.getEmBodyCheckInfo(" and embc_id=" + eadaId);

	private String username = UserInfo.getUsername();
	private CoBaseServePromiseModel pomodel = new CoBaseServePromiseModel();
	private CoServePromiseSelectBll bcbll = new CoServePromiseSelectBll();
	private List<CoBaseServePromiseModel> prlist = new ArrayList<CoBaseServePromiseModel>();

	private Date clientshowdate;

	// 构造函数
	public Embc_SignRepartController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		if (bclist.size() > 0) {
			model = bclist.get(0);
		}
		prlist = bcbll.getPromiseList("and cid=" + model.getCid());
		if (prlist.size() > 0) {
			pomodel = prlist.get(0);
		}
		if (model.getEmbc_tapr_id() == null) {
			model.setEmbc_tapr_id(tapr_id);
		}
	}

	@Command
	public void signreport(@BindingParam("win") Window win) {
		if (clientshowdate == null) {
			Messagebox.show("请选择签收时间", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		String sql = ",ebcl_clientshowdate='" + changedate(clientshowdate)
				+ "',ebcl_showclient='" + username + "',ebcl_state=12";
		model.setOcon("客服签收体检报告");
		EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
		String[] str = obll.EmBodyCheckEdit(model, sql, "3");
		if (str[0] == "1") {
			embodycheckoperlogModel logm=new embodycheckoperlogModel();
			logm.setBclg_addname(UserInfo.getUsername());
			logm.setBclg_content("签收了体检报告");
			logm.setBclg_ebcl_id(model.getEbcl_id());
			obll.insertLog(logm);
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmBodyCheckModel getModel() {
		return model;
	}

	public void setModel(EmBodyCheckModel model) {
		this.model = model;
	}

	private String changedate(Date d) {
		String formatDate = null;
		if (d != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			formatDate = df.format(d);
		}
		return formatDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private java.sql.Date changetosqldate(Date d) {
		java.sql.Date date = null;
		if (d != null && !d.equals("")) {
			date = new java.sql.Date(d.getTime());
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (str != null && !str.equals("")) {
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public CoBaseServePromiseModel getPomodel() {
		return pomodel;
	}

	public void setPomodel(CoBaseServePromiseModel pomodel) {
		this.pomodel = pomodel;
	}

	public Date getClientshowdate() {
		return clientshowdate;
	}

	public void setClientshowdate(Date clientshowdate) {
		this.clientshowdate = clientshowdate;
	}

}
