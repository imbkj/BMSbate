package chart.bar;

import java.util.List;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;

import Model.StatisticsResultModel;
import dal.Statistics.Stat_SelectDal;

public class ChartData {

	public static CategoryModel getModel(String aa) {

		Stat_SelectDal dal = new Stat_SelectDal();
		List<StatisticsResultModel> list = dal.getStatResult(6, "131", "");

		CategoryModel model = new SimpleCategoryModel();

		for (int i = 0; i < list.size(); i++) {
			model.setValue(list.get(i).getStre_content(), list.get(i)
					.getStre_smonth(), Float.valueOf(list.get(i).getStre_sum()));
		}

		/*
		 * model.setValue("机构数", "201401", new Integer(10));
		 * 
		 * model.setValue("客户数", "201401", new Integer(20));
		 * 
		 * model.setValue("员工数", "201401", new Integer(30));
		 * 
		 * model.setValue("人均服务费", "201401", new Integer(10));
		 * 
		 * model.setValue("机构数", "201402", new Integer(20));
		 * 
		 * model.setValue("客户数", "201402", new Integer(30));
		 * 
		 * model.setValue("员工数", "201402", new Integer(10));
		 * 
		 * model.setValue("人均服务费", "201402", new Integer(20));
		 * 
		 * model.setValue("机构数", "201403", new Integer(30));
		 * 
		 * model.setValue("客户数", "201403", new Integer(10));
		 * 
		 * model.setValue("员工数", "201403", new Integer(20));
		 * 
		 * model.setValue("人均服务费", "201403", new Integer(30));
		 * 
		 * model.setValue("机构数", "201404", new Integer(30));
		 * 
		 * model.setValue("客户数", "201404", new Integer(10));
		 * 
		 * model.setValue("员工数", "201404", new Integer(20));
		 * 
		 * model.setValue("人均服务费", "201404", new Integer(30));
		 * 
		 * model.setValue("机构数", "201405", new Integer(30));
		 * 
		 * model.setValue("客户数", "201405", new Integer(10));
		 * 
		 * model.setValue("员工数", "201405", new Integer(20));
		 * 
		 * model.setValue("人均服务费", "201405", new Integer(30));
		 * 
		 * model.setValue("机构数", "201406", new Integer(30));
		 * 
		 * model.setValue("客户数", "201406", new Integer(10));
		 * 
		 * model.setValue("员工数", "201406", new Integer(20));
		 * 
		 * model.setValue("人均服务费", "201406", new Integer(30));
		 */

		return model;
	}

	public static CategoryModel getFModel(String aa) {

		Stat_SelectDal dal = new Stat_SelectDal();
		List<StatisticsResultModel> list = dal.getStatResult(6, "128", "");

		CategoryModel fmodel = new SimpleCategoryModel();

		for (int i = 0; i < list.size(); i++) {
			fmodel.setValue(list.get(i).getStre_content(), list.get(i)
					.getStre_smonth(), Float.valueOf(list.get(i).getStre_sum()));
		}

		return fmodel;
	}

	public static CategoryModel getLineModel() {

		Stat_SelectDal dal = new Stat_SelectDal();
		List<StatisticsResultModel> list = dal.getStatResult(6, "128", "");

		CategoryModel linemodel = new SimpleCategoryModel();

		for (int i = 0; i < list.size(); i++) {
			linemodel.setValue(list.get(i).getStre_content(), list.get(i)
					.getStre_smonth(), Float.valueOf(list.get(i).getStre_sum()));
		}

		return linemodel;
	}

	public static PieModel getPieModel() {
		Stat_SelectDal dal = new Stat_SelectDal();
		List<StatisticsResultModel> list = dal.getStatResult(6, "127", "");
		PieModel Pmodel = new SimplePieModel();
		for (int i = 0; i < list.size(); i++) {
			Pmodel.setValue(list.get(i).getStre_content(),
					Float.valueOf(list.get(i).getStre_sum()));
		}

		return Pmodel;
	}
}
