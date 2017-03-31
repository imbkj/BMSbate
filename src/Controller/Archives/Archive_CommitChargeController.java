package Controller.Archives;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.Archives.Archive_CommitChargeBll;
import Model.EmArchiveDatumModel;
import Util.UserInfo;

public class Archive_CommitChargeController {
	private EmArchiveDatumModel eam = new EmArchiveDatumModel();
	private Archive_CommitChargeBll bll = new Archive_CommitChargeBll();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private Integer tapr_id = null;
	private String username = UserInfo.getUsername();

	public Archive_CommitChargeController() {
		if(Executions.getCurrent().getArg()
				.get("id")!=null)
		{
			tapr_id = Integer.valueOf(Executions.getCurrent().getArg()
					.get("id").toString());
		}
		eam = bll.getInfo(eadaId).get(0);
	}

	// 查看报价单
	@Command
	public void check() {
		System.out.println(eam.getEmba_id());
		Map map = new HashMap<>();
		map.put("embaId", eam.getEmba_id());

		Window window = (Window) Executions.createComponents(
				"../Embase/EmQuotation.zul", null, map);
		window.doModal();
	}

	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {
		final Combobox cb = (Combobox) win.getFellow("cmt");
		if (cb.getSelectedItem() != null) {
			Messagebox.show("确认提交数据?", "操作提示", new Messagebox.Button[] {
					Messagebox.Button.OK, Messagebox.Button.CANCEL },
					Messagebox.QUESTION,
					new EventListener<Messagebox.ClickEvent>() {
						@Override
						public void onEvent(ClickEvent event) throws Exception {
							// TODO Auto-generated method stub
							if (Messagebox.Button.OK.equals(event.getButton())) {
								Integer i = 0;
								SimpleDateFormat sdf = new SimpleDateFormat(
										"yyyy-MM-dd");
								eam.setEada_charge(null);
								Datebox d1 = (Datebox) win.getFellow("date1");
								Datebox d2 = (Datebox) win.getFellow("date2");
								Doublebox db1 = (Doublebox) win
										.getFellow("dbox1");
								Doublebox db2 = (Doublebox) win
										.getFellow("dbox2");
								Doublebox db3 = (Doublebox) win
										.getFellow("dbox3");

								if (cb.getValue() != null
										&& cb.getValue().equals("是")) {

									eam.setEada_charge("费用从");
									if (d1.getValue() != null
											&& !d1.getValue().equals("")) {
										eam.setEada_charge(eam.getEada_charge()
												+ sdf.format(d1.getValue()));
									}

									eam.setEada_charge(eam.getEada_charge()
											+ "欠费至");
									if (d2.getValue() != null
											&& !d2.getValue().equals("")) {
										eam.setEada_charge(eam.getEada_charge()
												+ sdf.format(d2.getValue()));
									}

									eam.setEada_charge(eam.getEada_charge()
											+ ",其中");
									if (db1.getValue() != null
											&& !db1.getValue().equals("")) {
										eam.setEada_charge(eam.getEada_charge()
												+ "档案费" + db1.getValue() + "元.");
									}

									if (db2.getValue() != null
											&& !db2.getValue().equals("")) {
										eam.setEada_charge(eam.getEada_charge()
												+ "户口费" + db2.getValue() + "元.");
									}

									if (db3.getValue() != null
											&& !db3.getValue().equals("")) {
										eam.setEada_charge(eam.getEada_charge()
												+ "滞纳金" + db3.getValue() + "元");
									}

								}

								i = bll.updateCharge(eadaId, cb
										.getSelectedItem().getLabel(), eam
										.getEada_charge(),
										eam.getEada_remark(), tapr_id, username);
								if (i > 0) {

									Messagebox.show("操作成功!", "操作提示",
											Messagebox.OK,
											Messagebox.INFORMATION);
								} else {
									Messagebox.show("操作失败!", "操作提示",
											Messagebox.OK, Messagebox.ERROR);

								}

								win.detach();

							}
						}
					});

		} else {
			Messagebox
					.show("请选择欠费情况!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出备注
	@Command
	public void addremark(@BindingParam("win") final Window win) {
		Map map = new HashMap<>();
		map.put("id", eadaId.toString());
		map.put("typeid", "2");
		map.put("gid",eam.getGid());
		Window window = (Window) Executions.createComponents(
				"../Archives/Remark_AddList.zul", null, map);
		window.doModal();
	}

	public EmArchiveDatumModel getEam() {
		return eam;
	}

	public void setEam(EmArchiveDatumModel eam) {
		this.eam = eam;
	}

}
