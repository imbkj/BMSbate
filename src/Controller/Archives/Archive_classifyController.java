package Controller.Archives;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xbill.DNS.EDNSOption;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import Model.EmArchiveDatumModel;
import Model.EmArchiveLinkModel;
import Model.EmHouseChangeModel;
import Model.LoginModel;
import Model.embaseyfModel;
import Util.UserInfo;
import bll.Archives.Archive_classifyBll;
import bll.EmHouse.EmHouse_EditBll;

public class Archive_classifyController {
	private List<EmArchiveLinkModel> ealist = new ListModelList<>();

	private Integer eadaId = 0;
	private Integer tapr_id = 0;
	private String username = UserInfo.getUsername();
	private Archive_classifyBll bll = new Archive_classifyBll();
	private String filetype = "";
	private String filearea = "";
	private boolean dis = false;
	private boolean dissdh = false;
	private Window win;
	private String eada_issdh="是";//是否开商调函

	private EmArchiveDatumModel edm = new EmArchiveDatumModel();

	public Archive_classifyController() {
		if (Executions.getCurrent().getArg().get("daid") != null) {
			eadaId = Integer.valueOf(Executions.getCurrent().getArg()
					.get("daid").toString());
		}
		if (Executions.getCurrent().getArg().get("id") != null) {
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}

		setEdm(eadaId);
		setEalist(eadaId);

	}

	@Command
	public void winD(@BindingParam("a") Window w) {
		win = w;
	}

	@Command()
	@NotifyChange({"dis","eada_issdh","dissdh"})
	public void getArea() {

		if (filetype.equals("委托人才")) {
			dis = true;
			eada_issdh="";
			dissdh=false;
		} else {
			eada_issdh="是";
			dis = false;
			filearea = "";
			dissdh=true;
		}

	}

	@Command
	public void save() {
		Integer i = bll.modData(filetype, filearea, edm.getEada_final(),
				edm.getEada_id());
		if (i > 0) {
			Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		} else {
			Messagebox.show("操作失败!", "操作提示", Messagebox.OK, Messagebox.ERROR);

		}
	}

	@Command
	public void back() {
		Map map = new HashMap<>();
		map.put("type", "档案");// 业务类型:来自WfClass的wfcl_name
		map.put("id", edm.getEada_id());// 业务表id
		map.put("tablename", "emarchivedatum");// 业务表名
		map.put("title", edm.getCoba_company() + "," + edm.getEada_name()
				+ ",档案调入");
		map.put("clazz", new Archive_classifyBll());
		map.put("method", "returnData");
		map.put("pclass", EmArchiveDatumModel.class);
		map.put("parameter", edm);
		map.put("checkName", "退回");
		map.put("tapr_id", tapr_id);
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		//m.setLog_name(edm.getEada_addname());
		m.setLog_name(edm.getCoba_client());
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		win.detach();
	}

	@Command("submit")
	public void submit() {
		if (filetype.equals("中智保管")
				|| (filetype.equals("委托人才") && !filearea.equals(""))) {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub

							if (Messagebox.Button.OK.equals(event.getButton())) {

								Integer i = bll.setFileType(eadaId, tapr_id,
										filetype, filearea, username,eada_issdh);

								if (i > 0) {
									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);

								}

								win.detach();

							}
						}
					});
		} else {
			filetype = "";
			filearea = "";
			Messagebox.show("请选择档案类型", "操作提示", Messagebox.OK, Messagebox.ERROR);

		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", eadaId.toString());
		map.put("typeid", "2");
		map.put("gid",edm.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	// 查看报价单
	@Command
	public void check() {
		Map map = new HashMap<>();
		map.put("embaId", edm.getEmba_id());
		Window window = (Window) Executions.createComponents(
				"../Embase/EmQuotation.zul", null, map);
		window.doModal();
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

	public EmArchiveDatumModel getEdm() {
		return edm;
	}

	public void setEdm(Integer id) {
		this.edm = bll.getEmArchiveDatumInfo(id).get(0);
	}

	public boolean isDis() {
		return dis;
	}

	public void setDis(boolean dis) {
		this.dis = dis;
	}

	public List<EmArchiveLinkModel> getEalist() {
		return ealist;
	}

	public void setEalist(Integer eadaId) {
		this.ealist = bll.getLinkInfo(eadaId);
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getFilearea() {
		return filearea;
	}

	public void setFilearea(String filearea) {
		this.filearea = filearea;
	}

	public String getEada_issdh() {
		return eada_issdh;
	}

	public void setEada_issdh(String eada_issdh) {
		this.eada_issdh = eada_issdh;
	}

	public boolean isDissdh() {
		return dissdh;
	}

	public void setDissdh(boolean dissdh) {
		this.dissdh = dissdh;
	}
	
}
