package com.audioquiz.feature.stats.presentation.fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
 import com.audioquiz.designsystem.model.AchievementUi;
 import com.audioquiz.designsystem.adapters.BindingImageFromUrl;
import com.audioquiz.designsystem.extensions.FragmentExtension;
import com.audioquiz.feature.stats.databinding.FragmentStatsBinding;
import com.audioquiz.feature.stats.presentation.viewmodels.UserStatisticsViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserStatisticsFragment extends Fragment {
    private static final String TAG = "UserStatisticsFragment";
    private UserStatisticsViewModel userStatisticsViewModel;
    private ActivityResultLauncher<Intent> uCropActivityResultLauncher;
    private ActivityResultLauncher<Intent> requestPermissionLauncher;

    private Drawable iconUser;
    private Drawable graphBackground;
    private Color iconIncorrect;
    private int selectedTabColor;
    private int unselectedTabColor;
    private LineData lineData; // Add this line

    private FragmentStatsBinding binding; // View binding instance
    @Inject
    BindingImageFromUrl imageLoader;

    @Inject
    public UserStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the ViewModel
        userStatisticsViewModel = new ViewModelProvider(this).get(UserStatisticsViewModel.class);

/*        // Initialize the ActivityResultLaunchers
        uCropActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> userStatisticsViewModel.handleCropResult(result)
        );

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> userStatisticsViewModel.handleImageSelectionResult(result, uCropActivityResultLauncher)
        );*/
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        FragmentExtension fragmentExtension = new FragmentExtension();
        selectedTabColor = FragmentExtension.getColor(this, com.audioquiz.designsystem.R.color.md_theme_primary);
        unselectedTabColor = FragmentExtension.getColor(this, com.audioquiz.designsystem.R.color.md_theme_onBackground);
        iconUser = fragmentExtension.getDrawable(this, com.audioquiz.designsystem.R.drawable.ic_user);
        graphBackground = fragmentExtension.getDrawable(this, com.audioquiz.designsystem.R.drawable.bg_gradient_01);

        setupViews(); // Setup all views and interactions
        return view;
    }



    private void setupViews() {
        String[] achievementNames = getResources().getStringArray(com.audioquiz.designsystem.R.array.achievement_names);
        String[] achievementDescriptions = getResources().getStringArray(com.audioquiz.designsystem.R.array.achievement_descriptions);
        TypedArray achievementIcons = getResources().obtainTypedArray(com.audioquiz.designsystem.R.array.badgeResources);

        List<AchievementUi> achievements = new ArrayList<>();
        for (int i = 0; i < achievementNames.length; i++) {
            int icon = achievementIcons.getResourceId(i, -1);
            String name = achievementNames[i];
            String description = achievementDescriptions[i];
            achievements.add(new AchievementUi(icon, name, description));
        }

        achievementIcons.recycle(); // Don't forget to recycle the TypedArray

        RecyclerView recyclerView = binding.recyclerviewAchievements;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new AchievementAdapter(achievements));

        binding.tvUserInitial.setVisibility(View.VISIBLE);

        setupUserImage();
        setupButtons();
        observeUserData();
       // setupLineChart();
      //  setupBarChart();
    }

    private void setupButtons() {
        binding.btnStats.setOnClickListener(v -> showStats());
        binding.buttonAchievements.setOnClickListener(v -> showAchievements());
    }


    private void observeUserData() {
        userStatisticsViewModel.getTotalScoreLiveData().observe(getViewLifecycleOwner(), totalXp -> binding.textTotalXp.setText(totalXp));
        userStatisticsViewModel.getAverageScoreLiveData().observe(getViewLifecycleOwner(), averageScore -> binding.textAverageScore.setText(String.valueOf(averageScore)));
        userStatisticsViewModel.getNumberOfQuizzesLiveData().observe(getViewLifecycleOwner(), numberOfQuizzes -> binding.textQuizzesAmount.setText(numberOfQuizzes));
        userStatisticsViewModel.getLongestStreakLiveData().observe(getViewLifecycleOwner(), longestStreak -> binding.textLongestStreak.setText(longestStreak));
        userStatisticsViewModel.getUsernameLiveData().observe(getViewLifecycleOwner(), username -> binding.textUsername.setText(username));
        userStatisticsViewModel.getUsernameLetterLiveData().observe(getViewLifecycleOwner(), userLetter -> {
            if (userLetter != null) {
                binding.tvUserInitial.setText(userLetter);
            }
        });
        userStatisticsViewModel.getUserLevelLiveData().observe(getViewLifecycleOwner(), userLevel -> binding.textLevel.setText(userLevel));
        userStatisticsViewModel.getProfileImageUrlLiveData().observe(getViewLifecycleOwner(), imageUrl -> {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                imageLoader.loadImage(imageUrl, binding.ivProfileImage); // Call loadImage
                binding.tvUserInitial.setVisibility(View.GONE);
            } else {
                binding.tvUserInitial.setVisibility(View.VISIBLE);
            }
        });

        /*userStatisticsViewModel.getProfileImageUrlLiveData().observe(getViewLifecycleOwner(), newImageUrl -> {
            if (newImageUrl != null && !newImageUrl.isEmpty()) {
                Glide.with(requireContext())
                        .load(newImageUrl)
                        .placeholder(iconUser)
                        .error(iconIncorrect)
                        .centerCrop()
                        .into(binding.ivProfileImage);
                Log.d(TAG, "Glide load initiated with URL: " + newImageUrl);
                binding.tvUserInitial.setVisibility(View.GONE);
            } else {
                binding.tvUserInitial.setVisibility(View.VISIBLE);
                Log.e(TAG, "Received empty or null image URL");
            }
        });*/
        userStatisticsViewModel.getFrequencyStats().observe(getViewLifecycleOwner(), frequencyStatsList -> {
            if (frequencyStatsList != null) {
                renderData(frequencyStatsList);
            } else {
                // Handle null case
                Log.e(TAG, "frequencyStatsMap is null. Cannot load chart data.");
            }
        });
    }
    ActivityResultLauncher<String> getContentLauncher; // Declare
    private ActivityResultLauncher<Intent> cropLauncher;
    private void setupUserImage() {
        cropLauncher = registerForActivityResult( // Initialize cropLauncher first
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // You don't need to handle the result here, as it's handled in ImageSelectionUtil
                }
        );
        getContentLauncher = registerForActivityResult( // Initialize getContentLauncher
                new ActivityResultContracts.GetContent(),
                uri -> {
                    userStatisticsViewModel.selectImage(this, uri, cropLauncher);}
        );

        binding.ivProfileImage.setOnClickListener(v -> getContentLauncher.launch("image/*"));
    }
    private void showAchievements() {
        // Change button styles
        binding.buttonAchievements.setTextColor(selectedTabColor);
        binding.btnStats.setTextColor(unselectedTabColor);

        // Show the all-time rank and hide the weekly rank
        binding.containerAchievements.setVisibility(View.VISIBLE);
        binding.containerStats.setVisibility(View.GONE);
    }
    private void showStats() {
        // Change button styles
        binding.buttonAchievements.setTextColor(unselectedTabColor);
        binding.btnStats.setTextColor(selectedTabColor);

        // Show the all-time rank and hide the weekly rank
        binding.containerAchievements.setVisibility(View.GONE);
        binding.containerStats.setVisibility(View.VISIBLE);
    }

    // Method to load graph frequencies from resources
    private int[] loadGraphFrequencies() {
        Resources res = getResources();
        return res.getIntArray(com.audioquiz.designsystem.R.array.graph_frequencies);
    }
    // Your existing renderData and setData methods
    public void renderData(List<Entry> frequencyEntryList) {
        // Load the graph frequencies
        int[] graphFrequencies = loadGraphFrequencies();

        // Setup for X-axis with custom labels
        XAxis xAxis = binding.lineChartFrequencies.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int index = (int) value;
                if (index >= 0 && index < graphFrequencies.length) {
                    return String.valueOf(graphFrequencies[index]);
                } else {
                    return "";
                }
            }
        });
        xAxis.setLabelCount(graphFrequencies.length, true);
        xAxis.setGranularity(1f); // restricts the interval to 1 (one frequency point per step)
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(graphFrequencies.length - 1);

        // Y-axis setup remains the same
        YAxis leftAxis = binding.lineChartFrequencies.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(new LimitLine(2.1f, "Maximum Limit"));
        leftAxis.addLimitLine(new LimitLine(-1.4f, "Minimum Limit"));

        // Initialize maxY and minY with default values
        float maxY = Float.NEGATIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;

        // Calculate maxY and minY based on entries
        for (Entry entry : frequencyEntryList) {
            if (entry.getY() > maxY) {
                maxY = entry.getY();
            }
            if (entry.getY() < minY) {
                minY = entry.getY();
            }
        }

        // Log values for debugging
        Log.d(TAG, "Calculated maxY: " + maxY);
        Log.d(TAG, "Calculated minY: " + minY);

        // Add some padding to maxY and minY to ensure visibility
        leftAxis.setAxisMaximum(maxY + 1);
        leftAxis.setAxisMinimum(minY - 1);

        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(true);  // Draw the zero line for better visibility
        leftAxis.setDrawLimitLinesBehindData(false);

        binding.lineChartFrequencies.getAxisRight().setEnabled(false);
        setData(binding.lineChartFrequencies, frequencyEntryList, graphFrequencies);
    }
    private void setData(LineChart lineChartFrequency, List<Entry> frequencyEntryList, int[] graphFrequencies) {
        if (frequencyEntryList != null && !frequencyEntryList.isEmpty()) {
            Log.d(TAG, "Frequency stats entry list: " + frequencyEntryList);

            ArrayList<Entry> values = new ArrayList<>(frequencyEntryList);

            LineDataSet dataSet;

            if (lineChartFrequency.getData() != null &&
                    lineChartFrequency.getData().getDataSetCount() > 0) {
                dataSet = (LineDataSet) lineChartFrequency.getData().getDataSetByIndex(0);
                dataSet.setValues(values);
                lineChartFrequency.getData().notifyDataChanged();
                lineChartFrequency.notifyDataSetChanged();
            } else {
                dataSet = new LineDataSet(values, "Sample Data");
                dataSet.setDrawIcons(false);
                dataSet.enableDashedLine(10f, 5f, 0f);
                dataSet.enableDashedHighlightLine(10f, 5f, 0f);
                dataSet.setColor(Color.DKGRAY);
                dataSet.setCircleColor(Color.DKGRAY);
                dataSet.setValueTextColor(Color.WHITE);
                dataSet.setLineWidth(1f);
                dataSet.setCircleRadius(3f);
                dataSet.setDrawCircleHole(false);
                dataSet.setValueTextSize(9f);
                dataSet.setDrawFilled(true);
                dataSet.setFormLineWidth(1f);
                dataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
                dataSet.setFormSize(15.f);

                if (Utils.getSDKInt() >= 18) {
                    Drawable drawable = graphBackground ;
                    dataSet.setFillDrawable(drawable);
                } else {
                    dataSet.setFillColor(Color.DKGRAY);
                }

                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(dataSet);
                LineData data = new LineData(dataSets);
                lineChartFrequency.setData(data);
            }
            lineChartFrequency.invalidate(); // Refresh the chart
        } else {
            Log.e(TAG, "frequencyStatsMap is null. Cannot load chart data.");
        }
    }
    //SETUP_CHARTS
    private Map<String, Integer> initializeLast7DaysScores() {
        Map<String, Integer> last7DaysScores = new LinkedHashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        // Start from 6 days ago
        calendar.add(Calendar.DAY_OF_MONTH, -6);
        for (int i = 0; i < 7; i++) {
            String date = dateFormat.format(calendar.getTime());
            last7DaysScores.put(date, 0);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return last7DaysScores;
    }
    private List<String> getDateLabels() {
        List<String> dateLabels = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Calendar todayCalendar = Calendar.getInstance();

        // Start from today and go back 6 days
        for (int i = 0; i < 7; i++) {
            Date currentDate = calendar.getTime();
            String formattedDate = dateFormat.format(currentDate);

            // Check if it's today's date
            if (calendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
                    && calendar.get(Calendar.DAY_OF_YEAR) == todayCalendar.get(Calendar.DAY_OF_YEAR)) {
                formattedDate = "T";
            } else {
                // Get the initial of the day of the week
                formattedDate = formattedDate.substring(0, 1);
            }

            dateLabels.add(formattedDate);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        // Reverse the list to have the dates in the correct order
        Collections.reverse(dateLabels);
        return dateLabels;
    }

   /* //LINE_CHART
    private void setupLineChart() {
        // Initialize line chart and set up configurations
        LineChart lineChart = binding.chart2;
        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.md_theme_primary);
        // Disable dragging and scaling
        lineChart.getDescription().setEnabled(false); // Disable description
        lineChart.getLegend().setEnabled(false); // Disable legend
        lineChart.setDragEnabled(false); // Disable dragging
        lineChart.setScaleEnabled(false); // Disable scaling
        lineChart.getAxisLeft().setDrawGridLines(true); // Enable horizontal grid lines
        lineChart.getXAxis().setDrawGridLines(false); // Disable vertical grid lines

        // Customize Y-axis legend
        YAxis yAxis = lineChart.getAxisLeft(); // Get the left Y-axis
        yAxis.setDrawLabels(true); // Enable drawing labels
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f); // Set label text size
        yAxis.setTextColor(colorPrimary); // Set label text color
        lineChart.getAxisRight().setDrawLabels(false); // Disable drawing labels on the right Y-axis
        lineChart.getAxisRight().setAxisMinimum(0f); // Set minimum value for Right Y-axis
        lineChart.getAxisLeft().setAxisMinimum(0f); // Set minimum value for left Y-axis
        yAxis.setAxisMinimum(0f); // Set minimum value for Y-axis
        yAxis.setAxisMaximum(100f); // Set maximum value for Y-axis

        // Set up X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Set interval to 1 (one label per data point)
        xAxis.setLabelCount(7); // Set the number of labels to be displayed
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getDateLabels())); // Set custom value formatter
        xAxis.setTextSize(12f); // Set label text size
        xAxis.setTextColor(colorPrimary); // Set label text color
        xAxis.setYOffset(10f); // Set offset from the X-axis
        lineChart.setExtraOffsets(0f, 0f, 0f, 10f); // Add extra padding to the bottom

        lineChart.post(() -> {
            if (lineData != null) {
                lineChart.setData(lineData);
                lineChart.invalidate();
            }
        });
        lineChart.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (lineChart.getWidth() > 0 && lineChart.getHeight() > 0) {
                    lineChart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (lineData != null) { // Add this line
                        lineChart.setData(lineData); // Add this line
                        lineChart.invalidate(); // Add this line
                    }
                }
            }
        });
    }
    private void observeLast7DaysScoresLine() {
        userStatisticsViewModel.getWeeklyScores().observe(getViewLifecycleOwner(), map -> {
            if (map != null) {
                List<Entry> entriesLine = new ArrayList<>();
                int index = 0;
                float maxValue = Float.MIN_VALUE;
                Map<String, Integer> last7DaysScoresMap = initializeLast7DaysScores();
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    maxValue = processEntry(entry, last7DaysScoresMap, entriesLine, index++, maxValue);
                }

                LineChart lineChart = binding.chart2;

                float finalMaxValue = maxValue;
                lineChart.post(() -> {
                    LineDataSet dataSet = new LineDataSet(entriesLine, "Points Earned");
                    int colorPrimary = ContextCompat.getColor(requireContext(), R.color.md_theme_primary);
                    dataSet.setColor(colorPrimary);
                    lineData = new LineData(dataSet);
                    float roundedMaxValue = (float) (Math.ceil((finalMaxValue + 10) / 10) * 10);
                    lineChart.getAxisLeft().setAxisMaximum(roundedMaxValue);
                    lineChart.getAxisRight().setAxisMaximum(roundedMaxValue);
                    lineChart.setData(lineData);
                    lineChart.invalidate();
                });
            }
        });
    }
    private float processEntry(Map.Entry<String, Integer> entry, Map<String, Integer> last7DaysScoresDto,
                               List<Entry> entriesLine, int index, float maxValue) {
        if (!entry.getKey().equals("totalLast7Days") && last7DaysScoresDto.containsKey(entry.getKey())) {
            Integer value = entry.getValue();
            if (value != null && value >= 0) {
                entriesLine.add(new Entry(index, value));
                maxValue = Math.max(maxValue, value);
            }
        }
        return maxValue;
    }

    // BAR_CHART
    private void setupBarChart() {
        BarChart barChart = binding.chart1;
        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.md_theme_primary);
        // Disable dragging and scaling
        barChart.getDescription().setEnabled(false); // Disable description
        barChart.getLegend().setEnabled(false); // Disable legend
        barChart.setDragEnabled(false); // Disable dragging
        barChart.setScaleEnabled(false); // Disable scaling
        barChart.getAxisLeft().setDrawGridLines(true); // Enable horizontal grid lines
        barChart.getXAxis().setDrawGridLines(false); // Disable vertical grid lines

        // Customize Y-axis legend
        YAxis yAxis = barChart.getAxisLeft(); // Get the left Y-axis
        yAxis.setDrawLabels(true); // Enable drawing labels
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setTextSize(12f); // Set label text size
        yAxis.setTextColor(colorPrimary); // Set label text color
        barChart.getAxisRight().setDrawLabels(false); // Disable drawing labels on the right Y-axis
        barChart.getAxisRight().setAxisMinimum(0f); // Set minimum value for Right Y-axis
        barChart.getAxisLeft().setAxisMinimum(0f); // Set minimum value for left Y-axis

        // Set up X-axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f); // Set interval to 1 (one label per data point)
        xAxis.setLabelCount(7); // Set the number of labels to be displayed
        xAxis.setValueFormatter(new IndexAxisValueFormatter(getDateLabels())); // Set custom value formatter
        xAxis.setTextSize(12f); // Set label text size
        xAxis.setTextColor(colorPrimary); // Set label text color
        xAxis.setYOffset(10f); // Set offset from the X-axis
        barChart.setExtraOffsets(0f, 0f, 0f, 10f); // Add extra padding to the bottom
    }
    private void observeLast7DaysScoresBar() {
        userStatisticsViewModel.getWeeklyScores().observe(getViewLifecycleOwner(), map -> {
            if (map != null) {
                Map<String, Integer> last7DaysScoresDto = initializeLast7DaysScores();
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    if (!entry.getKey().equals("totalLast7Days") && last7DaysScoresDto.containsKey(entry.getKey())) {
                        last7DaysScoresDto.put(entry.getKey(), entry.getValue());
                    }
                }
                List<BarEntry> entries = new ArrayList<>();
                int index = 0;
                for (Integer value : last7DaysScoresDto.values()) {
                    entries.add(new BarEntry(index++, value));
                }
                Collections.reverse(entries);
                BarDataSet dataSet = new BarDataSet(entries, "Points Earned");
                int colorPrimary = ContextCompat.getColor(requireContext(), R.color.md_theme_primary);
                dataSet.setColor(colorPrimary);
                BarData barData = new BarData(dataSet);
                BarChart barChart = binding.chart1;
                barChart.setData(barData);
                barChart.invalidate();
                float maxValue = calculateMaxValue(entries);
                float roundedMaxValue = (float) (Math.ceil((maxValue + 10) / 10) * 10);
                barChart.getAxisLeft().setAxisMaximum(roundedMaxValue);
                barChart.getAxisRight().setAxisMaximum(roundedMaxValue);
            }
        });
    }
    private float calculateMaxValue(List<BarEntry> entries) {
        float maxValue = Float.MIN_VALUE;
        for (BarEntry entry : entries) {
            if (entry.getY() > maxValue) {
                maxValue = entry.getY();
            }
        }
        return maxValue;
    }


    private BarChart barChart;
    private LineChart lineChart;
    private LineChart lineChartFrequency;

  ConstraintLayout containerAchievements;
    LinearLayout containerStats;
    private MaterialCardView container_profile_image;
    private MaterialButton buttonStats;
    private MaterialButton buttonAchievements;
    private ImageView userImage;

    private TextView tvUserInitial;
    private TextView totalXpTextView;
    private TextView textUsername;
    private TextView averageScoreTextView;
    private TextView numberOfQuizzesTextView;
    private TextView longestStreakTextView;
    private TextView currentStreakTextView;
    private TextView levelTextView;


    public void renderData(View view) {
        lineChart = view.findViewById(R.id.lineChartFrequencies);
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setAxisMaximum(10f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawLimitLinesBehindData(true);

        LimitLine ll1 = new LimitLine(215f, "Maximum Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(70f, "Minimum Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaximum(350f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setDrawLimitLinesBehindData(false);

        lineChart.getAxisRight().setEnabled(false);
        setData(lineChart);
    }

    private void setData(LineChart lineChart) {
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));
        values.add(new Entry(3, 80));
        values.add(new Entry(4, 120));
        values.add(new Entry(5, 110));
        values.add(new Entry(7, 150));
        values.add(new Entry(8, 250));
        values.add(new Entry(9, 190));

        LineDataSet set1;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Sample Data");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.bg_gradient_01);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            lineChart.setData(data);
        }
    }

    //LINE_CHART_FREQUENCY
    private void loadChartData(Map<String, Map<String,Object>> frequencyStatsMap) {
        List<Entry> totalQuestionsEntries = new ArrayList<>();
        List<Entry> correctAnswersEntries = new ArrayList<>();

        // Convert map entries to a list and sort it
        List<Map.Entry<String, Map<String, Object>>> sortedEntries = new ArrayList<>(frequencyStatsMap.entrySet());
        sortedEntries.sort((e1, e2) -> Float.compare(Float.parseFloat(e1.getKey()), Float.parseFloat(e2.getKey())));

        for (Map.Entry<String, Map<String, Object>> entry : sortedEntries) {
            Log.d(TAG, "loadChartData: " + entry);
            String frequency = entry.getKey();
            Map<String, Object> data = entry.getValue();

            float xValue = Float.parseFloat(frequency);

            totalQuestionsEntries.add(new Entry(xValue, DataUtils.getIntValue(data, "totalQuestions")));
            correctAnswersEntries.add(new Entry(xValue, DataUtils.getIntValue(data, "correctAnswers")));
        }

        LineDataSet totalQuestionsDataSet = new LineDataSet(totalQuestionsEntries, "Total Questions");
        totalQuestionsDataSet.setColor(Color.BLUE);
        totalQuestionsDataSet.setValueTextColor(Color.WHITE);

        LineDataSet correctAnswersDataSet = new LineDataSet(correctAnswersEntries, "Correct Answers");
        correctAnswersDataSet.setColor(Color.GREEN);
        correctAnswersDataSet.setValueTextColor(Color.WHITE);

        LineData lineData = new LineData();
        lineData.addDataSet(totalQuestionsDataSet);
        lineData.addDataSet(correctAnswersDataSet);

        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }

    private void setupFrequenciesLineChart(View view) {
        lineChart = view.findViewById(R.id.lineChartFrequencies);
        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.md_theme_primary);
        // Disable dragging and scaling
        lineChart.getDescription().setEnabled(false); // Disable description
        lineChart.getLegend().setEnabled(false); // Disable legend
        lineChart.setDragEnabled(false); // Disable dragging
        lineChart.setScaleEnabled(false); // Disable scaling
        lineChart.getAxisLeft().setDrawGridLines(true); // Enable horizontal grid lines
        lineChart.getXAxis().setDrawGridLines(false); // Disable vertical grid lines

        lineChart.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (lineChart.getWidth() > 0 && lineChart.getHeight() > 0) {
                    lineChart.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (lineData != null) { // Add this line
                        lineChart.setData(lineData); // Add this line
                        lineChart.invalidate(); // Add this line
                    }
                }
            }
        });
    }


       setupProgressBars(view);
       updateProgressBars();

    private void setupUserImage(View view) {
        userImage = view.findViewById(R.id.iv_profile_image);
        userImage.setOnClickListener(v -> ImageSelectionUtil.selectImage(this, requestPermissionLauncher));
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        requestPermissionLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> uCropActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Log.d(TAG, "uCropActivityResultLauncher triggered with resultCode: " + result.getResultCode());
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri croppedImageUri = UCrop.getOutput(data);
                        if (croppedImageUri != null) {
                            Log.d(TAG, "Cropped image URI: " + croppedImageUri);
                            // Upload the cropped image
                            userStatisticsViewModel.uploadProfileImage(croppedImageUri, new UserStatisticsViewModel.OnImageUploadListener() {
                                @Override
                                public void onImageUploadSuccess(String downloadUrl) {
                                    Log.d(TAG, "Image uploaded successfully: " + downloadUrl);
                                    userStatisticsViewModel.setProfileImageUrlLiveData(downloadUrl);
                                }

                                @Override
                                public void onImageUploadFailure(Exception e) {
                                    Log.e(TAG, "Image upload failed: " + e.getMessage());
                                }
                            });
                        }
                    }
                } else {
                    Log.e(TAG, "uCropActivityResultLauncher triggered with unexpected resultCode: " + result.getResultCode());
                }
            }
    );
    private final ActivityResultLauncher<Intent> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            // Start uCrop activity
                            ImageSelectionUtil.startUCrop(this, selectedImageUri, uCropActivityResultLauncher);
                        }
                    }
                }
            }
    );


    private void observeUserDataa() {
        userStatisticsViewModel.getGeneralStatsLiveData().observe(getViewLifecycleOwner(), generalStatsDto -> {
                totalXpTextView.setText(String.valueOf(generalStatsDto.getTotalScore()));
                averageScoreTextView.setText(String.valueOf(generalStatsDto.getAverageScore()));
                Optional.of(generalStatsDto.getAverageScore())
                        .ifPresent(score -> averageScoreTextView.setText(getString(R.string.average_score_format, score)));
                longestStreakTextView.setText(String.valueOf(generalStatsDto.getLongestStreak()));
                currentStreakTextView.setText(String.valueOf(generalStatsDto.getCurrentStreak()));
        });
        userStatisticsViewModel.getUserProfileLiveData().observe(getViewLifecycleOwner(), userProfile -> {
            textUsername.setText(userProfile.getUsername());
            String newImageUrl = userProfile.getProfileImage();
            if (newImageUrl != null && !newImageUrl.isEmpty()) {
                Glide.with(requireContext())
                        .load(newImageUrl)
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_incorrect)
                        .centerCrop()
                        .into(userImage);
                Log.d(TAG, "Glide load initiated with URL: " + newImageUrl);
            } else {
                Log.e(TAG, "Received empty or null image URL");
            }
        });
        userStatisticsViewModel.getUserLevelLiveData().observe(getViewLifecycleOwner(), userLevel -> {
            if (userLevel != null) {
                levelTextView.setText(userLevel);
            }
        });
        userStatisticsViewModel.getProfileImageUrlLiveData().observe(getViewLifecycleOwner(), newImageUrl -> {
            if (newImageUrl != null && !newImageUrl.isEmpty()) {
                Glide.with(requireContext())
                        .load(newImageUrl)
                        .placeholder(R.drawable.ic_user)
                        .error(R.drawable.ic_incorrect)
                        .centerCrop()
                        .into(userImage);
                Log.d(TAG, "Glide load initiated with URL: " + newImageUrl);
            } else {
                Log.e(TAG, "Received empty or null image URL");
            }
        });
    }

    private ProgressBar progressBarSoundwavesStats;
    private ProgressBar progressBarSynthesisStats;
    private ProgressBar progressBarMixingStats;
    private ProgressBar progressBarProcessingStats;
    private ProgressBar progressBarProductionStats;
    private ProgressBar progressBarMusictheoryStats;

    private TextView textSoundwavesStats;
    private TextView textSynthesisStats;
    private TextView textMixingStats;
    private TextView textProductionStats;
    private TextView textProcessingStats;
    private TextView textMusictheoryStats;
    private void setupProgressBars(View view) {
        progressBarSoundwavesStats = view.findViewById(R.id.progressBar_soundwaves_stats);
        progressBarSynthesisStats = view.findViewById(R.id.progressBar_synthesis_stats);
        progressBarMixingStats = view.findViewById(R.id.progressBar_mixing_stats);
        progressBarProductionStats = view.findViewById(R.id.progressBar_production_stats);
        progressBarProcessingStats = view.findViewById(R.id.progressBar_processing_stats);
        progressBarMusictheoryStats = view.findViewById(R.id.progressBar_musictheory_stats);

        textSoundwavesStats = view.findViewById(R.id.text_soundwaves_stats);
        textSynthesisStats = view.findViewById(R.id.text_synthesis_stats);
        textMixingStats = view.findViewById(R.id.text_mixing_stats);
        textProductionStats = view.findViewById(R.id.text_production_stats);
        textProcessingStats = view.findViewById(R.id.text_processing_stats);
        textMusictheoryStats = view.findViewById(R.id.text_musictheory_stats);
    }
    private void updateProgressBar(String category, ProgressBar progressBar, TextView textView) {
        LiveData<Integer> currentChapter = userStatisticsViewModel.getCategoryChapter(category);
            final float progress = ((float) (currentChapter.getValue() - 1) / 3) * 100;
            progressBar.setProgress((int) progress);
            textView.setText(String.format(Locale.getDefault(), "%.0f%%", progress));
    }
    public void updateProgressBars() {
        updateProgressBar("Soundwaves", progressBarSoundwavesStats, textSoundwavesStats);
        updateProgressBar("Synthesis", progressBarSynthesisStats, textSynthesisStats);
        updateProgressBar("Mixing", progressBarMixingStats, textMixingStats);
        updateProgressBar("Production", progressBarProductionStats, textProductionStats);
        updateProgressBar("Processing", progressBarProcessingStats, textProcessingStats);
        updateProgressBar("MusicTheory", progressBarMusictheoryStats, textMusictheoryStats);
    }
*/

}