package Controller.EmCommercialInsurance;

import java.sql.SQLException;

import impl.MessageImpl;
import impl.MessageImplProxy;

import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import dal.EmCommercialInsurance.CI_Insurant_ListDal;

import service.MessageService;
import Model.CI_Insurant_ListModel;
import Model.SysMessageModel;
import Util.FileOperate;
import Util.UserInfo;
import service.MessageService;


public class Test_Controller {
	private MessageService serviceProxy;
	private MessageService msgservice;
	private ListModelList<CI_Insurant_ListModel> coli_lst;// 显示所选商保连带人类型

	
	public Test_Controller() throws SQLException{
		CI_Insurant_ListDal dal = new CI_Insurant_ListDal();
		
		coli_lst = new ListModelList<CI_Insurant_ListModel>(
				dal.getembase(""));
		 
		for (int i = 0; i < coli_lst.size(); i++) {
			
	
		System.out.println("aa");
		Integer k = 0;
		String[] str = new String[5];
		String content;
		
		content=coli_lst.get(0).getEcin_insurant()+"您好！附件为您投保的"+coli_lst.get(0).getEcin_castsort()+"型保险的商保手册。为了使您更好地了解深圳中智为您提供的商业保险的保障内容，并能获得我们提供的良好服务，请您仔细阅读本商保手册内容。希望本手册能够成为您随身的服务指南，帮助您实现“轻松工作、保险无忧”。 本手册仅为商业保险保障内容介绍及理赔指引，若内容与保险条款规定有冲突，以条款为准。手册将根据国家政策变化和服务需要随时进行更新和完善，请以深圳中智通知的最新版本为准。如在阅读或使用过程中有任何疑问，请联系深圳中智的雇员福利部商保组进行解答，也可发送邮件至ci_service@szciic.com。";
		
		SysMessageModel model = new SysMessageModel();
		model.setSyme_content(content);// 短信内容
		model.setSyme_url("");
		model.setSyme_reply_id(0);
		model.setSyme_log_id(5);// 发件人id
		model.setSmwr_type("");
		model.setSymr_name("张志强");// 收件人姓名
		model.setSymr_log_id(5);//收件人id
		model.setEmail(1);
		model.setSyme_title("投保手册测试");
		model.setEmailtitle("投保手册测试");

		String[][] filename = new String[1][2];
		filename[0][0] = "";
		filename[0][1] = FileOperate.getAbsolutePath() +"OfficeFile/Templet/EmCI/"+coli_lst.get(0).getEcin_castsort()+".pdf";
		System.out.println(filename[0][1] );
		model.setFileurl(filename);
		model.setEmail_address("zzq@szciic.com");
		msgservice = new MessageImpl("", 0);
		serviceProxy = new MessageImplProxy(msgservice);
		str = serviceProxy.Add(model);
		
		System.out.println("ok");
		
		}
	}
}
