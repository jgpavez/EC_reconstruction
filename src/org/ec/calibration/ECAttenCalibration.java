package org.ec.calibration;

import org.jlab.coda.clara.core.ClaraUser;

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
     * Read the Atten Map database.
     */
    public void readAttenMap()
    {
        // TODO Read the Atten Map database
    }


    @Override
    public void run()
    {
        readAttenMap();
    }
}
