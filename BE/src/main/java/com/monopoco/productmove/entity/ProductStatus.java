package com.monopoco.productmove.entity;

import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

@Getter
@ToString
public enum ProductStatus {
    NONE(0),NEWLY_PRODUCED(1), COMING_DISTRIBUTION(2), AT_DISTRIBUTION(3), SOLD(4), ERROR_NEED_WARRANTY(5), UNDER_WARRANTY(6),
    WARRANTY_DONE(7), WARRANTY_RETURNED_TO_CUSTOMER(8), ERROR_NEED_RETURN_FACTORY(9), ERROR_ALREADY_SENT_TO_FACTORY(10),
    ERROR_NEED_SUMMON(11), OUT_OF_WARRANTY_PERIOD(12), RETURN_TO_FACTORY(13);

    private final int status;

    ProductStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


    public static ProductStatus of(int status)
    {
        return Stream.of(ProductStatus.values())
                .filter(p -> p.getStatus() == status)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static ProductStatus copy(ProductStatus status) {
        return of(status.getStatus());
    }

    @Override
    public String toString()
    {
        switch (status) {
            case 1:
                return "Newly Produced";
            case 2:
                return "Coming Distribution";
            case 3:
                return "At Distribution";
            case 4:
                return "Sold";
            case 5:
                return "Error need warranty";
            case 6:
                return "UNDER_WARRANTY";
            case 7:
                return "WARRANTY_DONE";
            case 8:
                return "WARRANTY_RETURNED_TO_CUSTOMER";
            case 9:
                return "ERROR_NEED_RETURN_FACTORY";
            case 10:
                return "ERROR_ALREADY_SENT_TO_FACTORY";
            case 11:
                return "ERROR_NEED_SUMMON";
            case 12:
                return "OUT_OF_WARRANTY_PERIOD";
            case 13:
                return "RETURN_TO_FACTORY";
            default:
                return "NONE";

        }
    }

}
