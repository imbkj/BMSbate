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

import Model.EmShebaoChangeMAModel;
import Model.LoginModel;
import Util.UserInfo;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;

public class Emsc_DeclareMA_ListController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();

	private List<EmShebaoChangeMAModel> sbDataList;

	private String sql = Executions.getCurrent().getArg().get("sql").toString();

	private int chkDec = Integer.parseInt(Executions.getCurrent().getArg()
			.get("chkDec").toString());
	private int count = 0; // 多少条数据

	private boolean all_chkTF = false;// 全选框是否选中

	// 获取用户名
	String username = UserInfo.getUsername();
	String userid = UserInfo.getUserid();

	public Emsc_DeclareMA_ListController() {
		// 获取页面数据
		sbDataList = dsbll.getEscmMACData("", sql, "");

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
				"Emsc_DeclareMA_Search.zul", null, null);
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

				String escm_ifdeclare = String
						.valueOf(((EmShebaoChangeMAModel) row.getValue())
								.getEscm_ifdeclare());

				if (!"3".equals(escm_ifdeclare)) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 12).getChildren()
							.get(0);
					ckb.setChecked(check);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 小信封
	@Command("msg")
	@NotifyChange("sbDataList")
	public void msg(@BindingParam("model") EmShebaoChangeMAModel model) {
		LoginDal d = new LoginDal();

		Map map = new HashMap<>();
		map.put("type", "社会保险生育津贴");// 业务类型:来自WfClass的wfcl_name
		map.put("id", model.getId());// 业务表id
		map.put("tablename", "EmShebaoChangeMA");// 业务表名
		map.put("title", model.getEscm_company() + " 的 " + model.getEscm_name()
				+ " (" + model.getGid() + ")的社保生育津贴申请数据小信封");
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(model.getEscm_addname());
		m.setLog_id(d.getUserIDByname(model.getEscm_addname()));
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list

		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEscmMACData("", sql, "");
	}

	// 申报社保
	@Command("declareSingle")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void declareSingle(@BindingParam("dataGrid") Grid dataGrid) {
		try {
			String[] message;
			int j = 0;
			int k = 0;
			String msg;

			// 选中项目
			for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {

				Checkbox ckb = (Checkbox) dataGrid.getCell(i, 12).getChildren()
						.get(0); // 获取checkbox

				if (ckb.isChecked()) {
					// 判断是否选中
					Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行
					int id = ((EmShebaoChangeMAModel) row.getValue()).getId();
					String escm_ifdeclare = String
							.valueOf(((EmShebaoChangeMAModel) row.getValue())
									.getEscm_ifdeclare());
					String flag = ((EmShebaoChangeMAModel) row.getValue())
							.getEscm_flag();

					// 选中几条数据
					k = k + 1;

					// 申报社保
					Integer d_ifdeclare = null;
					if (escm_ifdeclare.equals("0")) {
						d_ifdeclare = 2;
					} else if (escm_ifdeclare.equals("2")) {
						d_ifdeclare = 1;
					} else {
						d_ifdeclare = Integer.parseInt(escm_ifdeclare);
					}
					EmShebaoChangeMAModel maModel = null;
					maModel = new EmShebaoChangeMAModel();

					maModel.setId(id);
					maModel.setEscm_ifdeclare(d_ifdeclare);
					maModel.setEscm_flag(flag);
					maModel.setEscm_addname(UserInfo.getUsername());

					Emsc_DeclareOperateBll opbll = new Emsc_DeclareOperateBll();
					message = opbll.ChangeMADeclareUpState(maModel);

					// 判断是否成功
					if (message[0].equals("1")) {
						j = j + 1;
					}
				}

			}

			if (k == 0) {
				// 弹出提示
				Messagebox.show("请选择数据！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				if (j == k) {
					msg = "操作成功！";
				} else {
					msg = "部分数据操作不成功，请检查！";
				}
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							sbDataList = dsbll.getEscmMACData("", sql, "");
							all_chkTF = false;
						}
					}
				};
				// 弹出提示
				Messagebox.show(msg, "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 打开上传决定书页面
	@Command("upload")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void upload(@BindingParam("dataGrid") Grid dataGrid) {
		try {
			String[] message;
			int k = 0;
			List<EmShebaoChangeMAModel> list;
			list = new ArrayList<EmShebaoChangeMAModel>();
			String msg;

			// 选中项目
			for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {

				Checkbox ckb = (Checkbox) dataGrid.getCell(i, 12).getChildren()
						.get(0); // 获取checkbox

				if (ckb.isChecked()) {
					// 判断是否选中
					Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行
					int id = ((EmShebaoChangeMAModel) row.getValue()).getId();
					int gid = ((EmShebaoChangeMAModel) row.getValue()).getGid();
					int cid = ((EmShebaoChangeMAModel) row.getValue()).getCid();
					Integer escm_ifdeclare = ((EmShebaoChangeMAModel) row
							.getValue()).getEscm_ifdeclare();
					String escm_client= ((EmShebaoChangeMAModel) row
							.getValue()).getEscm_client();
					String escm_company= ((EmShebaoChangeMAModel) row
							.getValue()).getEscm_company();
					String escm_name= ((EmShebaoChangeMAModel) row
							.getValue()).getEscm_name();
					// 选中几条数据
					k = k + 1;

					EmShebaoChangeMAModel maModel = null;
					maModel = new EmShebaoChangeMAModel();

					maModel.setId(id);
					maModel.setGid(gid);
					maModel.setCid(cid);
					maModel.setEscm_ifdeclare(escm_ifdeclare);
					maModel.setEscm_client(escm_client);
					maModel.setEscm_company(escm_company);
					maModel.setEscm_name(escm_name);
					list.add(maModel);
				}

			}
			if (k > 0) {
				// 专递csiiM
				Map map = new HashMap();
				map.put("list", list);
				Window window = (Window) Executions.createComponents(
						"Emsc_DetailMAUploadDef.zul", null, map);
				window.doModal();
				// 刷新
				sbDataList = dsbll.getEscmMACData("", sql, "");
			} else {
				// 弹出提示
				Messagebox.show("请选择数据！", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 编辑
	@Command("edit")
	@NotifyChange("sbDataList")
	public void edit(@BindingParam("id") int id) {
		// 专递csiiM
		Map map = new HashMap();
		map.put("daid", id);
		Window window = (Window) Executions.createComponents(
				"Emsc_DetailMA.zul", null, map);
		window.doModal();
		// 刷新
		sbDataList = dsbll.getEscmMACData("", sql, "");
	}

	public List<EmShebaoChangeMAModel> getSbDataList() {
		return sbDataList;
	}

	public void setSbDataList(List<EmShebaoChangeMAModel> sbDataList) {
		this.sbDataList = sbDataList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getChkDec() {
		return chkDec;
	}

	public void setChkDec(int chkDec) {
		this.chkDec = chkDec;
	}

	public boolean isAll_chkTF() {
		return all_chkTF;
	}

	public void setAll_chkTF(boolean all_chkTF) {
		this.all_chkTF = all_chkTF;
	}

}
