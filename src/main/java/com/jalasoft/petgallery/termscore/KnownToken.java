package com.jalasoft.petgallery.termscore;

import java.util.List;

public interface KnownToken {
    List<Term> process(List<Term> terms);
}
