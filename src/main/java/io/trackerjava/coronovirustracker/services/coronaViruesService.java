package io.trackerjava.coronovirustracker.services;

import io.trackerjava.coronovirustracker.models.LoactionStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class coronaViruesService {

    private static String VIRUS_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    private List<LoactionStats> allStats=new ArrayList<>();

    public List<LoactionStats> getAllStats() {
        return allStats;
    }

//    public void setAllStats(List<LoactionStats> allStats) {
//        this.allStats = allStats;
//    }

    @PostConstruct     //It tells that when we start a application and instance of service class is created by spring then execute this method
    @Scheduled(cron = "* * 1 * * *") //Run the method of the regular basis    1 means that it will update after every 1 hour
    public void fetchData() throws IOException, InterruptedException {
        List<LoactionStats> newStats=new ArrayList<>();
        HttpClient client=HttpClient.newHttpClient();
        HttpRequest request= (HttpRequest) HttpRequest.newBuilder()  //Client to server
                    .uri(URI.create(VIRUS_URL))     //Requesting the String URI to go to the particular path
                    .build();
        HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString()); //It sends the response back
        //System.out.println(httpResponse.body());
        StringReader csvreader=new StringReader(httpResponse.body());  //Parse the String(Convert the String into CSV Format)
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvreader);
        for (CSVRecord record : records) {
            LoactionStats loactionStats=new LoactionStats();
            loactionStats.setState(record.get("Province/State"));
            loactionStats.setCountry(record.get("Country/Region"));
            int latestCases=Integer.parseInt(record.get(record.size()-1));
            int prevCases=Integer.parseInt(record.get(record.size()-2));
           // loactionStats.setLatestTotalStats(Integer.parseInt(record.get(record.size()-1)));
            loactionStats.setDifferenceCases(latestCases-prevCases);
            loactionStats.setLatestTotalStats(Integer.parseInt(record.get(record.size()-1)));
            System.out.println(loactionStats);
            newStats.add(loactionStats);
        }
        this.allStats=newStats;   //Always allStats updated with the newStats list according to different cases increased day by day
    }

}
