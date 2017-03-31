package Model;

import java.math.BigDecimal;

public class CoBaseServePromiseModel {
	private Integer cid;
    /// cosp_id
    private Integer  cosp_id;
             /// 合同签订时间:入职前签订\入职后签订
    private String  cosp_coco_signtime;
             /// 合同签订人
    private String  cosp_coco_signname;
             /// 合同签订方式
    private String  cosp_coco_signtype;
             /// 聘用确认书的处理方式

    private String  cosp_coco_hiretype;
             /// 客户要求签订合同的份数
    private Integer  cosp_coco_amount;
             /// 客户要求签订聘用确认书的份数
    private Integer  cosp_coco_hbamount;
             /// 是否有其他文件需要代签

    private String  cosp_coco_ortherfile;
             /// 签订合同需要收集的资料:系统设置内容客服打钩保存（该项目由客服提供需收取明细）
    private String  cosp_coco_datainfo;
             /// 合同签订注意事项
    private String  cosp_coco_careinfo;
             /// 员工入职联系人:联系员工本人、联系客户指定人、联系内部客服（含客户的重要HR)
    private String  cosp_enty_caliname;
             /// 入职资料收取
    private String  cosp_enty_datainfo;
             /// 证件类费用收取方式:个人付、公司付、随付款
    private String  cosp_enty_feepaytype;
             /// 入职资料注意事项

    private String  cosp_enty_careinfo;
             /// 社保公积金交单变更——联系人：员工本人、指定联系人、客服
    private String  cosp_sbhs_caliname;
             /// 社保公积金交单变更——邮寄费用：客户承担、中智承担
    private String  cosp_sbhs_postfee;
             /// 社保公积金交单变更——注意事项
    private String  cosp_sbhs_careinfo;
             /// 就业登记——联系人：员工本人、指定联系人、本人
    private String  cosp_jbrg_caliname;
             /// 就业登记邮寄费用:客户承担\中智承担
    private String  cosp_jbrg_postfee;
             /// 就业登记注意事项
    private String  cosp_jbrg_careinfo;
             /// 各种证件（公积金卡、社保卡、居住证）联系人
    private String  cosp_card_caliname;
             /// 各种证件邮寄费用
    private String  cosp_card_postfee;
             /// 各种证件的注意事项
    private String  cosp_card_careinfo;
             /// 盖章表格递交人
    private String  cosp_bsal_caliname;
             /// 邮寄费用
    private String  cosp_bsal_postfee;
             /// 表格盖章注意事项
    private String  cosp_bsal_careinfo;
             /// 发票邮寄人联系人
    private String  cosp_invo_caliname;
             /// 发票邮寄费用
    private String  cosp_invo_postfee;
             /// 发票寄送注意事项
    private String  cosp_invo_careinfo;
             /// 涉及工资数据问题联系对象
    private String  cosp_page_caliname;
             /// 商保理赔资料联系人
    private String  cosp_dali_caliname;
             /// 伤保理赔资料邮寄费用
    private String  cosp_dali_postfee;
             /// 伤保理赔资料注意事项
    private String  cosp_dali_careinfo;
             /// 档案管理规定(具体到每个员工的规定)
    private String  cosp_file_fix;
             /// 各种证明的联系人
    private String  cosp_prve_caliname;
             /// 各种证明的邮费的负责人
    private String  cosp_pive_postfee;
             /// 各种证明的注意事项
    private String  cosp_prve_careinfo;
             /// 独立户盖章表格——联系人
    private String  cosp_sing_caliname;
             /// 独立户盖章表格——注意事项
    private String  cosp_sing_careinfo;
             /// 社保企业账户所属社保局
    private String  cosp_acnt_sbiess;
             /// 企业社保是否有办理UKEY
    private String  cosp_acnt_sbukey;
             /// 办理各项业务所需资料清单
    private String  cosp_acnt_sbdata;
             /// 公积金归集银行
    private String  cosp_acnt_housebank;
             /// cosp_acnt_houseukey
    private String  cosp_acnt_houseukey;
             /// cosp_acnt_housepaytime
    private String  cosp_acnt_housepaytime;
             /// cosp_acnt_housedata
    private String  cosp_acnt_housedata;
             /// 社保企业编号
    private String  cosp_acnt_sbno;
             /// UKEY到期时间
    private String  cosp_acnt_sbukeyreachtime;
             /// 公积金企业账户账号
    private String  cosp_acnt_houseno;
             /// 公积金缴存比例
    private BigDecimal  cosp_anct_housescale;
             /// 企业公积金UKEY到期时间
    private String  cosp_anct_houseukeyreachtime;
             /// 状态:0、取消；1、在使用
    private Integer  cosp_state;
    		//添加人
    private String cosp_addname;
    		//添加时间
    private String cosp_addtime;
    
