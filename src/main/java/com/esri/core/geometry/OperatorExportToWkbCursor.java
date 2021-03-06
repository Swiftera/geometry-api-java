package com.esri.core.geometry;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class OperatorExportToWkbCursor extends ByteBufferCursor {
    GeometryCursor m_inputGeometryCursor;
    int m_exportFlags;

    public OperatorExportToWkbCursor(int exportFlags, GeometryCursor geometryCursor) {
        if (geometryCursor == null)
            throw new GeometryException("invalid argument");

        m_exportFlags = exportFlags;
        m_inputGeometryCursor = geometryCursor;
    }

    @Override
    public boolean hasNext() { return m_inputGeometryCursor != null && m_inputGeometryCursor.hasNext(); }

    @Override
    public ByteBuffer next() {
        Geometry geometry = m_inputGeometryCursor.next();
        if (geometry != null) {
            int size = OperatorExportToWkbLocal.exportToWKB(m_exportFlags, geometry, null);
            ByteBuffer wkbBuffer = ByteBuffer.allocate(size).order(ByteOrder.nativeOrder());
            OperatorExportToWkbLocal.exportToWKB(m_exportFlags, geometry, wkbBuffer);
            return wkbBuffer;
        }
        return null;
    }

    @Override
    public long getByteBufferID() {
        return m_inputGeometryCursor.getGeometryID();
    }


}
