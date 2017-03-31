package Controller.EmReg;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import dal.EmReg.EmReg_ListDal;

import Model.EmRegistrationInfoModel;
import bll.EmReg.EmReg_ListBll;
import bll.EmReg.EmReg_OperateBll;

public class EmReg_DocBackController {

	private EmRegistrationInfoModel erm = new EmRegistrationInfoModel();

	Integer daid;
	private Integer puzu_id;
	private String name = "";
	private Date date;
	private List<String> list = new ArrayList<String>();

	public EmReg_DocBackController() {
		try {
			EmReg_ListBll bll = new EmReg_ListBll();
			list = bll.getLogin();
			daid = Integer.parseInt(Executions.getCurrent().getArg()
					.get("daid").toString());

			setErm(bll.getEmRegInfo(daid, ""));
			if (erm.getErin_hjtype() != null
					&& erm.getErin_hjtype().equals("本市城镇")) {
				setPuzu_id(erm.getCori_sz_puzu_id() == null ? 12 : erm
						.getCori_sz_puzu_id());
				EmReg_ListDal dl = new EmReg_ListDal();
				boolean flag = dl.isexistdata(puzu_id);
				if (!flag) {
					puzu_id = 12;
				}
			} else {
				setPuzu_id(erm.getCori_wd_puzu_id() == null ? 22 : erm
						.getCori_wd_puzu_id());
				EmReg_ListDal dl = new EmReg_ListDal();
				boolean flag = dl.isexistdata(puzu_id);
				if (!flag) {
					puzu_id = 22;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 提交
	 * 
	 * @param win
	 * @param gd
	 */
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("gd") Grid gd, @BindingParam("a") String a) {
		EmReg_OperateBll bll = new EmReg_OperateBll();
		try {
			String strinfo = "";
			String sql = "";
			if (a != null && a.equals("1")) {
				if (name == null || name.equals("")) {
					strinfo = "请填写签收人姓名";
				} else if (date == null) {
					strinfo = "请填写签收时间";
				} else {
					sql = ",erin_signname='" + name + "',erin_signtime='"
							+ getStringDate(date) + "'";
				}
			} else if (a != null && a.equals("2")) {
				if (name == null || name.equals("")) {
					strinfo = "请填写退资料人姓名";
				} else if (date == null) {
					strinfo = "请填写退资料时间";
				} else {
					sql = ",erin_databackname='" + name
							+ "',erin_databacktime='" + getStringDate(date)
							+ "'";
				}
			} else if (a != null && a.equals("3")) {
				if (name == null || name.equals("")) {
					strinfo = "请填写退资料人姓名";
				} else if (date == null) {
					strinfo = "请填写退资料时间";
				} else {
					sql = ",erin_signcenter='" + name
							+ "',erin_signcentertime='" + getStringDate(date)
							+ "'";
				}
			}
			if (strinfo == "") {
				String[] str = bll.DocBack(erm, gd);
				if (str[0].equals("1")) {
					bll.docinfoback(daid, sql);
					Messagebox.show(str[1], "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show(str[1], "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show(strinfo, "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("提交出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Integer getPuzu_id() {
		return puzu_id;
	}

	public void setPuzu_id(Integer puzu_id) {
		this.puzu_id = puzu_id;
	}

	public EmRegistrationInfoModel getErm() {
		return erm;
	}

	public void setErm(EmRegistrationInfoModel erm) {
		this.erm = erm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}
}
