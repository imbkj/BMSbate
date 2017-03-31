package Controller.EmTax;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.PropersonTaxationModel;
import bll.EmTax.PropersonTaxationBll;


public class PropersonTaxation_SelectEnstrController {
	private PropersonTaxationBll bll= new  PropersonTaxationBll();
	private String coba_client="",cpct_type="";

	private List<PropersonTaxationModel> list = new ArrayList<PropersonTaxationModel>();
	
	public PropersonTaxation_SelectEnstrController() {
		coba_client = Executions.getCurrent().getArg().get("coba_client").toString();
		cpct_type = Executions.getCurrent().getArg().get("cpct_type").toString();
		System.out.println(coba_client);
		System.out.println(cpct_type);
		try {
		    list=bll.getEntrustDetailList(coba_client, cpct_type);
		  } catch (SQLException e) {
			e.printStackTrace();
		    }
		
	}

	public List<PropersonTaxationModel> getList() {
		return list;
	}

	public void setList(List<PropersonTaxationModel> list) {
		this.list = list;
	}
	
	
}
