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

import bll.CoFinanceManage.Cfma_AgencyOperateBll;

import Util.UserInfo;

public class Cfma_AgencyCollectController {
	private final int coab_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("coab_id").toString());
	private final String city = Executions.getCurrent().getArg().get("city")
			.toString();
	private final String coab_name = Executions.getCurrent().getArg()
			.get("coab_name").toString();
	private String username;

	public Cfma_AgencyCollectController() {
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
					Cfma_AgencyOperateBll opbll = new Cfma_AgencyOperateBll();
					int i = opbll.addAgencyCollect(coab_id, "0", paidin,
							username, remark);
					if (i == 1) {
						Binder bind = (Binder) view.getParent().getAttribute(
								"binder");
						bind.postCommand("refreshCfagModel", null);
						Messagebox.show("添加收款成功。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
						win.detach();
					} else {
						Messagebox.show("添加收款失败。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("您领取的金额有误，请确认领取金额后再提交。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}

		} catch (Exception e) {
			Messagebox.show("添加收款出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public String getCity() {
		return city;
	}

	public String getCoab_name() {
		return coab_name;
	}

	public String getUsername() {
		return username;
	}

}
