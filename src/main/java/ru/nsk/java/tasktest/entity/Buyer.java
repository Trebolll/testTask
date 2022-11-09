package ru.nsk.java.tasktest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "buyer",schema = "task", catalog = "test_task")
public class Buyer {

    private String firstName;
    private String lastName;

    @Basic
    @Column(name = "id_purchases", nullable = true)
    private Long idPurchases;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Buyer buyer = (Buyer) o;

        if (id != null ? !id.equals(buyer.id) : buyer.id != null) return false;
        if (firstName != null ? !firstName.equals(buyer.firstName) : buyer.firstName != null) return false;
        if (lastName != null ? !lastName.equals(buyer.lastName) : buyer.lastName != null) return false;
        if (idPurchases != null ? !idPurchases.equals(buyer.idPurchases) : buyer.idPurchases != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (idPurchases != null ? idPurchases.hashCode() : 0);
        return result;
    }
}
