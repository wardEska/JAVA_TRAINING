package com.lev2.common.dto;

import java.util.List;

public record PagedResult<T>(int page, int pageSize , long totalRecords, List<T> data) {
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(getClass().getSimpleName()).append("{")
                .append("page=").append(page)
                .append(", pageSize=").append(pageSize)
                .append(", totalRecords=").append(totalRecords)
                .append(", totalPages=").append(getTotalPages())
                .append(", data=[");

        data.forEach(item ->
                sb.append("\n  ").append(item)
        );

        sb.append("\n]}");

        return sb.toString();
    }

    public int getTotalPages(){
        return (int)Math.ceil((double)totalRecords/pageSize);
    }
}
