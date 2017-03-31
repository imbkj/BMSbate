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
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundPwdModel;
import Util.SingleBllFactory;
import bll.CoHousingFund.CoHousingFund_PwdBll;

/**
 * 数字证书续期控制
 * 
 * @author Administrator
 * 
 */
public class CoHouse_pwd_RenewalController {

	private CoHousingFundPwdChangeModel m = (CoHousingFundPwdChangeModel) Executions
			.getCurrent().getArg().get("cfpc");
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private List<CoHousingFundPwdModel> pwdList; // 该单位的可用密钥

	// 构造器
	public CoHouse_pwd_RenewalController() {
		init();
	}

	// 初始化
	public void init() {
		pwdList = cfpb.allPwd(m.getCfpc_cohf_id());
		System.out.println(m.getCfpc_id());
	}

	// 检测时间
	@Command
	public void addDateCheck() {
		if (m.getStartdate() == null || m.getEnddate() == null) {
			Messagebox.show("请录入日期信息");
		} else {
			if (!m.getStartdate().before(m.getEnddate())) {
				Messagebox.show("起始时间请在到期时间之后");
			}
		}
	}

	// 状态为未申报提交
	@Command
	public void submit(@BindingParam("wind") final Window win,
			@BindingParam("gd") final Grid gd,
			@BindingParam("sbstate") final int sbstate,
			@BindingParam("m") CoHousingFundPwdModel cfpm) {
		System.out.println(m.getStartdate());
		m.setCfpc_chfz_id(cfpm.getChfp_id());
		m.setCfpc_state(sbstate);
		m.setCfpc_zb_name(cfpm.getChfp_zb_name());
		m.setCfpc_zb_number(cfpm.getChfp_zb_number());
		m.setCfpc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		try {
			String[] message = docOC.UpchkHaveTo(gd);
			if (message[0] == "1") {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.YES.equals(event.getButton())) {
							boolean flag = cfpb.renewalPwdc(m);
							if (flag) {
								docOC.UpsubmitDoc(gd,
										String.valueOf(m.getCfpc_id()));
								if (sbstate != 3) {
									Messagebox.show("申报中!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									cfpb.getPwdChangeByid(m.getCfpc_id());
								}
							} else {
								Messagebox.show("添加失败!");
							}

							if (sbstate == 3) {
								boolean flag2 = cfpb.renewalPwd(m);
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

		}

	}

	// 申报中状态提交申报
	@Command
	public void shenbao(@BindingParam("wind") final Window win,
			@BindingParam("gd") final Grid gd) {
		m.setCfpc_state(3);
		m.setCfpc_addname(Executions.getCurrent().getDesktop().getSession()
				.getAttribute("username").toString());
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {

					boolean flag = cfpb.renewalPwd(m);
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

	public List<CoHousingFundPwdModel> getPwdList() {
		return pwdList;
	}

	public void setPwdList(List<CoHousingFundPwdModel> pwdList) {
		this.pwdList = pwdList;
	}

	public CoHousingFundPwdChangeModel getM() {
		return m;
	}

	public void setM(CoHousingFundPwdChangeModel m) {
		this.m = m;
	}

}
