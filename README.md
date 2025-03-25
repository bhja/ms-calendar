Requirements.
*  Needs gradle and 
* JDK 21 and above.

#### STEPS
1. Replace the properties with the corresponding azure properties under application.properties.
   azure.tenant-id=
   azure.client-id=
   azure.client-secret=
   azure.redirect-uri=
   Build the code using the gradle command
   ./gradle build
   Start the jar under target as
   <b> java -jar build/libs/calendar-1.0.jar </b>
2. Login to the app using the url localhost:8080
3. Follow the steps as prompted. The access token and refresh token are displayed.
4. Click on Refresh . A new set of access and refresh token will be displayed.


