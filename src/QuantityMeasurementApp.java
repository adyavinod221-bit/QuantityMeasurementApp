// ======================= LENGTH UNIT (UC8) =======================
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private final double toFeet;

    LengthUnit(double toFeet) {
        this.toFeet = toFeet;
    }

    public double convertToBaseUnit(double value) {
        return value * toFeet;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeet;
    }
}

// ======================= WEIGHT UNIT (UC9) =======================
enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKg;

    WeightUnit(double toKg) {
        this.toKg = toKg;
    }

    public double convertToBaseUnit(double value) {
        return value * toKg;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKg;
    }
}

// ======================= LENGTH CLASS =======================
class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value) || unit == null)
            throw new IllegalArgumentException("Invalid input");
        this.value = value;
        this.unit = unit;
    }

    public QuantityLength convertTo(LengthUnit target) {
        double base = unit.convertToBaseUnit(value);
        return new QuantityLength(target.convertFromBaseUnit(base), target);
    }

    public QuantityLength add(QuantityLength other, LengthUnit target) {
        double sum =
                this.unit.convertToBaseUnit(this.value) +
                        other.unit.convertToBaseUnit(other.value);

        return new QuantityLength(target.convertFromBaseUnit(sum), target);
    }

    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuantityLength o)) return false;
        return Double.compare(
                this.unit.convertToBaseUnit(this.value),
                o.unit.convertToBaseUnit(o.value)
        ) == 0;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}

// ======================= WEIGHT CLASS (UC9) =======================
class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (!Double.isFinite(value) || unit == null)
            throw new IllegalArgumentException("Invalid input");

        this.value = value;
        this.unit = unit;
    }

    public QuantityWeight convertTo(WeightUnit target) {
        double base = unit.convertToBaseUnit(value);
        return new QuantityWeight(target.convertFromBaseUnit(base), target);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit target) {
        double sum =
                this.unit.convertToBaseUnit(this.value) +
                        other.unit.convertToBaseUnit(other.value);

        return new QuantityWeight(target.convertFromBaseUnit(sum), target);
    }

    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof QuantityWeight o)) return false;

        return Double.compare(
                this.unit.convertToBaseUnit(this.value),
                o.unit.convertToBaseUnit(o.value)
        ) == 0;
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}

// ======================= MAIN APP (UC5–UC9 DEMO) =======================
public class QuantityMeasurementApp {

    public static void main(String[] args) {

        // ================= LENGTH =================
        QuantityLength oneFoot = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength twelveInches = new QuantityLength(12.0, LengthUnit.INCHES);

        System.out.println(oneFoot.equals(twelveInches)); // true
        System.out.println(oneFoot.convertTo(LengthUnit.INCHES)); // 12
        System.out.println(oneFoot.add(twelveInches)); // 2 FEET
        System.out.println(oneFoot.add(twelveInches, LengthUnit.YARDS)); // ~0.667

        // ================= WEIGHT =================
        QuantityWeight oneKg = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight gram1000 = new QuantityWeight(1000.0, WeightUnit.GRAM);
        QuantityWeight pound = new QuantityWeight(2.20462, WeightUnit.POUND);

        System.out.println(oneKg.equals(gram1000)); // true
        System.out.println(oneKg.convertTo(WeightUnit.GRAM)); // 1000
        System.out.println(oneKg.add(gram1000)); // 2 KG
        System.out.println(oneKg.add(pound, WeightUnit.KILOGRAM)); // mixed add
    }
}