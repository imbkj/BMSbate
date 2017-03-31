import impl.WorkflowCore.WfOperateImpl;
import impl.WorkflowCore.WfSearchTaskImpl;

import java.awt.Label;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.TreeModel;
import org.zkoss.zul.TreeNode;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import service.WorkflowCore.WfSearchTaskService;

import bll.LoginBll;
import bll.CoBase.EmbaseMoveAccountBll;
import bll.CoCompact.Compact_EmpAllotBll;
import bll.CoSocialInsurance.CoSocialInsurance_ListBll;
import bll.EmBenefit.EmBenefit_AllotBll;
import bll.EmBenefit.EmBenefit_ManagerBll;
import bll.EmBenefit.EmBenefit_comitListBll;
import bll.EmHouse.EmHouseSetBll;
import bll.EmHouse.EmHouse_EditBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmBase_DistributeImpl;
import bll.Embase.EmBase_OnBoardBll;
import bll.Embase.EmbaseLogin_AddBll;
import bll.SocialInsurance.Algorithm_RegisteredDataBll;

import com.sun.crypto.provider.RSACipher;
import com.sun.imageio.plugins.wbmp.WBMPImageReader;
import com.sun.org.apache.bcel.internal.util.Class2HTML;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Pattern;

import dal.LoginDal;
import dal.CoBase.CoBase_SelectDal;
import dal.CoCompact.CoCompactBaseDal;
import dal.CoHousingFund.CoHousingFund_ListDal;
import dal.CoProduct.cpd_List1Dal;
import dal.EmBenefit.EmBenefitDal;
import dal.EmBodyCheck.EmBc_CommitSelectDal;
import dal.EmCommercialInsurance.CI_Insurant_ListDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseCompanyIdDal;
import dal.EmHouse.EmHouseSetupDal;
import dal.Embase.EmOnBoardListDal;
import dal.Embase.EmbaseNotdal;
import dal.Taskflow.EmBaseMenulistDal;

import Conn.dbconn;
import Controller.systemWindowController;
import Controller.CoSocialInsurance.CoSocialInsurance_AddController;
import Controller.EmSheBaocard.newExcelImpl;
import Model.CoBaseModel;
import Model.CoCompactModel;
import Model.CoHousingFundModel;
import Model.CoOfferListModel;
import Model.CoProductModel;
import Model.EmActySupplierInfoModel;
import Model.EmBenefitModel;
import Model.EmBodyCheckDateListModel;
import Model.EmBodyCheckModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeGJJModel;
import Model.EmHouseChangeModel;
import Model.EmHouseGJJMonthModel;
import Model.EmHouseSetupModel;
import Model.EmShebaoUpdateModel;
import Model.EmWelfareModel;
import Model.EmZYTModel;
import Model.EmbaseBusinessCenterModel;
import Model.EmbaseModel;
import Model.EmbaseNotInModel;
import Model.SocialInsuranceClassInfoViewModel;
import Model.SocialInsuranceModel;
import Model.WfTaskListInfoModel;
import Util.CalendarDate;
import Util.CategoryModel;
import Util.CategoryTreeNode;
import Util.DateStringChange;
import Util.DateUtil;
import Util.FileOperate;
import Util.Log4jInit;
import Util.MD5;
import Util.ObjectAttributeUtil;
import Util.ObjectClassUtil;
import Util.SocialInsuranceCalculator;
import Util.StringFormat;
import Util.UserInfo;
import Util.plyUtil;
import java.util.regex.*;

import javax.servlet.ServletException;

