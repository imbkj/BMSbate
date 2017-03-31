package Controller.EmCensus.EmDh;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmDhModel;
import bll.EmCensus.EmDh.EmDh_LxOpeate;
import bll.EmCensus.EmDh.EmDh_LxOpeateFactory;
import bll.EmCensus.EmDh.EmDh_LxOpeateFactoryImpl;
import bll.EmCensus.EmDh.EmDh_LxOperateBll;
import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class EmDh_LxHandOverDataController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmDh_SelectBll bll = new EmDh_SelectBll();
	private List<EmDhModel> dhmodellist = bll.getEmDhInfo(" and a.id=" + id);
	private EmDhModel dhmodel = new EmDhModel();

	public EmDh_LxHandOverDataController() {
		if (!dhmodellist.isEmpty()) {
			dhmodel = dhmodellist.get(0);
		}
	}

	// 暂存提交按钮
	@Command("submit")
	public void submit(@BindingParam("win") final Window win,
			@BindingParam("docGrid") Grid docGrid) {
		DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		// 调用内联页方法chkHaveTo(Grid gd)
		try {
			String[] message = docOC.UpchkHaveTo(docGrid);
			// 判断材料必选项是否已选
			if (message[0].equals("1")) {
				message = docOC.UpsubmitDoc(docGrid, id);
				if (message[0].equals("1")) {
					Messagebox.show("提交成功", "操作提示", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("提交失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("有必选材料没有选,请选择", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 交接材料确认提交
	@Command("submitconfirm")
	public void submitconfirm(@BindingParam("win") final Window win,
			@BindingParam("docGrid") final Grid docGrid,
			@BindingParam("emdh_time") final Date emdh_time) throws Exception {
		final DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
		String[] message = docOC.UpchkHaveTo(docGrid);
		if (emdh_time == null) {
			Messagebox.show("请选择材料交齐时间", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else if (message[0] != "1") {
			Messagebox.show("有必选材料没有选,请选择", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.YES.equals(event.getButton())) {
						EmDh_LxOperateBll opbll = new EmDh_LxOperateBll();
						EmDhModel m = new EmDhModel();
						m.setId(Integer.parseInt(id));
						if (tperid != null && !tperid.equals("")) {
							m.setEmdh_taprid(Integer.parseInt(tperid));
						}
						m.setStates("交接材料");
						m.setEmdh_time2(emdh_time);
						EmDh_LxConreoller lxController=new EmDh_LxConreollerImpl("交接材料");
						String[] str=lxController.edit(m);
						if (str[0].equals("1")) {
							// 调用内联页方法submitDoc(Grid gd)
							docOC.UpsubmitDoc(docGrid, id);
							Messagebox.show("提交成功", "操作提示", Messagebox.OK,
									Messagebox.INFORMATION);
							win.detach();
						} else {
							Messagebox.show(str[1], "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						}
					}
				}
			};
			Messagebox.show("确认交齐材料并提交?", "提示", new Messagebox.Button[] {
					Messagebox.Button.YES, Messagebox.Button.NO },
					Messagebox.QUESTION, clickListener);
		}
	}

	// 弹出备注
	@Command
	public void addRemark() {
		Map map = new HashMap<>();
		map.put("daid", dhmodel.getId() + "");
		map.put("id", dhmodel.getEmdh_taprid() + "");
		map.put("model", dhmodel);
		map.put("gid", dhmodel.getGid());
		Window window = (Window) Executions.createComponents(
				"../EmCensus/Emdh_AddRemark.zul", null, map);
		window.doModal();
	}

	public EmDhModel getDhmodel() {
		return dhmodel;
	}

	public void setDhmodel(EmDhModel dhmodel) {
		this.dhmodel = dhmodel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// 时间格式转换
	private java.sql.Date timechange(Date d) {
		java.sql.Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}
}
