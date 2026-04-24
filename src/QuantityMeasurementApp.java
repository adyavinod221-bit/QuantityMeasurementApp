// ======================= STANDALONE LENGTH UNIT (UC8) =======================
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // Convert this unit → base unit (FEET)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // Convert base unit (FEET) → this unit
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

// ======================= QUANTITY LENGTH CLASS =======================
class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    // ---------- UC8: CONVERT TO ANOTHER UNIT ----------
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityLength(converted, targetUnit);
    }

    // ---------- UC6: ADD (DEFAULT UNIT = FIRST OPERAND) ----------
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    // ---------- UC7: ADD (EXPLICIT TARGET UNIT) ----------
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Null not allowed");
        }

        double sumBase =
                this.unit.convertToBaseUnit(this.value) +
                        other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
    }

    // ---------- UC1–UC4: EQUALITY ----------
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength other)) return false;

        return Double.compare(
                this.unit.convertToBaseUnit(this.value),
                other.unit.convertToBaseUnit(other.value)
        ) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}

// ======================= MAIN APP (UC5–UC8 DEMO) =======================
public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);
        QuantityLength oneYard = new QuantityLength(1.0, LengthUnit.YARDS);
        QuantityLength oneCm = new QuantityLength(2.54, LengthUnit.CENTIMETERS);

        // ================= UC1–UC4: EQUALITY =================
        System.out.println(oneFoot.equals(new QuantityLength(12.0, LengthUnit.INCHES))); // true

        // ================= UC5: CONVERSION =================
        System.out.println(oneFoot.convertTo(LengthUnit.INCHES)); // 12
        System.out.println(oneYard.convertTo(LengthUnit.FEET));    // 3

        // ================= UC6: ADD (DEFAULT UNIT) =================
        System.out.println(oneFoot.add(twelveInches)); // 2 FEET

        // ================= UC7: ADD (EXPLICIT UNIT) =================
        System.out.println(oneFoot.add(twelveInches, LengthUnit.FEET));   // 2 FEET
        System.out.println(oneFoot.add(twelveInches, LengthUnit.INCHES)); // 24 INCHES
        System.out.println(oneFoot.add(twelveInches, LengthUnit.YARDS));  // ~0.667 YARDS

        // ================= EXTRA CASES =================
        System.out.println(oneYard.add(new QuantityLength(3.0, LengthUnit.FEET), LengthUnit.YARDS)); // 2 YARDS
        System.out.println(oneCm.convertTo(LengthUnit.INCHES)); // ~1 INCH
    }
}