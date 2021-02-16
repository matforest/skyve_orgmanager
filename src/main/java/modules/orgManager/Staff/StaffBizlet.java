package modules.orgManager.Staff;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.skyve.domain.messages.Message;
import org.skyve.domain.messages.ValidationException;
import org.skyve.domain.types.DateOnly;
import org.skyve.metadata.model.document.Bizlet;

import modules.admin.ModulesUtil;
import modules.orgManager.domain.Staff;

public class StaffBizlet extends Bizlet<Staff> {

	private static final long serialVersionUID = 1L;

	@Override
	public void validate(Staff bean, ValidationException e) throws Exception {

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
	public void preSave(Staff bean) throws Exception {

		if (StringUtils.isBlank(bean.getCode())) {
			String nextNum = ModulesUtil.getNextDocumentNumber("S", Staff.MODULE_NAME, Staff.DOCUMENT_NAME,
					Staff.codePropertyName, 3);
			bean.setCode(nextNum);
		}

		super.preSave(bean);
	}
}
