package Controller.Archives;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Util.UserInfo;
import bll.Archives.Archive_addLinkBll;
import bll.Archives.Archive_classifyBll;

public class Archive_addLinkController {
	private Archive_addLinkBll bll = new Archive_addLinkBll();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private String username = UserInfo.getUsername();
	private EmArchiveDatumModel m = new EmArchiveDatumModel();
	private Archive_classifyBll sbll = new Archive_classifyBll();
	private List<EmArchiveLinkModel> list=new ArrayList<EmArchiveLinkModel>();
	
	public Archive_addLinkController() {
		m=sbll.getEmArchiveDatumInfo(eadaId).get(0);
		list=sbll.getInfoByGid(m.getGid());
	}

	@Command("btnSubmit")
	@NotifyChange("list")
	public void btnSubmit(@BindingParam("win") final Window win) {
		final Combobox cbtype = (Combobox) win.getFellow("linkType");
		final Datebox dbdate = (Datebox) win.getFellow("linkTime");
		final Textbox tbcontent = (Textbox) win.getFellow("content");

		if (cbtype.getSelectedItem() == null) {
			Messagebox
					.show("请选择联系方式!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			if (dbdate.getValue()==null) {
				Messagebox.show("请选择联系时间!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (tbcontent.getValue().equals("")) {
					Messagebox.show("请录入联系内容!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
							Messagebox.Button.OK, Messagebox.Button.CANCEL },
							Messagebox.QUESTION,
							new EventListener<Messagebox.ClickEvent>() {
								@Override
								public void onEvent(ClickEvent event)
										throws Exception {
									// TODO Auto-generated method stub

									if (Messagebox.Button.OK.equals(event
											.getButton())) {

										Integer i = 0;
										i = bll.addlink(eadaId, cbtype
												.getSelectedItem().getLabel(),
												dbdate.getValue(), tbcontent
														.getValue(), username,m.getGid());
										if (i > 0) {
											Messagebox.show("提交成功", "操作提示",
													Messagebox.OK,
													Messagebox.INFORMATION);
											//win.detach();
											list=sbll.getInfoByGid(m.getGid());
										} else {
											Messagebox.show("发生错误,请联系IT",
													"操作提示", Messagebox.OK,
													Messagebox.ERROR);
										}

									}
								}
							});
				}
			}
		}
	}

	public List<EmArchiveLinkModel> getList() {
		return list;
	}

	public void setList(List<EmArchiveLinkModel> list) {
		this.list = list;
	}
	
	
}
