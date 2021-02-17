package modules.orgManager.Staff;

import java.util.Date;

import org.skyve.domain.types.DateOnly;
import org.skyve.impl.util.TimeUtil;
import org.skyve.util.Util;

import modules.orgManager.domain.Office;
import modules.orgManager.domain.Staff;

public class StaffExtension extends Staff {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer getAgeInYears() {

		Integer age = -1;

		DateOnly dob = getDateOfBirth();
		if (dob != null) {

			int days = TimeUtil.numberOfDaysInRange(dob, new Date());
			// Close enough :)
			return Math.floorDiv(days, 365);
		}

		return age;
	}

	public void home() {

		Util.LOGGER.warning("Attempting to re-homing user " + this);
		Office office = this.getHomeOffice();

		if (office != null && office.getLocation() != null) {
			this.setLocation(office.getLocation().getCentroid());
		}
	}
}
