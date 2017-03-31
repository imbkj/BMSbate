package Model;

import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.GroupsModelArray;

public class cpdFeeRelationGroupingModel extends
		GroupsModelArray<CoProductModel, String, String, Object> {

	private static final String footerString = "共  %d 条";
	private boolean showGroup;

	public cpdFeeRelationGroupingModel(List<CoProductModel> data,
			Comparator<CoProductModel> cmpr, boolean showGroup) {
		super(data.toArray(new CoProductModel[0]), cmpr);

		this.showGroup = showGroup;
	}

	protected String createGroupHead(CoProductModel[] groupdata, int index, int col) { 
        String ret = ""; 
        if (groupdata.length > 0) { 
            ret = groupdata[0].getCity(); 
          
        } 
  
        return ret; 
    } 
  
    protected String createGroupFoot(CoProductModel[] groupdata, int index, int col) { 
        return String.format(footerString, groupdata.length); 
    } 
  
    public boolean hasGroupfoot(int groupIndex) { 
        boolean retBool = false; 
          
        if(showGroup) { 
            retBool = super.hasGroupfoot(groupIndex); 
        } 
          
        return retBool; 
    } 

}
