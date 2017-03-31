package Controller.EmBenefit;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmBenefit_AllotBll;

import Model.CoProductModel;

public class EmBenefit_AllotController {
	private List<CoProductModel> ebList = new ListModelList<>();
	private List<CoProductModel> cpList = new ListModelList<>();
	private EmBenefit_AllotBll bll = new EmBenefit_AllotBll();
	private String bfName;

	private final Integer embeId = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());
	
	private Window win = (Window) Path.getComponent("/win");

	public EmBenefit_AllotController() {
		System.out.println(embeId);
		bfName = bll.getName(embeId);
		setEbList(embeId);
		setCpList(0,"");
	}
	
	@Command("ltr")
	@NotifyChange({"ebList","cpList"})
	public void ltr(@BindingParam("a") CoProductModel cpm){
		Integer i = bll.updateList(embeId, cpm.getCopr_id());
		Textbox tb = (Textbox) win.getFellow("copr");
		if (i<=0) {
			Messagebox.show("操作失败", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}else {
			setEbList(embeId);
			setCpList(0,tb.getValue()==null?"":tb.getValue());
		}
	}
	
	@Command("rtl")
	@NotifyChange({"ebList","cpList"})
	public void rtl(@BindingParam("a") CoProductModel cpm){
		Integer i = bll.updateList(0, cpm.getCopr_id());
		Textbox tb = (Textbox) win.getFellow("copr");
		if (i<=0) {
			Messagebox.show("操作失败", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}else {
			setEbList(embeId);
			setCpList(0,tb.getValue()==null?"":tb.getValue());
		}
	}
	
	@Command("SearchCp")
	@NotifyChange("cpList")
	public void SearchCp(){
		
		Textbox tb = (Textbox) win.getFellow("copr");
		setCpList(0,tb.getValue()==null?"":tb.getValue());
		
	}
	
	@Command("clear")
	@NotifyChange({"ebList","cpList"})
	public void clear(){
		Textbox tb = (Textbox) win.getFellow("copr");
		bll.clearList(0, embeId);
		setEbList(embeId);
		setCpList(0,tb.getValue()==null?"":tb.getValue());
	}
	

	public String getBfName() {
		return bfName;
	}

	public void setBfName(String bfName) {
		this.bfName = bfName;
	}

	public List<CoProductModel> getEbList() {
		return ebList;
	}

	public void setEbList(Integer id) {
		this.ebList = bll.getEbList(id);
	}

	public List<CoProductModel> getCpList() {
		return cpList;
	}

	public void setCpList(Integer id,String name) {
		this.cpList = bll.getCpList(id,name);
	}

}
