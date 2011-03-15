package org.ec.services;

import org.ec.calibration.ECAdcCalibration;
import org.ec.calibration.ECAttenCalibration;
import org.ec.calibration.ECCalibrationData;
import org.ec.calibration.ECCalibrationDataArray;
import org.ec.calibration.ECTdcCalibration;
import org.ec.detector.ECLayerName;
import org.ec.detector.ECViewLabel;
import org.ec.detector.ECGeneral;
import org.jlab.coda.clara.core.CServiceParameter;
import org.jlab.coda.clara.core.ICService;

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
        ECCalibrationDataArray calibrationArray = new ECCalibrationDataArray();

        for (int sector = 0; sector < ECGeneral.MAX_SECTORS; sector++) {
            for (ECLayerName layer: ECLayerName.values()) {
                for (ECViewLabel view: ECViewLabel.values()) {
                    for (int strip = 0; strip < ECGeneral.MAX_STRIPS; strip++) {
                        calibrationArray.putIfAbsent(calibrationArray.getKey(strip, layer, view,sector), new ECCalibrationData(strip, layer, view, sector));
                    }
                }
            }
        }

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

        return calibrationArray;
    }


    public Object executeService(int[] arg0, Object[] arg1)
    {
        // TODO Auto-generated method stub
        return null;
    }


    public String getAuthor()
    {
        // TODO Auto-generated method stub
        return null;
    }


    public String getDescription()
    {
        // TODO Auto-generated method stub
        return null;
    }


    public int getInputType()
    {
        // TODO Auto-generated method stub
        return 0;
    }


    public int[] getInputTypes()
    {
        // TODO Auto-generated method stub
        return null;
    }


    public String getName()
    {
        // TODO Auto-generated method stub
        return null;
    }


    public int getOutputType()
    {
        // TODO Auto-generated method stub
        return 0;
    }


    public String getVersion()
    {
        // TODO Auto-generated method stub
        return null;
    }
}
