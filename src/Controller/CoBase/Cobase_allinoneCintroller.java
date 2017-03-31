package Controller.CoBase;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.Allinone_ListModel;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoOfferModel;
import Model.EmHouseUpdateModel;
import Model.EmShebaoSZSIFileModel;
import Model.EmShebaoUpdateModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.CoBase.CoBase_AllinoneBll;
import bll.CoBase.CoBase_SelectBll;
import bll.EmSheBao.EmSheBao_DSelectBll;
import bll.SocialInsurance.Algorithm_InfoBll;

public class Cobase_allinoneCintroller {

	Integer cid = 0;
	Integer coco_id = 0;
	String sbhjtype = "";
	String gjjhjtype = "";
	Integer single = 3;
	Integer singlegjj = 3;

	private List<CoCompactModel> compactList = new ListModelList<>();
	private CoCompactModel compactModel = new CoCompactModel();
	private List<CoOfferModel> coofferList;
	private List<CoOfferModel> scoofferList = new ListModelList<>();

	CoBase_AllinoneBll cbll = new CoBase_AllinoneBll();

	Allinone_ListModel model = new Allinone_ListModel();

	private ListModelList<Allinone_ListModel> house_listcount;// 显示公积金合计

	private ListModelList<Allinone_ListModel> sb_listcount;// 显示社保数据合计

	private List<String> hjtypelist = new ListModelList<>();

	private ListModelList<Allinone_ListModel> coglist_list;// 显示所选服务类型

	private ListModelList<Allinone_ListModel> sb_list;// 显示社保数据

	private ListModelList<Allinone_ListModel> house_list;// 显示住房数据

	private ListModelList<Allinone_ListModel> emcompact_list;// 显示劳动合同数据

	private ListModelList<Allinone_ListModel> wt_list;// 显示委托出数据

	private ListModelList<Allinone_ListModel> gz_list;// 显示工资数据

	private ListModelList<Allinone_ListModel> file_list;// 显示档案数据

	private ListModelList<Allinone_ListModel> tj_list;// 显示体检数据

	private ListModelList<Allinone_ListModel> card_list;// 显示社保卡数据

	private ListModelList<Allinone_ListModel> hj_list;// 显示户籍数据

	private ListModelList<Allinone_ListModel> gzk_list;// 显示公积金卡制卡数据

	private ListModelList<Allinone_ListModel> glk_list;// 显示公积金卡领卡数据

	private ListModelList<Allinone_ListModel> sy_list;// 显示商保数据

	private ListModelList<Allinone_ListModel> wt_listcount; // 委托外地数据

	private boolean visCountSB = false; // 是否显示社保计算按钮

