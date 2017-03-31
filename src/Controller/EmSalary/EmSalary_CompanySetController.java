package Controller.EmSalary;

import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSalary.EmSalary_SalaryOperateBll;
import bll.EmSalary.EmSalary_SalarySelBll;
import bll.EmSalary.ItemFormula_OperateBll;

public class EmSalary_CompanySetController {
	private final String cid = Executions.getCurrent().getArg().get("cid")
			.toString();
	private final String shortname = Executions.getCurrent().getArg()
			.get("shortname").toString();
	private final String gzUser = Executions.getCurrent().getArg()
			.get("gzUser").toString();
	private final String gzAudUser = Executions.getCurrent().getArg()
			.get("gzAudUser").toString();
	private List<String> cwUserList;

	public EmSalary_CompanySetController() {
		EmSalary_SalarySelBll bll = new EmSalary_SalarySelBll();
		cwUserList = bll.getCwUser();
	}

	@Command("update")
	public void update(@BindingParam("win") Window win,
			@BindingParam("gz") String gz, @BindingParam("gzaud") String gzaud,
			@ContextParam(ContextType.VIEW) Component view) {
		EmSalary_SalaryOperateBll opBll = new EmSalary_SalaryOperateBll();
		try {
			
			// 判断是否第一次设置薪酬负责人，如果是，则添加工资项目设置流程
			List<CoBaseModel> cobaseList;
			CoBase_SelectBll coBll = new CoBase_SelectBll();
			cobaseList = coBll.getCobaseinfo(" AND a.cid=" + cid);
			if (cobaseList.size() > 0) {
				String gzaddname = "";
				gzaddname = cobaseList.get(0).getCoba_gzaddname();
				if (gzaddname == null || "".equals(gzaddname)
						|| "null".equals(gzaddname) || "NULL".equals(gzaddname)) {
					ItemFormula_OperateBll utbll = new ItemFormula_OperateBll();
					utbll.clcAddSalaryItemId(Integer.parseInt(cid), Integer
							.parseInt(Util.DateStringChange.getOwnmonth()),
							UserInfo.getUsername());
				}
			}

			String[] message = opBll
					.setGzUser(Integer.parseInt(cid), gz, gzaud);
			if (message[0].equals("1")) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("setList", null);
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();

			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("设置薪酬负责人出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public String getCid() {
		return cid;
	}

	public String getShortname() {
		return shortname;
	}

	public List<String> getCwUserList() {
		return cwUserList;
	}

	public String getGzUser() {
		return gzUser;
	}

	public String getGzAudUser() {
		return gzAudUser;
	}

}
