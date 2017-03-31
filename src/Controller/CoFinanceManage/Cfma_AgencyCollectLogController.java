package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import bll.CoFinanceManage.Cfma_AgencySelBll;

import Model.CoAgencyBaseModel;
import Model.CoFinanceAgencyCollectModel;

public class Cfma_AgencyCollectLogController {
	private final int coab_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("coab_id").toString());
	private String city;
	private String coab_name;
	private List<CoFinanceAgencyCollectModel> collectList;
	private CoAgencyBaseModel coabModel;
	private String sumPaidin;

	public Cfma_AgencyCollectLogController() {
		Cfma_AgencySelBll bll = new Cfma_AgencySelBll();
		collectList = bll.getAgencyCollectList(coab_id);
		coabModel = bll.getAgency(coab_id);
		coab_name = coabModel.getCoab_name();
		city = coabModel.getCoab_city();
		// 合计收款
		sumPaidin();
	}

	// 合计收款
	public void sumPaidin() {
		if (collectList.size() == 0) {
			sumPaidin = "0";
		} else {
			BigDecimal sum = BigDecimal.ZERO;
			for (CoFinanceAgencyCollectModel m : collectList) {
				sum = sum.add(m.getCfac_TotalPaidIn());
			}
			sumPaidin = new DecimalFormat("#,###.##").format(sum);
		}
	}

	public String getCity() {
		return city;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public List<CoFinanceAgencyCollectModel> getCollectList() {
		return collectList;
	}

	public String getSumPaidin() {
		return sumPaidin;
	}

}
