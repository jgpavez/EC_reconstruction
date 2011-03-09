package org.ec.services;

import java.util.ArrayList;

import org.ec.bos.ECEvu;
import org.ec.detector.ECLayer;
import org.ec.detector.ECSector;
import org.ec.detector.ECStrip;
import org.ec.detector.ECView;

import org.jlab.coda.clara.core.CServiceParameter;
import org.jlab.coda.clara.core.ICService;


public class ECCalStripsService implements ICService
{
    public void configure(CServiceParameter arg0)
    {
        // TODO Auto-generated method stub
    }


    public Object executeService(int arg0, Object arg1)
    {
        ECSector sector = (ECSector) arg1;
        ArrayList<ECLayer> layers = (ArrayList<ECLayer>) sector.getLayerList();

        // For each one of the layers INNER, OUTER and COVER
        for (ECLayer layer : layers.subList(1, layers.size())) {
            for (ECView view : layer.getViewList()) {
                for (ECEvu evu : view.getEvuList()) {
                    int    idEvu  = evu.getID();
                    double dADC   = evu.getADC();
                    double dTDC   = evu.getTDC();
                    double rawADC = dADC - view.calEo[idEvu];    // Save ADC minus pedestal
                    double rawTDC = dTDC;                        // Save raw TDC value
                    double energy = rawTDC * view.calEch[idEvu];
                    if (energy < 0) energy = 0;

                    // Create new strip with the given ID and energy.
                    ECStrip strip = new ECStrip(idEvu, energy);

                    double time;
                    if (dTDC > 0 && dTDC < 9000 && rawADC > 0)
                         // Filter out bad TDC. Upper limit for dTDC was
                         // changed from 4096 to 9000 because of the new multihit
                         // pipeline TDC
                         //
                         // H.S. Jo November 2005
                        time = dTDC * view.calTch[idEvu] + view.calTo[idEvu]
                                + view.calTadc[idEvu] / Math.sqrt(rawADC);
                    else
                        time = -999;

                    strip.setTime(time);
                    strip.setRawAdcs(rawADC);
                    view.addStrip(strip);
                }
            }
        }

        // TODO Fill WHOLE layer

        return sector;
    }


    @Override
    public Object executeService(int[] arg0, Object[] arg1) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getAuthor() {
        // TODO Auto-generated method stub
        return "Ricardo Oyarzun";
    }


    @Override
    public String getDescription() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public int getInputType() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public int[] getInputTypes() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "ECCalStripService";
    }


    @Override
    public int getOutputType() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public String getVersion() {
        // TODO Auto-generated method stub
        return "1.0";
    }
}
