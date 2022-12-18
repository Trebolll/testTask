package ru.nsk.java.tasktest.json.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nsk.java.tasktest.json.search.BuyerResult;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private String type = "search";
    private List<BuyerResult> results = new ArrayList<>();
}
