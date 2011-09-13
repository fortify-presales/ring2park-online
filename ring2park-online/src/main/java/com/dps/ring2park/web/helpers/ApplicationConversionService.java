package com.dps.ring2park.web.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.convert.converters.StringToDate;
import org.springframework.binding.convert.service.DefaultConversionService;
import org.springframework.stereotype.Component;

import com.dps.ring2park.service.VehicleService;

@Component("conversionService")
public class ApplicationConversionService extends DefaultConversionService {
        
    @Autowired
    private VehicleService vehicleService;        

    @Override
    protected void addDefaultConverters() {
        super.addDefaultConverters();
        StringToDate dateConverter = new StringToDate();
        dateConverter.setPattern("MM/dd/yyyy");
        addConverter(new StringToVehicle(vehicleService));
    }

}
