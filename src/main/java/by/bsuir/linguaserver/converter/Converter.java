package by.bsuir.linguaserver.converter;

public interface Converter<F, T> {

    public T convert(F object);
}
