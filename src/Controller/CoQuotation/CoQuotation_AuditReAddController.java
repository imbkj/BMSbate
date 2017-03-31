package Controller.CoQuotation;

import impl.MessageImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoQuotation.CoQuotation_List1Bll;
import bll.CoQuotation.CoofferOperateBll;

import Model.CoOfferModel;

public class CoQuotation_AuditReAddController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tarpid = (String)Executions.getCurrent().getArg().get("id");
	private CoOfferModel model=new CoOfferModel();
	private CoQuotation_List1Bll bll = new CoQuotation_List1Bll();
	private List<CoOfferModel> list=new ArrayList<CoOfferModel>();
	public CoQuotation_AuditReAddController()
	{
		if(id!=null)
		{
			try {
				list=bll.getCoOfferInfoList(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list.size()>0)
			{
				model=list.get(0);
			}
		}
	}
	
	//重新提交
	@Command
	public void summit(@BindingParam("win") Window win)
	{
		//CoofferOperateBll obll=new CoofferOperateBll();
		//String[] str=obll.coofferReSummit(model, Integer.parseInt(tarpid));
		CoofferOperateBll cbll = new CoofferOperateBll();
		String[] str = cbll.CoofferAuditAdd(model);
		if(str[0]=="1")
		{
			Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		}
		else
		{
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public CoOfferModel getModel() {
		return model;
	}
	public void setModel(CoOfferModel model) {
		this.model = model;
	}
	
	
}
