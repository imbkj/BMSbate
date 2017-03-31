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
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import com.sun.security.auth.NTDomainPrincipal;

import bll.CoHousingFund.CoHousingFund_PwdBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundModel;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Util.SingleBllFactory;
import Util.SingletonCoHousingFundPwd;

public class CoHouse_PwdList1SerController {

	private String currentime;
	private String renewalRemark;
	private String addRemark;
	private String updateRemark;
	private int addLimit;
	private int renewalLimit;
	private String yName;
	private String yNumber;
	private int chfp_id;
	private String cid;
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private List<CoHousingFundPwdModel> pwdList;
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private CoHousingFundModel cohf;
	private Map<String, CoHousingFundModel> map = (Map<String, CoHousingFundModel>) Executions
			.getCurrent().getArg();
	Set<String> keySet = map.keySet();
	Iterator it = keySet.iterator();

	public CoHouse_PwdList1SerController() {
		while (it.hasNext()) {
			String key = (String) it.next();
			if (!"".equals(key) && key.equals("cohf")) {
				cohf = map.get(key);
			}
		}
		cid = cohf.getCid().toString();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		currentime = sdf.format(System.currentTimeMillis());

		pwdList = cfpb.getPwdById(Integer.valueOf(cohf.getCohf_id()));

	}

	@Command
	public void isHavePwd(@BindingParam("rebewalcheck") Checkbox rebewalc,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2) {
		// 如果不存在密钥，则不让变更和续期显示
		if (SingletonCoHousingFundPwd.getInstance().getChpi()
				.allPwd(cohf.getCohf_id()).size() == 0) {
			rebewalc.setDisabled(true);
			updatec.setDisabled(true);
			gd2.setVisible(false);
			gd1.setVisible(false);
			gd.setVisible(true);
		} else {
			gd2.setVisible(false);
			gd1.setVisible(false);
			gd.setVisible(true);
		}
	}

	@Command
	public void yearLimit(@BindingParam("addLimit") int addLimit) {
		if (addLimit != 0) {
			this.addLimit = addLimit;
		}
	}

	@Command
	public void yearLimit2(@BindingParam("renewalLimit") int renewalLimit) {
		if (renewalLimit != 0) {
			this.renewalLimit = renewalLimit;
		}

	}

	@Command
	public void getChfp_id(@BindingParam("chfp_id") int chfp_id) {
		if (chfp_id != 0) {
			this.chfp_id = chfp_id;
		}
	}

	@Command
	public void showApply(@BindingParam("addRow") Row addrow,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("showRenewalRow") Row showRenewalRow,
			@BindingParam("rebewalcheck") Checkbox renewalc,
			@BindingParam("updateRow") Row updaterow,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2) {
		if (addc != null && addc.isChecked()) {
			addrow.setVisible(true);
			renewalc.setChecked(false);
			showRenewalRow.setVisible(false);
			updaterow.setVisible(false);
			updatec.setChecked(false);
			gd2.setVisible(false);
			gd1.setVisible(false);
			gd.setVisible(true);
		} else {
			addrow.setVisible(false);
		}
	}

	@Command
	public void showRenewal(@BindingParam("addRow") Row addRow,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("showRenewalRow") Row showRenewalRow,
			@BindingParam("rebewalcheck") Checkbox renewalc,
			@BindingParam("updateRow") Row updateRow,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2) {
		if (renewalc != null && renewalc.isChecked()) {
			showRenewalRow.setVisible(true);
			addRow.setVisible(false);
			addc.setChecked(false);
			updateRow.setVisible(false);
			updatec.setChecked(false);
			gd2.setVisible(false);
			gd1.setVisible(true);
			gd.setVisible(false);
		} else {
			showRenewalRow.setVisible(false);
		}
	}

	@Command
	public void showUpdate(@BindingParam("addRow") Row addRow,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("showRenewalRow") Row showRenewalRow,
			@BindingParam("rebewalcheck") Checkbox renewalc,
			@BindingParam("updateRow") Row updateRow,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2) {
		if (updatec != null && updatec.isChecked()) {
			updateRow.setVisible(true);
			addRow.setVisible(false);
			renewalc.setChecked(false);
			showRenewalRow.setVisible(false);
			addc.setChecked(false);
			gd2.setVisible(true);
			gd1.setVisible(false);
			gd.setVisible(false);
		} else {
			updateRow.setVisible(false);
		}
	}

