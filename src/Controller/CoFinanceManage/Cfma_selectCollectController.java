package Controller.CoFinanceManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceCollectModel;
import Util.SingleBllFactory;
import bll.CoFinanceManage.Cfma_SelBll;

/**
 * 打印收款选择界面
 * 
 * @author Administrator
 * 
 */
public class Cfma_selectCollectController {

	List<CoFinanceCollectModel> cfcmList = (List<CoFinanceCollectModel>) (Executions
			.getCurrent().getArg().get("cfcmList"));

	private List<CoFinanceCollectModel> cfcList;
	private Cfma_SelBll csb = SingleBllFactory.getInstance().getCsb();

	public Cfma_selectCollectController() {
		if (cfcmList.size() != 0) {
			// 根据公司id查询账单
			cfcList = csb.getCfcListByCid(cfcmList);
		} else {
			cfcList = new ArrayList<CoFinanceCollectModel>();
		}

	}

	/**
	 * 全选
	 * 
	 * @param gridId
	 * @param checkall
	 */
	@Command
	public void allCheck(@BindingParam("gridid") Grid gridId,
			@BindingParam("check") String checkall) {
		for (int i = 0; i < cfcList.size(); i++) {
			Checkbox checkbox = (Checkbox) gridId.getCell(i, 0).getChildren()
					.get(0);
			if (checkall != null && !checkall.equals("")
					&& checkall.equals("checkall")) {
				checkbox.setChecked(true);
			}
		}
	}

	/**
	 * 反选
	 * 
	 * @param gridId
	 * @param allnotcheck
	 */
	@Command
	public void allnotCheck(@BindingParam("gridid") Grid gridId) {
		for (int i = 0; i < cfcList.size(); i++) {
			Checkbox checkbox = (Checkbox) gridId.getCell(i, 0).getChildren()
					.get(0);
			if (checkbox.isChecked() == true) {
				checkbox.setChecked(false);
			} else {
				checkbox.setChecked(true);
			}
		}
	}

	// 打印下一步
	@Command
	public void nextStep(@BindingParam("gridid") Grid grid) {
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < cfcList.size(); i++) {
			Checkbox c = (Checkbox) grid.getCell(i, 0).getChildren().get(0);
			if (c.isChecked() == true) {
				list.add(c.getValue().toString());
			}
		}
		if (list.size() != 0) {
			System.out.println("check=" + list.size() + "value=" + list.get(0));
			List<CoFinanceCollectModel> l = csb.getCollectListPrint(list);
			for (int i = 0; i < cfcList.size(); i++) {
				for (int j = 0; j < l.size(); j++) {
					if (cfcList.get(i).getCfco_cfmb_number()
							.equals(l.get(j).getCfco_cfmb_number())) {
						l.get(j)
								.setCoba_client(cfcList.get(i).getCoba_client());
						l.get(j).setCoba_company(
								cfcList.get(i).getCoba_company());
						l.get(j).setOwnmonth(cfcList.get(i).getOwnmonth());
						l.get(j)
								.setCfco_remark(cfcList.get(i).getCfco_remark());
						l.get(j).setCfco_addtime(
								cfcList.get(i).getCfco_addtime());
					}
				}
			}
			Map<String, List<CoFinanceCollectModel>> map = new HashMap<String, List<CoFinanceCollectModel>>();
			map.put("cfcmList", l);
			Window window = (Window) Executions.createComponents(
					"Cfma_invoiceAddDoc.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("未选择有效账单", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<CoFinanceCollectModel> getCfcList() {
		return cfcList;
	}

	public void setCfcList(List<CoFinanceCollectModel> cfcList) {
		this.cfcList = cfcList;
	}

}
