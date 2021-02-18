package modules.orgManager.PublicAccessView.models;

import org.locationtech.jts.geom.Geometry;
import org.skyve.metadata.view.model.map.MapModel;
import org.skyve.metadata.view.model.map.MapResult;
import org.skyve.util.Util;

import modules.orgManager.Office.models.OfficeMap;
import modules.orgManager.domain.Office;
import modules.orgManager.domain.PublicAccessView;

public class OfficeMapModel extends MapModel<PublicAccessView> {

	@Override
	public MapResult getResult(Geometry mapBounds) throws Exception {

		PublicAccessView view = getBean();
		Office office = view.getSelectedOffice();

		try {
			return OfficeMap.getResultForOffice(mapBounds, office);
		} catch (Exception e) {

			Util.LOGGER.severe(e.getLocalizedMessage());
			return null;
		}

	}

}
