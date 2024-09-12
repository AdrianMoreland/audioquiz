package com.adrian.model.stats;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the scores of a user for the last 7 days.
 * [dailyScores] The scores of the user per day.
 * [totalLast7Days] The total score of the user for the last 7 days.
 */
public class Last7DaysScores {
    private String id;
    private Date lastUpdated;
    private Map<String, Integer> dailyScores;
    private int totalLast7Days;

    private Last7DaysScores(Builder builder) {
        this.id = builder.id;
        this.lastUpdated = builder.lastUpdated;
        this.dailyScores = builder.dailyScores;
        this.totalLast7Days = builder.totalLast7Days;
    }

    private Last7DaysScores() {
        this.lastUpdated = new Date();
        this.dailyScores = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            this.dailyScores.put("Day " + (i + 1), 0);
        }
        this.totalLast7Days = 0;
    }

    public static Last7DaysScores createDefault(String id) {
        Last7DaysScores last7DaysScores = new Last7DaysScores();
        last7DaysScores.setId(id);
        return new Last7DaysScores();
    }

    // GETTERS
    public String getId() {
        return id;
    }
    public Date getLastUpdated() {
        return lastUpdated;
    }
    public Map<String, Integer> getDailyScores() {
        return dailyScores;
    }
    public int getTotalLast7Days() {
        return totalLast7Days;
    }
    // SETTERS
    public void setId(String id) {
        this.id = id;
    }
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public void setDailyScores(Map<String, Integer> dailyScores) {
        this.dailyScores = dailyScores;
    }
    public void setTotalLast7Days(int totalLast7Days) {
        this.totalLast7Days = totalLast7Days;
    }

    // BUILDER
    public static class Builder {
        public String id;
        public Date lastUpdated;
        private Map<String, Integer> dailyScores = new HashMap<>();
        private int totalLast7Days;

        public Builder() {
             for (int i = 0; i < 7; i++) {
                this.dailyScores.put("Day " + (i + 1), 0);
            }
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setLastUpdated(Date lastUpdated) {
            this.lastUpdated = lastUpdated;
            return this;
        }

        public Builder setDailyScores(Map<String, Integer> dailyScores) {
            this.dailyScores = dailyScores;
            this.totalLast7Days = dailyScores.values().stream().mapToInt(Integer::intValue).sum();
            return this;
        }

        public Builder setTotalLast7Days(int totalLast7Days) {
            this.totalLast7Days = totalLast7Days;
            return this;
        }

        public Last7DaysScores build() {
            return new Last7DaysScores(this);
        }
    }

    // UTILS
    public static Map<String, Object> toMap (Last7DaysScores last7DaysScores) {
        Map<String, Integer> dailyScoresMap = new HashMap<>(last7DaysScores.getDailyScores());
        Map<String, Object> last7DaysScoresMap = new HashMap<>();
        last7DaysScoresMap.put("dailyScores", dailyScoresMap);
        last7DaysScoresMap.put("totalLast7Days", last7DaysScores.getTotalLast7Days());
        return last7DaysScoresMap;
    }

    public static Last7DaysScores convertLast7DaysScores(Object data) {
        Last7DaysScores last7DaysScores = createDefault("id");
        if (data instanceof Last7DaysScores) {
            last7DaysScores.setDailyScores(getDailyScoresMap((Last7DaysScores) data));
            last7DaysScores.setTotalLast7Days(((Last7DaysScores) data).getTotalLast7Days());
        }
        return last7DaysScores;
    }

    private static Map<String, Integer> getDailyScoresMap(Last7DaysScores data) {
        return new HashMap<>(data.getDailyScores());
    }

    @Override
    public String toString() {
        return "com.example.model.domain.Last7DaysScores{" +
                "dailyScores=" + dailyScores +
                ", totalScoreLast7Days=" + totalLast7Days +
                '}';
    }


   /*
      public com.example.model.domain.Last7DaysScores updateLast7DaysScores(int quizScore, Timestamp lastQuizDate) {
        Log.d(TAG, "_______________________________________________________________________________________________");
        Log.d(TAG, "Updating Scores for Last 7 days");
        Log.d(TAG, "BEFORE: " + dailyScores);
        String dateKey = DataUtils.getTimestampToString(lastQuizDate);
        Integer currentScore = this.dailyScores.get(dateKey);
        if (currentScore == null) {
            currentScore = 0;
        }
        currentScore += quizScore;
        this.dailyScores.put(dateKey, currentScore);
        Log.d(TAG, "AFTER: " + dailyScores);

        // Keep only the last 7 days
        if (this.dailyScores.size() > 7) {
            String oldestDate = Collections.min(this.dailyScores.keySet());
            this.dailyScores.remove(oldestDate);
        }
        return this;
    }

  public static com.example.model.domain.Last7DaysScores fromMap(Map<String, Object> map) {
        Builder builder = new Builder();

        // Extract and set dailyScores
        Object dailyScoresObject = map.get("dailyScores");
        if (dailyScoresObject instanceof Map) {
            Map<String, Integer> dailyScores = new HashMap<>((Map<String, Integer>) dailyScoresObject);
            builder.setDailyScores(dailyScores);
        }

        // Extract and set totalScoreLast7Days
        Object totalScoreLast7DaysObject = map.get("totalLast7Days");
        if (totalScoreLast7DaysObject instanceof Integer) {
            builder.setTotalLast7Days((Integer) totalScoreLast7DaysObject);
        }

        return builder.build();
    }

    // Update the last 7 days scores based on the provided score and last quiz date
    private com.example.model.domain.Last7DaysScores updateLast7DaysScores(int newScore, Timestamp lastQuizDate, com.example.model.domain.Last7DaysScores last7DaysScores) {
        Log.d(TAG, "_______________________________________________________________________________________________");
        Log.d(TAG, "Updating Scores for Last 7 days");
        Map<String, Integer> last7DaysScoresMap = last7DaysScores.getDailyScores();
        Log.d(TAG, "BEFORE: " + last7DaysScoresMap);

        // Convert the Timestamp to a formatted date string
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String lastQuizDateString = sdf.format(lastQuizDate.toDate());
        // Fetch the current date score value from the map
        int currentScore = dailyScores.get(lastQuizDateString);
        Log.d(TAG, "               Current day: " + lastQuizDateString + ", Current score: " + currentScore);

        // Add the new score to the current score
        int updatedScore = currentScore + newScore;
        Log.d(TAG, "               Updated score: " + updatedScore + " for today's date: " + lastQuizDateString);

        Map<String, Integer>currentDayScore = new HashMap<>();
        currentDayScore.put(lastQuizDateString, updatedScore);
        // Put the updated score back into the map
        last7DaysScores.setDailyScores(currentDayScore);

        // Update totalLast7Days field
        //   int totalLast7Days = last7DaysScores.getTotalScoreLast7Days();
        //  totalLast7Days += newScore;
        //  com.example.model.domain.Last7DaysScores.Builder("totalLast7Days", totalLast7Days);

        // Ensure the map only contains the last 7 days
        if (last7DaysScoresMap.size() > 7) {
            String oldestDate = Collections.min(last7DaysScores.getDailyScores().keySet());
            last7DaysScoresMap.remove(oldestDate);
        }

        Log.d(TAG, "AFTER: " + last7DaysScores);
        Log.d(TAG, "_______________________________________________________________________________________________");

        return last7DaysScores;
    }
*/

}
