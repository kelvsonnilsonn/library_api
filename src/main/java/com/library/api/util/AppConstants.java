package com.library.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {

    // =========== PATHS =========== //
    public static final String BOOK_BASE_PATH = "/livros";
    public static final String ID_PATH = "/{id}";
    public static final String SEARCH_AVAILABLE_PATH = "/disponivel";
    public static final String EVENT_BASE_PATH = "/events";
    public static final String ADMIN_PATH = "/admin";

    public static final String USER_BASE_PATH = "/users";

    public static final String AUTH_BASE_PATH = "/auth";
    public static final String LOGIN_PATH = "/login";
    public static final String REGISTER_PATH = "/register";
    public static final String CHANGE_PASSWORD_PATH = "/new";

    public static final String BORROW_BASE_PATH = "/borrow";
    public static final String DUE_PATH = "/atrasados";
    public static final String HISTORY_PATH = "/historico";

    public static final String EVENTS_IN_INTERVAL_PATH = "/interval";
    public static final String MY_EVENTS_IN_INTERVAL_PATH = "/my-events/interval";
    public static final String USER_EVENTS_IN_INTERVAL_PATH = "/user-events/interval";


    // =========== SYSTEM MESSAGES =========== //

    public static final String SUCCESS_PASSWORD_CHANGE_MSG = "A senha foi alterada";

    // =========== EXCEPTIONS MESSAGES =========== //

    public static final String INVALID_ISBN_MESSAGE = "O ISBN inserido é inválido.";
    public static final String CANT_CREATE_PASSWORD_MESSAGE = "A senha inserida não segue o padrão (conter mais que 3 caracteres).";
    public static final String USER_NOT_FOUND_MESSAGE = "O usuário não foi encontrado";
    public static final String BOOK_NOT_FOUND_MESSAGE = "O livro não foi encontrado";

    public static final String PRE_AUTHORIZE_ADMIN_REQUISITION = "hasAuthority('ADMIN_ROLE')";
    public static final String PRE_AUTHORIZE_ALL_REQUISITION = "permitAll()";


    // ============ EVENTS ================ //

    public static final String AGGREGATE_BOOK_TYPE = "BOOK";
    public static final String AGGREGATE_BORROW_TYPE = "BORROW";
    public static final String AGGREGATE_USER_TYPE = "USER";
}
