package Controller.EmBenefit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

public class EmActy_EmbaseListController {

	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private List<EmWelfareModel> list = new ListModelList<>();
	private EmWelfareModel ewfm = new EmWelfareModel();
	private EmBenefit_comitListBll bll = new EmBenefit_comitListBll();

	private EmActy_GiftInfoSelectBll blls = new EmActy_GiftInfoSelectBll();
	private List<EmActySuppilerGiftInfoModel> lists = blls
			.getEmActyGiftInfo(" and gift_id=" + id);
	private Window win = (Window) Path.getComponent("/winEmp");
	private String sortid = "", giftname = "";
	private Integer num = 0, noenum;
	String sql = "", sqlc = "", username = UserInfo.getUsername();
	private EmActySuppilerGiftInfoModel ml = new EmActySuppilerGiftInfoModel();
	private List<EmWelfareModel> warelist = new ArrayList<EmWelfareModel>();
	private List<String> clientlist = new ArrayList<String>();
	private EmWelfareModel mm = new EmWelfareModel();
	private Date xy;

	public EmActy_EmbaseListController() {
		if (lists.size() > 0) {
			giftname = lists.get(0).getGift_name();
			noenum = lists.get(0).getGift_nownum();
			ewfm.setEmwf_state(4);
			sortid = lists.get(0).getGift_sortid();
			ewfm.setEmwf_sortid(sortid);
			sql = "and emwf_sortid='" + ewfm.getEmwf_sortid()
					+ "'  and emwf_state=6";
			list = bll.getLists(sql);
			clientlist = bll.clientlist(sql);
			num = list.size();
			ml = lists.get(0);
			warelist = bll
					.getEmWelfareList(" and emwf_state=7 and  emwf_sortid='"
							+ ml.getGift_sortid() + "' ");
		}
	}

