package Controller.CoLatencyClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import impl.UserInfoImpl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.CoLatencyClient.CoLatencyClient_AddBll;

import service.UserInfoService;

import Model.CoAgencyLinkmanModel;
import Model.CoLatencyClientModel;

public class ColaClientLinkManUpdate_Controller extends SelectorComposer<Component> implements AfterCompose{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox linkname;
	@Wire
	private Textbox job;
	@Wire
	private Textbox tel;
	@Wire
	private Textbox tel1;
	@Wire
	private Textbox mobile;
	@Wire
	private Textbox fax;
	@Wire
	private Textbox email;
	@Wire
	private Textbox email1;
	@Wire
	private Textbox cali_duty;
	@Wire
	private Textbox remark;
	@Wire
	private Window winb;
	CoAgencyLinkmanModel frommodel = (CoAgencyLinkmanModel)Executions.getCurrent().getArg().get("linkmodel");
	CoLatencyClientModel addmodel = (CoLatencyClientModel)Executions.getCurrent().getArg().get("model");
	@Override
	public void afterCompose() {
		// TODO Auto-generated method stub
		
	}
	
	//联系人信息更新事件
	@Listen("onClick = #updatelinkman")
	public void updatelinkmanClientList(){
		CoAgencyLinkmanModel model=new CoAgencyLinkmanModel();
		String str="";
		str=ifStandard(model);
		if(str=="")
		{
			CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
			model.setCali_id(frommodel.getCali_id());
			str=bll.CoLatencyClientLinkmanupdate_P_cyj(model);
			if(str=="修改成功"||str.equals("修改成功"))
			{
				Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
				//Executions.sendRedirect("CoLatencyClient_InfoList.zul");
				winb.detach();
			}
			else
			{
				Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			}
		}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	//添加联系人信息事件
	@Listen("onClick = #addlinkman")
	public void addlinkmanClientList(){
		String str="";
		CoAgencyLinkmanModel model=new CoAgencyLinkmanModel();
		str=ifStandard(model);
		if(str=="")
		{
			CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
			int k=bll.CoLatencyClientLinkmanAdd(model,addmodel.getCola_id());
			if(k>0)
			{
				str="添加失败";
				Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			}
			else if(k==-1)
			{
				str="系统错误，请联系开发人员";
				Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			}
			else
			{
				str="添加成功";
				Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
				//Executions.sendRedirect("CoLatencyClient_InfoList.zul");
				winb.detach();
			}
		}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	//判断字符串是否是数字
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	//判断Email的格式是否正确
	public static boolean isEmail(String str){
		boolean tag = true;  
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(str);
		if (!mat.find()) {  
            tag = false;  
        }
		return tag;
	}
	
	//判断输入的用户名，电话号码，邮件是否符合要求
	private String ifStandard(CoAgencyLinkmanModel model)
	{
		Session session =  Executions.getCurrent().getDesktop().getSession();
		UserInfoService uservice=new UserInfoImpl(session);
		String username=uservice.getUsername();
		String str="";
		if(linkname.getValue()!=""&&!linkname.getValue().equals(""))
		{
			model.setCali_name(linkname.getValue());
			model.setCali_job(job.getValue());
			if(tel.getValue()!=""&&!tel.getValue().equals(""))
			{
				model.setCali_tel(tel.getValue());
			}
			if(tel1.getValue()!=""&&!tel1.getValue().equals(""))
			{
				model.setCali_tel1(tel1.getValue());
			}
			if(mobile.getValue()!=""&&!mobile.getValue().equals(""))
			{
				model.setCali_mobile(mobile.getValue());
			}
			model.setCali_fax(fax.getValue());
			if(email.getValue()!=""&&!email.getValue().equals(""))
			{
				model.setCali_email(email.getValue());
			}
			if(email1.getValue()!=""&&!email1.getValue().equals(""))
			{
				model.setCali_email1(email1.getValue());
			}
			model.setCali_duty(cali_duty.getValue());
			model.setCali_remark(remark.getValue());
			model.setCali_modname(username);
			model.setCali_addname(username);
		}
		else
		{
			str="联系人姓名不能为空";
		}
		return str;
	}
	
	//判断输入的电话号码是否有错误
		public static boolean isTel(String str){
			boolean flag=false;
			flag=isNum(str);
			if(flag==false)
			{
				int k= str.length();
				for(int i=0;i<k;i++)
				{
					if(str.substring(i, i+1)!=null)
					{
						if(!str.substring(i, i+1).equals("-")&&!isNum(str.substring(i, i+1)))
						{
							flag=false;
							break;
						}
						else
						{
							flag=true;
						}
					}
				}
			}
			return flag;
		}

}
