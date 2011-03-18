package org.ec.calibration;

import java.io.Serializable;

import org.ec.detector.ECGeneral;
import org.ec.detector.ECLayer;
import org.ec.detector.ECView;

/**
 * The <code>ECGeometry</code> class, encapsulate the geometry data of EC for
 * all strips, layer, views and sectors.  Also calculate the default data for
 * the calibration.
 * <p>
 * <p><font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), March 14, 2011</font>
 *
 * @author      jgpavez
 * @version     0.1
 */
public class ECGeometry implements Serializable
{

	private static final long serialVersionUID = -7552644838582831241L;
	// Tcl Varibles
    public static int       wholeSurf = 16;
    public static int       innerSurf = 1;
    public static int       outerSurf = 16;
    public static int       coverSurf = 1;

    private double          ylow;
    private double          yhi;
    private double          dylow;
    private double          dyhi;
    private double          thickness;
    private double          isec;
    private double          zEClow;
    private double          zEChi;
    private double          tgrho;
    private double          pi;
    private double          d2Rad;

    private double[]        xoff;
    private double[]        yoff;
    private double[]        zoff;
    public double[][][]     rotm;
    public double[][]       n2Sect;
    public double[][][]     edge_L;
    private double          bsc_r;
    private double          bsc_a;
    public ECBscSector[]    bscSector;
    public ECBscLayer[]     bscLayer;


    /**
     * Construct a ECGeometry object and define the default data for the
     * calibration object, the ideal geometry settings.
     */
    public ECGeometry()
    {
        pi          = 2 * Math.acos(0);
        d2Rad       = pi/180;

        xoff        = new double[ECGeneral.MAX_SECTORS];
        yoff        = new double[ECGeneral.MAX_SECTORS];
        zoff        = new double[ECGeneral.MAX_SECTORS];
        rotm        = new double[ECGeneral.MAX_SECTORS][3][3];
        n2Sect      = new double[3][ECGeneral.MAX_SECTORS];
        edge_L      = new double[3][4][ECGeneral.MAX_SECTORS];
        bscSector   = new ECBscSector[ECGeneral.MAX_SECTORS];
        bscLayer    = new ECBscLayer[4];

        // Set Default
        double[] surf = new double[4];

        surf[ECLayer.Name.WHOLE.ordinal()] = ECGeometry.wholeSurf;
        surf[ECLayer.Name.INNER.ordinal()] = ECGeometry.innerSurf;
        surf[ECLayer.Name.OUTER.ordinal()] = ECGeometry.outerSurf;
        surf[ECLayer.Name.COVER.ordinal()] = ECGeometry.coverSurf;

        // What is CStep?
        bsc_r      =   510.32;
        bsc_a      =    25;
        ylow       =  -182.974 ;
        yhi        =   189.956;
        dylow      =     0.43708;
        dyhi       =     0.45419;
        thickness  =     1.2381;
        tgrho      =     1.95325;

        for (int i = 0; i < ECGeneral.MAX_SECTORS; i++) {
            xoff[i] = 0.0;
            yoff[i] = 0.0;
            zoff[i] = 0.0;

            bscSector[i] = new ECBscSector();

            bscSector[i].setPhi((i)*60);

            for (int jr = 0; jr < 3; jr++) {
                for (int ir = 0; ir < 3; ir++) {
                    rotm[i][jr][ir] = 0.0;
                    if (jr == ir) rotm[i][jr][ir] = 1;
                }
            }
        }

        // CALL EC_READ_ECCG
        for (ECLayer.Name layer : ECLayer.Name.values()) {
            double ylow = this.ylow - this.dylow * (surf[layer.ordinal()] - 1);
            double yhi  = this.yhi  + this.dyhi * (surf[layer.ordinal()] - 1);
            double xlow = (ylow - yhi) / tgrho;
            double xhi  = -xlow;

            bscLayer[layer.ordinal()] = new ECBscLayer();

            bscLayer[layer.ordinal()].setDepth( (surf[layer.ordinal()] -1) * thickness);
            bscLayer[layer.ordinal()].setH(yhi - ylow);
            bscLayer[layer.ordinal()].setH1(Math.abs(ylow));
            bscLayer[layer.ordinal()].setH2(yhi);

            for (int j = 0; j < 6; j++) {
                edge_L[ECView.Label.U.ordinal()][layer.ordinal()][j] = Math.sqrt(Math.pow(xlow, 2) + Math.pow(yhi - ylow, 2));
                edge_L[ECView.Label.V.ordinal()][layer.ordinal()][j] = Math.sqrt(Math.pow(xlow - xhi,2));
                edge_L[ECView.Label.W.ordinal()][layer.ordinal()][j] = Math.sqrt(Math.pow(xhi,2) + Math.pow(yhi - ylow, 2));
            }
        }

        for (int i = 0; i < ECGeneral.MAX_SECTORS; i++) {
            bscSector[i].setX( bsc_r * Math.sin(bsc_a * d2Rad)* Math.cos(bscSector[i].getPhi() * d2Rad) + xoff[i]);
            bscSector[i].setY( bsc_r * Math.sin(bsc_a * d2Rad)* Math.sin(bscSector[i].getPhi() * d2Rad) + yoff[i]);
            bscSector[i].setZ( bsc_r * Math.cos(bsc_a * d2Rad) + zoff[i]);
        }

        zEChi  = bsc_r * Math.cos( bsc_a * d2Rad) - this.ylow * Math.sin(bsc_a * d2Rad);
        zEClow = bsc_r * Math.cos( bsc_a * d2Rad) - this.yhi * Math.sin(bsc_a * d2Rad);

        for (int i = 0; i < ECGeneral.MAX_SECTORS; i ++) {
            n2Sect[0][i] = (Math.sin(bsc_a * d2Rad) * Math.cos(bscSector[i].getPhi() * d2Rad));
            n2Sect[1][i] = (Math.sin(bsc_a * d2Rad) * Math.sin(bscSector[i].getPhi() * d2Rad));
            n2Sect[2][i] = (Math.cos(bsc_a * d2Rad));
        }
    }


