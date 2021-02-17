package modules.orgManager.Staff;

import java.util.List;

import org.skyve.CORE;
import org.skyve.job.CancellableJob;
import org.skyve.persistence.DocumentQuery;
import org.skyve.util.Util;

import modules.orgManager.domain.Staff;

public class ReHomeJob extends CancellableJob {

	@Override
	public void execute() throws Exception {

		List<String> log = getLog();

		DocumentQuery query = CORE.getPersistence().newDocumentQuery(Staff.MODULE_NAME, Staff.DOCUMENT_NAME);
		query.getFilter().addEquals(Staff.statusPropertyName, Staff.Status.in);

		List<StaffExtension> results = query.beanResults();

		int count = 0;
		int total = results.size();
		Util.LOGGER.warning("Found " + total + " staff members with status==in");

		for (StaffExtension currStaff : results) {

			log.add("Updating home location for " + currStaff.getBizKey());
			currStaff.home();

			count++;
			setPercentComplete(count, total);

		}

		CORE.getPersistence().save(results);

		setPercentComplete(100);
	}

	protected void setPercentComplete(int count, int total) {

		this.setPercentComplete((int) (count / (float) total));
	}
}
