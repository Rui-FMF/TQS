package tqs.homework.airquality;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class AirData {
    private String name;
    private String url;
    private Integer aqi;
    private String dominantPollutant;
    private Map<String, Object> polMap;




    public AirData() {
        super();
    }

    public AirData(String name, String url, Integer aqi, String dominantPollutant, JSONObject iaqi) {
        this.name = name;
        this.url = url;
        this.aqi = aqi;
        this.dominantPollutant = dominantPollutant;
        try {
            this.polMap = this.JSONtoMap(iaqi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public String getDominantPollutant() {
        return dominantPollutant;
    }

    public void setDominantPollutant(String dominantPollutant) {
        this.dominantPollutant = dominantPollutant;
    }

    public Map<String, Object> getPolMap() {
        return polMap;
    }

    public void setPolMap(Map<String, Object> polMap) {
        this.polMap = polMap;
    }

    public Map<String, Object> JSONtoMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            var value = object.get(key);

            if(value instanceof JSONArray) {
                value = JSONtoList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = JSONtoMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public List<Object> JSONtoList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = JSONtoList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = JSONtoMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }
}

