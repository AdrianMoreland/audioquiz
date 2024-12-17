package com.audioquiz.data.repository.rank;

import com.audioquiz.api.datasources.rank.RankAllTimeDataSource;
import com.audioquiz.core.domain.repository.rank.RankRepository;
import com.audioquiz.core.model.rank.RankEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;


public class RankRepository_Impl implements RankRepository {
    private static final String TAG = "RankRepository_Impl";
    private final RankAllTimeDataSource.Local local;
    private final RankAllTimeDataSource.Remote remote;
    private ArrayList<RankEntry> rankAllTimeList;
    private ArrayList<RankEntry> rankWeeklyEntriesList;

    @Inject
    public RankRepository_Impl(RankAllTimeDataSource.Remote remote,
                               RankAllTimeDataSource.Local rankAllTimeDataSource) {
        this.remote = remote;
        this.local = rankAllTimeDataSource;
    }


    public void init() {
        getRankStats();
    }

    private void getRankStats() {

    }


    // Getters for rank entries lists
    public List<RankEntry> getRankAllTimeList() {
        if (rankAllTimeList == null) {
            rankAllTimeList = new ArrayList<>();
            rankAllTimeList.addAll(getRankEntries());
        }
        return rankAllTimeList;
    }

    private Collection<? extends RankEntry> getRankEntries() {
            return null;
    }

    public List<RankEntry> getRankWeeklyEntriesList() {
        if (rankWeeklyEntriesList == null) {
            rankWeeklyEntriesList = new ArrayList<>();
            rankWeeklyEntriesList.addAll(getRankWeeklyEntries());
        }
        return rankWeeklyEntriesList;
    }

    private Collection<? extends RankEntry> getRankWeeklyEntries() {
            return null;
    }
}

