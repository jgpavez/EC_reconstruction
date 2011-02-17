package org.ec.detector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.ec.bos.ECEvu;


/**
 * The <code>View</code> class represents an orientation or view in the EC
 * detector. The views belongs to one {@link ECLayer layer}, and they are used
 * to store the layer data for each of the three views in the detector.
 * <p>
 * Each view has a list of strips objects (not the full number of strips, just
 * the ones with information) and a list of peaks (created from the strips).
 * It also has a label to identify it.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 15, 2011</font>
 *
 * @author      smancill
 * @version     0.1
 * @see         ECStrip
 * @see         ECFitPeak
 */
public class ECView
{
    private ECViewLabel label;
    private String      key;

    private ArrayList<ECEvu>     evuList;
    private ArrayList<ECStrip>   stripList;

    // TODO Need a better way to store this calibration information
    public double[] calEch;
    public double[] calAtten;
    public double[] calEo;

    public double[] calTch;
    public double[] calTo;
    public double[] calTadc;
    public double[] caldT1;
    public double[] caldT2;
    public double[] calTrms;
    public double[] calTDCStat;


    /**
     * Construct an object representing the EC view of the given label.  This
     * view is part of a layer object and is also identified by an unique
     * key constructed using the layer key and the view label.  This view does
     * not have any information about the strips on it.  That information need
     * to be added.
     *
     * @param label     the label identifying the view
     * @param layerKey  the key of the layer that the view belongs
     */
    public ECView(ECViewLabel label, String layerKey)
    {
        this.label      = label;
        this.key        = layerKey + "." + label;
        this.evuList    = new ArrayList<ECEvu>();
        this.stripList  = new ArrayList<ECStrip>();

        this.calEch     = new double[ECGeneral.MAX_STRIPS];
        this.calAtten   = new double[ECGeneral.MAX_STRIPS];
        this.calEo      = new double[ECGeneral.MAX_STRIPS];
        this.calTch     = new double[ECGeneral.MAX_STRIPS];
        this.calTo      = new double[ECGeneral.MAX_STRIPS];
        this.calTadc    = new double[ECGeneral.MAX_STRIPS];
        this.caldT1     = new double[ECGeneral.MAX_STRIPS];
        this.caldT2     = new double[ECGeneral.MAX_STRIPS];
        this.calTrms    = new double[ECGeneral.MAX_STRIPS];
        this.calTDCStat = new double[ECGeneral.MAX_STRIPS];
    }


    /**
     * Add a new {@link ECEvu Evu} object, with the data of the BOS file.
     * This object containts the TDC and ADC information for one strip of the
     * view.
     *
     * @param e  the Evu object to be added
     */
    public void addEvu(ECEvu e)
    {
        evuList.add(e);
    }


    /**
     * Get the list of {@link ECEvu Evu} objects of this view, to iterate over
     * it.  The list of {@link ECStrip strips} can be generated using its
     * information.
     *
     * @return  a {@link Collection} with the EVu objects of the view
     */
    public Collection<ECEvu> getEvuList()
    {
       return Collections.unmodifiableList(evuList);
    }


    /**
     * Add a new strip to the list of strips of the view.
     *
     * @param s  the strip object to be added
     */
    public void addStrip(ECStrip s)
    {
        stripList.add(s);
    }


    /**
     * Get the number of strips with information in the view.
     *
     * @return  the number of strips
     */
    public int getNStrips()
    {
        return stripList.size();
    }


    /**
     * Get the list of strips of the view to iterate over it.
     *
     * @return  a {@link Collection} with the strips of the view
     */
    public Collection<ECStrip> getStripList()
    {
       return Collections.unmodifiableList(stripList);
    }


    /**
     * Get the label identifying the view.
     *
     * @return  the label of the view.
     */
    public ECViewLabel getLabel()
    {
        return label;
    }
}
