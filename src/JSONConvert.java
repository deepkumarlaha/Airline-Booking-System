/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Tapas
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.proteanit.sql.DbUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.json.JSONException;

public class JSONConvert {

    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public String FLIGHT_DETAILS_XML_STRING;


    public void flightDetailsJSON() {
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(FLIGHT_DETAILS_XML_STRING);
            String jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            //System.out.println(jsonPrettyPrintString);
            JSONObject jsonObject = new JSONObject(jsonPrettyPrintString);
//            System.out.println(jsonObject);

            //storing FlightDetails array inside an arr to access the inside objects.
            JSONArray arr = jsonObject.getJSONObject("OTA_AirDetailsRS").getJSONArray("FlightDetails");
            
            

            //display flight details
            for (int i = 0; i < arr.length(); i++) {
                String departureAirportCode = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getJSONObject("DepartureAirport").getString("LocationCode");
                String departureAirportName = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getJSONObject("DepartureAirport").getString("FLSLocationName");
                String arrivalAirportCode = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getJSONObject("ArrivalAirport").getString("LocationCode");
                String arrivalAirportName = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getJSONObject("ArrivalAirport").getString("FLSLocationName");
                String airlineName = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getJSONObject("MarketingAirline").getString("CompanyShortName");
                String departureDateTime = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getString("DepartureDateTime");
                String arrivalDateTime = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getString("ArrivalDateTime");
                String uuid = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getString("FLSUUID");
                int flightNumber = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getInt("FlightNumber");
                String journeyDuration = arr.getJSONObject(i).getJSONObject("FlightLegDetails").getString("JourneyDuration");

                System.out.println(departureAirportCode + " " + departureAirportName + " " + arrivalAirportCode + " " + arrivalAirportName + "\n" +
                airlineName + " " + departureDateTime + " " + arrivalDateTime + " " + uuid + " " + flightNumber + " " + journeyDuration + "\n");
                
                //Enter the flight details to the database and show it to the user
                
                String time[]=departureDateTime.split("T");
                String time1[]=arrivalDateTime.split("T");
                String flightNumber1=String.valueOf(flightNumber);
                
                try{
                    
            //String departureAirportCode1 = null;
            
                    
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/ATBS?"+"user=root&password");
            
            String q2="insert into `flightdetails`(`departurecode`,`departureairportname`,`arrivalcode`,`arrivalairportname`,`airlinename`,`departuredate`,`departuretime`,`arrivaldate`,`arrivaltime`,`uuid`,`flightnumber`,`journeyduration`) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst=conn.prepareStatement(q2);
            pst.setString(1, departureAirportCode);
            pst.setString(2, departureAirportName);
            pst.setString(3, arrivalAirportCode);
            pst.setString(4, arrivalAirportName);
            pst.setString(5, airlineName);
            pst.setString(6, time[0]);
            pst.setString(7, time[1]);
            pst.setString(8, time1[0]);
            pst.setString(9, time1[1]);
            pst.setString(10, uuid);
            pst.setString(11, flightNumber1);
            pst.setString(12, journeyDuration);
            pst.executeUpdate();
                        
            
            
            }
            catch(Exception e){
                System.out.println(e.toString());
                //msg3.setText(e.toString());
            }

                
                


                
                
            }
            FlightPage ob=new FlightPage();
            Home ob1=new Home();
            ob1.setVisible(false);
            ob.setVisible(true);

        } catch (JSONException je) {
            System.out.println(je.toString());
        }


    }
}
