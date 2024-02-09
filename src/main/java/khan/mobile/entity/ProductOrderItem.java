package khan.mobile.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductOrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productOrderItemId")
    private Long productOrderItemId;
    private String productOrderItemColor;
    private Float productOrderItemSize;
    private String productOrderItemOther;
    private int productOrderItemQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productOrderId")
    private ProductOrder productOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Products products;

    @Builder
    public ProductOrderItem(Long productOrderItemId, String productOrderItemColor, Float productOrderItemSize, String productOrderItemOther, int productOrderItemQuantity, ProductOrder productOrder, Products products) {
        this.productOrderItemId = productOrderItemId;
        this.productOrderItemColor = productOrderItemColor;
        this.productOrderItemSize = productOrderItemSize;
        this.productOrderItemOther = productOrderItemOther;
        this.productOrderItemQuantity = productOrderItemQuantity;
        this.productOrder = productOrder;
        this.products = products;
    }

    public void updateOrderItem(String color, Float size, String other, int quantity) {
        this.productOrderItemColor = color;
        this.productOrderItemSize = size;
        this.productOrderItemOther = other;
        this.productOrderItemQuantity = quantity;
    }
}
