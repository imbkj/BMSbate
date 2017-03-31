package Util;

import impl.CheckStringImpl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import service.CheckStringService;

import Conn.dbconn;

public class CobaseUtil {
	
	//获取客服
	public static List<String> getClientList()
	{
		List<String> list=new ArrayList<String>();
		dbconn db=new dbconn();
		ResultSet rs=null;
		String sql="select distinct(coba_client) coba_client from CoBase where coba_client is not null and coba_client<>'' order by coba_client";
		try {
			rs=db.GRS(sql);
			list.add("");
			while (rs.next()) {
				list.add(rs.getString("coba_client"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//获取公司CID、公司简称、公司全称、客服、客服经理、部门经理
	//参数: （int ,string） int:cid,string 简称或全称
	public int getcid(String strwhere)
	{
		CheckStringService ch = new CheckStringImpl();
		dbconn db=new dbconn();
		ResultSet rs=null;
		StringBuilder sql =new StringBuilder();
		
		if (!strwhere.equals("") & strwhere!=null )
		{
		
			if(ch.isNum(strwhere))
			{
				sql.append("select * from cobase where cid="+strwhere+"");
			}
			else
			{
				sql.append("select * from cobase where coba_name='"+strwhere+"' or coba_shortname='"+strwhere+"'");
			}
		}
		
		return 0;
		
	}
	
	
	//由CID获取客服、客户经理
}
