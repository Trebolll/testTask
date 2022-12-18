package ru.nsk.java.tasktest.json.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsk.java.tasktest.repo.BuyerRepository;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String name;

    private List<BuyerRepository.BuyerStatJSON> purchases;

    private Long totalExpenses;
}
