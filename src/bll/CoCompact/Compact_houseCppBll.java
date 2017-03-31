package bll.CoCompact;

import java.util.List;

import org.openxmlformats.schemas.officeDocument.x2006.math.OMathDocument;
import org.zkoss.zul.ListModelList;

import dal.LoginDal;
import dal.PubEmailDal;
import dal.CoCompact.CoCompactCppAduitDal;
import dal.CoCompact.CoCompact_OperateDal;

import Model.CoCompactCppAduitModel;
import Model.LoginModel;
import Model.PubEmailModel;
import Model.loginroleModel;
import Util.DateStringChange;
import Util.MailSenderInfo;
import Util.SimpleMailSender;

public class Compact_houseCppBll {

	public List<CoCompactCppAduitModel> search(CoCompactCppAduitModel cm) {
		List<CoCompactCppAduitModel> list = new ListModelList<>();
		CoCompactCppAduitDal dal = new CoCompactCppAduitDal();
		list = dal.getlist(cm);
		return list;
	}

	public Integer dataAdd(CoCompactCppAduitModel cm) {
		CoCompactCppAduitDal dal = new CoCompactCppAduitDal();
		Integer i = dal.add(cm);
		return i;
	}

	public Integer dataMod(CoCompactCppAduitModel cm) {
		CoCompactCppAduitDal dal = new CoCompactCppAduitDal();
		Integer i = dal.mod(cm, cm.getCoca_id());
		return i;
	}

	public Integer sendMail(CoCompactCppAduitModel m) {
		LoginDal ld = new LoginDal();
		List<LoginModel> list = new ListModelList<>();
		list = ld.getemail(m.getReceName());
		String send = list.get(0).getLog_email();
		list = ld.getemail(m.getSendName());
		String rece = list.get(0).getLog_email();

		PubEmailDal dal = new PubEmailDal();
		PubEmailModel pm = new PubEmailModel();
		pm.setPuem_title(m.getCoba_company() + "公积金合同比例修改被退回");
		pm.setPuem_content(m.getCoba_company() + "公积金合同比例修改被退回,退回原因:"
				+ m.getCoca_backreason());
		pm.setPuem_email(send);
		pm.setPuem_replyto(rece);
		pm.setPuem_sendtime(DateStringChange
				.Datestringnow("yyyy-MM-dd HH:mm:ss"));
		pm.setPuem_addname(m.getCoca_modname());
		pm.setPuem_ifHTML(0);
		Integer i = dal.EmailAdd(pm);
		return i;
	}

	public Integer updateCompact(CoCompactCppAduitModel cm) {
		CoCompact_OperateDal dal = new CoCompact_OperateDal();
		if (cm.getCoca_cpp() != null && !cm.getCoca_cpp().equals("浮动比例")) {
			cm.setCoca_cpp(cm.getCoca_cpp().substring(0,
					cm.getCoca_cpp().length() - 1));
			cm.setCoca_cpp(String.valueOf(Double.valueOf(cm.getCoca_cpp()) / 100));
		}
		Integer i = dal.updateCoCompact(cm, cm.getCoca_coco_id());
		return i;
	}
}
