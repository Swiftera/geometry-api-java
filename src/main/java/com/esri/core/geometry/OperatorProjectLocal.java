/*
 Copyright 1995-2015 Esri

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 For additional information, contact:
 Environmental Systems Research Institute, Inc.
 Attn: Contracts Dept
 380 New York Street
 Redlands, California, USA 92373

 email: contracts@esri.com
 */

package com.esri.core.geometry;

import org.proj4.PJ;

import java.util.Arrays;

//This is a stub
class OperatorProjectLocal extends OperatorProject {

	@Override
	public GeometryCursor execute(GeometryCursor inputGeoms,
			ProjectionTransformation transform, ProgressTracker progressTracker) {
		return new OperatorProjectCursor(inputGeoms, transform, progressTracker);
	}

	public Geometry execute(Geometry inputGeom,
			ProjectionTransformation transform, ProgressTracker progressTracker) {
		return execute(new SimpleGeometryCursor(inputGeom), transform, progressTracker).next();
	}

	@Override
	public int transform(ProjectionTransformation transform, Point[] pointsIn,
			int count, Point[] pointsOut) throws org.proj4.PJException {
		return Projecter.transform(transform, pointsIn, count, pointsOut);
	}

	public double[] transform(ProjectionTransformation transform,
			double[] coordsSrc, int pointCount) throws org.proj4.PJException {
		return Projecter.transform(transform, coordsSrc, pointCount);
	}

	@Override
	public Geometry foldInto360RangeGeodetic(/* const */Geometry _geom, /* const */
	SpatialReference pannableSR, /* GeodeticCurveType */int curveType) {
		throw new GeometryException("not implemented");
	}

	@Override
	public Geometry foldInto360Range(/* const */Geometry geom, /* const */
	SpatialReference pannableSR) {
		throw new GeometryException("not implemented");
	}
}
