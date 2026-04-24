public class QuantityMeasurementApp {

    // Enum (same as UC5)
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

    // Quantity class
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
                throw new IllegalArgumentException("Invalid value");
            }
        }

        private double toBaseUnit() {
            return unit.toFeet(value);
        }

        // ✅ UC6: Instance method (returns new object)
        public Quantity add(Quantity other) {

            if (other == null) {
                throw new IllegalArgumentException("Other quantity cannot be null");
            }

            // Convert both to base unit (feet)
            double sumInFeet = this.toBaseUnit() + other.toBaseUnit();

            // Convert back to THIS unit (first operand rule)
            double resultValue = this.unit.fromFeet(sumInFeet);

            return new Quantity(resultValue, this.unit);
        }

        // Optional static method (overloaded style)
        public static Quantity add(Quantity q1, Quantity q2) {
            if (q1 == null || q2 == null) {
                throw new IllegalArgumentException("Operands cannot be null");
            }
            return q1.add(q2); // delegate
        }

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

    // Demo
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        Quantity result = q1.add(q2);

        System.out.println("Input: add(1 ft, 12 in)");
        System.out.println("Output: " + result); // Quantity(2.0, FEET)
    }
}