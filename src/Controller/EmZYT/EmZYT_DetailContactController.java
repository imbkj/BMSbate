package Controller.EmZYT;

import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.record.formula.eval.StringValueEval;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Messagebox.ClickEvent;

import bll.EmZYT.EmZYT_OperateBll;
import bll.EmZYT.EmZYT_SelectBll;
import bll.Embase.EmbaseLogin_AddBll;

import Model.EmZYTModel;
import Model.PubCodeConversionModel;
import Util.DateStringChange;
import Util.UserInfo;

public class EmZYT_DetailContactController {
	private EmZYT_SelectBll sbll = new EmZYT_SelectBll();
	private EmbaseLogin_AddBll eabll = new EmbaseLogin_AddBll();
	private DateStringChange sdc = new DateStringChange();
	private EmZYT_OperateBll obll = new EmZYT_OperateBll();

	private EmZYTModel emztM = (EmZYTModel) Executions.getCurrent().getArg().get("emztM");
	private EmZYTModel emztOldM = new EmZYTModel(); // 原数据model
	private String username = UserInfo.getUsername();

	private List<PubCodeConversionModel> educationList = eabll
			.getPubCodeList("文化程度"); // 学历下拉框数据
	private List<String> folkList = eabll.getFolkList(); // 民族
	private Date fileendmonth;
	private String datastate;

	public EmZYT_DetailContactController() {

		//获取原数据
		emztOldM=sbll.getEmZYTList(" AND id="+String.valueOf(emztM.getId())).get(0);

		// 档案费用缴至哪个月份类型转换
		try {
			fileendmonth = sdc.StringtoDate(
					String.valueOf(emztM.getEmzt_fileendmonth()), "yyyyMM");
		} catch (Exception e) {
			fileendmonth = new Date();
		}

		// 材料状态
		datastate = emztM.getDatastate();

	}

