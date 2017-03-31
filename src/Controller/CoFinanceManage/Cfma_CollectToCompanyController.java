package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_CollectOperateBll;

import Util.UserInfo;

public class Cfma_CollectToCompanyController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final String company = Executions.getCurrent().getArg()
			.get("company").toString();
	private String username;

	public Cfma_CollectToCompanyController() {
		username = UserInfo.getUsername();
	}

	@Command("addCollect")
	public void addCollect(@BindingParam("paidin") BigDecimal paidin,
			@BindingParam("remark") String remark,
			@BindingParam("win") Window win,
			@ContextParam(ContextType.VIEW) Component view) {
		try {
			if (paidin.compareTo(BigDecimal.ZERO) == 1) {
				DecimalFormat df = new DecimalFormat("#.00");
				paidin = new BigDecimal(df.format(paidin));
				if (Messagebox.show("请核对收款金额：" + paidin, "操作提示", Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					Cfma_CollectOperateBll opbll = new Cfma_CollectOperateBll();
					int i = opbll.addCollectToCompany(cid, paidin, username,
							remark);
					if (i > 1) {
						Binder bind = (Binder) view.getParent().getAttribute(
								"binder");
						bind.postCommand("refreshTotalModel", null);
						Messagebox.show("添加收款成功。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();
					} else {
						Messagebox.show("添加收款失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("请输入正确的金额。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("添加收款出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	

	public int getCid() {
		return cid;
	}

	public String getCompany() {
		return company;
	}

	public String getUsername() {
		return username;
	}

}
