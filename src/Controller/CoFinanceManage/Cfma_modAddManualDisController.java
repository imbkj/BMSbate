package Controller.CoFinanceManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoFinanceManualDisposableModel;
import Util.UserInfo;
import bll.CoFinanceManage.Cfma_ManualDisSpOperateBll;
import bll.CoFinanceManage.Cfma_ManualDisposableBll;

public class Cfma_modAddManualDisController {

	private CoBaseModel com;
	private Date ownmonthCom = new Date();
	private List<Integer> monthList = new ArrayList<Integer>();
	private int ownmonth;
	private CoFinanceManualDisposableModel m;
	private Cfma_ManualDisposableBll bll = new Cfma_ManualDisposableBll();
	private List<CoCompactModel> cocos = new ArrayList<CoCompactModel>();
	private List<String> copas = new ArrayList<String>();
	private String taprid = Executions.getCurrent().getArg().get("id")
			.toString();
	private String daid = Executions.getCurrent().getArg().get("daid")
			.toString();
	private String Compact;
	private String emba_name;

	public Cfma_modAddManualDisController() {
		com = bll.getCoInfo(Integer.parseInt(daid));
		m = bll.getCheckCoInfo(Integer.parseInt(daid));
		emba_name = bll.getEminfo(m.getGid()).getEmba_name();
		// 装配所属月份的combox 只能是未来的5个月
		ownmonth = Integer.parseInt(new SimpleDateFormat("yyyyMM")
				.format(ownmonthCom));
		m.setOwnmonth(ownmonth);
		for (int i = 0; i < 5; i++) {
			monthList.add(ownmonth + i);
		}
		cocos = bll.getCocoinfo(com.getCid()); // 获取到合同信息
		copas = bll.getCoPA();
		// 根据coco_id查询合同信息
		for (int i = 0; i < cocos.size(); i++) {
			if (m.getCfmd_coco_id() == cocos.get(i).getCoco_id()) {
				Compact = cocos.get(i).getCoco_compactid();
			}
		}
	}

	// 提交保存数据
	@Command
	public void commit(@BindingParam("addcoWin") Window window) {
		if (m.getCfmd_Reason() == null || m.getCfmd_Reason().equals("")) {
			Messagebox
					.show("收费原因不能为空", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			if (m.getCfmd_copr_name() == null
					|| m.getCfmd_copr_name().equals("")) {
				Messagebox.show("福利产品不能为空", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (m.getCfmd_cpac_name() == null
						|| m.getCfmd_cpac_name().equals("")) {
					Messagebox.show("财务科目名称不能为空", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					if (m.getCfmd_Receivable() == null) {
						Messagebox.show("应收费用不能为空", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					} else {
						if (m.getCfmd_coco_id() == 0) {
							Messagebox.show("必须选择一个合同", "操作提示", Messagebox.OK,
									Messagebox.ERROR);
						} else {
							Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
							m.setCfmd_addname(UserInfo.getUsername()); // 设置添加人
							m.setCid(com.getCid());
							m.setCfmd_tapr_id(Integer.parseInt(taprid));
							String str[] = null;
							// 判断应收费用是否大于0,大于0则直接不用再审核，并且将任务单终止
							if (m.getCfmd_Receivable().doubleValue() > 0) {
								m.setCfmd_state(2);
								str = operabll.stopRwd(m);
							} else {
								// 提交数据，重新进入审核
								m.setCfmd_state(4); // 状态4为审核中
								str = operabll.reSubmit(m, com);
							}
							if (str[0].equals("1")) {
								Messagebox.show(str[1], "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
								window.detach();
							} else {
								Messagebox.show(str[1], "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						}
					}
				}
			}
		}
	}

	// 删除数据
	@Command
	public void delete(@BindingParam("addcoWin") Window window) {
		// 删除数据，并终止任务单
		Cfma_ManualDisSpOperateBll operabll = new Cfma_ManualDisSpOperateBll();
		String[] str = operabll.deleteData(m);
		if (str[0].equals("1")) {
			Messagebox.show(str[1], "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
			window.detach();
		} else {
			Messagebox.show(str[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public String getEmba_name() {
		return emba_name;
	}

	public void setEmba_name(String emba_name) {
		this.emba_name = emba_name;
	}

	public String getCompact() {
		return Compact;
	}

	public void setCompact(String compact) {
		Compact = compact;
	}

	public List<String> getCopas() {
		return copas;
	}

	public void setCopas(List<String> copas) {
		this.copas = copas;
	}

	public List<CoCompactModel> getCocos() {
		return cocos;
	}

	public void setCocos(List<CoCompactModel> cocos) {
		this.cocos = cocos;
	}

	public CoFinanceManualDisposableModel getM() {
		return m;
	}

	public void setM(CoFinanceManualDisposableModel m) {
		this.m = m;
	}

	public List<Integer> getMonthList() {
		return monthList;
	}

	public void setMonthList(List<Integer> monthList) {
		this.monthList = monthList;
	}

	public int getOwnmonth() {
		return ownmonth;
	}

	public void setOwnmonth(int ownmonth) {
		this.ownmonth = ownmonth;
	}

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}

}
