package test;

import org.ec.calibration.ECCalibrationDataArray;
import org.ec.calibration.ECGeometry;
import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECView;
import org.ec.services.ECCalibrationService;
import org.ec.services.ECGeometryService;
import org.jlab.coda.cMsg.test.connectTest;
import org.jlab.coda.clara.core.ClaraUser;
import org.jlab.coda.clara.system.ABase;
import org.jlab.coda.clara.core.CServiceRegistration;

public class TestCalibration extends ClaraUser  
{
	public static void main(String[] args)
	{
		TestCalibration test = new TestCalibration();
		test.initialize();
	}
	public TestCalibration()
	{
		
	}
	public void initialize()
	{
		connect();
		ABase base = new ABase();
		String containerName = "TEST1";
		createServiceContainer(containerName);
		
		while ( getServiceContainer(containerName) == null ){
			sleep(100);
		}
		String serviceName1 = addService(containerName,"org.ec.services.ECGeometryService");
		String serviceName2 = addService(containerName, "org.ec.services.ECCalibrationService");
		
		while ( (  getServiceOutputType(serviceName1, false) <= 0) 
				&& ( getServiceOutputType(serviceName1, false)) <= 0 ){
					System.out.println("... Waiting for service to register");
					sleep(1000);
				}
				System.out.println(" ... Services up");
				try{
					Object geometryObj = base.B2O((byte[])requestAndGetService(getContainer(serviceName1, false),serviceName1, null ,10000));
					ECGeometry geometry = (ECGeometry)geometryObj;
					System.out.println(" Pi:    " + geometry.getPi());
				} catch ( Exception e) {
					e.printStackTrace();
					return;
				}
				// TODO: Solve how to get a object array
				try{
					Object calibrationObj = base.B2O((byte[])requestAndGetService(getContainer(serviceName2,false),serviceName2, null, 1000000));
					ECCalibrationDataArray[] calibration = (ECCalibrationDataArray[])calibrationObj;
					for (int sector = 0; sector < ECGeneral.MAX_SECTORS; sector++) {
						for (ECLayer.Name layer: ECLayer.Name.values()) {
							for (ECView.Label view: ECView.Label.values()) {
								for (int strip = 0; strip < ECGeneral.MAX_STRIPS; strip++) {
									System.out.println(" ech:   " + Double.toString(calibration[0].getData(strip, layer, view).getEch()));
									System.out.println(" atten: " + Double.toString(calibration[1].getData(strip, layer, view).getAtten()));
									System.out.println(" Trms:  " + Double.toString(calibration[2].getData(strip, layer, view).getTrms()));
									System.out.println(" To:    " + Double.toString(calibration[3].getData(strip, layer, view).getTo()));
								}
							}
						}
					}
				} catch ( Exception e) {
					e.printStackTrace();
					return;
				}	
	}
}
