package modules.orgManager.Staff;

import java.util.Date;

import org.skyve.domain.types.DateOnly;
import org.skyve.impl.util.TimeUtil;

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
}
