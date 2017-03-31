package Controller.Embase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import bll.Embase.EmbaseListBll;

public class Embase_editListController {
	private List<Integer> countdate;
	private String strwhere = "";
	public List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	EmbaseListBll embasebll = new EmbaseListBll();

	public Embase_editListController() {

		Date d = new Date();
		embaselist = embasebll.searchembaseeditlist(strwhere);
		countdate = embasebll.datecount(strwhere);
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

				embaselist = embasebll.searchembaseeditlist(strwhere
						.split("\\|")[1].trim());
				countdate = embasebll
						.datecount(strwhere.split("\\|")[1].trim());
				setStrwhere(strwhere.split("\\|")[2].trim());
			} else {
				embaselist = embasebll.searchembaseeditlist(strwhere.trim());
				countdate = embasebll.datecount(strwhere.trim());
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	// 打开业务中心
	@Command
	@NotifyChange("embaselist")
	public void openbucenter(@BindingParam("a") EmbaseModel em)
			throws InterruptedException {
		Map map = new HashMap<>();

		map.put("embaId", em.getEmba_id());

		Window window;
		if (em.getEmba_state().equals(2)) {
			window = (Window) Executions.createComponents(
					"../EmBase/EmBase_Add.zul", null, map);
			window.doModal();
			embaselist = embasebll.searchembaseeditlist(em.getGid().toString());
		} else if (em.getEmba_state().equals(0)) {
			map.put("cxrz", em.getCid());
			window = (Window) Executions.createComponents(
					"../EmBase/EmBase_Add.zul", null, map);
			window.doModal();
			embaselist = embasebll.searchembaseeditlist(em.getGid().toString());
		}

		else if (em.getEmba_state().equals(5)) {

			window = (Window) Executions.createComponents(
					"../EmBase/EmBase_Addselect.zul", null, map);
			window.doModal();
			embaselist = embasebll.searchembaseeditlist(em.getGid().toString());
		} else {
			map.put("daid", em.getGid());
			window = (Window) Executions.createComponents(
					"../SystemControl/EmBuCenterInfoList.zul", null, map);
			window.doModal();
		}

	}

	// 打开业务中心
	@Command
	@NotifyChange("embaselist")
	public void openbucenteronly(@BindingParam("a") EmbaseModel em)
			throws InterruptedException {
		Map map = new HashMap<>();

		map.put("embaId", em.getEmba_id());
		Window window;

		map.put("daid", em.getGid());
		window = (Window) Executions.createComponents(
				"../SystemControl/EmBuCenterInfoList.zul", null, map);
		window.doModal();

	}

	// 打开离职页面
	@Command("openDimission")
	@NotifyChange({ "embaselist", "countdate" })
	public void openDimission(@BindingParam("a") EmbaseModel em)
			throws InterruptedException {
		Map map = new HashMap<>();
		map.put("daid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("Embase_Dimission.zul",
				null, map);
		window.doModal();
		embaselist = embasebll.searchembaselist(strwhere);
		countdate = embasebll.datecount(strwhere);
	}

	// 打开短信页面
	@Command("openmobile")
	public void openmobile(@BindingParam("a") EmbaseModel em)
			throws InterruptedException {
		Map map = new HashMap<>();
		map.put("mobile", em.getEmba_mobile());
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("SMS_Add.zul", null, map);
		window.doModal();
	}

	// 打开图片页面
	@Command("openempic")
	public void openempic(@BindingParam("a") EmbaseModel em)
			throws InterruptedException {
		Map map = new HashMap<>();
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions
				.createComponents("EmPic_Up.zul", null, map);
		window.doModal();
	}

	// 打开重新入职页面
	@Command
	@NotifyChange({ "embaselist", "countdate" })
	public void reentry(@BindingParam("model") EmbaseModel em,
			@BindingParam("url") String url) throws InterruptedException {
		Map map = new HashMap<>();
		map.put("model", em);
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		embaselist = embasebll.searchembaselist(strwhere);
		countdate = embasebll.datecount(strwhere);
	}
}
