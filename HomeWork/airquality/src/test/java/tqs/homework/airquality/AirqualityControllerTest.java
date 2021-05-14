package tqs.homework.airquality;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AirqualityControllerTest {

    private AirData ad;

    @Mock(lenient = true)
    public static Cache cache;

    @InjectMocks
    private AirqualityController airqualityController;

    @BeforeEach
    void setUp() {
        ad = new AirData("aveiro", "url", 28, "o3", new JSONObject());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getWhenInCache() throws IOException, InterruptedException {
        Mockito.when(cache.get("aveiro")).thenReturn(ad);
        assertEquals(airqualityController.getDataFromCache("aveiro"), ad);
        Mockito.verify(cache, Mockito.times(1)).get("aveiro");
    }


    @Test
    public void getWhenNotInCache() throws IOException, InterruptedException {
        Mockito.when(cache.get("aveiro")).thenReturn(null);
        assertEquals(airqualityController.getDataFromCache("aveiro"), null);
        Mockito.verify(cache, Mockito.times(1)).get("aveiro");
    }
}