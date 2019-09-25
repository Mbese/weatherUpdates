package com.example.weatherupdates.viewmodel;

import androidx.lifecycle.Observer;

import com.example.weatherupdates.model.WeatherUpdateResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeatherUpdatesViewModelTest {
    private WeatherUpdatesViewModel viewModel;
    @Mock
    private Observer<WeatherUpdateResponse> mockObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        viewModel = spy(new WeatherUpdatesViewModel());
        viewModel.liveData.observeForever(mockObserver);
    }

    @Test
    public void loadWeatherUpdates() {
        WeatherUpdateResponse response = new WeatherUpdateResponse();
        viewModel.loadWeatherUpdates("123", "123");

        verify(mockObserver, times(1)).onChanged(response);
    }
}