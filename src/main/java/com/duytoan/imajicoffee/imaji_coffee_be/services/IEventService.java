package com.duytoan.imajicoffee.imaji_coffee_be.services;

import com.duytoan.imajicoffee.imaji_coffee_be.dto.EventDto;

import java.util.List;

public interface IEventService {
    public abstract List<EventDto> getPageUpComingEvent(int page, int size);
    public abstract List<EventDto> getPageClosedEvent(int page, int size);
}
