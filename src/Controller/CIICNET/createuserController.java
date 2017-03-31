package Controller.CIICNET;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmbaseModel;
import Model.loginroleModel;
import bll.Embase.Emba_Contactbll;
import bll.Embase.EmbaseListBll;
import bll.CIICNET.createuserbll;
import Model.Emcontactinfo;
import bll.Embase.SMS_CheckBll;
import Util.IdcardUtil;
import Util.UserInfo;
import bll.LoginBll;
import Model.LoginModel;
import Util.MD5;

public class createuserController {

	/**
	 * @param args
	 */
	int gid = Integer.parseInt(Executions.getCurrent().getArg().get("gid")
			.toString());
	private EmbaseModel embamodel= new EmbaseModel();
	private EmbaseListBll embabll =new EmbaseListBll();
	private createuserbll createuserb =new createuserbll();
	private Emcontactinfo emcmodel =new Emcontactinfo();
	
	Emba_Contactbll emcbll = new Emba_Contactbll();
	
	public createuserController()
	{
		System.out.println(gid);
		embamodel=embabll.getEmbaseInfo(" and gid="+gid );
		emcmodel =emcbll.getemcontactmodel(gid);
    }
	
	
	/**
	 * 创建网上中智账号
	 * @throws Exception 
	 */
	@Command("btnSubmit")
	public void btnSubmit(@BindingParam("picWin") Window win) throws Exception
	{
		
		if (createuserb.checkuser(embamodel.getEmba_idcard())>0)
		{
			if (embamodel.getEmba_mobile()==null || embamodel.getEmba_mobile().equals("")|| embamodel.getEmba_mobile().equals("0"))
			{
				Messagebox.show("亲请先补充该员工手机号码。", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
			else if(createuserb.createuser(embamodel)==0)
					{
				Messagebox
				.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				
				LoginBll lgbll=new LoginBll();
				LoginModel lmodel =new LoginModel();
				 
			 
				lmodel=lgbll.loginList(" and log_name='"+UserInfo.getUsername()+"'").get(0);
				 
				//发送入职短信
				SMS_CheckBll smsbll =new SMS_CheckBll();
				StringBuilder smsstr=new StringBuilder();
				smsstr.append(embamodel.getEmba_name());
				smsstr.append("您好，受");
				smsstr.append(embamodel.getCoba_shortname());
				smsstr.append("的委托我司将为您代缴深圳社保。请及时登陆深圳中智www.szciic.com-雇员入职系统完善您的个人信息");
				smsstr.append("。您登陆的用户名为您的姓名，登陆密码为您的身份证号码。如有疑问请联系我司客服");
				smsstr.append(lmodel.getLog_name());
				smsstr.append("0755-");
				smsstr.append(lmodel.getLog_tel());
				smsstr.append("【深圳中智】");
				try {
					//smsbll.sms_add(embamodel.getEmba_mobile(), "", UserInfo.getUsername());
				
					smsbll.sms_add(embamodel.getEmba_mobile(), smsstr.toString(), UserInfo.getUsername());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				emcmodel.setEmzt_contactstateweb("1");//更新网上中智状态：1未更新
				emcbll.updateEmcontent(emcmodel);
				win.detach();
				
					}
			 
			else {
				Messagebox.show("提交失败,请联系IT帅哥", "提示", Messagebox.OK, Messagebox.INFORMATION);
			}
		} else {
		Messagebox.show("已经创建账号", "提示", Messagebox.OK, Messagebox.INFORMATION);
		}
		
	}
	

	/**
	 * 查询社保电脑号
	 * 
	 */
	@Command
	public void computerid_search() {
		try {
			if (embamodel.getEmba_name() == null || embamodel.getEmba_name().isEmpty()) {
				Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (embamodel.getEmba_idcard() == null
					|| embamodel.getEmba_idcard().isEmpty()) {
				Messagebox.show("请输入身份证号码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (!IdcardUtil.validateCard(embamodel.getEmba_idcard())) {
				Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String url = "/Embase/Embase_Computerid_search.zul";
				String searurl = "http://dgciic:81/ComputeridSearch.aspx?idcard="
						+ embamodel.getEmba_idcard();
				Map<String, Object> map = new HashMap<>();
				map.put("url", searurl);

				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public EmbaseModel getEmbamodel() {
		return embamodel;
	}

	public void setEmbamodel(EmbaseModel embamodel) {
		this.embamodel = embamodel;
	}
	
	
	

}
