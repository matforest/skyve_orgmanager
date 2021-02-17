package modules.orgManager.Staff;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.skyve.domain.messages.Message;
import org.skyve.domain.messages.ValidationException;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.DateTime;
import org.skyve.metadata.model.document.Bizlet;

import modules.admin.ModulesUtil;
import modules.orgManager.domain.Staff;
import modules.orgManager.domain.Staff.Status;
import modules.orgManager.domain.StaffStatusHistory;

public class StaffBizlet extends Bizlet<StaffExtension> {

	private static final long serialVersionUID = 1L;

	@Override
	public void validate(StaffExtension bean, ValidationException e) throws Exception {

		DateOnly dob = bean.getDateOfBirth();
		DateOnly today = new DateOnly(new Date());

		if (dob != null) {
			if (dob.getTime() > today.getTime()) {
				e.getMessages()
						.add(new Message(Staff.dateOfBirthPropertyName, "Date of birth must not be after today"));
			}
		}

		super.validate(bean, e);
	}

	@Override
	public void preSave(StaffExtension bean) throws Exception {

		if (StringUtils.isBlank(bean.getCode())) {
			String nextNum = ModulesUtil.getNextDocumentNumber("S", Staff.MODULE_NAME, Staff.DOCUMENT_NAME,
					Staff.codePropertyName, 3);
			bean.setCode(nextNum);
		}

		if (bean.originalValues().containsKey(Staff.statusPropertyName)) {

			Status currStatus = bean.getStatus();
			StaffStatusHistory historyEntry = StaffStatusHistory.newInstance();

			historyEntry.setStatus(currStatus);
			historyEntry.setStatusTime(new DateTime());
			historyEntry.setParent(bean);

			List<StaffStatusHistory> histories = bean.getStaffStatusHistories();
			histories.add(historyEntry);

			Collections.sort(histories);
			// histories.stream().sorted().collect(Collectors.toList());
		}

		super.preSave(bean);
	}
}
