module com.example.javaf {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;


    opens com.example.javaf to javafx.fxml;
    exports com.example.javaf;
    exports Controller;
    opens Controller to javafx.fxml;
    opens Entity to org.hibernate.orm.core;
}