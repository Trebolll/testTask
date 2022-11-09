package ru.nsk.java.tasktest.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseSearchDate {

    private Date dateFrom;

    private Date dateTo;

}
