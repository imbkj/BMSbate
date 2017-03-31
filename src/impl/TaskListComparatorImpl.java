package impl;

import java.io.Serializable;
import java.util.Comparator;

import org.zkoss.zul.GroupComparator;

import Model.TaskListModel;
import Model.WfTaskListInfoModel;
public class TaskListComparatorImpl   implements
Comparator<WfTaskListInfoModel>, GroupComparator<WfTaskListInfoModel>,
Serializable{

	@Override
	public int compareGroup(WfTaskListInfoModel arg0, WfTaskListInfoModel arg1) {
		// TODO Auto-generated method stub
		if (arg0.getTacl_name().equals(arg1.getTacl_name())) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int compare(WfTaskListInfoModel arg0, WfTaskListInfoModel arg1) {
		// TODO Auto-generated method stub
		return arg0.getTacl_name().compareTo(arg1.getTacl_name().toString());
	}

}
