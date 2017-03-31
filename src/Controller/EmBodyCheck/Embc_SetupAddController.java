package Controller.EmBodyCheck;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmBcSetupAddressModel;
import Model.EmBcSetupModel;
import Util.UserInfo;
import bll.EmBodyCheck.EmBcSetup_OperateBll;
import bll.EmBodyCheck.Embc_SetupSellectBll;

public class Embc_SetupAddController {
	EmBcSetup_OperateBll bll = new EmBcSetup_OperateBll();
	EmBcSetupModel frommodel = (EmBcSetupModel) Executions.getCurrent()
			.getArg().get("model");
	Date statedate = null;
	Date enddate = null;
	List<EmBcSetupAddressModel> addresslist = new ArrayList<EmBcSetupAddressModel>();

	public Embc_SetupAddController() {

		if (frommodel != null) {

			Embc_SetupSellectBll selectbll = new Embc_SetupSellectBll();
			if (frommodel.getEbcs_inuredate() != null
					&& frommodel.getEbcs_inuredate() != "") {
				statedate = changedate(frommodel.getEbcs_inuredate());
			}
			if (frommodel.getEbcs_indate() != null
					&& frommodel.getEbcs_indate() != "") {
				enddate = changedate(frommodel.getEbcs_indate());
			}
			addresslist = selectbll.getEmBcSetupAddressInfo(frommodel
					.getEbcs_id());
			frommodel.setStateName(frommodel.getEbcs_state().equals(1) ? "生效"
					: "终止");
		}
	}

