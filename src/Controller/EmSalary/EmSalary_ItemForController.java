package Controller.EmSalary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.callback.ConfirmationCallback;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Model.CoSalaryItemFormulaModel;
import Model.CoSalaryItemIDInfoModel;
import Util.UserInfo;

public class EmSalary_ItemForController {
	private List<CoSalaryItemFormulaModel> itemFormulaList;
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private List<CoSalaryItemIDInfoModel> itemIDInfoList;

	// 获取CoSalaryItemIDInfo表id
	private int csii_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());

	private String cid;
	private int csii_tapr_id; // 任务单id
	private int csii_edit_state;
	private String csii_itemid;

	// 获取用户名
	String username = UserInfo.getUsername();

	public EmSalary_ItemForController() {
		// 获取项目组合数据
		itemIDInfoList = ifSBll.getCoSalaryItemID(" AND csii_id="
				+ String.valueOf(csii_id));

		cid = String.valueOf(itemIDInfoList.get(0).getCid()); // 公司编号
		csii_edit_state = itemIDInfoList.get(0).getCsii_edit_state(); // 是否可以编辑
		csii_itemid=String.valueOf(itemIDInfoList.get(0).getCsii_itemid());	//工资项目组合编号
		
		//获取未参加工资算法的工资项目
		itemFormulaList=ifSBll.getNotJoinFormula(csii_itemid);
	}

	// 弹出操作页面
	@Command("openZUL")
	public void openZUL(@BindingParam("url") String url) {
		Map map = new HashMap();
		map.put("cid", cid);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		
		//获取未参加工资算法的工资项目
		itemFormulaList=ifSBll.getNotJoinFormula(csii_itemid);
	}

	// 完成设置并结束任务单
	@Command("finish")
	@NotifyChange({"itemIDInfoList"})
	public void finish(@BindingParam("win") final Window win) {

		Messagebox.show("确定完成设置并结束任务单?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							// 任务单id
							csii_tapr_id = itemIDInfoList.get(0)
									.getCsii_tapr_id();
							// 调用任务单方法
							String[] message = ifOBll.clcFinishSalaryItemId(
									csii_id, csii_tapr_id, username,Integer.parseInt(cid));

							if (message[0].equals("1")) {
								// 刷新
								itemIDInfoList = ifSBll
										.getCoSalaryItemID(" AND csii_id="
												+ String.valueOf(csii_id));

								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.INFORMATION);
								win.detach();

							} else {
								Messagebox.show(message[1], "操作提示",
										Messagebox.OK, Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<CoSalaryItemFormulaModel> getItemFormulaList() {
		return itemFormulaList;
	}

	public void setItemFormulaList(
			List<CoSalaryItemFormulaModel> itemFormulaList) {
		this.itemFormulaList = itemFormulaList;
	}

	public int getCsii_edit_state() {
		return csii_edit_state;
	}

	public void setCsii_edit_state(int csii_edit_state) {
		this.csii_edit_state = csii_edit_state;
	}

}
