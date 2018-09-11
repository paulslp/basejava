package ru.javawebinar.basejava.storage;
public enum Planet {
    MERCURY (3.303e+23, 2.4397e6),
    VENUS   (4.869e+24, 6.0518e6),
    EARTH   (5.976e+24, 6.37814e6),
    MARS    (6.421e+23, 3.3972e6),
    JUPITER (1.9e+27,   7.1492e7),
    SATURN  (5.688e+26, 6.0268e7),
    URANUS  (8.686e+25, 2.5559e7),
    NEPTUNE (1.024e+26, 2.4746e7);

    private final double mass;   // в килограммах
    private final double radius; // в метрах
    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
    }
    private double mass() { return mass; }
    private double radius() { return radius; }

    // гравитационная постоянная
    public static final double G = 6.67300E-11;

    double surfaceGravity() {
        return G * mass / (radius * radius);
    }
    double surfaceWeight(double otherMass) {
        return otherMass * surfaceGravity();
    }

    public static void printListPlanet() {
        /*if (args.length != 1) {
            System.err.println("Usage: java Planet &lt;earth_weight&gt;");
            System.exit(-1);
        }*/
        double earthWeight = 1e+22;//Double.parseDouble(1e+22/*args[0]*/);
        double mass = earthWeight/EARTH.surfaceGravity();
        for (Planet p : Planet.values())
            System.out.printf("Your weight on %s is %f%n",
                    p, p.surfaceWeight(mass));
    }

    public static void main(String[] args) {
        /*if (args.length != 1) {
            System.err.println("Usage: java Planet &lt;earth_weight&gt;");
            System.exit(-1);
        }*/
        double earthWeight = 1e+22;//Double.parseDouble(1e+22/*args[0]*/);
        double mass = earthWeight/EARTH.surfaceGravity();
        for (Planet p : Planet.values())
            System.out.printf("Your weight on %s is %f%n",
                    p, p.surfaceWeight(mass));
    }
}