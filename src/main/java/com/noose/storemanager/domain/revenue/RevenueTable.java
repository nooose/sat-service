package com.noose.storemanager.domain.revenue;

import com.noose.storemanager.domain.BaseEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "REVENUE_TABLE")
@NoArgsConstructor
public class RevenueTable extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "REVENUE_TABLE_ID")
    private long id;

    @OneToMany(mappedBy = "revenueTable")
    private List<PaymentDetail> paymentDetails = new ArrayList<>();

}
