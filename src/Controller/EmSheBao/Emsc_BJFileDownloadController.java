package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.EmSheBao.Emsi_OperateDal;

import bll.EmSheBao.Emsc_DeclareOperateBll;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_CheckOperateBll;
import bll.EmSheBao.Emsi_SelBll;

import Model.EmShebaoBJModel;
import Util.FileOperate;
import Util.UserInfo;

public class Emsc_BJFileDownloadController {
	private Emsc_DeclareSelBll dsbll = new Emsc_DeclareSelBll();
	private Emsc_DeclareOperateBll dobll = new Emsc_DeclareOperateBll();
	private String id = Executions.getCurrent().getArg().get("daid").toString();
	private EmShebaoBJModel bjModel;

	public Emsc_BJFileDownloadController() {
		bjModel = dsbll.getBjInfoById(Integer.parseInt(id));
	}

	// 下载
	@Command("download")
	public void download() {
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
		String[] message;
		String msg="";
		message = dobll.BjDeclareDownload(bjModel);

		// 判断是否成功
		if (message[0].equals("1")) {
			
			//更新相应的医疗补交数据状态
			Emsi_OperateDal eDal = new Emsi_OperateDal();
			boolean ifJLBJ = eDal.getShebaoBJJL(bjModel.getGid(),
					bjModel.getOwnmonth(), bjModel.getEmsb_startmonth());
			if (ifJLBJ == true) {
				// 通过养老补交获取医疗补交数据
				EmShebaoBJModel jlM = new EmShebaoBJModel();
				Emsi_SelBll bll=new Emsi_SelBll() ;
				jlM = bll.getBjJLListByBJid(bjModel.getId());
				
				String[] message2=dobll.BjJLDeclareDownload(jlM);
				if (!"1".equals(message2[0])) {
					msg = "，但是社保医疗数据操作失败，请联系IT部！";
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
			Messagebox.show(message[1]+msg, "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);

		}
	}

	// 退回
	@Command("bjReturn")
	public void bjReturn(@BindingParam("win") final Window win,
			@BindingParam("remark") Textbox remark) {
		try {
			if (remark.getValue() != null && !"".equals(remark.getValue())) {
				String msg="";
				Emsi_CheckOperateBll opBll = new Emsi_CheckOperateBll();
				String[] message = opBll.bjCheckReturn(Integer.parseInt(id),
						bjModel.getEmsb_tapr_id(), remark.getValue(),
						UserInfo.getUsername());
				if ("1".equals(message[0])) {
					
					//如果有对应的医疗数据，则同时退回医疗数据
					Emsi_OperateDal eDal = new Emsi_OperateDal();
					boolean ifJLBJ = eDal.getShebaoBJJL(bjModel.getGid(),
							bjModel.getOwnmonth(), bjModel.getEmsb_startmonth());
					if (ifJLBJ == true) {
						// 通过养老补交获取医疗补交数据
						EmShebaoBJModel jlM = new EmShebaoBJModel();
						Emsi_SelBll bll=new Emsi_SelBll() ;
						jlM = bll.getBjJLListByBJid(bjModel.getId());

						String[] message2=opBll.bjJLCheckReturn(jlM.getId(), jlM.getEmsb_tapr_id(), remark.getValue(), UserInfo.getUsername());
						if (!"1".equals(message2[0])) {
							msg = "，但是社保医疗数据操作失败，请联系IT部！";
						}
					}
					
					Messagebox.show(message[1]+msg, "操作提示", Messagebox.OK,
							Messagebox.NONE);
					win.detach();
				} else {
					Messagebox.show(message[1], "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
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
