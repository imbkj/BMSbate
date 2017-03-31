package Controller.EmBaseCompact;

import impl.WorkflowCore.WfOperateImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import service.WorkflowCore.WfOperateService;
import bll.CoCompact.CoCompactSA.CoCompact_UpOperateImpl;

public class EmBaseCompact_OperateBll {
			
		// 公司合同上传
			public String[] UDocFileUpload(int cid, int gid, String doin_id, String url,
					String size, String addname) throws Exception {

				String[] message = new String[5];
				try {
					Object[] obj = { "5", cid, gid, doin_id, url,size,addname };
					// 执行工作流
					WfOperateService wf = new WfOperateImpl(new CoCompact_UpOperateImpl());
					message = wf.AddTaskToNext(obj, "劳动合同补充协议", "XX劳动合同模板上传", 5, addname,
							"", cid, "");
				} catch (Exception e) {
					message[0] = "2";
					message[1] = "劳动合同补充协议上传，操作出错。";
				}
				return message;
			}
		
		// Date类型转换String
		public String DatetoSting(Date d) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String Date = sdf.format(d);
			return Date;
		}
}
