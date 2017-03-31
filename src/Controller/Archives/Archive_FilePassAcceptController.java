package Controller.Archives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmArchiveModel;
import Util.UserInfo;
import bll.Archives.Archive_FilePassBll;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class Archive_FilePassAcceptController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tarpid = null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveDatumModel> list = bll
			.getEmArchiveDatumInfo(" and eada_id=" + id);
	private EmArchiveDatumModel model = new EmArchiveDatumModel();
	private EmArchiveModel amodel = new EmArchiveModel();
	private List<EmArchiveModel> archivelist = new ArrayList<EmArchiveModel>();
	private List<EmArchiveLinkModel> ealist = new ListModelList<>();

	public Archive_FilePassAcceptController() {
		if (Executions.getCurrent().getArg().get("id") != null) {
			tarpid = Executions.getCurrent().getArg().get("id").toString();
		}
		if (list.size() > 0) {
			model = list.get(0);
		}
		archivelist = bll.getEmArchiveInfo(" and emar_state=1 and a.gid="
				+ model.getGid() + " and emar_fid='" + model.getEada_fid()
				+ "'");
		if (archivelist.size() > 0) {
			amodel = archivelist.get(0);
		}
		if(model.getEada_fileplace()==null||model.getEada_fileplace().equals(""))
		{
			model.setEada_fileplace(amodel.getEmar_archiveplace());
		}
		setEalist(Integer.valueOf(id));
	}
	
	
	@Command
	public void submit(@BindingParam("win") final Window win)
	{
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							Archive_FilePassBll bllo = new Archive_FilePassBll();
							EmArchiveDatumModel models = new EmArchiveDatumModel();
							models.setEada_id(model.getEada_id());
							models.setEada_addname(UserInfo.getUsername());
							models.setEada_tapr_id(model.getEada_tapr_id());
							models.setEada_type("受理申请");
							String sql = ",eada_final=2";
							String[] str = bllo.Archive_FilePassAccepted(models, sql);
							if (str[0] == "1" || str[0].equals("1")) {
//								Integer tarpid = Integer.parseInt(str[2]);
//								models.setEada_tapr_id(tarpid);
//								bllo.skiptonext(models, "", tarpid);
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

	// 退回
	@Command
	public void back(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id);
		map.put("ta", tarpid);
		map.put("model", model);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"../Archives/Archive_back.zul", null, map);
		window.doModal();
		if (map.get("flag") == "1") {
			win.detach();
		}
	}

	// 查看报价单
	@Command
	public void check() {
		Map map = new HashMap<>();
		map.put("embaId", model.getEmba_id());

		Window window = (Window) Executions.createComponents(
				"../Embase/EmQuotation.zul", null, map);
		window.doModal();
	}

	@Command("addlink")
	@NotifyChange("ealist")
	public void addlink() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("daid", Integer.valueOf(id));
		Window window = (Window) Executions.createComponents(
				"/Archives/Archive_addLink.zul", null, map);
		window.doModal();
		setEalist(Integer.valueOf(id));
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "1");
		map.put("gid",model.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public EmArchiveDatumModel getModel() {
		return model;
	}

	public void setModel(EmArchiveDatumModel model) {
		this.model = model;
	}

	public EmArchiveModel getAmodel() {
		return amodel;
	}

	public void setAmodel(EmArchiveModel amodel) {
		this.amodel = amodel;
	}

	public List<EmArchiveLinkModel> getEalist() {
		return ealist;
	}

	public void setEalist(Integer eadaId) {
		this.ealist = bll.getLinkInfo(eadaId);
	}

}
