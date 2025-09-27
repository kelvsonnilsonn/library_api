package com.library.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppConstants {

    // =========== PATHS =========== //
    public static final String BOOK_BASE_PATH = "/api/livros";
    public static final String CREATE_BOOK_PATH = "/create";

    public static final String USER_BASE_PATH = "/api/users";
    public static final String CREATE_USER_PATH = "/create";


    // =========== SYSTEM MESSAGES =========== //

    public static final String USER_DELETED_MSG = "A conta %s foi deletada";
    public static final String BOOK_DELETED_MSG = "O livro %s foi deletado";

    // =========== EXCEPTIONS MESSAGES =========== //

    public static final String INVALID_ISBN_MESSAGE = "O ISBN inserido é inválido.";
    public static final String CANT_CREATE_PASSWORD_MESSAGE = "A senha inserida não segue o padrão (conter mais que 3 caracteres).";
    public static final String USER_NOT_FOUND_MESSAGE = "O usuário não foi encontrado";
    public static final String BOOK_NOT_FOUND_MESSAGE = "O livro não foi encontrado";

}
