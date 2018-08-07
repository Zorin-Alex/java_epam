package Poly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Poly {
    public class Points {
        private double x;
        private double y;
        private double angle;

        public Points(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "" + x + " " + y;
        }
    }

    private List<Points> nodes = new ArrayList<>();

    public int getNodesSize() {
        return nodes.size();
    }

    public double getArea() {
        double area = 0;
        for (int i = 0; i < nodes.size(); i++) {
            int j = i + 1;
            if (j == nodes.size()) {
                j = 0;
            }
            area += (nodes.get(i).x + nodes.get(j).x) * (nodes.get(i).y - nodes.get(j).y);
        }
        return Math.abs(area / 2);
    }

    public void addNode(double x, double y) {
        nodes.add(new Points(x, y));
    }


    public void fillMissingNodes() {
        addNode(nodes.get(0).x, nodes.get(1).y);
        addNode(nodes.get(1).x, nodes.get(0).y);
        sortNodes();
    }

    public boolean isRectangle() {
        if ((nodes.size() == 2) && (nodes.get(0).x != nodes.get(1).x) && (nodes.get(0).y != nodes.get(1).y)) {
            return true;
        } else if (nodes.size() == 4) {
            sortNodes();
            if ((distance(nodes.get(0), nodes.get(1)) == distance(nodes.get(2), nodes.get(3))) &&
                    (distance(nodes.get(1), nodes.get(2)) == distance(nodes.get(3), nodes.get(0))) &&
                    (distance(nodes.get(1), nodes.get(3)) == distance(nodes.get(2), nodes.get(0)))) {
                return true;
            }
        }
        return false;
    }

    private double distance(Points a, Points b) {
        return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
    }

    private void sortNodes() {
        float x = 0, y = 0;
        for (Points p : nodes) {
            x += p.x;
            y += p.y;
        }
        x /= nodes.size();
        y /= nodes.size();
        for (Points tmp : nodes) {
            tmp.angle = Math.atan2(tmp.x - x, tmp.y - y);
        }
        Collections.sort(nodes, (a, b) -> Double.compare(a.angle, b.angle));
    }

    public Poly getIntersection(Poly p) {
        Poly result = new Poly();
        for (int i = 0; i < nodes.size(); i++) {
            int j = i + 1;
            if (j == nodes.size()) {
                j = 0;
            }
            for (int k = 0; k < p.nodes.size(); k++) {
                int m = k + 1;
                if (m == p.nodes.size()) {
                    m = 0;
                }
                Points tmp = lineIntersect(nodes.get(i), nodes.get(j), p.nodes.get(k), p.nodes.get(m));
                if (tmp != null) {
                    result.nodes.add(tmp);
                }
            }
        }
        for (Points tmp:p.nodes) {
            if (isNodeInside(tmp)) {
                result.nodes.add(tmp);
            }
        }
        for (Points tmp:nodes) {
            if (p.isNodeInside(tmp)) {
                result.nodes.add(tmp);
            }
        }

        result.sortNodes();
        return result;
    }

    private Points lineIntersect(Points p1, Points p2, Points p3, Points p4) {
        double v1 = multiVectors(new Points(p4.x - p3.x, p4.y - p3.y), new Points(p1.x - p3.x, p1.y - p3.y));
        double v2 = multiVectors(new Points(p4.x - p3.x, p4.y - p3.y), new Points(p2.x - p3.x, p2.y - p3.y));
        double v3 = multiVectors(new Points(p2.x - p1.x, p2.y - p1.y), new Points(p4.x - p1.x, p4.y - p1.y));
        double v4 = multiVectors(new Points(p2.x - p1.x, p2.y - p1.y), new Points(p3.x - p1.x, p3.y - p1.y));
        if (!((v1 * v2 <= 0) && (v3 * v4 <= 0))) {
            return null;
        } else {
            double x = p4.x + ((p3.x - p4.x) * Math.abs(v3)) / Math.abs(v4 - v3);
            double y = p4.y + ((p3.y - p4.y) * Math.abs(v3)) / Math.abs(v4 - v3);
            return new Points(x, y);
        }

    }

    private boolean isLineIntersect(Points p1, Points p2, Points p3, Points p4) {
        return !(lineIntersect(p1, p2, p3, p4) == null);
    }

    private boolean isNodeInside(Points p) {
        double maxX = nodes.get(0).x;
        for (Points tmp : nodes) {
            if (tmp.x > maxX) {
                maxX = tmp.x;
            }
        }
        maxX++;
        int count = 0;
        for (int i = 0; i < nodes.size(); i++) {
            int j = i + 1;
            if (j == nodes.size()) {
                j = 0;
            }
            if (isLineIntersect(nodes.get(i), nodes.get(j), p, new Points(maxX, p.y))) {
                count++;
            }
        }
        return (count % 2 != 0);
    }


    private double multiVectors(Points a, Points b) {
        return a.x * b.y - b.x * a.y;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Points point : nodes) {
            sb.append("(").append((int) point.x).append(":").append((int) point.y).append(")");
        }
        return sb.toString();
    }


}
