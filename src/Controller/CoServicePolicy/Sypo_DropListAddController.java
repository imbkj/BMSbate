package Controller.CoServicePolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import Model.CoServicePolicyTitleDropInfoModel;

public class Sypo_DropListAddController {
	private Map map = Executions.getCurrent().getArg();
	private List<CoServicePolicyTitleDropInfoModel> droplist=new ArrayList<CoServicePolicyTitleDropInfoModel>();
	
	@Command
	public void creatvl(@BindingParam("vl") Vlayout vl)
	{
		List list=vl.getChildren();
		Integer k=list.size();
		for(int i=0;i<droplist.size();i++)
		{
			if(i<k)
			{
				Textbox tb=(Textbox) list.get(i);
				tb.setValue(droplist.get(i).getDrop_content());
			}
			else
			{
				Textbox tb=new Textbox(droplist.get(i).getDrop_content());
				tb.setWidth("80%");
				tb.setParent(vl);
			}
		}
	}
	
	public Sypo_DropListAddController()
	{
		droplist=(List<CoServicePolicyTitleDropInfoModel>) map.get("droplist");
	}
	@Command
	public void submit(@BindingParam("vyt") Vlayout vyt,@BindingParam("win") Window win)
	{
		Integer k=0;
		if(vyt!=null)
		{
			List list=vyt.getChildren();
			if(list.size()>0)
			{
				for(int i=0;i<list.size();i++)
				{
					Textbox tb=(Textbox) list.get(i);
					if(tb.getValue()!=null&&!tb.getValue().equals(""))
					{
						CoServicePolicyTitleDropInfoModel m=new CoServicePolicyTitleDropInfoModel();
						m.setDrop_content(tb.getValue());
						droplist.add(m);
					}
				}
			}
		}
		if(droplist.size()>0)
		{
			map.put("droplist", droplist);
			win.detach();
		}
		else
		{
			Messagebox.show("至少输入一个内容", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
}
