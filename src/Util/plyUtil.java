package Util;

import java.io.File;
import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;

import bll.EmCommissionOut.EmCommissionOutListBll;

import Model.EmCommissionOutPayUpdateCRTModel;
import Model.EmCommissionOutPayUpdateFeeDetailModel;
import Model.EmCommissionOutPayUpdateModel;

public class plyUtil {

	// 将SET转换成STRING(去掉"["和"]")
	public String SetToString(Set<Object> s) {
		String str = s.toString().replace("[", "").replace("]", "")
				.replace(" ", "");
		return str;
	}

	// 截取字符串,长度为len,截取后加...
	public static String substr(String str, int len) {
		if (str != null && !str.equals("")) {
			if (str.length() > len) {
				str = str.substring(0, len) + "...";
			}
		}
		return str;
	}

	// 获取grid的行数
	public int getGridCount(Grid gd) {
		return gd.getRows().getChildren().size();
	}

	// 获取绝对路径
	public String getAbsolutePath(String path, String filename, Object classname) {
		String abPath = "";

		abPath = classname.getClass().getResource(path).getFile();
		System.out.println(abPath);
		abPath = abPath.replace("%20", " ").substring(1);
		abPath += filename;

		return abPath;
	}

	/**
	 * 读取excel表头
	 * 
	 * @param file
	 *            excel文件
	 * @param titlerow
	 *            表头行数
	 * @return Object[0] 0为失败、1为成功;Object[1] 失败原因;Object[2] 表头列表
	 */
	public static Object[] readExcel(File file, Integer titlerow) {
		List<String> titleList = new ListModelList<>();
		Object[] obj = { "0", "", titleList };
		Workbook workbook = null;
		Sheet sheet = null;
		Cell[] cells = null;

		flagA: {
			try {
				// 读取excel文件
				workbook = Workbook.getWorkbook(file);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (workbook == null) {
				obj[0] = "0";
				obj[1] = "无法读取excel";
				obj[2] = null;

				break flagA;
			} else {

				try {
					// 获取第一个工作表
					sheet = workbook.getSheet(0);
				} catch (Exception e) {
					obj[0] = "0";
					obj[1] = "无法读取第一个工作表";
					obj[2] = null;

					break flagA;
				}

				// 获取所选行的所有单元格
				try {
					cells = sheet.getRow(titlerow - 1);
				} catch (Exception e) {

				}

				if (cells != null && cells.length > 0) {
					for (int i = 0; i < cells.length; i++) {
						if (!cells[i].getType().equals("Empty")) {
							titleList.add(cells[i].getContents());
						}
					}

					if (titleList.size() > 0) {
						obj[0] = "1";
						obj[1] = "";
						obj[2] = titleList;
					} else {
						obj[0] = "0";
						obj[1] = "所选行内无任何内容";
						obj[2] = null;

						break flagA;
					}
				} else {
					obj[0] = "0";
					obj[1] = "无法读取所选行";
					obj[2] = null;

					break flagA;
				}
			}
		}

		return obj;
	}

	/**
	 * @param file
	 *            excel文件
	 * @param datarow
	 *            数据第一行行数
	 * @param fieldList
	 *            表头与系统字段对应的列表
	 * @return Object[0] 0为失败、1为成功;Object[1] 失败原因;Object[2] 影响行数; Object[3]
	 *         表内数据列表
	 */
	public Object[] ReadEmOutPayExcel(File file, Integer datarow,
			List<EmCommissionOutPayUpdateCRTModel> fieldList) {
		List<EmCommissionOutPayUpdateModel> list = new ListModelList<>();
		Object[] obj = { "0", "", 0, list };
		Integer row = 0;
		Workbook workbook = null;
		Sheet sheet = null;
		Cell[] cells = null;

		flagA: {
			try {
				// 读取excel文件
				workbook = Workbook.getWorkbook(file);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (workbook == null) {
				obj[0] = "0";
				obj[1] = "无法读取excel";
				obj[2] = 0;
				obj[3] = null;

				break flagA;
			} else {
				try {
					// 获取第一个工作表
					sheet = workbook.getSheet(0);
				} catch (Exception e) {
					obj[0] = "0";
					obj[1] = "无法读取第一个工作表";
					obj[2] = 0;
					obj[3] = null;

					break flagA;
				}

				// 获取所选行的所有单元格
				try {
					// 从传参的数据第一行开始循环获取数据
					for (int i = datarow - 1; i < sheet.getRows(); i++) {
						// 获取每行的列集合
						cells = sheet.getRow(i);
						if (cells != null && cells.length > 0) {
							// 表头计数(防止遇到合并列时计数出错,所以单独计数)
							Integer fieldIndex = 0;
							// 声明数据model
							EmCommissionOutPayUpdateModel pum = new EmCommissionOutPayUpdateModel();
							// 声明数据费用明细列表
							List<EmCommissionOutPayUpdateFeeDetailModel> feeList = pum
									.getFeeList();
							// 遍历列集合,获取数据
							for (int j = 0; j < cells.length; j++) {
								// 获取表头对应的字段model
								EmCommissionOutPayUpdateCRTModel crtm = fieldList
										.get(fieldIndex);
								// 判断是否合并列
								if (!cells[j].getType().equals("Empty")) {

									// 判断excel表头、系统字段是否不为空
									if (crtm.getEcuc_excel_title() != null
											&& crtm.getEcuc_excel_title1() != null
											&& crtm.getEcpr_ecpu_fieldstr() != null) {

										// 获取单元格的值
										Object cellContent = null;

										try {
											if (crtm.getEcpr_type().equals(
													"BigDecimal")) {
												cellContent = new BigDecimal(
														cells[j].getContents());
											} else if (crtm.getEcpr_type()
													.equals("Integer")) {
												if (crtm.getEcpr_ecpu_field()
														.equals("bjownmonth")) {
													try {
														cellContent = Handle_ownmonth(cells[j]
																.getContents());
													} catch (Exception e) {
														e.printStackTrace();
													}
												} else {
													try {
														cellContent = Integer
																.parseInt(cells[j]
																		.getContents()
																		.toString());
													} catch (Exception e) {
														cellContent = null;
													}
												}
											} else {
												cellContent = cells[j]
														.getContents();

												// 如果比例字段是以20%的形式记录，则转为0.2的形式
												if (crtm.getEcpr_ecpu_field()
														.equals("epfd_cp")
														|| crtm.getEcpr_ecpu_field()
																.equals("epfd_op")) {
													if (RegexUtil
															.isExists(
																	"%",
																	cellContent
																			.toString())) {
														try {
															cellContent = cellContent
																	.toString()
																	.replace(
																			"%",
																			"");
															Double double1 = new Double(
																	cellContent
																			.toString());
															double1 = double1 / 100;
															cellContent = double1
																	.toString();
														} catch (Exception e) {
															e.printStackTrace();
														}
													}
												}
											}
										} finally {
											// 将excel单元格的值放入对应属性
											if (cellContent != null) {
												if (crtm.getEcpr_sicl_id() == null
														&& !crtm.getEcuc_ecpr_id()
																.equals(0)) {
													pum.setField(
															crtm.getEcpr_ecpu_field(),
															cellContent,
															getTypeClass(crtm
																	.getEcpr_type()));
												} else if (crtm
														.getEcuc_ecpr_id()
														.equals(0)) {
													// 福利项目
													EmCommissionOutPayUpdateFeeDetailModel fm = new EmCommissionOutPayUpdateFeeDetailModel();

													fm.setEpfd_sicl_id(0);
													fm.setEpfd_name(crtm
															.getEcpr_ecpu_fieldstr());
													fm.setField(
															crtm.getEcpr_ecpu_field(),
															cellContent,
															getTypeClass(crtm
																	.getEcpr_type()));

													feeList.add(fm);
												} else if (crtm
														.getEcpr_sicl_id() != null) {
													// 五险一金
													boolean flag = false;

													// 判断是否已经加入了此项目
													for (EmCommissionOutPayUpdateFeeDetailModel fm : feeList) {
														if (crtm.getEcpr_sicl_id()
																.equals(fm
																		.getEpfd_sicl_id())) {
															fm.setField(
																	crtm.getEcpr_ecpu_field(),
																	cellContent,
																	getTypeClass(crtm
																			.getEcpr_type()));

															flag = true;
															break;
														}
													}
													if (!flag) {
														EmCommissionOutPayUpdateFeeDetailModel fm = new EmCommissionOutPayUpdateFeeDetailModel();

														fm.setEpfd_sicl_id(crtm
																.getEcpr_sicl_id());
														fm.setEpfd_name(new EmCommissionOutListBll()
																.getSoClassNameBySiclId(crtm
																		.getEcpr_sicl_id()));
														fm.setField(
																crtm.getEcpr_ecpu_field(),
																cellContent,
																getTypeClass(crtm
																		.getEcpr_type()));

														feeList.add(fm);
													}
													flag = false;
												}
											}
										}
									}

									// 表头计数自增(步长：1)
									fieldIndex++;
								}
							}
							pum.setEcpu_state(1);
							pum.setEcpu_addname(UserInfo.getUsername());
							pum.setFeeList(feeList);
							list.add(pum);
							row++;
						}
					}

					if (list.size() > 0) {
						// 将没有身份证的数据清除
						for (int i = 0; i < list.size(); i++) {
							EmCommissionOutPayUpdateModel pum = list.get(i);
							if (pum.getEcpu_idcard() == null
									|| pum.getEcpu_idcard().isEmpty()) {
								list.remove(pum);
								i--;
								row--;
							}
						}
					}

					if (list.size() > 0) {
						obj[0] = "1";
						obj[1] = "读取成功!";
						obj[2] = row;
						obj[3] = list;
					} else {
						obj[0] = "0";
						obj[1] = "无数据!";
						obj[2] = 0;
						obj[3] = null;
					}
				} catch (Exception e) {
					obj[0] = "0";
					obj[1] = e.toString();
					obj[2] = 0;
					obj[3] = null;
				}
			}
		}
		return obj;
	}

	// 根据字段名获取SET方法名；
	public static String setMethod(String name) {
		return "set"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	// 根据字段名获取SET方法名；
	public static String getMethod(String name) {
		return "get"
				+ name.replaceFirst(name.substring(0, 1), name.substring(0, 1)
						.toUpperCase());
	}

	/**
	 * 根据数据类型获取数据类
	 * 
	 * @param type
	 *            数据类型的字符串
	 * @return Class 数据类
	 */
	public static Class<?> getTypeClass(String type) {
		Class<?> class1 = null;
		switch (type) {
		case "Integer":
			class1 = Integer.class;
			break;
		case "Date":
			class1 = Date.class;
			break;
		case "String":
			class1 = String.class;
			break;
		case "BigDecimal":
			class1 = BigDecimal.class;
			break;

		default:
			break;
		}

		return class1;
	}

	/**
	 * 处理付款起始月份
	 * 
	 * @param content
	 * @return
	 */
	public Integer Handle_ownmonth(Object content) {
		String ownmonth = null;
		try {
			ownmonth = content.toString();

			if (RegexUtil.isExists(".", ownmonth)) {
				ownmonth = ownmonth.replace(".", "").substring(0, 6).trim();
			} else if (RegexUtil.isExists("-", ownmonth)) {
				ownmonth = ownmonth.replace("-", "").substring(0, 6).trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ownmonth == null ? null : Integer.parseInt(ownmonth);
	}
}