	@Command
	@NotifyChange("chfmList")
	public void submit(@BindingParam("addcheck") Checkbox addc,
			@BindingParam("rebewalcheck") Checkbox rebewalc,
			@BindingParam("updatecheck") Checkbox updatecheck,
			@BindingParam("gd") final Grid gd,
			@BindingParam("gd1") final Grid gd1,
			@BindingParam("gd2") final Grid gd2,
			@BindingParam("win") final Window win) {

		try {

			if (addc.isChecked()) {
				final CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
				if (addLimit != 0) {
					// 选中新增密钥
					cfpc.setCfpc_yearlimit(addLimit);
					cfpc.setCfpc_chfp_id(0);
					cfpc.setCfpc_addtype("申请数字证书");
					cfpc.setCfpc_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());
					cfpc.setOwnmonth(Integer.valueOf(currentime));
					cfpc.setCfpc_cohf_id(cohf.getCohf_id());
					cfpc.setCfpc_state(1);
					cfpc.setCfpc_remark(addRemark);
					if (addLimit != 0) {
						String[] message = docOC.AddchkHaveTo(gd);
						if (message[0] == "1") {
							EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
								int cfpc_id = -1;

								public void onEvent(ClickEvent event)
										throws Exception {
									if (Messagebox.Button.YES.equals(event
											.getButton())) {
										cfpc_id = cfpb.addPwdChange(cfpc, null);
										if (cfpc_id != -1) {
											docOC.AddsubmitDoc(gd,
													String.valueOf(cfpc_id));
											Messagebox.show("添加成功!", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
											win.detach();
										}
									}
								}
							};
							Messagebox.show("确认提交资料？", null,
									new Messagebox.Button[] {
											Messagebox.Button.YES,
											Messagebox.Button.NO },
									Messagebox.QUESTION, clickListener);
						}
					}
				} else {
					Messagebox.show("请选择办理年限");
				}
			} else if (rebewalc.isChecked()) {
				final CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
				if (renewalLimit != 0) {
					// 续期
					cfpc.setCfpc_chfp_id(0);
					cfpc.setCfpc_yearlimit(renewalLimit);
					cfpc.setCfpc_addtype("数字证书续期");
					cfpc.setCfpc_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());
					cfpc.setOwnmonth(Integer.valueOf(currentime));
					cfpc.setCfpc_cohf_id(cohf.getCohf_id());
					cfpc.setCfpc_state(1);
					cfpc.setCfpc_remark(renewalRemark);
					if (renewalLimit != 0) {
						String[] message = docOC.AddchkHaveTo(gd1);
						if (message[0] == "1") {
							EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
								int cfpc_id = -1;

								public void onEvent(ClickEvent event)
										throws Exception {
									if (Messagebox.Button.YES.equals(event
											.getButton())) {
										cfpc_id = cfpb.addPwdChange(cfpc, null);
										if (cfpc_id != -1) {
											docOC.AddsubmitDoc(gd1,
													String.valueOf(cfpc_id));
											Messagebox.show("添加成功!", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
											win.detach();
										}
									}
								}
							};
							Messagebox.show("确认提交资料？", null,
									new Messagebox.Button[] {
											Messagebox.Button.YES,
											Messagebox.Button.NO },
									Messagebox.QUESTION, clickListener);

						}
					}
				} else {
					Messagebox.show("请选择办理年限");
				}
			} else if (updatecheck.isChecked()) {
				final CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
				for (int i = 0; i < pwdList.size(); i++) {
					if (pwdList.get(i).getChfp_id() == chfp_id) {
						cfpc.setCfpc_zb_name(pwdList.get(i).getChfp_zb_name());
						cfpc.setCfpc_zb_number(pwdList.get(i)
								.getChfp_zb_number());
					}
				}
				if (chfp_id != 0) {
					// 密钥变更
					cfpc.setCfpc_chfp_id(chfp_id);
					cfpc.setCfpc_addtype("密钥专办员变更");
					cfpc.setCfpc_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());
					cfpc.setOwnmonth(Integer.valueOf(currentime));
					cfpc.setCfpc_cohf_id(cohf.getCohf_id());
					cfpc.setCfpc_state(1);
					cfpc.setCfpc_remark(updateRemark);
					cfpc.setCfpc_yearlimit(0);
					String[] message = docOC.AddchkHaveTo(gd2);
					if (message[0] == "1") {
						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							int cfpc_id = -1;

							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.YES.equals(event
										.getButton())) {
									cfpc_id = cfpb.addPwdChange(cfpc, "密钥变更");
									if (cfpc_id != -1) {
										docOC.AddsubmitDoc(gd2,
												String.valueOf(cfpc_id));
										Messagebox.show("添加成功!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										win.detach();
									}
								}
							}
						};
						Messagebox.show("确认提交资料？", null,
								new Messagebox.Button[] {
										Messagebox.Button.YES,
										Messagebox.Button.NO },
								Messagebox.QUESTION, clickListener);
					}
				} else {
					Messagebox.show("请选择要变更的专办员");
				}

			} else {
				Messagebox.show("操作失败");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public List<CoHousingFundPwdModel> getPwdList() {
		return pwdList;
	}

	public void setPwdList(List<CoHousingFundPwdModel> pwdList) {
		this.pwdList = pwdList;
	}

	public int getAddLimit() {
		return addLimit;
	}

	public void setAddLimit(int addLimit) {
		this.addLimit = addLimit;
	}

	public int getRenewalLimit() {
		return renewalLimit;
	}

	public void setRenewalLimit(int renewalLimit) {
		this.renewalLimit = renewalLimit;
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

	public String getRenewalRemark() {
		return renewalRemark;
	}

	public void setRenewalRemark(String renewalRemark) {
		this.renewalRemark = renewalRemark;
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

	public String getCurrentime() {
		return currentime;
	}

	public void setCurrentime(String currentime) {
		this.currentime = currentime;
	}

}
