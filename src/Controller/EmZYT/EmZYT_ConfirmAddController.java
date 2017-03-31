package Controller.EmZYT;

import impl.WorkflowCore.WfOperateImpl;

import java.math.BigDecimal;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.GlobalCommand;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;

import Model.CoBaseModel;
import Model.EmZYTModel;
import Model.EmbaseModel;
import Util.UserInfo;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.Embase.Embase_OnBoardImpl;

public class EmZYT_ConfirmAddController {
	private EmZYT_SelectBll sBll = new EmZYT_SelectBll();
	private CoBaseModel cobaM = (CoBaseModel) Executions.getCurrent().getArg()
			.get("cobaM");
	private EmZYTModel emztM = (EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM");

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

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	String includeUrl = "../Embase/EmBase_Add.zul";

	private Window winID;
	private Include ic;

	public EmZYT_ConfirmAddController() {
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

		// 判断是否已经操作过入职且被雇员服务中心退回
		if (emztM.getEmba_id() != 0) {
			// 将任务单节点退回到第一步
			EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();
			EmbaseModel ebm = new EmbaseModel();
			List<EmbaseModel> emList = new ListModelList<>();
			emList = bll.embaseinfo(emztM.getEmba_id());
			if (emList.size() > 0) {
				ebm = emList.get(0);
			}

			if (ebm.getEmba_tapr_id() != null && ebm.getEmba_tapr_id() != 0) {
				WfBusinessService wfbs = new Embase_OnBoardImpl();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				Object[] obj = { "返回报价单", ebm };
				String[] str = wfs.ReturnToPrev(obj, ebm.getEmba_tapr_id(),
						UserInfo.getUsername(), "");
			}

		}

	}

	@Command("wininfo")
	public void wininfo(@BindingParam("a") Window winD) {
		winID = winD;
	}

	@Command("includeinfo")
	public void includeinfo(@BindingParam("ic") Include ic1) {
		ic = ic1;
	}

	@GlobalCommand
	public void winClose(@ContextParam(ContextType.VIEW) Component view) {
		Binder bind = (Binder) view.getParent().getAttribute("binder");
		bind.postCommand("refreshWin", null);
		winID.detach();
	}

	@GlobalCommand
	public void embaNext(@BindingParam("ebm") EmbaseModel ebm1,
			@BindingParam("emztM") EmZYTModel emztM1) {
		includeUrl = "../Embase/EmBase_Entry.zul?way=1";

		Executions.getCurrent().setAttribute("ebm", ebm1);
		Executions.getCurrent().setAttribute("emztM", emztM1);
		BindUtils.postNotifyChange(null, null, this, "includeUrl");
		ic.invalidate();
	}

	@GlobalCommand
	public void embaPrev(@BindingParam("daid") Integer daid1,
			@BindingParam("embaId") Integer embaId1,
			@BindingParam("emztM") EmZYTModel emztM1) {

		includeUrl = "../Embase/EmBase_Add.zul?way=1";
		Executions.getCurrent().setAttribute("daid", daid1);
		Executions.getCurrent().setAttribute("embaId", embaId1);
		Executions.getCurrent().setAttribute("emztM", emztM1);
		BindUtils.postNotifyChange(null, null, this, "includeUrl");
		ic.invalidate();

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

	public CoBaseModel getCobaM() {
		return cobaM;
	}

	public void setCobaM(CoBaseModel cobaM) {
		this.cobaM = cobaM;
	}

	public EmZYTModel getEmztM() {
		return emztM;
	}

	public void setEmztM(EmZYTModel emztM) {
		this.emztM = emztM;
	}

	public String getIncludeUrl() {
		return includeUrl;
	}

	public void setIncludeUrl(String includeUrl) {
		this.includeUrl = includeUrl;
	}

}
