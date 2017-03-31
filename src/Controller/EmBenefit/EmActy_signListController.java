package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import Model.EmActySuppilerGiftInfoModel;
import Model.EmActyWarehouseModel;
import Model.EmWelfareModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBenefit.EmActy_GiftInfoOperateBll;
import bll.EmBenefit.EmActy_GiftInfoSelectBll;
import bll.EmBenefit.EmBenefit_comitListBll;

public class EmActy_signListController {

	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<EmWelfareModel> list = new ListModelList<>();
	// private List<EmWelfareModel> listc = new ListModelList<>();
	private EmWelfareModel ewfm = new EmWelfareModel();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();

	private EmActy_GiftInfoSelectBll blls = new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> lists = blls
			.getEmActyGiftInfo(" and gift_id=" + id);
	private Window win = (Window) Path.getComponent("/winEmp");
	private String sortid = "", giftname = "", remark = "";
	private Integer num = 0, noenum;
	String sql = "", sqlc = "", username = UserInfo.getUsername();
	private EmActySuppilerGiftInfoModel ml = new EmActySuppilerGiftInfoModel();

	public EmActy_signListController() {
		if (lists.size() > 0) {
			giftname = lists.get(0).getGift_name();
			noenum = lists.get(0).getGift_nownum();
			ewfm.setEmwf_state(4);
			sortid = lists.get(0).getGift_sortid();

			ewfm.setEmwf_sortid(sortid);
			sql = "and emwf_sortid='" + ewfm.getEmwf_sortid()
					+ "'  and emwf_state=7";
			list = bll.getLists(sql);
			sqlc = "and emwf_sortid='" + ewfm.getEmwf_sortid()
					+ "'  and emwf_state =7";
			// listc=bll.getLists(sqlc);
			num = list.size();
			ml = lists.get(0);
		}
	}

	// 全选
	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka) {
		Grid gd = (Grid) win.getFellow("gdList");
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 13) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren().get(0)
						.getChildren().get(0);
				ck.setChecked(cka.isChecked());

			} else {

				return;
			}

		}
	}

	// 单个签收签收
	@Command
	@NotifyChange({ "list", "num" })
	public void sign(@BindingParam("win") Window win,
			@BindingParam("model") final EmWelfareModel model) {
		remark = "签收礼品";
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("id", id);
		map.put("tarpid", tapr_id);
		Window window = (Window) Executions.createComponents(
				"../EmBenefit/EmActy_GiftClientSign.zul", null, map);
		window.doModal();
		list = bll.getLists(sql);
		num = list.size();
		if (num <= 0) {
			tonext(",gift_state=6");
			win.detach();
		}
	}

	// 流程转到下一步
	private String[] tonext(String strsql) {
		//检查是否所有的礼品都使用库存，所有的礼品都使用了库存则直接完结流程
		List<EmWelfareModel> telist = new ListModelList<>();
		String sssql = "and emwf_sortid='" + ewfm.getEmwf_sortid()
				+ "'  and emwf_state=8";
		telist = bll.getLists(sssql);
		int anum = 0;
		String[] str = new String[5];
		EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		m.setGift_remark(remark);
		EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
		for (EmWelfareModel wmm : telist) {
			anum = anum + wmm.getEmwf_amount();
		}
		int kkl = lists.get(0).getGift_userhousenum();
		int totalnum=lists.get(0).getGift_totalnum();
		if (kkl==totalnum) {
			str = obll.endEmActy_GiftInfos(m, strsql, "3");
		} else {
			str = obll.updateEmActy_GiftInfos(m, strsql, "3");
		}
		win.detach();
		return str;
	}

	// 流程转到下一步(预付款)——直接结束流程
	private String[] tonexts(String strsql) {
		String[] str = new String[5];
		EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		m.setGift_remark("签收礼品");
		// String strsql=",gift_state=5";
		EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
		str = obll.endEmActy_GiftInfos(m, strsql, "3");
		win.detach();
		return str;
	}

	// 发礼品下一步提交
	@Command
	public void summit() {
		remark = "发放礼品";
		String str = "提交后流程将转到下一步，是否确认提交";
		if (list.size() > 0) {
			str = "还有礼品没有发放,是否确定转下一步";
		}
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					tonext(",gift_state=5");
				}
			}
		};
		Messagebox.show(str, "提示", new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);
	}

	// 签收礼品下一步提交
	@Command
	public void summitsign() {
		remark = "签收礼品";
		String str = "提交后流程将转到下一步，是否确认提交";
		if (list.size() > 0) {
			str = "还有礼品没有签收,是否确定转下一步";
		}
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					// 把没有发放的礼品放到库存
					if (list.size() > 0) {
						AddHouse();
					}
					tonext(",gift_state=6");
				}
			}
		};
		Messagebox.show(str, "提示", new Messagebox.Button[] {
				Messagebox.Button.YES, Messagebox.Button.NO },
				Messagebox.QUESTION, clickListener);
	}
	
	private void AddHouse()
	{
		//添加库存
		EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
		for(EmWelfareModel m:list)
		{
			EmActyWarehouseModel wamodel = new EmActyWarehouseModel();
			wamodel.setWase_name(m.getProd_name());
			if(m.getPrty_name()!=null&&!m.getPrty_name().equals(""))
			{
				wamodel.setWase_name(wamodel.getWase_name()+"("+m.getPrty_name()+")");
			}
			wamodel.setWase_addname(username);
			wamodel.setWase_totalnum(m.getEmwf_producenum());
			wamodel.setWase_prod_id(m.getEmwf_prod_id());
			wamodel.setWase_prty_id(m.getEmwf_prty_id());
			wamodel.setWase_unit(m.getProd_unit());
			Integer hsry_wase_id = obll.EmActyWarehouse(wamodel);
		}
	}

	// 弹出批量签收页面
	@Command
	@NotifyChange({ "list", "num" })
	public void signall(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		remark = "签收礼品";
		List<EmWelfareModel> lists = new ListModelList<>();
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 13) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren().get(0)
						.getChildren().get(0);
				if (ck.isChecked()) {
					EmWelfareModel ml = ck.getValue();
					lists.add(ml);
				}
			}
		}
		if (lists.size() > 0) {
			Map map = new HashMap<>();
			map.put("list", lists);
			map.put("id", id);
			map.put("tarpid", tapr_id);
			map.put("paytype", ml.getGift_paytype());
			Window window = (Window) Executions.createComponents(
					"../EmBenefit/EmActy_GiftClientSignAll.zul", null, map);
			window.doModal();
			list = bll.getLists(sql);
			num = list.size();
			if (num <= 0) {
				if (ml.getGift_paytype().equals("货到付款")) {
					// tonext(",gift_state=6");
				} else {
					// tonexts(",gift_state=6");
				}
				win.detach();
			}

		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public List<EmWelfareModel> getList() {
		return list;
	}

	public void setList(List<EmWelfareModel> list) {
		this.list = list;
	}

	public String getGiftname() {
		return giftname;
	}

	public void setGiftname(String giftname) {
		this.giftname = giftname;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public EmWelfareModel getEwfm() {
		return ewfm;
	}

	public void setEwfm(EmWelfareModel ewfm) {
		this.ewfm = ewfm;
	}

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public EmActySuppilerGiftInfoModel getMl() {
		return ml;
	}

	public void setMl(EmActySuppilerGiftInfoModel ml) {
		this.ml = ml;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}

}
