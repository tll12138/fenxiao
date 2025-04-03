package cn.iocoder.yudao.module.fx.utils;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tll
 * @date 2024-12-30 10:28:12
 */
public final class CollectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

    //~ 创建集合

    public static <T> List<T> newList(T... elements) {
        return fill(new ArrayList<T>(), elements);
    }

    public static <T> List<T> newLinkedList(T... elements) {
        return fill(new LinkedList<T>(), elements);
    }

    public static <T> Set<T> newSet(T... elements) {
        return fill(new HashSet<T>(), elements);
    }

    public static <T extends Comparable<? extends T>> Set<T> newTreeSet(T... elements) {
        return fill(new TreeSet<T>(), elements);
    }

    public static <T> Set<T> newTreeSet(Comparator<T> comparator, T... elements) {
        return fill(new TreeSet<T>(comparator), elements);
    }

    public static <C extends Collection<T>, T> C fill(C collection, T... elements) {
        if (collection != null && elements != null) {
            Collections.addAll(collection, elements);
        }
        return collection;
    }

    private CollectionUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static <T> List<T> emptyToDefault(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(collection);
    }

    /**
     * copy集合中class信息到另一个集合的class
     * copy性能最好的工具
     *
     * @param sources 被复制的集合
     * @param clazz   转换后的class
     * @param <T>     泛型
     * @return list
     */
    public static <T, M> List<T> copyList(Collection<M> sources, Class<T> clazz) {
        try {
            if (!isEmpty(sources) && null != clazz) {
                return Optional.of(sources).orElse(new ArrayList<>()).stream().map(m ->
                        ObjectUtils.copyProperties(m, clazz)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            logger.error("CollectionUtil.copyList执行异常", e);
        }
        return Lists.newArrayList();
    }

}
