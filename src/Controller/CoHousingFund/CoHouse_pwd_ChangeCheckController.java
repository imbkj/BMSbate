package Controller.CoHousingFund;

import java.text.SimpleDateFormat;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFund_PwdBll;

/**
 * 密钥变更
 * 
 * @author Administrator
 * 
 */
public class CoHouse_pwd_ChangeCheckController {

	private CoHousingFundPwdChangeModel m = (CoHousingFundPwdChangeModel) Executions
			.getCurrent().getArg().get("cfpc");
	private List<CoHousingFundZbModel> notPwdZb;
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	// 构造器
	public CoHouse_pwd_ChangeCheckController() {
		notPwdZb = cfpb.notPwdZb(m.getCfpc_cohf_id());
		m.setNumberAndName(m.getCfpc_zb_name() + m.getCfpc_zb_number());

	}

	// 新密钥专办员combobox赋值
	@Command
	public void change(@BindingParam("m") CoHousingFundZbModel chfz) {
		// 拿到将变更的专办员 ，把专办员信息set到密钥
		m.setCfpc_chfz_id(chfz.getChfz_id());

	}

	// 未申报状态提交
	@Command
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("sbstate") final int sbstate,
			@BindingParam("m") CoHousingFundZbModel chfz) {
		m.setCfpc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		if (m.getCfpc_chfz_id() != 0) {
			try {
				// 先将专办员信息set到一个密钥对象
				final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
				cfpm.setChfp_zb_name(chfz.getChfz_name());
				cfpm.setChfp_zb_number(chfz.getChfz_number());
				cfpm.setChfp_chfz_id(chfz.getChfz_id());
				cfpm.setChfp_id(m.getCfpc_chfp_id());
				if (m.getCfpc_yearlimit() != null) {
					cfpm.setChfp_yearlimit(m.getCfpc_yearlimit());
				}
				if (m.getCfpc_startdate() != null) {
					cfpm.setChfp_startdate(new SimpleDateFormat().parse(m
							.getCfpc_startdate()));
				}
				if (m.getCfpc_enddate() != null) {
					cfpm.setChfp_enddate(new SimpleDateFormat().parse(m
							.getCfpc_enddate()));
				}

				String[] message = docOC.UpchkHaveTo(gd);
				if (message[0] == "1") {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								boolean flag = cfpb.changePwdc(cfpm,
										m.getCfpc_id(), sbstate); // 修改change表数据和状态
								if (flag) {
									docOC.UpsubmitDoc(gd, m.getCfpc_id() + "");
									if (sbstate != 3) {
										Messagebox.show("申报中!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
									}
								}
								if (sbstate == 3) {
									cfpb.changePwd(cfpm, m.getCfpc_chfp_id());
									Messagebox.show("已申报!", "操作提示",
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

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Messagebox.show("请选择要变更的专办员!", "error", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 申报中状态提交
	@Command
	public void shenbao(@BindingParam("wind") final Window win,
			@BindingParam("gd") final Grid gd) {
		m.setCfpc_state(3);
		m.setCfpc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		final CoHousingFundPwdModel cfpm = new CoHousingFundPwdModel();
		cfpm.setChfp_id(m.getCfpc_chfp_id());
		cfpm.setChfp_chfz_id(m.getCfpc_chfz_id());
		cfpm.setChfp_zb_name(m.getCfpc_zb_name());
		cfpm.setChfp_zb_number(m.getCfpc_zb_number());

		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					boolean flag = cfpb.changePwd(cfpm, m.getCfpc_id());
					if (flag) {
						cfpb.changeStatus(m); // 修改状态
						Messagebox.show("已申报!", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
						win.detach();
					}
				}
			}
		};
		Messagebox.show("确认提交资料？", null, new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);

	}

	public List<CoHousingFundZbModel> getNotPwdZb() {
		return notPwdZb;
	}

	public void setNotPwdZb(List<CoHousingFundZbModel> notPwdZb) {
		this.notPwdZb = notPwdZb;
	}

	public CoHousingFundPwdChangeModel getM() {
		return m;
	}

	public void setM(CoHousingFundPwdChangeModel m) {
		this.m = m;
	}

}
