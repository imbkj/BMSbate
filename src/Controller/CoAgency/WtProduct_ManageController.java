package Controller.CoAgency;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoProduct.cpd_ListBll;

import Model.CoProductModel;

public class WtProduct_ManageController {
	private int cabc_id = Integer.parseInt(Executions.getCurrent()
			.getParameter("cabc_id").toString());
	private List<CoProductModel> coproductlist;
	cpd_ListBll bll = new cpd_ListBll();

	public WtProduct_ManageController() {
		coproductlist = bll.getAgProduct(cabc_id);
	}

	// 弹出修改窗口
	@Command("mod")
	@NotifyChange("coproductlist")
	public void mod(@BindingParam("cop") CoProductModel cop)
			throws SQLException {
		Map<String, CoProductModel> copMap = new HashMap<String, CoProductModel>();
		copMap.put("cop", cop);
		Window window = (Window) Executions.createComponents(
				"/CoProduct/cpd_info.zul", null, copMap);
		window.doModal();
		coproductlist = bll.getAgProduct(cabc_id);
	}

	// 删除
	@Command("del")
	@NotifyChange("coproductlist")
	public void del(@BindingParam("id") int copr_id) throws SQLException {
		if (Messagebox.show("是否删除?", "INFORMATION", Messagebox.YES
				| Messagebox.NO, Messagebox.INFORMATION) == Messagebox.YES) {
			int row = bll.delete(copr_id);
			if (row > 0) {
				Messagebox.show("删除成功!", "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
			} else {
				Messagebox.show("删除失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		coproductlist = bll.getAgProduct(cabc_id);
	}

	// 新增产品
	@Command("addProduct")
	@NotifyChange("coproductlist")
	public void addProduct() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cabc_id", cabc_id);
		Window window = (Window) Executions.createComponents(
				"../CoProduct/Agcpd_add.zul", null, map);
		window.doModal();
		coproductlist = bll.getAgProduct(cabc_id);
	}

	public List<CoProductModel> getCoproductlist() {
		return coproductlist;
	}

}
