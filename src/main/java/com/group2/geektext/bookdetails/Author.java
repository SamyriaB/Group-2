package com.group2.geektext.bookdetails;

// Author info sent in request body when creating author

public record Author(
        String firstName,
        String lastName,
        String biography,
        Integer publisherId
) {}
