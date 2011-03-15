package org.ec.calibration;

import org.jlab.coda.clara.core.ClaraUser;

/**
 * The <code> ECTdcCalibration </code> class load the information of the EC
 * calibration map, in specific the <em>EC_To</em>, <em>EC_Tadc</em>,
 * <em>EC_dT1</em>, <em>EC_dT2</em>, <em>EC_Trms</em> and <em>TDCstat</em>
 * system info, into the <code>ECCalibrationArray</code>, filling the
 * <em>To</em>, <em>Tadc</em>, <em>dT1</em>, <em>dT2m</em>, <em>Trms</em> and
 * <em>TDCstat</em> variables.
 * <p>
 * Implements runnable, because is called in parallel with the other calibration
 * classes.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), March 14, 2011</font>
 *
 * @author jgpavez
 * @version 0.1
 */
public class ECTdcCalibration extends ClaraUser implements Runnable
{
    ECCalibrationDataArray dataArray;


    /**
     * Construct the object, only set the <em>ECCalibrationDataArray</em> data
     * object to the own attribute
     *
     * @param data
     */
    public ECTdcCalibration( ECCalibrationDataArray data )
    {
        dataArray = data;
    }


    /**
     * Read the TDC Map database.
     */
    public void readTdcMap()
    {
        // TODO Read the TDC Map database.
    }


    @Override
    public void run()
    {
        readTdcMap();
    }
}
