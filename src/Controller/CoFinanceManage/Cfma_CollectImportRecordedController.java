package Controller.CoFinanceManage;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_CollectImportBll;

import Model.CoFinanceCollectImportModel;
import Util.UserInfo;

public class Cfma_CollectImportRecordedController {
	private final int cfci_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cfci_id").toString());

	private CoFinanceCollectImportModel ciModel;
	private Cfma_CollectImportBll bll;
	private String company;
	private String type;
	private String usage;

	public Cfma_CollectImportRecordedController() {
		bll = new Cfma_CollectImportBll();
		ciModel = bll.getCollectImportModel(cfci_id);
		company = ciModel.getCfci_company();
		usage = ciModel.getCfci_usage();
	}

	// 入账
	@Command("recorded")
	public void recorded(@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (checkData()) {
				if (Messagebox.show("确定将这笔" + ciModel.getCfci_amount()
						+ "元的收款录入：" + company + "吗？", "操作提示", Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					ciModel.setCfci_company(company);
					ciModel.setCfci_usage(usage);
					if ("公司".equals(type)) {
						ciModel.setCfci_type(1);
					} else if ("机构".equals(type)) {
						ciModel.setCfci_type(2);
					}
					int i = bll.addImportRecorded(ciModel,
							UserInfo.getUsername());
					if (i == 1) {
						Binder bind = (Binder) view.getParent().getAttribute(
								"binder");
						bind.postCommand("recordedToRefreshWin", null);
						// 弹出提示
						Messagebox.show("入账成功。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();
					} else if (i == 2) {
						Messagebox.show("对方账户名称有误，在系统中未找到该" + type + "。",
								"操作提示", Messagebox.OK, Messagebox.INFORMATION);
					} else {
						Messagebox.show("收款入账失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("收款入账出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检测数据
	private boolean checkData() {
		try {
			if (type == null || "".equals(type)) {
				Messagebox.show("请选择收款类型。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				return false;
			} else if (company == null || "".equals(company)) {
				Messagebox.show("请填写对方账户名称。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public CoFinanceCollectImportModel getCiModel() {
		return ciModel;
	}

}
