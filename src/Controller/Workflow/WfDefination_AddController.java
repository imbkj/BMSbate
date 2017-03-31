package Controller.Workflow;

import impl.UserInfoImpl;

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

import service.UserInfoService;

import bll.Workflow.WfClassBll;
import bll.Workflow.WfDefinationBll;
import Model.WfClassModel;

public class WfDefination_AddController extends SelectorComposer<Component> {
	private ListModelList<WfClassModel> wfClassList;
	private Integer wfType;
	private String wfName;
	private String wfCode;
	private String wfRemark;

	WfClassBll wfcBll = new WfClassBll();
	WfDefinationBll wfdBll = new WfDefinationBll();

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	@Wire
	Combobox wf_Type;
	@Wire
	Textbox wf_Name;
	@Wire
	Textbox wf_Code;
	@Wire
	Textbox wf_Remark;

	// 初始化变量
	public WfDefination_AddController() throws Exception {
		wfClassList = new ListModelList<WfClassModel>(wfcBll.getClassList());

	}

	//提交修改
	@Listen("onClick = #btSubmit")
	public void AddDefination() throws Exception {

		if (wf_Type.getSelectedIndex() >= 0 && !wf_Name.getValue().equals("")
				&& !wf_Code.getValue().equals("")) {

			String[] message = wfdBll.AddWfDefination(
					Integer.valueOf(wf_Type.getSelectedItem().getValue()
							.toString()), wf_Code.getValue(),
					wf_Name.getValue(), wf_Remark.getValue(), username);
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	                public void onEvent(ClickEvent event) throws Exception {
	                    if (Messagebox.Button.OK.equals(event.getButton())) {
	                        Executions.sendRedirect("WfDefination_List.zul");	//跳转页面
	                    }
	                }
	            };
	          //弹出提示
	            Messagebox.show(message[1], "操作提示", new Messagebox.Button[] { Messagebox.Button.OK },
	            		Messagebox.INFORMATION, clickListener);	
			}else {
				//弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			if (wf_Type.getSelectedIndex() < 0) {
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

	public ListModelList<WfClassModel> getWfClassList() {
		return wfClassList;
	}

	public void setWfClassList(ListModelList<WfClassModel> wfClassList) {
		this.wfClassList = wfClassList;
	}

	public Integer getWfType() {
		return wfType;
	}

	public void setWfType(Integer wfType) {
		this.wfType = wfType;
	}

	public String getWfName() {
		return wfName;
	}

	public void setWfName(String wfName) {
		this.wfName = wfName;
	}

	public String getWfCode() {
		return wfCode;
	}

	public void setWfCode(String wfCode) {
		this.wfCode = wfCode;
	}

	public String getWfRemark() {
		return wfRemark;
	}

	public void setWfRemark(String wfRemark) {
		this.wfRemark = wfRemark;
	}

}
