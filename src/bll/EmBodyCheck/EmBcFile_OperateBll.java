package bll.EmBodyCheck;

import Model.EmbodyCheckFileModel;
import dal.EmBodyCheck.EmBcFile_OperateDal;

public class EmBcFile_OperateBll {
	private EmBcFile_OperateDal dal=new EmBcFile_OperateDal();
	
	// 体检文件新增
	public Integer EmbodyCheckFileAdd(EmbodyCheckFileModel m) {
		return dal.EmbodyCheckFileAdd(m);
	}
	
	// 体检文件修改
		public Integer EmbodyCheckFileEdit(EmbodyCheckFileModel m) {
			return dal.EmbodyCheckFileEdit(m);
		}

}
