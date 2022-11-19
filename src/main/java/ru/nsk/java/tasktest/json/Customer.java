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
public class Customer {

    private String name;
    private List<PurchaseJson> purchases;
    private Long totalExpenses;
}
