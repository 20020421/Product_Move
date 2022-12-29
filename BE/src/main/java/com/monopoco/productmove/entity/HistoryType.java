package com.monopoco.productmove.entity;

import java.util.stream.Stream;

public enum HistoryType {

    NONE(0),WAREHOUSING(1), EXPORT_TO_DISTRIBUTOR(2), SELL(3);
    private final int type;

    HistoryType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }


    public static HistoryType of(int type)
    {
        return Stream.of(HistoryType.values())
                .filter(p -> p.getType() == type)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static HistoryType copy(HistoryType type) {
        return of(type.getType());
    }

    @Override
    public String toString() {
        switch (type) {
            case 1:
                return "Warehousing";
            case 2:
                return "Export to distributor agent";
            case 3:
                return "Sell";

            default:
                return "NONE";

        }
    }
}
