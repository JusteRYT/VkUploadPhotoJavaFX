module com.example.vkupload {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires sdk;


    opens com.example.vkupload to javafx.fxml;
    exports com.example.vkupload;
}