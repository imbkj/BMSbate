package Controller.Embase;

import java.util.List;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import bll.Embase.EmQutationBll;

import dal.Embase.Embasedal;

import Model.CoOfferModel;
import Model.EmbaseModel;

public class EmQutationController {

	private List<CoOfferModel> list = new ListModelList<>();
	private Integer embaID = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());
	private Integer gid = 0;
	private Window win;

	private EmQutationBll bll = new EmQutationBll();

	public EmQutationController() {
		List<EmbaseModel> emlist = new ListModelList<>();
		System.out.println(embaID);
		emlist = bll.embaseList(embaID);
		System.out.println(emlist.size());
		if (emlist.size() > 0) {
			gid = emlist.get(0).getGid();
		}
		System.out.println(gid);
		setList(gid);
		System.out.println(list.size());
	}

	@Command
	public void Emp(@BindingParam("a") Window winC) {
		win = winC;
	}

	public List<CoOfferModel> getList() {
		return list;
	}

	public void setList(Integer id) {
		this.list = bll.coofferInfo(id);
	}

}