    /**
     * @return the ylow
     */
    public double getYlow()
    {
        return ylow;
    }


    /**
     * @param ylow the ylow to set
     */
    public void setYlow(double ylow)
    {
        this.ylow = ylow;
    }


    /**
     * @return the yhi
     */
    public double getYhi()
    {
        return yhi;
    }


    /**
     * @param yhi the yhi to set
     */
    public void setYhi(double yhi)
    {
        this.yhi = yhi;
    }


    /**
     * @return the dylow
     */
    public double getDylow()
    {
        return dylow;
    }


    /**
     * @param dylow the dylow to set
     */
    public void setDylow(double dylow)
    {
        this.dylow = dylow;
    }


    /**
     * @return the dyhi
     */
    public double getDyhi()
    {
        return dyhi;
    }


    /**
     * @param dyhi the dyhi to set
     */
    public void setDyhi(double dyhi)
    {
        this.dyhi = dyhi;
    }


    /**
     * @return the thickness
     */
    public double getThickness()
    {
        return thickness;
    }


    /**
     * @param thickness the thickness to set
     */
    public void setThickness(double thickness)
    {
        this.thickness = thickness;
    }


    /**
     * @return the isec
     */
    public double getIsec()
    {
        return isec;
    }


    /**
     * @param isec the isec to set
     */
    public void setIsec(double isec)
    {
        this.isec = isec;
    }


    /**
     * @return the zEClow
     */
    public double getzEClow()
    {
        return zEClow;
    }


    /**
     * @param zEClow the zEClow to set
     */
    public void setzEClow(double zEClow)
    {
        this.zEClow = zEClow;
    }


    /**
     * @return the zEChi
     */
    public double getzEChi()
    {
        return zEChi;
    }


    /**
     * @param zEChi the zEChi to set
     */
    public void setzEChi(double zEChi)
    {
        this.zEChi = zEChi;
    }


    /**
     * @return the pi
     */
    public double getPi()
    {
        return pi;
    }


    /**
     * @param pi the pi to set
     */
    public void setPi(double pi)
    {
        this.pi = pi;
    }


    /**
     * @return the d2Rad
     */
    public double getD2Rad()
    {
        return d2Rad;
    }


    /**
     * @param d2Rad the d2Rad to set
     */
    public void setD2Rad(double d2Rad)
    {
        this.d2Rad = d2Rad;
    }


    /**
     * Get the X offset of the local coordinate system
     *
     * @return the xoff
     */
    public double getXoff(int i)
    {
        return xoff[i];
    }


    /**
     * Set the X offset of the local coordinate system
     * @param xoff the xoff to set
     */
    public void setXoff(int place, double xoff)
    {
        this.xoff[place] = xoff;
    }


    /**
     * Get the Y offset of the local coordinate system
     * @return the yoff
     */
    public double getYoff(int i)
    {
        return yoff[i];
    }


    /**
     * Set the Y offset of the local coordinate system
     * @param yoff the yoff to set
     */
    public void setYoff(int place, double yoff)
    {
        this.yoff[place] = yoff;
    }


    /**
     * Get the Z offset of the local coordinate system
     * @return the zoff
     */
    public double getZoff(int i)
    {
        return zoff[i];
    }


