package Controller.EmSheBaocard;

import java.io.File;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.omg.PortableInterceptor.INACTIVE;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.ExcelService;

import Model.CoAgencyLinkmanModel;
import Model.EmHouseTakeCardInfoModel;
import Model.EmShebaoCardInfoModel;
import Model.EmbaseModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;
import bll.CoBase.CoBaseLinkMan_SelectBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;
import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

public class Sbcd_EditListController {
	private EmShebaoCardInfoSelectBll bll = new EmShebaoCardInfoSelectBll();
	private List<EmShebaoCardInfoModel> list = new ArrayList<EmShebaoCardInfoModel>();
	private List<EmShebaoCardInfoModel> statelist = bll.getStateList("");
	private String gid, sbcd_name, idcard, sbcd_computerid, cid, company,
			sbcd_comno;
	private List<EmShebaoCardInfoModel> checkedlist = new ArrayList<EmShebaoCardInfoModel>();
	private String statename = "", sbcd_content, sbcd_bank;
	private Integer sbcd_bankdocid;
	private String url = "../EmSheBaocard/Sbcd_CenterDealAll.zul";
	private List<String> branbanklist = bll.getBankBranchInfoList();
	// 获取操作者的标志
	private String oper = Executions.getCurrent().getParameter("oper")
			.toString();// 1、客服；2、中心；3、后道
	private String sqlstr = "";
	private String blclass = "", cosp_card_caliname = "",
			cosp_bsal_caliname = "", branbank = "", client = "";
	private List<String> addlist = bll.getAddList();
	private String addname = "";
	private List<String> clientlist = bll.getClientsList();
	private String operatetype = "中心签收时间";
	private String clientstr = "";

	public Sbcd_EditListController() {
		if (oper != null) {
			if (oper.equals("1"))// 客服
			{
				sqlstr = sqlstr + " and sbcd_stateid in(6,7)";// sbcd_stateid:6、转交客服；7、客服签收；
				clientstr = " and coba_client in(select log_name from Login "
						+ " where dep_id in(select dep_id from Login where log_name='"
						+ UserInfo.getUsername() + "'))";// sbcd_stateid:6、转交客服；7、客服签收；
			} else if (oper.equals("2"))// 中心
			{
				sqlstr = sqlstr+ " and datediff(d,sbcd_addtime,getdate())<100 and sbcd_stateid in(4,5,9,12,13,14,16,17,18)";// sbcd_stateid:4、福利领卡；5、中心签收；12、客服核收；9、退回
			} else if (oper.equals("3"))// 后道
			{
				sqlstr = sqlstr + " and sbcd_stateid in(1,2,3,15)";// sbcd_stateid:1、中心核收；2、福利核收；3、已交银行
			}
		}
		sqlstr = sqlstr + clientstr;
		list = bll.getEmShebaoCardInfoList(sqlstr);
	}

