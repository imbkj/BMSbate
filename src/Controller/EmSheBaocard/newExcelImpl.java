package Controller.EmSheBaocard;

import java.io.File;
import java.util.List;

import bll.EmSheBaocard.EmShebaoCardInfoSelectBll;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import Model.EmShebaoCardInfoModel;
import Model.PubCodeConversionModel;
import service.ExcelService;

public class newExcelImpl implements ExcelService {

	private String file;// excel文件对象
	private String filename;// sheet名称
	private List<EmShebaoCardInfoModel> list;

	public newExcelImpl(String file, String filename,
			List<EmShebaoCardInfoModel> lists) {
		// TODO Auto-generated method stub
		this.file = file;
		this.filename = filename;
		this.list = lists;
	}

	@Override
	public void writeExcel() throws Exception {
        Workbook wk=Workbook.getWorkbook(new File(file));
       
        // 使用模板创建
     	WritableWorkbook wbook = Workbook.createWorkbook(new File(
     			 filename), wk);
        WritableSheet  sheet = wbook.getSheet(0);
		// 开始写入内容
		int row =2,y=1;
		EmShebaoCardInfoSelectBll bll=new EmShebaoCardInfoSelectBll();
		List<PubCodeConversionModel> classlist =bll.getPubCodeConversionList(36,"证件类型");//证件类型
		List<PubCodeConversionModel> edulist = bll.getPubCodeConversionList(36,"文化程度");//文化程度
		List<PubCodeConversionModel> marylist =bll.getPubCodeConversionList(36,"婚姻状况");//婚姻状况
		List<PubCodeConversionModel> joblist =bll.getPubCodeConversionList(36,"职业性质");//职业
		List<PubCodeConversionModel> folklist =bll.getFolkList("");//民族
		List<PubCodeConversionModel> provlist =bll.getPubProvinceList("");//联系地址省
		List<PubCodeConversionModel> hjcitylist =bll.getCityList("");//户籍城市
		for (int i=0; i < list.size(); i++) {
			// 用model获取每一行数据
			EmShebaoCardInfoModel model = list.get(i);
			// 将每列数据写入工作表中
			Label label = null;
			label = new Label(0, row, y+"",getHeader());
			sheet.addCell(label);
			label = new Label(1, row,model.getSbcd_sbnumber(),getHeader());
			sheet.addCell(label);
			label = new Label(2, row, model.getSbcd_companyname(),getHeader());
			sheet.addCell(label);
			label = new Label(3, row, model.getSbcd_name(),getHeader());
			sheet.addCell(label);
			label = new Label(4, row, model.getSbcd_computerid(),getHeader());
			sheet.addCell(label);
			String idcalass=getCodes(classlist,model.getSbcd_idcardclass());
			if(idcalass==null||idcalass.equals(""))
			{
				idcalass="9";
			}
			label = new Label(5, row, idcalass,getHeader());
			sheet.addCell(label);
			label = new Label(6, row, model.getSbcd_idcard(),getHeader());
			sheet.addCell(label);
			label = new Label(7, row, getDatetype(model.getSbcd_idcardstartdate()),getHeader());
			sheet.addCell(label);
			label = new Label(8, row, getDatetype(model.getSbcd_idcardenddate()),getHeader());
			sheet.addCell(label);
			label = new Label(9, row, getSex(model.getSbcd_sex()),getHeader());
			sheet.addCell(label);
			label = new Label(10, row, getDatetype(model.getSbcd_birthday()),getHeader());
			sheet.addCell(label);
			label = new Label(11, row, getFolk(folklist,model.getSbcd_folk()),getHeader());
			sheet.addCell(label);
			if(model.getSbcd_mobile()!=null&&!model.getSbcd_mobile().equals(""))
			{
				label = new Label(12, row, model.getSbcd_mobile(),getHeader());
				sheet.addCell(label);
			}
			else//没有联系电话默认为：75583323640
			{
				label = new Label(12, row, "075583323640",getHeader());
				sheet.addCell(label);
			}
			label = new Label(13, row, "440000",getHeader());//440000为广东省
			sheet.addCell(label);
			label = new Label(14, row, "440300",getHeader());//440300为深圳市
			sheet.addCell(label);
			if(model.getSbcd_address()!=null&&!model.getSbcd_address().equals(""))
			{
				label = new Label(15, row, model.getSbcd_address(),getHeader());
				sheet.addCell(label);
			}
			else//地址默认公司地址
			{
				label = new Label(15, row, "深圳市福田区深南中路1002号新闻大厦31楼",getHeader());
				sheet.addCell(label);
			}
			label = new Label(16, row, model.getSbcd_photonum(),getHeader());
			sheet.addCell(label);
			label = new Label(17, row, getCode(provlist,model.getSbcd_hjprovince()),getHeader());
			sheet.addCell(label);
			label = new Label(18, row, getCode(hjcitylist,model.getSbcd_hjcity()),getHeader());
			sheet.addCell(label);
			if(model.getSbcd_education()==null||model.getSbcd_education().equals(""))
			{
				label = new Label(19, row, "99",getHeader());//默认99（其他）
				sheet.addCell(label);
			}
			else
			{
				label = new Label(19, row, getCode(edulist,model.getSbcd_education()),getHeader());
				sheet.addCell(label);
			}
			if(model.getSbcd_marry()!=null&&!model.getSbcd_marry().equals(""))
			{
				label = new Label(20, row, getCode(marylist,model.getSbcd_marry()),getHeader());
				sheet.addCell(label);
			}
			else
			{
				label = new Label(20, row,"09",getHeader());//默认09（未说明婚姻状况）
				sheet.addCell(label);
			}
			if(model.getSbcd_hj()!=null&&!model.getSbcd_hj().equals(""))
			{
				String hjcode="2";//2代表市外城镇；1表示市内城镇
				if(model.getSbcd_hj().equals("深户"))
				{
					hjcode="1";
				}
				label = new Label(21, row,hjcode,getHeader());
				sheet.addCell(label);
			}
			else
			{
				label = new Label(21, row,"2",getHeader());//为空的话默认市外城镇
				sheet.addCell(label);
			}
			label = new Label(22, row, model.getSbcd_agencies(),getHeader());
			sheet.addCell(label);
			String job="11";//默认11
			if(model.getSbcd_job()!=null&&!model.getSbcd_job().equals(""))
			{
				job=getCode(joblist,model.getSbcd_job());
			}
			label = new Label(23, row, job,getHeader());
			sheet.addCell(label);
			label = new Label(24, row, "",getHeader());
			sheet.addCell(label);
			label = new Label(25, row, model.getSbcd_remark(),getHeader());
			sheet.addCell(label);
			row++;
			y++;
			if(y>4)
			{
				sheet.insertRow(row);
			}
		}
		// 写入数据
		wbook.write();
		// 关闭文件
		wbook.close();
	}

