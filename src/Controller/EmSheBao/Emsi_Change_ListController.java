package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_OperateBll;

import Model.EmSheBaoChangeModel;
import Util.UserInfo;

public class Emsi_Change_ListController {
	private String sql = Executions.getCurrent().getArg().get("sql").toString();
	private List<EmSheBaoChangeModel> ecList;
	private int count;
	private Emsc_DeclareSelBll selBll;

	public Emsi_Change_ListController() {
		selBll = new Emsc_DeclareSelBll();
		ecList = selBll.getEmscData(sql);
		count = ecList.size();
	}

	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 19).getChildren()
						.get(0);
				ckb.setChecked(check);
			} catch (Exception e) {
				// 排除不可选内容
			}
		}
	}

	// 批量确认
	@Command("declareData")
	@NotifyChange("ecList")
	public void declareSingle(@BindingParam("gdChange") Grid gdChange) {
		List<EmSheBaoChangeModel> list = getPageData(gdChange);
		Emsi_OperateBll opbll = new Emsi_OperateBll();
		try {
			String[] message = opbll
					.ChangeDeclare(list, UserInfo.getUsername());
			if ("1".equals(message[0]) || "0".equals(message[0])) {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				ecList = selBll.getEmscData(sql);
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("确认社保变更出错！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 获取页面勾选项
	private List<EmSheBaoChangeModel> getPageData(Grid gdChange) {
		List<EmSheBaoChangeModel> list = new ArrayList<EmSheBaoChangeModel>();
		List<Component> rows = gdChange.getRows().getChildren();
		EmSheBaoChangeModel m = null;
		Row row;
		for (Object obj : rows) {
			row = (Row) obj;
			try {
				// 判断该行是否勾选
				if (((Checkbox) row.getChildren().get(19).getChildren().get(0))
						.isChecked()) {
					m = (EmSheBaoChangeModel) row.getValue();
					list.add(m);
				}
			} catch (Exception e) {
				// 排除不可勾选的内容
			}
		}
		return list;
	}

	public List<EmSheBaoChangeModel> getEcList() {
		return ecList;
	}

	public int getCount() {
		return count;
	}

}
