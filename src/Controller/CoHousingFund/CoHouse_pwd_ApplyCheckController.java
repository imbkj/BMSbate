package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.CoHousingFundPwdChangeModel;
import Model.CoHousingFundZbModel;
import Util.SingleBllFactory;
import Util.UserInfo;
import bll.CoHousingFund.CoHousingFund_PwdBll;

//申请数字证书控制
public class CoHouse_pwd_ApplyCheckController {

	private CoHousingFundPwdChangeModel m = (CoHousingFundPwdChangeModel) Executions
			.getCurrent().getArg().get("cfpc");
	private CoHousingFund_PwdBll cfpb = SingleBllFactory.getInstance()
			.getCfpb();
	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private List<CoHousingFundZbModel> notPwdZb = new ListModelList<>();

	// 构造器
	public CoHouse_pwd_ApplyCheckController() {
		init();

	}

	// 初始化
	public void init() {
		// 加载没有密钥的专办员
		notPwdZb = cfpb.notPwdZb(m.getCfpc_cohf_id());

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
			@BindingParam("gd1") final Grid gd1,
			@BindingParam("sbstate") final int sbstate,
			@BindingParam("m") CoHousingFundZbModel chfz) {
		if (sbstate == 3) {

			if (chfz == null || chfz.equals("")) {
				Messagebox.show("请选择密钥专办员!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (m.getStartdate() == null || m.getStartdate().equals("")) {
				Messagebox.show("密钥起始日期不能为空!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
			if (m.getEnddate() == null || m.getEnddate().equals("")) {
				Messagebox.show("密钥到期日期不能为空!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}
		if (chfz != null) {
			m.setCfpc_chfz_id(chfz.getChfz_id());
			m.setCfpc_zb_name(chfz.getChfz_name());
			m.setCfpc_zb_number(chfz.getChfz_number());
		}

		m.setCfpc_state(sbstate);

		m.setCfpc_addname(UserInfo.getUsername());
		String[] message = null;
		try {
			if (m.getCfpc_flag() == 1) { // 从账户接管添加的数据 读取和更新gd1
				message = docOC.UpchkHaveTo(gd1);
			} else {
				message = docOC.UpchkHaveTo(gd);
			}
			if (message[0] == "1") {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.YES.equals(event.getButton())) {
							boolean flag = cfpb.addPwdc(m);
							if (flag) {
								if (m.getCfpc_flag() == 1) {
									docOC.UpsubmitDoc(gd1,
											String.valueOf(m.getCfpc_id()));
									if (sbstate != 3) {
										Messagebox.show("资料提交成功!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										cfpb.getPwdChangeByid(m.getCfpc_id());

									}
								} else {
									docOC.UpsubmitDoc(gd,
											String.valueOf(m.getCfpc_id()));
									if (sbstate != 3) {
										Messagebox.show("资料提交成功!", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										cfpb.getPwdChangeByid(m.getCfpc_id());
									}
								}
							} else {
								Messagebox.show("添加失败!");
								return;
							}

							if (sbstate == 3) {
								boolean flag2 = cfpb.addPwd(m);
								if (flag2) {
									Messagebox.show("已申报!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);

								}
							}
							win.detach();
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
			@BindingParam("gd") final Grid gd,
			@BindingParam("m") CoHousingFundZbModel chfz) {
		if (chfz == null || chfz.equals("")) {
			Messagebox.show("请选择密钥专办员!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if (m.getStartdate() == null || m.getStartdate().equals("")) {
			Messagebox.show("密钥起始日期不能为空!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		if (m.getEnddate() == null || m.getEnddate().equals("")) {
			Messagebox.show("密钥到期日期不能为空!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		m.setCfpc_state(3);
		m.setCfpc_addname(UserInfo.getUsername());
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {

					boolean flag = cfpb.addPwd(m);
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
