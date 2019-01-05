package com.esri.core.geometry;

public class OperatorExportToHashCursor extends StringCursor {
    GeometryCursor m_geometryCursor;

    public OperatorExportToHashCursor(GeometryCursor geometryCursor, ProgressTracker progressTracker) {
        if (geometryCursor == null)
            throw new GeometryException("invalid argument");

        m_geometryCursor = geometryCursor;
    }

    @Override
    public String next() {
        Geometry geometry = m_geometryCursor.next();
        if (geometry != null) {
            int hashCode = geometry.hashCode();
            return Integer.toString(hashCode);
        }
        return null;
    }

    @Override
    public long getID() {
        return m_geometryCursor.getGeometryID();
    }

    @Override
    public boolean hasNext() {
        return m_geometryCursor != null && m_geometryCursor.hasNext();
    }
}
