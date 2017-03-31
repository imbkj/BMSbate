package Controller.EmZYT;

import java.math.BigDecimal;

import org.zkoss.zk.ui.Executions;

import bll.EmZYT.EmZYT_SelectBll;

import Model.EmZYTModel;

public class EmZYT_DetailNewController {
	private EmZYT_SelectBll sBll = new EmZYT_SelectBll();

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

	//private int way = Integer.parseInt(Executions.getCurrent().getArg().get("way").toString()); // 父页面类型 1：委托单管理页；2：委托单申报页
	private int way =1;

	private int emzt_id = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getId();
	private String emzt_class = ((EmZYTModel) Executions.getCurrent().getArg()
			.get("emztM")).getEmzt_class();

	public EmZYT_DetailNewController() {
		// 公积金合计
		house_total = String.valueOf(housecp.add(houseop));

		// 福利津小计
		flfee = sBll.getSpecInfo(way, "福利津小计", city, emzt_flfee);

		// 服务费
		fee = sBll.getSpecInfo(way, "服务费", city, emzt_fee);

		// 档案费
		filefee = sBll.getSpecInfo(way, "档案费", city, emzt_filefee);

		// 服务费是否含档案费
		iffeefile = sBll.getSpecInfo(way, "服务费是否含档案费", city, emzt_iffeefile);

		// 总计
		total = sBll.getSpecInfo(way, "总计", city, emzt_total);

		// 报价单新增日期
		if (emzt_class.equals("新增")) {
			ezcg_addtime = "；报价单新增日期：" + sBll.getEZCGAddtime(emzt_id);
		} else {
			ezcg_addtime = "";
		}
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

}
