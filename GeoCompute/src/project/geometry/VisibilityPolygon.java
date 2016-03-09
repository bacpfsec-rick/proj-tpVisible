package project.geometry;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Stack;

public class VisibilityPolygon extends Component{
    ArrayList OriginalVertices = new ArrayList();
    ArrayList vertices = new ArrayList();
    boolean computeFinished = false;

    Point point = new Point(100,100);
    boolean pointActive = false;

    Polygon polygon = new Polygon();

    Stack VP = new Stack();
    Stack L = new Stack();
    Stack R = new Stack();

    // Setter and getter to use it
    public void setOriginalVertices(ArrayList al) {
        OriginalVertices = al;
        for (Object o : OriginalVertices) {
            Point p = (Point)o;
            polygon.addPoint(p.x,p.y);
        }
    }
    public ArrayList getVertices() {
        return vertices;
    }
    public void setPoint(Point p) {
        point = p;
    }

    private Point Intersection(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)
    {
        int la = -(x1 * y2 - x2 * y1);
        int lb = y1 - y2;
        int lc = x2 - x1;

        int ma = -(x3 * y4 - x4 * y3);
        int mb = y3 - y4;
        int mc = x4 - x3;

        int h1 = -(lb * mc - mb * lc);
        int h2 =  ma * lc - la * mc;
        int h3 = la * mb - ma * lb;

        int kx = 0;
        int ky = 0;

        if(h1 != 0)
        {
            kx = h2 / h1;
            ky = h3 / h1;
        }
        else
        {
//            System.out.println("divided by zero, in Intersection.");
        }

        return new Point(kx, ky);

    }

