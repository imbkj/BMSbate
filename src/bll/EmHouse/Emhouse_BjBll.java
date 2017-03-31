package bll.EmHouse;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.zkoss.zul.ListModelList;

import Model.CoBaseModel;
import Model.EmHouseBJModel;
import Model.EmHouseChangeModel;
import Model.EmHouseUpdateModel;
import Model.EmbaseModel;
import Model.PubCodeConversionModel;
import Model.PubStateModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.MonthListUtil;
import dal.PubCodeConversionDal;
import dal.CoBase.CoBase_SelectDal;
import dal.EmHouse.EmHouseChangeDal;
import dal.EmHouse.EmHouseUpdateDal;
import dal.EmHouse.Emhouse_BjDal;
import dal.Embase.EmBaseLogin_AddDal;
import dal.Embase.Embasedal;
import dal.SystemControl.UserListDal;

public class Emhouse_BjBll {
	Date nowDate = new Date(); // 获取当前时间
	String nowmonth = DateStringChange.DatetoSting(nowDate, "yyyyMM");

	// 查询补缴信息
	public List<EmHouseBJModel> getEmhouseBjInfo(String str) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		if (str == null) {
			str = "";
		}
		return dal.getEmhouseBjInfo(str, false);
	}

	// 查询补缴信息(受托台账页面调用)
	public List<EmHouseBJModel> getEmhouseBjInfoZYT(String str) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		if (str == null) {
			str = "";
		}
		return dal.getEmhouseBjInfoZYT(str, false);
	}

	// 获取所属月份列表
	public List<String> getOwnmonthList(Integer gid) {
		List<String> list = new ArrayList<>();
		MonthListUtil mu = new MonthListUtil();
		String[] s_ownmonth = new String[3];
		EmHouseSetBll bll = new EmHouseSetBll();
		Integer ownmonth = bll.nowmonth2(gid);
		s_ownmonth = mu.getMonthList(true, ownmonth.toString(), "f", 3);
		for (String s : s_ownmonth) {
			list.add(s);
		}
		return list;
	}

	// 查询补缴信息
	public List<EmHouseBJModel> getemhousebjList(Integer gid, Integer ownmonth) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		EmHouseBJModel m = new EmHouseBJModel();
		m.setGid(gid);
		m.setOwnmonth(ownmonth);
		m.setSelf(true);
		list = dal.housebjList(m, false, null, false, null, null, false);
		return list;
	}

	// 查询补缴
	public List<EmHouseBJModel> emhousebjList(EmHouseBJModel em) {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		list = dal.housebjList(em, false, null, false, null, null, false);
		return list;
	}

	// 查询公司信息
	public List<CoBaseModel> getcompanylist(String name) {
		List<CoBaseModel> list = new ListModelList<>();
		CoBase_SelectDal dal = new CoBase_SelectDal();
		list = dal.getInfoListByName("%" + name + "%");
		return list;
	}

	// 获取客服列表
	public List<loginroleModel> getclientList(String dept) {
		List<loginroleModel> list = new ListModelList<>();
		UserListDal dal = new UserListDal();
		try {
			list = dal.getdistinctColumn("log_name", "", dept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 查询员工信息
	public List<EmbaseModel> getembaseList(String cid, String name) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		try {
			list = dal.getInfoListByName("%" + cid + "%", "%" + name + "%");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// 获取所属月份列表
	public List<EmHouseBJModel> getownmontList() {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		EmHouseBJModel em = new EmHouseBJModel();
		list = dal.housebjList(em, false, null, true, "ownmonth",
				" ownmonth desc", true);
		return list;
	}

	// 获取员工基本信息
	public List<EmbaseModel> getembaseInfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		Embasedal dal = new Embasedal();
		list = dal.getEmBaseById(id);
		return list;
	}

	// 获取员工在册信息
	public List<EmbaseModel> embaseinfo(Integer id) {
		List<EmbaseModel> list = new ListModelList<>();
		EmBaseLogin_AddDal dal = new EmBaseLogin_AddDal();
		list = dal.getembaseInfo(id);
		return list;
	}

	// 获取添加人
	public List<EmHouseBJModel> getaddnamelist() {
		List<EmHouseBJModel> list = new ListModelList<>();
		Emhouse_BjDal dal = new Emhouse_BjDal();
		EmHouseBJModel em = new EmHouseBJModel();
		list = dal.housebjList(em, false, null, true, "emhb_addname",
				" emhb_addname desc", true);

		return list;
	}

	// 获取当月变更数
	public Integer getchangeNum() {
		Integer i = 0;
		Emhouse_BjDal dal = new Emhouse_BjDal();
		List<EmHouseBJModel> list = new ListModelList<>();
		for (Integer j = 0; j < 3; j++) {
			list = dal.getNum(nowmonth, j.toString(), null);
			if (list.size() > 0) {
				if (list.get(0).getNum() != null) {
					i += list.get(0).getNum();
				}

			}
		}

		return i;
	}

	// 获取当月已处理数
	public Integer getfinishNum() {

		Integer i = 0;
		Emhouse_BjDal dal = new Emhouse_BjDal();
		List<EmHouseBJModel> list = dal.getNum(nowmonth, "1", null);
		if (list.size() > 0) {
			if (list.get(0).getNum() != null) {
				i += list.get(0).getNum();
			}

		}

		return i;
	}

	// 获取当月中智大户变更数
	public Integer getzzNum() {
		Integer i = 0;
		Emhouse_BjDal dal = new Emhouse_BjDal();
		List<EmHouseBJModel> list = new ListModelList<>();
		for (Integer j = 0; j < 3; j++) {
			list = dal.getNum(nowmonth, j.toString(), "0");
			if (list.size() > 0) {
				if (list.get(0).getNum() != null) {
					i += list.get(0).getNum();
				}
			}
		}

		return i;
	}

	// 获取当月独立户变更数
	public Integer getsingleNum() {
		Integer i = 0;
		Emhouse_BjDal dal = new Emhouse_BjDal();
		List<EmHouseBJModel> list = new ListModelList<>();
		for (Integer j = 0; j < 3; j++) {
			list = dal.getNum(nowmonth, j.toString(), "1");
			if (list.size() > 0) {
				if (list.get(0).getNum() != null) {
					i += list.get(0).getNum();
				}

			}
		}

		return i;
	}

	public List<PubCodeConversionModel> getreasonList() {
		PubCodeConversionDal dal = new PubCodeConversionDal();
		return dal.getListInfo(38, "补缴原因");
	}

	// 查询在册数据
	public List<EmHouseUpdateModel> emhouseupdateList(Integer gid, Integer stop) {
		List<EmHouseUpdateModel> list = new ListModelList<>();
		EmHouseUpdateDal dal = new EmHouseUpdateDal();
		list = dal.houseupdateInfoByGid(gid, stop);
		return list;
	}

	// 新增公积金补缴
	public Integer emhousebjAdd(EmHouseBJModel em) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		Integer i = dal.add(em);
		return i;
	}

	// 申报公积金补缴
	public Integer EmhouseBjDeclare(EmHouseBJModel em) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		return dal.EmhouseBjDeclare(em);
	}

	// 修改数据
	public Integer emhousebjcommit(EmHouseBJModel em, Integer id) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		return dal.mod(em, id, null, null, null);
	}

	// 删除补缴数据
	public Integer emhousebjDel(Integer id) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		Integer i = dal.delById(id);
		return i;
	}

	// 删除补缴数据
	public Integer bjDel(Integer gid) {
		Emhouse_BjDal dal = new Emhouse_BjDal();
		Integer i = dal.delByGid(gid);
		return i;
	}

	public String export(List<EmHouseBJModel> list) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date trialTime = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);
		String exfileName = sdf.format(trialTime) + "bj";

		if (list.size() > 0) {
			String absolutePath = FileOperate.getAbsolutePath();
			// 创建Excel文件
			WritableWorkbook wb;
			try {
				wb = Workbook
						.createWorkbook(new File(absolutePath
								+ "OfficeFile/DownLoad/EmHouse/" + exfileName
								+ ".xls"));

				// 创建Excel工作表
				WritableSheet ws = wb.createSheet("sheet1", 0);

				jxl.write.Label lbl = null;
				WritableCellFormat wcf = getBodyCellStyle();
				String[] str = { "序号", "公司简称", "所属月份", "缴费月份", "姓名", "单位公积金",
						"个人公积金", "身份证", "基数", "比例", "补缴起始月", "补缴终止月", "总额",
						"客服", "状态" };
				ws.insertRow(0);
				ws.setRowView(0, 375, false);
				Integer i = 0;
				for (String s : str) {
					lbl = new jxl.write.Label(i, 0, s, wcf);
					ws.setColumnView(i, 13);
					ws.addCell(lbl);
					i++;
				}
				i = 1;
				for (EmHouseBJModel ebm : list) {
					lbl = new jxl.write.Label(0, i, i.toString(), wcf);
					ws.addCell(lbl);

					lbl = new jxl.write.Label(1, i, ebm.getEmhb_company(), wcf);
					ws.addCell(lbl);

					if (ebm.getOwnmonth() != null) {
						lbl = new jxl.write.Label(2, i, ebm.getOwnmonth()
								.toString(), wcf);
						ws.addCell(lbl);
					}

					if (ebm.getEmhb_feemonth() != null) {
						lbl = new jxl.write.Label(3, i, ebm.getEmhb_feemonth()
								.toString(), wcf);
						ws.addCell(lbl);
					}
					lbl = new jxl.write.Label(4, i, ebm.getEmhb_name()
							.toString(), wcf);
					ws.addCell(lbl);

					if (ebm.getEmhb_companyid() != null) {
						lbl = new jxl.write.Label(5, i, ebm.getEmhb_companyid()
								.toString(), wcf);
						ws.addCell(lbl);
					}
					if (ebm.getEmhb_houseid() != null) {
						lbl = new jxl.write.Label(6, i, ebm.getEmhb_houseid()
								.toString(), wcf);
						ws.addCell(lbl);
					}

					if (ebm.getEmhb_idcard() != null) {
						lbl = new jxl.write.Label(7, i, ebm.getEmhb_idcard()
								.toString(), wcf);
						ws.addCell(lbl);
					}

					lbl = new jxl.write.Label(8, i, String.valueOf(ebm
							.getEmhb_radix()), wcf);
					ws.addCell(lbl);
					lbl = new jxl.write.Label(9, i, String.valueOf(ebm
							.getEmhb_cpp()), wcf);
					ws.addCell(lbl);

					if (ebm.getEmhb_startmonth() != null) {
						lbl = new jxl.write.Label(10, i, ebm
								.getEmhb_startmonth().toString(), wcf);
						ws.addCell(lbl);
					}

					if (ebm.getEmhb_stopmonth() != null) {
						lbl = new jxl.write.Label(11, i, ebm
								.getEmhb_stopmonth().toString(), wcf);
						ws.addCell(lbl);
					}

					if (ebm.getEmhb_total() != null) {
						lbl = new jxl.write.Label(12, i, ebm.getEmhb_total()
								.toString(), wcf);
						ws.addCell(lbl);
					}

					if (ebm.getClient() != null) {
						lbl = new jxl.write.Label(13, i, ebm.getClient()
								.toString(), wcf);
						ws.addCell(lbl);
					}

					if (ebm.getStates() != null) {
						lbl = new jxl.write.Label(14, i, ebm.getStates()
								.toString(), wcf);
						ws.addCell(lbl);
					}
					i++;
				}
				// 写入数据
				wb.write();
				// 关闭文件
				wb.close();

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return exfileName;
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
}
