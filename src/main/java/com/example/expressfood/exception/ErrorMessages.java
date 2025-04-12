package com.example.expressfood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    MISSING_REQUIRED_FILED("Missing required filed"),
    RECORD_ALREADY_EXIST("Record is already exist"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No Record with provided has been found"),
    UPDATE_RECORD_ERROR("Error will updating the record"),
    DELETE_RECORD_ERROR("Error will deleting the record"),
    ATTRIBUTE_UPDATING_ERROR("Object has attributes that cannot be changed"),
    INVALID_ENTITY("Invalid entity"),
    CANT_BE_DELETE("You can't delete this entity"),
    PASSWORD_LENGTH_ERROR("Password length error"),
    PARAMETER_FORMAT_ERROR("Parameter format error"),
    AUTHORITY_EXCEPTION("Your are not allowed to to this action"),
    ACCESS_DENIED("Access Denied"),
    UNMATCHED_PASSWORD("The old password doesn't match with provided password");

    private final String errorMessage;

}
