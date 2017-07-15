package Wooombat.JSON;


import java.util.Collection;

public class Constraint {

    private String alignment, axis;
    private Collection<Offset> offsets;

    Constraint(String alignment, String axis, Collection<Offset> offsets) {
        this.alignment = alignment;
        this.axis = axis;
        this.offsets = offsets;
    }
}
