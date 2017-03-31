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

import bll.MainRecord.MaintenanceRecordBll;
import Model.MaintenanceRecordModel;

/**
 * 
 * @author suhongyuan
 * @create 2016-11-14
 */
public class MaintenanceRecord_UpdateController {
	private final MaintenanceRecordModel mainModel = (MaintenanceRecordModel) Executions.getCurrent().getArg().get("model");
	private MaintenanceRecordBll bll= new MaintenanceRecordBll();
	//更新未审核维护记录
	@Command("Charge")
	public void Charge(@BindingParam("proposereson") String proposereson,
			    @BindingParam("sql") String sql,
				@BindingParam("win") Window win,
				@ContextParam(ContextType.VIEW) Component view) {
		MaintenanceRecordModel mr =new MaintenanceRecordModel();
		   mr.setProposereson(proposereson);
		   mr.setSql(sql);
		   mr.setId(mainModel.getId());
	    int a= bll.updateMainRecord(mr);
	    if (a>0) {
			Binder bind = (Binder) view.getParent().getAttribute("binder");
			bind.postCommand("refreshAll", null);
			Messagebox.show("修改成功！", "操作提示", Messagebox.OK,Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("修改失败！", "操作提示", Messagebox.OK,
					Messagebox.NONE);
		}
	}

	public MaintenanceRecordModel getMainModel() {
		return mainModel;
	}
    
}
