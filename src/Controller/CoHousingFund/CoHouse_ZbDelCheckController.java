package Controller.CoHousingFund;

import java.util.List;

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
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFundZbBll;
import bll.CoHousingFund.CoHousingFund_PwdBll;

/**
 * 注销专办员控制
 * 
 * @author Administrator
 * 
 */
public class CoHouse_ZbDelCheckController {

	private List<CoHousingFundZbModel> zbListBycohf_id;

	private CoHousingFundZbBll cfzb = SingleBllFactory.getInstance().getChzb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private CoHousingFundZbChangeModel m = (CoHousingFundZbChangeModel) Executions
			.getCurrent().getArg().get("cfzc");
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();

	public CoHouse_ZbDelCheckController() {
		// 注销专办员 ，首先要获取被注销的专办员列表 ，被注销的专办员不能拥有密钥
		zbListBycohf_id = cfpb.notPwdZb(m.getCfzc_cohf_id());

	}

	// 获取combobox选择的chfzid
	@Command
	public void setfzid(@BindingParam("chfz") CoHousingFundZbModel chfz) {
		m.setCfzc_chfz_id(chfz.getChfz_id());
		m.setCfzc_name(chfz.getChfz_name());
		m.setCfzc_number(chfz.getChfz_tel());
		m.setCfzc_tel(chfz.getChfz_tel());
		m.setCfzc_mail(chfz.getChfz_mail());
		m.setCfzc_mobile(chfz.getChfz_mobile());

	}

	// 申报中状态提交申报
	@Command
	public void shenbao(@BindingParam("win") final Window win) {

		m.setCfzc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {

			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {

					boolean flag2 = cfzb.delZb(m);
					if (flag2) {
						// cfzb.updateCusZbChange(chfm);
						m.setCfzc_state(3);
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

	// 未申报状态提交申报
	@Command
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("sbstate") final int sbstate) {
		m.setCfzc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		System.out.println(m.getCfzc_chfz_id());
		m.setCfzc_state(sbstate); // 根据选择的状态来进行申报
		if (m.getCfzc_chfz_id() != 0) {
			try {
				String[] message = docOC.UpchkHaveTo(gd);
				if (message[0] == "1") {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.YES.equals(event.getButton())) {
								// 修改zbchange表
								boolean flag = cfzb.delZbc(m);
								if (flag) {
									docOC.UpsubmitDoc(gd, m.getCfzc_id() + "");
									if (sbstate != 3) {
										Messagebox.show("申报中!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
									}
								}
								if (flag && sbstate == 3) { // 如果选择的状态为3 则直接完成申报
									boolean flag2 = cfzb.delZb(m);
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
		} else {
			Messagebox.show("请选择要注销的专办员");
		}
	}

	public CoHousingFundZbChangeModel getM() {
		return m;
	}

	public void setM(CoHousingFundZbChangeModel m) {
		this.m = m;
	}

	public List<CoHousingFundZbModel> getZbListBycohf_id() {
		return zbListBycohf_id;
	}

	public void setZbListBycohf_id(List<CoHousingFundZbModel> zbListBycohf_id) {
		this.zbListBycohf_id = zbListBycohf_id;
	}

}
