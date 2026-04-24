public class QuantityMeasurementApp {

    // Enum with conversion factors (BASE = FEET)
    public enum LengthUnit {

        FEET(1.0),

        INCH(1.0 / 12.0),

        YARD(3.0),

        CENTIMETER(0.0328084);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double feetValue) {
            return feetValue / toFeetFactor;
        }
    }

    // Quantity class (unchanged logic + conversion support)
    public static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            validate(value, unit);
            this.value = value;
            this.unit = unit;
        }

        private static void validate(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Value must be finite");
            }
        }

        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        // Instance conversion → returns NEW object (immutability)
        public Quantity convertTo(LengthUnit targetUnit) {
            validate(this.value, targetUnit);

            double base = toBaseUnit();
            double converted = targetUnit.fromFeet(base);

            return new Quantity(converted, targetUnit);
        }

        // equals() (same as UC3/UC4)
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    // ✅ STATIC API (Main UC5 requirement)
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units cannot be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        // Normalize to base (feet)
        double base = source.toFeet(value);

        // Convert to target
        return target.fromFeet(base);
    }

    // Method Overloading (as required)
    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit from,
                                                     LengthUnit to) {
        return convert(value, from, to);
    }

    public static Quantity demonstrateLengthConversion(Quantity quantity,
                                                       LengthUnit target) {
        return quantity.convertTo(target);
    }

    // Demo methods
    public static void main(String[] args) {

        System.out.println("convert(1.0, FEET, INCH) = "
                + convert(1.0, LengthUnit.FEET, LengthUnit.INCH));

        System.out.println("convert(3.0, YARD, FEET) = "
                + convert(3.0, LengthUnit.YARD, LengthUnit.FEET));

        System.out.println("convert(36.0, INCH, YARD) = "
                + convert(36.0, LengthUnit.INCH, LengthUnit.YARD));

        System.out.println("convert(1.0, CENTIMETER, INCH) = "
                + convert(1.0, LengthUnit.CENTIMETER, LengthUnit.INCH));
    }
}