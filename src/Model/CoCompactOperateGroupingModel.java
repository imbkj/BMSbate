package Model;

import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.GroupsModelArray;

public class CoCompactOperateGroupingModel extends
		GroupsModelArray<CoOfferListModel, String, String, Object> {

	private static final String footerString = "共  %d 条";
	private boolean showGroup;

	public CoCompactOperateGroupingModel(List<CoOfferListModel> data,
			Comparator<CoOfferListModel> cmpr, boolean showGroup) {
		super(data.toArray(new CoOfferListModel[0]), cmpr);

		this.showGroup = showGroup;
	}

	protected String createGroupHead(CoOfferListModel[] groupdata, int index,
			int col) {
		String ret = "";
		if (groupdata.length > 0) {
			ret = groupdata[0].getCoof_name();

		}

		return ret;
	}

	protected String createGroupFoot(CoOfferListModel[] groupdata, int index,
			int col) {
		return String.format(footerString, groupdata.length);
	}

	public boolean hasGroupfoot(int groupIndex) {
		boolean retBool = false;

		if (showGroup) {
			retBool = super.hasGroupfoot(groupIndex);
		}

		return retBool;
	}
}
