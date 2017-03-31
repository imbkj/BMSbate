package Controller.EmSalary;

import impl.MessageImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.MessageService;


import dal.LoginDal;

import Model.EmSalaryBaseSel_viewModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSalary.EmSalary_DataOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_RepayReasonController {
	private ItemFormula_SelectBll isBll = new ItemFormula_SelectBll();
	private EmSalary_SalarySelBll esBll = new EmSalary_SalarySelBll();
	private EmSalary_DataOperateBll eoBll = new EmSalary_DataOperateBll();

	private List reasonList; // 重发原因
	private EmSalaryBaseSel_viewModel esdaM; // 员工工资数据
	private int esda_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("esda_id").toString()); // 工资数据id

	// 获取用户名
	String username = UserInfo.getUsername();

	public EmSalary_RepayReasonController() {
		reasonList = isBll.getRepayReason();
		esdaM = esBll.getSalaryEmBase(esda_id);
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("repay_reason") Combobox repay_reason) {

		if (repay_reason.getSelectedItem() != null) {
			esdaM.setEsda_rp_reason(repay_reason.getSelectedItem().getValue()
					.toString());

			// 调用方法
			String[] message = eoBll.repayReason(esdaM, username);

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// //跳转页面
							win.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);

				// 发送给客服
				String cobaClient = "";
				CoBase_SelectBll cbsBll = new CoBase_SelectBll();
				try {
					cobaClient = cbsBll
							.getCobaseinfo(" AND a.cid=" + esdaM.getCid())
							.get(0).getCoba_client();
				} catch (Exception e) {
					cobaClient = "";
				}

				String coba_gzaddname = "";
				try {// 薪酬负责人
					coba_gzaddname = cbsBll
							.getCobaseinfo(" AND a.cid=" + esdaM.getCid())
							.get(0).getCoba_gzaddname();
				} catch (Exception e) {
					coba_gzaddname = "";
				}

				// 添加系统短信提醒
				String emailTitle = esdaM.getCoba_shortname() + "公司的员工"
						+ esdaM.getEmba_name() + esdaM.getOwnmonth()
						+ "月份工资数据退回明细";
				String msgstr = esdaM.getCoba_shortname() + "公司的员工"
						+ esdaM.getEmba_name() + esdaM.getOwnmonth()
						+ "月份工资数据退回需重发，原因为："
						+ repay_reason.getSelectedItem().getLabel()
						+ "。如有问题请联系薪酬负责人：" + coba_gzaddname;

				if (!"".equals(cobaClient)) {
					addMsg(emailTitle, msgstr, cobaClient);
				}

				// 发送给操作人
				addMsg(emailTitle, msgstr, username);

				// 发送给薪酬负责人
				if (!username.equals(coba_gzaddname)) {
					addMsg(emailTitle, msgstr, coba_gzaddname);
				}

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			// 弹出提示
			Messagebox
					.show("请选择重发原因！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 新增系统短信
	public void addMsg(String eTitle, String msgstr, String rName) {
		// 参数解释，业务表名：tablename；业务表id:id
		MessageService msgservice = new MessageImpl("EmSalaryData", esda_id);

		LoginDal d = new LoginDal();
		// 调用添加短信息方法
		SysMessageModel sysm = new SysMessageModel();
		sysm.setSyme_content(msgstr);// 短信内容
		sysm.setSyme_log_id(d.getUserIDByname(username));// 发件人id
		sysm.setSymr_name(rName);// 收件人姓名
		sysm.setSymr_log_id(d.getUserIDByname((rName)));// 收件人姓名id
		sysm.setEmail(1);
		sysm.setEmailtitle(eTitle);
		msgservice.Add(sysm);
	}

	public List getReasonList() {
		return reasonList;
	}

	public void setReasonList(List reasonList) {
		this.reasonList = reasonList;
	}

	public EmSalaryBaseSel_viewModel getEsdaM() {
		return esdaM;
	}

	public void setEsdaM(EmSalaryBaseSel_viewModel esdaM) {
		this.esdaM = esdaM;
	}

}
