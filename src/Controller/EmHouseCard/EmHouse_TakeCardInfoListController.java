package Controller.EmHouseCard;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;

import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.EmHouseCard.EmHouse_MakeCardInfoSelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

import Model.CoAgencyLinkmanModel;
import Model.EmHouseTakeCardInfoModel;
import Model.EmbaseModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;

public class EmHouse_TakeCardInfoListController {
	private EmHouse_TakeCardInfoSelectBll bll = new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseTakeCardInfoModel> list = new ArrayList<EmHouseTakeCardInfoModel>();
	private List<String> ownmonthlist = bll.getOwnmonthInfo(
			"distinct(ownmonth) as ownmonth", " order by ownmonth desc",
			"ownmonth");
	private List<String> applist = bll.getOwnmonthInfo(
			"distinct(convert(varchar(6),re_apptime,112)) as appmonth",
			" and re_apptime is not null order by appmonth desc", "appmonth");
	// 获取客服信息
	private EmHouse_MakeCardInfoSelectBll mbll = new EmHouse_MakeCardInfoSelectBll();
	private List<String> clientlist = mbll.getLoginInfo();
	private List<EmHouseTakeCardInfoModel> slist = bll
			.getStateInfo(" and state_type=2 ");
	private Integer zlnum, flnum, yhnum, ftnum;// 助理核收、福利核收、已交银行
	private String strsql = "", clientstr = "", cstr = "";
	private String sid = Executions.getCurrent().getParameter("id").toString();
	private String ifidcard;
	private String cosp_card_caliname;
	private String emba_state;
	private String emba_dept;
	private String contactsate;
	private String card_data_caliname;
	private String bankJc;
	private String sName;

	public EmHouse_TakeCardInfoListController() {
		// 1表示客服页面
		if (sid.equals("1") || sid == "1") {
			strsql = " and a.CID in ( select cid from DataPopedom "
					+ "where log_id=" + UserInfo.getUserid()
					+ " and dat_selected=1 ) " + " and re_state=25";
			sName = "已交客服";
			// + " and state_name in('已交客服','客服签收') ";
			String dep_id = UserInfo.getDepID();
			cstr = " and dep_id=" + dep_id;
			strsql = strsql + cstr;
			// clientstr = strsql;

		}
		// 2表示福利页面
		else if (sid.equals("2") || sid == "2") {
			// strsql = " and state_name in('中心核收','福利核收','已交银行')";
			strsql = " and re_state=8";
			sName = "中心核收";
		}
		// 3表示客服签收页面
		else if (sid.equals("3") || sid == "3") {
			strsql = " and a.CID in ( select cid from DataPopedom "
					+ "where log_id=" + UserInfo.getUserid()
					+ " and dat_selected=1 ) " + " and re_state=13";
			sName = "客服签收";
			// + " and state_name in('客服签收','已交客服')";
			clientstr = strsql;

		}
		// 4表示雇员服务中心页面
		else if (sid.equals("4") || sid == "4") {// 渣打银行银行独立户的数据服务中心不需要处理,1000221743是渣打银行独立的公积金帐号
			strsql = " and re_cgjjno<>'1000221743'" + " and re_state=19";
			sName = "我司待领";
			// + " and state_name in('我司待领','客服核收','退回','福利领卡','中心签收','待收资料')";
			strsql = strsql;
			// +
			// " and re_id not in(select re_id from EmHouseTakeCardInfo where "
			// + " Re_AccountType='独立开户' and Re_State=19)";
			clientstr = strsql;
		} else {
			sName = "我司待领";
			strsql = " and re_state=19";
		}
		if (UserInfo.getDepID().equals("2") || UserInfo.getDepID().equals("6")
				|| UserInfo.getDepID().equals("8")) {
			strsql = strsql + " and dep_id=" + UserInfo.getDepID();
		}
		// strsql+=" and re_state=19";
		list = bll.getEmhouseTakeCardInfo(strsql);
		Integer[] intinfo = bll.getCountInfo();
		zlnum = intinfo[0];
		flnum = intinfo[1];
		yhnum = intinfo[2];
		ftnum = intinfo[3];

	}

