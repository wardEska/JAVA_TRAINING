package com.lev2.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QueryKeyWords {
    public static final String SELECT = "SELECT";
    public static final String FROM = "FROM";
    public static final String ORDER_BY = "orderBy";
    public static final String ORDER_DIR = "orderDir";
    public static final String PAGE = "page";
    public static final String PAGE_SIZE = "pageSize";
    public static final String DES = "DESC";
    public static final String ASC = "ASC";

    public static final Set<String> PERMITTED_KEYS = Set.of(
            ORDER_BY,
            ORDER_DIR,
            PAGE,
            PAGE_SIZE
    );

    public static final Set<String> PERMITTED_ORDER_DIR = Set.of(
            DES,
            ASC
    );
}
