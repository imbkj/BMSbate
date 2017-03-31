package Controller.EmSheBao;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.EmSheBao.Emsi_OperateDal;

import Model.EmShebaoBJModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_CheckOperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsc_BJFileDownloadAllController {
	private Emsc_DeclareSelBll dsbll = new Emsc_DeclareSelBll();
	private Emsc_DeclareOperateBll dobll = new Emsc_DeclareOperateBll();
	private List<EmShebaoBJModel> list = new ArrayList<EmShebaoBJModel>();
	private EmShebaoBJModel bjModel = new EmShebaoBJModel();;
	private String id = Executions.getCurrent().getArg().get("daid").toString();

	public Emsc_BJFileDownloadAllController() {
		bjModel = dsbll.getBjInfoById(Integer.parseInt(id));
		// 获取当月所有养老补交数据
		list = dsbll.getBjAllInfoById(Integer.parseInt(id),
				" and bj.emsb_ifdeclare=2");
	}

	// 下载
	@Command("download")
	public void download() {
		// 获取下载模板路径
		bjModel = new EmShebaoBJModel();
		bjModel = list.get(0);

		String allPath = "EmSheBao/File/Upload/Declare/"
				+ bjModel.getEmsb_uploadfile();

		try {
			FileOperate.download(allPath);
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 转下一步
	@Command("submit")
	public void submit(@BindingParam("win") final Window win) {
		String msg = "";
		for (int i = 0; i < list.size(); i++) {
			// 获取补交数据
			bjModel = new EmShebaoBJModel();
			bjModel = list.get(i);
			String[] message;
			message = dobll.BjDeclareDownload(bjModel);

			// 判断是否成功
			if (message[0].equals("1")) {

				// 更新相应的医疗补交数据状态
				Emsi_OperateDal eDal = new Emsi_OperateDal();
				boolean ifJLBJ = eDal.getShebaoBJJL(bjModel.getGid(),
						bjModel.getOwnmonth(), bjModel.getEmsb_startmonth());
				if (ifJLBJ == true) {
					// 通过养老补交获取医疗补交数据
					EmShebaoBJModel jlM = new EmShebaoBJModel();
					Emsi_SelBll bll = new Emsi_SelBll();
					jlM = bll.getBjJLListByBJid(bjModel.getId());

					String[] message2 = dobll.BjJLDeclareDownload(jlM);
					if (!"1".equals(message2[0])) {
						msg = msg + "，" + jlM.getOwnmonth() + "医疗补交数据操作不成功";
					}
				}
			} else {
				msg = msg + "，" + bjModel.getOwnmonth() + "养老补交数据操作不成功";
			}
		}
		EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
			public void onEvent(ClickEvent event) throws Exception {
				if (Messagebox.Button.OK.equals(event.getButton())) {
					win.detach();
				}
			}
		};
		// 弹出提示
		Messagebox.show("操作完成！" + msg, "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, clickListener);
	}

	// 退回
	@Command("bjReturn")
	public void bjReturn(@BindingParam("win") final Window win,
			@BindingParam("remark") Textbox remark) {
		try {
			String msg = "";
			if (remark.getValue() != null && !"".equals(remark.getValue())) {
				for (int i = 0; i < list.size(); i++) {
					// 获取补交数据
					bjModel = new EmShebaoBJModel();
					bjModel = list.get(i);

					Emsi_CheckOperateBll opBll = new Emsi_CheckOperateBll();
					String[] message = opBll.bjCheckReturn(bjModel.getId(),
							bjModel.getEmsb_tapr_id(), remark.getValue(),
							UserInfo.getUsername());
					if ("1".equals(message[0])) {

						// 如果有对应的医疗数据，则同时退回医疗数据
						Emsi_OperateDal eDal = new Emsi_OperateDal();
						boolean ifJLBJ = eDal.getShebaoBJJL(bjModel.getGid(),
								bjModel.getOwnmonth(),
								bjModel.getEmsb_startmonth());
						if (ifJLBJ == true) {
							// 通过养老补交获取医疗补交数据
							EmShebaoBJModel jlM = new EmShebaoBJModel();
							Emsi_SelBll bll = new Emsi_SelBll();
							jlM = bll.getBjJLListByBJid(bjModel.getId());

							String[] message2 = opBll.bjJLCheckReturn(
									jlM.getId(), jlM.getEmsb_tapr_id(),
									remark.getValue(), UserInfo.getUsername());
							if (!"1".equals(message2[0])) {
								msg = msg + "，" + jlM.getOwnmonth()
										+ "医疗补交数据操作不成功";
							}
						}

					} else {
						msg = msg + "，" + bjModel.getOwnmonth() + "养老补交数据操作不成功";
					}

				}

				Messagebox.show("退回操作完成！" + msg, "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show("请在备注处，填写退回原因。", "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("退回出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmShebaoBJModel getBjModel() {
		return bjModel;
	}

	public void setBjModel(EmShebaoBJModel bjModel) {
		this.bjModel = bjModel;
	}

}