	// 打开短信页面
	@Command("openmobile")
	public void openmobile(@BindingParam("a") EmHouseTakeCardInfoModel em)
			throws InterruptedException {
		Map map = new HashMap<>();
		String mobile = bll.getEmba_mobile(em.getGid());
		map.put("mobile", mobile);
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("../Embase/SMS_Add.zul",
				null, map);
		window.doModal();
	}

	// 查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("nametype") String nametype,
			@BindingParam("name") String name,
			@BindingParam("ownmonth") Date ownmonth,
			@BindingParam("appmonth") Date appmonth,
			@BindingParam("ifidcard") String ifidcard,
			@BindingParam("clienttype") String clienttype,
			@BindingParam("client") String client,
			@BindingParam("type") String type,
			@BindingParam("statename") Combobox statename,
			@BindingParam("timetype") String timetype,
			@BindingParam("timevalue") Date timevalue,
			@BindingParam("comtype") String comtype,
			@BindingParam("comname") String comname) {
		String str = "";
		if (!name.equals("") && name != "" && !nametype.equals("")
				&& nametype != "") {
			if (nametype.equals("员工姓名") || nametype == "员工姓名") {
				str = str + " and username='" + name + "'";
			}
			if (nametype.equals("员工编号") || nametype == "员工编号") {
				str = str + " and a.gid='" + name + "'";
			} else if (nametype.equals("身份证号") || nametype == "身份证号") {
				str = str + " and idcard like'%" + name + "%'";
			} else if (nametype.equals("个人公积金号") || nametype == "个人公积金号") {
				str = str + " and re_gjjno like'%" + name + "%'";
			}
		}

		if (!comname.equals("") && comname != "" && !comtype.equals("")
				&& comtype != "") {
			if (comtype.equals("单位简称") || comtype == "单位简称") {
				str = str + " and shortname like'%" + comname + "%'";
			} else if (comtype.equals("单位编号") || comtype == "单位编号") {
				str = str + " and a.cid =" + comname;
			} else if (comtype.equals("单位公积金号") || comtype == "单位公积金号") {
				str = str + " and re_cgjjno like'%" + comname + "%'";
			}
		}

		if (!clienttype.equals("") && clienttype != "" && !client.equals("")
				&& client != "") {
			if (clienttype.equals("客服") || clienttype == "客服") {
				str = str + " and coba_client='" + client + "'";
			} else if (nametype.equals("客服助理") || nametype == "客服助理") {
				str = str + " and Pla_ClientAssistant='" + client + "'";
			} else if (nametype.equals("福利助理") || nametype == "福利助理") {
				str = str + " and Pla_WelfreAssistant='" + client + "'";
			}
		}

		if (!timetype.equals("") && timetype != "" && timevalue != null
				&& !timevalue.equals("")) {
			String ve = datechange(timevalue);
			if (timetype.equals("客服核收资料时间") || timetype == "客服核收资料时间") {
				str = str + " and convert(varchar(10),Pla_clientTime,120)='"
						+ ve + "'";
			} else if (timetype.equals("助理核收资料时间") || timetype == "助理核收资料时间") {
				str = str
						+ " and convert(varchar(10),Pla_ClientAssistantTime,120) ='"
						+ ve + "'";
			} else if (timetype.equals("福利核收资料时间") || timetype == "福利核收资料时间") {
				str = str
						+ " and convert(varchar(10),Gjj_WelfreAssistantTime,120) ='"
						+ ve + "'";
			} else if (timetype.equals("递交银行时间") || timetype == "递交银行时间") {
				str = str + " and convert(varchar(10),Pla_ToBankTime,120) ='"
						+ ve + "'";
			} else if (timetype.equals("福利领卡时间") || timetype == "福利领卡时间") {
				str = str + " and convert(varchar(10),Pla_flTime,120) ='" + ve
						+ "'";
			} else if (timetype.equals("客服领卡时间") || timetype == "客服领卡时间") {
				str = str + " and convert(varchar(10),Pla_ReceiveTime,120) ='"
						+ ve + "'";
			} else if (timetype.equals("员工领卡时间") || timetype == "员工领卡时间") {
				str = str + " and convert(varchar(10),Re_time,120) ='" + ve
						+ "'";
			} else if (timetype.equals("中心签收时间") || timetype == "中心签收时间") {
				str = str + " and convert(varchar(10),pla_receivetime,120) ='"
						+ ve + "'";
			}
		}

		if (ownmonth != null && !ownmonth.equals("")) {
			str = str + " and a.ownmonth=" + changedate(ownmonth);
		}

		if (appmonth != null && !appmonth.equals("")) {
			str = str + " and convert(varchar(6),re_apptime,112)='"
					+ changedate(appmonth) + "'";
		}

		if (!type.equals("") && type != "") {
			if (type.equals("独立开户")) {
				str = str + " and Re_AccountType like '%" + type + "%'";
			} else {
				str = str + " and Re_AccountType !='独立开户'";
			}
		}

		if (statename.getValue() != null && !statename.getValue().equals("")
				&& statename.getValue() != "") {
			str = str + " and re_state="
					+ statename.getSelectedItem().getValue();
		}
		if (ifidcard != null) {
			if (ifidcard.equals("有")) {
				str = str + " and picnum>0";
			} else if (ifidcard.equals("无")) {
				str = str + " and isnull(picnum,0)<=0";
			}
		}
		// 1表示客服页面
		if (sid.equals("1") || sid.equals("3")) {
			str = str + clientstr;
		}
		if (cosp_card_caliname != null && !cosp_card_caliname.equals("")) {
			if (cosp_card_caliname.equals("客服")) {
				str = str + " and cosp_card_caliname like '%"
						+ cosp_card_caliname + "%'";
			} else if (cosp_card_caliname.equals("员工本人")) {
				str = str + " and cosp_card_caliname like '%员工%'";
			} else {
				str = str
						+ " and cosp_card_caliname not like '%客服%'"
						+ " and cosp_card_caliname not like '%员工本人%' and cosp_card_caliname is not null ";
			}
		}

		if (UserInfo.getDepID().equals("2") || UserInfo.getDepID().equals("6")
				|| UserInfo.getDepID().equals("8")) {
			strsql = strsql + " and dep_id=" + UserInfo.getDepID();
		}

		if (emba_state != null && !emba_state.equals("")) {
			if (emba_state.equals("离职")) {
				str = str
						+ " and a.gid in(select gid from embase where emba_state=0)";
			} else {
				str = str
						+ " and a.gid not in(select gid from embase where emba_state=0)";
			}
		}
		if (emba_dept != null && !emba_dept.equals("")) {
			if (emba_dept.contains("全国")) {
				str = str
						+ " and coba_client in(select log_name from login where dep_id=6)";
			} else {
				str = str
						+ " and coba_client in(select log_name from login where dep_id=2)";
			}
		}
		if (contactsate != null && !contactsate.equals("")) {
			str = str + " and re_contactsate='" + contactsate + "'";
		}
		if (card_data_caliname != null && !card_data_caliname.equals("")) {
			if (card_data_caliname.equals("客服")) {
				str = str + " and cosp_card_data_caliname like '%"
						+ card_data_caliname + "%'";
			} else if (card_data_caliname.equals("员工本人")) {
				str = str + " and cosp_card_data_caliname like '%员工%'";
			} else {
				str = str + " and cosp_card_data_caliname not like '%客服%'"
						+ " and cosp_card_data_caliname not like '%员工本人%'"
						+ " and cosp_card_data_caliname is not null ";
			}
		}
		// 服务中心页面
		if (sid.equals("4") || sid == "4") {// 渣打银行银行独立户的数据服务中心不需要处理,1000221743是渣打银行独立的公积金帐号
			str = str + " and re_cgjjno<>'1000221743'";
		}

		// 缴存银行
		if (bankJc != null && !bankJc.equals("")) {
			str += " and cohf_bankjc='" + bankJc + "'";
		}

		strsql = str;
		list = bll.getEmhouseTakeCardInfo(str);
	}

	// 导入数据
	@Command
	public void insertdata() {
		Window window = (Window) Executions.createComponents(
				"HuCard_TakeInfoAdd.zul", null, null);
		window.doModal();
	}

	// 打开联系人信息
	@Command
	public void opencaliname(
			@BindingParam("model") EmHouseTakeCardInfoModel model,
			@BindingParam("type") String type) {
		String val = model.getCosp_card_caliname();
		if (type.equals("2")) {
			val = model.getCosp_card_data_caliname();
		}
		if (val != null || val.contains("联系人")) {
			String a[] = val.split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll.getLinkmanByCid(
						model.getCid(), 1);
				for (int i = 0; i < linklist.size(); i++) {
					if (linklist.get(i).getCali_name().equals(a[1])) {
						cali_id = linklist.get(i).getCali_id();
					}
				}
				if (cali_id != 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("cali_id", String.valueOf(cali_id));
					Window window = (Window) Executions.createComponents(
							"../CoBase/CoBaseLinkMan_Sel.zul", null, map);
					window.doModal();
				}
			}
		}
	}

	// 全选
	@Command
	public void checkall(@BindingParam("ck") Checkbox ck,
			@BindingParam("gd") Grid gd,
			@BindingParam("rowindex") Integer rowindex) {
		Integer activePage = gd.getActivePage();
		Integer startIndex = 0;
		startIndex = activePage * gd.getPageSize();
		if (rowindex == null) {
			rowindex = 16;
		}
		Integer endIndex = startIndex + gd.getPageSize();
		if (ck.isChecked()) {
			for (int i = startIndex; i < endIndex; i++) {
				if (gd.getCell(i, rowindex) != null) {
					Checkbox cb = (Checkbox) gd.getCell(i, rowindex)
							.getChildren().get(0);
					cb.setChecked(ck.isChecked());
				}
			}
		} else {
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, 16) != null) {
					Checkbox cb = (Checkbox) gd.getCell(i, rowindex)
							.getChildren().get(0);
					cb.setChecked(ck.isChecked());
				}
			}
		}
	}

	// 打开批量处理页面
	@Command
	@NotifyChange("list")
	public void openZUL(@BindingParam("gd") Grid gd,
			@BindingParam("url") String url,
			@BindingParam("rowindex") Integer rowindex) {
		List<EmHouseTakeCardInfoModel> maplist = new ArrayList<EmHouseTakeCardInfoModel>();
		int n = gd.getRows().getChildren().size();
		if (rowindex == null) {
			rowindex = 16;
		}
		for (int i = 0; i < n; i++) {
			if (gd.getCell(i, rowindex) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, rowindex).getChildren()
						.get(0);
				if (cb.isChecked() && cb.isVisible()) {
					EmHouseTakeCardInfoModel ml = cb.getValue();
					maplist.add(ml);
				}
			}
		}
		String s = ifsame(maplist);
		if (s.equals("") || s == "") {
			EmHouseTakeCardInfoModel mol = maplist.get(0);
			if (mol.getState_Name().equals("福利领卡")
					|| mol.getState_Name().equals("中心签收")) {
				url = "HuCard_CenterDealAll.zul";
			} else if (mol.getState_Name().equals("我司待领")) {
				url = "HuCard_CenterUpdateAll.zul";
			} else if (mol.getState_Name().equals("待收资料")) {
				url = "HuCard_ClientDealAll.zul";
			}
			Map map = new HashMap<>();
			map.put("list", maplist);
			map.put("sid", sid);
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			list = bll.getEmhouseTakeCardInfo(strsql);
		} else {
			Messagebox.show(s, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 判断选择的数据是否都符合要求
	private String ifsame(List<EmHouseTakeCardInfoModel> maplist) {
		String str = "";
		String bankname = "";// 缴存银行
		Integer stateid = 0;// 状态id
		String accounttype = "";
		if (maplist.size() > 0) {
			for (int i = 0; i < maplist.size(); i++) {
				if (i == 0) {
					if (maplist.get(0) != null) {
						bankname = maplist.get(0).getCohf_banklk() + "";
						stateid = maplist.get(0).getRe_state();
						accounttype = maplist.get(0).getRe_accounttype() + "";
					}
				} else {
					if (!stateid.equals(maplist.get(i).getRe_state())
							&& stateid != maplist.get(i).getRe_state()) {
						str = "不同的领卡状态数据不能做批量处理";
						break;
					} else if (!ifaccountsame(accounttype, maplist.get(i)
							.getRe_accounttype())) {
						str = "独立户与中智户不能一起做批量处理";
						break;
					} else if (!bankname
							.equals(maplist.get(i).getCohf_banklk())
							&& bankname != maplist.get(i).getCohf_banklk()) {
						if (stateid != 11 && stateid != 12) {
							str = "不同的缴存银行不能一起做批量处理";
						}
						break;
					}
				}
			}
		} else {
			str = "请选择数据";
		}
		return str;
	}

	// 判断两个开户类型是否相同
	private boolean ifaccountsame(String firstaccount, String nextaccount) {
		boolean flag = true;
		if (firstaccount.equals("独立开户") || firstaccount == "独立开户") {
			if (!nextaccount.equals("独立开户") && nextaccount != "独立开户") {
				flag = false;
			}
		} else {
			if (nextaccount.equals("独立开户") || nextaccount == "独立开户") {
				flag = false;
			}
		}
		return flag;
	}

	// 导出excel
	@Command("Export")
	public void Export(@BindingParam("gd") Grid gd) throws Exception {
		plyUtil ply = new plyUtil();
		String path = "/../../EmHouseCard/downloadfile/";// 导出保存路径
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename = "领卡信息" + sdf.format(date) + ".xls";// 定义导出文件名称
		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName = "公积金卡领卡信息";// Excel表格名
		// 定义表头
		String[] title = { "序号", "所属月份", "单位公积金号", "公司简称", "个人公积金号", "姓名",
				"身份证号", "卡号", "客服", "缴存银行", "开户类型", "领卡状态", "申报日期", "在职状态",
				"部门", "有无身份证复印件" };
		try {
			// 使用自己写的Excel导出实现类把数据写入Excel
			ExcelService exl = new bll.EmHouseCard.ExcelImpl(file, sheetName,
					title, list);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Filedownload.save(file, "xls");// 导出Excel
		// file.delete();
	}

	// 生成清册数据
	@Command
	public void Exportqingce(@BindingParam("gd") Grid gd,
			@BindingParam("rowindex") Integer rowindex) throws IOException {
		List<EmHouseTakeCardInfoModel> checkedlist = new ArrayList<EmHouseTakeCardInfoModel>();
		int n = gd.getPageSize();
		if (list.size() < gd.getPageSize()) {
			n = list.size();
		}
		String idstr = "";
		if (rowindex == null) {
			rowindex = 16;
		}
		for (int i = 0; i < n; i++) {
			if (gd.getCell(i, rowindex) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, rowindex).getChildren()
						.get(0);
				if (cb.isChecked()) {
					EmHouseTakeCardInfoModel ml = cb.getValue();
					checkedlist.add(ml);
					idstr = idstr + ml.getRe_id() + ",";
				}
			}
		}

		if (idstr.length() > 0) {
			idstr = idstr.substring(0, idstr.length() - 1);
		}
		Integer ok = 0;
		// 判断单位公积金号是否一样(都是中智户就可以导出)
		// 先判断是否都是中智户
		boolean iFCiic = bll.iFCiicCompanyId(idstr);// iFCiic=true表示所有的数据都是中智户
		int isSame = 1;
		if (!iFCiic) {
			isSame = bll.isSameCompanyId(idstr);
			if (isSame > 1) {
				Messagebox.show("单位公积金帐号不同，不能导出", "提示", Messagebox.OK,
						Messagebox.ERROR);
				ok = 1;
			}
			if (isSame == 0) {
				Messagebox.show("没有单位公积金帐号，不能导出", "提示", Messagebox.OK,
						Messagebox.ERROR);
				ok = 1;
			}
		}

		plyUtil ply = new plyUtil();
		String path = "/../../EmHouseCard/file/";
		String paths = "EmHouseCard/downloadfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "qingce.xls";

		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "联名卡清册" + sdf.format(date) + "_"
				+ UserInfo.getUsername() + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		try {
			if (isSame == 1) {
				ExcelService exl = new qingceExcelImpl(solpath, absolutePath
						+ paths + newfilename, checkedlist);
				exl.writeExcel();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		FileOperate.download(paths + newfilename);
	}

	// 生成交接清单
	@Command
	public void Exportqingdan(@BindingParam("gd") Grid gd,
			@BindingParam("rowindex") Integer rowindex) throws IOException {
		List<EmHouseTakeCardInfoModel> checkedlist = new ArrayList<EmHouseTakeCardInfoModel>();
		int n = gd.getRows().getChildren().size();
		String idstr = "";
		if (rowindex == null) {
			rowindex = 16;
		}
		for (int i = 0; i < n; i++) {
			if (gd.getCell(i, rowindex) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, rowindex).getChildren()
						.get(0);
				if (cb.isChecked()) {
					EmHouseTakeCardInfoModel ml = cb.getValue();
					checkedlist.add(ml);
					idstr = idstr + ml.getRe_id() + ",";
				}
			}
		}
		if (idstr.length() > 0) {
			idstr = idstr.substring(0, idstr.length() - 1);
		}
		Integer ok = 0;
		// 判断单位公积金号是否一样
		int isSame = bll.isSameCompanyId(idstr);
		if (isSame > 1) {
			Messagebox.show("单位公积金帐号不同，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		if (isSame == 0) {
			Messagebox.show("没有单位公积金帐号，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		plyUtil ply = new plyUtil();
		String path = "/../../EmHouseCard/file/";
		String paths = "EmHouseCard/downloadfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "qingdant.xls";

		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "联名卡清单" + sdf.format(date) + "_"
				+ UserInfo.getUsername() + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		try {
			if (isSame == 1) {
				ExcelService exl = new qingdanExcelImpl(solpath, absolutePath
						+ paths + newfilename, checkedlist);
				exl.writeExcel();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		if (ok == 0) {
			FileOperate.download(paths + newfilename);
		}
	}

	// 生成交接清单
	@Command
	public void ExportZhaoHang(@BindingParam("gd") Grid gd,
			@BindingParam("rowindex") Integer rowindex) throws IOException {
		List<EmHouseTakeCardInfoModel> checkedlist = new ArrayList<EmHouseTakeCardInfoModel>();
		int n = gd.getRows().getChildren().size();
		String idstr = "";
		if (rowindex == null) {
			rowindex = 16;
		}
		for (int i = 0; i < n; i++) {
			if (gd.getCell(i, rowindex) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, rowindex).getChildren()
						.get(0);
				if (cb.isChecked()) {
					EmHouseTakeCardInfoModel ml = cb.getValue();
					checkedlist.add(ml);
					idstr = idstr + ml.getRe_id() + ",";
				}
			}
		}
		if (idstr.length() > 0) {
			idstr = idstr.substring(0, idstr.length() - 1);
		}
		Integer ok = 0;
		// 判断单位公积金号是否一样
		int isSame = bll.isSameCompanyId(idstr);
		if (isSame > 1) {
			Messagebox.show("单位公积金帐号不同，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		if (ok == 0 && isSame == 0) {
			Messagebox.show("没有单位公积金帐号，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		boolean iFzh = bll.iFZH(idstr);
		if (ok == 0 && !iFzh) {
			Messagebox.show("选择的数据不全部是招行的数据，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		boolean iFSameCompany = bll.iFSameCompany(idstr);
		if (ok == 0 && !iFSameCompany) {
			Messagebox.show("选择的数据公司名称不同，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		plyUtil ply = new plyUtil();
		String path = "/../../EmHouseCard/file/";
		String paths = "EmHouseCard/downloadfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "zh.xls";

		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "招行联名卡清单" + sdf.format(date) + "_"
				+ UserInfo.getUsername() + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		try {
			if (isSame == 1) {
				ExcelService exl = new zhExcelImpl(solpath, absolutePath
						+ paths + newfilename, checkedlist);
				exl.writeExcel();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		if (ok == 0) {
			FileOperate.download(paths + newfilename);
		}
	}

	// 生成交接清单
	@Command
	public void ExportJianhang(@BindingParam("gd") Grid gd,
			@BindingParam("rowindex") Integer rowindex) throws IOException {
		List<EmHouseTakeCardInfoModel> checkedlist = new ArrayList<EmHouseTakeCardInfoModel>();
		int n = gd.getRows().getChildren().size();
		String idstr = "";
		if (rowindex == null) {
			rowindex = 16;
		}
		for (int i = 0; i < n; i++) {
			if (gd.getCell(i, rowindex) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, rowindex).getChildren()
						.get(0);
				if (cb.isChecked()) {
					EmHouseTakeCardInfoModel ml = cb.getValue();
					checkedlist.add(ml);
					idstr = idstr + ml.getRe_id() + ",";
				}
			}
		}
		if (idstr.length() > 0) {
			idstr = idstr.substring(0, idstr.length() - 1);
		}
		Integer ok = 0;
		// 判断单位公积金号是否一样
		int isSame = bll.isSameCompanyId(idstr);
		if (isSame > 1) {
			Messagebox.show("单位公积金帐号不同，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		if (ok == 0 && isSame == 0) {
			Messagebox.show("没有单位公积金帐号，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		boolean iFzh = bll.iFJH(idstr);
		if (ok == 0 && !iFzh) {
			Messagebox.show("选择的数据不全部是建行的数据，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		boolean iFSameCompany = bll.iFSameCompany(idstr);
		if (ok == 0 && !iFSameCompany) {
			Messagebox.show("选择的数据公司名称不同，不能导出", "提示", Messagebox.OK,
					Messagebox.ERROR);
			ok = 1;
		}
		plyUtil ply = new plyUtil();
		String path = "/../../EmHouseCard/file/";
		String paths = "EmHouseCard/downloadfile/";
		String absolutePath = FileOperate.getAbsolutePath();
		String filename = "jh.xls";

		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String newfilename = "建行联名卡清册" + sdf.format(date) + "_"
				+ UserInfo.getUsername() + ".xls";
		// 获取绝对路径
		String solpath = ply.getAbsolutePath(path, filename, this);// 获取模板路径
		try {
			if (isSame == 1) {
				ExcelService exl = new jhExcelImpl(solpath, absolutePath
						+ paths + newfilename, checkedlist);
				exl.writeExcel();
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		if (ok == 0) {
			FileOperate.download(paths + newfilename);
		}
	}

	// 打开编辑、退回页面
	@Command
	@NotifyChange("list")
	public void openzulEdit(
			@BindingParam("model") EmHouseTakeCardInfoModel model,
			@BindingParam("url") String url) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("daid", model.getRe_id() + "");
		map.put("id", model.getRe_taprid() + "");
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		list = bll.getEmhouseTakeCardInfo(strsql);
	}

	// 打开领卡详细信息页面
	@Command
	public void opendetail(@BindingParam("model") EmHouseTakeCardInfoModel model) {
		model.setList(bll
				.getEmhouseCardBackInfo("  and backlei=2 and backleiname='领卡' and Back_CardId="
						+ model.getRe_id()));

		Map map = new HashMap<>();
		map.put("model", model);
		Window window = (Window) Executions.createComponents(
				"HuCard_TakeCardDetailInfo.zul", null, map);
		window.doModal();
	}

	// 打开图片页面
	@Command("openempic")
	public void openempic(@BindingParam("a") EmHouseTakeCardInfoModel em) {
		Map map = new HashMap<>();
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("../Embase/EmPic_Up.zul",
				null, map);
		window.doModal();
	}

	// 修改联系状态
	@Command
	public void changecontactsate(
			@BindingParam("model") EmHouseTakeCardInfoModel em) {
		EmHouse_TakeCardInfoOperateBll obll = new EmHouse_TakeCardInfoOperateBll();
		String ss = ",re_contactsate='" + em.getRe_contactsate() + "'";
		obll.updateTakeCardInfo(em.getRe_id(), ss);
	}

	public List<EmHouseTakeCardInfoModel> getList() {
		return list;
	}

	public void setList(List<EmHouseTakeCardInfoModel> list) {
		this.list = list;
	}

	public List<String> getOwnmonthlist() {
		return ownmonthlist;
	}

	public void setOwnmonthlist(List<String> ownmonthlist) {
		this.ownmonthlist = ownmonthlist;
	}

	public List<String> getApplist() {
		return applist;
	}

	public void setApplist(List<String> applist) {
		this.applist = applist;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

	public List<EmHouseTakeCardInfoModel> getSlist() {
		return slist;
	}

	public void setSlist(List<EmHouseTakeCardInfoModel> slist) {
		this.slist = slist;
	}

	private String datechange(Date d) {
		String date = "";
		SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd");
		date = time.format(d);
		return date;
	}

	public Integer getZlnum() {
		return zlnum;
	}

	public void setZlnum(Integer zlnum) {
		this.zlnum = zlnum;
	}

	public Integer getFlnum() {
		return flnum;
	}

	public void setFlnum(Integer flnum) {
		this.flnum = flnum;
	}

	public Integer getYhnum() {
		return yhnum;
	}

	public void setYhnum(Integer yhnum) {
		this.yhnum = yhnum;
	}

	public Integer getFtnum() {
		return ftnum;
	}

	public void setFtnum(Integer ftnum) {
		this.ftnum = ftnum;
	}

	public String getIfidcard() {
		return ifidcard;
	}

	public void setIfidcard(String ifidcard) {
		this.ifidcard = ifidcard;
	}

	public String getCosp_card_caliname() {
		return cosp_card_caliname;
	}

	public void setCosp_card_caliname(String cosp_card_caliname) {
		this.cosp_card_caliname = cosp_card_caliname;
	}

	public String getEmba_state() {
		return emba_state;
	}

	public void setEmba_state(String emba_state) {
		this.emba_state = emba_state;
	}

	public String getEmba_dept() {
		return emba_dept;
	}

	public void setEmba_dept(String emba_dept) {
		this.emba_dept = emba_dept;
	}

	public String getContactsate() {
		return contactsate;
	}

	public void setContactsate(String contactsate) {
		this.contactsate = contactsate;
	}

	public String getCard_data_caliname() {
		return card_data_caliname;
	}

	public void setCard_data_caliname(String card_data_caliname) {
		this.card_data_caliname = card_data_caliname;
	}

	private String changedate(Date d) {
		String dateString = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if (d != null && !d.equals("")) {
			dateString = formatter.format(d);
		}
		return dateString;
	}

	public String getBankJc() {
		return bankJc;
	}

	public void setBankJc(String bankJc) {
		this.bankJc = bankJc;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

}
