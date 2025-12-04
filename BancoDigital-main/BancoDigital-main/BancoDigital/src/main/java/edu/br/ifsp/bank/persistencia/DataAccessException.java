package edu.br.ifsp.bank.persistencia;

public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataAccessException(String msg) {
		super(msg);
	}

	public DataAccessException(Throwable t) {
		super(t);
	}

}
