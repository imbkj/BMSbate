package Controller.Archives;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_FeeManagerBll;
import Model.EmArchivePaymentModel;
import Model.EmHouseChangeModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.FileOperate;
import Util.FormatObjectValueUtil;
import Util.UserInfo;
import Util.pinyin4jUtil;

public class Archive_FeeManagerController {
	private List<EmArchivePaymentModel> eaplist = new ListModelList<EmArchivePaymentModel>();
	private List<EmArchivePaymentModel> ownmonthList = new ListModelList<EmArchivePaymentModel>();
	private List<EmArchivePaymentModel> loanManList = new ListModelList<EmArchivePaymentModel>();

	private EmArchivePaymentModel epModel = new EmArchivePaymentModel();

	private Archive_FeeManagerBll bll = new Archive_FeeManagerBll();
	private FormatObjectValueUtil fovu = new FormatObjectValueUtil();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private boolean billDisplay = false;

	private Window win;

	public Archive_FeeManagerController() {
		EmArchivePaymentModel em = new EmArchivePaymentModel();
		em.setEmap_lstate(1);
		setEaplist(em);
		setLoanManList();
		setOwnmonthList();

		messageState();
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		win = winD;
	}

	@Command("checkall")
	@NotifyChange({ "eaplist", "billDisplay" })
	public void checkall(@BindingParam("a") Checkbox cka,
			@BindingParam("b") Grid gd) {
		billDisplay = true;
		boolean b = false;
		for (EmArchivePaymentModel em : eaplist) {
			em.setChecked(cka.isChecked());
			if (em.isChecked()) {
				b = true;
				if (em.getEmap_lstate() > 2) {
					billDisplay = false;
				}
			}
		}
		if (!b) {
			billDisplay = false;
		}

	}

	@Command
	public void updateDate(@BindingParam("a") String v, @BindingParam("c") String name)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		
		Method method = epModel.getClass().getMethod(setMethod(name), String.class);
		
		method.invoke(epModel, v);

	}

	@Command("checksingle")
	@NotifyChange("billDisplay")
	public void checksingle(@BindingParam("a") Checkbox ck,
			@BindingParam("b") Checkbox cka) {
		if (!ck.isChecked()) {
			cka.setChecked(false);
		}
		billDisplay = true;
		boolean b = false;
		for (EmArchivePaymentModel em : eaplist) {
			if (em.isChecked()) {
				b = true;
				if (em.getEmap_lstate()>2) {
					billDisplay = false;
				}
			}
		}
		if (!b) {
			billDisplay = false;
		}
	}

	@Command("SearchInfo")
	@NotifyChange("eaplist")
	public void SearchInfo() {
		Combobox cbfee = (Combobox) win.getFellow("feetype");
		String feeType = fovu.getFieldValue(cbfee);
		epModel.setEmap_lstate(feeType.equals("") ? null : Integer
				.valueOf(feeType));
		setEaplist(epModel);
		messageState();
	}

	public void messageState() {
		List<SysMessageModel> mList = new ListModelList<>();
		mList = bll.getMsgList("EmArchivePayment", UserInfo.getUsername(),
				Integer.valueOf(UserInfo.getUserid()));
		for (EmArchivePaymentModel em : eaplist) {
			for (SysMessageModel sm : mList) {
				if (em.getEmap_id().equals(sm.getSmwr_tid())) {
					em.setMessage(true);
					if (sm.getSymr_readstate() != null) {
						em.setReadState(sm.getSymr_readstate().equals(1) ? true
								: false);
					}

				}
			}

		}

	}

	@Command
	@NotifyChange("eaplist")
	public void newfee() {
		Window window = (Window) Executions.createComponents(
				"Archive_addFee.zul", null, null);
		window.doModal();
		SearchInfo();
	}

	@Command
	public void info(@BindingParam("a") EmArchivePaymentModel em) {
		Map map = new HashMap();
		map.put("epm", em);

		Window window = (Window) Executions.createComponents(
				"Archive_addFeeInfo.zul", null, map);
		window.doModal();
	}

	@Command
	public void mod(@BindingParam("a") EmArchivePaymentModel em) {
		Map map = new HashMap();
		map.put("epm", em);

		Window window = (Window) Executions.createComponents(
				"Archive_addFeeEdit.zul", null, map);
		window.doModal();
	}

	@Command
	@NotifyChange("eaplist")
	public void del(@BindingParam("a") final EmArchivePaymentModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.delpayment(em);
							if (i > 0) {
								bll.delpaymentinfo(em);
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								EmArchivePaymentModel epm = new EmArchivePaymentModel();
								epm.setEmap_lstate(1);
								setEaplist(epm);
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});

	}

	@Command
	@NotifyChange("eaplist")
	public void listId() {
		String str = bll.listId(eaplist);
		FileOperate.download("OfficeFile/DownLoad/EmArchivePayment/" + str
				+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername())
				+ ".xls");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date d = new Date();
		for (EmArchivePaymentModel em : eaplist) {
			if (em.isChecked()) {
				em.setEmap_cdate(em.getEmap_cdate());
				em.setEmap_sdate(em.getEmap_sdate());
				em.setEmap_lstate(2);
				em.setEmap_idlist("RS"
						+ sdf.format(d)
						+ pinyin4jUtil.getPinYinHeadChar(UserInfo.getUsername()));
				em.setEmap_idname(UserInfo.getUsername());
				em.setEmap_idtime(sdf.format(new Date()));
				Integer i = bll.modpayment(em);
				if (i > 0) {
					bll.movePayment(em);
					bll.movePaymentMod(em);
				}
			}

		}

		EmArchivePaymentModel epm = new EmArchivePaymentModel();
		epm.setEmap_lstate(1);
		setEaplist(epm);
	}

	@Command
	public void report() {
		String str = bll.report(eaplist);
		FileOperate.download("OfficeFile/DownLoad/EmArchivePayment/" + str
				+ ".xls");
	}

	@Command
	public void message(@BindingParam("a") EmArchivePaymentModel em) {
		Map map = new HashMap<>();
		map.put("id", em.getEmap_id());// 业务表id
		map.put("tablename", "EmArchivePayment");// 业务表名
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel lm = new LoginModel();
		lm.setLog_name(em.getEmap_client());
		mlist.add(lm);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	public List<EmArchivePaymentModel> getEaplist() {
		return eaplist;
	}

	public void setEaplist(EmArchivePaymentModel em) {
		this.eaplist = bll.getBaseList(em);
	}

	public List<EmArchivePaymentModel> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList() {
		this.ownmonthList = bll.getOwnmonthList();
	}

	public List<EmArchivePaymentModel> getLoanManList() {
		return loanManList;
	}

	public void setLoanManList() {
		this.loanManList = bll.getLoanManList();
	}

	public EmArchivePaymentModel getEpModel() {
		return epModel;
	}

	public void setEpModel(EmArchivePaymentModel epModel) {
		this.epModel = epModel;
	}

	public boolean isBillDisplay() {
		return billDisplay;
	}

	public void setBillDisplay(boolean billDisplay) {
		this.billDisplay = billDisplay;
	}

	public static String getMethod(String name) {
		return "get"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	public static String setMethod(String name) {
		return "set"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

}
