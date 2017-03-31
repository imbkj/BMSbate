package Controller.CoServicePolicy;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

import service.ExcelService;

import Model.CoAgencyBaseCityRelModel;
import Model.CoAgencyBaseModel;
import Model.CoServicePolicyTitleModel;
import Model.CoServicePolicyTypeModel;
import Util.plyUtil;
import bll.CoServicePolicy.SePy_CityPolicySelectBll;

public class SePy_PolicyInfoController {
	private CoAgencyBaseModel model = (CoAgencyBaseModel)Executions.getCurrent().getArg().get("model");
	private SePy_CityPolicySelectBll bll=new SePy_CityPolicySelectBll();
	private List<CoServicePolicyTypeModel> list=bll.getCoServicePolicyTypeLists(model.getCabc_id(),"");
	private List<CoServicePolicyTypeModel> typelist=list;
	private List<CoServicePolicyTitleModel> titlelist=new ArrayList<CoServicePolicyTitleModel>();
	private String tiptxt;
	public SePy_PolicyInfoController()
	{
		addTitlelist();
	}
	
	//选择类型事件
	@Command
	@NotifyChange({"list","tiptxt","titlelist"})
	public void checktype(@BindingParam("lb") Listbox lb,@BindingParam("bd") Bandbox bd)
	{
		String selectedval="";
		List<CoServicePolicyTypeModel> newlist=new ArrayList<CoServicePolicyTypeModel>();
		if(lb.getSelectedCount()>0)
		{
			for(Listitem item:lb.getSelectedItems())
			{
				CoServicePolicyTypeModel m=item.getValue();
				newlist.add(m);
				if(selectedval==null||selectedval.equals(""))
				{
					selectedval=item.getLabel();
				}
				else
				{
					selectedval=selectedval+"、"+item.getLabel();
				}
			}
			bd.setValue(selectedval);
			tiptxt=selectedval;
			list=newlist;
		}
		else
		{
			list=bll.getCoServicePolicyTypeLists(model.getCabc_id(),"");
			bd.setValue("");
		}
		addTitlelist();
	}
	
	//导出数据
	@Command
	public void Export(HttpServletResponse response) throws IOException
	{
		plyUtil ply = new plyUtil();
		String path = "/../../CoServicePolicy/file/downloadfile/";// 导出保存路径
		// 创建当前日子
		Date date = new Date();
		// 格式化日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		// 格式化日期(产生文件名)
		String filename =sdf.format(date) +".xls";// 定义导出文件名称
		// 获取绝对路径
		path = ply.getAbsolutePath(path, filename, this);
		// System.out.println(path);
		// 创建文件
		File file = new File(path);
		file.createNewFile();
		String sheetName =filename;// Excel表格名
		//定义表头
		String[] title = { "类型", "标题", "内容"};
		CoAgencyBaseCityRelModel com=new CoAgencyBaseCityRelModel();
		try {
			//使用自己写的Excel导出实现类把数据写入Excel
			ExcelService exl = new ExcelImpl(file, sheetName,
					title, list,com);
			exl.writeExcel();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Filedownload.save(file, "xls");//导出Excel
		// file.delete();
	}
	
	//给titlelist赋值
	private void addTitlelist()
	{
		titlelist.clear();
		for(CoServicePolicyTypeModel ml:list)
		{
			titlelist.addAll(ml.getTitlelist());
		}
	}
	
	public List<CoServicePolicyTypeModel> getList() {
		return list;
	}
	public void setList(List<CoServicePolicyTypeModel> list) {
		this.list = list;
	}
	public List<CoServicePolicyTypeModel> getTypelist() {
		return typelist;
	}
	public void setTypelist(List<CoServicePolicyTypeModel> typelist) {
		this.typelist = typelist;
	}

	public List<CoServicePolicyTitleModel> getTitlelist() {
		return titlelist;
	}

	public void setTitlelist(List<CoServicePolicyTitleModel> titlelist) {
		this.titlelist = titlelist;
	}

	public String getTiptxt() {
		return tiptxt;
	}

	public void setTiptxt(String tiptxt) {
		this.tiptxt = tiptxt;
	}
	
}
