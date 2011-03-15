package org.ec.calibration;

import org.jlab.coda.clara.core.ClaraUser;

/**
 * The <code> ECAdcCalibration </code> class load the information of the EC
 * calibration map, in specific the <em>EC_GAIN</em> and <em>EC_PEDESTAL</em>
 * system info, into the <code>ECCalibrationArray</code>, filling the
 * <em>Eo</em> and <em>Ech</em> variables.
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
public class ECAdcCalibration extends ClaraUser implements Runnable
{
    ECCalibrationDataArray dataArray;


    /**
     * Construct the object, only set the <em>ECCalibrationDataArray</em> data
     * object to his own attribute
     *
     * @param data
     */
    public ECAdcCalibration(ECCalibrationDataArray data)
    {
        dataArray = data;
    }


    /**
     * Read the ADC Map database.
     */
    public void readAdcMap()
    {
        // TODO read the ADC Map database
    }

    @Override
    public void run()
    {
        readAdcMap();
    }
}
