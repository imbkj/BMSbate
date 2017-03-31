package bll.CoHousingFund;

import java.util.List;

import org.zkoss.zul.ListModelList;

import dal.CoHousingFund.CoHouseFileDal;

import Model.CoHousingFundFileModel;

public class CoHouseFileBll {

	// 查询指引列表
	public List<CoHousingFundFileModel> getFilelist(Integer pid) {
		CoHouseFileDal dal = new CoHouseFileDal();
		return dal.getFilelist(pid);
	}

	// 查询指引列表(含父节点)
	public List<CoHousingFundFileModel> getFilelistShow(Integer pid) {
		CoHouseFileDal dal = new CoHouseFileDal();
		return dal.getFilelistShow(pid);
	}

	// 查询指引列表
	public List<CoHousingFundFileModel> getFilelist(String name) {
		CoHouseFileDal dal = new CoHouseFileDal();
		name = "%" + name + "%";
		return dal.getFilelist(name);
	}

	// 查询指引列表
	public List<CoHousingFundFileModel> getFilelist(String name, Integer pid) {
		CoHouseFileDal dal = new CoHouseFileDal();
		name = "%" + name + "%";
		return dal.getFilelist(name, pid);
	}

	// 查询最大排序
	public Integer getMaxSort(Integer pid) {
		Integer i = 1;
		CoHouseFileDal dal = new CoHouseFileDal();
		List<CoHousingFundFileModel> list = new ListModelList<>();
		list = dal.getMaxSort(pid);
		if (list.size() > 0) {
			i = list.get(0).getChff_sort();
			i++;
		}
		return i;
	}

	// 查询父节点
	public Integer getPid(Integer id) {
		Integer i = 0;
		CoHouseFileDal dal = new CoHouseFileDal();
		List<CoHousingFundFileModel> list = new ListModelList<>();
		list = dal.getPid(id);
		if (list.size() > 0) {
			i = list.get(0).getChff_pid();

		}
		return i;

	}

	// 新增项目
	public Integer add(CoHousingFundFileModel m) {
		CoHouseFileDal dal = new CoHouseFileDal();
		return dal.add(m.getChff_pid(), m.getChff_name(), m.getChff_url(),
				m.getChff_sort(), m.getChff_content(), m.getChff_remark(),
				m.getChff_iffile(), m.getChff_addname());
	}

	// 修改
	public Integer mod(CoHousingFundFileModel m) {
		CoHouseFileDal dal = new CoHouseFileDal();
		return dal.mod(m.getChff_id(), m.getChff_pid(), m.getChff_name(),
				m.getChff_url(), m.getChff_sort(), m.getChff_content(),
				m.getChff_remark(), m.getChff_iffile());
	}

	// 删除
	public Integer del(Integer id) {
		CoHouseFileDal dal = new CoHouseFileDal();
		return dal.del(id);
	}

	// 上传文件
	public Integer uploadfile(Integer id) {

		return null;
	}
}
