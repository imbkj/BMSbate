package bll.Embase;

import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.GroupsModelArray;
import Model.CoCompactCoofferModel;

public class CoCompactGroupingBll extends
		GroupsModelArray<CoCompactCoofferModel, String, String, Object> {

	private boolean showGroup;

	public CoCompactGroupingBll(List<CoCompactCoofferModel> data,
			Comparator<CoCompactCoofferModel> cmpr, boolean showGroup) {
		super(data.toArray(new CoCompactCoofferModel[0]), cmpr);

		this.showGroup = showGroup;
	}

	protected String createGroupHead(CoCompactCoofferModel[] groupdata,
			int index, int col) {
		String ret = "";
		if (groupdata.length > 0) {
			ret = groupdata[0].getCoco_compactid();
			if (groupdata[0].getCoco_shebao()!=null && !groupdata[0].getCoco_shebao().equals("")) {
				ret+="  ,社保开户:"+groupdata[0].getCoco_shebao();
			}
			if (groupdata[0].getCoco_house()!=null && !groupdata[0].getCoco_house().equals("")) {
				ret+="  ,公积金开户:"+groupdata[0].getCoco_shebao();
			}
		}

		return ret;
	}


	public boolean hasGroupfoot(int groupIndex) {
		boolean retBool = false;

		if (showGroup) {
			retBool = super.hasGroupfoot(groupIndex);
		}

		return retBool;
	}
}
