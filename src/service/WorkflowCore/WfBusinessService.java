/**
 * @Classname WfBusinessService
 * @ClassInfo 工作流业务接口
 * @author 李文洁
 * @Date 2013-10-24
 */
package service.WorkflowCore;

public interface WfBusinessService {
	/**
	 * @Methodname:业务操作方法
	 * 
	 * @input: obj:业务参数
	 * 
	 * @output: String[0]:0失败1成功2出错
	 * ；String[1]:提示信息；String[2]:业务表ID
	 * ;String[3]：业务主表的表名；String[4]:操作内容；
	 */
	public String[] Operate(Object[] obj);

	/**
	 * @Methodname:退回业务操作方法
	 * 
	 * @input: obj:业务参数
	 * 
	 * @output: String[0]:0失败1成功2出错
	 * ；String[1]:提示信息；String[2]:业务表ID
	 * ;String[3]：业务主表的表名；String[4]:操作内容；
	 */
	public String[] ReturnOperate(Object[] obj);
	
	/**
	 * @Methodname:跳过业务操作方法
	 * 
	 * @input: obj:业务参数
	 * 
	 * @output: String[0]:0失败1成功2出错
	 * ；String[1]:提示信息；String[2]:业务表ID;String[3]:业务表名(可选填，主要用于业务中心修改业务办理状态)
	 */
	public String[] SkipOperate(Object[] obj);
	
	/**
	 * @Methodname:终止业务操作方法
	 * 
	 * @input: obj:业务参数
	 * 
	 * @output: String[0]:0失败1成功2出错
	 * ；String[1]:提示信息；String[2]:业务表ID
	 * ;String[3]：业务主表的表名；String[4]:操作内容；
	 */
	public String[] StopOperate(Object[] obj);
	
	/**
	 * @Methodname:更新业务表的流程ID
	 * 
	 * @input: dataId:业务表主键ID;tapr_id:流程ID;state:状态 0：父任务更新的数据；1：子任务更新的数据；
	 * 
	 * @output: 成功或失败 true or false
	 */
	public Boolean UpdateTaprid(int dataId, int tapr_id,int state);

	/**
	 * @Methodname:用于任务单操作出错业务数据的处理
	 * 
	 * @remark:如新建任务失败时需删除掉业务数据
	 * 
	 * @input: dataId:业务表主键ID
	 * 
	 * @output:
	 */
	public Boolean ErrOperate(int dataId);
}
