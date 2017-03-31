package Controller.SocialInsurance;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import bll.SocialInsurance.Algorithm_InfoBll;
import bll.SocialInsurance.Algorithm_RegisteredDataBll;
import Model.EmCommissionOutModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Util.UserInfo;

public class Algorithm_RegisteredDataController {
	private final int sial_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("sial_id").toString());
	private SocialInsuranceAlgorithmViewModel saModel;
	private List<EmCommissionOutModel> ecouList;
	private Algorithm_InfoBll bll;
	private int admin = 0;

	public Algorithm_RegisteredDataController() {
		testAdmin();
		bll = new Algorithm_InfoBll();
		saModel = bll.getSiAlBase(sial_id);
		if (saModel != null) {
			ecouList = bll.getRegDataBySoinId(saModel.getSoin_id());
		}
	}

	// 更新在册明细数据、变更表数据
	@Command("upRegData")
	public void upRegData() {
		Algorithm_RegisteredDataBll opBll = new Algorithm_RegisteredDataBll();
		String[] message = opBll.upRegData(saModel.getSoin_id(), sial_id,
				saModel.getSial_execdate());
		Messagebox.show(message[1], "操作提示", Messagebox.OK,
				Messagebox.INFORMATION);
	}

	// 测试权限
	private void testAdmin() {
		String username = UserInfo.getUsername();
		if ("李文洁".equals(username)||"赵敏捷".equals(username)||"张志强".equals(username)||"林少斌".equals(username)||"彭耀".equals(username)) {
			this.admin = 1;
		}
	}

	public int getAdmin() {
		return admin;
	}

	public SocialInsuranceAlgorithmViewModel getSaModel() {
		return saModel;
	}

	public List<EmCommissionOutModel> getEcouList() {
		return ecouList;
	}

}
