package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.LoginDal;

import Model.EmShebaoChangeSZSIModel;
import Model.LoginModel;
import Util.UserInfo;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSheBao.EmSheBao_DOperateBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.SocialInsurance.Algorithm_InfoBll;
import bll.SystemControl.PubRegUserBll;

public class EmSheBao_DSZSIListController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private EmSheBao_DOperateBll dobll = new EmSheBao_DOperateBll();
	private List<EmShebaoChangeSZSIModel> sbDataList;
	private PubRegUserBll prBll = new PubRegUserBll();
	private CoBase_SelectBll csbll = new CoBase_SelectBll();

	private String sql = Executions.getCurrent().getArg().get("sql").toString();
	private String top = Executions.getCurrent().getArg().get("top").toString();
	private String order = Executions.getCurrent().getArg().get("order")
			.toString();

	private int count = 0; // 多少条数据
	private boolean all_chkTF = false;// 全选框是否选中

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public EmSheBao_DSZSIListController() {
		// 获取页面数据
		sbDataList = dsbll.getEmSCSZSIData(top, sql, order);

		try {
			count = sbDataList.size();
		} catch (Exception e) {
			count = 0;
		}
	}

	// 返回
	@Command("pageback")
	public void pageback(@BindingParam("win") Window win) {
		win.detach();
		Window window = (Window) Executions.createComponents(
				"EmSheBao_DSZSISearch.zul", null, null);
		window.doModal();
	}

	// 表格每行checkbox全选
	@Command("gdallselect")
	public void gdallselect(@BindingParam("grid") Grid gd,
			@BindingParam("check") boolean check) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				// 判断是否可以选中
				Row row = (Row) gd.getRows().getChildren().get(i); // 获取Gird的行

				int escs_ifdeclare = ((EmShebaoChangeSZSIModel) row.getValue())
						.getEscs_ifdeclare();

				if (escs_ifdeclare == 0 || escs_ifdeclare == 2) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 13).getChildren()
							.get(0);
					ckb.setChecked(check);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 上传单子
	@Command("upload")
	@NotifyChange("sbDataList")
	public void upload(@BindingParam("id") int id) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("daid", id);
		Window window = (Window) Executions.createComponents(
				"Emsc_SZSIFileUpload.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEmSCSZSIData(top, sql, order);
	}

	// 编辑
	@Command("edit")
	@NotifyChange("sbDataList")
	public void edit(@BindingParam("id") int id) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("daid", id);
		Window window = (Window) Executions.createComponents(
				"Emsc_DetailSZSI.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEmSCSZSIData(top, sql, order);
	}

	// 小信封
	@Command("msg")
	@NotifyChange("sbDataList")
	public void msg(@BindingParam("model") EmShebaoChangeSZSIModel model) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "社会保险");// 业务类型:来自WfClass的wfcl_name
		map.put("id", model.getEscs_id());// 业务表id
		map.put("tablename", "EmShebaoChangeSZSI");// 业务表名
		map.put("title", model.getEscs_company() + " 的 " + model.getEscs_name()
				+ " (" + model.getGid() + ") 的社保交单变更数据小信封");
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(model.getEscs_addname());
		m.setLog_id(d.getUserIDByname(model.getEscs_addname()));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEmSCSZSIData(top, sql, order);
	}

	// 申报社保
	@Command("declareForSingle")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void declareForSingle(@BindingParam("dataGrid") Grid dataGrid) {
		String[] message;
		int j = 0;
		int k = 0;
		String msg;

		// 选中项目
		for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) dataGrid.getCell(i, 13).getChildren()
						.get(0); // 获取checkbox

				if (ckb.isChecked()) {
					// 判断是否选中
					Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行
					int id = ((EmShebaoChangeSZSIModel) row.getValue())
							.getEscs_id();
					int emsc_tapr_id = ((EmShebaoChangeSZSIModel) row
							.getValue()).getEmsc_tapr_id();
					int emsc_ifdeclare = ((EmShebaoChangeSZSIModel) row
							.getValue()).getEscs_ifdeclare();
					int cid = ((EmShebaoChangeSZSIModel) row.getValue())
							.getCid();
					int gid = ((EmShebaoChangeSZSIModel) row.getValue())
							.getGid();
					String escs_change = ((EmShebaoChangeSZSIModel) row
							.getValue()).getEscs_change();
					Integer ifnet= ((EmShebaoChangeSZSIModel) row.getValue()).getEscs_ifnet();
					String flag=((EmShebaoChangeSZSIModel) row.getValue()).getEscs_flag();
					
					
					// 选中几条数据
					k = k + 1;

					// 申报社保
					if (emsc_ifdeclare == 8 && ifnet!=1 && !"变更户籍".equals(escs_change)) {
						message = dobll.declareSZSISingle(id, emsc_tapr_id,
								username, cid);
					} else if (emsc_ifdeclare == 6 || (emsc_ifdeclare == 8 && ifnet==1 && "变更户籍".equals(escs_change)) ) {
						message = dobll.declareSZSISingleSuccess(id,
								emsc_tapr_id, username);

						// 更新在册表数据
						if (message[0].equals("1")
								&& "变更户籍".equals(escs_change)) {
							Algorithm_InfoBll aiBll = new Algorithm_InfoBll();
							aiBll.upLocalShebaoUpdate(0, gid);
						}
					}/*else if (emsc_ifdeclare == 8 && ifnet==1 && "变更户籍".equals(escs_change)) {//网报数据直接更新成已申报
						message=dobll.declareSZSIChangeState(
								id,
								emsc_tapr_id,
								1, flag, username, cid,
								"");
						// 更新在册表数据
						if (message[0].equals("1")
								&& "变更户籍".equals(escs_change)) {
							Algorithm_InfoBll aiBll = new Algorithm_InfoBll();
							aiBll.upLocalShebaoUpdate(0, gid);
						}
					} */
					else {
						message = new String[2];
						message[0] = "0";
					}

					// 判断是否成功
					if (message[0].equals("1")) {
						j = j + 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (k == 0) {
			// 弹出提示
			Messagebox.show("请选择数据！", "操作提示", Messagebox.OK, Messagebox.ERROR);
		} else {
			if (j == k) {
				msg = "操作成功！";
			} else {
				msg = "部分数据操作不成功，请检查！";
			}
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						sbDataList = dsbll.getEmSCSZSIData(top, sql, order);
						all_chkTF = false;
					}
				}
			};
			// 弹出提示
			Messagebox.show(msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		}
	}

	public List<EmShebaoChangeSZSIModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmShebaoChangeSZSIModel> sbDataList) {
		this.sbDataList = sbDataList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isAll_chkTF() {
		return all_chkTF;
	}

	public void setAll_chkTF(boolean all_chkTF) {
		this.all_chkTF = all_chkTF;
	}

}
