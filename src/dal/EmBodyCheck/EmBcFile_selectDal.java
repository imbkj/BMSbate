package dal.EmBodyCheck;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Conn.dbconn;
import Model.EmbodyCheckFileModel;

public class EmBcFile_selectDal {
	
	//查询体检文件信息
	public List<EmbodyCheckFileModel> getEmbodyCheckFileList(String str)
	{
		List<EmbodyCheckFileModel> list=new ArrayList<EmbodyCheckFileModel>();
		ResultSet rs=null;
		dbconn db=new dbconn();
		try{
			String sql="select file_id,ebcs_hospital,ebsa_address," +
					"convert(varchar(10),file_addtime,120) as file_addtime," +
					"a.* from EmbodyCheckFile a inner join EmBcSetup b on a.file_ebcs_id=b.ebcs_id " +
					"left join EmBcSetupAddress c on a.file_ebsa_id=c.ebsa_id where 1=1"+str;
			System.out.println(sql);
			list = db.find(sql, EmbodyCheckFileModel.class, null);
		}
		catch(Exception e)
		{
			
		}
		return list;
	}

}
