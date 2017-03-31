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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmArchiveModel;
import Model.EmArchiveSetupModel;
import Model.EmDhFileModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

public class Archive_AcceptedController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tarpid =null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveDatumModel> list = new ListModelList<>();
	private List<EmArchiveLinkModel> ealist = new ListModelList<>();
	private List<EmArchiveModel> archivelist = new ArrayList<EmArchiveModel>();
	private List<EmArchiveSetupModel> setuplist = new ListModelList<>();
	private List<CoglistModel> cglist = new ListModelList<>();

	private EmArchiveDatumModel model = new EmArchiveDatumModel();
	private EmArchiveDatumModel nmodel = new EmArchiveDatumModel();
	private EmArchiveModel amodel = new EmArchiveModel();

	private EmDh_SelectBll filebll = new EmDh_SelectBll();
	private List<EmDhFileModel> filelist = filebll.getDhFile(Integer
			.parseInt(id));

	private Integer startdate;
	private EmArchiveDatumModel ml = new EmArchiveDatumModel();

	public Archive_AcceptedController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tarpid=Executions.getCurrent().getArg().get("id").toString();
		}
		setSetuplist();
		list = bll.getEmArchiveDatumInfo(" and eada_id=" + id);
		if (list.size() > 0) {
			model = list.get(0);
			nmodel= list.get(0);
		}
		archivelist = bll.getEmArchiveInfo("  and emar_state=1 and a.gid="
				+ model.getGid());
		if (archivelist.size() > 0) {
			amodel = archivelist.get(0);
			setAmodel(amodel);
		}
		if(model.getEada_fileplace()==null||model.getEada_fileplace().equals(""))
		{
			model.setEada_fileplace(amodel.getEmar_archiveplace());
		}
		setCglist(model.getGid());
		if (cglist.size() > 0) {
			startdate = cglist.get(0).getCgli_startdate();
		}
		ml = bll.getEmArchiveDatumModel(" and eada_type=0 and a.gid="+model.getGid());
		if (model.getEada_savefiledate() == null) {
			model.setEada_savefiledate(ml.getEada_savefiledate());
		}
		if (model.getEada_deadline() == null) {
			model.setEada_deadline(ml.getEada_deadline());
		}
		if (model.getEada_fileplace() == null
				|| model.getEada_fileplace().equals("")) {
			model.setEada_fileplace(ml.getEada_fileplace());
		}
		setEalist(Integer.valueOf(id));
	}

	// 提交更新
	@Command
	public void summitupdate(@BindingParam("detail") final Window detail) {
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub
						if (Messagebox.Button.OK.equals(event.getButton())) {
							EmArchiveDatum_OperateBll bllo = new EmArchiveDatum_OperateBll();
							EmArchiveDatumModel models = new EmArchiveDatumModel();
							models.setEada_id(model.getEada_id());
							models.setEada_addname(UserInfo.getUsername());
							models.setEada_tapr_id(model.getEada_tapr_id());
							models.setEada_type("受理申请");
							String sql = ",eada_final=2";
							String[] str = bllo.Accepted(models, sql);
							if (str[0] == "1" || str[0].equals("1")) {
								if (model.getEada_type().equals("查借材料")
										|| model.getEada_type().equals("出具证明")) {

									if (amodel.getEmar_archivetype() != null
											&& amodel.getEmar_colhj() != null
											&& amodel.getEmar_archivearea() != null) {
										// 档案类型为市内人才且户口挂靠为否时需要收费，否则跳过费用结算步骤
										// 市内人才、户口挂靠为否
										if (amodel.getEmar_archivetype()
												.equals("委托人才")
												&& amodel.getEmar_colhj()
														.equals("0")
												&& amodel.getEmar_archivearea()
														.equals("市内")) {

										} else {
											Integer tarpid = Integer
													.parseInt(str[2]);
											models.setEada_tapr_id(tarpid);
											bllo.skiptonext(models, "", tarpid);
										}
									} else {
										Integer tarpid = Integer
												.parseInt(str[2]);
										models.setEada_tapr_id(tarpid);
										bllo.skiptonext(models, "", tarpid);
									}
								} else {
									Integer tarpid = Integer.parseInt(str[2]);
									models.setEada_tapr_id(tarpid);
									bllo.skiptonext(models, "", tarpid);
								}
								Messagebox.show(str[1], "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								detail.detach();
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

	@Command
	public void checkbox(@BindingParam("ck") Checkbox ck) {
		if (ck.isChecked()) {
			ck.setChecked(false);
		} else {
			ck.setChecked(true);
		}
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

	public EmArchiveModel getamodel() {
		return amodel;
	}

	public void setamodel(EmArchiveModel amodel) {
		this.amodel = amodel;
	}

	public List<CoglistModel> getCglist() {
		return cglist;
	}

	public void setCglist(Integer gid) {
		this.cglist = bll.coglistInfo(gid);
	}

	public Integer getStartdate() {
		return startdate;
	}

	public void setStartdate(Integer startdate) {
		this.startdate = startdate;
	}

	public List<EmDhFileModel> getFilelist() {
		return filelist;
	}

	public void setFilelist(List<EmDhFileModel> filelist) {
		this.filelist = filelist;
	}

	public List<EmArchiveLinkModel> getEalist() {
		return ealist;
	}

	public void setEalist(Integer eadaId) {
		this.ealist = bll.getLinkInfo(eadaId);
	}

	public List<EmArchiveSetupModel> getSetuplist() {
		return setuplist;
	}

	public void setSetuplist() {
		this.setuplist = bll.getSetUp();
	}

	public EmArchiveDatumModel getNmodel() {
		return nmodel;
	}

	public void setNmodel(EmArchiveDatumModel nmodel) {
		this.nmodel = nmodel;
	}
	
}
