package Controller.CoLatencyClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import impl.UserInfoImpl;
import impl.SystemControl.Data_PopedomIpml;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import com.zhuozhengsoft.pageoffice.wordwriter.Column;

import service.DataPopedomService;
import service.UserInfoService;

import Model.CoAgencyLinkmanModel;
import Model.CoBaseModel;
import Model.CoLaContactsModel;
import Model.CoLatencyClientModel;
import Model.CreditAppraiseModel;
import Model.CreditContentInfoModel;
import Model.CreditCriterionModel;
import Model.CreditInfoModel;
import Model.LoginModel;
import Model.PubTradeModel;
import Util.pinyin4jUtil;
import bll.CoLatencyClient.CoLatencyClient_AddBll;
import bll.SystemControl.Data_PopedomBll;

public class CoLatencyClient_AddController extends SelectorComposer<Component>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Window colawin;
	@Wire
	private Combobox company;
	@Wire
	private Textbox website;
	@Wire
	private Textbox address;
	@Wire
	private Combobox clientstyle;
	@Wire
	private Combobox ifsign;
	@Wire
	private Combobox ClientArea;
	@Wire
	private Combobox clientsize;
	@Wire
	private Combobox trade;
	@Wire
	private Combobox follower;
	@Wire
	private Combobox clientsource;
	@Wire
	private Combobox slevel;
	@Wire
	private Intbox ownyear;
	@Wire
	private Textbox servicecontent;
	@Wire
	private Textbox remark;
	@Wire
	private Grid linkinfo;
	@Wire
	private Rows credit;
	@Wire
	private Grid gridcredit;
	@Wire 
	private Vlayout notesvla;
	@Wire
	private Combobox realsize;
	@Wire
	private Combobox ssource;
	
	@Wire
	private Combobox kind;
	List<CoAgencyLinkmanModel> ljlist;
	
	private String companyname="";
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService uservice=new UserInfoImpl(session);
	String username=uservice.getUsername();
	CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
	List<PubTradeModel> trademodel=bll.getTradeIndo();
	
	//获取跟进人信息
	private DataPopedomService dps = new Data_PopedomIpml("13", "");
	private List<String> personList =bll.getpidLoginlist(); // 执行人
	private List<String> list =new ArrayList<String>(); // 执行人
	private Integer nums=0;
	
	// 重写组件初始化方法
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
		//获取当前年份
		
		 Calendar cal = Calendar.getInstance();
		 int year = cal.get(Calendar.YEAR);
		 
		ownyear.setValue(year);
		addCredit();
		nums=list.size();
	}
	
	//潜在客户新增
	@Listen("onClick = #addbtn")
	public void addCoLatencyClient(){
		
		String syt=ifEmploy();
		if(syt=="")
		{
			String stris=colaLinkPeopExist();
			
			if(stris==""&&stris.equals(""))
			{
				pinyin4jUtil util=new pinyin4jUtil();
				CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
				CoLatencyClientModel model=new CoLatencyClientModel();
				model.setCola_company(company.getValue());
				model.setCola_companytype(clientstyle.getValue());
				model.setCola_website(website.getValue());
				model.setCola_address(address.getValue());
				model.setCola_sign(Integer.parseInt((String) ifsign.getSelectedItem().getValue()));
				model.setCola_clientarea(ClientArea.getValue());
				model.setCola_kind(kind.getValue());
				model.setCola_clientsize(clientsize.getValue());
				model.setCola_trade(trade.getValue());
				model.setCola_clientsource(clientsource.getValue());
				model.setCola_successlevel(Integer.parseInt((String) slevel.getSelectedItem().getValue()));
				model.setCola_ownyear(ownyear.getValue());
				model.setCola_servicecontent(servicecontent.getValue());
				model.setCola_remark(remark.getValue());
				model.setCola_addname(username);
				model.setCola_servicessource(ssource.getValue());
				if (follower.getValue()!=null && !follower.getValue().equals("")) {
					model.setCola_follower(follower.getValue());
				}else {
					model.setCola_follower(username);
				}
				
				model.setCola_realsize(realsize.getValue());
				String spellname=util.getPinYinHeadChar(company.getValue());
				model.setCola_spell(spellname);
				int rowid=bll.CoLatencyClient_Add(model);
				
				//潜在客户跟进人权限添加
				Data_PopedomBll ddll =new Data_PopedomBll();
				ddll.Data_Popedomaddcz(rowid, follower.getValue());
				//潜在客户添加人权限添加
				ddll.Data_Popedomaddcz(rowid, username);
				
				
				if(rowid>0)
				{
					addCriteInfo(rowid);
					if(!ljlist.isEmpty())
					{
						for(int j=0;j<ljlist.size();j++)
						{
							bll.CoLatencyClientLinkmanAdd(ljlist.get(j), rowid);
						}
					}
					
					//添加联系记录
					List<Component> hl=notesvla.getChildren();
					if(!hl.isEmpty())
					{
						for(int h=0;h<hl.size();h++)
						{
							//获取联系时间
							Hlayout hdb=(Hlayout) (hl.get(h).getChildren().get(0));
							Datebox dbox=(Datebox) hdb.getChildren().get(0);
							
							//获取联系记录内容
							Hlayout htxt=(Hlayout) (hl.get(h).getChildren().get(1));
							Textbox txtbox=(Textbox) htxt.getChildren().get(0);
							if(dbox.getValue()!=null&&!txtbox.getValue().equals("")&&txtbox.getValue()!="")
							{
								CoLaContactsModel colamodel=new CoLaContactsModel();
								colamodel.setClco_content(txtbox.getValue());
								colamodel.setClco_addname(username);
								colamodel.setClco_calo_id(rowid);
								colamodel.setClco_linktime(dbox.getValue());
								bll.addCoLaContactsInfo(colamodel);
							}
						}
					}
					
					Messagebox.show("添加成功","提示",Messagebox.OK, Messagebox.INFORMATION);
					//Executions.sendRedirect("/CoLatencyClient/CoLatencyClient_Add.zul");
					colawin.detach();
				}
				else if(rowid==-1)
				{
					Messagebox.show("系统出错了，请联系开发人员","提示",Messagebox.OK, Messagebox.ERROR);
				}
				else
				{
					Messagebox.show("添加失败","提示",Messagebox.OK, Messagebox.ERROR);
				}
			}
			else
			{
				Messagebox.show(stris,"提示",Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	//判断是否存在公司名称
	@Command
	@NotifyChange({"list","nums"})
	public void ifExistcompanyName(@BindingParam("combo") Combobox combo)
	{
		if(companyname.trim()!=""&&!companyname.trim().equals(""))
		{
			
		}
		else
		{
			Messagebox.show("公司名称不能为空","提示",Messagebox.OK, Messagebox.ERROR);
		}
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public List<PubTradeModel> getTrademodel() {
		return trademodel;
	}
	public List<String> getPersonList() {
		return personList;
	}
	//判断填写数据是否为空的方法
	private String ifEmploy()
	{
		String str="";
		if(company.getValue()==null||company.getValue().trim().equals(""))
		{
			str="公司名称不能为空,请输入公司名称";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			company.focus();
		}
		
		else if(clientstyle.getValue()==null||clientstyle.getValue().equals(""))
		{
			str="请选择公司类型";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			clientstyle.focus();			
		}
		
		else if(address.getValue()==null||address.getValue().equals(""))
		{
			str="请输入公司地址";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			address.focus();
		}
		else if(ifsign.getValue()==null||ifsign.getValue().equals(""))
		{
			str="请选择国内是否注册";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			ifsign.focus();
		}
		else if(ClientArea.getValue()==null||ClientArea.getValue().equals(""))
		{
			str="请选择客户位置";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			ClientArea.focus();
		}
		else if(kind.getValue()==null||kind.getValue().equals(""))
		{
			str="请选择客户企业性质";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			kind.focus();
		}
		else if(clientsize.getValue()==null||clientsize.getValue().equals(""))
		{
			str="请输入客户规模";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			clientsize.focus();
		}
		
		else if(trade.getValue()==null||trade.getValue().equals(""))
		{
			str="请选择所属行业";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			trade.focus();
		}
		
		else if(clientsource.getValue()==null||clientsource.getValue().equals(""))
		{
			str="请选择客户来源";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			clientsource.focus();
		}
		
		else if(slevel.getValue()==null||slevel.getValue().equals(""))
		{
			str="请选择成功率";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			slevel.focus();
		}
		
		else if(ownyear.getValue()==null||ownyear.getValue().equals(""))
		{
			str="请输入所属年份";
			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.ERROR);
			ownyear.focus();
		}
		
//		else if(servicecontent.getValue()==""||servicecontent.getValue().equals(""))
//		{
//			str="请输入客户要求服务内容";
//			Messagebox.show(str,"提示",Messagebox.OK, Messagebox.INFORMATION);
//			servicecontent.focus();
//		}
		return str;
	}
	
	//生成信誉评价的标准项
	private void addCredit()
	{
		CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
		List<CreditCriterionModel> creditmodel=bll.getCreditIndoValid();
		if(!creditmodel.isEmpty())
		{
			int numr=creditmodel.size()%2;
			int y=0;
			if(numr==0)
			{
				y=creditmodel.size()/2;
			}
			else
			{
				y=creditmodel.size()/2+1;
			}
			int h=0;
			for(int i=0;i<y;i++)
			{
				Row newrow=new Row();
				newrow.setParent(credit);
				Cell newcell1=new Cell();
				Cell newcell2=new Cell();
				Cell newcell3=new Cell();
				Cell newcell4=new Cell();
				
				if(h<creditmodel.size())
				{
					Label lal=new Label(creditmodel.get(h).getCrcr_content());
					lal.setParent(newcell1);
					List<CreditContentInfoModel> conmodel=bll.getCreditContentInfoValid(creditmodel.get(h).getCrcr_id());
					if(!conmodel.isEmpty())
					{
						Combobox cmb=new Combobox();
						Comboitem cmbIt=new Comboitem();
						cmb.setReadonly(true);
						cmbIt.setParent(cmb);
						cmbIt.setValue(""+creditmodel.get(h).getCrcr_id());
						for(int m=0;m<conmodel.size();m++)
						{
							Comboitem cmbItem=new Comboitem();
							cmbItem.setParent(cmb);
							cmbItem.setLabel(conmodel.get(m).getCrst_name());
							cmbItem.setValue(""+creditmodel.get(h).getCrcr_id());
						}
						cmb.setParent(newcell2);
					}
					else	
					{
						Combobox cmb=new Combobox();
						Comboitem cmbItem=new Comboitem();
						cmbItem.setValue(""+creditmodel.get(h).getCrcr_id());
						cmb.setButtonVisible(false);
						cmbItem.setParent(cmb);
						cmb.setParent(newcell2);
					}
				}
				if(h+1<creditmodel.size())
				{
					int j=h+1;
					Label la2=new Label(creditmodel.get(j).getCrcr_content());
					la2.setParent(newcell3);
					List<CreditContentInfoModel> conmodel=bll.getCreditContentInfoValid(creditmodel.get(j).getCrcr_id());
					if(!conmodel.isEmpty())
					{
						Combobox cmb=new Combobox();
						cmb.setReadonly(true);
						Comboitem cmbIt=new Comboitem();
						cmbIt.setValue(""+creditmodel.get(j).getCrcr_id());
						cmbIt.setParent(cmb);
						for(int m=0;m<conmodel.size();m++)
						{
							Comboitem cmbItem=new Comboitem();
							cmbItem.setParent(cmb);
							cmbItem.setValue(""+creditmodel.get(h).getCrcr_id());
							cmbItem.setLabel(conmodel.get(m).getCrst_name());
						}
						cmb.setParent(newcell4);
					}
					else	
					{
						Combobox cmb=new Combobox();
						cmb.setButtonVisible(false);
						Comboitem cmbItem=new Comboitem();
						cmbItem.setValue(""+creditmodel.get(h).getCrcr_id());
						cmbItem.setParent(cmb);
						cmb.setParent(newcell4);
					}
				}
				newcell1.setParent(newrow);
				newcell2.setParent(newrow);
				newcell3.setParent(newrow);
				newcell4.setParent(newrow);
				h=h+2;
			}
		}
	}
	
	//添加信誉评定信息
	public void addCriteInfo(int rowid)
	{
		CoLatencyClient_AddBll bll=new CoLatencyClient_AddBll();
		List<CreditInfoModel> models=bll.getCocrRelationInfo(rowid);
		int rids=0;
		if(models.isEmpty())
		{
			CreditInfoModel m=new CreditInfoModel();
			m.setCrIn_addname(username);
			rids=bll.addCreditInfo(m,rowid);
		}
		else
		{
			rids=models.get(0).getCrIn_id();
		}
		if(rids>0)
		{
			try{
				if(!credit.getChildren().isEmpty())
				{
					int rownum=credit.getChildren().size();
					for(int n=0;n<rownum;n++)
					{
						
						if(!gridcredit.getCell(n,1).getChildren().isEmpty())
						{
							Label lbel=(Label) gridcredit.getCell(n,0).getChildren().get(0);
							List<CreditCriterionModel> cbmodel=bll.getCreditInfoFromName(lbel.getValue());
							CreditAppraiseModel model=new CreditAppraiseModel();
							Combobox comb=(Combobox) gridcredit.getCell(n,1).getChildren().get(0);
							String con=comb.getValue();
							model.setCrap_addname(username);
							model.setCrap_content(con);
							model.setCrap_crcr_id(cbmodel.get(0).getCrcr_id());
							model.setCrap_crin_id(rids);
							bll.addCreditAppraise(model);
						}
						if(!gridcredit.getCell(n,3).getChildren().isEmpty())
						{
							Label lbel=(Label) gridcredit.getCell(n,2).getChildren().get(0);
							List<CreditCriterionModel> cbmodel=bll.getCreditInfoFromName(lbel.getValue());
							CreditAppraiseModel model=new CreditAppraiseModel();
							Combobox comb=(Combobox) gridcredit.getCell(n,3).getChildren().get(0);
							String con=comb.getValue();
							model.setCrap_addname(username);
							model.setCrap_content(con);
							model.setCrap_crcr_id(cbmodel.get(0).getCrcr_id());
							model.setCrap_crin_id(rids);
							bll.addCreditAppraise(model);
						}
					}
				}
			}catch(Exception e)
			{
				System.out.println("系统出错了，请联系开发人员"+e.getMessage());
			}
		}
	}
	
	//获取潜在客户联系人信息并判断输入的数据是否符合要求的方法
	private String colaLinkPeopExist()
	{
		ljlist=new ArrayList<CoAgencyLinkmanModel>();
		if(!ljlist.isEmpty())
		{
			ljlist.clear();
		}
		int rownum=linkinfo.getRows().getChildren().size();
		String strd="";
		if(rownum>0)
		{
			//获取联系人个数
			int y=rownum/5;
			
			for(int l=0;l<y;l++)
			{
			//获取每个联系人的第一行
			int h=l*5;
			CoAgencyLinkmanModel cmodel=new CoAgencyLinkmanModel();
			String linkname="";
			String linkjob="";
			String linktel="";
			String linktel2="";
			String linkmobile="";
			String linkfax="";
			String linkemail="";
			String linkemail2="";
			String remark="";
			String cali_duty="";
			try
			{
				int v=0;
				for(int i=h;i<h+5;i++)
				{
					Row ro=(Row)linkinfo.getRows().getChildren().get(i);
					//第一行：姓名、职务
					if(v==0)
					{
						Cell cel=(Cell)ro.getChildren().get(2);
						Cell cel2=(Cell)ro.getChildren().get(4);
						Textbox tb=(Textbox)cel.getChildren().get(0);
						Textbox tb2=(Textbox)cel2.getChildren().get(0);
						if(tb.getValue()!=""&&!tb.getValue().equals(""))
						{
							linkname=tb.getValue();
						}
						linkjob=tb2.getValue();	
					}
					//第二行：电话号码1、2
					if(v==1)
					{
						Cell cel=(Cell)ro.getChildren().get(1);
						Cell cel2=(Cell)ro.getChildren().get(3);
						Textbox tb=(Textbox)cel.getChildren().get(0);
						Textbox tb2=(Textbox)cel2.getChildren().get(0);
						if(linkname!=""||!linkname.equals(""))
						{
							if(tb.getValue()!=""&&!tb.getValue().equals(""))
							{
//								if(isTel(tb.getValue()))
//								{
									linktel=tb.getValue();
//								}
//								else
//								{
//									strd="电话号码格式有误";
//									tb.focus();
//									break;
//								}
							}
							if(tb2.getValue()!=""&&!tb2.getValue().equals(""))
							{
//								if(isTel(tb2.getValue()))
//								{
									linktel2=tb2.getValue();
//								}
//								else
//								{
//									strd="电话号码格式有误";
//									tb2.focus();
//									break;
//								}
							}
						}
					}
					//第三行手机号码、传真
					else if(v==2)
					{
						Cell cel=(Cell)ro.getChildren().get(1);
						Cell cel2=(Cell)ro.getChildren().get(3);
						Textbox tb=(Textbox)cel.getChildren().get(0);
						Textbox tb2=(Textbox)cel2.getChildren().get(0);
						
						if(tb.getValue()!=""&&!tb.getValue().equals(""))
						{
//							if(isNum(tb.getValue()))
//							{
								linkmobile=tb.getValue();
//							}
//							else
//							{
//								strd="手机号码只能输入数字";
//								tb.focus();
//								break;
//							}
						}
						linkfax=tb2.getValue();
					 }
					//第四行电子邮箱1、2
					else if(v==3)
					{
						Cell cel=(Cell)ro.getChildren().get(1);
						Cell cel2=(Cell)ro.getChildren().get(3);
						Textbox tb=(Textbox)cel.getChildren().get(0);
						Textbox tb2=(Textbox)cel2.getChildren().get(0);
						
						if(tb.getValue()!=""&&!tb.getValue().equals(""))
						{
//							if(isEmail(tb.getValue()))
//							{
								linkemail=tb.getValue();
//							}
//							else
//							{
//								strd="输入的邮箱格式不对";
//								tb.focus();
//								break;
//							}
						}

						if(tb2.getValue()!=""&&!tb2.getValue().equals(""))
						{
//							if(isEmail(tb2.getValue()))
//							{
								linkemail2=tb2.getValue();
//							}
//							else
//							{
//								strd="输入的邮箱格式不对";
//								tb2.focus();
//								break;
//							}
						}
					}
					//第五行职责，备注
					else if(v==4)
					{
						//职责
						Cell celz=(Cell)ro.getChildren().get(1);
						Textbox tbz=(Textbox)celz.getChildren().get(0);
						cali_duty=tbz.getValue();
						
						//备注
						Cell cel=(Cell)ro.getChildren().get(3);
						Textbox tb=(Textbox)cel.getChildren().get(0);
						remark=tb.getValue();
						
					}
					 v=v+1;
				}
				if(strd!="")
				{
					break;
				}
				else if(linkname!=""||!linkname.equals(""))
				{
					cmodel.setCali_name(linkname);
					cmodel.setCali_job(linkjob);
					cmodel.setCali_tel(linktel);
					cmodel.setCali_mobile(linkmobile);
					cmodel.setCali_fax(linkfax);
					cmodel.setCali_email(linkemail);
					cmodel.setCali_addname(username);
					cmodel.setCali_tel1(linktel2);
					cmodel.setCali_email1(linkemail2);
					cmodel.setCali_remark(remark);
					cmodel.setCali_duty(cali_duty);
					ljlist.add(cmodel);
				}
				}
				catch(Exception e)
				{
					System.out.println("错误"+e.getMessage());
				}
			}
		}
		return strd;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}
	
//	//判断字符串是否都是数字
//	public static boolean isNum(String str){
//		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
//	}
//	
//	//判断输入的电话号码是否有错误
//	public static boolean isTel(String str){
//		boolean flag=false;
//		flag=isNum(str);
//		if(flag==false)
//		{
//			int k= str.length();
//			for(int i=0;i<k;i++)
//			{
//				if(str.substring(i, i+1)!=null)
//				{
//					if(!str.substring(i, i+1).equals("-")&&!isNum(str.substring(i, i+1)))
//					{
//						flag=false;
//						break;
//					}
//					else
//					{
//						flag=true;
//					}
//				}
//			}
//		}
//		return flag;
//	}
//	
//	//判断输入的Email格式是否正确
//	public static boolean isEmail(String str){
//		boolean tag = true;  
//		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
//		final Pattern pattern = Pattern.compile(pattern1);
//		final Matcher mat = pattern.matcher(str);
//		if (!mat.find()) {  
//            tag = false;  
//        }
//		return tag;
//	}
	
}
