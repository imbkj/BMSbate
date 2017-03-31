package Controller.MainRecord;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.MaintenanceRecordModel;
import bll.MainRecord.MaintenanceRecordBll;
/**
 * 
 * @author suhongyuan
 *
 */
public class MaintenanceRecordHr_UpdateController {
	private final MaintenanceRecordModel mainModel = (MaintenanceRecordModel) Executions.getCurrent().getArg().get("model");
	private MaintenanceRecordBll bll= new MaintenanceRecordBll();
	
	//更新/新增审核维护记录维护结果
	@Command("Charge")
	public void Charge(@BindingParam("mainresult") String mainresult,
					@BindingParam("win") Window win,
					@ContextParam(ContextType.VIEW) Component view) {
			MaintenanceRecordModel mr =new MaintenanceRecordModel();
               mr.setMainresult(mainresult);
			   mr.setId(mainModel.getId());
		    int a= bll.updateAduitMainRecord(mr);
		    if (a>0) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("refreshAll", null);
				Messagebox.show("操作成功！", "操作提示", Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("操作失败！", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}
		
	public MaintenanceRecordModel getMainModel() {
		return mainModel;
	}
}
