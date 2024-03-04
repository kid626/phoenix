package com.bruce.phoenix.core.event.model;

import com.bruce.phoenix.core.event.service.DefaultEventServiceImpl;
import com.bruce.phoenix.core.event.service.EventService;
import lombok.Data;

import java.io.Serializable;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/1 16:04
 * @Author Bruce
 */
@Data
public class EventModel<T> implements Serializable {

    private String eventId;

    private EventService<T> eventService = new DefaultEventServiceImpl<>();

    private T params;


}
