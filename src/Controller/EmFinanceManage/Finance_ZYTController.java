package Controller.EmFinanceManage;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

import Model.FinanceZYTModel;
import Util.FileOperate;
import Util.UserInfo;
import bll.EmFinanceManage.Finance_ZYTBll;

public class Finance_ZYTController {

	private Media[] media;
	private String absolutePath;
	private String uploadFlieName;
	private String uploadfolder;
	private String uploadState = "";
	private List<FinanceZYTModel> flist = new ListModelList<>();

	private Finance_ZYTBll bll;

	private String jg = "";
	private BigDecimal fl = BigDecimal.ZERO;
	private BigDecimal other = BigDecimal.ZERO;
	private BigDecimal file = BigDecimal.ZERO;
	private BigDecimal fee = BigDecimal.ZERO;
	private BigDecimal sb = BigDecimal.ZERO;
	private BigDecimal gjj = BigDecimal.ZERO;
	private BigDecimal total = BigDecimal.ZERO;

	public Finance_ZYTController() {
		bll = new Finance_ZYTBll();
		absolutePath = FileOperate.getAbsolutePath();
		uploadfolder = "/OfficeFile/UpLoad/EmZYT/";
	}

	// 文件检查
	@Command("browse")
	@NotifyChange({ "uploadState", "flist", "fl", "other", "file", "fee", "sb",
			"gjj", "total" })
	public void browse(@ContextParam(ContextType.BIND_CONTEXT) BindContext ul) {
		uploadState = "";
		UploadEvent upEvent = (UploadEvent) ul.getTriggerEvent();
		media = upEvent.getMedias();
		flist.clear();
		for (int i = 0; i < media.length; i++) {

			if ("xls".equals(media[i].getFormat())) {
				uploadFlieName = media[i].getName();
				String uploadName = mosaicFileName();

				if (FileOperate.upload(media[i], uploadfolder + uploadName)) {
					uploadState = "上传成功";
					flist = bll.initData(absolutePath + uploadfolder
							+ uploadName, UserInfo.getUsername());
					if (flist.size() > 0) {

						List<FinanceZYTModel> tList = bll.total(UserInfo
								.getUsername());
						if (tList.size() > 0) {
							fl = tList.get(0).getFlTotal();
							other = tList.get(0).getOtherTotal();
							file = tList.get(0).getFileTotal();
							fee = tList.get(0).getFeeTotal();
							sb = tList.get(0).getSbTotal();
							gjj = tList.get(0).getGjjTotal();
							total = tList.get(0).getTotal();
						}
					}else {
						uploadState="数据读取失败";
					}
				} else {
					uploadState = "上传失败";
				}
			}else {
				Messagebox.show("文件格式不对,请确认文件后缀为xls", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		}
	}

	@Command
	public void createtxt() {
		if (jg != null && !jg.equals("")) {
			if (flist.size() > 0) {
				for (FinanceZYTModel m : flist) {
					bll.mod(m.getId(), m.getUid(), m.getCompactType());
				}
				try {
					Filedownload.save(
							new File(bll.txtFile(jg, UserInfo.getUsername())),
							null);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Messagebox.show("文件生成失败.", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				}
			} else {
				Messagebox.show("请上传账单.", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			Messagebox
					.show("请输入机构名称.", "操作提示", Messagebox.OK, Messagebox.ERROR);
		}
	}

	// 拼接上传文件的名称
	private String mosaicFileName() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String nowtime = sdf.format(date);
		String name = nowtime + uploadFlieName;
		return name;
	}

	public String getUploadState() {
		return uploadState;
	}

	public void setUploadState(String uploadState) {
		this.uploadState = uploadState;
	}

	public List<FinanceZYTModel> getFlist() {
		return flist;
	}

	public void setFlist(List<FinanceZYTModel> flist) {
		this.flist = flist;
	}

	public BigDecimal getFl() {
		return fl;
	}

	public void setFl(BigDecimal fl) {
		this.fl = fl;
	}

	public BigDecimal getOther() {
		return other;
	}

	public void setOther(BigDecimal other) {
		this.other = other;
	}

	public BigDecimal getFile() {
		return file;
	}

	public void setFile(BigDecimal file) {
		this.file = file;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getSb() {
		return sb;
	}

	public void setSb(BigDecimal sb) {
		this.sb = sb;
	}

	public BigDecimal getGjj() {
		return gjj;
	}

	public void setGjj(BigDecimal gjj) {
		this.gjj = gjj;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

}
