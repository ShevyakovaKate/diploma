package backend.demo.model;

public class Parameter {

    private String _name;
    private double _value;
    private double _minValue;
    private double _maxValue;

    public Parameter() {
    }

    public Parameter(String _name, double _value, double _minValue, double _maxValue) {
        this._name = _name;
        this._value = _value;
        this._minValue = _minValue;
        this._maxValue = _maxValue;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_value() {
        return _value;
    }

    public void set_value(double _value) {
        this._value = _value;
    }

    public double get_minValue() {
        return _minValue;
    }

    public void set_minValue(double _minValue) {
        this._minValue = _minValue;
    }

    public double get_maxValue() {
        return _maxValue;
    }

    public void set_maxValue(double _maxValue) {
        this._maxValue = _maxValue;
    }
}
