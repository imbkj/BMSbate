package Controller.SocialInsurance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;

import Model.SocialInsuranceAlgorithmViewModel;
import Model.SocialInsuranceClassInfoViewModel;
import bll.SocialInsurance.Algorithm_InfoBll;

public class Algorithm_SearchController {
	private final int sial_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("sial_id").toString());
	private SocialInsuranceAlgorithmViewModel saModel;
	private List<SocialInsuranceClassInfoViewModel> classList;
	private List<String[]> execDateList;
	private Algorithm_InfoBll bll;


	public Algorithm_SearchController() {
		bll = new Algorithm_InfoBll();
		setExecDateList();
		setSaModel(sial_id);
		setClassList(sial_id);
	}

	// 根据执行时间查询
	@Command("searchAl")
	@NotifyChange({ "saModel", "classList" })
	public void searchAl(@BindingParam("cb") Combobox cb) {
		int id = Integer.parseInt((String) cb.getSelectedItem().getValue());
		setSaModel(id);
		setClassList(id);
	}

	public SocialInsuranceAlgorithmViewModel getSaModel() {
		return saModel;
	}

	private void setSaModel(int id) {
		this.saModel = bll.getSiAlBase(id);
		saModel.setSial_execdatestr(DatetoSting(saModel.getSial_execdate()));
	}

	// Date类型转换String
	private String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
		String Date = sdf.format(d);
		return Date;
	}

	public List<SocialInsuranceClassInfoViewModel> getClassList() {
		return classList;
	}

	private void setClassList(int id) {
		this.classList = bll.getSiAlInfo(id);
	}

	public List<String[]> getExecDateList() {
		return execDateList;
	}

	private void setExecDateList() {
		this.execDateList = bll.getExecdateList(sial_id);
	}

}
