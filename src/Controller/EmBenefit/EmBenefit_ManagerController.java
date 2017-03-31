package Controller.EmBenefit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmBenefit.EmBenefit_ManagerBll;

import Model.EmBenefitModel;
import Util.UserInfo;

public class EmBenefit_ManagerController {
	private List<EmBenefitModel> beList = new ListModelList<>();
	private EmBenefit_ManagerBll bll = new EmBenefit_ManagerBll();
	private Integer num = 0;
	private Window win = (Window) Path.getComponent("/winEW");
	private String username = UserInfo.getUsername();

	public EmBenefit_ManagerController() {
		setBeList("");
		setNum();
	}

	@Command("Search")
	@NotifyChange("beList")
	public void Search() {
		Textbox tb = (Textbox) win.getFellow("item");
		setBeList(tb.getValue());

	}
	
	@Command("start")
	public void start(@BindingParam("a") final EmBenefitModel ebfm){
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.startMission(ebfm,username);
							if (i>0) {
								Messagebox.show("操作成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
							}else {
								Messagebox.show("操作失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
						
					}
		});
	}

	@Command("mod")
	@NotifyChange({ "beList", "num" })
	public void mod(@BindingParam("a") EmBenefitModel ebfm) {
		Map map = new HashMap<>();
		map.put("id", ebfm.getEmbf_id());
		Window window = (Window) Executions.createComponents(
				"EmBenefit_Mod.zul", null, map);
		window.doModal();
		setBeList("");
		setNum();
	}

	@Command("link")
	@NotifyChange("num")
	public void link(@BindingParam("a") EmBenefitModel ebfm) {
		Map map = new HashMap<>();
		map.put("id", ebfm.getEmbf_id());
		Window window = (Window) Executions.createComponents(
				"EmBenefit_Allot.zul", null, map);
		window.doModal();
		setNum();
	}
	
	//关联产品（陈耀家）
	@Command
	@NotifyChange("num")
	public void allot(@BindingParam("a") EmBenefitModel model)
	{
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"EmActy_ProductAllot.zul", null, map);
		window.doModal();
		setNum();
	}

	@Command("Del")
	@NotifyChange({ "beList", "num" })
	public void Del(@BindingParam("a") final EmBenefitModel ebfm) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.delEmBenefit(ebfm.getEmbf_id(),
									username);
							if (i > 0) {
								setBeList("");
								setNum();
								Messagebox.show("操作成功", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show("操作失败", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public List<EmBenefitModel> getBeList() {
		return beList;
	}

	public void setBeList(String name) {
		this.beList = bll.getList(name);
	}

	public Integer getNum() {
		return num;
	}

	public void setNum() {
		this.num = bll.sumTotal();
	}

}
