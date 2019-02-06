package ru.unisuite.methodicalcomplexservice.exception;

public enum McsErrorCode {

    COULDNT_GET_DATA(0, "Ошибка при получении данных. "),
    COULDNT_EXECUTE_SQL_COMMAND(1, "Ошибка при выполнении команды на изменение данных. "),
    COULDNT_UPDATE_MODULE_VERSION(2, "Ошибка при проверке обновления модуля. "),
    COULDNT_EXPORT_MC_XML(3, "Не удается выгрузить XML файл методического комплектса. ");
	

    private final int errorCode;
    private final String description;

    private McsErrorCode(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public int getCodeValue() {
        return errorCode;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
      return errorCode + ": " + description;
    }
    
}
