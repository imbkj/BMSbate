package Controller.CoBase;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;

import Model.CoBaseModel;
import Model.CoFinanceVATModel;
import Model.PubTradeModel;
import Model.SystLogModel;
import Util.EntitiesComparedUtils;
import Util.GetIpUtil;
import Util.UserInfo;
import Util.pinyin4jUtil;
import bll.CoBase.CoBase_OperateBll;
import bll.CoBase.CoBase_SelectBll;
import bll.CoLatencyClient.CoFinanceVatBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;
import bll.SystemControl.SystLogInfoBll;

public class CoBaseInfo_UpdateController {
	String username = UserInfo.getUsername();
	private CoServiceRequestSelectBll sbll = new CoServiceRequestSelectBll();
	private CoBase_SelectBll cobabll = new CoBase_SelectBll();
	CoLatencyClient_AddBll bll = new CoLatencyClient_AddBll();
	List<PubTradeModel> tradlist;
	List<String> loginlist;
	List<String> loginlist2;
	List<String> loginlist3;
	List<String> loginlist4;
	List<String> loginlist5;
	List<String> models;
	CoBaseModel frommodel = (CoBaseModel) Executions.getCurrent().getArg()
			.get("model");
	private String cobaname = frommodel.getCoba_company();
	private String shoname = frommodel.getCoba_shortname();
	private String sign = "";
	private Date hzbegindate = null;
	// suhongyuan
	private CoFinanceVATModel coFinanceVATModel = new CoFinanceVATModel();
	private CoFinanceVatBll vatBll = new CoFinanceVatBll();
	List<CoFinanceVATModel> list;
	private SystLogInfoBll logBll = new SystLogInfoBll();
	CoFinanceVATModel oldva = new CoFinanceVATModel();
	private String adderss = "";

	public String getAdderss() {
		return adderss;
	}

	public void setAdderss(String adderss) {
		this.adderss = adderss;
	}