    // point verify
    public boolean pointVerify() {
        if(!polygon.contains(point.x, point.y))
        {
            System.out.println("("+point.x+","+point.y+")"+"is outside of Polygon.");
            return false;
        }
        return true;
    }
    // Assume the vertices are given in a CW order, and no three vertices are collinear.
    public void VisibilityCompute()
    {
        if (!pointVerify()) {
            return;
        }
        System.out.println("Computing vp for point(" + point.x + "," + point.y + ")");

        VP.removeAllElements();
        L.removeAllElements();
        R.removeAllElements();

        vertices.clear();
        for(int h = 0; h < OriginalVertices.size(); h++)	/*Restore the vertices array from the OriginalVertices.*/
            vertices.add(OriginalVertices.get(h));

        Point firstPoint = (Point)vertices.get(0);
        Point lastPoint = (Point)vertices.get(vertices.size()-1);
        if(Math.abs((double)(firstPoint.x - lastPoint.x)) < 2.0 && Math.abs((double)(firstPoint.y - lastPoint.y)) < 2.0)
            vertices.remove(vertices.size()-1);

			/*Preprocessing step: To find the closest intersection to x.*/
        Point STR;

        int epson = 100000;
        int index = 0; /*The segment between vertice[i] and vertice[(i+1)% size] has the closest intersection point to x.*/
        int closestx = 0;

        for(int i = 0; i < vertices.size(); i++)
        {
            Point start = (Point)vertices.get(i);
            Point end = (Point)vertices.get((i+1)% vertices.size());

            if(end.y - start.y != 0)
            {
                int px = (end.x - start.x) * (point.y - start.y) / (end.y - start.y) + start.x;

                if(px < end.x && px > start.x || px > end.x && px < start.x)
                {
                    if(point.x - px > 0 && point.x - px < epson)
                    {
                        index = i;
                        closestx = px;
                        epson = point.x - px;
                    }
                }
            }
        }
        STR = new Point(closestx, point.y);

        Point start = (Point)vertices.get(index);
        Point end = (Point)vertices.get((index+1)% vertices.size());
        if((STR.x != start.x || STR.y != start.y ) && (STR.x != end.x || STR.y != end.y))
			/*if STR is not a vertex then insert STR into the sequence of vertices representing P.*/
        {
            ArrayList newVertices = new ArrayList();
            newVertices.add(STR);
            for(int i = index+1; i  < vertices.size(); i++)
                newVertices.add(vertices.get(i));
            for(int i = 0; i < index+1; i++)
                newVertices.add(vertices.get(i));
            vertices = newVertices;
            //repaint();
        }
			/*End of Preprocessing.*/

			/*Initialization Step*/
        VP.push(STR);					/*PUSH(VP) <-- STR*/
        VP.push(vertices.get(1));			/*PUSH(VP <-- SUCC(STR))*/

        Line2D.Float STR_INF = new Line2D.Float((float)STR.x, (float)STR.y, (float)-10000, (float)STR.y);
        R.push(STR_INF);

        Point t = STR;
        int i = 0;	/*Keep track of the index of t during the while loop.*/
			/*End of Initialization Step*/

			/*General Step*/
        while(true)
        {
            int size = vertices.size();
            Point mu = (Point)vertices.get((i+1)%size);
            Point sigma = (Point)vertices.get((i+2)%size);
				/*Case 0. sigma = STR*/
            if(sigma == STR)
                break;
            int determinant1 = (mu.x - point.x) * (sigma.y - point.y) - (sigma.x - point.x) * (mu.y - point.y);
            int determinant2 = (mu.x - t.x) * (sigma.y - t.y) - (sigma.x - t.x) * (mu.y - t.y);

				/*Case 1. x mu sigma is a right turn*/
            if(determinant1 >= 0)																				       {
//                System.out.println("Case 1");

                if(!R.empty())
                {
                    Line2D.Float mu_sigma = new Line2D.Float((float)mu.x, (float)mu.y, (float)sigma.x, (float)sigma.y);
                    Line2D.Float rs = new Line2D.Float();
                    while(!R.empty())
                    {
                        rs = (Line2D.Float)R.pop();
                        int rx = (int)rs.getX1();
                        int ry = (int)rs.getY1();
                        if(rs.intersectsLine(mu_sigma) || !testTheta(sigma.x, sigma.y, rx, ry, point.x, point.y))
                            break;
                    }

                    int rx = (int)rs.getX1();
                    int ry = (int)rs.getY1();
						/*Case 1a. mu_sigma intersects rs at point k*/
                    if(rs.intersectsLine(mu_sigma))																                               {
//                        System.out.println("Case 1a");

                        int sx = (int)rs.getX2();
                        int sy = (int)rs.getY2();

                        Point k = Intersection(rx, ry, sx, sy, mu.x, mu.y, sigma.x, sigma.y);
                        int kx = k.x;
                        int ky = k.y;

                        Line2D.Float rk = new Line2D.Float((float)rs.getX1(), (float)rs.getY1(), (float)kx, (float)ky);
							/*SCAN the vertices of P from sigma until an edge, ei, intersects rk at a point l*/
                        for(int j = i+2; j<size; j++)
                        {
                            Point ei = (Point)vertices.get(j);
                            Point ei_1 = (Point)vertices.get((j+1)%size);
                            Line2D.Float e = new Line2D.Float((float)ei.x, (float)ei.y, (float)ei_1.x, (float)ei_1.y);
                            if(e.intersectsLine(rk))
                            {

                                Point l = Intersection(ei.x, ei.y, ei_1.x, ei_1.y, rx, ry, kx, ky);
                                int lx =l.x;
                                int ly = l.y;

									/*SUCC(mu) <-- k; SUCC(k) <-- l; SUCC(l) <-- ei_1*/
                                ArrayList newVertices = new ArrayList();
                                for(int h = 0; h <= i+1; h++)
                                    newVertices.add(vertices.get(h));
                                newVertices.add(k);
                                newVertices.add(l);
                                for(int h = j + 1; h < size; h++)
                                    newVertices.add(vertices.get(h));
                                vertices = newVertices;
                                //repaint();

									/*PUSH(VP) <-- k; PUSH(VP) <-- l; PUSH(R) <-- rl; t <-- k*/
                                VP.push(k);
                                VP.push(l);
                                Line2D.Float rl = new Line2D.Float((float)rs.getX1(), (float)rs.getY1(), (float)lx, (float)ly);
                                R.push(rl);

                                i += 2;
                                t = k;

                                break;
                            }
                        }
                    }
                    else if(!testTheta(sigma.x, sigma.y, rx, ry, point.x, point.y))	/*Case 1b. Theta(sigma, x) <= Theta(r,x)*/
                    {
//                        System.out.println("Case 1b");
							/*PUSH(VP) <-- sigma; PUSH(R) <-- rs; t <-- SUCC(t)*/
                        VP.push(sigma);
                        R.push(rs);
                        i++;
                        t = mu;
                    }
                    else/*Nothing happen, in a convex hull chain, keep on searching.*/
                    {
                        VP.push(sigma);
                        i++;
                        t = mu;
                    }
                }
                else /*Nothing happen, in a convex hull chain, keep on searching.*/
                {
                    VP.push(sigma);
                    i++;
                    t = mu;
                }
            }
            else if(determinant1 < 0 && determinant2 < 0)	/*Case 2. x mu sigma is a left turn and t mu sigma is a left turn*/
            {
//                System.out.println("Case 2");

                int kx = 0;
                int ky = 0;
                int indexI = 0;

                Point k = new Point();

					/*SCAN the vertices of P from sigma until an edge, ei, intersects x mu - extended in k*/
                for(int j = i+2; j<size; j++)					{
						/*First calculate the intersection point of line x-mu and line vertices[j] vertices[j+1]*/
                    Point ei = (Point)vertices.get(j);
                    Point ei_1 = (Point)vertices.get((j+1)%size);

                    k = Intersection(ei.x, ei.y, ei_1.x, ei_1.y, point.x, point.y, mu.x, mu.y);
                    kx = k.x;
                    ky = k.y;

						/*Then see whether the intersection point is in the x mu - extended ray and segment vertices[j] vertices[j+1]*/
                    int sameDirection = (mu.x - point.x) * (kx - point.x) + (mu.y - point.y) * (ky - point.y);
                    if(sameDirection > 0 &&  (kx > ei.x && kx < ei_1.x || kx < ei.x && kx > ei_1.x))
                    {
                        indexI = j;
                        break;
                    }
                }

					/*SUCC(mu) <-- k; SUCC(k) <-- ei_1*/
                ArrayList newVertices = new ArrayList();
                for(int h = 0; h <= i+1; h++)
                    newVertices.add(vertices.get(h));
                newVertices.add(k);
                for(int h = indexI + 1; h < size; h++)
                    newVertices.add(vertices.get(h));
                vertices = newVertices;
                //repaint();

					/*PUSH(VP) <-- k; PUSH(L) <-- mu_k*/
                VP.push(k);
                Line2D.Float mu_k = new Line2D.Float((float)mu.x, (float)mu.y, (float)kx, (float)ky);
                L.push(mu_k);

                i++;
                t = mu;
            }
				/*Case 3. x mu sigma is a left turn and t mu sigma is a right turn*/
            else if(determinant1 < 0 && determinant2 > 0)
            {
//                System.out.println("Case 3");

                Line2D.Float mu_sigma = new Line2D.Float((float)mu.x, (float)mu.y, (float)sigma.x, (float)sigma.y);
                Line2D.Float rs = new Line2D.Float();
                if(!L.empty())
                    rs = (Line2D.Float)L.pop();
                else
                    rs = null;
                while(!L.empty())
                {
                    int rx = (int)rs.getX1();
                    int ry = (int)rs.getY1();
                    if(rs.intersectsLine(mu_sigma) || !testTheta(rx, ry, sigma.x, sigma.y, point.x, point.y))
                        break;
                    rs = (Line2D.Float)L.pop();
                }
                if(rs != null)
                {
                    int rx = (int)rs.getX1();
                    int ry = (int)rs.getY1();
                    if(L.empty() && testTheta(rx, ry, sigma.x, sigma.y, point.x, point.y) && !rs.intersectsLine(mu_sigma))
                        rs = null;
                }

                if(rs != null && rs.intersectsLine(mu_sigma))			/*Case 3a. mu_sigma intersects rs at point k*/
                {
//                    System.out.println("Case 3a.");

                    int rx = (int)rs.getX1();
                    int ry = (int)rs.getY1();
                    Point r = new Point(rx, ry);
                    int sx = (int)rs.getX2();
                    int sy = (int)rs.getY2();

                    Point	k = Intersection(rx, ry, sx, sy, mu.x, mu.y, sigma.x, sigma.y);
                    int kx = k.x;
                    int ky = k.y;

                    Line2D.Float rk = new Line2D.Float((float)rs.getX1(), (float)rs.getY1(), (float)kx, (float)ky);

						/*SCAN the vertices of P from sigma until an edge, ei, intersects rk at a point l*/
                    for(int j = i+2; j<size; j++)
                    {
                        Point ei = (Point)vertices.get(j);
                        Point ei_1 = (Point)vertices.get((j+1)%size);
                        Line2D.Float e = new Line2D.Float((float)ei.x, (float)ei.y, (float)ei_1.x, (float)ei_1.y);
                        if(e.intersectsLine(rk))
                        {

                            Point l = Intersection(ei.x, ei.y, ei_1.x, ei_1.y, rx, ry, kx, ky);
                            int lx = l.x;
                            int ly = l.y;

                            Point w = (Point)VP.pop();
                            while(w.x != sx || w.y != sy)
                                w = (Point)VP.pop();

								/*SUCC(r) <-- l; SUCC(l) <-- ei_1*/
                            int rIndex = 0;		/*First we have to find the index of r.*/
                            for(rIndex = 0; rIndex < size; rIndex++)
                            {
                                Point tmp = (Point)vertices.get(rIndex);
                                if(rx == tmp.x && ry == tmp.y)
                                    break;
                            }
                            ArrayList newVertices = new ArrayList();
                            for(int h = 0; h <= rIndex; h++)
                                newVertices.add(vertices.get(h));
                            newVertices.add(l);
                            for(int h = j+1; h < size; h++)
                                newVertices.add(vertices.get(h));
                            vertices = newVertices;
                            //repaint();

								/*PUSH(VP) <-- l; PUSH(L) <-- rl*/
                            VP.push(l);
                            Line2D.Float rl = new Line2D.Float((float)rs.getX1(), (float)rs.getY1(), (float)lx, (float)ly);
                            L.push(rl);

                            i = rIndex;
                            t = r;

                            break;
                        }
                    }
                }
                else //if(rs == null || !testTheta(rx, ry, sigma.x, sigma.y, point.x, point.y) )	/*Case 3b.*/
                {
//                    System.out.println("Case 3b.");

                    if(rs != null)
                        L.push(rs);
                    Point r = (Point)VP.pop();
                    Point s = (Point)VP.pop();

						/*First calculate the intersection point of line x-sigma and line rs*/
                    int kx = 0;
                    int ky = 0;
                    while(true)
                    {
                        Point k = Intersection(r.x, r.y, s.x, s.y, point.x, point.y, sigma.x, sigma.y);
                        kx = k.x;
                        ky = k.y;

							/*Then see whether the intersection point is in the x sigma - extended ray and segment rs*/
                        int sameDirection = (sigma.x - point.x) * (kx - point.x) + (sigma.y - point.y) * (ky - point.y);
                        if(sameDirection > 0 &&  (kx > r.x && kx < s.x || kx < r.x && kx > s.x))
                            break;
                        else
                        {
                            r = s;
                            if(VP.empty())
                            {
                                System.out.println("Stack Empty. Error due to Numerical roundings.");
//                                System.exit(0);
                                computeFinished = false;
                                repaint();
                                return;
                            }
                            s = (Point)VP.pop();
                        }
                    }

						/*Find the index of s in the vertices array.*/
                    int indexS = 0;
                    for(indexS = 0; indexS < size; indexS ++)
                    {
                        Point tmp = (Point)vertices.get(indexS);
                        if(s.x == tmp.x && s.y == tmp.y)
                            break;
                    }
						/*Then SUCC(s) <-- k; PUSH(VP) <-- s; PUSH(VP) <-- k*/
                    Point k = new Point(kx, ky);
                    ArrayList newVertices = new ArrayList();
                    for(int h = 0; h <= indexS; h++)
                        newVertices.add(vertices.get(h));
                    newVertices.add(k);

                    VP.push(s);
                    VP.push(k);

                    t = k;
                    //i = indexS + 1;

						/*Procedure REGIONAL*/
                    Point y = (Point)vertices.get((i+3)%size);


                    int determinant3 = (sigma.x - point.x) * (y.y - point.y) - (y.x - point.x) * (sigma.y - point.y);
                    int determinant4 = (sigma.x - mu.x) * (y.y - mu.y) - (y.x - mu.x) * (sigma.y - mu.y);

                    if(determinant3 < 0)																	   			       /*Case a. x_sigma_y is a left turn.*/
                    {
//                        System.out.println("REGIONAL Case a.");

                        for(int h = i+2; h<size; h++)
                            newVertices.add(vertices.get(h));
                        VP.push(sigma);
                        vertices = newVertices;
                        //repaint();
                        i = indexS + 1;
                    }
						/*Case b. x_sigma_y is a right turn and mu_sigma_y is a left turn.*/
                    else if(determinant3 >0 && determinant4 < 0)								{
//                        System.out.println("REGIONAL Case b.");
                        for(int h = i+2; h<size; h++)
                            newVertices.add(vertices.get(h));
                        VP.push(sigma);
                        vertices = newVertices;
                        //repaint();
                        Line2D.Float sigma_k = new Line2D.Float((float)sigma.x, (float)sigma.y, (float)kx, (float)ky);
                        L.push(sigma_k);
                        i = indexS + 1;
                    }
						/*Case c. x_sigma_y is a right turn and mu_sigma_y is a right turn.*/
                    else if(determinant3 >0 && determinant4 > 0)
                    {
//                        System.out.println("REGIONAL Case c.");
							/*SCAN the vertices of P from y until an edge, ei, intersects sigma_k at a point l*/
                        for(int j = i+3; j<size; j++)								{
                            Line2D.Float sigma_k = new Line2D.Float((float)sigma.x, (float)sigma.y, (float)kx, (float)ky);

                            Point ei = (Point)vertices.get(j);
                            Point ei_1 = (Point)vertices.get((j+1)%size);
                            Line2D.Float e = new Line2D.Float((float)ei.x, (float)ei.y, (float)ei_1.x, (float)ei_1.y);
                            if(e.intersectsLine(sigma_k))
                            {
                                Point l = Intersection(ei.x, ei.y, ei_1.x, ei_1.y, sigma.x, sigma.y, kx, ky);
                                int lx = l.x;
                                int ly = l.y;


									/*SUCC(k) <-- l; SUCC(l) <-- ei_1*/
                                newVertices.add(l);
                                for(int h = j + 1; h < size; h++)
                                    newVertices.add(vertices.get(h));
                                vertices = newVertices;
                                //repaint();

									/*PUSH(VP) <-- l; PUSH(R) <-- sigma_l*/
                                VP.push(l);
                                Line2D.Float sigma_l = new Line2D.Float((float)sigma.x, (float)sigma.y, (float)lx, (float)ly);
                                R.push(sigma_l);

                                i = indexS + 1;

                                break;
                            }
                        }
                    }

						/*End of Procedure REGIONAL*/
                }
            }

        }
			/*End of General Step*/

        computeFinished = true;
        repaint();

    }