	public Cobase_allinoneCintroller() {
		try {
			cid = Integer.valueOf(Executions.getCurrent().getArg().get("cid")
					.toString());
			if (Executions.getCurrent().getArg().get("coco_id") != null) {
				coco_id = Integer.valueOf(Executions.getCurrent().getArg()
						.get("coco_id").toString());
			}

			// 判断用户是否为it部
			if ("3".equals(UserInfo.getDepID())) {
				visCountSB = true;
			}
			init();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox
					.show("页面加载出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		}

	}

	/**
	 * 初始化
	 * 
	 */
	public void init() {
		CoBase_SelectBll bll = new CoBase_SelectBll();
		try {
			setCompactList(bll.getcompactList(cid));
			compactList.add(0, new CoCompactModel());
			if (coco_id > 0) {
				setCoofferList(bll.getcoofferList(" and b.coco_id=" + coco_id));
			} else {
				setCoofferList(bll.getcoofferList(" and b.cid=" + cid));
			}

			search();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			loaddata();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 检索
	 * 
	 */
	@Command
	@NotifyChange({ "scoofferList" })
	public void search() {
		try {
			scoofferList.clear();

			for (CoOfferModel m : coofferList) {
				if (compactModel.getCoco_id() != null) {
					if (!m.getCoof_coco_id().equals(compactModel.getCoco_id())) {
						continue;
					}
				}

				scoofferList.add(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 计算社保在册数据
	@Command("countShebao")
	public void countShebao() {
		Algorithm_InfoBll aiBll = new Algorithm_InfoBll();
		aiBll.upLocalShebaoUpdate(cid, 0);
		// 刷新
		init();
		Messagebox.show("ok!", "ERROR", Messagebox.OK, Messagebox.INFORMATION);
	}

	@Command("sbhjlxOnChange")
	@NotifyChange({ "sb_list", "sb_listcount" })
	public void sbhjlxOnChange() {
		try {
			String strwhere = "";

			if (sbhjtype.equals("所有账户")) {
				single = null;
				strwhere = "and es.cid=" + cid + "  ";
			} else if (sbhjtype.equals("中智大户")) {
				single = 0;
				strwhere = "and es.cid=" + cid + " and es.esiu_single=0";
			} else if (sbhjtype.equals("中智委托")) {
				single = 2;
				strwhere = "and es.cid=" + cid + " and es.esiu_single=2";
			} else if (sbhjtype.equals("独立开户")) {
				single = 1;
				strwhere = "and es.cid=" + cid + " and es.esiu_single=1";
			} else {
				single = null;
				strwhere = "and es.cid=" + cid + "  ";
			}

			sb_list = new ListModelList<Allinone_ListModel>(
					cbll.getsb_list(strwhere));
			sb_listcount = new ListModelList<Allinone_ListModel>(
					cbll.getsb_listcount(strwhere));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 显示社保数据

	}

	@Command("gjjhjlxOnChange")
	@NotifyChange({ "house_list", "house_listcount" })
	public void gjjhjlxOnChange() {
		try {
			String strwhere = "";

			if (gjjhjtype.equals("所有账户")) {
				singlegjj = null;
				strwhere = "and eu.cid=" + cid + "  ";
			} else if (gjjhjtype.equals("中智大户")) {
				singlegjj = 0;
				strwhere = "and eu.cid=" + cid + " and emhu_single=0";
			} else if (gjjhjtype.equals("中智委托")) {
				singlegjj = 2;
				strwhere = "and eu.cid=" + cid + " and emhu_single=2";
			} else if (gjjhjtype.equals("独立开户")) {
				singlegjj = 1;
				strwhere = "and eu.cid=" + cid + " and  emhu_single=1";
			}

			house_list = new ListModelList<Allinone_ListModel>(
					cbll.gethouse_list(strwhere));
			house_listcount = new ListModelList<Allinone_ListModel>(
					cbll.gethouse_listcount(strwhere));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 显示社保数据

	}

	// 弹出查看页面
	@Command("chakan")
	public void chakan(@BindingParam("model") CoOfferModel model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("coofid", model.getCoof_id());
		Window window = (Window) Executions.createComponents(
				"/CoQuotation/CoQuotationInfoSelect.zul", null, map);
		window.doModal();
	}

	public void loaddata() throws SQLException {
		// List<Allinone_ListModel> list = cbll.getall_state(cid);
		//
		// model = list.get(0);
		//
		// coglist_list = new ListModelList<Allinone_ListModel>(
		// cbll.getcoglist_list(cid));// 显示所选服务类型

		hjtypelist.add("所有账户");
		hjtypelist.add("中智大户");
		hjtypelist.add("中智委托");
		hjtypelist.add("独立开户");

		house_listcount = new ListModelList<Allinone_ListModel>(
				cbll.gethouse_listcount(cid));

		sb_listcount = new ListModelList<Allinone_ListModel>(
				cbll.getsb_listcount(cid));

		sb_list = new ListModelList<Allinone_ListModel>(cbll.getsb_list(cid));// 显示社保数据

		house_list = new ListModelList<Allinone_ListModel>(
				cbll.gethouse_list(cid));// 显示住房数据

		emcompact_list = new ListModelList<Allinone_ListModel>(
				cbll.getemcompact_list(cid));// 显示劳动合同数据

		wt_list = new ListModelList<Allinone_ListModel>(cbll.getwt_list(cid));// 显示委托出数据

		gz_list = new ListModelList<Allinone_ListModel>(cbll.getgz_list(cid));// 显示工资数据

		file_list = new ListModelList<Allinone_ListModel>(
				cbll.getfile_list(cid));// 显示档案数据

		tj_list = new ListModelList<Allinone_ListModel>(cbll.gettj_list(cid));// 显示体检数据

		card_list = new ListModelList<Allinone_ListModel>(
				cbll.getcard_list(cid));// 显示社保卡数据

		hj_list = new ListModelList<Allinone_ListModel>(cbll.gethj_list(cid));// 显示户籍数据

		gzk_list = new ListModelList<Allinone_ListModel>(cbll.getgzk_list(cid));// 显示公积金卡制卡数据

		glk_list = new ListModelList<Allinone_ListModel>(cbll.getglk_list(cid));// 显示公积金卡领卡数据

		sy_list = new ListModelList<Allinone_ListModel>(cbll.getsy_list(cid));// 显示商保数据

		wt_listcount = new ListModelList<Allinone_ListModel>(
				cbll.getwt_listcount(cid));// 显示商保数据

	}

	/**
	 * 导出社保局最新台账
	 */
	@Command
	public void downSZSI() {

		try {
			//打开下载页面
			Map map = new HashMap();
			map.put("cid", cid);
			Window window = (Window) Executions.createComponents("../EmSheBao/Emsc_SZSI_FinanceFileDownload.zul",
					null, map);
			window.doModal();

		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("打开页面出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * 导出excel
	 */
	@Command
	public void excel() {
		// 获取当前系统的时间
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(System
				.currentTimeMillis());
		CoBase_SelectBll bll = new CoBase_SelectBll();
		// 获取公司信息
		CoBaseModel cbm = bll.getCobaseByCid(cid);
		// 获取该公司的社保信息

		List<EmShebaoUpdateModel> list = bll.getShebaoInfoByCidAllinOne(cid,
				single);

		// 打开文件
		WritableWorkbook book = null;
		try {
			// 设置标题的文字格式
			WritableFont wf = new WritableFont(WritableFont.COURIER, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			// 项目的绝对路径下创建一个文件夹
			File f = new File(FileOperate.getAbsolutePath() + "../downloads");
			if (!f.isFile()) {
				f.mkdir();
			}
			// System.out.println(f.getPath());
			book = Workbook.createWorkbook(new File(FileOperate
					.getAbsolutePath() + "../downloads/" + date + "社会保险.xls"));

			// 标题内容
			String sheetName = cbm.getCoba_company();
			// 生成标题
			WritableSheet sheet = book.createSheet(sheetName, 0);
			// 行高设置行高,参数(行数,高度)
			sheet.setRowView(0, 500);
			sheet.setColumnView(5, 30);
			// 标题
			sheet.addCell(new Label(0, 0, "人数:", wcf));
			sheet.addCell(new Label(1, 0, list.size() + "人"));
			sheet.addCell(new Label(2, 0, "公司编号:", wcf));
			sheet.addCell(new Label(3, 0, cid + ""));
			sheet.addCell(new Label(4, 0, "公司名称:", wcf));
			sheet.addCell(new Label(5, 0, cbm.getCoba_company()));

			// 表头
			String[] title = { "员工编号", "员工姓名", "员工身份证", "户籍", "社保电脑号", "社保基数",
					"养老", "医疗", "工伤", "失业", "生育", "公积金", "养老单位", "养老个人",
					"医疗个人", "医疗单位", "生育个人", "生育单位", "失业单位", "失业个人", "工伤个人	",
					"工伤单位", "个人合计", "单位合计", "本月状态" };
			for (int i = 0; i < title.length; i++) {
				// 用于写入文本内容到工作表中去
				Label label = null;
				// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
				label = new Label(i, 1, title[i], wcf);
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					sheet.addCell(new Label(0, i + 2, list.get(i).getGid() + ""));
					sheet.addCell(new Label(1, i + 2, list.get(i)
							.getEsiu_name()));
					sheet.addCell(new Label(2, i + 2, list.get(i)
							.getEsiu_idcard()));
					sheet.addCell(new Label(3, i + 2, list.get(i).getEsiu_hj()));
					sheet.addCell(new Label(4, i + 2, list.get(i)
							.getEsiu_computerid()));
					sheet.addCell(new Label(5, i + 2, list.get(i)
							.getEsiu_radix() + ""));
					sheet.addCell(new Label(6, i + 2, list.get(i).getEsiu_yl()));
					sheet.addCell(new Label(7, i + 2, list.get(i)
							.getEsiu_yltype()));
					sheet.addCell(new Label(8, i + 2, list.get(i).getEsiu_gs()));
					sheet.addCell(new Label(9, i + 2, list.get(i).getEsiu_sye()));
					sheet.addCell(new Label(10, i + 2, list.get(i)
							.getEsiu_syu()));
					sheet.addCell(new Label(11, i + 2, list.get(i)
							.getEsiu_house()));
					sheet.addCell(new Label(12, i + 2, list.get(i)
							.getEsiu_ylcp() + ""));
					sheet.addCell(new Label(13, i + 2, list.get(i)
							.getEsiu_ylop() + ""));
					sheet.addCell(new Label(14, i + 2, list.get(i)
							.getEsiu_jlop() + ""));
					sheet.addCell(new Label(15, i + 2, list.get(i)
							.getEsiu_jlcp() + ""));
					sheet.addCell(new Label(16, i + 2, list.get(i)
							.getEsiu_syuop() + ""));
					sheet.addCell(new Label(17, i + 2, list.get(i)
							.getEsiu_syucp() + ""));
					sheet.addCell(new Label(18, i + 2, list.get(i)
							.getEsiu_syecp() + ""));
					sheet.addCell(new Label(19, i + 2, list.get(i)
							.getEsiu_syeop() + ""));
					sheet.addCell(new Label(20, i + 2, list.get(i)
							.getEsiu_gsop() + ""));
					sheet.addCell(new Label(21, i + 2, list.get(i)
							.getEsiu_gscp() + ""));
					/*
					 * sheet.addCell(new Label(22, i + 2, list.get(i)
					 * .getEsiu_houseop() + "")); sheet.addCell(new Label(23, i
					 * + 2, list.get(i) .getEsiu_housecp() + ""));
					 */
					if (list.get(i).getEsiu_ifstop() == 0) {
						sheet.addCell(new Label(22, i + 2, list.get(i)
								.getEsiu_totalop() + ""));
						sheet.addCell(new Label(23, i + 2, list.get(i)
								.getEsiu_totalcp() + ""));
					} else {
						sheet.addCell(new Label(22, i + 2, "0"));
						sheet.addCell(new Label(23, i + 2, "0"));
					}

					sheet.addCell(new Label(24, i + 2, list.get(i).getEmsc_s8()
							+ ""));
				}
			}
			// 弹出下载excel数据
			FileOperate.download("../downloads/" + date + "社会保险.xls");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				book.write();
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

	@Command
	public void excelgjj() {
		// 获取当前系统的时间
		String date = new SimpleDateFormat("yyyyMMddHHmmss").format(System
				.currentTimeMillis());
		CoBase_SelectBll bll = new CoBase_SelectBll();
		// 获取公司信息
		CoBaseModel cbm = bll.getCobaseByCid(cid);
		// 获取该公司的社保信息
		List<EmHouseUpdateModel> list = bll.getGjjInfoByCidAllinOne(cid,
				singlegjj);

		// 打开文件
		WritableWorkbook book = null;
		try {
			// 设置标题的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE);// 字体、粗体、斜体、下划线
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			// 项目的绝对路径下创建一个文件夹
			File f = new File(FileOperate.getAbsolutePath() + "../downloads");
			if (!f.isFile()) {
				f.mkdir();
			}
			System.out.println(f.getPath());

			book = Workbook.createWorkbook(new File(FileOperate
					.getAbsolutePath() + "../downloads/" + date + "公积金.xls"));
			// 标题内容
			String sheetName = cbm.getCoba_company();
			// 生成标题
			WritableSheet sheet = book.createSheet(sheetName, 0);
			// 行高设置行高,参数(行数,高度)
			sheet.setRowView(0, 500);
			sheet.setColumnView(5, 30);
			// 标题
			sheet.addCell(new Label(0, 0, "人数:", wcf));
			sheet.addCell(new Label(1, 0, list.size() + "人", wcf));
			sheet.addCell(new Label(2, 0, "公司编号:", wcf));
			sheet.addCell(new Label(3, 0, cid + "", wcf));
			sheet.addCell(new Label(4, 0, "公司名称:", wcf));
			sheet.addCell(new Label(5, 0, cbm.getCoba_company(), wcf));

			// 表头
			String[] title = { "员工编号", "员工姓名", "公司", "单位公积金编号", "月份", "证件类型",
					"证件编号", "户籍", "公积金电脑号", "公积金基数", "个人公积金号", "公司比例", "个人比例",
					"公司缴纳", "个人缴纳", "状态" };
			for (int i = 0; i < title.length; i++) {
				// 用于写入文本内容到工作表中去
				Label label = null;
				// 在Label对象的构造中指明单元格位置(参数依次代表列数、行数、内容 )
				label = new Label(i, 1, title[i], wcf);
				// 将定义好的单元格添加到工作表中
				sheet.addCell(label);
			}
			if (list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					sheet.addCell(new Label(0, i + 2, list.get(i).getGid() + ""));
					sheet.addCell(new Label(1, i + 2, list.get(i)
							.getEmhu_name()));
					sheet.addCell(new Label(2, i + 2, list.get(i)
							.getEmhu_company()));
					sheet.addCell(new Label(3, i + 2, list.get(i)
							.getEmhu_companyid()));
					sheet.addCell(new Label(4, i + 2, list.get(i).getOwnmonth()
							+ ""));
					sheet.addCell(new Label(5, i + 2, list.get(i)
							.getEmhu_idcardclass()));
					sheet.addCell(new Label(6, i + 2, list.get(i)
							.getEmhu_idcard()));
					sheet.addCell(new Label(7, i + 2, list.get(i).getEmhu_hj()));
					sheet.addCell(new Label(8, i + 2, list.get(i)
							.getEmhu_computerid()));
					sheet.addCell(new Label(9, i + 2, list.get(i)
							.getEmhu_radix() + ""));
					sheet.addCell(new Label(10, i + 2, list.get(i)
							.getEmhu_houseid()));

					sheet.addCell(new Label(11, i + 2, list.get(i)
							.getEmhu_cpp() + ""));
					sheet.addCell(new Label(12, i + 2, list.get(i)
							.getEmhu_opp() + ""));
					if (list.get(i).getEmhu_ifstop() == 0) {
						sheet.addCell(new Label(13, i + 2, list.get(i)
								.getEmhu_cp() + ""));
						sheet.addCell(new Label(14, i + 2, list.get(i)
								.getEmhu_op() + ""));
					} else {
						sheet.addCell(new Label(13, i + 2, "0"));
						sheet.addCell(new Label(14, i + 2, "0"));
					}

					sheet.addCell(new Label(15, i + 2, list.get(i).getState()
							+ ""));

				}
			}
			// 弹出下载excel数据
			FileOperate.download("../downloads/" + date + "公积金.xls");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				book.write();
				book.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getHjtypelist() {
		return hjtypelist;
	}

	public void setHjtypelist(List<String> hjtypelist) {
		this.hjtypelist = hjtypelist;
	}

	public String getSbhjtype() {
		return sbhjtype;
	}

	public void setSbhjtype(String sbhjtype) {
		this.sbhjtype = sbhjtype;
	}

	public String getGjjhjtype() {
		return gjjhjtype;
	}

	public void setGjjhjtype(String gjjhjtype) {
		this.gjjhjtype = gjjhjtype;
	}

	public void setHjtypelist(ArrayList<String> hjtypelist) {
		this.hjtypelist = hjtypelist;
	}

	public ListModelList<Allinone_ListModel> getHouse_listcount() {
		return house_listcount;
	}

	public void setHouse_listcount(
			ListModelList<Allinone_ListModel> house_listcount) {
		this.house_listcount = house_listcount;
	}

	public ListModelList<Allinone_ListModel> getSb_listcount() {
		return sb_listcount;
	}

	public void setSb_listcount(ListModelList<Allinone_ListModel> sb_listcount) {
		this.sb_listcount = sb_listcount;
	}

	public List<CoCompactModel> getCompactList() {
		return compactList;
	}

	public void setCompactList(List<CoCompactModel> compactList) {
		this.compactList = compactList;
	}

	public List<CoOfferModel> getCoofferList() {
		return coofferList;
	}

	public void setCoofferList(List<CoOfferModel> coofferList) {
		this.coofferList = coofferList;
	}

	public CoCompactModel getCompactModel() {
		return compactModel;
	}

	public void setCompactModel(CoCompactModel compactModel) {
		this.compactModel = compactModel;
	}

	public List<CoOfferModel> getScoofferList() {
		return scoofferList;
	}

	public void setScoofferList(List<CoOfferModel> scoofferList) {
		this.scoofferList = scoofferList;
	}

	public ListModelList<Allinone_ListModel> getCoglist_list() {
		return coglist_list;
	}

	public void setCoglist_list(ListModelList<Allinone_ListModel> coglist_list) {
		this.coglist_list = coglist_list;
	}

	public ListModelList<Allinone_ListModel> getSb_list() {
		return sb_list;
	}

	public void setSb_list(ListModelList<Allinone_ListModel> sb_list) {
		this.sb_list = sb_list;
	}

	public ListModelList<Allinone_ListModel> getHouse_list() {
		return house_list;
	}

	public void setHouse_list(ListModelList<Allinone_ListModel> house_list) {
		this.house_list = house_list;
	}

	public ListModelList<Allinone_ListModel> getEmcompact_list() {
		return emcompact_list;
	}

	public void setEmcompact_list(
			ListModelList<Allinone_ListModel> emcompact_list) {
		this.emcompact_list = emcompact_list;
	}

	public ListModelList<Allinone_ListModel> getWt_list() {
		return wt_list;
	}

	public void setWt_list(ListModelList<Allinone_ListModel> wt_list) {
		this.wt_list = wt_list;
	}

	public ListModelList<Allinone_ListModel> getGz_list() {
		return gz_list;
	}

	public void setGz_list(ListModelList<Allinone_ListModel> gz_list) {
		this.gz_list = gz_list;
	}

	public ListModelList<Allinone_ListModel> getFile_list() {
		return file_list;
	}

	public void setFile_list(ListModelList<Allinone_ListModel> file_list) {
		this.file_list = file_list;
	}

	public ListModelList<Allinone_ListModel> getTj_list() {
		return tj_list;
	}

	public void setTj_list(ListModelList<Allinone_ListModel> tj_list) {
		this.tj_list = tj_list;
	}

	public ListModelList<Allinone_ListModel> getCard_list() {
		return card_list;
	}

	public void setCard_list(ListModelList<Allinone_ListModel> card_list) {
		this.card_list = card_list;
	}

	public ListModelList<Allinone_ListModel> getHj_list() {
		return hj_list;
	}

	public void setHj_list(ListModelList<Allinone_ListModel> hj_list) {
		this.hj_list = hj_list;
	}

	public ListModelList<Allinone_ListModel> getGzk_list() {
		return gzk_list;
	}

	public void setGzk_list(ListModelList<Allinone_ListModel> gzk_list) {
		this.gzk_list = gzk_list;
	}

	public ListModelList<Allinone_ListModel> getGlk_list() {
		return glk_list;
	}

	public void setGlk_list(ListModelList<Allinone_ListModel> glk_list) {
		this.glk_list = glk_list;
	}

	public Allinone_ListModel getModel() {
		return model;
	}

	public void setModel(Allinone_ListModel model) {
		this.model = model;
	}

	public ListModelList<Allinone_ListModel> getSy_list() {
		return sy_list;
	}

	public void setSy_list(ListModelList<Allinone_ListModel> sy_list) {
		this.sy_list = sy_list;
	}

	public ListModelList<Allinone_ListModel> getWt_listcount() {
		return wt_listcount;
	}

	public void setWt_listcount(ListModelList<Allinone_ListModel> wt_listcount) {
		this.wt_listcount = wt_listcount;
	}

	public boolean isVisCountSB() {
		return visCountSB;
	}

	public void setVisCountSB(boolean visCountSB) {
		this.visCountSB = visCountSB;
	}

}
