package Controller.CoHousingFund;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.WorkflowCore.WfOperateService;

import bll.CoHousingFund.CoHousingFund_ListBll;
import bll.CoHousingFund.CoHousingFund_OperateBll;

import Model.CoCompactModel;
import Model.CoHousingFundChangeModel;
import Util.UserInfo;

public class CoHousingFund_UpdateCompactController {
	private List<CoCompactModel> compactList = new ListModelList<>();
	private List<CoHousingFundChangeModel> cmList = new ListModelList<>();

	private CoHousingFund_ListBll bll = new CoHousingFund_ListBll();

	private CoCompactModel cm = new CoCompactModel();

	private Integer daid = 0;
	private Integer taprId = 0;

	private Window win;

	public CoHousingFund_UpdateCompactController() {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());
		}
		if (Executions.getCurrent().getArg().get("id") != null) {
			taprId = Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
		}

		cmList = bll.getCoHoChangeList(" and chfc_id=" + daid);
		if (cmList.size() > 0) {
			cm.setCid(cmList.get(0).getCid().toString());
			cm.setCompany(cmList.get(0).getChfc_company());
			cm.setCoco_houseid(cmList.get(0).getChfc_houseid());
			cm.setCoco_cpp(cmList.get(0).getChfc_cpp().toString());
			cm.setCoco_houseacc(cmList.get(0).getChfc_banktsid());
			cm.setCoco_housebank(cmList.get(0).getChfc_bankts());
			cm.setCoco_modname(UserInfo.getUsername());
			cm.setChfc_id(daid);
			compactList = bll.getcompactList(cmList.get(0).getCid());

		}
		System.out.println("id=" + daid);
	}

	@Command
	public void opWin(@BindingParam("a") Window w) {
		win = w;
	}

	@Command
	public void updateCompact(@BindingParam("a") Integer k) {
		cm.setCoco_housefee(k);
	}

	@Command
	public void submit() {
		Combobox cb = (Combobox) win.getFellow("compact");
		if (cb.getSelectedItem() != null) {
			cm.setCoco_id(Integer.valueOf(cb.getSelectedItem().getValue()
					.toString()));
		} else {
			Messagebox.show("请选择合同.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		if (!(cm.getCoco_housefee() != null && !cm.getCoco_housefee()
				.equals(""))) {
			Messagebox.show("请选择缴款方式", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}

		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							/*
							 * Object[] obj = { "更新合同", cm }; WfOperateService
							 * wfs = new WfOperateImpl( new
							 * CoHousingFund_OperateBll()); String[] str =
							 * wfs.PassToNext(obj, taprId,
							 * UserInfo.getUsername(), "",
							 * Integer.valueOf(cm.getCid()), "");
							 * 
							 * 
							 * if (str[0].equals("1")) { Messagebox.show("提交成功",
							 * "操作提示", Messagebox.OK, Messagebox.INFORMATION);
							 * win.detach(); }else { Messagebox.show("提交失败",
							 * "操作提示", Messagebox.OK, Messagebox.ERROR); }
							 */
							CoHousingFund_OperateBll obll = new CoHousingFund_OperateBll();
							Integer i = obll.updateCompact(cm);
							if (i > 0) {
								Messagebox.show("提交成功", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("提交失败", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<CoCompactModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(List<CoCompactModel> compactList) {
		this.compactList = compactList;
	}

	public CoCompactModel getCm() {
		return cm;
	}

	public void setCm(CoCompactModel cm) {
		this.cm = cm;
	}

}
