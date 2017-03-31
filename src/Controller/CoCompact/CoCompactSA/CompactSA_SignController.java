package Controller.CoCompact.CoCompactSA;

import impl.UserInfoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;
import Model.CoCompactSAModel;
import bll.CoCompact.CoCompactSA.CoCompactSA_SelectBll;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;

public class CompactSA_SignController extends SelectorComposer<Component> {

	private CoCompactSA_OperateBll ccsaBll = new CoCompactSA_OperateBll();

	CoCompactSA_SelectBll ccsaB = new CoCompactSA_SelectBll();

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	int ccsa_id = 0;
	int ccsa_tapr_id = 0;

	// int ccsa_id =
	// ((CoCompactModel)Executions.getCurrent().getArg().get("ccsaM")).getCoco_id();

	// int ccsa_tapr_id =
	// ((CoCompactModel)Executions.getCurrent().getArg().get("ccsaM")).getCoco_tapr_id();

	private CoCompactSAModel ccsaList;

	Date dateInuredate = new Date(); // 合同生效日
	Date dateIndate = new Date(); // 合同到期日

	@Wire
	private Datebox ccsa_returndate; // 合同签回日期
	@Wire
	private Datebox ccsa_signdate; // 合同签订日期
	@Wire
	private Combobox ccsa_signplace; // 合同签订场地
	@Wire
	private Window w1;

	public CompactSA_SignController() {
		ccsa_tapr_id = Integer.parseInt(Executions.getCurrent().getArg()
				.get("id").toString());
		ccsa_id = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());
		ccsaList = ccsaB.searchCCSABase(ccsa_id).get(0);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 合同生效日
		String sInuredate = ccsaList.getCcsa_inuredate();
		try {
			dateInuredate = sdf.parse(sInuredate);
		} catch (Exception e) {
			dateInuredate = null;
		}

		// 合同到期日
		String sIndate = ccsaList.getCcsa_indate();
		try {
			dateIndate = sdf.parse(sIndate);
		} catch (Exception e) {
			dateIndate = null;
		}
	}

	// 公司合同签回方法
	@Listen("onClick=#btSubmit")
	public void signCompactSA() throws Exception {

		String returndate = ""; // 合同签回日期
		String signdate = ""; // 合同签订日期
		// String inuredate=""; //合同生效日

		// 日期判断
		if (ccsa_returndate.getValue() != null) {
			returndate = ccsaBll.DatetoSting(ccsa_returndate.getValue());
		}
		if (ccsa_signdate.getValue() != null) {
			signdate = ccsaBll.DatetoSting(ccsa_signdate.getValue());
		}
		/*
		 * if(ccsa_inuredate.getValue() != null){ inuredate =
		 * ccsaBll.DatetoSting(ccsa_inuredate.getValue()); }
		 */

		// 判断必填项是否为空
		if (!"".equals(returndate)) {

			// 调用合同签回方法
			String[] message = ccsaBll.signCoCompactSA(ccsa_tapr_id, ccsa_id,
					returndate, signdate, ccsa_signplace.getValue(), 
					username,ccsaList.getCid());

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("Compact_SginList.zul");
							// //跳转页面
							w1.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			if ("".equals(returndate)) {
				Messagebox.show("请选择“签回日期”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public Date getDateInuredate() {
		return dateInuredate;
	}

	public void setDateInuredate(Date dateInuredate) {
		this.dateInuredate = dateInuredate;
	}

	public Date getDateIndate() {
		return dateIndate;
	}

	public void setDateIndate(Date dateIndate) {
		this.dateIndate = dateIndate;
	}

	public CoCompactSAModel getCcsaList() {
		return ccsaList;
	}

	public void setCcsaList(CoCompactSAModel ccsaList) {
		this.ccsaList = ccsaList;
	}

}
