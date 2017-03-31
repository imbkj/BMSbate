package Controller.EmCensus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Model.EmCensusModel;
import Model.EmHJBorrowCardModel;
import Util.UserInfo;
import bll.EmCensus.EmCensus_OperateBll;
import bll.EmCensus.EmCensus_SelectBll;

public class EmCensus_BorrowCardAuditController {
	private String id = (String) Executions.getCurrent().getArg().get("daid");
	private String tperid = (String) Executions.getCurrent().getArg().get("id");
	private EmCensus_SelectBll bll = new EmCensus_SelectBll();
	private List<EmHJBorrowCardModel> blist = bll
			.getEmHJBorrowCardInfos(" and ehbc_id=" + id);
	private EmHJBorrowCardModel bmodel = new EmHJBorrowCardModel();
	private EmCensusModel emba=new EmCensusModel();

	public EmCensus_BorrowCardAuditController() {
		if (!blist.isEmpty()) {
			bmodel = blist.get(0);
			emba=bll.getEmCensusId(bmodel.getGid());
		}
	}

	// 判断是否有委托他人
	@Command
	public void rwvisible(@BindingParam("rw") Row rw,
			@BindingParam("val") String val) {
		if (val.equals("是") || val == "是") {
			rw.setVisible(true);
		} else {
			rw.setVisible(false);
		}
	}

