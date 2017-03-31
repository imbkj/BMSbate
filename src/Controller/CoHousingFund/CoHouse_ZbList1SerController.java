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
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFundZbBll;
import bll.CoHousingFund.CoHousingFund_PwdBll;

/**
 * 前到业务页面控制层
 * 
 * @author Administrator
 * 
 */
public class CoHouse_ZbList1SerController {
	private String addRemark;
	private String updateRemark;
	private String delRemark;
	private String company;
	private String houseid;
	private String cohf_id;
	private String currentime;
	private int cid;
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private List<CoHousingFundZbModel> zbListBycohf_id;
	private int zbid;
	private String status;
	private String addtype;
	private CoHousingFundZbChangeModel cfzc;
	private int addLimit;

	private Map<String, String> map = (Map<String, String>) Executions
			.getCurrent().getArg();
	Set<String> keySet = map.keySet();
	Iterator it = keySet.iterator();

	public CoHouse_ZbList1SerController() {
		String key = null;
		while (it.hasNext()) {
			key = (String) it.next();
			if (!"".equals(key) && key.equals("houseid")) {
				houseid = map.get(key);
			} else if (!"".equals(key) && key.equals("company")) {
				company = map.get(key);
			} else if (!"".equals(key) && key.equals("cohf_id")) {
				// 查询有没有关联到的专办员
				cohf_id = map.get(key);
			} else if (!"".equals(key) && key.equals("status")) {
				status = map.get(key);
			} else if (!"".equals(key) && key.equals("cid")) {
				cid = Integer.valueOf(map.get(key));
			}
		}

		cfzc = cfzb.addtype(Integer.valueOf(cohf_id));

		zbListBycohf_id = cfzb.getZbListBycohf_id(Integer.valueOf(cohf_id));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		currentime = sdf.format(System.currentTimeMillis());

	}

	@Command
	public void getZbid(@BindingParam("zbid") int zbid) {
		this.zbid = zbid;
	}

	@Command
	public void isapplypwd(@BindingParam("checkid") Checkbox c,
			@BindingParam("gd4") Groupbox g) {
		if (c.isChecked()) {
			g.setVisible(true);
		} else {
			g.setVisible(false);
		}
	}

	@Command
	public void yearLimit(@BindingParam("addLimit") int addLimit) {
		if (addLimit != 0) {
			this.addLimit = addLimit;
		}
	}

	@Command
	public void isHaveZb(@BindingParam("delcheck") Checkbox delc,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("resubmit") Button resubmit,
			@BindingParam("submit") Button submit, @BindingParam("gd") Grid gd,
			@BindingParam("gd1") Grid gd1, @BindingParam("gd2") Grid gd2) {

		// 如果存在专办员，就让其他两个checkbox可以勾选，并且查询出专办员
		// delc.setDisabled(false);
		// System.out.println(cfzc.getCfzc_remark());
		// updatec.setDisabled(false);
		// addremark.setValue(cfzc.getCfzc_remark());
		if (!"".equals(addtype) && addtype != null && addtype.equals("修改专办员信息")) {
			gd1.setVisible(true);
			gd.setVisible(false);
			gd2.setVisible(false);
		} else if (!"".equals(addtype) && addtype != null
				&& addtype.equals("注销专办员")) {
			gd1.setVisible(false);
			gd.setVisible(false);
			gd2.setVisible(true);
		}
		if (cfzb.isHaveZb(cohf_id)) {
			gd1.setVisible(false);
			gd.setVisible(true);
			gd2.setVisible(false);
		} else {
			delc.setDisabled(true);
			updatec.setDisabled(true);
			gd1.setVisible(false);
			gd.setVisible(true);
			gd2.setVisible(false);
		}

	}

