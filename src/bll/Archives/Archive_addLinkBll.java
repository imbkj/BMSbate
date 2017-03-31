package bll.Archives;

import java.text.DateFormat;
import java.util.Date;

import dal.Archives.EmArchiveLinkModelDal;

public class Archive_addLinkBll {

	// 添加联系记录
	public Integer addlink(Integer id, String linktype, Date linktime,
			String content, String username,int gid) {
		Integer i = 0;
		EmArchiveLinkModelDal dal = new EmArchiveLinkModelDal();

		try {
			i = dal.addLinkInfo(
					id,
					DateFormat.getDateInstance(DateFormat.MEDIUM).format(
							linktime), linktype, content, username,gid);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return i;
	}
}
