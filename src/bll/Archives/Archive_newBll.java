package bll.Archives;

import impl.WorkflowCore.WfOperateImpl;

import java.util.List;
import org.zkoss.zul.ListModelList;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import dal.PubCodeConversionDal;
import dal.Archives.EmArchiveDatumDal;
import dal.SystemControl.UserListDal;
import Model.EmArchiveDatumModel;
import Model.EmArchiveModel;
import Model.EmArchiveSetupModel;
import Model.PubCodeConversionModel;
import Util.FormatObjectValueUtil;

public class Archive_newBll {

	// 获取档案调入信息
	public List<EmArchiveDatumModel> getInfo(Integer id) {
		List<EmArchiveDatumModel> list = new ListModelList<>();
		EmArchiveDatumDal dal = new EmArchiveDatumDal();
		try {
			list = dal.getInfoById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	//获取EmArchiveSetupModel列表
	public List<EmArchiveSetupModel> getdaInfo() {
		PubCodeConversionDal dal = new PubCodeConversionDal();
		return dal.getdaInfo();
	}
	
	// 获取列表
	public List<PubCodeConversionModel> getInfoList(Integer id, String name) {
		List<PubCodeConversionModel> list = new ListModelList<>();
		PubCodeConversionDal dal = new PubCodeConversionDal();
		try {
			list = dal.getListInfo(id, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 获取部门信息
	public String getDept(String name) {
		String str = "";
		UserListDal dal = new UserListDal();
		try {
			str = dal.getDept(name);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return str;
	}

	//添加档案信息
	public Integer addfile(EmArchiveModel eam, Integer id, Integer taprId,
			String username) {
		Integer i = 0;
		WfBusinessService wfbs = new Archive_newImpl();
		WfOperateService wfs = new WfOperateImpl(wfbs);
		Integer cid=0;
		if(eam.getCid()!=null)
		{
			cid=eam.getCid();
		}
		Object[] obj = { id, eam.getEmar_idcard(),
				eam.getEmar_censusregister(), eam.getEmar_crbelongs(),
				eam.getEmar_party(), eam.getEmar_partydate(),
				eam.getEmar_partybelongs(), eam.getEmar_degree(),
				eam.getEmar_school(), eam.getEmar_specialty(),
				eam.getEmar_gradate(),
				eam.getEmar_marrystate(), eam.getEmar_fertilitystate(),
				eam.getEmar_workdate(), eam.getEmar_caste(),
				eam.getEmar_casteassessdate(),
				eam.getEmar_peoplefoldmode(), eam.getEmar_inciicdate(),
				eam.getEmar_link(), eam.getEmar_specialdata(),
				eam.getEmar_address(), eam.getEmar_archiveclass(),
				eam.getEmar_wtmode(), eam.getEmar_archiveplace(),
				eam.getEmar_archivesource(), eam.getEmar_archivefoldmode(),
				eam.getEmar_archivefolddate(), eam.getEmar_archivefoldreason(),
				eam.getEmar_transferorderid(), eam.getEmar_surrogatecardid(),
				eam.getEmar_promisesdate(), eam.getEmar_prodate(),
				eam.getEmar_surrogateid(), eam.getEmar_firstdeadline(),
				eam.getEmar_continuedeadline(), eam.getEmar_colhj(), username,
				eam.getEmar_szresume(), eam.getEmar_remark(),
				eam.getEmar_archivetype(), eam.getEmar_archivearea() };

		String[] str = wfs.PassToNext(obj, taprId, username, "", cid, "");

		if (str[0].equals("1"))
			i = Integer.valueOf(str[2]);
		return i;
	}
	
	//使用身份证号码和档案类型来查询员工之前是否有档案
		public EmArchiveModel getEmarchive(String idcard,String archiveType)
		{
			EmArchiveDatumDal dal = new EmArchiveDatumDal();
			return dal.getEmarchive(idcard, archiveType);
		}

}