    /*Return true if Theta(x1, o) > Theta(x2, o), otherwise, return false.*/
    private boolean testTheta(int x1, int y1, int x2, int y2, int ox, int oy)
    {
        int vector1x = x1 - ox;
        int vector1y = y1 - oy;
        int area1 = findArea(vector1x, vector1y);

        int vector2x = x2 - ox;
        int vector2y = y2 - oy;
        int area2 = findArea(vector2x, vector2y);

        if(area1 > area2)
            return true;
        else if(area1 < area2)
            return false;
        else	/*area1 == area2*/
        {
            if(vector1y * vector2x - vector2y*vector1x > 0)
                return true;
            else
                return false;
        }


    }

    private int findArea(int vx, int vy)
    {
        if(vx < 0 && vy <= 0)
            return 1;
        else if(vx >= 0 && vy <0)
            return 2;
        else if(vx > 0 && vy >= 0)
            return 3;
        else if(vx <= 0 && vy >0)
            return 4;
        return 0;
    }

    public void addpoint(int x,int y)
    {

        OriginalVertices.add(new Point(x, y));
        //vertices.add(new Point(x, y));
        polygon.addPoint(x, y);

    }
    public void paint(Graphics g)
    {

        g.setColor(Color.blue);

        if(OriginalVertices.size() > 0)
        {

            int[] xPoints = new int[OriginalVertices.size()];
            int[] yPoints = new int[OriginalVertices.size()];

            for(int i = 0; i < OriginalVertices.size(); i++)
            {
                Point p = (Point)OriginalVertices.get(i);
                xPoints[i] = p.x;
                yPoints[i] = p.y;
            }

            g.drawPolyline(xPoints, yPoints, OriginalVertices.size());

            for(int i = 0; i < OriginalVertices.size(); i++)
            {
                Point p = (Point)OriginalVertices.get(i);
                g.fillOval(p.x-2, p.y-2, 4, 4);
            }

        }

        if(computeFinished)
        {
            g.setColor(Color.red);

            if(vertices.size() > 0)
            {

                int[] xPoints = new int[vertices.size()+1];
                int[] yPoints = new int[vertices.size()+1];

                for(int i = 0; i < vertices.size(); i++)
                {
                    Point p = (Point)vertices.get(i);
                    xPoints[i] = p.x;
                    yPoints[i] = p.y;
                }

                xPoints[vertices.size()] = xPoints[0];
                yPoints[vertices.size()] = yPoints[0];

                g.drawPolyline(xPoints, yPoints, vertices.size()+1);

                for(int i = 0; i < vertices.size(); i++)
                {
                    Point p = (Point)vertices.get(i);
                    g.fillOval(p.x-2, p.y-2, 4, 4);
                }

            }
        }

        if(pointActive)
        {
            g.setColor(Color.green);
            g.fillOval(point.x-3, point.y-3,6,6);
        }
    }
    public void reset()
    {
        vertices.clear();
        OriginalVertices.clear();
        pointActive = false;
        computeFinished = false;
        polygon.npoints = 0;
    }
}