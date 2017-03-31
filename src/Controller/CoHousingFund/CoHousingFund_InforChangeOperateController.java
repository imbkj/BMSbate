package Controller.CoHousingFund;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

import Model.CoHousingFundChangeModel;
import Model.CoHousingFundInforChangeModel;
import Util.UserInfo;

public class CoHousingFund_InforChangeOperateController {

	Integer daid = 0;
	private Integer state = 0;
	private CoHousingFundChangeModel chfcModel = new CoHousingFundChangeModel();
	private List<CoHousingFundInforChangeModel> cficList = new ListModelList<>();

	public CoHousingFund_InforChangeOperateController() {
		try {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		CoHousingFund_ListBll bll = new CoHousingFund_ListBll();
		try {
			chfcModel = bll.getCoHoChangeList(" and chfc_id=" + daid).get(0);
			setCficList(bll.getInforChangeList(" and cfic_chfc_id=" + daid));

			if (chfcModel.getChfc_state().equals(4)
					|| chfcModel.getChfc_state().equals(5)) {
				state = chfcModel.getChfc_laststate();
			} else {
				state = chfcModel.getChfc_state();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 */
	@Command
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		CoHousingFund_OperateBll bll = new CoHousingFund_OperateBll();

		try {
			chfcModel.setChfc_state(state + 1);
			chfcModel.setChfc_addname(UserInfo.getUsername());

			String[] str = bll.updatestate(chfcModel, gd);
			if (str[0].equals("1")) {
				Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
	
	@Command
	public void back(@BindingParam("win") final Window win){
		Messagebox.show("确认退回数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							CoHousingFund_OperateBll bll = new CoHousingFund_OperateBll();
							chfcModel.setChfc_state(4);
							Integer i = bll.returnTocommit(chfcModel);
							if (i > 0) {
								Messagebox.show("操作成功.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("操作失败.", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}
						}
					}
				});
		

	}

	public CoHousingFundChangeModel getChfcModel() {
		return chfcModel;
	}

	public void setChfcModel(CoHousingFundChangeModel chfcModel) {
		this.chfcModel = chfcModel;
	}

	public List<CoHousingFundInforChangeModel> getCficList() {
		return cficList;
	}

	public void setCficList(List<CoHousingFundInforChangeModel> cficList) {
		this.cficList = cficList;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}
