package Controller.Archives;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_ClientCommitBll;
import bll.Archives.EmArchiveDatum_OperateBll;

import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmArchivePaymentModel;
import Util.UserInfo;

public class Archive_ClientCommitController {
	private EmArchiveDatumModel edm = new EmArchiveDatumModel();
	private List<EmArchiveLinkModel> ealist = new ListModelList<>();
	private Archive_ClientCommitBll bll = new Archive_ClientCommitBll();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id =null;
	private String username = UserInfo.getUsername();

	public Archive_ClientCommitController() {
		if(Executions.getCurrent().getArg()
				.get("id")!=null)
		{
			tapr_id=Integer.parseInt(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		setEalist(eadaId);// 联系信息列表
		try {
			edm = bll.getEmarchiveDatumInfoById(eadaId).get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	@Command("addlink")
	@NotifyChange("ealist")
	public void addlink() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("daid", eadaId);
		Window window = (Window) Executions.createComponents(
				"/Archives/Archive_addLink.zul", null, map);
		window.doModal();
		setEalist(eadaId);
	}

	@Command
	@NotifyChange("ealist")
	public void del(@BindingParam("a") final EmArchiveLinkModel em) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Integer i = bll.delMessage(em.getEali_id());
							if (i > 0) {

								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								setEalist(eadaId);
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							EmArchiveDatum_OperateBll blloper = new EmArchiveDatum_OperateBll();
							String[] str = blloper.Accepted(edm, "");
							if (str[0] == "1") {
								Messagebox.show(str[1], "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show(str[1], "提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				});
	}

	public EmArchiveDatumModel getEdm() {
		return edm;
	}

	public void setEdm(EmArchiveDatumModel edm) {
		this.edm = edm;
	}

	public List<EmArchiveLinkModel> getEalist() {
		return ealist;
	}

	public void setEalist(Integer id) {
		this.ealist = bll.getLinkInfo(id);
	}

}
