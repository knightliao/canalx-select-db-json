package com.knightliao.canalx.db.exception;

/**
 *
 */
public class CanalxSelectDbJsonException extends RuntimeException {
    public CanalxSelectDbJsonException() {
    }

    public CanalxSelectDbJsonException(String message) {
        super(message);
    }

    public CanalxSelectDbJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CanalxSelectDbJsonException(Throwable cause) {
        super(cause);
    }
}
