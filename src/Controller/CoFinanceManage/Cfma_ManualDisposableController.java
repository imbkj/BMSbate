package Controller.CoFinanceManage;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.CoFinanceManualDisposableModel;
import bll.CoFinanceManage.Cfma_ManualDisposableBll;

/**
 * 手动添加非标记录
 * 
 * @author Administrator
 * 
 */
public class Cfma_ManualDisposableController {

	private int cid = (Integer) Executions.getCurrent().getArg().get("cid");
	private CoBaseModel com;
	private Date ownmonth = new Date();
	private Cfma_ManualDisposableBll bll = new Cfma_ManualDisposableBll();
	private List<CoFinanceManualDisposableModel> colist;
	private List<CoFinanceManualDisposableModel> emlist;
	private String cototal;
	private String emtotal;
	private Window window;

	public Cfma_ManualDisposableController() {
		init();
	}

	// 初始化
	public void init() {
		com = bll.getCoBase(cid);
		// 获取公司非标信息
		colist = bll.getCoFinanceManualDisposable(cid, ownmonth);
		// 获取员工非标信息
		emlist = bll.getEmFinanceManualDisposable(cid, ownmonth);
		search(ownmonth);
	}

	@Command
	@NotifyChange({ "cototal", "emtotal", "emlist", "colist" })
	public void search(@BindingParam("month") Date month) {
		colist = bll.getCoFinanceManualDisposable(cid, month);
		// 获取员工非标信息
		emlist = bll.getEmFinanceManualDisposable(cid, month);
		searchCoTotal(month);
		searchEmTotal(month);
	}

	// 查询公司总计
	public void searchCoTotal(Date month) {
		double cob = 0.00;
//		for (int i = 0; i < colist.size(); i++) {
//			cob = cob + colist.get(i).getCfmd_Receivable().doubleValue();
//		}
//		cototal = new SimpleDateFormat("yyyyMM").format(month) // 获取公司应收总计
//				+ "月份的公司非标合计应收费用为"
//				+ new DecimalFormat("#0.00").format(cob)
//				+ "元";
	}

	// 查询员工总计
	public void searchEmTotal(Date month) {
		double emb = 0.00;
//		for (int i = 0; i < emlist.size(); i++) {
//			emb = emb + emlist.get(i).getCfmd_Receivable().doubleValue();
//		}
//		emtotal = new SimpleDateFormat("yyyyMM").format(month) // 获取员工应收总计
//				+ "月份的员工非标合计应收费用为"
//				+ new DecimalFormat("#0.00").format(emb)
//				+ "元";
	}

	// 添加公司非标
	@Command
	@NotifyChange("colist")
	public void addCoFb(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, CoBaseModel> map = new HashMap<String, CoBaseModel>();
		map.put("m", com);
		window = (Window) Executions.createComponents(
				"/CoFinanceManage/Cfma_AddManualDisposable.zul", view, map);
		window.doModal();
	}

	// 添加员工非标
	@Command
	@NotifyChange("emlist")
	public void addEmFb(@ContextParam(ContextType.VIEW) Component view) {
		Map<String, CoBaseModel> map = new HashMap<String, CoBaseModel>();
		map.put("m", com);
		window = (Window) Executions.createComponents(
				"/CoFinanceManage/Cfma_AddEmManualDisposable.zul", view, map);
		window.doModal();
	}

	// 刷新员工列表
	@Command
	@NotifyChange({ "emlist", "emtotal" })
	public void emlist(@BindingParam("ownmonth") int month) {
		System.out.println(month);
		try {
			emlist = bll.getEmFinanceManualDisposable(cid,
					new SimpleDateFormat("yyyyMM").parse(month + ""));
			searchEmTotal(new SimpleDateFormat("yyyyMM").parse(month + ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	// 刷新公司列表
	@Command
	@NotifyChange({ "colist", "cototal" })
	public void colist(@BindingParam("ownmonth") int month) {
		try {
			colist = bll.getCoFinanceManualDisposable(cid,
					new SimpleDateFormat("yyyyMM").parse(month + ""));
			searchCoTotal(new SimpleDateFormat("yyyyMM").parse(month + ""));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getEmtotal() {
		return emtotal;
	}

	public void setEmtotal(String emtotal) {
		this.emtotal = emtotal;
	}

	public String getCototal() {
		return cototal;
	}

	public void setCototal(String cototal) {
		this.cototal = cototal;
	}

	public List<CoFinanceManualDisposableModel> getColist() {
		return colist;
	}

	public void setColist(List<CoFinanceManualDisposableModel> colist) {
		this.colist = colist;
	}

	public List<CoFinanceManualDisposableModel> getEmlist() {
		return emlist;
	}

	public void setEmlist(List<CoFinanceManualDisposableModel> emlist) {
		this.emlist = emlist;
	}

	public Date getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(Date ownmonth) {
		this.ownmonth = ownmonth;
	}

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}

}
