package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-db.xml",
        "classpath:spring/spring.xml",
        "classpath:spring/spring-app.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2019, Month.APRIL, 30, 16, 0), "Админ Завтрак", 5000);
        Meal created = service.create(newMeal, 100001);
        assertMatch(created, service.get(100010, 100001));
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID + 2, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(5, 100001);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID + 2, USER_ID);
        assertMatch(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(5, 100001);
    }

    @Test
    public void getBetweenDates() {
        assertMatch(Arrays.asList(MEAL6, MEAL5, MEAL4), service.getBetweenDates(LocalDate.of(2015, Month.MAY, 31), LocalDate.of(2015, Month.MAY, 31), USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, Arrays.asList(MEAL8, MEAL7));
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1);
        updated.setCalories(700);
        updated.setDescription("Завтрак123");
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID + 2, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = new Meal(MEAL1);
        updated.setCalories(700);
        updated.setDescription("Завтрак123");
        service.update(updated, ADMIN_ID);
    }
}