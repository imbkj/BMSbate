package Controller.EmHouse;



import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;

import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmHouseBJModel;
import Util.CalendarDate;
import Util.DateUtil;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmHouse.EmHouseSetBll;

import bll.EmHouse.Emhouse_BjBll;

public class EmHouse_BjModController {
	private EmHouseBJModel ejm = new EmHouseBJModel();
	private List<String> ownmonglist = new ListModelList<>();
	private List<String> feeownmonglist = new ListModelList<>();
	private List<EmHouseBJModel> list = new ListModelList<>();

	private Window win;
	private Emhouse_BjBll bll = new Emhouse_BjBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

	private Integer bjid = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Date d1;
	private Date d2;

	public EmHouse_BjModController() {
		ejm.setEmhb_id(bjid);
		setList();

		if (list.size() > 0) {
			ejm = list.get(0);
			ownmonglist = sbll.ownmonthlist("bj", ejm.getCid(), ejm.getGid(),null);
			feeownmonglist = sbll.ownmonthlist("fee", ejm.getCid(),
					ejm.getGid(), Integer.valueOf(ejm.getOwnmonth()));
			if (ejm.getEmhb_startmonth() != null) {
				try {
					d1 = sdf.parse(ejm.getEmhb_startmonth().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (ejm.getEmhb_stopmonth() != null) {
				try {
					d2 = sdf.parse(ejm.getEmhb_stopmonth().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	@Command("winC")
	public void winC(@BindingParam("a") Window winD) {
		win = winD;
		if (ejm.getGid() == null) {
			Messagebox.show("暂无公积金在册信息!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			win.detach();
			return;
		}
		if (ejm.getEmhb_companyid() == null) {
			Messagebox.show("该用户单位公积金账户信息不全!", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			// win.detach();
		}

	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ejm.getGid() + "",
				"雇员服务中心");
	}

	@Command
	@NotifyChange("ejm")
	public void sumtotal() {
		Datebox db1 = (Datebox) win.getFellow("std");
		Datebox db2 = (Datebox) win.getFellow("spd");
		
		Double radix = 0.0;
		if (ejm.getEmhb_radix().compareTo(BigDecimal.ZERO) >0) {
			radix = sbll.radixLimit(ejm.getEmhb_radix());
			// radix = db.getValue();
		}

		if (db1.getValue() != null && db2.getValue() != null
				&& radix != null) {
			if (DateUtil.datediff(db1.getValue(), db2.getValue(), "d") >= 0) {
				
				BigDecimal bd = new BigDecimal(radix
						* ejm.getEmhb_cpp()
						* 2
						* (CalendarDate.calInterval(db1.getValue(),
								db2.getValue(), "m") + 1));
				
				ejm.setEmhb_total(bd.setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				Messagebox.show("补缴终止月小于补缴起始月!", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}

	}

	@Command
	public void submit() {
		/*
		List<EmHouseBJModel> l = bll.getemhousebjList(ejm.getGid(), ejm.getOwnmonth());
		if (l.size()>0) {
			Messagebox.show("当前月份已有补缴数据!", "提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}*/
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {
							Datebox dbst = (Datebox) win.getFellow("std");
							Datebox dbsp = (Datebox) win.getFellow("spd");
							if (dbst.getValue() != null) {
								ejm.setEmhb_startmonth(Integer.valueOf(sdf
										.format(dbst.getValue())));
							}
							if (dbsp.getValue() != null) {
								ejm.setEmhb_stopmonth(Integer.valueOf(sdf
										.format(dbsp.getValue())));
							}
							ejm.setEmhb_radix(ejm.getEmhb_radix().setScale(2,
									BigDecimal.ROUND_HALF_DOWN));
							ejm.setEmhb_addname(UserInfo.getUsername());

							Integer i = bll.emhousebjcommit(ejm, bjid);

							if (i > 0) {
								DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
								Grid grid = (Grid) win.getFellow("docGrid");
								String[] message = docOC.UpsubmitDoc(grid, ejm
										.getEmhb_id().toString());

								Messagebox.show("提交成功.", "提示", Messagebox.OK,
										Messagebox.INFORMATION);
								win.detach();
							} else {
								Messagebox.show("提交失败.", "提示", Messagebox.OK,
										Messagebox.ERROR);
							}

						}
					}
				});
	}

	public EmHouseBJModel getEjm() {
		return ejm;
	}

	public void setEjm(EmHouseBJModel ejm) {
		this.ejm = ejm;
	}

	public List<EmHouseBJModel> getList() {
		return list;
	}

	public void setList() {
		this.list = bll.emhousebjList(ejm);
	}

	public List<String> getOwnmonglist() {
		return ownmonglist;
	}

	public void setOwnmonglist(List<String> ownmonglist) {
		this.ownmonglist = ownmonglist;
	}

	public List<String> getFeeownmonglist() {
		return feeownmonglist;
	}

	public void setFeeownmonglist(List<String> feeownmonglist) {
		this.feeownmonglist = feeownmonglist;
	}

	public Date getD1() {
		return d1;
	}

	public void setD1(Date d1) {
		this.d1 = d1;
	}

	public Date getD2() {
		return d2;
	}

	public void setD2(Date d2) {
		this.d2 = d2;
	}

}
