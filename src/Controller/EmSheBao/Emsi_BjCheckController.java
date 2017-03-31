package Controller.EmSheBao;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmSheBao.Emsi_OperateDal;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.EmShebaoBJModel;
import Util.RedirectUtil;
import Util.UserInfo;
import bll.EmSheBao.Emsc_DeclareSelBll;
import bll.EmSheBao.Emsi_CheckOperateBll;
import bll.EmSheBao.Emsi_SelBll;

public class Emsi_BjCheckController {
	private final int id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("daid").toString());

	private EmShebaoBJModel bjModel;
	private Emsc_DeclareSelBll selbll;
	private String reason;

	public Emsi_BjCheckController() {
		selbll = new Emsc_DeclareSelBll();
		bjModel = selbll.getBjInfoById(id);
		reason = "";

	}

	// 提交转下一步
	@Command("bjPass")
	public void bjPass(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd) {
		try {
			String msg="";
			
			Emsi_CheckOperateBll opBll = new Emsi_CheckOperateBll();
			String[] message = opBll.bjCheckPass(id, bjModel.getEmsb_tapr_id(),
					reason, UserInfo.getUsername());
			if ("1".equals(message[0])) {
				
				//更新相应的医疗补交数据状态
				Emsi_OperateDal eDal = new Emsi_OperateDal();
				boolean ifJLBJ = eDal.getShebaoBJJL(bjModel.getGid(),
						bjModel.getOwnmonth(), bjModel.getEmsb_startmonth());
				if (ifJLBJ == true) {
					// 通过养老补交获取医疗补交数据
					EmShebaoBJModel jlM = new EmShebaoBJModel();
					Emsi_SelBll bll=new Emsi_SelBll() ;
					jlM = bll.getBjJLListByBJid(bjModel.getId());

					String[] message2=opBll.bjJLCheckPass(jlM.getId(),jlM.getEmsb_tapr_id(),reason,UserInfo.getUsername());
					if (!"1".equals(message2[0])) {
						msg = "，但是社保医疗数据操作失败，请联系IT部！";
					}
				}
				
				// 处理材料
				DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
				docOC.AddsubmitDoc(gd, String.valueOf(id));
				Messagebox.show(message[1]+msg, "操作提示", Messagebox.OK,
						Messagebox.NONE);
				win.detach();
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("操作出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 退回
	@Command("bjReturn")
	public void bjReturn(@BindingParam("win") Window win) {
		try {
			if (reason != null && !"".equals(reason)) {
				String msg="";
				
				Emsi_CheckOperateBll opBll = new Emsi_CheckOperateBll();
				String[] message = opBll.bjCheckReturn(id,
						bjModel.getEmsb_tapr_id(), reason,
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

						String[] message2=opBll.bjJLCheckReturn(jlM.getId(), jlM.getEmsb_tapr_id(), reason, UserInfo.getUsername());
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
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("退回出错。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	@Command("lx")
	public void lx() {
		RedirectUtil u = new RedirectUtil();

		u.indexAddTab("/Embase/Embase_yfzxinfo.zul?gid=" + bjModel.getGid()
				+ "", "雇员服务中心");
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public EmShebaoBJModel getBjModel() {
		return bjModel;
	}

}
