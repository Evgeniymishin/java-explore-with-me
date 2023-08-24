package ru.practicum;

import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class Constant {
    public static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(Constant.DATEFORMAT);

    public enum State {
        PENDING,
        PUBLISHED,
        CANCELED
    }

    public enum UserRequestSort {
        EVENT_DATE,
        VIEWS
    }

    public enum StateAction {
        SEND_TO_REVIEW,
        CANCEL_REVIEW,
        PUBLISH_EVENT,
        REJECT_EVENT
    }

    public enum StateParticipation {
        CONFIRMED,
        REJECTED,
        PENDING,
        CANCELED
    }
}
