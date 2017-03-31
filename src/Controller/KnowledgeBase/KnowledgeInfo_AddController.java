package Controller.KnowledgeBase;

import impl.UserInfoImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.zkforge.ckez.CKeditor;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

import service.UserInfoService;

import Model.KnowledgeBase_ContentModel;
import Util.FileOperate;
import bll.KnowledgeBase.KnowledgeBase_Bll;

public class KnowledgeInfo_AddController extends SelectorComposer<Component> implements AfterCompose{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Combobox classname;
	@Wire
	private Vlayout filelist;
	@Wire
	private Button attachBtn;
	@Wire
	private Textbox questions;
	@Wire
	private CKeditor answer;
	private String filename="";
	private InputStream inputStream=null;
	private String strtype="";
	private Media media;
	KnowledgeBase_Bll bll=new KnowledgeBase_Bll();
	//获取问题的子类的级类型信息
	List<KnowledgeBase_ContentModel> kwdechlist=bll.getKnowledgeBaseConClassInfo();
	
	@Override
	public void afterCompose() {
		// TODO Auto-generated method stub
		
	}
	
	//初始化组件
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		answer.setValue("答：");
		if(!kwdechlist.isEmpty())
		{
			for(int i=0;i<kwdechlist.size();i++)
			{
				Comboitem item=new Comboitem();
				item.setParent(classname);
				item.setLabel(kwdechlist.get(i).getKbco_classname());
			}
		}
	}
	
	//获取上传的政策文件
	@Listen("onUpload= #attachBtn")
	public void upload2( @ContextParam(ContextType.TRIGGER_EVENT) UploadEvent ev){	
		//Fileupload.get();
		media=ev.getMedia();
		if(media!=null)
		{
			try {
			this.filename=media.getName();
			strtype=media.getFormat();
			if(strtype!="txt")
			{
				this.inputStream=media.getStreamData();
				final Hlayout hl = new Hlayout();
				hl.appendChild(new Label(media.getName()));
				A rm = new A("取消");
				rm.addEventListener(Events.ON_CLICK,new org.zkoss.zk.ui.event.EventListener(){
					public void onEvent(Event event) throws Exception {
						attachBtn.setVisible(true);
						inputStream=null;
						media=null;
						hl.detach();
		            	}
					});
				hl.appendChild(rm);
				filelist.appendChild(hl);
				attachBtn.setVisible(false);
			}
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	//添加知识信息事件
	@Listen("onClick= #summit")
	public void addKnowledgeInfo(){
		KnowledgeBase_Bll bll=new KnowledgeBase_Bll();
		KnowledgeBase_ContentModel model=new KnowledgeBase_ContentModel();
		String str=isEmploy(model);
		if(str=="")
		{
			String realPath="KnowledgeBase/file/";
			String name = FileOperate.getAbsolutePath() +realPath+ filename;
			if(filename!=""&&filename!=null&&!filename.equals(""))
			{
				model.setKbco_fileurl(name);
				model.setKbco_remark(filename);
			}
			else
			{
				model.setKbco_fileurl("");
				model.setKbco_remark("");
			}
			//添加知识库内容
			int k=bll.addKnowleBaseInfo(model);
			if(k>0)
			{
				if(media!=null)
				{
					FileOperate.upload(media, realPath+filename);
				}
				Messagebox.show("添加成功","提示",Messagebox.OK, Messagebox.INFORMATION);
				Executions.sendRedirect("KnowledgeInfo_Add.zul");
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
	
	//判断填入的数据是否为空
	public String isEmploy(KnowledgeBase_ContentModel model)
	{
		String str="";
		if(classname.getValue()==""||classname.getValue().equals(""))
		{
			str="问题类型不能为空";
			classname.focus();
		}
		else if(questions.getValue()==""||questions.getValue().equals(""))
		{
			str="问题不能为空";
			questions.focus();
		}
		else if(answer.getValue()=="答："||answer.getValue().equals("答："))
		{
			str="问题答案不能为空";
			questions.focus();
		}
		else
		{
			Session session =  Executions.getCurrent().getDesktop().getSession();
			UserInfoService uservice=new UserInfoImpl(session);
			String username=uservice.getUsername();
			model.setKbco_classname(classname.getValue());
			model.setKbco_title(questions.getValue());
			model.setKbco_content(answer.getValue());
			model.setKbco_addname(username);
		}
		return str;
	}
		
}
