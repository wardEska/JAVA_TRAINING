package com.lev2.helper;

import java.math.BigDecimal;
import java.util.*;


public class QueryBuilder {
    protected static final int DEFAULT_PAGE_SIZE = 10;
    protected static final int DEFAULT_PAGE = 1;




    public static<T> void addToFilter(Class<T> entityClass,Map<String,Object> filters , String key, String value){
        if(!ReflectionBuilder.hasProperty(entityClass,key) && !isPermittedKey(key)){
            System.out.println("The key : "+ key + " is not found in student");
            return;
        }
        if(key.equalsIgnoreCase(QueryKeyWords.ORDER_DIR) && !isPermittedOrderDir(value)){
            System.out.println("Not Permitted Order Directions");
            return;
        }
        if(key.equalsIgnoreCase(QueryKeyWords.ORDER_BY) && !ReflectionBuilder.hasProperty(entityClass,value)){
            System.out.println("Invalid property");
            return;
        }

        if((key.equalsIgnoreCase(QueryKeyWords.PAGE) || key.equalsIgnoreCase(QueryKeyWords.PAGE_SIZE)) && !isNumber(value)){
            System.out.println(key + " should be a number");
            return;
        }

        filters.put(key,value);
    }

    public static String getOrderBy(Map<String,Object> filters){
        var orderBy = getValue(filters,QueryKeyWords.ORDER_BY);
        return orderBy == null ? null : orderBy.toString();
    }

    public static String getOrderDir(Map<String,Object> filters){
        var orderDirValue = getValue(filters,QueryKeyWords.ORDER_DIR);
        return orderDirValue != null && isPermittedOrderDir(orderDirValue.toString()) ? orderDirValue.toString() : QueryKeyWords.ASC;
    }

    public static int getPageSize(Map<String,Object> filters){
        var pageSizeValue = getValue(filters,QueryKeyWords.PAGE_SIZE);
        var pageSize =  pageSizeValue != null && isNumber(pageSizeValue.toString()) ? Integer.parseInt(pageSizeValue.toString()) : DEFAULT_PAGE_SIZE ;
        return pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
    }
    public static int getPageNumber(Map<String,Object> filters){
        var pageValue = getValue(filters,QueryKeyWords.PAGE);
        var page = pageValue != null && isNumber(pageValue.toString()) ? Integer.parseInt(pageValue.toString()) : DEFAULT_PAGE ;
        return page > 0 ? page : DEFAULT_PAGE;
    }

    public static int getOffset(int page , int pageSize){
        return (page - 1) * pageSize;
    }

    public static String getKey(Map<String,Object> filters , String key){
        return filters.keySet().stream()
                .filter(x->x.equalsIgnoreCase(key)).findFirst().orElse(null);
    }

    public static Object getValue(Map<String,Object> filters , String key){
        var foundKey = getKey(filters , key);
        if(foundKey == null)
            return null;
        return filters.get(foundKey);
    }




    private static boolean isPermittedKey(String key) {
        return key != null && QueryKeyWords.PERMITTED_KEYS
                .stream()
                .anyMatch(x -> x.equalsIgnoreCase(key));
    }

    private static boolean isPermittedOrderDir(String value) {
        return value != null && QueryKeyWords.PERMITTED_ORDER_DIR
                .stream()
                .anyMatch(x -> x.equalsIgnoreCase(value));
    }

    public static boolean isNumber(String value) {
        if (value == null || value.isBlank()) return false;

        try {
            new BigDecimal(value.trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
