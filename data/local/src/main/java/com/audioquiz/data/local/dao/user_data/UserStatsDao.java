package com.audioquiz.data.local.dao.user_data;

public interface UserStatsDao {

}

/*@Dao
public interface UserStatsDao extends BaseDao<UserStatsEntity>{

    @Query("SELECT * FROM user_profile LIMIT 1")
    Single<UserProfileEntity> getUserProfile();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserStats(UserStatsEntity userStats);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveFrequencyStatsData(FrequencyStatsEntity frequencyStats);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCategoryStats(CategoryStatsEntity categoryStats);
}*/
