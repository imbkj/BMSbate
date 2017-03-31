package Controller.EmZYT;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.CoBaseModel;
import Model.EmZYTModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmbaseListBll;

public class EmZYT_ConfirmAdjustController {
	private EmZYT_SelectBll sBll = new EmZYT_SelectBll();
	private EmZYT_OperateBll oBll = new EmZYT_OperateBll();

	private BigDecimal housecp = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_housecp();
	private BigDecimal houseop = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_houseop();

	private String house_total; // 公积金合计
	private String flfee; // 福利津小计
	private String fee; // 服务费
	private String filefee; // 档案费
	private String iffeefile; // 服务费是否含档案费
	private String total; // 总计
	private String ezcg_addtime; // 报价单新增日期
	private Integer embaId;
	private Integer gid = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getGid();
	private Integer cid = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getCid();

	private String city = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_scity();
	private String emzt_flfee = String.valueOf(((EmZYTModel) Executions
			.getCurrent().getArg().get("emztM")).getEmzt_flfee());
	private String emzt_fee = String.valueOf(((EmZYTModel) Executions
			.getCurrent().getArg().get("emztM")).getEmzt_fee());
	private String emzt_filefee = String.valueOf(((EmZYTModel) Executions
			.getCurrent().getArg().get("emztM")).getEmzt_filefee());
	private String emzt_iffeefile = String.valueOf(((EmZYTModel) Executions
			.getCurrent().getArg().get("emztM")).getEmzt_iffeefile());
	private String emzt_total = String.valueOf(((EmZYTModel) Executions
			.getCurrent().getArg().get("emztM")).getEmzt_total());

	private int emzt_id = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getId();
	private String emzt_class = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_class();
	private int emzt_tapr_id = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_tapr_id();

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	private Window winID;

	public EmZYT_ConfirmAdjustController() {
		// 公积金合计
		house_total = String.valueOf(housecp.add(houseop));

		// 福利津小计
		flfee = sBll.getSpecInfo(1, "福利津小计", city, emzt_flfee);

		// 服务费
		fee = sBll.getSpecInfo(1, "服务费", city, emzt_fee);

		// 档案费
		filefee = sBll.getSpecInfo(1, "档案费", city, emzt_filefee);

		// 服务费是否含档案费
		iffeefile = sBll.getSpecInfo(1, "服务费是否含档案费", city, emzt_iffeefile);

		// 总计
		total = sBll.getSpecInfo(1, "总计", city, emzt_total);

		// 报价单新增日期
		if (emzt_class.equals("新增")) {
			ezcg_addtime = "；报价单新增日期：" + sBll.getEZCGAddtime(emzt_id);
		} else {
			ezcg_addtime = "";
		}

		EmbaseListBll embasebll = new EmbaseListBll();
		try {
			embaId = embasebll.searchembaselist(String.valueOf(gid)).get(0)
					.getEmba_id();
		} catch (Exception e) {
			embaId = 0;
		}

	}

	// 弹出公司业务中心
	@Command("openCoManager")
	public void openCoManager() {
		if (cid != 0 && cid != null) {
			CoBase_SelectBll sebll = new CoBase_SelectBll();
			CoBaseModel cobaM = new ListModelList<CoBaseModel>(
					sebll.getCobaseeditinfo(" and  a.cid="
							+ String.valueOf(cid))).get(0);
			Map map = new HashMap();
			map.put("cid", cid);
			map.put("model", cobaM);
			Window window = (Window) Executions.createComponents(
					"../CoMenuList/CoMe_List.zul", null, map);
			window.doModal();
		} else {
			// 弹出提示
			Messagebox.show("请把补充公司编号！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 转雇员服务中心
	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {

		EmZYTModel m = new EmZYTModel();
		m.setId(emzt_id);
		m.setEmzt_state(2);
		m.setEmzt_declarename(username);
		m.setEmzt_tapr_id(emzt_tapr_id);

		// 调用更新方法
		String[] message;
		message = oBll.confirmAdjust(m);
		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// 关闭页面
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	// 已处理
	@Command("success")
	public void success(@BindingParam("win") final Window win) {

		EmZYTModel m = new EmZYTModel();
		m.setId(emzt_id);
		m.setEmzt_state(1);
		m.setEmzt_declarename(username);
		m.setEmzt_tapr_id(emzt_tapr_id);

		// 调用更新方法
		String[] message;
		message = oBll.declareEmZYTUpdate(m);
		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// 关闭页面
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	@Command("wininfo")
	public void wininfo(@BindingParam("a") Window winD) {
		winID = winD;
	}

	@GlobalCommand
	public void winClose(@ContextParam(ContextType.VIEW) Component view) {
		Binder bind = (Binder) view.getParent().getAttribute("binder");
		bind.postCommand("refreshWin", null);
		winID.detach();
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getFlfee() {
		return flfee;
	}

	public void setFlfee(String flfee) {
		this.flfee = flfee;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getHouse_total() {
		return house_total;
	}

	public void setHouse_total(String house_total) {
		this.house_total = house_total;
	}

	public String getFilefee() {
		return filefee;
	}

	public void setFilefee(String filefee) {
		this.filefee = filefee;
	}

	public String getIffeefile() {
		return iffeefile;
	}

	public void setIffeefile(String iffeefile) {
		this.iffeefile = iffeefile;
	}

	public String getEzcg_addtime() {
		return ezcg_addtime;
	}

	public void setEzcg_addtime(String ezcg_addtime) {
		this.ezcg_addtime = ezcg_addtime;
	}

	public Integer getEmbaId() {
		return embaId;
	}

	public void setEmbaId(Integer embaId) {
		this.embaId = embaId;
	}

	public Integer getGid() {
		return gid;
	}

	public void setGid(Integer gid) {
		this.gid = gid;
	}

}
