package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import bll.PrintBll;
import Conn.dbconn;
import Model.ClassPrintModel;
import Util.UserInfo;

public class CommPrintDocumentInfoController {
private ClassPrintModel co=new ClassPrintModel();
private List<ClassPrintModel> list = new ListModelList<ClassPrintModel>();

private int gid =Integer.parseInt(Executions.getCurrent().getArg().get("gid").toString());
private String sql="";
private int row=0;
PrintBll bll =new PrintBll();
public CommPrintDocumentInfoController() {
	init();
}

public void init(){
	String dep_id=UserInfo.getDepID();
	ClassPrintModel co1=new ClassPrintModel();
	ClassPrintModel co2=new ClassPrintModel();
	ClassPrintModel co3=new ClassPrintModel();
	ClassPrintModel co4=new ClassPrintModel();
	ClassPrintModel co5=new ClassPrintModel();
	ClassPrintModel co6=new ClassPrintModel();
	ClassPrintModel co7=new ClassPrintModel();
	ClassPrintModel co8=new ClassPrintModel();
	ClassPrintModel co9=new ClassPrintModel();
	ClassPrintModel co10=new ClassPrintModel();
	ClassPrintModel co11=new ClassPrintModel();
	ClassPrintModel co12=new ClassPrintModel();
	ClassPrintModel co13=new ClassPrintModel();
	ClassPrintModel co14=new ClassPrintModel();
	ClassPrintModel co15=new ClassPrintModel();
	ClassPrintModel co16=new ClassPrintModel();
	ClassPrintModel co17=new ClassPrintModel();
	ClassPrintModel co18=new ClassPrintModel();
	ClassPrintModel co19=new ClassPrintModel();
	ClassPrintModel co20=new ClassPrintModel();
	List<ClassPrintModel> listM = new ArrayList<ClassPrintModel>();
	co1.setId("1");
	co1.setClassuse("我司员工");
	co1.setClasstype("证明");
	co1.setClassname("户口无法迁入中智集体户证明");
	co1.setClassmode("预览");
	listM.add(co1);
	co2.setId("2");
	co2.setClassuse("参加住房公积金");
	co2.setClasstype("住房公积金");
	co2.setClassname("住房公积金个人账户异地转入申请表");
	co2.setClassmode("预览");
	listM.add(co2);
	co3.setId("3");
	co3.setClassuse("离职员工");
	co3.setClasstype("证明");
	co3.setClassname("辞职信（员工给中智）");
	co3.setClassmode("预览");
	listM.add(co3);
	co4.setId("4");
	co4.setClassuse("非广东户籍赴港");
	co4.setClasstype("证明");
	co4.setClassname("在职证明");
	co4.setClassmode("预览");
	listM.add(co4);
	co5.setId("5");
	co5.setClassuse("派遣类员工");
	co5.setClasstype("证明");
	co5.setClassname("派遣证明");
	co5.setClassmode("预览");
	listM.add(co5);
	co6.setId("6");
	co6.setClassuse("委托进员工");
	co6.setClasstype("证明");
	co6.setClassname("公积金缴纳证明（受托）");
	co6.setClassmode("预览");
	listM.add(co6);
	co7.setId("7");
	co7.setClassuse("委托进员工");
	co7.setClasstype("证明");
	co7.setClassname("社保缴纳证明（受托）");
	co7.setClassmode("预览");
	listM.add(co7);
	co8.setId("8");
	co8.setClassuse("派遣类离职员工");
	co8.setClasstype("证明");
	co8.setClassname("离职证明");
	co8.setClassmode("预览");
	listM.add(co8);
	co9.setId("9");
	co9.setClassuse("委托进员工");
	co9.setClasstype("证明");
	co9.setClassname("开具户口无法接收证明");
	co9.setClassmode("预览");
	listM.add(co9);
	co18.setId("18");
	co18.setClassuse("在中智户参加住房公积金员工");
	co18.setClasstype("证明");
	co18.setClassname("公积金缴纳证明");
	co18.setClassmode("预览");
	listM.add(co18);
	co19.setId("19");
	co19.setClassuse("在中智户参加社保员工");
	co19.setClasstype("证明");
	co19.setClassname("社保缴纳证明");
	co19.setClassmode("预览");
	listM.add(co19);
	co20.setId("20");
	co20.setClassuse("在中智户参加社保住房公积金员工");
	co20.setClasstype("证明");
	co20.setClassname("社保公积金缴纳证明");
	co20.setClassmode("预览");
	listM.add(co20);
	//co10.setId("10");
	//co10.setClassuse("客服");
	//co10.setClasstype("证明");
	//co10.setClassname("社保代缴纳证明");
	//co10.setClassmode("预览");
	//listM.add(co10);
	if(dep_id.equals("8")){
		co11.setId("11");
		co11.setClassuse("离职员工(外包)");
		co11.setClasstype("证明");
		co11.setClassname("离职证明(外包)");
		co11.setClassmode("预览");
		listM.add(co11);
		co12.setId("12");
		co12.setClassuse("员工(外包)");
		co12.setClasstype("证明");
		co12.setClassname("出国在职证明(外包)");
		co12.setClassmode("预览");
		listM.add(co12);
		co13.setId("13");
		co13.setClassuse("员工(外包)");
		co13.setClasstype("证明");
		co13.setClassname("标准版在职证明(外包)");
		co13.setClassmode("预览");
		listM.add(co13);
		co14.setId("14");
		co14.setClassuse("员工(外包)");
		co14.setClasstype("证明");
		co14.setClassname("收入证明(外包)");
		co14.setClassmode("预览");
		listM.add(co14);
		co15.setId("15");
		co15.setClassuse("员工(外包)");
		co15.setClasstype("辞职信模版");
		co15.setClassname("辞职信(外包)");
		co15.setClassmode("预览");
		listM.add(co15);
		co16.setId("16");
		co16.setClassuse("员工(外包)");
		co16.setClasstype("说明");
		co16.setClassname("未能提供离职证明的说明(外包)");
		co16.setClassmode("预览");
		listM.add(co16);
		co17.setId("17");
		co17.setClassuse("员工(外包)");
		co17.setClasstype("证明");
		co17.setClassname("实习证明(外包)");
		co17.setClassmode("预览");
		listM.add(co17);
	}
    setList(listM);
}
@Command("print")
public void print(@BindingParam("cpm") ClassPrintModel cpm){
	Map<String, Object> arg =new HashMap<String, Object>();
	arg.put("gid", gid);
	System.out.println(gid);
	String url="";
	if(cpm.getId().equals("1")){
		sql="select distinct emba_name,emba_idcard,cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d "
	    		+" from embase a "
	    		+" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='社会保险服务' "
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户' "
	    		+" inner join cobase f on a.cid=f.cid "
	    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=2 "
	    		+" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0  "
	    		+" where a.gid="+gid+" and a.emba_state in(1,2,5) ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class1.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("2")){
		sql="select cohf_bankgj,cohf_houseid,cohf_company,emhu_houseid,emhu_name,emhu_idcard "
	    		 +" from embase a "
	    		 +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	    		 +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='住房公积金服务' "
	    		 +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		 +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户' "
	    		 +" inner join cobase f on a.cid=f.cid "
	    		 +" inner join login g on f.coba_client=g.log_name and log_inure=1 "
	    		 +" inner join (select m.cohf_bankgj,cohf_houseid,cohf_company,emhu_houseid,emhu_name,emhu_idcard,GID,Emhu_IfStop "
	    		 +" from EmHouseUpdate k inner join CoHousingFund m on k.emhu_companyid=m.cohf_houseid ) i on a.gid=i.GID and i.Emhu_IfStop=0 "
	    		 +" where a.gid="+gid+" and a.emba_state in(1,2,5) ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class2.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("3")){
		 sql="select distinct emba_name,emba_idcard, cast(year(getdate()) as varchar(4)) y, "
	            +" cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d "
	            +" from embase a " 
	            +" inner join coglist b on a.gid=b.gid " 
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id " 
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_compacttype in('af','fs-2') "
	            +" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	            +" where a.gid="+gid+" and a.emba_state=0 ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class3.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("4")){
		sql="select  distinct emba_name,emba_idcard , cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m, "
	               +" cast(day(getdate()) as varchar(4)) d,year(emba_indate) ye,month(emba_indate) mo "
	               +" from embase a "
	               +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	               +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id " 
	               +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	               +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_compacttype in('af','fs-2') "
	               +" inner join cobase f on a.cid=f.cid "
	               +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=2 "
	               +" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0 " 
	               +" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	               +" where a.gid="+gid+" and Esiu_hj  like '%市外%' and a.emba_state in(1,2,5) ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class4.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("5")){
		sql="select  distinct emba_name,emba_idcard , cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m, " 
	    		+" cast(day(getdate()) as varchar(4)) d,year(emba_indate) ye, month(emba_indate) mo,day(emba_indate) da,emba_sex " 
	    		+" from embase a  " 
	    		+" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null " 
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id "  
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id " 
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_compacttype in('af','fs-2') " 
	    		+" inner join cobase f on a.cid=f.cid " 
	    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 "  
	    		+" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0 "  
	    		+" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null " 
	    		+" where a.gid="+gid+" and a.emba_state in(1,2,5) ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class5.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("6")){
		sql="select top 1 coba_company,emba_name,emba_name,i.emhu_houseid,i.emhu_idcard,Ownmonth, "
	            +" cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d from embase a " 
	            +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='住房公积金服务' "
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户' "
	            +" inner join cobase f on a.cid=f.cid "
	            +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=6 "
	            +" inner join (select m.cohf_bankgj,cohf_houseid,cohf_company,emhu_houseid,emhu_name,emhu_idcard,GID,Emhu_IfStop "
	            +"           from EmHouseUpdate k inner join CoHousingFund m on k.emhu_companyid=m.cohf_houseid ) i on a.gid=i.GID and i.Emhu_IfStop=0 "
	            +" left join emhouse n on a.gid=n.GID  "
	            +" where a.gid="+gid+" and a.emba_state in(1,2,5) order by n.Ownmonth ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class6.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("7")){
		sql="select top 1 coba_company,emba_name,coab_name,h.Esiu_ComputerID,h.esiu_idcard,i.Ownmonth,cast(year(getdate()) as varchar(4)) y, "
	            +" cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d from embase a  "
	            +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='社会保险服务' "
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户' "
	            +" inner join cobase f on a.cid=f.cid "
	            +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=6 "
	            +" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0  "
	            +" inner join CoAgencyBase y on e.cabc_id=y.coab_id  "
	            +" left join emshebao i on a.gid=i.GID "
	            +" where a.gid="+gid+" and a.emba_state in(1,2,5) order by i.Ownmonth ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class7.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("8")){
		sql="select distinct a.gid,emba_name,emba_idcard,coba_company,emba_outdate,isnull(ebco_working_station,'') ebco_working_station, "
	    		+" cast(year(emba_indate) as varchar(4)) y,cast(month(emba_indate) as varchar(4)) m,cast(day(emba_indate)as varchar(4)) d, "
	    		+" cast(year(getdate()) as varchar(4)) yn,cast(month(getdate()) as varchar(4)) mn,cast(day(getdate()) as varchar(4)) dn, "
	    		+" year(emba_outdate) ye, month(emba_outdate) mo,day(emba_outdate) da from embase a  "
	    		+" inner join coglist b on a.gid=b.gid  "
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id  "
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_compacttype in('af','fs-2') "
	    		+" inner join EmBaseCompact j on a.gid=j.gid "
	    		+" inner join CoBase co on co.CID=a.cid "
	    		+" where a.gid="+gid+"  and a.emba_state=0  ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class9.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("9")){
		sql="select top 1 coba_company,emba_name,coab_name,h.Esiu_ComputerID,h.esiu_idcard,i.Ownmonth,cast(year(getdate()) as varchar(4)) y, "
	    		+" cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d from embase a  "
	    		+" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='社会保险服务' "
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户'  "
	    		+" inner join cobase f on a.cid=f.cid "
	    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=6 "
	    		+" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0 " 
	    		+" inner join CoAgencyBase y on e.cabc_id=y.coab_id  "
	    		+" left join emshebao i on a.gid=i.GID "
	    		+" where a.gid="+gid+" and a.emba_state in(1,2,5) order by i.Ownmonth ";	
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class8.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("10")){
		 url="../ClassPrint/DocumentInfo_Class10.zul";
	}else if(cpm.getId().equals("11")){
		sql="select distinct a.gid,emba_name,emba_idcard,coba_company,emba_outdate,isnull(ebco_working_station,'') ebco_working_station, "
	    		+" cast(year(emba_indate) as varchar(4)) y,cast(month(emba_indate) as varchar(4)) m,cast(day(emba_indate)as varchar(4)) d, "
	    		+" cast(year(getdate()) as varchar(4)) yn,cast(month(getdate()) as varchar(4)) mn,cast(day(getdate()) as varchar(4)) dn, "
	    		+" year(emba_outdate) ye, month(emba_outdate) mo,day(emba_outdate) da from embase a  "
	    		+" inner join coglist b on a.gid=b.gid  "
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id  "
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9  "
	    		+" inner join cobase f on a.cid=f.cid "
	    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 "
	    		+" inner join EmBaseCompact j on a.gid=j.gid "
	    		+" where a.gid="+gid+"  and a.emba_state=0  ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class20.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 }
	}else if(cpm.getId().equals("12")){
		sql="select distinct a.gid,emba_name,emba_idcard,coba_company,isnull(ebco_working_station,'') ebco_working_station, "
	    		+" cast(year(emba_indate) as varchar(4)) y,cast(month(emba_indate) as varchar(4)) m,cast(day(emba_indate)as varchar(4)) d, "
	    		+" cast(year(getdate()) as varchar(4)) yn,cast(month(getdate()) as varchar(4)) mn,cast(day(getdate()) as varchar(4)) dn "
	    		+" from embase a  "
	    		+" inner join coglist b on a.gid=b.gid  "
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id  "
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9  "
	    		+" inner join cobase f on a.cid=f.cid "
	    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 "
	    		+" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	    		+" where a.gid="+gid+" and a.emba_state in(1,2,5) ";	
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class11.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("13")){
		sql="select distinct a.gid,emba_name,emba_idcard,coba_company,isnull(ebco_working_station,'') ebco_working_station, "
	    		+" cast(year(emba_indate) as varchar(4)) y,cast(month(emba_indate) as varchar(4)) m,cast(day(emba_indate)as varchar(4)) d, "
	    		+" cast(year(getdate()) as varchar(4)) yn,cast(month(getdate()) as varchar(4)) mn,cast(day(getdate()) as varchar(4)) dn "
	    		+" from embase a  "
	    		+" inner join coglist b on a.gid=b.gid  "
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id  "
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9  "
	    		+" inner join cobase f on a.cid=f.cid "
	    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 "
	    		+" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	    		+" where a.gid="+gid+" and a.emba_state in(1,2,5) ";	
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class12.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("14")){
		sql="select  distinct emba_name,emba_idcard , cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m, "
	            +" cast(day(getdate()) as varchar(4)) d,isnull(ebco_working_station,'') ebco_working_station "
	            +" from embase a  "
	            +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id  "
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 " 
	            +" inner join cobase f on a.cid=f.cid "
	            +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 " 
	            +" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	            +" where a.gid="+gid+"  and a.emba_state in(1,2,5) ";	
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class13.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("15")){
		sql="select distinct emba_name,emba_idcard, cast(year(getdate()) as varchar(4)) y, "
	            +" cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d "
	            +" from embase a " 
	            +" inner join coglist b on a.gid=b.gid " 
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id " 
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9  "
	            +" inner join cobase f on a.cid=f.cid "
	            +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 "
	            +" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	            +" where a.gid="+gid+" and a.emba_state=0 ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class14.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("16")){
		sql="select distinct emba_name,emba_idcard, cast(year(getdate()) as varchar(4)) y, "
	            +" cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d "
	            +" from embase a " 
	            +" inner join coglist b on a.gid=b.gid " 
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id " 
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9  "
	            +" inner join cobase f on a.cid=f.cid "
	            +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 "
	            +" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	            +" where a.gid="+gid+" and a.emba_state in(1,2,5) ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class15.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("17")){
		sql="select distinct a.gid,emba_name,emba_idcard,coba_company,isnull(ebco_working_station,'') ebco_working_station, "
	    		+" cast(year(emba_indate) as varchar(4)) y,cast(month(emba_indate) as varchar(4)) m,cast(day(emba_indate)as varchar(4)) d, "
	    		+" cast(year(getdate()) as varchar(4)) yn,cast(month(getdate()) as varchar(4)) mn,cast(day(getdate()) as varchar(4)) dn "
	    		+" from embase a  "
	    		+" inner join coglist b on a.gid=b.gid  "
	    		+" inner join CoOfferList c on b.cgli_coli_id=c.coli_id  "
	    		+" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	    		+" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9  "
	    		+" inner join cobase f on a.cid=f.cid "
	    		+" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=8 "
	    		+" inner join EmBaseCompact j on a.gid=j.gid and ebco_filing_date is not null "
	    		+" where a.gid="+gid+" and a.emba_state in(1,2,5) ";	
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class16.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("18")){
		sql="select top 1 coba_company,emba_name,emba_name,i.emhu_houseid,i.emhu_idcard,Ownmonth, "
	            +" cast(year(getdate()) as varchar(4)) y,cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d from embase a " 
	            +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='住房公积金服务' "
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户' "
	            +" inner join cobase f on a.cid=f.cid "
	            +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=2 "
	            +" inner join (select m.cohf_bankgj,cohf_houseid,cohf_company,emhu_houseid,emhu_name,emhu_idcard,GID,Emhu_IfStop "
	            +"           from EmHouseUpdate k inner join CoHousingFund m on k.emhu_companyid=m.cohf_houseid ) i on a.gid=i.GID and i.Emhu_IfStop=0 "
	            +" left join emhouse n on a.gid=n.GID  "
	            +" where a.gid="+gid+" and a.emba_state in(1,2,5) order by n.Ownmonth ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class18.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("19")){
		sql="select top 1 coba_company,emba_name,coba_company,h.Esiu_ComputerID,h.esiu_idcard,i.Ownmonth,cast(year(getdate()) as varchar(4)) y, "
	            +" cast(month(getdate()) as varchar(4)) m,cast(day(getdate()) as varchar(4)) d from embase a  "
	            +" inner join coglist b on a.gid=b.gid and b.cgli_stopdate IS null "
	            +" inner join CoOfferList c on b.cgli_coli_id=c.coli_id and coli_name='社会保险服务' "
	            +" inner join CoOffer d on c.coli_coof_id=d.coof_id "
	            +" inner join CoCompact e on d.coof_coco_id=e.coco_id and coco_state>3 and coco_state!=9 and e.coco_shebao!='独立开户' "
	            +" inner join cobase f on a.cid=f.cid "
	            +" inner join login g on f.coba_client=g.log_name and log_inure=1 and g.dep_id=2 "
	            +" inner join EmShebaoUpdate h on a.gid=h.GID and Esiu_IfStop=0  "
	            +" left join emshebao i on a.gid=i.GID "
	            +" where a.gid="+gid+" and a.emba_state in(1,2,5) order by i.Ownmonth ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class19.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else if(cpm.getId().equals("20")){
		sql="select a.gid,a.cid,emba_name,emba_idcard,b.coba_company,c.coab_name,d.sb_s_month,d.sb_e_month,e.gjj_s_month,e.gjj_e_month from embase a "
          +" left join cobase b on a.cid=b.CID "
          +" left join ( "
          +"  select a.gid,e.coab_name from coglist a inner join CoOfferList b on a.cgli_coli_id=b.coli_id "
          +"  inner join CoOffer c on b.coli_coof_id=c.coof_id "
          +"  inner join CoCompact d on c.coof_coco_id=d.coco_id "
          +"  inner join StAgencyBase_view e on d.cabc_id=e.coab_id "
          +"  where coli_name in('社会保险服务','住房公积金服务','人事基础服务费') and gid="+gid+" "
          +"  group by a.gid,e.coab_name "
          +" )c on a.gid=c.gid "
          +" left join "
          +" (select gid,MIN(ownmonth)sb_s_month,MAX(ownmonth)sb_e_month "
          +" from "
          +" ( "
          +" select GID,Ownmonth from emshebao where GID="+gid+" "
          +" union all "
          +" select gid,ownmonth from EmShebaoUpdate where Esiu_IfStop=0 and GID="+gid+" "
          +" )a "
          +" group by GID "
          +" )d on a.gid=d.gid "
          +" left join "
          +" ( "
          +" select gid,MIN(ownmonth)gjj_s_month,MAX(ownmonth)gjj_e_month "
          +" from "
          +" ( "
          +" select GID,Ownmonth from emhouse where GID="+gid+" "
          +" union all "
          +" select gid,ownmonth from EmhouseUpdate where Emhu_IfStop=0 and GID="+gid+" "
          +" )b "
          +" group by GID "
          +" )e on a.gid=e.gid "
          +" where a.gid="+gid+" ";
		 row=bll.isExit(sql);
		 if(row>0){
			 url="../ClassPrint/DocumentInfo_Class21.zul"; 
		 }else{
				Messagebox.show("员工不符合适用范围!", "操作提示", Messagebox.OK, Messagebox.ERROR);
				url="";
		 } 
	}else{
		url="";
	}
	
	if(!url.equals("")){
		Window wd =(Window) Executions.createComponents(url, null, arg);
		wd.doModal();
	}
	
}
public ClassPrintModel getCo() {
	return co;
}

public void setCo(ClassPrintModel co) {
	this.co = co;
}

public List<ClassPrintModel> getList() {
	return list;
}

public void setList(List<ClassPrintModel> list) {
	this.list = list;
}
}
