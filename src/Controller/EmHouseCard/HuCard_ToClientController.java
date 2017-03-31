package Controller.EmHouseCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

import Model.EmHouseTakeCardInfoModel;
import bll.EmHouseCard.EmHouse_TakeCardInfoOperateBll;
import bll.EmHouseCard.EmHouse_TakeCardInfoSelectBll;

public class HuCard_ToClientController {
	private String id = (String)Executions.getCurrent().getArg().get("daid");
	//String tperid = (String)Executions.getCurrent().getArg().get("id");
	private EmHouse_TakeCardInfoSelectBll bll=new EmHouse_TakeCardInfoSelectBll();
	private List<EmHouseTakeCardInfoModel> list=new ArrayList<EmHouseTakeCardInfoModel>();
	private EmHouseTakeCardInfoModel model= new EmHouseTakeCardInfoModel();
	private boolean takeinfovis=false;
	public HuCard_ToClientController()
	{
		list=bll.getEmhouseTakeCardInfo(" and re_id="+id);
		if(list.size()>0)
		{
			model=list.get(0);;
		}
	}
	
	@Command
	@NotifyChange("takeinfovis")
	public void infovisible(@BindingParam("cel") Cell cell,@BindingParam("takename") Cell takename,
			@BindingParam("takeinfo") Row takeinfo,@BindingParam("val") String val)
	{
		if(val!=null&&!val.equals("")&&val!="")
		{
			if(val.equals("2")||val=="2")
			{
				takeinfovis=true;
			}
			else
			{
				takeinfovis=false;
			}
		}
	}
	
	@Command
	@NotifyChange("takeinfovis")
	public void summit(@BindingParam("statename") String statename,@BindingParam("username") String username,
			@BindingParam("sendtime") Date sendtime,@BindingParam("sendtype") String sendtype,
			@BindingParam("win") Window win)
	{
		EmHouseTakeCardInfoModel m =new EmHouseTakeCardInfoModel();
		m.setRe_id(model.getRe_id());
		m.setRe_taprid(model.getRe_taprid());
		m.setCid(model.getCid());
		String sql="";
		EmHouse_TakeCardInfoOperateBll bll=new EmHouse_TakeCardInfoOperateBll();
		if(statename!=null&&!statename.equals("")&&statename!="")
		{
			//等于1表示选择了已交客服，则领卡状态修改为已交客服
			if(statename.equals("1")||statename=="1")
			{
				sql=",Re_State=25";
			}
			//等于2表示选择了员工已领，则领卡状态修改为员工已领并修改领卡信息
			else if(statename.equals("2")||statename=="2")
			{
				sql=",Re_State=14,re_name='"+username+"',pla_taketype='"+sendtype+"',re_time='"+datechange(sendtime)+"'";
			}
			String[] str=bll.TakeCardInfoEdit(m,statename,sql);
			if(str[0]=="1")
			{
				Messagebox.show("提交成功", "提示", Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("提交失败", "提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}
	public EmHouseTakeCardInfoModel getModel() {
		return model;
	}
	public void setModel(EmHouseTakeCardInfoModel model) {
		this.model = model;
	}

	public boolean isTakeinfovis() {
		return takeinfovis;
	}

	public void setTakeinfovis(boolean takeinfovis) {
		this.takeinfovis = takeinfovis;
	}
	private String datechange(Date d)
	{
		String date="";
		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); 
		if(d!=null&&!d.equals(""))
		{
			date=time.format(d);
		}
		return date;
	}
	
}
