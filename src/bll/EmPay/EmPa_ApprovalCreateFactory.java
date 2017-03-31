package bll.EmPay;

import Model.EmPayModel;

public class EmPa_ApprovalCreateFactory {
	private EmPa_Approval approval;

	public EmPa_ApprovalCreateFactory(EmPayModel model) {
		switch (model.getApprovalType()) {
		case "客户经理":
			approval = new EmPa_ClinetManagerMApproval(model);
			break;
		case "部门经理":
			approval = new EmPa_DeptMApprovalImpl(model);
			break;
		case "总经理助理":
			approval = new EmPa_assMApprovalImpl(model);
			break;
		case "票据":
			approval = new EmPa_InvoiceApprovalImpl(model);
			break;
		case "财务":
			approval = new EmPa_FinanceApprovalImpl(model);
			break;
		case "总经理":
			approval = new EmPa_ManagerApprovalImpl(model);
			break;
		case "出纳":
			approval = new EmPa_CashierApprovalImpl(model);
			break;
		default:
			break;
		}
	}

	public EmPa_Approval getApproval() {
		return approval;
	}

}
