package tqs.homework.airquality;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONObject;


@Controller
public class AirqualityController {

    static String apiToken = "e99fa04d48da6b1e00dd86f34223824f9be60d9d";
    String baseUrl = "https://api.waqi.info/feed/";
    Cache locationsCache = new Cache(100, 50);

    @GetMapping("/main")
    public String main(@RequestParam(name="location", required=false, defaultValue="Lisbon") String location, Model model) throws IOException, InterruptedException {
        model.addAttribute("location", location);

        var data = getAirData(location);
        if (data==null) {
            model.addAttribute("loc_err", "true");
            return "main";
        }

        model.addAttribute("loc_err", "false");
        model.addAttribute("aqi", data.getAqi());
        model.addAttribute("dom_pol", data.getDominantPollutant());
        model.addAttribute("pol_map", data.getPolMap());
        return "main";
    }

    public AirData getAirData(String location) throws IOException, InterruptedException {

        AirData data = getDataFromCache(location);

        if(data == null) {
            data = getDataFromAPI(location);
            if (data!=null){
                locationsCache.put(location.toLowerCase(), data);
            }
        }
        return data;
    }

    public AirData getDataFromCache(String location){
        return locationsCache.get(location.toLowerCase());
    }

    public AirData getDataFromAPI(String location) throws IOException, InterruptedException {

        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.waqi.info/feed/"+location+"/?token="+ apiToken))
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpResponse<String> response = HttpClient
                .newBuilder()
                .proxy(ProxySelector.getDefault())
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        try {
            var jsonResponse = new JSONObject(response.body());
            String status = (String) jsonResponse.get("status");
            if(status.equals("error")){
                return null;
            }
            JSONObject data = (JSONObject) jsonResponse.get("data");

            JSONObject city = (JSONObject) data.get("city");
            String cityName = (String) city.get("name");

            String url = (String) city.get("url");

            Integer aqi = (Integer) data.get("aqi");

            String dominantPollutant = (String) data.get("dominentpol");
            JSONObject iaqi = (JSONObject) data.get("iaqi");

            return new AirData(cityName, url, aqi, dominantPollutant, iaqi);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
