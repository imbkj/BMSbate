package Controller.Archives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoglistModel;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Util.UserInfo;
import bll.Archives.EmArchiveDatum_OperateBll;
import bll.Archives.EmArchive_SelectBll;

public class Archive_FileConsultComfirmController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tarpid = null;
	private EmArchive_SelectBll bll = new EmArchive_SelectBll();
	private List<EmArchiveDatumModel> list = bll
			.getEmArchiveDatumInfo(" and eada_id=" + id);
	EmArchiveDatumModel model = new EmArchiveDatumModel();
	private EmArchiveModel amodel = new EmArchiveModel();
	private List<EmArchiveModel> archivelist = new ArrayList<EmArchiveModel>();
	private List<CoglistModel> cglist = new ListModelList<>();
	private Integer startdate;
	private String iffee = "", feeinfo;
	private EmArchiveDatumModel ml = new EmArchiveDatumModel();
	private String emar_archiveplace="";
	public Archive_FileConsultComfirmController() {
		if(Executions.getCurrent().getArg().get("id")!=null)
		{
			tarpid=Executions.getCurrent().getArg().get("id").toString();
		}
		if (list.size() > 0) {
			model = list.get(0);
			if(model.getEada_fileplace()!=null&&!model.getEada_fileplace().equals(""))
			{
				emar_archiveplace=model.getEada_fileplace();
			}
		}
		archivelist = bll.getEmArchiveInfo(" and emar_state=1 and a.gid="
				+ model.getGid());
		if(archivelist.size()<=0)
		{
			archivelist = bll.getEmArchiveInfo(" and a.gid="
					+ model.getGid());
		}
		
		if (archivelist.size() > 0) {
			amodel = archivelist.get(0);
			setAmodel(amodel);
			if(emar_archiveplace.equals(""))
			{
				if(amodel.getEmar_archiveplace()!=null&&!amodel.getEmar_archiveplace().equals(""))
				{
					emar_archiveplace=amodel.getEmar_archiveplace();
				}
			}
		}
		setCglist(model.getGid());
		if (cglist.size() > 0) {
			startdate = cglist.get(0).getCgli_startdate();
		}
		if(model.getEada_fileplace()==null||model.getEada_fileplace().equals(""))
		{
			model.setEada_fileplace(emar_archiveplace);
		}
	}

	// 提交更新
	@Command
	public void summitupdate(@BindingParam("detail") final Window detail) {
		if (iffee != null && !iffee.equals("")) {
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
								if (feeinfo != null && !feeinfo.equals("")) {
									if (iffee != null && iffee.equals("是")) {
										sql = sql + ",eada_arrearageinfo='"
												+ feeinfo + "',eada_iffee=1";
									}
								}
								String[] str = bllo.Accepted(models, sql);
								if (str[0] == "1" || str[0].equals("1")) {
									if (iffee != null && iffee.equals("否")) {
										Integer tarpid = Integer
												.parseInt(str[2]);
										models.setEada_tapr_id(tarpid);
										bllo.skiptonext(models, "", tarpid);
									}
									Messagebox.show(str[1], "提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
									detail.detach();
								} else {
									Messagebox.show(str[1], "提示",
											Messagebox.OK, Messagebox.ERROR);
								}
							}
						}
					});
		} else {
			Messagebox.show("请选择是否欠费", "提示", Messagebox.OK, Messagebox.ERROR);
		}

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

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", id.toString());
		map.put("typeid", "2");
		map.put("gid",model.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTarpid() {
		return tarpid;
	}

	public void setTarpid(String tarpid) {
		this.tarpid = tarpid;
	}

	public List<EmArchiveDatumModel> getList() {
		return list;
	}

	public void setList(List<EmArchiveDatumModel> list) {
		this.list = list;
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

	public List<EmArchiveModel> getArchivelist() {
		return archivelist;
	}

	public void setArchivelist(List<EmArchiveModel> archivelist) {
		this.archivelist = archivelist;
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

	public String getIffee() {
		return iffee;
	}

	public void setIffee(String iffee) {
		this.iffee = iffee;
	}

	public String getFeeinfo() {
		return feeinfo;
	}

	public void setFeeinfo(String feeinfo) {
		this.feeinfo = feeinfo;
	}

	public String getEmar_archiveplace() {
		return emar_archiveplace;
	}

	public void setEmar_archiveplace(String emar_archiveplace) {
		this.emar_archiveplace = emar_archiveplace;
	}
	
}
