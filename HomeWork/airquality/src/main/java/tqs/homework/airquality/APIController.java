package tqs.homework.airquality;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api")
public class APIController {

    @Autowired
    AirqualityController airqualityController;

    @GetMapping("/location/{location}")
    public List<AirData> getAirDataByLocation(@PathVariable(value = "location") String location) throws IOException, InterruptedException {
        List<AirData> dataList = new ArrayList<>();

        var data = airqualityController.getAirData(location);
        if (data!=null){
            dataList.add(data);
        }
        return dataList;
    }

    @GetMapping("/coordinates")
    public List<AirData> getAirDataByCoordinates(@RequestParam(name="lat") String lat, @RequestParam(name="lng") String lng) throws IOException, InterruptedException {
        List<AirData> dataList = new ArrayList<>();

        String coords = "geo:"+lat+";"+lng;

        var data = airqualityController.getAirData(coords);

        if (data!=null){
            dataList.add(data);
        }
        return dataList;
    }


    @GetMapping("/here")
    public List<AirData> getAirDataByCurrentLocation() throws IOException, InterruptedException {
        List<AirData> dataList = new ArrayList<>();

        var data = airqualityController.getAirData("here");

        if (data!=null){
            dataList.add(data);
        }
        return dataList;
    }

    @GetMapping("/cache")
    public Map<String, Object> getCacheStats() {
        return airqualityController.locationsCache.getStats();
    }
}
