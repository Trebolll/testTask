package ru.nsk.java.tasktest.repo;

import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsk.java.tasktest.entity.Buyer;

import java.util.List;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {


    @Query("select b FROM Buyer b where b.lastName=:lastName")
    List<Buyer> findByLastName(@Param("lastName") String lastName);


    @Query("select b from Buyer b " +
            "inner join b.purchase pu " +
            "where pu.product.name=:productName " +
            "group by b " +
            "having count(b)>=:minPurchase"
    )
    List<Buyer> findBuyerByProduct(@Param("minPurchase") Long minPurchase,
                                   @Param("productName") String productName
    );

    @Query(value = "with sums as (" +
            "select b.*, sum(pr.cost) as summa from task.buyer b " +
            "inner join task.purchase pu on b.id=pu.id_buyer " +
            "inner join task.product pr on pr.id = pu.id_product group by b.id ) " +
            "select id, first_name, last_name from sums where summa<:max and summa>:min", nativeQuery = true
    )
    List<Buyer> findMinMax(@Param("min") Long min, @Param("max") Long max);

    @Query("select b from Buyer b " +
            "inner join b.purchase pu  " +
            " group by b order by pu.size desc")
    Page<Buyer> findBad(Pageable pageable);

}
