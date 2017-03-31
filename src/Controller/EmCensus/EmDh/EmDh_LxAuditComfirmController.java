package Controller.EmCensus.EmDh;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmDhModel;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmDh_LxAuditComfirmController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmDh_SelectBll bll = new EmDh_SelectBll();
	private List<EmDhModel> dhmodellist = bll.getEmDhInfo(" and a.id=" + id);
	private EmDhModel dhmodel = new EmDhModel();
	private String feetype;
	private BigDecimal fee = BigDecimal.ZERO;

	public EmDh_LxAuditComfirmController() {
		if (dhmodellist.size() > 0) {
			dhmodel = dhmodellist.get(0);
		}
	}
	
	@Command
	public void submit(@BindingParam("win") final Window win)
	{
		if(dhmodel.getEmdh_time5()==null)
		{
			Messagebox.show("请选择预审通过时间","提示",Messagebox.OK,Messagebox.ERROR);
			return;
		}
		EmDhModel m=new EmDhModel();
		m.setEmdh_idcard(dhmodel.getEmdh_idcard());
		m.setId(dhmodel.getId());
		m.setEmdh_fee(fee);
		m.setEmdh_fistfeetype(feetype);
		m.setEmdh_totalfee(dhmodel.getEmdh_totalfee());
		m.setEmdh_time5(dhmodel.getEmdh_time5());
		if(dhmodel.getEmdh_taprid()!=null)
		{
			m.setEmdh_taprid(dhmodel.getEmdh_taprid());
		}
		EmDh_LxConreoller lxController=new EmDh_LxConreollerImpl("预审确认");
		String[] str=lxController.edit(m);
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
		EmDh_LxConreoller lxController = new EmDh_LxConreollerImpl("重置流程");
		String[] str = lxController.edit(dhmodel);
		if (str[0].equals("1")) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else if (str[0].equals("-1")) {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出页面
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

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

}
