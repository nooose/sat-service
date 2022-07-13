package com.noose.storemanager.domain.revenue;

import com.noose.storemanager.domain.BaseEntity;
import com.noose.storemanager.domain.type.Payment;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PAYMENT_DETAIL")
@NoArgsConstructor
public class PaymentDetail extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "PAYMENT_DETAIL_ID")
    private long id;

    private String payerName;
    private String receiverName;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    private int money;

    @ManyToOne
    @JoinColumn(name = "REVENUE_TABLE_ID")
    private RevenueTable revenueTable;

    public PaymentDetail(long id, String payerName, String receiverName, Payment payment, int money) {
        this.id = id;
        this.payerName = payerName;
        this.receiverName = receiverName;
        this.payment = payment;
        this.money = money;
    }
}
