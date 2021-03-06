/**
 * Copyright 2015 Palantir Technologies
 *
 * Licensed under the BSD-3 License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.palantir.common.base;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.palantir.util.VisitorCheckedException;

public class AbortingVisitors {

    private AbortingVisitors() {/**/}

    /**
     * Since a visitor is purely a consumer of values, it can be cast to a more specific type.  If you have a
     * visitor than consumes Objects, it could also consume any other more specific type.
     */
    public static <T, K extends Exception> AbortingVisitor<T, K> wrap(final VisitorCheckedException<? super T, ? extends K> v) {
        return new AbortingVisitor<T, K>() {
            @Override
            public boolean visit(T item) throws K {
                v.visit(item);
                return true;
            }
        };
    }

    /**
     * Since a visitor is purely a consumer of values, it can be cast to a more specific type.  If you have a
     * visitor than consumes Objects, it could also consume any other more specific type.
     *
     * @param <F> from type
     * @param <T> to type
     */
    public static <F, T extends F, K extends Exception> AbortingVisitor<List<T>, K> wrapBatching(final AbortingVisitor<? super List<F>, ? extends K> v) {
        return new AbortingVisitor<List<T>, K>() {
            @Override
            public boolean visit(List<T> item) throws K {
                return v.visit(Collections.<F>unmodifiableList(item));
            }
        };
    }

    public static <T, K extends Exception> AbortingVisitor<Iterable<T>, K> batching(final AbortingVisitor<? super T, ? extends K> v) {
        return new AbortingVisitor<Iterable<T>, K>() {
            @Override
            public boolean visit(Iterable<T> item) throws K {
                for (T t : item) {
                    if (!v.visit(t)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    /**
     * @deprecated in favor of {@link BatchingVisitableView#filter(Predicate)}
     */
    @Deprecated
    public static <T, K extends Exception> AbortingVisitor<Iterable<T>, K> filterBatch(
            final AbortingVisitor<? super List<T>, K> v, final Predicate<? super T> p)  {
        return new AbortingVisitor<Iterable<T>, K>() {
            @Override
            public boolean visit(Iterable<T> item) throws K {
                List<T> list = ImmutableList.copyOf(Iterables.filter(item, p));
                if (list.isEmpty()) {
                    return true;
                } else {
                    return v.visit(list);
                }
            }
        };
    }

    public static <T> AbortingVisitor<T, RuntimeException> alwaysFalse() {
        return new AbortingVisitor<T, RuntimeException>() {
            @Override
            public boolean visit(T item) throws RuntimeException {
                return false;
            }
        };
    }

    public static <T> AbortingVisitor<T, RuntimeException> alwaysTrue() {
        return new AbortingVisitor<T, RuntimeException>() {
            @Override
            public boolean visit(T item) throws RuntimeException {
                return true;
            }
        };
    }

}
