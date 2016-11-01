package com.example.qapitalinterview.converters;

/**
 * Created by John on 10/30/2016.
 */

public interface IConverter<I,O> {
    O convert(I from);
}
