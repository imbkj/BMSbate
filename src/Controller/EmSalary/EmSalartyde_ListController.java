package Controller.EmSalary;

import java.util.List;
import java.util.ArrayList;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;

import Model.EmSalaryInfoModel;
import bll.EmSalary.EmSalaryInfoListBll;

public class EmSalartyde_ListController {
	
	private List<EmSalaryInfoModel> salaryinfolist;
	private EmSalaryInfoListBll gridupdatebll = new EmSalaryInfoListBll();
	
	
	private final int wherestrinfo = Integer.parseInt(Executions.getCurrent()
			.getParameter("wherestrinfo").toString());
	private final int type = Integer.parseInt(Executions.getCurrent()
			.getParameter("type").toString());
//	Integer.parseInt(Executions.getCurrent()
//			.getParameter("id").toString())
	EmSalaryInfoListBll emsb= new EmSalaryInfoListBll();
	
	public EmSalartyde_ListController()
	{
		salaryinfolist =new ArrayList<EmSalaryInfoModel>();
		
		System.out.println(wherestrinfo);
		System.out.println(type);
		salaryinfolist=emsb.getsalaryinfoList(wherestrinfo,type);
	}
	
	@Command("updategrid")
	@NotifyChange("salaryinfolist")
	public void updategrid(@BindingParam("self") Label lb) throws Exception {
		gridupdatebll.updatevisbel(gridupdatebll.getcid(wherestrinfo), lb.getValue(), 1,type);
		salaryinfolist=emsb.getsalaryinfoList(wherestrinfo,type);
	}
	

	public List<EmSalaryInfoModel> getSalaryinfolist() {
		return salaryinfolist;
	}

	public void setSalaryinfolist(List<EmSalaryInfoModel> salaryinfolist) {
		this.salaryinfolist = salaryinfolist;
	}

}
