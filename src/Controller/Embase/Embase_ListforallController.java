package Controller.Embase;

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

import bll.Embase.EmbaseListBll;
import Model.EmbaseModel;
import Util.RedirectUtil;

public class Embase_ListforallController {

	private List<Integer> countdate;
	private String strwhere = "";
	public List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	EmbaseListBll embasebll = new EmbaseListBll();

	public Embase_ListforallController() {

//		embaselist = embasebll.searchembaselist(strwhere);
//		countdate = embasebll.datecount(strwhere);

	}

	public List<Integer> getCountdate() {
		return countdate;
	}

	public void setCountdate(List<Integer> countdate) {
		this.countdate = countdate;
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

	// 查询客服数据权限列表
	@Command
	@NotifyChange({ "embaselist", "countdate" })
	public void search() throws SQLException {
		// System.out.print(log_name);

		try {
			if (strwhere.indexOf("|") != -1) {

				embaselist = embasebll.searchembaselistall(strwhere
						.split("\\|")[1].trim());
				countdate = embasebll
						.datecount(strwhere.split("\\|")[1].trim());
				setStrwhere(strwhere.split("\\|")[2].trim());
			} else {
				embaselist = embasebll.searchembaselistall(strwhere.trim());
				countdate = embasebll.datecount(strwhere.trim());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// 打开业务中心
	@Command
	public void openbucenter(@BindingParam("a") EmbaseModel em) {
		Map map = new HashMap<>();
		map.put("daid", em.getGid());
		map.put("embaId", em.getEmba_id());
		Window window;
		if (em.getEmba_state().equals(2)) {
			window = (Window) Executions.createComponents(
					"../EmBase/EmBase_Add.zul", null, map);

		} else {
			window = (Window) Executions.createComponents(
					"../SystemControl/EmBuCenterInfoList.zul", null, map);

		}
		window.doModal();
	}

	// 打开离职页面
	@Command("openDimission")
	@NotifyChange({ "embaselist", "countdate" })
	public void openDimission(@BindingParam("a") EmbaseModel em) {
		Map map = new HashMap<>();
		map.put("daid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("Embase_Dimission.zul",
				null, map);
		window.doModal();
		embaselist = embasebll.searchembaselistall(strwhere);
		countdate = embasebll.datecount(strwhere);
	}

	// 打开短信页面
	@Command("openmobile")
	public void openmobile(@BindingParam("a") EmbaseModel em) {
		Map map = new HashMap<>();
		map.put("mobile", em.getEmba_mobile());
		Window window;
		window = (Window) Executions.createComponents("SMS_Check.zul", null,
				map);
		window.doModal();
	}

	// 打开图片页面
	@Command("openempic")
	public void openempic(@BindingParam("a") EmbaseModel em) {
		Map map = new HashMap<>();
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("EmPic_Check.zul", null,
				map);
		window.doModal();
	}
	
	// 打开详情页面
	@Command("openinfo")
	public void openinfo(@BindingParam("a") EmbaseModel em) {
		Map map = new HashMap<>();
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("Embase_Allinone.zul", null,
				map);
		window.doModal();
	}
	
	// 打开详情页面
	@Command("embaseinfo")
	public void embaseinfo(@BindingParam("model") EmbaseModel em) {
//		Map map = new HashMap<>();
//		map.put("m", em);
//		Window window;
//		window = (Window) Executions.createComponents("Embase_yfzxinfo.zul", null,
//				map);
//		window.doModal();
		
		RedirectUtil u =new RedirectUtil();
		
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid="+em.getGid()+"", "雇员服务中心");
		
		
	}
	
 
}
