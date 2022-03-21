package com.stason.testing.controller.utils.comparators.test;

import com.stason.testing.model.entity.Test;

import java.util.Comparator;

public class TestDifficultyComparator implements Comparator<Test> {
    @Override
    public int compare(Test o1, Test o2) {
        return o1.getDifficulty().compareTo(o2.getDifficulty());
    }
}
