package ru.nsk.java.tasktest.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindBuyerByProduct {

    private String productName;
    private Long minPurchases;

}