    //体检报告联系人
    private String cosp_bcrp_caliname;
    //体检报告邮费
    private String cosp_bcrp_postfee;
    //体检报告所需事项
    private String cosp_bcrp_careinfo;
    //就业登记所属街道
    private String cosp_jbrg_street;
    //就业登记是否有签订责任书
    private String cosp_jbrg_ifdutybook;
    //就业登记未签订责任书是否可以办理就业登记手续
    private String cosp_jbrg_nodutybookok;
    //就业登记办理各项业务所需资料清单
    private String cosp_jbrp_datainfo;
    
    private String cosp_jbrg_data_caliname;
    private String cosp_sbhs_data_caliname;
    private String cosp_card_data_caliname;
    
    private Integer cosp_bcrp_bclinkid;
    private String cosp_bcrp_linknumber;
    
    private Integer cosp_bcrp_caliid;
    private String cosp_bcrp_bclinkname;
    
    private String cosp_bcrp_email;
    
	public Integer getCosp_id() {
		return cosp_id;
	}
	public void setCosp_id(Integer cosp_id) {
		this.cosp_id = cosp_id;
	}
	public String getCosp_coco_signtime() {
		return cosp_coco_signtime;
	}
	public void setCosp_coco_signtime(String cosp_coco_signtime) {
		this.cosp_coco_signtime = cosp_coco_signtime;
	}
	public String getCosp_coco_signname() {
		return cosp_coco_signname;
	}
	public void setCosp_coco_signname(String cosp_coco_signname) {
		this.cosp_coco_signname = cosp_coco_signname;
	}
	public String getCosp_coco_signtype() {
		return cosp_coco_signtype;
	}
	public void setCosp_coco_signtype(String cosp_coco_signtype) {
		this.cosp_coco_signtype = cosp_coco_signtype;
	}
	public String getCosp_coco_hiretype() {
		return cosp_coco_hiretype;
	}
	public void setCosp_coco_hiretype(String cosp_coco_hiretype) {
		this.cosp_coco_hiretype = cosp_coco_hiretype;
	}
	public Integer getCosp_coco_amount() {
		return cosp_coco_amount;
	}
	public void setCosp_coco_amount(Integer cosp_coco_amount) {
		this.cosp_coco_amount = cosp_coco_amount;
	}
	public Integer getCosp_coco_hbamount() {
		return cosp_coco_hbamount;
	}
	public void setCosp_coco_hbamount(Integer cosp_coco_hbamount) {
		this.cosp_coco_hbamount = cosp_coco_hbamount;
	}
	public String getCosp_coco_ortherfile() {
		return cosp_coco_ortherfile;
	}
	public void setCosp_coco_ortherfile(String cosp_coco_ortherfile) {
		this.cosp_coco_ortherfile = cosp_coco_ortherfile;
	}
	public String getCosp_coco_datainfo() {
		return cosp_coco_datainfo;
	}
	public void setCosp_coco_datainfo(String cosp_coco_datainfo) {
		this.cosp_coco_datainfo = cosp_coco_datainfo;
	}
	public String getCosp_coco_careinfo() {
		return cosp_coco_careinfo;
	}
	public void setCosp_coco_careinfo(String cosp_coco_careinfo) {
		this.cosp_coco_careinfo = cosp_coco_careinfo;
	}
	public String getCosp_enty_caliname() {
		return cosp_enty_caliname;
	}
	public void setCosp_enty_caliname(String cosp_enty_caliname) {
		this.cosp_enty_caliname = cosp_enty_caliname;
	}
	public String getCosp_enty_datainfo() {
		return cosp_enty_datainfo;
	}
	public void setCosp_enty_datainfo(String cosp_enty_datainfo) {
		this.cosp_enty_datainfo = cosp_enty_datainfo;
	}
	public String getCosp_enty_feepaytype() {
		return cosp_enty_feepaytype;
	}
	public void setCosp_enty_feepaytype(String cosp_enty_feepaytype) {
		this.cosp_enty_feepaytype = cosp_enty_feepaytype;
	}
	public String getCosp_enty_careinfo() {
		return cosp_enty_careinfo;
	}
	public void setCosp_enty_careinfo(String cosp_enty_careinfo) {
		this.cosp_enty_careinfo = cosp_enty_careinfo;
	}
	public String getCosp_sbhs_caliname() {
		return cosp_sbhs_caliname;
	}
	public void setCosp_sbhs_caliname(String cosp_sbhs_caliname) {
		this.cosp_sbhs_caliname = cosp_sbhs_caliname;
	}
	public String getCosp_sbhs_postfee() {
		return cosp_sbhs_postfee;
	}
	public void setCosp_sbhs_postfee(String cosp_sbhs_postfee) {
		this.cosp_sbhs_postfee = cosp_sbhs_postfee;
	}
	public String getCosp_sbhs_careinfo() {
		return cosp_sbhs_careinfo;
	}
	public void setCosp_sbhs_careinfo(String cosp_sbhs_careinfo) {
		this.cosp_sbhs_careinfo = cosp_sbhs_careinfo;
	}
	public String getCosp_jbrg_caliname() {
		return cosp_jbrg_caliname;
	}
	public void setCosp_jbrg_caliname(String cosp_jbrg_caliname) {
		this.cosp_jbrg_caliname = cosp_jbrg_caliname;
	}
	public String getCosp_jbrg_postfee() {
		return cosp_jbrg_postfee;
	}
	public void setCosp_jbrg_postfee(String cosp_jbrg_postfee) {
		this.cosp_jbrg_postfee = cosp_jbrg_postfee;
	}
	public String getCosp_jbrg_careinfo() {
		return cosp_jbrg_careinfo;
	}
	public void setCosp_jbrg_careinfo(String cosp_jbrg_careinfo) {
		this.cosp_jbrg_careinfo = cosp_jbrg_careinfo;
	}
	public String getCosp_card_caliname() {
		return cosp_card_caliname;
	}
	public void setCosp_card_caliname(String cosp_card_caliname) {
		this.cosp_card_caliname = cosp_card_caliname;
	}
	public String getCosp_card_postfee() {
		return cosp_card_postfee;
	}
	public void setCosp_card_postfee(String cosp_card_postfee) {
		this.cosp_card_postfee = cosp_card_postfee;
	}
	public String getCosp_card_careinfo() {
		return cosp_card_careinfo;
	}
	public void setCosp_card_careinfo(String cosp_card_careinfo) {
		this.cosp_card_careinfo = cosp_card_careinfo;
	}
	public String getCosp_bsal_caliname() {
		return cosp_bsal_caliname;
	}
	public void setCosp_bsal_caliname(String cosp_bsal_caliname) {
		this.cosp_bsal_caliname = cosp_bsal_caliname;
	}
	public String getCosp_bsal_postfee() {
		return cosp_bsal_postfee;
	}
	public void setCosp_bsal_postfee(String cosp_bsal_postfee) {
		this.cosp_bsal_postfee = cosp_bsal_postfee;
	}
	public String getCosp_bsal_careinfo() {
		return cosp_bsal_careinfo;
	}
	public void setCosp_bsal_careinfo(String cosp_bsal_careinfo) {
		this.cosp_bsal_careinfo = cosp_bsal_careinfo;
	}
	public String getCosp_invo_caliname() {
		return cosp_invo_caliname;
	}
	public void setCosp_invo_caliname(String cosp_invo_caliname) {
		this.cosp_invo_caliname = cosp_invo_caliname;
	}
	public String getCosp_invo_postfee() {
		return cosp_invo_postfee;
	}
	public void setCosp_invo_postfee(String cosp_invo_postfee) {
		this.cosp_invo_postfee = cosp_invo_postfee;
	}
	public String getCosp_invo_careinfo() {
		return cosp_invo_careinfo;
	}
	public void setCosp_invo_careinfo(String cosp_invo_careinfo) {
		this.cosp_invo_careinfo = cosp_invo_careinfo;
	}
	public String getCosp_page_caliname() {
		return cosp_page_caliname;
	}
	public void setCosp_page_caliname(String cosp_page_caliname) {
		this.cosp_page_caliname = cosp_page_caliname;
	}
	public String getCosp_dali_caliname() {
		return cosp_dali_caliname;
	}
	public void setCosp_dali_caliname(String cosp_dali_caliname) {
		this.cosp_dali_caliname = cosp_dali_caliname;
	}
	public String getCosp_dali_postfee() {
		return cosp_dali_postfee;
	}
	public void setCosp_dali_postfee(String cosp_dali_postfee) {
		this.cosp_dali_postfee = cosp_dali_postfee;
	}
	public String getCosp_dali_careinfo() {
		return cosp_dali_careinfo;
	}
	public void setCosp_dali_careinfo(String cosp_dali_careinfo) {
		this.cosp_dali_careinfo = cosp_dali_careinfo;
	}
	public String getCosp_file_fix() {
		return cosp_file_fix;
	}
	public void setCosp_file_fix(String cosp_file_fix) {
		this.cosp_file_fix = cosp_file_fix;
	}
	public String getCosp_prve_caliname() {
		return cosp_prve_caliname;
	}
	public void setCosp_prve_caliname(String cosp_prve_caliname) {
		this.cosp_prve_caliname = cosp_prve_caliname;
	}
	public String getCosp_pive_postfee() {
		return cosp_pive_postfee;
	}
	public void setCosp_pive_postfee(String cosp_pive_postfee) {
		this.cosp_pive_postfee = cosp_pive_postfee;
	}
	public String getCosp_prve_careinfo() {
		return cosp_prve_careinfo;
	}
	public void setCosp_prve_careinfo(String cosp_prve_careinfo) {
		this.cosp_prve_careinfo = cosp_prve_careinfo;
	}
	public String getCosp_sing_caliname() {
		return cosp_sing_caliname;
	}
	public void setCosp_sing_caliname(String cosp_sing_caliname) {
		this.cosp_sing_caliname = cosp_sing_caliname;
	}
	public String getCosp_sing_careinfo() {
		return cosp_sing_careinfo;
	}
	public void setCosp_sing_careinfo(String cosp_sing_careinfo) {
		this.cosp_sing_careinfo = cosp_sing_careinfo;
	}
	public String getCosp_acnt_sbiess() {
		return cosp_acnt_sbiess;
	}
	public void setCosp_acnt_sbiess(String cosp_acnt_sbiess) {
		this.cosp_acnt_sbiess = cosp_acnt_sbiess;
	}
	public String getCosp_acnt_sbukey() {
		return cosp_acnt_sbukey;
	}
	public void setCosp_acnt_sbukey(String cosp_acnt_sbukey) {
		this.cosp_acnt_sbukey = cosp_acnt_sbukey;
	}
	public String getCosp_acnt_sbdata() {
		return cosp_acnt_sbdata;
	}
	public void setCosp_acnt_sbdata(String cosp_acnt_sbdata) {
		this.cosp_acnt_sbdata = cosp_acnt_sbdata;
	}
	public String getCosp_acnt_housebank() {
		return cosp_acnt_housebank;
	}
	public void setCosp_acnt_housebank(String cosp_acnt_housebank) {
		this.cosp_acnt_housebank = cosp_acnt_housebank;
	}
	public String getCosp_acnt_houseukey() {
		return cosp_acnt_houseukey;
	}
	public void setCosp_acnt_houseukey(String cosp_acnt_houseukey) {
		this.cosp_acnt_houseukey = cosp_acnt_houseukey;
	}
	public String getCosp_acnt_housepaytime() {
		return cosp_acnt_housepaytime;
	}
	public void setCosp_acnt_housepaytime(String cosp_acnt_housepaytime) {
		this.cosp_acnt_housepaytime = cosp_acnt_housepaytime;
	}
	public String getCosp_acnt_housedata() {
		return cosp_acnt_housedata;
	}
	public void setCosp_acnt_housedata(String cosp_acnt_housedata) {
		this.cosp_acnt_housedata = cosp_acnt_housedata;
	}
	public String getCosp_acnt_sbno() {
		return cosp_acnt_sbno;
	}
	public void setCosp_acnt_sbno(String cosp_acnt_sbno) {
		this.cosp_acnt_sbno = cosp_acnt_sbno;
	}
	public String getCosp_acnt_sbukeyreachtime() {
		return cosp_acnt_sbukeyreachtime;
	}
	public void setCosp_acnt_sbukeyreachtime(String cosp_acnt_sbukeyreachtime) {
		this.cosp_acnt_sbukeyreachtime = cosp_acnt_sbukeyreachtime;
	}
	public String getCosp_acnt_houseno() {
		return cosp_acnt_houseno;
	}
	public void setCosp_acnt_houseno(String cosp_acnt_houseno) {
		this.cosp_acnt_houseno = cosp_acnt_houseno;
	}
	public BigDecimal getCosp_anct_housescale() {
		return cosp_anct_housescale;
	}
	public void setCosp_anct_housescale(BigDecimal cosp_anct_housescale) {
		this.cosp_anct_housescale = cosp_anct_housescale;
	}
	public String getCosp_anct_houseukeyreachtime() {
		return cosp_anct_houseukeyreachtime;
	}
	public void setCosp_anct_houseukeyreachtime(String cosp_anct_houseukeyreachtime) {
		this.cosp_anct_houseukeyreachtime = cosp_anct_houseukeyreachtime;
	}
	public Integer getCosp_state() {
		return cosp_state;
	}
	public void setCosp_state(Integer cosp_state) {
		this.cosp_state = cosp_state;
	}
	public String getCosp_addname() {
		return cosp_addname;
	}
	public void setCosp_addname(String cosp_addname) {
		this.cosp_addname = cosp_addname;
	}
	public String getCosp_addtime() {
		return cosp_addtime;
	}
	public void setCosp_addtime(String cosp_addtime) {
		this.cosp_addtime = cosp_addtime;
	}
	public String getCosp_bcrp_caliname() {
		return cosp_bcrp_caliname;
	}
	public void setCosp_bcrp_caliname(String cosp_bcrp_caliname) {
		this.cosp_bcrp_caliname = cosp_bcrp_caliname;
	}
	public String getCosp_bcrp_postfee() {
		return cosp_bcrp_postfee;
	}
	public void setCosp_bcrp_postfee(String cosp_bcrp_postfee) {
		this.cosp_bcrp_postfee = cosp_bcrp_postfee;
	}
	public String getCosp_bcrp_careinfo() {
		return cosp_bcrp_careinfo;
	}
	public void setCosp_bcrp_careinfo(String cosp_bcrp_careinfo) {
		this.cosp_bcrp_careinfo = cosp_bcrp_careinfo;
	}
	public String getCosp_jbrg_street() {
		return cosp_jbrg_street;
	}
	public void setCosp_jbrg_street(String cosp_jbrg_street) {
		this.cosp_jbrg_street = cosp_jbrg_street;
	}
	public String getCosp_jbrg_ifdutybook() {
		return cosp_jbrg_ifdutybook;
	}
	public void setCosp_jbrg_ifdutybook(String cosp_jbrg_ifdutybook) {
		this.cosp_jbrg_ifdutybook = cosp_jbrg_ifdutybook;
	}
	public String getCosp_jbrg_nodutybookok() {
		return cosp_jbrg_nodutybookok;
	}
	public void setCosp_jbrg_nodutybookok(String cosp_jbrg_nodutybookok) {
		this.cosp_jbrg_nodutybookok = cosp_jbrg_nodutybookok;
	}
	public String getCosp_jbrp_datainfo() {
		return cosp_jbrp_datainfo;
	}
	public void setCosp_jbrp_datainfo(String cosp_jbrp_datainfo) {
		this.cosp_jbrp_datainfo = cosp_jbrp_datainfo;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCosp_jbrg_data_caliname() {
		return cosp_jbrg_data_caliname;
	}
	public void setCosp_jbrg_data_caliname(String cosp_jbrg_data_caliname) {
		this.cosp_jbrg_data_caliname = cosp_jbrg_data_caliname;
	}
	public String getCosp_sbhs_data_caliname() {
		return cosp_sbhs_data_caliname;
	}
	public void setCosp_sbhs_data_caliname(String cosp_sbhs_data_caliname) {
		this.cosp_sbhs_data_caliname = cosp_sbhs_data_caliname;
	}
	public String getCosp_card_data_caliname() {
		return cosp_card_data_caliname;
	}
	public void setCosp_card_data_caliname(String cosp_card_data_caliname) {
		this.cosp_card_data_caliname = cosp_card_data_caliname;
	}
	
	public String getCosp_bcrp_linknumber() {
		return cosp_bcrp_linknumber;
	}
	public void setCosp_bcrp_linknumber(String cosp_bcrp_linknumber) {
		this.cosp_bcrp_linknumber = cosp_bcrp_linknumber;
	}
	public Integer getCosp_bcrp_bclinkid() {
		return cosp_bcrp_bclinkid;
	}
	public void setCosp_bcrp_bclinkid(Integer cosp_bcrp_bclinkid) {
		this.cosp_bcrp_bclinkid = cosp_bcrp_bclinkid;
	}
	public Integer getCosp_bcrp_caliid() {
		return cosp_bcrp_caliid;
	}
	public void setCosp_bcrp_caliid(Integer cosp_bcrp_caliid) {
		this.cosp_bcrp_caliid = cosp_bcrp_caliid;
	}
	public String getCosp_bcrp_bclinkname() {
		return cosp_bcrp_bclinkname;
	}
	public void setCosp_bcrp_bclinkname(String cosp_bcrp_bclinkname) {
		this.cosp_bcrp_bclinkname = cosp_bcrp_bclinkname;
	}
	public String getCosp_bcrp_email() {
		return cosp_bcrp_email;
	}
	public void setCosp_bcrp_email(String cosp_bcrp_email) {
		this.cosp_bcrp_email = cosp_bcrp_email;
	}
}
