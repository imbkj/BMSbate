package Controller.EmHouse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import bll.EmHouse.EmHouse_FinanceBll;
import Model.EmHouseGJJMonthModel;

public class EmHouse_FinanceMonthController {
	private List<EmHouseGJJMonthModel> egmList;
	private EmHouse_FinanceBll bll = new EmHouse_FinanceBll();
	private Integer total;
	private Integer zz;
	private Integer single;

	private Window win = (Window) Path.getComponent("/winFM");

	public EmHouse_FinanceMonthController() {
		setEgmList("");
		setTotal("");
		setZz("");
		setSingle("");
	}

	@Command("search")
	@NotifyChange("egmList")
	public void search() {
		Textbox tb = (Textbox) win.getFellow("company");
		String name = tb.getValue() == null ? "" : tb.getValue();

		setEgmList(name);
		setTotal(name);
		setZz(name);
		setSingle(name);

	}

	@Command("uploadGL")
	@NotifyChange("egmList")
	public void uploadGL() {
		Map map = new HashMap();
		map.put("m", 1);
		Window window = (Window) Executions.createComponents(
				"EmHouse_UpLoad.zul", null, map);
		window.doModal();
		setEgmList("");
	}

	@Command("checkerr")
	public void checkerr() {
		if (!bll.getEmHouseGjjNum()) {
			if (bll.getEmHouseMonthGjjNum()) {

				if (!bll.getEmhouseMonthErr()) {

					Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
							Messagebox.Button.OK, Messagebox.Button.CANCEL },
							Messagebox.QUESTION,
							new EventListener<Messagebox.ClickEvent>() {
								@Override
								public void onEvent(ClickEvent event)
										throws Exception {
									// TODO Auto-generated method stub
									if (Messagebox.Button.OK.equals(event
											.getButton())) {
										Integer i = bll.checkMonthErr();
										Messagebox.show("发现" + i + "条无法匹配数据.",
												"操作提示", Messagebox.OK,
												Messagebox.INFORMATION);
									}
								}
							});
				} else {
					Messagebox.show("当前月份存在逻辑检查数据,点击[确定]重新检查", "操作提示",
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
										Integer i = bll.DelMonthErr();
										if (i > 0) {

											Integer j = bll.checkMonthErr();
											Messagebox.show("发现" + j
													+ "条无法匹配数据.", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
										} else {
											Messagebox.show("清空数据失败.", "操作提示",
													Messagebox.OK,
													Messagebox.ERROR);
										}
									}
								}
							});

				}
			}else {
				Messagebox.show("请上传当月台前数据.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("当月已上传台帐后数据.无法操作", "操作提示", Messagebox.OK,
					Messagebox.ERROR);

		}
	}

	@Command
	@NotifyChange("egmList")
	public void del(@BindingParam("a") final EmHouseGJJMonthModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.DelGjjmonthList(em
									.getEmhu_companyid());
							if (i > 0) {
								Messagebox.show("数据已删除", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								setEgmList("");
							}
						}
					}
				});

	}

	@Command("ClearGL")
	@NotifyChange("egmList")
	public void ClearGL() {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.DelGjjmonthList();

							Messagebox.show("数据已删除", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							setEgmList("");
						}
					}
				});

	}

	public List<EmHouseGJJMonthModel> getEgmList() {
		return egmList;
	}

	public void setEgmList(String name) {
		this.egmList = bll.getEmhousegjjMonth(name);
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(String name) {
		this.total = bll.getEmhousegjjMonthNum(name, null);
	}

	public Integer getZz() {
		return zz;
	}

	public void setZz(String name) {
		this.zz = bll.getEmhousegjjMonthNum(name, 0);
	}

	public Integer getSingle() {
		return single;
	}

	public void setSingle(String name) {
		this.single = bll.getEmhousegjjMonthNum(name, 1);
	}

}
