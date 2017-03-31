package Controller.Embase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import dal.DocumentsInfo.Documents_OperateDal;

import bll.Embase.EmbaseLogin_AddBll;

import Controller.DocumentsInfo.DocumentsInfo_OperationController;
import Model.Emcontactinfo;
import Model.EmbaseModel;
import Model.LoginModel;
import Model.PubCodeConversionModel;
import bll.Embase.Emba_Contactbll;

import bll.Embase.EmbaseListBll;
import bll.Taskflow.EmBaseMenulistSelectBll;
import Util.DateStringChange;
import Util.IdcardUtil;
import Util.UserInfo;

public class EmContactController {
	private int gid;
	private EmbaseModel emmodel = new EmbaseModel();

	private Emcontactinfo emztM = new Emcontactinfo();
	private  Emcontactinfo emztOldM = new Emcontactinfo();

	private DocumentsInfo_OperationController docOC = new DocumentsInfo_OperationController();
	private EmbaseListBll embabll = new EmbaseListBll();
	private EmBaseMenulistSelectBll selectbll = new EmBaseMenulistSelectBll();
	private Emba_Contactbll ecbll = new Emba_Contactbll();
	private EmbaseLogin_AddBll eabll = new EmbaseLogin_AddBll();

	private Date fileendmonth;
	private DateStringChange sdc = new DateStringChange();
	private String r_record;
	private List<String> emzt_titlelist = new ListModelList<>();;

	private List<PubCodeConversionModel> educationList = eabll
			.getPubCodeList("文化程度"); // 学历下拉框数据
	private List<String> folkList = eabll.getFolkList(); // 民族
	
	private EmbaseLogin_AddBll embll = new EmbaseLogin_AddBll();
	
	private String recordsd="";
	private String doType; //材料新增或者修改参数
	private int tid;
	
	private ArrayList<String> sbrelationList = new ArrayList<String>();

	//private String datastate;

	public EmContactController() {
		sbrelationList.add("");
		sbrelationList.add("配偶");
		sbrelationList.add("子女");
		// 获取gid
		gid = (Integer) Executions.getCurrent().getArg().get("gid");
		// gid=10065;
		emztM = ecbll.getemcontactmodel(gid);
		emztOldM =(Emcontactinfo)emztM.clone();
		dateload();
		tid=emztM.getId();
		Documents_OperateDal doDal = new Documents_OperateDal();
		int type=doDal.chenckDocSubmitInfo(137,tid);
		
		if (type==0)
		{
			doType="a";
			tid=0;
		}
		else
		{
			doType="u";
		 
		}
	 

	}

	private void dateload() {

		emzt_titlelist.add("无");
		emzt_titlelist.add("初级");
		emzt_titlelist.add("中级");
		emzt_titlelist.add("高级");
		// 获取embasmodel
		emmodel = selectbll.getEmbaseLoginInfo(gid).get(0);
		// 获取联系记录model
		
		if (!"".equals(emztM.getEmzt_hand()))
				{
		emztM.setEmzt_hand(emmodel.getEmba_hand());
				}
		
		if (!"".equals(emztM.getEmzt_computerid()))
		{
				emztM.setEmzt_computerid(emmodel.getEmba_computerid());
		}
		
		if (!"".equals(emztM.getEmzt_houseid()))
		{
				emztM.setEmzt_houseid(emmodel.getEmba_houseid());
		}
		
		if (!"".equals(emztM.getEmzt_marital()))
		{
				emztM.setEmzt_marital(emmodel.getEmba_marital());
		}
		
		if (!"".equals(emztM.getEmzt_education()))
		{
				emztM.setEmzt_education(emmodel.getEmba_education());
		}
		if (!"".equals(emztM.getEmzt_education()))
		{
				emztM.setEmzt_education(emmodel.getEmba_education());
		}
		
		if (!"".equals(emztM.getEmzt_title()))
		{
				emztM.setEmzt_title(emmodel.getEmba_title());
		}
		
	 
	 
		
		System.out.println(emztM.getEmzt_title());
	

		if (emztM.getEmzt_fileendmonth() > 0) {
			fileendmonth = sdc.StringtoDate(
					String.valueOf(emztM.getEmzt_fileendmonth()), "yyyyMM");
		} else {
			fileendmonth = new Date();
		}

		// 材料状态
		// datastate = emztM.getDatastate();

	}
 
