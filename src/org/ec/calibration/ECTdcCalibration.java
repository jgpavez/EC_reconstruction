package org.ec.calibration;

import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECView;
import org.jlab.coda.clara.core.ClaraUser;
import org.jlab.coda.clara.system.ABase;

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
    String system = "EC_CALIB";
    String subsystem1 = "EC_Tch";
    String subsystem2 = "EC_To";
    String subsystem3 = "EC_Tadc";
    String subsystem4 = "EC_dT1";
    String subsystem5 = "EC_dT2";
    String subsystem6 = "EC_Trms";
    String subsystem7 = "TDCstat";



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
     * Read the TDC Map database. Need the subSystem name to construct the query and return
     * all constants for each layer and view in a float array.
     * @return 
     */
    public double[][][] readTdcMap(String subSystem)
    {
        connect();
        ABase base = new ABase();
        String containerName = "ECCALIBRATION";
        double[][][] constants;
        	
        createServiceContainer(containerName);
        constants = new double[4][3][];
        
        Object[] params = new Object[9];
        params[0] = system;
        params[1] = subSystem;
        params[3] = null;
        params[4] = "2037-1-1";
        params[5] = 28000;
        params[6] = "";
        params[7] = "";
        params[8] = "";
        
        while ( getServiceContainer(containerName) == null ){
        	sleep(100);
        }
        String serviceName1 = addService(containerName, "org.ec.maputil.MapUtilService");
        
        while ( (getServiceOutputType(serviceName1,false) <= 0)
        		&& ( getServiceOutputType(serviceName1, false)) <= 0 ){
        			System.out.println("... Waiting for service to register");
        			sleep(1000);
        }
		for (ECLayer.Name layer: ECLayer.Name.values()) {
			if ( layer.ordinal() == 3 || layer.ordinal() == 0) continue;
			for (ECView.Label view: ECView.Label.values()) {
		        params[2] = layer.name().substring(0,1).toUpperCase() + layer.name().substring(1).toLowerCase();
				try{
					Object constantsObj = base.B2O((byte[])requestAndGetService(getContainer(serviceName1, false),serviceName1,params,10000000));
					constants[layer.ordinal()][view.ordinal()] = (double[])constantsObj;
				} catch ( Exception e) {
					e.printStackTrace();
				}
			}
		}
		return constants;
    }


    @Override
    public void run()
    {
  
        double[][][] tch  		= readTdcMap(subsystem1);
        double[][][] to   		= readTdcMap(subsystem2);
        double[][][] tadc 	  	= readTdcMap(subsystem3);
        double[][][] dt1    	= readTdcMap(subsystem4);
        double[][][] dt2    	= readTdcMap(subsystem5);
        double[][][] trms 	 	= readTdcMap(subsystem6);
        double[][][] tdcstat 	= readTdcMap(subsystem7);
        
        for (int sector = 0; sector < ECGeneral.MAX_SECTORS; sector++) {
			for (ECLayer.Name layer: ECLayer.Name.values()) {
				if ( layer.ordinal() == 3 || layer.ordinal() == 0 ) continue;
				for (ECView.Label view: ECView.Label.values()) {
					for (int strip = 0; strip < ECGeneral.MAX_EC_STRIPS; strip++) {
							dataArray.getData(sector, strip, layer, view).setTch(tch[layer.ordinal()][view.ordinal()][sector+strip]);
							dataArray.getData(sector, strip, layer, view).setTo(to[layer.ordinal()][view.ordinal()][sector+strip]);
							dataArray.getData(sector, strip, layer, view).setTadc(tadc[layer.ordinal()][view.ordinal()][sector+strip]);
							dataArray.getData(sector, strip, layer, view).setDt1(dt1[layer.ordinal()][view.ordinal()][sector+strip]);
							dataArray.getData(sector, strip, layer, view).setDt2(dt2[layer.ordinal()][view.ordinal()][sector+strip]);
							dataArray.getData(sector, strip, layer, view).setTrms(trms[layer.ordinal()][view.ordinal()][sector+strip]);
							dataArray.getData(sector, strip, layer, view).setTdcStat(tdcstat[layer.ordinal()][view.ordinal()][sector+strip]);
						
					}
				}
			}
		}
    }
}