	@Command("submit")
	public void submit(@BindingParam("type") int type,
			@BindingParam("win") final Window win,
			@BindingParam("r_record") Textbox r_record,
			@BindingParam("emzt_r_record") Textbox emzt_r_record) {


		//联系状态
		if (type == 0) {//点击保存
			emztM.setEmzt_contactstate(emztOldM.getEmzt_contactstate());
		}else{
			emztM.setEmzt_contactstate(type);
			
			// 数据状态判断
			if (type == 4) {
				if (emztM.getEmzt_state() != 0 && emztM.getEmzt_state() != 1
						&& emztM.getEmzt_state() != 2 && emztM.getEmzt_state() != 3
						&& emztM.getEmzt_state() != 11) {
				} else {
					emztM.setEmzt_state(2);
				}
			}
		}
		
		// 档案费用缴至哪个月份判断
		if (fileendmonth != null) {
			emztM.setEmzt_fileendmonth(Integer.parseInt(sdc.DatetoSting(
					fileendmonth, "yyyyMM")));
		}

		// 材料状态
		if (datastate.equals("未提交")) {
			emztM.setEmzt_datastate(1);
		} else if (datastate.equals("部分提交")) {
			emztM.setEmzt_datastate(2);
		} else if (datastate.equals("资料齐全")) {
			emztM.setEmzt_datastate(3);
		} else {
			emztM.setEmzt_datastate(0);
		}

		String record = ""; // 记录
		record =r_record.getValue(); // 历史记录
		
		//判断更新的内容
		if(emztOldM.getEmzt_t_name()!=emztM.getEmzt_t_name()){
			record = record +"正确姓名由“"+emztOldM.getEmzt_t_name()+"”改为“"+emztM.getEmzt_t_name()+"”；";
		}
		if(emztOldM.getEmzt_t_idcard()!=emztM.getEmzt_t_idcard()){
			record = record +"正确身份证由“"+emztOldM.getEmzt_t_idcard()+"”改为“"+emztM.getEmzt_t_idcard()+"”；";
		}
		if(emztOldM.getEmzt_hjadd()!=emztM.getEmzt_hjadd()){
			record = record +"户籍由“"+emztOldM.getEmzt_hjadd()+"”改为“"+emztM.getEmzt_hjadd()+"”；";
		}
		if(emztOldM.getEmzt_education()!=emztM.getEmzt_education()){
			record = record +"学历由“"+emztOldM.getEmzt_education()+"”改为“"+emztM.getEmzt_education()+"”；";
		}
		if(emztOldM.getEmzt_folk()!=emztM.getEmzt_folk()){
			record = record +"民族由“"+emztOldM.getEmzt_folk()+"”改为“"+emztM.getEmzt_folk()+"”；";
		}
		if(emztOldM.getEmzt_email()!=emztM.getEmzt_email()){
			record = record +"Email由“"+emztOldM.getEmzt_email()+"”改为“"+emztM.getEmzt_email()+"”；";
		}
		if(emztOldM.getEmzt_ifshebao()!=emztM.getEmzt_ifshebao()){
			record = record +"是否买过社保由“"+emztOldM.getEmzt_ifshebao()+"”改为“"+emztM.getEmzt_ifshebao()+"”；";
		}
		if(emztOldM.getEmzt_computerid()!=emztM.getEmzt_computerid()){
			record = record +"社保电脑号由“"+emztOldM.getEmzt_computerid()+"”改为“"+emztM.getEmzt_computerid()+"”；";
		}
		if(emztOldM.getEmzt_hand()!=emztM.getEmzt_hand()){
			record = record +"利手由“"+emztOldM.getEmzt_hand()+"”改为“"+emztM.getEmzt_hand()+"”；";
		}
		if(emztOldM.getEmzt_ifsbcard()!=emztM.getEmzt_ifsbcard()){
			record = record +"是否办过社保卡由“"+emztOldM.getEmzt_ifsbcard()+"”改为“"+emztM.getEmzt_ifsbcard()+"”；";
		}
		if(emztOldM.getEmzt_ifhouse()!=emztM.getEmzt_ifhouse()){
			record = record +"是否买过公积金由“"+emztOldM.getEmzt_ifhouse()+"”改为“"+emztM.getEmzt_ifhouse()+"”；";
		}
		if(emztOldM.getEmzt_houseid()!=emztM.getEmzt_houseid()){
			record = record +"公积金号由“"+emztOldM.getEmzt_houseid()+"”改为“"+emztM.getEmzt_houseid()+"”；";
		}
		if(emztOldM.getEmzt_marital()!=emztM.getEmzt_marital()){
			record = record +"婚姻状况由“"+emztOldM.getEmzt_marital()+"”改为“"+emztM.getEmzt_marital()+"”；";
		}
		if(emztOldM.getEmzt_m_name()!=emztM.getEmzt_m_name()){
			record = record +"配偶姓名由“"+emztOldM.getEmzt_m_name()+"”改为“"+emztM.getEmzt_m_name()+"”；";
		}
		if(emztOldM.getEmzt_m_idcard()!=emztM.getEmzt_m_idcard()){
			record = record +"配偶身份证由“"+emztOldM.getEmzt_m_idcard()+"”改为“"+emztM.getEmzt_m_idcard()+"”；";
		}
		if(emztOldM.getEmzt_fileplace()!=emztM.getEmzt_fileplace()){
			record = record +"档案存放单位由“"+emztOldM.getEmzt_fileplace()+"”改为“"+emztM.getEmzt_fileplace()+"”；";
		}
		if(emztOldM.getEmzt_ifda()!=emztM.getEmzt_ifda()){
			record = record +"是否有原托管协议由“"+emztOldM.getEmzt_ifda()+"”改为“"+emztM.getEmzt_ifda()+"”；";
		}
		if(emztOldM.getEmzt_ifowed()!=emztM.getEmzt_ifowed()){
			record = record +"是否欠费由“"+emztOldM.getEmzt_ifowed()+"”改为“"+emztM.getEmzt_ifowed()+"”；";
		}
		if(emztOldM.getEmzt_fileendmonth()!=emztM.getEmzt_fileendmonth()){
			record = record +"档案费用缴至哪个月由“"+String.valueOf(emztOldM.getEmzt_fileendmonth())+"”改为“"+String.valueOf(emztM.getEmzt_fileendmonth())+"”；";
		}
		if(emztOldM.getEmzt_ifrc()!=emztM.getEmzt_ifrc()){
			record = record +"户口是否托管在人才由“"+emztOldM.getEmzt_ifrc()+"”改为“"+emztM.getEmzt_ifrc()+"”；";
		}
		if(emztOldM.getEmzt_ofileplace()!=emztM.getEmzt_ofileplace()){
			record = record +"其它档案存放单位由“"+emztOldM.getEmzt_ofileplace()+"”改为“"+emztM.getEmzt_ofileplace()+"”；";
		}
		if(emztOldM.getEmzt_iffileservice()!=emztM.getEmzt_iffileservice()){
			record = record +"是否有档案托管服由“"+emztOldM.getEmzt_iffileservice()+"”改为“"+emztM.getEmzt_iffileservice()+"”；";
		}
		if(emztOldM.getEmzt_iffilechange()!=emztM.getEmzt_iffilechange()){
			record = record +"员工档案是否转移由“"+emztOldM.getEmzt_iffilechange()+"”改为“"+emztM.getEmzt_iffilechange()+"”；";
		}
		if(emztOldM.getEmzt_nifc_reason()!=emztM.getEmzt_nifc_reason()){
			record = record +"员工档案不可转移原因由“"+emztOldM.getEmzt_nifc_reason()+"”改为“"+emztM.getEmzt_nifc_reason()+"”；";
		}
		if(emztOldM.getEmzt_ifhouseseal()!=emztM.getEmzt_ifhouseseal()){
			record = record +"是否通知员工完成公积金封存由“"+emztOldM.getEmzt_ifhouseseal()+"”改为“"+emztM.getEmzt_ifhouseseal()+"”；";
		}
		if(emztOldM.getEmzt_sbc_notice()!=emztM.getEmzt_sbc_notice()){
			record = record +"是否通知员工办理社保卡由“"+emztOldM.getEmzt_sbc_notice()+"”改为“"+emztM.getEmzt_sbc_notice()+"”；";
		}
		if(emztOldM.getEmzt_data_notice()!=emztM.getEmzt_data_notice()){
			record = record +"是否通知员工提交相关材料由“"+emztOldM.getEmzt_data_notice()+"”改为“"+emztM.getEmzt_data_notice()+"”；";
		}
		if(emztOldM.getEmzt_title()!=emztM.getEmzt_title()){
			record = record +"职称由“"+emztOldM.getEmzt_title()+"”改为“"+emztM.getEmzt_title()+"”；";
		}
		
		
		if (!"".equals(emzt_r_record.getValue())) {
			record = record + emzt_r_record.getValue() + "【"
					+ sdc.Datestringnow("yyyy-MM-dd") + "   " + username + "】；";
		}
		else{
			if (!record.equals(r_record.getValue())) {
				record = record + "【"
						+ sdc.Datestringnow("yyyy-MM-dd") + "   " + username + "】；";
			}
		}

		emztM.setEmzt_r_record(record);
		
		// 调用更新方法
		String[] message = obll.upEmZYTContact(emztM);

		if (message[0].equals("1")) {
			EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
				public void onEvent(ClickEvent event) throws Exception {
					if (Messagebox.Button.OK.equals(event.getButton())) {
						// //跳转页面
						win.detach();
					}
				}
			};
			// 弹出提示
			Messagebox.show(message[1], "操作提示",
					new Messagebox.Button[] { Messagebox.Button.OK },
					Messagebox.INFORMATION, clickListener);
		} else {
			// 弹出提示
			Messagebox
					.show(message[1], "操作提示", Messagebox.OK, Messagebox.ERROR);
		}

	}

	public Date getFileendmonth() {
		return fileendmonth;
	}

	public void setFileendmonth(Date fileendmonth) {
		this.fileendmonth = fileendmonth;
	}

	public EmZYTModel getEmztM() {
		return emztM;
	}

	public void setEmztM(EmZYTModel emztM) {
		this.emztM = emztM;
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

	public String getDatastate() {
		return datastate;
	}

	public void setDatastate(String datastate) {
		this.datastate = datastate;
	}

}
