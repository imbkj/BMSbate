package Controller.EmCommissionOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.poi.xssf.usermodel.XSSFRow;
import org.zkoss.poi.xssf.usermodel.XSSFSheet;
import org.zkoss.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.poi.xssf.usermodel.XSSFCell;


import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;
import org.zkoss.zul.Cell;



import Model.CoBaseModel;
import Model.EmCommissionOutChangeModel;
import Model.EmCommissionOutFeeDetailChangeModel;
import Model.EmCommissionOutFeeDetailModel;
import Model.EmCommissionOutStandardModel;
import Model.EmCommissionOutimportModel;
import Model.EmCommissionYearChangemModel;
import Model.EmbaseModel;
import Model.MaintenanceRecordModel;
import Model.SocialInsuranceAlgorithmViewModel;
import Model.wtoutFeeModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.SocialInsuranceEmCommissionOut;
import Util.UserInfo;
import bll.EmCommissionOut.EmCommissionOutListBll;
import bll.EmCommissionOut.EmCommissionOut_OperateBll;
import bll.EmCommissionOut.EmCommissionOutimportBll;

public class EmCommissionOutimport_Controller {
	
    private EmCommissionOutimportBll bll =new EmCommissionOutimportBll();
    
    private List<EmCommissionOutimportModel> changelist = new ArrayList<EmCommissionOutimportModel>();
    
    private List<wtoutFeeModel> wtoutFeeList= new ArrayList<wtoutFeeModel>();
    
    private List<SocialInsuranceAlgorithmViewModel> socialInsuranceList =new ArrayList<SocialInsuranceAlgorithmViewModel>(); 
    
    private List<EmCommissionOutimportModel> list = new ArrayList<EmCommissionOutimportModel>();
    private List<EmCommissionOutChangeModel> subList = new ListModelList<>();
	

	private List<EmCommissionOutStandardModel> stardList;// 合同标准
	private List<EmCommissionOutChangeModel> cityList;// 城市
	private List<EmCommissionOutChangeModel> titleList;// 当地标准
	private List<EmCommissionOutStandardModel> fuwulist;// 服务费列表
	private List<EmCommissionOutStandardModel> filelist;// 档案费列表
	private EmCommissionOutChangeModel titleModel = new EmCommissionOutChangeModel();// 获取标准详细信息
	private List<EmCommissionOutFeeDetailChangeModel> flList = new ListModelList<>();// 获取标准福利产品信息
	//private List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
	private EmCommissionOutStandardModel stardModel = new EmCommissionOutStandardModel();
    
    private String wtot_feetitle="";//服务费名称
    private String wtss_title="";//标准名
    private String wtot_feeid="";//服务费代码
    private Integer cid=null;
    private String city="";//城市名
    private String coab_name="";//机构名
    private List<String> feetitlelist=null;//服务费名称列
    private List<String> titlelist=null;//标准名列
    private List<String> citylist=null;//城市列
    private List<String> namelist=null;//机构名列
    
    private String soin="";//当地标准名
    private String soin_id="";//当地标准代码
    private String soincity="";//当地城市
    private String soinname="";//当地机构
    private List<String> soinlist=null;//当地标准名列
    private List<String> soincitylist=null;//当地城市列
    private List<String> soinnamelist=null;//当地机构名列
    
    private String ecouname="";//员工姓名
    private String addname="";//添加人
    private List<String> ecounamelist=null;//员工姓名列
    private List<String> addnamelist=null;//添加人列
    
