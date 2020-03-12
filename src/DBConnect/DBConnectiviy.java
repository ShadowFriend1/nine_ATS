package DBConnect;

interface DBConnectivity {
    String dafaultAddress = "jdbc:h2:localhost:8000/ATS";
    String defaultUserName = "sa";
    String defaultPassword = "";

    String update();

    String query();
}