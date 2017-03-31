package Controller.EmHouse;

import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_SaleryBll;

import Model.EmHouseCpp;
import Model.EmHouseUpdateModel;
import Util.DateStringChange;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmHouse_SaleryController {
	private List<EmHouseCpp> cplist = new ListModelList<>();
	private EmHouseUpdateModel eum = new EmHouseUpdateModel();
	private EmHouse_SaleryBll bll = new EmHouse_SaleryBll();
	private EmHouseSetBll sbll = new EmHouseSetBll();
	private Window win;
	private Double houseRadix = 0.0;
	private Double cpp = 0.0;

	private Integer embaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("embaId").toString());

	public EmHouse_SaleryController() {
		eum = bll.getinfo(embaId);
		if (eum != null && eum.getEmhu_id() != null) {
			eum.setEmhu_cpp2(eum.getEmhu_cpp().toString());
			eum.setEmhu_opp2(eum.getEmhu_opp().toString());
			houseRadix = eum.getEmhu_radix();
			cpp = eum.getEmhu_cpp();
			if (eum.getEmhu_companyid() == null
					|| eum.getEmhu_companyid().equals("")
					|| eum.getEmhu_houseid() == null
					|| eum.getEmhu_houseid().equals("")) {
				// eum.setConfirm(true);
			}
			cplist = sbll.cplist(eum.getGid());
			
			
		}
	}

	@Command
	public void winC(@BindingParam("a") Window w) {
		win = w;
		if (!bll.Salery()) {
			RedirectUtil util = new RedirectUtil();
			Messagebox.show("年调已关闭.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			// util.refreshEntryThirdUrl("/EmHouse/Emhouse_Index.zul");
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		} else {
			if (eum == null || eum.getEmhu_id() == null) {
				RedirectUtil util = new RedirectUtil();
				Messagebox.show("未找到在册数据", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
			}
		}

		if (sbll.gjjOnair(eum.getCid(), eum.getGid(), eum.getOwnmonth(),
				eum.getEmhu_single())) {
			RedirectUtil util = new RedirectUtil();
			Messagebox.show("当月已截单,请在台账更新后再操作", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		}

		if (Integer.valueOf(DateStringChange.getOwnmonth()) > sbll.nowmonth()) {

			RedirectUtil util = new RedirectUtil();
			Messagebox.show("当月公积金数据未更新.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
		}

	}

	@Command
	public void link() {
		RedirectUtil u = new RedirectUtil();
		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + eum.getGid() + "",
				"雇员服务中心");
	}

	@Command
	@NotifyChange("eum")
	public void updateCompanyId(@BindingParam("a") Combobox cb) {
		if (cb!=null && cb.getSelectedItem()!=null) {
			EmHouseCpp m = cb.getSelectedItem().getValue();
			eum.setEmhu_companyid(m.getCompanyid());
		}
		
	}

	@Command
	public void submit() {
		if (eum.getEmhu_houseid()==null || eum.getEmhu_houseid().equals("")) {
			Messagebox.show("员工公积金编号为空.", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}
		
		
		if (houseRadix != null && !houseRadix.equals("")) {
			if (houseRadix.equals(eum.getEmhu_radix())) {
				Messagebox.show("请输入新基数.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			} else {

				Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
						Messagebox.Button.OK, Messagebox.Button.CANCEL },
						Messagebox.QUESTION,
						new EventListener<Messagebox.ClickEvent>() {
							@Override
							public void onEvent(ClickEvent event)
									throws Exception {
								// TODO Auto-generated method stub

								if (Messagebox.Button.OK.equals(event
										.getButton())) {
									eum.setEmhu_cpp(cpp);
									eum.setEmhu_radix(houseRadix);
									Integer i = bll.update(eum);
									if (i > 0) {
										Messagebox.show("操作成功.", "操作提示",
												Messagebox.OK,
												Messagebox.INFORMATION);
										RedirectUtil util = new RedirectUtil();
										util.refreshEmUrl("/EmHouse/Emhouse_Index.zul");
									} else {
										Messagebox
												.show("操作失败.", "操作提示",
														Messagebox.OK,
														Messagebox.ERROR);
										return;
									}
								}
							}
						});
			}
		} else {
			Messagebox.show("请输入新基数.", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		}
	}

	public EmHouseUpdateModel getEum() {
		return eum;
	}

	public void setEum(EmHouseUpdateModel eum) {
		this.eum = eum;
	}

	public List<EmHouseCpp> getCplist() {
		return cplist;
	}

	public void setCplist(List<EmHouseCpp> cplist) {
		this.cplist = cplist;
	}

	public Double getHouseRadix() {
		return houseRadix;
	}

	public void setHouseRadix(Double houseRadix) {
		this.houseRadix = houseRadix;
	}

	public Double getCpp() {
		return cpp;
	}

	public void setCpp(Double cpp) {
		this.cpp = cpp;
	}

}
