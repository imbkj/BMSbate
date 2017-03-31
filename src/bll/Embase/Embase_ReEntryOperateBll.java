package bll.Embase;

import impl.WorkflowCore.WfOperateImpl;
import service.WorkflowCore.WfBusinessService;
import service.WorkflowCore.WfOperateService;
import Util.UserInfo;
import dal.Embase.Embase_ReEntryOperateDal;

public class Embase_ReEntryOperateBll {
	private Embase_ReEntryOperateDal dal = new Embase_ReEntryOperateDal();

	public Integer EmBase_ReEntry(Integer gid, String addname) {
		return dal.EmBase_ReEntry(gid, addname);
	}

	// 终止任务单
	public String[] endTaskList(String id, String tablename, Integer tarpid)
			throws Exception {
		String[] str = new String[5];
		if (tarpid != null) {
			Object[] obj = { tablename, id };
			WfBusinessService wfbs = new Embase_ReEntryImpl();
			WfOperateService wfs = new WfOperateImpl(wfbs);
			str = wfs.OverTask(obj, tarpid, "系统", "");
		} else {
			str[0] = "0";
		}
		return str;
	}

	// 删除商保change的数据
	public Integer deletesahngbao(Integer id) {
		return dal.deletesahngbao(id);
	}

	// 删除劳动合同change的数据
	public Integer deletesEmCompact(Integer id) {
		return dal.deletesEmCompact(id);
	}

	// 根据emshebaochange的id把社保在册表的数据更新
	public Integer updateEmshebao(Integer id) {
		return dal.updateEmshebao(id);
	}

	// 根据emhousechange的id把公积金在册表的数据更新
	public Integer updateEmHouse(Integer id) {
		return dal.updateEmHouse(id);
	}

	// 根据emhousechange的id删除数据
	public Integer deleteEmHouse(Integer id) {
		return dal.deleteEmHouse(id);
	}
}
