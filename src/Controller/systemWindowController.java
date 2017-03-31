package Controller;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Window;

public class systemWindowController extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;
	private final String url = Executions.getCurrent().getArg().get("sendUrl")
			.toString();
	@SuppressWarnings("unchecked")
	private final Map<String, Object> map = (Map<String, Object>) Executions
			.getCurrent().getArg().get("sendMap");

	@Listen("onClick = #btSubmit")
	public void sendWindow() {
		Window window = (Window) Executions.createComponents(url, null, map);
		window.doModal();
	}
}
