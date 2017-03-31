package Controller.CoReg;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import bll.CoReg.CoReg_ListBll;
import bll.CoReg.CoReg_OperateBll;

import Model.CoOnlineRegisterInfoModel;
import Model.DocumentsInfoModel;

public class CoReg_DocAllotController {

	private List<DocumentsInfoModel> docList;
	private String hjtype = "深户";
	private boolean allcheck = false;
	CoOnlineRegisterInfoModel com = new CoOnlineRegisterInfoModel();

	// 页面传值
	Integer daid = 0;

	public CoReg_DocAllotController() {
		try {
			CoReg_ListBll bll = new CoReg_ListBll();

			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			com = bll.getCoregInfo(daid);
			setDocList(new ListModelList<>(bll.getDocList(com
					.getCori_sz_puzu_id())));

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("allcheck")
	public void allcheck(@BindingParam("gd") Grid gd) {
		Integer rowcount = gd.getRows().getChildren().size();
		for (int i = 0; i < rowcount; i++) {
			Checkbox ckb1 = (Checkbox) gd.getCell(i, 0).getChildren().get(0);
			ckb1.setChecked(allcheck);
		}
	}

	@Command("search")
	@NotifyChange({ "docList", "allcheck" })
	public void search() {
		try {
			CoReg_ListBll bll = new CoReg_ListBll();
			if (hjtype.equals("深户")) {
				setDocList(new ListModelList<>(bll.getDocList(com
						.getCori_sz_puzu_id())));
			} else {
				setDocList(new ListModelList<>(bll.getDocList(com
						.getCori_wd_puzu_id())));
			}
			allcheck = false;
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("检索出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			CoReg_OperateBll bll = new CoReg_OperateBll();
			Integer row = 0;
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				DocumentsInfoModel m = ((Row) gd.getRows().getChildren().get(i))
						.getValue();
				if (((Checkbox) gd.getCell(i, 0).getChildren().get(0))
						.isChecked()) {
					m.setDire_state(1 + "");
				} else {
					m.setDire_state(0 + "");
				}
				row += bll.EmRegDocUpdateState(m);
			}
			if (row > 0) {
				if (Messagebox.show("保存成功,是否关闭窗口?", "QUESTION", Messagebox.OK
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.OK) {
					win.detach();
				}
			} else {
				Messagebox.show("保存失败!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("保存出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public List<DocumentsInfoModel> getDocList() {
		return docList;
	}

	public void setDocList(List<DocumentsInfoModel> docList) {
		this.docList = docList;
	}

	public String getHjtype() {
		return hjtype;
	}

	public void setHjtype(String hjtype) {
		this.hjtype = hjtype;
	}

	public boolean isAllcheck() {
		return allcheck;
	}

	public void setAllcheck(boolean allcheck) {
		this.allcheck = allcheck;
	}
}
