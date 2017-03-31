package Model;

import java.util.Comparator;
import java.util.List;


import org.zkoss.zul.GroupsModelArray;
import Model.WfTaskListInfoModel;


public class TaskListGroupModel extends
GroupsModelArray<WfTaskListInfoModel, String, String, Object> {
	
	private static final String footerString = "共  %d 条";
	private boolean showGroup;
	
	public TaskListGroupModel(List<WfTaskListInfoModel> data,
			Comparator<WfTaskListInfoModel> cmpr, boolean showGroup) {
		super(data.toArray(new WfTaskListInfoModel[0]), cmpr);

		this.showGroup = showGroup;
	}
	
	protected String createGroupHead(WfTaskListInfoModel[] groupdata, int index, int col) { 
        String ret = ""; 
        if (groupdata.length > 0) { 
            ret = groupdata[0].getTacl_name(); 
        } 
  
        return ret; 
    } 
  
    protected String createGroupFoot(WfTaskListInfoModel[] groupdata, int index, int col) { 
        return String.format(footerString, groupdata.length); 
    } 
  
    public boolean hasGroupfoot(int groupIndex) { 
        boolean retBool = true; 
          
        if(showGroup) { 
            retBool = super.hasGroupfoot(groupIndex); 
        } 
          
        return retBool; 
    } 
	

}
