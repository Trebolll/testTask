package ru.nsk.java.tasktest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nsk.java.tasktest.entity.Buyer;
@Repository
public interface BuyerRepository extends JpaRepository<Buyer,Long> {
}
