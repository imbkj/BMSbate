package Controller.EmCommissionOutNew;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.EmCommissionOutNew.EmCommissionOut_AddAutBll;
import bll.EmCommissionOutNew.EmCommissionOut_AutBll;
import bll.EmCommissionOutNew.EmCommissionOut_AutOperateBll;

import Model.CoAgencyLinkmanModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutZYTModel;
import Util.FileOperate;
import Util.UserInfo;
import Util.plyUtil;

public class EmCommissionOut_AutController {
	private ListModelList<EmCommissionOutChangeModel> citylist;// 显示委托城市

	private ListModelList<EmCommissionOutChangeModel> typelist;// 显示委托类型

	private ListModelList<EmCommissionOutChangeModel> wtjglist;// 显示委托机构

	private ListModelList<EmCommissionOutChangeModel> clientlist;// 显示客服名称

	private ListModelList<EmCommissionOutChangeModel> wtout_aut_list;// 显示未确认数据

	private ListModelList<EmCommissionOutZYTModel> ci_excel;// 导出excel商保数据

	private EmCommissionOutChangeModel wt_state_list;// 显示委托数据统计

	private List<EmCommissionOutChangeModel> gettaprid = new ListModelList<>();// 获取cid,taprid

	EmCommissionOut_AutBll bll = new EmCommissionOut_AutBll();

	@Init
	public void init() throws SQLException {
		citylist = new ListModelList<EmCommissionOutChangeModel>(
				bll.getcitylist());// 显示城市列表

		typelist = new ListModelList<EmCommissionOutChangeModel>(
				bll.gettypelist(""));// 显示委托类型

		wtjglist = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtjglist(""));// 显示委托机构

		clientlist = new ListModelList<EmCommissionOutChangeModel>(
				bll.getclientlist(""));// 显示客服名称

