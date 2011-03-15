package org.ec.calibration;

import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayerName;
import org.ec.detector.ECViewLabel;
import org.jlab.coda.clara.core.ClaraUser;

/**
 * 
 * The <code> ECAdcCalibration </code> class load the information of the EC calibration
 * map, in specific the <em>EC_GAIN</em> and <em>EC_PEDESTAL</em> system info, into the
 * <code>ECCalibrationArray</code>, filling the <em>Eo</em> and <em>Ech</em> variables.
 * 
 * Implements runnable, because is called in parallel with the other calibration classes.
 * 
 * 
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 15, 2011</font>
 * 
 * @author jgpavez
 *
 */
public class ECAdcCalibration extends ClaraUser implements Runnable
{
	
	ECCalibrationDataArray dataArray;
	
	
	/**
	 * Construct the object, only set the <em>ECCalibrationDataArray</em> data object to his
	 * own attribute
	 * 
	 * @param data
	 */
	public ECAdcCalibration( ECCalibrationDataArray data)
	{
		dataArray = data;
	}
	

	/**
	 * Read the ADC Map database. TODO
	 * 
	 */
	public void readAdcMap()
	{
		// TODO
	}
	
	@Override
	public void run()
	{
		readAdcMap();

	}
}
