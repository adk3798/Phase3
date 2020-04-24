Harsh Patel
Adam King

Phase 3 readme

Setup:
  - Get source code. Should contain directories App and PHPandSQL
  - From now on I'll refer to the directory where you've stored the source code as "BASE"
  - Make sure xampp server is setup
  - Copy BASE/PHPandSQL folder ito the [Your install location of xampp]/xampp/htdocs/dashboard
  - Make sure your server has a database named db2
  - Run the file BASE/PHPandSQL/setup.sql on the db2 database to create and populate the tables
  - Open Android Studio
  - Select option to open an existing Android Studio project
  - Find and select the BASE/App/Db2_Phase3 directory and click OK to open the project
  - Open the file res/values/strings.xml in Android Studio
  - Find the line in strings.xml that defines "BASE_SERVER_URL"
  - Change this line so it links to the folder where you stored the PHP and SQL files earlier.
    ~ This requires getting the ip address for your server (not localhost). I found mine in the netstat section of the xampp controller
    ~ Should be formatted something like "http://[IP ADDRESS]/dashboard/PHPandSQL/" (don't forget the ending slash)
  - Setup a device simulator to run the app in Android Studio. (I personally used a Pixel 2)
  - Run the app using the Run app button (looks like a play button) near the top of the Android Studio window.