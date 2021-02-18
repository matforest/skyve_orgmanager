package modules.orgManager.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.impl.domain.AbstractTransientBean;

/**
 * PublicAccessView
 * 
 * @navhas n selectedOffice 0..1 Office
 * @stereotype "transient"
 */
@XmlType
@XmlRootElement
public class PublicAccessView extends AbstractTransientBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "orgManager";
	/** @hidden */
	public static final String DOCUMENT_NAME = "PublicAccessView";

	/** @hidden */
	public static final String selectedOfficePropertyName = "selectedOffice";

	/**
	 * Selected Office
	 **/
	private Office selectedOffice = null;

	@Override
	@XmlTransient
	public String getBizModule() {
		return PublicAccessView.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return PublicAccessView.DOCUMENT_NAME;
	}

	public static PublicAccessView newInstance() {
		try {
			return CORE.getUser().getCustomer().getModule(MODULE_NAME).getDocument(CORE.getUser().getCustomer(), DOCUMENT_NAME).newInstance(CORE.getUser());
		}
		catch (RuntimeException e) {
			throw e;
		}
		catch (Exception e) {
			throw new DomainException(e);
		}
	}

	@Override
	@XmlTransient
	public String getBizKey() {
		return toString();

	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof PublicAccessView) && 
					this.getBizId().equals(((PublicAccessView) o).getBizId()));
	}

	/**
	 * {@link #selectedOffice} accessor.
	 * @return	The value.
	 **/
	public Office getSelectedOffice() {
		return selectedOffice;
	}

	/**
	 * {@link #selectedOffice} mutator.
	 * @param selectedOffice	The new value.
	 **/
	@XmlElement
	public void setSelectedOffice(Office selectedOffice) {
		if (this.selectedOffice != selectedOffice) {
			preset(selectedOfficePropertyName, selectedOffice);
			this.selectedOffice = selectedOffice;
		}
	}

	/**
	 * showMap
	 *
	 * @return The condition
	 */
	@XmlTransient
	public boolean isShowMap() {
		return (getSelectedOffice() != null);
	}

	/**
	 * {@link #isShowMap} negation.
	 *
	 * @return The negated condition
	 */
	public boolean isNotShowMap() {
		return (! isShowMap());
	}
}
