package Controller.EmCensus.EmDh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmCensus.EmDh.EmDh_OperateBll;
import bll.EmCensus.EmDh.EmDh_SelectBll;

import Model.CoBaseModel;
import Model.EmDhModel;
import Model.EmbaseModel;
import Util.RedirectUtil;
import Util.UserInfo;

public class EmDh_AddController {
	private EmDh_SelectBll bll = new EmDh_SelectBll();
	private List<EmbaseModel> embaselist = new ArrayList<EmbaseModel>();
	EmbaseModel frommodel = (EmbaseModel) Executions.getCurrent().getArg()
			.get("model");
	String gid = Executions.getCurrent().getArg().get("gid") + "";
	// private List<String> clientlist=bll.getClient();
	private String eshebaotype = "";
	private boolean dnvis = false, rw = false, hkcell = false;
	private String ifdn = "否", ifhk = "否";
	private EmDhModel model = new EmDhModel();

	public EmDh_AddController() {
		if (gid != null && !gid.equals("") && gid != "") {
			embaselist = bll.getEmbaseInfo(" and gid=" + gid);
			if (embaselist != null && !embaselist.isEmpty()) {
				frommodel = embaselist.get(0);
			}
		}
		if (frommodel != null) {
			init();
			EmDh_SelectBll dhbll = new EmDh_SelectBll();
			boolean flag = dhbll.getShebaoType(frommodel.getCid());
			if (flag) {
				model.setEmdh_shebaotype("独立户");
			} else {
				model.setEmdh_shebaotype("中智户");
			}
			if (frommodel != null) {
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
				String ym = year + "" + month + "";
				EmDh_SelectBll selectbll = new EmDh_SelectBll();
				List<String> danganlist = selectbll.getCogListInfo(
						frommodel.getGid(), Integer.parseInt(ym), "档案管理");
				List<String> hukoulist = selectbll.getCogListInfo(
						frommodel.getGid(), Integer.parseInt(ym), "户口管理");
				if (danganlist.size() > 0) {
					rw = true;
					dnvis = true;
					ifdn = "是";
				}

				if (hukoulist.size() > 0) {
					rw = true;
					hkcell = true;
					ifhk = "是";
				}
			}
		}
	}

	private void init() {
		if (frommodel != null) {
			model.setEmdh_name(frommodel.getEmba_name());
			model.setEmdh_company(frommodel.getCoba_name());
			model.setCoba_shortname(frommodel.getCoba_name());
			model.setEmdh_tel(frommodel.getEmba_mobile());
			model.setEmdh_mail(frommodel.getEmba_email());
			model.setEmdh_client(frommodel.getCoba_client());
			model.setCid(frommodel.getCid());
			model.setGid(frommodel.getGid());
			model.setEmdh_idcard(frommodel.getEmba_idcard());
			model.setEmdh_marital(frommodel.getEmba_marital());
			model.setEmdh_education(frommodel.getEmba_education());
			model.setEmdh_school(frommodel.getEmba_school());
		}
	}

	//
	@Command
	public void createwin(@BindingParam("win") Window win) {
		if (bll.ifExistDhInfo(Integer.parseInt(gid))) {
			Messagebox.show("该员工已有调户信息", "提示", Messagebox.OK, Messagebox.ERROR);
			RedirectUtil util = new RedirectUtil();
			util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
		}
	}

	// 新增调户申请
	@Command
	public void summit(@BindingParam("feetime") Combobox feetime) {
		if (model.getEmdh_dhtype() == null || model.getEmdh_dhtype().equals("")) {
			Messagebox.show("请选择调户方式", "提示", Messagebox.OK, Messagebox.ERROR);
		}else {
			EmDh_OperateBll bll = new EmDh_OperateBll();
			Date now = new Date();
			model.setEmdh_servetar("");
			model.setEmdh_servetype("");
			model.setEmdh_isbackfee(0);
			model.setEmdh_backreason("");
			model.setEmdh_state(1);
			model.setEmdh_addtime(now);
			model.setEmdh_addname(UserInfo.getUsername());
			model.setEmdh_time1(now);
			model.setEmdh_isnote(0);
			model.setEmdh_state1(2);
			model.setEmdh_ifdn(ifdn);
			model.setEmdh_ifhk(ifhk);
			model.setEmdh_goveservetype("");
			if (model.getEmdh_feetime() != null
					&& !model.getEmdh_feetime().equals("")) {
				model.setEmdh_feestep(Integer.parseInt(feetime
						.getSelectedItem().getValue().toString()));
			}
			String[] str = bll.EmDhAdd(model);
			if (str[0] == "1") {
				Messagebox.show(str[1], "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				RedirectUtil util = new RedirectUtil();
				util.refreshEmUrl("/EmCensus/EmRs_FileServerInfo.zul");
			} else {
				Messagebox.show(str[1], "提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	public EmbaseModel getFrommodel() {
		return frommodel;
	}

	public void setFrommodel(EmbaseModel frommodel) {
		this.frommodel = frommodel;
	}

	public String getEshebaotype() {
		return eshebaotype;
	}

	public void setEshebaotype(String eshebaotype) {
		this.eshebaotype = eshebaotype;
	}

	public boolean isDnvis() {
		return dnvis;
	}

	public void setDnvis(boolean dnvis) {
		this.dnvis = dnvis;
	}

	public boolean isRw() {
		return rw;
	}

	public void setRw(boolean rw) {
		this.rw = rw;
	}

	public boolean isHkcell() {
		return hkcell;
	}

	public void setHkcell(boolean hkcell) {
		this.hkcell = hkcell;
	}

	public String getIfdn() {
		return ifdn;
	}

	public void setIfdn(String ifdn) {
		this.ifdn = ifdn;
	}

	public String getIfhk() {
		return ifhk;
	}

	public void setIfhk(String ifhk) {
		this.ifhk = ifhk;
	}

	public EmDhModel getModel() {
		return model;
	}

	public void setModel(EmDhModel model) {
		this.model = model;
	}

	// 判断字符串是否都是数字
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	// 判断输入的电话号码是否有错误
	public static boolean isTel(String str) {
		boolean flag = false;
		flag = isNum(str);
		if (flag == false) {
			int k = str.length();
			for (int i = 0; i < k; i++) {
				if (str.substring(i, i + 1) != null) {
					if (!str.substring(i, i + 1).equals("-")
							&& !isNum(str.substring(i, i + 1))) {
						flag = false;
						break;
					} else {
						flag = true;
					}
				}
			}
		}
		return flag;
	}

	// 判断输入的Email格式是否正确
	public static boolean isEmail(String str) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(str);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}
}
