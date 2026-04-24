public class QuantityMeasurementApp {

    // Inner class representing Feet measurement
    public static class Feet {
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        // Override equals() method
        @Override
        public boolean equals(Object obj) {

            // 1. Same reference (Reflexive)
            if (this == obj) {
                return true;
            }

            // 2. Null check + Type check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // 3. Cast
            Feet other = (Feet) obj;

            // 4. Compare using Double.compare()
            return Double.compare(this.value, other.value) == 0;
        }

        // 5. hashCode (best practice)
        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    // Main method (UC1 execution)
    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        boolean result = f1.equals(f2);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + result + ")");
    }
}