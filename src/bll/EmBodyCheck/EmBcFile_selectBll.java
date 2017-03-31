package bll.EmBodyCheck;

import java.util.List;

import Model.EmbodyCheckFileModel;
import dal.EmBodyCheck.EmBcFile_selectDal;

public class EmBcFile_selectBll {
	private EmBcFile_selectDal dal=new EmBcFile_selectDal();
	//查询体检文件信息
	public List<EmbodyCheckFileModel> getEmbodyCheckFileList(String str)
	{
		return dal.getEmbodyCheckFileList(str);
	}
}
