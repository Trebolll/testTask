package ru.nsk.java.tasktest.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsk.java.tasktest.entity.Buyer;

import java.util.List;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Long> {

    List<Buyer> findByLastNameContainsIgnoreCase(String lastName);


    @Query("select b from Buyer b " +
            "inner join b.purchases pu " +
            "where pu.product.name=:productName " +
            "group by b " +
            "having count(b)>:minPurchases"
    )
    List<Buyer> findBuyerByProduct(@Param("minPurchases") Long minPurchases,
                                   @Param("productName") String productName
    );

    @Query("select b from Buyer b " +
            "inner join b.purchases pu  " +
            " group by b " +
            "having max(pu.product.cost)<:max and min(pu.product.cost)>:min "
    )
    List<Buyer> findMinMax(@Param("min") int min, @Param("max") int max);

    @Query("select b from Buyer b " +
            "inner join b.purchases pu  " +
            " group by b order by pu.size desc")
    Page<Buyer> findBad(Pageable pageable);

}