	@Command
	public void showCell(@BindingParam("addRow") Row addrow,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("delRow") Row delrow,
			@BindingParam("delcheck") Checkbox delc,
			@BindingParam("updateRow") Row updaterow,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2, @BindingParam("gd4") Groupbox g,
			@BindingParam("checkid") Checkbox c) {
		if (addc != null && addc.isChecked()) {
			addrow.setVisible(true);
			delc.setChecked(false);
			updaterow.setVisible(false);
			updatec.setChecked(false);
			delrow.setVisible(false);
			gd2.setVisible(false);
			gd1.setVisible(false);
			gd.setVisible(true);
			if (c.isChecked()) {
				g.setVisible(true);
			}

		} else {
			addrow.setVisible(false);
			gd.setVisible(false);
		}
	}

	@Command
	public void showDel(@BindingParam("addRow") Row addrow,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("delRow") Row delrow,
			@BindingParam("delcheck") Checkbox delc,
			@BindingParam("updateRow") Row updaterow,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2, @BindingParam("gd4") Groupbox g) {
		if (delc != null && delc.isChecked()) {
			delrow.setVisible(true);
			addc.setChecked(false);
			addrow.setVisible(false);
			updatec.setChecked(false);
			updaterow.setVisible(false);
			gd2.setVisible(true);
			gd1.setVisible(false);
			gd.setVisible(false);
			g.setVisible(false);
		} else {
			delrow.setVisible(false);
			gd2.setVisible(false);
		}
	}

	@Command
	public void showUpdate(@BindingParam("addRow") Row addrow,
			@BindingParam("addcheck") Checkbox addc,
			@BindingParam("delRow") Row delrow,
			@BindingParam("delcheck") Checkbox delc,
			@BindingParam("updateRow") Row updaterow,
			@BindingParam("updatecheck") Checkbox updatec,
			@BindingParam("gd") Grid gd, @BindingParam("gd1") Grid gd1,
			@BindingParam("gd2") Grid gd2, @BindingParam("gd4") Groupbox g) {
		if (updatec != null && updatec.isChecked()) {
			updaterow.setVisible(true);
			addc.setChecked(false);
			addrow.setVisible(false);
			delc.setChecked(false);
			delrow.setVisible(false);
			gd2.setVisible(false);
			gd1.setVisible(true);
			gd.setVisible(false);
			g.setVisible(false);
		} else {
			updaterow.setVisible(false);
			gd1.setVisible(false);
		}
	}

