package bll.EmCommissionOut;
import impl.WorkflowCore.WfOperateImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import bll.EmSalary.EmSalary_Editimpl;
import bll.EmSalary.EmSalary_SalaryOperateImpl;

import Model.CoAgencyBaseCityRelViewModel;
import Model.CoAgencyBaseModel;
import Model.EmCommissionYearChangemModel;
import Model.EmCommissionyearchangetitleModel;
import Model.EmbaseModel;
import dal.CoAgency.CoAgencyBaseDal;
import dal.CoAgency.WtAgency_DisCitySelDal;
import dal.EmCommissionOut.EmCommissionyearchangetitleDal;
import dal.EmSocialinPaper.EmSocialinPaperListDal;

public class EmCommissionyearchangetitleBll {

	/**
	 * @param args
	 */

	EmCommissionyearchangetitleDal ecdal =new EmCommissionyearchangetitleDal();
	
	//添加年调总表数据
	public String[] changetitleadd(ArrayList<EmCommissionyearchangetitleModel> modellist,String username)
	{
		String[] message = new String[2];
		String cityname = "";
		EmCommissionyearchangetitleOperateImpl impl = new EmCommissionyearchangetitleOperateImpl();
		cityname=ecdal.getcityname(modellist.get(0).getCoab_id()).getCoab_city();
		WfOperateService wf = new WfOperateImpl(impl);
		
		try {
			
			int taba_id = ecdal.addTaskBatch(username, "委托基数采集");
			Object[] obj = { "1", modellist, username,taba_id };
			message=wf.AddTaskToNext(obj, "委托外地基数采集", cityname+"基数采集", 65, username, "委托基数采集", taba_id, "");
		} catch (Exception e) {
			message[0] = "2";
			message[1] = "已存在此地区的采集";
		}
		System.out.println(message[1]);
		return message;
		
	//	return ecdal.changetitleadd(model);
		
	}
	//更新年调总表到期时间
	public int edittitleadd(EmCommissionyearchangetitleModel model) throws SQLException
	{
		
		return ecdal.changetitleupdate(model);
	}
	
	
	
	//检查是否有添加过未完结的年调
	public String[] checkdata(ArrayList<EmCommissionyearchangetitleModel> alist) throws Exception
	{
		String[] msg =new String[2];
		msg=ecdal.checkdata(alist);
		return msg;
		
	}
	
	
	public List<CoAgencyBaseModel> getCoAgencyBaseAll(String city) {
		//CoAgencyBaseDal dal = new CoAgencyBaseDal();
		WtAgency_DisCitySelDal dal = new WtAgency_DisCitySelDal();
		//String str = checkExceptCity(city);
		List<CoAgencyBaseModel> BaseList = dal.getCoAgBaseListByCity(city);
		return BaseList;
	}

	//根据城市查询委托机构
	public CoAgencyBaseCityRelViewModel getcabc_id(String city,String wtjgname)
	{
		
		return ecdal.getcabc_id(city, wtjgname);
		
	}
	
	//传入城市名称，拼接sql语句
	private String checkExceptCity(String city){
		StringBuilder str = new StringBuilder();
		if(!"".equals(city)){
			str.append(" and coab_id  in(select cabc_coab_id from CoAgencyBaseCityRel ");
			str.append("where cabc_ppc_id=(select id from PubProCity where name='");
			str.append(city);
			str.append("') and cabc_state=1) ");
		}
		return str.toString();
	}
	
	//查询年调总表列表--任务单
	public List<EmCommissionyearchangetitleModel> getemcommtlist(int state ,int da_id)
	{
		List<EmCommissionyearchangetitleModel> list = new ArrayList<EmCommissionyearchangetitleModel>();
		list = ecdal.getemcommtlist(state,da_id);
		return list;
	}
	//查询年调总表列表--sh 带任务单ID 关联政策信息
	public List<EmCommissionyearchangetitleModel> getemcommtlistsh(int i)
	{
		List<EmCommissionyearchangetitleModel> list = new ArrayList<EmCommissionyearchangetitleModel>();
		list = ecdal.getemcommtlistsh(i);
		return list;
	}
	 
	
	
	//查询年调总表列表--总业务
	public List<EmCommissionyearchangetitleModel> getemcommtlistall(int state,String city,String coab_name)
	{
		List<EmCommissionyearchangetitleModel> list = new ArrayList<EmCommissionyearchangetitleModel>();
		list = ecdal.getemcommtlistall(state,city,coab_name);
		return list;
	}
	
	//查询年调总表列表--总业务
		public List<EmCommissionyearchangetitleModel> getemcommtlistforid(int ecyt_id)
		{
			List<EmCommissionyearchangetitleModel> list = new ArrayList<EmCommissionyearchangetitleModel>();
			list = ecdal.getemcommtlistforid(ecyt_id);
			return list;
		}
		
	
	//撤销未导入
	public boolean unlockedit(int ecyc_id,int state)
	{
		return ecdal.unlockedit(ecyc_id,state);
		
	}
	
	//提交年调数据
	public boolean updateemcomm(String ecyc_idcont,int state) throws Exception
	{
		return ecdal.updateemcomm(ecyc_idcont,state);
		
	}
	
	//退回年调数据
	public boolean rebacknt(List<EmCommissionYearChangemModel> ml,int state) throws Exception
	{
		boolean r = false;
		 
		for (EmCommissionYearChangemModel m:ml)
		{
			
			if (ecdal.rebacknt(state,m.getEcyc_id()))
			{
				r=  true;
			}
		
			
		}
	
		return r;

	}
	
	
	//更新年调数据状态
	public boolean  adutingemcommlist(String idcountstr,String username)
	{
		
		
		int x=1;
		
		try {
			x=ecdal.adutingemcommlist(idcountstr,username);
			
			
		} catch (Exception e) {
			
		}
		if (x==0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
		
	}
	//任务单审核检查
	public int checktasktonext(int d_id,int state)
	{
		return ecdal.checktasktonext(d_id, state);
	}
	
	//任务单前进
	public String[] passtonext(String id,int t_id,String username,int d_id)
	{
		String[] message;
		Object[] obj = {id,t_id,username,d_id};
		EmCommissionyearchangetitleOperateImpl impl = new EmCommissionyearchangetitleOperateImpl();
		WfOperateService wf = new WfOperateImpl(impl);
		message=wf.PassToNext(obj, t_id, username, "", d_id, "");
		 return  message;
	}
	
	//年调列表查询--任务单
		public  List<EmCommissionYearChangemModel> searchembaselist(Object... objs) {
		//String sql = checkEmbaseSearchstr(str);
		return  ecdal.getembaList(objs);
	}
		
		//年调列表查询--后道
		public  List<EmCommissionYearChangemModel> searchembaselisthd(Object... objs) {
		//String sql = checkEmbaseSearchstr(str);
		return  ecdal.getembaListhd(objs);
	}
		
		//年调列表查询--后道
		public  List<EmCommissionYearChangemModel> searchembaselisthdl(String strwhere) {
		//String sql = checkEmbaseSearchstr(str);
		return  ecdal.getembaListhd(strwhere);
	}
		
		
		//年调列表查询--后道
		public  List<EmCommissionYearChangemModel> getembaListhdzd() {
		//String sql = checkEmbaseSearchstr(str);
		return  ecdal.getembaListhdzd( );
	}
	
	
		
		
		
	

}
