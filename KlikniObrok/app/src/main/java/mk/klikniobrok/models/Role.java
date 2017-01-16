package mk.klikniobrok.models;

/**
 * Created by gjorgjim on 1/16/17.
 */

public enum Role {
    CUSTOMER {
        @Override
        public String toString() {
            return "CUSTOMER";
        }
    },
    EMPLOYEE {
        @Override
        public String toString() {
            return "EMPLOYEE";
        }
    },
    MANAGER {
        @Override
        public String toString() {
            return "MANAGER";
        }
    }
}