// ======================= LENGTH UNIT ENUM =======================
enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(0.0328084); // 1 cm = 0.0328084 feet

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    public double toFeet(double value) {
        return value * toFeet;
    }

    public double fromFeet(double feetValue) {
        return feetValue / toFeet;
    }
}

// ======================= QUANTITY LENGTH CLASS =======================
public class QuantityMeasurementApp {

    private final double value;
    private final LengthUnit unit;

    public QuantityMeasurementApp(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    // ================= BASE CONVERSION =================
    private double toFeet() {
        return unit.toFeet(value);
    }

    private static double fromFeet(double feet, LengthUnit targetUnit) {
        return targetUnit.fromFeet(feet);
    }

    // ================= UC7: ADDITION (WITH TARGET UNIT) =================
    public static QuantityLength add(QuantityLength l1,
                                     QuantityLength l2,
                                     LengthUnit targetUnit) {

        if (l1 == null || l2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Null not allowed");
        }

        double sumInFeet = l1.toFeet() + l2.toFeet();
        double result = fromFeet(sumInFeet, targetUnit);

        return new QuantityLength(result, targetUnit);
    }

    // ================= UC6: ADDITION (DEFAULT FIRST UNIT) =================
    public static QuantityLength add(QuantityLength l1,
                                     QuantityLength l2) {

        return add(l1, l2, l1.unit);
    }

    // ================= UC5: CONVERSION =================
    public static double convert(double value,
                                 LengthUnit from,
                                 LengthUnit to) {

        if (from == null || to == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid input");
        }

        double feet = from.toFeet(value);
        return to.fromFeet(feet);
    }

    // ================= UC1 - UC4: EQUALITY =================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength other)) return false;

        return Double.compare(this.toFeet(), other.toFeet()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(toFeet());
    }

    // ================= DISPLAY =================
    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}

// ======================= MAIN APP =======================
public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength oneCm = new QuantityLength(1.0, LengthUnit.CENTIMETERS);

        // ===== UC1-UC4: EQUALITY =====
        System.out.println(oneFoot.equals(new QuantityLength(1.0, LengthUnit.FEET)));
        System.out.println(oneFoot.equals(twelveInches)); // true

        // ===== UC5: CONVERSION =====
        System.out.println(QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES)); // 12
        System.out.println(QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.FEET));   // 3

        // ===== UC6: ADDITION (DEFAULT UNIT) =====
        System.out.println(QuantityLength.add(oneFoot, twelveInches));

        // ===== UC7: ADDITION (TARGET UNIT) =====
        System.out.println(QuantityLength.add(oneFoot, twelveInches, LengthUnit.FEET));
        System.out.println(QuantityLength.add(oneFoot, twelveInches, LengthUnit.INCHES));
        System.out.println(QuantityLength.add(oneFoot, twelveInches, LengthUnit.YARDS));

        // Extra checks
        System.out.println(QuantityLength.add(oneYard, oneFoot, LengthUnit.YARDS));
        System.out.println(QuantityLength.add(oneCm, twelveInches, LengthUnit.CENTIMETERS));
    }
}