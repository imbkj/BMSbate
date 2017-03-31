package Controller.EmCensus.EmDh;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;
import Model.EmDhModel;
import bll.EmCensus.EmDh.EmDh_LxOpeate;
import bll.EmCensus.EmDh.EmDh_LxOpeateReturnImpl;
import bll.EmCensus.EmDh.EmDh_LxOperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmDh_LxConditionAuditController {
	// private EmDh_LxOperateBll bll=new EmDh_LxOperateBll();
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmDh_SelectBll bll = new EmDh_SelectBll();
	private List<EmDhModel> dhmodellist = bll.getEmDhInfo(" and a.id=" + id);
	private EmDhModel dhmodel = new EmDhModel();
	private String dhtype = "";

	public EmDh_LxConditionAuditController() {
		if (dhmodellist.size() > 0) {
			dhmodel = dhmodellist.get(0);
			dhtype = dhmodel.getEmdh_dhtype();
		}
	}
	
	@Command
	public void submit(@BindingParam("win") final Window win)
	{
		if(dhmodel.getEmdh_time3()==null)
		{
			Messagebox.show("请选择审核时间","提示",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		if(dhmodel.getEmdh_zhtype()==null||dhmodel.getEmdh_zhtype().equals(""))
		{
			Messagebox.show("请选择账户类型","提示",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		//判断调户类型是否与新增时一样
		if(!dhmodel.getEmdh_dhtype().equals(dhtype))
		{
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.YES.equals(event.getButton())) {
						EmDh_LxConreoller lxController=new EmDh_LxConreollerImpl("调户方式改变");
						String[] str=lxController.edit(dhmodel);
					}
				}
			};
			Messagebox.show("选择的调户方式和客服提交的不同，如果提交流程将退回到第三步", "提示",
					new Messagebox.Button[] { Messagebox.Button.YES,
							Messagebox.Button.NO },
					Messagebox.QUESTION, clickListener);
		}
		else
		{
			EmDh_LxConreoller lxController=new EmDh_LxConreollerImpl("条件审核");
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
	}

	// 重置流程
	@Command("beginagain")
	public void beginagain(@BindingParam("win") final Window win) {
		EmDh_LxConreoller lxController=new EmDh_LxConreollerImpl("重置流程");
		String[] str=lxController.edit(dhmodel);
		if(str[0].equals("1"))
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}else if(str[0].equals("-1"))
		{
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出页面
	@Command
	public void openZul(@BindingParam("openType") String openType) {
		if(openType!=null&&!openType.equals(""))
		{
			EmDh_OpenFactory factory=new EmDh_OpenFactoryImpl(openType);
			EmDh_OpenZul openZul=factory.OpenZulFactory();
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
