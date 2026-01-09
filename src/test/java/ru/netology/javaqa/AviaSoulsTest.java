package ru.netology.javaqa;


import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class AviaSoulsTest {

    @Test
    void compareTo_smallerPrice_returnsNegative() {
        Ticket cheaper = new Ticket("SVO", "LED", 1000, 10, 12);
        Ticket expensive = new Ticket("SVO", "LED", 1500, 10, 12);

        assertTrue(cheaper.compareTo(expensive) < 0, "Билет с меньшей ценой должен быть меньше");
        assertTrue(expensive.compareTo(cheaper) > 0, "Билет с большей ценой должен быть больше");
        assertEquals(0, cheaper.compareTo(new Ticket("SVO", "LED", 1000, 10, 12)),
                "Билеты с одинаковой ценой должны считаться равными");
    }

    @Test
    void search_givenTickets_returnsSortedByPrice() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("SVO", "LED", 1000, 10, 12);
        Ticket t2 = new Ticket("SVO", "LED", 1500, 9, 13);
        Ticket t3 = new Ticket("DME", "LED", 1200, 11, 14);

        manager.add(t1);
        manager.add(t2);
        manager.add(t3);

        Ticket[] expected = {t1, t2};
        Ticket[] actual = manager.search("SVO", "LED");

        assertArrayEquals(expected, actual, "Поиск должен возвращать билеты по маршруту, отсортированные по цене");
    }

    @Test
    void compare_flightTimeShorter_returnsNegative() {
        Ticket t1 = new Ticket("SVO", "LED", 1000, 10, 12); // 2 часа
        Ticket t2 = new Ticket("SVO", "LED", 1500, 9, 13);  // 4 часа

        TicketTimeComparator comparator = new TicketTimeComparator();

        assertTrue(comparator.compare(t1, t2) < 0, "Билет с меньшей длительностью полёта должен быть меньше");
        assertTrue(comparator.compare(t2, t1) > 0, "Билет с большей длительностью полёта должен быть больше");
        assertEquals(0, comparator.compare(new Ticket("SVO","LED",0,10,12), new Ticket("SVO","LED",0,10,12)),
                "Билеты с одинаковой длительностью должны считаться равными");
    }

    @Test
    void searchAndSortBy_givenComparator_sortsAccordingly() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("SVO", "LED", 1000, 10, 12); // 2 часа
        Ticket t2 = new Ticket("SVO", "LED", 1500, 9, 13);  // 4 часа
        Ticket t3 = new Ticket("DME", "LED", 1200, 11, 14);

        manager.add(t1);
        manager.add(t2);
        manager.add(t3);

        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket[] expected = {t1, t2};
        Ticket[] actual = manager.searchAndSortBy("SVO", "LED", comparator);

        assertArrayEquals(expected, actual, "searchAndSortBy должен сортировать билеты по переданному компаратору");
    }

    @Test
    void search_noMatchingTickets_returnsEmptyArray() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("SVO", "LED", 1000, 10, 12);
        Ticket t2 = new Ticket("DME", "LED", 1200, 11, 14);

        manager.add(t1);
        manager.add(t2);

        Ticket[] actual = manager.search("LED", "SVO");
        assertEquals(0, actual.length, "Если билетов нет, должен возвращаться пустой массив");
    }

    @Test
    void findAll_afterAddingTickets_returnsAllTickets() {
        AviaSouls manager = new AviaSouls();

        Ticket t1 = new Ticket("SVO", "LED", 1000, 10, 12);
        Ticket t2 = new Ticket("SVO", "LED", 1500, 9, 13);

        manager.add(t1);
        manager.add(t2);

        Ticket[] expected = {t1, t2};
        assertArrayEquals(expected, manager.findAll(), "findAll должен возвращать все добавленные билеты");
    }
}
