package de.msg.schulung.java8.steinbruch.flatmap;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapTest {

    @Test
    public void testFlatMap() {

        @SuppressWarnings("unchecked")
        final List<Integer>[] lists = new List[5];

        Stream.iterate(0, i -> i + 1).limit(5).forEach(index -> {
            lists[index] = Stream.iterate(index, j -> j + 1).limit(5).collect(Collectors.toList());
        });

        final Stream<List<Integer>> streamOfLists = Arrays.stream(lists);

        final Stream<Integer> flattened = streamOfLists.flatMap(Collection::stream);

        final String flattenedString = flattened.map(String::valueOf).collect(Collectors.joining(","));

        System.out.println(flattenedString);

        Assert.assertEquals("0,1,2,3,4,1,2,3,4,5,2,3,4,5,6,3,4,5,6,7,4,5,6,7,8", flattenedString);

    }

    @Test
    @Ignore
    public void testInfiniteFlatMap() {

        @SuppressWarnings("unchecked")
        final Stream<Integer>[] streams = new Stream[3];

        streams[0] = Stream.iterate(1, x -> {
            System.out.println(x);
            return 10 * x;
        }).limit(10);

        // unendlicher Stream
        streams[1] = Stream.iterate(1, new TestInfiniteStreamUnaryOperator());

        streams[2] = Stream.iterate(5, x -> x + 5).limit(10);

        // Stream von Streams, von denen der zweite unendlich ist
        final Stream<Stream<Integer>> stream = Stream.of(streams[0], streams[1], streams[2]);

        // flachklopfen der Streams
        final Stream<Integer> flattened = stream.flatMap(s -> s);

        // durch die terminal operation wird die Stream-Verarbeitung ausgelöst; der erste ist endlich und
        // liefert ein limitiertes Ergebnis, da der zweite unendlich ist, endet seine Verarbeitung beim
        // Mappen nicht und der dritte Stream kommt gar nicht mehr dran
        System.out.println("begin collecting");
        final List<Integer> flattenedList = flattened.collect(Collectors.toList());
        System.out.println("finished collecting");

        final String flattenedString = flattenedList.stream().map(String::valueOf).collect(Collectors.joining(","));

        System.out.println(flattenedString);
    }

    private static class TestInfiniteStreamUnaryOperator implements UnaryOperator<Integer> {

        @Override
        public Integer apply(Integer t) {
//            System.out.println(t);
//            for (long i = 0; i < 10000; i++) ;
            return 2 + t;
        }

    }
}
