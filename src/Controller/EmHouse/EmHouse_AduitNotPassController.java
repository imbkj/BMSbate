package Controller.EmHouse;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouse_EditBll;

import Model.EmHouseUpdateModel;

public class EmHouse_AduitNotPassController {
	private List<EmHouseUpdateModel> list = new ListModelList<>();
	private EmHouse_EditBll bll = new EmHouse_EditBll();

	public EmHouse_AduitNotPassController() {
		setList();
	}

	@Command
	@NotifyChange("list")
	public void aduit(@BindingParam("a") final EmHouseUpdateModel em) {
		if (em.getEmhu_houseid() != null && em.getEmhu_houseid().length() == 11) {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								Integer i=bll.modHouseupdate(em.getEmhu_id(), em.getEmhu_houseid());
								if (i>0) {
									Messagebox.show("更新成功.", "操作提示", Messagebox.OK,
											Messagebox.ERROR);
									setList();
								}else {
									Messagebox.show("更新失败.", "操作提示", Messagebox.OK,
											Messagebox.ERROR);
								}
							}
						}
					});
		} else {
			Messagebox.show("员工公积金编号有误", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public List<EmHouseUpdateModel> getList() {
		return list;
	}

	public void setList() {
		this.list = bll.gethouseupdateAduit();
	}

}
