package com.esri.core.geometry;

/**
 * Created by davidraleigh on 4/17/16.
 */
final public class OperatorGeneralizeByAreaLocal extends OperatorGeneralizeByArea {

    @Override
    public GeometryCursor execute(GeometryCursor geoms,
                                  double percentReduction,
                                  boolean bRemoveDegenerateParts,
                                  GeneralizeType generalizeType,
                                  SpatialReference spatialReference,
                                  ProgressTracker progressTracker) {

        return new OperatorGeneralizeByAreaCursor(geoms,
                percentReduction,
                bRemoveDegenerateParts,
                generalizeType,
                spatialReference,
                progressTracker);
    }

    @Override
    public GeometryCursor execute(GeometryCursor geoms,
                                  boolean bRemoveDegenerateParts,
                                  int maxPointCount,
                                  GeneralizeType generalizeType,
                                  SpatialReference spatialReference,
                                  ProgressTracker progressTracker) {
        return new OperatorGeneralizeByAreaCursor(geoms,
                bRemoveDegenerateParts,
                maxPointCount,
                generalizeType,
                spatialReference,
                progressTracker);
    }

    @Override
    public Geometry execute(Geometry geom,
                            double percentReduction,
                            boolean bRemoveDegenerateParts,
                            GeneralizeType generalizeType,
                            SpatialReference spatialReference,
                            ProgressTracker progressTracker) {

        SimpleGeometryCursor inputGeomCurs = new SimpleGeometryCursor(geom);

        GeometryCursor geometryCursor = execute(inputGeomCurs,
                                                percentReduction,
                                                bRemoveDegenerateParts,
                                                generalizeType,
                                                spatialReference,
                                                progressTracker);

        return geometryCursor.next();
    }

    @Override
    public Geometry execute(Geometry geom,
                            boolean bRemoveDegenerateParts,
                            int maxPointCount,
                            GeneralizeType generalizeType,
                            SpatialReference spatialReference,
                            ProgressTracker progressTracker) {

        SimpleGeometryCursor inputGeomCurs = new SimpleGeometryCursor(geom);

        GeometryCursor geometryCursor = execute(inputGeomCurs,
                bRemoveDegenerateParts,
                maxPointCount,
                generalizeType,
                spatialReference,
                progressTracker);

        return geometryCursor.next();

    }
}
