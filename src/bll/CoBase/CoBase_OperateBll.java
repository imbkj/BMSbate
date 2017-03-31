/**
 * @Classname CoBase_OperateBll
 * @ClassInfo 公司基本信息数据库修改处理类
 * @author 林少斌、陈耀家
 * @Date 2013-11-27
 */
package bll.CoBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dal.CoBase.CoBase_OperateDal;
import dal.CoBase.CoBase_SelectDal;
import dal.Embase.Embasedal;
import Model.CoBaseModel;
import Model.EmbaseModel;

public class CoBase_OperateBll {
	CoBase_OperateDal cobaDal = new CoBase_OperateDal();

	//查询公司信息
	public CoBaseModel search(int cid){
		CoBase_SelectDal dal = new CoBase_SelectDal();
		return dal.getCobaseByCid(cid);
	}
	
	// 修改公司注册信息
	public String[] updateCoBaseReg(int cid, String username,
			String coba_regagent, String coba_companymanager,
			String coba_address, String coba_certificate, String coba_bregid,
			String coba_regexpires, String coba_regtime, String coba_reglimit,
			String coba_organcode, String coba_orregtime,
			String coba_organdeadline, String coba_ntid, String coba_ntregtime,
			String coba_ntlimit, String coba_ntbank, String coba_ntaccount,
			String coba_ntdeadline, String coba_ltregid, String coba_ltregtime,
			String coba_ltlimit, String coba_ltstate, String coba_ltid,
			String coba_ltdeadline, String coba_ltbank, String coba_ltaccount,
			String coba_gtstate, String coba_gtbank, String coba_gtacc,
			String coba_gtto, String coba_gtdeadline, int coba_gzautoemail,
			int coba_gzautoemaildays, int coba_gzemailtype,
			String coba_ufclass, String coba_ufid, String coba_ufid2,
			String coba_sihospital, String coba_sihosphone,
			String coba_sihosadd, String coba_regremark) {
		String[] message = new String[2];
		int result = 0;
		try {
			CoBaseModel m = new CoBaseModel();
			m.setCid(cid);
			m.setCoba_regagent(coba_regagent);
			m.setCoba_companymanager(coba_companymanager);
			m.setCoba_address(coba_address);
			m.setCoba_certificate(coba_certificate);
			m.setCoba_bregid(coba_bregid);
			m.setCoba_regexpires(coba_regexpires);
			m.setCoba_regtime(coba_regtime);
			m.setCoba_reglimit(coba_reglimit);
			m.setCoba_organcode(coba_organcode);
			m.setCoba_orregtime(coba_orregtime);
			m.setCoba_organdeadline(coba_organdeadline);
			m.setCoba_ntid(coba_ntid);
			m.setCoba_ntregtime(coba_ntregtime);
			m.setCoba_ntlimit(coba_ntlimit);
			m.setCoba_ntbank(coba_ntbank);
			m.setCoba_ntaccount(coba_ntaccount);
			m.setCoba_ntdeadline(coba_ntdeadline);
			m.setCoba_ltregid(coba_ltregid);
			m.setCoba_ltregtime(coba_ltregtime);
			m.setCoba_ltlimit(coba_ltlimit);
			m.setCoba_ltstate(coba_ltstate);
			m.setCoba_ltid(coba_ltid);
			m.setCoba_ltdeadline(coba_ltdeadline);
			m.setCoba_ltbank(coba_ltbank);
			m.setCoba_ltaccount(coba_ltaccount);
			m.setCoba_gtstate(coba_gtstate);
			m.setCoba_gtbank(coba_gtbank);
			m.setCoba_gtacc(coba_gtacc);
			m.setCoba_gtto(coba_gtto);
			m.setCoba_gtdeadline(coba_gtdeadline);
			m.setCoba_gzautoemail(coba_gzautoemail);
			m.setCoba_gzautoemaildays(coba_gzautoemaildays);
			m.setCoba_gzemailtype(coba_gzemailtype);
			m.setCoba_ufclass(coba_ufclass);
			m.setCoba_ufid(coba_ufid);
			m.setCoba_ufid2(coba_ufid2);
			m.setCoba_sihospital(coba_sihospital);
			m.setCoba_sihosphone(coba_sihosphone);
			m.setCoba_sihosadd(coba_sihosadd);
			m.setCoba_regremark(coba_regremark);

			result = cobaDal.updateCoBaseReg(username, m);
			if (result == 0) {
				message[0] = "1";
				message[1] = "公司注册信息修改成功!";
			} else {
				message[0] = "0";
				message[1] = "公司注册信息修改失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司注册信息修改，操作出错。";
		}
		return message;
	}

	// 修改公司财务信息
	public String[] updateCoBaseCS(int cid, String username,
			String coba_emfi_paydate, String coba_emfi_backdate,
			String coba_gz_paydate, String coba_emailgz_paydate,
			String coba_papergz_paydate, String coba_emfics_deldate,
			String coba_emfics_paydate, String coba_emfics_backdate,
			String coba_invoicerule) {

		String[] message = new String[2];
		int result = 0;
		try {
			CoBaseModel m = new CoBaseModel();
			m.setCid(cid);
			m.setCoba_emfi_paydate(coba_emfi_paydate);
			m.setCoba_emfi_backdate(coba_emfi_backdate);
			m.setCoba_gz_paydate(coba_gz_paydate);
			m.setCoba_emailgz_paydate(coba_emailgz_paydate);
			m.setCoba_papergz_paydate(coba_papergz_paydate);
			m.setCoba_emfics_deldate(coba_emfics_deldate);
			m.setCoba_emfics_paydate(coba_emfics_paydate);
			m.setCoba_emfics_backdate(coba_emfics_backdate);
			m.setCoba_invoicerule(coba_invoicerule);

			result = cobaDal.updateCoBaseCS(username, m);
			if (result == 0) {
				message[0] = "1";
				message[1] = "公司财务信息修改成功!";
			} else {
				message[0] = "0";
				message[1] = "公司财务信息修改失败!";
			}
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "公司财务信息修改，操作出错。";
		}
		return message;
	}

	// 新增公司信息
	public int addCoBaseInfo(CoBaseModel m) {
		return cobaDal.addCoBaseInfo(m);
	}

	// 修改公司信息
	public int updateCoBaseInfo(CoBaseModel m) {
		return cobaDal.updateCoBaseInfo(m);
	}
	
	//公司解约
	public Integer stopCobase(Integer cid, String stopdate, String stopreason,
			String stopRemark, String modname){
		return cobaDal.stopCobase(cid, stopdate, stopreason, stopRemark, modname);
	}
	//查询是否还有在职员工
	public boolean embaseInfo(Integer cid){
		Embasedal edal = new Embasedal();
		EmbaseModel m = new EmbaseModel();
		m.setCid(cid);
		m.setEmba_state(1);
		List<EmbaseModel> list = edal.getEmbaseInfoByPama(m);
		if (list.size()>0) {
			return true;
		}else {
			return false;
		}
	}

	// Date类型转换String
	public String DatetoSting(Date d) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String Date = sdf.format(d);
		return Date;
	}
}
