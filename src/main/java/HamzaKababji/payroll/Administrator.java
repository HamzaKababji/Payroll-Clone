package HamzaKababji.payroll;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;

public class Administrator extends PayrollUser {
    private ObjectProperty<Date> dateCreated;

    // Constructors
    public Administrator(String id, String fullName, Date dateCreated, UserAccount account) {
        setID(id);
        setFullName(fullName);
        setUserAccount(account);
        this.dateCreated = new SimpleObjectProperty<>(dateCreated);
    }

    // dateCreated property
    public void setDateCreated(Date _date) {
        this.dateCreated.set(_date);
    }
    public ObjectProperty<Date> dateCreatedProperty() {
        return this.dateCreated;
    }
    public Date getDateCreated() {
        return this.dateCreated.get();
    }

}
