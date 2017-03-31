package Controller.EmSheBao;

import impl.MessageImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import service.MessageService;

import dal.LoginDal;

import Model.EmShebaoChangeMAModel;
import Model.LoginModel;
import Model.SysMessageModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.LoginBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.SystemControl.SystLogInfoBll;

public class Emsi_fd_DeclareMA_ListController {
	private EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
	private Emsc_DeclareOperateBll obll = new Emsc_DeclareOperateBll();
	private MessageService msgservice;
	private LoginBll loginBll = new LoginBll();

	private List<EmShebaoChangeMAModel> sbDataList;

	private String sql = Executions.getCurrent().getArg().get("sql").toString();
	private String order=" order by declareTime"; 
	
	private int count = 0; // 多少条数据

	private boolean all_chkTF = false;// 全选框是否选中

	public Emsi_fd_DeclareMA_ListController() {
		// 获取页面数据
		sbDataList = dsbll.getEscmMACData("", sql, order);

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
				"Emsi_fd_DeclareMA_Search.zul", null, null);
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

				if (escm_ifdeclare.equals("11")) {
					Checkbox ckb = (Checkbox) gd.getCell(i, 11).getChildren()
							.get(0);
					ckb.setChecked(check);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 下载批量表
	@Command("download")
	public void download(@BindingParam("m") EmShebaoChangeMAModel m,
			@BindingParam("type") Integer type) {
		String allPath = "EmSheBao/File/Upload/Declare/";
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
		sbDataList = dsbll.getEscmMACData("", sql, order);
	}

	@Command("confirmAll")
	@NotifyChange({ "sbDataList", "all_chkTF" })
	public void confirmAll(@BindingParam("dataGrid") Grid dataGrid) {
		if (Messagebox.show("确定批量操作社保生育津贴收款确认？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

			try {
				int k = 0;
				int j = 0;
				List<EmShebaoChangeMAModel> list;
				list = new ArrayList<EmShebaoChangeMAModel>();

				// 选中项目
				for (int i = 0; i < dataGrid.getRows().getChildren().size(); i++) {

					Checkbox ckb = (Checkbox) dataGrid.getCell(i, 11)
							.getChildren().get(0); // 获取checkbox

					if (ckb.isChecked()) {
						// 判断是否选中
						Row row = (Row) dataGrid.getRows().getChildren().get(i); // 获取Gird的行
						int id = ((EmShebaoChangeMAModel) row.getValue())
								.getId();
						int gid = ((EmShebaoChangeMAModel) row.getValue())
								.getGid();
						int cid = ((EmShebaoChangeMAModel) row.getValue())
								.getCid();
						String escm_client = ((EmShebaoChangeMAModel) row
								.getValue()).getEscm_client();
						String escm_company = ((EmShebaoChangeMAModel) row
								.getValue()).getEscm_company();
						String escm_name = ((EmShebaoChangeMAModel) row
								.getValue()).getEscm_name();
						BigDecimal escm_fee = ((EmShebaoChangeMAModel) row
								.getValue()).getEscm_fee();
						Integer escm_ifdeclare = ((EmShebaoChangeMAModel) row
								.getValue()).getEscm_ifdeclare();

						// 选中几条数据
						k = k + 1;

						if (escm_ifdeclare == 11) {
							EmShebaoChangeMAModel maModel = null;
							maModel = new EmShebaoChangeMAModel();

							maModel.setId(id);
							maModel.setGid(gid);
							maModel.setCid(cid);
							maModel.setEscm_client(escm_client);
							maModel.setEscm_company(escm_company);
							maModel.setEscm_name(escm_name);
							maModel.setEscm_fee(escm_fee);
							maModel.setEscm_ifdeclare(escm_ifdeclare);
							list.add(maModel);

							boolean result;

							// 调用方法
							result = feeConfirm(maModel);
							if (result) {
								j = j + 1;
							}
						}

					}

				}

				if (k > 0) {
					if (k == j) {
						// 弹出提示
						Messagebox.show("批量操作成功！", "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);
					} else {
						// 弹出提示
						Messagebox.show("部分数据操作不成功！", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
					}
				} else {
					// 弹出提示
					Messagebox.show("请选择数据！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			// 刷新
			sbDataList = dsbll.getEscmMACData("", sql, order);
		}
	}

	@Command("confirm")
	@NotifyChange("sbDataList")
	public void confirm(@BindingParam("m") EmShebaoChangeMAModel model) {
		if (Messagebox.show("确定操作社保生育津贴收款确认？", "操作提示", Messagebox.YES
				| Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {

			boolean result;

			// 调用方法
			result = feeConfirm(model);
			if (result) {
				// 系统短信和邮件通知客服
				EmSheBao_DSelectBll dsbll = new EmSheBao_DSelectBll();
				SysMessageModel msgModel = null;
				msgModel = new SysMessageModel();

				msgModel.setSyme_content(model.getEscm_company() + " 的 "
						+ model.getEscm_name() + " 社保生育津贴收款已确认，请知悉。");// 短信内容
				msgModel.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
				msgModel.setSymr_name(model.getEscm_client());// 收件人姓名
				msgModel.setSymr_log_id(loginBll.getUserIDByname(model
						.getEscm_client()));
				msgModel.setEmail(1);// 1表示同时发邮件
				msgModel.setSyme_title(model.getEscm_company() + " 的 "
						+ model.getEscm_name() + " 社保生育津贴收款已确认");
				// 调用方法
				msgservice = new MessageImpl("EmShebaoChangeMA", model.getId());
				msgservice.Add(msgModel);

				// 加log
				SystLogInfoBll logBll = new SystLogInfoBll();
				logBll.addLog(null, model.getId(), model.getCid(),
						model.getGid(), "社保信息", "确认生育津贴收款",
						UserInfo.getUsername());
				Messagebox.show("操作确认成功!", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			} else {
				Messagebox.show("操作确认失败!", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

			// 刷新
			sbDataList = dsbll.getEscmMACData("", sql, order);

		}
	}

	public boolean feeConfirm(EmShebaoChangeMAModel model) {
		boolean result;

		// 判断是否已上传决定数据，福利是否已录入金额
		if (model.getEscm_fee() != null && model.getEscm_ifdeclare() == 11
				&& model.getEscm_fee().compareTo(new BigDecimal(0)) == 1) {
			String[] message = obll.ChangeMAConfirmFee(model);
			if ("1".equals(message[0])) {
				result = true;
			} else {
				result = false;
			}
		} else {
			result = false;
		}
		return result;
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

	public boolean isAll_chkTF() {
		return all_chkTF;
	}

	public void setAll_chkTF(boolean all_chkTF) {
		this.all_chkTF = all_chkTF;
	}

}
