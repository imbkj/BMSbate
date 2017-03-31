package Controller.Batchprocessing;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Include;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Window;

import bll.Batchprocessing.BatP_emCommissonOutBll;
import bll.Batchprocessing.BatP_embaseBll;

import Model.CoBaseModel;
import Model.EmShebaoChangeUploadModel;
import Model.EmbaseGDModel;
import Model.EmbaseModel;
import Model.embaseyfModel;
import Util.DateStringChange;
import Util.FileOperate;
import Util.UserInfo;

public class BatP_emCommissionOutController {

	private List<EmbaseModel> ulList;
	private List<EmbaseModel> winUlList;
	private String uploadFlieName="";
	private final String downfolder = "Batchprocessing/Templet/EmBase/";
	private String templetType;
	private Media media;
	private final int dataRow = 4;
	private BatP_emCommissonOutBll bll;
	private Integer ccid=0;
	private Integer cid=0;
	private String name;
	private String idcard;
	private String addname;
	private String state;
	private String coname;
	private boolean checkAll=false;
	
	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}

	private String addtime;
	private String type;
	
	public BatP_emCommissionOutController()
	{
		templetType = "";
		bll=new BatP_emCommissonOutBll();
		winUlList=ulList=bll.getEmBaseById(Util.UserInfo.getUsername());
	}
	
	// 下载模板
	@Command("downloadTemplet")
	public void downloadTemplet() {
		String path = downfolder;
		try {
			String filename = getTempletFilename();
			if (!"".equals(filename) && filename != null) {
				FileOperate.download(path + filename);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("模板下载出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	// 获取模板文件名
		private String getTempletFilename() {
			String fliename = "";
			try {
				if (!"".equals(templetType) && templetType != null) {
					switch (templetType) {
					case "基本信息导入":
						fliename = "EmBase_AddNew.xls";
						break;
					case "基本信息修改":
						fliename = "EmBase_edit.xls";
						break;
					 
					default:
						Messagebox.show("模板类型有误。", "操作提示", Messagebox.OK,
								Messagebox.ERROR);
						break;
					}
				} else {
					Messagebox.show("请先选择模板类型。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return fliename;
		}
		
		
		@Command("btSubmitfp")
		public void btSubmitfp(@BindingParam("a") EmbaseGDModel em) {
			if (ccid>0)
			{
			Map map = new HashMap<>();
			
			CoBaseModel cbm=new CoBaseModel();
			cbm.setCid(ccid);
			map.put("model", cbm);
			Window window;
			window = (Window) Executions.createComponents("../CoCompact/EmBase_CompactAllot.zul", null, map);
			window.doModal();
			}else
			{
				Messagebox.show("请填写公司编号。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}
			

	// 文件检查
		@Command("browse")
		@NotifyChange("uploadFlieName")
		public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
			UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
			this.media = upEvent.getMedia();
			if ("xls".equals(media.getFormat())) {
				uploadFlieName = media.getName();
			} else {
				this.media = null;
				uploadFlieName = "";
				Messagebox.show("上传的文件格式有误。", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}

		// 导入数据
		@Command("upload")
		@NotifyChange("winUlList")
		public void upload() {
			try {
				if (!"".equals(templetType) && templetType != null) {
					if (this.media != null) {
						String absolutePath = FileOperate.getAbsolutePath();
						String uploadfolder = "Batchprocessing/Upload/EmBase/";
						String uploadName = mosaicFileName();
						// 上传文件至服务器
						if (FileOperate.upload(media, uploadfolder + uploadName)) {
							// 获取上传Excel的内容,并更新至数据库
							String[] message = getExcel(
									absolutePath + uploadfolder, uploadName);
							if ("1".equals(message[0]) || "0".equals(message[0])) {
								// 弹出提示
								Messagebox.show(message[1], "操作提示", Messagebox.OK,
										Messagebox.NONE);

								winUlList=ulList=bll.getEmBaseById(Util.UserInfo.getUsername());
							} else {
								// 弹出提示
								Messagebox.show(message[1], "操作提示", Messagebox.OK,
										Messagebox.ERROR);
							}
						} else {
							Messagebox.show("文件上传出错。", "操作提示", Messagebox.OK,
									Messagebox.NONE);
						}
					} else {
						Messagebox.show("请选择需要上传的文件。", "操作提示", Messagebox.OK,
								Messagebox.NONE);
					}
				} else {
					Messagebox.show("请先选择模板类型。", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("数据导入出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
			
		}

		// 拼接上传文件的名称
		private String mosaicFileName() {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String nowtime = sdf.format(date);
			String name = nowtime + "_" + UserInfo.getUserid() + ".xls";
			return name;
		}
		
		// 检索
		@Command("search")
		@NotifyChange("winUlList")
		public void search() {
			List<EmbaseModel> list = new ArrayList<EmbaseModel>();
			try {
				for (EmbaseModel m : ulList) {
					try {
						
						if (cid != 0) {
							if (m.getCid() != cid)
								continue;
						}
										
						if (coname != null && !"".equals(coname)) {
							if (!coname.equals(m.getCoba_shortname()))
								continue;
						}
						if (name != null && !"".equals(name)) {
							if (!name.equals(m.getEmba_name()))
								continue;
						}
						if (idcard != null && !"".equals(idcard)) {
							if (!idcard.equals(m.getEmba_idcard()))
								continue;
						}
					  
						if (state != null && !"".equals(state)) {
							if (!state.equals(m.getEmba_statebatchstr()))
								continue;
						}
						if (addtime != null && !"".equals(addtime)) {
							if (!m.getEmba_addtime().contains(addtime))
								continue;
						}
		
						list.add(m);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				winUlList = list;
			 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Command("setAddtime")
		public void setAddtime(@BindingParam("dbAddtime") Datebox dbAddtime) {
			try {
				this.addtime = dbAddtime.getValue() != null ? DateStringChange
						.DatetoSting(dbAddtime.getValue(), "yyyy-MM-dd") : "";
			} catch (Exception e) {
				e.printStackTrace();
			}
			search();
		}
		
		// 全选
		@Command("checkAll")
		public void checkAll() {
			try {
				for (EmbaseModel m : winUlList) {
					if (m.getEmba_state() == 0) {
						m.setCheck(checkAll);
					}
				}
				BindUtils.postNotifyChange(null, null, this, "winUlList");
			} catch (Exception e) {
				Messagebox.show("全选操作出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
				e.printStackTrace();
			}
		}
		
		
		
		// 获取上传Excel的内容,并更新至数据库
		private String[] getExcel(String path, String fileName) {
			String[] message = new String[3];
			File file = null;
			Workbook wb = null;
			try {
				file = new File(path + fileName);
				// 读取Excel文件
				wb = Workbook.getWorkbook(file);
				// 读取工作表
				Sheet st = wb.getSheet(0);
				int rows = st.getRows();
				if (rows > 1) {
					// 核对Excel数据格式
					if (checkTh(st)) {
						// 存放数据至LIST
						List<EmbaseModel> ciList = getExcelData(st);
						if (ciList.size() > 0) {
							// 更新至数据库
							message = bll.addBatchUpload(ciList,
									UserInfo.getUsername(),fileName);
						} else {
							message[0] = "2";
							message[1] = "未找到可导入的数据。";
						}
					} else {
						message[0] = "2";
						message[1] = "导入的Excel与模板的数据格式不一致。";
					}
				} else {
					message[0] = "2";
					message[1] = "导入的Excel中并未找到有效数据。";
				}
			} catch (Exception e) {
				e.printStackTrace();
				message[0] = "2";
				message[1] = "数据获取出错。";
			} finally {
				wb.close();
				file.delete();
			}
			return message;
		}

		// 核对修改工资数据格式
		private boolean checkTh(Sheet st) {
			try {
				switch (templetType) {
				case "基本信息导入":
					if (!"公司编号".equals(st.getCell(0, dataRow-1).getContents().trim()))
						return false;
					if (!"员工姓名".equals(st.getCell(1, dataRow-1).getContents().trim()))
						return false;
					if (!"员工身份证".equals(st.getCell(2, dataRow-1).getContents().trim()))
						return false;
					if (!"入职时间".equals(st.getCell(3,dataRow-1).getContents().trim()))
						return false;
					if (!"手机".equals(st.getCell(4, dataRow-1).getContents().trim()))
						return false;
					break;
				default:
					return false;
				}

			} catch (Exception e) {
				return false;
			}

			return true;
		}
		

		// 存放修改工资数据至LIST
		private List<EmbaseModel> getExcelData(Sheet st) {
			List<EmbaseModel> list = new ArrayList<EmbaseModel>();
			EmbaseModel m = null;
			BigDecimal radix;
			try {
				// 遍历数据行
				for (int r = dataRow; r < st.getRows(); r++) {
					try {
						if (st.getCell(0, r).getContents() == null
								|| "".equals(st.getCell(0, r).getContents())) {
							break;
						}
						m = new EmbaseModel();
						m.setCid(Integer.parseInt(st.getCell(0, r).getContents().trim()));
						m.setEmba_name(st.getCell(1, r).getContents().trim());
						m.setEmba_idcard(st.getCell(2, r).getContents().trim());
						m.setEmba_indate(st.getCell(3, r).getContents().trim());
						m.setEmba_mobile(st.getCell(4, r).getContents().trim());
						m.setEmba_nationality(st.getCell(5, r).getContents().trim());
						m.setEmba_folk(st.getCell(6, r).getContents().trim());
						
						m.setEmba_hjadd(st.getCell(7, r).getContents().trim());
						m.setEmba_phone(st.getCell(8, r).getContents().trim());
						m.setEmba_email(st.getCell(9, r).getContents().trim());
						
						m.setEmba_address(st.getCell(10, r).getContents().trim());
						m.setEmba_fileplace(st.getCell(11, r).getContents().trim());
						m.setEmba_party(st.getCell(12, r).getContents().trim());
						m.setEmba_degree(st.getCell(13, r).getContents().trim());
						m.setEmba_education(st.getCell(14, r).getContents().trim());
						m.setEmba_specialty(st.getCell(15, r).getContents().trim());
						m.setEmba_school(st.getCell(16, r).getContents().trim());
						
						
						m.setEmba_gz_bank(st.getCell(19, r).getContents().trim());
						m.setEmba_gz_account(st.getCell(20, r).getContents().trim());
						m.setEmba_house_bank(st.getCell(21, r).getContents().trim());
						m.setEmba_house_account(st.getCell(22, r).getContents().trim());
						
						m.setEmba_writeoff_bank(st.getCell(23, r).getContents().trim());
						m.setEmba_writeoff_account(st.getCell(24, r).getContents().trim());
						m.setEmba_sy_bank(st.getCell(25, r).getContents().trim());
						m.setEmba_sy_account(st.getCell(26, r).getContents().trim());
						m.setEmba_remark(st.getCell(27, r).getContents().trim());
						
						m.setEmba_englishname(st.getCell(28, r).getContents().trim());
						m.setEmba_number(st.getCell(29, r).getContents().trim());
						
						bll.checkdata(m);
						
						list.add(m);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		
		
	public String getUploadFlieName() {
		return uploadFlieName;
	}

	public void setUploadFlieName(String uploadFlieName) {
		this.uploadFlieName = uploadFlieName;
	}
 
	
	@Command("submit")
	@NotifyChange("winUlList")
	public void submit()
	{
		try {
			String[] message = bll.addtoembase(winUlList);
			if ("1".equals(message[0])) {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
 
				
				ulList=bll.getEmBaseById(Util.UserInfo.getUsername());
				checkAll = false;
				
			
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交数据出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}
	//	search();
		
		winUlList=ulList=bll.getEmBaseById(Util.UserInfo.getUsername());
	
		
		
	}
	
	
	@Command("delesubmit")
	@NotifyChange("winUlList")
	public void delesubmit()
	{
		try {
			String[] message = bll.updateEmbaseBatch(winUlList);
			if ("1".equals(message[0])) {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.NONE);
 
				
				ulList=bll.getEmBaseById(Util.UserInfo.getUsername());
				checkAll = false;
				
			
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} catch (Exception e) {
			Messagebox.show("提交数据出錯。", "操作提示", Messagebox.OK, Messagebox.ERROR);
			e.printStackTrace();
		}

		
		winUlList=ulList=bll.getEmBaseById(Util.UserInfo.getUsername());
	
		
		
	}
	
	

	public List<EmbaseModel> getUlList() {
		return ulList;
	}

	public void setUlList(List<EmbaseModel> ulList) {
		this.ulList = ulList;
	}

	public List<EmbaseModel> getWinUlList() {
		return winUlList;
	}

	public void setWinUlList(List<EmbaseModel> winUlList) {
		this.winUlList = winUlList;
	}

	public String getTempletType() {
		return templetType;
	}

	public void setTempletType(String templetType) {
		this.templetType = templetType;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public String getConame() {
		return coname;
	}

	public void setConame(String coname) {
		this.coname = coname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isCheckAll() {
		return checkAll;
	}

	public void setCheckAll(boolean checkAll) {
		this.checkAll = checkAll;
	}

	public Integer getCcid() {
		return ccid;
	}

	public void setCcid(Integer ccid) {
		this.ccid = ccid;
	}
	
	
			

}
