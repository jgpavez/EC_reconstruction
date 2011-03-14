package org.ec.services;

import org.ec.calibration.ECGeometry;
import org.jlab.coda.clara.core.CServiceParameter;
import org.jlab.coda.clara.core.ICService;

/**
 * The <code>ECGeometryService</code> service, load the geometry data
 * for EC and return it like a ECGeometry object.
 * Still not working like service.
 * TODO
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
public class ECGeometryService implements ICService
{

	@Override
	public void configure(CServiceParameter arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public Object executeService(int arg0, Object arg1)
	{
		ECGeometry geometry = new ECGeometry();
		return geometry;
	}

	@Override
	public Object executeService(int[] arg0, Object[] arg1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAuthor()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getInputType()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getInputTypes()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOutputType()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getVersion()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
