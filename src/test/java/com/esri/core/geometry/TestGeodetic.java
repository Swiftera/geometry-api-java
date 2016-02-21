package com.esri.core.geometry;

import junit.framework.TestCase;

import org.junit.Test;

public class TestGeodetic extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testTriangleLength() {
		Point pt_0 = new Point(10, 10);
		Point pt_1 = new Point(20, 20);
		Point pt_2 = new Point(20, 10);
		double length = 0.0;
		length += GeometryEngine.geodesicDistanceOnWGS84(pt_0, pt_1);
		length += GeometryEngine.geodesicDistanceOnWGS84(pt_1, pt_2);
		length += GeometryEngine.geodesicDistanceOnWGS84(pt_2, pt_0);
		assertTrue(Math.abs(length - 3744719.4094597572) < 1e-13 * 3744719.4094597572);
	}

	@Test
	public void testRotationInvariance() {
		Point pt_0 = new Point(10, 40);
		Point pt_1 = new Point(20, 60);
		Point pt_2 = new Point(20, 40);
		double length = 0.0;
		length += GeometryEngine.geodesicDistanceOnWGS84(pt_0, pt_1);
		length += GeometryEngine.geodesicDistanceOnWGS84(pt_1, pt_2);
		length += GeometryEngine.geodesicDistanceOnWGS84(pt_2, pt_0);
		assertTrue(Math.abs(length - 5409156.3896271614) < 1e-13 * 5409156.3896271614);

		for (int i = -540; i < 540; i += 5) {
			pt_0.setXY(i + 10, 40);
			pt_1.setXY(i + 20, 60);
			pt_2.setXY(i + 20, 40);
			length = 0.0;
			length += GeometryEngine.geodesicDistanceOnWGS84(pt_0, pt_1);
			length += GeometryEngine.geodesicDistanceOnWGS84(pt_1, pt_2);
			length += GeometryEngine.geodesicDistanceOnWGS84(pt_2, pt_0);
			assertTrue(Math.abs(length - 5409156.3896271614) < 1e-13 * 5409156.3896271614);
		}
	}

	@Test
	public void testDistanceFailure() {
		{
			Point p1 = new Point(-60.668485, -31.996013333333334);
			Point p2 = new Point(119.13731666666666, 32.251583333333336);
			double d = GeometryEngine.geodesicDistanceOnWGS84(p1, p2);
			assertTrue(Math.abs(d - 19973410.50579736) < 1e-13 * 19973410.50579736);
		}

		{
			Point p1 = new Point(121.27343833333333, 27.467438333333334);
			Point p2 = new Point(-58.55804833333333, -27.035613333333334);
			double d = GeometryEngine.geodesicDistanceOnWGS84(p1, p2);
			assertTrue(Math.abs(d - 19954707.428360686) < 1e-13 * 19954707.428360686);
		}

		{
			Point p1 = new Point(-53.329865, -36.08110166666667);
			Point p2 = new Point(126.52895166666667, 35.97385);
			double d = GeometryEngine.geodesicDistanceOnWGS84(p1, p2);
			assertTrue(Math.abs(d - 19990586.700431127) < 1e-13 * 19990586.700431127);
		}

		{
			Point p1 = new Point(-4.7181166667, 36.1160166667);
			Point p2 = new Point(175.248925, -35.7606716667);
			double d = GeometryEngine.geodesicDistanceOnWGS84(p1, p2);
			assertTrue(Math.abs(d - 19964450.206594173) < 1e-12 * 19964450.206594173);
		}
	}

	@Test
	public void testVicenty() {
		double a = 6378137.0; // radius of spheroid for WGS_1984
		double e2 = 0.0066943799901413165; // ellipticity for WGS_1984
		double rpu = Math.PI / 180.0;
		double dpu = 180.0 / Math.PI;
		double distance = 2000.0;
		{
			Point p1 = new Point(0.0, 0.0);
			PeDouble lam = new PeDouble();
			PeDouble phi = new PeDouble();
			GeoDist.geodesic_forward(a, e2, p1.getX() * rpu, p1.getY() * rpu, distance, 0.0 * rpu, lam, phi);
			assertEquals(0.0, lam.val * dpu, 0.000001);
			assertEquals(0.01808739, phi.val * dpu, 0.000001);

			PeDouble actualDistance = new PeDouble();
			GeoDist.geodesic_distance_ngs(a, e2, p1.getX() * rpu, p1.getY() * rpu, lam.val, phi.val, actualDistance, null, null);
			assertEquals(actualDistance.val, distance, .02);

		}
		{
			Point p1 = new Point(45.0, 45.0);
			PeDouble lam = new PeDouble();
			PeDouble phi = new PeDouble();
			GeoDist.geodesic_forward(a, e2, p1.getX() * rpu, p1.getY() * rpu, distance, 20.0 * rpu, lam, phi);

			assertEquals(45.01691097, phi.val * dpu, 0.000001);
			assertEquals(45.00867811, lam.val * dpu, 0.000001);
		}
		{
			Point p1 = new Point(60.0, 45.0);
			PeDouble lam = new PeDouble();
			PeDouble phi = new PeDouble();
			GeoDist.geodesic_forward(a, e2, p1.getX() * rpu, p1.getY() * rpu, distance, 20.0 * rpu, lam, phi);

			//45.01691097
			assertEquals(45.01691097, phi.val * dpu, 0.000001);
			assertEquals(60.00867811, lam.val * dpu, 0.000001);
		}
		{
			Point p1 = new Point(-65.0, -45.0);
			PeDouble lam = new PeDouble();
			PeDouble phi = new PeDouble();
			GeoDist.geodesic_forward(a, e2, p1.getX() * rpu, p1.getY() * rpu, distance, -20.0 * rpu, lam, phi);

			//-44.98308832 -65.00867301
			assertEquals(-44.98308832, phi.val * dpu, 0.000001);
			assertEquals(-65.00867301, lam.val * dpu, 0.000001);
		}
		{
			Point p1 = new Point(-165.0, -45.0);
			PeDouble lam = new PeDouble();
			PeDouble phi = new PeDouble();
			GeoDist.geodesic_forward(a, e2, p1.getX() * rpu, p1.getY() * rpu, distance, 220.0 * rpu, lam, phi);

			//-45.01378505 -165.01630863
			assertEquals(-45.01378505, phi.val * dpu, 0.000001);
			assertEquals(-165.01630863, lam.val * dpu, 0.000001);
		}
	}

	@Test
	public void testGeodeticBufferPoint() {
		{
			SpatialReference sr = SpatialReference.create(4326);
			Point p1 = new Point(0.0, 0.0);
			OperatorGeodesicBuffer opBuf = (OperatorGeodesicBuffer)OperatorFactoryLocal.getInstance().getOperator(Operator.Type.GeodesicBuffer);
			double distance = 1000;
			Polygon poly = (Polygon)opBuf.execute(p1, sr, GeodeticCurveType.Geodesic, distance, 0.1, false, null);
			assertNotNull(poly);
			assertTrue(poly.getType() == Geometry.Type.Polygon);
			double area = poly.calculateArea2D();
			double diff = Math.abs(3139350.203046864 - area);
			assertTrue("The difference between the circular and the geodesic buffer shouldn't be to great",diff < 100.0);
			assertTrue("The difference between the circular and the geodesic buffer should be greater than 0", diff > 0.0);
		}
	}
	
	@Test
	public void testLengthAccurateCR191313() {
		/*
		 * // random_test(); OperatorFactoryLocal engine =
		 * OperatorFactoryLocal.getInstance(); //TODO: Make this:
		 * OperatorShapePreservingLength geoLengthOp =
		 * (OperatorShapePreservingLength)
		 * factory.getOperator(Operator.Type.ShapePreservingLength);
		 * SpatialReference spatialRef = SpatialReference.create(102631);
		 * //[6097817.59407673
		 * ,17463475.2931517],[-1168053.34617516,11199801.3734424
		 * ]]],"spatialReference":{"wkid":102631}
		 * 
		 * Polyline polyline = new Polyline();
		 * polyline.startPath(6097817.59407673, 17463475.2931517);
		 * polyline.lineTo(-1168053.34617516, 11199801.3734424); double length =
		 * geoLengthOp.execute(polyline, spatialRef, null);
		 * assertTrue(Math.abs(length - 2738362.3249366437) < 2e-9 * length);
		 */
	}

	

}
