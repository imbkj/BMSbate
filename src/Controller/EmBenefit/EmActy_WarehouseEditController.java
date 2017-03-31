package Controller.EmBenefit;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;

import Model.EmActyWarehouseModel;

public class EmActy_WarehouseEditController {
	private EmActyWarehouseModel m = (EmActyWarehouseModel) Executions
			.getCurrent().getArg().get("model");
	private Integer num;
	private EmActy_GiftInfoOperateBll bll = new EmActy_GiftInfoOperateBll();

	@Command
	public void summit(@BindingParam("win") Window win) {
		if (num != null) {
			if (num >= 0) {
				String editcontent="数量由："+m.getWase_nownum()+"修改为："+num;
				Integer k = bll.WarehouseEdit(m, num,editcontent);
				if (k > 0) {
					Messagebox.show("修改成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("修改失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("修改后的数量不能是负数", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请填入修改后的数量", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public EmActyWarehouseModel getM() {
		return m;
	}

	public void setM(EmActyWarehouseModel m) {
		this.m = m;
	}

}
