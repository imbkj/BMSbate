package Controller.EmBenefit;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;

import Model.EmActyWarehouseHistoryModel;
import Model.EmActyWarehouseModel;

public class EmActy_WarehouseHistoryEditController {
	private EmActyWarehouseHistoryModel model = (EmActyWarehouseHistoryModel) Executions
			.getCurrent().getArg().get("model");
	private EmActyWarehouseModel m = (EmActyWarehouseModel) Executions
			.getCurrent().getArg().get("m");
	private Integer num;
	private EmActy_GiftInfoOperateBll bll = new EmActy_GiftInfoOperateBll();

	@Command
	public void summit(@BindingParam("win") Window win) {
		if (num != null) {
			if (num >= 0) {
				if(model.getHsry_wase_id()==null)
				{
					model.setHsry_wase_id(m.getWase_id());
				}
				Integer k = bll.WarehouseHistoryEdit(model, num);
				if (k > 0) {
					Messagebox.show("修改成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
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

	public EmActyWarehouseHistoryModel getModel() {
		return model;
	}

	public void setModel(EmActyWarehouseHistoryModel model) {
		this.model = model;
	}

	public EmActyWarehouseModel getM() {
		return m;
	}

	public void setM(EmActyWarehouseModel m) {
		this.m = m;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
