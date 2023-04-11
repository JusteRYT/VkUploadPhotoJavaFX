module com.example.vkupload {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires sdk;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires java.xml.bind;


    opens com.example.vkupload to javafx.fxml;
    exports com.example.vkupload;
}