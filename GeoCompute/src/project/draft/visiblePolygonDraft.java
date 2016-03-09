package project.draft;

import project.geometry.lineRelation;
import project.geometry.pointRelation;
import uk.co.geolib.geolib.C2DLine;
import uk.co.geolib.geolib.C2DPoint;
import uk.co.geolib.geopolygons.C2DPolygon;

import java.util.*;

/**
 * Class for generating visible polygon with ray casting method in simple polygon
 */
public class visiblePolygonDraft {
    private C2DPoint origin;
    private ArrayList<C2DPoint> vertex;
    private ArrayList<C2DLine> rays;
    private ArrayList<C2DLine> edges;
    private C2DPolygon polygon;

    // Constructor to load the origin point and polygon
    public visiblePolygonDraft(C2DPoint ori, ArrayList<C2DPoint> vet) {
        origin = ori;
        vertex = vet;
        rays = new ArrayList<C2DLine>();
        vertex.forEach((vertice)-> {
            if (!((origin.x==vertice.x)&&(origin.y==vertice.y))) {
                rays.add(new C2DLine(origin,vertice));
            }
        });
        edges = new ArrayList<C2DLine>();
        int size = vertex.size();
        for (int i=0; i<size; i++) {
            edges.add(new C2DLine(vertex.get(i%size), vertex.get((i+1)%size)));
        }
        polygon = new C2DPolygon(vertex,false);
    }

    // Method to compute and general visibility polygon
    public ArrayList<C2DPoint> generate() {
        ArrayList<C2DPoint> result = new ArrayList<C2DPoint>();
        rays.forEach((ray)-> {
            // Use micro error to check if line segment is line the polygon with GeoLib
            double xFrom = ray.GetPointFrom().x;
            double yFrom = ray.GetPointFrom().y;
            double xTo = ray.GetPointTo().x;
            double yTo = ray.GetPointTo().y;
            C2DLine microErrorRay = new C2DLine(
                    new C2DPoint(xFrom+(xTo-xFrom)/1000,yFrom+(yTo-yFrom)/1000),
                    new C2DPoint(xTo-(xTo-xFrom)/1000,yTo-(yTo-yFrom)/1000));
            if (polygon.Contains(microErrorRay)){
                result.add(ray.GetPointFrom());
            } else {
                // Check for intersection point and overlap points
                ArrayList<C2DPoint> intersections = new ArrayList<C2DPoint>();
                ArrayList<C2DPoint> overlaps = new ArrayList<C2DPoint>();
                for (C2DLine edge: edges){
                    if (lineRelation.overlap(ray,edge)) {
                        C2DPoint p1 = edge.GetPointFrom();
                        C2DPoint p2 = edge.GetPointTo();
                        if (pointRelation.distanceSquare(p1,origin)>pointRelation.distanceSquare(p2,origin)) {
                            overlaps.add(p1);
                            overlaps.add(p2);
                        } else {
                            overlaps.add(p2);
                            overlaps.add(p1);
                        }

                    }
                }
            }
        });
        return result;
    }
}
