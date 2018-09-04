package Poly;

import java.util.Scanner;
//
public class Launcher {

    public static void main(String[] args) {
        System.out.println("Input 2 rectangles. You have to use template \"x:y\" with space between vertices.\nYou can use 2 vertices to make a rectangle parallel to the coordinate axes, or 4 for other cases");
        System.out.println("Input first rectangle:");
        Poly first = getRect();
        System.out.println("Area: " +first.getArea());
        System.out.println("Input second rectangle:");
        Poly second = getRect();
        System.out.println("Area: " + second.getArea());
        Poly result = first.getIntersection(second);
        System.out.println("Result: " + result);
        System.out.println("Area: " + result.getArea());

    }

    private static Poly getRect() {
        Scanner sc = new Scanner(System.in);
        String str;
        Poly p;
        qwe: while (true) {
            str = sc.nextLine();
            String[] nodes = str.trim().split(" ");
            p = new Poly();
            for (String node : nodes) {
                String tmp[] = node.trim().split(":");
                try {
                    float x = Float.parseFloat(tmp[0]);
                    float y = Float.parseFloat(tmp[1]);
                    p.addNode(x, y);
                } catch (NumberFormatException e) {
                    System.out.println("Incorrect format:\"" + node + "\", try again");
                    continue qwe;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("It's not enough");
                }
            }
            if (p.isRectangle()) {
                break;
            } else {
                System.out.println("It's not a rectangle, try again");
            }
        }
        if (p.getNodesSize() == 2) {
            p.fillMissingNodes();
        }
        return p;
    }
}
