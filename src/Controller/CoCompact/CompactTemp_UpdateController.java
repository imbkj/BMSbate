package Controller.CoCompact;

import impl.CoCompactOperateComparatorImpl;
import impl.UserInfoImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import service.UserInfoService;
import Model.CoCompactModel;
import Model.CoCompactOperateGroupingModel;
import Model.CoOfferListModel;
import bll.CoCompact.CoCompact_OperateBll;

public class CompactTemp_UpdateController extends SelectorComposer<Component> {
	private CoCompact_OperateBll cocoBll = new CoCompact_OperateBll();
	//private List citylist; // 合同履行地
	private List<CoOfferListModel> coofferinfoList;
	private CoCompactOperateGroupingModel coofferInfoGroupList;
	private boolean showGroup = false;

	// 获取用户名
	Session session = Executions.getCurrent().getDesktop().getSession();
	UserInfoService user = new UserInfoImpl(session);
	String username = user.getUsername();

	int coco_id = ((CoCompactModel) Executions.getCurrent().getArg()
			.get("cocoM")).getCoco_id();
	
	Date inureDate = new Date();	//读取合同生效日，用于string转换date


	public CompactTemp_UpdateController() throws Exception {
		//citylist = cocoBll.getCityName(); // 合同履行地
		
		setCoofferinfoList(new ListModelList<CoOfferListModel>(
				cocoBll.getCoOfferInfoList(coco_id)));// 报价单详情
		
		coofferInfoGroupList = new CoCompactOperateGroupingModel(
				getCoofferinfoList(), new CoCompactOperateComparatorImpl(),
				showGroup);//获取报价单分组列表
		
		//读取合同生效日
		String sInureDate= ((CoCompactModel) Executions.getCurrent().getArg()
				.get("cocoM")).getCoco_inuredate();;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
			inureDate = sdf.parse(sInureDate);
		}
		catch(Exception e){
			inureDate = null;
		}

	}

	// 公司合同修改模板方法
	@Command("updateCompactTemp")
	public void updateCompactTemp(@BindingParam("w1") final Window w1,
			@BindingParam("coco_inuredate") Datebox coco_inuredate,@BindingParam("coco_compacttype") Textbox coco_compacttype,
			@BindingParam("coco_remark") Textbox coco_remark) throws Exception {

		//日期判断
		String inuredate="";
		if(coco_inuredate.getValue() != null){
			inuredate = cocoBll.DatetoSting(coco_inuredate.getValue());
		}
				
		// 判断必填项是否为空
		if (!inuredate.equals("")) {

			// 调用合同生成模板方法
			String[] message = cocoBll.updateCompactTemp(coco_id,
					"深圳",inuredate, coco_remark.getValue());

			// 弹出提示并跳转页面
			if (message[0].equals("1")) {
				EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
					public void onEvent(ClickEvent event) throws Exception {
						if (Messagebox.Button.OK.equals(event.getButton())) {
							//跳转页面
							// Executions.sendRedirect("Compact_InFileList.zul");
							// 
							w1.detach();
						}
					}
				};
				// 弹出提示
				Messagebox.show(message[1], "操作提示",
						new Messagebox.Button[] { Messagebox.Button.OK },
						Messagebox.INFORMATION, clickListener);
			} else {
				// 弹出提示
				Messagebox.show(message[1], "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
		} else {
			/*if (coco_servicearea.getValue().equals("")) {
				Messagebox.show("请选择“合同履行地”", "操作提示", Messagebox.OK,
						Messagebox.ERROR);
			}
			else*/ if("".equals(inuredate)){
				Messagebox.show("请选择“合同生效日期”", "操作提示", Messagebox.OK, Messagebox.ERROR);
			}
		}
	}

	
	public Date getInureDate() {
		return inureDate;
	}

	public void setInureDate(Date inureDate) {
		this.inureDate = inureDate;
	}

/*	public List getCitylist() {
		return citylist;
	}

	public void setCitylist(List citylist) {
		this.citylist = citylist;
	}*/

	public List<CoOfferListModel> getCoofferinfoList() {
		return coofferinfoList;
	}

	public void setCoofferinfoList(List<CoOfferListModel> coofferinfoList) {
		this.coofferinfoList = coofferinfoList;
	}

	public CoCompactOperateGroupingModel getCoofferInfoGroupList() {
		return coofferInfoGroupList;
	}

	public void setCoofferInfoGroupList(
			CoCompactOperateGroupingModel coofferInfoGroupList) {
		this.coofferInfoGroupList = coofferInfoGroupList;
	}

}
