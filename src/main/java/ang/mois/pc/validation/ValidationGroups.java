package ang.mois.pc.validation;

// Interface acts as markers for validation "cases"
public interface ValidationGroups {

    // validation rules to apply only on creation
    interface OnCreate {}

    // validation rules to apply only on update
    interface OnUpdate {}
}
