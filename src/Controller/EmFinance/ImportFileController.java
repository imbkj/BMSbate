package Controller.EmFinance;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import Model.EmFinanceZYTModel;
import Model.EmHouseTakeCardInfoModel;
import Util.FileOperate;
import bll.EmFinance.EmFinance_OperateBll;
import bll.EmFinance.EmFinance_SelectBll;

public class ImportFileController  extends SelectorComposer<Component>{
	@Wire
	private Textbox filepath;
	@Wire
	private Combobox city;
	@Wire
	private Datebox ownmonth;
	@Wire
	private Window impwin;
	private EmFinance_SelectBll bll=new EmFinance_SelectBll();
	private String filename="";
	private String nowtime="";
	private String filenames="";
	private InputStream inputStream=null;
	private Media media;
	private ListModel<String> citylist;
	private List<String> citylists;
	
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		citylists=bll.getCity();
		citylist=new ListModelList<String>(citylists);
		city.setModel(citylist);
		Executions.getCurrent().getDesktop().getWebApp().getConfiguration().setMaxUploadSize(30000000);
	}
	
	//获取上传的政策文件
	@Listen("onUpload= #attachBtn")
	public void uploadfile(@ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev)
	{
		try{
		media=null;
		inputStream=null;
		media=ev.getMedia();
		if(media!=null)
		{
			if(media.getFormat().equals("xls")||media.getFormat()=="xls"||media.getFormat().equals("xlsx")||media.getFormat()=="xlsx")
			{
				try {
					this.filename=media.getName();
					this.inputStream=media.getStreamData();
					filepath.setValue(filename);
				}catch(Exception e)
				{
					System.out.println("错误:"+e.getMessage());
				}
			}
			else
			{
				Messagebox.show("选择的文件格式不正确", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Listen("onClick= #summit")
	public void addTakeCardInfo(){
		if(ownmonth.getValue()!=null&&!ownmonth.getValue().equals(""))
		{
			if(city.getValue()!=null&&!city.getValue().equals(""))
			{
				String realPath="EmFinance/uploadfile/";SimpleDateFormat form = new SimpleDateFormat("yyyyMM");
				
				if(inputStream!=null&&!inputStream.equals(""))
				{
					if(media!=null)
					{
						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
						Date date = new Date();
						nowtime= formatter.format(date);
						String fname=nowtime.trim()+filename.trim();
						String name = FileOperate.getAbsolutePath() +realPath+ fname;
						filenames=fname;
						boolean flag=FileOperate.upload(media, realPath+fname);
						if(flag)
						{
							if(media.getFormat().equals("xls")||media.getFormat()=="xls")
							{
								
								Integer k=loadXls2(name);
								if(k>0)
								{
									EmFinance_OperateBll obll=new EmFinance_OperateBll();
									
									//删除不是该城市同一月份以前导入的数据，只需判断导入文件名不相等
									obll.delEmFinanceZYT(Integer.parseInt(changedate(ownmonth.getValue())), city.getValue().substring(1), fname);
									//更新已导入数据的CID与GID
									obll.EmFinanceZYTEdit(Integer.parseInt(changedate(ownmonth.getValue())), fname);
									Messagebox.show("导入成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
									Executions.sendRedirect("/EmFinance/ImportFile.zul");
								}
								else
								{
									Messagebox.show("导入失败", "提示", Messagebox.OK, Messagebox.ERROR);
								}
							}
							else if(media.getFormat().equals("xlsx")||media.getFormat()=="xlsx")
							{
								loadXlsx(name);
							}
						}
					}
				}
				else
				{
					Messagebox.show("请选择导入文件", "提示", Messagebox.OK, Messagebox.ERROR);
				}	
			}
			else
			{
				Messagebox.show("请选择委托城市", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show("请选择所属月份", "提示", Messagebox.OK, Messagebox.ERROR);
		}	
	}
	
	 /** 
	  * 读取office 2003 xls 
	  * @param filePath 
	  */  
	 public Integer loadXls2(String filepath){
		 String own=changedate(ownmonth.getValue());
		 String cityval=city.getValue();
		 Integer k=0;
		 if(own!=null&&!own.equals(""))
		 {
			 if(cityval!=null&&!cityval.equals(""))
			 {
				 try{
					// 创建对Excel工作簿文件的引用
					HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filepath));
					// 创建对工作表的引用。
					// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
					HSSFSheet sheet = workbook.getSheet("Sheet1");
					// 也可用getSheetAt(int index)按索引引用，
					// 在Excel文档中，第一张工作表的缺省索引是0，
					// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
					// 读取左上端单元
					HSSFRow row ;
					for(int i=sheet.getFirstRowNum();i<sheet.getPhysicalNumberOfRows();i++)
					{
						row = sheet.getRow(i);
						// 输出单元内容，cell.getStringCellValue()就是取所在单元的值
						if(i>0)
							{
								EmFinanceZYTModel model=new EmFinanceZYTModel();
								row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);//[执行城市]
								row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);//委托方机构序号
								row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);//[受托方机构序号]
								row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);//[公司名称]
								row.getCell(73).setCellType(Cell.CELL_TYPE_STRING);//[智翼通雇员编号]
								row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);//[雇员姓名]
								row.getCell(13).setCellType(Cell.CELL_TYPE_STRING);//[收费类型]
								row.getCell(16).setCellType(Cell.CELL_TYPE_STRING);//[备注]
								row.getCell(17).setCellType(Cell.CELL_TYPE_STRING);//[应收养老金]
								row.getCell(18).setCellType(Cell.CELL_TYPE_STRING);//[应收养老个人缴费]
								row.getCell(19).setCellType(Cell.CELL_TYPE_STRING);//[应收养老公司缴费]
								row.getCell(20).setCellType(Cell.CELL_TYPE_STRING);//[应收医疗金]
								row.getCell(21).setCellType(Cell.CELL_TYPE_STRING);//[应收医疗个人缴费]
								row.getCell(22).setCellType(Cell.CELL_TYPE_STRING);//[应收医疗公司缴费]
								row.getCell(23).setCellType(Cell.CELL_TYPE_STRING);//[应收失业金]
								row.getCell(24).setCellType(Cell.CELL_TYPE_STRING);//[应收失业个人缴费]
								row.getCell(25).setCellType(Cell.CELL_TYPE_STRING);//[应收失业公司缴费]
								row.getCell(26).setCellType(Cell.CELL_TYPE_STRING);//[应收生育金]
								row.getCell(27).setCellType(Cell.CELL_TYPE_STRING);//[应收生育个人缴费]
								row.getCell(28).setCellType(Cell.CELL_TYPE_STRING);//[应收生育公司缴费]
								row.getCell(29).setCellType(Cell.CELL_TYPE_STRING);//[应收工伤金]
								row.getCell(30).setCellType(Cell.CELL_TYPE_STRING);//[应收工伤个人缴费]
								row.getCell(31).setCellType(Cell.CELL_TYPE_STRING);//[应收工伤公司缴费]
								row.getCell(32).setCellType(Cell.CELL_TYPE_STRING);//[应收公积金]
								row.getCell(33).setCellType(Cell.CELL_TYPE_STRING);//[应收公积个人缴费]
								row.getCell(34).setCellType(Cell.CELL_TYPE_STRING);//[应收公积公司缴费]
								row.getCell(35).setCellType(Cell.CELL_TYPE_STRING);//[应收补充公积金]
								row.getCell(36).setCellType(Cell.CELL_TYPE_STRING);//[应收补充公积个人缴费]
								row.getCell(37).setCellType(Cell.CELL_TYPE_STRING);//[应收补充公积公司缴费]
								row.getCell(38).setCellType(Cell.CELL_TYPE_STRING);//[应收其中社保费]
								row.getCell(40).setCellType(Cell.CELL_TYPE_STRING);//[应收服务产品]
								row.getCell(41).setCellType(Cell.CELL_TYPE_STRING);//[应收服务费]
								row.getCell(42).setCellType(Cell.CELL_TYPE_STRING);//[应收档案保管费]
								row.getCell(46).setCellType(Cell.CELL_TYPE_STRING);//[调整费用]
								row.getCell(47).setCellType(Cell.CELL_TYPE_STRING);//[应收费用总计]
								row.getCell(51).setCellType(Cell.CELL_TYPE_STRING);//[养老公司基数]
								row.getCell(53).setCellType(Cell.CELL_TYPE_STRING);//[医疗公司基数]
								row.getCell(55).setCellType(Cell.CELL_TYPE_STRING);//[失业公司基数]
								row.getCell(57).setCellType(Cell.CELL_TYPE_STRING);//[工伤公司基数]
								row.getCell(59).setCellType(Cell.CELL_TYPE_STRING);//[生育公司基数]
								row.getCell(61).setCellType(Cell.CELL_TYPE_STRING);//[公积金公司基数]
								row.getCell(63).setCellType(Cell.CELL_TYPE_STRING);//[补充公积金公司基数]
								row.getCell(64).setCellType(Cell.CELL_TYPE_STRING);//[证件号码]
								
								model.setScompany(row.getCell(4).getStringCellValue());
								model.setScity(cityval.substring(1));
								model.setRcity(row.getCell(3).getStringCellValue());
								model.setRcompany(row.getCell(5).getStringCellValue());
								model.setEmfz_company(row.getCell(7).getStringCellValue());
								model.setEmfz_zgid(row.getCell(73).getStringCellValue());
								model.setEmfz_name(row.getCell(9).getStringCellValue());
								model.setEmfz_idcard(row.getCell(64).getStringCellValue());
								model.setOwnmonth(Integer.parseInt(own));
								model.setEmfz_yltotal(strtoBig(row.getCell(17).getStringCellValue()));
								model.setEmfz_syetotal(strtoBig(row.getCell(23).getStringCellValue()));
								model.setEmfz_gstotal(strtoBig(row.getCell(29).getStringCellValue()));
								model.setEmfz_syutotal(strtoBig(row.getCell(26).getStringCellValue()));
								model.setEmfz_jltotal(strtoBig(row.getCell(20).getStringCellValue()));
								model.setEmfz_housetotal(strtoBig(row.getCell(32).getStringCellValue()));
								model.setEmfz_bjtotal(strtoBig(row.getCell(35).getStringCellValue()));
								model.setEmfz_sbtotal(strtoBig(row.getCell(38).getStringCellValue()));
								model.setEmfz_elsefee(strtoBig(row.getCell(46).getStringCellValue()));
								model.setEmfz_sbchange(row.getCell(13).getStringCellValue());
								model.setEmfz_servertotal(strtoBig(row.getCell(40).getStringCellValue()));
								model.setEmfz_fee(strtoBig(row.getCell(41).getStringCellValue()));
								model.setEmfz_filefee(strtoBig(row.getCell(42).getStringCellValue()));
								model.setEmfz_total(strtoBig(row.getCell(47).getStringCellValue()));
								model.setEmfz_remark(row.getCell(16).getStringCellValue());
								model.setEmfz_ylcp(strtoBig(row.getCell(19).getStringCellValue()));
								model.setEmfz_ylop(strtoBig(row.getCell(18).getStringCellValue()));
								model.setEmfz_jlcp(strtoBig(row.getCell(22).getStringCellValue()));
								model.setEmfz_jlop(strtoBig(row.getCell(21).getStringCellValue()));
								model.setEmfz_gscp(strtoBig(row.getCell(31).getStringCellValue()));
								model.setEmfz_gsop(strtoBig(row.getCell(30).getStringCellValue()));
								model.setEmfz_syecp(strtoBig(row.getCell(25).getStringCellValue()));
								model.setEmfz_syeop(strtoBig(row.getCell(24).getStringCellValue()));
								model.setEmfz_syucp(strtoBig(row.getCell(28).getStringCellValue()));
								model.setEmfz_syuop(strtoBig(row.getCell(27).getStringCellValue()));
								model.setEmfz_housecp(strtoBig(row.getCell(34).getStringCellValue()));
								model.setEmfz_houseop(strtoBig(row.getCell(33).getStringCellValue()));
								model.setEmfz_bjcp(strtoBig(row.getCell(37).getStringCellValue()));
								model.setEmfz_bjop(strtoBig(row.getCell(36).getStringCellValue()));
								model.setEmfz_ylradix(strtoBig(row.getCell(51).getStringCellValue()));
								model.setEmfz_jlradix(strtoBig(row.getCell(53).getStringCellValue()));
								model.setEmfz_gsradix(strtoBig(row.getCell(57).getStringCellValue()));
								model.setEmfz_syeradix(strtoBig(row.getCell(55).getStringCellValue()));
								model.setEmfz_syuradix(strtoBig(row.getCell(59).getStringCellValue()));
								model.setEmfz_houseradix(strtoBig(row.getCell(61).getStringCellValue()));
								model.setEmfz_bjradix(strtoBig(row.getCell(63).getStringCellValue()));
								model.setEmfz_filename(filenames);
								Integer l=EmFinanceZYTAdd(model);
								if(l>0)
								{
									k=k+1;
								}
								else
								{
									k=0;
									break;
								}
								}
							}
					
					}catch(Exception e) {
						System.out.println("已运行xlRead() : " + e );
					}
			 }
		 }
		 return k;
	 }
	 
	 /** 
	  * 读取office 2007 xlsx 
	  * @param filePath 
	  */  
	 public void loadXlsx(String filepath){
		 
	 }
	
	 private Integer EmFinanceZYTAdd(EmFinanceZYTModel model)
	 {
		 EmFinance_OperateBll obll=new EmFinance_OperateBll();
		 return obll.EmFinanceZYTAdd(model);
	 }
	
	public ListModel<String> getCitylist() {
		return citylist;
	}

	public void setCitylist(ListModel<String> citylist) {
		this.citylist = citylist;
	}

	private String changedate(Date d)
	{
		String dateString="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		if(d!=null&&!d.equals(""))
		{
			dateString = formatter.format(d);
		}
		return dateString;
	}
	
	private BigDecimal strtoBig(String str)
	{
		BigDecimal bd = BigDecimal.ZERO;
		if(str!=null&&!str.equals(""))
		{
			bd=new BigDecimal(str);
		}
		return bd;
	}

}
