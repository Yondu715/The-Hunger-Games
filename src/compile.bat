javac --module-path .\Libs\javafx\lib --add-modules=javafx.controls,javafx.fxml,mysql.connector.java --class-path "..;.;" .\DB\*.java
javac --module-path .\Libs\javafx\lib --add-modules=javafx.controls,javafx.fxml,mysql.connector.java --class-path "..;.;" .\SceneSwitcher.java
javac --module-path .\Libs\javafx\lib --add-modules=javafx.controls,javafx.fxml,mysql.connector.java --class-path "..;.;Libs\kryonet\jars\production\onejar\kryonet-2.21-all.jar" .\game\client\*.java
javac --module-path .\Libs\javafx\lib --add-modules=javafx.controls,javafx.fxml,mysql.connector.java --class-path "..;.;Libs\kryonet\jars\production\onejar\kryonet-2.21-all.jar" .\game\*.java
javac --module-path .\Libs\javafx\lib --add-modules=javafx.controls,javafx.fxml,mysql.connector.java --class-path "..;.;Libs\kryonet\jars\production\onejar\kryonet-2.21-all.jar" .\game\server\*.java
javac --module-path .\Libs\javafx\lib --add-modules=javafx.controls,javafx.fxml,mysql.connector.java --class-path "..;.;" .\Main.java
pause