	// 打开处理页面
	@Command
	@NotifyChange("list")
	public void openzul(@BindingParam("model") EmShebaoCardInfoModel model) {
		String url = "";
		url = getUrl(model);
		Map map = new HashMap<>();
		map.put("daid", model.getSbcd_id() + "");
		map.put("id", model.getSbcd_tarpid() + "");
		if (url != null && !url.equals("")) {
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			list = bll.getEmShebaoCardInfoList(sqlstr);
		} else {
			Messagebox
					.show("该状态不能做任何操作", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 状态为客服核收时：服务中心打开处理页面
	@Command
	@NotifyChange("list")
	public void opencenter(@BindingParam("model") EmShebaoCardInfoModel model) {
		String url = "Sbcd_AddAgain.zul";
		Map map = new HashMap<>();
		map.put("daid", model.getSbcd_id() + "");
		map.put("id", model.getSbcd_tarpid() + "");
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
		list = bll.getEmShebaoCardInfoList(sqlstr);
	}

	@Command
	public void ExportOut() {
		Window window = (Window) Executions.createComponents(
				"../EmSheBaocard/Emba_InfoOut.zul", null, null);
		window.doModal();
	}

	// 全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox ck,
			@BindingParam("indexnum") Integer indexnum) {
		Integer cellindex = 14;
		if (indexnum != null) {
			cellindex = indexnum;
		}
		if (checkedlist.size() > 0) {
			checkedlist.clear();
		}
		Integer activePage = gd.getActivePage();
		Integer startIndex = 0;
		startIndex = activePage * gd.getPageSize();

		Integer endIndex = startIndex + gd.getPageSize();
		if (ck.isChecked()) {
			for (int i = startIndex; i < endIndex; i++) {
				if (gd.getCell(i, cellindex) != null) {
					Checkbox cb = (Checkbox) gd.getCell(i, cellindex)
							.getChildren().get(0);
					cb.setChecked(ck.isChecked());
					if (cb.isChecked()) {
						EmShebaoCardInfoModel m = cb.getValue();
						checkedlist.add(m);
					} else {

					}
				}
			}
		} else {
			for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
				if (gd.getCell(i, cellindex) != null) {
					Checkbox cb = (Checkbox) gd.getCell(i, cellindex)
							.getChildren().get(0);
					cb.setChecked(ck.isChecked());
					if (cb.isChecked()) {
						EmShebaoCardInfoModel m = cb.getValue();
						checkedlist.add(m);
					} else {

					}
				}
			}
		}
	}

	// 单选
	@Command
	public void checksingle(@BindingParam("gd") Grid gd,
			@BindingParam("indexnum") Integer indexnum) {
		if (checkedlist.size() > 0) {
			checkedlist.clear();
		}
		Integer cellindex = 14;
		if (indexnum != null) {
			cellindex = indexnum;
		}
		Integer num = gd.getRows().getChildren().size();

		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, cellindex) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, cellindex).getChildren()
						.get(0);
				if (cb.isChecked()) {
					EmShebaoCardInfoModel m = cb.getValue();
					checkedlist.add(m);
				} else {

				}
			}
		}
	}

	// 打开批量处理页面
	// operater表示操作者（客服：client,服务中心:center,后道:fl）
	@Command
	@NotifyChange("list")
	public void editall(@BindingParam("gd") Grid gd,
			@BindingParam("operater") String operater) {
		String str = ifSatisfy(checkedlist, operater);
		if (str == "") {
			Map map = new HashMap<>();
			map.put("list", checkedlist);
			map.put("statename", statename);
			map.put("sbcd_content", sbcd_content);
			if (sbcd_bankdocid == null || sbcd_bankdocid.equals("")) {
				sbcd_bankdocid = 0;
			}
			map.put("docid", sbcd_bankdocid + "");
			Window window = (Window) Executions
					.createComponents(url, null, map);
			window.doModal();
			list = bll.getEmShebaoCardInfoList(sqlstr);
		} else {
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
		if (checkedlist.size() > 0) {
			checkedlist.clear();
		}
	}

	// 判断选择的数据是否符合批量处理
	private String ifSatisfy(List<EmShebaoCardInfoModel> list, String operater) {
		String str = "";
		statename = "";
		sbcd_content = "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					statename = list.get(0).getCdst_statename();
					sbcd_content = list.get(0).getSbcd_content();
					sbcd_bank = list.get(0).getSbcd_bank();
					sbcd_bankdocid = list.get(0).getSbcd_bankdocid();
				} else {
					if (sbcd_content != null
							&& list.get(i).getCdst_statename() != null) {
						if (!statename.equals(list.get(i).getCdst_statename())) {
							str = "选择的制卡状态不同，不能一起做批量处理";
							break;
						}
					}
					if (sbcd_content != null
							&& list.get(i).getSbcd_content() != null) {
						if (!sbcd_content.equals(list.get(i).getSbcd_content())) {
							str = "选择的制卡类型不同，不能一起做批量处理";
							break;
						}
					}
					if (operater.equals("fl")) {
						if (sbcd_content != null
								&& list.get(i).getSbcd_bank() != null) {
							if (!sbcd_bank.equals(list.get(i).getSbcd_bank())) {
								if (!sbcd_bank.equals(list.get(i)
										.getSbcd_bank())) {
									str = "选择的制卡银行不同，不能一起做批量处理";
									break;
								}
							}
						}
					}
				}
			}
		} else {
			str = "请选择数据";
		}
		if (str == null || str.equals("")) {
			if (statename != null && !statename.equals("")) {
				// 操作者是服务中心
				if (operater.equals("center")) {// 4,5,9,12,13,14,16
					if (!statename.equals("客服核收")) {
						if (!statename.equals("福利领卡")
								&& !statename.equals("中心签收")
								&& !statename.equals("待制卡")
								&& !statename.equals("中心制卡")
								&& !statename.equals("中心已交银行")
								&& !statename.equals("待盖章")
								&& !statename.equals("已打单")) {
							str = "你无权操作该状态的数据";
						}
					} else {
						str = "该状态不能做批量处理";
					}
				} else if (operater.equals("client")) {
					if (!statename.equals("转交客服") && !statename.equals("客服签收")) {
						str = "你无权操作该状态的数据";
					}
				} else if (operater.equals("fl")) {
					if (!statename.equals("中心核收") && !statename.equals("福利核收")
							&& !statename.equals("已交银行")
							&& !statename.equals("待打单")) {
						str = "你无权操作该状态的数据";
					} else {
						url = "../EmSheBaocard/Sbcd_FlDealAll.zul";
					}
				} else {
					str = "你无权操作该状态的数据";
				}
			} else {
				str = "你无权操作该状态的数据";
			}
		}
		return str;
	}

	// 导出数据
	@Command
	public void Export(HttpServletResponse response, @BindingParam("gd") Grid gd)
			throws Exception {
		if (checkedlist.size() > 0) {
			checkedlist.clear();
		}
		Integer num = gd.getRows().getChildren().size();
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 14) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, 14).getChildren().get(0);
				if (cb.isChecked()) {
					EmShebaoCardInfoModel m = cb.getValue();
					checkedlist.add(m);
				} else {

				}
			}
		}
		String str = ifSatisfyOutExcel(checkedlist);
		if (str == null || str.equals("")) {
			plyUtil ply = new plyUtil();
			String path = "/../../EmSheBaocard/file/";
			String paths = "EmSheBaocard/downloadfile/";
			String absolutePath = FileOperate.getAbsolutePath();
			String filename = "存量.xls";
			if (sbcd_content != null && sbcd_content.equals("新增")) {
				filename = "新增.xls";
			}
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String newfilename = "(" + sbcd_content + ")社保卡信息"
					+ sdf.format(date) + ".xls";
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
				if (sbcd_content != null && sbcd_content.equals("新增")) {
					ExcelService exl = new newExcelImpl(solpath, absolutePath
							+ paths + newfilename, checkedlist);
					exl.writeExcel();
				} else {
					ExcelService exl = new CunExcelImpl(solpath, absolutePath
							+ paths + newfilename, checkedlist);
					exl.writeExcel();
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			FileOperate.download(paths + newfilename);
		} else {
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 中心导出数据
	@Command
	public void Exportcenter(HttpServletResponse response,
			@BindingParam("gd") Grid gd, @BindingParam("ck") Checkbox ck,
			@BindingParam("indexnum") Integer indexnum) throws Exception {
		Integer cellindex = 14;
		if (indexnum != null) {
			cellindex = indexnum;
		}
		if (checkedlist.size() > 0) {
			checkedlist.clear();
		}
		Integer num = gd.getRows().getChildren().size();
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, cellindex) != null) {
				Checkbox cb = (Checkbox) gd.getCell(i, cellindex).getChildren()
						.get(0);
				if (cb.isChecked()) {
					EmShebaoCardInfoModel m = cb.getValue();
					checkedlist.add(m);
				} else {

				}
			}
		}
		if (checkedlist.size() <= 0) {
			checkedlist = list;
		}
		String str = ifSatisfyOutExcel(checkedlist);
		if (str == null || str.equals("")) {
			plyUtil ply = new plyUtil();
			String path = "/../../EmSheBaocard/file/";
			String paths = "EmSheBaocard/downloadfile/";
			String absolutePath = FileOperate.getAbsolutePath();
			String filename = "存量c.xls";
			if (sbcd_content != null && sbcd_content.equals("新增")) {
				filename = "新增.xls";
			}
			// 创建当前日子
			Date date = new Date();
			// 格式化日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			// 格式化日期(产生文件名)
			String newfilename = "(" + sbcd_content + ")社保卡信息"
					+ sdf.format(date) + ".xls";
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
				if (sbcd_content != null && sbcd_content.equals("新增")) {
					ExcelService exl = new newExcelImpl(solpath, absolutePath
							+ paths + newfilename, checkedlist);
					exl.writeExcel();
				} else {
					ExcelService exl = new CunExcelCenterImpl(solpath,
							absolutePath + paths + newfilename, checkedlist);
					exl.writeExcel();
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			FileOperate.download(paths + newfilename);
		} else {
			Messagebox.show(str, "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 判断选择的数据是否一起导出
	private String ifSatisfyOutExcel(List<EmShebaoCardInfoModel> list) {
		String str = "";
		String idstr = "";
		statename = "";
		sbcd_content = "";
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i == 0) {
					statename = list.get(0).getCdst_statename();
					sbcd_content = list.get(0).getSbcd_content();
				}
				idstr = idstr + list.get(0).getSbcd_id() + ",";
			}
			if (idstr.length() > 0) {
				idstr = idstr.substring(0, idstr.length() - 1);
				boolean ifSameClass = bll.ifSameClass(idstr);// ifSameClass=true表示选择的数据办理类型相同
				if (!ifSameClass) {
					str = "选择的数据办理类型不相同，不能导出";
				}
			}
		} else {
			str = "请选择数据";
		}
		return str;
	}

	// 根据状态抬出不同的url
	private String getUrl(EmShebaoCardInfoModel model) {
		String url = "";
		// 中心核收时弹出福利核收页面
		if (model.getCdst_statename().equals("中心核收")) {
			url = "/EmSheBaocard/Sbcd_flTakeDataInfo.zul";
		} else if (model.getCdst_statename().equals("福利核收")) {
			url = "/EmSheBaocard/Sbcd_FlUpBank.zul";
		} else if (model.getCdst_statename().equals("已交银行")) {
			url = "/EmSheBaocard/Sbcd_FlTakeCard.zul";
		} else if (model.getCdst_statename().equals("福利领卡")) {
			url = "/EmSheBaocard/Sbcd_CenterTakeCard.zul";
		} else if (model.getCdst_statename().equals("中心签收")) {
			url = "/EmSheBaocard/Sbcd_IfToClient.zul";
		} else if (model.getCdst_statename().equals("转交客服")) {
			url = "/EmSheBaocard/Sbcd_ClientTakeCard.zul";
		} else if (model.getCdst_statename().equals("客服签收")) {
			url = "/EmSheBaocard/Sbcd_ClientSendcard.zul";
		} else if (model.getCdst_statename().equals("退回")) {
			url = "/EmSheBaocard/Sbcd_AddAgain.zul";
		} else if (model.getCdst_statename().equals("待打单")) {
			url = "/EmSheBaocard/Sbcd_FlPrint.zul";
		} else if (model.getCdst_statename().equals("已打单")) {
			url = "/EmSheBaocard/Sbcd_CenterStamp.zul";
		} else if (model.getCdst_statename().equals("待制卡")) {
			url = "/EmSheBaocard/Sbcd_centerMakeCard.zul";
		} else if (model.getCdst_statename().equals("中心制卡")) {
			url = "/EmSheBaocard/Sbcd_CenterUpBank.zul";
		} else if (model.getCdst_statename().equals("中心已交银行")) {
			url = "/EmSheBaocard/Sbcd_CenterTakeCard.zul";
		} else if (model.getCdst_statename().equals("待盖章")) {
			url = "/EmSheBaocard/Sbcd_CenterUpData.zul";
		}
		return url;
	}

	// 打开图片页面
	@Command("openempic")
	public void openempic(@BindingParam("a") EmShebaoCardInfoModel em) {
		Map map = new HashMap<>();
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("../Embase/EmPic_Up.zul",
				null, map);
		window.doModal();
	}

	// 查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("sbcdstate") Combobox sbcdstate,
			@BindingParam("operatetime") Datebox operatetime) {
		String sql = "";
		if (gid != null && !gid.equals("")) {
			sql = sql + " and a.gid=" + gid;
		}
		if (sbcd_name != null && !sbcd_name.equals("")) {
			sql = sql + " and sbcd_name='" + sbcd_name + "'";
		}
		if (idcard != null && !idcard.equals("")) {
			sql = sql + " and sbcd_idcard='" + idcard + "'";
		}
		if (sbcd_computerid != null && !sbcd_computerid.equals("")) {
			sql = sql + " and sbcd_computerid='" + sbcd_computerid + "'";
		}
		if (cid != null && !cid.equals("")) {
			sql = sql + " and a.cid='" + cid + "'";
		}
		if (company != null && !company.equals("")) {
			sql = sql + " and (coba_company='" + company
					+ "' or coba_shortname='" + company + "')";
		}
		if (sbcd_comno != null && !sbcd_comno.equals("")) {
			sql = sql + " and sbcd_sbnumber='" + sbcd_comno + "'";
		}
		if (sbcdstate.getValue() != null && !sbcdstate.getValue().equals("")) {
			sql = sql + " and sbcd_stateid='"
					+ sbcdstate.getSelectedItem().getValue() + "'";
		}
		if (blclass != null && !blclass.equals("")) {
			sql = sql + " and sbcd_content='" + blclass + "'";
		}
		if (cosp_card_caliname != null && !cosp_card_caliname.equals("")) {
			if (cosp_card_caliname.equals("客服")) {
				sql = sql + " and cosp_card_caliname='" + cosp_card_caliname
						+ "'";
			} else {
				sql = sql + " and cosp_card_caliname<>'客服'";
			}
		}
		if (cosp_bsal_caliname != null && !cosp_bsal_caliname.equals("")) {
			if (cosp_bsal_caliname.equals("客服")) {
				sql = sql + " and cosp_bsal_caliname='" + cosp_bsal_caliname
						+ "'";
			} else {
				sql = sql + " and cosp_bsal_caliname<>'客服'";
			}
		}
		if (branbank != null && !branbank.equals("")) {
			sql = sql + " and sbcd_upbankname='" + branbank + "'";
		}
		if (addname != null && !addname.equals("")) {
			sql = sql + " and sbcd_addname like '" + addname + "'";
		}
		if (client != null && !client.equals("")) {
			sql = sql + " and coba_client = '" + client + "'";
		}
		if (operatetype != null && operatetime != null) {
			if (operatetype.contains("制卡")) {
				if (operatetime.getValue() != null) {
					sql = sql
							+ " and convert(varchar(10),sbcd_centermaketime,120)='"
							+ DateToStr(operatetime.getValue()) + "'";
				}
			} else if (operatetype.contains("银行")) {
				if (operatetime.getValue() != null) {
					sql = sql
							+ " and convert(varchar(10),sbcd_tobanktime,120)='"
							+ DateToStr(operatetime.getValue()) + "'";
				}
			} else if (operatetype.contains("签收")) {
				if (operatetime.getValue() != null) {
					sql = sql
							+ " and convert(varchar(10),sbcd_centertaketime,120)='"
							+ DateToStr(operatetime.getValue()) + "'";
				}
			}
		}
		sql = sql + clientstr;
		list = bll.getEmShebaoCardInfoList(sql);
	}

	// 打开详细
	@Command
	public void datail(@BindingParam("model") EmShebaoCardInfoModel model) {
		Map map = new HashMap<>();
		map.put("m", model);
		Window window = (Window) Executions.createComponents(
				"/EmSheBaocard/Sbcd_DetailInfo.zul", null, map);
		window.doModal();
	}
	
	//打开修改状态页面
	@Command
	public void editstate(@BindingParam("model") EmShebaoCardInfoModel model) {
		Map map = new HashMap<>();
		map.put("m", model);
		Window window = (Window) Executions.createComponents(
				"/EmSheBaocard/Sbcd_EditState.zul", null, map);
		window.doModal();
	}

	// 打开取消页面
	@Command
	public void cancel(@BindingParam("model") EmShebaoCardInfoModel model) {
		Map map = new HashMap<>();
		map.put("model", model);
		map.put("flag", "0");
		Window window = (Window) Executions.createComponents(
				"/EmSheBaocard/Sbcd_Cancel.zul", null, map);
		window.doModal();
	}

	// 打开短信页面
	@Command("openmobile")
	public void openmobile(@BindingParam("a") EmShebaoCardInfoModel em)
			throws InterruptedException {
		EmHouse_TakeCardInfoSelectBll hsbll = new EmHouse_TakeCardInfoSelectBll();
		Map map = new HashMap<>();
		String mobile = hsbll.getEmba_mobile(em.getGid());
		map.put("mobile", mobile);
		map.put("gid", em.getGid());
		Window window;
		window = (Window) Executions.createComponents("../Embase/SMS_Add.zul",
				null, map);
		window.doModal();
	}

	// 打开联系人信息
	@Command
	public void opencaliname(@BindingParam("model") EmShebaoCardInfoModel model) {
		String val = model.getCosp_card_caliname();
		if (val != null || val.contains("联系人")) {
			String a[] = val.split("—");
			if (a.length > 1) {
				Integer cali_id = 0;
				CoBaseLinkMan_SelectBll lmBll = new CoBaseLinkMan_SelectBll();
				List<CoAgencyLinkmanModel> linklist = lmBll
						.getLinkmanByCid(model.getCid(),1);
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

	public List<EmShebaoCardInfoModel> getList() {
		return list;
	}

	public void setList(List<EmShebaoCardInfoModel> list) {
		this.list = list;
	}

	public List<EmShebaoCardInfoModel> getStatelist() {
		return statelist;
	}

	public void setStatelist(List<EmShebaoCardInfoModel> statelist) {
		this.statelist = statelist;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getSbcd_name() {
		return sbcd_name;
	}

	public void setSbcd_name(String sbcd_name) {
		this.sbcd_name = sbcd_name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getSbcd_computerid() {
		return sbcd_computerid;
	}

	public void setSbcd_computerid(String sbcd_computerid) {
		this.sbcd_computerid = sbcd_computerid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSbcd_comno() {
		return sbcd_comno;
	}

	public void setSbcd_comno(String sbcd_comno) {
		this.sbcd_comno = sbcd_comno;
	}

	public List<String> getBranbanklist() {
		return branbanklist;
	}

	public void setBranbanklist(List<String> branbanklist) {
		this.branbanklist = branbanklist;
	}

	public String getBlclass() {
		return blclass;
	}

	public void setBlclass(String blclass) {
		this.blclass = blclass;
	}

	public String getCosp_card_caliname() {
		return cosp_card_caliname;
	}

	public void setCosp_card_caliname(String cosp_card_caliname) {
		this.cosp_card_caliname = cosp_card_caliname;
	}

	public String getCosp_bsal_caliname() {
		return cosp_bsal_caliname;
	}

	public void setCosp_bsal_caliname(String cosp_bsal_caliname) {
		this.cosp_bsal_caliname = cosp_bsal_caliname;
	}

	public String getBranbank() {
		return branbank;
	}

	public void setBranbank(String branbank) {
		this.branbank = branbank;
	}

	public List<String> getAddlist() {
		return addlist;
	}

	public void setAddlist(List<String> addlist) {
		this.addlist = addlist;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	public List<String> getClientlist() {
		return clientlist;
	}

	public void setClientlist(List<String> clientlist) {
		this.clientlist = clientlist;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getOperatetype() {
		return operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		if (date != null) {
			str = format.format(date);
		}
		return str;
	}
}