	// 审核
	@Command
	public void EmCensusPass(@BindingParam("ifstate") String ifstate,
			@BindingParam("win") Window win) {
		String sql = "";
		int stateid = 0;
		if (ifstate.equals("1") || ifstate == "1") {
			stateid = 2;
		}
		EmHJBorrowCardModel model = new EmHJBorrowCardModel();
		model.setEhbc_id(Integer.parseInt(id));
		if (tperid != null && !tperid.equals("null") && !tperid.equals("")) {
			model.setEhbc_tarpid(Integer.parseInt(tperid));
		}
		EmCensus_OperateBll bll = new EmCensus_OperateBll();
		String[] str = bll.EmHJBorrowCardUpdate(model, sql, stateid);
		if (str[0] == "1") {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 借卡审核
	@Command
	public void borrowemCensusAudit(@BindingParam("home") Checkbox home,
			@BindingParam("homecopy") Checkbox homecopy,
			@BindingParam("hjcard") Checkbox hjcard,
			@BindingParam("borrowtime") Date borrowtime,
			@BindingParam("cash") String cash,
			@BindingParam("ifhbu") String ifhbu,
			@BindingParam("borrowhand") String borrowhand,
			@BindingParam("cashtype") String cashtype,
			@BindingParam("borrowreason") String borrowreason,
			@BindingParam("flremark") String flremark,
			@BindingParam("ifother") String ifother,
			@BindingParam("win") Window win) {
		String sql = "";
		sql = sql + ",ehbc_state = 1";
		EmHJBorrowCardModel model = new EmHJBorrowCardModel();
		model.setEhbc_id(Integer.parseInt(id));
		if (tperid != null && !tperid.equals("null") && !tperid.equals("")) {
			model.setEhbc_tarpid(Integer.parseInt(tperid));
		}
		if (home.isChecked()) {
			sql = sql + ",ehbc_sy=1";
		} else {
			sql = sql + ",ehbc_sy=0";
		}
		if (homecopy.isChecked()) {
			sql = sql + ",ehbc_syfy=1";
		} else {
			sql = sql + ",ehbc_syfy=0";
		}
		if (hjcard.isChecked()) {
			sql = sql + ",ehbc_grhk=1";
		} else {
			sql = sql + ",ehbc_grhk=0";
		}
		if (borrowtime != null) {
			sql = sql + ",ehbc_outime='" + timechange(borrowtime) + "'";
		}
		if (cash != null && !cash.equals("") && cash != "") {
			sql = sql + ",ehbc_fee='" + cash + "'";
		}
		if (borrowhand != null && !borrowhand.equals("") && borrowhand != "") {
			sql = sql + ",ehbc_handin_name='" + borrowhand + "'";
		}
		if (cashtype != null && !cashtype.equals("") && cashtype != "") {
			sql = sql + ",ehbc_wtfeetype='" + cashtype + "'";
		}
		if (borrowreason != null && !borrowreason.equals("")
				&& borrowreason != "") {
			sql = sql + ",ehbc_case='" + borrowreason + "'";
		}
		if (flremark != null && !flremark.equals("") && flremark != "") {
			sql = sql + ",ehbc_remark='" + flremark + "'";
		}
		sql = sql + ",ehbc_ifhbu='" + ifhbu + "'";
		Integer statesid = 0;
		// 判断是否有借户口首页或者户口卡，没有的话则直接结束流程
		String msg = "";
		if (bmodel.getEhbc_sy() != 1 && bmodel.getEhbc_grhk() != 1) {
			statesid = 3;
			msg = "，流程已自动完结";
		}
		EmCensus_OperateBll bll = new EmCensus_OperateBll();
		String[] str = bll.EmHJBorrowCardUpdate(model, sql, statesid);
		if (str[0] == "1") {
			Messagebox.show("提交成功" + msg, "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 确认借卡
	@Command
	public void borrowcardprint(@BindingParam("win") Window win) {
		String sql = "";
		sql = sql + ",ehbc_state = 2";
		EmHJBorrowCardModel model = new EmHJBorrowCardModel();
		model.setEhbc_id(Integer.parseInt(id));
		if (tperid != null && !tperid.equals("null") && !tperid.equals("")) {
			model.setEhbc_tarpid(Integer.parseInt(tperid));
		}
		EmCensus_OperateBll obll = new EmCensus_OperateBll();
		String[] str = obll.EmHJBorrowCardUpdate(model, sql, 0);
		if (str[0] == "1") {
			// 是否只有首页复印件
			if (bmodel != null) {
				if (bmodel.getEhbc_sy() == 0 && bmodel.getEhbc_grhk() == 0) {
					List<EmHJBorrowCardModel> blist = bll
							.getEmHJBorrowCardInfos(" and ehbc_id=" + id);
					EmHJBorrowCardModel nm = blist.get(0);
					if (nm.getEhbc_tarpid() != null) {
						obll.endjk(nm, nm.getEhbc_tarpid());
					}
					obll.updateBorrowCardInfo(nm, ",ehbc_state = 4");
				}
			}
			win.detach();
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			win.detach();
			Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 确认借卡
	public void cardprint(String ebhc_id, String tarpid, String user) {
		String sql = "";
		sql = sql + ",ehbc_state = 2";
		EmHJBorrowCardModel model = new EmHJBorrowCardModel();
		model.setEhbc_id(Integer.parseInt(ebhc_id));
		if (tperid != null && !tperid.equals("null") && !tperid.equals("")) {
			model.setEhbc_tarpid(Integer.parseInt(tarpid));
		}
		EmCensus_OperateBll bll = new EmCensus_OperateBll();
		model.setEhbc_tarpid(Integer.parseInt(tarpid));
		model.setEhbc_addname(user);
		String[] str = bll.EmHJBorrowCardUpdate(model, sql, 0);
	}

	// 还卡
	@Command
	public void borrowCardBack(@BindingParam("home") Checkbox home,
			@BindingParam("hjcard") Checkbox hjcard,
			@BindingParam("backer") String backer,
			@BindingParam("bachcash") String bachcash,
			@BindingParam("backtime") Date backtime,
			@BindingParam("win") Window win,
			@BindingParam("remark") String remark,
			@BindingParam("rctime") Date rctime) {
		if (home.isChecked() || hjcard.isChecked()) {
			String sql = "";
			int y = 0;
			EmHJBorrowCardModel model = new EmHJBorrowCardModel();
			model.setEhbc_id(Integer.parseInt(id));
			if (tperid != null && !tperid.equals("null") && !tperid.equals("")) {
				model.setEhbc_tarpid(Integer.parseInt(tperid));
			}
			if (home.isChecked()) {
				sql = sql + ",ehbc_sy=2";
			}
			if (hjcard.isChecked()) {
				sql = sql + ",ehbc_grhk=2";
			}
			if (backer != null && !backer.equals("") && backer != "") {
				sql = sql + ",ehbc_backname='" + backer + "'";
			}
			if (bachcash != null && !bachcash.equals("") && bachcash != "") {
				sql = sql + ",ehbc_backfee='" + bachcash + "'";
			}
			if (backtime != null) {
				sql = sql + ",ehbc_backtime='" + timechange(backtime) + "'";
			}

			if (rctime != null) {
				sql = sql + ",embc_rctime='" + timechange(rctime) + "'";
			}
			sql = sql + ",ehbc_remark='" + remark + "'";
			if (bmodel.getEhbc_sy() == 1) {
				if (home.isChecked() == false) {
					y = 1;
				}
			}

			// 判断是否已经还齐卡
			if (bmodel.getEhbc_grhk() == 1) {
				if (hjcard.isChecked() == false) {
					y = 1;
				}
			}
			if (y == 0) {
				sql = sql + ",ehbc_state = 4";
			} else {
				sql = ",ehbc_state = 3";
			}
			EmCensus_OperateBll bll = new EmCensus_OperateBll();
			String[] str = bll.EmHJBorrowCardUpdate(model, sql, y);
			if (str[0] == "1") {
				Messagebox.show("提交成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请至少选一种换卡类型", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//打开取消办理页面
	@Command
	public void cancel(@BindingParam("win") Window win)
	{
		Map map=new HashMap<>();
		map.put("model", bmodel);
		map.put("flag", "0");
		Window window=(Window)Executions.createComponents("../EmCensus/EmCensus_BorrowCardCancel.zul", null, map);
		window.doModal();
		if(map.get("flag").equals("1"))
		{
			win.detach();
		}
	}

	public EmHJBorrowCardModel getBmodel() {
		return bmodel;
	}

	public void setBmodel(EmHJBorrowCardModel bmodel) {
		this.bmodel = bmodel;
	}

	// 时间格式转换
	private Date timechange(java.util.Date d) {
		Date da = null;
		if (d != null && !d.equals("")) {
			java.sql.Date date = new java.sql.Date(d.getTime());
			da = date;
		}
		return da;
	}

	public String getTperid() {
		return tperid;
	}

	public void setTperid(String tperid) {
		this.tperid = tperid;
	}

	public EmCensusModel getEmba() {
		return emba;
	}

	public void setEmba(EmCensusModel emba) {
		this.emba = emba;
	}
	
}
