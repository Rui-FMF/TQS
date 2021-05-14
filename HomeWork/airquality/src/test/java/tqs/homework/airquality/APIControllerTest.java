package tqs.homework.airquality;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(APIController.class)
class APIControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirqualityController airqualityController;

    @Test
    void getAirDataByLocation() throws Exception {
        AirData lisbon_data = new AirData("Lisbon", "url", 30, "o3", new JSONObject());

        given(airqualityController.getAirData("lisbon")).willReturn(lisbon_data);
        mvc.perform(get("/api/location/lisbon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", hasKey("name")))
                .andExpect(jsonPath("$[0]", hasKey("url")))
                .andExpect(jsonPath("$[0]", hasKey("aqi")))
                .andExpect(jsonPath("$[0]", hasKey("dominantPollutant")))
                .andExpect(jsonPath("$[0]", hasKey("pol_map")));
    }

    @Test
    void getAirDataByCoordinates() throws Exception {
        AirData coord_data = new AirData("geo:40;-8", "url", 30, "o3", new JSONObject());

        given(airqualityController.getAirData("geo:40;-8")).willReturn(coord_data);
        mvc.perform(get("/api/coordinates?lat=40&lng=-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", hasKey("name")))
                .andExpect(jsonPath("$[0]", hasKey("url")))
                .andExpect(jsonPath("$[0]", hasKey("aqi")))
                .andExpect(jsonPath("$[0]", hasKey("dominantPollutant")))
                .andExpect(jsonPath("$[0]", hasKey("pol_map")));
    }

    @Test
    void getAirDataByCurrentLocation() throws Exception {
        AirData here_data = new AirData("here", "url", 30, "o3", new JSONObject());

        given(airqualityController.getAirData("here")).willReturn(here_data);
        mvc.perform(get("/api/here"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", hasKey("name")))
                .andExpect(jsonPath("$[0]", hasKey("url")))
                .andExpect(jsonPath("$[0]", hasKey("aqi")))
                .andExpect(jsonPath("$[0]", hasKey("dominantPollutant")))
                .andExpect(jsonPath("$[0]", hasKey("pol_map")));
    }
}