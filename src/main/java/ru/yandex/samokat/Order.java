package ru.yandex.samokat;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class Order {
    public final String firstName;
    public final String lastName;
    public final String address;
    public final String metroStation;
    public final String phone;
    public final int rentTime;
    public final String deliveryDate;
    public final String comment;
    public final String[] color;

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getRandom(int colorNumber){
        Random random = new Random();

        String[] firstNames = new String[] { "Ян", "Брюс", "Мастер", "Изя", "Darth", "Иннокентий" }; // массив firstNames
        final String firstName = firstNames[random.nextInt(firstNames.length)]; // выбор случайного имени из массива

        String[] lastNames = new String[] { "Ли", "О", "Кукуев", "Йода", "Шниперсон", "Vader" }; // массив lastNames
        final String lastName = lastNames[random.nextInt(lastNames.length)];

        String[] addresses = new String[] { "Бутиковский переулок", "Вишнёвый проезд", "Вересковая улица", "Водопроводный переулок", "Графитный проезд", "Головинская набережная" }; // массив addresses
        final String address = addresses[random.nextInt(addresses.length)];

        String[] metroStations = new String[] { "Лубянка", "Сокольники", "Спортивная", "Фили", "ВДНХ", "Волжская" }; // массив metroStations
        final String metroStation = metroStations[random.nextInt(metroStations.length)];

        String[] phones = new String[] { "74950232624", "74950234444", "55550232624", "74950232", "888999111" }; // массив phones
        final String phone = phones[random.nextInt(phones.length)];

        final int rentTime = random.nextInt(6)+1;

        String[] deliveryDates = new String[] { "02.01.2022", "15.03.2022", "03.07.2022", "10.01.2022", "22.11.2022", "25.12.2022" }; // массив deliveryDates
        final String deliveryDate = deliveryDates[random.nextInt(deliveryDates.length)];

        final String comment = RandomStringUtils.randomAlphabetic(100);

        final String[] color = new String[2];
        if (colorNumber == 1) {
            color[0] = "black";
        }
        else if (colorNumber == 2) {
            color[0] = "black";
            color[1] = "gray";
        }
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
