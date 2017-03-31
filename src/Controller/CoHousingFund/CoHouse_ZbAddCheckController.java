package Controller.CoHousingFund;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundZbChangeModel;
import Util.EmailUtil;
import Util.SingleBllFactory;
import Util.TelUtil;
import bll.CoHousingFund.CoHousingFundZbBll;

public class CoHouse_ZbAddCheckController {

	private CoHousingFundZbChangeModel m = (CoHousingFundZbChangeModel) Executions
			.getCurrent().getArg().get("cfzc");
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	public CoHouse_ZbAddCheckController() {

	}

	/**
	 * 检查邮箱格式
	 * 
	 * @param email
	 */
	@Command
	public void checkEmailSimple() {
		try {
			if (m.getCfzc_mail() != null && !m.getCfzc_mail().isEmpty()) {
				if (!EmailUtil.checkEmailSimple(m.getCfzc_mail())) {
					Messagebox.show("邮箱格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					m.setCfzc_mail(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证手机号码
	 * 
	 */
	@Command
	public void checkMobile() {
		try {
			if (m.getCfzc_mobile() != null && !m.getCfzc_mobile().isEmpty()) {
				if (!TelUtil.isMobile(m.getCfzc_mobile())) {
					Messagebox.show("手机号码格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					m.setCfzc_mobile(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证固定电话
	 * 
	 */
	@Command
	public void checkPhone() {
		try {
			if (m.getCfzc_tel() != null && !m.getCfzc_tel().isEmpty()) {
				if (!TelUtil.isPhone(m.getCfzc_tel())) {
					Messagebox.show("家庭电话格式错误,请重新填写!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
					m.setCfzc_tel(null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 未申报状态提交申报
	 */
	@Command
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("sbstate") final int sbstate) {
		m.setCfzc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());

		try {
			String[] message = docOC.UpchkHaveTo(gd);
			if (message[0] == "1") {
				if (sbstate == 3) {

					if (m.getCfzc_name() == null || m.getCfzc_name().equals("")) {
						Messagebox.show("请输入专办员姓名！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					if (m.getCfzc_number() == null
							|| m.getCfzc_number().equals("")) {
						Messagebox.show("请输入专办员号！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					if (m.getCfzc_tel() == null || m.getCfzc_tel().equals("")) {
						Messagebox.show("请输入固定电话！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					if (m.getCfzc_mobile() == null
							|| m.getCfzc_mobile().equals("")) {
						Messagebox.show("请输入移动电话！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
					if (m.getCfzc_mail() == null || m.getCfzc_mail().equals("")) {
						Messagebox.show("请输入电子邮箱！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						return;
					}
				}
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.YES.equals(event.getButton())) {
							boolean flag = cfzb.addZbchange(m, sbstate);
							if (flag) {
								docOC.UpsubmitDoc(gd, m.getCfzc_id() + "");
								if (sbstate != 3) {
									Messagebox.show("申报中!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								}
							}
							if (flag && sbstate == 3) { // 如果选择的状态为3 则直接完成申报
								boolean flag2 = cfzb.addZb(m);
								if (flag2) {
									Messagebox.show("已申报!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									win.detach();
								}
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
	}

	// 申报中状态提交申报
	@Command
	public void shenbao(@BindingParam("rid") String r,
			@BindingParam("win") final Window win) {
		m.setCfzc_state(3);
		m.setCfzc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					// 状态3说明已申报
					boolean flag1 = cfzb.addZb(m);
					if (flag1) {
						boolean flag = cfzb.changeStatus(m);
						if (flag) {
							Messagebox.show("业务办理成功");
							win.detach();
						}
					}
				}
			}
		};
		Messagebox.show("确认完成申报？确认后将不能修改资料", null, new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);

	}

	public CoHousingFundZbChangeModel getM() {
		return m;
	}

	public void setM(CoHousingFundZbChangeModel m) {
		this.m = m;
	}

}
