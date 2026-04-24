public class QuantityMeasurementApp {

    // Step 1: Enum for Units
    public enum LengthUnit {
        FEET(1.0),
        INCH(1.0 / 12.0); // 1 inch = 1/12 feet

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }
    }

    // Step 2: Generic Quantity Class
    public static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            if (Double.isNaN(value)) {
                throw new IllegalArgumentException("Value must be numeric");
            }
            this.value = value;
            this.unit = unit;
        }

        public double toBaseUnit() {
            return unit.toFeet(value);
        }

        // Step 3: equals() with conversion
        @Override
        public boolean equals(Object obj) {

            // Reflexive
            if (this == obj) return true;

            // Null + Type check
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            // Compare after converting to same base (feet)
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(toBaseUnit());
        }
    }

    // Main method (demo)
    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCH);

        Quantity q3 = new Quantity(1.0, LengthUnit.INCH);
        Quantity q4 = new Quantity(1.0, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        System.out.println("Input: Quantity(1.0, INCH) and Quantity(1.0, INCH)");
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");
    }
}