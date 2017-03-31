package Controller.CoBase;

import impl.MessageImpl;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import service.MessageService;

import dal.LoginDal;
import dal.CoBase.CoBase_OperateDal;

import Model.CoBaseModel;
import Model.PubTradeModel;
import Model.SysMessageModel;
import Util.UserInfo;
import bll.CoBase.CoBase_OperateBll;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.CoLatencyClient.CoServiceRequestSelectBll;

public class CoBaseInfo_AddController extends SelectorComposer<Component>{
	@Wire
	private Combobox trade;//所属行业
	@Wire
	private Combobox clientsource;//客户来源
	@Wire
	private Combobox clientsize;//客户规模
	@Wire
	private Combobox clientstyle;//客户类型
	@Wire
	private Combobox ClientArea;//客户所在区域
	@Wire
	private Combobox client;//客服
	@Wire
	private Combobox clientmanager;//客服经理
	@Wire
	private Combobox deptclientmanager;//部门经理
	@Wire
	private Combobox kind;//企业客户性质
	@Wire
	private Textbox company;//公司名称
	@Wire
	private Textbox shortname;//公司简称
	@Wire
	private Textbox website;//公司网址
	@Wire
	private Textbox enname;//公司英文名
	@Wire
	private Textbox registermaney;//注册资金
	@Wire
	private Datebox hzbegindate;//合作起始日
	@Wire
	private Textbox bgaddress;//办公地址
	@Wire
	private Textbox invoiceadd;//发票邮寄地址
	@Wire
	private Textbox remark;//备注
	private CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
	private ListModel<PubTradeModel> tradlist;
	private ListModel<String> loginlist;
	private ListModel<String> loginlist2;
	private ListModel<String> loginlist3;
	private CoServiceRequestSelectBll sbll=new CoServiceRequestSelectBll();
	private ListModel managerlist;
	private List<String> li=sbll.getManagerlist();
	List<String> models;
	
	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		tradlist=new ListModelList<PubTradeModel>(bll.getTradeIndo());
		trade.setModel(tradlist);
		models=bll.getLoginInfo();
		loginlist=new ListModelList<String>(models);
		loginlist2=new ListModelList<String>(models);
		loginlist3=new ListModelList<String>(models);
		managerlist=new ListModelList<String>(li);
		client.setModel(loginlist);
		clientmanager.setModel(loginlist2);
		deptclientmanager.setModel(managerlist);
	}
	
	//公司信息新增
	@Listen("onClick = #addcobase")
	public void addCoBase(){
		String str=isEmploy();
		if(str=="")
		{
		CoBase_OperateBll addcobasebll=new CoBase_OperateBll();
		CoBaseModel model=new CoBaseModel();
		model.setCoba_company(company.getValue().trim());
		model.setCoba_shortname(shortname.getValue().trim());
		model.setCoba_address(bgaddress.getValue());
		model.setCoba_englishname(enname.getValue());
		model.setCoba_clientsource(clientsource.getValue());
		model.setCoba_clientsize(clientsize.getValue());
		model.setCoba_industytype(trade.getValue());
		model.setCoba_setuptype(clientstyle.getValue());
		model.setCoba_area(ClientArea.getValue());
		if(registermaney.getValue()==""||registermaney.getValue().equals(""))
		{
			model.setCoba_reg_fund(null);
		}
		else
		{
			model.setCoba_reg_fund(registermaney.getValue());
		}
		model.setCoba_manageraddress(bgaddress.getValue());
		model.setCoba_invoiceaddress(invoiceadd.getValue());
		model.setCoba_client(client.getValue());
//		model.setCoba_manager(clientmanager.getValue());
//		model.setCoba_clientmanager(deptclientmanager.getValue());
		model.setCoba_clientmanager(clientmanager.getValue());
		model.setCoba_manager(deptclientmanager.getValue());
		model.setCoba_website(website.getValue());
		String datestr=null;
		if(hzbegindate.getValue()!=null)
		{
			datestr=addcobasebll.DatetoSting(hzbegindate.getValue());
		}
		model.setCoba_hzqsr(datestr);
		model.setCoba_remark(remark.getValue());
		model.setCoba_addname(UserInfo.getUsername());
		model.setCoba_kind(kind.getValue());
		int k=addcobasebll.addCoBaseInfo(model);
		
		//权限添加
		bll.Data_Popedomalladd(k, client.getValue());
		
		MessageService msgservice=new MessageImpl("CoBase",k);
		SysMessageModel msgmodel=new SysMessageModel();
				
		LoginDal d =new LoginDal();
	 
		String tittle="请分配中心员服";
		msgmodel.setEmail(1);
		msgmodel.setEmailtitle(tittle);
		msgmodel.setSyme_content("请分配公司编号:"+k+"的员服人员");
		msgmodel.setSymr_name("曾琴娜");// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname("曾琴娜"));// ;
		msgservice.Add(msgmodel);
		msgmodel.setSymr_name("林敏");// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname("林敏"));// ;
		msgservice.Add(msgmodel);
		msgmodel.setSymr_name("郭姗姗");// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname("郭姗姗"));// ;
		msgservice.Add(msgmodel);
		
		msgmodel.setSymr_name("赵敏捷");// 收件人姓名
		msgmodel.setSymr_log_id(d.getUserIDByname("赵敏捷"));// ;
		msgservice.Add(msgmodel);
		
		
		if(k>0){
			Messagebox.show("添加成功","提示",Messagebox.OK, Messagebox.INFORMATION);
			Executions.sendRedirect("/cobase/CobaseInfo_Add.zul");
		}
		else
		{
			Messagebox.show("添加失败","提示",Messagebox.OK, Messagebox.INFORMATION);
		}
		}
		else
		{
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	
	
	private String isEmploy()
	{
		String str="";
		if(company.getValue()==""||company.getValue().equals(""))
		{
			str="公司名称不能为空";
		}
		else if(sbll.ifExist(company.getValue()))
		{
			str="公司名称已经存在";
		}
		else if(shortname.getValue()==""||shortname.getValue().equals(""))
		{
			str="公司简称不能为空";
		}
		else if(sbll.ifExistShortName(shortname.getValue()))
		{
			str="公司简称已经存在";
		}
		else if(kind.getValue()==""||kind.getValue().equals(""))
		{
			str="企业客户性质不能为空";
		}
		else if(client.getValue()==""||client.getValue().equals(""))
		{
			str="请选择客服";
		}
		else if(clientmanager.getValue()==""||clientmanager.getValue().equals(""))
		{
			str="请选择客服经理";
		}
		else if(deptclientmanager.getValue()==""||deptclientmanager.getValue().equals(""))
		{
			str="请选择部门经理";
		}
		else if(registermaney.getValue()!=""&&!registermaney.getValue().equals(""))
		{
			if(isNum(registermaney.getValue())==false)
			{
				str="注册资金只能是数字";
			}
		}
		
		return str;
	}
	
	//检查公司名称是否已存在
	@Listen("onChange = #company")
	public void ifExist(){
		if(company.getValue()!=null&&!company.getValue().equals(""))
		{
			boolean flag=sbll.ifExist(company.getValue());
			if(flag)
			{
				Messagebox.show("公司名称已经存在","提示",Messagebox.OK, Messagebox.INFORMATION);
				company.setFocus(true);
			}
		}
	}
	
	//检查公司简称是否已存在
	@Listen("onChange = #shortname")
	public void ifExistShortName(){
		if(shortname.getValue()!=null&&!shortname.getValue().equals(""))
		{
			boolean flag=sbll.ifExistShortName(shortname.getValue());
			if(flag)
			{
				Messagebox.show("公司简称已经存在","提示",Messagebox.OK, Messagebox.INFORMATION);
				shortname.setFocus(true);
			}
		}
	}
	public ListModel<PubTradeModel> getTradlist() {
		return tradlist;
	}

	public void setTradlist(ListModel<PubTradeModel> tradlist) {
		this.tradlist = tradlist;
	}

	public ListModel<String> getLoginlist() {
		return loginlist;
	}

	public void setLoginlist(ListModel<String> loginlist) {
		this.loginlist = loginlist;
	}
	//判断字符串是否都是数字
	public static boolean isNum(String str){
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}
