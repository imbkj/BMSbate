package bll.EmCensus.EmDh;

import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmDhModel;

public class EmDh_LxOpeateReturnImpl implements EmDh_LxOpeate{
	/**
	 * 
	 * @author chenyaojia
	 *@Description:重置流程——调回到第三步
	 */
	private int k=0;
	@Override
	public String[] edit(final EmDhModel m) {
		final EmDh_LxOperateBll obll = new EmDh_LxOperateBll();
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					String[] strs = new String[5];
					String sql=",emdh_state=1";
					strs = obll.ReturnStep(m,sql);
					if (strs[0].equals("1")) {
						k=1;
					} else {
						k=-1;
					}
				}
			}
		};
		Messagebox.show("重置流程后流程将退回到交接材料，是否确认提交", "提示",
				new Messagebox.Button[] { Messagebox.Button.YES,
						Messagebox.Button.NO }, Messagebox.QUESTION,
				clickListener);
		String[] str = new String[5];
		str[0]=k+"";
		return str;
	}

}
