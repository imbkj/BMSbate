package bll.Embase;

import java.util.ArrayList;
import java.util.List;

import dal.Embase.Embase_ReEntryDal;

import Model.CancelDimissionModel;
import Model.EmArchiveDatumModel;
import Model.EmBaseCompactChangeModel;
import Model.EmComInsuranceChangeModel;
import Model.EmCommissionOutChangeModel;
import Model.EmHouseChangeModel;
import Model.EmSheBaoChangeModel;
import Model.EmbaseModel;

public class Embase_ReEntryBll {
	private Embase_ReEntryDal dal=new Embase_ReEntryDal();
	//查询员工信息
	public List<EmbaseModel> getEmbaseList(String str)
	{
		return dal.getEmbaseList(str);
	}
	
	//根据emba_id查询员工信息
	public EmbaseModel getEmbaseInfoByEmbaId(Integer emba_id)
	{
		EmbaseModel model=new EmbaseModel();
		List<EmbaseModel> list=getEmbaseList(" and emba_id="+emba_id);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
	
	//根据gid查询员工信息
	public EmbaseModel getEmbaseInfoByGid(Integer gid)
	{
		EmbaseModel model=new EmbaseModel();
		List<EmbaseModel> list=getEmbaseList(" and gid="+gid);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
	
	//查询员工社保停缴信息
	public List<EmSheBaoChangeModel> getSheBaoInfo(String str)
	{
		return dal.getSheBaoInfo(str);
	}
	
	//查询员工公积金停缴信息
	public List<EmHouseChangeModel> getEmhouseInfo(String str)
	{
		return dal.getEmhouseInfo(str);
	}
	
	//查询员工档案调出信息
	public List<EmArchiveDatumModel> getEmArchiveDatumInfo(String str)
	{
		return dal.getEmArchiveDatumInfo(str);
	}
	
	//查询商保停缴数据
	public List<EmComInsuranceChangeModel> getEmComInsuranceChangeInfo(String str)
	{
		return dal.getEmComInsuranceChangeInfo(str);
	}
	
	//查询委托外地停缴数据
	public List<EmCommissionOutChangeModel> getEmCommissionOutChangeInfo(String str)
	{
		return dal.getEmCommissionOutChangeInfo(str);
	}
	
	//查询劳动合同终止数据
	public List<EmBaseCompactChangeModel> getEmBaseCompactChangeInfo(String str)
	{
		return dal.getEmBaseCompactChangeInfo(str);
	}
	
	//社会保险服务、住房公积金服务、劳动合同、商业保险、委托外地、档案保管
	//把个业务合并成一个List
	public List<CancelDimissionModel> getList(Integer gid)
	{
		List<CancelDimissionModel> list=new ArrayList<CancelDimissionModel>();
		//社会保险服务
		List<EmSheBaoChangeModel> sblist=getSheBaoInfo(" and gid="+gid);
		if(sblist.size()>0)
		{
			for(EmSheBaoChangeModel m:sblist)
			{
				CancelDimissionModel sbm=new CancelDimissionModel();
				sbm.setTypeid(1);
				sbm.setId(m.getId());
				sbm.setGid(m.getGid());
				sbm.setCid(m.getCid());
				sbm.setInfoname("社会保险服务");
				sbm.setChange(m.getEmsc_change());
				sbm.setAddname(m.getEmsc_addname());
				sbm.setAddtime(m.getEmsc_addtime());
				sbm.setTablename("EmShebaoChange");
				sbm.setType("sb"+m.getEmsc_change());
				if(m.getEmsc_ifdeclare().equals("0"))
				{
					sbm.setStatename("未申报");
					sbm.setStateid(0);
				}
				else if(m.getEmsc_ifdeclare().equals("1"))
				{
					sbm.setStatename("已申报");
				}
				else if(m.getEmsc_ifdeclare().equals("2"))
				{
					sbm.setStatename("申报中");
				}
				else if(m.getEmsc_ifdeclare().equals("3"))
				{
					sbm.setStatename("退回");
					sbm.setStateid(0);
				}
				else
				{
					sbm.setStatename("待确定");
					sbm.setStateid(0);
				}
				sbm.setReadstate(m.getReadstate());
				list.add(sbm);
			}
		}
		
		//住房公积金服务
		List<EmHouseChangeModel> hslist=getEmhouseInfo(" and gid="+gid);
		if(hslist.size()>0)
		{
			for(EmHouseChangeModel m:hslist)
			{
				CancelDimissionModel hsm=new CancelDimissionModel();
				hsm.setTypeid(2);
				hsm.setId(m.getEmhc_id());
				hsm.setGid(m.getGid());
				hsm.setCid(m.getCid());
				hsm.setInfoname("住房公积金服务");
				hsm.setChange(m.getEmhc_change());
				hsm.setAddname(m.getEmhc_addname());
				hsm.setAddtime(m.getEmhc_addtime());
				hsm.setTablename("EmHouseChange");
				hsm.setType("hs"+m.getEmhc_change());
				if(m.getEmhc_ifdeclare().equals(0))
				{
					hsm.setStatename("未申报");
					hsm.setStateid(0);
				}
				else if(m.getEmhc_ifdeclare().equals(1))
				{
					hsm.setStatename("已申报");
				}
				else if(m.getEmhc_ifdeclare().equals(2))
				{
					hsm.setStatename("申报中");
				}
				else if(m.getEmhc_ifdeclare().equals(3))
				{
					hsm.setStatename("退回");
					hsm.setStateid(0);
				}
				else
				{
					hsm.setStatename("待确定");
					hsm.setStateid(0);
				}
				hsm.setReadstate(m.getReadstate());
				list.add(hsm);
			}
		}
		
		//劳动合同
		List<EmBaseCompactChangeModel> cmlist=getEmBaseCompactChangeInfo(" and gid="+gid);
		if(cmlist.size()>0)
		{
			for(EmBaseCompactChangeModel m:cmlist)
			{
				CancelDimissionModel hsm=new CancelDimissionModel();
				hsm.setTypeid(3);
				hsm.setId(m.getEbcc_id());
				hsm.setGid(m.getGid());
				hsm.setCid(m.getCid());
				hsm.setInfoname("劳动合同服务");
				hsm.setChange(m.getEbcc_change());
				hsm.setAddname(m.getEbcc_addname());
				hsm.setAddtime(m.getEbcc_addtime());
				hsm.setStatename(m.getEbcc_change());
				hsm.setStateid(0);
				hsm.setTablename("EmBaseCompactChange");
				hsm.setType("ld"+m.getEbcc_change());
				hsm.setReadstate(m.getReadstate());
				list.add(hsm);
			}
		}
		//商业保险
		List<EmComInsuranceChangeModel> sylist=getEmComInsuranceChangeInfo(" and gid="+gid);
		if(sylist.size()>0)
		{
			for(EmComInsuranceChangeModel m:sylist)
			{
				CancelDimissionModel hsm=new CancelDimissionModel();
				hsm.setTypeid(4);
				hsm.setId(m.getEcic_id());
				hsm.setGid(m.getGid());
				hsm.setCid(m.getCid());
				hsm.setInfoname("商业保险服务");
				hsm.setChange("停缴");
				hsm.setAddname(m.getEcic_addname());
				hsm.setAddtime(m.getEcic_addtime());
				if(m.getEcic_state2().equals("0"))
				{
					hsm.setStatename("未申报");
					hsm.setStateid(0);
				}
				else if(m.getEcic_state2().equals("1"))
				{
					hsm.setStatename("已申报");
				}
				else
				{
					hsm.setStatename("退回");
					hsm.setStateid(0);
				}
				hsm.setTablename("EmComInsuranceChange");
				hsm.setType("sybx"+hsm.getChange());
				hsm.setReadstate(m.getReadstate());
				list.add(hsm);
			}
		}
		//委托外地
		List<EmCommissionOutChangeModel> wtlist=getEmCommissionOutChangeInfo(" and gid="+gid);
		if(wtlist.size()>0)
		{
			for(EmCommissionOutChangeModel m:wtlist)
			{
				CancelDimissionModel hsm=new CancelDimissionModel();
				hsm.setTypeid(5);
				hsm.setId(m.getEcoc_id());
				hsm.setGid(m.getGid());
				hsm.setCid(m.getCid());
				hsm.setInfoname("委托外地");
				hsm.setChange(m.getEcoc_addtype());
				hsm.setAddname(m.getEcoc_addname());
				hsm.setAddtime(m.getAddtime());
				if(m.getEcoc_state().equals(1))
				{
					hsm.setStatename("未确认");
					hsm.setStateid(0);
				}
				else if(m.getEcoc_state().equals(2))
				{
					hsm.setStatename("第一次确认");
				}
				else if(m.getEcoc_state().equals(3))
				{
					hsm.setStatename("第二次确认");
				}
				else if(m.getEcoc_state().equals(4))
				{
					hsm.setStatename("退回");
					hsm.setStateid(0);
				}
				else if(m.getEcoc_state().equals(5))
				{
					hsm.setStatename("撤销");
					hsm.setStateid(0);
				}
				hsm.setTablename("EmCommissionOutChange");
				hsm.setType("wt"+hsm.getStatename());
				hsm.setReadstate(m.getReadstate());
				list.add(hsm);
			}
		}
		//档案保管
		List<EmArchiveDatumModel> dalist=getEmArchiveDatumInfo(" and gid="+gid);
		if(dalist.size()>0)
		{
			for(EmArchiveDatumModel m:dalist)
			{
				CancelDimissionModel hsm=new CancelDimissionModel();
				hsm.setTypeid(6);
				hsm.setId(m.getEada_id());
				hsm.setGid(m.getGid());
				hsm.setCid(m.getCid());
				hsm.setInfoname("档案保管");
				hsm.setChange("档案调出");
				hsm.setAddname(m.getEada_addname());
				hsm.setAddtime(m.getEada_addtime());
				//System.out.println("final="+m.getEada_final());
				if(m.getEada_final().equals("0"))
				{
					hsm.setStatename("待确定");
					hsm.setStateid(0);
				}
				else if(m.getEada_final().equals("1"))
				{
					hsm.setStatename("待处理");
					hsm.setStateid(0);
				}
				else if(m.getEada_final().equals("2"))
				{
					hsm.setStatename("处理中");
				}
				else if(m.getEada_final().equals("3"))
				{
					hsm.setStatename("已完成");
				}
				else
				{
					hsm.setStatename("退回");
					hsm.setStateid(0);
				}
				hsm.setTablename("EmArchiveDatum");
				hsm.setType("dn"+hsm.getStatename());
				hsm.setReadstate(m.getReadstate());
				list.add(hsm);
			}
		}
		return list;
	}
	
	
	//查询员工社保停缴信息
	public EmSheBaoChangeModel getSheBaoInfoModel(Integer id)
	{
		EmSheBaoChangeModel model=new EmSheBaoChangeModel();
		List<EmSheBaoChangeModel> list=getSheBaoInfo(" and id="+id);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
		
	//查询员工公积金停缴信息
	public EmHouseChangeModel getEmhouseInfoModel(Integer id)
	{
		EmHouseChangeModel model=new EmHouseChangeModel();
		List<EmHouseChangeModel> list=getEmhouseInfo(" and emhc_id="+id);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
		
	//查询员工档案调出信息
	public EmArchiveDatumModel getEmArchiveDatumInfoModel(Integer id)
	{
		EmArchiveDatumModel model=new EmArchiveDatumModel();
		List<EmArchiveDatumModel> list=getEmArchiveDatumInfo(" and eada_id="+id);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
		
	//查询商保停缴数据
	public EmComInsuranceChangeModel getEmComInsuranceChangeInfoModel(Integer id)
	{
		EmComInsuranceChangeModel model=new EmComInsuranceChangeModel();
		List<EmComInsuranceChangeModel> list=getEmComInsuranceChangeInfo(" and ecic_id="+id);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
		
	//查询委托外地停缴数据
	public EmCommissionOutChangeModel getEmCommissionOutChangeInfoModel(Integer id)
	{
		EmCommissionOutChangeModel model=new EmCommissionOutChangeModel();
		List<EmCommissionOutChangeModel> list=getEmCommissionOutChangeInfo(" and ecoc_id="+id);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
		
	//查询劳动合同终止数据
	public EmBaseCompactChangeModel getEmBaseCompactChangeInfoModel(Integer id)
	{
		EmBaseCompactChangeModel model=new EmBaseCompactChangeModel();
		List<EmBaseCompactChangeModel> list=getEmBaseCompactChangeInfo(" and ebcc_id="+id);
		if(list.size()>0)
		{
			model=list.get(0);
		}
		return model;
	}
}
