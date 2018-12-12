package system;

public class DaoException extends Exception {
    public final static int FAIL_TO_INSERT = 1;
    public final static int UPDATE_FAILED = 2;
    public final static int FAIL_TO_DELETE = 3;
    public final static int SQL_ERROR = 4;
    private final int errorCode;

    public DaoException(int errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }
    public int getErrorCode() {
        return errorCode;
    }
}
