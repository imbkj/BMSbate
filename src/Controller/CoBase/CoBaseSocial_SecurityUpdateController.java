package Controller.CoBase;


import java.sql.SQLException;
import java.util.List;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseSocialSecurityModel;
import bll.CoBase.CoBaseSocial_SecurityBll;

/**
 * 
 * @author suhongyuan
 * @create 2017-01-05
 * 员工修改
 */
public class CoBaseSocial_SecurityUpdateController {
	 private final CoBaseSocialSecurityModel mainModel = (CoBaseSocialSecurityModel) Executions.getCurrent().getArg().get("model");
	 private CoBaseSocial_SecurityBll bll= new CoBaseSocial_SecurityBll();
     private List<String> list=null;
     
     private String coba_assistant="";
     
	 public CoBaseSocial_SecurityUpdateController() {
		try {
			list=bll.getCobaList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//更新员工姓名
	 @Command("Charge")
	 public void Charge(
					@BindingParam("win") Window win,
					@ContextParam(ContextType.VIEW) Component view) {
			 CoBaseSocialSecurityModel mr =new CoBaseSocialSecurityModel();
			 mr.setCoba_assistant(coba_assistant);
			 mr.setCid(mainModel.getCid());
		    int a= bll.updateCoBase(mr);
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

	public CoBaseSocialSecurityModel getMainModel() {
		return mainModel;
	}

	public CoBaseSocial_SecurityBll getBll() {
		return bll;
	}

	public void setBll(CoBaseSocial_SecurityBll bll) {
		this.bll = bll;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getCoba_assistant() {
		return coba_assistant;
	}

	public void setCoba_assistant(String coba_assistant) {
		this.coba_assistant = coba_assistant;
	}
	
	
	 
}
