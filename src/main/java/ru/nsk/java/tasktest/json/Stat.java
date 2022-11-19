package ru.nsk.java.tasktest.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stat {

    private String type="stat";
    private Long totalDays;

    private List<Customer> customers;
    private Long totalExpenses;
    private Double avgExpenses;
}
