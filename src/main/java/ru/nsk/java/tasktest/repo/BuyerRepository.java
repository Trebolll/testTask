package ru.nsk.java.tasktest.repo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsk.java.tasktest.entity.Buyer;

import java.util.Date;
import java.util.List;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {


    List<Buyer> findByLastNameContainsIgnoreCase(String lastName);


    @Query("select b from Buyer b " +
            "inner join b.purchase pu  " +
            "where pu.product.name=:productName " +
            "group by b " +
            "having count(b)>=:minPurchases"
    )
    List<Buyer> findBuyerByProduct(@Param("productName") String productName,
                                   @Param("minPurchases") Long minPurchases
    );


    @Query(value = "with sums as (" +
            "select b.*, sum(pr.cost) as summa from task.buyer b " +
            "inner join task.purchase pu on b.id=pu.id_buyer " +
            "inner join task.product pr on pr.id = pu.id_product group by b.id ) " +
            "select id, first_name, last_name from sums where summa<:max and summa>:min", nativeQuery = true
    )
    List<Buyer> findMinMax(@Param("min") long min, @Param("max") long max);

    @Query("select b from Buyer b " +
            "left join b.purchase pu  " +
            " group by b order by count(pu.id) asc")
    Page<Buyer> findBad(Pageable pageable);

    @Query(value = "select concat(b.last_name, ' ', b.first_name) as buyerName, pr.name as productName, sum(pr.cost) as expenses from task.buyer b " +
            " inner join " +
            "        task.purchase pu " +
            "            on b.id=pu.id_buyer " +
            "inner join " +
            "task.product pr " +
            "on pr.id = pu.id_product " +
            "where date(pu.date_purchase) between :date1 and :date2 " +
            "group by b.id, pr.name", nativeQuery = true)
    List<BuyerStat> buyersByDate(@Param("date1") Date date1, @Param("date2") Date date2);


    interface BuyerStat {
        @JsonIgnore
        String getBuyerName();

        String getProductName();

        Long getExpenses();
    }
}