		wtout_aut_list = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtout_aut_list("", "", "", "", "", "", "", "",""));// 显示未确认数据

		wt_state_list = bll.getwt_state_list();// 显示委托统计
	}

	// 委托处理
	@Command("wt_aut")
	@NotifyChange("wtout_aut_list")
	public void wt_aut(@BindingParam("emco") EmCommissionOutChangeModel emco,
			@BindingParam("cid") Textbox cid, @BindingParam("gid") Textbox gid,
			@BindingParam("name") Textbox name,
			@BindingParam("company") Textbox company,
			@BindingParam("type") Combobox type,
			@BindingParam("wtjg") Combobox wtjg,
			@BindingParam("city") Combobox city,
			@BindingParam("client") Combobox client)
			throws WrongValueException, SQLException {
		String aut_url = "";
		Map arg = new HashMap();
		arg.put("ecoc_id", emco.getEcoc_id());

		if (emco.getEcoc_addtype().equals("新增")
				|| emco.getEcoc_addtype().equals("调整")) {
			aut_url = "EmCommissionOut_AddAut.zul";
		}

		if (emco.getEcoc_addtype().equals("离职")) {
			aut_url = "EmCommissionOut_DimAut.zul";
		}
		
		if (emco.getEcoc_addtype().equals("一次性费用")) {
			arg.put("daid", emco.getEcoc_id());
			arg.put("cm", emco);
			aut_url = "/EmCommissionOut/EmCommissionOut_OneTimeFeeOperate.zul";
		}

		Window wnd = (Window) Executions.createComponents(aut_url, null, arg);
		wnd.doModal();
		wtout_aut_list = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtout_aut_list(cid.getValue(), gid.getValue(),
						name.getValue(), company.getValue(), type.getValue(),
						wtjg.getValue(), city.getValue(), client.getValue(),""));// 显示未确认数据
	}

	// 导出批量上传数据
	@Command("ZYTExcel")
	public void ZYTExcel(@BindingParam("b") Grid gridco) throws Exception {
		if (gridco.getRows().getChildren().size() <= 0) {
			Messagebox.show("请至少选择一个员工!", "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		} else {
			String adtyp = "";
			for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
				try {
					Checkbox chk = (Checkbox) gridco.getCell(i, 0).getChildren()
							.get(0);

					if (chk.isChecked()) {

						Label addtype = (Label) gridco.getCell(i, 4).getChildren()
								.get(0);
						adtyp = addtype.getValue();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			System.out.println(adtyp);

			String filename = ""; // 文件名
			String absolutePath; // 服务器地址
			String filetype = "wt_out_excel.xls"; // 文件类型
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);

			filename = nowtime + filetype;
			absolutePath = FileOperate.getAbsolutePath();

			String excel_tp = "";

			if (adtyp.equals("新增") || adtyp.equals("调整")) {
				excel_tp = "/OfficeFile/Templet/EmCommissionOut/WT_ZYT_UP.xls";
			} else {
				excel_tp = "/OfficeFile/Templet/EmCommissionOut/WT_ZYT_DIM.xls";
			}

			// 创建exce
			// 读取Excel模板
			Workbook wb = Workbook
					.getWorkbook(new File(absolutePath + excel_tp));
			// 使用模板创建
			WritableWorkbook wwb = Workbook.createWorkbook(new File(
					absolutePath + "/OfficeFile/DownLoad/EmCommissionOut/"
							+ filename), wb);
			// 生成工作表
			WritableSheet sheet = wwb.getSheet(0);
			// Label label1 = null;
			int rowdata = 0;
			String pid = "";

			for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
				try {
					Checkbox chk = (Checkbox) gridco.getCell(i, 0).getChildren()
							.get(0);

					if (chk.isChecked()) {
						if (adtyp.equals("新增") || adtyp.equals("调整")) {
							ci_excel = new ListModelList<EmCommissionOutZYTModel>(
									bll.ci_excel(chk.getValue().toString()));// 显示保险在保类型
						} else {
							ci_excel = new ListModelList<EmCommissionOutZYTModel>(
									bll.ci_exceldim(chk.getValue().toString()));// 显示保险在保类型
						}

						rowdata = rowdata + 1;

						pid = String.valueOf(rowdata - 3);

						jxl.write.Label label1 = new jxl.write.Label(0, rowdata,
								ci_excel.get(0).getT1());
						jxl.write.Label label2 = new jxl.write.Label(1, rowdata,
								ci_excel.get(0).getT2());
						jxl.write.Label label3 = new jxl.write.Label(2, rowdata,
								ci_excel.get(0).getT3());
						jxl.write.Label label4 = new jxl.write.Label(3, rowdata,
								ci_excel.get(0).getT4());
						jxl.write.Label label5 = new jxl.write.Label(4, rowdata,
								ci_excel.get(0).getT5());
						jxl.write.Label label6 = new jxl.write.Label(5, rowdata,
								ci_excel.get(0).getT6());
						jxl.write.Label label7 = new jxl.write.Label(6, rowdata,
								ci_excel.get(0).getT7());
						jxl.write.Label label8 = new jxl.write.Label(7, rowdata,
								ci_excel.get(0).getT8());
						jxl.write.Label label9 = new jxl.write.Label(8, rowdata,
								ci_excel.get(0).getT9());
						jxl.write.Label label10 = new jxl.write.Label(9, rowdata,
								ci_excel.get(0).getT10());
						jxl.write.Label label11 = new jxl.write.Label(10, rowdata,
								ci_excel.get(0).getT11());
						jxl.write.Label label12 = new jxl.write.Label(11, rowdata,
								ci_excel.get(0).getT12());
						jxl.write.Label label13 = new jxl.write.Label(12, rowdata,
								ci_excel.get(0).getT13());
						jxl.write.Label label14 = new jxl.write.Label(13, rowdata,
								ci_excel.get(0).getT14());
						jxl.write.Label label15 = new jxl.write.Label(14, rowdata,
								ci_excel.get(0).getT15());
						jxl.write.Label label16 = new jxl.write.Label(15, rowdata,
								ci_excel.get(0).getT16());
						jxl.write.Label label17 = new jxl.write.Label(16, rowdata,
								ci_excel.get(0).getT17());
						jxl.write.Label label18 = new jxl.write.Label(17, rowdata,
								ci_excel.get(0).getT18());
						jxl.write.Label label19 = new jxl.write.Label(18, rowdata,
								ci_excel.get(0).getT19());
						jxl.write.Label label20 = new jxl.write.Label(19, rowdata,
								ci_excel.get(0).getT20());
						jxl.write.Label label21 = new jxl.write.Label(20, rowdata,
								ci_excel.get(0).getT21());
						jxl.write.Label label22 = new jxl.write.Label(21, rowdata,
								ci_excel.get(0).getT22());
						jxl.write.Label label23 = new jxl.write.Label(22, rowdata,
								ci_excel.get(0).getT23());
						jxl.write.Label label24 = new jxl.write.Label(23, rowdata,
								ci_excel.get(0).getT24());
						jxl.write.Label label25 = new jxl.write.Label(24, rowdata,
								ci_excel.get(0).getT25());
						jxl.write.Label label26 = new jxl.write.Label(25, rowdata,
								ci_excel.get(0).getT26());
						jxl.write.Label label27 = new jxl.write.Label(26, rowdata,
								ci_excel.get(0).getT27());
						jxl.write.Label label28 = new jxl.write.Label(27, rowdata,
								ci_excel.get(0).getT28());
						jxl.write.Label label29 = new jxl.write.Label(28, rowdata,
								ci_excel.get(0).getT29());
						jxl.write.Label label30 = new jxl.write.Label(29, rowdata,
								ci_excel.get(0).getT30());
						jxl.write.Label label31 = new jxl.write.Label(30, rowdata,
								ci_excel.get(0).getT31());
						jxl.write.Label label32 = new jxl.write.Label(31, rowdata,
								ci_excel.get(0).getT32());
						jxl.write.Label label33 = new jxl.write.Label(32, rowdata,
								ci_excel.get(0).getT33());
						jxl.write.Label label34 = new jxl.write.Label(33, rowdata,
								ci_excel.get(0).getT34());
						jxl.write.Label label35 = new jxl.write.Label(34, rowdata,
								ci_excel.get(0).getT35());
						jxl.write.Label label36 = new jxl.write.Label(35, rowdata,
								ci_excel.get(0).getT36());
						jxl.write.Label label37 = new jxl.write.Label(36, rowdata,
								ci_excel.get(0).getT37());
						jxl.write.Label label38 = new jxl.write.Label(37, rowdata,
								ci_excel.get(0).getT38());
						jxl.write.Label label39 = new jxl.write.Label(38, rowdata,
								ci_excel.get(0).getT39());
						jxl.write.Label label40 = new jxl.write.Label(39, rowdata,
								ci_excel.get(0).getT40());
						jxl.write.Label label41 = new jxl.write.Label(40, rowdata,
								ci_excel.get(0).getT41());
						jxl.write.Label label42 = new jxl.write.Label(41, rowdata,
								ci_excel.get(0).getT42());
						jxl.write.Label label43 = new jxl.write.Label(42, rowdata,
								ci_excel.get(0).getT43());
						jxl.write.Label label44 = new jxl.write.Label(43, rowdata,
								ci_excel.get(0).getT44());
						jxl.write.Label label45 = new jxl.write.Label(44, rowdata,
								ci_excel.get(0).getT45());
						jxl.write.Label label46 = new jxl.write.Label(45, rowdata,
								ci_excel.get(0).getT46());
						jxl.write.Label label47 = new jxl.write.Label(46, rowdata,
								ci_excel.get(0).getT47());
						jxl.write.Label label48 = new jxl.write.Label(47, rowdata,
								ci_excel.get(0).getT48());
						jxl.write.Label label49 = new jxl.write.Label(48, rowdata,
								ci_excel.get(0).getT49());
						jxl.write.Label label50 = new jxl.write.Label(49, rowdata,
								ci_excel.get(0).getT50());
						jxl.write.Label label51 = new jxl.write.Label(50, rowdata,
								ci_excel.get(0).getT51());
						jxl.write.Label label52 = new jxl.write.Label(51, rowdata,
								ci_excel.get(0).getT52());
						jxl.write.Label label53 = new jxl.write.Label(52, rowdata,
								ci_excel.get(0).getT53());
						jxl.write.Label label54 = new jxl.write.Label(53, rowdata,
								ci_excel.get(0).getT54());
						jxl.write.Label label55 = new jxl.write.Label(54, rowdata,
								ci_excel.get(0).getT55());
						jxl.write.Label label56 = new jxl.write.Label(55, rowdata,
								ci_excel.get(0).getT56());
						jxl.write.Label label57 = new jxl.write.Label(56, rowdata,
								ci_excel.get(0).getT57());
						jxl.write.Label label58 = new jxl.write.Label(57, rowdata,
								ci_excel.get(0).getT58());
						jxl.write.Label label59 = new jxl.write.Label(58, rowdata,
								ci_excel.get(0).getT59());
						jxl.write.Label label60 = new jxl.write.Label(59, rowdata,
								ci_excel.get(0).getT60());
						jxl.write.Label label61 = new jxl.write.Label(60, rowdata,
								ci_excel.get(0).getT61());
						jxl.write.Label label62 = new jxl.write.Label(61, rowdata,
								ci_excel.get(0).getT62());
						jxl.write.Label label63 = new jxl.write.Label(62, rowdata,
								ci_excel.get(0).getT63());
						jxl.write.Label label64 = new jxl.write.Label(63, rowdata,
								ci_excel.get(0).getT64());
						jxl.write.Label label65 = new jxl.write.Label(64, rowdata,
								ci_excel.get(0).getT65());
						jxl.write.Label label66 = new jxl.write.Label(65, rowdata,
								ci_excel.get(0).getT66());
						jxl.write.Label label67 = new jxl.write.Label(66, rowdata,
								ci_excel.get(0).getT67());
						jxl.write.Label label68 = new jxl.write.Label(67, rowdata,
								ci_excel.get(0).getT68());
						jxl.write.Label label69 = new jxl.write.Label(68, rowdata,
								ci_excel.get(0).getT69());
						jxl.write.Label label70 = new jxl.write.Label(69, rowdata,
								ci_excel.get(0).getT70());
						jxl.write.Label label71 = new jxl.write.Label(70, rowdata,
								ci_excel.get(0).getT71());
						jxl.write.Label label72 = new jxl.write.Label(71, rowdata,
								ci_excel.get(0).getT72());
						jxl.write.Label label73 = new jxl.write.Label(72, rowdata,
								ci_excel.get(0).getT73());
						jxl.write.Label label74 = new jxl.write.Label(73, rowdata,
								ci_excel.get(0).getT74());
						jxl.write.Label label75 = new jxl.write.Label(74, rowdata,
								ci_excel.get(0).getT75());
						jxl.write.Label label76 = new jxl.write.Label(75, rowdata,
								ci_excel.get(0).getT76());
						jxl.write.Label label77 = new jxl.write.Label(76, rowdata,
								ci_excel.get(0).getT77());
						jxl.write.Label label78 = new jxl.write.Label(77, rowdata,
								ci_excel.get(0).getT78());
						jxl.write.Label label79 = new jxl.write.Label(78, rowdata,
								ci_excel.get(0).getT79());
						jxl.write.Label label80 = new jxl.write.Label(79, rowdata,
								ci_excel.get(0).getT80());
						jxl.write.Label label81 = new jxl.write.Label(80, rowdata,
								ci_excel.get(0).getT81());
						jxl.write.Label label82 = new jxl.write.Label(81, rowdata,
								ci_excel.get(0).getT82());
						jxl.write.Label label83 = new jxl.write.Label(82, rowdata,
								ci_excel.get(0).getT83());
						jxl.write.Label label84 = new jxl.write.Label(83, rowdata,
								ci_excel.get(0).getT84());
						jxl.write.Label label85 = new jxl.write.Label(84, rowdata,
								ci_excel.get(0).getT85());
						jxl.write.Label label86 = new jxl.write.Label(85, rowdata,
								ci_excel.get(0).getT86());
						jxl.write.Label label87 = new jxl.write.Label(86, rowdata,
								ci_excel.get(0).getT87());
						jxl.write.Label label88 = new jxl.write.Label(87, rowdata,
								ci_excel.get(0).getT88());
						jxl.write.Label label89 = new jxl.write.Label(88, rowdata,
								ci_excel.get(0).getT89());
						jxl.write.Label label90 = new jxl.write.Label(89, rowdata,
								ci_excel.get(0).getT90());
						jxl.write.Label label91 = new jxl.write.Label(90, rowdata,
								ci_excel.get(0).getT91());
						jxl.write.Label label92 = new jxl.write.Label(91, rowdata,
								ci_excel.get(0).getT92());
						jxl.write.Label label93 = new jxl.write.Label(92, rowdata,
								ci_excel.get(0).getT93());
						jxl.write.Label label94 = new jxl.write.Label(93, rowdata,
								ci_excel.get(0).getT94());
						jxl.write.Label label95 = new jxl.write.Label(94, rowdata,
								ci_excel.get(0).getT95());
						jxl.write.Label label96 = new jxl.write.Label(95, rowdata,
								ci_excel.get(0).getT96());
						jxl.write.Label label97 = new jxl.write.Label(96, rowdata,
								ci_excel.get(0).getT97());
						jxl.write.Label label98 = new jxl.write.Label(97, rowdata,
								ci_excel.get(0).getT98());
						jxl.write.Label label99 = new jxl.write.Label(98, rowdata,
								ci_excel.get(0).getT99());
						jxl.write.Label label100 = new jxl.write.Label(99, rowdata,
								ci_excel.get(0).getT100());
						jxl.write.Label label101 = new jxl.write.Label(100,
								rowdata, ci_excel.get(0).getT101());
						jxl.write.Label label102 = new jxl.write.Label(101,
								rowdata, ci_excel.get(0).getT102());
						jxl.write.Label label103 = new jxl.write.Label(102,
								rowdata, ci_excel.get(0).getT103());
						jxl.write.Label label104 = new jxl.write.Label(103,
								rowdata, ci_excel.get(0).getT104());
						jxl.write.Label label105 = new jxl.write.Label(104,
								rowdata, ci_excel.get(0).getT105());
						jxl.write.Label label106 = new jxl.write.Label(105,
								rowdata, ci_excel.get(0).getT106());
						jxl.write.Label label107 = new jxl.write.Label(106,
								rowdata, ci_excel.get(0).getT107());
						jxl.write.Label label108 = new jxl.write.Label(107,
								rowdata, ci_excel.get(0).getT108());
						jxl.write.Label label109 = new jxl.write.Label(108,
								rowdata, ci_excel.get(0).getT109());
						jxl.write.Label label110 = new jxl.write.Label(109,
								rowdata, ci_excel.get(0).getT110());
						jxl.write.Label label111 = new jxl.write.Label(110,
								rowdata, ci_excel.get(0).getT111());
						jxl.write.Label label112 = new jxl.write.Label(111,
								rowdata, ci_excel.get(0).getT112());
						jxl.write.Label label113 = new jxl.write.Label(112,
								rowdata, ci_excel.get(0).getT113());
						jxl.write.Label label114 = new jxl.write.Label(113,
								rowdata, ci_excel.get(0).getT114());
						jxl.write.Label label115 = new jxl.write.Label(114,
								rowdata, ci_excel.get(0).getT115());
						jxl.write.Label label116 = new jxl.write.Label(115,
								rowdata, ci_excel.get(0).getT116());
						jxl.write.Label label117 = new jxl.write.Label(116,
								rowdata, ci_excel.get(0).getT117());
						jxl.write.Label label118 = new jxl.write.Label(117,
								rowdata, ci_excel.get(0).getT118());
						jxl.write.Label label119 = new jxl.write.Label(118,
								rowdata, ci_excel.get(0).getT119());
						jxl.write.Label label120 = new jxl.write.Label(119,
								rowdata, ci_excel.get(0).getT120());
						jxl.write.Label label121 = new jxl.write.Label(120,
								rowdata, ci_excel.get(0).getT121());
						jxl.write.Label label122 = new jxl.write.Label(121,
								rowdata, ci_excel.get(0).getT122());
						jxl.write.Label label123 = new jxl.write.Label(122,
								rowdata, ci_excel.get(0).getT123());
						jxl.write.Label label124 = new jxl.write.Label(123,
								rowdata, ci_excel.get(0).getT124());
						jxl.write.Label label125 = new jxl.write.Label(124,
								rowdata, ci_excel.get(0).getT125());
						jxl.write.Label label126 = new jxl.write.Label(125,
								rowdata, ci_excel.get(0).getT126());
						jxl.write.Label label127 = new jxl.write.Label(126,
								rowdata, ci_excel.get(0).getT127());
						jxl.write.Label label128 = new jxl.write.Label(127,
								rowdata, ci_excel.get(0).getT128());
						jxl.write.Label label129 = new jxl.write.Label(128,
								rowdata, ci_excel.get(0).getT129());
						jxl.write.Label label130 = new jxl.write.Label(129,
								rowdata, ci_excel.get(0).getT130());
						jxl.write.Label label131 = new jxl.write.Label(130,
								rowdata, ci_excel.get(0).getT131());
						jxl.write.Label label132 = new jxl.write.Label(131,
								rowdata, ci_excel.get(0).getT132());
						jxl.write.Label label133 = new jxl.write.Label(132,
								rowdata, ci_excel.get(0).getT133());
						jxl.write.Label label134 = new jxl.write.Label(133,
								rowdata, ci_excel.get(0).getT134());
						jxl.write.Label label135 = new jxl.write.Label(134,
								rowdata, ci_excel.get(0).getT135());
						jxl.write.Label label136 = new jxl.write.Label(135,
								rowdata, ci_excel.get(0).getT136());
						jxl.write.Label label137 = new jxl.write.Label(136,
								rowdata, ci_excel.get(0).getT137());
						jxl.write.Label label138 = new jxl.write.Label(137,
								rowdata, ci_excel.get(0).getT138());
						jxl.write.Label label139 = new jxl.write.Label(138,
								rowdata, ci_excel.get(0).getT139());
						jxl.write.Label label140 = new jxl.write.Label(139,
								rowdata, ci_excel.get(0).getT140());
						jxl.write.Label label141 = new jxl.write.Label(140,
								rowdata, ci_excel.get(0).getT141());
						jxl.write.Label label142 = new jxl.write.Label(141,
								rowdata, ci_excel.get(0).getT142());
						jxl.write.Label label143 = new jxl.write.Label(142,
								rowdata, ci_excel.get(0).getT143());
						jxl.write.Label label144 = new jxl.write.Label(143,
								rowdata, ci_excel.get(0).getT144());
						jxl.write.Label label145 = new jxl.write.Label(144,
								rowdata, ci_excel.get(0).getT145());
						jxl.write.Label label146 = new jxl.write.Label(145,
								rowdata, ci_excel.get(0).getT146());
						jxl.write.Label label147 = new jxl.write.Label(146,
								rowdata, ci_excel.get(0).getT147());
						jxl.write.Label label148 = new jxl.write.Label(147,
								rowdata, ci_excel.get(0).getT148());
						jxl.write.Label label149 = new jxl.write.Label(148,
								rowdata, ci_excel.get(0).getT149());
						jxl.write.Label label150 = new jxl.write.Label(149,
								rowdata, ci_excel.get(0).getT150());
						jxl.write.Label label151 = new jxl.write.Label(150,
								rowdata, ci_excel.get(0).getT151());
						jxl.write.Label label152 = new jxl.write.Label(151,
								rowdata, ci_excel.get(0).getT152());
						jxl.write.Label label153 = new jxl.write.Label(152,
								rowdata, ci_excel.get(0).getT153());
						jxl.write.Label label154 = new jxl.write.Label(153,
								rowdata, ci_excel.get(0).getT154());
						jxl.write.Label label155 = new jxl.write.Label(154,
								rowdata, ci_excel.get(0).getT155());
						jxl.write.Label label156 = new jxl.write.Label(155,
								rowdata, ci_excel.get(0).getT156());
						jxl.write.Label label157 = new jxl.write.Label(156,
								rowdata, ci_excel.get(0).getT157());
						jxl.write.Label label158 = new jxl.write.Label(157,
								rowdata, ci_excel.get(0).getT158());
						jxl.write.Label label159 = new jxl.write.Label(158,
								rowdata, ci_excel.get(0).getT159());
						jxl.write.Label label160 = new jxl.write.Label(159,
								rowdata, ci_excel.get(0).getT160());
						jxl.write.Label label161 = new jxl.write.Label(160,
								rowdata, ci_excel.get(0).getT161());
						jxl.write.Label label162 = new jxl.write.Label(161,
								rowdata, ci_excel.get(0).getT162());
						jxl.write.Label label163 = new jxl.write.Label(162,
								rowdata, ci_excel.get(0).getT163());
						jxl.write.Label label164 = new jxl.write.Label(163,
								rowdata, ci_excel.get(0).getT164());
						jxl.write.Label label165 = new jxl.write.Label(164,
								rowdata, ci_excel.get(0).getT165());
						jxl.write.Label label166 = new jxl.write.Label(165,
								rowdata, ci_excel.get(0).getT166());
						jxl.write.Label label167 = new jxl.write.Label(166,
								rowdata, ci_excel.get(0).getT167());
						jxl.write.Label label168 = new jxl.write.Label(167,
								rowdata, ci_excel.get(0).getT168());
						jxl.write.Label label169 = new jxl.write.Label(168,
								rowdata, ci_excel.get(0).getT169());
						jxl.write.Label label170 = new jxl.write.Label(169,
								rowdata, ci_excel.get(0).getT170());
						jxl.write.Label label171 = new jxl.write.Label(170,
								rowdata, ci_excel.get(0).getT171());
						jxl.write.Label label172 = new jxl.write.Label(171,
								rowdata, ci_excel.get(0).getT172());
						jxl.write.Label label173 = new jxl.write.Label(172,
								rowdata, ci_excel.get(0).getT173());
						jxl.write.Label label174 = new jxl.write.Label(173,
								rowdata, ci_excel.get(0).getT174());
						jxl.write.Label label175 = new jxl.write.Label(174,
								rowdata, ci_excel.get(0).getT175());
						jxl.write.Label label176 = new jxl.write.Label(175,
								rowdata, ci_excel.get(0).getT176());

						sheet.addCell(label1);
						sheet.addCell(label2);
						sheet.addCell(label3);
						sheet.addCell(label4);
						sheet.addCell(label5);
						sheet.addCell(label6);
						sheet.addCell(label7);
						sheet.addCell(label8);
						sheet.addCell(label9);
						sheet.addCell(label10);
						sheet.addCell(label11);
						sheet.addCell(label12);
						sheet.addCell(label13);
						sheet.addCell(label14);
						sheet.addCell(label15);
						sheet.addCell(label16);
						sheet.addCell(label17);
						sheet.addCell(label18);
						sheet.addCell(label19);
						sheet.addCell(label20);
						sheet.addCell(label21);
						sheet.addCell(label22);
						sheet.addCell(label23);
						sheet.addCell(label24);
						sheet.addCell(label25);
						sheet.addCell(label26);
						sheet.addCell(label27);
						sheet.addCell(label28);
						sheet.addCell(label29);
						sheet.addCell(label30);
						sheet.addCell(label31);
						sheet.addCell(label32);
						sheet.addCell(label33);
						sheet.addCell(label34);
						sheet.addCell(label35);
						sheet.addCell(label36);
						sheet.addCell(label37);
						sheet.addCell(label38);
						sheet.addCell(label39);
						sheet.addCell(label40);
						sheet.addCell(label41);
						sheet.addCell(label42);
						sheet.addCell(label43);
						sheet.addCell(label44);
						sheet.addCell(label45);
						sheet.addCell(label46);
						sheet.addCell(label47);
						sheet.addCell(label48);
						sheet.addCell(label49);
						sheet.addCell(label50);
						sheet.addCell(label51);
						sheet.addCell(label52);
						sheet.addCell(label53);
						sheet.addCell(label54);
						sheet.addCell(label55);
						sheet.addCell(label56);
						sheet.addCell(label57);
						sheet.addCell(label58);
						sheet.addCell(label59);
						sheet.addCell(label60);
						sheet.addCell(label61);
						sheet.addCell(label62);
						sheet.addCell(label63);
						sheet.addCell(label64);
						sheet.addCell(label65);
						sheet.addCell(label66);
						sheet.addCell(label67);
						sheet.addCell(label68);
						sheet.addCell(label69);
						sheet.addCell(label70);
						sheet.addCell(label71);
						sheet.addCell(label72);
						sheet.addCell(label73);
						sheet.addCell(label74);
						sheet.addCell(label75);
						sheet.addCell(label76);
						sheet.addCell(label77);
						sheet.addCell(label78);
						sheet.addCell(label79);
						sheet.addCell(label80);
						sheet.addCell(label81);
						sheet.addCell(label82);
						sheet.addCell(label83);
						sheet.addCell(label84);
						sheet.addCell(label85);
						sheet.addCell(label86);
						sheet.addCell(label87);
						sheet.addCell(label88);
						sheet.addCell(label89);
						sheet.addCell(label90);
						sheet.addCell(label91);
						sheet.addCell(label92);
						sheet.addCell(label93);
						sheet.addCell(label94);
						sheet.addCell(label95);
						sheet.addCell(label96);
						sheet.addCell(label97);
						sheet.addCell(label98);
						sheet.addCell(label99);
						sheet.addCell(label100);
						sheet.addCell(label101);
						sheet.addCell(label102);
						sheet.addCell(label103);
						sheet.addCell(label104);
						sheet.addCell(label105);
						sheet.addCell(label106);
						sheet.addCell(label107);
						sheet.addCell(label108);
						sheet.addCell(label109);
						sheet.addCell(label110);
						sheet.addCell(label111);
						sheet.addCell(label112);
						sheet.addCell(label113);
						sheet.addCell(label114);
						sheet.addCell(label115);
						sheet.addCell(label116);
						sheet.addCell(label117);
						sheet.addCell(label118);
						sheet.addCell(label119);
						sheet.addCell(label120);
						sheet.addCell(label121);
						sheet.addCell(label122);
						sheet.addCell(label123);
						sheet.addCell(label124);
						sheet.addCell(label125);
						sheet.addCell(label126);
						sheet.addCell(label127);
						sheet.addCell(label128);
						sheet.addCell(label129);
						sheet.addCell(label130);
						sheet.addCell(label131);
						sheet.addCell(label132);
						sheet.addCell(label133);
						sheet.addCell(label134);
						sheet.addCell(label135);
						sheet.addCell(label136);
						sheet.addCell(label137);
						sheet.addCell(label138);
						sheet.addCell(label139);
						sheet.addCell(label140);
						sheet.addCell(label141);
						sheet.addCell(label142);
						sheet.addCell(label143);
						sheet.addCell(label144);
						sheet.addCell(label145);
						sheet.addCell(label146);
						sheet.addCell(label147);
						sheet.addCell(label148);
						sheet.addCell(label149);
						sheet.addCell(label150);
						sheet.addCell(label151);
						sheet.addCell(label152);
						sheet.addCell(label153);
						sheet.addCell(label154);
						sheet.addCell(label155);
						sheet.addCell(label156);
						sheet.addCell(label157);
						sheet.addCell(label158);
						sheet.addCell(label159);
						sheet.addCell(label160);
						sheet.addCell(label161);
						sheet.addCell(label162);
						sheet.addCell(label163);
						sheet.addCell(label164);
						sheet.addCell(label165);
						sheet.addCell(label166);
						sheet.addCell(label167);
						sheet.addCell(label168);
						sheet.addCell(label169);
						sheet.addCell(label170);
						sheet.addCell(label171);
						sheet.addCell(label172);
						sheet.addCell(label173);
						sheet.addCell(label174);
						sheet.addCell(label175);
						sheet.addCell(label176);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
				
			}
			// 写入数据
			wwb.write();
			// 关闭文件
			wwb.close();

			FileOperate.download("/OfficeFile/DownLoad/EmCommissionOut/"
					+ filename);

			// 弹出提示
			Messagebox.show("操作完成！", "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, null);
			// Executions.sendRedirect("CI_Insurant_Aut.zul");

		}
	}

	/**
	 * 导出Excel(单条、批量)
	 * 
	 * @param set
	 */
	@Command("ExportExcel")
	public void ExportExcel(@BindingParam("b") Grid gridco) {
		if (gridco.getRows().getChildren().size() > 0) {
			// 按照set中第一个类型导出

			String addty = "";
			String ec_id = "";
			for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
				try {
					Label addtype = (Label) gridco.getCell(i, 4).getChildren()
							.get(0);
					Checkbox chk = (Checkbox) gridco.getCell(i, 0).getChildren()
							.get(0);
					if (chk.isChecked()) {
						addty = addtype.getValue().toString();
						ec_id = chk.getValue().toString();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			System.out.println("xxxxaaaa");
			System.out.println(addty);
			if (addty.equals("新增") || addty.equals("调整")) {
				try {
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_all_excel.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);

					}

					// 读取Excel模板
					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_out_all.xls", this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					WritableCellFormat wcf = null;
					jxl.write.Label label = null;
					int row = 12;

					for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
						try {
							Checkbox chk1 = (Checkbox) gridco.getCell(i, 0)
									.getChildren().get(0);
							if (chk1.isChecked()) {
								System.out.println("xxxxx");
								if (addty.equals("新增") || addty.equals("调整")) {
									ci_excel = new ListModelList<EmCommissionOutZYTModel>(
											bll.ci_excel_f(chk1.getValue().toString()));// 显示保险在保类型
									// 插入一行
									sheet.insertRow(row);

									// 序号
									wcf = new WritableCellFormat(sheet.getCell(0,
											row + 1).getCellFormat());
									label = new jxl.write.Label(0, row, i + 1 + "",
											wcf);
									sheet.addCell(label);

									// 公司名称
									sheet.mergeCells(1, row, 2, row);
									wcf = new WritableCellFormat(sheet.getCell(1,
											row + 1).getCellFormat());
									label = new jxl.write.Label(1, row, ci_excel
											.get(0).getT7(), wcf);
									sheet.addCell(label);

									// 员工姓名
									wcf = new WritableCellFormat(sheet.getCell(3,
											row + 1).getCellFormat());
									label = new jxl.write.Label(3, row, ci_excel
											.get(0).getT2(), wcf);
									sheet.addCell(label);

									// 身份证号码
									wcf = new WritableCellFormat(sheet.getCell(2,
											row + 1).getCellFormat());
									label = new jxl.write.Label(4, row, ci_excel
											.get(0).getT11(), wcf);
									sheet.addCell(label);

									// 联系电话
									// sheet.mergeCells(4, row, 5, row);
									wcf = new WritableCellFormat(sheet.getCell(5,
											row + 1).getCellFormat());
									label = new jxl.write.Label(5, row, ci_excel
											.get(0).getT5()
											+ "/"
											+ ci_excel.get(0).getT4(), wcf);
									sheet.addCell(label);

									// 户籍
									// sheet.mergeCells(6, row, 7, row);
									// wcf = new WritableCellFormat(sheet.getCell(6,
									// row + 1).getCellFormat());
									// label = new Label(6, row,
									// m.getEcoc_domicile(),
									// wcf);
									// sheet.addCell(label);

									// 受托地
									wcf = new WritableCellFormat(sheet.getCell(6,
											row + 1).getCellFormat());
									label = new jxl.write.Label(6, row, ci_excel
											.get(0).getT24(), wcf);
									sheet.addCell(label);

									// 社保委托起始日
									wcf = new WritableCellFormat(sheet.getCell(7,
											row + 1).getCellFormat());
									label = new jxl.write.Label(7, row, ci_excel
											.get(0).getT199(), wcf);
									sheet.addCell(label);

									// 住房委托起始日
									wcf = new WritableCellFormat(sheet.getCell(12,
											row + 1).getCellFormat());
									label = new jxl.write.Label(12, row, ci_excel
											.get(0).getT198(), wcf);
									sheet.addCell(label);

									// 社保基数
									wcf = new WritableCellFormat(sheet.getCell(8,
											row + 1).getCellFormat());
									label = new jxl.write.Label(8, row, ci_excel
											.get(0).getT197() + "", wcf);
									sheet.addCell(label);

									// 公司社保费
									wcf = new WritableCellFormat(sheet.getCell(9,
											row + 1).getCellFormat());
									label = new jxl.write.Label(9, row, ci_excel
											.get(0).getT195() + "", wcf);
									sheet.addCell(label);

									// 个人社保费
									wcf = new WritableCellFormat(sheet.getCell(10,
											row + 1).getCellFormat());
									label = new jxl.write.Label(10, row, ci_excel
											.get(0).getT194() + "", wcf);
									sheet.addCell(label);

									// 合计社保费
									wcf = new WritableCellFormat(sheet.getCell(11,
											row + 1).getCellFormat());
									label = new jxl.write.Label(11, row, ci_excel
											.get(0).getT193() + "", wcf);
									sheet.addCell(label);

									// 住房基数
									wcf = new WritableCellFormat(sheet.getCell(13,
											row + 1).getCellFormat());
									label = new jxl.write.Label(13, row, ci_excel
											.get(0).getT196() + "", wcf);
									sheet.addCell(label);

									// 商保
									wcf = new WritableCellFormat(sheet.getCell(18,
											row + 1).getCellFormat());
									label = new jxl.write.Label(18, row, "0.00",
											wcf);
									sheet.addCell(label);

									// 体检
									wcf = new WritableCellFormat(sheet.getCell(17,
											row + 1).getCellFormat());
									label = new jxl.write.Label(17, row, "0.00",
											wcf);
									sheet.addCell(label);

									// 其它时间
									wcf = new WritableCellFormat(sheet.getCell(19,
											row + 1).getCellFormat());
									label = new jxl.write.Label(19, row, "", wcf);
									sheet.addCell(label);

									// 总计
									wcf = new WritableCellFormat(sheet.getCell(22,
											row + 1).getCellFormat());
									label = new jxl.write.Label(22, row, ci_excel
											.get(0).getT192() + "", wcf);
									sheet.addCell(label);

									// 备注
									wcf = new WritableCellFormat(sheet.getCell(23,
											row + 1).getCellFormat());
									label = new jxl.write.Label(23, row, ci_excel
											.get(0).getT112() + "", wcf);
									sheet.addCell(label);

									// 公积金明细
									label = new jxl.write.Label(15, row, ci_excel
											.get(0).getT80(), wcf);
									sheet.addCell(label);
									label = new jxl.write.Label(14, row, ci_excel
											.get(0).getT79(), wcf);
									sheet.addCell(label);
									label = new jxl.write.Label(16, row, ci_excel
											.get(0).getT191() + "", wcf);
									sheet.addCell(label);

									// 补充福利
									// label = new Label(11, row,
									// m.getEcoc_welfare_sum()
									// + "", wcf);
									// sheet.addCell(label);
									// 服务费
									label = new jxl.write.Label(21, row, ci_excel
											.get(0).getT190() + "", wcf);
									sheet.addCell(label);
									// 档案费
									label = new jxl.write.Label(20, row, ci_excel
											.get(0).getT189() + "", wcf);
									sheet.addCell(label);
									// 其他
									// label = new Label(14, row, "", wcf);
									// sheet.addCell(label);
									// 备注
									label = new jxl.write.Label(22, row, ci_excel
											.get(0).getT112(), wcf);
									sheet.addCell(label);

									row++;
								}
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						
						
					}
					sheet.removeRow(row);

					ci_excel = new ListModelList<EmCommissionOutZYTModel>(
							bll.ci_excel_f(ec_id));// 显示保险在保类型
					// 获取委托机构联系人信息
					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(Integer.parseInt(ci_excel.get(
							0).getT200()));

					// 委托机构
					wcf = new WritableCellFormat(sheet.getCell(2, 3)
							.getCellFormat());

					label = new jxl.write.Label(1, 3,
							ci_excel.get(0).getT110(), wcf);
					sheet.addCell(label);

					// 联系人姓名
					label = new jxl.write.Label(1, 4, calList.get(0)
							.getCali_name(), wcf);
					sheet.addCell(label);

					// 传真
					// wcf = new WritableCellFormat(sheet.getCell(9, 5)
					// .getCellFormat());
					label = new jxl.write.Label(1, 6, calList.get(0)
							.getCali_fax(), wcf);
					sheet.addCell(label);

					// 电话
					// wcf = new WritableCellFormat(sheet.getCell(9, row - 1)
					// .getCellFormat());
					label = new jxl.write.Label(1, 5, calList.get(0)
							.getCali_tel(), wcf);
					sheet.addCell(label);

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();

					try {
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
										+ filename);
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);

					}
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}

			if (addty.equals("离职")) {
				try {
					String filename = new SimpleDateFormat("yyyyMMddHHmmssSSS")
							.format(new Date())
							+ UserInfo.getUserid()
							+ "wt_out_all_excel.xls";
					File file = new File(new plyUtil().getAbsolutePath(
							"/../../OfficeFile/DownLoad/EmCommissionOut",
							filename, this));
					try {
						file.createNewFile();
					} catch (Exception e) {
						Messagebox.show("生成文件出错,请再次点击生成!", "ERROR",
								Messagebox.OK, Messagebox.ERROR);

					}

					Workbook wb = Workbook
							.getWorkbook(new File(
									new plyUtil()
											.getAbsolutePath(
													"/../../OfficeFile/Templet/EmCommissionOut",
													"wt_dis_all.xls", this)));

					// 使用模板创建
					WritableWorkbook wwb = Workbook.createWorkbook(file, wb);

					// 生成工作表,(name:First Sheet,参数0表示这是第一页)
					WritableSheet sheet = wwb.getSheet(0);

					WritableCellFormat wcf = null;
					jxl.write.Label label = null;
					int row = 12;

					for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
						Checkbox chk1 = (Checkbox) gridco.getCell(i, 0)
								.getChildren().get(0);
						if (chk1.isChecked()) {
							System.out.println("xxxxx");
							if (addty.equals("离职")) {
								ci_excel = new ListModelList<EmCommissionOutZYTModel>(
										bll.ci_exceldim(chk1.getValue()
												.toString()));// 显示保险在保类型
								// 插入一行
								sheet.insertRow(row);

								// 序号
								wcf = new WritableCellFormat(sheet.getCell(0,
										row + 1).getCellFormat());
								label = new jxl.write.Label(0, row, i + 1 + "",
										wcf);
								sheet.addCell(label);

								// 公司名称
								sheet.mergeCells(1, row, 2, row);
								label = new jxl.write.Label(1, row, ci_excel
										.get(0).getT7(), wcf);
								sheet.addCell(label);

								// 员工姓名
								label = new jxl.write.Label(3, row, ci_excel
										.get(0).getT2(), wcf);
								sheet.addCell(label);

								// 身份证号码
								label = new jxl.write.Label(4, row, ci_excel
										.get(0).getT11(), wcf);
								sheet.addCell(label);

								// 受托地
								wcf = new WritableCellFormat(sheet.getCell(6,
										row + 1).getCellFormat());
								label = new jxl.write.Label(5, row, ci_excel
										.get(0).getT23(), wcf);
								sheet.addCell(label);

								// 离职时间
								wcf = new WritableCellFormat(sheet.getCell(5,
										row + 1).getCellFormat());
								label = new jxl.write.Label(6, row, ci_excel
										.get(0).getT200(), wcf);
								sheet.addCell(label);

								// 社保委托起始日
								wcf = new WritableCellFormat(sheet.getCell(5,
										row + 1).getCellFormat());
								label = new jxl.write.Label(8, row, ci_excel
										.get(0).getT199(), wcf);
								sheet.addCell(label);

								// 住房委托起始日
								wcf = new WritableCellFormat(sheet.getCell(5,
										row + 1).getCellFormat());
								label = new jxl.write.Label(9, row, ci_excel
										.get(0).getT198(), wcf);
								sheet.addCell(label);

								// 住房委托起始日
								wcf = new WritableCellFormat(sheet.getCell(5,
										row + 1).getCellFormat());
								label = new jxl.write.Label(7, row, ci_excel
										.get(0).getT197(), wcf);
								sheet.addCell(label);

								// 备注
								label = new jxl.write.Label(11, row, ci_excel
										.get(0).getT196(), wcf);
								sheet.addCell(label);
								row++;
							}
						}
					}
					sheet.removeRow(row);

					ci_excel = new ListModelList<EmCommissionOutZYTModel>(
							bll.ci_exceldim(ec_id));// 显示保险在保类型
					// 获取委托机构联系人信息
					List<CoAgencyLinkmanModel> calList = new ListModelList<>();
					calList = bll.getLinkManList(Integer.parseInt(ci_excel.get(
							0).getT195()));

					// 委托机构
					wcf = new WritableCellFormat(sheet.getCell(2, 3)
							.getCellFormat());
					label = new jxl.write.Label(2, 3,
							ci_excel.get(0).getT110(), wcf);
					sheet.addCell(label);

					// 联系人姓名
					label = new jxl.write.Label(2, 4, calList.get(0)
							.getCali_name(), wcf);
					sheet.addCell(label);

					// 传真
					// wcf = new WritableCellFormat(sheet.getCell(9, 5)
					// .getCellFormat());
					label = new jxl.write.Label(2, 5, calList.get(0)
							.getCali_fax(), wcf);
					sheet.addCell(label);

					// 电话
					// wcf = new WritableCellFormat(sheet.getCell(9, row - 1)
					// .getCellFormat());
					// label = new jxl.write.Label(1, 5, calList.get(0)
					// .getCali_tel(), wcf);
					// sheet.addCell(label);

					// 写入数据
					wwb.write();
					// 关闭文件
					wwb.close();

					try {
						FileOperate
								.download("OfficeFile/DownLoad/EmCommissionOut/"
										+ filename);
					} catch (Exception e) {
						e.printStackTrace();
						Messagebox.show("获取下载文件失败!", "ERROR", Messagebox.OK,
								Messagebox.ERROR);

					}
				} catch (Exception e) {
					e.printStackTrace();
					Messagebox.show("导出出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
	}

	@Command("checkall")
	public void checkall(@BindingParam("a") boolean ck,
			@BindingParam("b") Grid gd) {
		for (int i = 0; i < gd.getRows().getChildren().size(); i++) {
			try {
				Checkbox ckb = (Checkbox) gd.getCell(i, 0).getChildren().get(0);
				ckb.setChecked(ck);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	@Command("search")
	@NotifyChange("wtout_aut_list")
	public void search(@BindingParam("cid") Textbox cid,
			@BindingParam("gid") Textbox gid,
			@BindingParam("name") Textbox name,
			@BindingParam("company") Textbox company,
			@BindingParam("type") Combobox type,
			@BindingParam("wtjg") Combobox wtjg,
			@BindingParam("city") Combobox city,
			@BindingParam("client") Combobox client, @BindingParam("b") Grid gd)
			throws SQLException {

		wtout_aut_list = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtout_aut_list(cid.getValue(), gid.getValue(),
						name.getValue(), company.getValue(), type.getValue(),
						wtjg.getValue(), city.getValue(), client.getValue(),""));// 显示未确认数据

	}
	
	@Command("ts_search")
	@NotifyChange("wtout_aut_list")
	public void ts_search(@BindingParam("cid") Textbox cid,
			@BindingParam("gid") Textbox gid,
			@BindingParam("name") Textbox name,
			@BindingParam("company") Textbox company,
			@BindingParam("type") Combobox type,
			@BindingParam("wtjg") Combobox wtjg,
			@BindingParam("city") Combobox city,
			@BindingParam("client") Combobox client, @BindingParam("b") Grid gd)
			throws SQLException {

		wtout_aut_list = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtout_aut_list("", "",
						"", "", "",
						"", "", "","1"));// 显示未确认数据

	}

	@Command("city_search")
	@NotifyChange({ "typelist", "wtjglist", "clientlist" })
	public void city_search(@BindingParam("city") Combobox city)
			throws SQLException {

		typelist = new ListModelList<EmCommissionOutChangeModel>(
				bll.gettypelist(city.getValue()));// 显示委托类型

		wtjglist = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtjglist(city.getValue()));// 显示委托机构

		clientlist = new ListModelList<EmCommissionOutChangeModel>(
				bll.getclientlist(city.getValue()));// 显示客服名称
	}

	/**
	 * 导出Excel(批量确认)
	 * 
	 * @param set
	 * @throws SQLException
	 * @throws WrongValueException
	 */
	@Command("updates")
	@NotifyChange("wtout_aut_list")
	public void updates(@BindingParam("b") Grid gridco,
			@BindingParam("cid") Textbox cid, @BindingParam("gid") Textbox gid,
			@BindingParam("name") Textbox name,
			@BindingParam("company") Textbox company,
			@BindingParam("type") Combobox type,
			@BindingParam("wtjg") Combobox wtjg,
			@BindingParam("city") Combobox city,
			@BindingParam("client") Combobox client)
			throws WrongValueException, SQLException {
		EmCommissionOut_AddAutBll csbll = new EmCommissionOut_AddAutBll();
		EmCommissionOut_AutOperateBll ccsaBll = new EmCommissionOut_AutOperateBll();
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			Checkbox chk1 = (Checkbox) gridco.getCell(i, 0).getChildren()
					.get(0);
			if (chk1.isChecked()) {
				try {
					setGettaprid(csbll.gettaprid(chk1.getValue().toString()));// 社保费用明细

					String[] message = ccsaBll.yc_aut(chk1.getValue()
							.toString(), Integer.parseInt(gettaprid.get(0)
							.getEcoc_tapr_id().toString()));

				} catch (Exception e) {
					// TODO: handle exception
					Messagebox.show("操作出错!", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
			}
		}
		// 弹出提示
		Messagebox.show("操作成功！", "操作提示",
				new Messagebox.Button[] { Messagebox.Button.OK },
				Messagebox.INFORMATION, null);

		wtout_aut_list = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtout_aut_list(cid.getValue(), gid.getValue(),
						name.getValue(), company.getValue(), type.getValue(),
						wtjg.getValue(), city.getValue(), client.getValue(),""));// 显示未确认数据
	}

	// 批量退回
	@Command("allback")
	@NotifyChange("wtout_aut_list")
	public void allback(@BindingParam("b") Grid gridco,
			@BindingParam("cid") Textbox cid, @BindingParam("gid") Textbox gid,
			@BindingParam("name") Textbox name,
			@BindingParam("company") Textbox company,
			@BindingParam("type") Combobox type,
			@BindingParam("wtjg") Combobox wtjg,
			@BindingParam("city") Combobox city,
			@BindingParam("client") Combobox client) throws WrongValueException,
			SQLException {
		EmCommissionOut_AddAutBll csbll = new EmCommissionOut_AddAutBll();
		EmCommissionOut_AutOperateBll ccsaBll = new EmCommissionOut_AutOperateBll();
		String chkall = "";
		for (int i = 0; i < gridco.getRows().getChildren().size(); i++) {
			try {
				Checkbox chk1 = (Checkbox) gridco.getCell(i, 0).getChildren()
						.get(0);
				if (chk1.isChecked()) {
					chkall = chkall + chk1.getValue().toString() + ",";

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		System.out.println(chkall);

		Map arg = new HashMap();
		arg.put("ecoc_id", chkall);

		Window wnd = (Window) Executions.createComponents(
				"EmCommissionOut_AllBack.zul", null, arg);
		wnd.doModal();

		wtout_aut_list = new ListModelList<EmCommissionOutChangeModel>(
				bll.getwtout_aut_list(cid.getValue(), gid.getValue(),
						name.getValue(), company.getValue(), type.getValue(),
						wtjg.getValue(), city.getValue(), client.getValue(),""));// 显示未确认数据
	}

	// 居中带边框
	private WritableCellFormat getFormatBorder() {
		WritableFont wf = new WritableFont(WritableFont.createFont("宋体"), 10);
		WritableCellFormat wc = new WritableCellFormat(wf);
		try {
			wc.setAlignment(Alignment.CENTRE);
			wc.setVerticalAlignment(VerticalAlignment.CENTRE);
			wc.setBorder(Border.ALL, BorderLineStyle.THIN);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return wc;
	}

	public ListModelList<EmCommissionOutChangeModel> getCitylist() {
		return citylist;
	}

	public void setCitylist(ListModelList<EmCommissionOutChangeModel> citylist) {
		this.citylist = citylist;
	}

	public ListModelList<EmCommissionOutChangeModel> getTypelist() {
		return typelist;
	}

	public void setTypelist(ListModelList<EmCommissionOutChangeModel> typelist) {
		this.typelist = typelist;
	}

	public ListModelList<EmCommissionOutChangeModel> getWtjglist() {
		return wtjglist;
	}

	public void setWtjglist(ListModelList<EmCommissionOutChangeModel> wtjglist) {
		this.wtjglist = wtjglist;
	}

	public ListModelList<EmCommissionOutChangeModel> getClientlist() {
		return clientlist;
	}

	public void setClientlist(
			ListModelList<EmCommissionOutChangeModel> clientlist) {
		this.clientlist = clientlist;
	}

	public ListModelList<EmCommissionOutChangeModel> getWtout_aut_list() {
		return wtout_aut_list;
	}

	public void setWtout_aut_list(
			ListModelList<EmCommissionOutChangeModel> wtout_aut_list) {
		this.wtout_aut_list = wtout_aut_list;
	}

	public EmCommissionOutChangeModel getWt_state_list() {
		return wt_state_list;
	}

	public void setWt_state_list(EmCommissionOutChangeModel wt_state_list) {
		this.wt_state_list = wt_state_list;
	}

	public List<EmCommissionOutChangeModel> getGettaprid() {
		return gettaprid;
	}

	public void setGettaprid(List<EmCommissionOutChangeModel> gettaprid) {
		this.gettaprid = gettaprid;
	}

}
