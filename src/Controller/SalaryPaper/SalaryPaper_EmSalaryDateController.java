package Controller.SalaryPaper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;

import Model.EmSalaryBaseAddItemModel;
import Model.EmSalaryDataModel;
import Model.SalaryPaperCoModel;
import bll.SalaryPaper.SalaryPaperBll;

public class SalaryPaper_EmSalaryDateController {

	private List<SalaryPaperCoModel> getCoList;
	private List<EmSalaryDataModel> salaryList;
	private List<EmSalaryDataModel> salaryWinList;
	private List<EmSalaryBaseAddItemModel> itemList;
	private String ownmonth;
	private int cid;
	private Include icBusinessList;
	private String url;
	private SalaryPaperBll spb = new SalaryPaperBll();

	public SalaryPaper_EmSalaryDateController() {
		init();
	}

	public void init() {

		getCoList = spb.getCoSetList();
	}

	@Command
	public void own(@BindingParam("month") Date d) {

		if (d != null) {
			ownmonth = new SimpleDateFormat("yyyyMM").format(d);
		}
	}

	@Command
	@NotifyChange({ "itemList", "salaryWinList" })
	public void search(@BindingParam("cocombox") Combobox combobox) {
		if (combobox.getSelectedItem()!=null) {
		cid = Integer.valueOf(String.valueOf(combobox.getSelectedItem()
				.getValue()));
		url = "/SalaryPaper/SalaryPaper_EmSalaryDateInclude.zul" + "?cid="
				+ cid + "&ownmonth=" + ownmonth;
		BindUtils.postNotifyChange(null, null, this, "url");
		//System.out.println(url);

		salaryList = spb.getSalaryList(cid, ownmonth); // 工资单数据
		itemList = spb.getItemList(cid, ownmonth); // 工资单项目
		setEmSalaryDataModelOfItemList(salaryList, itemList);
		salaryWinList = salaryList;
		refreshListSrc();
		}
		else {
			if (combobox.getSelectedItem()==null) {
				Messagebox.show("请选择公司。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 获取include组件
	@Command("setInclude")
	public void setInclude(@BindingParam("com") Include com) {
		icBusinessList = com;
	}

	// 刷新业务列表
	private void refreshListSrc() {
		try {
			/*
			 * Include icBusinessList = (Include) Path
			 * .getComponent("/winMothlyBillManage/icBusinessList");
			 */
			icBusinessList.invalidate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<SalaryPaperCoModel> getGetCoList() {
		return getCoList;
	}

	public void setGetCoList(List<SalaryPaperCoModel> getCoList) {
		this.getCoList = getCoList;
	}

	public List<EmSalaryBaseAddItemModel> getGzdItemList() {
		return itemList;
	}

	// 初始化EmSalaryDataModel的itemList
	private void setEmSalaryDataModelOfItemList(List<EmSalaryDataModel> sdList,
			List<EmSalaryBaseAddItemModel> itList) {
		for (EmSalaryDataModel m : sdList) {
			try {
				m.setGzdItemList(itList);
			} catch (Exception e) {
			}
		}
	}

	public String getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(String ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<EmSalaryDataModel> getSalaryWinList() {
		return salaryWinList;
	}

	public void setSalaryWinList(List<EmSalaryDataModel> salaryWinList) {
		this.salaryWinList = salaryWinList;
	}

	public List<EmSalaryBaseAddItemModel> getItemList() {
		return itemList;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
