package com.jalasoft.petgallery.termscore.language;

import com.jalasoft.petgallery.termscore.KnownToken;
import com.jalasoft.petgallery.termscore.Pair;
import com.jalasoft.petgallery.termscore.Term;
import com.jalasoft.petgallery.termscore.TermType;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SpreadOrToken implements KnownToken {

    @Override
    public List<Term> process(List<Term> terms) {
        Map<String, List<Term>> termsMap = new TreeMap<>();
        List<Term> result = new LinkedList<>();

        for (Term term : terms) {
            if (term.is(TermType.OR)) {
                for (Term childTerm : term.getChildren()) {
                    putTermInShard(termsMap, childTerm);
                }
            } else if (term.hasValue()) {
                putTermInShard(termsMap, term);
            } else {
                result.add(term);
            }
        }

        List<Term> orTerms = termsMap.values().stream()
            .map(shard -> {
                if (shard.size() == 1) {
                    return shard.get(0);
                }
                return Term.or(shard);
            })
            .collect(Collectors.toList());
        result.addAll(orTerms);

        return result;
    }

    private void putTermInShard(Map<String, List<Term>> termsMap, Term term) {
        Pair pair = term.getValue();
        List<Term> shard;
        if (termsMap.containsKey(pair.getKey())) {
            shard = termsMap.get(pair.getKey());
        } else {
            shard = new LinkedList<>();
            termsMap.put(pair.getKey(), shard);
        }
        shard.add(term);
    }
}
