package Controllers;

import DBConnect.MyDBConnectivity;

public interface SystemController {
    void setId (int id);
    void setDatabaseC(MyDBConnectivity db);
}
