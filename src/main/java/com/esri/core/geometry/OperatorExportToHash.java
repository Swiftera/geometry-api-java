package com.esri.core.geometry;

public abstract class OperatorExportToHash extends Operator {
    @Override
    public Type getType() {
        return null;
    }

    public abstract StringCursor execute(int exportFlags,
                                         GeometryCursor geometryCursor,
                                         ProgressTracker progressTracker);

    public abstract String execute(int exportFlags,
                                   Geometry geometry,
                                   ProgressTracker progress_tracker);

    public static OperatorExportToHash local() {
        return (OperatorExportToHash) OperatorFactoryLocal.getInstance().getOperator(Type.ExportToHash);
    }
}