    private String uploadFlieName = "";
    private Media[] media;
	private InputStream inputStream = null;
	private String absolutePath = "";
	private String uploadfolder = "/OfficeFile/UpLoad/OutImport/";
	SocialInsuranceEmCommissionOut calUtil = new SocialInsuranceEmCommissionOut();		
	public EmCommissionOutimport_Controller() {
		try {
			feetitlelist= bll.getfeetitlelist();
			titlelist= bll.gettitlelist();
			citylist= bll.getcitylist();
			namelist =bll.getnamelist();
			soinlist=bll.getsoinlist();
			soincitylist=bll.getsoincitylist();
			soinnamelist=bll.getsoinnamelist();
			ecounamelist=bll.getecounamelist();
			addnamelist=bll.getaddnamelist();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		searchWtoutFee();
		searchSocialInsurance();
		searchEntrustInfo();
		
		
	}
	
	 
	
	//查询服务
	@Command("searchWtoutFee")
	@NotifyChange("wtoutFeeList")
    public void searchWtoutFee(){
		StringBuilder strsql= new StringBuilder();
		if(!wtot_feeid.trim().equals("")){
			strsql.append(" and wtot_feeid = "+wtot_feeid+"" );
		}
		if(!wtot_feetitle.trim().equals("")){
			strsql.append(" and wtot_feetitle like '%"+wtot_feetitle+"%'" );
		}
		if(!wtss_title.trim().equals("")){
			strsql.append(" and wtss_title like '%"+wtss_title+"%'" );
		}
		if(!city.trim().equals("")){
			strsql.append(" and city='"+city+"'" );
		}
		if(!city.trim().equals("")){
			strsql.append(" and coab_name='"+coab_name+"'" );
		}
		if(cid!=null){
			strsql.append(" and cid="+cid+" " );
		}
		strsql.append(" ");
		try {
			wtoutFeeList = bll.getWtoutFeeList(strsql.toString());//查询
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
	
/**	@Command
	public void checkAll(@BindingParam("gd") Grid gd,
			@BindingParam("ck") Checkbox allCk) {
		int num = gd.getPageSize();
		if (list.size() < num) {
			num = list.size();
		}
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					CoBaseModel m = ck.getValue();
					m.setChecked(allCk.isChecked());
					ck.setChecked(allCk.isChecked());
				}
			}
		}
	}

	
	@Command
	public void checkCb(@BindingParam("cel") Cell cell,
			@BindingParam("m") EmCommissionOutimportModel model) {
		Row row = (Row) cell.getParent();
		if (row.getChildren() != null && row.getChildren().size() > 0) {
			Cell rcell = (Cell) row.getChildren().get(0);
			if (rcell.getChildren() != null && rcell.getChildren().size() > 1) {
				Checkbox cb = (Checkbox) rcell.getChildren().get(1);
				if (cb.isChecked()) {
					model.setChecked(false);
					cb.setChecked(false);
				} else {
					model.setChecked(true);
					cb.setChecked(true);
				}
			}
		}
	}
	*/
	
	//全选当前页记录
	@Command("checkAll")
	public void checkAll(@BindingParam("gd") Grid gd,
				@BindingParam("ck") Checkbox allCk) {
		 int num = list.size();
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null && gd.getCell(i, 0) instanceof Cell) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					ck.setChecked(allCk.isChecked());
				}
			  }
			}
		}
	//选着的记录
	@Command("checkCb")
	public void checkCb(@BindingParam("cel") Cell cell) {
		Row row = (Row) cell.getParent();
		if (row.getChildren() != null && row.getChildren().size() > 0) {
			Cell rcell = (Cell) row.getChildren().get(0);
			if (rcell.getChildren() != null && rcell.getChildren().size() > 1) {
				Checkbox cb = (Checkbox) rcell.getChildren().get(1);
				if (cb.isChecked()) {
					cb.setChecked(false);
				 } else {
					cb.setChecked(true);
				 }
			 }
			}
		}
	

	
	//根据id删除记录
	@Command
	@NotifyChange("list")
	public void deleteUpload(@BindingParam("gd") Grid gd){
		List<EmCommissionOutimportModel> listM= new ArrayList<EmCommissionOutimportModel>();
		int num= list.size();
		for (int i = 0; i < num; i++) {
			if (gd.getCell(i, 0) != null && gd.getCell(i, 0) instanceof Cell) {
				Cell cell = (Cell) gd.getCell(i, 0);
				if (cell.getChildren().size() > 1) {
					Checkbox ck = (Checkbox) cell.getChildren().get(1);
					if (ck.isChecked()) {
						EmCommissionOutimportModel md = ck.getValue();
						listM.add(md);
					}
				}
			}
		}
		int n=0;
		if(listM.size()>0){
			 for(int j=0;j<listM.size();j++){
				 EmCommissionOutimportModel mr= listM.get(j);
				 int a=bll.deleteEntrustInfo(mr.getEcou_id()); 			//根据id删除记录
				 if (a>0){
						n++;
				  }
				
			 }
			 Messagebox.show("共删除数据"+n+"条；" , "操作提示", Messagebox.OK,Messagebox.NONE);
		}else{
			Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		
		searchEntrustInfo();
		
	}
	
	//查询
	@Command("searchSocialInsurance")
	@NotifyChange("socialInsuranceList")
	public void searchSocialInsurance(){
		StringBuilder strsql= new StringBuilder();
		if(!soin_id.trim().equals("")){
			strsql.append(" and soin_id ="+soin_id+"" );
	}
		if(!soin.trim().equals("")){
				strsql.append(" and soin_title like '%"+soin+"%'" );
		}
		if(!soincity.trim().equals("")){
				strsql.append(" and b.city like '%"+soincity+"%'" );
		}
		if(!soinname.trim().equals("")){
				strsql.append(" and b.coab_name='"+soinname+"'" );
		}
		strsql.append(" ");
		try {
			socialInsuranceList = bll.getSocialInsurance(strsql.toString());//查询
		} catch (SQLException e) {
				e.printStackTrace();
		}
	   }
	//按条件查询导入的异地excel数据
	@Command("searchEntrustInfo")
	@NotifyChange("list")
	public void searchEntrustInfo(){
		StringBuilder strsql= new StringBuilder();
		if(!ecouname.trim().equals("")){
				strsql.append(" and ecou_name like '%"+ecouname+"%'" );
		}
		if(!addname.trim().equals("")){
				strsql.append(" and ecou_addname like '%"+addname+"%'" );
		}
		strsql.append(" ");
		try {
			list= bll.getEntrustInfo(strsql.toString());
		} catch (SQLException e) {
				e.printStackTrace();
		}
	}
	// 下载模版
	@Command("downloadTemplet")
	public void downloadTemplet() {
	   String filename = "EmCommissionOutimport.xls";
	   if (filename != null && !filename.equals("")) {
			String absolutePath = FileOperate.getAbsolutePath();
			String path = absolutePath + "OfficeFile/Templet/EmCommissionOut/"+ filename;
			File file = new File(path);
			try {
				 Filedownload.save(file, "xlsx");
				} catch (FileNotFoundException e) {// TODO Auto-generated catch block
					e.printStackTrace();
			    }
		}
	}
	
	// 文件检查
	@Command("browse")
	@NotifyChange({ "uploadFlieName"})
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
			UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
			media = upEvent.getMedias();
			inputStream = null;
			for (int i = 0; i < media.length; i++) {
				if ("xls".equals(media[i].getFormat())|| "xlsx".equals(media[i].getFormat())) {
					uploadFlieName = media[i].getName();
					absolutePath = FileOperate.getAbsolutePath();
					String uploadName = mosaicFileName();
					if (FileOperate.upload(media[i], uploadfolder + uploadName)) {
						    this.inputStream = media[i].getStreamData();
						UpLoadNewAdd();
						
					 }
					} else {
						media = null;
						uploadFlieName = "";
						Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
						return;
					}
			}
		}
	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + "(" + UserInfo.getUsername() + ")"
					+ uploadFlieName;
		return name;
	}
	
	private void UpLoadNewAdd() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String gid= "";//员工编号
		String ecou_idcard= "";//身份证号码
		String ecou_name= "";//员工姓名
		String ecou_soin_id= "";//当地标准代码
		String ecou_ecos_id= "";//服务费代码	
		String ecou_email= "";//邮箱
		String ecou_mobile= "";//手机号
		String ecou_filestate= "";//是否保管档案
		Date ecou_compact_f= null;//合同起始时间
		Date ecou_compact_l= null;//合同结束时间
		String ecou_com_phone= "";//工作电话
		String ecou_sb_base= "";//社保基数
		String ecou_house_base= "";//公积金基数
		String ecou_salary= "";//实际工资
		String ecou_state= "";//在职状态  0.离职 1.在职
	    String ecou_client= "";//客服
		String ecou_addname= "";//添加人
		String ecou_addtime= "";//添加时间
		String ecou_remark= "";//备注
		Date ecou_yldate= null; //养老起始
		Date ecou_yliaodate= null; //医疗起始
		Date ecou_dbdate= null;//大病起始
		Date ecou_syudate= null; //生育起始
		Date ecou_gsdate= null; //工伤起始
		Date ecou_sydate= null;//失业保险起始
		Date ecou_zfdate= null; //住房起始
		String ecou_zfcp= ""; //住房单位比例
		String ecou_zfop= "" ;//住房个人比例
		Date  ecou_bczfdate= null;//补充公积金起始
		String ecou_bczfcp= "";//补充住房单位比例
		String ecou_bczfop= "";//补充住房个人比例
		Date  ecou_cbjdate= null;//残保金起始时间
		Date ecou_fwfdate= null;//服务费起始时间
		Date ecou_filedate=null;//档案费起始时间
		if (inputStream != null) {
			// 创建对Excel工作簿文件的引用
			XSSFWorkbook workbook;
			try {
				workbook = new XSSFWorkbook(inputStream);
				// 创建对工作表的引用。
				// 本例是按名引用（让我们假定那张表有着缺省名"Sheet1"）
				XSSFSheet sheet = workbook.getSheetAt(0);
				// 也可用getSheetAt(int index)按索引引用，
				// 在Excel文档中，第一张工作表的缺省索引是0，
				// 其语句为：
				// 读取左上端单元
				XSSFRow row;
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					row = sheet.getRow(i);
					if (row != null) {
		
		
						if (row.getCell(0) != null) {
							row.getCell(0).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(0).getStringCellValue() != null) {
								gid = row.getCell(0)
										.getStringCellValue().toString();
							}
						}
						if (row.getCell(1) != null) {
							row.getCell(1).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(1).getStringCellValue() != null) {
								ecou_name = row.getCell(1).getStringCellValue()
										.toString();
							}
						}

						if (row.getCell(2) != null) {
							row.getCell(2).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(2).getStringCellValue() != null) {
								ecou_idcard = row.getCell(2).getStringCellValue()
										.toString();
							}
						}

						if (row.getCell(3) != null) {
							row.getCell(3).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(3).getStringCellValue() != null) {
								ecou_ecos_id = row.getCell(3).getStringCellValue()
										.toString();
							}
						}

						if (row.getCell(4) != null) {
							row.getCell(4).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(4).getStringCellValue() != null) {
								ecou_soin_id = row.getCell(4).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(5) != null) {
							row.getCell(5).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(5).getStringCellValue() != null) {
								ecou_filestate = row.getCell(5).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(6) != null) {
							row.getCell(6).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(6).getStringCellValue() != null) {
								 ecou_com_phone= row.getCell(6).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(7) != null) {
							row.getCell(7).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(7).getStringCellValue() != null) {
								ecou_mobile = row.getCell(7).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(8) != null) {//日期型先判断是否为数字
							row.getCell(8).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(8).getDateCellValue() != null) {//存在日期数据，不为空
								ecou_compact_f = row.getCell(8).getDateCellValue();
							}
						}
						if (row.getCell(9) != null) {
							row.getCell(9).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(9).getDateCellValue() != null) {
								ecou_compact_l = row.getCell(9).getDateCellValue();
							}
						}
						if (row.getCell(10) != null) {
							row.getCell(10).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(10).getStringCellValue() != null) {
								ecou_salary = row.getCell(10).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(11) != null) {
							row.getCell(11).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(11).getStringCellValue() != null) {
								ecou_sb_base = row.getCell(11).getStringCellValue()
										.toString();
							}
						}
						if (row.getCell(12) != null) {
							row.getCell(12).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(12).getStringCellValue() != null) {
								ecou_house_base = row.getCell(12)
										.getStringCellValue().toString();
							}
						}
						if (row.getCell(13) != null) {//日期型先判断是否为数字
							row.getCell(13).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(13).getDateCellValue() != null) {//存在日期数据，不为空时
								ecou_yldate =row.getCell(13).getDateCellValue();
							}
						}
						if (row.getCell(14) != null) {
							row.getCell(14).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(14).getDateCellValue() != null) {
								ecou_yliaodate = row.getCell(14).getDateCellValue();
							}
						}
						if (row.getCell(15) != null) {
							row.getCell(15).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(15).getDateCellValue() != null) {
								ecou_dbdate =row.getCell(15).getDateCellValue();
							}
						}
						if (row.getCell(16) != null) {
							row.getCell(16).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(16).getDateCellValue() != null) {
								ecou_syudate = row.getCell(16).getDateCellValue();
							}
						}
						if (row.getCell(17) != null) {
							row.getCell(17).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(17).getDateCellValue() != null) {
								ecou_gsdate = row.getCell(17).getDateCellValue();
							}
						}
						if (row.getCell(18) != null) {
							row.getCell(18).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(18).getDateCellValue() != null) {
								ecou_sydate = row.getCell(18).getDateCellValue();
							}
						}
						if (row.getCell(19) != null) {
							row.getCell(19).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(19).getDateCellValue() != null) {
								ecou_zfdate =row.getCell(19).getDateCellValue();
							}
						}
						if (row.getCell(20) != null) {
							row.getCell(20).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(20).getStringCellValue() != null) {
								ecou_zfcp= row.getCell(20)
										.getStringCellValue().toString();
							}
						}
						if (row.getCell(21) != null) {
							row.getCell(21).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(21).getStringCellValue() != null) {
								ecou_zfop = row.getCell(21)
										.getStringCellValue().toString();
							}
						}
						if (row.getCell(22) != null) {
							row.getCell(22).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(22).getDateCellValue() != null) {
								ecou_bczfdate = row.getCell(22).getDateCellValue();
							}
						}
						if (row.getCell(23) != null) {
							row.getCell(23).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(23).getStringCellValue() != null) {
								ecou_bczfcp = row.getCell(23)
										.getStringCellValue().toString();
							}
						}
						if (row.getCell(24) != null) {
							row.getCell(24).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(24).getStringCellValue() != null) {
								ecou_bczfop = row.getCell(24)
										.getStringCellValue().toString();
							}
						}
						if (row.getCell(25) != null) {
							row.getCell(25).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(25).getDateCellValue() != null) {
								ecou_cbjdate = row.getCell(25).getDateCellValue();
							}
						}
						if (row.getCell(26) != null) {
							row.getCell(26).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(26).getDateCellValue() != null) {
								ecou_fwfdate =row.getCell(26).getDateCellValue();
							}
						}
						
						
						if (row.getCell(27) != null) {
							row.getCell(27).setCellType(XSSFCell.CELL_TYPE_NUMERIC);
							if (row.getCell(27).getDateCellValue() != null) {
								ecou_filedate =row.getCell(27).getDateCellValue();
							}
						}
					
						
						
						
						if (row.getCell(28) != null) {
							row.getCell(28).setCellType(XSSFCell.CELL_TYPE_STRING);
							if (row.getCell(28).getStringCellValue() != null) {
								ecou_remark = row.getCell(28)
										.getStringCellValue().toString();
							}
						}
						

						//数据合法性核查
						/*1、请选择合同标准和当地标准!
						 * 2、请输入至少一个起始日
						 * 3、工作电话，个人手机请至少填写一个!
						 * 4、合同开始时间不能为空
						 * 5、合同结束日期
						 * 6、该员工已有该机构委托单，请误重复添加
						 * 7、档案保管情况核查
						 */ 
					   
						 if("".equals(ecou_name)&&"".equals(gid)&&"".equals(ecou_idcard))
						    {
						    	 Messagebox.show("姓名、员工该编号、员工身份证均不能为空！ " 
										 , "操作提示", Messagebox.OK,Messagebox.NONE);
						    	 return;
						    }
						    if("".equals(ecou_soin_id))
						    {
						    	 Messagebox.show("'"+ecou_name+"'当地标准代码未填写！" 
										 , "操作提示", Messagebox.OK,Messagebox.NONE);
						    	 return;
						    }
						    if("".equals(ecou_ecos_id))
						    {
						    	 Messagebox.show("'"+ecou_name+"'服务费代码未填写！" 
										 , "操作提示", Messagebox.OK,Messagebox.NONE);
						    	 return;
						    }
						    if("".equals(ecou_compact_f))
						    {
						    	 Messagebox.show("'"+ecou_name+"'合同起始时间未填写！"  
										 , "操作提示", Messagebox.OK,Messagebox.NONE);
						    	 return;
						    }
						    if("".equals(ecou_compact_l))
						    {
						    	 Messagebox.show("'"+ecou_name+"'合同结束时间未填写！"  
										 , "操作提示", Messagebox.OK,Messagebox.NONE);
						    	 return;
						    }
						    if("".equals(ecou_mobile) && "".equals(ecou_com_phone))
						    		
						    {
						    	 Messagebox.show("'"+ecou_name+"'工作电话，个人手机请至少填写一个！"  
										 , "操作提示", Messagebox.OK,Messagebox.NONE);
						    	 return;
						    }
						    
						
						
						
						//对获取到execle的值进行处理
						if (gid != null && !gid.equals("")) {
							EmbaseModel em= bll.findEmbaseInfo(gid);//根据员工编号查询是否存在该用户资料
							if(em!=null){
								EmCommissionOutimportModel eim= new EmCommissionOutimportModel();
								eim.setGid(Integer.parseInt(gid));
								if(ecou_name!=null&&!ecou_name.equals("")&&ecou_name.equals(em.getEmba_name())){
									eim.setEcou_name(ecou_name);
								}
								else
								{
									 Messagebox.show("编号为 "+gid+"姓名不匹配，请核查！" 
											 , "操作提示", Messagebox.OK,Messagebox.NONE);
									 return;
								}
								if(ecou_idcard!=null&&!ecou_idcard.equals("")&&ecou_idcard.equals(em.getEmba_idcard())){
									eim.setEcou_idcard(ecou_idcard);
								}
								else									
								{
									 Messagebox.show("编号为 "+gid+"身份证不匹配，请核查！" 
											 , "操作提示", Messagebox.OK,Messagebox.NONE);
									 return;
								}
								if(ecou_ecos_id!=null&&!ecou_ecos_id.equals("")&&isInteger(ecou_ecos_id)){
									eim.setEcou_ecos_id(Integer.parseInt(ecou_ecos_id));
								}else{
									eim.setEcou_ecos_id(0);
								}
								if(ecou_soin_id!=null&&!ecou_soin_id.equals("")&&isInteger(ecou_soin_id)){
									eim.setEcou_soin_id(Integer.parseInt(ecou_soin_id));
								}else{
									eim.setEcou_soin_id(0);
								}
								if(ecou_filestate.equals("是")){
									eim.setEcou_filestate(1);
								}else{
									eim.setEcou_filestate(0);
								}
								if(ecou_com_phone!=null&&!ecou_com_phone.equals("")){
									eim.setEcou_com_phone(ecou_com_phone);
								}
								if(ecou_mobile!=null&&!ecou_mobile.equals("")){
									eim.setEcou_mobile(ecou_mobile);
								}
								if(ecou_compact_f!=null&&!ecou_compact_f.equals("")){
									eim.setEcou_compact_f(df.format(ecou_compact_f));
								}
								if(ecou_compact_l!=null&&!ecou_compact_l.equals("")){
									eim.setEcou_compact_l(df.format(ecou_compact_l));
								}
								if (ecou_sb_base != null && !ecou_sb_base.equals("")&&isNumeric(ecou_sb_base)) {
									eim.setEcou_sb_base(new BigDecimal(ecou_sb_base).setScale(2, BigDecimal.ROUND_HALF_UP));
								}else{
									eim.setEcou_sb_base(new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP));
								}
								if (ecou_house_base != null && !ecou_house_base.equals("")&&isNumeric(ecou_house_base)) {
									eim.setEcou_house_base(new BigDecimal(ecou_house_base).setScale(2, BigDecimal.ROUND_HALF_UP));
								}else{
									eim.setEcou_house_base(new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP));
								}
								if (ecou_salary != null && !ecou_salary.equals("")&&isNumeric(ecou_salary)) {
									eim.setEcou_salary(new BigDecimal(ecou_salary).setScale(2, BigDecimal.ROUND_HALF_UP));
								}else{
									eim.setEcou_salary(new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP));
								}
								if(ecou_yldate!=null&&!ecou_yldate.equals("")){
										eim.setEcou_yldate(df.format(ecou_yldate));
								}
								if(ecou_yliaodate!=null&&!ecou_yliaodate.equals("")){
										eim.setEcou_yliaodate(df.format(ecou_yliaodate));
								}
								if(ecou_dbdate!=null&&!ecou_dbdate.equals("")){
										eim.setEcou_dbdate(df.format(ecou_dbdate));
								}
								if(ecou_syudate!=null&&!ecou_syudate.equals("")){
										eim.setEcou_syudate(df.format(ecou_syudate));
								}
								if(ecou_gsdate!=null&&!ecou_gsdate.equals("")){
										eim.setEcou_gsdate(df.format(ecou_gsdate));
								}
								if(ecou_sydate!=null&&!ecou_sydate.equals("")){
										eim.setEcou_sydate(df.format(ecou_sydate));
								}
								if(ecou_zfdate!=null&&!ecou_zfdate.equals("")){
										eim.setEcou_zfdate(df.format(ecou_zfdate));
								}
								if(ecou_bczfdate!=null&&!ecou_bczfdate.equals("")){
										eim.setEcou_bczfdate(df.format(ecou_bczfdate));
								}
								if(ecou_cbjdate!=null&&!ecou_cbjdate.equals("")){
									 eim.setEcou_cbjdate(df.format(ecou_cbjdate));
								}
								if(ecou_fwfdate!=null&&!ecou_fwfdate.equals("")){
										eim.setEcou_fwfdate(df.format(ecou_fwfdate));
								}
								if(ecou_filedate!=null&&!ecou_filedate.equals("")){
									eim.setEcou_filedate(df.format(ecou_filedate));
								}	
								if(ecou_remark!=null&&!ecou_remark.equals("")){
									eim.setEcou_remark(ecou_remark);
								}
								if(ecou_zfcp!=null&&!ecou_zfcp.equals("")){
									eim.setEcou_zfcp(ecou_zfcp);
								}
								if(ecou_zfop!=null&&!ecou_zfop.equals("")){
									eim.setEcou_zfop(ecou_zfop);
								}
								if(ecou_bczfcp!=null&&!ecou_bczfcp.equals("")){
									eim.setEcou_bczfcp(ecou_bczfcp);
								}
								if(ecou_bczfop!=null&&!ecou_bczfop.equals("")){ 
									eim.setEcou_bczfop(ecou_bczfop);
								}
								changelist.add(eim);
							}
							
							
						}
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 导入数据
	@Command
	@NotifyChange("list")
	public void summitUpload() {
		if (inputStream == null) {
			Messagebox.show("请选择文件", "操作提示", Messagebox.OK, Messagebox.ERROR);
			return;
		 } else if (changelist.size() <= 0) {
			Messagebox.show("没有找到有效数据.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			return;
		 }
		 List<EmCommissionOutimportModel> failList = new ArrayList<EmCommissionOutimportModel>();
		 Integer k = 0;
		 for (EmCommissionOutimportModel m : changelist) {
			    m.setEcou_importflie("/OfficeFile/UpLoad/OutImport/");
			    

			    
			    
				Integer kl = bll.importEntrustInfo(m);
				if (kl > 0) {
					k = k + 1;
				}
			}
			if (k == changelist.size()) {
				Messagebox
						.show("导入成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
			} else if (k == 0) {
				Messagebox.show("导入失败", "提示", Messagebox.OK, Messagebox.ERROR);
			} else {
				Messagebox.show("部分数据导入成功", "提示", Messagebox.OK,
						Messagebox.INFORMATION);
			}
			if (changelist.size() > 0) {
				changelist.clear();
			}
			
			list.clear();
			list.addAll(findEntrustInfo());
			
			searchEntrustInfo();

		}
	
	
	

	/**
	 * 判断是否需要补缴，并加入补缴数据
	 * 
	 * @param list
	 *            起始日列表，已去除空值、从小到大排序
	 */
	public List<EmCommissionOutChangeModel> bj(EmCommissionOutChangeModel md,
			List<EmCommissionOutFeeDetailChangeModel> list,
			List<EmCommissionOutChangeModel> subList, List<EmCommissionOutStandardModel> fuwulist12) {
		try {
			//List<EmCommissionOutFeeDetailChangeModel> listss =new ArrayList<>();
			
			
			// 当前时间
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			// 最早起始日
			Calendar c1 = Calendar.getInstance();
			c1.setTime(list.get(0).getTempDate());

			// 月份差值=(年份差)*12+(月份差)
			Integer forcount = ((c.get(Calendar.YEAR) - (c1.get(Calendar.YEAR)))
					* 12 + c.get(Calendar.MONTH) + 1)
					- (c1.get(Calendar.MONTH) + 1);

			// 差值大于0，则需要添加补缴数据
			if (forcount > 0) {
				Date date = list.get(0).getTempDate();
				for (int i = 0; i < forcount; i++) {
					EmCommissionOutChangeModel subM = new EmCommissionOutChangeModel();
					//list.clear();
					//list = feelistCopy();
					subM=(EmCommissionOutChangeModel) md.clone();

					subM.setTitle_date(date);
					subM.setEcoc_addtype("补缴");
					subM.setEcoc_state(1);
					subM.setEcoc_title_date(DateStringChange.DatetoSting(
							subM.getTitle_date(), "yyyy-MM-01"));
					subM.setEcoc_name("委托外地" + subM.getEcoc_addtype() + "("
							+ subM.getEcoc_title_date() + ")");
			
					for (EmCommissionOutFeeDetailChangeModel m1 : list) {
						if (m1.getTempDate() != null) {
							if (m1.getTempDate()
									.before(DateStringChange.StringtoDate(
											DateStringChange
													.Datestringnow("yyyy-MM-01"),
											"yyyy-MM-01"))) {
								m1.setEofc_start_date(DateStringChange
										.DatetoSting(date, "yyyy-MM-01"));
							} else {
								m1.setEofc_co_base(null);
								m1.setEofc_em_base(null);
							}
						}
					}
					subM = submHandle(subM, list, date,fuwulist12);
					subList.add(subM);

					// 月份加1
					Calendar datec = Calendar.getInstance();
					datec.setTime(date);
					datec.add(2, 1);
					date = datec.getTime();
				}

				// 加入补缴收费月份
				for (EmCommissionOutChangeModel ecm : subList) {
					if (ecm.getEcoc_addtype().equals("补缴")) {
						ecm.setEcoc_bjmonth(DateStringChange.DatetoSting(date,
								"yyyyMM"));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return subList;
	}



	
	
	
	// 生成委托外地任务单
		@Command
		@NotifyChange("list")
		public void summitUploadinto(@BindingParam("gd") Grid gd) throws SQLException {
			Integer count = 0;
			String[] str=new String[3];
			int summitcount=0;
			EmCommissionOutListBll addbll = new EmCommissionOutListBll();
			List<EmCommissionOutimportModel> listM= new ArrayList<EmCommissionOutimportModel>();
			int num= list.size();
			for (int i = 0; i < num; i++) {
				if (gd.getCell(i, 0) != null && gd.getCell(i, 0) instanceof Cell) {
					Cell cell = (Cell) gd.getCell(i, 0);
					if (cell.getChildren().size() > 1) {
						Checkbox ck = (Checkbox) cell.getChildren().get(1);
						if (ck.isChecked()) {
							EmCommissionOutimportModel md = ck.getValue();
							listM.add(md);
						}
					}
				}
			}
			int n=0;
			if(listM.size()>0){
				 for(int j=0;j<listM.size();j++){
					 EmCommissionOutimportModel mr=new EmCommissionOutimportModel();
					try {
						mr = bll.getEntrustInfomodel(listM.get(j).getEcou_id());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
					//数据检查
					//cid,gid,姓名,身份证号码核查
					str=bll.checkem(mr.getEcou_id());
					if (str[0].equals("1"))
					{
						 Messagebox.show("'"+str[1]+":"+str[3]+"'" 
								 , "操作提示", Messagebox.OK,Messagebox.NONE);
						 
						 return;
					}
				//数据计算	
				//初始化	
					EmCommissionOutChangeModel m = new EmCommissionOutChangeModel();	
					List<EmCommissionOutChangeModel> subList = new ListModelList<>();
					 List<EmCommissionOutFeeDetailChangeModel> feeList = new ListModelList<>();
					 
					 List<EmCommissionOutStandardModel> wtfwf =new ListModelList<>();
					 List<EmCommissionOutStandardModel> daf =new ListModelList<>();
					 
					
					m.setGid(mr.getGid());
					m.setEmba_name(mr.getEcou_name());
					m.setEcoc_name(mr.getEcou_name());
					m.setEmba_idcard(mr.getEcou_idcard());
					m.setEcoc_idcard(mr.getEcou_idcard());
					m.setEcoc_phone(mr.getEcou_com_phone());
					m.setEcoc_mobile(mr.getEcou_mobile());
					m.setEcoc_email(mr.getEcou_email());
					m.setEcoc_client(mr.getEcou_client());
					m.setEcoc_sb_base(mr.getEcou_sb_base());
					m.setEcoc_house_base(mr.getEcou_house_base());
					m.setEcoc_soin_id(mr.getEcou_soin_id());
					m.setEcoc_ecos_id(mr.getEcou_ecos_id());
					m.setEcoc_addtype("新增"); // 新增
					m.setEcoc_type("ecocadd");
				//	m.setEcoc_in_date(DateStringChange.StringtoDate(mr.getEcou_compact_f(),"yyyy-MM-dd"));
					m.setEcoc_compact_f(mr.getEcou_compact_f());
					//m.setEcoc_compact_l(mr.getEcou_compact_l());
					if (mr.getEcou_compact_l() != null &&
							DateStringChange.StringtoDate(mr.getEcou_compact_l(),"yyyy-MM-dd").compareTo
							(DateStringChange.StringtoDate(mr.getEcou_compact_f(),"yyyy-MM-dd"))>0) {
						m.setEcoc_compact_l(mr.getEcou_compact_l());
					} else {
						m.setEcoc_compact_l("无固定期限");
					}
					m.setEcoc_salary(mr.getEcou_salary());
					m.setEcoc_sb_base(mr.getEcou_sb_base());
					m.setEcoc_house_base(mr.getEcou_house_base());
					
					wtfwf=addbll.getStardListbyfeeid(mr.getEcou_ecos_id());
					daf=addbll.getfilelist(mr.getGid(), wtfwf.get(0).getCabc_id());
					m.setCid(wtfwf.get(0).getCid());

					m.setEcoc_service_fee(wtfwf.get(0).getEcos_service_fee());
					
					
					m.setEcoc_laststate(0);
					m.setEcoc_state(1);
					m.setEcoc_client(mr.getEcou_client());
					m.setEcoc_addname(UserInfo.getUsername());
					m.setEcoc_remark("批量新增");
					 
					feeList=addbll.getNullSoClassList(mr.getEcou_soin_id());
					// 服务费
					EmCommissionOutFeeDetailChangeModel mfw = new EmCommissionOutFeeDetailChangeModel();
					mfw.setEofc_name("服务费");
					mfw.setSicl_class("委托出标准费用");
					mfw.setEofc_month_sum(m.getEcoc_service_fee());
					mfw.setEofc_em_base(new BigDecimal(0));
					mfw.setEofc_co_base(new BigDecimal(0));
					if (mfw.getTempDate() == null) {
						// m1.setTempDate(new Date());
					}
					mfw.setEofc_sicl_id(22);
					feeList.add(mfw);
					
					// 档案费
					if (mr.getEcou_filestate()==1)
					{
						if (daf.size()==0)
						{
							 Messagebox.show("'"+m.getEmba_name()+":"+wtfwf.get(0).getCity()+"--"+wtfwf.get(0).getCoab_name()+"',无档案产品;" 
									 , "操作提示", Messagebox.OK,Messagebox.NONE);
							 
							 return;
						}else
						{
						m.setEcoc_file_fee(daf.get(0).getEcos_archvie_fee());
						EmCommissionOutFeeDetailChangeModel m2 = new EmCommissionOutFeeDetailChangeModel();
						m2.setEofc_name("档案费");
						m2.setSicl_class("委托出标准费用");
						m2.setEofc_month_sum(m.getEcoc_file_fee());
						m2.setEofc_em_base(new BigDecimal(0));
						m2.setEofc_co_base(new BigDecimal(0));

						m2.setEofc_sicl_id(23);
						
						feeList.add(m2);
						}
					}
					else
					{
						m.setEcoc_file_fee(new BigDecimal(0));
					}

			

				
					
					
					
					for (EmCommissionOutFeeDetailChangeModel m1 : feeList) {
					
						// 0：相等，1：大于，2：小于
						if (m1.getEofc_name().equals("养老保险")
								& mr.getEcou_sb_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_yldate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("医疗保险")
								& mr.getEcou_sb_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_yldate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("生育保险")
								& mr.getEcou_sb_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_syudate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("工伤保险")
								& mr.getEcou_sb_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_gsdate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("失业保险")
								& mr.getEcou_sb_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_sydate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("住房公积金")
								& mr.getEcou_house_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_zfdate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("补充公积金")
								& mr.getEcou_house_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_bczfdate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("补充公积金")
								& mr.getEcou_house_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_bczfdate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("大病医疗")
								& mr.getEcou_sb_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_dbdate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("残保金")
								& mr.getEcou_sb_base().compareTo(
										BigDecimal.ZERO) > 0
								) {
							m1.setEofc_em_base(mr.getEcou_sb_base());
							m1.setEofc_co_base(mr.getEcou_sb_base());
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_cbjdate(), "yyyy-MM-dd"));
							
						}
						if (m1.getEofc_name().equals("服务费")) {
						
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_fwfdate(), "yyyy-MM-dd"));
							
						}
						
						if (m1.getEofc_name().equals("档案费")) {
							
							m1.setTempDate(DateStringChange.StringtoDate(mr.getEcou_filedate(), "yyyy-MM-dd"));
							
						}
						
						
						
						
					}
					
					
					Date date = null;
				
					Date newdate = null;
				
					try {
						// 新增委托单分单代码块
						add: {
	
						List<EmCommissionOutFeeDetailChangeModel> listc = new ListModelList<>();
					
					
						listc = feelistCopy(feeList);
							// 去除起始日为空的项目
							for (int i = 0; i < listc.size(); i++) {
								if (listc.get(i).getTempDate() == null) {
									listc.remove(i);
									i--;
								}
							}

							if (listc.size() > 0) {
								// 冒泡排序，从小到大
								for (int i = 0; i < listc.size() - 1; i++) {
									for (int j1 = 0; j1 < listc.size() - 1 - i; j1++) {
										if (listc.get(j1).getTempDate()
												.after(listc.get(j1 + 1).getTempDate())) {
											EmCommissionOutFeeDetailChangeModel tempM = listc
													.get(j1 + 1);
											listc.set(j1 + 1, listc.get(j1));
											listc.set(j1, tempM);
										}
									}
								}
								// 获取最小起始日
								date = listc.get(0).getTempDate();

							
								EmCommissionOutChangeModel subM = new EmCommissionOutChangeModel();
								listc = feelistCopy(feeList);
								// 新增委托单字段数据处理
								subM.setTitle_date(date);
								subM.setEcoc_addtype("新增");
								subM.setEcoc_type("ecocadd");
								
								subM.setEcoc_title_date(DateStringChange.DatetoSting(
										subM.getTitle_date(), "yyyy-MM-01"));
								subM.setEcoc_name("委托外地" + subM.getEcoc_addtype() + "("
										+ subM.getEcoc_title_date() + ")");
								
								m.setEcoc_title_date(DateStringChange.DatetoSting(
										subM.getTitle_date(), "yyyy-MM-01"));
								m.setEcoc_name("委托外地" + subM.getEcoc_addtype() + "("
										+ subM.getEcoc_title_date() + ")");
								
								for (EmCommissionOutFeeDetailChangeModel m1 : listc) {
									if (m1.getTempDate() != null) {

										if (DateStringChange.comparedate(m1.getTempDate(),
												DateStringChange.Datenow()) == 1) {

											/*
											 * m1.setEofc_start_date(DateStringChange.
											 * DatetoSting( DateStringChange.Datenow(),
											 * "yyyy-MM-01"));
											 */
											m1.setEofc_start_date(DateStringChange
													.DatetoSting(m1.getTempDate(),
															"yyyy-MM-01"));

											newdate = DateStringChange.StringtoDate(
													DateStringChange.DatetoSting(
															DateStringChange.Datenow(),
															"yyyy-MM-01"), "yyyy-MM-01");

										} else {
											m1.setEofc_start_date(DateStringChange
													.DatetoSting(m1.getTempDate(),
															"yyyy-MM-01"));
											newdate = date;
										}

										/*
										 * m1.setEofc_start_date(DateStringChange.DatetoSting
										 * ( m1.getTempDate(), "yyyy-MM-01"));
										 */
									}
								}
								subM = submHandle(m, listc, newdate,wtfwf);

								// 添加新增委托单
								subList.add(subM);
								count++;
							
								// 调用补缴方法
								bj(subM,listc, subList,wtfwf);
							} else {
								break add;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					//生成任务单
					
					EmCommissionOut_OperateBll opbll = new EmCommissionOut_OperateBll();
					try {
						 count = opbll.add(subList, m.getEmba_name(),
								 wtfwf.get(0).getCity());

						if (count > 0) {
						
							//更新导入表状态
							mr.setEcou_state(2);
							bll.updateEntrustInfo(mr);
							
							summitcount++;
							
						}
						
					} catch (Exception e) {
						
						e.printStackTrace();
						str[2]=str[2]+mr.getCid()+",";
						
					}

					
				 }
				
			}else{
				Messagebox.show("请选择数据", "提示", Messagebox.OK, Messagebox.ERROR);
			}
			if (count > 0)
			{
				//summitcount=listM.size()-count;
			Messagebox.show("共提交成功"+listM.size()+" 数据 ,成功提交" + summitcount + "条!", "INFORMATION",
					Messagebox.OK, Messagebox.INFORMATION);
			
			
			
			
			}else
			{
				Messagebox.show("提交出错!错误编号："+str[2]+"", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
			
			searchEntrustInfo();
			
			
			}
		
		
					/**
					 * 计算总计
					 * 
					 */
					public EmCommissionOutChangeModel calSum(EmCommissionOutChangeModel m) {
						// 重置总计
						m.setEcoc_sb_em_sum(new BigDecimal(0));
						m.setEcoc_sb_co_sum(new BigDecimal(0));
						m.setEcoc_sb_sum(new BigDecimal(0));
						m.setEcoc_gjj_em_sum(new BigDecimal(0));
						m.setEcoc_gjj_co_sum(new BigDecimal(0));
						m.setEcoc_gjj_sum(new BigDecimal(0));
						m.setEcoc_sum(new BigDecimal(0));
						m.setEcoc_welfare_sum(new BigDecimal(0));

						try {
							// 社保、公积金、福利
							for (EmCommissionOutFeeDetailChangeModel m1 : m.getFeeList()) {
								// 社保
								if (m1.getSicl_class().equals("社会保险")) {
									// 社保企业总计
									m.setEcoc_sb_co_sum(m.getEcoc_sb_co_sum().add(
											m1.getEofc_co_sum()));
									// 社保个人总计
									m.setEcoc_sb_em_sum(m.getEcoc_sb_em_sum().add(
											m1.getEofc_em_sum()));
								}
								// 公积金
								else if (m1.getSicl_class().equals("公积金")) {
									// 公积金企业总计
									m.setEcoc_gjj_co_sum(m.getEcoc_gjj_co_sum().add(
											m1.getEofc_co_sum()));
									// 公积金个人总计
									m.setEcoc_gjj_em_sum(m.getEcoc_gjj_em_sum().add(
											m1.getEofc_em_sum()));
								}
								// 福利
								else if (m1.getSicl_class().equals("福利项目")) {
									m.setEcoc_welfare_sum(m.getEcoc_welfare_sum().add(
											m1.getEofc_month_sum()));
								}
								// 总计
								m.setEcoc_sum(m.getEcoc_sb_em_sum().add(
										m.getEcoc_sb_co_sum().add(
												m.getEcoc_gjj_em_sum().add(
														m.getEcoc_gjj_co_sum().add(
																m.getEcoc_service_fee())))));
							}
							// 社保总计
							m.setEcoc_sb_sum(m.getEcoc_sb_em_sum().add(m.getEcoc_sb_co_sum()));
							// 公积金总计
							m.setEcoc_gjj_sum(m.getEcoc_gjj_em_sum()
									.add(m.getEcoc_gjj_co_sum()));

						} catch (Exception e) {
							e.printStackTrace();
							Messagebox
									.show("费用总计出错!", "ERROR", Messagebox.OK, Messagebox.ERROR);
						}
						return m;
						
					}
			
		/**
		 * 委托单model处理(计算、赋值...)
		 * 
		 * @param subM
		 * @param list
		 * @param title_date
		 * @return
		 */
		public EmCommissionOutChangeModel submHandle(
				EmCommissionOutChangeModel subM,
				List<EmCommissionOutFeeDetailChangeModel> list, Date title_date, List<EmCommissionOutStandardModel> fuwulist12) {
			// 设置计算信息
			if (calUtil.setCalculator(subM.getEcoc_soin_id(), title_date,
					fuwulist12.get(0).getEcos_shebao_feetype(),
					fuwulist12.get(0).getEcos_gjj_feetype())) {
				// 计算费用
				list = calUtil.getEmCommissionOutItemFee(list);

				//fieldhandle(subM);

				subM.setFeeList(list);

				// 总计
				subM = calSum(subM);
			}

			return subM;
		}
		

		/**
		 * 复制feeList
		 * 
		 * @param list
		 * @return
		 */
		public List<EmCommissionOutFeeDetailChangeModel> feelistCopy(List<EmCommissionOutFeeDetailChangeModel> feeListde ) {
			List<EmCommissionOutFeeDetailChangeModel> list = new ListModelList<>();
			for (int i = 0; i < feeListde.size(); i++) {
				EmCommissionOutFeeDetailChangeModel m1 = new EmCommissionOutFeeDetailChangeModel();
				EmCommissionOutFeeDetailChangeModel m2 = new EmCommissionOutFeeDetailChangeModel();

				m1 = feeListde.get(i);

				m2.setEofc_name(m1.getEofc_name());
				m2.setEofc_sicl_id(m1.getEofc_sicl_id());
				m2.setSicl_class(m1.getSicl_class());
				m2.setEofc_em_base(m1.getEofc_em_base());
				m2.setEofc_co_base(m1.getEofc_co_base());
				m2.setEofc_em_sum(m1.getEofc_em_sum());
				m2.setEofc_co_sum(m1.getEofc_co_sum());
				m2.setTempDate(m1.getTempDate());
				m2.setEofc_month_sum(m1.getEofc_month_sum());
				m2.setEofc_content(m1.getEofc_content());
				m2.setEofc_state(1);
				m2.setEofc_ecop_id(m1.getEofc_ecop_id());

				list.add(m2);
			}

			return list;
		}

	//删除委托地批量导入的记录
    @Command("delete")
    public void delete(@BindingParam("model") EmCommissionOutimportModel model) throws SQLException{
       String username =UserInfo.getUsername();
       if(username.equals(model.getEcou_addname())){
         if(Messagebox.show("确认要删除该记录吗？", "操作提示",Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES){
    	   int a=bll.deleteEntrustInfo(model.getEcou_id());                      //根据id删除记录
    	   if(a>0){
    		  Messagebox.show("删除成功！", "提示", Messagebox.OK,
					Messagebox.INFORMATION);
    	   }else{
    		  Messagebox.show("删除失败！", "提示", Messagebox.OK,
					Messagebox.ERROR);
    	   }
    	   list.clear();
		   list.addAll(findEntrustInfo());
         }
       }else{
    	   Messagebox.show("所选记录不是当前用户添加，不能对其进行删除处理！", "提示", Messagebox.OK,Messagebox.INFORMATION);
       }
    }
    
    //更新委托地批量导入的记录
    @Command("update")
    public void update(@ContextParam(ContextType.VIEW) Component view,@BindingParam("model") EmCommissionOutimportModel model){
    	String username =UserInfo.getUsername();
        if(username.equals(model.getEcou_addname())){
            Map<String, EmCommissionOutimportModel> map =new HashMap<String, EmCommissionOutimportModel>();
                map.put("model", model);
            Window win = (Window) Executions.createComponents("/EmCommissionOut/EmCommissionOutimport_Update.zul", view, map);//跳转至更新页
            win.doModal();
        }else{
     	   Messagebox.show("所选记录不是当前用户添加，不能对其进行更新处理！", "提示", Messagebox.OK,Messagebox.INFORMATION);
        }
    }
    //子页面刷新
    @Command("refresh")
	@NotifyChange("list")
    public void refreshParentWindow(){
    	list.clear();
        list.addAll(findEntrustInfo());
      }
     
    public List<EmCommissionOutimportModel> findEntrustInfo(){
		StringBuilder strsql= new StringBuilder();
		if(!ecouname.trim().equals("")){
				strsql.append(" and ecou_name like '%"+ecouname+"%'" );
		}
		if(!addname.trim().equals("")){
				strsql.append(" and ecou_addname like '%"+addname+"%'" );
		}
		strsql.append(" ");
		try {
			return bll.getEntrustInfo(strsql.toString());
		} catch (SQLException e) {
				e.printStackTrace();
			return null;
		}
	}
    

	public String getWtot_feetitle() {
		return wtot_feetitle;
	}
	public void setWtot_feetitle(String wtot_feetitle) {
		this.wtot_feetitle = wtot_feetitle;
	}
	public String getWtss_title() {
		return wtss_title;
	}
	public void setWtss_title(String wtss_title) {
		this.wtss_title = wtss_title;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCoab_name() {
		return coab_name;
	}
	public void setCoab_name(String coab_name) {
		this.coab_name = coab_name;
	}
	public List<String> getFeetitlelist() {
		return feetitlelist;
	}
	public void setFeetitlelist(List<String> feetitlelist) {
		this.feetitlelist = feetitlelist;
	}
	public List<String> getTitlelist() {
		return titlelist;
	}
	public void setTitlelist(List<String> titlelist) {
		this.titlelist = titlelist;
	}
	public List<String> getCitylist() {
		return citylist;
	}
	public void setCitylist(List<String> citylist) {
		this.citylist = citylist;
	}
	public List<String> getNamelist() {
		return namelist;
	}
	public void setNamelist(List<String> namelist) {
		this.namelist = namelist;
	}
	public List<wtoutFeeModel> getWtoutFeeList() {
		return wtoutFeeList;
	}
	public void setWtoutFeeList(List<wtoutFeeModel> wtoutFeeList) {
		this.wtoutFeeList = wtoutFeeList;
	}
	public List<SocialInsuranceAlgorithmViewModel> getSocialInsuranceList() {
		return socialInsuranceList;
	}
	public void setSocialInsuranceList(
			List<SocialInsuranceAlgorithmViewModel> socialInsuranceList) {
		this.socialInsuranceList = socialInsuranceList;
	}
	public String getSoin() {
		return soin;
	}
	public void setSoin(String soin) {
		this.soin = soin;
	}
	public String getSoincity() {
		return soincity;
	}
	public void setSoincity(String soincity) {
		this.soincity = soincity;
	}
	public String getSoinname() {
		return soinname;
	}
	public void setSoinname(String soinname) {
		this.soinname = soinname;
	}
	public List<String> getSoinlist() {
		return soinlist;
	}
	public void setSoinlist(List<String> soinlist) {
		this.soinlist = soinlist;
	}
	public List<String> getSoincitylist() {
		return soincitylist;
	}
	public void setSoincitylist(List<String> soincitylist) {
		this.soincitylist = soincitylist;
	}
	public List<String> getSoinnamelist() {
		return soinnamelist;
	}
	public void setSoinnamelist(List<String> soinnamelist) {
		this.soinnamelist = soinnamelist;
	}
	public String getUploadFlieName() {
		return uploadFlieName;
	}
	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}
	public List<EmCommissionOutimportModel> getList() {
		return list;
	}
	public void setList(List<EmCommissionOutimportModel> list) {
		this.list = list;
	}
	
	
	public List<EmCommissionOutFeeDetailChangeModel> getFlList() {
		return flList;
	}



	public void setFlList(List<EmCommissionOutFeeDetailChangeModel> flList) {
		this.flList = flList;
	}



	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getEcouname() {
		return ecouname;
	}
	public void setEcouname(String ecouname) {
		this.ecouname = ecouname;
	}
	public String getAddname() {
		return addname;
	}
	public void setAddname(String addname) {
		this.addname = addname;
	}
	public List<String> getEcounamelist() {
		return ecounamelist;
	}
	public void setEcounamelist(List<String> ecounamelist) {
		this.ecounamelist = ecounamelist;
	}
	public List<String> getAddnamelist() {
		return addnamelist;
	}
	public void setAddnamelist(List<String> addnamelist) {
		this.addnamelist = addnamelist;
	}
	
	
	
	public EmCommissionOutChangeModel getTitleModel() {
		return titleModel;
	}



	public void setTitleModel(EmCommissionOutChangeModel titleModel) {
		this.titleModel = titleModel;
	}



	/**
     * 判断是否为数值
     * 支持格式："33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
     * @param str String
     * @return boolean
     */
   public static boolean isNumeric(String str) {
       int begin = 0;
       boolean once = true;
       if (str == null || str.trim().equals("")) {
           return false;
       }
       str = str.trim();
       if (str.startsWith("+") || str.startsWith("-")) {
           if (str.length() == 1) {
               // "+" "-"
               return false;
           }
           begin = 1;
       }
       for (int i = begin; i < str.length(); i++) {
           if (!Character.isDigit(str.charAt(i))) {
               if (str.charAt(i) == '.' && once) {
                   // '.' can only once
                   once = false;
               }
               else {
                   return false;
               }
           }
       }
       if (str.length() == (begin + 1) && !once) {
           // "." "+." "-."
           return false;
       }
       return true;
   }
   

   public List<EmCommissionOutChangeModel> getSubList() {
		return subList;
	}



	public void setSubList(List<EmCommissionOutChangeModel> subList) {
		this.subList = subList;
	}



	public List<EmCommissionOutChangeModel> getCityList() {
		return cityList;
	}



	public void setCityList(List<EmCommissionOutChangeModel> cityList) {
		this.cityList = cityList;
	}



	public EmCommissionOutStandardModel getStardModel() {
		return stardModel;
	}



	public void setStardModel(EmCommissionOutStandardModel stardModel) {
		this.stardModel = stardModel;
	}



	public List<EmCommissionOutStandardModel> getStardList() {
		return stardList;
	}



	public void setStardList(List<EmCommissionOutStandardModel> stardList) {
		this.stardList = stardList;
	}



public List<EmCommissionOutChangeModel> getTitleList() {
		return titleList;
	}



	public void setTitleList(List<EmCommissionOutChangeModel> titleList) {
		this.titleList = titleList;
	}



	public List<EmCommissionOutStandardModel> getFuwulist() {
		return fuwulist;
	}



	public void setFuwulist(List<EmCommissionOutStandardModel> fuwulist) {
		this.fuwulist = fuwulist;
	}



	public List<EmCommissionOutStandardModel> getFilelist() {
		return filelist;
	}



	public void setFilelist(List<EmCommissionOutStandardModel> filelist) {
		this.filelist = filelist;
	}



/**
     * 判断是否为整型
     * 支持 格式 ："33" "003300" "+33" " -0000 "
     * @param str String
     * @return boolean
     */
   public static boolean isInteger(String str) {
       int begin = 0;
       if (str == null || str.trim().equals("")) {
           return false;
       }
       str = str.trim();
       if (str.startsWith("+") || str.startsWith("-")) {
           if (str.length() == 1) {
               // "+" "-"
               return false;
           }
           begin = 1;
       }
       for (int i = begin; i < str.length(); i++) {
           if (!Character.isDigit(str.charAt(i))) {
               return false;
           }
       }
       return true;
   }



public String getWtot_feeid() {
	return wtot_feeid;
}



public void setWtot_feeid(String wtot_feeid) {
	this.wtot_feeid = wtot_feeid;
}



public String getSoin_id() {
	return soin_id;
}



public void setSoin_id(String soin_id) {
	this.soin_id = soin_id;
}

}
