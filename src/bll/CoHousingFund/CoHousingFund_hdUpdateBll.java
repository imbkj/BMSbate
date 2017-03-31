package bll.CoHousingFund;

import impl.MessageImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.zkoss.zul.ListModelList;

import service.MessageService;

import Model.CoHousingFundChangeModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.SysMessage.SysMessage_AddBll;

import dal.CoHousingFund.CoHousingFund_OperateDal;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

public class CoHousingFund_hdUpdateBll {

	public Integer addFiledata(File file, CoHousingFundChangeModel cm) {
		Integer i = 0;
		Workbook wb;

		try {
			wb = Workbook.getWorkbook(file);
			Sheet[] sheets = wb.getSheets();
			if (sheets == null || sheets.length == 0) {
				throw new NullPointerException("该文件没有工作页面");
			} else {
				CoHousingFund_OperateDal dal = new CoHousingFund_OperateDal();
				i = dal.uploadfile(cm.getChfc_id(), file.getName());

				SysMessageModel model = new SysMessageModel();
				model.setSyme_content(cm.getChfc_company()
						+ ",公积金缴存登记申请表已上传系统，请尽快打印给客户盖章。");// 短信内容
				model.setSyme_url("");
				model.setSyme_reply_id(0);
				model.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));// 发件人id
				model.setSmwr_type("");
				model.setSymr_name(cm.getChfc_addname());// 收件人姓名
				model.setEmail(1);
				model.setSyme_title(cm.getChfc_company() + ",公积金独立户有反馈信息");
				model.setEmailtitle(cm.getChfc_company() + ",公积金独立户有反馈信息");
				MessageService msgservice = new MessageImpl(
						"CoHousingFundChange", cm.getChfc_id());
				msgservice.Add(model);
			}

		} catch (BiffException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return i;
	}

}
