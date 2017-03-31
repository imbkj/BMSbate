package Controller.CoServePromise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Window;

import Model.CoAgencyLinkmanModel;
import Util.SocialInsuranceCalculator.item;
import bll.CoBase.CoBaseLinkMan_SelectBll;

public class Cobase_LinkListController {
	String cid = Executions.getCurrent().getArg().get("cid").toString();
	private CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
	private List<CoAgencyLinkmanModel> linklist = lmBll.getLinkmanByCid(Integer
			.parseInt(cid));
	private String name = "";
	private Map map = Executions.getCurrent().getArg();

	public Cobase_LinkListController() {
		if (Executions.getCurrent().getArg().get("name") != null) {
			name = Executions.getCurrent().getArg().get("name").toString();
			CoAgencyLinkmanModel lm = new CoAgencyLinkmanModel();
			if (name != null && !name.equals("")) {
				lm.setCali_name(name);
				map.put("lm", lm);
			}
		}
	}

	@Command
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gp") Radiogroup gp) {
		int k = 0;
		if (gp.getSelectedItem() != null) {
			CoAgencyLinkmanModel lm = gp.getSelectedItem().getValue();
			map.put("lm", lm);
			map.put("acml", lm);
			win.detach();
		} else {
			Messagebox.show("请选择一个联系人", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 打开新增联系人页面
	@Command
	@NotifyChange("linklist")
	public void add() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("cid", String.valueOf(cid));
		Window window = (Window) Executions.createComponents(
				"../CoBase/CoBaseLinkMan_Add.zul", null, map);
		window.doModal();
		linklist = lmBll.getLinkmanByCid(Integer.parseInt(cid));
	}
	
	//选择事件
	@Command
	public void selectlb(@BindingParam("litem") Listitem item)
	{
		if(item!=null)
		{
			Listcell cell=(Listcell) item.getChildren().get(7);
			if(cell!=null&&cell.getChildren().size()>0)
			{
				Radio rd=(Radio) cell.getChildren().get(0);
				rd.setChecked(true);
			}
		}
	}

	public List<CoAgencyLinkmanModel> getLinklist() {
		return linklist;
	}

	public void setLinklist(List<CoAgencyLinkmanModel> linklist) {
		this.linklist = linklist;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
