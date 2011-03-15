package test;

import org.ec.calibration.ECCalibrationDataArray;
import org.ec.calibration.ECGeometry;
import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECView;
import org.ec.services.ECCalibrationService;
import org.ec.services.ECGeometryService;

public class TestCalibration
{
	public static void main(String[] args)
	{
		ECCalibrationService service 		= new ECCalibrationService();
		ECCalibrationDataArray calibration 	= (ECCalibrationDataArray) service.executeService(0, null);
		ECGeometryService geometryService  	= new ECGeometryService();
		ECGeometry geometry					= (ECGeometry) geometryService.executeService(0, null);

		for (int sector = 0; sector < ECGeneral.MAX_SECTORS; sector++) {
			for (ECLayer.Name layer: ECLayer.Name.values()) {
				for (ECView.Label view: ECView.Label.values()) {
					for (int strip = 0; strip < ECGeneral.MAX_STRIPS; strip++) {
						System.out.println(" ech:   " + Double.toString(calibration.getData(strip, layer, view, sector).getEch()));
						System.out.println(" atten: " + Double.toString(calibration.getData(strip, layer, view, sector).getAtten()));
						System.out.println(" Trms:  " + Double.toString(calibration.getData(strip, layer, view, sector).getTrms()));
						System.out.println(" To:    " + Double.toString(calibration.getData(strip, layer, view, sector).getTo()));
					}
				}
			}
		}

		System.out.println(" Pi:    " + geometry.getPi());
	}
}
