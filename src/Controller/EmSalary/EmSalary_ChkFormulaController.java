package Controller.EmSalary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;

import bll.CoAgency.WtAgency_DisCitySelBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSalary.ItemFormula_SelectBll;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Util.DateStringChange;

public class EmSalary_ChkFormulaController {
	private EmSalary_SalarySelBll sbll = new EmSalary_SalarySelBll();
	private WtAgency_DisCitySelBll cBll = new WtAgency_DisCitySelBll();
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();

	private List<String> citylist; // 个税申报地和工资发放地
	private List<EmSalaryBaseAddItemModel> csIIInfoList;
	private int cfin_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cfin_id").toString());
	private int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private String nowMonth = DateStringChange
			.DatetoSting(new Date(), "yyyyMM");

	private String cfin_name;

	private EmSalaryDataModel esdaM = new EmSalaryDataModel();

	public EmSalary_ChkFormulaController() throws Exception {
		citylist = cBll.getCityName(); // 获取个税申报地
		csIIInfoList = sbll.getCSIIInfo(cid, Integer.parseInt(nowMonth), -1);
		cfin_name = ifSBll
				.getFormulaInfo(" AND cfin_id=" + String.valueOf(cfin_id))
				.get(0).getCfin_name();
	}

	// 提交方法
	@Command("submit")
	@NotifyChange("csIIInfoList")
	public void submit(@BindingParam("gdItem") Grid gdItem,
			@BindingParam("hpro") Combobox hpro,
			@BindingParam("nationality") Combobox nationality,
			@BindingParam("taxplace") Combobox taxplace) {
		if (hpro.getSelectedItem() != null
				&& nationality.getSelectedItem() != null
				&& taxplace.getSelectedItem() != null) {
			List<EmSalaryBaseAddItemModel> list = getPageItem(gdItem);
			esdaM.setEsda_nationality(nationality.getSelectedItem().getLabel());
			esdaM.setEsda_hpro(hpro.getSelectedItem().getLabel());
			esdaM.setEsda_taxplace(taxplace.getSelectedItem().getLabel());
			esdaM.setCfin_id(cfin_id);
			esdaM.insertItemList(list);
			esdaM.sumItemInfoToItemList();
			csIIInfoList = esdaM.getItemList();
		} else {
			// 弹出提示
			Messagebox.show("请选择“雇佣关系”，“国籍”，“个税申报地”", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	// 获取页面项目信息
	private List<EmSalaryBaseAddItemModel> getPageItem(Grid gdItem) {
		List<EmSalaryBaseAddItemModel> list = new ArrayList<EmSalaryBaseAddItemModel>();
		List<Component> rows = gdItem.getRows().getChildren();
		EmSalaryBaseAddItemModel m = null;
		Row comp = null;
		for (Object obj : rows) {
			comp = (Row) obj;
			m = comp.getValue();
			try {

				m.setAmount(((Decimalbox) comp.getChildren().get(1)
						.getChildren().get(0)).getValue());
			} catch (Exception e) {
				m.setAmount(new BigDecimal(0));
			}
			if (m.getAmount() == null) {
				m.setAmount(BigDecimal.ZERO);

			}
			list.add(m);
		}
		return list;
	}

	public List<EmSalaryBaseAddItemModel> getCsIIInfoList() {
		return csIIInfoList;
	}

	public void setCsIIInfoList(List<EmSalaryBaseAddItemModel> csIIInfoList) {
		this.csIIInfoList = csIIInfoList;
	}

	public String getCfin_name() {
		return cfin_name;
	}

	public void setCfin_name(String cfin_name) {
		this.cfin_name = cfin_name;
	}

	public List<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(List<String> citylist) {
		this.citylist = citylist;
	}

}
