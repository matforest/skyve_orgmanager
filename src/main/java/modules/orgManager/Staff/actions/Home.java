package modules.orgManager.Staff.actions;

import java.lang.Override;
import modules.orgManager.Staff.StaffExtension;
import org.skyve.metadata.controller.ServerSideAction;
import org.skyve.metadata.controller.ServerSideActionResult;
import org.skyve.web.WebContext;

public class Home implements ServerSideAction<StaffExtension> {
	@Override
	public ServerSideActionResult<StaffExtension> execute(StaffExtension bean, WebContext webContext) {
		
		bean.home();
		
		return new ServerSideActionResult<>(bean);
	}
}
