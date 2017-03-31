package Controller.EmCommissionOut.Standard;

import java.util.List;
import java.util.Set;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.EmCommissionOut.Standard.Standard_OperateBll;
//import bll.EmCommissionOut.Standard.Standard_ListBll;
import bll.EmCommissionOut.Standard.WtServiceStandardBll;
import Model.WtServiceStandardrelationModel;

import Model.CoBaseModel;
import Util.UserInfo;

public class WtstandardController {
	
	private CoBaseModel com = new CoBaseModel();
	private WtServiceStandardrelationModel m =new WtServiceStandardrelationModel();
	
	
	
	// 社保、公积金
	private List<String> htxztypeList = new ListModelList<>();
	private List<String> ldhtqdtypeList = new ListModelList<>();
	private List<String> ldhtbbtypeList = new ListModelList<>();
	private List<String> ygtypeList = new ListModelList<>();
	private List<String> dafeetypeList = new ListModelList<>();
	private List<String> bcgjjtypeList = new ListModelList<>();
	private List<String> sybxfwtypeList = new ListModelList<>();
	private List<String> bcfltypeList = new ListModelList<>();
	private List<String> dfgztypeList = new ListModelList<>();
	private List<String> dbgstyptList = new ListModelList<>();
	private List<String> sbzhtypeList = new ListModelList<>();
	private List<String> sbfeetypeList = new ListModelList<>();
	private List<String> gjjzhtypeList = new ListModelList<>();
	private List<String> gjjfeetypeList = new ListModelList<>();
	
	Integer cid = 0;
	
	WtServiceStandardBll bll = new WtServiceStandardBll();
	
	public WtstandardController()
	{
		
		cid = Integer.parseInt(Executions.getCurrent().getArg().get("cid")
				.toString());
		setCom(bll.getCobaInfo(cid));
		
		
		
		//合同性质
		htxztypeList.add("");
		htxztypeList.add("代理");
		htxztypeList.add("派遣");
		
		ldhtqdtypeList.add("");
		ldhtqdtypeList.add("客户自签");
		ldhtqdtypeList.add("委托方");
		ldhtqdtypeList.add("受托方");
		
		ldhtbbtypeList.add("");
		ldhtbbtypeList.add("客户公司");
		ldhtbbtypeList.add("委托方");
		ldhtbbtypeList.add("受托方");
		
		ygtypeList.add("");
		ygtypeList.add("客户自办");
		ygtypeList.add("受托方办");
		
		dafeetypeList.add("");
		dafeetypeList.add("不保管");
		dafeetypeList.add("保管并全员支付费用");
		dafeetypeList.add("保管但按实际 发生支付");
		
		sbzhtypeList.add("");
		sbzhtypeList.add("不做");
		sbzhtypeList.add("受托方账户");
		sbzhtypeList.add("独立账户");

		sbfeetypeList.add("");
		sbfeetypeList.add("委托对账");
		sbfeetypeList.add("单独支付");

		gjjzhtypeList.add("");
		gjjzhtypeList.add("不做");
		gjjzhtypeList.add("受托方账户");
		gjjzhtypeList.add("独立账户");

		gjjfeetypeList.add("");
		gjjfeetypeList.add("委托对账");
		gjjfeetypeList.add("单独支付");
		
		bcgjjtypeList.add("");
		bcgjjtypeList.add("不做");
		bcgjjtypeList.add("受托方账户");
		bcgjjtypeList.add("独立账户");
		
		sybxfwtypeList.add("");
		sybxfwtypeList.add("不做");
		sybxfwtypeList.add("受托方");
		sybxfwtypeList.add("委托方");
		
		bcfltypeList.add("");
		bcfltypeList.add("不做");
		bcfltypeList.add("受托方");
		bcfltypeList.add("委托方");
		
		dfgztypeList.add("");
		dfgztypeList.add("不做");
		dfgztypeList.add("受托方");
		dfgztypeList.add("委托方");
		

		dbgstyptList.add("");
		dbgstyptList.add("不做");
		dbgstyptList.add("受托方");
		dbgstyptList.add("委托方");
		
		
		
	}
	
