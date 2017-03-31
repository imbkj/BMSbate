package Controller.Workflow;

import impl.UserInfoImpl;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import service.UserInfoService;

import bll.Workflow.WfNode_UpdateBll;

import Model.WfNodeModel;

public class WfNode_UpdateController extends SelectorComposer<Component>{
	private static final long serialVersionUID = 1L;
	private final int wfno_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<WfNodeModel> nodeList;
	@Wire
	private Window winNodeUp;
	@Wire
	private Textbox tbName;
	@Wire
	private Intbox ibStep;
	@Wire
	private Textbox tbUrl;
	@Wire
	private Combobox cbRuntype;
	@Wire
	private Textbox tbRunprocedure;
	@Wire
	private Intbox ibMaxday;
	@Wire
	private Radiogroup rdIfview;
	@Wire
	private Radiogroup rdIfhavechild;
	@Wire
	private Radiogroup rdIfskip;
	@Wire
	private Radiogroup rdIfreturn;
	@Wire
	private Radiogroup rdIfrevoke;
	@Wire
	private Radiogroup rdIfstop;
	@Wire
	private Radiogroup rdState;
	@Wire
	private Textbox tbSelectuser;
	@Wire
	private Textbox tbRemark;
	
	public WfNode_UpdateController(){
		setNodeList();
	}

	// 新增节点
	@Listen("onClick = #btSubmit")
	public void addBase() {
		try {
			// 获取操作用户
			Session session = Executions.getCurrent().getDesktop().getSession();
			UserInfoService user = new UserInfoImpl(session);

			WfNode_UpdateBll bll = new WfNode_UpdateBll();
			String wfno_name = tbName.getValue();
			String wfno_selectuser = tbSelectuser.getValue();
			String wfno_runprocedure = tbRunprocedure.getValue();
			String wfno_url = tbUrl.getValue();
			String wfno_remark = tbRemark.getValue();
			String wfno_addname = user.getUsername();
			int step;
			try {
				step = ibStep.getValue();
			} catch (Exception e) {
				step = 0;
			}
			int maxday;
			try {
				maxday = ibMaxday.getValue();
			} catch (Exception e) {
				maxday = 0;
			}
			int ifview = Integer.parseInt(rdIfview.getSelectedItem().getValue()
					.toString());
			int ifskip = Integer.parseInt(rdIfskip.getSelectedItem().getValue()
					.toString());
			int ifreturn = Integer.parseInt(rdIfreturn.getSelectedItem()
					.getValue().toString());
			int ifstop = Integer.parseInt(rdIfstop.getSelectedItem().getValue()
					.toString());
			int ifrevoke = Integer.parseInt(rdIfrevoke.getSelectedItem()
					.getValue().toString());
			int ifhavechild = Integer.parseInt(rdIfhavechild.getSelectedItem()
					.getValue().toString());
			int state = Integer.parseInt(rdState.getSelectedItem().getValue()
					.toString());
			String runtypeStr = cbRuntype.getValue();
			int runtype;
			if (runtypeStr.equals("人为操作")) {
				runtype = 1;
			} else {
				runtype = 2;
			}
			// 调用新增方法返回操作信息
			String[] message = bll.UpdateNode(wfno_id, wfno_name,
					wfno_selectuser, wfno_runprocedure, wfno_url, wfno_remark,
					wfno_addname, step, maxday, runtype, ifview, ifskip,
					ifreturn, ifstop, ifrevoke, ifhavechild, state);
			if (message[0].equals("1")) {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
				winNodeUp.detach();
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			Messagebox.show("修改节点出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	public List<WfNodeModel> getNodeList() {
		return nodeList;
	}

	public void setNodeList() {
		WfNode_UpdateBll bll = new WfNode_UpdateBll();
		this.nodeList = bll.getNodeBasebyId(wfno_id);
	}
	
	
}
