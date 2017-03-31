package Controller.EmTax;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Executions;

import Model.PropersonTaxationModel;
import bll.EmTax.PropersonTaxationBll;


public class PropersonTaxation_SelectTaxController {
	private PropersonTaxationBll bll= new  PropersonTaxationBll();
	private String coba_gzaddname="",cpct_type="";

	private List<PropersonTaxationModel> list = new ArrayList<PropersonTaxationModel>();
	
	public PropersonTaxation_SelectTaxController() {
		coba_gzaddname = Executions.getCurrent().getArg().get("coba_gzaddname").toString();
		cpct_type = Executions.getCurrent().getArg().get("cpct_type").toString();
		System.out.println(coba_gzaddname);
		System.out.println(cpct_type);
		try {
		     if(cpct_type.trim().equals("财税")){
			     list=bll.findPropersonTaxationTaxList(coba_gzaddname);
		     }
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
