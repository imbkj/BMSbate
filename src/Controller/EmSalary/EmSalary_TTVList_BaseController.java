package Controller.EmSalary;

import java.math.BigDecimal;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Button;
import org.zkoss.zul.Messagebox;

import Model.EmSalaryHistoryDataModel;
import bll.EmSalary.EmSalary_DataOperateBll;
import bll.EmSalary.EmSalary_HisSelBll;

public class EmSalary_TTVList_BaseController {
	private final String date = Executions.getCurrent().getParameter("date")
			.toString();
	private final String state = Executions.getCurrent().getParameter("state")
			.toString();
	private final String gzaddname = Executions.getCurrent()
			.getParameter("gzaddname").toString();
	private Map<Integer, EmSalaryHistoryDataModel> map;
	private EmSalary_HisSelBll bll = new EmSalary_HisSelBll();
	private EmSalary_DataOperateBll oBll = new EmSalary_DataOperateBll();
	private boolean ifData;
	private int sumData = 0;
	private BigDecimal totalEx = BigDecimal.ZERO;

	public EmSalary_TTVList_BaseController() {
		pageStart();
	}

	@Command("upTTVState")
	@NotifyChange({ "map", "ifData", "sumData", "totalEx" })
	public void upTTVState(@BindingParam("cid") Integer cid,@BindingParam("bt") Button bt) {
		String[] message;
		message = oBll.upTTVState(cid, date);
		if (message[0].equals("1")) {
			// 弹出提示
			Messagebox.show(message[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			bt.setDisabled(true);
			bt.setLabel("已审核");
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@NotifyChange({ "map", "ifData", "sumData", "totalEx" })
	public void pageStart() {
		map = bll.getTTVData(date, state, gzaddname);
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

	public String getDate() {
		return date;
	}

}
