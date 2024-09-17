package HamzaKababji.payroll;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EarningSource {
    private StringProperty code;
    private StringProperty name;
    public static int earningSourceID = 0;

    public EarningSource(){
        this.code = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
    }

    public EarningSource(String nM){
        earningSourceID = earningSourceID + 1;
        code = new SimpleStringProperty(String.valueOf(earningSourceID));
        name = new SimpleStringProperty(nM);
    }

    public EarningSource(String ID, String nM){
        code = new SimpleStringProperty(ID);
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
