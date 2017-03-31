package Controller.CoAgency;

import java.sql.SQLException;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import bll.CoProduct.cpd_addBll;
import bll.EmCommissionOut.Standard.wtoutFeeBll;

import Model.CoAgencyBaseCityRelViewModel;

public class CoAgencyFeeManageController {
	public String city;
	public String ag;
	private Integer cabc_id;
	wtoutFeeBll wtbll  =new wtoutFeeBll();
	
	public CoAgencyBaseCityRelViewModel lsm=new CoAgencyBaseCityRelViewModel();

	public CoAgencyFeeManageController()
	{
		
		cabc_id = Integer.parseInt(Executions.getCurrent().getArg().get("cabc_id")
				.toString());

		System.out.println(cabc_id);
	
		lsm=wtbll.getCoAgencyListView(cabc_id).get(0);
		city=lsm.getCity();
		ag=lsm.getCoab_name();
	}
	
	@Command("submit")
	public void submit() throws SQLException
	{
		int i;
		cpd_addBll bll=new cpd_addBll();
		
		if (lsm.getCabc_fee()==null || "".equals(lsm.getCabc_fee()))
		{
		
			Messagebox.show("请入服务费", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
		else
		{
					
			i=bll.editfee(lsm);
			
			if (i>0)
			{
				
				Messagebox.show("提交成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
			else
			{
				Messagebox.show("提交失败", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
		
		
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAg() {
		return ag;
	}

	public void setAg(String ag) {
		this.ag = ag;
	}

	public CoAgencyBaseCityRelViewModel getLsm() {
		return lsm;
	}

	public void setLsm(CoAgencyBaseCityRelViewModel lsm) {
		this.lsm = lsm;
	}
	
	
	
	
	
	
}