	public CoBaseInfo_UpdateController() {

		loginlist = loginlist2 = loginlist3 = loginlist4 = loginlist5 = bll
				.getLoginInfo();
		tradlist = bll.getTradeIndo();
		if (frommodel.getCoba_sign() == 1) {
			sign = "是";
		} else if (frommodel.getCoba_sign() == 0) {
			sign = "否";
		}
		if (frommodel.getCoba_hzqsr() != ""
				&& frommodel.getCoba_hzqsr() != null
				&& !frommodel.getCoba_hzqsr().equals("")) {
			SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
			try {
				hzbegindate = formate.parse(frommodel.getCoba_hzqsr());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Date d = cobabll.getCocoInuredate(frommodel.getCid());
		if (d != null) {
			hzbegindate = d;
		}

		try {
			list = vatBll.getCoFinanceVatDat(frommodel.getCid());
			if (list.size() > 0) {
				coFinanceVATModel = list.get(0);
				setAdderss(coFinanceVATModel.getCfva_reg_add());
				oldva = (CoFinanceVATModel) coFinanceVATModel.clone();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 公司信息修改
	@Command
	public void updateCoBase() throws SQLException {
		String str = isEmploy();
		if (str == "") {

			CoBase_OperateBll addcobasebll = new CoBase_OperateBll();
			String spell = pinyin4jUtil.getPinYinHeadChar(frommodel
					.getCoba_company());
			if (spell != null && !spell.equals("")) {
				frommodel.setCoba_spell(spell.subSequence(0, 1).toString());
			}
			String shortspell = pinyin4jUtil.getPinYinHeadChar(frommodel
					.getCoba_shortname());
			if (shortspell != null && !shortspell.equals("")) {
				frommodel.setCoba_shortspell(shortspell.subSequence(0, 1)
						.toString());
				frommodel.setCoba_namespell(shortspell);
			}
			if (frommodel.getCoba_reg_fund() == null
					|| frommodel.getCoba_reg_fund().equals("")) {
				frommodel.setCoba_reg_fund(null);
			}
			if (sign != null) {
				if (sign.equals("是")) {
					frommodel.setCoba_sign(1);
				} else {
					frommodel.setCoba_sign(0);
				}
			}
			String datestr = null;
			if (hzbegindate != null) {
				datestr = addcobasebll.DatetoSting(hzbegindate);
			}
			frommodel.setCoba_hzqsr(datestr);
			frommodel.setCoba_addname(UserInfo.getUsername());
			int k = addcobasebll.updateCoBaseInfo(frommodel);
			if (k > 0) {
				Messagebox.show("修改成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
				List<CoBaseModel> cobaselist = new ArrayList<CoBaseModel>();
			} else {
				Messagebox.show("修改失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}

			/*
			 * //营改增信息添加 suhongyaun
			 * coFinanceVATModel.setCid(frommodel.getCid());
			 * coFinanceVATModel.setCfva_company(frommodel.getCoba_company());
			 * coFinanceVATModel.setCfva_reg_add(frommodel.getCoba_address());
			 * coFinanceVATModel.setCfva_addname(UserInfo.getUsername());
			 * coFinanceVATModel.setCfva_reg_add(getAdderss());
			 * 
			 * Integer vat; if(list.size()>0){
			 * vat=vatBll.updateCoFinanceVat(coFinanceVATModel);
			 * 
			 * }else{ vat=vatBll.addCoFinanceVat(coFinanceVATModel);
			 * 
			 * 
			 * }
			 * 
			 * HttpServletRequest request = (HttpServletRequest)
			 * Executions.getCurrent().getNativeRequest(); String
			 * ip=GetIpUtil.getIpAddr(request); System.out.println(ip); String
			 * className
			 * =this.getClass().getName().substring(this.getClass().getName
			 * ().lastIndexOf(".")+1); String s=""; SystLogModel m=new
			 * SystLogModel(); m.setCid(coFinanceVATModel.getCid().toString());
			 * if(oldva.getCfva_id()!=null){ try { s =
			 * EntitiesComparedUtils.OldToNewReflect(oldva,coFinanceVATModel);
			 * System.out.println(s); } catch (NoSuchMethodException |
			 * IllegalAccessException | IllegalArgumentException |
			 * InvocationTargetException e) { e.printStackTrace(); } }else{ try
			 * { s = EntitiesComparedUtils.NewReflect(coFinanceVATModel);
			 * System.out.println(s); } catch (NoSuchMethodException |
			 * IllegalAccessException | IllegalArgumentException |
			 * InvocationTargetException e) { e.printStackTrace(); } }
			 * m.setContent(s); m.setAddname(username); m.setIP(ip);
			 * m.setGID(""); m.setClass1(className); logBll.addSystLog(m);
			 */
		} else {
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	private String isEmploy() {
		String str = "";
		if (frommodel.getCoba_company() == null
				|| frommodel.getCoba_company().equals("")) {
			str = "公司名称不能为空";
			return str;
		}
		if (frommodel.getCoba_shortname() == null
				|| frommodel.getCoba_shortname().equals("")) {
			str = "公司简称不能为空";
			return str;
		}
		if (frommodel.getCoba_setuptype() == null
				|| frommodel.getCoba_setuptype().equals("")) {
			str = "机构性质不能为空";
			return str;
		}
		if (frommodel.getCoba_kind() == null
				|| frommodel.getCoba_kind().equals("")) {
			str = "客户企业性质不能为空";
			return str;
		}
		if (frommodel.getCoba_client() == null
				|| frommodel.getCoba_client().equals("")) {
			str = "请选择客服";
			return str;
		}
		if (frommodel.getCoba_clientmanager() == null
				|| frommodel.getCoba_clientmanager().equals("")) {
			str = "请选择客服经理";
			return str;
		}
		if (frommodel.getCoba_manager() == null
				|| frommodel.getCoba_manager().equals("")) {
			str = "请选择部门经理";
			return str;
		}
		if (frommodel.getCoba_reg_fund() != null
				&& !frommodel.getCoba_reg_fund().equals("")) {
			if (isNum(frommodel.getCoba_reg_fund()) == false) {
				str = "注册资金只能是数字";
				return str;
			}
		}
		/*
		if (coFinanceVATModel.getCfva_title() == null
				|| coFinanceVATModel.getCfva_title().equals("")) {
			str = "发票抬头不能为空";
			return str;
		}
		if (coFinanceVATModel.getCfva_contact() == null
				|| coFinanceVATModel.getCfva_contact().equals("")) {
			str = "发票联系人不能为空";
			return str;
		}
		if (coFinanceVATModel.getCfva_contact_tel() == null
				|| coFinanceVATModel.getCfva_contact_tel().equals("")) {
			str = "发票联系人电话不能为空";
			return str;
		}
		if (coFinanceVATModel.getCfva_vat_add() == null
				|| coFinanceVATModel.getCfva_vat_add().equals("")) {
			str = "发票接收地址不能为空";
			return str;
		}
		if (adderss == null || adderss.equals("")) {
			str = "注册地址不能为空";
			return str;
		}
		if (coFinanceVATModel.getCfva_taxpayers() == null
				|| coFinanceVATModel.getCfva_taxpayers().equals("")
				|| coFinanceVATModel.getCfva_taxpayers() == "否"
				|| coFinanceVATModel.getCfva_taxpayers().equals("否")) {
			str = "";
			return str;
		} else {
			if (coFinanceVATModel.getCfva_taxpayers() == "是"
					|| coFinanceVATModel.getCfva_taxpayers().equals("是")) {
				if (coFinanceVATModel.getCfva_tel() == null
						|| coFinanceVATModel.getCfva_tel().equals("")) {
					str = "电话不能为空";
					return str;
				}
				if (coFinanceVATModel.getCfva_number1() == null
						|| coFinanceVATModel.getCfva_number1().equals("")) {
					str = "纳税人识别号不能为空";
					return str;
				}

				if (coFinanceVATModel.getCfva_bank_acc() == null
						|| coFinanceVATModel.getCfva_bank_acc().equals("")) {
					str = "银行账号不能为空";
					return str;
				}
				if (coFinanceVATModel.getCfva_bank() == null
						|| coFinanceVATModel.getCfva_bank().equals("")) {
					str = "开户银行名称(详细到支行)不能为空";
					return str;
				}
			}
		}*/
		return str;
	}

	// //检查公司名称是否已存在
	// @Listen("onChange = #company")
	// public void ifExist(){
	// if(company.getValue()!=null&&!company.getValue().equals(""))
	// {
	// boolean flag=sbll.ifExist(company.getValue());
	// if(flag)
	// {
	// Messagebox.show("公司名称已经存在","提示",Messagebox.OK, Messagebox.INFORMATION);
	// company.setFocus(true);
	// }
	// }
	// }

	// 检查公司简称是否已存在
	// @Listen("onChange = #shortname")
	// public void ifExistShortName(){
	// if(shortname.getValue()!=null&&!shortname.getValue().equals(""))
	// {
	// boolean flag=sbll.ifExistShortName(shortname.getValue());
	// if(flag)
	// {
	// Messagebox.show("公司简称已经存在","提示",Messagebox.OK, Messagebox.INFORMATION);
	// shortname.setFocus(true);
	// }
	// }
	// }

	// 判断字符串是否都是数字
	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	public CoBaseModel getFrommodel() {
		return frommodel;
	}

	public void setFrommodel(CoBaseModel frommodel) {
		this.frommodel = frommodel;
	}

	public void setTradlist(List<PubTradeModel> tradlist) {
		this.tradlist = tradlist;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getHzbegindate() {
		return hzbegindate;
	}

	public void setHzbegindate(Date hzbegindate) {
		this.hzbegindate = hzbegindate;
	}

	public List<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(List<String> loginlist) {
		this.loginlist = loginlist;
	}

	public List<String> getLoginlist2() {
		return loginlist2;
	}

	public void setLoginlist2(List<String> loginlist2) {
		this.loginlist2 = loginlist2;
	}

	public List<String> getLoginlist3() {
		return loginlist3;
	}

	public void setLoginlist3(List<String> loginlist3) {
		this.loginlist3 = loginlist3;
	}

	public List<String> getLoginlist4() {
		return loginlist4;
	}

	public void setLoginlist4(List<String> loginlist4) {
		this.loginlist4 = loginlist4;
	}

	public List<String> getLoginlist5() {
		return loginlist5;
	}

	public void setLoginlist5(List<String> loginlist5) {
		this.loginlist5 = loginlist5;
	}

	public List<PubTradeModel> getTradlist() {
		return tradlist;
	}

	public CoFinanceVATModel getCoFinanceVATModel() {
		return coFinanceVATModel;
	}

	public void setCoFinanceVATModel(CoFinanceVATModel coFinanceVATModel) {
		this.coFinanceVATModel = coFinanceVATModel;
	}

	// 查询公司全称是否已存在
	public boolean ifExist(String company) {
		boolean flag = false;
		CoServiceRequestSelectBll bl = new CoServiceRequestSelectBll();
		if (company != null && !company.equals(cobaname)) {
			flag = bl.ifExist(company);
		}
		return flag;
	}

	// 查询公司简称是否已存在
	public boolean ifExistShortName(String company) {
		boolean flag = false;
		CoServiceRequestSelectBll bl = new CoServiceRequestSelectBll();
		if (company != null && !company.equals(shoname)) {
			flag = bl.ifExistShortName(company);
		}
		return flag;
	}
}
