package Controller.CoBase;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoLatencyClient.CoFinanceVatBll;
import bll.SystemControl.SystLogInfoBll;

import Model.CoBaseModel;
import Model.CoFinanceVATModel;
import Model.SystLogModel;
import Util.EntitiesComparedUtils;
import Util.GetIpUtil;
import Util.UserInfo;

public class CoBase_invoiceController {

	private CoFinanceVATModel cfm = new CoFinanceVATModel();// 修改数据
	private CoFinanceVATModel cfm2 = new CoFinanceVATModel();// 历史数据
	private CoBaseModel baseModel = new CoBaseModel();
	private CoFinanceVatBll vatBll = new CoFinanceVatBll();
	private SystLogInfoBll logBll = new SystLogInfoBll();

	private boolean only = false;
	private boolean simple = false;

	public CoBase_invoiceController() throws Exception {
		if (Executions.getCurrent().getArg().get("model") != null) {
			baseModel = (CoBaseModel) Executions.getCurrent().getArg()
					.get("model");

			List<CoFinanceVATModel> list = vatBll.getCoFinanceVatDat(baseModel
					.getCid());
			if (list.size() > 0) {
				cfm = list.get(0);
				cfm2 = (CoFinanceVATModel) cfm.clone();
			}
			if (cfm.getCid() == null) {
				cfm.setCid(baseModel.getCid());
				cfm.setCfva_company(baseModel.getCoba_company());

			}

		}
	}

	@Command
	@NotifyChange({"cfm","only","simple"})
	public void modinfo() {
		if (cfm.getCfva_taxpayers()!=null) {
			if (cfm.getCfva_taxpayers().equals("是")) {
				only=false;
				simple=false;
				cfm.setCfva_only(true);
				cfm.setCfva_simple(true);
			}else if (cfm.getCfva_taxpayers().equals("否")) {
				only=true;
				simple=false;
				cfm.setCfva_only(false);
				cfm.setCfva_simple(true);
			}else {
				only=false;
				simple=false;
				cfm.setCfva_only(false);
				cfm.setCfva_simple(false);
			}
		}
	}

	@Command
	public void submit() {

		if (cfm.getCfva_reg_add() == null || cfm.getCfva_reg_add().equals("")) {
			Messagebox.show("请输入注册地址", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = 0;
							if (cfm.getCfva_id() != null
									&& !cfm.getCfva_id().equals("")) {
								i = vatBll.updateCoFinanceVat(cfm);
							} else {
								cfm.setCfva_addname(UserInfo.getUsername());
								i = vatBll.addCoFinanceVat(cfm);
							}
							if (i > 0) {

								HttpServletRequest request = (HttpServletRequest) Executions
										.getCurrent().getNativeRequest();
								String IP = GetIpUtil.getIpAddr(request);
								List<SystLogModel> list = EntitiesComparedUtils
										.compareModel(cfm2, cfm,
												CoFinanceVATModel.class);
								for (SystLogModel m : list) {
									m.setIP(IP);
									m.setCid(baseModel.getCid().toString());
									m.setGID("0");
									m.setClass1("发票信息");
									m.setAddname(UserInfo.getUsername());
									logBll.addSystLog(m);
								}
								Messagebox.show("修改成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show("修改失败.", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public CoFinanceVATModel getCfm() {
		return cfm;
	}

	public void setCfm(CoFinanceVATModel cfm) {
		this.cfm = cfm;
	}

	public boolean isOnly() {
		return only;
	}

	public void setOnly(boolean only) {
		this.only = only;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

}
