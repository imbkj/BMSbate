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
import Model.EmHouseGJJModel;

public class EmHouse_FinanceController {
	private List<EmHouseGJJModel> egmList;
	private EmHouse_FinanceBll bll = new EmHouse_FinanceBll();
	private Integer total;
	private Integer zz;
	private Integer single;

	private Window win = (Window) Path.getComponent("/winF");

	public EmHouse_FinanceController() {
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
		map.put("m", 0);
		Window window = (Window) Executions.createComponents(
				"EmHouse_UpLoad.zul", null, map);
		window.doModal();
		setEgmList("");
	}

	@Command("checkerr")
	public void checkerr() {
		String str = "确认提交数据?";
		if (bll.getEmhouseErr()) {
			str = "当前月份存在逻辑检查数据,点击[确定]重新检查";
		}
		Messagebox.show(str, "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.DelErr();
							Integer j = bll.checkErr();
							Messagebox.show("发现" + j + "条无法匹配数据.", "操作提示",
									Messagebox.OK, Messagebox.INFORMATION);

						}
					}
				});

	}

	@Command
	@NotifyChange("egmList")
	public void del(@BindingParam("a") final EmHouseGJJModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.DelGjjList(em.getEmhu_companyid());
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
							Integer i = bll.DelGjjList();

							Messagebox.show("数据已删除", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							setEgmList("");
						}
					}
				});

	}

	public List<EmHouseGJJModel> getEgmList() {
		return egmList;
	}

	public void setEgmList(String name) {
		this.egmList = bll.getEmHousegjj(name);
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(String name) {
		this.total = bll.getEmhousegjjNum(name, null);
	}

	public Integer getZz() {
		return zz;
	}

	public void setZz(String name) {
		this.zz = bll.getEmhousegjjNum(name, 0);
	}

	public Integer getSingle() {
		return single;
	}

	public void setSingle(String name) {
		this.single = bll.getEmhousegjjNum(name, 1);
	}

	

}
