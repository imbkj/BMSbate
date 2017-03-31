package Controller.EmCensus;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmCensusModel;

public class EmCensus_UpDataInfoController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	private String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmCensus_SelectBll bll=new EmCensus_SelectBll();
	private List<EmCensusModel> emcensus=bll.getEmCensusInfo(" and emhj_id="+id);
	private EmCensusModel hjmodel=new EmCensusModel();

	public EmCensus_UpDataInfoController() {
		// TODO Auto-generated constructor stub
		if(emcensus.size()>0)
		{
			hjmodel=emcensus.get(0);
		}
	}
	
	//交接材料
	@Command
	public void updata(@BindingParam("docGrid") Grid gd,@BindingParam("win") Window win)
	{
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		try {
			// 调用内联页方法chkHaveTo(Grid gd)
			String[] message = docOC.AddchkHaveTo(gd);
			if (message[0].equals("1")) {
				message = docOC.AddsubmitDocout(gd,id);
				if(message[0].equals("1"))
				{
					EmCensusModel model=new EmCensusModel();
					model.setEmhj_id(Integer.parseInt(id));
					if(tperid!=null&&!tperid.equals(""))
					{
						model.setEmhj_taprid(Integer.parseInt(tperid));
					}
					model.setOperateinfo("交接材料");
					EmCensus_OperateBll bll=new EmCensus_OperateBll();
					String[] str=bll.EmCensusUpdate(model, "",0);
					if(str[0].equals("1"))
					{
						Messagebox.show("提交成功", "操作提示", Messagebox.OK, Messagebox.INFORMATION);
						win.detach();
					}
					else
					{
						Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
					}
				}
				else
				{
					Messagebox.show("提交失败", "操作提示", Messagebox.OK, Messagebox.ERROR);
				}
			}
		}
		catch (Exception e) {
			System.out.println("错误："+e.getMessage());
			Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmCensusModel getHjmodel() {
		return hjmodel;
	}

	public void setHjmodel(EmCensusModel hjmodel) {
		this.hjmodel = hjmodel;
	}
	
}
