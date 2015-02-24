package org.dicadeveloper.weplantaforest.reports.co2;

import javax.validation.constraints.NotNull;

import org.dicadeveloper.weplantaforest.persist.Co2Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class Co2Service {

    private Co2Repository _co2Repository;

    @Autowired
    public Co2Service(@Qualifier("co2TreeRepository") Co2Repository co2Respository) {
        _co2Repository = co2Respository;
    }

    @NotNull
    public Co2Dto getGetCo2OfAllTrees() {
        long amountOfTrees = _co2Repository.countAmountOfTrees();
        double co2 = _co2Repository.getCo2Saving();
        return new Co2Dto(amountOfTrees, co2);
    }
}