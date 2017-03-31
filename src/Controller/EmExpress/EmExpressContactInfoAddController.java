package Controller.EmExpress;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmExpress.EmExpressInfoOperateBll;

import Model.EmExpressContactInfoModel;

public class EmExpressContactInfoAddController {
	private String flag = (String) Executions.getCurrent().getArg().get("flag");
	private EmExpressContactInfoModel model=new EmExpressContactInfoModel();
	private Integer gid=null,cid=null;
	
	public EmExpressContactInfoAddController()
	{
		if(flag!=null&&flag.equals("emba"))
		{
			gid=(Integer)Executions.getCurrent().getArg().get("gid");
			cid=(Integer)Executions.getCurrent().getArg().get("cid");
		}
		else
		{
			cid=(Integer)Executions.getCurrent().getArg().get("cid");
		}
	}
	
	//邮寄地址新增
	@Command
	public void submit(@BindingParam("win") Window win)
	{
		model.setGid(gid);
		model.setCid(cid);
		String str="";
		str=ifEmpty(model);
		if(str!=null&&!str.equals(""))
		{
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			EmExpressInfoOperateBll bll=new EmExpressInfoOperateBll();
			Integer k=0;
			if(flag!=null&&flag.equals("emba"))
			{
				k=bll.EmExpressContactInfoAdd(model);
			}
			else
			{
				k=bll.EmExpressContactInfoCobaseAdd(model);
			}
			if(k>0)
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				//Executions.sendRedirect("EmExpressContactInfoAdd.zul");
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	//判断地址信息是否为空
	private String ifEmpty(EmExpressContactInfoModel m)
	{
		String str="";
		if(m.getExct_address()==null||m.getExct_address().equals(""))
		{
			str="请填写邮寄详细地址";
		}
		else if(m.getExct_receivename()==null||m.getExct_receivename().equals(""))
		{
			str="请填写收件人姓名";
		}
		else if(m.getExct_mobile()==null||m.getExct_mobile().equals(""))
		{
			str="请填写收件人手机号码";
		}
		return str;
	}
	public EmExpressContactInfoModel getModel() {
		return model;
	}

	public void setModel(EmExpressContactInfoModel model) {
		this.model = model;
	}
	
	

}
