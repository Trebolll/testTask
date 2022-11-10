package ru.nsk.java.tasktest.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase",schema = "task", catalog = "test_task")
public class Purchase {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date_purchase")
    private Date datePurchase;
    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private Product idProduct;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Purchase purchase = (Purchase) o;

        if (id != null ? !id.equals(purchase.id) : purchase.id != null) return false;
        if (idProduct != null ? !idProduct.equals(purchase.idProduct) : purchase.idProduct != null) return false;
        if (datePurchase != null ? !datePurchase.equals(purchase.datePurchase) : purchase.datePurchase != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idProduct != null ? idProduct.hashCode() : 0);
        result = 31 * result + (datePurchase != null ? datePurchase.hashCode() : 0);
        return result;
    }
}
