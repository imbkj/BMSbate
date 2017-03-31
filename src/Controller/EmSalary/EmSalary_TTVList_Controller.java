package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmSalary.EmSalary_DataOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;

import Util.DateStringChange;

public class EmSalary_TTVList_Controller {
	private String icSrc;
	private Include icBase;
	private EmSalary_DataOperateBll oBll = new EmSalary_DataOperateBll();
	private List<String> gzUserList;
	private EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
	
	public EmSalary_TTVList_Controller() {
		gzUserList = bll.getGzUser();
	}

	@Command("search")
	public void search(@BindingParam("db") Datebox db,
			@BindingParam("cb") Combobox cb,
			@BindingParam("gzaddname") Combobox gzaddname) {
		try {
			if (db.getValue() != null) {
				pageLoad(DateStringChange.DatetoSting(db.getValue(),
						"yyyy-MM-dd"), cb.getValue(), gzaddname.getValue());
			} else {
				db.focus();
				Messagebox.show("请选择发放日期。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pageLoad(String date, String state, String gzaddname) {
		icSrc = "../EmSalary/EmSalary_TTVList_Base.zul?date=" + date
				+ "&state=" + state + "&gzaddname=" + gzaddname;
		BindUtils.postNotifyChange(null, null, this, "icSrc");
		icBase.invalidate();
	}

	@Command("upState")
	public void upState(@BindingParam("db") final Datebox db,
			@BindingParam("cb") final Combobox cb,
			@BindingParam("gzaddname") final Combobox gzaddname) {
		try {
			if (db.getValue() != null) {

				Messagebox.show(
						"确认审核"
								+ DateStringChange.DatetoSting(db.getValue(),
										"yyyy-MM-dd") + "发放的所有工资数据?", "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK,
								Messagebox.Button.CANCEL },
						Messagebox.QUESTION,
						new EventListener<Messagebox.ClickEvent>() {
							@Override
							public void onEvent(ClickEvent event)
									throws Exception {
								// TODO Auto-generated method stub

								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									String[] message;
									message = oBll
											.upTTVStateAll(DateStringChange
													.DatetoSting(db.getValue(),
															"yyyy-MM-dd"));
									if (message[0].equals("1")) {
										// 弹出提示
										Messagebox.show(message[1], "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);

										// 刷新页面
										pageLoad(DateStringChange.DatetoSting(
												db.getValue(), "yyyy-MM-dd"),
												cb.getValue(), gzaddname
														.getValue());

									} else {
										// 弹出提示
										Messagebox
												.show(message[1], "操作提示",
														Messagebox.OK,
														Messagebox.ERROR);
									}
								}
							}
						});
			} else {
				db.focus();
				Messagebox.show("请选择发放日期。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获取include组件
	@Command("setInclude")
	public void setInclude(@BindingParam("com") Include com) {
		icBase = com;
	}

	public String getIcSrc() {
		return icSrc;
	}

	public List<String> getGzUserList() {
		return gzUserList;
	}

	public void setGzUserList(List<String> gzUserList) {
		this.gzUserList = gzUserList;
	}
	
	
}
