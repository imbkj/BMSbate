package Controller.CoHousingFund;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundModel;
import Model.CoHousingFundZbChangeModel;
import Model.CoHousingFundZbModel;
import Util.EmailUtil;
import Util.SingleBllFactory;
import Util.TelUtil;
import bll.CoHousingFund.CoHousingFundZbBll;

/**
 * 后到变更专办员控制
 * 
 * @author Administrator
 * 
 */
public class CoHouse_ZbChangeCheckController {

	private int cid;
	private CoHousingFundZbChangeModel cusZbChangeInfo;

	private CoHousingFundZbModel newZbChangeInfo;
	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();

	private CoHousingFundZbChangeModel m = (CoHousingFundZbChangeModel) Executions
			.getCurrent().getArg().get("cfzc");

	public CoHouse_ZbChangeCheckController() {

		CoHousingFundModel cohf = cfzb.getCohf(Integer.valueOf(m
				.getCfzc_cohf_id()));
		cid = cohf.getCid();

		// 查询原来的专办员信息
		newZbChangeInfo = cfzb
				.getNewZbInfo(Integer.valueOf(m.getCfzc_chfz_id()));

		// 获取当前时间

		// 查询修改后的专办员信息
		cusZbChangeInfo = cfzb.addtype(Integer.valueOf(m.getCfzc_id()));

	}

	/**
	 * 检查邮箱格式
	 * 
	 * @param email
	 */
	@Command
	@NotifyChange({ "m" })
	public void checkEmailSimple(@BindingParam("email") String email) {
		try {
			if (email != null && !email.isEmpty()) {
				if (!EmailUtil.checkEmailSimple(email)) {
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
	public void checkMobile(@BindingParam("mobile") String value) {
		try {
			if (value != null && !value.isEmpty()) {
				if (!TelUtil.isMobile(value)) {
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
	public void checkPhone(@BindingParam("tel") String value) {
		try {
			if (value != null && !value.isEmpty()) {
				if (!TelUtil.isPhone(value)) {
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
	 * 申报中状态提交
	 * 
	 * @param r
	 */
	@Command
	public void shenbao(@BindingParam("win") final Window win) {

		m.setCfzc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {

			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					// 修改zb表
					boolean flag2 = cfzb.updateZb(m, m.getCfzc_chfz_id());
					if (flag2) {
						m.setCfzc_state(3);
						// cfzb.updateCusZbChange(chfm);
						boolean flag = cfzb.changeStatus(m);
						System.out.println(flag);
						if (flag) {
							Messagebox.show("申报成功");
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

	// 未申报提交
	@Command
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("sbstate") final int sbstate) {
		newZbChangeInfo.setRemark(m.getCfzc_remark()); // 获取备注信息
		newZbChangeInfo.setCfzc_id(m.getCfzc_id());
		try {
			String[] message = docOC.UpchkHaveTo(gd);
			if (message[0] == "1") {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.YES.equals(event.getButton())) {

							// 修改zbchange表
							boolean flag = cfzb
									.addZbc(newZbChangeInfo, sbstate);
							if (flag) {
								docOC.UpsubmitDoc(gd, m.getCfzc_id() + "");
								if (sbstate != 3) {
									Messagebox.show("申报中!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								}
							}
							if (flag && sbstate == 3) { // 如果选择的状态为3 则直接完成申报
								m.setCfzc_name(newZbChangeInfo.getChfz_name());
								m.setCfzc_tel(newZbChangeInfo.getChfz_tel());
								m.setCfzc_mail(newZbChangeInfo.getChfz_mail());
								m.setCfzc_mobile(newZbChangeInfo
										.getChfz_mobile());
								m.setCfzc_addname(Executions.getCurrent()
										.getDesktop().getSession()
										.getAttribute("username").toString());
								boolean flag2 = cfzb.updateZb(m,
										m.getCfzc_chfz_id());
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

	public CoHousingFundZbChangeModel getM() {
		return m;
	}

	public void setM(CoHousingFundZbChangeModel m) {
		this.m = m;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public CoHousingFundZbChangeModel getCusZbChangeInfo() {
		return cusZbChangeInfo;
	}

	public void setCusZbChangeInfo(CoHousingFundZbChangeModel cusZbChangeInfo) {
		this.cusZbChangeInfo = cusZbChangeInfo;
	}

	public CoHousingFundZbModel getNewZbChangeInfo() {
		return newZbChangeInfo;
	}

	public void setNewZbChangeInfo(CoHousingFundZbModel newZbChangeInfo) {
		this.newZbChangeInfo = newZbChangeInfo;
	}

}
