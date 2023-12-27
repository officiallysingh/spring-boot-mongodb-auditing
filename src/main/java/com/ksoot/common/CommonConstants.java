package com.ksoot.common;

import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class CommonConstants {

  public static final String PATH_VAR_ID = "{id}";

  public static final String MEDIA_TYPE_ANY = "*/*";

  public static final String API_CONTEXT = "/**";

  public static final String SYSTEM_USER = "SYSTEM";

  public static final String HEADER_EXPAND = "x-expand";

  public static final String HEADER_LOCATION = "Location";

  public static final String HEADER_AUTHORIZATION = "Authorization";

  public static final String DOT = ".";

  public static final String SLASH = "/";

  public static final Locale SYSTEM_LOCALE = Locale.getDefault();

  public static final String API = SLASH + "api";

  public static final String V1 = SLASH + "v1";

  public static final String V2 = SLASH + "v2";

  public static final int DEFAULT_PAGE_SIZE = 16;

  // ------ Persistence constants ------
  public static final String GLOBAL_SEQ_ID_GENERATOR = "GLOBAL_SEQ_ID_GENERATOR";

  public static final String UUID2_GENERATOR = "UUID2_GENERATOR";

  public static final String GLOBAL_SEQ_NAME = "global_sequence";

  public static final String GLOBAL_SEQ_INITIAL_VALUE = "1000";
}
