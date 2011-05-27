package org.ec.maputil;

/**
 * The MapUtilService makes the connection to the calibration mysql database at
 * jdbc:mysql:clasdb.jlab.org and return the constants for each Item.
 * 
 * The service can be configured receiving the hostname and the database, by default
 * this are set to clasdb and calib respectively.
 * 
 * The execution of the service can receive two set of parameters:
 * 
 * System name, subsystem name, item name, runIndexTable ,date and run number
 * In this case return a float array with the constants for the item for the given run number and date.
 * The RunIndexTable is set to RunIndex by default.
 * 
 * System name, subsystem name, item name, runIndexTable, date,runMin, runMax and channel.
 * In this case return a float array with the channel data for the information given.
 * The RunIndexTable is set to RunIndex by default.
 *  
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 15, 2011</font>
 * 
 * @author jgpavez
 *
 */

import org.jlab.coda.clara.core.CServiceParameter;
import org.jlab.coda.clara.core.ICService;

public class MapUtilService implements ICService
{
	private String hostname = "clasdb";
	private String database = "calib";
	
	/*
	 * Receive a new hostname and database to make the connection.
	 * This parameters are received like CServiceParameter variable,
	 * @param params
	 */
	@Override
	public void configure(CServiceParameter params)
	{
		if ( params.getType() == STRING_ARRAY ){
			String[] config = (String[])params.getValue();
			if ( config[0] != "" )	this.hostname = config[0];
			if ( config[1] != "" )  this.database = config[1];
			System.out.println("Hostname = " + this.hostname);
			System.out.println("Database = " + this.database);
		}
	}

	/*
	* This function works in two ways:
	* If receive system name, subSystem name, item name, runIndexTable(optional), date and run number (the others 
	* parameters are received like empty string) return the constants for the given item, like a float
	* array
	* 
	* If receive system name, subSystem name, item name, runIndexTable(optional), date, runMin, runMax and channel
	* ( the other parameters are set to empty string) return the data for the channel data given, like a 
	* float array.
	* 
	* In both cases runIndexTable is optional and is set by default to RunIndex
	*
	* @see org.jlab.coda.clara.core.ICService#executeService(int, java.lang.Object)
	*/
	@Override
	public Object executeService(int type, Object input)
	{
		String runIndexTable;
		System.out.println("STARTING MAPUTIL FUNCTION");
		double[] returns;
		RSystemsCaldb caldb = loadCaldbSystem();
		if ( caldb == null ) return null;
	    Object array[] = new Object[9];
	    array[0] = "EC_CALIB";
		array[1] = "EC_GAIN";
		array[2] = "InnerU";
		array[3] =  null;
		array[4] = "2037-1-1";
		array[5] = 28000;
		array[6] = "";
		array[7] = "";
		array[8] = "";
		if ( type == OBJECT_ARRAY ){
		//	Object[] array = (Object[])input;
			if ( array[0] != null && array[1] != null && array[2] != null && array[4] != null && 
				(( array[5] != null) || (array[6] != null && array[7] != null && array[8] != null))){
				if ( array[3] == null || array[3] == "" ) runIndexTable = "RunIndex";
				else runIndexTable = (String)array[3];
				try{
					if ( array[5] != null ){
						System.out.println("Starting the connection");
						RConstantSet constants = caldb.getConstants((String)array[0], (String)array[1], (String)array[2], 
												runIndexTable, (String)array[4], (Integer)array[5]);
						returns = constants.getAsDoubleArray();
						System.out.println("Connection done");
						return returns;
					}
					else {
						RChannelData channeldata = caldb.getChannelData((String)array[0], (String)array[1], (String)array[2], 
												runIndexTable, (String)array[4], (Integer)array[6], (Integer)array[7], (String)array[8]);
						returns = channeldata.getAsDoubleArray();
						return returns;
					}	
				} catch(Exception e) { e.printStackTrace(); }
			} else { System.out.println(" ERROR: Some input data missing "); }
		} else { System.out.println(" ERROR: Wrong input type "); }
		return null;
	}

	@Override
	public Object executeService(int[] arg0, Object[] arg1)
	{
		return null;
	}

	@Override
	public String getAuthor()
	{
		return "jgpavez";
	}

	@Override
	public String getDescription()
	{
		return "MapUtil service to load calibration constants";
	}

	@Override
	public int getInputType()
	{
		return OBJECT_ARRAY;
	}

	@Override
	public int[] getInputTypes()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return "MapUtilService";
	}

	@Override
	public int getOutputType()
	{
		return OBJECT;
	}

	@Override
	public String getVersion()
	{
		return "0.1";
	}
	
	/* 
	 * Configure the connection information and return a RSystemCaldb object 
	 * that control the connection with the database
	 */
	private RSystemsCaldb loadCaldbSystem(){
		String caldburl = "jdbc:mysql://" + hostname + ".jlab.org/" + database;
		RSystemsCaldb caldb = new RSystemsCaldb();
	    caldb.setDriver("org.gjt.mm.mysql.Driver"); // Change this rute
	    caldb.setURL(caldburl);
	    caldb.setUsername("clasuser");
	    caldb.setPassword("");
		try{
	    caldb.loadSystems();
		} catch ( Exception e ){
			e.printStackTrace();
			return null;
		}
		return caldb;
	}

}
