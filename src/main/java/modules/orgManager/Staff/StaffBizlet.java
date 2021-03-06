package modules.orgManager.Staff;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.skyve.CORE;
import org.skyve.domain.messages.Message;
import org.skyve.domain.messages.ValidationException;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.DateTime;
import org.skyve.metadata.customer.Customer;
import org.skyve.metadata.model.document.Bizlet;
import org.skyve.metadata.model.document.Document;
import org.skyve.metadata.module.Module;
import org.skyve.util.Binder;
import org.skyve.web.WebContext;

import modules.admin.ModulesUtil;
import modules.orgManager.StaffStatusHistory.StaffStatusHistoryExtension;
import modules.orgManager.domain.Staff;
import modules.orgManager.domain.Staff.Status;

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

		super.preSave(bean);
	}

	@Override
	public void preRerender(String source, StaffExtension bean, WebContext webContext) throws Exception {

		// Status has changed
		if (Staff.statusPropertyName.equals(source) && //
				bean.originalValues().containsKey(Staff.statusPropertyName)) {

			addStatusHistoryEntry(bean);

			// Status is now "In the office"
			if (Staff.Status.in.equals(bean.getStatus())) {
				// Set the users location to the office location
				bean.home();
			}
		}

		super.preRerender(source, bean, webContext);
	}

	private void addStatusHistoryEntry(StaffExtension bean) {

		Status currStatus = bean.getStatus();
		StaffStatusHistoryExtension historyEntry = StaffStatusHistoryExtension.newInstance();

		historyEntry.setStatus(currStatus);
		historyEntry.setStatusTime(new DateTime());
		historyEntry.setParent(bean);

		List<StaffStatusHistoryExtension> histories = bean.getStaffStatusHistories();
		histories.add(historyEntry);

		Customer customer = CORE.getPersistence().getUser().getCustomer();
		Module module = customer.getModule(Staff.MODULE_NAME);
		Document document = module.getDocument(customer, Staff.DOCUMENT_NAME);
		Binder.sortCollectionByMetaData(bean, customer, module, document, Staff.staffStatusHistoriesPropertyName);
	}
}
