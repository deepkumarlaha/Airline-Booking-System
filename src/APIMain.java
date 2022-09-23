/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Tapas
 */
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;


public class APIMain {
    
    String flightDate;
    String departureIATA;
    String arivalIATA;
    
    //String url1="https://timetable-lookup.p.rapidapi.com/TimeTable/"+departureIATA+"/"+arivalIATA+"/"+flightDate+"/?7Day=N&Results=20&Connection=NONSTOP&Sort=Departure";
   
    
    
    public void APIFlightDetails() throws IOException, InterruptedException {
        //object declaration
        JSONConvert JSONC = new JSONConvert();

        //TODO: binding url from user import

        //API: https://rapflightlookup/api/timetable-lookup
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://timetable-lookup.p.rapidapi.com/TimeTable/"+departureIATA+"/"+arivalIATA+"/"+flightDate+"/?7Day=N&Results=20&Connection=NONSTOP&Sort=Departure"))
                
                //.uri(URI.create(url1))
                .header("X-RapidAPI-Key", "49a135ee95mshd9d1d14075fe0e5p1415e8jsn341f3394974d")
                .header("X-RapidAPI-Host", "timetable-lookup.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());

        JSONC.FLIGHT_DETAILS_XML_STRING=response.body();
        JSONC.flightDetailsJSON();
    }

}

