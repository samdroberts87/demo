package com.example.visa_movement_tracker.entities.request_entities.adapters;

import com.example.visa_movement_tracker.entities.Person;
import com.example.visa_movement_tracker.entities.Visa;
import com.example.visa_movement_tracker.entities.request_entities.VisaRequest;

public class VisaAdapter {
    public static VisaRequest fromVisa(Visa visa) {
        VisaRequest request = new VisaRequest();
        request.setId(visa.getId());
        request.setPersonId(visa.getPerson() != null ? visa.getPerson().getId() : null);
        request.setCountry(visa.getCountry());
        request.setVisaType(visa.getVisaType());
        request.setVisaStartDate(visa.getVisaStartDate());
        request.setVisaExpiryDate(visa.getVisaExpiryDate());
        return request;
    }

    public static Visa toVisa(VisaRequest request, Person person) {
        Visa visa = new Visa();
        visa.setId(request.getId());
        visa.setPerson(person);
        visa.setCountry(request.getCountry());
        visa.setVisaType(request.getVisaType());
        visa.setVisaStartDate(request.getVisaStartDate());
        visa.setVisaExpiryDate(request.getVisaExpiryDate());
        return visa;
    }

}