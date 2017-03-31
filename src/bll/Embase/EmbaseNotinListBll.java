package bll.Embase;

import impl.CheckStringImpl;

import java.util.List;

import service.CheckStringService;

import Model.EmbaseModel;
import Model.EmbaseNotInModel;
import dal.Embase.EmbaseNotdal;

public class EmbaseNotinListBll {
	
	private static EmbaseNotdal emnotindal = new EmbaseNotdal();

	
	// 按条件查询员工列表
		public  List<EmbaseNotInModel> searchembaselist(String str) {
			String sql = checkEmbaseSearchstr(str);
			return  emnotindal.getembanotinList(sql);
		}
		

		// 按条件查询员工列表
			public  List<EmbaseNotInModel> searchembaselistst(String str) {
				//String sql = checkEmbaseSearchstr(str);
				return  emnotindal.getembanotinList(str);
			}
			
			// 按条件查询员工列表
			public  List<EmbaseNotInModel> getembanotinListall(int str) {
				//String sql = checkEmbaseSearchstr(str);
				return  emnotindal.getembanotinListall(str);
			}
			
		
		
			
			
		// 按条件查询员工列表(综合性查询)
		public static String[] searchembaselists() {
			String[] dictionary=emnotindal.getembanotinList();
			return dictionary;
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
							if (Long.parseLong(str)>9999 & Long.parseLong(str)<999999)
							{
								sql.append(" and (gid=");
								sql.append(str);
								sql.append(")");
							}
							else if (Long.parseLong(str)<9999)
							{
								sql.append(" and (cid=");
								sql.append(str);
								sql.append(")");
							}
							
							else if(Long.parseLong(str)<Long.parseLong("30000000000"))
							{
								sql.append(" and (emba_idcard=");
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
						else if (strletter.length==18 || strletter.length==15)
						{
							sql.append(" and (emba_idcard=");
							sql.append(str);
							sql.append(")");
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
						sql.append("%')");
					}	
				}
				else
				{
					sql.append(" and 1=1");
				}
				
		       return sql.toString();
			}
}
