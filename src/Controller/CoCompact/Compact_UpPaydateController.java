package Controller.CoCompact;

import java.text.DecimalFormat;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.CoCompact.BaseInfo_SelectListBll;
import bll.CoCompact.CoCompact_OperateBll;
import Model.CoCompactModel;

public class Compact_UpPaydateController {
	private CoCompactModel cocoM;
	private BaseInfo_SelectListBll cocoB = new BaseInfo_SelectListBll();
	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();

	int coco_id = Integer.parseInt(Executions.getCurrent().getArg()
			.get("coco_id").toString());

	private Double fw_per;// 服务类比例%
	private Double fl_per;// 福利类比例%
	private Double dk_per;// 代扣代缴类比例%
	private DecimalFormat df = new DecimalFormat(".##");

	public Compact_UpPaydateController() {
		cocoM = cocoB.searchCoCoBase(String.valueOf(coco_id)).get(0);
		setFw_per(Double.parseDouble(df.format(cocoM.getCoco_fw_p() * 100)));
		setFl_per(Double.parseDouble(df.format(cocoM.getCoco_fl_p() * 100)));
		setDk_per(Double.parseDouble(df.format(cocoM.getCoco_dk_p() * 100)));
	}

	@Command("submit")
	public void submit(@BindingParam("win") Window win) {

		if (pageChk()) {

			String[] message = cocoBll.upCocoPaydate(cocoM);
			// 弹出提示
			if ("1".equals(message[0])) {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.INFORMATION);

				// 关闭窗口
				win.detach();
			} else {
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}

		}
	}

	public boolean pageChk() {
		boolean chk = true;

		if (cocoM.getCoco_paydate() == 0 || cocoM.getCoco_paydate() == null) {
			chk = false;
			// 弹出提示
			Messagebox.show("请选择 每月付款日！", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			// 检查比例录入的格式是否正确
			if (chk) {
				try {
					cocoM.setCoco_fw_p(fw_per / 100);
				} catch (Exception e) {
					chk = false;
					// 弹出提示
					Messagebox.show("服务类比例 格式有误！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}

			if (chk) {
				try {
					cocoM.setCoco_fl_p(fl_per / 100);
				} catch (Exception e) {
					chk = false;
					// 弹出提示
					Messagebox.show("福利类比例 格式有误！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
			if (chk) {
				try {
					cocoM.setCoco_dk_p(dk_per / 100);
				} catch (Exception e) {
					chk = false;
					// 弹出提示
					Messagebox.show("代扣代缴类比例 格式有误！", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}

		return chk;
	}

	public CoCompactModel getCocoM() {
		return cocoM;
	}

	public void setCocoM(CoCompactModel cocoM) {
		this.cocoM = cocoM;
	}

	public Double getFw_per() {
		return fw_per;
	}

	public void setFw_per(Double fw_per) {
		this.fw_per = fw_per;
	}

	public Double getFl_per() {
		return fl_per;
	}

	public void setFl_per(Double fl_per) {
		this.fl_per = fl_per;
	}

	public Double getDk_per() {
		return dk_per;
	}

	public void setDk_per(Double dk_per) {
		this.dk_per = dk_per;
	}

}
