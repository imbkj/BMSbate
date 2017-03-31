package Controller.CoServicePolicy;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoServicePolicy.SePy_CityPolicyOperateBll;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;

import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;

public class SyPo_TitleListController {
	private CoServicePolicyTypeModel model = (CoServicePolicyTypeModel) Executions
			.getCurrent().getArg().get("typemodel");
	private SePy_CityPolicySelectBll bll = new SePy_CityPolicySelectBll();
	private List<CoServicePolicyTitleModel> list = bll
			.getCoServicePolicyTitle(" and item_type_id=" + model.getNote_id());
	private SePy_CityPolicyOperateBll obll = new SePy_CityPolicyOperateBll();

	@Command
	public void edit(@BindingParam("model") CoServicePolicyTitleModel m) {
		
	}

	@Command
	@NotifyChange("list")
	public void del(@BindingParam("model") final CoServicePolicyTitleModel m) {
		Messagebox.show("确认删除数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer K = obll.updateCoServicePolicyTitleState(m
									.getItem_id());
							if (K > 0) {
								Messagebox.show("删除成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								list = bll
										.getCoServicePolicyTitle(" and item_type_id="
												+ model.getNote_id());
							} else {
								Messagebox.show("删除失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<CoServicePolicyTitleModel> getList() {
		return list;
	}

	public void setList(List<CoServicePolicyTitleModel> list) {
		this.list = list;
	}

}
