package modules.orgManager.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import modules.orgManager.Staff.StaffExtension;
import org.locationtech.jts.geom.Geometry;
import org.skyve.CORE;
import org.skyve.domain.messages.DomainException;
import org.skyve.domain.types.DateOnly;
import org.skyve.domain.types.Enumeration;
import org.skyve.impl.domain.AbstractPersistentBean;
import org.skyve.impl.domain.ChangeTrackingArrayList;
import org.skyve.impl.domain.types.jaxb.DateOnlyMapper;
import org.skyve.impl.domain.types.jaxb.GeometryMapper;
import org.skyve.metadata.model.document.Bizlet.DomainValue;

/**
 * Staff
 * 
 * @depend - - - Status
 * @navcomposed 1 staffStatusHistories 0..n StaffStatusHistory
 * @navhas n homeOffice 0..1 Office
 * @stereotype "persistent"
 */
@XmlType
@XmlRootElement
public abstract class Staff extends AbstractPersistentBean {
	/**
	 * For Serialization
	 * @hidden
	 */
	private static final long serialVersionUID = 1L;

	/** @hidden */
	public static final String MODULE_NAME = "orgManager";
	/** @hidden */
	public static final String DOCUMENT_NAME = "Staff";

	/** @hidden */
	public static final String codePropertyName = "code";
	/** @hidden */
	public static final String namePropertyName = "name";
	/** @hidden */
	public static final String lastNamePropertyName = "lastName";
	/** @hidden */
	public static final String dateOfBirthPropertyName = "dateOfBirth";
	/** @hidden */
	public static final String ageInYearsPropertyName = "ageInYears";
	/** @hidden */
	public static final String homeOfficePropertyName = "homeOffice";
	/** @hidden */
	public static final String locationPropertyName = "location";
	/** @hidden */
	public static final String imagePropertyName = "image";
	/** @hidden */
	public static final String statusPropertyName = "status";
	/** @hidden */
	public static final String staffStatusHistoriesPropertyName = "staffStatusHistories";

	/**
	 * Status
	 **/
	@XmlEnum
	public static enum Status implements Enumeration {
		in("in", "In the office"),
		otl("otl", "Out to Lunch"),
		out("out", "Out");

		private String code;
		private String description;

		/** @hidden */
		private DomainValue domainValue;

		/** @hidden */
		private static List<DomainValue> domainValues;

		private Status(String code, String description) {
			this.code = code;
			this.description = description;
			this.domainValue = new DomainValue(code, description);
		}

		@Override
		public String toCode() {
			return code;
		}

		@Override
		public String toDescription() {
			return description;
		}

		@Override
		public DomainValue toDomainValue() {
			return domainValue;
		}

