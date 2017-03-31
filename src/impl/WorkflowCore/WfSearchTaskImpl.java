/**
 * @Classname WfCoreSearchTaskService
 * @ClassInfo 查询任务单实现类（继承WfCoreSearchTaskService接口）
 * @author 李文洁
 * @Date 2013-10-24
 */
package impl.WorkflowCore;

import java.util.ArrayList;
import java.util.List;

import Model.WfTaskListInfoModel;
import Util.RegexUtil;
import service.WorkflowCore.WfSearchTaskService;
import dal.WorkflowCore.WfCoreSearchTaskDal;

public class WfSearchTaskImpl implements WfSearchTaskService {

	/**
	 * @Methodname:根据用户名查找可操作的任务单
	 * 
	 * @input: username:用户名;
	 * 
	 * @output: List<WfTaskListInfoModel>
	 **/
	@Override
	public List<WfTaskListInfoModel> SearchCanOpTaskByUser(String username) {
		WfCoreSearchTaskDal dal = new WfCoreSearchTaskDal();
		List<WfTaskListInfoModel> taskList = dal
				.SearchCanOpTaskByUser(username);
		return taskList;
	}
	
	@Override
	public List<WfTaskListInfoModel> SearchCanOpTaskByUser(String username,Integer taclId) {
		WfCoreSearchTaskDal dal = new WfCoreSearchTaskDal();
		List<WfTaskListInfoModel> taskList = dal
				.SearchCanOpTaskByUser(username,taclId);
		return taskList;
	}

	/**
	 * @Methodname:检索任务单
	 * 
	 * @input: key:检索字段;value:检索内容；
	 * 
	 * @output: List<WfTaskListInfoModel>
	 * 
	 * @remark:单字段模糊搜索
	 **/
	@Override
	public List<WfTaskListInfoModel> SearchOpTaskByCon(
			List<WfTaskListInfoModel> list, String key, String value) {
		List<WfTaskListInfoModel> l = new ArrayList<WfTaskListInfoModel>();
		try {
			for (WfTaskListInfoModel m : list) {
				if (RegexUtil.isExists(value, m.getSearchConMap().get(key))) {
					l.add(m);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	// 测试
	public static void main(String arg[]) {
		WfSearchTaskImpl impl = new WfSearchTaskImpl();
		List<WfTaskListInfoModel> l = impl.SearchCanOpTaskByUser("李文洁");
		System.out.println(l.size());
		System.out.println(impl.SearchOpTaskByCon(l, "cid", "1130").size());
		System.out.println(impl.SearchOpTaskByCon(l, "cid", "11").size());
		System.out.println(impl.SearchOpTaskByCon(l, "gid", "11301").size());
	}

}
