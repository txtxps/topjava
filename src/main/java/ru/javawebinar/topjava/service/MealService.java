package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), userId);
    }

    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<Meal> getBetween(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return repository.getBetween(userId, startDate, endDate, startTime, endTime);
    }
}