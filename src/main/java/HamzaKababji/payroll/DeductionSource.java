package HamzaKababji.payroll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeductionSource {
    private StringProperty code;
    private StringProperty name;
    public static int deductionSourceID = 0;

    public DeductionSource(){
        code = new SimpleStringProperty();
        name = new SimpleStringProperty();
    }

    public DeductionSource(String nM){
        deductionSourceID ++;
        code = new SimpleStringProperty(String.valueOf(deductionSourceID));
        name = new SimpleStringProperty(nM);
    }
    public DeductionSource(String cd, String nM){
        code = new SimpleStringProperty(cd);
        name = new SimpleStringProperty(nM);
    }

    public void setCode(String _code) {code.set(_code);}
    public void setName(String _code) {
        name.set(_code);
    }
    public String getCode() {
        return code.get();
    }
    public String getName() {
        return name.get();
    }
    public StringProperty codeProperty() {
        return code;
    }
    public StringProperty nameProperty() {
        return name;
    }
}
