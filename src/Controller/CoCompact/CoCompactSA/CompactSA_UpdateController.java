package Controller.CoCompact.CoCompactSA;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bll.CoCompact.CoCompactSA.CoCompactSA_OperateBll;

import Model.CoCompactSAModel;

public class CompactSA_UpdateController extends SelectorComposer<Component>{
	
	@Wire
	private Datebox inuredate;
	@Wire
	private Textbox remark;
	@Wire
	private Window win;
	
	CoCompactSAModel frommodel = (CoCompactSAModel)Executions.getCurrent().getArg().get("ccsaM");
	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Date ccsa_inuredate=dateToString(frommodel.getCcsa_inuredate());
		if(ccsa_inuredate!=null)
		{
			inuredate.setValue(ccsa_inuredate);
		}
	
	}
	
	//修改合同信息
	@Listen("onClick = #updatebtn")
	public void updateCoCompactSA(){
		if(inuredate.getValue()!=null)
		{
			CoCompactSA_OperateBll bll=new CoCompactSA_OperateBll();
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
			boolean flag=bll.UpCoCompactSA(formatDate.format(inuredate.getValue()),remark.getValue(),
					frommodel.getCcsa_id());
			if(flag==true)
			{
				Messagebox.show("修改成功","提示",Messagebox.OK, Messagebox.INFORMATION);
				win.detach();
			}
			else
			{
				Messagebox.show("修改失败","提示",Messagebox.OK, Messagebox.INFORMATION);
			}
		}
		else
		{
			Messagebox.show("生效时间不能为空","提示",Messagebox.OK, Messagebox.INFORMATION);
		}
	}
	
	/**
     * String转Date
     * 
     * @param count
     * @return
     */
    private Date dateToString(String date) {
    	Date time=null;
    	if(date!=null&&date!=""&&!date.equals(""))
    	{
    		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
    		try {
    			time = formatDate.parse(date);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}	
    	}
    	return time;
    }
}