	@Command
	@NotifyChange("chfmList")
	public void submit(@BindingParam("addcheck") Checkbox addc,
			@BindingParam("delcheck") Checkbox delc,
			@BindingParam("updatecheck") Checkbox updatecheck,
			@BindingParam("gd") final Grid gd,
			@BindingParam("win") final Window win,
			@BindingParam("gd1") final Grid gd1,
			@BindingParam("gd2") final Grid gd2,
			@BindingParam("gdgd") final Grid gd3,
			@BindingParam("checkid") final Checkbox c) {
		final CoHousingFundZbChangeModel cfzc = new CoHousingFundZbChangeModel();
		cfzc.setOwnmonth(Integer.valueOf(currentime));
		cfzc.setCfzc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		cfzc.setCfzc_cohf_id(Integer.valueOf(cohf_id));
		cfzc.setCfzc_state(1);
		try {

			if (addc.isChecked()) {
				if (!c.isChecked()) {
					zbid = 0;
					cfzc.setCfzc_addtype("新增专办员");
					cfzc.setCfzc_remark(addRemark);
					cfzc.setCfzc_chfz_id(zbid);
					String[] message = docOC.AddchkHaveTo(gd);
					if (message[0] == "1") {
						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							int cfzc_id = -1;

							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.YES.equals(event
										.getButton())) {
									cfzc_id = cfzb.addZbChange(cfzc);
									if (cfzc_id != -1) {
										docOC.AddsubmitDoc(gd,
												String.valueOf(cfzc_id));
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
					zbid = 0;
					cfzc.setCfzc_addtype("新增专办员");
					cfzc.setCfzc_remark(addRemark);
					cfzc.setCfzc_chfz_id(zbid);
					final CoHousingFundPwdChangeModel cfpc = new CoHousingFundPwdChangeModel();
					cfpc.setCfpc_yearlimit(addLimit);
					cfpc.setCfpc_chfp_id(0);
					cfpc.setCfpc_addtype("申请数字证书");
					cfpc.setCfpc_addname(Executions.getCurrent().getDesktop()
							.getSession().getAttribute("username").toString());
					cfpc.setOwnmonth(Integer.valueOf(currentime));
					cfpc.setCfpc_cohf_id(Integer.valueOf(cohf_id));
					cfpc.setCfpc_state(1);
					cfpc.setCfpc_remark(addRemark);
					String[] message = docOC.AddchkHaveTo(gd);
					String[] message1 = docOC.AddchkHaveTo(gd3);

					if (message[0] == "1" && message1[0].equals("1")) {
						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							int cfzc_id = -1;
							int cfpc_id = -1;

							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.YES.equals(event
										.getButton())) {
									cfpc_id = cfpb.addPwdChange(cfpc, null);
									cfzc.setCfzc_cfpc_id(cfpc_id);
									cfzc_id = cfzb.addZbChange(cfzc);

									if (cfzc_id != -1 && cfpc_id != -1) {
										docOC.AddsubmitDoc(gd3,
												String.valueOf(cfpc_id));
										docOC.AddsubmitDoc(gd,
												String.valueOf(cfzc_id));
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
			} else if (delc.isChecked()) {
				zbid = 0;
				cfzc.setCfzc_addtype("注销专办员");
				cfzc.setCfzc_remark(delRemark);
				cfzc.setCfzc_chfz_id(zbid);
				String[] message = docOC.AddchkHaveTo(gd2);
				if (message[0] == "1") {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						int cfzc_id = -1;

						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								cfzc_id = cfzb.addZbChange(cfzc);
								if (cfzc_id != -1) {
									docOC.AddsubmitDoc(gd2,
											String.valueOf(cfzc_id));
									Messagebox.show("添加成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();
								}
							}
						}
					};
					Messagebox.show("确认提交资料？", null, new Messagebox.Button[] {
							Messagebox.Button.YES, Messagebox.Button.NO },
							Messagebox.QUESTION, clickListener);

				} else {
					// Messagebox.show("材料未齐全", "操作提示", Messagebox.OK,
					// Messagebox.INFORMATION);
				}
			} else if (updatecheck.isChecked() && zbid != 0) {
				cfzc.setCfzc_chfz_id(zbid);
				cfzc.setCfzc_addtype("修改专办员信息");
				cfzc.setCfzc_remark(updateRemark);

				String[] message = docOC.AddchkHaveTo(gd1);
				if (message[0] == "1") {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						int cfzc_id = -1;

						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								cfzc_id = cfzb.addZbChange(cfzc);
								if (cfzc_id != -1) {
									docOC.AddsubmitDoc(gd1,
											String.valueOf(cfzc_id));
									Messagebox.show("添加成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();
								}
							}
						}
					};
					Messagebox.show("确认提交资料？", null, new Messagebox.Button[] {
							Messagebox.Button.YES, Messagebox.Button.NO },
							Messagebox.QUESTION, clickListener);

				} else {
					Messagebox.show("材料未齐全", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				}

			} else {
				Messagebox.show("请选择专办员,否则请右上角。。。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public List<CoHousingFundZbModel> getZbListBycohf_id() {
		return zbListBycohf_id;
	}

	public void setZbListBycohf_id(List<CoHousingFundZbModel> zbListBycohf_id) {
		this.zbListBycohf_id = zbListBycohf_id;
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

	public String getCurrentime() {
		return currentime;
	}

	public void setCurrentime(String currentime) {
		this.currentime = currentime;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getHouseid() {
		return houseid;
	}

	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}

}
