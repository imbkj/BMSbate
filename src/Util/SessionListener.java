/**
 * @Classname SessionListener
 * @ClassInfo session监听事件，继承Initiator接口
 * @author 李文洁
 * @Date 2013-9-27
 */
package Util;

import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.Initiator;

public class SessionListener implements Initiator{
	public void doInit(Page page, Map args) throws Exception {
		String path = page.getRequestPath();

		if ("/login.zul".equals(path)
				|| "/SystemControl/Pub_RegUser.zul".equals(path)) {
			return;
		} else {
			Session session = Executions.getCurrent().getDesktop().getSession();
			if (session.getAttribute("username") == null) {
				Executions.sendRedirect("login.zul");
			}
		}

	}
}