/*    // Fetch the top N ranked users from Firestore based on total score and store them in SQLite
    private void getRankStats() {
        firebaseRepositoryFacade.fetchTopRankedUsers(30, this::processUserDocuments);
    }

    private void getRankWeeklyStats() {
        firebaseRepositoryFacade.fetchTopWeeklyRankedUsers(10, this::processWeeklyUserDocuments);
    }

    private void processUserDocuments(List<DocumentSnapshot> documents) {
        List<RankAllTimeEntity> rankEntries = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            RankAllTimeEntity rankAllTimeEntity = createRankEntryFromDocument(document);
            rankEntries.add(rankAllTimeEntity);
        }
        local.insertAll(rankEntries);
    }

    private void processWeeklyUserDocuments(List<DocumentSnapshot> documents) {
        List<RankWeeklyEntity> rankEntries = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            RankWeeklyEntity rankWeeklyEntity = createRankWeeklyEntryFromDocument(document);
            rankEntries.add(rankWeeklyEntity);
        }
        rankWeeklyDb.insertAll(rankEntries);
    }

    private RankWeeklyEntity createRankWeeklyEntryFromDocument(DocumentSnapshot document) {
        RankWeeklyEntity rankWeeklyEntity = new RankWeeklyEntity();
        rankWeeklyEntity.setUserId(document.getId());
        rankWeeklyEntity.setUsername(document.getString("username"));
        rankWeeklyEntity.setProfileImage(document.getString("profileImage"));
        rankWeeklyEntity.setTotalScore(Objects.requireNonNull(document.getLong("totalScore")).intValue());
        rankWeeklyEntity.setAverageScore(Objects.requireNonNull(document.getDouble("averageScore")));

        return rankWeeklyEntity;    }

    private RankAllTimeEntity createRankEntryFromDocument(DocumentSnapshot document) {
        RankAllTimeEntity rankAllTimeEntity = new RankAllTimeEntity();
        rankAllTimeEntity.setUserId(document.getId());
        rankAllTimeEntity.setUsername(document.getString("username"));
        rankAllTimeEntity.setProfileImage(document.getString("profileImage"));
        rankAllTimeEntity.setTotalScore(Objects.requireNonNull(document.getLong("totalScore")).intValue());
        rankAllTimeEntity.setAverageScore(Objects.requireNonNull(document.getDouble("averageScore")));

        return rankAllTimeEntity;
    }

    // Fetch rank entries from SQLite
    public List<RankEntry> getRankEntries() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                QuestionContract.RankEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                QuestionContract.RankEntry.COLUMN_NAME_TOTAL_SCORE + " DESC"
        );

        List<RankEntry> entries = new ArrayList<>();
        while (cursor.moveToNext()) {
            RankEntry rankEntry = cursorToRankEntry(cursor);
            entries.add(rankEntry);
        }
        cursor.close();
        return entries;
    }

    // Fetch weekly rank entries from SQLite
    public List<RankEntry> getRankWeeklyEntries() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                QuestionContract.RankWeekEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                QuestionContract.RankWeekEntry.COLUMN_NAME_LAST_SEVEN_DAYS + " DESC"
        );

        List<RankEntry> entries = new ArrayList<>();
        while (cursor.moveToNext()) {
            RankEntry rankEntry = cursorToRankWeeklyEntry(cursor);
            entries.add(rankEntry);
        }
        cursor.close();
        return entries;
    }

    // Convert Cursor to com.example.model.domain.RankEntry (for all-time ranks)
    private RankEntry cursorToRankEntry(Cursor cursor) {
        RankEntry rankEntry = new RankEntry();
        int userIdIndex = cursor.getColumnIndex(QuestionContract.RankEntry.COLUMN_NAME_USER_ID);
        int usernameIndex = cursor.getColumnIndex(QuestionContract.RankEntry.COLUMN_NAME_USERNAME);
        int totalScoreIndex = cursor.getColumnIndex(QuestionContract.RankEntry.COLUMN_NAME_TOTAL_SCORE);
        int averageScoreIndex = cursor.getColumnIndex(QuestionContract.RankEntry.COLUMN_NAME_AVERAGE_SCORE);

        if (userIdIndex != -1 && usernameIndex != -1 && totalScoreIndex != -1 && averageScoreIndex != -1) {
            String rankUserId = cursor.getString(userIdIndex);
            String rankUsername = cursor.getString(usernameIndex);
            int rankTotalScore = cursor.getInt(totalScoreIndex);
            double rankAverageScore = cursor.getDouble(averageScoreIndex);

            rankEntry.setUserId(rankUserId);
            rankEntry.setUsername(rankUsername);
            rankEntry.setTotalScore(rankTotalScore);
            rankEntry.setAverageScore(rankAverageScore);
        }
        // Set other fields as needed
        return rankEntry;
    }

    // Convert Cursor to com.example.model.domain.RankEntry (for weekly ranks)
    private RankEntry cursorToRankWeeklyEntry(Cursor cursor) {
        RankEntry rankEntry = new RankEntry();
        int userIdIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_USER_ID);
        int usernameIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_USERNAME);
        int totalScoreIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_TOTAL_SCORE);
        int lastSevenDaysIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_LAST_SEVEN_DAYS);

        if (userIdIndex != -1 && usernameIndex != -1 && totalScoreIndex != -1 && lastSevenDaysIndex != -1) {
            String rankUserId = cursor.getString(userIdIndex);
            String rankUsername = cursor.getString(usernameIndex);
            int rankTotalScore = cursor.getInt(totalScoreIndex);
            int rankLastSevenDays = cursor.getInt(lastSevenDaysIndex);

            rankEntry.setUserId(rankUserId);
            rankEntry.setUsername(rankUsername);
            rankEntry.setTotalScore(rankTotalScore);
            rankEntry.setWeeklyScore(rankLastSevenDays);
        }
        // Set other fields as needed
        return rankEntry;
    }
}*/

