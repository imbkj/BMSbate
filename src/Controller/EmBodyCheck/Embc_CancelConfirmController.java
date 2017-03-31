package Controller.EmBodyCheck;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmBodyCheckItemModel;
import Model.EmBodyCheckModel;
import Model.EmBodycheckCancelModel;
import Model.EmbaseModel;
import Model.TaskProcessViewModel;
import Util.UserInfo;
import bll.Archives.EmArchive_SelectBll;
import bll.EmBodyCheck.EmBcInfo_OperateBll;
import bll.EmBodyCheck.EmBcInfo_SelectBll;
import bll.EmBodyCheck.EmbcItem_SelectBll;

public class Embc_CancelConfirmController {
	private EmBodyCheckModel model = new EmBodyCheckModel();
	private Integer eadaId = Integer.valueOf(Executions.getCurrent().getArg()
			.get("daid").toString());
	private EmBcInfo_SelectBll selectbll = new EmBcInfo_SelectBll();
	private EmbcItem_SelectBll itembll = new EmbcItem_SelectBll();
	private String embctype = "";// 体检类型
	// 员工信息
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	// 体检项目信息
	private List<EmBodyCheckItemModel> itemlist = new ArrayList<EmBodyCheckItemModel>();
	private EmbaseModel emmodel = new EmbaseModel();
	private List<EmBodyCheckModel> bclist = selectbll
			.getEmBodyCheckInfo(" and embc_id=" + eadaId);
	private TaskProcessViewModel tmodel = new TaskProcessViewModel();
	private String username = UserInfo.getUsername();
	private EmBodycheckCancelModel m=new EmBodycheckCancelModel();
	private Map map=Executions.getCurrent().getArg();
	
	// 构造函数
	public Embc_CancelConfirmController() {
		if (bclist.size() > 0) {
			model = bclist.get(0);
		}
		if (model.getGid() != null && model.getGid() > 0) {
			embaselist = selectbll.getEmBaseInfo(" and emba_state=1 and gid="
					+ model.getGid());
			if (embaselist.size() > 0) {
				emmodel = embaselist.get(0);
			}
		}
		if (model != null) {
			embctype = chengeEmbcType(model);
		}
		itemlist = itembll.getEmBcItemInfo(" and ebit_id in("
				+ model.getEbcl_itemnums() + ")");
		if (emmodel.getEmba_idcard() == null
				|| emmodel.getEmba_idcard().equals("")) {
			if (model.getEmbc_idcard() != null
					&& !model.getEmbc_idcard().equals("")) {
				emmodel.setEmba_idcard(model.getEmbc_idcard());
			}
		}
		m=selectbll.getEmBodycheckCancelModel(model.getEbcl_id());
	}

	// 把体检类型有数字转换成中文
	private String chengeEmbcType(EmBodyCheckModel m) {
		String type = "";
		if (m.getEbcl_type() != null) {
			if (m.getEbcl_type() == 0) {
				type = "单次体检";
			} else if (m.getEbcl_type() == 1) {
				type = "入职体检";
			} else if (m.getEbcl_type() == 2) {
				type = "年度体检";
			}
		}
		return type;
	}

	// 确认取消预约
	@Command
	public void confirm(@BindingParam("win") Window win) {
		EmBcInfo_OperateBll obll = new EmBcInfo_OperateBll();
		String sql = ",ebcl_state=0";//ebcl_state=0表示已取消
		model.setOcon("后道确认取消预约");
		String[] str = obll.EmBodyCheckBack(model,sql);
		if (str[0] == "1") {
			map.put("flag", "1");
			Messagebox
					.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			win.detach();
		} else {
			Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public String getEmbctype() {
		return embctype;
	}

	public void setEmbctype(String embctype) {
		this.embctype = embctype;
	}

	public List<EmBodyCheckItemModel> getItemlist() {
		return itemlist;
	}

	public void setItemlist(List<EmBodyCheckItemModel> itemlist) {
		this.itemlist = itemlist;
	}

	public EmBodyCheckModel getModel() {
		return model;
	}

	public void setModel(EmBodyCheckModel model) {
		this.model = model;
	}

	private String changedate(Date d) {
		String formatDate = "";
		if (d != null) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			formatDate = df.format(d);
		}
		return formatDate;
	}

	public TaskProcessViewModel getTmodel() {
		return tmodel;
	}

	public void setTmodel(TaskProcessViewModel tmodel) {
		this.tmodel = tmodel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	public EmBodycheckCancelModel getM() {
		return m;
	}

	public void setM(EmBodycheckCancelModel m) {
		this.m = m;
	}

	private java.sql.Date changetosqldate(Date d) {
		java.sql.Date date = null;
		if (d != null && !d.equals("")) {
			date = new java.sql.Date(d.getTime());
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			if (str != null && !str.equals("")) {
				date = format.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
