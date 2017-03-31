package Controller.EmCommissionOutNew;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmCommissionOutChangeModel;
import bll.EmCommissionOutNew.EmCommissionOut_AddAutBll;
import bll.EmCommissionOutNew.EmCommissionOut_AllEcBll;
import bll.EmCommissionOutNew.EmCommissionOut_AutOperateBll;
import bll.Taskflow.Task_controlBll;

public class EmCommissionOut_AllBackController {

	private List<EmCommissionOutChangeModel> gettaprid = new ListModelList<>();// 获取cid,taprid

	// 批量二次确认
	@Command("wt_backall")
	public void wt_backall(@BindingParam("ecoc_id") Label ecoc_id,
			@BindingParam("outcontent") Textbox outcontent,
			@BindingParam("win") Window win) throws Exception {

		String[] allecoc_id = ecoc_id.getValue().split(",");

		int rsult = 1;
		int er_st=0;
		
		if (outcontent.getValue().equals("")) {
			Messagebox.show("请录入退回原因！", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
			return;
		}

		for (int i = 0; i < allecoc_id.length; i++) {

			System.out.println("--" + allecoc_id[i]);
			EmCommissionOut_AddAutBll bll = new EmCommissionOut_AddAutBll();
			EmCommissionOut_AutOperateBll ccsaBll = new EmCommissionOut_AutOperateBll();
			setGettaprid(bll.gettaprid(allecoc_id[i]));// 社保费用明细
			try {
				String[] message = ccsaBll.back(
						allecoc_id[i],
						Integer.parseInt(gettaprid.get(0).getEcoc_tapr_id()
								.toString()), outcontent.getValue());
				
				Task_controlBll tbll =new Task_controlBll();
				 if (tbll.setOpName(Integer.parseInt(message[2].toString()),gettaprid.get(0).getCoba_client())==1)
		            {			            

		            //Messagebox.show(message[1], "操作提示", Messagebox.OK,
		            //      Messagebox.INFORMATION);
		            //win.detach();
		            }
		            else
		            {
		            	er_st=er_st+1;
		           //     Messagebox
		           //     .show("退回失败，请联系IT部门!", "操作提示", Messagebox.OK, Messagebox.ERROR);
		            } 
			} catch (Exception e) {
				// TODO: handle exception
				Messagebox.show("操作出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
		
		String err_con="";
		if(er_st>0){
			err_con="其中有"+er_st+"条未退回";
		}

		Messagebox.show("操作已完成！"+err_con, "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);
		win.detach();
	}

	public List<EmCommissionOutChangeModel> getGettaprid() {
		return gettaprid;
	}

	public void setGettaprid(List<EmCommissionOutChangeModel> gettaprid) {
		this.gettaprid = gettaprid;
	}

}
