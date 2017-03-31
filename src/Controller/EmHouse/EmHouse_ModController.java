package Controller.EmHouse;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.Embase.EmbaseLogin_AddBll;

import Model.EmHouseChangeModel;
import Model.PubCodeConversionModel;
import Util.DateStringChange;
import Util.RedirectUtil;

public class EmHouse_ModController {
	private List<EmHouseChangeModel> list = new ListModelList<>();
	private EmHouseChangeModel ecm = new EmHouseChangeModel();
	private List<String> ownmonthList = new ArrayList<>();
	private List<PubCodeConversionModel> listPC = new ListModelList<>();
	private EmHouse_EditBll bll = new EmHouse_EditBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private EmbaseLogin_AddBll abll = new EmbaseLogin_AddBll();
	
	private Integer id = Integer.valueOf(Executions.getCurrent().getArg()
			.get("id").toString());
	private Window win = (Window) Path.getComponent("/winMod");

	private Integer gjjfwownmonth; // 公积金服务起始月
	private Integer gjjtzownmont; // 公积金台账月
	private boolean house_ifStop;
	
	public EmHouse_ModController() {
		ecm.setEmhc_id(id);
		list = bll.getChangeList(ecm);
		Integer nowmonth = sbll.nowmonth();
		if (list.size() > 0) {
			ecm = list.get(0);
			/*
			if (!ecm.getOwnmonth().equals(nowmonth)) {
				ecm.setOwnmonth(nowmonth);
			}*/
			
			//GjjOwnmonth();
			
			

		}
		setOwnmonthList();
		setListPC();
	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + ecm.getGid() + "",
				"雇员服务中心");
	}

	// 计算公积金所属月份
	public void GjjOwnmonth() {

		gjjtzownmont = abll.houseOwnmonth();
		gjjfwownmonth = abll.getownmonth(ecm.getGid());

		house_ifStop = sbll.gjjOnair(ecm.getCid(), ecm.getGid(), gjjtzownmont,
				null); // 公积金接单日

		if (house_ifStop) {
			// 截单社保所属月份+1
			ecm.setOwnmonth(Integer.valueOf(DateStringChange
					.ownmonthAddoneMonth(gjjtzownmont.toString())));
		} else {
			if (gjjfwownmonth >= gjjtzownmont) {
				ecm.setOwnmonth(gjjfwownmonth);
			} else {
				ecm.setOwnmonth(gjjtzownmont);
			}

		}

	}

	@Command
	public void submit() {
		if (ecm.getEmhc_change().equals("新增")) {
			if (ecm.getEmhc_mobile() != null
					&& !ecm.getEmhc_mobile().equals("")) {
				if (ecm.getEmhc_mobile().length() != 11) {
					Messagebox.show("手机号码为11位!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			} else {
				Messagebox.show("手机号码为11位!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}else {
			if (ecm.getEmhc_mobile() != null
					&& !ecm.getEmhc_mobile().equals("")) {
				if (ecm.getEmhc_mobile().length() != 11) {
					Messagebox.show("手机号码为11位!", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
					return;
				}
			}
		}
		
		Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
				Messagebox.Button.OK, Messagebox.Button.CANCEL },
				Messagebox.QUESTION,
				new EventListener<Messagebox.ClickEvent>() {
					@Override
					public void onEvent(ClickEvent event) throws Exception {
						// TODO Auto-generated method stub

						if (Messagebox.Button.OK.equals(event.getButton())) {

							Integer i = bll.modInfo(ecm);
							//bll.modUpdate(ecm);
							bll.updateData(ecm.getGid());
							if (i > 0) {
								Messagebox.show("操作成功!", "操作提示", Messagebox.OK,
										Messagebox.INFORMATION);
							} else {
								Messagebox.show("操作失败!", "操作提示", Messagebox.OK,
										Messagebox.ERROR);

							}
							win.detach();

						}
					}
				});
	}

	public EmHouseChangeModel getEcm() {
		return ecm;
	}

	public void setEcm(EmHouseChangeModel ecm) {
		this.ecm = ecm;
	}

	public List<String> getOwnmonthList() {
		return ownmonthList;
	}

	public void setOwnmonthList() {
		this.ownmonthList = bll.getOwnmonthList();
	}

	public List<PubCodeConversionModel> getListPC() {
		return listPC;
	}

	public void setListPC() {
		this.listPC = bll.getpcInfo();
	}
}
