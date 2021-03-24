package com.comp3000.project.cms.converters;

public abstract class Converter<S, T> {

    public abstract S convert(T t) throws Exception;

}