    /**
     * Set the Z offset of the local coordinate system
     * @param zoff the zoff to set
     */
    public void setZoff(int place, double zoff)
    {
        this.zoff[place] = zoff;
    }


    /**
     * @return the rotm
     */
    public double getRotm( int i, int j, int k)
    {
        return rotm[i][j][k];
    }


    /**
     * Get the rotation matrix
     * @param rotm the rotm to set
     */
    public void setRotm(int i, int j, int k, double rotm)
    {
        this.rotm[i][j][k] = rotm;
    }


    /**
     * Set the rotation matrix
     * @return the n2Sect
     */
    public double getN2Sect(int i , int j)
    {
        return n2Sect[i][j];
    }


    /**
     * @param n2Sect the n2Sect to set
     */
    public void setN2Sect(int i, int j, double n2Sect)
    {
        this.n2Sect[i][j] = n2Sect;
    }


    /**
     * @return the edge_L
     */
    public double getEdge_L( int i, int j, int k )
    {
        return edge_L[i][j][k];
    }


    /**
     * @param edge_L the edge_L to set
     */
    public void setEdge_L(int i, int j, int k, double edge_L)
    {
        this.edge_L[i][j][k] = edge_L;
    }


    /**
     * Get the distance
     * @return the ecBsc_r
     */
    public double getBsc_r()
    {
        return bsc_r;
    }


    /**
     * Set the distance
     * @param ecBsc_r the ecBsc_r to set
     */
    public void setBsc_r(double bsc_r)
    {
        this.bsc_r = bsc_r;
    }


    /**
     * Get the angle
     * @return the ecBsc_a
     */
    public double getBsc_a()
    {
        return bsc_a;
    }


    /**
     * Set the angle
     * @param ecBsc_a the ecBsc_a to set
     */
    public void setBsc_a(double bsc_a)
    {
        this.bsc_a = bsc_a;
    }


    /**
     * @param tgrho
     */
    public void setTgrho(double tgrho)
    {
        this.tgrho = tgrho;
    }


    /**
     * @return
     */
    public double getTgrho()
    {
        return tgrho;
    }



    /**
    * Sector calibration data
    *
    * @author      jgpavez
    */
    public static class ECBscSector implements Serializable
    {

		private static final long serialVersionUID = 3828937390991049108L;
		private double phi;
        private double x;
        private double y;
        private double z;


        /**
        * Get the phi angle of each sector.
        *
        * @return  the phi
        */
        public double getPhi()
        {
            return phi;
        }


        /**
        * Set the phi angle of each sector.
        *
        * @param phi  the phi to set
        */
        public void setPhi(double phi)
        {
            this.phi = phi;
        }


        /**
        * Get the X origin of the local coordinate system.
        *
        * @return  the xCoord
        */
        public double getX()
        {
            return x;
        }



        /**
        * Set the X origin of the local coordinate system.
        *
        * @param xCoord  the xCoord to set
        */
        public void setX(double x)
        {
            this.x = x;
        }


        /**
        * Get the Y origin of the local coordinate system.
        *
        * @return  the yCoord
        */
        public double getY()
        {
            return y;
        }


        /**
        * Get the Y origin of the local coordinate system.
        *
        * @param yCoord  the yCoord to set
        */
        public void setY(double y)
        {
            this.y = y;
        }


        /**
        * Get the Z origin of the local coordinate system.
        *
        * @return  the zCoord
        */
        public double getZ()
        {
            return z;
        }


        /**
        * Set the Z origin of the local coordinate system.
        *
        * @param zCoord  the zCoord to set
        */
        public void setZ(double z)
        {
            this.z = z;
        }
    }



    /**
    * Layer calibration data.
    *
    * @author      jgpavez
    */
    public static class ECBscLayer implements Serializable
    {

		private static final long serialVersionUID = -6479451403957066410L;
	
		private double depth;
        private double h;
        private double h1;
        private double h2;

        /**
        * @return  the depth
        */
        public double getDepth()
        {
            return depth;
        }


        /**
        * @param depth  the depth to set
        */
        public void setDepth(double depth)
        {
            this.depth = depth;
        }


        /**
        * @return  the h
        */
        public double getH()
        {
            return h;
        }


        /**
        * @param h  the h to set
        */
        public void setH(double h)
        {
            this.h = h;
        }


        /**
        * @return  the h1
        */
        public double getH1()
        {
            return h1;
        }


        /**
        * @param h1  the h1 to set
        */
        public void setH1(double h1)
        {
            this.h1 = h1;
        }


        /**
        * @return  the h2
        */
        public double getH2()
        {
            return h2;
        }


        /**
        * @param h2  the h2 to set
        */
        public void setH2(double h2)
        {
            this.h2 = h2;
        }
    }
}

