/*
 * 创建人：林少斌
 * 创建时间：2013-9-22
 * 用途：委托机构联系人页面Controller
 */
package Controller.CoAgency;

import java.text.SimpleDateFormat;
import java.util.Date;

import impl.UserInfoImpl;

import org.zkoss.lang.Exceptions;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import bll.CoAgency.Linkman_UpBll;
import Model.CoAgencyLinkmanModel;
import service.UserInfoService;

public class LinkMan_UpdateController  extends SelectorComposer<Component>{
	
	private Linkman_UpBll updateBll = new Linkman_UpBll();
	private String chkVip;	//是否重要联系人
	int cali_id = ((CoAgencyLinkmanModel)Executions.getCurrent().getArg().get("caliM")).getCali_id();		//联系人id
	//获取用户名
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	Date bDate = new Date();

	@Wire
	private Textbox cali_linkman;	//联系人组名
	@Wire
	private Checkbox cali_vip;		//是否重要联系人
	@Wire
	private Textbox cali_name;	//姓名
	@Wire
	private Textbox cali_ename; 	//英文名
	@Wire
	private Textbox cali_mobile;		//手机
	@Wire
	private Textbox cali_tel;		//座机
	@Wire
	private Textbox cali_job;	//职位
	@Wire
	private Textbox cali_fax;	//传真
	@Wire
	private Datebox cali_birth;	//生日
	@Wire
	private Textbox cali_hobby;	//兴趣爱好
	@Wire
	private Textbox cali_email;	//Email
	@Wire
	private Textbox	cali_address;	//联系地址
	@Wire
	private Textbox cali_personality;	//个性描述
	@Wire
	private Textbox	cali_remark;		//备注
	@Wire
	private Window w1;
	
	
	
	public LinkMan_UpdateController() {
		//获取生日
		String birth = ((CoAgencyLinkmanModel)Executions.getCurrent().getArg().get("caliM")).getCali_birth();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			bDate = sdf.parse(birth);
		}
		catch(Exception e){
			bDate = null;
		}
	}



	//修改数据方法
	@Listen("onClick=#btSubmit")
	public void updateBase() throws Exception{
		//判断必填项是否为空
		if(!"".equals(cali_id) && !cali_linkman.getValue().equals("") && !cali_name.getValue().equals("") && (!cali_mobile.getValue().equals("") || !cali_tel.getValue().equals(""))){
			//判断“是否重要联系人”是否选中
			if(cali_vip.isChecked()){
				chkVip="1";
			}
			else {
				chkVip="0";
			}
			
			//生日判断
			String birth="";
			if(cali_birth.getValue() != null){
				birth = updateBll.DatetoSting(cali_birth.getValue());
			}	
			//调用修改方法
			String[] message = updateBll.UpdateLinkmanBase(cali_id, cali_linkman.getValue(), cali_name.getValue(),
					cali_ename.getValue(), cali_job.getValue(), cali_tel.getValue(), cali_mobile.getValue(), cali_fax.getValue(), 
					cali_email.getValue(), birth, cali_hobby.getValue(), cali_address.getValue(), cali_personality.getValue(), 
					cali_remark.getValue(),chkVip, username);
		
			//弹出提示并跳转页面
			if (message[0].equals("1")) {
	            EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	                public void onEvent(ClickEvent event) throws Exception {
	                    if (Messagebox.Button.OK.equals(event.getButton())) {
	                       // Executions.sendRedirect("BaseInfo_UpdateList.zul");	//跳转页面
	                    	w1.detach();
	                    }
	                }
	            };
	            //弹出提示
	            Messagebox.show(message[1], "操作提示", new Messagebox.Button[] { Messagebox.Button.OK },
	            		Messagebox.INFORMATION, clickListener);
			}
			else {
				//弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		
		}
		else{
			if(cali_linkman.getValue().equals("")){
				Messagebox.show("请录入“联系人组名”", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
			if(cali_name.getValue().equals("")){
				Messagebox.show("请录入“姓名”", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
			if(cali_mobile.getValue().equals("") && cali_tel.getValue().equals("")){
				Messagebox.show("请录入“手机”或“座机”", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}



	public Date getbDate() {
		return bDate;
	}



	public void setbDate(Date bDate) {
		this.bDate = bDate;
	}
	
	
}
