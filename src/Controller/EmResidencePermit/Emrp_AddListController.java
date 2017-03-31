package Controller.EmResidencePermit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmResidencePermit.Emrp_ChangeBll;
import bll.EmResidencePermit.Emrp_ListBll;

import Model.CoOnlineRegisterInfoModel;
import Model.EmResidencePermitChangeModel;
import Util.RegexUtil;
import Util.UserInfo;

public class Emrp_AddListController {

	private List<CoOnlineRegisterInfoModel> emList;
	private List<CoOnlineRegisterInfoModel> semList = new ListModelList<>();

	// 检索条件
	private String cid = "";
	private String shortname = "";
	private String gid = "";
	private String name = "";

	public Emrp_AddListController() {
		try {
			Emrp_ListBll bll = new Emrp_ListBll();
			setEmList(bll.getEmbaseList());
			search();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("search")
	@NotifyChange("semList")
	public void search() {
		semList.clear();

		for (CoOnlineRegisterInfoModel m : emList) {

			if (!cid.isEmpty()) {
				if (!RegexUtil.isExists(cid, m.getCid() + "")) {
					continue;
				}
			}
			if (!shortname.isEmpty()) {
				if (!RegexUtil.isExists(shortname, m.getCoba_shortname())) {
					continue;
				}
			}
			if (!gid.isEmpty()) {
				if (!RegexUtil.isExists(gid, m.getGid() + "")) {
					continue;
				}
			}
			if (!name.isEmpty()) {
				if (!RegexUtil.isExists(name, m.getEmba_name())) {
					continue;
				}
			}
			semList.add(m);
		}
	}

	// 弹出新办窗口
	@Command("add")
	@NotifyChange("semList")
	public void add(@BindingParam("gid") Integer gid,
			@BindingParam("type") String type,
			@BindingParam("erin_id") Integer erin_id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gid", gid);
		map.put("type", type);
		map.put("erin_id", erin_id);
		Window window = (Window) Executions.createComponents("Emrp_Add.zul",
				null, map);
		window.doModal();

		Emrp_ListBll bll = new Emrp_ListBll();
		setEmList(bll.getEmbaseList());
		search();
	}

	// 居住证转换新增
	@Command("changeadd")
	@NotifyChange("semList")
	public void changeadd(@BindingParam("each") CoOnlineRegisterInfoModel coM) {
		EmResidencePermitChangeModel m = new EmResidencePermitChangeModel();

		if (Messagebox.show("是否确认提交居住证转换?", "CONFIRM", Messagebox.OK
				| Messagebox.CANCEL, Messagebox.QUESTION) == Messagebox.OK) {

			// 数据处理
			try {
				m.setGid(coM.getGid());
				m.setCid(coM.getCid());
				m.setOwnmonth(new SimpleDateFormat("yyyyMM").format(new Date()));
				m.setErpc_idcard(coM.getEmba_idcard());
				m.setErpc_laststate(0);
				m.setErpc_state(1);
				m.setErpc_addname(UserInfo.getUsername());
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("数据处理出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

			try {
				String[] str = new Emrp_ChangeBll().changeadd(m,
						coM.getEmba_name());

				if (str[0].equals("1")) {
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public List<CoOnlineRegisterInfoModel> getEmList() {
		return emList;
	}

	public void setEmList(List<CoOnlineRegisterInfoModel> emList) {
		this.emList = emList;
	}

	public List<CoOnlineRegisterInfoModel> getSemList() {
		return semList;
	}

	public void setSemList(List<CoOnlineRegisterInfoModel> semList) {
		this.semList = semList;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
