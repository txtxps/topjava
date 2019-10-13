package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealRepoImpl implements MealRepository {
    private AtomicInteger count = new AtomicInteger(0);
    private Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    {
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) meal.setId(count.incrementAndGet());
        return mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }


    @Override
    public Meal get(int id) {
        return mealMap.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<Meal>(mealMap.values());
    }
}