/*@InstallIn(SingletonComponent.class)
@Module
public class RankRepository_Impl implements RankRepository {

    private static final String TAG = "RankRepository_Impl";
    private final FirebaseRepository firebaseRepository;
    private final QuizDbHelper dbHelper;
    private final DataCache dataCache;
    private ArrayList<com.example.model.domain.RankEntry> rankWeeklyEntriesList;
    private ArrayList<com.example.model.domain.RankEntry> rankEntriesList;


    @Inject
    public RankRepository_Impl(FirebaseRepository firebaseRepository,
                              QuizDbHelper dbHelper,
                              DataCache dataCache) {
        this.firebaseRepository = firebaseRepository;
        this.dbHelper = dbHelper;
        this.dataCache = dataCache;
    }

    public void init() {
        getRankStats();
        getRankWeeklyStats();
    }

    // USER_RANKS_DATA_FETCHING_AND_LOGIC
    // --------------------------------------------------------------------------------------------
    //ALL_TIME_RANKS
    // Fetch the top 30 ranked users from Firestore and store them in SQLite
    public void getRankStats() {
        int userTotalScore = dataCache.getCachedObject().getGeneralStats().getTotalScore();

        firebaseRepository.getFirestore().collection("user_statistics")
                .whereGreaterThan("generalStatsDto.totalScore", userTotalScore)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int userRank = task.getResult().size() + 1;
                        if (userRank > 30) {
                            fetchUsers(userRank, userTotalScore, 5, Query.Direction.DESCENDING);
                            fetchUsers(userRank, userTotalScore, 4, Query.Direction.ASCENDING);
                        } else {
                            fetchTopUsers(30);
                        }
                    }
                });
    }
    private void fetchTopUsers(int topNumber) {
        firebaseRepository.getFirestore().collection("user_statistics")
                .orderBy("totalScore", Query.Direction.DESCENDING)
                .limit(topNumber)
                .get()
                .addOnCompleteListener(this::processUserDocuments);
    }
    private void fetchUsers(int userRank, int userTotalScore, int limit, Query.Direction direction) {
        firebaseRepository.getFirestore().collection("user_statistics")
                .orderBy("totalScore", direction)
                .startAt(userTotalScore)
                .limit(limit)
                .get()
                .addOnCompleteListener(this::processUserDocuments);
    }
    private void processUserDocuments(Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(QuestionContract.com.example.model.domain.RankEntry.TABLE_NAME, null, null);
            for (QueryDocumentSnapshot document : task.getResult()) {
                addRankEntryToDatabase(document, db);
            }
        }
    }
    private void addRankEntryToDatabase(QueryDocumentSnapshot document, SQLiteDatabase db) {
        com.example.model.domain.RankEntry rankEntry = new com.example.model.domain.RankEntry();
        rankEntry.setUserId(document.getId());
        rankEntry.setUsername(document.getString("username"));
        rankEntry.setProfileImage(document.getString("profileImage"));
        rankEntry.setTotalScore(Objects.requireNonNull(document.getLong("totalScore")).intValue());
        rankEntry.setAverageScore(Objects.requireNonNull(document.getDouble("averageScore")));

        ContentValues values = new ContentValues();
        values.put(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_USER_ID, rankEntry.getUserId());
        values.put(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_USERNAME, rankEntry.getUsername());
        values.put(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_TOTAL_SCORE, rankEntry.getTotalScore());
        values.put(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_AVERAGE_SCORE, rankEntry.getAverageScore());
        db.insertGeneralStatsLocal(QuestionContract.com.example.model.domain.RankEntry.TABLE_NAME, null, values);
    }
    public List<com.example.model.domain.RankEntry> getRankEntriesList() {
        if (rankEntriesList == null) {
            rankEntriesList = new ArrayList<>();
            rankEntriesList = (ArrayList<com.example.model.domain.RankEntry>) getRankEntries();
        }
        return rankEntriesList;
    }
    // Fetch the top 30 ranked users from SQLite
    public List<com.example.model.domain.RankEntry> getRankEntries() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                QuestionContract.com.example.model.domain.RankEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_TOTAL_SCORE + " DESC"
        );

        rankEntriesList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int userIdIndex = cursor.getColumnIndex(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_USER_ID);
            int usernameIndex = cursor.getColumnIndex(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_USERNAME);
            int totalScoreIndex = cursor.getColumnIndex(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_TOTAL_SCORE);
            int averageScoreIndex = cursor.getColumnIndex(QuestionContract.com.example.model.domain.RankEntry.COLUMN_NAME_AVERAGE_SCORE);

            if (userIdIndex != -1 && usernameIndex != -1 && totalScoreIndex != -1 && averageScoreIndex != -1) {
                String rankUserId = cursor.getString(userIdIndex);
                String rankUsername = cursor.getString(usernameIndex);
                int rankTotalScore = cursor.getInt(totalScoreIndex);
                double rankAverageScore = cursor.getDouble(averageScoreIndex);

                rankEntriesList.add(new com.example.model.domain.RankEntry(rankUserId, rankUsername, rankTotalScore, rankAverageScore));
            }
        }
        cursor.close();

        return rankEntriesList;
    }

    // --------------------------------------------------------------
    // WEEKLY_RANKS
    // Fetch the top 10 ranked users from Firestore based on last 7 days scores and store them in SQLite
    public void getRankWeeklyStats() {
        firebaseRepository.getFirestore().collection("user_statistics")
                .orderBy("last7DaysScoresDto.totalLast7Days", Query.Direction.DESCENDING)
                .limit(10) // Limit the number of documents fetched to 10
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Pair<com.example.model.domain.RankEntry, Integer>> rankEntries = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            com.example.model.domain.RankEntry rankEntry = new com.example.model.domain.RankEntry();
                            rankEntry.setUserId(document.getId());
                            rankEntry.setUsername(document.getString("username"));
                            rankEntry.setProfileImage(document.getString("profileImage"));
                            Log.d(TAG, "user weekly: " + document.getString("username"));

                            Object objGeneralStats = document.getData().getOrDefault("generalStatsDto", null);
                            if (objGeneralStats instanceof Map) {
                                @SuppressWarnings("unchecked")
                                Map<String, Object> generalStatsDto = (Map<String, Object>) objGeneralStats;

                                int totalScore = DataUtils.getIntValue(generalStatsDto, "totalScore");
                                rankEntry.setTotalScore(totalScore);
                                double averageScore = DataUtils.getIntValue(generalStatsDto, "averageScore");
                                rankEntry.setAverageScore(averageScore);
                            } else {
                                Log.d(TAG, "objGeneralStats is not a map");
                            }
                            int totalScoreLast7Days = 0;

                            Object obj = document.getData().getOrDefault("last7DaysScoresDto", null);
                            if (obj instanceof Map) {
                                @SuppressWarnings("unchecked")
                                Map<String, Integer> last7DaysScoresDto = (Map<String, Integer>) obj;

                                totalScoreLast7Days = DataUtils.getIntValue(last7DaysScoresDto, "totalScore");
                                rankEntry.setWeeklyScore(totalScoreLast7Days);
                            } else {
                                Log.d(TAG, "objGeneralStats is not a map");
                            }
                            rankEntries.add(new Pair<>(rankEntry, totalScoreLast7Days));
                        }

                        rankEntries.sort((pair1, pair2) -> pair2.second - pair1.second);
                        rankEntries = rankEntries.subList(0, Math.min(10, rankEntries.size()));

                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        db.delete(QuestionContract.RankWeekEntry.TABLE_NAME, null, null);

                        for (Pair<com.example.model.domain.RankEntry, Integer> pair : rankEntries) {
                            ContentValues values = new ContentValues();
                            values.put(QuestionContract.RankWeekEntry.COLUMN_NAME_USER_ID, pair.first.getUserId());
                            values.put(QuestionContract.RankWeekEntry.COLUMN_NAME_USERNAME, pair.first.getUsername());
                            values.put(QuestionContract.RankWeekEntry.COLUMN_NAME_TOTAL_SCORE, pair.first.getTotalScore());
                            values.put(QuestionContract.RankWeekEntry.COLUMN_NAME_LAST_SEVEN_DAYS, pair.second);
                            db.insertGeneralStatsLocal(QuestionContract.RankWeekEntry.TABLE_NAME, null, values);

                        }
                        // Your existing code...
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                    loadRankWeeklyList();
                })
                .addOnFailureListener(e -> {
                    // This block will be executed if there is any error while executing the query
                    Log.e(TAG, "Error executing Firestore query", e);
                });
    }
    // Load the top 10 ranked users and store them in a list
    private void loadRankWeeklyList() {
        rankWeeklyEntriesList = (ArrayList<com.example.model.domain.RankEntry>) getRankWeeklyEntries();
    }
    // Fetch the top 10 ranked users based on last 7 days scores from SQLite
    public List<com.example.model.domain.RankEntry> getRankWeeklyEntries() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //   Log.d(TAG, "Querying table: " + QuestionContract.RankWeekEntry.TABLE_NAME); // Make sure this is the correct table name
        Cursor cursor = db.query(
                QuestionContract.RankWeekEntry.TABLE_NAME, // Make sure this is the correct table name
                null,
                null,
                null,
                null,
                null,
                QuestionContract.RankWeekEntry.COLUMN_NAME_LAST_SEVEN_DAYS + " DESC"
        );

        rankWeeklyEntriesList = new ArrayList<>();
        while (cursor.moveToNext()) {
            int userIdIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_USER_ID);
            int usernameIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_USERNAME);
            int totalScoreIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_TOTAL_SCORE);
            int lastSevenDaysIndex = cursor.getColumnIndex(QuestionContract.RankWeekEntry.COLUMN_NAME_LAST_SEVEN_DAYS);

            if (userIdIndex != -1 && usernameIndex != -1 && totalScoreIndex != -1 && lastSevenDaysIndex != -1) {
                String rankUserId = cursor.getString(userIdIndex);
                String rankUsername = cursor.getString(usernameIndex);
                int rankTotalScore = cursor.getInt(totalScoreIndex);
                int rankLastSevenDays = cursor.getInt(lastSevenDaysIndex);

                rankWeeklyEntriesList.add(new com.example.model.domain.RankEntry(rankUserId, rankUsername, rankTotalScore, rankLastSevenDays));
            }
        }
        cursor.close();

        return rankWeeklyEntriesList;
    }
    // Get the weekly rank list (called from ViewModel)
    public List<com.example.model.domain.RankEntry> getRankWeeklyList() {
        if (rankWeeklyEntriesList == null) {
            rankWeeklyEntriesList = new ArrayList<>();
            getRankWeeklyEntries();
        }
        return rankWeeklyEntriesList;
    }

}*/
