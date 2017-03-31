package Controller.EmResidencePermit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;
import Model.EmCAFFeeInfoModel;
import Model.EmResidencePermitInfoModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;
import bll.EmResidencePermit.Emrp_FeeOperateBll;
import bll.EmResidencePermit.Emrp_FeeSelectBll;
import bll.EmResidencePermit.Emrp_ListBll;

public class Emrp_FeeManagerController {
	private Emrp_FeeSelectBll bll = new Emrp_FeeSelectBll();
	private String sqls=" and erpi_fee is not null and erpi_fee>0";
	private List<EmResidencePermitInfoModel> list = bll.getFeeList(sqls);
	private List<String> clientlist = bll.getClient();
	private EmResidencePermitInfoModel model = new EmResidencePermitInfoModel();
	private Date erpi_wd_loan_date, erpi_ri_date;
	private List<EmResidencePermitInfoModel> cklist = new ArrayList<EmResidencePermitInfoModel>();

	@Command
	@NotifyChange("list")
	public void search(@BindingParam("ownmonth") Date ownmonth) {
		if (ownmonth != null) {
			DateStringChange c = new DateStringChange();
			model.setOwnmonth(c.DatetoSting(ownmonth, "yyyyMM"));
		} else {
			model.setOwnmonth("");
		}
		if (erpi_wd_loan_date != null) {
			DateStringChange c = new DateStringChange();
			model.setErpi_wd_loan_date(c.DatetoSting(erpi_wd_loan_date,
					"yyyy-MM-dd"));
		} else {
			model.setErpi_wd_loan_date("");
		}
		if (erpi_ri_date != null) {
			DateStringChange c = new DateStringChange();
			model.setErpi_ri_date(c.DatetoSting(erpi_ri_date, "yyyy-MM-dd"));
		} else {
			model.setErpi_ri_date("");
		}
		list = bll.getFeeInfoList(model);
	}

	// 生成费用明细单号
	public Integer createnumber(Grid gd) {
		Integer k = 0;
		// 检查是否有勾选数据
		if (cklist.size() > 0) {
			String number = "WJZZ" + GetNowDate();
			String addname = UserInfo.getUsername();
			Emrp_FeeOperateBll obll = new Emrp_FeeOperateBll();
			for (EmResidencePermitInfoModel m : cklist) {
				EmCAFFeeInfoModel model = new EmCAFFeeInfoModel();
				model.setGid(m.getGid());
				model.setCid(m.getCid());
				model.setOwnmonth(Integer.parseInt(m.getOwnmonth()));
				model.setEcfi_caf_id(m.getErpi_id());
				model.setEcfi_cl_number(number);
				model.setEcfi_payment_kind(m.getErpi_payment_kind());
				model.setEcfi_payment_state(m.getErpi_payment_state());
				model.setEcfi_fee(m.getErpi_fee());
				model.setEcfi_addname(addname);
				m.setErpi_cl_number(number);
				if (m.getErpi_fee()!=null&&m.getErpi_fee() > 0) {
					k = k + obll.EmCAFFeeInfoAdd(model);
				}
			}
		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		return k;
	}

	// 全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck) {
		if (!cklist.isEmpty()) {
			cklist.clear();
		}
		Integer num = gd.getRows().getChildren().size();
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 13) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 13).getChildren().get(0);
				cb.setChecked(ck.isChecked());
				if (cb.isChecked()) {
					EmResidencePermitInfoModel m = cb.getValue();
					cklist.add(m);
				}
			}
		}
	}

	// 单选
	@Command
	public void checkck(@BindingParam("gd") Grid gd) {
		if (!cklist.isEmpty()) {
			cklist.clear();
		}
		Integer num = gd.getRows().getChildren().size();
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 13) != null) {
				Checkbox ck = (Checkbox) gd.getCell(i, 13).getChildren().get(0);
				EmResidencePermitInfoModel m = ck.getValue();
				if (ck.isChecked()) {
					cklist.add(m);
				}
			}
		}
	}

	// 生成支付明细excel
	@Command
	@NotifyChange("list")
	public void Export(@BindingParam("gd") Grid gd, HttpServletResponse response)
			throws Exception {
		if (cklist.size() > 0) {
			Integer k = createnumber(gd);
			if (k > 0) {
				plyUtil ply = new plyUtil();
				String path = "/../../EmResidencePermit/file/";
				String paths = "EmResidencePermit/downloadfile/";
				String absolutePath = FileOperate.getAbsolutePath();
				String filename = "hdmx.xls";
				String number = "WJZZ" + GetNowDate();
				// 创建当前日子
				Date date = new Date();
				// 格式化日期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				// 格式化日期(产生文件名)
				String newfilename = "居住证明细" + sdf.format(date) + ".xls";
				// 获取绝对路径
				String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
				// 创建文件
				// File file = new File(path);
				// file.createNewFile();
				try {
					File f = new File(absolutePath + paths + newfilename);
					if (f.isFile()) {
						f.delete();
					}
					ExcelService exl = new newExcelImpl(solpath, absolutePath
							+ paths + newfilename, cklist, number);
					exl.writeExcel();
					list = bll.getFeeInfoList(model);
				} catch (Exception e) {
					System.out.println(e.toString());
				}
				FileOperate.download(paths + newfilename);
			} else {
				Messagebox.show("生成支付明细失败", "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 详情
	@Command("detail")
	public void detail(@BindingParam("each") EmResidencePermitInfoModel m,
			@BindingParam("role") String role) {
		String url = "Emrp_Detail.zul";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("daid", m.getErpi_id());
		map.put("role", role);
		Window win = (Window) Executions.createComponents(url, null, map);
		win.doModal();
	}

	public String GetNowDate() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	public List<EmResidencePermitInfoModel> getList() {
		return list;
	}

	public void setList(List<EmResidencePermitInfoModel> list) {
		this.list = list;
	}

	public EmResidencePermitInfoModel getModel() {
		return model;
	}

	public void setModel(EmResidencePermitInfoModel model) {
		this.model = model;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

	public Date getErpi_wd_loan_date() {
		return erpi_wd_loan_date;
	}

	public void setErpi_wd_loan_date(Date erpi_wd_loan_date) {
		this.erpi_wd_loan_date = erpi_wd_loan_date;
	}

	public Date getErpi_ri_date() {
		return erpi_ri_date;
	}

	public void setErpi_ri_date(Date erpi_ri_date) {
		this.erpi_ri_date = erpi_ri_date;
	}

}
