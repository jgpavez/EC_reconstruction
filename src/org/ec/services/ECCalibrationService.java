package org.ec.services;

import org.ec.calibration.ECAdcCalibration;
import org.ec.calibration.ECAttenCalibration;
import org.ec.calibration.ECCalibrationData;
import org.ec.calibration.ECCalibrationDataArray;
import org.ec.calibration.ECTdcCalibration;
import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECView;
import org.jlab.coda.clara.core.CServiceParameter;
import org.jlab.coda.clara.core.ICService;
import org.jlab.coda.clara.system.AConstants;

/**
 * The <code>ECCalibrationService</code> service load the calibration data for
 * the EC. This service create a <code>ECCalibrationDataArray</code> object and
 * fill it with CalibrationData objects for each strip, layer, view and sector.
 * After that call the ECAdcCalibration, ECAttenCalibration and the
 * ECTdcCalibration classes to load and calculate the calibration data. Each of
 * this classes are called in parallel, because each one modify only part of the
 * ECCalibrationDataArray object.
 * <p>
 * When the calibration is loaded returns a CalibratoinDataArray object.
 * Still not working like service.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), March 14, 2011</font>
 *
 * @author jgpavez
 * @version 0.1
 */
public class ECCalibrationService implements ICService
{
    public void configure(CServiceParameter arg0)
    {
        // TODO Auto-generated method stub
    }

    public Object executeService(int arg0, Object arg1)
    {
    	System.out.println("Starting calibration loader... ");
    	ECCalibrationDataArray[] dataArray = new ECCalibrationDataArray[ECGeneral.MAX_SECTORS];
    	for ( int sector = 0; sector < ECGeneral.MAX_SECTORS; sector++ ){
    		ECCalibrationDataArray calibrationArray = new ECCalibrationDataArray( sector );
        
            for (ECLayer.Name layer: ECLayer.Name.values()) {
                for (ECView.Label view: ECView.Label.values()) {
                    for (int strip = 0; strip < ECGeneral.MAX_STRIPS; strip++) {
                        calibrationArray.putIfAbsent(calibrationArray.getKey(strip, layer, view), new ECCalibrationData(strip, layer, view ));
                    }
                }
            }
            // TODO: Problem Thread inside service?
            Thread adcCalibration   = new Thread(new ECAdcCalibration(calibrationArray));
            Thread tdcCalibration   = new Thread(new ECTdcCalibration(calibrationArray));
            Thread attenCalibration = new Thread(new ECAttenCalibration(calibrationArray));

            System.out.println("Starting calibration Process");
            adcCalibration.start();
            tdcCalibration.start();
            attenCalibration.start();

            while (adcCalibration.isAlive() || tdcCalibration.isAlive() || attenCalibration.isAlive()) {
            // Wait.
            }
        
            dataArray[sector] = calibrationArray;
    	
    	}
        System.out.println(" Calibration Process finish...");
        return dataArray;
    }


    public Object executeService(int[] arg0, Object[] arg1)
    {
        return null;
    }


    public String getAuthor()
    {
        return "jgpavez";
    }


    public String getDescription()
    {
        return "Load the Calibration Data";
    }


    public int getInputType()
    {
        return AConstants.OBJECT;
    }


    public int[] getInputTypes()
    {
        return null;
    }


    public String getName()
    {
        return "ECCalibrationService";
    }


    public int getOutputType()
    {
        return AConstants.OBJECT_ARRAY;
    }


    public String getVersion()
    {
        return "0.1";
    }
}
