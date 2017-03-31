package Controller.EmFinanceManage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import bll.EmFinanceManage.emgt_selectBll;
import bll.Embase.EmbaseListBll;

public class Emgt_EmbaseListController {
	private String strwhere = "";
	public List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	emgt_selectBll embasebll = new emgt_selectBll();

	public Emgt_EmbaseListController() {
		embaselist = embasebll.searchembaseeditlist(strwhere);
	}
	

	// 查询客服数据权限列表
	@Command
	@NotifyChange({ "embaselist" })
	public void search() throws SQLException {
		// System.out.print(log_name);

		try {
			if (strwhere.indexOf("|") != -1) {
				embaselist = embasebll.searchembaseeditlist(strwhere.split("\\|")[1]);
				setStrwhere(strwhere.split("\\|")[2]);
			} else {
				embaselist = embasebll.searchembaseeditlist(strwhere);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public String getStrwhere() {
		return strwhere;
	}

	public void setStrwhere(String strwhere) {
		this.strwhere = strwhere;
	}

	public List<EmbaseModel> getEmbaselist() {
		return embaselist;
	}

	public void setEmbaselist(List<EmbaseModel> embaselist) {
		this.embaselist = embaselist;
	}

	// 添加收款
	@Command("openzul")
	public void openzul(@BindingParam("model") EmbaseModel em) {
		Map map = new HashMap<>();
		map.put("model",em);
		Window window;
		window = (Window) Executions.createComponents("Emgt_Add.zul", null,
				map);
		window.doModal();
	}
}
