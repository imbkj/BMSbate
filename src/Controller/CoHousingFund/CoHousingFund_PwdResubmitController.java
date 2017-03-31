package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.CoHousingFund.CoHousingFund_PwdBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Util.SingleBllFactory;

public class CoHousingFund_PwdResubmitController {

	private CoHousingFundPwdChangeModel cfpc;
	private String addYearlimit;
	private String currentime;
	private String addRemark;
	private String yName;
	private String yNumber;
	private String updateRemark;
	private String renewalYearLimit;
	private String renewalRemark;

	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();

	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private Map<String, CoHousingFundPwdChangeModel> map = (Map<String, CoHousingFundPwdChangeModel>) Executions
			.getCurrent().getArg();
	Set<String> keySet = map.keySet();
	Iterator it = keySet.iterator();

	public CoHousingFund_PwdResubmitController() {
		String key = null;
		while (it.hasNext()) {
			key = (String) it.next();
			if (key.equals("info")) {
				cfpc = map.get(key);
			}
		}

		// 赋值
		currentime = new SimpleDateFormat("yyyyMM").format(System
				.currentTimeMillis());
		addYearlimit = cfpc.getCfpc_yearlimit().toString();
		addRemark = cfpc.getCfpc_remark();
		yName = cfpc.getCfpc_zb_name();
		yNumber = cfpc.getCfpc_zb_number();
		updateRemark = cfpc.getCfpc_remark();
		renewalYearLimit = cfpc.getCfpc_yearlimit().toString();
		renewalRemark = cfpc.getCfpc_remark();
	}

	/**
	 * oncreate
	 */
	@Command
	public void setInfo(@BindingParam("addcheck") Checkbox addc,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("rebewalcheck") Checkbox rebewalc,
			@BindingParam("addYearlimit") Combobox addYearlimit,
			@BindingParam("enewalYearLimit") Combobox enewalYearLimit,
			@BindingParam("addRow") Row addRow,
			@BindingParam("updateRow") Row updateRow,
			@BindingParam("showRenewalRow") Row showRenewalRow,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2) {
		if (cfpc.getCfpc_addtype().equals("申请数字证书")) {
			addRow.setVisible(true);
			updateRow.setVisible(false);
			showRenewalRow.setVisible(false);
			addc.setChecked(true);
			updatec.setDisabled(true);
			rebewalc.setDisabled(true);
			gd.setVisible(true);
			gd1.setVisible(false);
			gd2.setVisible(false);
		} else if (cfpc.getCfpc_addtype().equals("密钥专办员变更")) {
			addRow.setVisible(false);
			updateRow.setVisible(true);
			showRenewalRow.setVisible(false);
			updatec.setChecked(true);
			addc.setDisabled(true);
			rebewalc.setDisabled(true);
			gd.setVisible(false);
			gd1.setVisible(false);
			gd2.setVisible(true);
		} else if (cfpc.getCfpc_addtype().equals("数字证书续期")) {
			addRow.setVisible(false);
			updateRow.setVisible(false);
			showRenewalRow.setVisible(true);
			rebewalc.setChecked(true);
			updatec.setDisabled(true);
			addc.setDisabled(true);
			gd.setVisible(false);
			gd1.setVisible(true);
			gd2.setVisible(false);
		}
	}

	@Command
	@NotifyChange("chfmList")
	public void submit(@BindingParam("addcheck") Checkbox addc,
			@BindingParam("rebewalcheck") Checkbox rebewalcheck,
			@BindingParam("updatecheck") Checkbox updatecheck,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2, @BindingParam("win") Window win) {
		CoHousingFundPwdChangeModel cfpm = new CoHousingFundPwdChangeModel();
		cfpm.setCfpc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());

		cfpm.setCfpc_id(cfpc.getCfpc_id());
		cfpm.setCfpc_state(1);
		try {
			if (addc.isChecked()) {
				cfpm.setCfpc_remark(addRemark);
				String[] message = docOC.UpchkHaveTo(gd);
				if (message[0] == "1") {
					int row = cfpb.resubmitPwdChange(cfpm);
					if (row != 0) {
						docOC.UpsubmitDoc(gd, cfpc.getCfpc_id().toString());
						Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				}

			} else if (rebewalcheck.isChecked()) {
				cfpm.setCfpc_remark(renewalRemark);
				String[] message = docOC.UpchkHaveTo(gd1);
				if (message[0] == "1") {
					int row = cfpb.resubmitPwdChange(cfpm);
					if (row != 0) {
						docOC.UpsubmitDoc(gd1, cfpc.getCfpc_id().toString());
						Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				}
			} else if (updatecheck.isChecked()) {
				cfpm.setCfpc_remark(updateRemark);
				String[] message = docOC.UpchkHaveTo(gd2);
				if (message[0] == "1") {
					int row = cfpb.resubmitPwdChange(cfpm);
					if (row != 0) {
						docOC.UpsubmitDoc(gd2, cfpc.getCfpc_id().toString());
						Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				}

			} else {
				Messagebox.show("操作失败", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CoHousingFundPwdChangeModel getCfpc() {
		return cfpc;
	}

	public void setCfpc(CoHousingFundPwdChangeModel cfpc) {
		this.cfpc = cfpc;
	}

	public String getAddYearlimit() {
		return addYearlimit;
	}

	public void setAddYearlimit(String addYearlimit) {
		this.addYearlimit = addYearlimit;
	}

	public String getCurrentime() {
		return currentime;
	}

	public void setCurrentime(String currentime) {
		this.currentime = currentime;
	}

	public String getAddRemark() {
		return addRemark;
	}

	public void setAddRemark(String addRemark) {
		this.addRemark = addRemark;
	}

	public String getyName() {
		return yName;
	}

	public void setyName(String yName) {
		this.yName = yName;
	}

	public String getyNumber() {
		return yNumber;
	}

	public void setyNumber(String yNumber) {
		this.yNumber = yNumber;
	}

	public String getUpdateRemark() {
		return updateRemark;
	}

	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark;
	}

	public String getRenewalYearLimit() {
		return renewalYearLimit;
	}

	public void setRenewalYearLimit(String renewalYearLimit) {
		this.renewalYearLimit = renewalYearLimit;
	}

	public String getRenewalRemark() {
		return renewalRemark;
	}

	public void setRenewalRemark(String renewalRemark) {
		this.renewalRemark = renewalRemark;
	}

}
