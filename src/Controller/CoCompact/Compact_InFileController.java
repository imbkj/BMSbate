package Controller.CoCompact;

import java.util.Date;
import java.util.List;

import impl.UserInfoImpl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;
import Model.CoCompactModel;
import Util.DateStringChange;
import bll.CoCompact.BaseInfo_SelectListBll;
import bll.CoCompact.CoCompact_OperateBll;

public class Compact_InFileController extends SelectorComposer<Component> {

	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();
	private DateStringChange dsc = new DateStringChange();
	BaseInfo_SelectListBll cocoB = new BaseInfo_SelectListBll();

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	// int coco_id
	// =((CoCompactModel)Executions.getCurrent().getArg().get("cocoM")).getCoco_id();
	int coco_id;
	int coco_tapr_id;
	private CoCompactModel cocoList;
	private Date filedate;
	private Date indate;

	@Wire
	private Datebox coco_filedate; // 合同归档日期
	@Wire
	private Textbox coco_fileid; // 合同存档编号
	@Wire
	private Textbox coco_remark; // 备注
	@Wire
	private Window w1;
	@Wire
	private Intbox coco_chs_copies; // 中文份数
	@Wire
	private Intbox coco_en_copies; // 英文份数
	@Wire
	private Combobox coco_delay; // 是否自动延长
	@Wire
	private Datebox coco_indate; // 合同归档日期
	@Wire
	private Combobox coco_paydate; // 每月付款日

	public Compact_InFileController() {
		coco_tapr_id = Integer.parseInt(Executions.getCurrent().getArg()
				.get("id").toString());
		coco_id = Integer.parseInt(Executions.getCurrent().getArg().get("daid")
				.toString());
		// cocoList = cocoB.searchCoCoBase(String.valueOf(coco_id)).get(0);
		List<CoCompactModel> list = cocoB.getcompact(coco_id);
		if (list.size() > 0) {
			cocoList = list.get(0);
		}
		filedate = dsc.StringtoDate(dsc.Datestringnow("yyyy-MM-dd"),
				"yyyy-MM-dd");
		indate = dsc.StringtoDate(cocoList.getCoco_indate(), "yyyy-MM-dd");
	}

	// 公司合同签回方法
	@Listen("onClick=#btSubmit")
	public void inFileCompact() throws Exception {

		String filedate = ""; // 合同归档日期
		String indate = ""; // 合同到期日期
		String chsCopies = "0"; // 中文份数
		String enCopies = "0"; // 英文份数
		String delay = "";// 是否自动延长
		String paydate = "";// 每月付款日

		// 日期判断
		if (coco_filedate.getValue() != null) {
			filedate = cocoBll.DatetoSting(coco_filedate.getValue());
		}
		if (coco_indate.getValue() != null) {
			indate = cocoBll.DatetoSting(coco_indate.getValue());
		}

		// 整数判断
		if (coco_chs_copies.getValue() != null) {
			chsCopies = String.valueOf(coco_chs_copies.getValue());
		}
		if (coco_en_copies.getValue() != null) {
			enCopies = String.valueOf(coco_en_copies.getValue());
		}

		if (coco_delay.getSelectedItem() != null) {
			delay = coco_delay.getValue();
		}
		if (coco_paydate.getSelectedItem() != null) {
			paydate = coco_paydate.getValue();
		}

		// 判断必填项是否为空
		if (chkPage(filedate, chsCopies, delay, indate, paydate)) {

			// 调用合同归档方法
			String[] message = cocoBll.returnCoCompact(coco_tapr_id, coco_id,
					filedate, coco_fileid.getValue(), chsCopies, enCopies,
					coco_remark.getValue(), username,
					Integer.parseInt(cocoList.getCoco_cola_id()),
					coco_delay.getValue(), indate, Integer.parseInt(paydate));

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// Executions.sendRedirect("Compact_InFileList.zul");
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
		}
	}

	public boolean chkPage(String filedate, String chsCopies,
			String coco_delay, String indate, String paydate) {
		boolean result;
		result = true;
		if ("".equals(filedate)) {
			Messagebox.show("请选择“合同归档日期”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			result = false;
		} else if (chsCopies.equals("0")) {
			Messagebox.show("请输入“中文合同份数”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			result = false;
		} else if ("".equals(coco_delay)) {
			Messagebox.show("请选择“是否自动延长”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			result = false;
		} else if ("否".equals(coco_delay) && "".equals(indate)) {
			Messagebox.show("请选择“合同到期日期”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			result = false;
		} else if ("".equals(paydate)) {
			Messagebox.show("请选择“每月付款日”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			result = false;
		}
		return result;
	}

	public CoCompactModel getCocoList() {
		return cocoList;
	}

	public void setCocoList(CoCompactModel cocoList) {
		this.cocoList = cocoList;
	}

	public Date getFiledate() {
		return filedate;
	}

	public void setFiledate(Date filedate) {
		this.filedate = filedate;
	}

	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}

}
