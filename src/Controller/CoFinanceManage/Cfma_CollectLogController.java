package Controller.CoFinanceManage;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoFinanceCollectModel;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_CollectOperateBll;
import bll.CoFinanceManage.Cfma_SelBll;

public class Cfma_CollectLogController {
	private final int cid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("cid").toString());
	private final String company = Executions.getCurrent().getArg()
			.get("company").toString();
	private String username;

	private List<CoFinanceCollectModel> collectList;
	private String sumPaidin;
	private Cfma_SelBll bll = new Cfma_SelBll();

	public Cfma_CollectLogController() {
		username = UserInfo.getUsername();
		collectList = bll.getCollectLog(cid);
		sumPaidin();
	}

	// 合计收款
	public void sumPaidin() {
		if (collectList.size() == 0) {
			sumPaidin = "0";
		} else {
			BigDecimal sum = BigDecimal.ZERO;
			for (CoFinanceCollectModel m : collectList) {
				sum = sum.add(m.getCfco_TotalPaidIn());
			}
			sumPaidin = new DecimalFormat("#,###.##").format(sum);
		}
	}

	@Command("addCollect")
	@NotifyChange({ "collectList", "sumPaidin" })
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
						collectList = bll.getCollectLog(cid);
						sumPaidin();
						Messagebox.show("添加收款成功。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCid() {
		return cid;
	}

	public String getCompany() {
		return company;
	}

	public List<CoFinanceCollectModel> getCollectList() {
		return collectList;
	}

	public String getSumPaidin() {
		return sumPaidin;
	}

}
