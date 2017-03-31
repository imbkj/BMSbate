package Controller.EmBenefit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.poi.poifs.crypt.EcmaDecryptor;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmBenefit.EmActy_compactModBll;

import Model.EmActyCompactModel;
import Model.EmActySupProductInfoModel;
import Util.UserInfo;

public class EmActy_compactModController {
	private List<EmActyCompactModel> list = new ListModelList<>();
	private List<EmActyCompactModel> compactlist = new ListModelList<>();
	private List<EmActySupProductInfoModel> prodList = new ListModelList<>();
	private EmActyCompactModel eacm = new EmActyCompactModel();
	private EmActySupProductInfoModel easm = new EmActySupProductInfoModel();
	private EmActy_compactModBll bll = new EmActy_compactModBll();
	private Window win = (Window) Path.getComponent("/wincompactMod");

	private Integer eadaId = 0;
	private Integer tapr_id = 0;
	private String username = UserInfo.getUsername();

	private Date signdate;
	private Date inuredate;
	private Date stopdate;

	public EmActy_compactModController() {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			eadaId = Integer.valueOf(Executions.getCurrent().getArg()
					.get("daid").toString());
		}
		if (Executions.getCurrent().getArg().get("id") != null) {
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		eacm = bll.getlist(eadaId).get(0);
		easm.setProd_eaco_id(eadaId);
		easm.setProd_supid(eacm.getEaco_supp_id());
		easm.setProd_modname(username);
		easm.setBstate(true);
		setProdList(easm);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			if (eacm.getEaco_signdate() != null) {
				signdate = sdf.parse(eacm.getEaco_signdate());
			}
			if (eacm.getEaco_inuredate() != null) {
				inuredate = sdf.parse(eacm.getEaco_inuredate());
			}
			if (eacm.getEaco_stopdate() != null) {
				stopdate = sdf.parse(eacm.getEaco_stopdate());
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Command("delProd")
	@NotifyChange("prodList")
	public void delProd(@BindingParam("a") final EmActySupProductInfoModel eapm) {
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							easm.setProd_eaco_id(0);
							easm.setProd_id(eapm.getProd_id());
							if (bll.modProd(easm) > 0) {
								Messagebox.show("操作成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								setProdList(easm);
							} else {
								Messagebox.show("操作失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});

	}

	@Command("submit")
	public void submit() {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd");

							Datebox db1 = (Datebox) win.getFellow("signdate");// 签订日期
							Datebox db2 = (Datebox) win.getFellow("inuredate");// 生效日期
							Datebox db3 = (Datebox) win.getFellow("stopdate");// 终止日期

							if (db1.getValue() != null) {
								eacm.setEaco_signdate(sdf.format(db1.getValue()));
							}
							if (db2.getValue() != null) {
								eacm.setEaco_inuredate(sdf.format(db2
										.getValue()));
							}
							if (db3.getValue() != null) {
								eacm.setEaco_stopdate(sdf.format(db3.getValue()));
							}
							if (eacm.getEaco_auto2() != null) {
								eacm.setEaco_auto(eacm.getEaco_auto2().equals(
										"是") ? 1 : 0);
							}

							if (eacm.getEaco_together2() != null) {
								eacm.setEaco_together(eacm.getEaco_together2()
										.equals("已合作") ? 1 : 0);
							}

							if (eacm.getEaco_compactid() != null
									&& !eacm.getEaco_compactid().equals("")) {

								Integer i = bll.mod(eacm);
								if (i > 0) {
									Messagebox.show("操作成功", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();

								} else {
									Messagebox.show("操作失败", "操作提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							} else {
								Messagebox.show("请输入合同号", "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}

						}
					}
				});
	}

	public EmActyCompactModel getEacm() {
		return eacm;
	}

	public void setEacm(EmActyCompactModel eacm) {
		this.eacm = eacm;
	}

	public List<EmActyCompactModel> getCompactlist() {
		return compactlist;
	}

	public void setCompactlist(String name, boolean type) {
		this.compactlist = bll.getCompactList(name, type);
	}

	public Date getSigndate() {
		return signdate;
	}

	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	public Date getInuredate() {
		return inuredate;
	}

	public void setInuredate(Date inuredate) {
		this.inuredate = inuredate;
	}

	public Date getStopdate() {
		return stopdate;
	}

	public void setStopdate(Date stopdate) {
		this.stopdate = stopdate;
	}

	public List<EmActySupProductInfoModel> getProdList() {
		return prodList;
	}

	public void setProdList(EmActySupProductInfoModel easm) {
		this.prodList = bll.getProdList(easm);
	}

}
