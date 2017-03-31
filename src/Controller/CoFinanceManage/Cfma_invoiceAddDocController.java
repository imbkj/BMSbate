package Controller.CoFinanceManage;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;

import Model.CoBaseModel;


/**
 * 打印发票controller
 * 
 * @author Administrator
 * 
 */
public class Cfma_invoiceAddDocController {

	private List<CoBaseModel> cfcmList = (List<CoBaseModel>) Executions
			.getCurrent().getArg().get("cfcmList");

	public Cfma_invoiceAddDocController() {
		Session session = Executions.getCurrent().getDesktop().getSession();
		session.setAttribute("cfcmList", cfcmList);
	}

}
