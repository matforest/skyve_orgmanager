package modules.orgManager.Office.models;

import java.util.ArrayList;
import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.skyve.metadata.view.model.map.MapFeature;
import org.skyve.metadata.view.model.map.MapItem;
import org.skyve.metadata.view.model.map.MapModel;
import org.skyve.metadata.view.model.map.MapResult;

import modules.orgManager.Staff.StaffExtension;
import modules.orgManager.domain.Office;

public class OfficeMap extends MapModel<Office> {

	@Override
	public MapResult getResult(Geometry mapBounds) throws Exception {

		Office office = getBean();
		return getResultForOffice(mapBounds, office);
	}

	public static MapResult getResultForOffice(Geometry mapBounds, Office office) {

		MapResult result = new MapResult();
		List<MapItem> items = new ArrayList<>();
		result.setItems(items);

		if (office == null) {

			return result;
		}

		Geometry officeLocation = office.getLocation();

		if (officeLocation != null) {
			if (mapBounds.intersects(officeLocation)) {
				MapItem item = new MapItem();
				item.setBizId(office.getBizId());
				item.setModuleName(office.getBizModule());
				item.setDocumentName(office.getBizDocument());
				item.setInfoMarkup(office.getBizKey());

				MapFeature feature = new MapFeature();
				feature.setGeometry(officeLocation);
				feature.setFillColour("#FF7F00"); // orange
				feature.setFillOpacity("0.15");
				feature.setStrokeColour("#000000"); // black
				item.getFeatures().add(feature);
				items.add(item);

				List<StaffExtension> employees = office.getEmployees();
				for (StaffExtension staffMember : employees) {

					MapItem newFeature = createStaffFeature(staffMember);
					items.add(newFeature);
				}
			}
		}

		return result;
	}

	private static MapItem createStaffFeature(StaffExtension staff) {

		MapFeature feature = new MapFeature();
		feature.setGeometry(staff.getLocation());

		MapItem item = new MapItem();
		item.getFeatures().add(feature);
		item.setBizId(staff.getBizId());
		item.setModuleName(staff.getBizModule());
		item.setDocumentName(staff.getBizDocument());
		item.setInfoMarkup(staff.getBizKey() + " TODO!");

		System.err.println(staff.getImage());

		return item;
	}

}