	@Command("submit")
	public void submit(@BindingParam("win") Window win,
			@BindingParam("set") Set<Listitem> set) {
		if (m.getWtss_title() == null || m.getWtss_title().isEmpty()) {
			Messagebox.show("请输入名称!", "ERROR", Messagebox.OK, Messagebox.ERROR);
		} else {

			try {
				//handlefield();
//				String[] str = new Standard_OperateBll().add(m,
//						com.getCoba_shortname(), set);
				m.setCid(cid);
				m.setWtss_state(0);
				m.setWtss_addname(UserInfo.getUsername());
				
				//判断是否有名字相同的标准
				
			if(	(bll.getmodellist("and a.wtss_title='"+m.getWtss_title()+"' and a.cid="+cid )).size()>0)
			{
				Messagebox.show("已存在此标准名，请更名。", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
			else
			{
				String strmsg=checkstr(m);
				if( strmsg.length()>0)
				{
					Messagebox.show(strmsg, "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
				}
				else
				{
				Integer msgint=bll.WtServiceStandardadd(m);
				
				
				

				if (msgint>0) {
					Messagebox.show("添加成功", "INFORMATION", Messagebox.OK,
							Messagebox.INFORMATION);
					win.detach();
				} else {
					Messagebox.show("添加失败", "ERROR", Messagebox.OK,
							Messagebox.ERROR);
				}
				}
			}
			} catch (Exception e) {
				e.printStackTrace();
				Messagebox.show("提交出错!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			}
			
		}
	}
	
	private String checkstr(WtServiceStandardrelationModel m)
	{
		StringBuilder str= new StringBuilder();
	
		if (m.getWtss_title() == null || m.getWtss_title().isEmpty())
		{
			str.append("标准名称忘填了!");
		}
		
		
		if (m.getWtss_compacttype() == null || m.getWtss_compacttype().isEmpty())
		{
			str.append("合同性质忘填了！");
		}
		if (m.getWtss_laborcontract() == null || m.getWtss_laborcontract().isEmpty())
		{
			str.append("劳动合同签订方忘填了！");
			
		}
		else
		{
			if(m.getWtss_laborcontract().equals("受托方"))
			{
				if (m.getWtss_laborcontractbb()== null || m.getWtss_laborcontractbb().isEmpty())
				{
					str.append("劳动合同版本忘填了！");
				}
			}
		}
		if (m.getWtss_shebaoco() == null || m.getWtss_shebaoco().isEmpty())
		{
			str.append("社保缴纳方忘填了！");
		}
		if (m.getWtss_gjjco() == null || m.getWtss_gjjco().isEmpty())
		{
			str.append("公积金缴纳方忘填了！");
		}
		if (m.getWtss_vcgjjco() == null || m.getWtss_vcgjjco().isEmpty())
		{
			str.append("补充公积金缴纳方忘填了！");
		}
		if (m.getWtss_archives() == null || m.getWtss_archives().isEmpty())
		{
			str.append("档案忘填了！");
		}
		if (m.getWtss_shebaopayty() == null || m.getWtss_shebaopayty().isEmpty())
		{
			str.append("社保支付方式忘填了！");
		}
		if (m.getWtss_gjjpayty()  == null || m.getWtss_gjjpayty().isEmpty())
		{
			str.append("公积金支付方式忘填了！");
		}
			
		return str.toString();
	}
	
	

	public CoBaseModel getCom() {
		return com;
	}

	public void setCom(CoBaseModel com) {
		this.com = com;
	}

	public WtServiceStandardrelationModel getM() {
		return m;
	}

	public void setM(WtServiceStandardrelationModel m) {
		this.m = m;
	}





	public List<String> getHtxztypeList() {
		return htxztypeList;
	}





	public void setHtxztypeList(List<String> htxztypeList) {
		this.htxztypeList = htxztypeList;
	}





	public List<String> getLdhtqdtypeList() {
		return ldhtqdtypeList;
	}





	public void setLdhtqdtypeList(List<String> ldhtqdtypeList) {
		this.ldhtqdtypeList = ldhtqdtypeList;
	}





	public List<String> getLdhtbbtypeList() {
		return ldhtbbtypeList;
	}





	public void setLdhtbbtypeList(List<String> ldhtbbtypeList) {
		this.ldhtbbtypeList = ldhtbbtypeList;
	}





	public List<String> getYgtypeList() {
		return ygtypeList;
	}





	public void setYgtypeList(List<String> ygtypeList) {
		this.ygtypeList = ygtypeList;
	}





	public List<String> getDafeetypeList() {
		return dafeetypeList;
	}





	public void setDafeetypeList(List<String> dafeetypeList) {
		this.dafeetypeList = dafeetypeList;
	}





	public List<String> getBcgjjtypeList() {
		return bcgjjtypeList;
	}





	public void setBcgjjtypeList(List<String> bcgjjtypeList) {
		this.bcgjjtypeList = bcgjjtypeList;
	}





	public List<String> getSybxfwtypeList() {
		return sybxfwtypeList;
	}





	public void setSybxfwtypeList(List<String> sybxfwtypeList) {
		this.sybxfwtypeList = sybxfwtypeList;
	}





	public List<String> getBcfltypeList() {
		return bcfltypeList;
	}





	public void setBcfltypeList(List<String> bcfltypeList) {
		this.bcfltypeList = bcfltypeList;
	}





	public List<String> getDfgztypeList() {
		return dfgztypeList;
	}





	public void setDfgztypeList(List<String> dfgztypeList) {
		this.dfgztypeList = dfgztypeList;
	}





	public List<String> getDbgstyptList() {
		return dbgstyptList;
	}





	public void setDbgstyptList(List<String> dbgstyptList) {
		this.dbgstyptList = dbgstyptList;
	}





	public List<String> getSbzhtypeList() {
		return sbzhtypeList;
	}





	public void setSbzhtypeList(List<String> sbzhtypeList) {
		this.sbzhtypeList = sbzhtypeList;
	}





	public List<String> getSbfeetypeList() {
		return sbfeetypeList;
	}





	public void setSbfeetypeList(List<String> sbfeetypeList) {
		this.sbfeetypeList = sbfeetypeList;
	}





	public List<String> getGjjzhtypeList() {
		return gjjzhtypeList;
	}





	public void setGjjzhtypeList(List<String> gjjzhtypeList) {
		this.gjjzhtypeList = gjjzhtypeList;
	}





	public List<String> getGjjfeetypeList() {
		return gjjfeetypeList;
	}





	public void setGjjfeetypeList(List<String> gjjfeetypeList) {
		this.gjjfeetypeList = gjjfeetypeList;
	}
	
	
	
}
