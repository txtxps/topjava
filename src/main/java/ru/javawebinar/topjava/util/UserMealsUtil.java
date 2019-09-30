package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Boolean> mealsPerDayExceeded = new HashMap<>();
        Map<LocalDate, Integer> caloriesOfDay = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal meal : mealList) {
            caloriesOfDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (a, b) -> a + b);
        }
        for (Map.Entry<LocalDate, Integer> entry : caloriesOfDay.entrySet()) {
            if (entry.getValue() > caloriesPerDay) {
                mealsPerDayExceeded.put(entry.getKey(), true);
            } else {
                mealsPerDayExceeded.put(entry.getKey(), false);
            }
        }
        for (UserMeal meal : mealList) {
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                userMealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), mealsPerDayExceeded.get(meal.getDateTime().toLocalDate())));
            }
        }
        return userMealWithExceedList;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMeal> userMealList = mealList.stream()
                .filter(meal -> meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime))
                .collect(Collectors.toList());
        Map<LocalDate, Integer> caloriesOfDay = mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));
        return userMealList.stream().map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesOfDay.get(meal.getLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}