	@Command("ckeckstrname")
	public void ckeckstrname()
	{
		if (!emztM.getEmzt_t_name().equals(emmodel.getEmba_name()) )
		{
			Messagebox.show("姓名与实际不符", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		 
	}
	
	@Command("ckeckstridcard")
	public void ckeckstridcard()
	{
		if (!emztM.getEmzt_t_idcard().equals(emmodel.getEmba_idcard()) )
		{
			Messagebox.show("身份证与实际不符", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		 
	}

	@Command("ckeckstrhj")
	public void ckeckstrhj()
	{
		if (!emztM.getEmzt_hjadd().equals(emmodel.getEmba_hjadd()) )
		{
			Messagebox.show("户籍与实际不符", "操作提示", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		 
	}



	// 打开短信页面
	@Command("openmobile")
	public void openmobile() {
		Map map = new HashMap<>();
		System.out.println(emmodel.getEmba_mobile());
		if (emmodel.getEmba_mobile() != null
				&& emmodel.getEmba_mobile().length() > 2
				&& !emmodel.getEmba_mobile().equals("")) {
			map.put("mobile", emmodel.getEmba_mobile());
			map.put("gid", emmodel.getGid());
			Window window;
			window = (Window) Executions.createComponents(
					"../Embase/SMS_Add.zul", null, map);
			window.doModal();
		} else {
			Messagebox.show("由于数据量太大，请尽量输入完整的手机号码，避免崩溃", "操作提示", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	// 打开图片页面
	@Command("openempic")
	public void openempic() {
		Map map = new HashMap<>();
		map.put("gid", gid);
		map.put("rwd", "rwd");
		Window window;
		window = (Window) Executions.createComponents("../Embase/EmPic_Up.zul",
				null, map);
		window.doModal();
	}

	// 更新或插入emcontactinfomodel
	@SuppressWarnings("unused")
	@Command("btnSubmit")
	@NotifyChange("emztM")
	public void btnSubmit(@BindingParam("gd") final Grid gd) {
		Integer mas;
		String record = ""; // 记录
		String oldrecord = "";
		Integer  IDENTITY;
		// oldrecord =emztOldM.getEmzt_r_record().toString(); // 历史记录

		// 判断更新的内容

		if  (emztOldM.getEmzt_t_name()!=null){
		if (!emztOldM.getEmzt_t_name().equals(emztM.getEmzt_t_name())) {
			record = record + "正确姓名由“" + emztOldM.getEmzt_t_name() + "”改为“"
					+ emztM.getEmzt_t_name() + "”；";
			
			if (!"".equals(emztM.getEmzt_t_name().trim()))
			{
			
			emmodel.setEmba_name(emztM.getEmzt_t_name());
			emmodel.setEmba_gjjuname(emztM.getEmzt_t_name());
			emmodel.setEmba_sbuname(emztM.getEmzt_t_name());
			
		}}
		}
		else
		{
			if(emztM.getEmzt_t_name()!=null)
			{
				record = record + "正确姓名由“" + emztOldM.getEmzt_t_name() + "”改为“"
						+ emztM.getEmzt_t_name() + "”；";
				if (!"".equals(emztM.getEmzt_t_name().trim()))
				{
				emmodel.setEmba_name(emztM.getEmzt_t_name());
				emmodel.setEmba_gjjuname(emztM.getEmzt_t_name());
				emmodel.setEmba_sbuname(emztM.getEmzt_t_name());
				
			}
			}
		}
		if ( emztOldM.getEmzt_t_idcard()!=null){
		if (!emztOldM.getEmzt_t_idcard().equals(emztM.getEmzt_t_idcard()) ) {
			record = record + "正确身份证由“" + emztOldM.getEmzt_t_idcard() + "”改为“"
					+ emztM.getEmzt_t_idcard() + "”；";
			
			if (!"".equals(emztM.getEmzt_t_idcard().trim()))
			{
			
			emmodel.setEmba_idcard(emztM.getEmzt_t_idcard());
			emmodel.setEmba_gjjidcard(emztM.getEmzt_t_idcard());
			emmodel.setEmba_sbidcard(emztM.getEmzt_t_idcard());
		}}
		
		}
		else
		{
			if(emztM.getEmzt_t_idcard()!=null)
			{
				record = record + "正确身份证由“" + emztOldM.getEmzt_t_idcard() + "”改为“"
						+ emztM.getEmzt_t_idcard() + "”；";

				if (!"".equals(emztM.getEmzt_t_idcard().trim()))
				{
				emmodel.setEmba_idcard(emztM.getEmzt_t_idcard());
				emmodel.setEmba_gjjidcard(emztM.getEmzt_t_idcard());
				emmodel.setEmba_sbidcard(emztM.getEmzt_t_idcard());
				}
			}
		}
	 
		if ( emztOldM.getEmzt_hjadd()!=null){
		if (!emztOldM.getEmzt_hjadd().equals(emztM.getEmzt_hjadd())) {
			record = record + "户籍由“" + emztOldM.getEmzt_hjadd() + "”改为“"
					+ emztM.getEmzt_hjadd() + "”；";
		}
		}
		
		else
		{
			if(emztM.getEmzt_hjadd()!=null)
			{
				record = record + "户籍由“" + emztOldM.getEmzt_hjadd() + "”改为“"
						+ emztM.getEmzt_hjadd() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_education()!=null){
		if (!emztOldM.getEmzt_education().equals(emztM.getEmzt_education())) {
			record = record + "学历由“" + emztOldM.getEmzt_education() + "”改为“"
					+ emztM.getEmzt_education() + "”；";
		}
		}
		
		else
		{
			if(emztM.getEmzt_education()!=null)
			{
				record = record + "学历由“" + emztOldM.getEmzt_education() + "”改为“"
						+ emztM.getEmzt_education() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_folk()!=null){
		if (!emztOldM.getEmzt_folk().equals( emztM.getEmzt_folk())) {
			record = record + "民族由“" + emztOldM.getEmzt_folk() + "”改为“"
					+ emztM.getEmzt_folk() + "”；";
		}
		}
		
		else
		{
			if(emztM.getEmzt_folk()!=null)
			{
				record = record + "民族由“" + emztOldM.getEmzt_folk() + "”改为“"
						+ emztM.getEmzt_folk() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_email()!=null){
		if (!emztOldM.getEmzt_email().equals(emztM.getEmzt_email())) {
			record = record + "Email由“" + emztOldM.getEmzt_email() + "”改为“"
					+ emztM.getEmzt_email() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_email()!=null)
			{
				record = record + "Email由“" + emztOldM.getEmzt_email() + "”改为“"
						+ emztM.getEmzt_email() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_ifshebao()!=null){
		if (!emztOldM.getEmzt_ifshebao().equals(emztM.getEmzt_ifshebao())) {
			record = record + "是否买过社保由“" + emztOldM.getEmzt_ifshebao() + "”改为“"
					+ emztM.getEmzt_ifshebao() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_ifshebao()!=null)
			{
				record = record + "是否买过社保由“" + emztOldM.getEmzt_ifshebao() + "”改为“"
						+ emztM.getEmzt_ifshebao() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_computerid()!=null){
		if (!emztOldM.getEmzt_computerid().equals(emztM.getEmzt_computerid())) {
			record = record + "社保电脑号由“" + emztOldM.getEmzt_computerid()
					+ "”改为“" + emztM.getEmzt_computerid() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_computerid()!=null)
			{
				record = record + "社保电脑号由“" + emztOldM.getEmzt_computerid()
						+ "”改为“" + emztM.getEmzt_computerid() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_hand()!=null){
		if (!emztOldM.getEmzt_hand().equals(emztM.getEmzt_hand())) {
			record = record + "利手由“" + emztOldM.getEmzt_hand() + "”改为“"
					+ emztM.getEmzt_hand() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_hand()!=null)
			{
				record = record + "利手由“" + emztOldM.getEmzt_hand() + "”改为“"
						+ emztM.getEmzt_hand() + "”；";
			}
		}
		
		
		if ( emztOldM.getEmzt_ifsbcard()!=null){
		if (!emztOldM.getEmzt_ifsbcard().equals(emztM.getEmzt_ifsbcard())) {
			record = record + "是否办过社保卡由“" + emztOldM.getEmzt_ifsbcard()
					+ "”改为“" + emztM.getEmzt_ifsbcard() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_ifsbcard()!=null)
			{
				record = record + "是否办过社保卡由“" + emztOldM.getEmzt_ifsbcard()
						+ "”改为“" + emztM.getEmzt_ifsbcard() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_ifhouse()!=null){
		if (!emztOldM.getEmzt_ifhouse().equals((emztM.getEmzt_ifhouse())))  {
			record = record + "是否买过公积金由“" + emztOldM.getEmzt_ifhouse() + "”改为“"
					+ emztM.getEmzt_ifhouse() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_ifhouse()!=null)
			{
				record = record + "是否买过公积金由“" + emztOldM.getEmzt_ifhouse() + "”改为“"
						+ emztM.getEmzt_ifhouse() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_houseid()!=null){
		if (!emztOldM.getEmzt_houseid().equals(emztM.getEmzt_houseid())) {
			record = record + "公积金号由“" + emztOldM.getEmzt_houseid() + "”改为“"
					+ emztM.getEmzt_houseid() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_houseid()!=null)
			{
				record = record + "公积金号由“" + emztOldM.getEmzt_houseid() + "”改为“"
						+ emztM.getEmzt_houseid() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_marital()!=null){
		if (!emztOldM.getEmzt_marital().equals(emztM.getEmzt_marital())) {
			record = record + "婚姻状况由“" + emztOldM.getEmzt_marital() + "”改为“"
					+ emztM.getEmzt_marital() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_marital()!=null)
			{
				record = record + "婚姻状况由“" + emztOldM.getEmzt_marital() + "”改为“"
						+ emztM.getEmzt_marital() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_m_name()!=null){
		if (!emztOldM.getEmzt_m_name().equals(emztM.getEmzt_m_name())) {
			record = record + "配偶姓名由“" + emztOldM.getEmzt_m_name() + "”改为“"
					+ emztM.getEmzt_m_name() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_m_name()!=null)
			{
				record = record + "配偶姓名由“" + emztOldM.getEmzt_m_name() + "”改为“"
						+ emztM.getEmzt_m_name() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_m_idcard()!=null){
		if (!emztOldM.getEmzt_m_idcard().equals(emztM.getEmzt_m_idcard())) {
			record = record + "配偶身份证由“" + emztOldM.getEmzt_m_idcard() + "”改为“"
					+ emztM.getEmzt_m_idcard() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_m_idcard()!=null)
			{
				record = record + "配偶身份证由“" + emztOldM.getEmzt_m_idcard() + "”改为“"
						+ emztM.getEmzt_m_idcard() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_fileplace()!=null){
		if (!emztOldM.getEmzt_fileplace().equals(emztM.getEmzt_fileplace())) {
			record = record + "档案存放单位由“" + emztOldM.getEmzt_fileplace()
					+ "”改为“" + emztM.getEmzt_fileplace() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_fileplace()!=null)
			{
				record = record + "档案存放单位由“" + emztOldM.getEmzt_fileplace()
						+ "”改为“" + emztM.getEmzt_fileplace() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_ifda()!=null){
		if (!emztOldM.getEmzt_ifda().equals(emztM.getEmzt_ifda())) {
			record = record + "是否有原托管协议由“" + emztOldM.getEmzt_ifda() + "”改为“"
					+ emztM.getEmzt_ifda() + "”；";
		}
		}
		
		else
		{
			if(emztM.getEmzt_ifda()!=null)
			{
				record = record + "是否有原托管协议由“" + emztOldM.getEmzt_ifda() + "”改为“"
						+ emztM.getEmzt_ifda() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_ifowed()!=null){
		if (!emztOldM.getEmzt_ifowed().equals(emztM.getEmzt_ifowed())) {
			record = record + "是否欠费由“" + emztOldM.getEmzt_ifowed() + "”改为“"
					+ emztM.getEmzt_ifowed() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_ifowed()!=null)
			{
				record = record + "是否欠费由“" + emztOldM.getEmzt_ifowed() + "”改为“"
						+ emztM.getEmzt_ifowed() + "”；";
			}
		}
		
 
		if (emztOldM.getEmzt_fileendmonth()!=(emztM.getEmzt_fileendmonth())) {
			record = record + "档案费用缴至哪个月由“"
					+ String.valueOf(emztOldM.getEmzt_fileendmonth()) + "”改为“"
					+ String.valueOf(emztM.getEmzt_fileendmonth()) + "”；";
		}
		 
		if ( emztOldM.getEmzt_ifrc()!=null){
			if (!emztOldM.getEmzt_ifrc().equals(emztM.getEmzt_ifrc())) {
			record = record + "户口是否托管在人才由“" + emztOldM.getEmzt_ifrc() + "”改为“"
					+ emztM.getEmzt_ifrc() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_ifrc()!=null)
			{
				record = record + "户口是否托管在人才由“" + emztOldM.getEmzt_ifrc() + "”改为“"
						+ emztM.getEmzt_ifrc() + "”；";
			}
		}
		
			if ( emztOldM.getEmzt_ofileplace()!=null){
		if (!emztOldM.getEmzt_ofileplace().equals(emztM.getEmzt_ofileplace())) {
			record = record + "其它档案存放单位由“" + emztOldM.getEmzt_ofileplace()
					+ "”改为“" + emztM.getEmzt_ofileplace() + "”；";
		}
			}
			else
			{
				if(emztM.getEmzt_ofileplace()!=null)
				{
					record = record + "其它档案存放单位由“" + emztOldM.getEmzt_ofileplace()
							+ "”改为“" + emztM.getEmzt_ofileplace() + "”；";
				}
			}
			
		if ( emztOldM.getEmzt_iffileservice()!=null){
		if (!emztOldM.getEmzt_iffileservice().equals(emztM.getEmzt_iffileservice())) {
			record = record + "是否有档案托管服由“" + emztOldM.getEmzt_iffileservice()
					+ "”改为“" + emztM.getEmzt_iffileservice() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_iffileservice()!=null)
			{
				record = record + "是否有档案托管服由“" + emztOldM.getEmzt_iffileservice()
						+ "”改为“" + emztM.getEmzt_iffileservice() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_iffilechange()!=null){
		if (!emztOldM.getEmzt_iffilechange().equals(emztM.getEmzt_iffilechange())) {
			record = record + "员工档案是否转移由“" + emztOldM.getEmzt_iffilechange()
					+ "”改为“" + emztM.getEmzt_iffilechange() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_iffilechange()!=null)
			{
				record = record + "员工档案是否转移由“" + emztOldM.getEmzt_iffilechange()
						+ "”改为“" + emztM.getEmzt_iffilechange() + "”；";
			}
		}
		if ( emztOldM.getEmzt_nifc_reason()!=null){
		if (!emztOldM.getEmzt_nifc_reason().equals(emztM.getEmzt_nifc_reason())) {
			record = record + "员工档案不可转移原因由“" + emztOldM.getEmzt_nifc_reason()
					+ "”改为“" + emztM.getEmzt_nifc_reason() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_nifc_reason()!=null)
			{
				record = record + "员工档案不可转移原因由“" + emztOldM.getEmzt_nifc_reason()
						+ "”改为“" + emztM.getEmzt_nifc_reason() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_ifhouseseal()!=null){
		if (!emztOldM.getEmzt_ifhouseseal().equals(emztM.getEmzt_ifhouseseal())) {
			record = record + "是否通知员工完成公积金封存由“"
					+ emztOldM.getEmzt_ifhouseseal() + "”改为“"
					+ emztM.getEmzt_ifhouseseal() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_ifhouseseal()!=null)
			{
				record = record + "是否通知员工完成公积金封存由“"
						+ emztOldM.getEmzt_ifhouseseal() + "”改为“"
						+ emztM.getEmzt_ifhouseseal() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_sbc_notice()!=null){
		if (!emztOldM.getEmzt_sbc_notice().equals(emztM.getEmzt_sbc_notice())) {
			record = record + "是否通知员工办理社保卡由“" + emztOldM.getEmzt_sbc_notice()
					+ "”改为“" + emztM.getEmzt_sbc_notice() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_sbc_notice()!=null)
			{
				record = record + "是否通知员工办理社保卡由“" + emztOldM.getEmzt_sbc_notice()
						+ "”改为“" + emztM.getEmzt_sbc_notice() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_data_notice()!=null){
		if (!emztOldM.getEmzt_data_notice().equals(emztM.getEmzt_data_notice())) {
			record = record + "是否通知员工提交相关材料由“" + emztOldM.getEmzt_data_notice()
					+ "”改为“" + emztM.getEmzt_data_notice() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_data_notice()!=null)
			{
				record = record + "是否通知员工提交相关材料由“" + emztOldM.getEmzt_data_notice()
						+ "”改为“" + emztM.getEmzt_data_notice() + "”；";
			}
		}
		
		if ( emztOldM.getEmzt_title()!=null){
		if (!emztOldM.getEmzt_title().equals(emztM.getEmzt_title())) {
			record = record + "职称由“" + emztOldM.getEmzt_title() + "”改为“"
					+ emztM.getEmzt_title() + "”；";
		}
		}
		else
		{
			if(emztM.getEmzt_title()!=null)
			{
				record = record + "职称由“" + emztOldM.getEmzt_title() + "”改为“"
						+ emztM.getEmzt_title() + "”；";
			}
		}
		
		
		if (sdc != null) {
			emztM.setEmzt_fileendmonth(Integer.parseInt(sdc.DatetoSting(
					fileendmonth, "YYYYMM")));
		}
		
		if(!"".equals(record) || !"".equals(recordsd))
		{
			
				record=record+recordsd;
				record=record+ "【"
						+ sdc.Datestringnow("yyyy-MM-dd") + "   "
						+ UserInfo.getUsername() + "】；";
				
			record = record +	emztOldM.getEmzt_r_record();
			
			emztM.setEmzt_r_record(record);
			
		}	
		else 
		{
			
			emztM.setEmzt_r_record(emztOldM.getEmzt_r_record());
		}
		

		
		 
		try {
			 	Emcontactinfo m = new Emcontactinfo();
				m.setEmzt_education(emztM.getEmzt_education());
				m.setEmzt_folk(emztM.getEmzt_folk());
				m.setEmzt_email(emztM.getEmzt_email());
				m.setEmzt_computerid(emztM.getEmzt_computerid());
				m.setEmzt_hand(emztM.getEmzt_hand());
				m.setEmzt_houseid(emztM.getEmzt_houseid());
				m.setGid(emztM.getGid());
				
				boolean flag = ecbll.updateEmbaInfo(m);
				
				//修改基本信息手机
				
			
				emmodel.setEmba_modname(UserInfo.getUsername());
				Integer i = embll.modInfo(emmodel);
				
			 
				
		
				
				
				
				if (ecbll.addembacontactinfo(emztM)> 0 || flag) {

					Messagebox.show("操作失败", "操作提示", Messagebox.OK,
							Messagebox.ERROR);
				} else {
					
					IDENTITY=ecbll.getemcontactmodel(gid).getId();
					
					try {
						// a是新增；u是修改
						String[] message=new String[1];
						
						
						// 新增
						if ("a".equals(doType)) {
							// 调用方法
							message = docOC.AddchkHaveTo(gd);

							// 判断材料必选项是否已选
							if (message[0].equals("1")) {

									message = docOC.AddsubmitDoc(gd, String.valueOf(IDENTITY));
								}

							 
						} else if ("u".equals(doType)) {
							// 调用方法
							message = docOC.UpsubmitDoc(gd, String.valueOf(IDENTITY));
						}
						Messagebox.show(message[1], "操作提示", Messagebox.OK,
								Messagebox.INFORMATION);

						// 修改

					} catch (Exception e) {
						Messagebox.show("操作失败。", "操作提示", Messagebox.OK, Messagebox.ERROR);
					}

					
//					Messagebox.show("保存成功", "提示", Messagebox.OK,
//							Messagebox.INFORMATION);
				}
			
		
				
			
					
	
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		emztM = ecbll.getemcontactmodel(gid);
	}
	
	@Command
	public void computerid_search() {
		try {
			if (emmodel.getEmba_name() == null || emmodel.getEmba_name().isEmpty()) {
				Messagebox.show("请输入姓名!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (emmodel.getEmba_idcard() == null
					|| emmodel.getEmba_idcard().isEmpty()) {
				Messagebox.show("请输入身份证号码!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else if (!IdcardUtil.validateCard(emmodel.getEmba_idcard())) {
				Messagebox.show("身份证不合法,请检查是否正确!", "ERROR", Messagebox.OK,
						Messagebox.ERROR);
			} else {
				String url = "/Embase/Embase_Computerid_search.zul";
				String searurl = "http://dgciic:81/ComputeridSearch.aspx?idcard="
						+ emmodel.getEmba_idcard();
				Map<String, Object> map = new HashMap<>();
				map.put("url", searurl);

				Window window = (Window) Executions.createComponents(url, null,
						map);
				window.doModal();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show("错误：" + e.toString(), "ERROR", Messagebox.OK,
					Messagebox.ERROR);
		}
	}
	
	@Command
	public void sendmsg(@BindingParam("table") String table) {
		String uname = "";
		Map map = new HashMap<>();
		map.put("type", "all");// 业务类型:来自WfClass的wfcl_name
		map.put("id", emztM.getId());// 业务表id
		map.put("tablename", table);// 业务表名
		map.put("msgname", uname);// 默认收件人,没有默认收件人则为空""
		List<LoginModel> mlist = new ArrayList<LoginModel>();
		LoginModel m = new LoginModel();
		m.setLog_name(uname);
		// 收件人姓名和收件人id至少要填一个
		mlist.add(m);
		map.put("list", mlist);// 默认收件人list
		map.put("title", "");
		Window window = (Window) Executions.createComponents(
				"../SysMessage/Message_Add.zul", null, map);
		window.doModal();
	}

	

	public String getRecordsd() {
		return recordsd;
	}

	public void setRecordsd(String recordsd) {
		this.recordsd = recordsd;
	}

	public List<String> getEmzt_titlelist() {
		return emzt_titlelist;
	}

	public void setEmzt_titlelist(List<String> emzt_titlelist) {
		this.emzt_titlelist = emzt_titlelist;
	}

	public EmbaseModel getEmmodel() {
		return emmodel;
	}

	public void setEmmodel(EmbaseModel emmodel) {
		this.emmodel = emmodel;
	}

	public Date getFileendmonth() {
		return fileendmonth;
	}

	public void setFileendmonth(Date fileendmonth) {
		this.fileendmonth = fileendmonth;
	}

	public List<PubCodeConversionModel> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<PubCodeConversionModel> educationList) {
		this.educationList = educationList;
	}

	public List<String> getFolkList() {
		return folkList;
	}

	public void setFolkList(List<String> folkList) {
		this.folkList = folkList;
	}

	public Emcontactinfo getEmztM() {
		return emztM;
	}

	public void setEmztM(Emcontactinfo emztM) {
		this.emztM = emztM;
	}

	public String getDoType() {
		return doType;
	}

	public void setDoType(String doType) {
		this.doType = doType;
	}

	public ArrayList<String> getSbrelationList() {
		return sbrelationList;
	}

	public void setSbrelationList(ArrayList<String> sbrelationList) {
		this.sbrelationList = sbrelationList;
	}
	
}
