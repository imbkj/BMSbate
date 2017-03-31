package Controller.EmCensus.EmDh;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.poi.util.Beta;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmDhModel;
import bll.EmCensus.EmDh.EmDh_LxOpeate;
import bll.EmCensus.EmDh.EmDh_LxOpeateReturnImpl;
import bll.EmCensus.EmDh.EmDh_LxOperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmDh_LxInfoAuditController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmDh_SelectBll bll = new EmDh_SelectBll();
	private List<EmDhModel> dhmodellist = bll.getEmDhInfo(" and a.id=" + id);
	private EmDhModel dhmodel = new EmDhModel();

	public EmDh_LxInfoAuditController() {
		if (dhmodellist.size() > 0) {
			dhmodel = dhmodellist.get(0);
		}
	}
	
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		if(dhmodel.getEmdh_time4()==null)
		{
			Messagebox.show("请选择审核时间", "提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
		EmDh_LxConreoller lxController=new EmDh_LxConreollerImpl("信息预审");
		String[] str=lxController.edit(dhmodel);
		if (str[0].equals("1")) {
			Messagebox.show("提交成功", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 重置流程
	@Command("beginagain")
	public void beginagain(@BindingParam("win") final Window win) {
		final EmDhModel model = new EmDhModel();
		model.setId(Integer.parseInt(id));
		if (tperid != null && !tperid.equals("")) {
			model.setEmdh_taprid(Integer.parseInt(tperid));
		}
		model.setEmdh_name(dhmodel.getEmdh_name());
		model.setEmdh_dhtype(dhmodel.getEmdh_dhtype());
		EmDh_LxConreoller lxController=new EmDh_LxConreollerImpl("重置流程");
		String[] str=lxController.edit(model);
		if (str[0].equals("1")) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else if (str[0].equals("-1")) {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command
	public void openZul(@BindingParam("openType") String openType) {
		if (openType != null && !openType.equals("")) {
			EmDh_OpenFactory factory = new EmDh_OpenFactoryImpl(openType);
			EmDh_OpenZul openZul = factory.OpenZulFactory();
			openZul.open(dhmodel);
		}
	}

	public EmDhModel getDhmodel() {
		return dhmodel;
	}

	public void setDhmodel(EmDhModel dhmodel) {
		this.dhmodel = dhmodel;
	}
}
