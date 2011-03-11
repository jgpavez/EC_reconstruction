package org.ec.deployment;

import java.util.ArrayList;

import org.ec.detector.ECSector;
import org.jlab.coda.clara.core.CServiceRegistration;
import org.jlab.coda.clara.core.ClaraUser;


/**
 * The <code>ECReconstruction</code> find hits in the EC detector.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Mar 8, 2011</font>
 *
 * @author      smancill
 * @version     0.1
 */
public class ECReconstruction extends ClaraUser
{
    private String container = "EC_container";

    private String fillStrips;
    private String findHits;
    private String findMatches;

    private ArrayList<ECSector> detector;

    public static void main(String[] args)
    {
        ECReconstruction ec = new ECReconstruction();

        ec.startServices();
        ec.linkServices();
        ec.runReconstruction();
    }


    public ECReconstruction()
    {
        detector   = new ArrayList<ECSector>();
        for (int i = 1; i <= 6; i++)
            detector.add(new ECSector(i));

        connect();
    }


    public void runReconstruction()
    {
        for (ECSector sector : detector) {
            requestService(container, fillStrips, sector, sector.getID());
            System.out.println("Started chain for sector " + sector.getID());
        }
    }


    public void startServices()
    {
        if (getServiceContainer(container) == null)
            createServiceContainer(container);

        int TIME_OUT_LIMIT = 10;

        int timeout = 0;
        while (getServiceContainer(container) == null && timeout < TIME_OUT_LIMIT) {
            waitOneSecond();
            timeout++;
        }

        if (timeout >= TIME_OUT_LIMIT) {
            System.out.println("Timeout error: could not start container");
            return;
        } else {
            System.out.println("Container started");
        }

        // Add all services
        fillStrips  = addService(container, "org.ec.services.StripsCreatorService");
        findHits    = addService(container, "org.ec.services.HitsFinderService");
        findMatches = addService(container, "org.ec.services.MatchFinderService");

        // Wait for all services to start
        ArrayList<String> serviceList = new ArrayList<String> ();
        serviceList.add(fillStrips);
        serviceList.add(findHits);
        serviceList.add(findMatches);

        TIME_OUT_LIMIT = 40;
        timeout = 0;
        boolean allFound = false;
        while (!allFound && timeout < TIME_OUT_LIMIT) {
            for (CServiceRegistration service : getServiceContainer(container).getServices()) {
                String serviceName = service.getRegistrationName();
                if (serviceList.contains(serviceName)) {
                    System.out.println("Service " + serviceName + " started.");
                    serviceList.remove(serviceName);
                }
            }
            if (serviceList.size() > 0) {
                timeout++;
                waitOneSecond();
            } else {
                allFound = true;
            }
        }

        if (timeout >= TIME_OUT_LIMIT)
            System.out.println("Timeout error: could not start services");
    }


    public void linkServices()
    {
        joinServices(container, fillStrips, container, findHits);
        joinServices(container, findHits,   container, findMatches);
    }


    private void waitOneSecond()
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
