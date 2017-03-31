package Controller.EmCommissionOutNew;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOutNew.EmCommissionOut_AllEcBll;
import bll.EmCommissionOutNew.EmCommissionOut_AutOperateBll;

public class EmCommissionOut_AllEcDiController {
	EmCommissionOut_AllEcBll bll = new EmCommissionOut_AllEcBll();

	// 批量二次确认
	@Command("wt_autall")
	public void wt_autall(@BindingParam("ecoc_id") Label ecoc_id,
			@BindingParam("yl_cp") Datebox yl_cp,
			@BindingParam("yl_op") Datebox yl_op,
			@BindingParam("jl_cp") Datebox jl_cp,
			@BindingParam("jl_op") Datebox jl_op,
			@BindingParam("djl_cp") Datebox djl_cp,
			@BindingParam("djl_op") Datebox djl_op,
			@BindingParam("sye_cp") Datebox sye_cp,
			@BindingParam("sye_op") Datebox sye_op,
			@BindingParam("syu_cp") Datebox syu_cp,
			@BindingParam("syu_op") Datebox syu_op,
			@BindingParam("gs_cp") Datebox gs_cp,
			@BindingParam("gs_op") Datebox gs_op,
			@BindingParam("house_cp") Datebox house_cp,
			@BindingParam("house_op") Datebox house_op,
			@BindingParam("fw_cp") Datebox fw_cp,
			@BindingParam("fw_op") Datebox fw_op,
			@BindingParam("file_cp") Datebox file_cp,
			@BindingParam("file_op") Datebox file_op,
			@BindingParam("fl_cp") Datebox fl_cp,
			@BindingParam("fl_op") Datebox fl_op,
			@BindingParam("yl_di") Label yl_di,
			@BindingParam("jl_di") Label jl_di,
			@BindingParam("djl_di") Label djl_di,
			@BindingParam("syu_di") Label syu_di,
			@BindingParam("gs_di") Label gs_di,
			@BindingParam("sye_di") Label sye_di,
			@BindingParam("house_di") Label house_di,
			@BindingParam("fw_di") Label fw_di,
			@BindingParam("file_di") Label file_di,
			@BindingParam("other_di") Label other_di,
			@BindingParam("win") Window win) throws Exception {

		String[] allecoc_id = ecoc_id.getValue().split(",");
		
		System.out.println("xxxxxx");
		System.out.println(ecoc_id.getValue());
		System.out.println(allecoc_id.length);

		String yl_cpdate = "";
		if (yl_cp.getValue() != null) {
			yl_cpdate = bll.DatetoSting(yl_cp.getValue());
		}

		String yl_opdate = "";
		if (yl_op.getValue() != null) {
			yl_opdate = bll.DatetoSting(yl_op.getValue());
		}

		String jl_cpdate = "";
		if (jl_cp.getValue() != null) {
			jl_cpdate = bll.DatetoSting(jl_cp.getValue());
		}

		String jl_opdate = "";
		if (jl_op.getValue() != null) {
			jl_opdate = bll.DatetoSting(jl_op.getValue());
		}

		String gs_cpdate = "";
		if (gs_cp.getValue() != null) {
			gs_cpdate = bll.DatetoSting(gs_cp.getValue());
		}

		String gs_opdate = "";
		if (gs_op.getValue() != null) {
			gs_opdate = bll.DatetoSting(gs_op.getValue());
		}

		String syu_cpdate = "";
		if (syu_cp.getValue() != null) {
			syu_cpdate = bll.DatetoSting(syu_cp.getValue());
		}

		String syu_opdate = "";
		if (syu_op.getValue() != null) {
			syu_opdate = bll.DatetoSting(syu_op.getValue());
		}

		String sye_cpdate = "";
		if (sye_cp.getValue() != null) {
			sye_cpdate = bll.DatetoSting(sye_cp.getValue());
		}

		String sye_opdate = "";
		if (sye_op.getValue() != null) {
			sye_opdate = bll.DatetoSting(sye_op.getValue());
		}

		String house_cpdate = "";
		if (house_cp.getValue() != null) {
			house_cpdate = bll.DatetoSting(house_cp.getValue());
		}

		String house_opdate = "";
		if (house_op.getValue() != null) {
			house_opdate = bll.DatetoSting(house_op.getValue());
		}

		String fw_date = "";
		if (fw_cp.getValue() != null) {
			fw_date = bll.DatetoSting(fw_cp.getValue());
		}

		String file_date = "";
		if (file_cp.getValue() != null) {
			file_date = bll.DatetoSting(file_cp.getValue());
		}

		String other_date = "";
		if (fl_cp.getValue() != null) {
			other_date = bll.DatetoSting(fl_cp.getValue());
		}
		int rsult = 1;

		for (int i = 0; i < allecoc_id.length; i++) {

			System.out.println("--" + allecoc_id[i]);

			rsult = bll.ecdiaut(allecoc_id[i], yl_cpdate, yl_opdate,
					sye_cpdate, sye_opdate, gs_cpdate, gs_opdate, jl_cpdate,
					jl_opdate, syu_cpdate, syu_opdate, house_cpdate,
					house_opdate, fw_date, file_date, other_date);// 更新二次确认时间
			
			if (rsult == 0) {
				EmCommissionOut_AutOperateBll ccsaBll = new EmCommissionOut_AutOperateBll();

				String[] message = ccsaBll.allec_aut(allecoc_id[i]);

			}
		}

		try {
			// 弹出提示

			Messagebox.show("操作已完成！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);

			// Executions.sendRedirect("EmCommissionOut_OveAut.zul?");
			
		} catch (Exception e) {
			// TODO: handle exception
			Messagebox.show("操作出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
		win.detach();
	}
}
