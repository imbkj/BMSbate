package Controller.CoCompact;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import impl.UserInfoImpl;
import impl.WorkflowCore.WfOperateImpl;

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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;
import service.WorkflowCore.WfOperateService;

import Model.CoCompactModel;
import Util.DateUtil;
import Util.UserInfo;
import bll.CoCompact.BaseInfo_SelectListBll;
import bll.CoCompact.CoCompact_OperateBll;
import bll.CoCompact.CoCompact_OperateImpl;

public class Compact_SignController extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();

	BaseInfo_SelectListBll cocoB = new BaseInfo_SelectListBll();

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	int coco_id = 0;
	int coco_tapr_id = 0;

	// int coco_id =
	// ((CoCompactModel)Executions.getCurrent().getArg().get("cocoM")).getCoco_id();

	// int coco_tapr_id =
	// ((CoCompactModel)Executions.getCurrent().getArg().get("cocoM")).getCoco_tapr_id();

	private CoCompactModel cocoList;

	Date dateInuredate = new Date(); // 合同生效日
	Date dateIndate = new Date(); // 合同到期日

	@Wire
	private Datebox coco_returndate; // 合同签回日期
	@Wire
	private Datebox coco_signdate; // 合同签订日期
	@Wire
	private Combobox coco_signplace; // 合同签订场地
	@Wire
	private Datebox coco_indate; // 合同到期日
	@Wire
	private Combobox coco_delay; // 合同自动延长
	@Wire
	private Textbox coco_money; // 合同币种
	@Wire
	private Combobox coco_invoice; // 发票类型
	@Wire
	private Window w1;
	@Wire
	private Combobox coco_paydate; // 每月付款日
	@Wire
	private Textbox coco_fw_p; // 服务类比例
	@Wire
	private Textbox coco_fl_p; // 福利类比例
	@Wire
	private Textbox coco_dk_p; // 代扣代缴类比例

	private Double fw_per;// 服务类比例%
	private Double fl_per;// 福利类比例%
	private Double dk_per;// 代扣代缴类比例%
	private DecimalFormat df = new DecimalFormat(".##");

	public Compact_SignController() {
		coco_tapr_id = Integer.parseInt(Executions.getCurrent().getArg()
				.get("id").toString());
		coco_id = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());
		// cocoList = cocoB.searchCoCoBase(String.valueOf(coco_id)).get(0);
		List<CoCompactModel> list = cocoB.getcompact(coco_id);
		if (list.size() > 0) {
			cocoList = list.get(0);

			setFw_per(Double
					.parseDouble(df.format(cocoList.getCoco_fw_p() * 100)));
			setFl_per(Double
					.parseDouble(df.format(cocoList.getCoco_fl_p() * 100)));
			setDk_per(Double
					.parseDouble(df.format(cocoList.getCoco_dk_p() * 100)));
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		// 合同生效日
		String sInuredate = cocoList.getCoco_inuredate();
		try {
			dateInuredate = sdf.parse(sInuredate);
		} catch (Exception e) {
			dateInuredate = null;
		}

		// 合同到期日
		String sIndate = cocoList.getCoco_indate();
		try {
			dateIndate = sdf.parse(sIndate);
		} catch (Exception e) {
			dateIndate = null;
		}
	}

	// 公司合同签回方法
	@Listen("onClick=#btSubmit")
	public void signCompact() throws Exception {

		String returndate = ""; // 合同签回日期
		String signdate = ""; // 合同签订日期
		// String inuredate=""; //合同生效日
		String indate = ""; // 合同到期日
		String paydate = "";// 每月付款日
		Integer ddInIn = 0;// 合同到期日与合同生效日差额

		// 日期判断
		if (coco_returndate.getValue() != null) {
			returndate = cocoBll.DatetoSting(coco_returndate.getValue());
		}
		if (coco_signdate.getValue() != null) {
			signdate = cocoBll.DatetoSting(coco_signdate.getValue());
		}
		/*
		 * if(coco_inuredate.getValue() != null){ inuredate =
		 * cocoBll.DatetoSting(coco_inuredate.getValue()); }
		 */
		if (coco_indate.getValue() != null) {
			indate = cocoBll.DatetoSting(coco_indate.getValue());
		}

		if (dateInuredate != null && !"".equals(indate)) {
			// 合同到期日不能早于合同生效日
			DateUtil du = new DateUtil();
			ddInIn = du.datediff(dateInuredate, coco_indate.getValue(), "d");
		}

		// 每月付款日
		if (coco_paydate.getSelectedItem() != null) {
			paydate = coco_paydate.getValue();
		}

		// 服务类比例
		Double fw_p = null;
		try {
			fw_p = Double.parseDouble(coco_fw_p.getValue()) / 100;
		} catch (Exception e) {
			fw_p = null;
		}

		// 福利类比例
		Double fl_p = null;
		try {
			fl_p = Double.parseDouble(coco_fl_p.getValue()) / 100;
		} catch (Exception e) {
			fl_p = null;
		}

		// 代扣代缴类比例
		Double dk_p = null;
		try {
			dk_p = Double.parseDouble(coco_dk_p.getValue()) / 100;
		} catch (Exception e) {
			dk_p = null;
		}

		// 判断必填项是否为空
		if (!"".equals(returndate) && !coco_delay.getValue().equals("")
				&& ddInIn >= 0 && !"".equals(paydate) && fw_p != null
				&& fl_p != null && dk_p != null) {

			// 调用合同签回方法
			String[] message = cocoBll.signCoCompact(coco_tapr_id, coco_id,
					returndate, signdate, coco_signplace.getValue(), indate,
					coco_delay.getValue(), coco_money.getValue(),
					coco_invoice.getValue(), username,
					Integer.parseInt(cocoList.getCoco_cola_id()),
					Integer.parseInt(paydate), fw_p, fl_p, dk_p);
			if (cocoList.getCid2() != null && cocoList.getCid2() > 0) {
				cocoBll.startMission(cocoList);
			}

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
				Messagebox.show("请选择“合同签回日期”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (coco_delay.getValue().equals("")) {
				Messagebox.show("请选择“合同自动延长”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (ddInIn < 0) {
				Messagebox.show("请选择“合同到期日”不能早于“合同生效日”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (paydate.equals("")) {
				Messagebox.show("请选择“每月付款日”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (fw_p == null) {
				Messagebox.show("请正确录入“服务类比例”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (fl_p == null) {
				Messagebox.show("请正确录入“福利类比例”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (dk_p == null) {
				Messagebox.show("请正确录入“代扣代缴类比例”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 公司合同签回方法
	@Listen("onClick=#btBack")
	public void back() {
		if (Messagebox.show("是否退回至“合同制作”？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

			String remark = "客服要求退回。";
			Object[] obj = { "7", coco_id, remark };
			// 执行工作流

			WfOperateService wf = new WfOperateImpl(new CoCompact_OperateImpl());
			String[] m = wf.ReturnToN(obj, coco_tapr_id,2,
					UserInfo.getUsername(), remark);

			if ("1".equals(m[0])) {
				Messagebox.show("退回成功！", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				// 关闭窗口
				w1.detach();
			} else {
				Messagebox.show("操作失败！", "操作提示", Messagebox.OK,
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

	public CoCompactModel getCocoList() {
		return cocoList;
	}

	public void setCocoList(CoCompactModel cocoList) {
		this.cocoList = cocoList;
	}

	public Double getFw_per() {
		return fw_per;
	}

	public void setFw_per(Double fw_per) {
		this.fw_per = fw_per;
	}

	public Double getFl_per() {
		return fl_per;
	}

	public void setFl_per(Double fl_per) {
		this.fl_per = fl_per;
	}

	public Double getDk_per() {
		return dk_per;
	}

	public void setDk_per(Double dk_per) {
		this.dk_per = dk_per;
	}

}
