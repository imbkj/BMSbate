package Controller.EmSheBao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmSheBaoChangeModel;
import Model.EmShebaoChangeMAModel;
import Model.EmbaseModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_OperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_DeleteChange_ListController {
	private final int gid = Integer.parseInt(Executions.getCurrent().getArg()
			.get("gid").toString());
	private Emsi_SelBll bll;
	private EmbaseModel emModel;
	private List<EmSheBaoChangeModel> ecList;

	public Emsi_DeleteChange_ListController() {
		bll = new Emsi_SelBll();
		emModel = bll.getEmBase(gid);
		ecList = bll.getChangListByGid(gid);
	}

	// 删除
	@Command("delChange")
	@NotifyChange("ecList")
	public void delBj(@BindingParam("id") int id,
			@BindingParam("type") int type, @BindingParam("taprid") int tapr_id) {
		try {
			int declare = bll.checkChangeDeclare(id, type);
			if (declare == 3 || declare == 4) {
				if (Messagebox.show("确认删除该社保变更信息吗？", "操作提示", Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					Emsi_OperateBll opbll = new Emsi_OperateBll();
					String[] message = opbll.DelChange(id, type, tapr_id,
							UserInfo.getUsername());
					if ("1".equals(message[0])) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						ecList = bll.getChangListByGid(gid);
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("社保变更状态有变，无法删除，请刷新页面查看最新状态。", "操作提示",
						Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("删除社保变更出错", "操作提示", Messagebox.OK, Messagebox.NONE);
		}
	}

	// 确认
	@Command("confirmChange")
	@NotifyChange("ecList")
	public void confirmChange(@BindingParam("m") EmSheBaoChangeModel m) {
		if (Messagebox.show("是否确认该社保变更信息？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
			try {
				Emsi_OperateBll opbll = new Emsi_OperateBll();
				String message[] = opbll
						.ChangDeclare(m, UserInfo.getUsername());
				if ("1".equals(message[0])) {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.NONE);
					ecList = bll.getChangListByGid(gid);
				} else {
					// 弹出提示
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				Messagebox.show("确认社保变更出错。", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	// 撤回
	@Command("revokeChange")
	@NotifyChange("ecList")
	public void revokeChange(@BindingParam("id") int id,
			@BindingParam("type") int type,
			@BindingParam("emsc_change") String emsc_change,
			@BindingParam("taprid") int tapr_id,
			@BindingParam("addname") String addname) {
		try {
			String user = UserInfo.getUsername();
			/*
			 * if (!user.equals(addname)) { Messagebox.show("抱歉，您无权限操作该变更。",
			 * "操作提示", Messagebox.OK, Messagebox.NONE); return; }
			 */
			if (bll.checkChangeDeclare(id, type) == 0) {
				if (Messagebox.show("是否撤回该社保变更信息？", "操作提示", Messagebox.YES
						| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
					Emsi_OperateBll opbll = new Emsi_OperateBll();
					String[] message = opbll.revokeChange(id, type, tapr_id,
							user, emsc_change, gid);
					if ("1".equals(message[0])) {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.NONE);
						ecList = bll.getChangListByGid(gid);
					} else {
						// 弹出提示
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				}
			} else {
				Messagebox.show("社保变更状态有变，无法撤回，请刷新页面查看最新状态。", "操作提示",
						Messagebox.OK, Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 编辑
	@Command("edit")
	@NotifyChange("ecList")
	public void edit(@BindingParam("id") int id,
			@BindingParam("type") int type,
			@BindingParam("emsc_change") String emsc_change) {
		Map<String, String> map = new HashMap<String, String>();
		String url = "";
		if (type == 1) {
			map.put("daid", String.valueOf(id));
			if ("新增".equals(emsc_change) || "调入".equals(emsc_change)) {
				url = "/EmSheBao/Emsi_Edit_Newin.zul";
			} else if ("修改工资".equals(emsc_change)) {
				url = "/EmSheBao/Emsi_Edit_Salary_Update.zul";
			} else if ("停交".equals(emsc_change)) {
				url = "/EmSheBao/Emsi_Edit_Stop.zul";
			}
		} else if (type == 2) {// 特殊交单
			// 判断是否为户籍变更
			// if ("变更户籍".equals(emsc_change)) {
			map.put("daid", String.valueOf(id));
			url = "/EmSheBao/Emsi_Change_SZSIResend.zul";
			// }
		} else if (type == 3) {
			map.put("daid", String.valueOf(id));
			url = "/EmSheBao/Emsi_ForeignerReSend.zul";
		} else if (type == 4) {
			map.put("daid", String.valueOf(id));
			url = "/EmSheBao/Escm_ChangeReSend.zul";
		}
		if (!"".equals(url)) {
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			ecList = bll.getChangListByGid(gid);
		} else {
			Messagebox.show("该变更，无法编辑，如有需要请删除后重新添加。", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 下载批量表
	@Command("download")
	public void download(@BindingParam("id") Integer id,
			@BindingParam("type") Integer type) {
		String allPath = "EmSheBao/File/Upload/Declare/";
		EmShebaoChangeMAModel m=new EmShebaoChangeMAModel();
		Emsc_DeclareSelBll selbll = new Emsc_DeclareSelBll();
		m=selbll.getMAChangeById(id);
		if (type == 1) {
			allPath = allPath + m.getEscm_af_filename();
		} else if (type == 2) {
			allPath = allPath + m.getEscm_bf_filename();
		} else if (type == 3) {
			allPath = allPath + m.getEscm_def_filename();
		}

		try {
			FileOperate.download(allPath);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmbaseModel getEmModel() {
		return emModel;
	}

	public List<EmSheBaoChangeModel> getEcList() {
		return ecList;
	}

}
