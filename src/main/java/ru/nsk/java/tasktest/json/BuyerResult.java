package ru.nsk.java.tasktest.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsk.java.tasktest.entity.Buyer;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuyerResult {

    private Object criteria;
    private List<Buyer> results = new ArrayList<>();

}
