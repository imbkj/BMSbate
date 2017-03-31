package bll.Embase;

import java.io.Serializable;
import java.util.Comparator;

import org.zkoss.zul.GroupComparator;

import Model.CoCompactCoofferModel;

public class CoCompactComparatorBll implements Comparator<CoCompactCoofferModel>,
		GroupComparator<CoCompactCoofferModel>, Serializable {

	@Override
	public int compareGroup(CoCompactCoofferModel arg0, CoCompactCoofferModel arg1) {
		// TODO Auto-generated method stub
		return arg0.getCoco_compactid().compareTo(arg1.getCoco_compactid());
	}

	@Override
	public int compare(CoCompactCoofferModel o1, CoCompactCoofferModel o2) {
		// TODO Auto-generated method stub
		if (o1.getCoco_compactid().equals(o2.getCoco_compactid())) {
			return 0;
		} else {
			return 1;
		}
	}

}
