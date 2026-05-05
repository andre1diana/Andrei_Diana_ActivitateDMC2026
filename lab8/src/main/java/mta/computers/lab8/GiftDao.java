package mta.computers.lab8;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GiftDao {
    @Insert
    long insert(Gift gift);

    @Query("SELECT * FROM gifts")
    List<Gift> getAllGifts();

    @Query("SELECT * FROM gifts WHERE message = :message LIMIT 1")
    Gift getGiftByMessage(String message);

    @Query("SELECT * FROM gifts WHERE weight BETWEEN :minWeight AND :maxWeight")
    List<Gift> getGiftsInWeightRange(int minWeight, int maxWeight);

    @Query("DELETE FROM gifts WHERE weight < :weight")
    int deleteGiftsWithWeightLessThan(int weight);

    @Query("UPDATE gifts SET weight = weight + 1 WHERE message LIKE :pattern")
    void incrementWeightForMessagesStartingWith(String pattern);
}
