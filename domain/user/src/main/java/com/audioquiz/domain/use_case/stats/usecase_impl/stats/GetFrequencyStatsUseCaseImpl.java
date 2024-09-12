package com.adrian.usecase.stats.usecase_impl.stats;


 import com.adrian.domain.user.FrequencyStatsRepository;
 import com.adrian.model.stats.FrequencyStats;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

 import io.reactivex.rxjava3.core.Single;


public class GetFrequencyStatsUseCaseImpl {
    private static final String TAG = "GetFrequencyStatsUseCase";
    private final FrequencyStatsRepository repository;;
    Map<String, Map<String, Object>> transformedData = new HashMap<>();
    FrequencyStats frequencyStatsLiveData;

    @Inject
    public GetFrequencyStatsUseCaseImpl(FrequencyStatsRepository repository) {
        this.repository = repository;
    }

    public Single<FrequencyStats> execute() {
        return repository.getFrequencyStats();
    }


}
