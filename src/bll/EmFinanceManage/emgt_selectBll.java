package bll.EmFinanceManage;

import impl.CheckStringImpl;

import java.util.List;

import service.CheckStringService;

import Model.EmGatheringModel;
import Model.EmbaseModel;
import Util.UserInfo;
import dal.EmFinanceManage.emgt_selectDal;

public class emgt_selectBll {
	private emgt_selectDal dal=new emgt_selectDal();
	
	public List<EmGatheringModel> getEmGatheringList(String str){
		return dal.getEmGatheringList(str);
	}
	
	// 按条件查询员工管理列表
	public  List<EmbaseModel> searchembaseeditlist(String str) {
		String sql = checkEmbaseSearchstr(str);
		//sql=sql+" and CID in ( select a.cid from DataPopedom where log_id="+UserInfo.getUserid()+" and  Dat_edited=1 )";
		return  dal.getembaList(sql);	
	}
	
	// 按条件查询员工管理列表
	public  List<EmbaseModel> getEmbaseList(String str) {
			return  dal.getembaList(str);	
		}
	
	// 员工列表查询拼接SQL
		private static  String checkEmbaseSearchstr(String str) {
				CheckStringService ch = new CheckStringImpl();
				StringBuilder sql = new StringBuilder();
				
				if (!str.equals("") & str!=null )
				{
				
					if(ch.isNum(str))
					{
						//判断是电话号码还是gid
						if (Long.parseLong(str)>1000000000)
						{
							sql.append(" and (emba_mobile='");
							sql.append(str);
							sql.append("')");
						}
						else
						{
							if (Long.parseLong(str)>9999)
							{
								sql.append(" and (gid=");
								sql.append(str);
								sql.append(")");
							}
							else
							{
								sql.append(" and (a.cid=");
								sql.append(str);
								sql.append(")");
							}
						}
				
					}
					else if(ch.isLetter(str.replaceAll(" ", "")))
					{
						String[] strletter;
						strletter=str.split(" ");
						if (strletter.length>1) 
						{
							sql.append(" and (emba_spell like '%");
							sql.append(strletter[0]);
							sql.append("%' and coba_namespell like '%");
							sql.append(strletter[1]);
							sql.append("%')");
						}
						else
						{
							sql.append(" and (emba_spell like '%");
							sql.append(strletter[0]);
							sql.append("%')");
						}
					}
					else
					{
						sql.append(" and (emba_name like '%");
						sql.append(str);
						sql.append("%' or coba_shortname like '"+str+"')");
					}	
				}
				else
				{
					sql.append(" and 1=1");
				}
		       return sql.toString();
	}
}
