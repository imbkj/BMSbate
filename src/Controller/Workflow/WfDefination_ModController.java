package Controller.Workflow;

import impl.UserInfoImpl;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import service.UserInfoService;

import bll.Workflow.WfClassBll;
import bll.Workflow.WfDefinationBll;

import Model.WfClassModel;
import Model.WfDefinationModel;

public class WfDefination_ModController extends SelectorComposer<Component> {

	private final Integer wfde_id = Integer.parseInt(Executions.getCurrent()
			.getArg().get("id").toString());

	private ListModelList<WfClassModel> wfcList;
	private List<WfDefinationModel> wfdList;

	WfDefinationBll wfdBll = new WfDefinationBll();
	WfClassBll wfcBll = new WfClassBll();
	
	@Wire
	private Window winMissionUp;
	@Wire
	private Combobox wf_Type;
	@Wire
	private Textbox wf_Name;
	@Wire
	private Textbox wf_Code;
	@Wire
	private Textbox wf_Remark;
	@Wire
	private Combobox wf_State;
	
	
	public WfDefination_ModController(){
		this.wfcList = new ListModelList<WfClassModel>(wfcBll.getClassList());
		setWfdList();
	}

	@Listen("onClick = #btSubmit")
	public void UpdateWfd() {

		if (wf_Type.getSelectedIndex() >= 0 && !wf_Name.getValue().equals("")
				&& !wf_Code.getValue().equals("")) {

			try {
				// 获取操作用户
				Session session = Executions.getCurrent().getDesktop()
						.getSession();
				UserInfoService user = new UserInfoImpl(session);

				
				String[] message = wfdBll.ModWfDefination(
						wfde_id,
						Integer.valueOf(wf_Type.getSelectedItem().getValue()
								.toString()), wf_Code.getValue(),
						wf_Name.getValue(), wf_Remark.getValue(),
						user.getUsername(),Integer.valueOf(wf_State.getSelectedItem().getValue().toString()));
				
				if (message[0].equals("1")) {
					EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
						public void onEvent(ClickEvent event) throws Exception {
							if (Messagebox.Button.OK.equals(event.getButton())) {
								winMissionUp.detach();
							}
						}
					};
					// 弹出提示
					Messagebox.show(message[1], "操作提示",
							new Messagebox.Button[] { Messagebox.Button.OK },
							Messagebox.INFORMATION, clickListener);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			if (wf_Type.getSelectedItem().getValue().equals("")) {
				Messagebox.show("请选择“任务类型”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);

			} else if (wf_Name.getValue().equals("")) {
				Messagebox.show("请录入“任务名称”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);

			} else if (wf_Code.getValue().equals("")) {
				Messagebox.show("请录入“任务单标识码”！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);

			}
		}

	}

	public ListModelList<WfClassModel> getWfcList() {
		return wfcList;
	}

	public void setWfcList(ListModelList<WfClassModel> wfcList) {
		this.wfcList = wfcList;
	}

	public List<WfDefinationModel> getWfdList() {
		return wfdList;
	}

	public void setWfdList() {
		WfDefinationBll wfdBll = new WfDefinationBll();
		this.wfdList = wfdBll.getDefinationListById(wfde_id);
	}

}