	// 体检机构新增
	@Command
	public void embcsetupadd(@BindingParam("setup") String setup,
			@BindingParam("linkname") String linkname,
			@BindingParam("phone") String phone,
			@BindingParam("begindate") Date begindate,
			@BindingParam("enddate") Date enddate,
			@BindingParam("feetype") String feetype,
			@BindingParam("address") String address,
			@BindingParam("liu") String liucheng,
			@BindingParam("remark") String remark,
			@BindingParam("w") Window win,
			@BindingParam("limit") BigDecimal limit,
			@BindingParam("pstate") String pstate) {
		EmBcSetupModel model = new EmBcSetupModel();
		model.setEbcs_addname(UserInfo.getUsername());
		model.setEbcs_hospital(setup);
		model.setEbcs_phone(phone);
		model.setEbcs_name(linkname);

		if (setup == null || setup.equals("")) {
			Messagebox.show("请输入机构名称", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (bll.checkEmbcSetUp(setup, null)) {
				Messagebox.show("该机构已存在.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		if (begindate != null && !begindate.equals("")) {
			model.setEbcs_inuredate(getStringDate(begindate));
		}
		if (enddate != null && !enddate.equals("")) {
			model.setEbcs_indate(getStringDate(enddate));
		}
		model.setEbcs_balance(feetype);
		model.setEbsa_address(address);
		model.setEbcs_flow(liucheng);
		model.setEbcs_remark(remark);
		if (pstate != null) {
			if (pstate.equals("生效")) {
				model.setEbcs_pstate(1);
			} else {
				model.setEbcs_pstate(0);
			}
		}
		model.setEbcs_limit(limit);
		int k = bll.EmBcSetupAdd(model);
		if (k > 0) {
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		win.detach();
	}

	// 体检机构信息更新
	@Command
	public void embcsetupUpdate(@BindingParam("setup") String setup,
			@BindingParam("linkname") String linkname,
			@BindingParam("phone") String phone,
			@BindingParam("begindate") Date begindate,
			@BindingParam("enddate") Date enddate,
			@BindingParam("feetype") String feetype,
			@BindingParam("address") String address,
			@BindingParam("liu") String liucheng,
			@BindingParam("remark") String remark,
			@BindingParam("id") String id,
			@BindingParam("winupdate") Window winupdate,
			@BindingParam("rws") Rows rws, @BindingParam("gd") Grid gd,
			@BindingParam("limit") BigDecimal limit,
			@BindingParam("pstate") String pstate,
			@BindingParam("datastate") String datastate) {
		EmBcSetupModel model = new EmBcSetupModel();
		model.setEbcs_addname(UserInfo.getUsername());
		model.setEbcs_hospital(setup);
		model.setEbcs_phone(phone);
		model.setEbcs_name(linkname);
		model.setEbcs_id(frommodel.getEbcs_id());
		if (pstate != null) {
			if (pstate.equals("生效")) {
				model.setEbcs_pstate(1);
			} else {
				model.setEbcs_pstate(0);
			}
		}
		if (datastate != null) {
			if (datastate.equals("生效")) {
				model.setEbcs_state(1);
			} else {
				model.setEbcs_state(0);
			}
		}
		model.setEbcs_limit(limit);

		if (model.getEbcs_hospital() == null
				|| model.getEbcs_hospital().equals("")) {
			Messagebox.show("请输入机构名称", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		} else {
			if (bll.checkEmbcSetUp(model.getEbcs_hospital(), model.getEbcs_id())) {
				Messagebox.show("该机构已存在.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
				return;
			}
		}

		if (begindate != null && !begindate.equals("")) {
			model.setEbcs_inuredate(getStringDate(begindate));
		}
		if (enddate != null && !enddate.equals("")) {
			model.setEbcs_indate(getStringDate(enddate));
		}
		model.setEbcs_balance(feetype);
		model.setEbsa_address(address);
		model.setEbcs_flow(liucheng);
		model.setEbcs_remark(remark);
		int k = bll.EmBcSetupUpdate(model);

		int j = 0;
		Date d = new Date();
		for (EmBcSetupAddressModel m : addresslist) {
			System.out.print(m.getStateName());
			m.setEbsa_modtime(getStringDate(d));
			m.setEbsa_modname(UserInfo.getUsername());
			m.setEbsa_state(m.getStateName().equals("有效") ? 1 : 0);
			m.setEbsa_ystate(m.isEbsa_ychecked() ? 1 : 0);
			m.setEbsa_istate(m.isEbsa_ichecked() ? 1 : 0);
			m.setEbsa_w1(m.isW1() ? 1 : 0);
			m.setEbsa_w2(m.isW2() ? 1 : 0);
			m.setEbsa_w3(m.isW3() ? 1 : 0);
			m.setEbsa_w4(m.isW4() ? 1 : 0);
			m.setEbsa_w5(m.isW5() ? 1 : 0);
			m.setEbsa_w6(m.isW6() ? 1 : 0);
			m.setEbsa_w7(m.isW7() ? 1 : 0);
			j = bll.UpdateEmBcSetupAddress(m);
		}

		if (k > 0 && j > 0) {
			Messagebox
					.show("更新成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			winupdate.detach();
		} else if (k > 0 && j == 0) {
			Messagebox.show("机构基本信息更新成功，地址更新失败"
					+ rws.getChildren().get(0).getClass(), "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			winupdate.detach();
		} else if (k == 0 && j > 0) {
			Messagebox.show("机构基本信息更新失败，地址更新成功"
					+ rws.getChildren().get(0).getClass(), "提示", Messagebox.OK,
					Messagebox.INFORMATION);
			winupdate.detach();
		} else {
			Messagebox.show("更新失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 弹出政策文件页面
	@Command
	public void opendoc(@BindingParam("model") EmBcSetupAddressModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"Embc_AddressDoc.zul", null, map);
		window.doModal();
	}

	public Date getStatedate() {
		return statedate;
	}

	public void setStatedate(Date statedate) {
		this.statedate = statedate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public List<EmBcSetupAddressModel> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<EmBcSetupAddressModel> addresslist) {
		this.addresslist = addresslist;
	}

	// 把时间转换成字符串格式
	public static String getStringDate(Date d) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(d);
		return dateString;
	}

	// 把字符串格式转换成时间
	public Date changedate(String s) {
		java.util.Date date = null;
		if (s != null && !s.equals("") && s != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟

			try {
				date = sdf.parse(s);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;
	}

	public EmBcSetupModel getFrommodel() {
		return frommodel;
	}

	public void setFrommodel(EmBcSetupModel frommodel) {
		this.frommodel = frommodel;
	}

}
