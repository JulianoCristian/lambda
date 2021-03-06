package com.jnape.palatable.lambda.functor.builtin;

import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.traversable.Traversable;

import java.util.Objects;
import java.util.function.Function;

/**
 * A functor over some value of type <code>A</code> that can be mapped over and retrieved later.
 *
 * @param <A> the value type
 */
public final class Identity<A> implements Applicative<A, Identity>, Traversable<A, Identity> {

    private final A a;

    public Identity(A a) {
        this.a = a;
    }

    /**
     * Retrieve the value.
     *
     * @return the value
     */
    public A runIdentity() {
        return a;
    }

    /**
     * Covariantly map over the value.
     *
     * @param fn  the mapping function
     * @param <B> the new value type
     * @return an Identity over B (the new value)
     */
    @Override
    @SuppressWarnings("unchecked")
    public <B> Identity<B> fmap(Function<? super A, ? extends B> fn) {
        return (Identity<B>) Applicative.super.fmap(fn);
    }

    @Override
    public <B> Identity<B> pure(B b) {
        return new Identity<>(b);
    }

    @Override
    public <B> Identity<B> zip(Applicative<Function<? super A, ? extends B>, Identity> appFn) {
        return new Identity<>(appFn.<Identity<Function<? super A, ? extends B>>>coerce().runIdentity().apply(a));
    }

    @Override
    public <B> Identity<B> discardL(Applicative<B, Identity> appB) {
        return Applicative.super.discardL(appB).coerce();
    }

    @Override
    public <B> Identity<A> discardR(Applicative<B, Identity> appB) {
        return Applicative.super.discardR(appB).coerce();
    }

    @Override
    public <B, App extends Applicative> Applicative<Identity<B>, App> traverse(
            Function<? super A, ? extends Applicative<B, App>> fn,
            Function<? super Traversable<B, Identity>, ? extends Applicative<? extends Traversable<B, Identity>, App>> pure) {
        return fn.apply(runIdentity()).fmap(Identity::new);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Identity && Objects.equals(a, ((Identity) other).a);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a);
    }
}
