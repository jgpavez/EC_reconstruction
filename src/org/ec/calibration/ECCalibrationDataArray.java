package org.ec.calibration;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECView;
import org.jlab.coda.jevio.EvioEvent;


/**
 * The <code>ECCalibrationDataArray</code> class represent the calibration for
 * all strips, layers, views and sectors. All the calibration objects in the
 * Array are mapped into a ConcurrentHashMap, where the key depends on the strip, layer,
 * view and sector of the calibration object. This key is calculated with the
 * <code> getKey </code>method
 *
 * <p><font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 15, 2011</font>
 *
 * @author jgpavez
 *
 */
public class ECCalibrationDataArray
            extends ConcurrentHashMap<Integer, ECCalibrationData>
            implements Serializable
{
    private static final long serialVersionUID = 4859368109715439088L;


    /**
     * Convert a Evio event into a CalibrationDataArray object.
     *
     * @param input
     * @return
     */
    public static ECCalibrationDataArray EvioToCalibrationData(EvioEvent input)
    {
        // TODO Convert the Evio event into an CalibrationDataArray
        return null;
    }


    /**
     * Convert a CalibrationDataArray object into a Evio event.
     *
     * @param input
     * @return
     */
    public static EvioEvent CalibrationDataToEvio( ECCalibrationDataArray input)
    {
        // TODO Convert the CalibrationDataArray to an Evio event
        return null;
    }


    /**
     * Calculates the Integer key used to get and put data in the HashMap. This
     * key is calculated with the strip, layer, view and sector, with a simple
     * and linear formula that not produce collisions of keys.
     *
     * @param stripID
     * @param layer
     * @param view
     * @return
     */
    public Integer getKey(int stripID, ECLayer.Name layer, ECView.Label view , int sectorID)
    {
        return new Integer( stripID +
                          ( ECGeneral.MAX_STRIPS * layer.ordinal() ) +
                          ( ECGeneral.MAX_STRIPS * 4 * view.ordinal() ) +
                          ( ECGeneral.MAX_STRIPS * 4 * 4 * ECGeneral.MAX_SECTORS ));
    }


    /**
     * Get a CalibrationData object from the HashMap, converting the strip,
     * layer, view and sector information into a key to search in the HashMap
     *
     * @param stripID
     * @param layer
     * @param view
     * @return
     */
    public ECCalibrationData getData(int stripID, ECLayer.Name layer, ECView.Label view, int sectorID)
    {
        return this.get(getKey(stripID, layer, view, sectorID));
    }


    /**
     * Put a CalibrationData object in the array, using the strip, layer, view
     * and sector of the calibration object to get the key to insert into the
     * HashMap
     *
     * @param input
     * @return
     */
    public boolean putData(ECCalibrationData input)
    {
        ECCalibrationData result = this.put(getKey(input.getStripID(), input.getLayer(), input.getView(), input.getSectorID()), input );

        if (result == null)
            return false;
        else
            return true;
    }
}
