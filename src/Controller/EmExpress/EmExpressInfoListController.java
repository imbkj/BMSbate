package Controller.EmExpress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmExpress.EmExpressInfoSelectBll;

import Model.EmExpressInfoModel;

public class EmExpressInfoListController {
	private String flag=Executions.getCurrent().getParameter("flag").toString();
	private EmExpressInfoSelectBll bll=new EmExpressInfoSelectBll();
	private List<EmExpressInfoModel> list=new ArrayList<EmExpressInfoModel>();
	private String cobananme,embaname,content,recevename,waynumber,rand,types,waycompany,exstate,ifnum;
	private String str=" and expr_state!=3",strorder=" order by expr_rank,expr_state",addname="";
	
	public EmExpressInfoListController()
	{
		if(flag.equals("1"))
		{
			list=bll.getEmExpressInfoList(str," order by expr_rank,expr_state");
		}
		else
		{
			list=bll.getEmExpressInfoList(str," order by expr_rank,expr_state");
		}
	}
	
	//修改
	@Command
	@NotifyChange("list")
	public void update(@BindingParam("model") EmExpressInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window) Executions.createComponents("EmExpressInfoUpdate.zul", null, map);
		window.doModal();
		list=bll.getEmExpressInfoList(str," order by expr_rank,expr_state");
	}
	
	//查询
	@Command
	@NotifyChange("list")
	public void search(@BindingParam("statebox") Combobox statebox)
	{
		str="";
		if(cobananme!=null&&!cobananme.equals(""))
		{
			str=str+" and coba_shortname like '%"+cobananme+"%'";
		}
		if(embaname!=null&&!embaname.equals(""))
		{
			str=str+" and emba_name like '%"+embaname+"%'";
		}
		if(content!=null&&!content.equals(""))
		{
			str=str+" and expr_content like '%"+content+"%'";
		}
		if(recevename!=null&&!recevename.equals(""))
		{
			str=str+" and exct_receivename like '%"+recevename+"%'";
		}
		if(waynumber!=null&&!waynumber.equals(""))
		{
			str=str+" and expr_waynumber = '"+waynumber+"'";
		}
		if(rand!=null&&!rand.equals(""))
		{
			str=str+" and expr_rank = '"+rand+"'";
		}
		if(types!=null&&!types.equals(""))
		{
			str=str+" and expr_class = '"+types+"'";
		}
		if(waycompany!=null&&!waycompany.equals(""))
		{
			str=str+" and expr_company = '"+waycompany+"'";
		}
		if(exstate!=null&&!exstate.equals(""))
		{
			str=str+" and expr_state = '"+statebox.getSelectedItem().getValue()+"'";
		}
		if(ifnum!=null&&!ifnum.equals(""))
		{
			if(ifnum.equals("有"))
			{
				str=str+" and num is not null";
				strorder=" order by expr_rank,exct_receivename";
			}
			else
			{
				str=str+" and num is null";
			}
		}
		if(addname!=null&&!addname.equals(""))
		{
			str=str+" and expr_addname='"+addname+"'";
		}
		list=bll.getEmExpressInfoList(str,strorder);
	}
	
	//全选
	@Command
	public void checkall(@BindingParam("gd") Grid gd,@BindingParam("ck") Checkbox ck)
	{
		for(int i=0;i<gd.getRows().getChildren().size();i++)
		{
			Cell cell=(Cell) gd.getCell(i, 15);
			if(cell.getChildren().size()>0)
			{
				Checkbox cks=(Checkbox)cell.getChildren().get(0);
				cks.setChecked(ck.isChecked());
			}
		}
	}
	
	//合并发件
	@Command
	@NotifyChange("list")
	public void bingsend(@BindingParam("gd") Grid gd)
	{
		List<EmExpressInfoModel> lt=new ArrayList<EmExpressInfoModel>();
		String idstr="";
		for(int i=0;i<gd.getRows().getChildren().size();i++)
		{
			Cell cell=(Cell) gd.getCell(i, 15);
			if(cell.getChildren().size()>0)
			{
				Checkbox cks=(Checkbox)cell.getChildren().get(0);
				if(cks.isChecked())
				{
					EmExpressInfoModel m=new EmExpressInfoModel();
					m=cks.getValue();
					lt.add(m);
					if(i==0)
					{
						idstr=m.getExpr_id()+"";
					}
					else
					{
						idstr=idstr+","+m.getExpr_id();
					}
				}
			}
		}
		int k=bll.ifstatesame(idstr);
		if(k>1)
		{
			Messagebox.show("选择的数据收件地址不同，不能一起处理", "提示", Messagebox.OK, Messagebox.ERROR);
		}
		else
		{
			Map map=new HashMap<>();
			map.put("lt", lt);
			Window window=(Window)Executions.createComponents("ExpressInfoUpdate.zul", null, map);
			window.doModal();
			list=bll.getEmExpressInfoList(str,strorder);
		}
	}
	
	//前道编辑
	@Command
	@NotifyChange("list")
	public void edit(@BindingParam("model") EmExpressInfoModel model)
	{
		if(model.getExpr_tarpid()!=null&&!model.getExpr_tarpid().equals(""))
		{
			Map map=new HashMap<>();
			map.put("daid", model.getExpr_id());
			map.put("id", model.getExpr_tarpid());
			Window window=(Window) Executions.createComponents("EmExpressGetExpress.zul", null, map);
			window.doModal();
			list=bll.getEmExpressInfoList(str," order by expr_rank,expr_state");
		}
		else
		{
			Messagebox.show("没有任务单信息", "提示", Messagebox.OK, Messagebox.ERROR);
		}
	}
	
	
	//前道编辑
	@Command
	public void zui(@BindingParam("model") EmExpressInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window) Executions.createComponents("EmExpressSearch.zul", null, map);
		window.doModal();		
	}
	
	//弹出打印页面
	@Command
	public void print(@BindingParam("model") EmExpressInfoModel model)
	{
		Map map=new HashMap<>();
		map.put("model", model);
		Window window=(Window)Executions.createComponents("EmExpressPrint.zul", null, map);
		window.doModal();
		
	}
	
	public List<EmExpressInfoModel> getList() {
		return list;
	}
	public void setList(List<EmExpressInfoModel> list) {
		this.list = list;
	}

	public String getCobananme() {
		return cobananme;
	}

	public void setCobananme(String cobananme) {
		this.cobananme = cobananme;
	}

	public String getEmbaname() {
		return embaname;
	}

	public void setEmbaname(String embaname) {
		this.embaname = embaname;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecevename() {
		return recevename;
	}

	public void setRecevename(String recevename) {
		this.recevename = recevename;
	}

	public String getWaynumber() {
		return waynumber;
	}

	public void setWaynumber(String waynumber) {
		this.waynumber = waynumber;
	}

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getWaycompany() {
		return waycompany;
	}

	public void setWaycompany(String waycompany) {
		this.waycompany = waycompany;
	}

	public String getExstate() {
		return exstate;
	}

	public void setExstate(String exstate) {
		this.exstate = exstate;
	}

	public String getIfnum() {
		return ifnum;
	}

	public void setIfnum(String ifnum) {
		this.ifnum = ifnum;
	}

	public String getAddname() {
		return addname;
	}

	public void setAddname(String addname) {
		this.addname = addname;
	}
	
	

}
