package org.ec.services;

import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECSector;
import org.ec.util.ECHitMaps;
import org.ec.util.ECHitsFinder;
import org.ec.util.ECPeaksFinder;
import org.jlab.coda.clara.core.CServiceParameter;
import org.jlab.coda.clara.core.ICService;
import org.jlab.coda.clara.system.AConstants;

/**
 * The <code>HitsFinderService</code> find the hits in one sector.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 22, 2011</font>
 *
 * @author      smancill
 * @version     0.1
 */
public class HitsFinderService implements ICService
{
    public void configure(CServiceParameter arg0)
    {
        // TODO Auto-generated method stub
    }


    public Object executeService(int arg0, Object arg1)
    {
        ECSector      sector = (ECSector) arg1;
        ECHitMaps     maps   = new ECHitMaps();
        ECPeaksFinder stp    = new ECPeaksFinder(sector, maps);
        ECHitsFinder  pth    = new ECHitsFinder(sector, maps);

        for (ECLayer layer : sector.getLayerList()) {
            if (layer.getName() == ECLayer.Name.COVER) {
                layer.setMaxStrips(ECGeneral.MAX_EC_STRIPS);
            } else {
                layer.setMaxStrips(ECGeneral.MAX_PCAL_STRIPS);
            }

            stp.findPeaks(layer);
            pth.initializePeakStatus(layer);
            while (pth.calculate()) {
                pth.findHits(layer);
                if (layer.getNHits() > 0) {
                    // At this point, all possible hits are identified and
                    // paths for each hit on the peak are defined. Time for
                    // attenuation correction
                    stp.correctPeaks(layer);
                    pth.correctEnergy(layer);
                }
            }
            pth.correctHits(layer);

        }

        return sector;
    }


    public Object executeService(int[] arg0, Object[] arg1)
    {
        return null;
    }


    public String getAuthor()
    {
        return "Sebastián Mancilla";
    }


    public String getDescription()
    {
        return "Find the hits in one sector";
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
        return "HitsFinder";
    }


    public int getOutputType()
    {
        return AConstants.OBJECT;
    }


    public String getVersion()
    {
        return "0.1";
    }

}