public class test {

	
	public void test() {
		List<SocialInsuranceModel> list = new ListModelList<>();
		Algorithm_RegisteredDataBll bll = new Algorithm_RegisteredDataBll();
		String sql = "SELECT soin_id,sial_id,sial_execdate" +
				" FROM SocialInsurance a" +
				" inner join (select * from SocialInsuranceAlgorithm" +
				" where sial_id in (select MAX(sial_id) from SocialInsuranceAlgorithm" +
				" where datediff(d,sial_execdate,GETDATE())>=0" +
				" group by sial_soin_id)" +
				") b on a.soin_id=b.sial_soin_id" +
				" where soin_state=1 and soin_id>4 and  AND a.soin_id IN (SELECT sich_soin_id from SocialInsuranceChange where sich_addtime>'2015-11-17'  )";
		dbconn db=new dbconn();
		try {
			list=db.find(sql, SocialInsuranceModel.class, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list.size());
		for (SocialInsuranceModel m : list) {
			//System.out.println(m.getSoin_id()+","+m.getSial_id()+","+m.getSial_execdate());
			bll.upRegData(m.getSoin_id(), m.getSial_id(), m.getSial_execdate());
		}
		//
		System.out.println("**");
	}

	public void testUpdateOut() {

	}

	public void testobject() throws ParseException {
		Integer[] ownmonth = { 201507, 201508, 201509 };
		Integer[] month = { 201507, 201508, 201509 };
		Integer[] fw = { 201504, 201505, 201506, 201507, 201508, 201509 };
		Integer[] sf = { 201504, 201505, 201506, 201507, 201508, 201509 };
		Boolean[] sb = { false, true };
		Boolean[] house = { false, true };
		Integer gid = 0;
		Integer cid = 0;
		boolean sb_ifStop = false;
		boolean house_ifStop = false;

		EmbaseLogin_AddBll bll = new EmbaseLogin_AddBll();
		EmHouseSetBll sbll = new EmHouseSetBll();

		Integer emba_emhb_ownmonth; // 公积金所属月
		Integer emba_emsb_ownmonth; // 社保所属月

		Integer sbfwownmonth; // 社保服务起始月
		Integer sbsfownmonth; // 社保收费起始日

		Integer gjjfwownmonth; // 公积金服务起始月
		Integer gjjsfownmonth; // 公积金费起始日

		Integer sbbjsownmonth = 0; // 社保补缴服务起始月
		Integer sbbjeownmonth = 0; // 社保补缴终止始日

		Integer gjjbjsownmonth = 0; // 公积金补缴服务起始月
		Integer gjjbjeownmonth = 0; // 公积金补缴终止始日

		Integer sbtzownmonth; // 社保台帐月
		Integer gjjtzownmont; // 公积金台账月

		// sbtzownmonth=bll.getSbUpdateOwnmonth();//台帐所属月
		// sb_ifStop=bll.ifStop(); //社保截单日

		// sbfwownmonth=bll.getsbownmonth(gid);
		// sbsfownmonth=bll.getsbownmonth2(gid);
		for (Boolean b : sb) {

			for (Integer m1 : month) {
				for (Integer m2 : fw) {
					System.out.println("自然月:" + m1 + ",服务起始月:" + m2 + "," + b);
					if (b) {

						if (m2 > m1) {
							// 参保:服务起始月
							System.out.println("参保月份:" + m2);

						} else {
							/*
							 * 参保:自然月+1 补缴:服务起始月--自然月
							 */

							System.out.println("参保月份:"
									+ DateStringChange.ownmonthAddoneMonth(m1
											.toString()));
							System.out.println("补缴月份:" + m2 + "->" + m1);
						}
					} else {
						if (m2 > m1) {
							// 参保:服务起始月
							System.out.println("参保月份:" + m2);
						} else if (m2.equals(m1)) {
							// 参保:服务起始月
							System.out.println("参保月份:" + m2);
						} else {
							// 参保:自然月
							// 补缴:服务起始月--自然月-1
							System.out.println("参保月份:" + m1);
							System.out.println("补缴月份:"
									+ m2
									+ "->"
									+ DateStringChange.ownmonthAdd(
											m1.toString(), -1));
						}

					}
					System.out.println("****");
				}
			}
		}
	}

	private CategoryTreeNode constructCategoryTreeNode(CategoryModel cm) {
		CategoryTreeNode ct = new CategoryTreeNode(cm, 0);

		List<CategoryTreeNode> list = new LinkedList<CategoryTreeNode>();
		list.add(ct);
		CategoryTreeNode child;
		CategoryTreeNode node;
		while (!list.isEmpty()) {
			node = list.remove(0);
			for (CategoryModel childCM : node.getData().getChildren()) {
				child = new CategoryTreeNode(childCM, 0);
				node.add(child);
				list.add(child);
			}
		}
		CategoryTreeNode rootNode = new CategoryTreeNode(null, -1);
		rootNode.add(ct);
		return rootNode;
	}

	public void b(EmbaseModel m) {
		String aa = "k" + "ok";
		System.out.print(m.getArchive() == aa);
	}

	public void testA(Object o, Class<?> clazz2) {
		Class clazz = o.getClass();
		Integer i = 0;
		Method m2;

		List<EmHouseChangeGJJModel> list = new ListModelList<>();
		EmHouseBJModel em = new EmHouseBJModel();
		em.setEmhb_id(17226);
		em.setEmhb_ifdeclare(3);
		em.setEmhb_tapr_id(2134);

		try {
			// m2 = clazz.getDeclaredMethod("getGjjList", Integer.class);
			// list = (List)m2.invoke(o, 1);
			m2 = clazz.getDeclaredMethod("returnBjFlow", clazz2);
			i = (Integer) m2.invoke(o, em);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list.size());
		System.out.println(i);
	}

	public WritableCellFormat getBodyCellStyle() {

		/*
		 * WritableFont.createFont("宋体")：设置字体为宋体 10：设置字体大小
		 * WritableFont.NO_BOLD:设置字体非加粗（BOLD：加粗 NO_BOLD：不加粗） false：设置非斜体
		 * UnderlineStyle.NO_UNDERLINE：没有下划线
		 */
		WritableFont font = new WritableFont(WritableFont.createFont("宋体"), 10,
				WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE);

		WritableCellFormat bodyFormat = new WritableCellFormat(font);
		try {
			// 设置单元格背景色：表体为白色
			bodyFormat.setBackground(Colour.WHITE);
			// 设置表头表格边框样式
			// 整个表格线为细线、黑色
			bodyFormat
					.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);

		} catch (WriteException e) {
			System.out.println("表体单元格样式设置失败！");
		}
		return bodyFormat;
	}

