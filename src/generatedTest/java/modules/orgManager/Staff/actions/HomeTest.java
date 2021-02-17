package modules.orgManager.Staff.actions;

import modules.orgManager.Staff.StaffExtension;
import modules.orgManager.domain.Staff;
import org.skyve.util.DataBuilder;
import org.skyve.util.test.SkyveFixture.FixtureType;
import util.AbstractActionTest;

/**
 * Generated - local changes will be overwritten.
 * Extend {@link AbstractActionTest} to create your own tests for this action.
 */
public class HomeTest extends AbstractActionTest<StaffExtension, Home> {

	@Override
	protected Home getAction() {
		return new Home();
	}

	@Override
	protected StaffExtension getBean() throws Exception {
		return new DataBuilder()
			.fixture(FixtureType.crud)
			.build(Staff.MODULE_NAME, Staff.DOCUMENT_NAME);
	}
}