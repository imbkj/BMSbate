package impl;

import java.io.Serializable;
import java.util.Comparator;

import org.zkoss.zul.GroupComparator;

import Model.CoProductModel;

public class cpdFeeRelationComparatorImpl implements
		Comparator<CoProductModel>, GroupComparator<CoProductModel>,
		Serializable {

	public int compareGroup(CoProductModel o1, CoProductModel o2) {
		if (o1.getCity().equals(o2.getCity())) {
			return 0;
		} else {
			return 1;
		}
	}

	public int compare(CoProductModel o1, CoProductModel o2) {

		return o1.getCity().compareTo(o2.getCity().toString());
	}

}
