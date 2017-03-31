package Controller.EmSalary;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.CoBase.CoBase_SelectDal;

import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Model.CoFormulaInfoModel;
import Model.EmBaseESInCFInModel;
import Util.UserInfo;

public class EmSalary_ForAssignController {
	private List<EmBaseESInCFInModel> emList;
	private List<CoFormulaInfoModel> formulaList;
	/*
	 * private List<CoBaseModel> clientList; private List<CoBaseModel>
	 * cobaseList;
	 */
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private CoBase_SelectDal cobaDal = new CoBase_SelectDal();

	private String cid = String.valueOf(Executions.getCurrent().getArg()
			.get("cid").toString());

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public EmSalary_ForAssignController() throws SQLException {

		if (!"".equals(cid)) {
			String sql = "";
			sql = sql + " AND cid=" + cid;
			emList = ifSBll.getEmBaESInList(sql);

			// 获取最新月份csii_itemid下的算法
			String csii_itemid = "";

			if (ifSBll.getCoSalaryItemID(" AND cid=" + cid).size() > 0) {
				csii_itemid = ifSBll.getCoSalaryItemID(" AND cid=" + cid)
						.get(0).getCsii_itemid();
			}

			// 获取算法
			formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='"
					+ csii_itemid + "'");
		}

	}

	@Command("search")
	@NotifyChange({ "emList" })
	public void search(@BindingParam("type") Combobox type) throws SQLException {
		if (type.getSelectedItem() != null) {
			String sql = "";
			sql = sql + " AND cid=" + cid;
			if ("未分配".equals(type.getSelectedItem().getLabel())) {
				sql = sql + " AND (cfin_id is null OR cfin_id=0)";
			} else if ("已分配".equals(type.getSelectedItem().getLabel())) {
				sql = sql + " AND cfin_id is not null AND cfin_id>0";
			}
			emList = ifSBll.getEmBaESInList(sql);
		}
	}

	/*
	 * // 获取公司下拉框
	 * 
	 * @Command("searchCobase")
	 * 
	 * @NotifyChange({ "cobaseList", "emList", "formulaList" }) public void
	 * searchCobase(@BindingParam("client") Combobox client) throws SQLException
	 * { if (client.getSelectedItem() != null) { cobaseList =
	 * cobaDal.getCobase(" AND coba_client='" +
	 * client.getSelectedItem().getLabel() + "'"); emList = null; formulaList =
	 * null; } }
	 * 
	 * // 获取员工及算法列表
	 * 
	 * @Command("search")
	 * 
	 * @NotifyChange({ "emList", "formulaList" }) public void
	 * search(@BindingParam("cid") Combobox cid) throws SQLException { if
	 * (cid.getSelectedItem() != null) { String sql = ""; sql = sql +
	 * " AND cid=" + String.valueOf(cid.getSelectedItem().getValue()); emList =
	 * ifSBll.getEmBaESInList(sql);
	 * 
	 * // 获取最新月份csii_itemid下的算法 String csii_itemid = "";
	 * 
	 * if (ifSBll.getCoSalaryItemID( " AND cid=" +
	 * String.valueOf(cid.getSelectedItem().getValue())) .size() > 0) {
	 * csii_itemid = ifSBll .getCoSalaryItemID( " AND cid=" +
	 * String.valueOf(cid.getSelectedItem() .getValue())).get(0)
	 * .getCsii_itemid(); }
	 * 
	 * // 获取算法 formulaList = ifSBll.getFormulaInfo(" AND csii_itemid='" +
	 * csii_itemid + "'"); } }
	 */
	@Command("submit")
	@NotifyChange({ "emList" })
	public void submit(@BindingParam("cid") Combobox cid,
			@BindingParam("emlist") Listbox emlist,
			@BindingParam("cfin_id") Listbox cfin_id,
			@BindingParam("winForAss") final Window winForAss) throws Exception {

		Set<Listitem> emCheck = emlist.getSelectedItems();

		if (!emCheck.isEmpty() && cfin_id.getSelectedItem() != null) {
			int chk_i = 0;
			int chk_j = 0;

			for (Listitem c : emCheck) {
				chk_i = chk_i + 1;

				// 调用方法
				String[] message = ifOBll.assFormulaInfo(
						((EmBaseESInCFInModel) c.getValue()).getCid(),
						((EmBaseESInCFInModel) c.getValue()).getGid(), String
								.valueOf(((CoFormulaInfoModel) cfin_id
										.getSelectedItem().getValue())
										.getCfin_id()), username);

				if (message[0].equals("0")) {
					chk_j = chk_j + 1;
				}

			}

			if (chk_i == chk_j) {
				String sql = "";
				sql = sql + " AND cid=" + cid;
				emList = ifSBll.getEmBaESInList(sql);

				// 弹出提示
				Messagebox.show("分配成功！", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);

			} else {
				// 弹出提示
				Messagebox.show("分配出错，请重新操作！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			if (emCheck.isEmpty()) {
				// 弹出提示
				Messagebox.show("请选择员工！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else if (cfin_id.getSelectedItem() == null) {
				// 弹出提示
				Messagebox.show("请选择算法！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public List<EmBaseESInCFInModel> getEmList() {
		return emList;
	}

	public void setEmList(List<EmBaseESInCFInModel> emList) {
		this.emList = emList;
	}

	public List<CoFormulaInfoModel> getFormulaList() {
		return formulaList;
	}

	public void setFormulaList(List<CoFormulaInfoModel> formulaList) {
		this.formulaList = formulaList;
	}
	/*
	 * public List<CoBaseModel> getClientList() { return clientList; }
	 * 
	 * public void setClientList(List<CoBaseModel> clientList) { this.clientList
	 * = clientList; }
	 * 
	 * public List<CoBaseModel> getCobaseList() { return cobaseList; }
	 * 
	 * public void setCobaseList(List<CoBaseModel> cobaseList) { this.cobaseList
	 * = cobaseList; }
	 */
}
