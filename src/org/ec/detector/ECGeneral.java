package org.ec.detector;

/**
 * This class store the constants used by all the other classes.
 * <p>
 * <font size = 1>JSA: Thomas Jefferson National Accelerator Facility<br>
 * This software was developed under a United States Government license,<br>
 * described in the NOTICE file included as part of this distribution.<br>
 * Copyright (c), Feb 15, 2011</font>
 *
 * @author      smancill
 * @version     0.1
 */
public final class ECGeneral
{
    public final static int     MAX_SECTORS            =      6;
    public final static int     MAX_STRIPS             =    108;
    public final static int     MAX_PEAKS              =     30;
    public final static int     MAX_HITS               =     10;


    public final static double  SPEED_OF_LIGHT         =     29.9792458;
    public final static double  INDEX_OF_REFRACTION    =      1.581;
    public final static double  SPEED_IN_PLASTIC       =     18.0;
    public final static double  EFF_SAMPLING_FRACTION  =      0.275;


    public final static double  DEFAULT_ECH            =      0.0001;
    public final static double  DEFAULT_TCH            =      0.050;
    public final static double  DEFAULT_TRMS           =      1.0;
    public final static double  DEFAULT_ATTEN          =    376.0;

    // Control
    public static int           TOUCH_ID               =      0;
    public static boolean       LN_WEIGHTS             =      true;
    public static double        STRIP_THRESHOLD        =      0.001;
    public static double        PEAK_THRESHOLD         =      0.003;
    public static double        HIT_THRESHOLD          =      0.010;
    public static int           MAX_EC_STRIPS          =     36;
    public static int           MAX_PCAL_STRIPS        =    108;
    public static double        EC_MATCH               =      3;
}
