package Controller.SysMessage;

import java.util.List;
import java.util.Map;

import impl.MessageImpl;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import service.MessageService;

import bll.SysMessage.Message_SelectBll;

import Model.SysMessageModel;
import Util.UserInfo;

public class Message_ReplyAddController {
	private String type = (String)Executions.getCurrent().getArg().get("type");
	private Integer id = (Integer)Executions.getCurrent().getArg().get("id");
	private String tablename = (String)Executions.getCurrent().getArg().get("tablename");
	private SysMessageModel model = (SysMessageModel)Executions.getCurrent().getArg().get("model");
	private Message_SelectBll bll=new Message_SelectBll();
	private List<SysMessageModel> temList =bll.gettemList(" and pmte_class='"+type+"'");
	private String content;
	private Map map=Executions.getCurrent().getArg();
	//回复
	@Command
	public void reply(@BindingParam("win") Window win)
	{
		if(content!=null&&!content.equals(""))
		{
			MessageService msgservice=new MessageImpl(tablename,id);
			SysMessageModel ml=new SysMessageModel();
			ml.setSyme_content(content);
			ml.setSyme_url("");
			ml.setCid(0);
			ml.setSyme_reply_id(model.getSyme_reply_id());
			ml.setSyme_log_id(Integer.parseInt(UserInfo.getUserid()));
			ml.setSmwr_type(type);
			ml.setGid(0);
			ml.setSymr_name(model.getSyme_addname());
			ml.setSymr_log_id(model.getSyme_log_id());
			ml.setSyme_id(model.getSyme_id());
			String[] str=msgservice.Reply(ml);
			if(str[0]=="1")
			{
				map.put("flag", "1");
				win.detach();
			}
			else
			{
				Messagebox.show("恢复失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	
	//模板选择
	@Command
	@NotifyChange("content")
	public void temselect(@BindingParam("temcb") Combobox temcb)
	{
		if(temcb.getValue()!=null||!temcb.getValue().equals(""))
		{
			content="";
			SysMessageModel m=temcb.getSelectedItem().getValue();
			content=m.getPmte_content();
		}
	}
	public List<SysMessageModel> getTemList() {
		return temList;
	}
	public void setTemList(List<SysMessageModel> temList) {
		this.temList = temList;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SysMessageModel getModel() {
		return model;
	}
	public void setModel(SysMessageModel model) {
		this.model = model;
	}
	
}