	public Integer createExportExcel(String filename, String path,
			List<String> list) {
		Integer i = 0;
		Workbook workBook = null;
		WritableWorkbook wb = null;
		try {
			workBook = Workbook.getWorkbook(new File(path));
			wb = Workbook.createWorkbook(new File(path), workBook);
			WritableSheet sheet = wb.getSheet(0);
			WritableFont wf = new WritableFont(WritableFont.createFont("宋体"),
					11, WritableFont.NO_BOLD, false);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			for (int j = 0; j < list.size(); j++) {

			}

		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

	public Integer completeFlow(Integer dataid, Integer tapr_Id, String tbName,
			String addname) {
		Integer i = 0;
		EmOnBoardListDal dal = new EmOnBoardListDal();
		if (dal.getTask(tapr_Id)) {
			System.out.println(i);
			i = dal.completeFlow(dataid, tapr_Id, tbName);
			System.out.println(i);
			if (dal.getFinish(i).equals(0)) {
				String[] message = new String[5];
				WfBusinessService wfbs = new EmBase_DistributeImpl();
				WfOperateService wfs = new WfOperateImpl(wfbs);
				Integer taprId = dal.getTaprId(i).get(0).getTaba_tapr_id();
				Integer Id = dal.getTaprId(i).get(0).getTaba_id();
				Object[] obj = { 2, Id };

				message = wfs.PassToNext(obj, taprId, addname, "", 0, "");

			}
		}

		return i;
	}

	public void testb() throws SQLException {
		List<EmHouseChangeModel> list = new ListModelList<>();
		dbconn db = new dbconn();
		StringBuilder sql = new StringBuilder();
		sql.append("select emhc_id,gid,cid,ownmonth,emhc_companyid,emhc_company,"
				+ "emhc_shortname,emhc_name,emhc_idcard,emhc_idcardclass,emhc_hj,"
				+ "emhc_houseid,emhc_mobile,emhc_title,emhc_wifename,emhc_wifeidcard,"
				+ "emhc_degree,emhc_change,"
				+ "emhc_radix,isnull(emhc_trueradix,0)emhc_trueradix,"
				+ "emhc_cpp,emhc_opp,"
				+ "emhc_single,emhc_ifprogress,emhc_ifdeclare,convert(varchar(19),"
				+ "emhc_declaretime,120)emhc_declaretime,emhc_declarename,"
				+ "convert(varchar(19),emhc_addtime,120)emhc_addtime,emhc_addname,"
				+ "emhc_remark,convert(varchar(19),emhc_confirmtime,120)emhc_confirmtime,"
				+ "emhc_flag,emhc_flagname,emhc_excelfile,emhc_client,emhc_content,emhc_tid,"
				+ "emhc_tapr_id,"
				+ "case emhc_ifdeclare when 0 then '未申报' when 1 then '已申报' when 2 then '申报中' when 3 then '退回' when 4 then '待确认' when 5 then '审核中' end emhc_statename,"
				+ "emhc_excompany,emhc_excompanyid");
		sql.append(" from EmHouseChange");
		sql.append(" where emhc_id = ?");
		list = db.find(sql.toString(), EmHouseChangeModel.class,
				dbconn.parseSmap(EmHouseChangeModel.class), 11634);
		System.out.print(list.size());
	}

	public void testa() throws SQLException {
		SocialInsuranceCalculator sm = new SocialInsuranceCalculator();
		Integer i = sm.getSionId("深户员工", "深圳", "深圳中智经济技术合作有限公司");
		Date d = new Date();
		sm.setBcgjj(false);
		List<SocialInsuranceClassInfoViewModel> list = sm.getGjjItemFee(i,
				new BigDecimal(1000), new BigDecimal(0.05), d);
		// for (SocialInsuranceClassInfoViewModel s:list) {
		// System.out.println(s.getFee());
		// System.out.println(s.getRadix());
		// }

		System.out.println(list.get(0).getFee());
		System.out.println(list.get(0).getRadix());

	}

	public void test1() throws SQLException {

		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);
		// print out a bunch of interesting things
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		System.out.println(sdf.format(trialTime));
		System.out.println("ERA: " + calendar.get(Calendar.ERA));
		System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
		System.out.println("WEEK_OF_YEAR: "
				+ calendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("WEEK_OF_MONTH: "
				+ calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("DATE: " + calendar.get(Calendar.DATE));
		System.out.println("DAY_OF_MONTH: "
				+ calendar.get(Calendar.DAY_OF_MONTH));
		System.out
				.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
		System.out
				.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("DAY_OF_WEEK_IN_MONTH: "
				+ calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
		System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
		System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
		System.out
				.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
		System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
		System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
		System.out
				.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
		System.out.println("ZONE_OFFSET: "
				+ (calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000)));
		System.out.println("DST_OFFSET: "
				+ (calendar.get(Calendar.DST_OFFSET) / (60 * 60 * 1000)));
		System.out.println("Current Time, with hour reset to 3");
		calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override
		calendar.set(Calendar.HOUR, 3);
		System.out.println("ERA: " + calendar.get(Calendar.ERA));
		System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
		System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
		System.out.println("WEEK_OF_YEAR: "
				+ calendar.get(Calendar.WEEK_OF_YEAR));
		System.out.println("WEEK_OF_MONTH: "
				+ calendar.get(Calendar.WEEK_OF_MONTH));
		System.out.println("DATE: " + calendar.get(Calendar.DATE));
		System.out.println("DAY_OF_MONTH: "
				+ calendar.get(Calendar.DAY_OF_MONTH));
		System.out
				.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
		System.out
				.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println("DAY_OF_WEEK_IN_MONTH: "
				+ calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));

	}
}
