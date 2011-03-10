package org.ec.detector;

import java.util.Collection;
import java.util.HashMap;
import java.util.TreeMap;


/**
 * The <code>ECSector</code> class represents a sector in the EC detector.
 * This detector has 6 sectors, and each sector object stores the data of one
 * of those sectors.
 * <p>
 * Each sector has a fixed list of four {@link ECLayer layers} that store the
 * sector data for each of the <em>WHOLE</em>, <em>INNER</em>, <em>OUTER</em>
 * and <em>COVER</em> layers of the detector, and an <code>ID</code> to
 * identify the sector (1 to 6).
 * <p>
 * You can get the data for all the layers by getting and iterating over the
 * list of layers, or you can get the data of one specific layer by giving its
 * name.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 15, 2011</font>
 *
 * @author      smancill
 * @version     0.1
 */
public class ECSector
{
    private int ID;
    private TreeMap<ECLayer.Name, ECLayer> layerList;

    private double phi;
    private HashMap<String, Double> origins;

    private TreeMap<ECLayer.Name, TreeMap<ECLayer.Name, Integer>> nmatch;

    /**
     * Construct an object representing the EC sector with the given ID.
     * Initialize all the properties to zero and create the list of the four
     * layers on the sector.
     *
     * @param id   the number of the sector (1 to 6)
     */
    public ECSector(int id)
    {
        this.ID  = id;
        this.phi = 0;

        // Create the hash for the local coordinate system
        this.origins   = new HashMap<String, Double>();

        origins.put("x", 0.0);
        origins.put("y", 0.0);
        origins.put("z", 0.0);

        // Create the layer list
        this.layerList = new TreeMap<ECLayer.Name, ECLayer>();
        for (ECLayer.Name name : ECLayer.Name.values()) {
            layerList.put(name, new ECLayer(name, id));
        }

        this.nmatch = new TreeMap<ECLayer.Name, TreeMap<ECLayer.Name,Integer>>();
        for (ECLayer.Name n1 : ECLayer.Name.values()) {
            TreeMap<ECLayer.Name, Integer> secondKey = new TreeMap<ECLayer.Name, Integer>();
            nmatch.put(n1, secondKey);
            for (ECLayer.Name n2 : ECLayer.Name.values())
                if (n1 != n2) nmatch.get(n1).put(n2, 0);
        }
    }


    /**
     * Get the list of layers in the sector, to iterate over it.
     *
     * @return  a {@link Collection} with the four layers of the sector.
     * @see     ECLayer
     */
    public Collection<ECLayer> getLayerList()
    {
        return layerList.values();
    }


    /**
     * Get a specific layer in the sector.
     *
     * @param name  the name of the desired layer
     * @return      the desired layer
     * @see         ECLayer
     */
    public ECLayer getLayer(ECLayer.Name name)
    {
        return layerList.get(name);
    }


    /**
     * Set the phi angle of the sector.
     *
     * @param phi the phi to set
     */
    public void setPhi(double phi)
    {
        this.phi = phi;
    }


    /**
     * Get the phi angle of the sector.
     *
     * @return the phi angle
     */
    public double getPhi()
    {
        return phi;
    }


    /**
     *  Get the origin of the local coordinate system.  This method gets the
     *  origin of one axis at the time.  Each of the three axes is represented
     *  by a letter, that can be <code>"x"</code>, <code>"y"</code> or
     *  <code>"z"</code>.
     *
     * @param axis the desired axis
     * @return     the origin of the local coordinate system for the given
     *             axis
     */
    public double getOrigins(String axis)
    {
        if (origins.containsKey(axis))
            return origins.get(axis);
        else
            return 0.0;
    }


    /**
     * Set the origin of the local coordinate system in the sector.  This
     * method sets the origin of one axis at the time.  Each of the three axes
     * is represented by a letter that must be pased as parameter, and it can
     * be <code>"x"</code>, <code>"y"</code> or <code>"z"</code>.
     *
     * @param axis     the axis to set
     * @param position the position for the given axis
     */
    public void setOrigins(String axis, double position)
    {
        if (origins.containsKey(axis))
            origins.put(axis, position);
    }


    /**
     * Get the number of matches between layers l1 and l2
     *
     * @param l1  the first layer
     * @param l2  the second layer
     *
     * @return  the number of matches between layers
     */
    public int getNmatch(ECLayer.Name l1, ECLayer.Name l2)
    {
        return nmatch.get(l1).get(l2);
    }


    /**
     * Increase the number of matches between layers l1 and l2 by one.
     *
     * @param l1  the first layer
     * @param l2  the second layer
     */
    public void addMatch(ECLayer l1, ECLayer l2)
    {
        int n = nmatch.get(l1.getName()).get(l2.getName()) - 1;
        nmatch.get(l1.getName()).put(l2.getName(), n);
    }


    /**
     * Reduce the number of matches between layers l1 and l2 by one.
     *
     * @param l1  the first layer
     * @param l2  the second layer
     */
    public void substractMatch(ECLayer l1, ECLayer l2)
    {
        int n = nmatch.get(l1.getName()).get(l2.getName()) - 1;
        nmatch.get(l1.getName()).put(l2.getName(), n);
    }


    /**
     * Get the ID of the sector.
     *
     * @return the sector ID
     */
    public int getID()
    {
        return ID;
    }
}
