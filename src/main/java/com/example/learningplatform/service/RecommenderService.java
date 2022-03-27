package com.example.learningplatform.service;

import com.example.learningplatform.model.Score;
import com.example.learningplatform.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommenderService {

    private DataModel model;
    private final ScoreRepository repository;
    int authorCounter = 0;
    int prefCounter = 0;
    Map<UUID, Integer> uuidToIntId = new HashMap<>();

    public void getPreferencesNew() throws TasteException {
        Map<UUID, List<Score>> groupedScores = new HashMap<>();
        FastByIDMap<PreferenceArray> preferences = new FastByIDMap<>();

        List<Score> allScores = repository.findAll();
        allScores.forEach( score -> {
            groupedScores.putIfAbsent(score.getAuthorId(), new LinkedList<>());
            groupedScores.get(score.getAuthorId()).add(score);
        });

        groupedScores.forEach((author, scores) ->{
            PreferenceArray array = new GenericUserPreferenceArray(scores.size());
            scores.forEach(score -> {
                Preference pref = new GenericPreference( authorCounter, score.getCourseId(), score.getScore());
                array.set(prefCounter, pref);
                prefCounter++;
            });
            uuidToIntId.put(author, authorCounter);
            preferences.put(authorCounter, array);
            ++authorCounter;
            prefCounter = 0;
        });
        authorCounter = 0;
        model = new GenericDataModel(preferences);
        System.out.println("Maximum preference : " + model.getMaxPreference());
        System.out.println("Minimum preference : " + model.getMinPreference());
        System.out.println("Number of items : " + model.getNumItems());
        System.out.println("Number Of Users : " + model.getNumUsers());

        System.out.println("Item ids for user 1");
        FastIDSet set = model.getItemIDsFromUser(uuidToIntId.get(UUID.fromString("f69d6a1e-c4ff-405a-b873-30693933b86a")));
        LongPrimitiveIterator iter = set.iterator();
        while (iter.hasNext()) {
            System.out.print(iter.nextLong() + " ");
        }
    }


    public List<RecommendedItem> recommendNew(UUID person, Integer numOfRecommendations){
        try{
            //Creating UserSimilarity object.
            UserSimilarity usersimilarity = new LogLikelihoodSimilarity(model);

            //Creating UserNeighbourHHood object.
            UserNeighborhood userneighborhood = new NearestNUserNeighborhood( 10, usersimilarity, model);

            //Create UserRecomender
            UserBasedRecommender recommender = new GenericUserBasedRecommender(model, userneighborhood, usersimilarity);

            List<RecommendedItem> recommendations = recommender.recommend(uuidToIntId.get(person), numOfRecommendations);
            System.out.println("Trying to recommend " + uuidToIntId.get(person) + " " + person);

            for (RecommendedItem recommendation : recommendations) {
                System.out.println(recommendation);
            }
            return recommendations;

        } catch(Exception e){
            log.error("Error during recommendations", e);
        }
        return null;
    }
}
