package impl;

import java.io.Serializable;
import java.util.Comparator;

import org.zkoss.zul.GroupComparator;

import Model.CoOfferListModel;

public class CoCompactOperateComparatorImpl implements
		Comparator<CoOfferListModel>, GroupComparator<CoOfferListModel>,
		Serializable {

	@Override
	public int compareGroup(CoOfferListModel arg0, CoOfferListModel arg1) {
		// TODO Auto-generated method stub
		if (arg0.getCoof_name().equals(arg1.getCoof_name())) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int compare(CoOfferListModel o1, CoOfferListModel o2) {
		// TODO Auto-generated method stub
		return o1.getCoof_name().compareTo(o2.getCoof_name().toString());
	}

}
