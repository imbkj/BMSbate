package Controller.CoCompact.CoCompactSA;

import impl.UserInfoImpl;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;
import Model.CoCompactSAModel;
import bll.CoCompact.CoCompactSA.CoCompactSA_SelectBll;
import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;

public class CompactSA_InFileController  extends SelectorComposer<Component>{
	private CoCompactSA_OperateBll ccsaBll = new CoCompactSA_OperateBll();
	
	CoCompactSA_SelectBll ccsaB  = new CoCompactSA_SelectBll();
	
	//获取用户名
	Session session =  Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();
	
	//int ccsa_id =((CoCompactSAModel)Executions.getCurrent().getArg().get("ccsaM")).getCcsa_id();
	int ccsa_id;
	int ccsa_tapr_id;
	private CoCompactSAModel ccsaList;
	
	
	@Wire
	private Datebox ccsa_filedate;	//合同归档日期
	@Wire
	private Textbox ccsa_fileid;	//合同存档编号
	@Wire
	private Textbox ccsa_remark;	//备注
	@Wire
	private Window w1;
	@Wire
	private Intbox ccsa_chs_copies;	//中文份数
	@Wire
	private Intbox ccsa_en_copies;		//英文份数
	
	
	public CompactSA_InFileController()
	{
	ccsa_tapr_id=Integer.parseInt(Executions.getCurrent().getArg().get("id").toString());
	ccsa_id=Integer.parseInt(Executions.getCurrent().getArg().get("daid").toString());
	ccsaList = ccsaB.searchCCSABase(ccsa_id).get(0);
	}
	
	
	//公司合同签回方法
	@Listen("onClick=#btSubmit")
	public void inFileCompact() throws Exception{
		
		String filedate="";	//合同归档日期
		String chsCopies="0"; //中文份数
		String enCopies="0"; //英文份数
		
		//日期判断
		if(ccsa_filedate.getValue() != null){
			filedate = ccsaBll.DatetoSting(ccsa_filedate.getValue());
		}
		
		//整数判断
		if(ccsa_chs_copies.getValue()!=null){
			chsCopies=String.valueOf(ccsa_chs_copies.getValue());
		}
		if(ccsa_en_copies.getValue()!=null){
			enCopies=String.valueOf(ccsa_en_copies.getValue());
		}

		//判断必填项是否为空
		if(!"".equals(filedate) && !chsCopies.equals("0")){
			
			//调用合同签回方法
			String[] message =ccsaBll.returnCoCompactSA(ccsa_tapr_id,ccsa_id, filedate, ccsa_fileid.getValue(),chsCopies,enCopies, ccsa_remark.getValue(),username,ccsaList.getCid());

			//弹出提示并跳转页面
			if (message[0].equals("1")) {
	            EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
	                public void onEvent(ClickEvent event) throws Exception {
	                    if (Messagebox.Button.OK.equals(event.getButton())) {
	                       // Executions.sendRedirect("Compact_InFileList.zul");	//跳转页面
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
			if("".equals(filedate)){
				Messagebox.show("请选择“归档日期”", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
			else if (chsCopies.equals("0")) {
				Messagebox.show("请输入“中文补充协议份数”", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}


	public CoCompactSAModel getCcsaList() {
		return ccsaList;
	}


	public void setCocoList(CoCompactSAModel ccsaList) {
		this.ccsaList = ccsaList;
	}
}
