package org.ec.services;

import org.ec.detector.ECSector;
import org.ec.util.ECMatchFinder;
import org.jlab.coda.clara.core.CServiceParameter;
import org.jlab.coda.clara.core.ICService;

/**
 * The <code>MatchFinderService</code> find matches of hits between layers.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 24, 2011</font>
 *
 * @author      smancill
 * @version     0.1
 */
public class MatchFinderService implements ICService
{

    public void configure(CServiceParameter arg0)
    {
        // TODO Auto-generated method stub
    }


    public Object executeService(int arg0, Object arg1)
    {
        ECSector      sector = (ECSector) arg1;
        ECMatchFinder matches = new ECMatchFinder(sector);

        matches.projectAllHits();
        matches.matchInnerLayer();
        matches.matchOuterLayer();

        return sector;
    }


    public Object executeService(int[] arg0, Object[] arg1)
    {
        // TODO Auto-generated method stub
        return null;
    }


    public String getAuthor()
    {
        return "Sebastián Mancilla";
    }


    public String getDescription()
    {
        return "Find matches of hits between layers";
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
        return "MatchFinder";
    }


    public int getOutputType()
    {
        // TODO Auto-generated method stub
        return 0;
    }


    public String getVersion()
    {
        return "0.1";
    }

}
