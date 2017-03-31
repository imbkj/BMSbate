package Controller.EmCommissionOut;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.bind.Binder;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import Model.EmCommissionOutimportModel;
import bll.EmCommissionOut.EmCommissionOutimportBll;

public class EmCommissionOutimport_UpdateController {
	
	private EmCommissionOutimportModel m = (EmCommissionOutimportModel) Executions.getCurrent().getArg().get("model");
	
    private EmCommissionOutimportBll bll =new EmCommissionOutimportBll();
  
    private String ecou_filestate="";//是否保管档案
    
    Date ecou_compact_f= null;//合同起始时间
	Date ecou_compact_l= null;//合同结束时间
    Date ecou_yldate= null; //养老起始
	Date ecou_yliaodate=null; //医疗起始
	Date ecou_dbdate= null;//大病起始
	Date ecou_syudate= null; //生育起始
	Date ecou_gsdate= null; //工伤起始
	Date ecou_sydate= null;//失业保险起始
	Date ecou_zfdate= null; //住房起始
	Date  ecou_cbjdate= null;//残保金起始时间
	Date ecou_fwfdate= null;//服务费起始时间
	Date ecou_bczfdate= null;//补充公积金起始	
	public EmCommissionOutimport_UpdateController() {
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		    try{
		      if(getM().getEcou_compact_f()!=null&&!getM().getEcou_compact_f().equals("")){
		        ecou_compact_f= df.parse(getM().getEcou_compact_f());//合同起始时间
		      }
		      if(getM().getEcou_compact_l()!=null&&!getM().getEcou_compact_l().equals("")){
			    ecou_compact_l= df.parse(getM().getEcou_compact_l());//合同结束时间
		      }
			  if(getM().getEcou_yldate()!=null&&!getM().getEcou_yldate().equals("")){
		        ecou_yldate= df.parse(getM().getEcou_yldate()); //养老起始
			  }
		      if(getM().getEcou_yliaodate()!=null&&!getM().getEcou_yliaodate().equals("")){
			    ecou_yliaodate=df.parse(getM().getEcou_yliaodate()); //医疗起始
		      }
			  if(getM().getEcou_dbdate()!=null&&!getM().getEcou_dbdate().equals("")){
			    ecou_dbdate= df.parse(getM().getEcou_dbdate());//大病起始
			  }
			  if(getM().getEcou_syudate()!=null&&!getM().getEcou_syudate().equals("")){
			    ecou_syudate= df.parse(getM().getEcou_syudate()); //生育起始
			  }
			  if(getM().getEcou_gsdate()!=null&&!getM().getEcou_gsdate().equals("")){
			    ecou_gsdate= df.parse(getM().getEcou_gsdate()); //工伤起始
			  }
			  if(getM().getEcou_sydate()!=null&&!getM().getEcou_sydate().equals("")){
			    ecou_sydate= df.parse(getM().getEcou_sydate());//失业保险起始
			  }
			  if(getM().getEcou_zfdate()!=null&&!getM().getEcou_zfdate().equals("")){
			    ecou_zfdate= df.parse(getM().getEcou_zfdate()); //住房起始
			  }
			  if(getM().getEcou_cbjdate()!=null&&!getM().getEcou_cbjdate().equals("")){
			    ecou_cbjdate= df.parse(getM().getEcou_cbjdate());//残保金起始时间
			  }
			  if(getM().getEcou_fwfdate()!=null&&!getM().getEcou_fwfdate().equals("")){
			    ecou_fwfdate= df.parse(getM().getEcou_fwfdate());//服务费起始时间
			  }
			  if(getM().getEcou_bczfdate()!=null&&!getM().getEcou_bczfdate().equals("")){
			    ecou_bczfdate = df.parse(getM().getEcou_bczfdate());
			  }
			  if(getM().getEcou_filestate()!=null){
				  if(getM().getEcou_filestate()==1){
					  ecou_filestate="是";
				  }else if(getM().getEcou_filestate()==0){
					  ecou_filestate="否";
				  }
			  }
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		    
		
	}
	//更新未审核维护记录
	@Command("Charge")
	public void Charge(@BindingParam("ecou_name") String ecou_name,@BindingParam("ecou_idcard") String ecou_idcard,@BindingParam("ecou_ecos_id") String ecou_ecos_id,
				    @BindingParam("ecou_soin_id") String ecou_soin_id,@BindingParam("ecou_mobile") String ecou_mobile,@BindingParam("ecou_filestate") String ecou_filestate,
				    @BindingParam("ecou_compact_l") String ecou_compact_l,@BindingParam("ecou_compact_f") String ecou_compact_f,@BindingParam("ecou_com_phone") String ecou_com_phone,
				    @BindingParam("ecou_sb_base") String ecou_sb_base,@BindingParam("ecou_house_base") String ecou_house_base,@BindingParam("ecou_salary") String ecou_salary,
				    @BindingParam("ecou_client") String ecou_client,@BindingParam("ecou_yldate") String ecou_yldate,@BindingParam("ecou_yliaodate") String ecou_yliaodate,
				    @BindingParam("ecou_dbdate") String ecou_dbdate,@BindingParam("ecou_syudate") String ecou_syudate,@BindingParam("ecou_gsdate") String ecou_gsdate,
				    @BindingParam("ecou_sydate") String ecou_sydate,@BindingParam("ecou_zfdate") String ecou_zfdate,@BindingParam("ecou_zfcp") String ecou_zfcp,
				    @BindingParam("ecou_zfop") String ecou_zfop,@BindingParam("ecou_bczfdate") String ecou_bczfdate,@BindingParam("ecou_bczfcp") String ecou_bczfcp,
				    @BindingParam("ecou_bczfop") String ecou_bczfop,@BindingParam("ecou_cbjdate") String ecou_cbjdate,@BindingParam("ecou_fwfdate") String ecou_fwfdate,
				    @BindingParam("ecou_remark") String ecou_remark,@BindingParam("win") Window win,@ContextParam(ContextType.VIEW) Component view) {
		    EmCommissionOutimportModel mr =new EmCommissionOutimportModel();
		           mr.setEcou_name(ecou_name);
		           mr.setEcou_idcard(ecou_idcard);
		           if(ecou_ecos_id!=null&&!ecou_ecos_id.equals("")&&isInteger(ecou_ecos_id)){
		        	   mr.setEcou_ecos_id(Integer.valueOf(ecou_ecos_id));
		           }else{
		        	   mr.setEcou_ecos_id(0);
		           }
		           if(ecou_soin_id!=null&&!ecou_soin_id.equals("")&&isInteger(ecou_soin_id)){
		        	   mr.setEcou_soin_id(Integer.valueOf(ecou_soin_id));
		           }else{
		        	   mr.setEcou_soin_id(0);
		           }
		           mr.setEcou_mobile(ecou_mobile);
		           if(ecou_filestate!=null&&!ecou_filestate.equals("")){
		        	   if(ecou_filestate=="是"){
		        		   mr.setEcou_filestate(1);
		        	   }else if(ecou_filestate=="否"){
		        		   mr.setEcou_filestate(0);
		        	   }
		           }else{
		        	   mr.setEcou_filestate(0);
		           }
		           mr.setEcou_compact_l(ecou_compact_l);
		           mr.setEcou_compact_f(ecou_compact_f);
		           mr.setEcou_com_phone(ecou_com_phone);
		           if (ecou_sb_base != null && !ecou_sb_base.equals("")&&isNumeric(ecou_sb_base)) {
						mr.setEcou_sb_base(new BigDecimal(ecou_sb_base).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else{
						mr.setEcou_sb_base(new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP));
					}
		           if (ecou_house_base != null && !ecou_house_base.equals("")&&isNumeric(ecou_house_base)) {
						mr.setEcou_house_base(new BigDecimal(ecou_house_base).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else{
						mr.setEcou_house_base(new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP));
					}
		           if (ecou_salary != null && !ecou_salary.equals("")&&isNumeric(ecou_salary)) {
						mr.setEcou_salary(new BigDecimal(ecou_salary).setScale(2, BigDecimal.ROUND_HALF_UP));
					}else{
						mr.setEcou_salary(new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP));
					}
		           mr.setEcou_client(ecou_client);
		           mr.setEcou_yldate(ecou_yldate);
		           mr.setEcou_yliaodate(ecou_yliaodate);
		           mr.setEcou_dbdate(ecou_dbdate);
		           mr.setEcou_syudate(ecou_syudate);
		           mr.setEcou_gsdate(ecou_gsdate);
		           mr.setEcou_sydate(ecou_sydate);
		           mr.setEcou_zfdate(ecou_zfdate);
		           mr.setEcou_zfcp(ecou_zfcp);
		           mr.setEcou_zfop(ecou_zfop);
		           mr.setEcou_bczfdate(ecou_bczfdate);
		           mr.setEcou_bczfcp(ecou_bczfcp);
		           mr.setEcou_bczfop(ecou_bczfop);
		           mr.setEcou_cbjdate(ecou_cbjdate);
		           mr.setEcou_fwfdate(ecou_fwfdate);
		           mr.setEcou_remark(ecou_remark);
		           mr.setEcou_id(getM().getEcou_id());
		           mr.setEcou_state(0);
		    int a= bll.updateEntrustInfo(mr);//更新
		    if (a>0) {
				Binder bind = (Binder) view.getParent().getAttribute("binder");
				bind.postCommand("refresh", null);
				Messagebox.show("修改成功！", "操作提示", Messagebox.OK,Messagebox.INFORMATION);
				win.detach();
			} else {
				Messagebox.show("修改失败！", "操作提示", Messagebox.OK,
						Messagebox.NONE);
			}
		}
	public EmCommissionOutimportModel getM() {
		return m;
	}

	public void setM(EmCommissionOutimportModel m) {
		this.m = m;
	}

	public Date getEcou_compact_f() {
		return ecou_compact_f;
	}

	public void setEcou_compact_f(Date ecou_compact_f) {
		this.ecou_compact_f = ecou_compact_f;
	}

	public Date getEcou_compact_l() {
		return ecou_compact_l;
	}

	public void setEcou_compact_l(Date ecou_compact_l) {
		this.ecou_compact_l = ecou_compact_l;
	}

	public Date getEcou_yldate() {
		return ecou_yldate;
	}

	public void setEcou_yldate(Date ecou_yldate) {
		this.ecou_yldate = ecou_yldate;
	}

	public Date getEcou_yliaodate() {
		return ecou_yliaodate;
	}

	public void setEcou_yliaodate(Date ecou_yliaodate) {
		this.ecou_yliaodate = ecou_yliaodate;
	}

	public Date getEcou_dbdate() {
		return ecou_dbdate;
	}

	public void setEcou_dbdate(Date ecou_dbdate) {
		this.ecou_dbdate = ecou_dbdate;
	}

	public Date getEcou_syudate() {
		return ecou_syudate;
	}

	public void setEcou_syudate(Date ecou_syudate) {
		this.ecou_syudate = ecou_syudate;
	}

	public Date getEcou_gsdate() {
		return ecou_gsdate;
	}

	public void setEcou_gsdate(Date ecou_gsdate) {
		this.ecou_gsdate = ecou_gsdate;
	}

	public Date getEcou_sydate() {
		return ecou_sydate;
	}

	public void setEcou_sydate(Date ecou_sydate) {
		this.ecou_sydate = ecou_sydate;
	}

	public Date getEcou_zfdate() {
		return ecou_zfdate;
	}

	public void setEcou_zfdate(Date ecou_zfdate) {
		this.ecou_zfdate = ecou_zfdate;
	}

	public Date getEcou_cbjdate() {
		return ecou_cbjdate;
	}

	public void setEcou_cbjdate(Date ecou_cbjdate) {
		this.ecou_cbjdate = ecou_cbjdate;
	}

	public Date getEcou_fwfdate() {
		return ecou_fwfdate;
	}

	public void setEcou_fwfdate(Date ecou_fwfdate) {
		this.ecou_fwfdate = ecou_fwfdate;
	}

	public Date getEcou_bczfdate() {
		return ecou_bczfdate;
	}

	public void setEcou_bczfdate(Date ecou_bczfdate) {
		this.ecou_bczfdate = ecou_bczfdate;
	}

	public String getEcou_filestate() {
		return ecou_filestate;
	}

	public void setEcou_filestate(String ecou_filestate) {
		this.ecou_filestate = ecou_filestate;
	}
	
	
	 /**
     * 判断是否为数值
     * 支持格式："33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
     * @param str String
     * @return boolean
     */
   public static boolean isNumeric(String str) {
       int begin = 0;
       boolean once = true;
       if (str == null || str.trim().equals("")) {
           return false;
       }
       str = str.trim();
       if (str.startsWith("+") || str.startsWith("-")) {
           if (str.length() == 1) {
               // "+" "-"
               return false;
           }
           begin = 1;
       }
       for (int i = begin; i < str.length(); i++) {
           if (!Character.isDigit(str.charAt(i))) {
               if (str.charAt(i) == '.' && once) {
                   // '.' can only once
                   once = false;
               }
               else {
                   return false;
               }
           }
       }
       if (str.length() == (begin + 1) && !once) {
           // "." "+." "-."
           return false;
       }
       return true;
   }

   /**
     * 判断是否为整型
     * 支持 格式 ："33" "003300" "+33" " -0000 "
     * @param str String
     * @return boolean
     */
   public static boolean isInteger(String str) {
       int begin = 0;
       if (str == null || str.trim().equals("")) {
           return false;
       }
       str = str.trim();
       if (str.startsWith("+") || str.startsWith("-")) {
           if (str.length() == 1) {
               // "+" "-"
               return false;
           }
           begin = 1;
       }
       for (int i = begin; i < str.length(); i++) {
           if (!Character.isDigit(str.charAt(i))) {
               return false;
           }
       }
       return true;
   }

}
