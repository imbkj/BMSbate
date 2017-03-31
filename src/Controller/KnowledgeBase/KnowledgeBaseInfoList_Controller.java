package Controller.KnowledgeBase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

import Model.KnowledgeBase_ContentModel;
import bll.KnowledgeBase.KnowledgeBase_Bll;

public class KnowledgeBaseInfoList_Controller {
	private String question="";
	private String upquestion="";
	private String nametitle="";
	private String str1="";
	private String str2="";
	private String str="";
	
	KnowledgeBase_Bll bll=new KnowledgeBase_Bll();
	
	//获取问题内容信息
	List<KnowledgeBase_ContentModel> kwdgeinfolist=bll.getKnowledgeBaseConInfo("");
	//获取问题的子类的级类型信息
	List<KnowledgeBase_ContentModel> kwdechlist=bll.getKnowledgeBaseConClassInfo();
	//获取字典库的内容添加人信息
	public List<KnowledgeBase_ContentModel> addnamelist=bll.getKnowleBaseAddnameInfo();
	KnowledgeBase_ContentModel kwdebasecontent;
	
	//查询问题信息
	@Command
	@NotifyChange("kwdgeinfolist")
	public void search(@BindingParam("uptime") Date uptime,
			@BindingParam("nametime") Date nametime)
	{
		str1="";
		str="";
		
		if(uptime!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateuptime = formatter.format(uptime);
			if(dateuptime!=""&&!dateuptime.equals("")&&dateuptime!=null)
			{
				str1=str1+" and convert(varchar(10),kbco_updatetime,120) = convert(varchar(10),'"+dateuptime+"',120) ";
			}
		}
		
		if(nametime!=null)
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateString = formatter.format(nametime);
			if(dateString!=""&&!dateString.equals("")&&dateString!=null)
			{
				str1=str1+" and convert(varchar(10),kbco_addtime,120) = convert(varchar(10),'"+dateString+"',120) ";
			}
		}
		str=str1+str2;
		kwdgeinfolist=bll.getKnowledgeBaseConInfo(str);
	}
	
	//快速查询问题信息
	@Command
	@NotifyChange("kwdgeinfolist")
	public void fastsearch()
	{
		str2="";
		str="";
		if(nametitle!=""&&!nametitle.equals("")&&nametitle!=null)
		{
			str2=str2+" and kbco_classname='"+nametitle+"'";
		}
		
		if(question!=""&&!question.equals(""))
		{
			str2=str2+" and kbco_title like '%"+question+"%'";
		}
		
		if(upquestion!=""&&!upquestion.equals(""))
		{
			str2=str2+" and kbco_addname like '%"+upquestion+"%'";
		}
		str=str1+str2;
		kwdgeinfolist=bll.getKnowledgeBaseConInfo(str);
	}
	
	//打开政策指引文件
	@Command
	@NotifyChange("kwdgeinfolist")
	public void openfile(@BindingParam("url") String fileurl)
	{
		try {
			String[] cmd = new String[5];
			//String url = "D:\\workspace\\BMSbeta\\WebContent\\KnowledgeBase\\file\\tst.xls";
			cmd[0] = "cmd";
			cmd[1] = "/c";
			cmd[2] = "start";
			cmd[3] = " ";
			cmd[4] =fileurl;
			Runtime.getRuntime().exec(cmd);
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	
	//弹出更新问题信息窗口
	@Command
	public void updatekwdeinfo(@BindingParam("kwdemodel") KnowledgeBase_ContentModel kwdemodel)
	{
		Map map=new HashMap();
		map.put("model", kwdemodel);
		Window window = (Window)Executions.createComponents("KnowledgeInfo_Update.zul",null, map);
		window.doModal();
		fastsearch();
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getUpquestion() {
		return upquestion;
	}

	public void setUpquestion(String upquestion) {
		this.upquestion = upquestion;
	}

	public String getNametitle() {
		return nametitle;
	}
	public void setNametitle(String nametitle) {
		this.nametitle = nametitle;
	}
	public List<KnowledgeBase_ContentModel> getAddnamelist() {
		return addnamelist;
	}

	public List<KnowledgeBase_ContentModel> getKwdgeinfolist() {
		return kwdgeinfolist;
	}
	
	
	public KnowledgeBase_ContentModel getKwdebasecontent() {
		return kwdebasecontent;
	}

	public void setKwdebasecontent(KnowledgeBase_ContentModel kwdebasecontent) {
		this.kwdebasecontent = kwdebasecontent;
	}

	public List<KnowledgeBase_ContentModel> getKwdechlist() {
		return kwdechlist;
	}
	
}
