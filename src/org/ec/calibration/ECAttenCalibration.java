package org.ec.calibration;

import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECView;
import org.jlab.coda.clara.core.ClaraUser;
import org.jlab.coda.clara.system.ABase;

/**
 * The <code> ECAttenCalibration </code> class load the information of the EC
 * calibration map, in specific the <em>EC_ATTEN</em> system info, into the
 * <code>ECCalibrationArray</code>, filling the <em>Atten</em> variable.
 * <p>
 * Implements runnable, because is called in parallel with the other
 * calibration classes.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), March 14, 2011</font>
 *
 * @author      jgpavez
 * @version     0.1
 */

public class ECAttenCalibration extends ClaraUser implements Runnable
{
	
    ECCalibrationDataArray dataArray;
    String system = "EC_CALIB";
    String subsystem = "EC_ATTEN";



    /**
     * Construct the object, only set the <em>ECCalibrationDataArray</em> data
     * object to his
     *
     * @param data  the array with data
     */
    public ECAttenCalibration(ECCalibrationDataArray array)
    {
        dataArray = array;
    }


    /**
     * Read the Atten Map database. Need the subSystem name to construct the query and return
     * all constants for each layer and view in a float array.
     * @return 
     */
    public double[][][] readAttenMap(String subSystem)
    {
        connect();
        ABase base = new ABase();
        String containerName = "ECCALIBRATION";
        	
        createServiceContainer(containerName);
        double[][][] constants;
        constants = new double[4][3][];
        
        Object[] params = new Object[9];
        params[0] = system;
        params[1] = subSystem;
        params[3] = null;
        params[4] = "2037-1-1";
        params[5] = 28000;
        params[6] = "";
        params[7] = "";
        params[8] = "";
        
        while ( getServiceContainer(containerName) == null ){
        	sleep(100);
        }
        String serviceName1 = addService(containerName, "org.ec.maputil.MapUtilService");
        
        while ( (getServiceOutputType(serviceName1,false) <= 0)
        		&& ( getServiceOutputType(serviceName1, false)) <= 0 ){
        			System.out.println("... Waiting for service to register");
        			sleep(1000);
        }
		for (ECLayer.Name layer: ECLayer.Name.values()) {
			if ( layer.ordinal() == 3) continue;
			for (ECView.Label view: ECView.Label.values()) {
		        params[2] = layer.name().substring(0,1).toUpperCase() + layer.name().substring(1).toLowerCase();
				try{
					Object constantsObj = base.B2O((byte[])requestAndGetService(getContainer(serviceName1, false),serviceName1,params,10000000));
					constants[layer.ordinal()][view.ordinal()] = (double[])constantsObj;
				} catch ( Exception e) {
					e.printStackTrace();
				}
			}
		}
		return constants;
    }


    @Override
    public void run()
    {
    	double[][][] atten = readAttenMap(subsystem);
        
        for (int sector = 0; sector < ECGeneral.MAX_SECTORS; sector++) {
			for (ECLayer.Name layer: ECLayer.Name.values()) {
				if ( layer.ordinal() == 3) continue;
				for (ECView.Label view: ECView.Label.values()) {
					for (int strip = 0; strip < ECGeneral.MAX_EC_STRIPS; strip++) {
							dataArray.getData(sector, strip, layer, view).setAtten(atten[layer.ordinal()][view.ordinal()][sector+strip]);
						
					}
				}
			}
		}
    }
}
