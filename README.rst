EC Package
==========

This is the development version of the EC reconstruction software using a
service oriented approach with the ClARA framework.

:Authors: S. Mancilla,
          R. Oyarzún,
          J. Pavez

:Version: 0.1

Design
------

The original FORTRAN algorithm has the following structure ::

    get information from BOS file
    for each sector do
        fill strips information
        for each layer do
            for each view do
                fit strips to peaks
            fit peaks to hits
        match hits between layers
    fill output BOS file

To move the old FORTRAN programming style to a modern object oriented design,
several classes have been created to represent the data structures:

=========  ===================================================================
ECSector   Represent a sector in the detector. It is the base class and it
           stores all the data used by the algorithm. Each sector have a list
           of four layers: *WHOLE*, *INNER*, *OUTER* and *COVER*. 
ECLayer    Represent one layer in the detector. It is the principal class, as
           it stores the list of found hits in that layer.  Each layer have a
           list with all the three views of the detector: *U*, *V* and *W*.
ECView     Represent a view in the detector.  For each view exits a list of
           information about its strips, and a list with the found peaks in
           that view.
ECStrip    The basic class, represent a strip and store the information
           obtained from BOS file.
ECFitPeak  Represent a peak, saving the necessary information for it.
ECFitHit   Represent a hit, saving the necessary information for it.
=========  ===================================================================

So, in a nutshell, each sector has four layers, each layer has three views,
and each view has a list of strips.  The algorithm finds peaks in each view
object, and then finds hits in the parent layer using the found peaks in its
three views.  This composition of objects allows us to represent the detector
in a more natural way.

See the Javadoc documentation for more details.

Services
--------

We decided to separate the algorithm in a chain of three services, using a
sector object as the data that should be passed between them::

    get information from BOS file
    for each sector do
        service1 -> service2 -> service3
    fill BOS file

The orchestrator reads the data for one event from the BOS file, and then for
each of the six sectors it starts the chain of services, each one doing the
following:

* The first service fills the information of strips using the TDC and ADC data
  from BOS file, and the calibration data of the detector.

* The second service is the main one. It iterates over the layers of the
  sector, to get the peaks from the strips, and then the hits from the peaks.
  
* The third service matches the hits between the layers in the sector.

Finally, when all the six sectors have been evaluated, the orchestrator fill
the BOS file with the calculated values.

Current status
--------------

* The main services have been created.  But probably the first one is too
  simple, and the second one could be split in other services.  We need to run
  tests and evaluate the performance.

* The orchestrator is finished.  You can test it and see the services running
  in the CLARA platform. But there is no data to test the services, because
  we can not read the BOS file yet.

* We are developing the code to get the calibration data.

* We have to figure out how to read the BOS file.

Install
-------

The current version is still in development. To compile it, you need to setup
the ``CLARA_HOME`` environment variable, as you can see in the ClARA
documentation.  Then just type in ``ant`` in your command line. It should
compile without problem, and a jar file will be created in the root directory
of the repository.

To run the orchestrator, you should set your ``CLARA_SERVICES`` variable to
``your-path-to-ec/build/classes``, start the CLARA platform and then run the
wrapper located in ``bin/``.

The ``EC.jar`` file created by the compilation should be in your
``CLASSPATH``.
