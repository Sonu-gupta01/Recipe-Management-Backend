package com.recipe.management.queries;

public class MessageQueries {

    public static final String SENDER_ID = "senderId";
    public static final String RECEIVER_ID = "receiverId";
    public static final String FETCH_ALL_MESSAGE="FROM Messages";
    public static final String FETCH_MESSAGE_BETWEEN_SENDER_AND_RECEIVER = "FROM Messages " +
            "WHERE (sender.id = :senderId AND receiver.id = :receiverId) " +
            "   OR (sender.id = :receiverId AND receiver.id = :senderId) " +
            "ORDER BY sentOn";
}
