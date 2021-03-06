package com.esri.core.geometry;

/**
 * Created by davidraleigh on 2/20/16.
 */
public class OperatorGeodesicBufferCursor extends GeometryCursor {
    private SpatialReferenceImpl m_spatialReference;
    private ProgressTracker m_progressTracker;
    private double[] m_distances;
    private double m_maxDeviation;
    private Envelope2D m_currentUnionEnvelope2D;
    private boolean m_bUnion;

    private int m_dindex;

    // GeometryCursor inputGeometries, SpatialReference sr, int curveType, double[] distancesMeters, double maxDeviationMeters, boolean bReserved, boolean bUnion, ProgressTracker progressTracker
    OperatorGeodesicBufferCursor(GeometryCursor inputGeoms,
                                 SpatialReference sr,
                                 double[] distances,
                                 double maxDeviation,
                                 boolean bReserved,
                                 boolean b_union,
                                 ProgressTracker progressTracker) {
        m_inputGeoms = inputGeoms;
        m_spatialReference = (SpatialReferenceImpl) sr;
        m_distances = distances;
        m_maxDeviation = maxDeviation;
        m_bUnion = b_union;
        m_currentUnionEnvelope2D = new Envelope2D();
        m_currentUnionEnvelope2D.setEmpty();
        m_dindex = -1;
        m_progressTracker = progressTracker;
    }


    @Override
    public Geometry next() {
        if (m_bUnion) {
            OperatorGeodesicBufferCursor cursor = new OperatorGeodesicBufferCursor(m_inputGeoms, m_spatialReference, m_distances, m_maxDeviation, false, false, m_progressTracker);
            return ((OperatorUnion) OperatorFactoryLocal.getInstance().getOperator(Operator.Type.Union)).execute(cursor, m_spatialReference, m_progressTracker).next();
        }

        if (hasNext()) {
            if (m_dindex + 1 < m_distances.length)
                m_dindex++;

            return geodesicBuffer(m_inputGeoms.next(), m_distances[m_dindex]);
        }

        return null;
    }

    // virtual bool IsRecycling() OVERRIDE { return false; }
    Geometry geodesicBuffer(Geometry geom, double distance) {
        return GeodesicBufferer.buffer(geom, distance, m_spatialReference, m_maxDeviation, 96, m_progressTracker);
    }
}
