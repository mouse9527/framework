package com.mouse.framework.domain.core;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class FixedSequenceService implements SequenceService {
    private final Queue<Long> longSeq;
    private final Queue<String> strSeq;

    public FixedSequenceService(Object... args) {
        longSeq = new LinkedList<>();
        strSeq = new LinkedList<>();
        for (Object arg : args) {
            if (arg instanceof Long) {
                longSeq.offer((Long) arg);
                continue;
            }
            if (arg instanceof String) {
                strSeq.offer((String) arg);
                continue;
            }
            throw new IllegalArgumentException("Illegal type of args");
        }
    }

    @Override
    public Long next() {
        Long seq = longSeq.poll();
        if (Objects.isNull(seq)) throw new IllegalStateException("Not enough long sequence");
        return seq;
    }

    @Override
    public String nextStr() {
        String seq = strSeq.poll();
        if (Objects.isNull(seq)) throw new IllegalStateException("Not enough string sequence");
        return seq;
    }
}