	// 全选
	@Command("checkall")
	public void checkall(@BindingParam("a") Checkbox cka) {
		Grid gd = (Grid) win.getFellow("gdList");
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 10) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 10).getChildren().get(0)
						.getChildren().get(0);
				ck.setChecked(cka.isChecked());

			} else {

				return;
			}

		}
	}

	// 单个礼品发放
	@Command
	@NotifyChange({ "list", "num" })
	public void send(@BindingParam("win") Window win,
			@BindingParam("model") final EmWelfareModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("id", id);
		map.put("tarpid", tapr_id);
		Window window = (Window) Executions.createComponents(
				"../EmBenefit/EmActy_sendGift.zul", null, map);
		window.doModal();
		list = bll.getLists(sql);
		num = list.size();
		if (num <= 0) {
			List<EmWelfareModel> lit = new ArrayList<EmWelfareModel>();
			// 查询是否有需签收数据，没有则直接跳过签收步骤
			lit = bll.getEmWelfareList("and emwf_sortid='"
					+ ewfm.getEmwf_sortid() + "'  and emwf_state=7");
			if (lit.size() > 0) {
				tonext(",gift_state=5");
			} else {
				// 跳过签收步骤，转到支付
				topay();
			}
		}
	}

	// 批量发放礼品
	@Command
	@NotifyChange({ "list", "num" })
	public void sendbatch() {
		Integer k = 0, kl = 0, checknum = 0;
		;
		Grid gd = (Grid) win.getFellow("gdList");
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 14) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren().get(0)
						.getChildren().get(0);
				if (ck.isChecked()) {
					checknum = checknum + 1;
				}
			}
		}
		if (noenum >= checknum) {
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, 14) != null) {
					Checkbox ck = (Checkbox) gd.getCell(i, 14).getChildren()
							.get(0).getChildren().get(0);
					if (ck.isChecked()) {
						EmWelfareModel ml = new EmWelfareModel();
						ml = ck.getValue();
						Integer ks = sentgift(id, ml.getEmwf_id());
						if (ks > 0) {
							k = 1;
						} else {
							kl = ks;
						}
					}
				}
			}
			if (k > 0) {
				list = bll.getLists(sql);
				num = list.size();
				if (list.size() == 0) {
					List<EmWelfareModel> lit = new ArrayList<EmWelfareModel>();
					// 查询是否有需签收数据，没有则直接跳过签收步骤
					lit = bll.getEmWelfareList("and emwf_sortid='"
							+ ewfm.getEmwf_sortid() + "'  and emwf_state=7");
					if (lit.size() > 0) {
						tonext(",gift_state=5");
					} else {
						// 跳过签收步骤，转到支付
						topay();
					}
				} else {
					EmActy_GiftInfoOperateBll bl = new EmActy_GiftInfoOperateBll();
					String sqlstr = ",gift_state=3";
					bl.updateGiftInfo(sqlstr, id);
				}
				Messagebox.show("发放成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			} else if (kl == -1) {
				Messagebox.show("礼品库存为0，发放失败", "提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				Messagebox.show("发放失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show("库存不足，不能发放", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 发放礼品
	private Integer sentgift(Integer gift_id, Integer emwf_id) {
		Textbox clienttxt = (Textbox) win.getFellow("client");
		EmActy_GiftInfoOperateBll bl = new EmActy_GiftInfoOperateBll();
		Integer k = bl.updateEmWelfare(gift_id, emwf_id, clienttxt.getValue());
		return k;
	}

	// 流程转到下一步
	private String[] tonext(String strsql) {
		String[] str = new String[5];
		EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		m.setGift_remark("发放礼品");
		EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
		str = obll.updateEmActy_GiftInfos(m, strsql, "1");
		win.detach();
		return str;
	}

	// 跳到支付节点
	private String[] topay() {
		String[] str = new String[5];
		String strsql = ",gift_state=6";
		EmActySuppilerGiftInfoModel m = new EmActySuppilerGiftInfoModel();
		m.setGift_id(id);
		m.setGift_tarpid(tapr_id);
		m.setGift_remark("");
		EmActy_GiftInfoOperateBll obll = new EmActy_GiftInfoOperateBll();
		str = obll.skiptopay(tapr_id, strsql, id);
		win.detach();
		return str;
	}

	// 弹出批量发放页面
	@Command
	@NotifyChange({ "list", "num" })
	public void sendall(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		List<EmWelfareModel> lists = new ListModelList<>();
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			if (gd.getCell(i, 10) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 10).getChildren().get(0)
						.getChildren().get(0);
				if (ck.isChecked()) {
					EmWelfareModel ml = ck.getValue();
					lists.add(ml);
				}
			}
		}
		String ss = "";
		if (ss == "") {
			if (lists.size() > 0) {
				Map map = new HashMap<>();
				map.put("list", lists);
				map.put("id", id);
				map.put("tarpid", tapr_id);
				Window window = (Window) Executions.createComponents(
						"../EmBenefit/EmActy_sendAllGift.zul", null, map);
				window.doModal();
				list = bll.getLists(sql);
				num = list.size();
				if (num <= 0) {
					win.detach();
				}
			} else {
				Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			Messagebox.show(ss, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 检查库存是否足够发放
	private String getIfSend(List<EmWelfareModel> list) {
		String str = "";
		Integer buynum = 0;
		String idstr = "";
		List<EmWelfareModel> countlist = new ArrayList<EmWelfareModel>();
		for (int i = 0; i < list.size(); i++) {
			buynum = buynum + list.get(i).getEmwf_amount();
			if (i == 0) {
				idstr = list.get(0).getEmwf_id() + "";
			} else {
				idstr = idstr + "," + list.get(i).getEmwf_id();
			}
		}
		if (idstr != null && !idstr.equals("")) {
			countlist = bll.getWfCount(" and emwf_id in(" + idstr + ")");
		}
		for (int y = 0; y < countlist.size(); y++) {
			EmWelfareModel wml = countlist.get(y);
			EmActyWarehouseModel wm = bll.getEmActyWarehouseModel(wml
					.getProd_name());
			if (wm.getWase_nownum() < wml.getNum()) {
				str = "库存不足，不能发放";
				break;
			}
		}
		return str;
	}

	// 提交
	@Command
	public void summit() {
		String str = "提交后流程将转到下一步，是否确认提交";
		if (list.size() > 0) {
			str = "还有礼品没有发放,是否确定转下一步";
		}
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.YES.equals(event.getButton())) {
					List<EmWelfareModel> lit = new ArrayList<EmWelfareModel>();
					// 把没有发放的礼品放到库存
					if (list.size() > 0) {
						AddHouse();
					}
					// 查询是否有需签收数据，没有则直接跳过签收步骤
					lit = bll.getEmWelfareList("and emwf_sortid='"
							+ ewfm.getEmwf_sortid() + "'  and emwf_state=7");
					if (lit.size() > 0) {
						tonext(",gift_state=5");
					} else {
						// 跳过签收步骤，转到支付
						topay();
					}
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

	// 发礼品页面查询
	@Command
	@NotifyChange("list")
	public void searchemba() {
		String sl = "";
		if (mm.getEmwf_company() != null && !mm.getEmwf_company().equals("")) {
			sl = sl + " and Emwf_company like '%" + mm.getEmwf_company() + "%'";
		}
		if (mm.getEmwf_name() != null && !mm.getEmwf_name().equals("")) {
			sl = sl + " and Emwf_name = '" + mm.getEmwf_name() + "'";
		}
		if (mm.getEmwf_idcard() != null && !mm.getEmwf_idcard().equals("")) {
			sl = sl + " and Emwf_idcard = '" + mm.getEmwf_idcard() + "'";
		}
		if (mm.getProd_name() != null && !mm.getProd_name().equals("")) {
			sl = sl + " and prod_name= '" + mm.getProd_name() + "'";
		}
		if (mm.getEmwf_dept() != null && !mm.getEmwf_dept().equals("")) {
			sl = sl + " and emwf_dept= '" + mm.getEmwf_dept() + "'";
		}
		if (mm.getEmwf_client() != null && !mm.getEmwf_client().equals("")) {
			sl = sl + " and emwf_client= '" + mm.getEmwf_client() + "'";
		}
		if (mm.getEmwf_delivery() != null && !mm.getEmwf_delivery().equals("")) {
			sl = sl + " and emwf_delivery= '" + mm.getEmwf_delivery() + "'";
		}
		if (xy != null) {
			sl = sl + " and convert(varchar(10),emwf_need,120)= '"
					+ changedate(xy) + "'";
		}
		list = bll.getLists(sql + sl);
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

	public List<EmWelfareModel> getWarelist() {
		return warelist;
	}

	public void setWarelist(List<EmWelfareModel> warelist) {
		this.warelist = warelist;
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

	public EmWelfareModel getMm() {
		return mm;
	}

	public void setMm(EmWelfareModel mm) {
		this.mm = mm;
	}

	public Date getXy() {
		return xy;
	}

	public void setXy(Date xy) {
		this.xy = xy;
	}

}
