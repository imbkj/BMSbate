package Controller.CoBase;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.CoBase.CoBaseLinkMan_OperateBll;
import Util.UserInfo;
public class CoBaseLinkMan_DelController extends SelectorComposer<Component>{
	private static final long serialVersionUID = 1L;

	private final int cali_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cali_id").toString());
	private final int cid = Integer.parseInt(Executions.getCurrent()
			.getArg().get("cid").toString());
	@Wire
	private Textbox cali_delreason;
	@Wire
	private Window winCoLinkDel;
	
	// 删除
	@Listen("onClick = #btSubmit")
	public void DelLinkman() {
		try{
		CoBaseLinkMan_OperateBll bll = new CoBaseLinkMan_OperateBll();
		int result = bll.DelCobaseLinkman(cid, cali_id, cali_delreason.getValue(), UserInfo.getUsername());
		if(result == 0){
			Messagebox.show("删除联系人成功。", "操作提示", Messagebox.OK,
					Messagebox.NONE);
			winCoLinkDel.detach();
		}else{
			Messagebox.show("删除联系人失败。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
		}catch(Exception e){
			Messagebox.show("删除联系人出错。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
}