		public static Status fromCode(String code) {
			Status result = null;

			for (Status value : values()) {
				if (value.code.equals(code)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static Status fromDescription(String description) {
			Status result = null;

			for (Status value : values()) {
				if (value.description.equals(description)) {
					result = value;
					break;
				}
			}

			return result;
		}

		public static List<DomainValue> toDomainValues() {
			if (domainValues == null) {
				Status[] values = values();
				domainValues = new ArrayList<>(values.length);
				for (Status value : values) {
					domainValues.add(value.domainValue);
				}
			}

			return domainValues;
		}
	}

	/**
	 * Code
	 **/
	private String code;
	/**
	 * First Name
	 **/
	private String name;
	/**
	 * Last Name
	 **/
	private String lastName;
	/**
	 * Date of Birth
	 **/
	private DateOnly dateOfBirth;
	/**
	 * Age In Years
	 **/
	private Integer ageInYears;
	/**
	 * Home Office
	 **/
	private Office homeOffice = null;
	/**
	 * Location
	 **/
	private Geometry location;
	/**
	 * Image
	 **/
	private String image;
	/**
	 * Status
	 **/
	private Status status = Status.in;
	/**
	 * Staff Status History
	 **/
	private List<StaffStatusHistory> staffStatusHistories = new ChangeTrackingArrayList<>("staffStatusHistories", this);

	@Override
	@XmlTransient
	public String getBizModule() {
		return Staff.MODULE_NAME;
	}

	@Override
	@XmlTransient
	public String getBizDocument() {
		return Staff.DOCUMENT_NAME;
	}

	public static StaffExtension newInstance() {
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
		try {
			return org.skyve.util.Binder.formatMessage("{code}", this);
		}
		catch (@SuppressWarnings("unused") Exception e) {
			return "Unknown";
		}
	}

	@Override
	public boolean equals(Object o) {
		return ((o instanceof Staff) && 
					this.getBizId().equals(((Staff) o).getBizId()));
	}

	/**
	 * {@link #code} accessor.
	 * @return	The value.
	 **/
	public String getCode() {
		return code;
	}

	/**
	 * {@link #code} mutator.
	 * @param code	The new value.
	 **/
	@XmlElement
	public void setCode(String code) {
		preset(codePropertyName, code);
		this.code = code;
	}

	/**
	 * {@link #name} accessor.
	 * @return	The value.
	 **/
	public String getName() {
		return name;
	}

	/**
	 * {@link #name} mutator.
	 * @param name	The new value.
	 **/
	@XmlElement
	public void setName(String name) {
		preset(namePropertyName, name);
		this.name = name;
	}

	/**
	 * {@link #lastName} accessor.
	 * @return	The value.
	 **/
	public String getLastName() {
		return lastName;
	}

	/**
	 * {@link #lastName} mutator.
	 * @param lastName	The new value.
	 **/
	@XmlElement
	public void setLastName(String lastName) {
		preset(lastNamePropertyName, lastName);
		this.lastName = lastName;
	}

	/**
	 * {@link #dateOfBirth} accessor.
	 * @return	The value.
	 **/
	public DateOnly getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * {@link #dateOfBirth} mutator.
	 * @param dateOfBirth	The new value.
	 **/
	@XmlSchemaType(name = "date")
	@XmlJavaTypeAdapter(DateOnlyMapper.class)
	@XmlElement
	public void setDateOfBirth(DateOnly dateOfBirth) {
		preset(dateOfBirthPropertyName, dateOfBirth);
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * {@link #ageInYears} accessor.
	 * @return	The value.
	 **/
	public Integer getAgeInYears() {
		return ageInYears;
	}

	/**
	 * {@link #ageInYears} mutator.
	 * @param ageInYears	The new value.
	 **/
	@XmlElement
	public void setAgeInYears(Integer ageInYears) {
		preset(ageInYearsPropertyName, ageInYears);
		this.ageInYears = ageInYears;
	}

	/**
	 * {@link #homeOffice} accessor.
	 * @return	The value.
	 **/
	public Office getHomeOffice() {
		return homeOffice;
	}

	/**
	 * {@link #homeOffice} mutator.
	 * @param homeOffice	The new value.
	 **/
	@XmlElement
	public void setHomeOffice(Office homeOffice) {
		if (this.homeOffice != homeOffice) {
			preset(homeOfficePropertyName, homeOffice);
			Office oldHomeOffice = this.homeOffice;
			this.homeOffice = homeOffice;
			if ((homeOffice != null) && (homeOffice.getEmployeesElementById(getBizId()) == null)) {
				homeOffice.getEmployees().add((StaffExtension) this);
			}
			if (oldHomeOffice != null) {
				oldHomeOffice.getEmployees().remove(this);
			}
		}
	}

	public void nullHomeOffice() {
		this.homeOffice = null;
	}

	/**
	 * {@link #location} accessor.
	 * @return	The value.
	 **/
	public Geometry getLocation() {
		return location;
	}

	/**
	 * {@link #location} mutator.
	 * @param location	The new value.
	 **/
	@XmlJavaTypeAdapter(GeometryMapper.class)
	@XmlElement
	public void setLocation(Geometry location) {
		preset(locationPropertyName, location);
		this.location = location;
	}

	/**
	 * {@link #image} accessor.
	 * @return	The value.
	 **/
	public String getImage() {
		return image;
	}

	/**
	 * {@link #image} mutator.
	 * @param image	The new value.
	 **/
	@XmlElement
	public void setImage(String image) {
		preset(imagePropertyName, image);
		this.image = image;
	}

	/**
	 * {@link #status} accessor.
	 * @return	The value.
	 **/
	public Status getStatus() {
		return status;
	}

	/**
	 * {@link #status} mutator.
	 * @param status	The new value.
	 **/
	@XmlElement
	public void setStatus(Status status) {
		preset(statusPropertyName, status);
		this.status = status;
	}

	/**
	 * {@link #staffStatusHistories} accessor.
	 * @return	The value.
	 **/
	@XmlElement
	public List<StaffStatusHistory> getStaffStatusHistories() {
		return staffStatusHistories;
	}

	/**
	 * {@link #staffStatusHistories} accessor.
	 * @param bizId	The bizId of the element in the list.
	 * @return	The value of the element in the list.
	 **/
	public StaffStatusHistory getStaffStatusHistoriesElementById(String bizId) {
		return getElementById(staffStatusHistories, bizId);
	}

	/**
	 * {@link #staffStatusHistories} mutator.
	 * @param bizId	The bizId of the element in the list.
	 * @param element	The new value of the element in the list.
	 **/
	public void setStaffStatusHistoriesElementById(String bizId, StaffStatusHistory element) {
		setElementById(staffStatusHistories, element);
	}

	/**
	 * {@link #staffStatusHistories} add.
	 * @param element	The element to add.
	 **/
	public boolean addStaffStatusHistoriesElement(StaffStatusHistory element) {
		boolean result = staffStatusHistories.add(element);
		element.setParent((StaffExtension) this);
		return result;
	}

	/**
	 * {@link #staffStatusHistories} add.
	 * @param index	The index in the list to add the element to.
	 * @param element	The element to add.
	 **/
	public void addStaffStatusHistoriesElement(int index, StaffStatusHistory element) {
		staffStatusHistories.add(index, element);
		element.setParent((StaffExtension) this);
	}

	/**
	 * {@link #staffStatusHistories} remove.
	 * @param element	The element to remove.
	 **/
	public boolean removeStaffStatusHistoriesElement(StaffStatusHistory element) {
		boolean result = staffStatusHistories.remove(element);
		element.setParent(null);
		return result;
	}

	/**
	 * {@link #staffStatusHistories} remove.
	 * @param index	The index in the list to remove the element from.
	 **/
	public StaffStatusHistory removeStaffStatusHistoriesElement(int index) {
		StaffStatusHistory result = staffStatusHistories.remove(index);
		result.setParent(null);
		return result;
	}
}
