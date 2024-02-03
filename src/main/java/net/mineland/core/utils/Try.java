package net.mineland.core.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Позволяет игрорировать некоторые фичи java<br>
 * Ну а хули нам, хохлам
 */
public class Try {

    public static ThrowableHandler throwableHandler = Throwable::printStackTrace;

    /**
     * Игрорировать проверяемое исключение (если проверяемое исключение возникнет, оно будет в обертке RuntimeException)
     * @param supplier code
     * @param <T>      type result
     * @return result
     */
    public static <T> T unchecked(SupplierThrows<T> supplier) {
        try {
            return supplier.get();
        } catch(Exception e) {
            doThrow0(e);
            throw new AssertionError(); // до сюда код не дойдет
        }
    }

    /**
     * Игрорировать проверяемое исключение (если проверяемое исключение возникнет, оно будет в обертке RuntimeException)
     * @param runnable code
     */
    public static void unchecked(RunnableThrows runnable) {
        try {
            runnable.run();
        } catch(Exception e) {
            doThrow0(e);
            throw new AssertionError(); // до сюда код не дойдет
        }
    }

    /**
     * Игрорировать проверяемое исключение (если проверяемое исключение возникнет, оно будет в обертке RuntimeException)
     * @param predicate code
     */
    public static <T> Predicate<T> unchecked(PredicateThrows<T> predicate) {
        return t -> {
            try {
                return predicate.test(t);
            } catch(Exception e) {
                doThrow0(e);
                throw new AssertionError(); // до сюда код не дойдет
            }
        };
    }

    /**
     * Игнорировать исключения целиком и полностью (если исключение воникнет, тогда просто будет printStackTrace)
     * @param supplier code
     * @param <T>      type result
     * @param def      дефотное значение, если возникнет исключение
     * @return result
     */
    public static <T> T ignore(SupplierThrows<T> supplier, T def) {
        try {
            return supplier.get();
        } catch(Throwable e) {
            throwableHandler.handle(e);
            return def;
        }
    }

    /**
     * Игнорировать исключения целиком и полностью (если исключение воникнет, тогда исключение передаётся в обработчик)
     * @param runnable code
     */
    public static Optional<Throwable> ignore(RunnableThrows runnable) {
        try {
            runnable.run();
        } catch(Throwable e) {
            throwableHandler.handle(e);
            return Optional.of(e);
        }
        return Optional.empty();
    }

    /**
     * Игнорировать исключения целиком и полностью (если исключение воникнет, тогда исключение передаётся в обработчик)
     * @param runnable code
     */
    public static Optional<Throwable> ignore(RunnableThrows runnable, Consumer<Throwable> consumer) {
        try {
            runnable.run();
        } catch(Throwable e) {
            consumer.accept(e);
            throwableHandler.handle(e);
            return Optional.of(e);
        }
        return Optional.empty();
    }

    /**
     * Игрорировать проверяемое исключение (если исключение возникнет, оно будет в обертке RuntimeException)
     * @param predicate code
     * @param <T>       type result
     * @param def       дефотное значение, если возникнет исключение
     */
    public static <T> Predicate<T> ignore(PredicateThrows<T> predicate, boolean def) {
        return t -> {
            try {
                return predicate.test(t);
            } catch(Throwable e) {
                throwableHandler.handle(e);
                return def;
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void doThrow0(Throwable e) throws E {
        throw (E) e;
    }

    public interface SupplierThrows<T> {

        T get() throws Exception;
    }

    public interface RunnableThrows {

        void run() throws Exception;
    }

    public interface PredicateThrows<T> {

        boolean test(T val) throws Exception;
    }

    public interface ThrowableHandler {

        void handle(Throwable throwable);
    }
}