package com.example.weatherupdates.repo;

import com.example.weatherupdates.model.WeatherUpdateResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class WeatherForecastCacheTest {

    @Mock
    private WeatherUpdateResponse mockWeatherUpdatesResponse;
    private WeatherForecastCache cache;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        cache = spy(new WeatherForecastCache());
    }

    @After
    public void afterTest() {
        cache.flush();
    }

    @Test
    public void testGetWeatherUpdatesResponse(){
        cache.setWeatherUpdatesResponse(mockWeatherUpdatesResponse);
        when(cache.getWeatherUpdatesResponse()).thenReturn(mockWeatherUpdatesResponse);
        assertThat(cache.getWeatherUpdatesResponse(), is(mockWeatherUpdatesResponse));
    }

    @Test
    public void flushClearsTheCache(){
        WeatherForecastCache cache = setupCacheForFlush();
        cache.flush();

        assertNull(cache.getWeatherUpdatesResponse());
    }

    private WeatherForecastCache setupCacheForFlush() {
        cache.setWeatherUpdatesResponse(new WeatherUpdateResponse());
        return  cache;
    }
}