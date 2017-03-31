package Controller.CoProduct;

import impl.cpdFeeRelationComparatorImpl;

import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Group;
import org.zkoss.zul.ListModelList;

import bll.CoProduct.cpd_ListBll;

import Model.CoProductModel;
import Model.cpdFeeRelationGroupingModel;

public class cpd_FeeRelationList1Controller {

	cpd_ListBll bll = new cpd_ListBll();
	int copr_id = (Integer) Executions.getCurrent().getArg().get("copr_id");
	private boolean showGroup = false; 
	private List<CoProductModel> list;
    
	//初始化
	@Init
	public void init() throws SQLException{
		setList(new ListModelList<CoProductModel>(
				bll.getEclassBycoprid(copr_id)));
	}
	
	//调用分组model
    public cpdFeeRelationGroupingModel getFeeRelationModel() { 
        return new cpdFeeRelationGroupingModel(list, new cpdFeeRelationComparatorImpl(), this.showGroup); 
    }

	public List<CoProductModel> getList() {
		return list;
	}


	public void setList(List<CoProductModel> list) {
		this.list = list;
	}
}
