package Controller.EmSalary;

import java.math.BigDecimal;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Messagebox;

import bll.EmSalary.ItemFormula_OperateBll;
import bll.EmSalary.ItemFormula_SelectBll;

public class EmSalary_TXTSetController {
	private ItemFormula_SelectBll ifSBll = new ItemFormula_SelectBll();
	private ItemFormula_OperateBll ifOBll = new ItemFormula_OperateBll();
	private BigDecimal balance = new BigDecimal(0);

	public EmSalary_TXTSetController() {
		// 获取当天最新的一条银行余额数据
		try {
			balance = ifSBll.getTXTBalance().getEtfs_balance();
		} catch (Exception e) {
			balance = new BigDecimal(0);
		}

	}

	// 提交
	@Command("submit")
	@NotifyChange("balance")
	public void submit() {
		try {
			if (balance != null) {

				String[] message = ifOBll.addTXTFileSet(balance);
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show("操作成功！", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} else {
				// 弹出提示
				Messagebox.show("请录入“银行余额”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

}
