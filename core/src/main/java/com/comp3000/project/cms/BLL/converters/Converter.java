package com.comp3000.project.cms.BLL.converters;

public interface Converter<From, To> {
    To convert(From t) throws Exception;
}
