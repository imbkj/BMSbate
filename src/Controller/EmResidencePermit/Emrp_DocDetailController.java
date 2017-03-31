package Controller.EmResidencePermit;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

public class Emrp_DocDetailController {
	
	private Integer daid = 0;
	private Integer gid = 0;

	public Emrp_DocDetailController() {
		try {
			setDaid(Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString()));
			setGid(Integer.parseInt(Executions.getCurrent().getArg().get("gid")
					.toString()));
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Integer getDaid() {
		return daid;
	}

	public void setDaid(Integer daid) {
		this.daid = daid;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}
}
