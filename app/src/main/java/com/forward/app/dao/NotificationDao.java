package com.forward.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.forward.app.models.Notification;

@Dao
public interface NotificationDao {
    @Insert
    void insertNotifications(Notification... notifications);

    @Update
    void updateNotifications(Notification... notifications);

    @Delete
    void deleteNotifications(Notification... notifications);
}
