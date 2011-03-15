package org.ec.calibration;

import java.io.Serializable;

import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayerName;
import org.ec.detector.ECView;
import org.ec.detector.ECViewLabel;

/**
 * The <code> ECCalibrationData </code> class encapsulate the calibration data
 * for a strip, in a layer, in a view, in a sector. Also define the default
 * values for the calibration variables
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), March 14, 2011</font>
 *
 * @author      jgpavez
 * @version     0.1
 */
public class ECCalibrationData implements Serializable
{
    private static final long serialVersionUID = 1L;

    private int stripID;
    private ECLayerName layer;
    private ECViewLabel view;
    private int sectorID;

    private double ech;
    private double eo;
    private double atten;
    private double tch;
    private double to;
    private double tadc;
    private double dt1;
    private double dt2;
    private double trms;
    private double tdcStat;


    /**
     * Construct a calibration object for a single strip, layer, view and
     * sector.  Also define the default values for the calibration object
     *
     * @param strip
     * @param layer
     * @param view
     * @param sector
     */
    public ECCalibrationData(int strip, ECLayerName layer, ECViewLabel view, int sector)
    {
        this.stripID  = strip;
        this.layer    = layer;
        this.view     = view;
        this.sectorID = sector;

        // Set Default values
        eo          =   0.0;
        ech         =   ECGeneral.DEFAULT_ECH;
        tch         =   ECGeneral.DEFAULT_TCH;
        to          =   0.0;
        tadc        =   0.0;
        dt1         =   0.0;
        dt2         =   0.0;
        trms        =   ECGeneral.DEFAULT_TRMS;
        tdcStat     =   0.0;
        atten       =   ECGeneral.DEFAULT_ATTEN;
    }


    /**
     *
     * @param stripID
     */
    public void setStripID(int stripID)
    {
        this.stripID = stripID;
    }


    public int getStripID()
    {
        return stripID;
    }


    /**
     * @return the layer
     */
    public ECLayerName getLayer()
    {
        return layer;
    }


    /**
     * @param layer the layer to set
     */
    public void setLayer(ECLayerName layer)
    {
        this.layer = layer;
    }


    /**
     * @return the ecCoord
     */
    public ECViewLabel getView()
    {
        return view;
    }


    /**
     * @param ecCoord the ecCoord to set
     */
    public void setView(ECViewLabel view)
    {
        this.view = view;
    }


    /**
     * @return
     */
    public int getSectorID()
    {
        return sectorID;
    }



    /**
     * @param sectorID
     */
    public void setSectorID(int sectorID)
    {
        this.sectorID = sectorID;
    }

    /**
     * Gev/ch
     * @return the ech
     */
    public double getEch()
    {
        return ech;
    }


    /**
     * @param ech the ech to set
     */
    public void setEch(double ech)
    {
        this.ech = ech;
    }


    /**
     * Gev/ped
     * @return the eo
     */
    public double getEo()
    {
        return eo;
    }


    /**
     * @param eo the eo to set
     */
    public void setEo(double eo)
    {
        this.eo = eo;
    }


    /**
     * Get the Attenuation argument
     * @return the atten
     */
    public double getAtten()
    {
        return atten;
    }


    /**
     * Set the Attenuation argument
     *
     * @param  atten the atten to set
     */
    public void setAtten(double atten)
    {
        this.atten = atten;
    }


    /**
     * Get the Timing calibration constant. nS/ch-
     *
     * @return  the tch
     */
    public double getTch()
    {
        return tch;
    }


    /**
     * Set the Timing calibration constant.
     *
     * @param tch  the tch to set
     */
    public void setTch(double tch)
    {
        this.tch = tch;
    }


    /**
     * Get Timing calibration constant. nS offset.
     *
     * @return  the to
     */
    public double getTo()
    {
        return to;
    }


    /**
     * Set Timing calibration constant. nS offset.
     *
     * @param to  the to to set
     */
    public void setTo(double to)
    {
        this.to = to;
    }


    /**
     * ADC dependant term
     *
     * @return  the tdc
     */
    public double getTadc()
    {
        return tadc;
    }


    /**
     * @param  tdc the tdc to set
     */
    public void setTadc(double tadc)
    {
        this.tadc = tadc;
    }


    /**
     * Get Time Walk term 1
     *
     * @return  the dt1
     */
    public double getDt1()
    {
        return dt1;
    }


    /**
     * Set Time Walk term 1
     * @param dt1  the dt1 to set
     */
    public void setDt1(double dt1)
    {
        this.dt1 = dt1;
    }


    /**
     * Time Walk term 2
     * @return  the dt2
     */
    public double getDt2()
    {
        return dt2;
    }


    /**
     * @param dt2 the dt2 to set
     */
    public void setDt2(double dt2)
    {
        this.dt2 = dt2;
    }


    /**
     * @return the trms
     */
    public double getTrms()
    {
        return trms;
    }


    /**
     * Rms
     *
     * @param trms  the trms to set
     */
    public void setTrms(double trms)
    {
        this.trms = trms;
    }


    /**
     * Get the TDC status
     * @return the tdcStat
     */
    public double getTdcStat()
    {
        return tdcStat;
    }


    /**
     * Set the TDC status.
     *
     * @param tdcStat  the tdcStat to set
     */
    public void setTdcStat(double tdcStat)
    {
        this.tdcStat = tdcStat;
    }
}
