package Controller.CoCompact.CoCompactSA;

import java.util.ArrayList;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Label;
import org.zkoss.zul.Vlayout;

import bll.CoCompact.CoCompactSA.CoCompactSA_SelectBll;

import Model.CoCompactSAModel;
import Util.FileOperate;

public class CompactSA_DetailController extends SelectorComposer<Component>{
	@Wire
	private Vlayout load;
	CoCompactSAModel frommodel = (CoCompactSAModel)Executions.getCurrent().getArg().get("ccsaM");
	ArrayList<String> list=null;
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		CoCompactSA_SelectBll bll=new CoCompactSA_SelectBll();
		list=bll.getCoCompactSAPubOffice(frommodel.getCcsa_id());
		if(!list.isEmpty())
		{
			for(int i=0;i<list.size();i++)
			{
				A a=new A();
				//a.setHref(list.get(i));
				final String url=list.get(i);
				a.setParent(load);
				a.addEventListener(Events.ON_CLICK,new org.zkoss.zk.ui.event.EventListener(){
					public void onEvent(Event event) throws Exception {
						FileOperate.download(url);
		            	}
					});
				Label la=new Label(list.get(i));
				la.setParent(a);
			}
		}
	}

	public ArrayList<String> getList() {
		return list;
	}

	public void setList(ArrayList<String> list) {
		this.list = list;
	}	
}
