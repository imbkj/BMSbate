package Controller.EmZYT;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;

import Model.EmZYTModel;
import Util.DateStringChange;

public class EmZYT_AdTypeController {
	private EmZYT_OperateBll oBll = new EmZYT_OperateBll();
	private DateStringChange dsc = new DateStringChange();
	private List<EmZYTModel> emztList = (List<EmZYTModel>) Executions
			.getCurrent().getArg().get("emztList");
	private List sclassList; // 委托事件
	private int listSize = emztList.size();
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private String chkString = "";
	private Integer upType = 0;

	public EmZYT_AdTypeController() {
		sclassList = sbll.getSclass(); // 委托事件下拉框
	}

	// 提交页面
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("sclass") final Combobox sclass,
			@BindingParam("ownmonth") final Datebox ownmonth) {

		// 判断
		if (chkPage(sclass, ownmonth)) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						String msg = "";
						int j = 0;
						int k = 0;
						for (int i = 0; i < emztList.size(); i++) {
							k = k + 1;

							// 判断是更新所属月份还是委托事件
							EmZYTModel m = new EmZYTModel();
							if (upType == 1) {// 所属月份
								m.setId(emztList.get(i).getId());
								m.setOwnmonth(Integer.parseInt(dsc.DatetoSting(
										ownmonth.getValue(), "yyyyMM")));
								String[] message = oBll.upEmZYTOwnmonth(m);

								if (message[0].equals("1")) {// 成功
									j = j + 1;
								}
							} else if (upType == 2) {// 委托事件
								m.setId(emztList.get(i).getId());
								m.setEmzt_class(sclass.getSelectedItem()
										.getLabel().toString());
								String[] message = oBll.upEmZYTAdType(m);

								if (message[0].equals("1")) {// 成功
									j = j + 1;
								}
							}

						}

						if (j == k) {
							msg = "操作成功！";
						} else {
							msg = "部分数据操作不成功，请检查！";
						}

						EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
							public void onEvent(ClickEvent event)
									throws Exception {
								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									// 关闭页面
									win.detach();
								}
							}
						};
						// 弹出提示
						Messagebox
								.show(msg,
										"操作提示",
										new Messagebox.Button[] { Messagebox.Button.OK },
										Messagebox.INFORMATION, clickListener);
					}
				}
			};

			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION, clickListener);
		} else {
			Messagebox.show(chkString, "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	public boolean chkPage(Combobox sclass, Datebox ownmonth) {
		boolean result = true;

		if (sclass.getSelectedItem() != null && ownmonth.getValue()!=null) {
			result = false;
			chkString = "不能同时修改“委托事件”和“所属月份”";
		} else if (sclass.getSelectedItem() == null
				&& ownmonth.getValue()==null) {
			result = false;
			chkString = "请选择“委托事件”或“所属月份”";
		} else {
			if (ownmonth.getValue()!=null) {// 更新所属月份
				upType = 1;
			} else if (sclass.getSelectedItem() != null) {// 更新委托事件
				upType = 2;
			}
		}

		return result;
	}

	public int getListSize() {
		return listSize;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public List getSclassList() {
		return sclassList;
	}

	public void setSclassList(List sclassList) {
		this.sclassList = sclassList;
	}

}
