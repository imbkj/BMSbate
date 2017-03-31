package Controller.CoFinanceManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Window;

import bll.CoFinanceManage.Cfma_ManualDisSpOperateBll;
import bll.CoFinanceManage.Cfma_ManualDisposableBll;

import Model.CoFinanceManualDisposableModel;
import Model.EmbaseModel;
import Util.RegexUtil;
import Util.UserInfo;

/**
 * 批量审核员工非标
 * 
 * @author Administrator
 * 
 */
public class Cfma_plCheckEmManualDisController {

	private List<CoFinanceManualDisposableModel> manauls;
	private List<CoFinanceManualDisposableModel> smanauls = new ArrayList<CoFinanceManualDisposableModel>();
	private Cfma_ManualDisposableBll bll = new Cfma_ManualDisposableBll();
	private Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
	private List<String> clients;
	private String client;
	private String username;
	private Date ownmonth;
	private String company;

	public Cfma_plCheckEmManualDisController() {
		username = UserInfo.getUsername();
		manauls = bll.getPlCheckInfo(username);
		clients = bll.getclients(UserInfo.getUsername()); // 根据登录人获取到登录人部门的人员
		search(null);
	}

	// 条件查询
	@Command
	@NotifyChange("smanauls")
	public void search(@BindingParam("month") Date month) {
		manauls = bll.getPlCheckInfo(username);
		if (manauls.size() > 0) {
			smanauls.clear();
			for (CoFinanceManualDisposableModel m : manauls) {
				if (month != null) {
					if (!RegexUtil.isExists(
							new SimpleDateFormat("yyyyMM").format(month),
							m.getOwnmonth() + "")) {
						continue;
					}
				}
				if (company != null) {
					try {
						Integer.valueOf(company);
						if (!RegexUtil.isExists(company,
								String.valueOf(m.getCid()))) {
							continue;
						}
					} catch (Exception e) {
						if (!RegexUtil.isExists(company, m.getCoba_company())) {
							continue;
						}
					}
				}
				if (client != null) {
					if (!RegexUtil.isExists(client, m.getCfmd_addname())) {
						continue;
					}
				}
				smanauls.add(m);
			}
		}
	}

	// 全选
	@Command
	public void allcheck(@BindingParam("check") Checkbox c,
			@BindingParam("rows") Rows rows) {
		if (rows.getChildren().size() > 0) {
			for (int i = 0; i < rows.getChildren().size(); i++) { // 获取到checkbox中被选择的对象
				Checkbox checkbox = (Checkbox) rows.getChildren().get(i)
						.getChildren().get(0);
				checkbox.setChecked(c.isChecked());
			}
		}
	}

	// 批量审核
	@Command
	@NotifyChange("smanauls")
	public void plcheck(@BindingParam("rows") Rows rows) {
		boolean flag = false;
		String[] str = null;
		if (rows.getChildren().size() > 0) {
			for (int i = 0; i < rows.getChildren().size(); i++) { // 获取到checkbox中被选择的对象
				Checkbox checkbox = (Checkbox) rows.getChildren().get(i)
						.getChildren().get(0);
				if (checkbox.isChecked() == true) { // 如果check被勾选，则对该员工生成添加非标的任务单
					str = operabll
							.plCheackCoDispo((CoFinanceManualDisposableModel) checkbox
									.getValue());
					if (str[0].equals("1")) {
						flag = true;
					}
				}
			}
			if (flag) {
				Messagebox.show("审核成功", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
				search(null);
			} else {
				Messagebox
						.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("未选择员工", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 删除数据
	@Command
	@NotifyChange("smanauls")
	public void delete(@BindingParam("m") CoFinanceManualDisposableModel m) {
		// 删除数据，并终止任务单
		Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
		String[] str = operabll.deleteData(m);
		if (str[0].equals("1")) {
			Messagebox.show(str[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			search(null);
		} else {
			Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<CoFinanceManualDisposableModel> getSmanauls() {
		return smanauls;
	}

	public void setSmanauls(List<CoFinanceManualDisposableModel> smanauls) {
		this.smanauls = smanauls;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public List<String> getClients() {
		return clients;
	}

	public void setClients(List<String> clients) {
		this.clients = clients;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public List<CoFinanceManualDisposableModel> getManauls() {
		return manauls;
	}

	public void setManauls(List<CoFinanceManualDisposableModel> manauls) {
		this.manauls = manauls;
	}

}
