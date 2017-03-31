package Controller.Batchprocessing;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import bll.Batchprocessing.EmHouse_BathOperateBll;

import Model.EmHouseChangeModel;

public class BatP_HouseAddController {
	private List<EmHouseChangeModel> list = (List<EmHouseChangeModel>) Executions
			.getCurrent().getArg().get("list");

	@Command
	@NotifyChange("list")
	public void summit() {
		List<EmHouseChangeModel> failList = new ArrayList<EmHouseChangeModel>();
		EmHouse_BathOperateBll bll = new EmHouse_BathOperateBll();
		Integer k = 0;
		for (EmHouseChangeModel m : list) {
			Integer kl = bll.EmHouseUpload(m);
			if (kl > 0) {
				k = k + 1;
				m.setMessage(true);
				m.setErrorMsg("导入成功");
			} else {
				m.setErrorMsg("导入失败");
				m.setMessage(false);
			}
		}
		if (k == list.size()) {
			Messagebox
					.show("导入成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else if (k == 0) {
			Messagebox.show("导入失败", "提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			Messagebox.show("部分数据导入成功", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
	}

	public List<EmHouseChangeModel> getList() {
		return list;
	}

	public void setList(List<EmHouseChangeModel> list) {
		this.list = list;
	}

}
