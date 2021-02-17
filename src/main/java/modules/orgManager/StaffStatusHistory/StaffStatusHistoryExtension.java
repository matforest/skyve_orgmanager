package modules.orgManager.StaffStatusHistory;

import org.skyve.domain.Bean;

import modules.orgManager.domain.StaffStatusHistory;

public class StaffStatusHistoryExtension extends StaffStatusHistory {

	private static final long serialVersionUID = 1L;

	@Override
	public int compareTo(Bean other) {

		if (other == null || !(other instanceof StaffStatusHistoryExtension)) {
			return 0;
		} else {
			StaffStatusHistoryExtension otherHistory = (StaffStatusHistoryExtension) other;
			return this.getStatusTime().compareTo(otherHistory.getStatusTime());
		}
	}
}
