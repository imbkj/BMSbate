/*
 * 创建人：林少斌
 * 创建时间：2013-9-22
 * 用途：委托机构基本修改页面Controller
 */

package Controller.CoAgency;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;

import bll.CoAgency.BaseInfo_AddBll;
import bll.CoAgency.BaseInfo_UpBll;

import service.DataPopedomService;
import service.UserInfoService;
import Model.LoginModel;
import Model.CoAgencyBaseModel;

public class BaseInfo_UpdateController extends SelectorComposer<Component>{
	private BaseInfo_AddBll addBll = new BaseInfo_AddBll();
	private BaseInfo_UpBll updateBll = new BaseInfo_UpBll();
	private List provincelist;		//省份
	private List citylist;		//城市
	private List<LoginModel> clientList = new ArrayList<LoginModel>();  //客服
	private DataPopedomService d =new Data_PopedomIpml("","全国项目部委托");
	//获取用户名
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
    String username = user.getUsername();
    

    int coab_id =((CoAgencyBaseModel) Executions.getCurrent().getArg().get("coabM")).getCoab_id();
	@Wire
	private Combobox cbSetuptype;	//机构性质
	@Wire
	private Combobox cbProvince;	//所属省
	@Wire
	private Combobox cbCity;		//所属市
	@Wire 
	private Combobox cbSource;	//客户来源
	@Wire
	private Textbox tbCapital;		//注册资金
	@Wire
	private Textbox tbRegaddress;	//注册地址
	@Wire
	private Textbox tbCompanymanager;	//法定代表人
	@Wire
	private Textbox tbCoaddress;	//联系地址
	@Wire
	private Textbox tbZipcode;	//公司邮编
	@Wire
	private Textbox tbBusiness;	//机构业务范围
	@Wire
	private Combobox cbSendinvoice;	//发票是否寄送
	@Wire
	private Combobox cbPayday;	//约定付款日期
	@Wire
	private Combobox cbPaymon;	//约定付款月份
	@Wire
	private Textbox tbAccountbank;	//开户行
	@Wire
	private Textbox tbAccountnum;	//银行账户
	@Wire
	private Combobox cbHz;	//合作状态
	@Wire
	private Textbox tbRemark;	//备注
	@Wire
	private Combobox cbClient;	//客服
	@Wire
	private Window w1;
	
	public BaseInfo_UpdateController() throws Exception{
		//省份下拉框列表
		provincelist=addBll.getProvinceName();
				
		//客服下拉框列表
		clientList = d.getroleLoginlist();	
	}
	
	//根据省份查询城市
	@Command("selCity")
	@NotifyChange("citylist")
	public void selCity(@BindingParam("contact") String province) throws Exception{
		setCitylist(addBll.getCityName(province));
	}
	
	//修改基本信息
	@Listen("onClick = #btSubmit")
	public void update() throws Exception{
		System.out.print(coab_id);
		//判断必填项是否为空
		if(!"".equals(coab_id) && !cbProvince.getValue().equals("") && !cbCity.getValue().equals("") && !cbSetuptype.getValue().equals("")){
			//调用修改方法
			String[] message = updateBll.UpdateBase(coab_id, cbProvince.getValue(), cbCity.getValue(), cbSetuptype.getValue(), cbSource.getValue(),
					tbCapital.getValue(), tbRegaddress.getValue(), tbCompanymanager.getValue(), tbCoaddress.getValue(), tbZipcode.getValue(),
					tbBusiness.getValue(), cbSendinvoice.getValue(),cbPayday.getValue(),cbPaymon.getValue(), tbAccountbank.getValue(),
					tbAccountnum.getValue(), cbHz.getValue(), tbRemark.getValue(), cbClient.getValue(), username);
			
			//弹出提示并跳转页面
			if (message[0].equals("1")) {
	            EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	                public void onEvent(ClickEvent event) throws Exception {
	                    if (Messagebox.Button.OK.equals(event.getButton())) {
	                        //Executions.sendRedirect("BaseInfo_UpdateList.zul");	//跳转页面
	                    	w1.detach();
	                    }
	                }
	            };
	            //弹出提示
	            Messagebox.show(message[1], "操作提示", new Messagebox.Button[] { Messagebox.Button.OK },
	            		Messagebox.INFORMATION, clickListener);
			}
			else{
				//弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
		else{
			if(cbSetuptype.getValue().equals("")){
				Messagebox.show("请选择“机构性质”！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
			else if(cbProvince.getValue().equals("")){
				Messagebox.show("请选择“省份”！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
			else if(cbCity.getValue().equals("")){
				Messagebox.show("请选择“城市”！", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	public List getProvincelist() {
		return provincelist;
	}

	public void setProvincelist(List provincelist) {
		this.provincelist = provincelist;
	}

	public List getCitylist() {
		return citylist;
	}

	public void setCitylist(List citylist) {
		this.citylist = citylist;
	}

	public List<LoginModel> getClientList() {
		return clientList;
	}

	public void setClientList(List<LoginModel> clientList) {
		this.clientList = clientList;
	}
}
