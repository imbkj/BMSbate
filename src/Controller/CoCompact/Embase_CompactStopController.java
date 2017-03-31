package Controller.CoCompact;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;

import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoCompact.EmBase_CompactStopBll;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferModel;
import Model.CoPclassModel;
import Model.CoglistModel;
import Util.DateStringChange;

public class Embase_CompactStopController {
	private List<CoCompactModel> compactlist;
	private List<CoOfferModel> quotelist;
	private List<CoPclassModel> classlist;
	private List<CoglistModel> emplist;

	private CoBaseModel cbm = (CoBaseModel) Executions.getCurrent().getArg()
			.get("model");

	private EmBase_CompactStopBll bll = new EmBase_CompactStopBll();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

	private Window win;

	public Embase_CompactStopController() {
		compactlist = bll.getcompactlist(cbm.getCid());
		quotelist = bll.getquotelist(cbm.getCid(), null);
		classlist = bll.getclasslist();
	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	@NotifyChange({ "quotelist", "emplist" })
	public void updateQuote() {
		Combobox cb = (Combobox) win.getFellow("compact");
		Combobox cbq = (Combobox) win.getFellow("quote");
		Combobox cbc = (Combobox) win.getFellow("pclass");
		Textbox tb = (Textbox) win.getFellow("product");
		String pclass = null;
		String product = null;
		if (cbc.getSelectedItem() != null) {
			if (cbc.getSelectedItem().getLabel() != null) {
				pclass = cbc.getSelectedItem().getLabel();
			}
		}
		if (tb.getValue() != null && !tb.getValue().equals("")) {
			product = tb.getValue();
		}

		if (cb.getSelectedItem() != null) {
			if (cb.getSelectedItem().getValue() != null) {
				quotelist = bll.getquotelist(
						null,
						Integer.valueOf(cb.getSelectedItem().getValue()
								.toString()));
				if (quotelist.size() == 1) {
					cbq.setValue(quotelist.get(0).getCoof_name());
					emplist = bll.getemplist(null, null, quotelist.get(0)
							.getCoof_id(), pclass, product);
				} else {
					cbq.setValue("");
					emplist = bll.getemplist(
							null,
							Integer.valueOf(cb.getSelectedItem().getValue()
									.toString()), null, pclass, product);
				}

			}
		} else {
			quotelist = null;
			cbq.setValue("");
			if (cbc.getSelectedItem() != null) {
				if (cbc.getSelectedItem().getLabel() != null) {
					pclass = cbc.getSelectedItem().getLabel();
				}
			}
			if (tb.getValue() != null && !tb.getValue().equals("")) {
				product = tb.getValue();
			}
			if (pclass != null || product != null) {
				emplist = bll.getemplist(cbm.getCid(), null, null, pclass,
						product);
			} else {
				emplist = null;
			}
		}
	}

	@Command
	@NotifyChange("emplist")
	public void SearchList() {
		Combobox cb = (Combobox) win.getFellow("compact");
		Combobox cbq = (Combobox) win.getFellow("quote");
		Combobox cbc = (Combobox) win.getFellow("pclass");
		Textbox tb = (Textbox) win.getFellow("product");

		Integer coid = null;
		Integer coofId = null;
		String pclass = null;
		String product = null;
		if (cb.getSelectedItem() != null
				&& cb.getSelectedItem().getValue() != null) {
			coid = Integer.valueOf(cb.getSelectedItem().getValue().toString());
		}
		if (cbq.getSelectedItem() != null
				&& cbq.getSelectedItem().getValue() != null) {
			coofId = Integer.valueOf(cbq.getSelectedItem().getValue()
					.toString());
		}
		if (cbc.getSelectedItem() != null) {
			if (cbc.getSelectedItem().getLabel() != null) {
				pclass = cbc.getSelectedItem().getLabel();
			}
		}
		if (tb.getValue() != null && !tb.getValue().equals("")) {
			product = tb.getValue();
		}

		if (coid != null || coofId != null || pclass != null || product != null) {
			emplist = bll.getemplist(cbm.getCid(), coid, coofId, pclass,
					product);
		} else {
			emplist = null;
		}
	}

	@Command
	public void checkitem(@BindingParam("a") CoglistModel cm) {

		emplist.get(emplist.indexOf(cm)).setChecked(!cm.isChecked());
		BindUtils.postNotifyChange(null, null, cm, "checked");
	}

	@Command
	public void copysd(@BindingParam("a") CoglistModel cm,
			@BindingParam("b") MouseEvent evt) {

		Integer k = emplist.indexOf(cm);
		Datebox db = (Datebox) evt.getTarget().getParent().getChildren().get(0);
		/*
		 * Integer d = Integer.valueOf(DateStringChange.ownmonthAdd(
		 * sdf.format(db.getValue()), 1));
		 */
		Integer d = Integer.valueOf(sdf.format(db.getValue()));
		for (int i = (k + 1); i < emplist.size(); i++) {
			emplist.get(i).setStopDate(db.getValue());
			emplist.get(i).setCgli_stopdate(d);
			BindUtils.postNotifyChange(null, null, emplist.get(i), "stopDate");
		}
	}

	@Command
	public void updateDate(@BindingParam("a") Datebox db,
			@BindingParam("b") CoglistModel cm) {
		if (db.getValue() != null && !db.getValue().equals("")) {
			cm.setStopDate(db.getValue());
			Date d = db.getValue();
			/*
			 * Integer std = Integer.valueOf(DateStringChange.ownmonthAdd(
			 * sdf.format(d), 1));
			 */
			Integer std = Integer.valueOf(sdf.format(d));
			cm.setCgli_stopdate(std);
		}
	}

	@Command
	public void checkall(@BindingParam("a") Checkbox ck) {
		for (CoglistModel m : emplist) {
			m.setChecked(ck.isChecked());
			BindUtils.postNotifyChange(null, null, m, "checked");
		}

	}

	@Command
	@NotifyChange("emplist")
	public void submit() {

		boolean b2 = false;

		for (CoglistModel m : emplist) {
			if (m.isChecked()) {
				b2 = true;
				if (m.getStopDate() == null && m.getStopDate().equals("")) {
					Messagebox.show(
							"请选择 " + m.getEmba_name() + "[" + m.getColi_name()
									+ "]的终止收费时间", "提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
		}
		if (!b2) {
			Messagebox.show("请选择需要终止项目的人员", "提示", Messagebox.OK,
					Messagebox.ERROR);
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							boolean b = false;

							for (CoglistModel m : emplist) {
								if (m.isChecked()) {

									b = bll.stopItem(m);
									if (!b) {
										Messagebox.show(
												m.getEmba_name() + "["
														+ m.getColi_name()
														+ "]" + "更新异常,错误行数:"
														+ emplist.indexOf(m),
												"提示", Messagebox.OK,
												Messagebox.ERROR);
										return;
									}
								}
							}

							if (b) {
								Messagebox.show("操作成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								SearchList();
							}
						}
					}
				});
	}

	public List<CoCompactModel> getCompactlist() {
		return compactlist;
	}

	public void setCompactlist(List<CoCompactModel> compactlist) {
		this.compactlist = compactlist;
	}

	public List<CoOfferModel> getQuotelist() {
		return quotelist;
	}

	public void setQuotelist(List<CoOfferModel> quotelist) {
		this.quotelist = quotelist;
	}

	public List<CoPclassModel> getClasslist() {
		return classlist;
	}

	public void setClasslist(List<CoPclassModel> classlist) {
		this.classlist = classlist;
	}

	public List<CoglistModel> getEmplist() {
		return emplist;
	}

	public void setEmplist(List<CoglistModel> emplist) {
		this.emplist = emplist;
	}

}
