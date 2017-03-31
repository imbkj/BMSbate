package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
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

import bll.CoHousingFund.CoHousingFundZbBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;

public class CoHousingFund_ZbResubmit {

	private CoHousingFundZbChangeModel cfzc;
	private String houseid;
	private String company;
	private String currentime;
	private String addRemark;
	private String updateRemark;
	private String delRemark;
	private String zbNameAndNumber;
	private CoHousingFundZbModel chfz;
	private int zbid;

	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private Map<String, CoHousingFundZbChangeModel> map = (Map<String, CoHousingFundZbChangeModel>) Executions
			.getCurrent().getArg();
	Set<String> keySet = map.keySet();
	Iterator it = keySet.iterator();

	public CoHousingFund_ZbResubmit() {
		String key = null;
		while (it.hasNext()) {
			key = (String) it.next();
			if (key.equals("info")) {
				cfzc = map.get(key);
			}
		}
		 chfz = cfzb.getCoHousingFundZbById(cfzc
				.getCfzc_chfz_id());
		if (cfzc.getCfzc_chfz_id() != 0) {
			zbid = cfzc.getCfzc_chfz_id();
			zbNameAndNumber = chfz.getChfz_name() + chfz.getChfz_number();
		}
		currentime = new SimpleDateFormat("yyyyMM").format(System
				.currentTimeMillis());
		houseid = cfzc.getCfzc_cohf_houseid();
		company = cfzc.getCfzc_cohf_company();
		// 根据专办员号查询专办员信息,主要用在专办员修改
		addRemark = cfzc.getCfzc_remark();
		updateRemark = cfzc.getCfzc_remark();
		delRemark = cfzc.getCfzc_remark();
	}

	/**
	 * oncreate
	 */
	@Command
	public void setInfo(@BindingParam("addcheck") Checkbox addc,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("delcheck") Checkbox delc,
			@BindingParam("zbcbox") Combobox zbcbox,
			@BindingParam("addRow") Row addRow,
			@BindingParam("updateRow") Row updateRow,
			@BindingParam("delRow") Row delRow, @BindingParam("gd") Grid gd,
			@BindingParam("gd1") Grid gd1, @BindingParam("gd2") Grid gd2) {
		if (cfzc.getCfzc_addtype().equals("新增专办员")) {
			addRow.setVisible(true);
			updateRow.setVisible(false);
			delRow.setVisible(false);
			addc.setChecked(true);
			updatec.setDisabled(true);
			delc.setDisabled(true);
			gd1.setVisible(false);
			gd.setVisible(true);
			gd2.setVisible(false);
		} else if (cfzc.getCfzc_addtype().equals("修改专办员信息")) {
			System.out.println("name=" + cfzc.getCfzc_name());
			zbcbox.setValue(cfzc.getCfzc_name());
			addRow.setVisible(false);
			updateRow.setVisible(true);
			delRow.setVisible(false);
			updatec.setChecked(true);
			addc.setDisabled(true);
			delc.setDisabled(true);
			gd1.setVisible(true);
			gd.setVisible(false);
			gd2.setVisible(false);
		} else if (cfzc.getCfzc_addtype().equals("注销专办员")) {
			addRow.setVisible(false);
			updateRow.setVisible(false);
			delRow.setVisible(true);
			delc.setChecked(true);
			updatec.setDisabled(true);
			addc.setDisabled(true);
			gd1.setVisible(false);
			gd.setVisible(false);
			gd2.setVisible(true);
		}
	}

	@Command
	@NotifyChange("chfmList")
	public void submit(@BindingParam("addcheck") Checkbox addc,
			@BindingParam("delcheck") Checkbox delc,
			@BindingParam("updatecheck") Checkbox updatecheck,
			@BindingParam("gd") Grid gd, @BindingParam("win") Window win,
			@BindingParam("gd1") Grid gd1, @BindingParam("gd2") Grid gd2) {
		CoHousingFundZbChangeModel cfzc = new CoHousingFundZbChangeModel();
		cfzc.setOwnmonth(Integer.valueOf(currentime));
		cfzc.setCfzc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		cfzc.setCfzc_id(this.cfzc.getCfzc_id());
		cfzc.setCfzc_state(1);
		try {
			int cfzc_id = 0;
			if (addc.isChecked()) {
				zbid = 0;
				cfzc.setCfzc_remark(addRemark);
				String[] message = docOC.UpchkHaveTo(gd);
				if (message[0] == "1") {
					boolean flag = cfzb.resubmitZb(cfzc);
					if (flag) {
						docOC.UpsubmitDoc(gd,
								String.valueOf(this.cfzc.getCfzc_id()));
						Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				}
			} else if (delc.isChecked()) {
				zbid = 0;
				cfzc.setCfzc_remark(delRemark);
				String[] message = docOC.UpchkHaveTo(gd2);
				if (message[0] == "1") {
					boolean flag = cfzb.resubmitZb(cfzc);
					if (flag) {
						docOC.UpsubmitDoc(gd2,
								String.valueOf(this.cfzc.getCfzc_id()));
						Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				} else {
					Messagebox.show("材料未齐全", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else if (updatecheck.isChecked() && zbid != 0) {
				// cfzc.setCfzc_chfz_id(zbid);
				// cfzc.setCfzc_addtype("修改专办员信息");
				cfzc.setCfzc_remark(updateRemark);

				String[] message = docOC.UpchkHaveTo(gd1);
				if (message[0] == "1") {
					boolean flag = cfzb.resubmitZb(cfzc);
					if (flag) {
						docOC.UpsubmitDoc(gd1,
								String.valueOf(this.cfzc.getCfzc_id()));
						Messagebox.show("添加成功!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				} else {
					Messagebox.show("材料未齐全", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}
			} else {
				Messagebox.show("操作失败", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public CoHousingFundZbModel getChfz() {
		return chfz;
	}

	public void setChfz(CoHousingFundZbModel chfz) {
		this.chfz = chfz;
	}

	public CoHousingFundZbChangeModel getCfzc() {
		return cfzc;
	}

	public void setCfzc(CoHousingFundZbChangeModel cfzc) {
		this.cfzc = cfzc;
	}

	public String getZbNameAndNumber() {
		return zbNameAndNumber;
	}

	public void setZbNameAndNumber(String zbNameAndNumber) {
		this.zbNameAndNumber = zbNameAndNumber;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getUpdateRemark() {
		return updateRemark;
	}

	public void setUpdateRemark(String updateRemark) {
		this.updateRemark = updateRemark;
	}

	public String getDelRemark() {
		return delRemark;
	}

	public void setDelRemark(String delRemark) {
		this.delRemark = delRemark;
	}

	public Map<String, CoHousingFundZbChangeModel> getMap() {
		return map;
	}

	public void setMap(Map<String, CoHousingFundZbChangeModel> map) {
		this.map = map;
	}

}
