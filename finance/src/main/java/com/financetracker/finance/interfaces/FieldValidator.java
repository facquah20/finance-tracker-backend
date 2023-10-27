package com.financetracker.finance.interfaces;

public interface FieldValidator<T> {
    /**
     * @param {Object}  modelObject model to validate
     * @return String
     * @description The method ensures that all fields of an object is properly validated before been saved 
     * in the database
     */
    public String validateField(T modelObject);
}
