package test;
import java.io.UnsupportedEncodingException;

import Conn.dbconn;
import Controller.systemSetController;
import Model.SocialInsuranceModel;
import Util.MailSenderInfo;
import Util.SimpleMailSender;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit; 

import org.zkoss.zul.ListModelList;

import bll.SocialInsurance.Algorithm_RegisteredDataBll;

public class sendmailtest {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	
	 //线程池能按时间计划来执行任务，允许用户设定计划执行任务的时间，int类型的参数是设定  
    //线程池中线程的最小数目。当任务较多时，线程池可能会自动创建更多的工作线程来执行任务  
    //此处用Executors.newSingleThreadScheduledExecutor()更佳。
    public ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(1);  
  
    //启动计时器  
    public void lanuchTimer(){  
        Runnable task = new Runnable() {  
            public void run() {  
                throw new RuntimeException();  
            }  
        };  
        scheduExec.scheduleWithFixedDelay(task, 1000*20, 1000*40, TimeUnit.MILLISECONDS);  
    }  
  
	public static  void main(String[] args) throws UnsupportedEncodingException {
//		// TODO Auto-generated method stub
//		String [][] AttachFile =new String[2][2];
//		String [] toaddress=new String[2];  //目标邮件
//		String [] csaddress={"cyj@szciic.com"}; //抄送邮件
//		String [] msaddress = new String[1]; //密送邮件
//		AttachFile[0][0]= new String ("北京考勤.xls");
//		AttachFile[0][1]= new String ("D:\\2014公积金卡.xlsx");
//		
//		
//		toaddress[0]=new String("zmj@szciic.com");
//		toaddress[1]=new String("lwj@szciic.com");
//		csaddress[0]=new String("zmj@szciic.com");
//		msaddress[0]=new String("zzq@szciic.com");
//		
//		
//		
//		 MailSenderInfo mailInfo = new MailSenderInfo();    
//		 mailInfo.setMailServerHost("smtp.szciic.com");  //发送服务器  
//		 mailInfo.setMailServerPort("25");    //发送端口 
//		 mailInfo.setValidate(true);     
//		 mailInfo.setUserName("cyj@szciic.com");  //用户名  
//		 mailInfo.setPassword("cyj1426");//您的邮箱密码    
//		 mailInfo.setFromAddress("cyj@szciic.com"); //发送邮箱名   
//		 mailInfo.setToAddress(toaddress);  //目标邮箱名  
//		 mailInfo.setCsAddress(csaddress); //抄送邮件名
//		 mailInfo.setMsAddress(msaddress); //密送邮件名
//		 mailInfo.setSubject("封测试邮件");    		   //邮件标题
//		 mailInfo.setContent("邮件测试1111111");     //邮件正文
//		 mailInfo.setAttachFileNames(AttachFile);  //邮件附件 String[0]为附件名，String[1]为附件地址
// 	 
//		    //这个类主要来发送邮件   
//		 SimpleMailSender sms = new SimpleMailSender();   
//		 sms.sendTextMail(mailInfo);//发送文体格式    
//	  // sms.sendHtmlMail(mailInfo);//发送html格式   
//		 
		 
		 List<SocialInsuranceModel> list = new ListModelList<>();
			Algorithm_RegisteredDataBll bll = new Algorithm_RegisteredDataBll();
			String sql = "SELECT soin_id,sial_id,sial_execdate" +
					" FROM SocialInsurance a" +
					" inner join (select * from SocialInsuranceAlgorithm" +
					" where sial_id in (select MAX(sial_id) from SocialInsuranceAlgorithm" +
					" where datediff(d,sial_execdate,GETDATE())>=0" +
					" group by sial_soin_id)" +
					") b on a.soin_id=b.sial_soin_id" +
					" where soin_state=1 and soin_id>4   AND a.soin_id " +
					"= 478";
			
//				"IN (SELECT sich_soin_id from SocialInsuranceChange where sich_addtime>'2015-11-11'  )";
			dbconn db=new dbconn();
			
			System.out.println(sql);
			try {
				list=db.find(sql, SocialInsuranceModel.class, null);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(list.size());
			for (SocialInsuranceModel m : list) {
			//	System.out.println(m.getSoin_id()+","+m.getSial_id()+","+m.getSial_execdate());
				bll.upRegData(m.getSoin_id(), m.getSial_id(), m.getSial_execdate());
			}
			//
			System.out.println("**");


	}

}
