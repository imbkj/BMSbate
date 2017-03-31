package Controller.EmSalary;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;

import Model.EmPayModel;
import Model.EmSalaryHistoryDataModel;
import bll.EmSalary.EmSalary_HisSelBll;

public class EmSalary_HistoryList_BaseController {
	private final String date = Executions.getCurrent().getParameter("date")
			.toString();
	private final int batch = Integer.parseInt(Executions.getCurrent()
			.getParameter("batch").toString());
	private Map<Integer, EmSalaryHistoryDataModel> map;
	private EmSalary_HisSelBll bll;
	private boolean ifData;
	private int sumData = 0;
	private BigDecimal totalEx = BigDecimal.ZERO;

	private List<EmPayModel> empaList;
	private int empa_count = 0;
	private BigDecimal empa_total = BigDecimal.ZERO;

	public EmSalary_HistoryList_BaseController() {
		bll = new EmSalary_HisSelBll();
		map = bll.getHisData(date, batch);
		if (map.size() > 0) {
			ifData = true;
			Object s[] = map.keySet().toArray();
			for (int i = 0; i < map.size(); i++) {
				sumData += map.get(s[i]).getEsdaListSize();
				totalEx = totalEx
						.add(map.get(s[i]).getSumModel().getEsda_pay());
			}
		} else {
			ifData = false;
		}

		// 支付模块数据
		empaList = bll.getEmPayList(date);
		if (empaList.size() > 0) {
			empa_count = empaList.size();
		}
		empa_total = bll.getEmPayTotal(date).getEmpa_fee();
	}

	public Map<Integer, EmSalaryHistoryDataModel> getMap() {
		return map;
	}

	public boolean isIfData() {
		return ifData;
	}

	public int getSumData() {
		return sumData;
	}

	public BigDecimal getTotalEx() {
		return totalEx;
	}

	public int getEmpa_count() {
		return empa_count;
	}

	public void setEmpa_count(int empa_count) {
		this.empa_count = empa_count;
	}

	public BigDecimal getEmpa_total() {
		return empa_total;
	}

	public void setEmpa_total(BigDecimal empa_total) {
		this.empa_total = empa_total;
	}

	public List<EmPayModel> getEmpaList() {
		return empaList;
	}

	public void setEmpaList(List<EmPayModel> empaList) {
		this.empaList = empaList;
	}

}
