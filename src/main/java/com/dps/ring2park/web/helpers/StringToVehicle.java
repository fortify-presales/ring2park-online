package com.dps.ring2park.web.helpers;

import org.springframework.binding.convert.converters.StringToObject;

import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.VehicleService;

public class StringToVehicle extends StringToObject {

	private VehicleService VehicleService;

	public StringToVehicle(VehicleService VehicleService) {
		super(Vehicle.class);
		this.VehicleService = VehicleService;
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Object toObject(String string,  Class targetClass) {
		if (string != null && string.length() > 0) {
			return VehicleService.findVehicleById(Long.valueOf(string));
		} else {
			return null;
		}
	}

	@Override
	protected String toString(Object object) {
		Vehicle vehicle = (Vehicle) object;
		return vehicle.toString();
	}

}
