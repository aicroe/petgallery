package com.jalasoft.petgallery.termscore;

import com.jalasoft.petgallery.termscore.language.*;

public class InterpreterFactory {

    public static Interpreter get() {
        Language language = new CommaSeparatedLanguage(
            new NotToken(),
            new OrToken(),
            new SpreadOrToken()
        );
        return new Interpreter(language);
    }
}
