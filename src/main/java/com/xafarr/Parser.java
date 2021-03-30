package com.xafarr;

import java.nio.file.Path;

public interface Parser<T> {
    T parse(Path file);
}
