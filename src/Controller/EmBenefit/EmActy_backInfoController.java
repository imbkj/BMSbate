package Controller.EmBenefit;

import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmBenefit_comitListBll;

import Model.EmWelfareModel;

public class EmActy_backInfoController {
	private Integer tarpid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("tarpid").toString());
	private Map map=Executions.getCurrent().getArg();
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private String sortid =Executions.getCurrent().getArg()
			.get("sortid").toString();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();
	private List<EmWelfareModel> list =bll.getLists(" and emwf_state=4 and emwf_sortid='"+sortid+"'");
	private String caseinfo="";//退回原因
	
	
	@Command
	public void back(@BindingParam("win") Window win)
	{
		if(list.size()>0)
		{
			if (caseinfo.equals("")) {
				Messagebox.show("请填写退回原因", "提示", Messagebox.OK, Messagebox.ERROR);
			}
			else
			{
				if(sortid!=null&&!sortid.equals(""))
				{
					EmActy_GiftInfoOperateBll obll=new EmActy_GiftInfoOperateBll();
					String[] str=obll.EmWelfareInfoStop(tarpid, sortid, caseinfo, id+"");
					if(str[0]=="1")
					{
						map.put("flag", "2");
						Messagebox.show("退回成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
						win.detach();
					}
					else
					{
						Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
					}
				}
			}
		}
		else
		{
			Messagebox.show("没有数据不能退回", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}


	public String getCaseinfo() {
		return caseinfo;
	}


	public void setCaseinfo(String caseinfo) {
		this.caseinfo = caseinfo;
	}
	
}