	// 根据中文查询代号
	private String getCode(List<PubCodeConversionModel> list, String name) {
		String code = "";
		if(name==null)
		{
			name="";
		}
		for (PubCodeConversionModel m : list) {
			if (m.getPcco_cnname() != null) {
				if (m.getPcco_cn() != null && m.getPcco_cnname().equals(name)) {
					code = m.getPcco_code();
					break;
				}
			} else {
				if (m.getPcco_cn() != null && m.getPcco_cn().equals(name)) {
					code = m.getPcco_code();
					break;
				}
			}
		}
		if (code == null) {
			code = "";
		}
		return code;
	}

	// 根据中文查询代号
	private String getCodes(List<PubCodeConversionModel> list, String name) {
		String code = "";
		if(name==null)
		{
			name="";
		}
		for (PubCodeConversionModel m : list) {
			if (m.getPcco_cnname() != null) {
				if (m.getPcco_cn() != null && m.getPcco_cnname().contains(name))// 在字符中包含name
				{
					code = m.getPcco_code();
					break;
				}
			} else {
				if (m.getPcco_cn() != null && m.getPcco_cn().contains(name))// 在字符中包含name
				{
					code = m.getPcco_code();
					break;
				}
			}
		}
		if (code == null) {
			code = "";
		}
		return code;
	}

	// 获取民族代号
	private String getFolk(List<PubCodeConversionModel> list, String name) {
		String code = "";
		if(name==null)
		{
			name="";
		}
		try {
			for (PubCodeConversionModel m : list) {
				if (m.getPcco_cnname() != null) {
					if (m.getPcco_cn() != null
							&& m.getPcco_cnname().contains(name))// 在字符中包含name
					{
						code = m.getPcco_id() + "";
						break;
					}
				} else {
					if (m.getPcco_cn() != null && m.getPcco_cn().contains(name))// 在字符中包含name
					{
						code = m.getPcco_id() + "";
						break;
					}
				}
			}
		} catch (Exception e) {

		}
		if (code == null || code.equals("")) {
			code = "99";// 99代表其他
		}
		return code;
	}

	// 获取性别代号
	private String getSex(String sex) {
		String sexcode = "9";
		if (sex != null && !sex.equals("")) {
			if (sex.contains("男")) {
				sexcode = "1";
			}
			if (sex.contains("女")) {
				sexcode = "2";
			} else {
				sexcode = "9";
			}
		}
		return sexcode;
	}

	// 替换日期的-,由yyyy-MM-dd 编程yyyyMMdd格式
	private String getDatetype(String str) {
		String newstr = "";
		if (str != null && !str.equals("")) {
			newstr = str.replace("-", "");
		}
		return newstr;
	}

	/**
	 * 设置头的样式
	 * 
	 * @return
	 */
	public static WritableCellFormat getHeader() {
		WritableCellFormat format = new WritableCellFormat();
		try {
			format.setAlignment(jxl.format.Alignment.CENTRE);// 左右居中
			format.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 上下居中
			format.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);// 黑色边框
		} catch (WriteException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return format;
	}
}
