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

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * A simple MapGeometryCursor implementation that wraps a single MapGeometry or
 * an array of MapGeometry classes
 */
class SimpleMapGeometryCursor extends MapGeometryCursor {
    MapGeometry m_geom;
    ArrayDeque<MapGeometry> m_geomDeque;
    long m_index;

    public SimpleMapGeometryCursor(MapGeometry geom) {
        m_geomDeque = new ArrayDeque<>(1);
        m_geom = geom;
        m_index = -1;
    }

    public SimpleMapGeometryCursor(MapGeometry[] geoms) {
        m_geomDeque = Arrays.stream(geoms).collect(Collectors.toCollection(ArrayDeque::new));
        m_index = -1;
    }

    @Override
    public long getGeometryID() {
        return m_index;
    }

    @Override
    public boolean hasNext() {
        return m_geomDeque.size() > 0;
    }

    @Override
    public MapGeometry next() {
        if (hasNext()) {
            m_index++;
            return m_geomDeque.pop();
        }

        return null;
    }
}
